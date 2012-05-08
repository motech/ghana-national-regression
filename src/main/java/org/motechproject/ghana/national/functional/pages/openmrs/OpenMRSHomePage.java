package org.motechproject.ghana.national.functional.pages.openmrs;

import org.motechproject.ghana.national.functional.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OpenMRSHomePage extends BasePage {

    @FindBy(id = "userLogout")
    private WebElement userLogout;

    public OpenMRSHomePage(WebDriver webDriver) {
        super(webDriver);
        elementPoller.waitFor(webDriver, By.id("userLogout"));
        PageFactory.initElements(webDriver, this);
    }

    public void logout() {
        userLogout.findElement(By.tagName("a")).click();
    }
}
