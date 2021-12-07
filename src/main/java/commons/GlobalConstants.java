package commons;

import java.io.File;

public class GlobalConstants {
    public static final long SHORT_TIMEOUT = 5;
    public static final long LONG_TIMEOUT = 30;

    public static final String PROJECT_PATH = System.getProperty("user.dir");
    public static final String UPLOAD_FOLDER_PATH = PROJECT_PATH + File.separator + "uploadFiles" + File.separator;
    public static final String DOWNLOAD_FOLDER_PATH = PROJECT_PATH + File.separator + "downloadFiles";

    public static final String BROWSER_NAME = "vananhle_a4dLAK";
    public static final String BROWSER_AUTOMATE_KEY = "ji9m64Mdrji4MuzUWG2a";
    public static final String BROWSER_STACK_URL = "https://" + BROWSER_NAME + ":" + BROWSER_AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

    public static final String SAUCE_NAME = "oauth-anhlv.enactusftu-f9826";
    public static final String SAUCE_AUTOMATE_KEY = "eb6da976-f344-4b83-875d-24c967a62c00";
    public static final String SAUCE_URL = "https://" + SAUCE_NAME + ":" + SAUCE_AUTOMATE_KEY + "@ondemand.us-west-1.saucelabs.com:443/wd/hub";

    public static final String CROSS_USERNAME = "anhle@wisami.com".replaceAll("@", "%40");
    public static final String CROSS_ACCESS_KEY = "u3e25a1cd8b0a857";
    public static final String CROSS_URL = "http://" + CROSS_USERNAME + ":" + CROSS_ACCESS_KEY + "@hub.crossbrowsertesting.com:80/wd/hub";

    public static final String LAMBDA_USERNAME = "anhlv.enactusftu";
    public static final String LAMBDA_ACCESS_KEY = "Ai4FsT5GbMCDKS2C7JI2mTelFkrtPe5Q0dIo90T2qhw3guvBv0";
    public static final String LAMBDA_URL = "http://" + LAMBDA_USERNAME + ":" + LAMBDA_ACCESS_KEY + "@hub.lambdatest.com/wd/hub";

}
