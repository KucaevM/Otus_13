import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import utils.WebDriverManager;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.lang.Thread;

public class XSSTestClass extends BaseTest{

    @Test(dataProvider = "xsstest")
    public void test(String url, String xssQuery) {

        String xpathForSearch = "//input[@type = 'text']";
        Boolean errorIsPresent = false;

        driver.get(url);
        List<WebElement> listElements = driver.findElements(By.xpath(xpathForSearch));

        for (int i = 1; i < listElements.size(); i++) {

            driver.get(url);
            WebElement element = driver.findElement(By.xpath("("+xpathForSearch+")[" + i + "]"));
            if (element.isDisplayed()) {
               String nameElement = element.getAttribute("Name");

                try {
                    element.sendKeys(xssQuery);
                    new Actions(driver).sendKeys(Keys.ENTER).build().perform();
                    Thread.sleep(1000);
                } catch (Exception e){
                    e.printStackTrace();
                    continue;
                }

                try {
                    Object returnValue = ((FirefoxDriver) driver).executeScript("return inject");
                    if (returnValue.equals(true)) {
                        textError =  textError + "На странице " + url + " элемент с имененм " + nameElement + " имеет XSS уязвимость " + xssQuery + "! \r\n";
                        ((FirefoxDriver) driver).executeScript("var inject = false");
                        errorIsPresent = true;
                    }
                } catch (Exception e){
                  e.printStackTrace();
                  continue;

                }
            }
        }
        if (errorIsPresent.equals(false)) {
            textError =  textError + "На странице " + url + " элементов с уязвимостью " + xssQuery + " не обнаружено. \r\n";
        }
    }

    @DataProvider(name = "xsstest")
    private Object[][] xssDataProvider(){

        ArrayList<String> SiteUrl = new ArrayList();
        SiteUrl.add("https://trudogolik24.ru/");
        SiteUrl.add("https://yandex.ru/");
        SiteUrl.add("https://www.gismeteo.ru/");

        ArrayList<String> stringXSS = new ArrayList();
        stringXSS.add("<script>var inject = true</script>");
        stringXSS.add("<scr<script>ipt>var inject = true</scr</script>ipt>");

        Object[][] dataObject = new Object[SiteUrl.size()*stringXSS.size()][2];

        int number = 0;
        for(String url : SiteUrl){
            for(String XSS : stringXSS){
                dataObject[number][0] = url;
                dataObject[number][1] = XSS;
                number = number + 1;
            }
        }
        return dataObject;
    }

}
