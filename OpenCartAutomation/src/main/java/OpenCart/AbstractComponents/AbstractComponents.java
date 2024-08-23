package OpenCart.AbstractComponents;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractComponents {
	WebDriver driver;
	WebDriverWait wait;
	Actions action;

	public AbstractComponents(WebDriver driver) {

		this.driver = driver;

	}

	/**
	 * Explicitly wait for the webElement to be invisible
	 * @param locator: By Locator of webElement to wait for
	 * @param time: number of seconds to wait
	 * */
	public WebElement waitforElement(By locator, int time) {

		wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	/**
	 * Explicitly wait for the webElement to be visible
	 * @param element: webElement to wait for
	 * @param time: number of seconds to wait
	 * */

	public WebElement waitforWebElement(WebElement element, int time) {

		wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		return wait.until(ExpectedConditions.visibilityOf(element));
	}


	/**
	 * Explicitly wait for the webElement to be invisible
	 * @param element: webElement to wait for
	 * @param time: number of seconds to wait
	 * */
	public boolean waitforInvisibiltyWebElement(WebElement element, int time) {

		wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		return wait.until(ExpectedConditions.invisibilityOf(element));
	}

	/**
	 * Explicitly wait for the webElement to be click able
	 * @param element: webElement to wait for
	 * @param time: number of seconds
	 * */
	public WebElement waitforClickableWebElement(WebElement element, int time) {

		wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * Explicitly wait for the page title that is substring of given pageTitle
	 * 
	 * @param pageTitle: title of the page to wait for
	 * @param time:      number of seconds
	 */
	public boolean waitforPageTitleIsSubString(String pageTitle, int time) {
		// System.out.println("Waiting for page load: " + pageTitle);
		wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		return wait.until(driver -> pageTitle.contains(driver.getTitle()));
	}
	
	/**
	 * Explicitly wait for the page title that contains the substring
	 * 
	 * @param pageTitle: title of the page to wait for
	 * @param time:      number of seconds
	 */
	public boolean waitforPageLoad(String pageTitle, int time) {
		// System.out.println("Waiting for page load: " + pageTitle);
		wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		return wait.until(ExpectedConditions.titleContains(pageTitle));
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return driver.getTitle();
	}

	/**
	 * Checks weather the element is in view
	 * 
	 * @param element to check in view
	 * @return true is element in view, false otherwise
	 */
	public boolean elementInView(WebElement element) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		return (Boolean) js.executeScript("var rect = arguments[0].getBoundingClientRect();" + "return ("
				+ "rect.top >= 0 &&" + "rect.left >= 0 &&"
				+ "rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&"
				+ "rect.right <= (window.innerWidth || document.documentElement.clientWidth)" + ");", element);
	}

	/**
	 * Java script executor to scroll
	 * 
	 * @param element is web element to scroll to
	 */
	public void scrollToElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	/**
	 * Wait for 5 seconds to complete scroll action
	 * 
	 * @param element is web element to scroll to
	 */
	public void waitForElementInView(final WebElement element) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				boolean isInView = (Boolean) js.executeScript("var elem = arguments[0],                 "
						+ "  box = elem.getBoundingClientRect(),     " + "  cx = box.left + box.width / 2,          "
						+ "  cy = box.top + box.height / 2,          " + "  e = document.elementFromPoint(cx, cy);  "
						+ "for (; e; e = e.parentElement) {          " + "  if (e === elem)                         "
						+ "    return true;                          " + "}                                         "
						+ "return false;                             ", element);
				return isInView;
			}
		});
	}

	/**
	 * Clicks on the WebElement in the given List of WebElements by Text.
	 * 
	 * @param Options List of Web elements
	 * @param Text    text to search by
	 * @return boolean: true if WebElement with text found in List<WebElement> false
	 *         otherwise
	 */
	public boolean SelectElementByText(List<WebElement> Options, String Text) {

		for (WebElement option : Options) {

			if (option.getText().equalsIgnoreCase(Text)) {
				option.click();
				return true;
			}
		}
		return false;

	}

	/**
	 * Perform action on the current page 
	 * @param keystoPress org.openqa.selenium.Keys object
	 */
	public void Press(Keys keystoPress) {

		action = new Actions(driver);
		action.keyDown(keystoPress);

	}

	/**
	 * Navigate back to pageHistory UnExpected Behavior if this is the first page in
	 * page history
	 */
	public void NavigateBack() {

		driver.navigate().back();
	}

	/**
	 * Clear System clip board
	 */

	public void clearClipBoard() {

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection emptyContent = new StringSelection("");
		clipboard.setContents(emptyContent, null);

	}

	/**
	 * @return system clip board content as string
	 */
	public String GetClipBoard() throws UnsupportedFlavorException, IOException {

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		String clipboardContent = (String) clipboard.getData(DataFlavor.stringFlavor);

		return clipboardContent;

	}

	/**
	 * perform Scroll action to web element if not in view and wait till in view
	 * 
	 * @param element to scroll to
	 */
	public void scrollTo(WebElement element) {

		if (!elementInView(element)) {

			scrollToElement(element);
			waitForElementInView(element);
		}

	}

}
