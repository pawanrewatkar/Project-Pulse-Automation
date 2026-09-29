package com.pulse.automation.tests;

import com.pulse.automation.base.BaseTest;
import com.pulse.automation.listeners.TestReportListener;
import com.pulse.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestReportListener.class)
public class LoginTest extends BaseTest {

    @Test
    public void verifyGoogleTitle() {

        LoginPage googlePage = new LoginPage(driver);

        String title = googlePage.getPageTitle();

        System.out.println("Page Title: " + title);

        Assert.assertEquals(title, "Project Pulse — Internal Work Platform");
    }
}