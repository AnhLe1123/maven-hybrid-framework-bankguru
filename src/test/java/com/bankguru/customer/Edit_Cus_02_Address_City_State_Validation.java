package com.bankguru.customer;

import commons.BaseTest;
import factoryEnvironment.EnvConfig;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pageObjects.*;
import utilities.DataUtil;

public class Edit_Cus_02_Address_City_State_Validation extends BaseTest {
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

        log.info("Pre-condition - Step 26: Open Edit Customer page");
        newCustomerPage.openSidebarMenuByPageName(driver, "Edit Customer");
        editCustomerPage = PageGeneratorManager.getEditCustomerPage(driver);

        log.info("Pre-condition - Step 27: Verify Edit Customer page displayed");
        verifyTrue(editCustomerPage.isPageTitleByTextDisplayed(driver, "Edit Customer Form"));

        log.info("Pre-condition - Step 28: Input to Customer ID textbox with value: " + cusID);
        editCustomerPage.inputToTextboxByName(driver, "cusid", cusID);

        log.info("Pre-condition - Step 29: Click to Submit button");
        editCustomerPage.clickToButtonByName(driver, "Submit");

        log.info("Pre-condition - Step 30: Verify Edit Customer title displayed");
        verifyTrue(editCustomerPage.isPageTitleByTextDisplayed(driver, "Edit Customer"));
    }

    @Test
    public void Edit_Address_01_Input_Empty_Address() {
        log.info("Edit_Empty_Address_01 - Step 01: Input to Address text area with empty value");
        editCustomerPage.inputToTextAreaByName(driver, "addr", "");

        log.info("Edit_Empty_Address_01 - Step 02: Press TAB key");
        editCustomerPage.pressTabKeyInTextAreaByName(driver, "addr");

        log.info("Edit_Empty_Address_01 - Step 03: Verify error message 'Address Field must not be blank' displayed");
        verifyEquals(editCustomerPage.getErrorMessageByFieldName(driver, "Address"), "Address Field must not be blank");
    }

    @Test
    public void Edit_City_01_Input_Empty_City() {
        log.info("Edit_Empty_City_01 - Step 01: Input to City textbox with empty value");
        editCustomerPage.inputToTextboxByName(driver, "city", "");

        log.info("Edit_Empty_City_01 - Step 02: Press TAB key");
        editCustomerPage.pressTabKeyInTextboxByName(driver, "city");

        log.info("Edit_Empty_City_01 - Step 03: Verify error message 'City Field must not be blank' displayed");
        verifyEquals(editCustomerPage.getErrorMessageByFieldName(driver, "City"), "City Field must not be blank");
    }

    @Test
    public void Edit_City_02_Input_Numeric_City() {
        log.info("Edit_Numeric_City_02 - Step 01: Input to City textbox with value '1234'");
        editCustomerPage.inputToTextboxByName(driver, "city", "1234");

        log.info("Edit_Numeric_City_02 - Step 02: Press TAB key");
        editCustomerPage.pressTabKeyInTextboxByName(driver, "city");

        log.info("Edit_Numeric_City_02 - Step 03: Verify error message 'Numbers are not allowed' displayed");
        verifyEquals(editCustomerPage.getErrorMessageByFieldName(driver, "City"), "Numbers are not allowed");

        log.info("Edit_Numeric_City_02 - Step 04: Input to City textbox with value 'city123'");
        editCustomerPage.inputToTextboxByName(driver, "city", "city123");

        log.info("Numeric_City_02 - Step 05: Press TAB key");
        editCustomerPage.pressTabKeyInTextboxByName(driver, "city");

        log.info("Numeric_City_02 - Step 06: Verify error message 'Numbers are not allowed' displayed");
        verifyEquals(editCustomerPage.getErrorMessageByFieldName(driver, "City"), "Numbers are not allowed");
    }

    @Test
    public void Edit_City_03_Input_City_Having_Special_Chars() {
        log.info("Edit_Special_Chars_City_03 - Step 01: Input to City textbox with value 'name!@#'");
        editCustomerPage.inputToTextboxByName(driver, "city", "City!@#");

        log.info("Edit_Special_Chars_City_03 - Step 02: Press TAB key");
        editCustomerPage.pressTabKeyInTextboxByName(driver, "city");

        log.info("Edit_Special_Chars_City_03 - Step 03: Verify error message 'Special characters are not allowed' displayed");
        verifyEquals(editCustomerPage.getErrorMessageByFieldName(driver, "City"), "Special characters are not allowed");

        log.info("Edit_Special_Chars_City_03 - Step 04: Input to City textbox with value '!@#'");
        editCustomerPage.inputToTextboxByName(driver, "city", "!@#");

        log.info("Edit_Special_Chars_City_03 - Step 05: Press TAB key");
        editCustomerPage.pressTabKeyInTextboxByName(driver, "city");

        log.info("Edit_Special_Chars_City_03 - Step 06: Verify error message 'Special characters are not allowed' displayed");
        verifyEquals(editCustomerPage.getErrorMessageByFieldName(driver, "City"), "Special characters are not allowed");
    }

    @Test
    public void Edit_State_01_Input_Empty_State() {
        log.info("Edit_Empty_State_01 - Step 01: Input to State textbox with empty value");
        editCustomerPage.inputToTextboxByName(driver, "state", "");

        log.info("Edit_Empty_State_01 - Step 02: Press TAB key");
        editCustomerPage.pressTabKeyInTextboxByName(driver, "state");

        log.info("Edit_Empty_State_01 - Step 03: Verify error message 'Customer name must not be blank' displayed");
        verifyEquals(editCustomerPage.getErrorMessageByFieldName(driver, "State"), "State must not be blank");
    }

    @Test
    public void Edit_State_02_Input_Numeric_State() {
        log.info("Edit_Numeric_Sate_02 - Step 01: Input to State textbox with value '1234'");
        editCustomerPage.inputToTextboxByName(driver, "state", "1234");

        log.info("Edit_Numeric_Sate_02 - Step 02: Press TAB key");
        editCustomerPage.pressTabKeyInTextboxByName(driver, "state");

        log.info("Edit_Numeric_Sate_02 - Step 03: Verify error message 'Numbers are not allowed' displayed");
        verifyEquals(editCustomerPage.getErrorMessageByFieldName(driver, "State"), "Numbers are not allowed");

        log.info("Edit_Numeric_Sate_02 - Step 04: Input to State textbox with value 'State123'");
        editCustomerPage.inputToTextboxByName(driver, "state", "State123");

        log.info("Edit_Numeric_Sate_02 - Step 05: Press TAB key");
        editCustomerPage.pressTabKeyInTextboxByName(driver, "state");

        log.info("Edit_Numeric_Sate_02 - Step 06: Verify error message 'Numbers are not allowed' displayed");
        verifyEquals(editCustomerPage.getErrorMessageByFieldName(driver, "State"), "Numbers are not allowed");
    }

    @Test
    public void Edit_State_03_Input_State_Having_Special_Chars() {
        log.info("Edit_State_Special_Chars_03 - Step 01: Input to State textbox with value 'State!@#'");
        editCustomerPage.inputToTextboxByName(driver, "state", "State!@#");

        log.info("Edit_State_Special_Chars_03 - Step 02: Press TAB key");
        editCustomerPage.pressTabKeyInTextboxByName(driver, "state");

        log.info("Edit_State_Special_Chars_03 - Step 03: Verify error message 'Special characters are not allowed' displayed");
        verifyEquals(editCustomerPage.getErrorMessageByFieldName(driver, "State"), "Special characters are not allowed");

        log.info("Edit_State_Special_Chars_03 - Step 04: Input to State textbox with value '!@#'");
        editCustomerPage.inputToTextboxByName(driver, "state", "!@#");

        log.info("Edit_State_Special_Chars_03 - Step 05: Press TAB key");
        editCustomerPage.pressTabKeyInTextboxByName(driver, "state");

        log.info("Edit_State_Special_Chars_03 - Step 06: Verify error message 'Special characters are not allowed' displayed");
        verifyEquals(editCustomerPage.getErrorMessageByFieldName(driver, "State"), "Special characters are not allowed");
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
    EditCustomerPO editCustomerPage;
    String emailAddress, userID, cusID, cusName, gender, birthDate, birthMonth, birthYear, address, city, state, pin, mobile, password;
}