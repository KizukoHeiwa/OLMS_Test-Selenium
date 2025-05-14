package org.olms_testselenium.POM;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ClassesPage {
    WebDriver driver;

    public ClassesPage(WebDriver driver) {
        this.driver = driver;
    }

    By AsideList_Label(String label) {
        return By.xpath("//aside//li//a[. = '" + label + "']");
    }

    By AsideList_Education(String to) {
        return By.xpath("//aside//li//a[contains(@href, '/academic/" + to + "')]");
    }

    By List_ClassByName(String name) {
        return By.xpath("//table//a[contains(., '" + name + "')]");
    }

    By Btn_ClassByNameByFunction(String name, String function) {
        return By.xpath("//table//tr[contains(., '"+name+"')]//.[contains(@data-testid, '"+function+"')]//ancestor::button");
    }

    By SearchField = By.xpath("//input[@placeholder='Tìm kiếm lớp học']");

    public void click_AsideLabel(String label) {
        try {
            WebElement element = driver.findElement(this.AsideList_Label(label));
            element.sendKeys(Keys.ARROW_DOWN);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            Thread.sleep(500);

            Actions actions = new Actions(driver);
            actions.moveToElement(element).click().perform();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void click_Education(String to) {
        WebElement element = driver.findElement(this.AsideList_Education(to));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(element));

        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
    }

    public void click_ClassByName(String name) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(this.List_ClassByName(name)));

            WebElement element = driver.findElement(this.List_ClassByName(name));
//            element.sendKeys(Keys.ARROW_DOWN);
//
//            Actions actions = new Actions(driver);
//            actions.moveToElement(element).click(element).perform();
            element.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void search_ClassByName(String name) {
        try {
            WebElement element = driver.findElement(this.SearchField);
            element.sendKeys(name);
            element.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void click_BtnClassByNameByFunction(String name, String function) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(this.Btn_ClassByNameByFunction(name, function)));

            WebElement element = driver.findElement(this.Btn_ClassByNameByFunction(name, function));
            element.sendKeys(Keys.ARROW_DOWN);

            Actions actions = new Actions(driver);
            actions.moveToElement(element).click(element).perform();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
