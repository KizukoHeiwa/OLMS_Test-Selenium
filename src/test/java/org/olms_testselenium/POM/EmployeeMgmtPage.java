package org.olms_testselenium.POM;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class EmployeeMgmtPage extends BasePage {
    By editUserProfile(String userName) {
        return By.xpath("//td[text()='" + userName + "']/following-sibling::td//button[@type='button']");
    }

    public EmployeeMgmtPage(WebDriver driver) {
        this.driver = driver;
    }

    By fieldByValue(String value) {
        return By.xpath("//input[@value='" + value + "']");
    }

    By btnByText(String text) {
        return By.xpath("//button[text()='" + text + "']");
    }

    By ariaInvalidByLabel(String label) {
        return By.xpath("//label[text()='" + label + "']/following-sibling::div//input[@aria-invalid='true']");
    }

    By calendarByLabel(String label) {
        return By.xpath("//label[text()='" + label + "']/following::input[1]");
    }

    public void clickNV(String userName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement targetElement = wait.until(ExpectedConditions.visibilityOfElementLocated(editUserProfile(userName)));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", targetElement);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        targetElement.click();
    }


    public void setInputFieldByValue(String value, String inputdata) {
        WebElement element = driver.findElement(this.fieldByValue(value));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(element));

        element.clear();
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click(element).perform();
        element.sendKeys(inputdata);

    }

    public void clickBtnByText(String text) {
        driver.findElement(btnByText(text)).click();
    }

    public void testAriaInvalidAppearsWithInvalidInput(String label) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
            wait.until(ExpectedConditions.visibilityOfElementLocated(this.ariaInvalidByLabel(label)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setInputDayByLabel(String label, String inputdata) {
        WebElement element = driver.findElement(this.calendarByLabel(label));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(element));

        element.clear();
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click(element).perform();
        element.sendKeys(inputdata);

    }
    public void checkData(String value){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.fieldByValue(value)));

        String actualValue = element.getAttribute("value");

        if (value.equals(actualValue)) {
        } else {
        }
    }




}
