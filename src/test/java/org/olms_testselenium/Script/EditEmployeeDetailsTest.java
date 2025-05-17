package org.olms_testselenium.Script;

import org.olms_testselenium.POM.NavigationMenu;
import org.olms_testselenium.POM.LoginPage;
import org.olms_testselenium.POM.EmployeeMgmtPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.olms_testselenium.Listener.ExtentReportListener;

import java.time.Duration;


@Listeners(ExtentReportListener.class)
public class EditEmployeeDetailsTest extends BaseTest {
    static Logger logger = LogManager.getLogger("EditEmployeeDetailsTest");
    NavigationMenu homePage;
    EmployeeMgmtPage employeeMgmtPage;

    void goToConfigurationPage(){
        homePage.clickMenu("Cấu hình");
        homePage.clickMenuCon("Quản lý nhân viên");
    }

//    @DataProvider(name = "users")
//    public Object[][] readUser() {
//        return ExcelHelper.getExcelData("C:/Users/haitn/Documents/TestdataOLMS.xlsx", "Users");
//    }
    private void turnOffImplicitWaits() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    }

    private void turnOnImplicitWaits() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    }
    @BeforeMethod
    void setup(ITestContext context){
        super.setup(context);
        LoginPage login = new LoginPage(driver);
        logger.warn("Sign in with accout admin...");
        login.login("testadmin","test1234");

        homePage = new NavigationMenu(driver);
        employeeMgmtPage = new EmployeeMgmtPage(driver);
    }

    @Test
    void invalidBirthday() {
        logger.info("Test: invalidBirthday - Nhập ngày sinh không hợp lệ");
        String username = "Khanh Test";
        String invalidBirthday = "13052030";
        String label="Ngày sinh";
        goToConfigurationPage();

        employeeMgmtPage.clickNV(username);
        employeeMgmtPage.setInputDayByLabel(label, invalidBirthday);
        logger.info("Kiểm tra hiển thị lỗi nhập liệu");
        employeeMgmtPage.testAriaInvalidAppearsWithInvalidInput(label);
        logger.info("Test: invalidBirthday - Kết thúc");
    }
    @Test
    void unableEditUsername1(){
        logger.info("Test: unableEditUsername1 - Nhập tên đăng nhập chứa ký tự đặc biệt");
        String username= "Khanh Test";
        String name = "khanh123";
        String invalidUsername ="123@#!";
        String label ="Tên đăng nhập / Tài khoản";
        String text = "Xác nhận";

        goToConfigurationPage();

        employeeMgmtPage.clickNV(username);
        employeeMgmtPage.setInputFieldByValue(name,invalidUsername);
        employeeMgmtPage.clickBtnByText(text);
        logger.info("Kiểm tra hiển thị lỗi nhập liệu");
        employeeMgmtPage.testAriaInvalidAppearsWithInvalidInput(label);
        logger.info("Test: unableEditUsername1 - Kết thúc");
    }
    @Test
    void leavingUsernameEmpty(){
        logger.info("Test: leavingUsernameEmpty - Bỏ trống tên đăng nhập");
        String username= "Khanh Test";
        String name = "khanh123";
        String invalidUsername ="";
        String label ="Tên đăng nhập / Tài khoản";
        String text = "Xác nhận";

        goToConfigurationPage();

        employeeMgmtPage.clickNV(username);
        employeeMgmtPage.setInputFieldByValue(name,invalidUsername);
        employeeMgmtPage.clickBtnByText(text);
        logger.info("Kiểm tra hiển thị lỗi nhập liệu");
        employeeMgmtPage.testAriaInvalidAppearsWithInvalidInput(label);
        logger.info("Test: leavingUsernameEmpty - Kết thúc");
    }

    @Test
    void leavingPhoneNumberEmpty(){
        logger.info("Test: leavingPhoneNumberEmpty - Bỏ trống số điện thoại");
        String username= "Khanh Test";
        String phoneNumber = "022334455";
        String invalidPhoneNumber ="";
        String label ="SĐT";
        String text = "Xác nhận";

        goToConfigurationPage();

        employeeMgmtPage.clickNV(username);
        employeeMgmtPage.setInputFieldByValue(phoneNumber,invalidPhoneNumber);
        employeeMgmtPage.clickBtnByText(text);
        logger.info("Kiểm tra hiển thị lỗi nhập liệu");
        employeeMgmtPage.testAriaInvalidAppearsWithInvalidInput(label);
        logger.info("Test: leavingPhoneNumberEmpty - Kết thúc");
    }
    @Test
    void testPasswordEqualUsernameError(){
        logger.info("Test: testPasswordEqualUsernameError - Mật khẩu trùng tên đăng nhập");
        String username= "Khanh Test";
        String password = "asdEDZ12#";
        String invalidPsw ="khanh123";
        String label ="Mật khẩu";
        String text = "Xác nhận";

        goToConfigurationPage();

        employeeMgmtPage.clickNV(username);
        employeeMgmtPage.setInputFieldByValue(password,invalidPsw);
        employeeMgmtPage.clickBtnByText(text);
        logger.info("Kiểm tra hiển thị lỗi nhập liệu");
        employeeMgmtPage.testAriaInvalidAppearsWithInvalidInput(label);
        logger.info("Test: testPasswordEqualUsernameError - Kết thúc");
    }
    @Test
    void testPasswordEqualFullnameError() {
        logger.info("Test: testPasswordEqualFullnameError - Mật khẩu trùng họ tên");
        String username = "Khanh Test";
        String password = "asdEDZ12#";
        String invalidPsw = "Khanh Test";
        String label = "Mật khẩu";
        String text = "Xác nhận";

        goToConfigurationPage();

        employeeMgmtPage.clickNV(username);
        employeeMgmtPage.setInputFieldByValue(password, invalidPsw);
        employeeMgmtPage.clickBtnByText(text);
        logger.info("Kiểm tra hiển thị lỗi nhập liệu");
        employeeMgmtPage.testAriaInvalidAppearsWithInvalidInput(label);
        logger.info("Test: testPasswordEqualFullnameError - Kết thúc");
    }
    @Test
    void testPasswordEqualPhoneNumber() {
        logger.info("Test: testPasswordEqualPhoneNumber - Mật khẩu trùng số điện thoại");
        String username = "Khanh Test";
        String password = "asdEDZ12#";
        String invalidPsw = "022334455";
        String label = "Mật khẩu";
        String text = "Xác nhận";

        goToConfigurationPage();

        employeeMgmtPage.clickNV(username);
        employeeMgmtPage.setInputFieldByValue(password, invalidPsw);
        employeeMgmtPage.clickBtnByText(text);
        logger.info("Kiểm tra hiển thị lỗi nhập liệu");
        employeeMgmtPage.testAriaInvalidAppearsWithInvalidInput(label);
        logger.info("Test: testPasswordEqualPhoneNumber - Kết thúc");
    }
    @Test
    void UsingInvalidPhoneNumber1(){
        logger.info("Test: UsingInvalidPhoneNumber1 - Nhập số điện thoại quá ngắn");
        String username= "Khanh Test";
        String phoneNumber = "022334455";
        String invalidPhoneNumber ="12";
        String label ="SĐT";
        String text = "Xác nhận";

        goToConfigurationPage();

        employeeMgmtPage.clickNV(username);
        employeeMgmtPage.setInputFieldByValue(phoneNumber,invalidPhoneNumber);
        employeeMgmtPage.clickBtnByText(text);
        logger.info("Kiểm tra hiển thị lỗi nhập liệu");
        employeeMgmtPage.testAriaInvalidAppearsWithInvalidInput(label);
        logger.info("Test: UsingInvalidPhoneNumber1 - Kết thúc");
    }
    @Test
    void UsingInvalidPhoneNumber2(){
        logger.info("Test: UsingInvalidPhoneNumber2 - Nhập số điện thoại là chữ");
        String username= "Khanh Test";
        String phoneNumber = "022334455";
        String invalidPhoneNumber ="ascbd";
        String label ="SĐT";
        String text = "Xác nhận";

        goToConfigurationPage();

        employeeMgmtPage.clickNV(username);
        employeeMgmtPage.setInputFieldByValue(phoneNumber,invalidPhoneNumber);
        employeeMgmtPage.clickBtnByText(text);
        logger.info("Kiểm tra hiển thị lỗi nhập liệu");
        employeeMgmtPage.testAriaInvalidAppearsWithInvalidInput(label);
        logger.info("Test: UsingInvalidPhoneNumber2 - Kết thúc");
    }
    @Test
    void TestCancelBtn(){
        logger.info("Test: TestCancelBtn - Nhấn nút hủy bỏ, dữ liệu không bị thay đổi");
        String username= "Khanh Test";
        String phoneNumber = "022334455";
        String invalidPhoneNumber ="12";
        String label ="SĐT";
        String text = "Huỷ bỏ";

        goToConfigurationPage();

        employeeMgmtPage.clickNV(username);
        employeeMgmtPage.setInputFieldByValue(phoneNumber,invalidPhoneNumber);
        employeeMgmtPage.clickBtnByText(text);
        logger.info("Quay lại kiểm tra dữ liệu gốc");
        employeeMgmtPage.clickNV(username);
        employeeMgmtPage.checkData(phoneNumber);
        logger.info("Test: TestCancelBtn - Kết thúc");
    }
    @Test
    void invalidNameUpdate1(){
        logger.info("Test: invalidNameUpdate1 - Nhập tên có ký tự đặc biệt");
        String username= "Khanh Test";
        String surname = "Khanh";
        String invalidSurname ="Khanh@#*123";
        String label ="Họ và tên đệm";
        String text = "Xác nhận";

        goToConfigurationPage();
        logger.info("Chọn nhân viên: " + username);
        employeeMgmtPage.clickNV(username);
        logger.info("Thay đổi '" + surname + "' thành '" + invalidSurname + "'");
        employeeMgmtPage.setInputFieldByValue(surname,invalidSurname);
        employeeMgmtPage.clickBtnByText(text);
        logger.info("Kiểm tra hiển thị lỗi nhập liệu");
        employeeMgmtPage.testAriaInvalidAppearsWithInvalidInput(label);
        logger.info("Test: invalidNameUpdate1 - Kết thúc");
    }

    @Test
    void invalidNameUpdate2(){
        logger.info("Test: invalidNameUpdate2 - Bỏ trống họ và tên đệm");
        String username= "Khanh Test";
        String surname = "Khanh";
        String invalidSurname ="";
        String label ="Họ và tên đệm";
        String text = "Xác nhận";

        goToConfigurationPage();

        employeeMgmtPage.clickNV(username);
        employeeMgmtPage.setInputFieldByValue(surname,invalidSurname);
        employeeMgmtPage.clickBtnByText(text);
        logger.info("Kiểm tra hiển thị lỗi nhập liệu");
        employeeMgmtPage.testAriaInvalidAppearsWithInvalidInput(label);
        logger.info("Test: invalidNameUpdate2 - Kết thúc");
    }

    @Test
    void invalidNameUpdate3(){
        logger.info("Test: invalidNameUpdate3 - Nhập tên chứa ký tự không hợp lệ");
        String username= "Khanh Test";
        String name = "Test";
        String invalidSurname ="123@#!";
        String label ="Tên";
        String text = "Xác nhận";

        goToConfigurationPage();

        employeeMgmtPage.clickNV(username);
        employeeMgmtPage.setInputFieldByValue(name,invalidSurname);
        employeeMgmtPage.clickBtnByText(text);
        logger.info("Kiểm tra hiển thị lỗi nhập liệu");
        employeeMgmtPage.testAriaInvalidAppearsWithInvalidInput(label);
        logger.info("Test: invalidNameUpdate3 - Kết thúc");
    }
    @Test
    void invalidNameUpdate4(){
        logger.info("Test: invalidNameUpdate4 - Bỏ trống trường Tên");
        String username= "Khanh Test";
        String name = "Test";
        String invalidSurname ="";
        String label ="Tên";
        String text = "Xác nhận";

        goToConfigurationPage();

        employeeMgmtPage.clickNV(username);
        employeeMgmtPage.setInputFieldByValue(name,invalidSurname);
        employeeMgmtPage.clickBtnByText(text);
        logger.info("Kiểm tra hiển thị lỗi nhập liệu");
        employeeMgmtPage.testAriaInvalidAppearsWithInvalidInput(label);
        logger.info("Test: invalidNameUpdate4 - Kết thúc");
    }

}
