package org.olms_testselenium.POM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ClassDetailsPage {
    By StudentByIndexFromList(int index) {
        return By.xpath("//table/tbody/tr[" + index + "]");
    }

    By GeneralInformationByIndexFromList = By.xpath("//div[@role = 'button']//following-sibling::div//input[@disabled = '']");

    public void click_Student_ByIndex(WebDriver driver, int index) {
        driver.findElement(this.StudentByIndexFromList(index)).click();
    }

    public List<String> get_GeneralInformationByIndex(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(this.GeneralInformationByIndexFromList));

            Thread.sleep(1000);

            List<String> generalInformation = new ArrayList<>();

            for (WebElement element : driver.findElements(this.GeneralInformationByIndexFromList)) {
                generalInformation.add(element.getAttribute("value"));
            }

            return generalInformation;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
