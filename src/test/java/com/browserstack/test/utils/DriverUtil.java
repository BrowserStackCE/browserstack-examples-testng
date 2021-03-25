package com.browserstack.test.utils;

import com.browserstack.app.utils.OsUtils;

import java.nio.file.Path;

public class DriverUtil {
    private static String driverLocation = Path.of(System.getProperty("user.dir"), "/src/test/resources/drivers").toString();

    public static void setDriverPathVariable() {
        if (OsUtils.isMac()) {
            System.setProperty("webdriver.chrome.driver", Path.of(driverLocation, "/chromedriver").toString());
        }
        if (OsUtils.isWindows()) {
            System.setProperty("webdriver.chrome.driver", Path.of(driverLocation, "/chromedriver.exe").toString());
        }
        if (OsUtils.isUnix()) {
            System.setProperty("webdriver.chrome.driver", Path.of(driverLocation, "/chromedriver").toString());
        }
    }
}
