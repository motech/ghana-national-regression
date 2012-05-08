package org.motechproject.ghana.national.functional.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FireFoxWebDriver extends BaseWebDriver {

    @Value("#{functionalTestProperties['firefox.display']}")
    private String firefoxDisplay;

    public WebDriver getDriver() {
        if (driver == null) {
            FirefoxProfile profile = new FirefoxProfile();
            profile.setEnableNativeEvents(true);
            FirefoxBinary firefoxBinary = new FirefoxBinary();
            firefoxBinary.setEnvironmentProperty("DISPLAY", System.getProperty("functional.test.display", ":0.0"));
            driver = new FirefoxDriver(firefoxBinary, profile);
        }
        return driver;
    }
}
