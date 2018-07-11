package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.util.logging.Level;

public class WebDriverManager {
    private static WebDriver driver;

    public static WebDriver getInstance() {

        String browserName = "firefox";

        if (driver == null) {
            if ("chrome".equals(browserName)) {
                DesiredCapabilities caps = DesiredCapabilities.chrome();
                LoggingPreferences logs = new LoggingPreferences();
                logs.enable(LogType.PERFORMANCE, Level.INFO);
                caps.setCapability(CapabilityType.LOGGING_PREFS, logs);
                driver = new ChromeDriver(caps);
                driver.manage().window().maximize();
            } else if ("chrome-headless".equals(browserName)) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless");
                driver = new ChromeDriver(options);
            } else if ("IE".equals(browserName)) {
                driver = new InternetExplorerDriver();
            }
            else if ("firefox".equals(browserName)) {
                driver = new FirefoxDriver();
            }

        }
        return driver;
    }
}
