package com.xiaoyue.nov;

import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import com.xiaoyue.nov.storage.Reader;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Created by xiaoyue26 on 17/11/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ReadingListApplication.class
        , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServerWebTests {
    private static ChromeDriver browser;

    @Value("${local.server.port}")
    private int port;

    @BeforeClass
    public static void openBrowser() {
        // 下载chromedriver到某个目录下,然后代码里指定一下路径
        System.setProperty("webdriver.chrome.driver","/opt/real/chr/chromedriver");
        browser = new ChromeDriver();
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    @Ignore // 浏览器模拟器登录还没写,因此先忽略这个.
    public void addBookToEmptyList() {
        Reader expectedReader = new Reader();
        expectedReader.setUsername("craig");
        expectedReader.setPassword("password");
        expectedReader.setFullname("Craig Walls");


        String baseUrl = "http://localhost:" + port;

        browser.get(baseUrl);

        String currentUrl = browser.getCurrentUrl();
        assertEquals(baseUrl +"/login", currentUrl);

        assertEquals("You have no books in your book list",
                browser.findElementByTagName("div").getText());

        browser.findElementByName("title").sendKeys("BOOK TITLE");
        browser.findElementByName("author").sendKeys("BOOK AUTHOR");
        browser.findElementByName("isbn").sendKeys("1234567890");
        browser.findElementByName("description").sendKeys("DESCRIPTION");
        browser.findElementByTagName("form").submit();

        WebElement dl =
                browser.findElementByCssSelector("dt.bookHeadline");
        assertEquals("BOOK TITLE by BOOK AUTHOR (ISBN: 1234567890)",
                dl.getText());
        WebElement dt =
                browser.findElementByCssSelector("dd.bookDescription");
        assertEquals("DESCRIPTION", dt.getText());
    }


    @AfterClass
    public static void closeBrowser() {
        browser.quit();
    }

}
