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

	public static void main(final String[] args) {
		// System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

		final ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("--headless");

		final WebDriver browser = new ChromeDriver(options);
		browser.manage().window().maximize();

		final MetadataFormController metadataForm = new MetadataFormController(browser);

		metadataForm.setActionProcess(() -> {

			final SleepTimer sleepTimer = new SleepTimerImpl();
			final LoginPort loginPort = new LoginAdapter(browser, sleepTimer);
			final DataReaderPort fileReaderPort = new FileReaderAdapter();
			final InputDataPort inputDataPort = new DataProcessorAdapter(browser, sleepTimer);
			final CleanDataPort cleanDataPort = new DataProcessorAdapter(browser, sleepTimer);

			if (SubmissionType.INPUT.equals(metadataForm.getSubmissionType())) {
				final InputDataCommand inputDataCommand = new InputDataCommand(metadataForm.getUsername(), metadataForm.getPassword(),
						metadataForm.getFileName());

				final InputDataUseCase inputDataUseCase = new InputDataService(loginPort, fileReaderPort, inputDataPort);

				final int records = inputDataUseCase.inputData(inputDataCommand);

				JOptionPane.showMessageDialog(null, records + " where successfully submited..");
			} else {

				final CleanDataCommand cleanDataCommand = new CleanDataCommand(metadataForm.getUsername(), metadataForm.getPassword(),
						metadataForm.getFileName());

				final CleanDataUseCase cleanDataUseCase = new CleanDataService(loginPort, fileReaderPort, cleanDataPort);

				final int records = cleanDataUseCase.cleanData(cleanDataCommand);

				JOptionPane.showMessageDialog(null, records + " where successfully cleaned..");
			}

			browser.close();
		});
	}
}
