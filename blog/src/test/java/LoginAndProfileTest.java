
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author dedg
 */
public class LoginAndProfileTest {
    private WebDriver driver;
    private final String screenshotDirectory = "screenshots/";

    @BeforeEach
    public void setUp() {
        //System.setProperty("webdriver.chrome.driver", "/usr/lib/chromium-browser/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        PageFactory.initElements(driver, this);
    }

    @Test
    @Order(1)
    public void testLogin() throws IOException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login("test123", "123");

        takeScreenshot("AfterLogin");

        DashboardPage dasboardPage = new DashboardPage(driver);
        assertTrue(dasboardPage.isProfileLinkDisplayed());
    }

    @Test
    @Order(2)
    public void testViewProfile() throws IOException {
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.open();

        takeScreenshot("AfterProfileView");

        assertTrue(profilePage.isFirstNameameEqualTo(""));
        assertTrue(profilePage.isLastNameameEqualTo(""));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void takeScreenshot(String screenshotName) throws IOException {
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(screenshotDirectory + screenshotName + ".png"));
    }
}

class LoginPage {
    private final WebDriver driver;
    
    private By usernameInput = By.xpath("//input[@id='username']");
    private By passwordInput = By.xpath("//input[@id='password']");
    private By loginButton  = By.cssSelector(".btn");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("http://localhost:8080/login");
    }

    public void login(String username, String password) {
        driver.findElement(usernameInput).sendKeys(username);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(loginButton).click();
    }
}

class ProfilePage {
    private WebDriver driver;

    private By profileName = By.xpath("//input[@id='username']");
    private By profileFirstName = By.xpath("//input[@id='firstName']");
    private By profileLastName = By.xpath("//input[@id='lastName']");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }
    
    public void open() {
        driver.get("http://localhost:8080/profile");
    }
    
    public boolean isFirstNameameEqualTo(String firstName) {
        return driver.findElement(profileFirstName).getText().equals(firstName);
    }
    
    public boolean isLastNameameEqualTo(String lastName) {
        return driver.findElement(profileLastName).getText().equals(lastName);
    }
}

class DashboardPage {
    private WebDriver driver;

    private By profileLink = By.xpath("//a[contains(@href, '/profile') and text()='My Profile']");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }
    
    public void open() {
        driver.get("http://localhost:8080/dashboard");
    }

    public void openProfile() {
        driver.findElement(profileLink).click();
    }

    public boolean isProfileLinkDisplayed() {
        return driver.findElement(profileLink).isDisplayed();
    }
}