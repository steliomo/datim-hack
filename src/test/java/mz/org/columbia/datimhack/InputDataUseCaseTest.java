/**
 *
 */
package mz.org.columbia.datimhack;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import mz.org.columbia.datimhack.application.in.InputDataCommand;
import mz.org.columbia.datimhack.application.in.InputDataUseCase;
import mz.org.columbia.datimhack.application.out.DataReaderPort;
import mz.org.columbia.datimhack.application.out.InputDataPort;
import mz.org.columbia.datimhack.application.out.LoginPort;
import mz.org.columbia.datimhack.application.service.InputDataService;

/**
 * @author Stélio Moiane
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class InputDataUseCaseTest {

	@Mock
	private LoginPort loginPort;

	@Mock
	private DataReaderPort fileReadPort;

	@Mock
	private InputDataPort inputDataPort;

	@InjectMocks
	private InputDataUseCase inputDataUseCase = new InputDataService(this.loginPort, this.fileReadPort, this.inputDataPort);

	@Test
	public void shouldInputData() {

		final InputDataCommand inputDataCommand = new InputDataCommand("stelio", "stelio", "CXCA_DATA.xlsx");

		final int expected = 10;

		Mockito.when(this.inputDataPort.inputData(ArgumentMatchers.anyList())).thenReturn(expected);

		final int data = this.inputDataUseCase.inputData(inputDataCommand);

		Mockito.verify(this.loginPort, Mockito.times(1)).login(inputDataCommand.getUsername(), inputDataCommand.getPassword());
		Mockito.verify(this.fileReadPort, Mockito.times(1)).readData(inputDataCommand.getFilename());
		Mockito.verify(this.inputDataPort, Mockito.times(1)).inputData(ArgumentMatchers.anyList());

		Assert.assertEquals(expected, data);
	}

}
