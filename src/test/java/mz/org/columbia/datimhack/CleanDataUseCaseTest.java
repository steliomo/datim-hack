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

import mz.org.columbia.datimhack.application.in.CleanDataCommand;
import mz.org.columbia.datimhack.application.in.CleanDataUseCase;
import mz.org.columbia.datimhack.application.out.CleanDataPort;
import mz.org.columbia.datimhack.application.out.DataReaderPort;
import mz.org.columbia.datimhack.application.out.LoginPort;
import mz.org.columbia.datimhack.application.service.CleanDataService;

/**
 * @author Stélio Moiane
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CleanDataUseCaseTest {

	@Mock
	private LoginPort loginPort;

	@Mock
	private DataReaderPort dataReaderPort;

	@Mock
	private CleanDataPort cleanDataPort;

	@InjectMocks
	CleanDataUseCase cleanDataUseCase = new CleanDataService(this.loginPort, this.dataReaderPort, this.cleanDataPort);

	@Test
	public void shouldCleanData() {

		final CleanDataCommand dataCommand = new CleanDataCommand("stelio", "stelio", "teste.xlxs");
		Mockito.when(this.cleanDataPort.cleanData(ArgumentMatchers.anyList())).thenReturn(10);

		final int result = this.cleanDataUseCase.cleanData(dataCommand);

		Mockito.verify(this.loginPort, Mockito.times(1)).login(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
		Mockito.verify(this.dataReaderPort, Mockito.times(1)).readData(ArgumentMatchers.anyString());
		Mockito.verify(this.cleanDataPort, Mockito.times(1)).cleanData(ArgumentMatchers.anyList());

		Assert.assertEquals(10, result);
	}
}
