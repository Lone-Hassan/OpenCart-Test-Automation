package OpenCart.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import OpenCart.AbstractComponents.openShopDB;
import OpenCart.PageObjects.HomePage;

public class BaseTest {

	protected WebDriver driver;
	protected openShopDB db;
	protected Properties prop;

	@BeforeClass(alwaysRun = true)
	public void DriverInitialization() throws IOException {

		initProperties();

		String browser = System.getProperty("browser") != null ? System.getProperty("browser")
				: prop.getProperty("browser");

		boolean isheadless = Boolean.getBoolean("headless") ? Boolean.getBoolean("headless")
				: Boolean.parseBoolean(prop.getProperty("headless"));

		// Initialize ChromeOptions

		ChromeOptions options = new ChromeOptions();

		// Disable notifications
		options.addArguments("--disable-notifications");
		options.addArguments(browser);
		if (isheadless) {
			options.addArguments("--headless");
			options.addArguments("--window-size=1552,840");
		}
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		/*
		 * Dimension di = driver.manage().window().getSize();
		 * System.out.println("Window width: "+di.getWidth()+" Window height: "+di.
		 * getHeight());
		 */

	}

	public void initProperties() throws IOException {

		prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\resources\\globalData.properties");
		prop.load(fis);
	}

	
	/**
	 * @param url to visit
	 * @return HomePage Object
	 * */
	public HomePage visit(String url) {
		driver.get(url);
		return new HomePage(driver);
	}

	public void OpenShopDB() throws SQLException {
		db = new openShopDB("jdbc:" + prop.getProperty("Database"), prop.getProperty("DB_root"),
				prop.getProperty("DB_password"));

	}
	
	public String captureScreenShot(String TestName,WebDriver driver) throws IOException {
		
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File destination = new File(System.getProperty("user.dir") + "//reports//" + TestName + ".png");
		FileUtils.copyFile(src, destination);

		return System.getProperty("user.dir") + "//reports//" + TestName + ".png";
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {

		if (driver != null)
			driver.close();

	}
}
