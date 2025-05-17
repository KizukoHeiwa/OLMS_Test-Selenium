package org.olms_testselenium.Script;

import org.olms_testselenium.POM.ClassPage;
import org.olms_testselenium.POM.NavigationMenu;
import org.olms_testselenium.POM.LoginPage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.olms_testselenium.Listener.ExtentReportListener;
import org.testng.annotations.Test;


import java.time.Duration;

@Listeners(ExtentReportListener.class)
public class CreateClassesTest extends BaseTest {
    static Logger logger = LogManager.getLogger("CreateClassesTest");
    NavigationMenu navigationMenu;
    ClassPage classPage;

    void goToClassPage() {
        navigationMenu.clickMenu("Đào tạo");
        navigationMenu.clickMenuCon("Lớp học");
    }
    void fillBasicInfo(ClassPage classPage, String[] labels, String[] values) {
        for (int i = 0; i < labels.length; i++) {
            classPage.setInputFieldByLabel(labels[i], values[i]);
        }
    }

    void fillClassInfo(ClassPage classPage, String[] labels, String[] values) {
        for (int i = 0; i < labels.length; i++) {
            classPage.setInputFieldByLabel(labels[i], values[i]);
        }
    }

    void setupClassStructure(ClassPage classPage, String scheduleLabel, String date, String time, String teachingTimeLabel, String[] teachingHours) {
        classPage.clickClassScheduleBtn();
        classPage.scrollDowṇ();
        classPage.setClassSchedule(scheduleLabel, date, time);
        classPage.setTeachingHours(teachingTimeLabel, teachingHours);
    }

    private void turnOffImplicitWaits() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    }

    private void turnOnImplicitWaits() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    }

    @BeforeMethod
    void setup(ITestContext context) {
        super.setup(context);
        LoginPage login = new LoginPage(driver);
        logger.warn("Sign in with accout admin...");
        login.login("testadmin", "test1234");

        navigationMenu = new NavigationMenu(driver);
        classPage = new ClassPage(driver);
    }

    @Test
    void leavingAllFieldsEmpty() {
        logger.info("Test: leavingAllFieldsEmpty - Start");
        String createBtn = "Tạo mới";
        String saveBtn = "Lưu";
        logger.info("Navigating to class page");
        goToClassPage();

        classPage.clickBtnByText(createBtn);
        classPage.clickBtnByText(saveBtn);
        logger.info("Checking for error popup");
        classPage.checkPopupError();
    }

    @Test
    void createClassFunctionValidData() {
        logger.info("Test: createClassFunctionValidData - Start");
        String[] field = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu", "Khoá học"};
        String[] data = {"Ielts", "16", "13/05/2025", "Online"};
        String[] classField = {"Phòng học", "Giáo viên VN", "Giáo viên nước ngoài", "Trợ giảng", "Người nhận xét"};
        String[] classData = {"Phòng 1 - Tầng 2", "Nguyễn Hằng", "Nguyệt Moon Mo", "Minh Châu Julie", "Nguyễn Thu Phương Rose"};
        String createBtn="Tạo mới"; String saveBtn="Lưu";
        String dateValue="Thứ 2";
        String label="Thời gian đứng lớp"; String scheduleLabel="Lịch học"; String time="17:30-19:00";
        String endDateLabel="Ngày hiệu lực";
        String startDate = "13/05/2025", endDate = "13/05/2026";

        classPage.setEffectiveDates(label, dateValue);
        logger.info("Đi tới trang lớp học và nhấn nút tạo mới");
        goToClassPage();
        classPage.clickBtnByText(createBtn);
        ClassPage classPage = new ClassPage(driver);
        logger.info("Điền thông tin cơ bản lớp học");
        fillBasicInfo(classPage, field, data);
        setupClassStructure(classPage, scheduleLabel, dateValue, time, label,
                new String[]{"17:30-18:00", "18:00-18:30", "18:30-19:00"});
        logger.info("Điền thông tin chi tiết lớp học");
        fillClassInfo(classPage, classField, classData);
        logger.info("Nhập ngày hiệu lực lớp học");
        classPage.setEffectiveDates(endDateLabel, endDate);
        logger.info("Nhấn nút lưu");
        classPage.clickBtnByText(saveBtn);
        logger.info("Test: createClassFunctionValidData - Kết thúc");
    }


    @Test
    void leavingScheduleFieldEmpty() {
        logger.info("Test: leavingScheduleFieldEmpty - Start");
        String[] field = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu", "Khoá học"};
        String[] data = {"Ielts", "16", "13/05/2025", "Online"};
        String createBtn="Tạo mới"; String saveBtn="Lưu";

        logger.info("Đi tới trang lớp học và nhấn nút tạo mới");
        goToClassPage();
        classPage.clickBtnByText(createBtn);
        ClassPage classPage = new ClassPage(driver);

        logger.info("Điền thông tin cơ bản lớp học");
        fillBasicInfo(classPage, field, data);

        logger.info("Không thiết lập lịch học, kiểm tra lỗi");
        classPage.clickBtnByText(saveBtn);
        classPage.checkPopupError();
        logger.info("Test: leavingScheduleFieldEmpty - Kết thúc");
    }

    @Test
    void leavingClassNameEmpty() {
        logger.info("Test: leavingClassNameEmpty - Start");
        String[] field = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu", "Khoá học"};
        String[] data = {"", "16", "13/05/2025", "Online"};
        String[] classField = {"Phòng học", "Giáo viên VN", "Giáo viên nước ngoài", "Trợ giảng", "Người nhận xét"};
        String[] classData = {"Phòng 1 - Tầng 2", "Nguyễn Hằng", "Nguyệt Moon Mo", "Minh Châu Julie", "Nguyễn Thu Phương Rose"};
        String createBtn="Tạo mới"; String saveBtn="Lưu";
        String dateValue="Thứ 2";
        String label="Thời gian đứng lớp"; String scheduleLabel="Lịch học"; String time="17:30-19:00";
        String endDateLabel="Ngày hiệu lực";
        String startDate = "13/05/2025", endDate = "13/05/2026";

        classPage.setEffectiveDates(label, dateValue);

        logger.info("Đi tới trang lớp học và nhấn nút tạo mới");
        goToClassPage();
        classPage.clickBtnByText(createBtn);
        ClassPage classPage = new ClassPage(driver);

        logger.info("Điền thông tin cơ bản (thiếu tên lớp)");
        fillBasicInfo(classPage, field, data);
        setupClassStructure(classPage, scheduleLabel, dateValue, time, label,
                new String[]{"17:30-18:00", "18:00-18:30", "18:30-19:00"});
        logger.info("Điền thông tin chi tiết lớp học");
        fillClassInfo(classPage, classField, classData);
        logger.info("Nhập ngày hiệu lực lớp học");
        classPage.setEffectiveDates(endDateLabel, endDate);

        logger.info("Nhấn nút lưu");
        classPage.clickBtnByText(saveBtn);
        logger.info("Kiểm tra popup lỗi do tên lớp để trống");
        classPage.checkPopupError();
        logger.info("Test: leavingClassNameEmpty - Kết thúc");
    }

    @Test
    void leavingAgeFieldEmpty() {
        logger.info("Test: leavingAgeFieldEmpty - Start");
        String[] field = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu", "Khoá học"};
        String[] data = {"Ielts", "", "13/05/2025", "Online"};
        String[] classField = {"Phòng học", "Giáo viên VN", "Giáo viên nước ngoài", "Trợ giảng", "Người nhận xét"};
        String[] classData = {"Phòng 1 - Tầng 2", "Nguyễn Hằng", "Nguyệt Moon Mo", "Minh Châu Julie", "Nguyễn Thu Phương Rose"};
        String createBtn="Tạo mới"; String saveBtn="Lưu";
        String dateValue="Thứ 2";
        String label="Thời gian đứng lớp"; String scheduleLabel="Lịch học"; String time="17:30-19:00";
        String endDateLabel="Ngày hiệu lực";
        String startDate = "13/05/2025", endDate = "13/05/2026";

        classPage.setEffectiveDates(label, dateValue);
        logger.info("Đi tới trang lớp học và nhấn nút tạo mới");
        goToClassPage();
        classPage.clickBtnByText(createBtn);
        ClassPage classPage = new ClassPage(driver);

        logger.info("Điền thông tin cơ bản lớp học (thiếu Độ tuổi)");
        fillBasicInfo(classPage, field, data);
        logger.info("Thiết lập cấu trúc lớp học");
        setupClassStructure(classPage, scheduleLabel, dateValue, time, label,
                new String[]{"17:30-18:00", "18:00-18:30", "18:30-19:00"});

        logger.info("Điền thông tin chi tiết lớp học");
        fillClassInfo(classPage, classField, classData);
        logger.info("Đặt ngày hiệu lực lớp học");
        classPage.setEffectiveDates(endDateLabel, endDate);

        logger.info("Nhấn nút Lưu");
        classPage.clickBtnByText(saveBtn);
        logger.info("Kiểm tra popup lỗi do để trống trường Độ tuổi");
        classPage.checkPopupError();
        logger.info("Test: leavingAgeFieldEmpty - Kết thúc");
    }
    @Test
    void leavingStartingDateFieldEmpty() {
        logger.info("Test: leavingStartingDateFieldEmpty - Start");
        String[] field = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu", "Khoá học"};
        String[] data = {"Ielts", "16", "", "Online"};
        String[] classField = {"Phòng học", "Giáo viên VN", "Giáo viên nước ngoài", "Trợ giảng", "Người nhận xét"};
        String[] classData = {"Phòng 1 - Tầng 2", "Nguyễn Hằng", "Nguyệt Moon Mo", "Minh Châu Julie", "Nguyễn Thu Phương Rose"};
        String createBtn="Tạo mới"; String saveBtn="Lưu";
        String dateValue="Thứ 2";
        String label="Thời gian đứng lớp"; String scheduleLabel="Lịch học"; String time="17:30-19:00";
        String endDateLabel="Ngày hiệu lực";
        String startDate = "13/05/2025", endDate = "13/05/2026";

        logger.info("Đặt ngày bắt đầu thời gian đứng lớp");
        classPage.setEffectiveDates(label, dateValue);
        logger.info("Đi tới trang lớp học và nhấn nút tạo mới");
        goToClassPage();
        classPage.clickBtnByText(createBtn);
        ClassPage classPage = new ClassPage(driver);

        logger.info("Điền thông tin cơ bản lớp học (thiếu Ngày bắt đầu)");
        fillBasicInfo(classPage, field, data);
        setupClassStructure(classPage, scheduleLabel, dateValue, time, label,
                new String[]{"17:30-18:00", "18:00-18:30", "18:30-19:00"});
        logger.info("Điền thông tin chi tiết lớp học");
        fillClassInfo(classPage, classField, classData);
        logger.info("Đặt ngày hiệu lực lớp học");
        classPage.setEffectiveDates(endDateLabel, endDate);

        logger.info("Nhấn nút Lưu");
        classPage.clickBtnByText(saveBtn);
        logger.info("Kiểm tra popup lỗi do để trống trường Ngày bắt đầu");
        classPage.checkPopupError();
        logger.info("Test: leavingStartingDateFieldEmpty - Kết thúc");
    }
    @Test
    void leavingCourseFieldEmpty() {
        logger.info("Test: leavingCourseFieldEmpty - Start");
        String[] field = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu"};
        String[] data = {"Ielts", "16", "13/05/2025"};
        String[] classField = {"Phòng học", "Giáo viên VN", "Giáo viên nước ngoài", "Trợ giảng", "Người nhận xét"};
        String[] classData = {"Phòng 1 - Tầng 2", "Nguyễn Hằng", "Nguyệt Moon Mo", "Minh Châu Julie", "Nguyễn Thu Phương Rose"};
        String createBtn="Tạo mới"; String saveBtn="Lưu";
        String dateValue="Thứ 2";
        String label="Thời gian đứng lớp"; String scheduleLabel="Lịch học"; String time="17:30-19:00";
        String endDateLabel="Ngày hiệu lực";
        String startDate = "13/05/2025", endDate = "13/05/2026";

        classPage.setEffectiveDates(label, dateValue);
        logger.info("Đi tới trang lớp học và nhấn nút tạo mới");
        goToClassPage();
        classPage.clickBtnByText(createBtn);
        ClassPage classPage = new ClassPage(driver);

        logger.info("Điền thông tin cơ bản lớp học (thiếu Khoá học)");
        fillBasicInfo(classPage, field, data);

        logger.info("Thiết lập cấu trúc lớp học");
        setupClassStructure(classPage, scheduleLabel, dateValue, time, label,
                new String[]{"17:30-18:00", "18:00-18:30", "18:30-19:00"});

        logger.info("Điền thông tin chi tiết lớp học");
        fillClassInfo(classPage, classField, classData);
        logger.info("Đặt ngày hiệu lực lớp học");
        classPage.setEffectiveDates(endDateLabel, endDate);

        logger.info("Nhấn nút Lưu");
        classPage.clickBtnByText(saveBtn);
        logger.info("Kiểm tra popup lỗi do để trống trường Khoá học");
        classPage.checkPopupError();
        logger.info("Test: leavingCourseFieldEmpty - Kết thúc");
    }
    @Test
    void leavingClassroomFieldEmpty() {
        String[] field = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu", "Khoá học"};
        String[] data = {"Ielts", "16", "13/05/2025", "Online"};
        String[] classField = {"Giáo viên VN", "Giáo viên nước ngoài", "Trợ giảng", "Người nhận xét"};
        String[] classData = {"Nguyễn Hằng", "Nguyệt Moon Mo", "Minh Châu Julie", "Nguyễn Thu Phương Rose"};

        String startDate = "13/05/2025", endDate = "13/05/2026";

        logger.info("Đặt ngày bắt đầu thời gian đứng lớp");
        classPage.setEffectiveDates("Thời gian đứng lớp", "Thứ 2");
        logger.info("Đi tới trang lớp học và nhấn nút tạo mới");
        goToClassPage();
        classPage.clickBtnByText("Tạo mới");
        ClassPage classPage = new ClassPage(driver);

        logger.info("Điền thông tin cơ bản lớp học");
        fillBasicInfo(classPage, field, data);
        logger.info("Thiết lập cấu trúc lớp học");
        setupClassStructure(classPage, "Lịch học", "Thứ 2", "17:30-19:00", "Thời gian đứng lớp",
                new String[]{"17:30-18:00", "18:00-18:30", "18:30-19:00"});
        logger.info("Điền thông tin chi tiết lớp học (thiếu Phòng học)");
        fillClassInfo(classPage, classField, classData);
        logger.info("Đặt ngày hiệu lực lớp học");
        classPage.setEffectiveDates("Ngày hiệu lực", endDate);

        logger.info("Nhấn nút Lưu");
        classPage.clickBtnByText("Lưu");
        classPage.checkPopupError();
    }
    @Test
    void leavingTeacherFieldEmpty() {
        logger.info("Test: leavingTeacherFieldEmpty - Start");
        String[] field = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu", "Khoá học"};
        String[] data = {"Ielts", "16", "13/05/2025", "Online"};
        String[] classField = {"Phòng học"};
        String[] classData = {"Phòng 1 - Tầng 2"};
        String createBtn="Tạo mới"; String saveBtn="Lưu";
        String dateValue="Thứ 2";
        String label="Thời gian đứng lớp"; String scheduleLabel="Lịch học"; String time="17:30-19:00";
        String endDateLabel="Ngày hiệu lực";
        String startDate = "13/05/2025", endDate = "13/05/2026";

        classPage.setEffectiveDates(label, dateValue);
        logger.info("Đi tới trang lớp học và nhấn nút tạo mới");
        goToClassPage();
        classPage.clickBtnByText(createBtn);
        ClassPage classPage = new ClassPage(driver);

        logger.info("Nhập thông tin cơ bản lớp học");
        fillBasicInfo(classPage, field, data);
        logger.info("Thiết lập cấu trúc lớp học");
        setupClassStructure(classPage, scheduleLabel, dateValue, time, label,
                new String[]{"17:30-18:00", "18:00-18:30", "18:30-19:00"});
        logger.info("Nhập thông tin lớp học (thiếu giáo viên)");
        fillClassInfo(classPage, classField, classData);
        classPage.setEffectiveDates(endDateLabel, endDate);

        logger.info("Click button 'Lưu'");
        classPage.clickBtnByText(saveBtn);
        logger.info("Kiểm tra popup lỗi khi thiếu giáo viên");
        classPage.checkPopupError();
        logger.info("Test: leavingTeacherFieldEmpty - Kết thúc");

    }


    @Test
    void createClassFunctionWithInvalidAgeData() {
        logger.info("Test: createClassFunctionWithInvalidAgeData - Start");
        String[] field = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu", "Khoá học"};
        String[] data = {"Ielts", "!@#$", "13/05/2025", "Online"};
        String[] classField = {"Phòng học", "Giáo viên VN", "Giáo viên nước ngoài", "Trợ giảng", "Người nhận xét"};
        String[] classData = {"Phòng 1 - Tầng 2", "Nguyễn Hằng", "Nguyệt Moon Mo", "Minh Châu Julie", "Nguyễn Thu Phương Rose"};
        String createBtn="Tạo mới"; String saveBtn="Lưu";
        String dateValue="Thứ 2";
        String label="Thời gian đứng lớp"; String scheduleLabel="Lịch học"; String time="17:30-19:00";
        String endDateLabel="Ngày hiệu lực";
        String startDate = "13/05/2025", endDate = "13/05/2026";

        classPage.setEffectiveDates(label, dateValue);
        goToClassPage();
        classPage.clickBtnByText(createBtn);
        ClassPage classPage = new ClassPage(driver);

        fillBasicInfo(classPage, field, data);
        setupClassStructure(classPage, scheduleLabel, dateValue, time, label,
                new String[]{"17:30-18:00", "18:00-18:30", "18:30-19:00"});
        fillClassInfo(classPage, classField, classData);
        classPage.setEffectiveDates(endDateLabel, endDate);

        classPage.clickBtnByText(saveBtn);
        logger.info("Kiểm tra popup lỗi do dữ liệu độ tuổi không hợp lệ");
        classPage.checkPopupError();
        logger.info("Test: createClassFunctionWithInvalidAgeData - Kết thúc");
    }
    @Test
    void startDateEqualToEndDate() {
        logger.info("Test: startDateEqualToEndDate - Start");
        String[] field = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu", "Khoá học"};
        String[] data = {"Ielts", "16", "13/05/2025", "Online"};
        String[] classField = {"Phòng học", "Giáo viên VN", "Giáo viên nước ngoài", "Trợ giảng", "Người nhận xét"};
        String[] classData = {"Phòng 1 - Tầng 2", "Nguyễn Hằng", "Nguyệt Moon Mo", "Minh Châu Julie", "Nguyễn Thu Phương Rose"};
        String createBtn="Tạo mới"; String saveBtn="Lưu";
        String dateValue="Thứ 2";
        String label="Thời gian đứng lớp"; String scheduleLabel="Lịch học"; String time="17:30-19:00";
        String endDateLabel="Ngày hiệu lực";
        String startDate = "13/05/2025", endDate = "13/05/2025";

        classPage.setEffectiveDates(label, dateValue);
        goToClassPage();
        classPage.clickBtnByText(createBtn);
        ClassPage classPage = new ClassPage(driver);

        fillBasicInfo(classPage, field, data);
        setupClassStructure(classPage, scheduleLabel, dateValue, time, label,
                new String[]{"17:30-18:00", "18:00-18:30", "18:30-19:00"});
        fillClassInfo(classPage, classField, classData);
        classPage.setEffectiveDates(endDateLabel, endDate);

        classPage.clickBtnByText(saveBtn);
        logger.info("Kiểm tra popup lỗi với ngày bắt đầu bằng ngày kết thúc");
        classPage.checkPopupError();
        logger.info("Test: startDateEqualToEndDate - Kết thúc");
    }
    @Test
    public void testDefaultStatusIsDangHoc() {
        logger.info("Test: testDefaultStatusIsDangHoc - Start");
        String createBtn = "Tạo mới", saveBtn = "Lưu";
        String expectedStatus = "Đang học";
        logger.info("Đi tới trang lớp học và nhấn nút tạo mới");
        goToClassPage();

        classPage.clickBtnByText(createBtn);
        classPage.scrollDowṇ();
        logger.info("Lấy giá trị trạng thái mặc định");
        String actualStatus = classPage.getDefaultStatus();

        logger.info("So sánh trạng thái mặc định với giá trị mong đợi");
        Assert.assertEquals(actualStatus, expectedStatus, "Trạng thái mặc định không đúng!");
        logger.info("Test: testDefaultStatusIsDangHoc - Kết thúc");
    }
    @Test
    void testStatusCanChange1() {
        logger.info("Test: testStatusCanChange1 - Start");
        String[] field = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu", "Khoá học"};
        String[] data = {"Ielts", "16", "13/05/2025", "Online"};
        String[] classField = {"Phòng học", "Giáo viên VN", "Giáo viên nước ngoài", "Trợ giảng", "Người nhận xét"};
        String[] classData = {"Phòng 1 - Tầng 2", "Nguyễn Hằng", "Nguyệt Moon Mo", "Minh Châu Julie", "Nguyễn Thu Phương Rose"};
        String createBtn="Tạo mới"; String saveBtn="Lưu";
        String dateValue="Thứ 2";
        String label="Thời gian đứng lớp"; String scheduleLabel="Lịch học"; String time="17:30-19:00";
        String endDateLabel="Ngày hiệu lực";
        String startDate = "13/05/2025", endDate = "13/05/2026";
        String status="Tạm dừng";

        classPage.setEffectiveDates(label, dateValue);
        goToClassPage();
        classPage.clickBtnByText(createBtn);
        ClassPage classPage = new ClassPage(driver);

        fillBasicInfo(classPage, field, data);
        setupClassStructure(classPage, scheduleLabel, dateValue, time, label,
                new String[]{"17:30-18:00", "18:00-18:30", "18:30-19:00"});
        fillClassInfo(classPage, classField, classData);
        classPage.setEffectiveDates(endDateLabel, endDate);
        logger.info("Thay đổi trạng thái lớp học thành 'Tạm dừng'");
        classPage.selectStatus(status);
        logger.info("Click button 'Lưu'");
        classPage.clickBtnByText(saveBtn);
        logger.info("Test: testStatusCanChange1 - Kết thúc");
    }
    @Test
    void testStatusCanChange2() {
        logger.info("Test: testStatusCanChange2 - Start");
        String[] field = {"Tên lớp học", "Độ tuổi", "Ngày bắt đầu", "Khoá học"};
        String[] data = {"Ielts", "16", "13/05/2025", "Online"};
        String[] classField = {"Phòng học", "Giáo viên VN", "Giáo viên nước ngoài", "Trợ giảng", "Người nhận xét"};
        String[] classData = {"Phòng 1 - Tầng 2", "Nguyễn Hằng", "Nguyệt Moon Mo", "Minh Châu Julie", "Nguyễn Thu Phương Rose"};
        String createBtn="Tạo mới"; String saveBtn="Lưu";
        String dateValue="Thứ 2";
        String label="Thời gian đứng lớp"; String scheduleLabel="Lịch học"; String time="17:30-19:00";
        String endDateLabel="Ngày hiệu lực";
        String startDate = "13/05/2025", endDate = "13/05/2026";
        String status="Kết thúc";

        classPage.setEffectiveDates(label, dateValue);
        goToClassPage();
        classPage.clickBtnByText(createBtn);
        ClassPage classPage = new ClassPage(driver);

        fillBasicInfo(classPage, field, data);
        setupClassStructure(classPage, scheduleLabel, dateValue, time, label,
                new String[]{"17:30-18:00", "18:00-18:30", "18:30-19:00"});
        fillClassInfo(classPage, classField, classData);
        classPage.setEffectiveDates(endDateLabel, endDate);
        logger.info("Thay đổi trạng thái lớp học thành 'Kết thúc'");
        classPage.selectStatus(status);
        logger.info("Click button 'Lưu'");
        classPage.clickBtnByText(saveBtn);
        logger.info("Test: testStatusCanChange2 - Kết thúc");
    }

}


