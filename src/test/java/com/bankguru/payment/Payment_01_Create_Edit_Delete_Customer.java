package com.bankguru.payment;

import commons.BaseTest;
import envConfig.Environment;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.*;
import utilities.DataUtil;

public class Payment_01_Create_Edit_Delete_Customer extends BaseTest {
    @Parameters({ "browser", "env" })
    @BeforeClass
    public void initBrowser(String browserName, String envName) {
        ConfigFactory.setProperty("env", envName);
        environment = ConfigFactory.create(Environment.class);
        fakeData = DataUtil.getData();

        emailAddress = fakeData.getEmailAddress();
        cusName = fakeData.getFullName();
        gender = "male";
        dob = "1972-08-10";
        address = fakeData.getAddress();
        city = fakeData.getCityName();
        state = fakeData.getStateName();
        pin = fakeData.getPinNumber();
        mobile = fakeData.getPhoneNumber();

        log.info("Pre-condition - Step 01: Open browser '" + browserName + "' and navigate to '" + environment.appUrl() + "'");
        driver = getBrowserDriver(browserName, environment.appUrl());

        log.info("Pre-condition - Step 02: Verify login page displayed");
        loginPage = PageGeneratorManager.getLoginPage(driver);
        verifyTrue(loginPage.isLoginPageDisplayed());

        log.info("Pre-condition - Step 03: Click to 'here' link at Login page");
        loginPage.clickToHereLink();

        log.info("Pre-condition - Step 04: Input to Email ID textbox at Access details page with value: " + emailAddress);
        loginPage.inputToTextboxByName(driver, "emailid", emailAddress);

        log.info("Pre-condition - Step 05: Click to 'Submit' button at Access details page");
        loginPage.clickToButtonByName(driver, "Submit");

        log.info("Pre-condition - Step 06: Verify Access details displayed");
        verifyTrue(loginPage.isAccessDetailsTitleDisplayed());
        userID = loginPage.getTextValueByLabelAtTable("User ID");
        password = loginPage.getTextValueByLabelAtTable("Password");

        log.info("Pre-condition - Step 07: Open Login page");
        loginPage.openUrl(driver, environment.appUrl());

        log.info("Pre-condition - Step 08: Input to UserID textbox at Login page with value: " + userID);
        loginPage.inputToTextboxByName(driver, "uid", userID);

        log.info("Pre-condition - Step 09: Input to Password textbox at Login page with value: " + password);
        loginPage.inputToTextboxByName(driver, "password", password);

        log.info("Pre-condition - Step 10: Click to 'Login' button");
        loginPage.clickToButtonByName(driver, "LOGIN");
        managerHomePage = PageGeneratorManager.getManagerHomePage(driver);

        log.info("Pre-condition - Step 11: Verify Manager Homepage displayed");
        verifyEquals(managerHomePage.getWelcomeText(), "Welcome To Manager's Page of Guru99 Bank");
        verifyTrue(managerHomePage.getMangerID().contains(userID));
    }

    @Test
    public void Payment_01_Create_New_Customer() {
        log.info("Create_Cus_01 - Step 01: Open New Customer page");
        log.info("Create_Cus_01 - Step 02: Verify New Customer displayed");
        log.info("Create_Cus_01 - Step 03: Input to Customer Name texbox with value: " + cusName);
        log.info("Create_Cus_01 - Step 04: Click to Gender radio button with value: " + gender);
        log.info("Create_Cus_01 - Step 05: Input to Date of Birth textbox with value: " + dob);
        log.info("Create_Cus_01 - Step 06: Input to Address texbox with value: " + address);
        log.info("Create_Cus_01 - Step 07: Input to City texbox with value: " + city);
        log.info("Create_Cus_01 - Step 08: Input to State texbox with value: " + state);
        log.info("Create_Cus_01 - Step 09: Input to Pin texbox with value: " + pin);
        log.info("Create_Cus_01 - Step 10: Input to Mobile Number texbox with value: " + mobile);
        log.info("Create_Cus_01 - Step 11: Input to Email texbox with value: " + emailAddress);
        log.info("Create_Cus_01 - Step 12: Input to Password texbox with value: " + password);
        log.info("Create_Cus_01 - Step 13: Click to 'Submit' button");
        log.info("Create_Cus_01 - Step 14: Verify success message displayed");
        log.info("Create_Cus_01 - Step 13: Verify registered customer info displayed");
    }

    @Test
    public void Payment_02_Edit_Customer() {

    }

    @Test
    public void Payment_03_Delete_Customer() {

    }

    @AfterClass(alwaysRun = true)
    public void cleanBrowser() {
        closeBrowserAndDriver();
    }

    WebDriver driver;
    DataUtil fakeData;
    Environment environment;
    LoginPO loginPage;
    ManagerHomePO managerHomePage;
    NewCustomerPO newCustomerPage;
    EditCustomerPO editCustomerPage;
    DeleteCustomerPO deleteCustomerPage;
    String emailAddress, userID, cusName, gender, dob, address, city, state, pin, mobile, password;
}
