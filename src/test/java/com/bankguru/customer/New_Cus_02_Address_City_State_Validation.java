package com.bankguru.customer;

import commons.BaseTest;
import factoryEnvironment.EnvConfig;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pageObjects.LoginPO;
import pageObjects.ManagerHomePO;
import pageObjects.NewCustomerPO;
import pageObjects.PageGeneratorManager;
import utilities.DataUtil;

public class New_Cus_02_Address_City_State_Validation extends BaseTest {
    @Parameters({"envName", "serverName", "browser", "ipAddress", "port", "osName", "osVersion"})
    @BeforeClass
    public void initBrowser(@Optional("local") String envName, @Optional("dev") String serverName, @Optional("chrome") String browserName, @Optional("localhost") String ipAddress, @Optional("4444") String portNumber, @Optional("Mac OS X") String osName, @Optional("10.16") String osVersion) {
        ConfigFactory.setProperty("env", serverName);
        envConfig = ConfigFactory.create(EnvConfig.class);
        fakeData = DataUtil.getData();
        emailAddress = fakeData.getEmailAddress();

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
    }

    @Test
    public void Address_01_Input_Empty_Address() {
        log.info("Empty_Address_01 - Step 01: Input to Address text area with empty value");
        newCustomerPage.inputToTextAreaByName(driver, "addr", "");

        log.info("Empty_Address_01 - Step 02: Press TAB key");
        newCustomerPage.pressTabKeyInTextAreaByName(driver, "addr");

        log.info("Empty_Address_01 - Step 03: Verify error message 'Address Field must not be blank' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "Address"), "Address Field must not be blank");
    }

    @Test
    public void Address_02_Input_Address_Having_Space_First_Char() {
        log.info("Space_First_Char_Address_02 - Step 01: Input to Address textbox with space character");
        newCustomerPage.inputToTextAreaByName(driver, "addr", " ");

        log.info("Address_Space_First_Char_Address_02 - Step 02: Press TAB key");
        newCustomerPage.pressTabKeyInTextAreaByName(driver, "addr");

        log.info("Address_Space_First_Char_Address_02 - Step 03: Verify error message 'First character can not have space' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "Address"), "First character can not have space");
    }

    @Test
    public void City_01_Input_Empty_City() {
        log.info("Empty_City_01 - Step 01: Input to City textbox with empty value");
        newCustomerPage.inputToCustomerTextboxByName("city", "");

        log.info("Empty_City_01 - Step 02: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "city");

        log.info("Empty_City_01 - Step 03: Verify error message 'City Field must not be blank' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "City"), "City Field must not be blank");
    }

    @Test
    public void City_02_Input_Numeric_City() {
        log.info("Numeric_City_02 - Step 01: Input to City textbox with value '1234'");
        newCustomerPage.inputToCustomerTextboxByName("city", "1234");

        log.info("Numeric_City_02 - Step 02: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "city");

        log.info("Numeric_City_02 - Step 03: Verify error message 'Numbers are not allowed' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "City"), "Numbers are not allowed");

        log.info("Numeric_City_02 - Step 04: Input to City textbox with value 'city123'");
        newCustomerPage.inputToCustomerTextboxByName("city", "city123");

        log.info("Numeric_City_02 - Step 05: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "city");

        log.info("Numeric_City_02 - Step 06: Verify error message 'Numbers are not allowed' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "City"), "Numbers are not allowed");
    }

    @Test
    public void City_03_Input_City_Having_Special_Chars() {
        log.info("Special_Chars_City_03 - Step 01: Input to City textbox with value 'name!@#'");
        newCustomerPage.inputToCustomerTextboxByName("city", "City!@#");

        log.info("Special_Chars_City_03 - Step 02: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "city");

        log.info("Special_Chars_City_03 - Step 03: Verify error message 'Special characters are not allowed' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "City"), "Special characters are not allowed");

        log.info("Special_Chars_City_03 - Step 04: Input to City textbox with value '!@#'");
        newCustomerPage.inputToCustomerTextboxByName("city", "!@#");

        log.info("Special_Chars_City_03 - Step 05: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "city");

        log.info("Special_Chars_City_03 - Step 06: Verify error message 'Special characters are not allowed' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "City"), "Special characters are not allowed");
    }

    @Test
    public void City_04_Input_City_Having_Space_First_Char() {
        log.info("Space_First_Char_City_04 - Step 01: Input to City textbox with space character");
        newCustomerPage.inputToCustomerTextboxByName("city", " ");

        log.info("Space_First_Char_City_04 - Step 02: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "city");

        log.info("Space_First_Char_City_04 - Step 03: Verify error message 'First character can not have space' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "City"), "First character can not have space");
    }

    @Test
    public void State_01_Input_Empty_State() {
        log.info("Empty_State_01 - Step 01: Input to State textbox with empty value");
        newCustomerPage.inputToCustomerTextboxByName("state", "");

        log.info("Empty_State_01 - Step 02: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "state");

        log.info("Empty_State_01 - Step 03: Verify error message 'Customer name must not be blank' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "State"), "State must not be blank");
    }

    @Test
    public void State_02_Input_Numeric_State() {
        log.info("Numeric_Sate_02 - Step 01: Input to State textbox with value '1234'");
        newCustomerPage.inputToCustomerTextboxByName("state", "1234");

        log.info("Numeric_Sate_02 - Step 02: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "state");

        log.info("Numeric_Sate_02 - Step 03: Verify error message 'Numbers are not allowed' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "State"), "Numbers are not allowed");

        log.info("Numeric_Sate_02 - Step 04: Input to State textbox with value 'State123'");
        newCustomerPage.inputToCustomerTextboxByName("state", "State123");

        log.info("Numeric_Sate_02 - Step 05: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "state");

        log.info("Numeric_Sate_02 - Step 06: Verify error message 'Numbers are not allowed' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "State"), "Numbers are not allowed");
    }

    @Test
    public void State_03_Input_State_Having_Special_Chars() {
        log.info("Special_Chars_03 - Step 01: Input to State textbox with value 'State!@#'");
        newCustomerPage.inputToCustomerTextboxByName("state", "State!@#");

        log.info("Special_Chars_03 - Step 02: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "state");

        log.info("Special_Chars_03 - Step 03: Verify error message 'Special characters are not allowed' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "State"), "Special characters are not allowed");

        log.info("Special_Chars_03 - Step 04: Input to State textbox with value '!@#'");
        newCustomerPage.inputToCustomerTextboxByName("state", "!@#");

        log.info("Special_Chars_03 - Step 05: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "state");

        log.info("Special_Chars_03 - Step 06: Verify error message 'Special characters are not allowed' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "State"), "Special characters are not allowed");
    }

    @Test
    public void State_04_Input_State_Having_Space_First_Char() {
        log.info("Space_First_Char_04 - Step 01: Input to State textbox with space character");
        newCustomerPage.inputToCustomerTextboxByName("state", " ");

        log.info("Space_First_Char_04 - Step 02: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "state");

        log.info("Space_First_Char_04 - Step 03: Verify error message 'First character can not have space' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "State"), "First character can not have space");
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
    String emailAddress, userID, password;
}