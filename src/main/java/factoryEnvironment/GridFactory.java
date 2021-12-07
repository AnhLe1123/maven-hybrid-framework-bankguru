package factoryEnvironment;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import commons.GlobalConstants;
import io.github.bonigarcia.wdm.WebDriverManager;

public class GridFactory {
	private WebDriver driver;
	private String browserName;
	private String ipAddress;
	private String portNumber;
	
	public GridFactory(String browserName, String ipAddress, String portNumber) {
		this.browserName = browserName;
		this.ipAddress = ipAddress;
		this.portNumber = portNumber;
	}

	public WebDriver createDriver() {
		Browser browser = Browser.valueOf(browserName.toUpperCase());
		DesiredCapabilities capability = null;

		if (browser == Browser.FIREFOX) {
			// WebDriverManager.firefoxdriver().setup();
			System.setProperty("webdriver.gecko.driver", GlobalConstants.PROJECT_PATH  + File.separator + "browserDrivers" + File.separator + "geckodriver");
			System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, GlobalConstants.PROJECT_PATH + File.separator + "browserLogs" + File.separator + "Firefox.log");
			
			capability = DesiredCapabilities.firefox();
			capability.setBrowserName("firefox");
			capability.setPlatform(Platform.MAC);
			
			FirefoxOptions options = new FirefoxOptions();
			options.merge(capability);
			
		} else if (browser == Browser.CHROME) {
			WebDriverManager.chromedriver().setup();
			
			capability = DesiredCapabilities.chrome();
			capability.setBrowserName("chrome");
			capability.setPlatform(Platform.MAC);
			
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
			
			options.merge(capability);
			
		} else if (browser == Browser.EDGE_CHROMIUM) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			
		} else if (browser == Browser.EDGE_LEGACY) {
			driver = new EdgeDriver();
			
		} else if (browser == Browser.SAFARI) {
			driver = new SafariDriver();
			
		} else if (browser == Browser.IE) {
			WebDriverManager.iedriver().arch32().driverVersion("3.141.59").setup();
			driver = new InternetExplorerDriver();
			
		} else if (browser == Browser.OPERA) {
			WebDriverManager.operadriver().setup();
			driver = new OperaDriver();
			
		} else if (browser == Browser.BRAVE) {
			WebDriverManager.chromedriver().driverVersion("96.0.4664.45").setup();
			ChromeOptions options = new ChromeOptions();
			options.setBinary("/Applications/Brave Browser.app/Contents/MacOS/Brave Browser");
			driver = new ChromeDriver(options);
			
		} else if (browser == Browser.COC_COC) {
			WebDriverManager.chromedriver().driverVersion("93.0.4577.15").setup();
			ChromeOptions options = new ChromeOptions();
			options.setBinary("/Applications/CocCoc.app/Contents/MacOS/CocCoc");
			driver = new ChromeDriver(options);
			
		} else if (browser == Browser.H_CHROME) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.setHeadless(true);
			options.addArguments("window-size=1920x1080");
			driver = new ChromeDriver(options);
			
		} else if (browser == Browser.H_FIREFOX) {
			// WebDriverManager.firefoxdriver().setup();
			System.setProperty("webdriver.gecko.driver", GlobalConstants.PROJECT_PATH + File.separator + "browserDrivers" + File.separator + "geckodriver");
			FirefoxOptions options = new FirefoxOptions();
			options.setHeadless(true);
			options.addArguments("window-size=1920x1080");
			driver = new FirefoxDriver(options);
			
		} else {
			throw new RuntimeException("Please input the correct browser name!");
		}
		
		try {
			driver = new RemoteWebDriver(new URL(String.format("http://%s:%s/wd/hub", ipAddress, portNumber)), capability);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return driver;
	}
}
