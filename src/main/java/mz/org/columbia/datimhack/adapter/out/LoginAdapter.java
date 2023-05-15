/**
 *
 */
package mz.org.columbia.datimhack.adapter.out;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import mz.org.columbia.datimhack.application.out.LoginPort;
import mz.org.columbia.datimhack.common.SleepTimer;

/**
 * @author Stélio Moiane
 *
 */
public class LoginAdapter implements LoginPort {

	private final WebDriver browser;

	private final SleepTimer sleepTimer;

	public LoginAdapter(final WebDriver browser, final SleepTimer sleepTimer) {
		this.browser = browser;
		this.sleepTimer = sleepTimer;
	}

	@Override
	public void login(final String url, final String username, final String password) {
		this.browser.get(url);

		this.browser.findElement(By.tagName("button")).click();
		this.browser.findElement(By.id("j_username")).sendKeys(username);
		this.browser.findElement(By.id("j_password")).sendKeys(password);
		this.browser.findElement(By.id("submit")).click();

		final WebDriverWait webDriverWait = new WebDriverWait(this.browser, Duration.ofSeconds(60));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jsx-2721515324")));

		this.sleepTimer.sleep(5.0);

		this.browser.findElement(By.className("jsx-2721515324")).getText();
	}
}
