package org.olms_testselenium.POM;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class ClassPage extends BasePage {

    public ClassPage(WebDriver driver) {
        this.driver=driver;
    }

    By fieldByLabel(String label){
        return By.xpath("//label[text()='"+label+"']/following::input[@type='text'][1]");
    }
    By classScheduleBtn = By.xpath("//label[text()='Lịch học']/following-sibling::button");
    By classScheduleByDateAndTime(String date, String time){
        return By.xpath("//ul[@role='listbox']//li[contains(., '"+date+"')  and contains(., '"+time+"')]");
    }
    By listPopUpByText(String text){
        return By.xpath("//ul[@role='listbox']//li[contains(.,'"+text+"')]");
    }
    By btnByText(String text) {
        return By.xpath("//button[text()='"+text+"']");
    }
    By popUpError = By.xpath("//h2[contains(text(),'Lỗi')]");

    By labelCard(String label){
        return By.xpath("//label[text()='"+label+"']//following-sibling::div/div");
    }
    By status = By.xpath("//label[text()='Trạng thái']/following-sibling::div//div[@id='class-status']");

    public void setInputFieldByLabel(String label, String inputdata) {
        WebElement element = driver.findElement(this.fieldByLabel(label));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(element));

        element.clear();
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click(element).perform();
        element.sendKeys(inputdata);

        if (Objects.equals(element.getAttribute("role"), "combobox")) {

            WebElement element2 = driver.findElement(this.listPopUpByText(inputdata));

            actions.moveToElement(element2).click(element2).perform();
        }

    }

    public void clickClassScheduleBtn(){
        driver.findElement(classScheduleBtn).click();
    }

    public void clickBtnByText(String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement targetElement = wait.until(ExpectedConditions.visibilityOfElementLocated(btnByText(text)));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        targetElement.click();
    }
    public void checkPopupError(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(this.popUpError));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void setClassSchedule(String label, String date, String time){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(this.labelCard(label)));

        element.click();
        WebElement targetElement=wait.until(ExpectedConditions.elementToBeClickable(this.classScheduleByDateAndTime(date,time)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", targetElement);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        targetElement.click();
    }
    public void scrollDowṇ(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement targetElement = wait.until(ExpectedConditions.visibilityOfElementLocated(status));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", targetElement);
    }
    public void setTeachingHours(String label, String[] hours) {
        List<WebElement> inputs = driver.findElements(this.labelCard(label));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (int i = 0; i < inputs.size() && i < hours.length; i++) {
            WebElement input = inputs.get(i);

            wait.until(ExpectedConditions.elementToBeClickable(input));

            Actions actions = new Actions(driver);
            actions.moveToElement(input).click().perform();
            String optionXPath = "//ul[@role='listbox']//li[contains(.,'" + hours[i] + "')]";
            WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(optionXPath)));

            option.click();
        }
    }
    public void setEffectiveDates(String label, String dateValue) {

        List<WebElement> inputs = driver.findElements(fieldByLabel(label));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (int i = 0; i < inputs.size(); i++) {
            WebElement input = inputs.get(i);
            wait.until(ExpectedConditions.elementToBeClickable(input));

            input.click();
            input.sendKeys(dateValue);

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public String getDefaultStatus() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement statusElement = wait.until(ExpectedConditions.visibilityOfElementLocated(status));
        return statusElement.getText();
    }
    public void selectStatus(String statusText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(status));
        dropdown.click();

        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(listPopUpByText(statusText)));
        option.click();
    }


}
