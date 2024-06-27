package org.example.testDemoQa;

import org.example.client.ApiClient;
import org.example.core.BaseSeleniumTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

public class DemoQaTest extends BaseSeleniumTest {

    private ApiClient apiClient = new ApiClient();

    @Test
    public void authorization() {
        setUp();
        login();
        BookPage bookPage = new BookPage(driver);
        String text = bookPage.getNoDataText();
        assertEquals("No rows found", text);
    }

    @Test
    public void testAddBook() throws IOException {

        apiClient.generateTokenAndAddBook("src/test/resources/book1.json");
        setUp();
        login();
        BookPage bookPage = new BookPage(driver);
        bookPage.selectDropdownOption();

        List<WebElement> rows = bookPage.getBookRows();
        int bookCount = 0;
        for (WebElement row : rows) {
            bookCount += bookPage.countBooksInRow(row);
        }

        assertEquals(6, bookCount);
        bookPage.deleteBooks();
    }

    @Test
    public void testAddBookAndDelete() throws IOException{

        apiClient.generateTokenAndAddBook("src/test/resources/book2.json");
        setUp();
        login();
        BookPage bookPage = new BookPage(driver);
        bookPage.selectDropdownOption();

        List<WebElement> rows = bookPage.getBookRows();
        int bookCount = 0;
        for (WebElement row : rows) {
            bookCount += bookPage.countBooksInRow(row);
        }

        assertEquals(2, bookCount);
        bookPage.deleteBooks();
    }
}
