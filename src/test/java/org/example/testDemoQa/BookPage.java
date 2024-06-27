package org.example.testDemoQa;

import org.example.core.BaseSeleniumPage;
import org.openqa.selenium.*;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
public class BookPage extends BaseSeleniumPage {

    private WebDriver driver;

    private By noDataAlert = By.cssSelector("div[class=rt-noData]");
    private By bookRows = By.cssSelector("div[class=rt-tr-group]");
    private By booksInRow = By.cssSelector("[id^='see-book-']");
    private By dropdown = By.cssSelector(".select-wrap");
    private By dropdownOption = By.cssSelector("div[class=-center] span.select-wrap option[value='10']");

    private By deleteButton = By.cssSelector(".text-right.button.di button[id=submit]");
    private By confirmationButton = By.cssSelector(".modal-footer button[id=closeSmallModal-ok]");

    public BookPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<WebElement> getBookRows() {
        return driver.findElements(bookRows);
    }

    public int countBooksInRow(WebElement row) {
        return row.findElements(booksInRow).size();
    }

    public String getNoDataText() {
        return driver.findElement(noDataAlert).getText();
    }

    public void selectDropdownOption() {
        driver.findElement(dropdown).click();
        driver.findElement(dropdownOption).click();
    }

    public void deleteBooks() {
        WebElement deleteBtn = driver.findElement(deleteButton);

        // Use JavascriptExecutor to click the button
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", deleteBtn);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(confirmationButton));

        WebElement confirmBtn = driver.findElement(confirmationButton);
        // Use JavascriptExecutor to click the confirmation button
        executor.executeScript("arguments[0].click();", confirmBtn);

        try {
            WebDriverWait waitForAlert = new WebDriverWait(driver, Duration.ofSeconds(10));
            waitForAlert.until(ExpectedConditions.alertIsPresent());

            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException e) {
            System.out.println("No alert present");
        }
    }
}
