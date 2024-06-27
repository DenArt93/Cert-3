package org.example.core;

import org.example.config.ConfigProvider;
import org.example.testDemoQa.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

abstract public class BaseSeleniumTest {
    protected WebDriver driver;


    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();// раскрываем браузер во весь экран
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        BaseSeleniumPage.setDriver(driver);

    }

    public void login() {
        ConfigProvider config = new ConfigProvider();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setUsername(config.getUser());
        loginPage.setPassword(config.getPassword());
        loginPage.clickLogin();
    }

    @AfterEach
    public void closeDriver() {
        driver.close();
        if (driver != null) {
            driver.quit();
        }

    }


}
