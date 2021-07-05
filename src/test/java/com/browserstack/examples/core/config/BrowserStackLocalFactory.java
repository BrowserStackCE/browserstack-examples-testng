package com.browserstack.examples.core.config;

import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.browserstack.local.Local;

public class BrowserStackLocalFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrowserStackLocalFactory.class);

    private static BrowserStackLocalFactory instance;

    private final Local local = new Local();
    private final String localIdentifier = RandomStringUtils.randomAlphabetic(8);

    private BrowserStackLocalFactory(Map<String, String> args) {
        try {
            args.put("localIdentifier", localIdentifier);
            local.start(args);
            LOGGER.info("Started BrowserStack Local with identifier {}.", localIdentifier);
        } catch (Exception e) {
            LOGGER.error("Initialization of BrowserStack Local with identifier {} failed.", localIdentifier);
            throw new RuntimeException("Initialization of BrowserStack Local failed.", e);
        }
    }

    public static void createInstance(Map<String, String> args) {
        if (instance == null) {
            synchronized (BrowserStackLocalFactory.class) {
                if (instance == null) {
                    instance = new BrowserStackLocalFactory(args);
                    Runtime.getRuntime().addShutdownHook(new Closer(instance.local));
                }
            }
        }
    }

    public static BrowserStackLocalFactory getInstance() {
        return instance;
    }

    public String getLocalIdentifier() {
        return instance.localIdentifier;
    }

    private static class Closer extends Thread {
        private final Local LOCAL;

        public Closer(Local local) {
            this.LOCAL = local;
        }

        @Override
        public void run() {
            try {
                if (LOCAL.isRunning()) {
                    LOCAL.stop();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
