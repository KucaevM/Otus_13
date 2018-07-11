
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.WebDriverManager;

import java.io.FileWriter;
import java.io.IOException;

public class BaseTest {

    protected WebDriver driver;
    protected String textError = "";

    @BeforeClass
    public void BeforeClass(){

        driver = WebDriverManager.getInstance();

    }

    @AfterClass
    public void AfterClass() {

        try(FileWriter writer = new FileWriter("./target/logs.txt", false))
        {
            writer.write(textError);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        System.out.println(textError);
        driver.quit();
    }
}

