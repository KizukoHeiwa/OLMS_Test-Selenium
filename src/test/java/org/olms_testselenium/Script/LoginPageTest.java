package org.olms_testselenium.Script;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.olms_testselenium.POM.LoginPage;
import org.olms_testselenium.Utils.ExcelHelper;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

//@Listeners(SimpleListener.class)
public class LoginPageTest extends BaseTest {
    static Logger logger = LogManager.getLogger("LoginPageTest");

    @DataProvider(name = "users")
    public Object[][] readUser() {
        return ExcelHelper.getExcelData("src/main/resources/OLMS_TestData.xlsx", "Users");
    }

    @Test(dataProvider = "users")
    void login(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);

        logger.info("{} - {}", username, password);
        logger.info("Insert username and password");
        loginPage.login(username, password);
        Assert.assertNotNull(driver.findElement(By.xpath("//h2[contains(text(), 'OLMS Admin')]")).getText());
        logger.info("Login successfully");
    }
}