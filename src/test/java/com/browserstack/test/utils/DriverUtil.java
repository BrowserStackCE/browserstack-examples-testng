package com.browserstack.test.utils;

import com.browserstack.app.utils.OsUtils;

import java.nio.file.FileSystems;

public class DriverUtil {
    private static final String driverLocation = FileSystems.getDefault().getPath(System.getProperty("user.dir"), "/src/test/resources/drivers").toString();

    public static void setDriverPathVariable() {
        if (OsUtils.isMac()) {
            System.setProperty("webdriver.chrome.driver", FileSystems.getDefault().getPath(driverLocation, "/chromedriver").toString());
        }
        if (OsUtils.isWindows()) {
            System.setProperty("webdriver.chrome.driver", FileSystems.getDefault().getPath(driverLocation, "/chromedriver.exe").toString());
        }
        if (OsUtils.isUnix()) {
            System.setProperty("webdriver.chrome.driver", FileSystems.getDefault().getPath(driverLocation, "/chromedriver").toString());
        }
    }
}
