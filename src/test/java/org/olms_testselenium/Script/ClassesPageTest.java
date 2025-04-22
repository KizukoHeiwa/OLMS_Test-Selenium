package org.olms_testselenium.Script;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.olms_testselenium.POM.ClassDetailsPage;
import org.olms_testselenium.POM.ClassesPage;
import org.olms_testselenium.POM.EditClassDialog;
import org.olms_testselenium.POM.LoginPage;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

//@Listeners(SimpleListener.class)
public class ClassesPageTest extends BaseTest {
    static Logger logger = LogManager.getLogger("LoginPageTest");

    ClassesPage classesPage = new ClassesPage(driver);
    ClassDetailsPage classDetailsPage = new ClassDetailsPage();

    void pointToClassesPage() {
        classesPage.click_AsideLabel("Đào tạo");
        classesPage.click_Education("classes");
    }

    void pointToClassByName(String className) {
        pointToClassesPage();
        classesPage.click_ClassByName(className);
    }

    private void turnOffImplicitWaits() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    }

    private void turnOnImplicitWaits() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    }

    @BeforeMethod
    public void setup(ITestContext context) {
        super.setup(context);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("testadmin", "test1234");
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
        classesPage.click_BtnClassByNameByFunction(className, buttonFunction);

        EditClassDialog editClassDialog = new EditClassDialog(driver);
        for (int i = 0; i < legends.length; i++) {
            editClassDialog.setInputFieldByLegend(legends[i], data[i]);
        }

        editClassDialog.clickBtnByText("Lưu");
    }

    @Test
    public void modifyGeneralInformation_ClassNameInvalid_1() {
        String className = "Sunny 66";
        String buttonFunction = "Edit";

        String[] legends = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu", "Khoá học"};
        String[] data = {"", "11", "21/04/2024", "Online"};

        pointToClassesPage();
        classesPage.click_BtnClassByNameByFunction(className, buttonFunction);

        EditClassDialog editClassDialog = new EditClassDialog(driver);
        for (int i = 0; i < legends.length; i++) {
            editClassDialog.setInputFieldByLegend(legends[i], data[i]);
        }

        editClassDialog.clickBtnByText("Lưu");
        classDetailsPage.check_ErrorPopUp(driver);
    }

    @Test
    public void modifyGeneralInformation_ClassNameInvalid_2() {
        String className = "Sunny 66";
        String buttonFunction = "Edit";

        String[] legends = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu", "Khoá học"};
        String[] data = {"!@#*@", "11", "21/04/2024", "Online"};

        pointToClassesPage();
        classesPage.click_BtnClassByNameByFunction(className, buttonFunction);

        EditClassDialog editClassDialog = new EditClassDialog(driver);
        for (int i = 0; i < legends.length; i++) {
            editClassDialog.setInputFieldByLegend(legends[i], data[i]);
        }

        editClassDialog.clickBtnByText("Lưu");
        classDetailsPage.check_ErrorPopUp(driver);
    }

    @Test
    public void modifyGeneralInformation_AgeInvalid() {
        String className = "Sunny 66";
        String buttonFunction = "Edit";

        String[] legends = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu", "Khoá học"};
        String[] data = {"Sunny 66", "#@!$%", "21/04/2024", "Online"};

        pointToClassesPage();
        classesPage.click_BtnClassByNameByFunction(className, buttonFunction);

        EditClassDialog editClassDialog = new EditClassDialog(driver);
        for (int i = 0; i < legends.length; i++) {
            editClassDialog.setInputFieldByLegend(legends[i], data[i]);
        }

        editClassDialog.clickBtnByText("Lưu");
        classDetailsPage.check_ErrorPopUp(driver);
    }

    @Test
    public void modifyGeneralInformation_DateInvalid_1() {
        String className = "Sunny 66";
        String buttonFunction = "Edit";

        String[] legends = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu", "Khoá học"};
        String[] data = {"Sunny 66", "11", "01/01/0000", "Online"};

        pointToClassesPage();
        classesPage.click_BtnClassByNameByFunction(className, buttonFunction);

        EditClassDialog editClassDialog = new EditClassDialog(driver);
        for (int i = 0; i < legends.length; i++) {
            editClassDialog.setInputFieldByLegend(legends[i], data[i]);
        }

        editClassDialog.clickBtnByText("Lưu");
        classDetailsPage.check_ErrorPopUp(driver);
    }

    @Test
    public void modifyGeneralInformation_DateInvalid_2() {
        String className = "Sunny 66";
        String buttonFunction = "Edit";

        String[] legends = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu", "Khoá học"};
        String[] data = {"Sunny 66", "11", "01/01/0001", "Online"};

        pointToClassesPage();
        classesPage.click_BtnClassByNameByFunction(className, buttonFunction);

        EditClassDialog editClassDialog = new EditClassDialog(driver);
        for (int i = 0; i < legends.length; i++) {
            editClassDialog.setInputFieldByLegend(legends[i], data[i]);
        }

        editClassDialog.clickBtnByText("Lưu");
        classDetailsPage.check_ErrorPopUp(driver);
    }

    @Test
    public void modifyGeneralInformation_DateInvalid_3() {
        String className = "Sunny 66";
        String buttonFunction = "Edit";

        String[] legends = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu", "Khoá học"};
        String[] data = {"Sunny 66", "11", "", "Online"};

        pointToClassesPage();
        classesPage.click_BtnClassByNameByFunction(className, buttonFunction);

        EditClassDialog editClassDialog = new EditClassDialog(driver);
        for (int i = 0; i < legends.length; i++) {
            editClassDialog.setInputFieldByLegend(legends[i], data[i]);
        }

        editClassDialog.clickBtnByText("Lưu");
        classDetailsPage.check_ErrorPopUp(driver);
    }
}
