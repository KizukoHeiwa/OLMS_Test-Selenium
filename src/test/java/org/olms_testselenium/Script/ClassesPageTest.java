package org.olms_testselenium.Script;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.olms_testselenium.Listener.SimpleListener;
import org.olms_testselenium.POM.ClassDetailsPage;
import org.olms_testselenium.POM.ClassesPage;
import org.olms_testselenium.POM.EditClassDialog;
import org.olms_testselenium.POM.LoginPage;
import org.olms_testselenium.Utils.ExcelHelper;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Listeners(SimpleListener.class)
public class ClassesPageTest extends BaseTest {
    static Logger logger = LogManager.getLogger("LoginPageTest");

    ClassesPage classesPage;
    ClassDetailsPage classDetailsPage;

    void pointToClassesPage() {
        classesPage.click_AsideLabel("Đào tạo");
        classesPage.click_Education("classes");
    }

    void pointToClassByName(String className) {
        pointToClassesPage();

        classesPage.search_ClassByName(className);
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

        classesPage = new ClassesPage(driver);
        classDetailsPage = new ClassDetailsPage(driver);
    }

    @DataProvider(name = "classData")
    public Object[][] readUser() {
        return ExcelHelper.getExcelData("src/main/resources/OLMS_TestData.xlsx", "classData");
    }

    @Test
    public void verifyData() {
        String className = "ID2";
        String[] fieldsData = {"ID2", "14/04/2025", "Đang học", "0", "1"};

        pointToClassByName(className);

        List<String> generalInformation = classDetailsPage.get_GeneralInformationByIndex();

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
        classesPage.search_ClassByName(className);

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
        classesPage.search_ClassByName(className);

        classesPage.click_BtnClassByNameByFunction(className, buttonFunction);

        EditClassDialog editClassDialog = new EditClassDialog(driver);
        for (int i = 0; i < legends.length; i++) {
            editClassDialog.setInputFieldByLegend(legends[i], data[i]);
        }

        editClassDialog.clickBtnByText("Lưu");
        classDetailsPage.check_ErrorPopUp();
    }

    @Test
    public void modifyGeneralInformation_ClassNameInvalid_2() {
        String className = "Sunny 66";
        String buttonFunction = "Edit";

        String[] legends = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu", "Khoá học"};
        String[] data = {"!@#*@", "11", "21/04/2024", "Online"};

        pointToClassesPage();
        classesPage.search_ClassByName(className);

        classesPage.click_BtnClassByNameByFunction(className, buttonFunction);

        EditClassDialog editClassDialog = new EditClassDialog(driver);
        for (int i = 0; i < legends.length; i++) {
            editClassDialog.setInputFieldByLegend(legends[i], data[i]);
        }

        editClassDialog.clickBtnByText("Lưu");
        classDetailsPage.check_ErrorPopUp();
    }

    @Test
    public void modifyGeneralInformation_AgeInvalid() {
        String className = "Sunny 66";
        String buttonFunction = "Edit";

        String[] legends = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu", "Khoá học"};
        String[] data = {"Sunny 66", "#@!$%", "21/04/2024", "Online"};

        pointToClassesPage();
        classesPage.search_ClassByName(className);

        classesPage.click_BtnClassByNameByFunction(className, buttonFunction);

        EditClassDialog editClassDialog = new EditClassDialog(driver);
        for (int i = 0; i < legends.length; i++) {
            editClassDialog.setInputFieldByLegend(legends[i], data[i]);
        }

        editClassDialog.clickBtnByText("Lưu");
        classDetailsPage.check_ErrorPopUp();
    }

    @Test
    public void modifyGeneralInformation_DateInvalid_1() {
        String className = "Sunny 66";
        String buttonFunction = "Edit";

        String[] legends = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu", "Khoá học"};
        String[] data = {"Sunny 66", "11", "01/01/0000", "Online"};

        pointToClassesPage();
        classesPage.search_ClassByName(className);

        classesPage.click_BtnClassByNameByFunction(className, buttonFunction);

        EditClassDialog editClassDialog = new EditClassDialog(driver);
        for (int i = 0; i < legends.length; i++) {
            editClassDialog.setInputFieldByLegend(legends[i], data[i]);
        }

        editClassDialog.clickBtnByText("Lưu");
        classDetailsPage.check_ErrorPopUp();
    }

    @Test
    public void modifyGeneralInformation_DateInvalid_2() {
        String className = "Sunny 66";
        String buttonFunction = "Edit";

        String[] legends = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu", "Khoá học"};
        String[] data = {"Sunny 66", "11", "01/01/0001", "Online"};

        pointToClassesPage();
        classesPage.search_ClassByName(className);

        classesPage.click_BtnClassByNameByFunction(className, buttonFunction);

        EditClassDialog editClassDialog = new EditClassDialog(driver);
        for (int i = 0; i < legends.length; i++) {
            editClassDialog.setInputFieldByLegend(legends[i], data[i]);
        }

        editClassDialog.clickBtnByText("Lưu");
        classDetailsPage.check_ErrorPopUp();
    }

    @Test
    public void modifyGeneralInformation_DateInvalid_3() {
        String className = "Sunny 66";
        String buttonFunction = "Edit";

        String[] legends = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu", "Khoá học"};
        String[] data = {"Sunny 66", "11", "", "Online"};

        pointToClassesPage();
        classesPage.search_ClassByName(className);

        classesPage.click_BtnClassByNameByFunction(className, buttonFunction);

        EditClassDialog editClassDialog = new EditClassDialog(driver);
        for (int i = 0; i < legends.length; i++) {
            editClassDialog.setInputFieldByLegend(legends[i], data[i]);
        }

        editClassDialog.clickBtnByText("Lưu");
        classDetailsPage.check_ErrorPopUp();
    }

    @Test
    public void ghiDanhHocVienHocThu() {
        try {
            String className = "ID2";
            pointToClassByName(className);

            classDetailsPage.click_ButtonByText("Ghi danh");

            classDetailsPage.set_StudentCboBox("Hoàng Anh");
            classDetailsPage.click_StudentTrialChk();
            Assert.assertEquals(classDetailsPage.get_SoBuoiField(), "2");
            // <0 mean nothing selected; >0 mean select by index
            classDetailsPage.click_LichHoc_ByIndex(1);
            classDetailsPage.click_ButtonByText("Lưu");

            Thread.sleep(1000);

            Assert.assertEquals(classDetailsPage.get_StatusStudentByName("Hoàng Anh"), "Học thử");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void ghiDanhHocVienHocThu_Invalid() {
        try {
            String className = "ID2";
            pointToClassByName(className);

            classDetailsPage.click_ButtonByText("Ghi danh");

            classDetailsPage.set_StudentCboBox("");
            classDetailsPage.click_StudentTrialChk();
            Assert.assertEquals(classDetailsPage.get_SoBuoiField(), "2");
            // <0 mean nothing selected; >0 mean select by index
            classDetailsPage.click_LichHoc_ByIndex(1);
            classDetailsPage.click_ButtonByText("Lưu");

            Thread.sleep(1000);

            classDetailsPage.check_ErrorPopUp();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void ghiDanhHocVien() {
        try {
            String className = "ID2";
            pointToClassByName(className);

            classDetailsPage.click_ButtonByText("Ghi danh");

            classDetailsPage.set_StudentCboBox("Hoàng Anh");
            classDetailsPage.set_SoBuoiField("5");
            // <0 mean nothing selected; >0 mean select by index
            classDetailsPage.click_LichHoc_ByIndex(1);
            classDetailsPage.click_ButtonByText("Lưu");

            Thread.sleep(1000);

            Assert.assertEquals(classDetailsPage.get_StatusStudentByName("Hoàng Anh"), "Học thử");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void ghiDanhHocVien_Invalid() {
        try {
            String className = "ID2";
            pointToClassByName(className);

            classDetailsPage.click_ButtonByText("Ghi danh");

            classDetailsPage.set_StudentCboBox("Hoàng Anh");
            classDetailsPage.set_SoBuoiField("-10");
            // <0 mean nothing selected; >0 mean select by index
            classDetailsPage.click_LichHoc_ByIndex(1);
            classDetailsPage.click_ButtonByText("Lưu");

            Thread.sleep(1000);

            Assert.assertEquals(classDetailsPage.get_StatusStudentByName("Hoàng Anh"), "Nợ phí");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void chinhSuaSoBuoi() {
        try {
            String className = "ID2";
            pointToClassByName(className);

            int soBuoiGhi = Integer.parseInt(classDetailsPage.get_SoBuoiGhiByName("Hoàng Anh"));
            int soBuoiConLai = Integer.parseInt(classDetailsPage.get_SoBuoiConLaiByName("Hoàng Anh"));

            classDetailsPage.click_ActionStudentByNameByFunction("Hoàng Anh", "IsoIcon");
            classDetailsPage.set_SoBuoiField("3");

            classDetailsPage.click_ButtonByText("Lưu");

            Thread.sleep(1000);

            Assert.assertEquals(classDetailsPage.get_StatusStudentByName("Hoàng Anh"), "Đang học");
            Assert.assertEquals(classDetailsPage.get_SoBuoiGhiByName("Hoàng Anh"), String.valueOf(soBuoiGhi + 3));
            Assert.assertEquals(classDetailsPage.get_SoBuoiConLaiByName("Hoàng Anh"), String.valueOf(soBuoiConLai + 3));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void chinhSuaSoBuoi_Invalid() {
        try {
            String className = "ID2";
            pointToClassByName(className);

            int soBuoiGhi = Integer.parseInt(classDetailsPage.get_SoBuoiGhiByName("Hoàng Anh"));
            int soBuoiConLai = Integer.parseInt(classDetailsPage.get_SoBuoiConLaiByName("Hoàng Anh"));

            classDetailsPage.click_ActionStudentByNameByFunction("Hoàng Anh", "IsoIcon");
            classDetailsPage.set_SoBuoiField("3");

            classDetailsPage.click_ButtonByText("Lưu");

            Thread.sleep(1000);

            classDetailsPage.check_ErrorPopUp();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
