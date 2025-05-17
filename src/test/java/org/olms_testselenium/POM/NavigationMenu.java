package org.olms_testselenium.POM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NavigationMenu extends BasePage{



    By menu(String menuName){

        return By.xpath("//span[text()='"+menuName+"']");
    }

    By submenu(String submenuName){

        return By.xpath("//p[text()='"+submenuName+"']");
    }


    public NavigationMenu(WebDriver driver){
        this.driver = driver;
    }


    public void clickMenu(String menuName){
        try {
            WebElement element = driver.findElement(this.menu(menuName));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            Thread.sleep(500);

            Actions actions = new Actions(driver);
            actions.moveToElement(element).click().perform();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    public void clickMenuCon(String submenuName){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(submenu(submenuName)));

        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(submenu(submenuName))).click().perform();
    }

}
