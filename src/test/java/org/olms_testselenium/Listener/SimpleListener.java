package org.olms_testselenium.Listener;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleListener implements ITestListener {
    public void onTestStart(org.testng.ITestResult result) { /* compiled code */ }

    public void onTestSuccess(org.testng.ITestResult result) { /* compiled code */ }

    public void onTestFailure(org.testng.ITestResult result) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmm");
        String formattedDate = formatter.format(date);

        System.out.println("Screenshot captured for test case: " + formattedDate + "-" + result.getTestClass().getName() + "." + result.getName());

        WebDriver driver = (WebDriver) result.getTestContext().getAttribute("driver");

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileHandler.copy(srcFile, new File(".\\screenshots\\" + formattedDate + "-" + result.getTestClass().getName() + "." + result.getName() + ".png"));
            System.out.println("Screenshot captured for test case: " + formattedDate + "-" + result.getTestClass().getName() + "." + result.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTestSkipped(org.testng.ITestResult result) { /* compiled code */ }

    public void onTestFailedButWithinSuccessPercentage(org.testng.ITestResult result) { /* compiled code */ }

    public void onTestFailedWithTimeout(org.testng.ITestResult result) { /* compiled code */ }

    public void onStart(org.testng.ITestContext context) { /* compiled code */ }

    public void onFinish(org.testng.ITestContext context) { /* compiled code */ }

}
