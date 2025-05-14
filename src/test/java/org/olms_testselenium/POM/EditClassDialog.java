package org.olms_testselenium.POM;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

public class EditClassDialog {
    WebDriver driver;

    public EditClassDialog(WebDriver driver) {
        this.driver = driver;
    }

    By inputFieldByLegend(String legend) {
        return By.xpath("//div[@role='dialog']//label[contains(text(), '"+legend+"')]//following-sibling::div/input");
    }

    By listBoxPopupByValue(String value) {
        return By.xpath("//ul[@role = 'listbox']/li[contains(text(), '"+value+"')]");
    }

    By btnByText(String text) {
        return By.xpath("//div[@role='dialog']//button[contains(text(), '"+text+"')]");
    }

    public void setInputFieldByLegend(String legend, String value) {
        if (value.isEmpty()) return;
        WebElement element = driver.findElement(this.inputFieldByLegend(legend));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(element));

        element.clear();
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click(element).perform();
        element.sendKeys(value);

        if (Objects.equals(element.getAttribute("role"), "combobox")) {
            WebElement element2 = driver.findElement(this.listBoxPopupByValue(value));

            actions.moveToElement(element2).click(element2).perform();
        }
    }

    public void clickBtnByText(String text) {
        WebElement element = driver.findElement(this.btnByText(text));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(element));

        Actions actions = new Actions(driver);
        actions.moveToElement(element).click(element).perform();
    }
}
