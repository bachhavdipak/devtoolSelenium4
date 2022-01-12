package Framework.Drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import net.thucydides.core.webdriver.DriverSource;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DriverManager implements DriverSource {

    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    private static final String binarySetupMessage = "Downloading and setting up chrome WebDriver binary from https://chromedriver.storage.googleapis.com:443";

    public WebDriver newDriver() {

        RemoteWebDriver driver = null;

        DesiredCapabilities dc = new DesiredCapabilities();


        //Do not change this. If you do change it, don't commit your change. Revert. Until we do this via environment variables or something similar.
        final String ACCESS_KEY = "XXXXX";

        EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        boolean remote = Boolean.parseBoolean(environmentVariables.getProperty("remote"));
        if (remote) {
            logger.info("##### Running web tests on experitest (remote) ####");
            String accessKey = System.getenv("experitest_ACCESS_KEY");
            String serverAddress = environmentVariables.getProperty("experitest.server");

            if (accessKey == null) {
                accessKey = environmentVariables.getProperty("experitest.key");
            }

            String environment = System.getProperty("environment");

            final ChromeOptions chromeOptions = new ChromeOptions();
            Map<String, Object> prefs = new HashMap<String, Object>();

            prefs.put("googlegeolocationaccess.enabled", false);
            prefs.put("profile.default_content_setting_values.geolocation", 2); // 1:allow 2:block
            prefs.put("profile.default_content_setting_values.notifications", 1);
            prefs.put("profile.managed_default_content_settings", 1);
            chromeOptions.setExperimentalOption("prefs", prefs);
            chromeOptions.setAcceptInsecureCerts(true);
            chromeOptions.addArguments(
                    "--ignore-certificate-errors",
                    "--disable-download-notification",
                    "--no-sandbox",
                    "--disable-site-isolation-trials",
                    "--enable-strict-powerful-feature-restrictions",
                    "--disable-geolocation",
                    "--disable-gpu");
            ChromeDriverService service;

            try {
                setupChromeDriverBinary();
                service = new ChromeDriverService.Builder()
                        .usingAnyFreePort()
                        .build();
                service.start();
                dc.setCapability(CapabilityType.BROWSER_VERSION, "94");
                dc.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                dc.setCapability("testName", "SpecSavers: End to End");
                dc.setCapability("accessKey", ACCESS_KEY);

                driver = new RemoteWebDriver(new URL("XXXXXXXXXX/wd/hub"), dc);
            } catch (Exception e) {
                logger.error("Error due to " + e.getMessage());
                throw new UnreachableBrowserException(e.getMessage());
            }
            return driver;
        } else {
            try {
                // File file = setupChromeDriverBinary();

                setupChromeDriverBinary();
                final ChromeOptions chromeOptions = new ChromeOptions();
                Map<String, Object> prefs = new HashMap<String, Object>();

                prefs.put("googlegeolocationaccess.enabled", false);
                prefs.put("profile.default_content_setting_values.geolocation", 2); // 1:allow 2:block
                prefs.put("profile.default_content_setting_values.notifications", 1);
                prefs.put("profile.managed_default_content_settings", 1);
                chromeOptions.setExperimentalOption("prefs", prefs);
                chromeOptions.setAcceptInsecureCerts(true);
                chromeOptions.addArguments(
                        "--ignore-certificate-errors",
                        "--disable-download-notification",
                        "--no-sandbox",
                        "--disable-site-isolation-trials",
                        "--enable-strict-powerful-feature-restrictions",
                        "--disable-geolocation",
                        "--disable-gpu");
                logger.info("Is this a ci_build? {}", SystemUtils.IS_OS_LINUX);
                if (SystemUtils.IS_OS_LINUX) {
                    chromeOptions.addArguments(
                            "--headless",
                            "--allow-running-insecure-content",
                            "--disable-web-security");

                } else if (SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_WINDOWS) {
                    chromeOptions.addArguments(
                            "--incognito",
                            "--start-fullscreen");
                }
                ChromeDriverService service;
                service = new ChromeDriverService.Builder()
                        .usingAnyFreePort()
                        .build();
                service.start();
                dc.setCapability(CapabilityType.BROWSER_VERSION, "93");
                dc.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                driver = new RemoteWebDriver(service.getUrl(), dc);

                return driver;

            } catch (UnreachableBrowserException | FileNotFoundException e) {
                logger.error("Error due to " + e.getMessage());
                throw new UnreachableBrowserException(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return driver;
    }

    public boolean takesScreenshots() {
        return true;
    }

    private void setupChromeDriverBinary() {
        logger.info(binarySetupMessage);

        try {
            if (SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_WINDOWS || SystemUtils.IS_OS_LINUX) {
                logger.info("driver binary setup started...");
                ChromeDriverManager.getInstance(DriverManagerType.CHROME).setup();
                WebDriverManager.chromedriver().setup();
            } else {
                System.getenv();
            }

        } catch (Exception e) {
            logger.info("Driver binary not setup correctly...");
            e.printStackTrace();
        }
    }
}