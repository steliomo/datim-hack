/**
 *
 */
package mz.org.columbia.datimhack;

import javax.swing.JOptionPane;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import mz.org.columbia.datimhack.adapter.in.MetadataFormController;
import mz.org.columbia.datimhack.adapter.out.DataProcessorAdapter;
import mz.org.columbia.datimhack.adapter.out.FileReaderAdapter;
import mz.org.columbia.datimhack.adapter.out.LoginAdapter;
import mz.org.columbia.datimhack.application.in.CleanDataCommand;
import mz.org.columbia.datimhack.application.in.CleanDataUseCase;
import mz.org.columbia.datimhack.application.in.InputDataCommand;
import mz.org.columbia.datimhack.application.in.InputDataUseCase;
import mz.org.columbia.datimhack.application.out.CleanDataPort;
import mz.org.columbia.datimhack.application.out.DataReaderPort;
import mz.org.columbia.datimhack.application.out.InputDataPort;
import mz.org.columbia.datimhack.application.out.LoginPort;
import mz.org.columbia.datimhack.application.service.CleanDataService;
import mz.org.columbia.datimhack.application.service.InputDataService;
import mz.org.columbia.datimhack.common.SleepTimer;
import mz.org.columbia.datimhack.common.SleepTimerImpl;
import mz.org.columbia.datimhack.domain.SubmissionType;

/**
 * @author Stélio Moiane
 *
 */
public class Application {

	private static WebDriver browser;

	public static void main(final String[] args) {
		// System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

		final MetadataFormController metadataForm = new MetadataFormController();

		metadataForm.setActionProcess(() -> {

			if (metadataForm.visulizeBrowser()) {
				Application.browser = new ChromeDriver();
				Application.browser.manage().window().maximize();

			} else {

				final ChromeOptions options = new ChromeOptions();
				options.addArguments("--remote-allow-origins=*");
				options.addArguments("--headless");

				Application.browser = new ChromeDriver(options);
			}

			metadataForm.visulizeBrowser();

			final SleepTimer sleepTimer = new SleepTimerImpl();
			final LoginPort loginPort = new LoginAdapter(Application.browser, sleepTimer);
			final DataReaderPort fileReaderPort = new FileReaderAdapter();
			final InputDataPort inputDataPort = new DataProcessorAdapter(Application.browser, sleepTimer);
			final CleanDataPort cleanDataPort = new DataProcessorAdapter(Application.browser, sleepTimer);

			if (SubmissionType.INPUT.equals(metadataForm.getSubmissionType())) {
				final InputDataCommand inputDataCommand = new InputDataCommand(metadataForm.getUrl(), metadataForm.getUsername(),
						metadataForm.getPassword(),
						metadataForm.getFileName());

				final InputDataUseCase inputDataUseCase = new InputDataService(loginPort, fileReaderPort, inputDataPort);

				try {
					final int records = inputDataUseCase.inputData(inputDataCommand);
					JOptionPane.showMessageDialog(null, records + " where successfully submited..");
				} catch (final Exception e) {
					JOptionPane.showMessageDialog(null, "There was an error processing data. Please try again!");
				}

			} else {

				final CleanDataCommand cleanDataCommand = new CleanDataCommand(metadataForm.getUrl(), metadataForm.getUsername(),
						metadataForm.getPassword(),
						metadataForm.getFileName());

				final CleanDataUseCase cleanDataUseCase = new CleanDataService(loginPort, fileReaderPort, cleanDataPort);

				try {
					final int records = cleanDataUseCase.cleanData(cleanDataCommand);
					JOptionPane.showMessageDialog(null, records + " where successfully cleaned..");
				} catch (final Exception e) {
					JOptionPane.showMessageDialog(null, "There was an error cleaning data. Please try again!");
				}
			}

			Application.browser.close();
		});
	}
}
