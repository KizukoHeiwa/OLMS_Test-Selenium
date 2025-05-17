package org.olms_testselenium.Listener;

import org.olms_testselenium.Utils.ExtentManager;
import com.aventstack.extentreports.*;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.testng.*;

import java.io.File;
import java.io.IOException;

import org.olms_testselenium.Script.BaseTest;

public class ExtentReportListener implements ITestListener {
    public static ExtentReports extent = ExtentManager.createInstance();
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test Passed");
    }

    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "Test Failed: " + result.getThrowable());

        Object currentClass = result.getInstance();
        WebDriver driver = ((BaseTest) currentClass).getDriver();

        String screenshotPath = "./screenshots/" + result.getMethod().getMethodName() + ".png";

        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            new File("./screenshots").mkdirs(); // Đảm bảo folder tồn tại
            FileHandler.copy(srcFile, new File(screenshotPath));
            test.get().addScreenCaptureFromPath(screenshotPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test Skipped");
    }

    public void onFinish(ITestContext context) {
        extent.flush();
    }

    public static ExtentTest getTest() {
        return test.get();
    }
}
