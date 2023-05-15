/**
 *
 */
package mz.org.columbia.datimhack.adapter.out;

import java.math.BigDecimal;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mz.org.columbia.datimhack.application.out.CleanDataPort;
import mz.org.columbia.datimhack.application.out.InputDataPort;
import mz.org.columbia.datimhack.common.SleepTimer;
import mz.org.columbia.datimhack.domain.GenericObject;

/**
 * @author Stélio Moiane
 *
 */
public class DataProcessorAdapter implements InputDataPort, CleanDataPort {

	private static final Logger logger = LoggerFactory.getLogger(DataProcessorAdapter.class);

	private final WebDriver driver;

	private static final String ORG_UNIT = "orgUnit";

	private static final String PERIOD = "period";

	private static final String MECANISM = "mecanism";

	private static final String FIELD = "entryFieldID";

	private static final String TECH_AREA = "techArea";

	private static final String VALUE = "value";

	private static final String ASSISTANCE = "assistance";

	private final SleepTimer sleepTimer;

	public DataProcessorAdapter(final WebDriver driver, final SleepTimer sleepTimer) {
		this.driver = driver;
		this.sleepTimer = sleepTimer;
	}

	@Override
	public int inputData(final String url, final List<GenericObject> data) {
		DataProcessorAdapter.logger.info("Starting the input process...");

		int counter = 0;
		Boolean periodMecanism = Boolean.FALSE;
		String lastOrgUnit = null;

		this.driver.get(url + "dhis-web-dataentry/index.action");
		final JavascriptExecutor javascriptExecutor = (JavascriptExecutor) this.driver;

		this.sleepTimer.sleep(120.0);

		for (final GenericObject record : data) {

			final String orgUnit = (String) record.getValue(DataProcessorAdapter.ORG_UNIT);

			if (!orgUnit.equals(lastOrgUnit)) {
				javascriptExecutor.executeScript("selection.select('" + orgUnit + "')");
				this.sleepTimer.sleep(5.0);
			}

			if (!periodMecanism) {

				final String period = (String) record.getValue(DataProcessorAdapter.PERIOD);
				javascriptExecutor.executeScript("document.getElementById('selectedPeriodId').value='" + period + "';periodSelected();");

				final String mecanism = (String) record.getValue(DataProcessorAdapter.MECANISM);
				javascriptExecutor.executeScript(
						"document.getElementById('category-SH885jaRe0o').value= '" + mecanism + "';dhis2.de.attributeSelected('SH885jaRe0o');");

				periodMecanism = Boolean.TRUE;
			}

			if (!orgUnit.equals(lastOrgUnit)) {
				this.sleepTimer.sleep(8.0);
				lastOrgUnit = orgUnit;
			}

			this.selectIndicatorTab(javascriptExecutor, record);
			this.selectAssistanceTab(javascriptExecutor, record);

			final String field = (String) record.getValue(DataProcessorAdapter.FIELD);
			final Double value = (Double) record.getValue(DataProcessorAdapter.VALUE);

			this.sleepTimer.sleep(0.3);

			if (BigDecimal.ZERO.intValue() != value.intValue()) {
				this.driver.findElement(By.id(field)).clear();
				this.driver.findElement(By.id(field)).sendKeys(String.valueOf(value.intValue()));
			}

			this.sleepTimer.sleep(0.3);
			this.driver.findElement(By.id(field)).sendKeys(Keys.TAB);

			counter++;
			DataProcessorAdapter.logger.info("Liner processed: " + counter);
		}

		DataProcessorAdapter.logger.info("total processed:" + counter);
		return counter;
	}

	private void selectIndicatorTab(final JavascriptExecutor js, final GenericObject record) {
		final TechnicalArea techArea = TechnicalArea.valueOf((String) record.getValue(DataProcessorAdapter.TECH_AREA));
		js.executeScript("document.querySelector(\"a[href='" + techArea.getTechArea() + "']\").click()");
	}

	private void selectAssistanceTab(final JavascriptExecutor js, final GenericObject record) {
		final TechnicalArea techArea = TechnicalArea.valueOf((String) record.getValue(DataProcessorAdapter.TECH_AREA));
		final char page = techArea.getTechArea().charAt(techArea.getTechArea().length() - 1);
		final AssistanceType assistanceType = AssistanceType.valueOf((String) record.getValue(DataProcessorAdapter.ASSISTANCE));
		js.executeScript("document.querySelector(\"a[href='" + assistanceType.getAssistanceType().replace('X', page) + "']\").click()");
	}

	@Override
	public int cleanData(final String url, final List<GenericObject> data) {

		int counter = 0;
		Boolean periodMecanism = Boolean.FALSE;
		String lastOrgUnit = null;

		this.driver.get(url + "dhis-web-dataentry/index.action");
		final JavascriptExecutor javascriptExecutor = (JavascriptExecutor) this.driver;

		this.sleepTimer.sleep(120.0);

		for (final GenericObject record : data) {

			final String orgUnit = (String) record.getValue(DataProcessorAdapter.ORG_UNIT);

			if (!orgUnit.equals(lastOrgUnit)) {
				javascriptExecutor.executeScript("selection.select('" + orgUnit + "')");
				this.sleepTimer.sleep(5.0);
			}

			if (!periodMecanism) {

				final String period = (String) record.getValue(DataProcessorAdapter.PERIOD);
				javascriptExecutor.executeScript("document.getElementById('selectedPeriodId').value='" + period + "';periodSelected();");

				final String mecanism = (String) record.getValue(DataProcessorAdapter.MECANISM);
				javascriptExecutor.executeScript(
						"document.getElementById('category-SH885jaRe0o').value= '" + mecanism + "';dhis2.de.attributeSelected('SH885jaRe0o');");

				periodMecanism = Boolean.TRUE;
			}

			if (!orgUnit.equals(lastOrgUnit)) {
				this.sleepTimer.sleep(8.0);
				lastOrgUnit = orgUnit;
			}

			this.selectIndicatorTab(javascriptExecutor, record);
			this.selectAssistanceTab(javascriptExecutor, record);

			final String field = (String) record.getValue(DataProcessorAdapter.FIELD);

			this.driver.findElement(By.id(field)).clear();

			this.sleepTimer.sleep(0.3);
			this.driver.findElement(By.id(field)).sendKeys(Keys.TAB);

			counter++;
		}

		return counter;
	}
}
