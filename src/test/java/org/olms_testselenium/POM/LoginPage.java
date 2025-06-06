package org.olms_testselenium.POM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// page_url = https://olms.codedao.io.vn
public class LoginPage {
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    By emailOrUsername = By.id("emailOrUsername");
    By password = By.id("password");
    By loginButton = By.xpath("//button[@type='submit']");

    public void login(String emailOrUsername, String password) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(loginButton));

            driver.findElement(this.emailOrUsername).sendKeys(emailOrUsername);
            driver.findElement(this.password).sendKeys(password);
            Thread.sleep(500);
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(loginButton)).click().perform();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}