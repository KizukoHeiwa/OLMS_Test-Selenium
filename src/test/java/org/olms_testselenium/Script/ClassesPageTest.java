package org.olms_testselenium.Script;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.olms_testselenium.POM.ClassDetailsPage;
import org.olms_testselenium.POM.ClassesPage;
import org.olms_testselenium.POM.EditClassDialog;
import org.olms_testselenium.POM.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class ClassesPageTest {
    WebDriver driver;

    ClassesPage classesPage = new ClassesPage();
    ClassDetailsPage classDetailsPage = new ClassDetailsPage();


    void pointToClassesPage() {
        classesPage.click_AsideLabel(driver, "Đào tạo");
        classesPage.click_Education(driver, "classes");
    }

    void pointToClassByName(String className) {
        pointToClassesPage();
        classesPage.click_ClassByName(driver, className);
    }

    private void turnOffImplicitWaits() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    }

    private void turnOnImplicitWaits() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    }

    @BeforeMethod
    public void setup() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get("https://olms.codedao.io.vn/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        LoginPage loginPage = new LoginPage();
        loginPage.login(driver, "testadmin", "test1234");
    }

    @Test
    public void verifyData() {
        String className = "Sunny 66";
        String[] fieldsData = {"Kindy 4", "15/01/2024", "Đang học", "7", "1"};

        pointToClassByName(className);

        List<String> generalInformation = classDetailsPage.get_GeneralInformationByIndex(driver);

        for (String data : fieldsData) {
            if (!generalInformation.contains(data)) {
                Assert.fail("\n" + Arrays.toString(fieldsData) + "\n" + generalInformation);
            }
        }
    }

    @Test
    public void modifyGeneralInformation() {
        String className = "Sunny 6";
        String buttonFunction = "Edit";

        String[] legends = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu", "Khoá học"};
        String[] data = {"Sunny 66", "11", "21/04/2024", "Online"};

        pointToClassesPage();
        classesPage.click_BtnClassByNameByFunction(driver, className, buttonFunction);

        EditClassDialog editClassDialog = new EditClassDialog();
        for (int i = 0; i < legends.length; i++) {
            editClassDialog.setInputFieldByLegend(driver, legends[i], data[i]);
        }

        editClassDialog.clickBtnByText(driver, "Lưu");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
