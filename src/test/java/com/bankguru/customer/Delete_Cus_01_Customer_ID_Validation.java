package com.bankguru.customer;

import commons.BaseTest;
import factoryEnvironment.EnvConfig;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pageObjects.*;
import utilities.DataUtil;

public class Delete_Cus_01_Customer_ID_Validation extends BaseTest {
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

        log.info("Pre-condition - Step 12: Open New Customer page");
        managerHomePage.openSidebarMenuByPageName(driver, "New Customer");
        newCustomerPage = PageGeneratorManager.getNewCustomerPage(driver);

        log.info("Pre-condition - Step 13: Verify New Customer page displayed");
        verifyTrue(newCustomerPage.isPageTitleByTextDisplayed(driver, "Add New Customer"));

        log.info("Pre-condition - Step 14: Input to Customer Name texbox with value: " + cusName);
        newCustomerPage.sleepInSecond(1);
        newCustomerPage.inputToCustomerTextboxByName("name", cusName);

        log.info("Pre-condition - Step 15: Click to Gender radio button with value: " + gender);
        newCustomerPage.checkToRadioButtonByValue(driver, gender.substring(0, 1));

        log.info("Pre-condition - Step 16: Input to Date of Birth textbox");
        newCustomerPage.inputBirthDateTextbox(birthDate, birthMonth, birthYear);

        log.info("Pre-condition - Step 17: Input to Address text area with value: " + address);
        newCustomerPage.inputToTextAreaByName(driver, "addr", address);

        log.info("Pre-condition - Step 18: Input to City texbox with value: " + city);
        newCustomerPage.inputToCustomerTextboxByName("city", city);

        log.info("Pre-condition - Step 19: Input to State texbox with value: " + state);
        newCustomerPage.inputToCustomerTextboxByName("state", state);

        log.info("Pre-condition - Step 20: Input to Pin texbox with value: " + pin);
        newCustomerPage.inputToCustomerTextboxByName("pinno", pin);

        log.info("Pre-condition - Step 21: Input to Mobile Number texbox with value: " + mobile);
        newCustomerPage.inputToCustomerTextboxByName("telephoneno", mobile);

        log.info("Pre-condition - Step 22: Input to Email texbox with value: " + emailAddress);
        newCustomerPage.inputToCustomerTextboxByName("emailid", emailAddress);

        log.info("Pre-condition - Step 23: Input to Password texbox with value: " + password);
        newCustomerPage.inputToCustomerTextboxByName("password", password);
        newCustomerPage.sleepInSecond(1);

        log.info("Pre-condition - Step 24: Click to 'Submit' button");
        newCustomerPage.clickToButtonByName(driver, "Submit");

        log.info("Pre-condition - Step 25: Verify success message displayed");
        verifyTrue(newCustomerPage.isPageTitleByTextDisplayed(driver, "Customer Registered Successfully!!!"));
        cusID = newCustomerPage.getTextValueByLabelAtTable(driver, "Customer ID");

        log.info("Pre-condition - Step 26: Open Delete Customer page");
        newCustomerPage.openSidebarMenuByPageName(driver, "Delete Customer");
        deleteCustomerPage = PageGeneratorManager.getDeleteCustomerPage(driver);

        log.info("Pre-condition - Step 27: Verify Delete Customer page displayed");
        verifyTrue(deleteCustomerPage.isPageTitleByTextDisplayed(driver, "Delete Customer Form"));
    }

    @Test
    public void Delete_Cus_01_Input_Empty_Customer_ID() {
        log.info("Delete_Empty_CusID_01 - Step 01: Input to Customer ID textbox with empty value");
        deleteCustomerPage.inputToTextboxByName(driver, "cusid", "");

        log.info("Delete_Empty_CusID_01 - Step 02: Press TAB key");
        deleteCustomerPage.pressTabKeyInTextboxByName(driver, "cusid");

        log.info("Delete_Empty_CusID_01 - Step 03: Verify error message 'Customer ID is required' displayed");
        verifyEquals(deleteCustomerPage.getErrorMessageByFieldName(driver, "Customer ID"), "Customer ID is required");
    }

    @Test
    public void Delete_Cus_02_Input_Numeric_Customer_ID() {
        log.info("Delete_Numeric_CusID_02 - Step 01: Input to Customer ID textbox with value: '1234Acc'");
        deleteCustomerPage.inputToTextboxByName(driver,"cusid", "1234Acc");

        log.info("Delete_Numeric_CusID_02 - Step 02: Press TAB key");
        deleteCustomerPage.pressTabKeyInTextboxByName(driver, "cusid");

        log.info("Delete_Numeric_CusID_02 - Step 03: Verify error message 'Characters are not allowed' displayed");
        verifyEquals(deleteCustomerPage.getErrorMessageByFieldName(driver, "Customer ID"), "Characters are not allowed");

        log.info("Delete_Numeric_CusID_02 - Step 04: Input to Customer ID textbox with value: 'Acc123'");
        deleteCustomerPage.inputToTextboxByName(driver, "cusid", "Acc123");

        log.info("Delete_Numeric_CusID_02 - Step 05: Press TAB key");
        deleteCustomerPage.pressTabKeyInTextboxByName(driver, "cusid");

        log.info("Delete_Numeric_CusID_02 - Step 06: Verify error message 'Characters are not allowed' displayed");
        verifyEquals(deleteCustomerPage.getErrorMessageByFieldName(driver, "Customer ID"), "Characters are not allowed");
    }

    @Test
    public void Delete_Cus_03_Input_Customer_ID_Having_Special_Characters() {
        log.info("Delete_CusID_Special_Chars_03 - Step 01: Input to Customer ID textbox with value 'name!@#'");
        deleteCustomerPage.inputToTextboxByName(driver, "cusid", "123!@#");

        log.info("Delete_CusID_Special_Chars_03 - Step 02: Press TAB key");
        deleteCustomerPage.pressTabKeyInTextboxByName(driver, "cusid");

        log.info("Delete_CusID_Special_Chars_03 - Step 03: Verify error message 'Special characters are not allowed' displayed");
        verifyEquals(deleteCustomerPage.getErrorMessageByFieldName(driver, "Customer ID"), "Special characters are not allowed");

        log.info("Delete_CusID_Special_Chars_03 - Step 04: Input to Customer ID textbox with value '!@#'");
        deleteCustomerPage.inputToTextboxByName(driver, "cusid", "!@#");

        log.info("Delete_CusID_Special_Chars_03 - Step 05: Press TAB key");
        deleteCustomerPage.pressTabKeyInTextboxByName(driver, "cusid");

        log.info("Delete_CusID_Special_Chars_03 - Step 06: Verify error message 'Special characters are not allowed' displayed");
        verifyEquals(deleteCustomerPage.getErrorMessageByFieldName(driver, "Customer ID"), "Special characters are not allowed");
    }

    @Test
    public void Delete_Cus_04_Input_Customer_ID_Having_Space_Char() {
        log.info("Delete_CusID_Space_Char_04 - Step 01: Input to Customer ID textbox with space character");
        deleteCustomerPage.inputToTextboxByName(driver, "cusid", "123 12");

        log.info("Delete_CusID_Space_Char_04 - Step 02: Press TAB key");
        deleteCustomerPage.pressTabKeyInTextboxByName(driver, "cusid");

        log.info("Delete_CusID_Space_Char_04 - Step 03: Verify error message 'Characters are not allowed' displayed");
        verifyEquals(deleteCustomerPage.getErrorMessageByFieldName(driver, "Customer ID"), "Characters are not allowed");
    }

    @Test
    public void Delete_Cus_05_Input_Customer_ID_Having_Space_First_Char() {
        log.info("Delete_CusID_Space_First_Char_05 - Step 01: Input to Customer ID textbox with blank space in 1st char");
        deleteCustomerPage.inputToTextboxByName(driver, "cusid", " ");

        log.info("Delete_CusID_Space_First_Char_05 - Step 02: Press TAB key");
        deleteCustomerPage.pressTabKeyInTextboxByName(driver, "cusid");

        log.info("Delete_CusID_Space_First_Char_05 - Step 03: Verify error message 'First character can not have space' displayed");
        verifyEquals(deleteCustomerPage.getErrorMessageByFieldName(driver, "Customer ID"), "First character can not have space");
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
    DeleteCustomerPO deleteCustomerPage;
    String emailAddress, userID, cusID, cusName, gender, birthDate, birthMonth, birthYear, address, city, state, pin, mobile, password;
}