package org.example.testDemoQa;

import org.example.core.BaseSeleniumPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class LoginPage extends BaseSeleniumPage {

    private WebDriver driver;

    private By username = By.cssSelector("input[id=userName]");
    private By password = By.cssSelector("input[id=password]");
    private By loginButton = By.cssSelector("#login");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        driver.get("https://demoqa.com/login");
    }

    public void setUsername(String name) {
        driver.findElement(username).sendKeys(name);
    }

    public void setPassword(String pass) {
        driver.findElement(password).sendKeys(pass);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

}
