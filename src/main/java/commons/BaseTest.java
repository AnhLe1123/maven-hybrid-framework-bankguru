package commons;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import factoryEnvironment.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.Reporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import reportConfig.VerificationFailures;

public class BaseTest {
    private WebDriver driver;
    protected final Log log;

    protected BaseTest() {
        log = LogFactory.getLog(getClass());
    }

    public WebDriver getWebDriver() {
        return this.driver;
    }

    protected WebDriver getBrowserDriver(String envName, String serverName, String browserName, String ipAddress, String portNumber, String osName, String osVersion) {
        switch (envName) {
            case "local":
                driver = new LocalFactory(browserName).createDriver();
                break;
            case "grid":
                driver = new GridFactory(browserName, ipAddress, portNumber).createDriver();
                break;
            case "browserStack":
                driver = new BrowserstackFactory(browserName, osName, osVersion).createDriver();
                break;
            case "saucelab":
                driver = new SaucelabFactory(browserName, osName).creaDriver();
                break;
            case "crossBrowser":
                driver = new CrossbrowserFactory(browserName, osName).createDriver();
                break;
            case "lambda":
                driver = new LambdaFactory(browserName, osName).createDriver();
                break;
            default:
                driver = new LocalFactory(browserName).createDriver();
                break;
        }

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(serverName);
        return driver;
    }

    protected int generateFakeNumber() {
        Random rand = new Random();
        return rand.nextInt(9999);
    }

    private boolean checkTrue(boolean condition) {
        boolean pass = true;
        try {
            if (condition == true) {
                log.info(" -------------------------- PASSED -------------------------- ");
            } else {
                log.info(" -------------------------- FAILED -------------------------- ");
            }
            Assert.assertTrue(condition);
        } catch (Throwable e) {
            pass = false;

            // Add issues in ReportNG
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    protected boolean verifyTrue(boolean condition) {
        return checkTrue(condition);
    }

    private boolean checkFailed(boolean condition) {
        boolean pass = true;
        try {
            if (condition == false) {
                log.info(" -------------------------- PASSED -------------------------- ");
            } else {
                log.info(" -------------------------- FAILED -------------------------- ");
            }
            Assert.assertFalse(condition);
        } catch (Throwable e) {
            pass = false;
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    protected boolean verifyFalse(boolean condition) {
        return checkFailed(condition);
    }

    private boolean checkEquals(Object actual, Object expected) {
        boolean pass = true;
        try {
            Assert.assertEquals(actual, expected);
            log.info(" -------------------------- PASSED -------------------------- ");
        } catch (Throwable e) {
            pass = false;
            log.info(" -------------------------- FAILED -------------------------- ");
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    protected boolean verifyEquals(Object actual, Object expected) {
        return checkEquals(actual, expected);
    }

    protected void closeBrowserAndDriver(String envName) {
        if (envName.equals("local") || envName.equals("grid")) {
            String cmd = "";
            try {
                String osName = System.getProperty("os.name").toLowerCase();
                log.info("OS name = " + osName);

                String driverInstanceName = driver.toString().toLowerCase();
                log.info("Driver instance name = " + driverInstanceName);

                if (driverInstanceName.contains("chrome")) {
                    if (osName.contains("window")) {
                        cmd = "taskkill /F /FI \"IMAGENAME eq chromedriver*\"";
                    } else {
                        cmd = "pkill chromedriver";
                    }
                } else if (driverInstanceName.contains("internetexplorer")) {
                    if (osName.contains("window")) {
                        cmd = "taskkill /F /FI \"IMAGENAME eq IEDriverServer*\"";
                    }
                } else if (driverInstanceName.contains("firefox")) {
                    if (osName.contains("windows")) {
                        cmd = "taskkill /F /FI \"IMAGENAME eq geckodriver*\"";
                    } else {
                        cmd = "pkill geckodriver";
                    }
                } else if (driverInstanceName.contains("edge")) {
                    if (osName.contains("window")) {
                        cmd = "taskkill /F /FI \"IMAGENAME eq msedgedriver*\"";
                    } else {
                        cmd = "pkill msedgedriver";
                    }
                } else if (driverInstanceName.contains("opera")) {
                    if (osName.contains("window")) {
                        cmd = "taskkill /F /FI \"IMAGENAME eq operadriver*\"";
                    } else {
                        cmd = "pkill operadriver";
                    }
                } else if (driverInstanceName.contains("safari")) {
                    if (osName.contains("mac")) {
                        cmd = "pkill safaridriver";
                    }
                }

                if (driver != null) {
                    driver.manage().deleteAllCookies();
                    driver.quit();
                }
            } catch (Exception e) {
                log.info(e.getMessage());
            } finally {
                try {
                    Process process = Runtime.getRuntime().exec(cmd);
                    process.waitFor();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            driver.quit();
        }
    }

    protected void showBrowserConsoleLogs(WebDriver driver) {
        if (driver.toString().contains("chrome")) {
            LogEntries logs = driver.manage().logs().get("browser");
            List<LogEntry> logList = logs.getAll();
            for (LogEntry logging : logList) {
                log.info("---------------- " + logging.getLevel().toString() + " ---------------- \n" + logging.getMessage());
            }
        }
    }
}
