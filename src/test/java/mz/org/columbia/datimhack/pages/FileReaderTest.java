/**
 *
 */
package mz.org.columbia.datimhack.pages;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import mz.org.columbia.datimhack.adapter.out.FileReaderAdapter;
import mz.org.columbia.datimhack.domain.GenericObject;

/**
 * @author Stélio Moiane
 *
 */
public class FileReaderTest {

	@Test
	public void shoulReadFile() {
		final FileReaderAdapter fileReaderServiceImpl = new FileReaderAdapter();

		final List<GenericObject> lines = fileReaderServiceImpl.readData("DATA_CLEAN.xlsx");

		Assert.assertFalse(lines.isEmpty());
	}
}
