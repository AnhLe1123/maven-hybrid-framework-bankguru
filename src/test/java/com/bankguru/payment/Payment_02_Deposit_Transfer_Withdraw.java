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

public class Payment_02_Deposit_Transfer_Withdraw extends BaseTest {
    @Parameters({"browser", "env"})
    @BeforeClass
    public void initBrowser(String browserName, String envName) {
        ConfigFactory.setProperty("env", envName);
        environment = ConfigFactory.create(Environment.class);
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
        accType = "Savings";
        initalDeposit = 50000;
        editAccType = "Current";
        depositAmount = 5000;
        depositDesc = "Deposit";
        withdrawalAmount = 15000;
        withdrawalDesc = "Withdrawal";
        transferAmount = 10000;
        transferDesc = "Transfer";

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
        userID = loginPage.getTextValueByLabelAtTable(driver, "User ID");
        password = loginPage.getTextValueByLabelAtTable(driver, "Password");

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

        log.info("Pre-condition - Step 12: Open New Customer page");
        managerHomePage.openSidebarMenuByPageName(driver, "New Customer");
        newCustomerPage = PageGeneratorManager.getNewCustomerPage(driver);

        log.info("Pre-condition - Step 13: Verify New Customer page displayed");
        verifyTrue(newCustomerPage.isPageTitleByTextDisplayed(driver, "Add New Customer"));

        log.info("Pre-condition - Step 14: Input to Customer Name texbox with value: " + cusName);
        newCustomerPage.sleepInSecond(1);
        newCustomerPage.inputToCustomerTextboxByName("name", cusName);

        log.info("Pre-condition - Step 04: Click to Gender radio button with value: " + gender);
        newCustomerPage.checkToRadioButtonByValue(driver, gender.substring(0, 1));

        log.info("Pre-condition - Step 05: Input to Date of Birth textbox");
        newCustomerPage.inputBirthDateTextbox(birthDate, birthMonth, birthYear);

        log.info("Pre-condition - Step 06: Input to Address text area with value: " + address);
        newCustomerPage.inputToTextAreaByName(driver, "addr", address);

        log.info("Pre-condition - Step 07: Input to City texbox with value: " + city);
        newCustomerPage.inputToCustomerTextboxByName("city", city);

        log.info("Pre-condition - Step 08: Input to State texbox with value: " + state);
        newCustomerPage.inputToCustomerTextboxByName("state", state);

        log.info("Pre-condition - Step 09: Input to Pin texbox with value: " + pin);
        newCustomerPage.inputToCustomerTextboxByName("pinno", pin);

        log.info("Pre-condition - Step 10: Input to Mobile Number texbox with value: " + mobile);
        newCustomerPage.inputToCustomerTextboxByName("telephoneno", mobile);

        log.info("Pre-condition - Step 11: Input to Email texbox with value: " + emailAddress);
        newCustomerPage.inputToCustomerTextboxByName("emailid", emailAddress);

        log.info("Pre-condition - Step 12: Input to Password texbox with value: " + password);
        newCustomerPage.inputToCustomerTextboxByName("password", password);
        newCustomerPage.sleepInSecond(1);

        log.info("Pre-condition - Step 13: Click to 'Submit' button");
        newCustomerPage.clickToButtonByName(driver, "Submit");
        newCustomerPage.sleepInSecond(1);

        log.info("Pre-condition - Step 14: Verify success message displayed");
        verifyTrue(newCustomerPage.isPageTitleByTextDisplayed(driver, "Customer Registered Successfully!!!"));
        cusID = newCustomerPage.getTextValueByLabelAtTable(driver, "Customer ID");
    }

    @Test
    public void Payment_01_Create_Account_And_Inital_Deposit() {
        log.info("Create_Acc_01 - Step 01: Open New Account page");
        newCustomerPage.openSidebarMenuByPageName(driver, "New Account");
        newAccountPage = PageGeneratorManager.getNewAccountPage(driver);

        log.info("Create_Acc_01 - Step 02: Verify New Account page displayed");
        verifyTrue(newAccountPage.isPageTitleByTextDisplayed(driver, "Add new account form"));

        log.info("Create_Acc_01 - Step 03: Input to Customer id textbox with value: " + cusID);
        newAccountPage.inputToTextboxByName(driver, "cusid", cusID);

        log.info("Create_Acc_01 - Step 04: Select option in Account type dropdown with value: " + accType);
        newAccountPage.selectOptionInDropdownByName(driver, "selaccount", accType);
        log.info("Create_Acc_01 - Step 05: Input to Inital deposit textbox with value: " + initalDeposit);
        newAccountPage.inputToTextboxByName(driver, "inideposit", String.valueOf(initalDeposit));
        currentAmount = initalDeposit;

        log.info("Create_Acc_01 - Step 06: Click to 'Submit' button");
        newAccountPage.clickToButtonByName(driver, "submit");

        log.info("Create_Acc_01 - Step 07: Verify success message displayed");
        verifyTrue(newAccountPage.isPageTitleByTextDisplayed(driver, "Account Generated Successfully!!!"));

        log.info("Create_Acc_01 - Step 08: Verify new account info displayed");
        accID = newAccountPage.getTextValueByLabelAtTable(driver, "Account ID");
        verifyEquals(newAccountPage.getTextValueByLabelAtTable(driver, "Customer ID"), cusID);
        verifyEquals(newAccountPage.getTextValueByLabelAtTable(driver, "Customer Name"), cusName);
        verifyEquals(newAccountPage.getTextValueByLabelAtTable(driver, "Email"), emailAddress);
        verifyEquals(newAccountPage.getTextValueByLabelAtTable(driver, "Account Type"), accType);
        verifyEquals(newAccountPage.getTextValueByLabelAtTable(driver, "Current Amount"), String.valueOf(initalDeposit));

        log.info("Create_Acc_01 - Step 09: Open New Account page");
        newCustomerPage.openSidebarMenuByPageName(driver, "New Account");
        newAccountPage = PageGeneratorManager.getNewAccountPage(driver);
        verifyTrue(newAccountPage.isPageTitleByTextDisplayed(driver, "Add new account form"));

        log.info("Create_Acc_01 - Step 11: Create Payee account");
        newAccountPage.inputToTextboxByName(driver, "cusid", cusID);
        newAccountPage.selectOptionInDropdownByName(driver, "selaccount", accType);
        newAccountPage.inputToTextboxByName(driver, "inideposit", String.valueOf(initalDeposit));
        newAccountPage.clickToButtonByName(driver, "submit");

        log.info("Create_Acc_01 - Step 08: Verify Payee account created successfully");
        verifyTrue(newAccountPage.isPageTitleByTextDisplayed(driver, "Account Generated Successfully!!!"));
        payeeID = newAccountPage.getTextValueByLabelAtTable(driver, "Account ID");
    }

    @Test
    public void Payment_02_Edit_Account() {
        log.info("Edit_Acc_02 - Step 01: Open Edit Account page");
        newAccountPage.openSidebarMenuByPageName(driver, "Edit Account");
        editAccountPage = PageGeneratorManager.getEditAccountPage(driver);

        log.info("Edit_Acc_02 - Step 02: Verify Edit Account page displayed");
        verifyTrue(editAccountPage.isPageTitleByTextDisplayed(driver, "Edit Account Form"));

        log.info("Edit_Acc_02 - Step 03: Input to Account No textbox with value: " + accID);
        editAccountPage.inputToTextboxByName(driver, "accountno", accID);

        log.info("Edit_Acc_02 - Step 04: Click to 'Submit' button");
        editAccountPage.clickToButtonByName(driver, "Submit");

        log.info("Edit_Acc_02 - Step 05: Change option in Type of account dropdown with value: " + editAccType);
        editAccountPage.selectOptionInDropdownByName(driver, "a_type", editAccType);

        log.info("Edit_Acc_02 - Step 06: Click to 'Submit' button");
        editAccountPage.clickToButtonByName(driver, "Submit");

        log.info("Edit_Acc_02 - Step 07: Verify success message displayed");
        verifyTrue(editAccountPage.isPageTitleByTextDisplayed(driver, "Account details updated Successfully!!!"));

        log.info("Edit_Acc_02 - Step 08: Verify updated account info displayed");
        verifyEquals(editAccountPage.getTextValueByLabelAtTable(driver, "Account ID"), accID);
        verifyEquals(editAccountPage.getTextValueByLabelAtTable(driver, "Customer ID"), cusID);
        verifyEquals(editAccountPage.getTextValueByLabelAtTable(driver, "Customer Name"), cusName);
        verifyEquals(editAccountPage.getTextValueByLabelAtTable(driver, "Email"), emailAddress);
        verifyEquals(editAccountPage.getTextValueByLabelAtTable(driver, "Account Type"), editAccType);
        verifyEquals(editAccountPage.getTextValueByLabelAtTable(driver, "Current Amount"), String.valueOf(initalDeposit));
    }

    @Test
    public void Payment_03_Transfer_Money_Into_Current_Account() {
        log.info("Deposit_03 - Step 01: Open Deposit page");
        editAccountPage.openSidebarMenuByPageName(driver, "Deposit");
        depositPage = PageGeneratorManager.getDepositPage(driver);

        log.info("Deposit_03 - Step 02: Verify Deposit page displayed");
        verifyTrue(depositPage.isPageTitleByTextDisplayed(driver, "Amount Deposit Form"));

        log.info("Deposit_03 - Step 03: Input to Account No textbox with value: " + accID);
        depositPage.inputToTextboxByName(driver, "accountno", accID);

        log.info("Deposit_03 - Step 04: Input to Amount textbox with value: " + depositAmount);
        depositPage.inputToTextboxByName(driver, "ammount", String.valueOf(depositAmount));

        log.info("Deposit_03 - Step 05: Input to Description textbox with value: " + depositDesc);
        depositPage.inputToTextboxByName(driver, "desc", depositDesc);

        log.info("Deposit_03 - Step 06: Click to 'Submit' button");
        depositPage.clickToButtonByName(driver, "Submit");

        log.info("Deposit_03 - Step 07: Verify Deposit details title displayed");
        verifyTrue(depositPage.isPageTitleByTextDisplayed(driver, "Transaction details of Deposit for Account " + accID));

        log.info("Deposit_03 - Step 08: Verify transaction details displayed correctly");
        verifyEquals(depositPage.getTextValueByLabelAtTable(driver, "Account No"), accID);
        verifyEquals(depositPage.getTextValueByLabelAtTable(driver, "Amount Credited"), String.valueOf(depositAmount));
        verifyEquals(depositPage.getTextValueByLabelAtTable(driver, "Type of Transaction"), "Deposit");
        verifyEquals(depositPage.getTextValueByLabelAtTable(driver, "Description"), depositDesc);

        currentAmount = currentAmount + depositAmount;
        log.info("Deposit_03 - Step 09: Verify current amount displayed with value: " + currentAmount);
        verifyEquals(depositPage.getTextValueByLabelAtTable(driver, "Current Balance"), String.valueOf(currentAmount));
    }

    @Test
    public void Payment_04_Withdrawal_Money_From_Current_Account() {
        log.info("Withdraw_01 - Step 01: Open Withdrawal page");
        editAccountPage.openSidebarMenuByPageName(driver, "Withdrawal");
        withdrawalPage = PageGeneratorManager.getWithdrawalPage(driver);

        log.info("Withdraw_01 - Step 02: Verify Withdrawal page displayed");
        verifyTrue(withdrawalPage.isPageTitleByTextDisplayed(driver, "Amount Withdrawal Form"));

        log.info("Withdraw_01 - Step 03: Input to Account No textbox with value: " + accID);
        withdrawalPage.inputToTextboxByName(driver, "accountno", accID);

        log.info("Withdraw_01 - Step 04: Input to Amount textbox with value: " + withdrawalAmount);
        withdrawalPage.inputToTextboxByName(driver, "ammount", String.valueOf(withdrawalAmount));

        log.info("Withdraw_01 - Step 05: Input to Description textbox with value: " + withdrawalDesc);
        withdrawalPage.inputToTextboxByName(driver, "desc", withdrawalDesc);

        log.info("Withdraw_01 - Step 06: Click to 'Submit' button");
        withdrawalPage.clickToButtonByName(driver, "Submit");

        log.info("Withdraw_01 - Step 07: Verify Withdrawal details title displayed");
        verifyTrue(withdrawalPage.isPageTitleByTextDisplayed(driver, "Transaction details of Withdrawal for Account " + accID));

        log.info("Withdraw_01 - Step 08: Verify transaction details displayed correctly");
        verifyEquals(withdrawalPage.getTextValueByLabelAtTable(driver, "Account No"), accID);
        verifyEquals(withdrawalPage.getTextValueByLabelAtTable(driver, "Amount Debited"), String.valueOf(withdrawalAmount));
        verifyEquals(withdrawalPage.getTextValueByLabelAtTable(driver, "Type of Transaction"), "Withdrawal");
        verifyEquals(withdrawalPage.getTextValueByLabelAtTable(driver, "Description"), withdrawalDesc);

        currentAmount = currentAmount - withdrawalAmount;
        log.info("Withdraw_01 - Step 09: Verify current amount displayed with value: " + currentAmount);
        verifyEquals(withdrawalPage.getTextValueByLabelAtTable(driver, "Current Balance"), String.valueOf(currentAmount));
    }

    @Test
    public void Payment_05_Transfer_Money_Into_Another_Account() {
        log.info("Transfer_05 - Step 01: Open Fund Transfer page");
        withdrawalPage.openSidebarMenuByPageName(driver, "Fund Transfer");
        fundTransferPage = PageGeneratorManager.getFundTransferPage(driver);

        log.info("Transfer_05 - Step 02: Verify Fund Transfer page displayed");
        verifyTrue(fundTransferPage.isPageTitleByTextDisplayed(driver, "Fund transfer"));

        log.info("Transfer_05 - Step 03: Input to Payers account no textbox with value: " + accID);
        fundTransferPage.inputToTextboxByName(driver, "payersaccount", accID);

        log.info("Transfer_05 - Step 04: Input to Payees account no textbox with value: " + payeeID);
        fundTransferPage.inputToTextboxByName(driver, "payeeaccount", payeeID);

        log.info("Transfer_05 - Step 05: Input to Amount textbox with value: " + transferAmount);
        fundTransferPage.inputToTextboxByName(driver, "ammount", String.valueOf(transferAmount));

        log.info("Transfer_05 - Step 06: Input to Description textbox with value: " + transferDesc);
        fundTransferPage.inputToTextboxByName(driver, "desc", transferDesc);

        log.info("Transfer_05 - Step 07: Click to 'Submit' button");
        fundTransferPage.clickToButtonByName(driver, "Submit");

        log.info("Transfer_05 - Step 08: Verify transfer details title displayed");
        verifyTrue(fundTransferPage.isPageTitleByTextDisplayed(driver, "Fund Transfer Details"));

        log.info("Transfer_05 - Step 09: Verify transfer info details displayed");
        verifyEquals(fundTransferPage.getTextValueByLabelAtTable(driver, "From Account Number"), accID);
        verifyEquals(fundTransferPage.getTextValueByLabelAtTable(driver, "To Account Number"), payeeID);
        verifyEquals(fundTransferPage.getTextValueByLabelAtTable(driver, "Amount"), String.valueOf(transferAmount));
        verifyEquals(fundTransferPage.getTextValueByLabelAtTable(driver, "Description"), transferDesc);
        currentAmount = currentAmount - transferAmount;

        log.info("Transfer_05 - Step 10: Open Balance Enquiry page");
        fundTransferPage.openSidebarMenuByPageName(driver, "Balance Enquiry");
        balanceEnquiryPage = PageGeneratorManager.getBalanceEnquiryPage(driver);

        log.info("Transfer_05 - Step 11: Verify Balance Enquiry page displayed");
        verifyTrue(balanceEnquiryPage.isPageTitleByTextDisplayed(driver, "Balance Enquiry Form"));

        log.info("Transfer_05 - Step 12: Input to Account No textbox with value: " + accID);
        balanceEnquiryPage.inputToTextboxByName(driver, "accountno", accID);

        log.info("Transfer_05 - Step 13: Click to 'Submit' button");
        balanceEnquiryPage.clickToButtonByName(driver, "Submit");

        log.info("Transfer_05 - Step 14: Verify Balance details title displayed");
        verifyTrue(balanceEnquiryPage.isPageTitleByTextDisplayed(driver, "Balance Details for Account " + accID));

        log.info("Transfer_05 - Step 15: Verify Balance info details displayed");
        verifyEquals(balanceEnquiryPage.getTextValueByLabelAtTable(driver, "Account No"), accID);
        verifyEquals(balanceEnquiryPage.getTextValueByLabelAtTable(driver, "Type of Account"), editAccType);

        log.info("Transfer_05 - Step 15: Verify Balance displayed with value: " + currentAmount);
        verifyEquals(balanceEnquiryPage.getTextValueByLabelAtTable(driver, "Balance"), String.valueOf(currentAmount));
    }

    @Test
    public void Payment_06_Delete_Account() {
        log.info("Delete_Acc_06 - Step 01: Open Delete account page");
        balanceEnquiryPage.openSidebarMenuByPageName(driver, "Delete Account");
        deleteAccountPage = PageGeneratorManager.getDeleteAccountPage(driver);

        log.info("Delete_Acc_06 - Step 02: Verify Delete account page displayed");
        verifyTrue(deleteAccountPage.isPageTitleByTextDisplayed(driver, "Delete Account Form"));

        log.info("Delete_Acc_06 - Step 03: Input to Account No textbox with value: " + accID);
        deleteAccountPage.inputToTextboxByName(driver, "accountno", accID);

        log.info("Delete_Acc_06 - Step 04: Click to 'Submit' button");
        deleteAccountPage.clickToButtonByName(driver, "Submit");

        log.info("Delete_Acc_06 - Step 05: Accept delete account alert");
        deleteAccountPage.acceptAlert(driver);

        log.info("Delete_Acc_06 - Step 06: Verify Account deleted successully alert displayed");
        verifyEquals(deleteAccountPage.getTextAlert(driver), "Account Deleted Sucessfully");
        deleteAccountPage.acceptAlert(driver);
        managerHomePage = PageGeneratorManager.getManagerHomePage(driver);

        log.info("Delete_Acc_06 - Step 07: Open Edit Account page");
        managerHomePage.openSidebarMenuByPageName(driver, "Edit Account");
        editAccountPage = PageGeneratorManager.getEditAccountPage(driver);

        log.info("Delete_Acc_06 - Step 08: Input to Account ID textbox with value: " + accID);
        editAccountPage.inputToTextboxByName(driver, "accountno", cusID);

        log.info("Delete_Acc_06 - Step 09: Click to 'Submit' button");
        editAccountPage.clickToButtonByName(driver, "Submit");
        editAccountPage.sleepInSecond(1);

        log.info("Delete_Acc_06 - Step 10: Verify Account does not exist alert displayed");
        verifyEquals(editAccountPage.getTextAlert(driver), "Account does not exist");
        editAccountPage.acceptAlert(driver);
    }

    @AfterClass(alwaysRun = true)
    public void cleanBrowser() {
        closeBrowserAndDriver();
    }

    WebDriver driver;
    DataUtil fakeData;
    Environment environment;
    LoginPO loginPage;
    DepositPO depositPage;
    WithdrawalPO withdrawalPage;
    NewAccountPO newAccountPage;
    ManagerHomePO managerHomePage;
    NewCustomerPO newCustomerPage;
    EditAccountPO editAccountPage;
    FundTransferPO fundTransferPage;
    DeleteAccountPO deleteAccountPage;
    BalanceEnquiryPO balanceEnquiryPage;
    String emailAddress, userID, cusID, accID, cusName, gender, birthDate, birthMonth, birthYear, address, city, state, pin, mobile, password, accType, editAccType, depositDesc, withdrawalDesc, payeeID, transferDesc;
    int initalDeposit, depositAmount, currentAmount, withdrawalAmount, transferAmount;
}
