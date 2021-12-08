package com.bankguru.customer;

import commons.BaseTest;
import factoryEnvironment.EnvConfig;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pageObjects.*;
import utilities.DataUtil;

public class New_Cus_04_Create_New_Customer extends BaseTest {
    @Parameters({"envName", "serverName", "browser", "ipAddress", "port", "osName", "osVersion"})
    @BeforeClass
    public void initBrowser(@Optional("local") String envName, @Optional("dev") String serverName, @Optional("chrome") String browserName, @Optional("localhost") String ipAddress, @Optional("4444") String portNumber, @Optional("Mac OS X") String osName, @Optional("10.16") String osVersion) {
        ConfigFactory.setProperty("env", serverName);
        envConfig = ConfigFactory.create(EnvConfig.class);
        fakeData = DataUtil.getData();

        emailAddress = fakeData.getEmailAddress();
        cusName = fakeData.getFullName();
        gender = "female";
        birthDate = "25";
        birthMonth = "11";
        birthYear = "1995";
        address = fakeData.getAddress();
        city = fakeData.getCityName();
        state = fakeData.getStateName();
        pin = fakeData.getPinNumber();
        mobile = fakeData.getPhoneNumber();

        log.info("Pre-condition - Step 01: Open browser '" + browserName + "' and navigate to '" + envConfig.appUrl() + "'");
        driver = getBrowserDriver(envName, envConfig.appUrl(), browserName, ipAddress, portNumber, osName, osVersion);

        log.info("Pre-condition - Step 02: Verify Login page displayed");
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
        userID = loginPage.getTextValueByLabelAtTable(driver, "User ID");
        password = loginPage.getTextValueByLabelAtTable(driver, "Password");

        log.info("Pre-condition - Step 07: Open Login page");
        loginPage.openUrl(driver, envConfig.appUrl());

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
    public void Cus_01_Create_New_Customer() {
        log.info("Create_Cus_01 - Step 01: Open New Customer page");
        managerHomePage.openSidebarMenuByPageName(driver, "New Customer");
        newCustomerPage = PageGeneratorManager.getNewCustomerPage(driver);

        log.info("Create_Cus_01 - Step 02: Verify New Customer page displayed");
        verifyTrue(newCustomerPage.isPageTitleByTextDisplayed(driver, "Add New Customer"));

        log.info("Create_Cus_01 - Step 03: Input to Customer Name texbox with value: " + cusName);
        newCustomerPage.sleepInSecond(1);
        newCustomerPage.inputToCustomerTextboxByName("name", cusName);

        log.info("Create_Cus_01 - Step 04: Click to Gender radio button with value: " + gender);
        newCustomerPage.checkToRadioButtonByValue(driver, gender.substring(0, 1));

        log.info("Create_Cus_01 - Step 05: Input to Date of Birth textbox");
        newCustomerPage.inputBirthDateTextbox(birthDate, birthMonth, birthYear);

        log.info("Create_Cus_01 - Step 06: Input to Address text area with value: " + address);
        newCustomerPage.inputToTextAreaByName(driver, "addr", address);

        log.info("Create_Cus_01 - Step 07: Input to City texbox with value: " + city);
        newCustomerPage.inputToCustomerTextboxByName("city", city);

        log.info("Create_Cus_01 - Step 08: Input to State texbox with value: " + state);
        newCustomerPage.inputToCustomerTextboxByName("state", state);

        log.info("Create_Cus_01 - Step 09: Input to Pin texbox with value: " + pin);
        newCustomerPage.inputToCustomerTextboxByName("pinno", pin);

        log.info("Create_Cus_01 - Step 10: Input to Mobile Number texbox with value: " + mobile);
        newCustomerPage.inputToCustomerTextboxByName("telephoneno", mobile);

        log.info("Create_Cus_01 - Step 11: Input to Email texbox with value: " + emailAddress);
        newCustomerPage.inputToCustomerTextboxByName("emailid", emailAddress);

        log.info("Create_Cus_01 - Step 12: Input to Password texbox with value: " + password);
        newCustomerPage.inputToCustomerTextboxByName("password", password);
        newCustomerPage.sleepInSecond(1);

        log.info("Create_Cus_01 - Step 13: Click to 'Submit' button");
        newCustomerPage.clickToButtonByName(driver, "Submit");

        log.info("Create_Cus_01 - Step 14: Verify success message displayed");
        verifyTrue(newCustomerPage.isPageTitleByTextDisplayed(driver, "Customer Registered Successfully!!!"));

        log.info("Create_Cus_01 - Step 15: Verify Customer Name displayed with value: " + cusName);
        verifyEquals(newCustomerPage.getTextValueByLabelAtTable(driver, "Customer Name"), cusName);

        log.info("Create_Cus_01 - Step 15: Verify Gender displayed with value: " + gender);
        verifyEquals(newCustomerPage.getTextValueByLabelAtTable(driver, "Gender"), gender);

        log.info("Create_Cus_01 - Step 16: Verify Birthdate displayed with value: " + birthYear + "-" + birthMonth + "-" + birthDate);
        verifyEquals(newCustomerPage.getTextValueByLabelAtTable(driver, "Birthdate"), birthYear + "-" + birthMonth + "-" + birthDate);

        log.info("Create_Cus_01 - Step 17: Verify Address displayed with value: " + address);
        verifyEquals(newCustomerPage.getTextValueByLabelAtTable(driver, "Address"), address);

        log.info("Create_Cus_01 - Step 18: Verify City displayed with value: " + city);
        verifyEquals(newCustomerPage.getTextValueByLabelAtTable(driver, "City"), city);

        log.info("Create_Cus_01 - Step 19: Verify State displayed with value: " + state);
        verifyEquals(newCustomerPage.getTextValueByLabelAtTable(driver, "State"), state);

        log.info("Create_Cus_01 - Step 20: Verify Pin displayed with value: " + pin);
        verifyEquals(newCustomerPage.getTextValueByLabelAtTable(driver, "Pin"), pin);

        log.info("Create_Cus_01 - Step 21: Verify Mobile Number displayed with value: " + mobile);
        verifyEquals(newCustomerPage.getTextValueByLabelAtTable(driver, "Mobile No."), mobile);

        log.info("Create_Cus_01 - Step 22: Verify Email displayed with value: " + emailAddress);
        verifyEquals(newCustomerPage.getTextValueByLabelAtTable(driver, "Email"), emailAddress);
    }

    @Parameters("envName")
    @AfterClass(alwaysRun = true)
    public void cleanBrowser(@Optional("local") String envName) {
        log.info("Post-condition - Close browser and driver");
        closeBrowserAndDriver(envName);
    }

    WebDriver driver;
    DataUtil fakeData;
    EnvConfig envConfig;
    LoginPO loginPage;
    ManagerHomePO managerHomePage;
    NewCustomerPO newCustomerPage;
    String emailAddress, userID, cusName, gender, birthDate, birthMonth, birthYear, address, city, state, pin, mobile, password;
}