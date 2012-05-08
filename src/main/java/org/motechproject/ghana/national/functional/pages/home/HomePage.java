package org.motechproject.ghana.national.functional.pages.home;

import org.motechproject.ghana.national.functional.pages.BasePage;
import org.motechproject.ghana.national.functional.util.PlatformSpecificExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    public HomePage(WebDriver webDriver) {
        super(webDriver);
        elementPoller.waitForElementLinkText("Logout", webDriver);
    }

    public void logout() {
        driver.findElement(By.linkText("Logout")).click();
    }

    public void openCreateFaclityPage() {
        selectMenu("Facility", "newfacility");
    }

    public void openSearchFaclityPage() {
        selectMenu("Facility", "searchfacility");
    }

    public void openCreateStaffPage() {
        selectMenu("Staff", "newstaff");
    }

    public void openSearchStaffPage() {
        selectMenu("Staff", "searchstaff");
    }

    public void openCreatePatientPage() {
        selectMenu("Patient", "newpatient");
    }

    public void openSearchPatientPage() {
        selectMenu("Patient", "searchpatient");
    }

    public boolean isLogoutLinkVisible() {
        try {
            return driver.findElement(By.linkText("Logout")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void selectMenu(final String menuName, final String menuItemId) {
        platformSpecificExecutor.execute(new PlatformSpecificExecutor.Code() {
            @Override
            public void windows() {
                WebElement webElement = javascriptExecutor.getElementById(menuItemId, driver);
                String link = webElement.getAttribute("href");
                driver.get(link);
            }

            @Override
            public void linux() {
                javascriptExecutor.selectMenu(driver, menuName, menuItemId);
            }
        });
    }
}


