package org.olms_testselenium.Script;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.olms_testselenium.Listener.SimpleListener;
import org.olms_testselenium.POM.LoginPage;
import org.olms_testselenium.POM.StaffPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Test class for Staff Management functionality
 *
 * @author Hoang Ha
 */
@Listeners(SimpleListener.class)
public class StaffPageTest extends BaseTest {
    static Logger logger = LogManager.getLogger("StaffPageTest");
    private LoginPage loginPage;
    private StaffPage staffPage;

    /**
     * Setup trước mỗi test case
     * - Đăng nhập với tài khoản admin
     * - Khởi tạo các page objects
     */
    @BeforeMethod
    void setupTest() {
        // Đăng nhập trước khi thực hiện các test cases
        loginPage = new LoginPage(driver);
        loginPage.login("testadmin", "test1234"); // Sử dụng tài khoản admin mặc định

        // Khởi tạo Staff Page
        staffPage = new StaffPage(driver);
    }

    /**
     * TC-SCRUM2-001: Kiểm tra điều kiện tiên quyết
     * Feature: Quản lý nhân viên
     * Sub-feature: Thêm nhân viên vào hệ thống
     * Linked Story: https://hthuy7605-studentmanagement.atlassian.net/browse/SCRUM-2
     * <p>
     * Pre-conditions:
     * - Đã có tài khoản "Admin" trên hệ thống https://olms.codedao.io.vn/
     * - Chưa đăng nhập.
     * <p>
     * Steps:
     * 1. Truy cập: https://olms.codedao.io.vn/
     * 2. Đăng nhập với quyền Admin và tìm mục Cấu hình và điều hướng đến trang Quản lý nhân viên
     * <p>
     * Expected Results:
     * - User đăng nhập thành công và mở được trang Quản lý nhân viên.
     * - Màn hình có nút "thêm nhân viên"
     * <p>
     */
    @Test
    void TC_SCRUM2_001_VerifyPrerequisitesForAddingStaff() {
        logger.info("Bắt đầu test case TC001: Kiểm tra điều kiện tiên quyết");

        // Bước 1: Truy cập: https://olms.codedao.io.vn/
        // Đã được thực hiện trong @BeforeMethod

        // Bước 2: Đăng nhập với quyền Admin và điều hướng đến trang Quản lý nhân viên
        // Đăng nhập đã được thực hiện trong @BeforeMethod
        logger.info("Điều hướng đến trang Quản lý nhân viên");
        staffPage.navigateToStaffPage();

        // Kiểm tra xem đã truy cập thành công vào trang Quản lý nhân viên chưa
        boolean isTitleDisplayed = staffPage.isPageTitleDisplayed();
        Assert.assertTrue(isTitleDisplayed, "Tiêu đề trang quản lý nhân viên không hiển thị đúng");
        logger.info("Đã truy cập thành công vào trang Quản lý nhân viên");

        // Kiểm tra có nút "Thêm nhân viên" hay không
        try {
            WebElement addStaffButton = driver.findElement(By.xpath("//button[contains(.,'Thêm nhân viên')]"));
            Assert.assertTrue(addStaffButton.isDisplayed(), "Nút 'Thêm nhân viên' không hiển thị");
            logger.info("Nút 'Thêm nhân viên' hiển thị trên trang");
        } catch (Exception e) {
            Assert.fail("Không tìm thấy nút 'Thêm nhân viên': " + e.getMessage());
        }

        logger.info("Kết thúc test case TC001: Kiểm tra điều kiện tiên quyết thành công");

        // Chờ 5 giây trước khi kết thúc test case để có thể quan sát kết quả
        try {
            logger.info("Chờ 5 giây trước khi đóng trình duyệt...");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            logger.error("Lỗi chờ đợi: " + e.getMessage());
        }
    }

    /**
     * TC-SCRUM2-002: Kiểm tra button "Thêm nhân viên" bấm được và chuyển hướng vào trang thêm nhân viên
     * Feature: Quản lý nhân viên
     * Sub-feature: Thêm nhân viên vào hệ thống
     * Linked Story: https://hthuy7605-studentmanagement.atlassian.net/browse/SCRUM-2
     * <p>
     * Pre-conditions:
     * - Admin đã đăng nhập và đang ở trang Quản lý nhân viên
     * <p>
     * Steps:
     * 1. Bấm nút "thêm nhân viên"
     * <p>
     * Expected Results:
     * - Bấm được button và pop up khung nhập thông tin tạo user
     * - Form hiển thị đầy đủ các trường: Họ và tên đệm, Tên, Ngày sinh, Phòng ban,
     * Ngày bắt đầu làm việc, Link Hợp đồng, Tên đăng nhập / Tài khoản, Mật khẩu, SĐT
     * - Form có các nút: Xác nhận và Huỷ bỏ
     * <p>
     */
    @Test
    void TC_SCRUM2_002_VerifyAddStaffButtonOpensForm() {
        logger.info("Bắt đầu test case TC002: Kiểm tra button Thêm nhân viên");

        // Điều kiện tiên quyết: Admin đã đăng nhập và đang ở trang Quản lý nhân viên
        // Đăng nhập đã được thực hiện trong @BeforeMethod
        logger.info("Điều hướng đến trang Quản lý nhân viên");
        staffPage.navigateToStaffPage();

        // Bước 1: Bấm nút "thêm nhân viên"
        logger.info("Bấm nút thêm nhân viên");
        staffPage.waitForBackdropToDisappear();
        staffPage.clickAddStaffButton();

        // Kiểm tra kết quả: Kiểm tra pop-up form thêm nhân viên hiển thị
        try {
            WebElement addStaffForm = driver.findElement(By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]"));
            Assert.assertTrue(addStaffForm.isDisplayed(), "Form thêm nhân viên không hiển thị");
            logger.info("Form thêm nhân viên hiển thị thành công");

            // Kiểm tra các trường nhập liệu trong form
            WebElement firstNameField = driver.findElement(By.xpath("//label[contains(text(),'Họ và tên đệm')]/following-sibling::div//input"));
            WebElement lastNameField = driver.findElement(By.xpath("//label[contains(text(),'Tên')]/following-sibling::div//input"));
            WebElement birthDateField = driver.findElement(By.xpath("//label[contains(text(),'Ngày sinh')]/following-sibling::div//input"));
            WebElement departmentField = driver.findElement(By.xpath("//label[contains(text(),'Phòng ban')]/following-sibling::div//label//span"));
            WebElement startDateField = driver.findElement(By.xpath("//label[contains(text(),'Ngày bắt đầu làm việc')]/following-sibling::div//input"));
            WebElement contractLinkField = driver.findElement(By.xpath("//label[contains(text(),'Link Hợp đồng')]/following-sibling::div//input"));
            WebElement usernameField = driver.findElement(By.xpath("//label[contains(text(),'Tên đăng nhập / Tài khoản')]/following-sibling::div//input"));
            WebElement passwordField = driver.findElement(By.xpath("//label[contains(text(),'Mật khẩu')]/following-sibling::div//input"));
            WebElement phoneField = driver.findElement(By.xpath("//label[contains(text(),'SĐT')]/following-sibling::div//input"));

            // Kiểm tra các buttons
            WebElement confirmButton = driver.findElement(By.xpath("//button[contains(text(),'Xác nhận')]"));
            WebElement cancelButton = driver.findElement(By.xpath("//button[contains(text(),'Huỷ bỏ')]"));

            // Kiểm tra các trường hiển thị đúng
            Assert.assertTrue(firstNameField.isDisplayed(), "Trường nhập Họ và tên đệm không hiển thị");
            Assert.assertTrue(lastNameField.isDisplayed(), "Trường nhập Tên không hiển thị");
            Assert.assertTrue(birthDateField.isDisplayed(), "Trường nhập Ngày sinh không hiển thị");
            Assert.assertTrue(departmentField.isDisplayed(), "Trường chọn Phòng ban không hiển thị");
            Assert.assertTrue(startDateField.isDisplayed(), "Trường nhập Ngày bắt đầu làm việc không hiển thị");
            Assert.assertTrue(contractLinkField.isDisplayed(), "Trường nhập Link Hợp đồng không hiển thị");
            Assert.assertTrue(usernameField.isDisplayed(), "Trường nhập Tên đăng nhập / Tài khoản không hiển thị");
            Assert.assertTrue(passwordField.isDisplayed(), "Trường nhập Mật khẩu không hiển thị");
            Assert.assertTrue(phoneField.isDisplayed(), "Trường nhập SĐT không hiển thị");
            Assert.assertTrue(confirmButton.isDisplayed(), "Nút Xác nhận không hiển thị");
            Assert.assertTrue(cancelButton.isDisplayed(), "Nút Huỷ bỏ không hiển thị");

            logger.info("Tất cả các trường nhập liệu và nút trong form hiển thị đầy đủ");
        } catch (Exception e) {
            Assert.fail("Form thêm nhân viên không hiển thị đúng: " + e.getMessage());
        }

        logger.info("Kết thúc test case TC002: Kiểm tra button Thêm nhân viên thành công");

        // Chờ 5 giây trước khi kết thúc test case để có thể quan sát kết quả
        try {
            logger.info("Chờ 5 giây trước khi đóng trình duyệt...");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            logger.error("Lỗi chờ đợi: " + e.getMessage());
        }
    }

    /**
     * TC-SCRUM2-004: Kiểm tra thêm nhân viên thành công khi nhập đầy đủ hợp lệ các trường bắt buộc và bỏ trống các ô tùy chọn
     * Feature: Quản lý nhân viên
     * Sub-feature: Thêm nhân viên vào hệ thống
     * Linked Story: https://hthuy7605-studentmanagement.atlassian.net/browse/SCRUM-2
     * <p>
     * Pre-conditions:
     * - Admin đã đăng nhập và đang ở màn hình "Thêm nhân viên"
     * <p>
     * Steps:
     * 1. Chỉ nhập đầy đủ các trường bắt buộc sau: Họ và tên đệm, Tên, Tên đăng nhập, Mật khẩu, SĐT và bỏ trống các trường còn lại
     * 2. Bấm "XÁC NHẬN"
     * <p>
     * Expected Results:
     * - Cho phép tạo nhân viên thành công
     * <p>
     */
    @Test
    void TC_SCRUM2_004_VerifyRequiredFieldsForAddingStaff() {
        logger.info("Bắt đầu test case TC004: Kiểm tra thêm nhân viên khi nhập đầy đủ hợp lệ các trường bắt buộc và bỏ trống các ô tùy chọn");

        // Điều kiện tiên quyết: Admin đã đăng nhập và đang ở trang Quản lý nhân viên
        // Đăng nhập đã được thực hiện trong @BeforeMethod
        logger.info("Điều hướng đến trang Quản lý nhân viên");
        staffPage.navigateToStaffPage();

        // Admin đã bấm nút "Thêm nhân viên"
        logger.info("Bấm nút thêm nhân viên");
        staffPage.waitForBackdropToDisappear();
        staffPage.clickAddStaffButton();

        try {
            // Verify form is displayed
            WebElement addStaffForm = driver.findElement(By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]"));
            Assert.assertTrue(addStaffForm.isDisplayed(), "Form thêm nhân viên không hiển thị");
            logger.info("Form thêm nhân viên hiển thị thành công");

            // Bước 1: Nhập đầy đủ các trường bắt buộc Họ, Tên, Tên đăng nhập, Mật khẩu, Số điện thoại
            // Nhập dữ liệu test theo yêu cầu
            WebElement firstNameField = driver.findElement(By.xpath("//label[contains(text(),'Họ và tên đệm')]/following-sibling::div//input"));
            WebElement lastNameField = driver.findElement(By.xpath("//label[contains(text(),'Tên')]/following-sibling::div//input"));
            WebElement usernameField = driver.findElement(By.xpath("//label[contains(text(),'Tên đăng nhập / Tài khoản')]/following-sibling::div//input"));
            WebElement passwordField = driver.findElement(By.xpath("//label[contains(text(),'Mật khẩu')]/following-sibling::div//input"));
            WebElement phoneField = driver.findElement(By.xpath("//label[contains(text(),'SĐT')]/following-sibling::div//input"));

            // Nhập dữ liệu test vào các trường
            firstNameField.sendKeys("Hoàng");
            lastNameField.sendKeys("Hà");
            // Tạo timestamp để tên đăng nhập không bị trùng
            String timestamp = String.valueOf(System.currentTimeMillis());
            usernameField.sendKeys("haht" + timestamp); // Thêm timestamp vào tên đăng nhập

            passwordField.sendKeys("12432uawqwq");
            phoneField.sendKeys("34873224");

            logger.info("Đã nhập dữ liệu vào các trường bắt buộc theo dữ liệu test");

            // Bỏ trống các ô tùy chọn "Ngày sinh", "Ngày bắt đầu làm việc", "Màu hiển thị"
            // Các ô này được để trống mặc định, không cần thực hiện thao tác

            // Bước 2: Bấm "XÁC NHẬN"
            WebElement confirmButton = driver.findElement(By.xpath("//button[contains(text(),'Xác nhận')]"));
            logger.info("Bấm nút Xác nhận");
            confirmButton.click();

            // Kiểm tra kết quả
            // Kỳ vọng: Cho phép tạo nhân viên thành công
            // Thực tế: Ô tùy chọn Phòng ban bị hiện cảnh báo là ô bắt buộc nhập. Không tạo được nhân viên vào hệ thống

            // 1. Đợi hiển thị thông báo lỗi
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//p[contains(@class, 'Mui-error')] | //label[contains(@class, 'Mui-error')]")));

            // 2. Kiểm tra nội dung thông báo lỗi có liên quan đến Phòng ban
            WebElement departmentErrorLabel = driver.findElement(By.xpath("//label[contains(text(),'Phòng ban')][contains(@class, 'Mui-error')]"));
            Assert.assertTrue(departmentErrorLabel.isDisplayed(), "Không hiển thị thông báo lỗi trường Phòng ban");
            logger.info("Trường Phòng ban hiển thị lỗi như mong đợi");

            // 3. Kiểm tra xem form thêm nhân viên đã tắt tức là thêm thành công
            Assert.assertTrue(addStaffForm.isDisplayed() == false, "Form thêm nhân viên vẫn hiển thị (không thêm thành công do thiếu trường bắt buộc)");
            logger.info("Form thêm nhân viên không còn hiển thị sau khi bấm Xác nhận");

            // Chờ 5 giây trước khi kết thúc test case để có thể quan sát kết quả
            try {
                logger.info("Chờ 5 giây trước khi đóng trình duyệt...");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                logger.error("Lỗi chờ đợi: " + e.getMessage());
            }

        } catch (Exception e) {
            Assert.fail("Kiểm tra trường bắt buộc Phòng ban thất bại: " + e.getMessage());
        }

        logger.info("Kết thúc test case TC004: Kiểm tra thêm nhân viên khi thiếu trường bắt buộc Phòng ban thành công");
    }

    /**
     * TC-SCRUM2-005: Nhập đầy đủ thông tin hợp lệ cho tất cả các trường, bao gồm cả các trường tùy chọn
     * Feature: Quản lý nhân viên
     * Sub-feature: Thêm nhân viên vào hệ thống
     * Linked Story: https://hthuy7605-studentmanagement.atlassian.net/browse/SCRUM-2
     * <p>
     * Pre-conditions:
     * - Admin đã đăng nhập và đang ở màn hình "Thêm nhân viên"
     * <p>
     * Steps:
     * 1. Nhập hết các trường:
     * Họ, Tên, Tên đăng nhập, Mật khẩu, Số điện thoại và các trường tùy chọn còn lại
     * 2. Bấm "XÁC NHẬN"
     * <p>
     * Expected Results:
     * - Cho phép tạo nhân viên thành công
     * <p>
     * Test data:
     * Họ: Hoàng
     * Tên: Hà
     * Tên đăng nhập: haht+timestamp
     * Mật khẩu: 12432uawqwq
     * Số điện thoại: 34873224
     * Ngày sinh: 01/01/2000
     * Phòng ban: Đào tạo/ GV Việt Nam
     * Màu hiển thị: #d0021b
     * Ngày bắt đầu làm việc: 01/05/2017
     * Link HĐ: https://prnt.sc/pPVSMWBFD91P
     */
    @Test
    void TC_SCRUM2_005_AddStaffWithAllValidFields() {
        logger.info("Bắt đầu test case TC005: Kiểm tra thêm nhân viên khi nhập đầy đủ thông tin hợp lệ cho tất cả các trường");

        // Điều kiện tiên quyết: Admin đã đăng nhập và đang ở trang Quản lý nhân viên
        // Đăng nhập đã được thực hiện trong @BeforeMethod
        logger.info("Điều hướng đến trang Quản lý nhân viên");
        staffPage.navigateToStaffPage();

        // Admin đã bấm nút "Thêm nhân viên"
        logger.info("Bấm nút thêm nhân viên");
        staffPage.waitForBackdropToDisappear();
        staffPage.clickAddStaffButton();

        try {
            // Verify form is displayed
            WebElement addStaffForm = driver.findElement(By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]"));
            Assert.assertTrue(addStaffForm.isDisplayed(), "Form thêm nhân viên không hiển thị");
            logger.info("Form thêm nhân viên hiển thị thành công");

            // Bước 1: Nhập đầy đủ các trường hợp lệ
            logger.info("Nhập đầy đủ tất cả các trường theo dữ liệu test");

            // Các trường thông tin cơ bản
            WebElement firstNameField = driver.findElement(By.xpath("//label[contains(text(),'Họ và tên đệm')]/following-sibling::div//input"));
            WebElement lastNameField = driver.findElement(By.xpath("//label[contains(text(),'Tên')]/following-sibling::div//input"));
            WebElement usernameField = driver.findElement(By.xpath("//label[contains(text(),'Tên đăng nhập / Tài khoản')]/following-sibling::div//input"));
            WebElement passwordField = driver.findElement(By.xpath("//label[contains(text(),'Mật khẩu')]/following-sibling::div//input"));
            WebElement phoneField = driver.findElement(By.xpath("//label[contains(text(),'SĐT')]/following-sibling::div//input"));

            // Các trường tùy chọn
            WebElement birthDateField = driver.findElement(By.xpath("//label[contains(text(),'Ngày sinh')]/following-sibling::div//input"));
            WebElement startDateField = driver.findElement(By.xpath("//label[contains(text(),'Ngày bắt đầu làm việc')]/following-sibling::div//input"));
            WebElement contractLinkField = driver.findElement(By.xpath("//label[contains(text(),'Link Hợp đồng')]/following-sibling::div//input"));

            // Nhập dữ liệu test vào các trường
            firstNameField.sendKeys("Hoàng");
            lastNameField.sendKeys("Hà");
            String timestamp = String.valueOf(System.currentTimeMillis());
            usernameField.sendKeys("haht" + timestamp); // Thêm timestamp vào tên đăng nhập
            passwordField.sendKeys("12432uawqwq");
            phoneField.sendKeys("34873224");

            // Nhập dữ liệu cho các trường tùy chọn
            birthDateField.click();
            birthDateField.sendKeys("01/01/2000");

            startDateField.click();
            startDateField.sendKeys("01/05/2017");

            contractLinkField.sendKeys("https://prnt.sc/pPVSMWBFD91P");

            // Chọn phòng ban - Đầu tiên chọn radio button "Đào tạo"
            try {
                WebElement daoTaoRadioLabel = driver.findElement(
                        By.xpath("//label[contains(@class, 'MuiFormControlLabel-root')]/span[contains(@class, 'MuiTypography-root') and text()='Đào tạo']"));
                daoTaoRadioLabel.click();
                logger.info("Đã chọn radio button 'Đào tạo'");
            } catch (Exception e) {
                logger.error("Không thể chọn radio button 'Đào tạo': " + e.getMessage());
                Assert.fail("Không thể chọn radio button 'Đào tạo'");
            }

            // Đợi cho dropdown hiển thị và chọn "Giáo viên Việt Nam"
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

            // Đợi một chút để đảm bảo UI đã cập nhật sau khi chọn radio button
            Thread.sleep(500);

            try {
                WebElement dropdownField = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@class='MuiInputBase-root MuiOutlinedInput-root MuiInputBase-colorPrimary MuiInputBase-fullWidth MuiInputBase-sizeSmall css-1owlt1s']")));
                dropdownField.click();
                logger.info("Đã click vào dropdown để chọn vai trò");

                // Đợi cho menu dropdown xuất hiện
                Thread.sleep(500); // Đợi animation hoàn tất

                // Đợi cho các tùy chọn dropdown hiển thị
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//ul[@role='listbox']/li[contains(text(),'Giáo viên Việt Nam')]")));

                // Tìm và click vào tùy chọn Giáo viên Việt Nam
                WebElement gvVietNamOption = driver.findElement(
                        By.xpath("//ul[@role='listbox']/li[contains(text(),'Giáo viên Việt Nam')]"));
                gvVietNamOption.click();
                // Đóng dropdown bằng giả lập nhấn nút ESC
                driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
                Thread.sleep(500);
                logger.info("Đã chọn 'Giáo viên Việt Nam' từ dropdown");
            } catch (Exception e) {
                logger.error("Không thể mở dropdown: " + e.getMessage());
                Assert.fail("Không thể mở dropdown để chọn vai trò");
            }

            // Đợi cho field màu hiển thị xuất hiện và nhập màu
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(),'Màu hiển thị')]/following-sibling::div//input")));
            WebElement colorField = driver.findElement(By.xpath("//label[contains(text(),'Màu hiển thị')]/following-sibling::div//input"));
            colorField.click();
            colorField.sendKeys("#d0021b");
            colorField.click(); // Click lại lần nữa để đóng color picker
            logger.info("Đã nhập màu hiển thị");

            logger.info("Đã nhập đầy đủ dữ liệu vào tất cả các trường theo dữ liệu test");

            // Bước 2: Bấm "XÁC NHẬN"
            WebElement confirmButton = driver.findElement(By.xpath("//button[contains(text(),'Xác nhận')]"));
            logger.info("Bấm nút Xác nhận");
            confirmButton.click();

            // Kiểm tra kết quả: Cho phép tạo nhân viên thành công
            // 1. Kiểm tra xem form thêm nhân viên đã tắt tức là thêm thành công
            Assert.assertTrue(staffPage.isAddStaffFormDisplayed() == false, "Form thêm nhân viên vẫn hiển thị");
            logger.info("Form thêm nhân viên không còn hiển thị sau khi bấm Xác nhận");


            // Chờ 5 giây trước khi kết thúc test case để có thể quan sát kết quả
            try {
                logger.info("Chờ 5 giây trước khi đóng trình duyệt...");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                logger.error("Lỗi chờ đợi: " + e.getMessage());
            }

        } catch (Exception e) {
            Assert.fail("Thêm nhân viên với đầy đủ thông tin thất bại: " + e.getMessage());
        }

        logger.info("Kết thúc test case TC005: Kiểm tra thêm nhân viên với đầy đủ thông tin thành công");
    }

    /**
     * TC-SCRUM2-006: Kiểm tra không cho phép tạo nhân viên khi không nhập thông tin gì và khi bỏ trống trường bắt buộc
     * Feature: Quản lý nhân viên
     * Sub-feature: Thêm nhân viên vào hệ thống
     * Linked Story: https://hthuy7605-studentmanagement.atlassian.net/browse/SCRUM-2
     * <p>
     * Pre-conditions:
     * - Admin đã đăng nhập và đang ở màn hình "Thêm nhân viên"
     * <p>
     * Steps:
     * TH1. Để trống không nhập bất kì field nào. Bấm "XÁC NHẬN" và Kiểm tra
     * <p>
     * TH2. Nhập hết các trường bắt buộc chỉ bỏ trống lần lượt các trường bắt buộc sau:
     * 2.1 Họ
     * 2.2 Tên
     * 2.3 Tên đăng nhập
     * 2.4 Mật khẩu
     * 2.5 Số điện thoại
     * 2.6 Phòng ban (radio buttons)
     * Bấm "XÁC NHẬN" và Kiểm tra
     * <p>
     * Expected Results:
     * - Hệ thống hiển thị đỏ cảnh báo những ô bắt buộc đang trống gồm: Họ, tên, Tên đăng nhập, Mật khẩu, SĐT, Phòng ban và
     * không cho phép tạo nhân viên
     * <p>
     */
    @Test
    void TC_SCRUM2_006_ValidateRequiredFieldsWhenEmpty() {
        logger.info("Bắt đầu test case TC006: Kiểm tra không cho phép tạo nhân viên khi không nhập thông tin gì và khi bỏ trống trường bắt buộc");

        // Điều kiện tiên quyết: Admin đã đăng nhập và đang ở trang Quản lý nhân viên
        // Đăng nhập đã được thực hiện trong @BeforeMethod
        logger.info("Điều hướng đến trang Quản lý nhân viên");
        staffPage.navigateToStaffPage();

        // Trường hợp 1: Để trống không nhập bất kì field nào
        logger.info("Trường hợp 1: Để trống không nhập bất kì field nào");

        // Mở form thêm nhân viên
        staffPage.waitForBackdropToDisappear();
        staffPage.clickAddStaffButton();

        try {
            // Verify form is displayed
            WebElement addStaffForm = driver.findElement(By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]"));
            Assert.assertTrue(addStaffForm.isDisplayed(), "Form thêm nhân viên không hiển thị");
            logger.info("Form thêm nhân viên hiển thị thành công");

            // Không nhập dữ liệu vào bất kỳ trường nào

            // Bấm "XÁC NHẬN"
            WebElement confirmButton = driver.findElement(By.xpath("//button[contains(text(),'Xác nhận')]"));
            logger.info("Bấm nút Xác nhận khi chưa nhập dữ liệu");
            confirmButton.click();

            // Kiểm tra hiển thị thông báo lỗi cho các trường bắt buộc
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

            // Kiểm tra lỗi trường Họ và tên đệm
            try {
                WebElement firstNameError = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//label[contains(text(),'Họ và tên đệm')][contains(@class, 'Mui-error')]")));
                Assert.assertTrue(firstNameError.isDisplayed(), "Không hiển thị lỗi cho trường Họ và tên đệm");
                logger.info("Hiển thị đúng thông báo lỗi cho trường Họ và tên đệm");
            } catch (Exception e) {
                logger.error("Không tìm thấy thông báo lỗi cho trường Họ và tên đệm: " + e.getMessage());
                Assert.fail("Không tìm thấy thông báo lỗi cho trường Họ và tên đệm");
            }

            // Kiểm tra lỗi trường Tên
            try {
                WebElement lastNameError = driver.findElement(
                        By.xpath("//label[contains(text(),'Tên')][contains(@class, 'Mui-error')]"));
                Assert.assertTrue(lastNameError.isDisplayed(), "Không hiển thị lỗi cho trường Tên");
                logger.info("Hiển thị đúng thông báo lỗi cho trường Tên");
            } catch (Exception e) {
                logger.error("Không tìm thấy thông báo lỗi cho trường Tên: " + e.getMessage());
                Assert.fail("Không tìm thấy thông báo lỗi cho trường Tên");
            }

            // Kiểm tra lỗi trường Tên đăng nhập
            try {
                WebElement usernameError = driver.findElement(
                        By.xpath("//label[contains(text(),'Tên đăng nhập')][contains(@class, 'Mui-error')]"));
                Assert.assertTrue(usernameError.isDisplayed(), "Không hiển thị lỗi cho trường Tên đăng nhập");
                logger.info("Hiển thị đúng thông báo lỗi cho trường Tên đăng nhập");
            } catch (Exception e) {
                logger.error("Không tìm thấy thông báo lỗi cho trường Tên đăng nhập: " + e.getMessage());
                Assert.fail("Không tìm thấy thông báo lỗi cho trường Tên đăng nhập");
            }

            // Kiểm tra lỗi trường Mật khẩu
            try {
                WebElement passwordError = driver.findElement(
                        By.xpath("//label[contains(text(),'Mật khẩu')][contains(@class, 'Mui-error')]"));
                Assert.assertTrue(passwordError.isDisplayed(), "Không hiển thị lỗi cho trường Mật khẩu");
                logger.info("Hiển thị đúng thông báo lỗi cho trường Mật khẩu");
            } catch (Exception e) {
                logger.error("Không tìm thấy thông báo lỗi cho trường Mật khẩu: " + e.getMessage());
                Assert.fail("Không tìm thấy thông báo lỗi cho trường Mật khẩu");
            }

            // Kiểm tra lỗi trường SĐT
            try {
                WebElement phoneError = driver.findElement(
                        By.xpath("//label[contains(text(),'SĐT')][contains(@class, 'Mui-error')]"));
                Assert.assertTrue(phoneError.isDisplayed(), "Không hiển thị lỗi cho trường SĐT");
                logger.info("Hiển thị đúng thông báo lỗi cho trường SĐT");
            } catch (Exception e) {
                logger.error("Không tìm thấy thông báo lỗi cho trường SĐT: " + e.getMessage());
                Assert.fail("Không tìm thấy thông báo lỗi cho trường SĐT");
            }

            // Kiểm tra lỗi trường Phòng ban
            try {
                WebElement departmentError = driver.findElement(
                        By.xpath("//label[contains(text(),'Phòng ban')][contains(@class, 'Mui-error')]"));
                Assert.assertTrue(departmentError.isDisplayed(), "Không hiển thị lỗi cho trường Phòng ban");
                logger.info("Hiển thị đúng thông báo lỗi cho trường Phòng ban");
            } catch (Exception e) {
                logger.error("Không tìm thấy thông báo lỗi cho trường Phòng ban: " + e.getMessage());
                Assert.fail("Không tìm thấy thông báo lỗi cho trường Phòng ban");
            }

            // Kiểm tra form vẫn hiển thị (không được đóng)
            Assert.assertTrue(addStaffForm.isDisplayed(), "Form thêm nhân viên đã đóng mặc dù có lỗi");
            logger.info("Form thêm nhân viên vẫn hiển thị khi có lỗi (đúng như mong đợi)");

            // Đóng form để chuẩn bị cho trường hợp tiếp theo
            WebElement cancelButton = driver.findElement(By.xpath("//button[contains(text(),'Huỷ bỏ')]"));
            cancelButton.click();

            // Đợi form đóng
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]")));

            // Trường hợp 2: Kiểm tra từng trường bắt buộc
            logger.info("Trường hợp 2: Kiểm tra từng trường bắt buộc");

            // Mảng chứa tên các trường cần kiểm tra (thêm Phòng ban vào danh sách trường bắt buộc)
            String[] requiredFields = {"Họ và tên đệm", "Tên", "Tên đăng nhập / Tài khoản", "Mật khẩu", "SĐT", "Phòng ban"};

            for (String fieldName : requiredFields) {
                // Mở lại form thêm nhân viên
                staffPage.clickAddStaffButton();
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]")));

                logger.info("Kiểm tra trường bắt buộc: " + fieldName);

                // Lấy các trường nhập liệu
                WebElement firstNameField = driver.findElement(By.xpath("//label[contains(text(),'Họ và tên đệm')]/following-sibling::div//input"));
                WebElement lastNameField = driver.findElement(By.xpath("//label[contains(text(),'Tên')]/following-sibling::div//input"));
                WebElement usernameField = driver.findElement(By.xpath("//label[contains(text(),'Tên đăng nhập / Tài khoản')]/following-sibling::div//input"));
                WebElement passwordField = driver.findElement(By.xpath("//label[contains(text(),'Mật khẩu')]/following-sibling::div//input"));
                WebElement phoneField = driver.findElement(By.xpath("//label[contains(text(),'SĐT')]/following-sibling::div//input"));

                // Nhập dữ liệu vào tất cả các trường trừ trường đang kiểm tra
                if (!fieldName.equals("Họ và tên đệm")) {
                    firstNameField.sendKeys("Hoàng");
                }

                if (!fieldName.equals("Tên")) {
                    lastNameField.sendKeys("Hà");
                }

                if (!fieldName.equals("Tên đăng nhập / Tài khoản")) {
                    String timestamp = String.valueOf(System.currentTimeMillis());
                    usernameField.sendKeys("haht" + timestamp);
                }

                if (!fieldName.equals("Mật khẩu")) {
                    passwordField.sendKeys("12432uawqwq");
                }

                if (!fieldName.equals("SĐT")) {
                    phoneField.sendKeys("34873224");
                }

                // Chọn phòng ban nếu không phải trường đang kiểm tra
                if (!fieldName.equals("Phòng ban")) {
                    WebElement daoTaoRadioLabel = driver.findElement(
                            By.xpath("//label[contains(@class, 'MuiFormControlLabel-root')]/span[contains(@class, 'MuiTypography-root') and text()='Đào tạo']"));
                    daoTaoRadioLabel.click();
                }

                // Bấm "XÁC NHẬN"
                confirmButton = driver.findElement(By.xpath("//button[contains(text(),'Xác nhận')]"));
                logger.info("Bấm nút Xác nhận khi bỏ trống trường " + fieldName);
                confirmButton.click();

                // Kiểm tra hiển thị thông báo lỗi cho trường đang kiểm tra
                try {
                    String xpath = "//label[contains(text(),'" + fieldName + "')][contains(@class, 'Mui-error')]";
                    WebElement fieldError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
                    Assert.assertTrue(fieldError.isDisplayed(), "Không hiển thị lỗi cho trường " + fieldName);
                    logger.info("Hiển thị đúng thông báo lỗi cho trường " + fieldName);
                } catch (Exception e) {
                    logger.error("Không tìm thấy thông báo lỗi cho trường " + fieldName + ": " + e.getMessage());
                    Assert.fail("Không tìm thấy thông báo lỗi cho trường " + fieldName);
                }

                // Kiểm tra form vẫn hiển thị (không được đóng)
                WebElement currentForm = driver.findElement(By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]"));
                Assert.assertTrue(currentForm.isDisplayed(), "Form thêm nhân viên đã đóng mặc dù có lỗi ở trường " + fieldName);
                logger.info("Form thêm nhân viên vẫn hiển thị khi có lỗi ở trường " + fieldName + " (đúng như mong đợi)");

                // Đóng form để chuẩn bị cho lần kiểm tra tiếp theo
                cancelButton = driver.findElement(By.xpath("//button[contains(text(),'Huỷ bỏ')]"));
                cancelButton.click();

                // Đợi form đóng
                wait.until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]")));
            }

        } catch (Exception e) {
            logger.error("Lỗi trong quá trình kiểm tra: " + e.getMessage());
            Assert.fail("Lỗi trong quá trình kiểm tra: " + e.getMessage());
        }

        logger.info("Kết thúc test case TC006: Kiểm tra không cho phép tạo nhân viên khi không nhập thông tin gì và khi bỏ trống trường bắt buộc");
    }

    /**
     * TC-SCRUM2-007: Kiểm tra danh sách Chức vụ không hiện dữ liệu và không thể chọn khi chưa chọn Phòng ban
     * Feature: Quản lý nhân viên
     * Sub-feature: Thêm nhân viên vào hệ thống
     * Linked Story: https://hthuy7605-studentmanagement.atlassian.net/browse/SCRUM-2
     * <p>
     * Pre-conditions:
     * - Admin đã đăng nhập và đang ở màn hình "Thêm nhân viên"
     * <p>
     * Steps:
     * 1. Bỏ trống không chọn phòng ban.
     * 2. Bấm vào ô danh sách chức vụ
     * <p>
     * Expected Results:
     * - Danh sách Chức vụ không hiện dữ liệu và không thể chọn được
     * <p>
     */
    @Test
    void TC_SCRUM2_007_VerifyRoleListDisabledWithoutDepartment() {
        logger.info("Bắt đầu test case TC007: Kiểm tra danh sách Chức vụ không hiện dữ liệu và không thể chọn khi chưa chọn Phòng ban");

        // Điều kiện tiên quyết: Admin đã đăng nhập và đang ở trang Quản lý nhân viên
        // Đăng nhập đã được thực hiện trong @BeforeMethod
        logger.info("Điều hướng đến trang Quản lý nhân viên");
        staffPage.navigateToStaffPage();

        // Mở form thêm nhân viên
        logger.info("Bấm nút thêm nhân viên");
        staffPage.waitForBackdropToDisappear();
        staffPage.clickAddStaffButton();

        try {
            // Verify form is displayed
            WebElement addStaffForm = driver.findElement(By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]"));
            Assert.assertTrue(addStaffForm.isDisplayed(), "Form thêm nhân viên không hiển thị");
            logger.info("Form thêm nhân viên hiển thị thành công");

            // Bước 1: Bỏ trống không chọn phòng ban (mặc định đã bỏ trống)
            logger.info("Không chọn phòng ban (mặc định không chọn)");

            // Bước 2: Bấm vào ô danh sách vai trò
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            // Tìm dropdown vai trò bằng CSS selector chính xác
            WebElement dropdownField = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='MuiInputBase-root MuiOutlinedInput-root MuiInputBase-colorPrimary MuiInputBase-fullWidth MuiInputBase-sizeSmall css-1owlt1s']")));

            // Click vào dropdown vai trò
            dropdownField.click();
            logger.info("Đã click vào dropdown để chọn vai trò");

            // Kiểm tra danh sách tùy chọn không hiển thị hoặc rỗng
            try {
                // Chờ tối đa 3 giây để kiểm tra xem menu dropdown có xuất hiện không
                wait = new WebDriverWait(driver, Duration.ofSeconds(3));
                List<WebElement> options = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.xpath("//ul[contains(@class,'MuiMenu-list')]/li")));

                // Nếu không có tùy chọn nào hoặc danh sách rỗng
                if (options.isEmpty()) {
                    logger.info("Dropdown vai trò không hiện dữ liệu khi chưa chọn phòng ban (đúng như mong đợi)");
                } else {
                    logger.error("Dropdown vai trò hiện " + options.size() + " tùy chọn mặc dù chưa chọn phòng ban");
                    Assert.fail("Dropdown vai trò hiện dữ liệu mặc dù chưa chọn phòng ban");
                }
            } catch (Exception e) {
                // Không tìm thấy menu tùy chọn, điều này cũng là kết quả mong đợi
                logger.info("Không tìm thấy menu tùy chọn vai trò mặc dù đã click (đúng như mong đợi)");
            }

            // Đóng dropdown bằng giả lập nhấn nút ESC
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
            Thread.sleep(500);

            // Test phần 2: Kiểm tra dropdown VAI TRÒ hoạt động bình thường sau khi chọn PHÒNG BAN
            logger.info("Kiểm tra dropdown vai trò sau khi chọn phòng ban");

            // Chọn một phòng ban
            WebElement daoTaoRadioLabel = driver.findElement(
                    By.xpath("//label[contains(@class, 'MuiFormControlLabel-root')]/span[contains(@class, 'MuiTypography-root') and text()='Đào tạo']"));
            daoTaoRadioLabel.click();
            logger.info("Đã chọn radio button 'Đào tạo'");

            // Click lại vào dropdown vai trò
            dropdownField = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='MuiInputBase-root MuiOutlinedInput-root MuiInputBase-colorPrimary MuiInputBase-fullWidth MuiInputBase-sizeSmall css-1owlt1s']")));
            dropdownField.click();
            logger.info("Đã click vào dropdown vai trò sau khi chọn phòng ban");

            // Kiểm tra danh sách tùy chọn CÓ hiển thị
            try {
                // Chờ tối đa 3 giây để menu dropdown xuất hiện
                wait = new WebDriverWait(driver, Duration.ofSeconds(3));
                List<WebElement> options = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.xpath("//ul[contains(@class,'MuiMenu-list')]/li")));

                // Phải có ít nhất một tùy chọn
                Assert.assertFalse(options.isEmpty(), "Dropdown vai trò không hiển thị dữ liệu sau khi chọn phòng ban");
                logger.info("Dropdown vai trò hiển thị " + options.size() + " tùy chọn sau khi chọn phòng ban (đúng như mong đợi)");

                // In ra danh sách các vai trò hiển thị
                for (WebElement option : options) {
                    logger.info("Vai trò hiển thị: " + option.getText());
                }

                // Đóng dropdown bằng giả lập nhấn nút ESC
                driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
                Thread.sleep(500);
            } catch (Exception e) {
                logger.error("Không tìm thấy tùy chọn vai trò sau khi chọn phòng ban: " + e.getMessage());
                Assert.fail("Không tìm thấy tùy chọn vai trò sau khi chọn phòng ban");
            }

            // Đóng form để kết thúc test case
            WebElement cancelButton = driver.findElement(By.xpath("//button[contains(text(),'Huỷ bỏ')]"));
            cancelButton.click();
            logger.info("Đã đóng form thêm nhân viên");

            // Đợi form đóng
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]")));

        } catch (Exception e) {
            logger.error("Lỗi trong quá trình kiểm tra: " + e.getMessage());
            Assert.fail("Lỗi trong quá trình kiểm tra: " + e.getMessage());
        }

        logger.info("Kết thúc test case TC007: Kiểm tra danh sách Chức vụ không hiện dữ liệu và không thể chọn khi chưa chọn Phòng ban");
    }

    /**
     * TC-SCRUM2-008: Kiểm tra hiển thị tự động danh sách Chức vụ tương ứng khi chọn Phòng ban
     * Feature: Quản lý nhân viên
     * Sub-feature: Thêm nhân viên vào hệ thống
     * Linked Story: https://hthuy7605-studentmanagement.atlassian.net/browse/SCRUM-2
     * <p>
     * Pre-conditions:
     * - Admin đã đăng nhập và đang ở màn hình "Thêm nhân viên"
     * <p>
     * Steps:
     * 1. Chọn Phòng ban lần lượt từng role:
     *    - "Văn phòng" -> bấm vào danh sách chức vụ
     *    - "Đào tạo" -> bấm vào danh sách chức vụ
     * <p>
     * Expected Results:
     * - Hệ thống tự động hiển thị danh sách Chức vụ tương ứng dạng dropdown list
     * - Chọn Văn phòng: hiển thị đúng chức vụ tương ứng: Tư vấn viên, CSKH
     * - Chọn Đào tạo: hiển thị đúng chức vụ tương ứng: Giáo viên Việt Nam, Giáo viên Nước Ngoài, Trợ giảng
     * <p>
     */
    @Test
    void TC_SCRUM2_008_VerifyRoleListByDepartment() {
        logger.info("Bắt đầu test case TC008: Kiểm tra hiển thị tự động danh sách Chức vụ tương ứng khi chọn Phòng ban");

        // Điều kiện tiên quyết: Admin đã đăng nhập và đang ở trang Quản lý nhân viên
        // Đăng nhập đã được thực hiện trong @BeforeMethod
        logger.info("Điều hướng đến trang Quản lý nhân viên");
        staffPage.navigateToStaffPage();

        // Mở form thêm nhân viên
        logger.info("Bấm nút thêm nhân viên");
        staffPage.waitForBackdropToDisappear();
        staffPage.clickAddStaffButton();

        try {
            // Verify form is displayed
            WebElement addStaffForm = driver.findElement(By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]"));
            Assert.assertTrue(addStaffForm.isDisplayed(), "Form thêm nhân viên không hiển thị");
            logger.info("Form thêm nhân viên hiển thị thành công");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            // PHẦN 1: KIỂM TRA CHỨC VỤ KHI CHỌN PHÒNG BAN "VĂN PHÒNG"
            logger.info("Bước 1: Chọn phòng ban 'Văn phòng'");

            // Tìm và click vào label của radio button Văn phòng (bằng cách click vào text)
            WebElement vanPhongRadioLabel = driver.findElement(
                    By.xpath("//label[contains(@class, 'MuiFormControlLabel-root')]/span[contains(@class, 'MuiTypography-root') and text()='Văn phòng']"));
            vanPhongRadioLabel.click();
            logger.info("Đã chọn radio button 'Văn phòng'");

            // Đợi một chút để hệ thống cập nhật danh sách chức vụ
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                logger.error("Lỗi chờ đợi: " + e.getMessage());
            }

            // Click vào dropdown vai trò để xem danh sách
            WebElement roleDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='MuiInputBase-root MuiOutlinedInput-root MuiInputBase-colorPrimary MuiInputBase-fullWidth MuiInputBase-sizeSmall css-1owlt1s']")));
            roleDropdown.click();
            logger.info("Đã click vào dropdown chức vụ sau khi chọn phòng ban 'Văn phòng'");

            // Kiểm tra danh sách vai trò hiển thị cho Văn phòng
            List<WebElement> officeRoleOptions = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//ul[contains(@class,'MuiMenu-list')]/li")));

            // Tạo danh sách vai trò mong đợi và vai trò thực tế
            List<String> expectedOfficeRoles = List.of("Tư vấn viên", "Chăm sóc khách hàng");
            List<String> actualOfficeRoles = new ArrayList<>();

            for (WebElement option : officeRoleOptions) {
                actualOfficeRoles.add(option.getText());
                logger.info("Chức vụ hiển thị cho 'Văn phòng': " + option.getText());
            }

            // Kiểm tra tất cả các vai trò mong đợi đều có trong danh sách thực tế
            for (String expectedRole : expectedOfficeRoles) {
                Assert.assertTrue(actualOfficeRoles.contains(expectedRole),
                        "Vai trò '" + expectedRole + "' không có trong danh sách chức vụ của Văn phòng");
            }

            // Kiểm tra số lượng vai trò hiển thị khớp với số lượng mong đợi
            Assert.assertEquals(actualOfficeRoles.size(), expectedOfficeRoles.size(),
                    "Số lượng chức vụ hiển thị không khớp với mong đợi cho Văn phòng");

            logger.info("Các chức vụ hiển thị đúng cho phòng ban 'Văn phòng'");

            // Đóng dropdown bằng giả lập nhấn nút ESC
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
            Thread.sleep(500);

            // PHẦN 2: KIỂM TRA CHỨC VỤ KHI CHỌN PHÒNG BAN "ĐÀO TẠO"
            logger.info("Bước 2: Chọn phòng ban 'Đào tạo'");

            // Tìm và click vào label của radio button Đào tạo (bằng cách click vào text)
            WebElement daoTaoRadioLabel = driver.findElement(
                    By.xpath("//label[contains(@class, 'MuiFormControlLabel-root')]/span[contains(@class, 'MuiTypography-root') and text()='Đào tạo']"));
            daoTaoRadioLabel.click();
            logger.info("Đã chọn radio button 'Đào tạo'");

            // Đợi một chút để hệ thống cập nhật danh sách chức vụ
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                logger.error("Lỗi chờ đợi: " + e.getMessage());
            }

            // Click vào dropdown vai trò để xem danh sách
            roleDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='MuiInputBase-root MuiOutlinedInput-root MuiInputBase-colorPrimary MuiInputBase-fullWidth MuiInputBase-sizeSmall css-1owlt1s']")));
            roleDropdown.click();
            logger.info("Đã click vào dropdown chức vụ sau khi chọn phòng ban 'Đào tạo'");

            // Kiểm tra danh sách vai trò hiển thị cho Đào tạo
            List<WebElement> trainingRoleOptions = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//ul[contains(@class,'MuiMenu-list')]/li")));

            // Tạo danh sách vai trò mong đợi và vai trò thực tế
            List<String> expectedTrainingRoles = List.of("Giáo viên Việt Nam", "Giáo viên Nước Ngoài", "Trợ giảng");
            List<String> actualTrainingRoles = new ArrayList<>();

            for (WebElement option : trainingRoleOptions) {
                actualTrainingRoles.add(option.getText());
                logger.info("Chức vụ hiển thị cho 'Đào tạo': " + option.getText());
            }

            // Kiểm tra tất cả các vai trò mong đợi đều có trong danh sách thực tế
            for (String expectedRole : expectedTrainingRoles) {
                Assert.assertTrue(actualTrainingRoles.contains(expectedRole),
                        "Vai trò '" + expectedRole + "' không có trong danh sách chức vụ của Đào tạo");
            }

            // Kiểm tra số lượng vai trò hiển thị khớp với số lượng mong đợi
            Assert.assertEquals(actualTrainingRoles.size(), expectedTrainingRoles.size(),
                    "Số lượng chức vụ hiển thị không khớp với mong đợi cho Đào tạo");

            logger.info("Các chức vụ hiển thị đúng cho phòng ban 'Đào tạo'");

            // Đóng dropdown bằng giả lập nhấn nút ESC
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
            Thread.sleep(500);

            // Đóng form để kết thúc test case
            WebElement cancelButton = driver.findElement(By.xpath("//button[contains(text(),'Huỷ bỏ')]"));
            cancelButton.click();
            logger.info("Đã đóng form thêm nhân viên");

            // Đợi form đóng
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]")));

        } catch (Exception e) {
            logger.error("Lỗi trong quá trình kiểm tra: " + e.getMessage());
            Assert.fail("Lỗi trong quá trình kiểm tra: " + e.getMessage());
        }

        logger.info("Kết thúc test case TC008: Kiểm tra hiển thị tự động danh sách Chức vụ tương ứng khi chọn Phòng ban");
    }

    /**
     * TC-SCRUM2-009: Kiểm tra có cảnh báo khi nhập trùng tên đăng nhập đã tồn tại trên hệ thống và không cho phép tạo nhân viên
     * Feature: Quản lý nhân viên
     * Sub-feature: Thêm nhân viên vào hệ thống
     * Linked Story: https://hthuy7605-studentmanagement.atlassian.net/browse/SCRUM-2
     * <p>
     * Pre-conditions:
     * - Admin đã đăng nhập và đang ở màn hình "Thêm nhân viên"
     * <p>
     * Steps:
     * 1. Nhập đầy đủ các trường bắt buộc (Họ và tên đệm, Tên, Tên đăng nhập, Mật khẩu, SĐT) theo cột Test Data/ User4
     * 2. Nhập tên đăng nhập: hthuy (username đã có trên hệ thống)
     * 3. Bấm xác nhận
     * <p>
     * Expected Results:
     * - Hệ thống báo lỗi "Tên đăng nhập đã tồn tại"
     * <p>
     */
    @Test
    void TC_SCRUM2_009_VerifyDuplicateUsernameError() {
        logger.info("Bắt đầu test case TC009: Kiểm tra có cảnh báo khi nhập trùng tên đăng nhập đã tồn tại");

        // Điều kiện tiên quyết: Admin đã đăng nhập và đang ở trang Quản lý nhân viên
        // Đăng nhập đã được thực hiện trong @BeforeMethod
        logger.info("Điều hướng đến trang Quản lý nhân viên");
        staffPage.navigateToStaffPage();

        // Mở form thêm nhân viên
        logger.info("Bấm nút thêm nhân viên");
        staffPage.waitForBackdropToDisappear();
        staffPage.clickAddStaffButton();

        try {
            // Verify form is displayed
            WebElement addStaffForm = driver.findElement(By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]"));
            Assert.assertTrue(addStaffForm.isDisplayed(), "Form thêm nhân viên không hiển thị");
            logger.info("Form thêm nhân viên hiển thị thành công");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            // Nhập họ và tên đệm
            WebElement lastNameField = driver.findElement(By.xpath("//label[contains(text(),'Họ và tên đệm')]/following-sibling::div//input"));
            lastNameField.clear();
            lastNameField.sendKeys("Nguyễn Văn");
            logger.info("Đã nhập họ và tên đệm: Nguyễn Văn");

            // Nhập tên
            WebElement firstNameField = driver.findElement(By.xpath("//label[contains(text(),'Tên')]/following-sibling::div//input"));
            firstNameField.clear();
            firstNameField.sendKeys("Test");
            logger.info("Đã nhập tên: Test");

            // Nhập tên đăng nhập đã tồn tại
            WebElement usernameField = driver.findElement(By.xpath("//label[contains(text(),'Tên đăng nhập')]/following-sibling::div//input"));
            usernameField.clear();
            usernameField.sendKeys("hthuy");
            logger.info("Đã nhập tên đăng nhập: hthuy (đã tồn tại trên hệ thống)");

            // Nhập mật khẩu
            WebElement passwordField = driver.findElement(By.xpath("//label[contains(text(),'Mật khẩu')]/following-sibling::div//input"));
            passwordField.clear();
            passwordField.sendKeys("Test1234");
            logger.info("Đã nhập mật khẩu: Test1234");

            // Nhập SĐT
            WebElement phoneField = driver.findElement(By.xpath("//label[contains(text(),'SĐT')]/following-sibling::div//input"));
            phoneField.clear();
            phoneField.sendKeys("0903123456");
            logger.info("Đã nhập SĐT: 0903123456");

            // Chọn phòng ban (chọn Văn phòng)
            WebElement vanPhongRadioLabel = driver.findElement(
                    By.xpath("//label[contains(@class, 'MuiFormControlLabel-root')]/span[contains(@class, 'MuiTypography-root') and text()='Văn phòng']"));
            vanPhongRadioLabel.click();
            logger.info("Đã chọn phòng ban: Văn phòng");

            // Đợi một chút để hệ thống cập nhật danh sách chức vụ
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                logger.error("Lỗi chờ đợi: " + e.getMessage());
            }

            // Chọn chức vụ
            WebElement roleDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='MuiInputBase-root MuiOutlinedInput-root MuiInputBase-colorPrimary MuiInputBase-fullWidth MuiInputBase-sizeSmall css-1owlt1s']")));
            roleDropdown.click();
            logger.info("Đã click vào dropdown chức vụ");

            // Chọn chức vụ "Tư vấn viên"
            WebElement roleOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//li[contains(text(),'Tư vấn viên')]")));
            roleOption.click();
            logger.info("Đã chọn chức vụ: Tư vấn viên");

            // Đóng dropdown bằng giả lập nhấn nút ESC
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
            Thread.sleep(500);

            // Bấm xác nhận
            logger.info("Bấm nút xác nhận");
            WebElement confirmButton = driver.findElement(By.xpath("//button[contains(text(),'Xác nhận')]"));
            confirmButton.click();

            // Đợi thông báo lỗi xuất hiện
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'MuiAlert-message') and contains(text(), 'Tên đăng nhập đã tồn tại')]")));

            // Kiểm tra thông báo lỗi
            Assert.assertTrue(errorMessage.isDisplayed(), "Thông báo lỗi không hiển thị");
            Assert.assertTrue(errorMessage.getText().contains("Tên đăng nhập đã tồn tại"),
                    "Thông báo lỗi không chứa nội dung 'Tên đăng nhập đã tồn tại'");
            logger.info("Đã xuất hiện thông báo lỗi: " + errorMessage.getText());

            // Kiểm tra form vẫn hiển thị (không đóng form khi có lỗi)
            WebElement formAfterError = driver.findElement(By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]"));
            Assert.assertTrue(formAfterError.isDisplayed(), "Form thêm nhân viên đã đóng sau khi hiển thị lỗi");
            logger.info("Form thêm nhân viên vẫn hiển thị sau khi có lỗi (đúng như mong đợi)");

            // Đóng form để kết thúc test case
            WebElement cancelButton = driver.findElement(By.xpath("//button[contains(text(),'Huỷ bỏ')]"));
            cancelButton.click();
            logger.info("Đã đóng form thêm nhân viên");

            // Đợi form đóng
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]")));

        } catch (Exception e) {
            logger.error("Lỗi trong quá trình kiểm tra: " + e.getMessage());
            Assert.fail("Lỗi trong quá trình kiểm tra: " + e.getMessage());
        }

        logger.info("Kết thúc test case TC009: Kiểm tra có cảnh báo khi nhập trùng tên đăng nhập đã tồn tại");
    }

    /**
     * TC-SCRUM2-013: Kiểm tra nút chức năng "Hủy bỏ"
     * Feature: Quản lý nhân viên
     * Sub-feature: Thêm nhân viên vào hệ thống
     * Linked Story: https://hthuy7605-studentmanagement.atlassian.net/browse/SCRUM-2
     * <p>
     * Pre-conditions:
     * - Admin đã đăng nhập và đang ở màn hình "Thêm nhân viên"
     * <p>
     * Steps:
     * 1. Nhập đầy đủ các trường bắt buộc (Họ và tên đệm, Tên, Tên đăng nhập, Mật khẩu, SĐT) theo cột Test Data/ User2
     * 2. Kiểm tra 02 TH sau, khi:
     *    2.1 Click vào nút "Hủy bỏ" khi chưa nhập dữ liệu
     *    2.2 Click vào nút "Hủy bỏ" sau khi nhập dữ liệu
     * <p>
     * Expected Results:
     * - Form đóng lại, không có dữ liệu nào được lưu
     * <p>
     */
    @Test
    void TC_SCRUM2_013_VerifyCancelButton() {
        logger.info("Bắt đầu test case TC013: Kiểm tra nút chức năng 'Hủy bỏ'");

        // Điều kiện tiên quyết: Admin đã đăng nhập và đang ở trang Quản lý nhân viên
        // Đăng nhập đã được thực hiện trong @BeforeMethod
        logger.info("Điều hướng đến trang Quản lý nhân viên");
        staffPage.navigateToStaffPage();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Kiểm tra số lượng nhân viên ban đầu
        int initialStaffCount = staffPage.getStaffCount();
        logger.info("Số lượng nhân viên ban đầu: " + initialStaffCount);

        try {
            // Test case 2.1: Click Hủy bỏ khi chưa nhập dữ liệu
            logger.info("Trường hợp 2.1: Click 'Hủy bỏ' khi chưa nhập dữ liệu");

            // Mở form thêm nhân viên
            staffPage.waitForBackdropToDisappear();
            staffPage.clickAddStaffButton();

            // Kiểm tra form đã hiển thị
            WebElement addStaffForm = driver.findElement(By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]"));
            Assert.assertTrue(addStaffForm.isDisplayed(), "Form thêm nhân viên không hiển thị");
            logger.info("Form thêm nhân viên hiển thị thành công");

            // Click vào nút "Hủy bỏ"
            WebElement cancelButton = driver.findElement(By.xpath("//button[contains(text(),'Huỷ bỏ')]"));
            cancelButton.click();
            logger.info("Đã click nút 'Hủy bỏ' khi chưa nhập dữ liệu");

            // Đợi form đóng
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]")));
            logger.info("Form đã đóng sau khi click 'Hủy bỏ'");

            // Kiểm tra số lượng nhân viên không thay đổi
            int currentStaffCount = staffPage.getStaffCount();
            Assert.assertEquals(currentStaffCount, initialStaffCount, "Số lượng nhân viên đã thay đổi sau khi click 'Hủy bỏ' khi chưa nhập dữ liệu");
            logger.info("Số lượng nhân viên không thay đổi: " + currentStaffCount);

            // Test case 2.2: Click Hủy bỏ sau khi nhập dữ liệu
            logger.info("Trường hợp 2.2: Click 'Hủy bỏ' sau khi nhập dữ liệu");

            // Mở lại form thêm nhân viên
            staffPage.waitForBackdropToDisappear();
            staffPage.clickAddStaffButton();

            // Kiểm tra form đã hiển thị
            addStaffForm = driver.findElement(By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]"));
            Assert.assertTrue(addStaffForm.isDisplayed(), "Form thêm nhân viên không hiển thị");
            logger.info("Form thêm nhân viên hiển thị thành công");

            // Nhập các trường bắt buộc
            // Nhập họ và tên đệm
            WebElement lastNameField = driver.findElement(By.xpath("//label[contains(text(),'Họ và tên đệm')]/following-sibling::div//input"));
            lastNameField.clear();
            lastNameField.sendKeys("Trần Thị");
            logger.info("Đã nhập họ và tên đệm: Trần Thị");

            // Nhập tên
            WebElement firstNameField = driver.findElement(By.xpath("//label[contains(text(),'Tên')]/following-sibling::div//input"));
            firstNameField.clear();
            firstNameField.sendKeys("Ngọc");
            logger.info("Đã nhập tên: Ngọc");

            // Nhập tên đăng nhập
            WebElement usernameField = driver.findElement(By.xpath("//label[contains(text(),'Tên đăng nhập')]/following-sibling::div//input"));
            usernameField.clear();
            usernameField.sendKeys("ttngoc");
            logger.info("Đã nhập tên đăng nhập: ttngoc");

            // Nhập mật khẩu
            WebElement passwordField = driver.findElement(By.xpath("//label[contains(text(),'Mật khẩu')]/following-sibling::div//input"));
            passwordField.clear();
            passwordField.sendKeys("Test1234");
            logger.info("Đã nhập mật khẩu: Test1234");

            // Nhập SĐT
            WebElement phoneField = driver.findElement(By.xpath("//label[contains(text(),'SĐT')]/following-sibling::div//input"));
            phoneField.clear();
            phoneField.sendKeys("0901234567");
            logger.info("Đã nhập SĐT: 0901234567");

            // Chọn phòng ban (chọn Văn phòng)
            WebElement vanPhongRadioLabel = driver.findElement(
                    By.xpath("//label[contains(@class, 'MuiFormControlLabel-root')]/span[contains(@class, 'MuiTypography-root') and text()='Văn phòng']"));
            vanPhongRadioLabel.click();
            logger.info("Đã chọn phòng ban: Văn phòng");

            // Đợi một chút để hệ thống cập nhật danh sách chức vụ
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                logger.error("Lỗi chờ đợi: " + e.getMessage());
            }

            // Chọn chức vụ
            WebElement roleDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='MuiInputBase-root MuiOutlinedInput-root MuiInputBase-colorPrimary MuiInputBase-fullWidth MuiInputBase-sizeSmall css-1owlt1s']")));
            roleDropdown.click();
            logger.info("Đã click vào dropdown chức vụ");

            // Chọn chức vụ "Tư vấn viên"
            WebElement roleOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//li[contains(text(),'Tư vấn viên')]")));
            roleOption.click();
            logger.info("Đã chọn chức vụ: Tư vấn viên");

            // Đóng dropdown bằng giả lập nhấn nút ESC
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
            Thread.sleep(500);

            // Click vào nút "Hủy bỏ"
            cancelButton = driver.findElement(By.xpath("//button[contains(text(),'Huỷ bỏ')]"));
            cancelButton.click();
            logger.info("Đã click nút 'Hủy bỏ' sau khi nhập dữ liệu");

            // Đợi form đóng
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]")));
            logger.info("Form đã đóng sau khi click 'Hủy bỏ'");

            // Kiểm tra số lượng nhân viên không thay đổi
            currentStaffCount = staffPage.getStaffCount();
            Assert.assertEquals(currentStaffCount, initialStaffCount, "Số lượng nhân viên đã thay đổi sau khi click 'Hủy bỏ' sau khi nhập dữ liệu");
            logger.info("Số lượng nhân viên không thay đổi: " + currentStaffCount);

            // Kiểm tra nhân viên với tên "Trần Thị Ngọc" không được thêm vào hệ thống
            WebElement staffRow = staffPage.findStaffByName("Trần Thị Ngọc");
            Assert.assertNull(staffRow, "Nhân viên đã được thêm vào hệ thống mặc dù đã click nút 'Hủy bỏ'");
            logger.info("Nhân viên 'Trần Thị Ngọc' không tồn tại trong hệ thống (đúng như mong đợi)");

        } catch (Exception e) {
            logger.error("Lỗi trong quá trình kiểm tra: " + e.getMessage());
            Assert.fail("Lỗi trong quá trình kiểm tra: " + e.getMessage());
        }

        logger.info("Kết thúc test case TC013: Kiểm tra nút chức năng 'Hủy bỏ'");
    }

    /**
     * TC-SCRUM2-016: Kiểm tra nhập tên đăng nhập tối đa 50 ký tự
     * Feature: Quản lý nhân viên
     * Sub-feature: Thêm nhân viên vào hệ thống
     * Linked Story: https://hthuy7605-studentmanagement.atlassian.net/browse/SCRUM-2
     * <p>
     * Pre-conditions:
     * - Admin đã đăng nhập và đang ở màn hình "Thêm nhân viên"
     * <p>
     * Steps:
     * 1. Nhập chuỗi gồm đúng 50 ký tự vào ô Tên đăng nhập
     * 2. Nhập các thông tin bắt buộc khác (họ, tên, mật khẩu, sđt...)
     * 3. Nhấn nút "Xác nhận"
     * <p>
     * Expected Results:
     * - Hệ thống chấp nhận và tạo nhân viên thành công, hiển thị trong danh sách
     * <p>
     */
    @Test
    void TC_SCRUM2_016_VerifyMaxLengthUsername() {
        logger.info("Bắt đầu test case TC016: Kiểm tra nhập tên đăng nhập tối đa 50 ký tự");

        // Điều kiện tiên quyết: Admin đã đăng nhập và đang ở trang Quản lý nhân viên
        // Đăng nhập đã được thực hiện trong @BeforeMethod
        logger.info("Điều hướng đến trang Quản lý nhân viên");
        staffPage.navigateToStaffPage();

        // Tạo tên nhân viên duy nhất bằng cách sử dụng timestamp
        String uniqueTimestamp = String.valueOf(System.currentTimeMillis());
        String lastName = "Nguyễn";
        String firstName = "Test" + uniqueTimestamp.substring(uniqueTimestamp.length() - 5);
        String fullName = lastName + " " + firstName;

        // Tạo tên đăng nhập đúng 50 ký tự
        String usernameBase = "test" + uniqueTimestamp.substring(uniqueTimestamp.length() - 5);
        // Thêm ký tự vào username để đạt đúng 50 ký tự
        StringBuilder username = new StringBuilder(usernameBase);
        while (username.length() < 50) {
            username.append("a");
        }

        String phoneNumber = "09" + uniqueTimestamp.substring(uniqueTimestamp.length() - 8);
        String department = "Văn phòng";
        String position = "Tư vấn viên";

        logger.info("Sẽ tạo nhân viên mới với thông tin:");
        logger.info("- Tên: " + fullName);
        logger.info("- Tên đăng nhập: " + username + " (độ dài: " + username.length() + " ký tự)");
        logger.info("- SĐT: " + phoneNumber);
        logger.info("- Phòng ban: " + department);
        logger.info("- Chức vụ: " + position);

        // Lưu tổng số nhân viên trước khi thêm mới
        int initialStaffCount = staffPage.getStaffCount();
        logger.info("Số lượng nhân viên trước khi thêm mới: " + initialStaffCount);

        try {
            // 1. Tạo nhân viên mới với tên đăng nhập 50 ký tự
            logger.info("Bắt đầu tạo nhân viên mới với tên đăng nhập 50 ký tự");

            // Mở form thêm nhân viên
            staffPage.waitForBackdropToDisappear();
            staffPage.clickAddStaffButton();

            // Đợi form hiển thị
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement addStaffForm = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]")));
            Assert.assertTrue(addStaffForm.isDisplayed(), "Form thêm nhân viên không hiển thị");
            logger.info("Form thêm nhân viên hiển thị thành công");

            // Nhập họ và tên đệm
            WebElement lastNameField = driver.findElement(By.xpath("//label[contains(text(),'Họ và tên đệm')]/following-sibling::div//input"));
            lastNameField.clear();
            lastNameField.sendKeys(lastName);
            logger.info("Đã nhập họ và tên đệm: " + lastName);

            // Nhập tên
            WebElement firstNameField = driver.findElement(By.xpath("//label[contains(text(),'Tên')]/following-sibling::div//input"));
            firstNameField.clear();
            firstNameField.sendKeys(firstName);
            logger.info("Đã nhập tên: " + firstName);

            // Nhập tên đăng nhập với đúng 50 ký tự
            WebElement usernameField = driver.findElement(By.xpath("//label[contains(text(),'Tên đăng nhập')]/following-sibling::div//input"));
            usernameField.clear();
            usernameField.sendKeys(username.toString());
            logger.info("Đã nhập tên đăng nhập với 50 ký tự: " + username);

            // Nhập mật khẩu
            WebElement passwordField = driver.findElement(By.xpath("//label[contains(text(),'Mật khẩu')]/following-sibling::div//input"));
            passwordField.clear();
            passwordField.sendKeys("Test1234");
            logger.info("Đã nhập mật khẩu: Test1234");

            // Nhập SĐT
            WebElement phoneField = driver.findElement(By.xpath("//label[contains(text(),'SĐT')]/following-sibling::div//input"));
            phoneField.clear();
            phoneField.sendKeys(phoneNumber);
            logger.info("Đã nhập SĐT: " + phoneNumber);

            // Chọn phòng ban (chọn Văn phòng)
            WebElement vanPhongRadioLabel = driver.findElement(
                    By.xpath("//label[contains(@class, 'MuiFormControlLabel-root')]/span[contains(@class, 'MuiTypography-root') and text()='Văn phòng']"));
            vanPhongRadioLabel.click();
            logger.info("Đã chọn phòng ban: " + department);

            // Đợi một chút để hệ thống cập nhật danh sách chức vụ
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                logger.error("Lỗi chờ đợi: " + e.getMessage());
            }

            // Chọn chức vụ
            WebElement roleDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='MuiInputBase-root MuiOutlinedInput-root MuiInputBase-colorPrimary MuiInputBase-fullWidth MuiInputBase-sizeSmall css-1owlt1s']")));
            roleDropdown.click();
            logger.info("Đã click vào dropdown chức vụ");

            // Chọn chức vụ "Tư vấn viên"
            WebElement roleOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//li[contains(text(),'Tư vấn viên')]")));
            roleOption.click();
            logger.info("Đã chọn chức vụ: " + position);

            // Đóng dropdown bằng giả lập nhấn nút ESC
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
            Thread.sleep(500);

            // Bấm nút xác nhận để tạo nhân viên
            WebElement confirmButton = driver.findElement(By.xpath("//button[contains(text(),'Xác nhận')]"));
            confirmButton.click();
            logger.info("Đã bấm nút xác nhận để tạo nhân viên");

            // Đợi thông báo biến mất và form đóng
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]")));
            logger.info("Form thêm nhân viên đã đóng");

            // 2. Kiểm tra nhân viên mới trong danh sách
            logger.info("Bắt đầu kiểm tra nhân viên mới trong danh sách");

            // Đợi trang tải lại sau khi thêm nhân viên
            staffPage.waitForBackdropToDisappear();

            // Kiểm tra tổng số nhân viên sau khi thêm mới
            int newStaffCount = staffPage.getStaffCount();
            Assert.assertEquals(newStaffCount, initialStaffCount + 1,
                    "Tổng số nhân viên không tăng lên sau khi thêm mới");
            logger.info("Số lượng nhân viên sau khi thêm mới: " + newStaffCount + " (tăng 1 so với ban đầu)");

            // Sử dụng hàm tìm kiếm mới để tìm nhân viên vừa tạo với tên đăng nhập 50 ký tự
            WebElement staffRow = staffPage.findStaffByDetails(fullName, phoneNumber, department, position);

            // Kiểm tra kết quả tìm kiếm
            Assert.assertNotNull(staffRow, "Không tìm thấy nhân viên mới tạo với tên đăng nhập 50 ký tự trong danh sách");
            logger.info("Đã tìm thấy nhân viên mới tạo với tên đăng nhập 50 ký tự trong danh sách");

            // Kết luận test case
            logger.info("Kết luận: Hệ thống cho phép tạo nhân viên với tên đăng nhập đúng 50 ký tự");

        } catch (Exception e) {
            logger.error("Lỗi trong quá trình kiểm tra: " + e.getMessage());
            Assert.fail("Lỗi trong quá trình kiểm tra: " + e.getMessage());
        }

        logger.info("Kết thúc test case TC016: Kiểm tra nhập tên đăng nhập tối đa 50 ký tự");
    }

    /**
     * TC-SCRUM2-017: Kiểm tra nhân viên mới vừa được tạo có trong danh sách nhân viên
     * Feature: Quản lý nhân viên
     * Sub-feature: Thêm nhân viên vào hệ thống
     * Linked Story: https://hthuy7605-studentmanagement.atlassian.net/browse/SCRUM-2
     * <p>
     * Pre-conditions:
     * - Admin đã đăng nhập và đang ở màn hình "Thêm nhân viên"
     * <p>
     * Steps:
     * 1. Tạo 1 user với đầy đủ thông tin hợp lệ thành công
     * 2. Vào danh sách nhân viên tìm kiếm user vừa tạo
     * 3. Quan sát
     * <p>
     * Expected Results:
     * - Nhân viên vừa được tạo hiển thị trong danh sách nhân viên
     * <p>
     */
    @Test
    void TC_SCRUM2_017_VerifyNewStaffInList() {
        logger.info("Bắt đầu test case TC017: Kiểm tra nhân viên mới vừa được tạo có trong danh sách nhân viên");

        // Điều kiện tiên quyết: Admin đã đăng nhập và đang ở trang Quản lý nhân viên
        // Đăng nhập đã được thực hiện trong @BeforeMethod
        logger.info("Điều hướng đến trang Quản lý nhân viên");
        staffPage.navigateToStaffPage();

        // Tạo tên nhân viên duy nhất bằng cách sử dụng timestamp
        String uniqueTimestamp = String.valueOf(System.currentTimeMillis());
        String lastName = "Nguyễn";
        String firstName = "Test" + uniqueTimestamp.substring(uniqueTimestamp.length() - 5);
        String fullName = lastName + " " + firstName;
        String username = "test" + uniqueTimestamp.substring(uniqueTimestamp.length() - 5);
        String phoneNumber = "09" + uniqueTimestamp.substring(uniqueTimestamp.length() - 8);
        String department = "Văn phòng";
        String position = "Tư vấn viên";

        logger.info("Sẽ tạo nhân viên mới với thông tin:");
        logger.info("- Tên: " + fullName);
        logger.info("- Tên đăng nhập: " + username);
        logger.info("- SĐT: " + phoneNumber);
        logger.info("- Phòng ban: " + department);
        logger.info("- Chức vụ: " + position);

        // Lưu tổng số nhân viên trước khi thêm mới
        int initialStaffCount = staffPage.getStaffCount();
        logger.info("Số lượng nhân viên trước khi thêm mới: " + initialStaffCount);

        try {
            // 1. Tạo nhân viên mới với thông tin hợp lệ
            logger.info("Bắt đầu tạo nhân viên mới");

            // Mở form thêm nhân viên
            staffPage.waitForBackdropToDisappear();
            staffPage.clickAddStaffButton();

            // Đợi form hiển thị
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement addStaffForm = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]")));
            Assert.assertTrue(addStaffForm.isDisplayed(), "Form thêm nhân viên không hiển thị");
            logger.info("Form thêm nhân viên hiển thị thành công");

            // Nhập họ và tên đệm
            WebElement lastNameField = driver.findElement(By.xpath("//label[contains(text(),'Họ và tên đệm')]/following-sibling::div//input"));
            lastNameField.clear();
            lastNameField.sendKeys(lastName);
            logger.info("Đã nhập họ và tên đệm: " + lastName);

            // Nhập tên
            WebElement firstNameField = driver.findElement(By.xpath("//label[contains(text(),'Tên')]/following-sibling::div//input"));
            firstNameField.clear();
            firstNameField.sendKeys(firstName);
            logger.info("Đã nhập tên: " + firstName);

            // Nhập tên đăng nhập
            WebElement usernameField = driver.findElement(By.xpath("//label[contains(text(),'Tên đăng nhập')]/following-sibling::div//input"));
            usernameField.clear();
            usernameField.sendKeys(username);
            logger.info("Đã nhập tên đăng nhập: " + username);

            // Nhập mật khẩu
            WebElement passwordField = driver.findElement(By.xpath("//label[contains(text(),'Mật khẩu')]/following-sibling::div//input"));
            passwordField.clear();
            passwordField.sendKeys("Test1234");
            logger.info("Đã nhập mật khẩu: Test1234");

            // Nhập SĐT
            WebElement phoneField = driver.findElement(By.xpath("//label[contains(text(),'SĐT')]/following-sibling::div//input"));
            phoneField.clear();
            phoneField.sendKeys(phoneNumber);
            logger.info("Đã nhập SĐT: " + phoneNumber);

            // Chọn phòng ban (chọn Văn phòng)
            WebElement vanPhongRadioLabel = driver.findElement(
                    By.xpath("//label[contains(@class, 'MuiFormControlLabel-root')]/span[contains(@class, 'MuiTypography-root') and text()='Văn phòng']"));
            vanPhongRadioLabel.click();
            logger.info("Đã chọn phòng ban: " + department);

            // Đợi một chút để hệ thống cập nhật danh sách chức vụ
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                logger.error("Lỗi chờ đợi: " + e.getMessage());
            }

            // Chọn chức vụ
            WebElement roleDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='MuiInputBase-root MuiOutlinedInput-root MuiInputBase-colorPrimary MuiInputBase-fullWidth MuiInputBase-sizeSmall css-1owlt1s']")));
            roleDropdown.click();
            logger.info("Đã click vào dropdown chức vụ");

            // Chọn chức vụ "Tư vấn viên"
            WebElement roleOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//li[contains(text(),'Tư vấn viên')]")));
            roleOption.click();
            logger.info("Đã chọn chức vụ: " + position);

            // Đóng dropdown bằng giả lập nhấn nút ESC
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
            Thread.sleep(500);

            // Bấm nút xác nhận để tạo nhân viên
            WebElement confirmButton = driver.findElement(By.xpath("//button[contains(text(),'Xác nhận')]"));
            confirmButton.click();
            logger.info("Đã bấm nút xác nhận để tạo nhân viên");

            // Đợi thông báo biến mất và form đóng
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]")));
            logger.info("Form thêm nhân viên đã đóng");

            // 2. Kiểm tra nhân viên mới trong danh sách
            logger.info("Bắt đầu kiểm tra nhân viên mới trong danh sách");

            // Đợi trang tải lại sau khi thêm nhân viên
            staffPage.waitForBackdropToDisappear();

            // Kiểm tra tổng số nhân viên sau khi thêm mới
            int newStaffCount = staffPage.getStaffCount();
            Assert.assertEquals(newStaffCount, initialStaffCount + 1,
                    "Tổng số nhân viên không tăng lên sau khi thêm mới");
            logger.info("Số lượng nhân viên sau khi thêm mới: " + newStaffCount + " (tăng 1 so với ban đầu)");

            // Sử dụng hàm tìm kiếm mới để tìm nhân viên vừa tạo
            WebElement staffRow = staffPage.findStaffByDetails(fullName, phoneNumber, department, position);

            // Kiểm tra kết quả tìm kiếm
            Assert.assertNotNull(staffRow, "Không tìm thấy nhân viên mới tạo trong danh sách");
            logger.info("Đã tìm thấy nhân viên mới tạo trong danh sách với thông tin đầy đủ");

        } catch (Exception e) {
            logger.error("Lỗi trong quá trình kiểm tra: " + e.getMessage());
            Assert.fail("Lỗi trong quá trình kiểm tra: " + e.getMessage());
        }

        logger.info("Kết thúc test case TC017: Kiểm tra nhân viên mới vừa được tạo có trong danh sách nhân viên");
    }

}
