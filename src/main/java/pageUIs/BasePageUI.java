package pageUIs;

public class BasePageUI {
    public static final String TEXTBOX_BY_NAME = "//input[@name='%s']";
    public static final String TEXTAREA_BY_NAME = "//textarea[@name='%s']";
    public static final String BUTTON_BY_VALUE = "//input[@value='%s']";
    public static final String SIDEBAR_MENU_BY_PAGE_NAME = "//ul[@class='menusubnav']//a[text()='%s']";
    public static final String PAGE_TITLE_BY_TEXT = "//p[@class='heading3' and text()='%s']";
    public static final String RADIO_BUTTON_BY_VALUE = "//input[@type='radio' and @value='%s']";
    public static final String TABLE_VALUE_BY_LABEL = "//tr//td[contains(text(),'%s')]/following-sibling::td";
    public static final String DROPDOWN_BY_NAME = "//select[@name='%s']";
}
