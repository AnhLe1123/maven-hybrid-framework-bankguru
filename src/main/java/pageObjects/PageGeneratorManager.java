package pageObjects;

import org.openqa.selenium.WebDriver;

public class PageGeneratorManager {

    public static LoginPO getLoginPage(WebDriver driver) {
        return new LoginPO(driver);
    }

    public static ManagerHomePO getManagerHomePage(WebDriver driver) {
        return new ManagerHomePO(driver);
    }

    public static NewCustomerPO getNewCustomerPage(WebDriver driver) {
        return new NewCustomerPO(driver);
    }

    public static EditCustomerPO getEditCustomerPage(WebDriver driver) {
        return new EditCustomerPO(driver);
    }

    public static DeleteCustomerPO getDeleteCustomerPage(WebDriver driver) {
        return new DeleteCustomerPO(driver);
    }
}
