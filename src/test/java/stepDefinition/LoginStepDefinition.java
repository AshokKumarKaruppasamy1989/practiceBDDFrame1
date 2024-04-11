package stepDefinition;

import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumNetworkConditions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginStepDefinition {

	public static WebDriver driver;
	public static Properties prop;
	FileInputStream file;

	@Before
	public void setUp() {
		String path = System.getProperty("user.dir") + "//src/test/resources//configfiles//config.properties";
		try {
			prop = new Properties();
			file = new FileInputStream(path);
			prop.load(file);
		} catch (Exception e) {
			e.getMessage();
		}

	}

	@Given("User launch the web page")
	public void user_launch_the_web_page() {

		String browser = prop.getProperty("browser");
		if (browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		} else {
			driver.quit();
		}

		String url = prop.getProperty("url");
		driver.get(url);
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
	}

	@When("User enter the credentials")
	public void user_enter_the_credentials() {
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.xpath("//button[@type='submit']")).click();

	}

	@Then("User successfully login")
	public void user_successfully_login() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[contains(text(),'Logout')]")));
		String Expected = "Welcome to the Secure Area. When you are done click logout below.";
		String Actual = driver
				.findElement(By.xpath(
						"//h4[contains(text(),'Welcome to the Secure Area. When you are done click logout below.')]"))
				.getText();
		assertEquals(Actual, Expected);
	}

	@Then("User not able to successfully login")
	public void user_not_able_to_successfully_login() {
		System.out.println(">>User not able to login<<");
	}

	@After
	public void tearDown() {
		driver.quit();
	}

}
