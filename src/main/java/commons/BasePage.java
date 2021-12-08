package commons;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageUIs.BasePageUI;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BasePage {

    public static BasePage getBasePage() {
        return new BasePage();
    }

    public void openUrl(WebDriver driver, String pageUrl) {
        driver.get(pageUrl);
    }

    protected void getPageTitle(WebDriver driver) {
        driver.getTitle();
    }

    protected void getPageUrl(WebDriver driver) {
        driver.getCurrentUrl();
    }

    protected void getPageSource(WebDriver driver) {
        driver.getPageSource();
    }

    protected void backToPage(WebDriver driver) {
        driver.navigate().back();
    }

    protected void forwardToPage(WebDriver driver) {
        driver.navigate().forward();
    }

    public void refreshCurrentPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    public Set<Cookie> getAllCookies(WebDriver driver) {
        return driver.manage().getCookies();
    }

    protected void setAllCookies(WebDriver driver, Set<Cookie> allCookies) {
        for (Cookie cookie : allCookies) {
            driver.manage().addCookie(cookie);
        }
    }

    protected Alert waitAlertPresence(WebDriver driver) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        return explicitWait.until(ExpectedConditions.alertIsPresent());
    }

    public void acceptAlert(WebDriver driver) {
        alert = waitAlertPresence(driver);
        alert.accept();
        sleepInSecond(2);
    }

    protected void cancelAlert(WebDriver driver) {
        alert = waitAlertPresence(driver);
        alert.dismiss();
    }

    protected void sendKeyToAlert(WebDriver driver, String value) {
        alert = waitAlertPresence(driver);
        alert.sendKeys(value);
    }

    public String getTextAlert(WebDriver driver) {
        alert = waitAlertPresence(driver);
        return alert.getText();
    }

    protected void switchWindowByID(WebDriver driver, String parentID) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String id : allWindows) {
            if (!id.equals(parentID)) {
                driver.switchTo().window(id);
            }
        }
    }

    protected void switchToWindowByTitle(WebDriver driver, String expectedTitle) {
        Set<String> allWindows = driver.getWindowHandles();

        for (String id : allWindows) {
            driver.switchTo().window(id);
            String windowTitle = driver.getTitle();
            if (windowTitle.equals(expectedTitle)) {
                break;
            }
        }
    }

    protected void closeAllWindowsWithoutParent(WebDriver driver, String ParentID) {
        Set<String> allWindows = driver.getWindowHandles();

        for (String id : allWindows) {
            if (!id.equals(ParentID)) {
                driver.switchTo().window(id);
                driver.close();
            }
        }
        driver.switchTo().window(ParentID);
    }

    public void sleepInSecond(long timeoutInSecond) {
        try {
            Thread.sleep(timeoutInSecond * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private By getByXpath(String locator) {
        return By.xpath(locator);
    }

    private WebElement getElement(WebDriver driver, String locator) {
        return driver.findElement(getByXpath(locator));
    }

    private WebElement getElement(WebDriver driver, String locator, String... params) {
        return driver.findElement(getByXpath(getDynamicLocator(locator, params)));
    }

    private List<WebElement> getElements(WebDriver driver, String locator) {
        return driver.findElements(getByXpath(locator));
    }

    private String getDynamicLocator(String locator, String... params) {
        return String.format(locator, (Object[]) params);
    }

    protected void clickToElement(WebDriver driver, String locator) {
        if (driver.toString().contains("internet explorer")) {
            clickToElementByJS(driver, locator);
            sleepInSecond(2);

        } else {
            getElement(driver, locator).click();
        }
    }

    protected void clickToElement(WebDriver driver, String locator, String... params) {
        if (driver.toString().contains("internet explorer")) {
            clickToElementByJS(driver, getDynamicLocator(locator, params));
            sleepInSecond(2);

        } else {
            getElement(driver, getDynamicLocator(locator, params)).click();
        }
    }

    protected void sendkeyToElement(WebDriver driver, String locator, String value) {
        getElement(driver, locator).clear();
        getElement(driver, locator).sendKeys(value);
    }

    protected void sendkeyToElement(WebDriver driver, String locator, String value, String... params) {
        locator = getDynamicLocator(locator, params);
        getElement(driver, locator).clear();
        getElement(driver, locator).sendKeys(value);
    }

    protected void selectItemInDropdownByText(WebDriver driver, String locator, String itemText) {
        select = new Select(getElement(driver, locator));
        select.selectByVisibleText(itemText);
    }

    protected void selectItemInDropdownByText(WebDriver driver, String locator, String itemText, String... params) {
        locator = getDynamicLocator(locator, params);
        select = new Select(getElement(driver, locator));
        select.selectByVisibleText(itemText);
    }

    protected String getSelectedItemInDropdown(WebDriver driver, String locator) {
        select = new Select(getElement(driver, locator));
        return select.getFirstSelectedOption().getText();
    }

    protected String getSelectedItemInDropdown(WebDriver driver, String locator, String... params) {
        select = new Select(getElement(driver, getDynamicLocator(locator, params)));
        return select.getFirstSelectedOption().getText();
    }

    protected boolean isDropdownMultiple(WebDriver driver, String locator) {
        select = new Select(getElement(driver, locator));
        return select.isMultiple();
    }

    protected void selectItemInCustomDropdown(WebDriver driver, String parentLocator, String childItemLocator, String expectedItem) {
        getElement(driver, parentLocator).click();
        sleepInSecond(1);

        explicitWait = new WebDriverWait(driver, longTimeout);
        List<WebElement> allItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(childItemLocator)));

        for (WebElement item : allItems) {
            if (item.getText().trim().equals(expectedItem)) {
                if (!item.isDisplayed()) {
                    jsExecutor = (JavascriptExecutor) driver;
                    jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
                    sleepInSecond(1);
                }

                item.click();
                sleepInSecond(1);
                break;
            }
        }
    }

    protected void enterAndSelectItemInCustomDropdown(WebDriver driver, String parentXpath, String textboxXpath, String childExpath, String expectedItem) {
        driver.findElement(By.xpath(parentXpath)).click();
        sleepInSecond(1);

        driver.findElement(By.xpath(textboxXpath)).sendKeys(expectedItem);
        sleepInSecond(1);

        List<WebElement> allItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childExpath)));

        for(WebElement item : allItems) {
            if(item.getText().trim().equals(expectedItem)) {
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
                sleepInSecond(2);
                item.click();
                break;
            }
        }
    }

    protected void enterAndTabToCustomDropdown(WebDriver driver, String textboxXpath, String expectedItem) {
        driver.findElement(By.xpath(textboxXpath)).sendKeys(expectedItem);
        sleepInSecond(1);

        driver.findElement(By.xpath(textboxXpath)).sendKeys(Keys.TAB);
        sleepInSecond(1);
    }

    protected String getElementAttribute(WebDriver driver, String locator, String attributeName) {
        return getElement(driver, locator).getAttribute(attributeName);
    }

    protected String getElementAttribute(WebDriver driver, String locator, String attributeName, String... params) {
        return getElement(driver, getDynamicLocator(locator, params)).getAttribute(attributeName);
    }

    protected String getElementText(WebDriver driver, String locator) {
        return getElement(driver, locator).getText();
    }

    protected String getElementText(WebDriver driver, String locator, String... params) {
        return getElement(driver, getDynamicLocator(locator, params)).getText();
    }

    protected String getCssValue(WebDriver driver, String locator, String value) {
        return getElement(driver, locator).getCssValue(value);
    }

    protected String convertRgbaToHexa(String rgbaValue) {
        return Color.fromString(rgbaValue).asHex();
    }

    protected int getElementSize(WebDriver driver, String locator) {
        return getElements(driver, locator).size();
    }

    protected int getElementSize(WebDriver driver, String locator, String... params) {
        return getElements(driver, getDynamicLocator(locator, params)).size();
    }

    protected void checkToCheckboxOrRadio(WebDriver driver, String locator) {
        if (!isElementSelected(driver, locator)) {
            if (driver.toString().contains("internet explorer")) {
                clickToElementByJS(driver, locator);
                sleepInSecond(2);

            } else {
                getElement(driver, locator).click();
            }
        }
    }

    protected void checkToCheckboxOrRadio(WebDriver driver, String locator, String... params) {
        locator = getDynamicLocator(locator, params);
        if (!isElementSelected(driver, locator)) {
            if (driver.toString().contains("internet explorer")) {
                clickToElementByJS(driver, locator);
                sleepInSecond(2);

            } else {
                getElement(driver, locator).click();
            }
        }
    }

    protected void uncheckToCheckbox(WebDriver driver, String locator) {
        if (isElementSelected(driver, locator)) {
            if (driver.toString().contains("internet explorer")) {
                clickToElementByJS(driver, locator);
                sleepInSecond(2);

            } else {
                getElement(driver, locator).click();
            }
        }
    }

    protected void uncheckToCheckbox(WebDriver driver, String locator, String... params) {
        locator = getDynamicLocator(locator, params);
        if (isElementSelected(driver, locator)) {
            if (driver.toString().contains("internet explorer")) {
                clickToElementByJS(driver, locator);
                sleepInSecond(2);

            } else {
                getElement(driver, locator).click();
            }
        }
    }

    protected boolean isElementDisplayed(WebDriver driver, String locator) {
        try {
            return getElement(driver, locator).isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected boolean isElementDisplayed(WebDriver driver, String locator, String... params) {
        try {
            return getElement(driver, getDynamicLocator(locator, params)).isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected boolean isElementUndisplayed(WebDriver driver, String locator) {
        System.out.println("Start time = " + new Date().toString());
        overrideGlobalTimeout(driver, shortTimeout);
        List<WebElement> elements = getElements(driver, locator);
        overrideGlobalTimeout(driver, longTimeout);

        if (elements.size() == 0) {
            System.out.println("Element not in DOM");
            System.out.println("End time = " + new Date().toString());
            return true;
        } else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
            System.out.println("Element in DOM but not visible in UI");
            System.out.println("End time = " + new Date().toString());
            return true;
        } else {
            System.out.println("Element in DOM and visible in UI");
            return false;
        }
    }

    protected boolean isElementUndisplayed(WebDriver driver, String locator, String... params) {
        System.out.println("Start time = " + new Date().toString());
        overrideGlobalTimeout(driver, shortTimeout);
        List<WebElement> elements = getElements(driver, getDynamicLocator(locator, params));
        overrideGlobalTimeout(driver, longTimeout);

        if (elements.size() == 0) {
            System.out.println("Element not in DOM");
            System.out.println("End time = " + new Date().toString());
            return true;
        } else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
            System.out.println("Element in DOM but not visible in UI");
            System.out.println("End time = " + new Date().toString());
            return true;
        } else {
            System.out.println("Element in DOM and visible in UI");
            return false;
        }
    }

    protected void overrideGlobalTimeout(WebDriver driver, long timeout) {
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
    }

    protected boolean isElementSelected(WebDriver driver, String locator) {
        return getElement(driver, locator).isSelected();
    }

    protected boolean isElementSelected(WebDriver driver, String locator, String... params) {
        return getElement(driver, getDynamicLocator(locator, params)).isSelected();
    }

    protected boolean isElementEnabled(WebDriver driver, String locator) {
        return getElement(driver, locator).isEnabled();
    }

    protected boolean isElementEnabled(WebDriver driver, String locator, String... params) {
        return getElement(driver, getDynamicLocator(locator, params)).isEnabled();
    }

    protected WebDriver switchToIframeByElement(WebDriver driver, String locator) {
        return driver.switchTo().frame(getElement(driver, locator));
    }

    protected WebDriver switchToDefaultContent(WebDriver driver) {
        return driver.switchTo().defaultContent();
    }

    protected void hoverToElement(WebDriver driver, String locator) {
        action = new Actions(driver);
        action.moveToElement(getElement(driver, locator)).perform();
    }

    protected void hoverToElement(WebDriver driver, String locator, String... params) {
        action = new Actions(driver);
        action.moveToElement(getElement(driver, getDynamicLocator(locator, params))).perform();
    }

    protected void doubleClickToElement(WebDriver driver, String locator) {
        action = new Actions(driver);
        action.doubleClick(getElement(driver, locator)).perform();
    }

    protected void rightClickToElement(WebDriver driver, String locator) {
        action = new Actions(driver);
        action.contextClick(getElement(driver, locator)).perform();
    }

    protected void dragAndDropElement(WebDriver driver, String sourceLocator, String targetLocator) {
        action = new Actions(driver);
        action.dragAndDrop(getElement(driver, sourceLocator), getElement(driver, targetLocator)).perform();
    }

    protected void pressKeyToElement(WebDriver driver, String locator, Keys key) {
        action = new Actions(driver);
        action.sendKeys(getElement(driver, locator), key).perform();
    }

    protected void pressKeyToElement(WebDriver driver, String locator, Keys key, String... params) {
        action = new Actions(driver);
        action.sendKeys(getElement(driver, getDynamicLocator(locator, params)), key).perform();
    }

    protected Object executeForBrowser(WebDriver driver, String javaScript) {
        jsExecutor = (JavascriptExecutor) driver;
        return jsExecutor.executeScript(javaScript);
    }

    protected String getInnerText(WebDriver driver) {
        jsExecutor = (JavascriptExecutor) driver;
        return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
    }

    protected boolean areExpectedTextInInnerText(WebDriver driver, String textExpected) {
        jsExecutor = (JavascriptExecutor) driver;
        String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
        return textActual.equals(textExpected);
    }

    protected void scrollToBottomPage(WebDriver driver) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    protected void scrollToTopPage(WebDriver driver) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
    }

    protected void navigateToUrlByJS(WebDriver driver, String url) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.location = '" + url + "'");
    }

    protected void highlightElement(WebDriver driver, String locator) {
        jsExecutor = (JavascriptExecutor) driver;
        WebElement element = getElement(driver, locator);
        String originalStyle = element.getAttribute("style");
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
        sleepInSecond(1);
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
    }

    protected void clickToElementByJS(WebDriver driver, String locator) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", getElement(driver, locator));
    }

    protected void clickToElementByJS(WebDriver driver, String locator, String... params) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", getElement(driver, getDynamicLocator(locator, params)));
    }

    protected void scrollToElement(WebDriver driver, String locator) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(driver, locator));
    }

    protected void scrollToElement(WebDriver driver, String locator, String... params) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(driver, getDynamicLocator(locator, params)));
    }

    protected void sendkeyToElementByJS(WebDriver driver, String locator, String value) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(driver, locator));
    }

    protected void sendkeyToElementByJS(WebDriver driver, String locator, String value, String... params) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(driver, getDynamicLocator(locator, params)));
    }

    protected void removeAttributeInDOM(WebDriver driver, String locator, String attributeRemove) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(driver, locator));
    }

    protected boolean isJQueryAjaxLoadedSuccess(WebDriver driver) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        jsExecutor = (JavascriptExecutor) driver;
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return (Boolean) jsExecutor.executeScript("return (window.jQuery !=null) && (jQuery.active === 0);");
            }
        };
        return explicitWait.until(jQueryLoad);
    }

    protected boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        jsExecutor = (JavascriptExecutor) driver;

        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };

        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
            }
        };

        return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
    }

    protected String getElementValidationMessage(WebDriver driver, String locator) {
        jsExecutor = (JavascriptExecutor) driver;
        return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(driver, locator));
    }

    protected boolean isImageLoaded(WebDriver driver, String locator) {
        jsExecutor = (JavascriptExecutor) driver;
        boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", getElement(driver, locator));
        if (status) {
            return true;
        } else {
            return false;
        }
    }

    protected void waitForElementVisible(WebDriver driver, String locator) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(locator)));
    }

    protected void waitForElementVisible(WebDriver driver, String locator, String... params) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(getDynamicLocator(locator, params))));
    }

    protected void waitForAllElementsVisible(WebDriver driver, String locator) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(locator)));
    }

    protected void waitForAllElementsVisible(WebDriver driver, String locator, String... params) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(getDynamicLocator(locator, params))));
    }

    protected void waitForElementClickable(WebDriver driver, String locator) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(locator)));
    }

    protected void waitForElementClickable(WebDriver driver, String locator, String... params) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(getDynamicLocator(locator, params))));
    }

    protected void waitForElementInvisible(WebDriver driver, String locator) {
        explicitWait = new WebDriverWait(driver, shortTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(locator)));
    }

    protected void waitForElementInvisible(WebDriver driver, String locator, String... params) {
        explicitWait = new WebDriverWait(driver, shortTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(getDynamicLocator(locator, params))));
    }

    public void inputToTextboxByName(WebDriver driver, String textboxName, String value) {
        waitForElementVisible(driver, BasePageUI.TEXTBOX_BY_NAME, textboxName);
        clickToElement(driver, BasePageUI.TEXTBOX_BY_NAME, textboxName);
        sendkeyToElement(driver,BasePageUI.TEXTBOX_BY_NAME, value, textboxName);
    }

    public void inputToTextAreaByName(WebDriver driver, String textAreaName, String value) {
        waitForElementVisible(driver, BasePageUI.TEXTAREA_BY_NAME, textAreaName);
        clickToElement(driver, BasePageUI.TEXTAREA_BY_NAME, textAreaName);
        sendkeyToElement(driver,BasePageUI.TEXTAREA_BY_NAME, value, textAreaName);
    }

    public void clickToButtonByName(WebDriver driver, String btnValue) {
        waitForElementClickable(driver, BasePageUI.BUTTON_BY_VALUE, btnValue);
        clickToElement(driver, BasePageUI.BUTTON_BY_VALUE, btnValue);
    }

    public void openSidebarMenuByPageName(WebDriver driver, String pageName) {
        waitForElementClickable(driver,BasePageUI.SIDEBAR_MENU_BY_PAGE_NAME, pageName);
        clickToElement(driver,BasePageUI.SIDEBAR_MENU_BY_PAGE_NAME, pageName);
    }

    public boolean isPageTitleByTextDisplayed(WebDriver driver, String pageTitle) {
        waitForElementVisible(driver, BasePageUI.PAGE_TITLE_BY_TEXT, pageTitle);
        return isElementDisplayed(driver, BasePageUI.PAGE_TITLE_BY_TEXT, pageTitle);
    }

    public void checkToRadioButtonByValue(WebDriver driver, String btnValue) {
        waitForElementClickable(driver,BasePageUI.RADIO_BUTTON_BY_VALUE, btnValue);
        checkToCheckboxOrRadio(driver,BasePageUI.RADIO_BUTTON_BY_VALUE, btnValue);
    }

    public String getTextValueByLabelAtTable(WebDriver driver, String label) {
        waitForElementVisible(driver,BasePageUI.TABLE_VALUE_BY_LABEL, label);
        return getElementText(driver,BasePageUI.TABLE_VALUE_BY_LABEL, label);
    }

    public boolean isTextboxByNameEnabled(WebDriver driver, String textboxName) {
        waitForElementVisible(driver, BasePageUI.TEXTBOX_BY_NAME, textboxName);
        return isElementEnabled(driver, BasePageUI.TEXTBOX_BY_NAME, textboxName);
    }

    public void selectOptionInDropdownByName(WebDriver driver, String dropdownName, String textValue) {
        waitForElementVisible(driver, BasePageUI.DROPDOWN_BY_NAME, dropdownName);
        selectItemInDropdownByText(driver, BasePageUI.DROPDOWN_BY_NAME, textValue, dropdownName);
    }

    public void pressTabKeyInTextboxByName(WebDriver driver, String textboxName) {
        waitForElementVisible(driver, BasePageUI.TEXTBOX_BY_NAME, textboxName);
        pressKeyToElement(driver, BasePageUI.TEXTBOX_BY_NAME, Keys.TAB, textboxName);
    }

    public void pressTabKeyInTextAreaByName(WebDriver driver, String textAreaName) {
        waitForElementVisible(driver, BasePageUI.TEXTAREA_BY_NAME, textAreaName);
        pressKeyToElement(driver, BasePageUI.TEXTAREA_BY_NAME, Keys.TAB, textAreaName);
    }

    public String getErrorMessageByFieldName(WebDriver driver, String fieldName) {
        waitForElementVisible(driver, BasePageUI.ERROR_MESSAGE_BY_FIELD_NAME, fieldName);
        return getElementText(driver, BasePageUI.ERROR_MESSAGE_BY_FIELD_NAME, fieldName);
    }

    private Alert alert;
    private Select select;
    private Actions action;
    private WebDriverWait explicitWait;
    private JavascriptExecutor jsExecutor;
    private long longTimeout = GlobalConstants.LONG_TIMEOUT;
    private long shortTimeout = GlobalConstants.SHORT_TIMEOUT;
}

