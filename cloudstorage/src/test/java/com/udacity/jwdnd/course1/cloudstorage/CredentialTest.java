package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTest {
    private static String firstName = "Ash";
    private static String lastName = "Ketchum";
    private static String username = "google";
    private static String password = "catchEmAll";

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testCredentialCreate() {

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        addCreds();
        logIn();

        // Try creating a new note and if detail are visible

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement credTab = driver.findElement(By.id("nav-credentials-tab"));
        credTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("googleUrl")));
        WebElement credUrlVis = driver.findElement(By.id("googleUrl"));
        Assertions.assertEquals("google", credUrlVis.getText());

        WebElement logoutButton = driver.findElement(By.id("logout-button"));
        logoutButton.click();
    }

    @Test
    public void testNoteUpdate() throws InterruptedException {

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        addCreds();
        logIn();

        // Try updating a note and if details are visible
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement credTab = driver.findElement(By.id("nav-credentials-tab"));
        credTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("googleUrl")));
        WebElement credEdit = driver.findElement(By.id("googleEditCredBtn"));
        credEdit.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        WebElement credUrlField = driver.findElement(By.id("credential-url"));
        credUrlField.click();
        credUrlField.clear();
        credUrlField.sendKeys("facebook");

        WebElement saveBtn = driver.findElement(By.id("cred-save"));
        saveBtn.click();

        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement credNavTab = driver.findElement(By.id("nav-credentials-tab"));
        credNavTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("facebookUrl")));
        WebElement CredUrlVis = driver.findElement(By.id("facebookUrl"));
        Assertions.assertEquals("facebook", CredUrlVis.getText());

        WebElement logoutButton = driver.findElement(By.id("logout-button"));
        logoutButton.click();
    }

    @Test
    public void testNoteDelete() {

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        addCreds();
        logIn();

        // Try deleting a note and if detail are visible
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement credTab = driver.findElement(By.id("nav-credentials-tab"));
        credTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("googleUrl")));
        WebElement credDelete = driver.findElement(By.id("googleDeleteCredBtn"));
        credDelete.click();

        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        credTab = driver.findElement(By.id("nav-credentials-tab"));
        credTab.click();

        List credRows = driver.findElements(By.className("cred-row"));
        Assertions.assertEquals(0, credRows.size());

        WebElement logoutButton = driver.findElement(By.id("logout-button"));
        logoutButton.click();
    }

    private void signUp() {

        // Visit the sign-up page.
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        // Fill out credentials
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.click();
        inputLastName.sendKeys(lastName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys(username);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys(password);

        // Attempt to sign up.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();
    }

    public void logIn() {
        driver.get("http://localhost:" + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement loginUserName = driver.findElement(By.id("inputUsername"));
        loginUserName.click();
        loginUserName.sendKeys(username);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement loginPassword = driver.findElement(By.id("inputPassword"));
        loginPassword.click();
        loginPassword.sendKeys(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));

    }

    private void addCreds(){
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        signUp();
        logIn();

        // Try creating a new note and if detail are visible
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement credTab = driver.findElement(By.id("nav-credentials-tab"));
        credTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-cred")));
        WebElement newCredBtn = driver.findElement(By.id("new-cred"));
        newCredBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        WebElement credUrlField = driver.findElement(By.id("credential-url"));
        credUrlField.click();
        credUrlField.sendKeys("google");

        WebElement credUsernameField = driver.findElement(By.id("credential-username"));
        credUsernameField.click();
        credUsernameField.sendKeys("Squirtle");

        WebElement credPasswordField = driver.findElement(By.id("credential-password"));
        credPasswordField.click();
        credPasswordField.sendKeys("water");

        WebElement saveBtn = driver.findElement(By.id("cred-save"));
        saveBtn.click();

        driver.get("http://localhost:" + this.port + "/home");
        WebElement logoutButton = driver.findElement(By.id("logout-button"));
        logoutButton.click();
    }
}
