package org.olms_testselenium.Script;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {
    public static WebDriver driver;


    static Logger logger = LogManager.getLogger("editProfile");
    @BeforeMethod
    void setup(ITestContext context) {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get("https://olms.codedao.io.vn/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        context.setAttribute("driver", driver);
    }

    public WebDriver getDriver(){
        return driver;
    }

    @AfterMethod
    void tearDown() {
        driver.quit();
    }
//    @AfterClass
//    void tearDown() {
//        driver.quit();
//    }
}
