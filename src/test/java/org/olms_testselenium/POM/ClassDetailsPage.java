package org.olms_testselenium.POM;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ClassDetailsPage {
    WebDriver driver;

    public ClassDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    By StudentByIndexFromList(int index) {
        return By.xpath("//table/tbody/tr[" + index + "]");
    }

    By GeneralInformationByIndexFromList = By.xpath("//div[@role = 'button']//following-sibling::div//input[@disabled = '']");

    By ErrorPopUp = By.xpath("//div[@role = 'dialog']//h2[contains(text(), 'Lỗi')]");

    By ButtonByText(String text) {
        return By.xpath("//button[contains(text(), '" + text + "')]");
    }

    By StudentCboBox = By.xpath("//div[@role = 'dialog']//input[@role='combobox']");

    By listBoxPopupByValue(String value) {
        return By.xpath("//ul[@role = 'listbox']/li[contains(text(), '"+value+"')]");
    }

    By StudentTrialChk = By.xpath("//div[@role = 'dialog']//input[@value='STUDENT_TRIAL']");

    By ListLichHoc = By.xpath("//div[@role = 'dialog']//*[@data-testid='CheckBoxIcon']/preceding-sibling::input");

    By TextFieldByLabel(String label) {
        return By.xpath("//div[@role = 'dialog']//label[contains(text(), '"+label+"')]/following-sibling::div/input");
    }

    By StatusStudentByName(String name) {
        return By.xpath("//h4[contains(text(), 'Danh sách học viên')]/ancestor::div[@role = 'button']/parent::div//tbody//p[contains(text(), '" + name + "')]/ancestor::tr/td[9]//span");
    }

    By ActionStudentByNameByFunction(String name, String function) {
        return By.xpath("//h4[contains(text(), 'Danh sách học viên')]/ancestor::div[@role = 'button']/parent::div//tbody//p[contains(text(), '" + name + "')]/ancestor::tr/td[10]//*[@data-testid='" + function + "']/parent::button");
    }

    By SoBuoiGhiByName(String name) {
        return By.xpath("//h4[contains(text(), 'Danh sách học viên')]/ancestor::div[@role = 'button']/parent::div//tbody//p[contains(text(), '" + name + "')]/ancestor::tr/td[4]");
    }

    By SoBuoiConLaiByName(String name) {
        return By.xpath("//h4[contains(text(), 'Danh sách học viên')]/ancestor::div[@role = 'button']/parent::div//tbody//p[contains(text(), '" + name + "')]/ancestor::tr/td[6]");
    }

    public void click_Student_ByIndex(int index) {
        WebElement element = driver.findElement(this.StudentByIndexFromList(index));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(element));

        Actions actions = new Actions(driver);
        actions.moveToElement(element).click(element).perform();
    }

    public void click_ButtonByText(String text) {
        try {
            WebElement element = driver.findElement(this.ButtonByText(text));

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(element));

            element.sendKeys(Keys.ARROW_DOWN);

            Thread.sleep(500);

            Actions actions = new Actions(driver);
            actions.scrollToElement(element).moveToElement(element).click(element).perform();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> get_GeneralInformationByIndex() {
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

    public void check_ErrorPopUp() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(this.ErrorPopUp));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void set_StudentCboBox(String value) {
        try {
            if (value.isEmpty()) return;

            WebElement element = driver.findElement(this.StudentCboBox);
            element.sendKeys(value);

            Thread.sleep(500);

            Actions actions = new Actions(driver);
            WebElement element2 = driver.findElement(this.listBoxPopupByValue(value));
            actions.moveToElement(element2).click(element2).perform();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void click_StudentTrialChk() {
        WebElement element = driver.findElement(this.StudentTrialChk);

        Actions actions = new Actions(driver);
        actions.moveToElement(element).click(element).perform();
    }

    public String get_SoBuoiField() {
        return driver.findElement(this.TextFieldByLabel("Số buổi")).getAttribute("value");
    }

    public void click_LichHoc_ByIndex(int index) {
        Actions actions = new Actions(driver);

        if (index <= 0) return;
        List<WebElement> listLichHoc = driver.findElements(this.ListLichHoc);
        for (int i = 1; i < listLichHoc.size(); i++) {
            if (i == index) continue;
            actions.moveToElement(listLichHoc.get(i)).click(listLichHoc.get(i)).perform();
        }
    }

    public void set_SoBuoiField(String value) {
        driver.findElement(this.TextFieldByLabel("Số buổi")).sendKeys(value);
    }

    public void set_GhiChuField(String value) {
        driver.findElement(this.TextFieldByLabel("Ghi chú")).sendKeys(value);
    }

    public String get_StatusStudentByName(String name) {
        return driver.findElement(this.StatusStudentByName(name)).getText();
    }

    public void click_ActionStudentByNameByFunction(String name, String function) {
        try {
            Actions actions = new Actions(driver);
            WebElement element = driver.findElement(this.ActionStudentByNameByFunction(name, function));

            element.sendKeys(Keys.ARROW_DOWN);

            actions.moveToElement(element).click(element).perform();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String get_SoBuoiGhiByName(String name) {
        return driver.findElement(this.SoBuoiGhiByName(name)).getText();
    }
    public String get_SoBuoiConLaiByName(String name) {
        return driver.findElement(this.SoBuoiConLaiByName(name)).getText();
    }
}
