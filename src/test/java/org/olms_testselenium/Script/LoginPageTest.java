package org.olms_testselenium.Script;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.olms_testselenium.POM.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;


public class LoginPageTest {
    WebDriver driver;

    @BeforeMethod
    void setup() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get("https://olms.codedao.io.vn/");
        driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    @Test
    void login() {
        LoginPage loginPage = new LoginPage();

        loginPage.login(driver, "testadmin", "test1234");
        Assert.assertNotNull(driver.findElement(By.xpath("//h2[contains(text(), 'OLMS Admin')]")).getText());
    }

    @AfterClass
    void tearDown() {
        driver.quit();
    }

}