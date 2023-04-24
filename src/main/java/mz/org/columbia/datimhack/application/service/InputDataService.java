/**
 *
 */
package mz.org.columbia.datimhack.application.service;

import java.util.List;

import mz.org.columbia.datimhack.application.in.InputDataCommand;
import mz.org.columbia.datimhack.application.in.InputDataUseCase;
import mz.org.columbia.datimhack.application.out.DataReaderPort;
import mz.org.columbia.datimhack.application.out.InputDataPort;
import mz.org.columbia.datimhack.application.out.LoginPort;
import mz.org.columbia.datimhack.domain.GenericObject;
import mz.org.columbia.datimhack.domain.SubmissionType;

/**
 * @author Stélio Moiane
 *
 */
public class InputDataService implements InputDataUseCase {

	private LoginPort loginPort;

	private DataReaderPort dataReaderPort;

	private InputDataPort inputDataPort;

	public InputDataService(final LoginPort loginPort, final DataReaderPort dataReaderPort, final InputDataPort inputDataPort) {
		this.loginPort = loginPort;
		this.dataReaderPort = dataReaderPort;
		this.inputDataPort = inputDataPort;
	}

	@Override
	public int inputData(final InputDataCommand inputDataCommand) {

		if (!SubmissionType.INPUT.equals(inputDataCommand.getSubmissionType())) {
			throw new RuntimeException("The submission type must be INPUT");
		}

		final List<GenericObject> data = this.dataReaderPort.readData(inputDataCommand.getFilename());

		this.loginPort.login(inputDataCommand.getUsername(), inputDataCommand.getPassword());

		final int records = this.inputDataPort.inputData(data);

		return records;
	}

}
