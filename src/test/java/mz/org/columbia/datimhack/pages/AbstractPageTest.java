/**
 *
 */
package mz.org.columbia.datimhack.pages;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;

/**
 * @author Stélio Moiane
 *
 */
public abstract class AbstractPageTest {

	protected WebDriver browser;

	@Before
	public void setup() {
		this.browser = new SafariDriver();
		this.browser.manage().window().maximize();
	}

	@After
	public void tearDown() {
		this.browser.close();
	}
}
