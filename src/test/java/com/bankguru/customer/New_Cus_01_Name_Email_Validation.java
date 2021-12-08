package com.bankguru.customer;

import commons.BaseTest;
import factoryEnvironment.EnvConfig;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pageObjects.*;
import utilities.DataUtil;

public class New_Cus_01_Name_Email_Validation extends BaseTest {
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
    public void Name_01_Input_Empty_Name() {
        log.info("Empty_Name_01 - Step 01: Input to Customer Name textbox with empty value");
        newCustomerPage.inputToCustomerTextboxByName("name", "");

        log.info("Empty_Name_01 - Step 02: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "name");

        log.info("Empty_Name_01 - Step 03: Verify error message 'Customer name must not be blank' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "Customer Name"), "Customer name must not be blank");
    }

    @Test
    public void Name_02_Input_Numeric_Name() {
        log.info("Numeric_Name_02 - Step 01: Input to Customer Name textbox with value: '1234'");
        newCustomerPage.inputToCustomerTextboxByName("name", "1234");

        log.info("Numeric_Name_02 - Step 02: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "name");

        log.info("Numeric_Name_02 - Step 03: Verify error message 'Numbers are not allowed' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "Customer Name"), "Numbers are not allowed");

        log.info("Numeric_Name_02 - Step 04: Input to Customer Name textbox with value: 'name123'");
        newCustomerPage.inputToCustomerTextboxByName("name", "name123");

        log.info("Numeric_Name_02 - Step 05: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "name");

        log.info("Numeric_Name_02 - Step 06: Verify error message 'Numbers are not allowed' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "Customer Name"), "Numbers are not allowed");
    }

    @Test
    public void Name_03_Input_Name_Having_Special_Chars() {
        log.info("Special_Chars_Name_03 - Step 01: Input to Customer Name textbox with value 'name!@#'");
        newCustomerPage.inputToCustomerTextboxByName("name", "name!@#");

        log.info("Special_Chars_Name_03 - Step 02: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "name");

        log.info("Special_Chars_Name_03 - Step 03: Verify error message 'Special characters are not allowed' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "Customer Name"), "Special characters are not allowed");

        log.info("Special_Chars_Name_03 - Step 04: Input to Customer Name textbox with value '!@#'");
        newCustomerPage.inputToCustomerTextboxByName("name", "!@#");

        log.info("Special_Chars_Name_03 - Step 05: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "name");

        log.info("Special_Chars_Name_03 - Step 06: Verify error message 'Special characters are not allowed' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "Customer Name"), "Special characters are not allowed");
    }

    @Test
    public void Name_04_Input_Name_Having_Space_First_Char() {
        log.info("Space_First_Char_Name_04 - Step 01: Input to Customer Name textbox with space character");
        newCustomerPage.inputToCustomerTextboxByName("name", " ");

        log.info("Space_First_Char_Name_04 - Step 02: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "name");

        log.info("Space_First_Char_Name_04 - Step 03: Verify error message 'First character can not have space' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "Customer Name"), "First character can not have space");
    }

    @Test
    public void Email_01_Input_Empty_Email() {
        log.info("Empty_Email_01 - Step 01: Input to Email textbox with empty value");
        newCustomerPage.inputToCustomerTextboxByName("emailid", "");

        log.info("Empty_Email_01 - Step 02: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "emailid");

        log.info("Empty_Email_01 - Step 03: Verify error message 'Email-ID must not be blank' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "E-mail"), "Email-ID must not be blank");
    }

    @Test
    public void Email_02_Input_Invalid_Email() {
        log.info("Invalid_Email_02 - Step 01: Input to Email textbox with value: 'guru99@gmail'");
        newCustomerPage.inputToCustomerTextboxByName("emailid", "guru99@gmail");

        log.info("Invalid_Email_02 - Step 02: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "emailid");

        log.info("Invalid_Email_02 - Step 03: Verify error message 'Email-ID is not valid' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "E-mail"), "Email-ID is not valid");

        log.info("Invalid_Email_02 - Step 01: Input to Email textbox with value: 'guru99'");
        newCustomerPage.inputToCustomerTextboxByName("emailid", "guru99");

        log.info("Invalid_Email_02 - Step 02: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "emailid");

        log.info("Invalid_Email_02 - Step 03: Verify error message 'Email-ID is not valid' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "E-mail"), "Email-ID is not valid");

        log.info("Invalid_Email_02 - Step 01: Input to Email textbox with value: 'Guru99@'");
        newCustomerPage.inputToCustomerTextboxByName("emailid", "Guru99@");

        log.info("Invalid_Email_02 - Step 02: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "emailid");

        log.info("Invalid_Email_02 - Step 03: Verify error message 'Email-ID is not valid' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "E-mail"), "Email-ID is not valid");

        log.info("Invalid_Email_02 - Step 01: Input to Email textbox with value: 'guru99@gmail.'");
        newCustomerPage.inputToCustomerTextboxByName("emailid", "guru99@gmail.");

        log.info("Invalid_Email_02 - Step 02: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "emailid");

        log.info("Invalid_Email_02 - Step 03: Verify error message 'Email-ID is not valid' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "E-mail"), "Email-ID is not valid");

        log.info("Invalid_Email_02 - Step 01: Input to Email textbox with value: 'guru99gmail.com'");
        newCustomerPage.inputToCustomerTextboxByName("emailid", "guru99gmail.com");

        log.info("Invalid_Email_02 - Step 02: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "emailid");

        log.info("Invalid_Email_02 - Step 03: Verify error message 'Email-ID is not valid' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "E-mail"), "Email-ID is not valid");
    }

    @Test
    public void Email_04_Input_Email_Having_Space_First_Char() {
        log.info("Space_First_Char_Email_03 - Step 01: Input to Customer Name textbox with space character");
        newCustomerPage.inputToCustomerTextboxByName("emailid", " ");

        log.info("Space_First_Char_Email_03 - Step 02: Press TAB key");
        newCustomerPage.pressTabKeyInTextboxByName(driver, "emailid");

        log.info("Space_First_Char_Email_03 - Step 03: Verify error message 'First character can not have space' displayed");
        verifyEquals(newCustomerPage.getErrorMessageByFieldName(driver, "E-mail"), "First character can not have space");
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