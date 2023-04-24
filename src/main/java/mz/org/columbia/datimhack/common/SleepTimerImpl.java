/**
 *
 */
package mz.org.columbia.datimhack.common;

/**
 * @author Stélio Moiane
 *
 */
public class SleepTimerImpl implements SleepTimer {

	@Override
	public void sleep(final Double seconds) {

		try {
			Thread.sleep(this.convertTomiliSeconds(seconds));
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	private int convertTomiliSeconds(final Double time) {
		final int intValue = time.intValue();
		return (int) ((time - intValue) * SleepTimer.MILIS + intValue * SleepTimer.MILIS);
	}
}
