package commons;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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

    private enum BROWSER {
        CHROME, FIREFOX, SAFARI, EDGE_CHRONIUM, EDGE_LEGACY, IE, COC_COC, OPERA, BRAVE, H_CHROME, H_FIREFOX;
    }

    private enum OS {
        WINDOW, MAC_OSX, LINUX;
    }

    private enum PLATFORM {
        ANDROID, IOS, WINDOW_PHONE;
    }

    public WebDriver getWebDriver() {
        return this.driver;
    }

    protected WebDriver getBrowserDriver(String browserName) {
        BROWSER browser = BROWSER.valueOf(browserName.toUpperCase());

        if (browser == BROWSER.FIREFOX) {
            // WebDriverManager.firefoxdriver().setup();
            System.setProperty("webdriver.gecko.driver", GlobalConstants.PROJECT_PATH + getSlash("browserDrivers") + "geckodriver");
            System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, GlobalConstants.PROJECT_PATH + File.separator + "browserLogs" + File.separator + "Firefox.log");
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("-private");
            driver = new FirefoxDriver(options);

        } else if (browser == BROWSER.CHROME) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            File extensionFile = new File(GlobalConstants.PROJECT_PATH + File.separator + "browserExtensions" + File.separator + "ultrasurf_1_6_1.crx");
            options.addExtensions(extensionFile);
            options.setExperimentalOption("useAutomationExtension", false);
            options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-geolocation");
            options.addArguments("--incognito");

            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            options.setExperimentalOption("prefs", prefs);
            driver = new ChromeDriver(options);

        } else if (browser == BROWSER.EDGE_CHRONIUM) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();

        } else if (browser == BROWSER.EDGE_LEGACY) {
            driver = new EdgeDriver();

        } else if (browser == BROWSER.SAFARI) {
            driver = new SafariDriver();

        } else if (browser == BROWSER.IE) {
            WebDriverManager.iedriver().arch32().driverVersion("3.141.59").setup();
            driver = new InternetExplorerDriver();

        } else if (browser == BROWSER.OPERA) {
            WebDriverManager.operadriver().setup();
            driver = new OperaDriver();

        } else if (browser == BROWSER.BRAVE) {
            WebDriverManager.chromedriver().driverVersion("96.0.4664.45").setup();
            ChromeOptions options = new ChromeOptions();
            options.setBinary("/Applications/Brave Browser.app/Contents/MacOS/Brave Browser");
            driver = new ChromeDriver(options);

        } else if (browser == BROWSER.COC_COC) {
            WebDriverManager.chromedriver().driverVersion("93.0.4577.15").setup();
            ChromeOptions options = new ChromeOptions();
            options.setBinary("/Applications/CocCoc.app/Contents/MacOS/CocCoc");
            driver = new ChromeDriver(options);

        } else if (browser == BROWSER.H_CHROME) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.setHeadless(true);
            options.addArguments("window-size=1920x1080");
            driver = new ChromeDriver(options);

        } else if (browser == BROWSER.H_FIREFOX) {
            // WebDriverManager.firefoxdriver().setup();
            System.setProperty("webdriver.gecko.driver", GlobalConstants.PROJECT_PATH + getSlash("browserDrivers") + "geckodriver");
            FirefoxOptions options = new FirefoxOptions();
            options.setHeadless(true);
            options.addArguments("window-size=1920x1080");
            driver = new FirefoxDriver(options);

        } else {
            throw new RuntimeException("Please input the correct browser name!");
        }

        driver.manage().timeouts().implicitlyWait(GlobalConstants.LONG_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        return driver;
    }

    protected WebDriver getBrowserDriver(String browserName, String appUrl) {
        BROWSER browser = BROWSER.valueOf(browserName.toUpperCase());

        if (browser == BROWSER.FIREFOX) {
            // WebDriverManager.firefoxdriver().setup();
            System.setProperty("webdriver.gecko.driver", GlobalConstants.PROJECT_PATH + getSlash("browserDrivers") + "geckodriver");
            System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, GlobalConstants.PROJECT_PATH + File.separator + "browserLogs" + File.separator + "Firefox.log");
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("-private");
            driver = new FirefoxDriver(options);

        } else if (browser == BROWSER.CHROME) {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            File extensionFile = new File(GlobalConstants.PROJECT_PATH + File.separator + "browserExtensions" + File.separator + "ultrasurf_1_6_1.crx");
            options.addExtensions(extensionFile);
            options.setExperimentalOption("useAutomationExtension", false);
            options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-geolocation");
            options.addArguments("--incognito");

            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            options.setExperimentalOption("prefs", prefs);
            driver = new ChromeDriver(options);

        } else if (browser == BROWSER.EDGE_CHRONIUM) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();

        } else if (browser == BROWSER.EDGE_LEGACY) {
            driver = new EdgeDriver();

        } else if (browser == BROWSER.SAFARI) {
            driver = new SafariDriver();

        } else if (browser == BROWSER.IE) {
            WebDriverManager.iedriver().arch32().driverVersion("3.141.59").setup();
            driver = new InternetExplorerDriver();

        } else if (browser == BROWSER.OPERA) {
            WebDriverManager.operadriver().setup();
            driver = new OperaDriver();

        } else if (browser == BROWSER.BRAVE) {
            WebDriverManager.chromedriver().driverVersion("96.0.4664.45").setup();
            ChromeOptions options = new ChromeOptions();
            options.setBinary("/Applications/Brave Browser.app/Contents/MacOS/Brave Browser");
            driver = new ChromeDriver(options);

        } else if (browser == BROWSER.COC_COC) {
            WebDriverManager.chromedriver().driverVersion("93.0.4577.15").setup();
            ChromeOptions options = new ChromeOptions();
            options.setBinary("/Applications/CocCoc.app/Contents/MacOS/CocCoc");
            driver = new ChromeDriver(options);

        } else if (browser == BROWSER.H_CHROME) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.setHeadless(true);
            options.addArguments("window-size=1920x1080");
            driver = new ChromeDriver(options);

        } else if (browser == BROWSER.H_CHROME) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.setHeadless(true);
            options.addArguments("window-size=1920x1080");
            driver = new ChromeDriver(options);

        } else if (browser == BROWSER.H_FIREFOX) {
            // WebDriverManager.firefoxdriver().setup();
            System.setProperty("webdriver.gecko.driver", GlobalConstants.PROJECT_PATH + getSlash("browserDrivers") + "geckodriver");
            FirefoxOptions options = new FirefoxOptions();
            options.setHeadless(true);
            options.addArguments("window-size=1920x1080");
            driver = new FirefoxDriver(options);

        } else {
            throw new RuntimeException("Please input the correct browser name!");
        }

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(appUrl);

        return driver;
    }

    protected int generateFakeNumber() {
        Random rand = new Random();
        return rand.nextInt(9999);
    }

    private String getSlash(String folderName) {
        String seperator = File.separator;
        return seperator + folderName + seperator;
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

    protected void closeBrowserAndDriver() {
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
