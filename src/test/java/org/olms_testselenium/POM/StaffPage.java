package org.olms_testselenium.POM;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object Model for the Staff Management page
 *
 * @author Hoang Ha
 */
public class StaffPage {
    static Logger logger = LogManager.getLogger("StaffPage");
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    @FindBy(xpath = "//h3[contains(text(), 'Danh sách nhân viên')]")
    private WebElement staffListHeader;

    @FindBy(xpath = "//button[contains(.,'Thêm nhân viên')]")
    private WebElement addStaffButton;

    @FindBy(xpath = "//table[@aria-label='table']//tbody/tr")
    private List<WebElement> staffRows;

    @FindBy(xpath = "//div[@role='combobox' and contains(.,'OLMS')]")
    private WebElement locationDropdown;

    /**
     * Constructor khởi tạo StaffPage
     *
     * @param driver WebDriver instance
     */
    public StaffPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    /**
     * Kiểm tra xem có MuiBackdrop-root đang hiển thị không và chờ cho đến khi nó biến mất
     * Method này được để public để có thể được sử dụng bởi các class khác
     */
    public void waitForBackdropToDisappear() {
        try {
            WebElement backdrop = driver.findElement(By.className("MuiBackdrop-root"));
            if (backdrop.isDisplayed() || backdrop.getCssValue("z-index").compareTo("1000") > 0) {
                wait.until(ExpectedConditions.invisibilityOf(backdrop));
            }
        } catch (Exception e) {
            // Backdrop không tồn tại, không cần xử lý
        }
    }

    /**
     * Điều hướng đến trang quản lý nhân viên
     *
     * @return StaffPage instance for method chaining
     */
    public StaffPage navigateToStaffPage() {
        // Click on Settings menu if needed
        try {
            // Kiểm tra và chờ MuiBackdrop biến mất trước khi thao tác
            waitForBackdropToDisappear();

            WebElement settingsMenu = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span[contains(text(),'Cấu hình')]")));
            settingsMenu.click();

            // Kiểm tra và chờ MuiBackdrop biến mất sau khi click menu
            waitForBackdropToDisappear();

            // Click on Staff management
            WebElement staffManagementLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//p[contains(text(),'Quản lý nhân viên')]")));
            staffManagementLink.click();
        } catch (Exception e) {
            // Kiểm tra và chờ MuiBackdrop biến mất trước khi thao tác
            waitForBackdropToDisappear();

            // Nếu menu đã mở, chỉ cần click vào link quản lý nhân viên
            WebElement staffManagementLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//p[contains(text(),'Quản lý nhân viên')]")));
            staffManagementLink.click();
        }

        // Đợi cho đến khi trang được tải hoàn tất
        wait.until(ExpectedConditions.visibilityOf(staffListHeader));
        return this;
    }

    /**
     * Kiểm tra tiêu đề trang có hiển thị chính xác không
     *
     * @return true nếu tiêu đề trang hiển thị chính xác
     */
    public boolean isPageTitleDisplayed() {
        return staffListHeader.isDisplayed() &&
                staffListHeader.getText().equals("Danh sách nhân viên");
    }

    /**
     * Lấy tổng số lượng nhân viên trong hệ thống từ thông tin phân trang
     *
     * @return tổng số lượng nhân viên
     */
    public int getStaffCount() {
        try {
            // Tìm element chứa thông tin phân trang
            WebElement paginationInfo = driver.findElement(By.xpath("//p[contains(@class, 'MuiTablePagination-displayedRows')]"));
            String paginationText = paginationInfo.getText();

            // Trích xuất số lượng bản ghi từ chuỗi "Hiển thị X đến Y của Z bản ghi"
            // Sử dụng regex để lấy số cuối cùng (Z) từ chuỗi
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(\\d+) bản ghi$");
            java.util.regex.Matcher matcher = pattern.matcher(paginationText);

            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            } else {
                // Nếu không tìm thấy pattern, trả về số lượng hàng hiện tại (không chính xác nhưng vẫn có thể sử dụng)
                return staffRows.size();
            }
        } catch (Exception e) {
            // Trong trường hợp có lỗi, trả về số lượng hàng hiện tại
            return staffRows.size();
        }
    }

    /**
     * Click vào nút thêm nhân viên mới
     */
    public void clickAddStaffButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addStaffButton)).click();
    }

    /**
     * Kiểm tra xem form thêm nhân viên có hiển thị không
     * Method này sẽ đợi tối đa 5 giây để theo dõi trạng thái của form
     * Nếu trong 5 giây form đóng lại, sẽ trả về false
     * Nếu sau 5 giây form vẫn hiển thị, sẽ trả về true
     *
     * @return true nếu form thêm nhân viên vẫn hiển thị sau 5 giây, false nếu form đã đóng
     */
    public boolean isAddStaffFormDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            // Đợi tối đa 5 giây để xem form có biến mất không
            // Nếu form biến mất trong 5 giây, invisibilityOfElementLocated sẽ trả về true
            // và phương thức này sẽ trả về false (form không hiển thị)
            boolean isFormGone = wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]")));
            return !isFormGone; // Nếu form biến mất (isFormGone = true), trả về false
        } catch (Exception e) {
            // Timeout sau 5 giây, form vẫn hiển thị
            try {
                // Kiểm tra nhanh lần cuối xem form có thực sự hiển thị không
                WebElement form = driver.findElement(
                        By.xpath("//div[contains(@class, 'MuiDialog-paper') and .//h2[contains(text(),'Thêm nhân viên')]]"));
                return form.isDisplayed();
            } catch (Exception ex) {
                // Không tìm thấy form trong lần kiểm tra cuối cùng
                return false;
            }
        }
    }

    /**
     * Tìm kiếm nhân viên dựa trên các thông tin chi tiết, có xử lý phân trang
     *
     * @param fullName    Tên đầy đủ của nhân viên (Họ và tên đệm + tên)
     * @param phoneNumber Số điện thoại của nhân viên, có thể null nếu không kiểm tra
     * @param department  Phòng ban của nhân viên, có thể null nếu không kiểm tra
     * @param position    Vị trí/chức vụ của nhân viên, có thể null nếu không kiểm tra
     * @return WebElement của dòng chứa nhân viên, hoặc null nếu không tìm thấy
     */
    public WebElement findStaffByDetails(String fullName, String phoneNumber, String department, String position) {
        logger.info("Bắt đầu tìm kiếm nhân viên với thông tin: Tên='" + fullName +
                "', SĐT='" + phoneNumber + "', Phòng ban='" + department +
                "', Vị trí='" + position + "'");

        try {
            // Trước hết, đi đến trang đầu tiên
            WebElement firstPageButton = driver.findElement(By.xpath("//button[@aria-label='first page']"));
            if (firstPageButton.isEnabled()) {
                firstPageButton.click();

                // Đợi cho bảng tải lại
                waitForBackdropToDisappear();
                Thread.sleep(500);

                // Đảm bảo Page Factory cập nhật danh sách staffRows
                PageFactory.initElements(driver, this);
            }

            // Bắt đầu tìm kiếm trên các trang
            boolean hasNextPage = true;
            int pageNumber = 1;

            while (hasNextPage) {
                // Tìm kiếm trên trang hiện tại
                WebElement result = findStaffInCurrentPage(fullName, phoneNumber, department, position);
                if (result != null) {
                    logger.info("Đã tìm thấy nhân viên '" + fullName + "' trên trang " + pageNumber);

                    // Lấy thông tin chi tiết nhân viên đã tìm thấy để hiển thị
                    String foundID = result.findElement(By.xpath("./td[1]")).getText();
                    String foundName = result.findElement(By.xpath("./td[2]")).getText();
                    String foundPhone = result.findElement(By.xpath("./td[3]")).getText();
                    String foundDept = result.findElement(By.xpath("./td[4]")).getText();
                    String foundPosition = result.findElement(By.xpath("./td[5]")).getText();

                    logger.info("Thông tin nhân viên tìm thấy: STT= '" + foundID + "' Tên='" + foundName + "', SĐT='" + foundPhone +
                            "', Phòng ban='" + foundDept + "', Vị trí='" + foundPosition + "'");

                    return result;
                }

                // Nếu không tìm thấy, chuyển đến trang tiếp theo (nếu có)
                WebElement nextPageButton = driver.findElement(By.xpath("//button[@aria-label='next page']"));
                if (nextPageButton.isEnabled()) {
                    nextPageButton.click();
                    pageNumber++;
                    logger.info("Chuyển đến trang " + pageNumber);

                    // Đợi cho bảng tải lại
                    waitForBackdropToDisappear();
                    Thread.sleep(500);

                    // Đảm bảo Page Factory cập nhật danh sách staffRows
                    PageFactory.initElements(driver, this);
                } else {
                    hasNextPage = false;
                    logger.info("Đã đến trang cuối cùng, kết thúc tìm kiếm");
                }
            }

            // Không tìm thấy sau khi đã duyệt qua tất cả các trang
            logger.info("Không tìm thấy nhân viên phù hợp sau khi tìm kiếm qua " + pageNumber + " trang");
            return null;

        } catch (Exception e) {
            logger.error("Lỗi trong quá trình tìm kiếm nhân viên: " + e.getMessage());
            return null;
        }
    }

    /**
     * Tìm kiếm nhân viên trên trang hiện tại của bảng
     *
     * @param fullName    Tên đầy đủ của nhân viên
     * @param phoneNumber Số điện thoại của nhân viên, có thể null
     * @param department  Phòng ban của nhân viên, có thể null
     * @param position    Vị trí/chức vụ của nhân viên, có thể null
     * @return WebElement của dòng chứa nhân viên, hoặc null nếu không tìm thấy
     */
    private WebElement findStaffInCurrentPage(String fullName, String phoneNumber, String department, String position) {
        // Đảm bảo danh sách staffRows đã được cập nhật
        PageFactory.initElements(driver, this);

        for (WebElement row : staffRows) {
            try {
                // Lấy dữ liệu của dòng hiện tại
                WebElement nameCell = row.findElement(By.xpath("./td[2]"));
                String currentName = nameCell.getText();

                // Kiểm tra tên (bắt buộc phải khớp)
                if (!currentName.equals(fullName)) {
                    continue;
                }

                // Nếu cần kiểm tra số điện thoại
                if (phoneNumber != null && !phoneNumber.isEmpty()) {
                    WebElement phoneCell = row.findElement(By.xpath("./td[3]"));
                    String currentPhone = phoneCell.getText();
                    if (!currentPhone.equals(phoneNumber)) {
                        continue;
                    }
                }

                // Nếu cần kiểm tra phòng ban
                if (department != null && !department.isEmpty()) {
                    WebElement deptCell = row.findElement(By.xpath("./td[4]"));
                    String currentDept = deptCell.getText();
                    if (!currentDept.equals(department)) {
                        continue;
                    }
                }

                // Nếu cần kiểm tra vị trí/chức vụ
                if (position != null && !position.isEmpty()) {
                    WebElement positionCell = row.findElement(By.xpath("./td[5]"));
                    String currentPosition = positionCell.getText();
                    if (!currentPosition.equals(position)) {
                        continue;
                    }
                }

                // Nếu đến đây, tất cả các điều kiện đều khớp
                return row;

            } catch (Exception e) {
                // Bỏ qua dòng này nếu có lỗi và tiếp tục với dòng tiếp theo
            }
        }

        // Không tìm thấy nhân viên phù hợp trên trang hiện tại
        return null;
    }

    /**
     * Tìm một nhân viên theo tên
     *
     * @param staffName tên nhân viên cần tìm
     * @return WebElement đại diện cho dòng của nhân viên, hoặc null nếu không tìm thấy
     */
    public WebElement findStaffByName(String staffName) {
        for (WebElement row : staffRows) {
            WebElement nameCell = row.findElement(By.xpath("./td[2]"));
            if (nameCell.getText().equals(staffName)) {
                return row;
            }
        }
        return null;
    }
}
