package com.bankguru.payment;

import commons.BaseTest;
import envConfig.Environment;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utilities.DataUtil;

public class Payment_02_Deposit_Transfer_Withdraw extends BaseTest {
    @Parameters({ "browser", "env" })
    @BeforeClass
    public void initBrowser(String browserName, String envName) {
        ConfigFactory.setProperty("env", envName);
        environment = ConfigFactory.create(Environment.class);
        driver = getBrowserDriver(browserName, environment.appUrl());
        fakeData = DataUtil.getData();
    }

    @Test
    public void Payment_01_Create_Account_And_Inital_Deposit() {

    }

    @Test
    public void Payment_02_Edit_Account() {

    }

    @Test
    public void Payment_03_Transfer_Money_Into_Current_Account() {

    }

    @Test
    public void Payment_04_Withdraw_Money_From_Current_Account() {

    }

    @Test
    public void Payment_05_Transfer_Money_Into_Another_Account() {

    }

    @Test
    public void Payment_06_Delete_Account() {

    }

    @AfterClass(alwaysRun = true)
    public void cleanBrowser() {
        closeBrowserAndDriver();
    }

    WebDriver driver;
    DataUtil fakeData;
    Environment environment;
}
