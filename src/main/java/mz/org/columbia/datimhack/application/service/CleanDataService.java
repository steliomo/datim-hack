/**
 *
 */
package mz.org.columbia.datimhack.application.service;

import java.util.List;

import mz.org.columbia.datimhack.application.in.CleanDataCommand;
import mz.org.columbia.datimhack.application.in.CleanDataUseCase;
import mz.org.columbia.datimhack.application.out.CleanDataPort;
import mz.org.columbia.datimhack.application.out.DataReaderPort;
import mz.org.columbia.datimhack.application.out.LoginPort;
import mz.org.columbia.datimhack.domain.GenericObject;
import mz.org.columbia.datimhack.domain.SubmissionType;

/**
 * @author Stélio Moiane
 *
 */
public class CleanDataService implements CleanDataUseCase {

	private LoginPort loginPort;
	private DataReaderPort dataReaderPort;
	private CleanDataPort cleanDataPort;

	public CleanDataService(final LoginPort loginPort, final DataReaderPort dataReaderPort, final CleanDataPort cleanDataPort) {
		this.loginPort = loginPort;
		this.dataReaderPort = dataReaderPort;
		this.cleanDataPort = cleanDataPort;
	}

	@Override
	public int cleanData(final CleanDataCommand cleanDataCommand) {

		if (!SubmissionType.CLEAN.equals(cleanDataCommand.getSubmissionType())) {
			throw new RuntimeException("the submission type must be CLEAN");
		}

		final List<GenericObject> data = this.dataReaderPort.readData(cleanDataCommand.getFilename());

		this.loginPort.login(cleanDataCommand.getUrl(), cleanDataCommand.getUsername(), cleanDataCommand.getPassword());

		final int records = this.cleanDataPort.cleanData(cleanDataCommand.getUrl(), data);

		return records;
	}

}
