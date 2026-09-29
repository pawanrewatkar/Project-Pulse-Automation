package com.pulse.automation.listeners;

import com.pulse.automation.utils.EmailUtil;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.pulse.automation.base.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestReportListener implements ITestListener {

    private ExtentReports extent;
    private ExtentTest test;

    @Override
    public void onStart(org.testng.ITestContext context) {

        ExtentSparkReporter sparkReporter =
                new ExtentSparkReporter("test-output/ProjectPulse_TestReport.html");

        extent = new ExtentReports();

        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Project", "Project Pulse");
        extent.setSystemInfo("Tester", "Pawan");
        extent.setSystemInfo("Environment", "Test");
    }

    @Override
    public void onTestStart(ITestResult result) {

        test = extent.createTest(result.getName());

        test.info("Test Started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        test.pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        test.fail("Test Failed");
        test.fail(result.getThrowable());

        try {

            BaseTest baseTest = (BaseTest) result.getInstance();

            TakesScreenshot screenshot =
                    (TakesScreenshot) baseTest.driver;

            String screenshotPath =
                    "test-output/screenshots/" + result.getName() + ".png";

            java.io.File source =
                    screenshot.getScreenshotAs(OutputType.FILE);

            java.io.File destination =
                    new java.io.File(screenshotPath);

            destination.getParentFile().mkdirs();

            java.nio.file.Files.copy(
                    source.toPath(),
                    destination.toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            test.addScreenCaptureFromPath(screenshotPath);

        } catch (Exception e) {

            test.warning("Unable to capture screenshot: "
                    + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        test.skip("Test Skipped");
    }

    @Override
    public void onFinish(org.testng.ITestContext context) {

        extent.flush();

        try {

            String reportPath =
                    "test-output/ProjectPulse_TestReport.html";

            EmailUtil.sendReport(
                    reportPath,
                    "Project Pulse Automation Test Report");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}