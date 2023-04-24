/**
 *
 */
package mz.org.columbia.datimhack.adapter.out;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mz.org.columbia.datimhack.application.out.DataReaderPort;
import mz.org.columbia.datimhack.domain.GenericObject;

/**
 * @author Stélio Moiane
 *
 */
public class FileReaderAdapter implements DataReaderPort {

	private static final Logger logger = LoggerFactory.getLogger(FileReaderAdapter.class);

	@Override
	public List<GenericObject> readData(final String filename) {
		final List<GenericObject> dataList = new ArrayList<>();

		try (InputStream file = new FileInputStream(new File(filename));
				XSSFWorkbook workbook = new XSSFWorkbook(file);) {

			final XSSFSheet sheet = workbook.getSheetAt(0);

			final Iterator<Row> rowIterator = sheet.iterator();
			final List<String> headers = new ArrayList<>();

			while (rowIterator.hasNext()) {

				final Row row = rowIterator.next();
				final Iterator<Cell> cellIterator = row.iterator();

				if (headers.isEmpty()) {
					FileReaderAdapter.logger.info("Filling the headers....");
					this.fillHeaders(headers, cellIterator);
					continue;
				}

				this.fillObjects(cellIterator, headers, dataList);
			}

			FileReaderAdapter.logger.info("Finish to read the file...");

		} catch (final Exception e) {
			FileReaderAdapter.logger.error("Error reading a file....");
			e.printStackTrace();
		}

		return dataList;
	}

	private void fillObjects(final Iterator<Cell> cellIterator, final List<String> headers,
			final List<GenericObject> dataList) {
		int cellNumber = 0;

		final GenericObject data = new GenericObject();

		while (cellIterator.hasNext()) {
			final Cell cell = cellIterator.next();

			if (CellType.NUMERIC == cell.getCellType()) {
				data.putValue(headers.get(cellNumber), cell.getNumericCellValue());
			} else if (CellType.STRING == cell.getCellType()) {
				data.putValue(headers.get(cellNumber), cell.getStringCellValue());
			} else if (CellType.BOOLEAN == cell.getCellType()) {
				data.putValue(headers.get(cellNumber), cell.getBooleanCellValue());
			}

			cellNumber++;
		}

		dataList.add(data);
	}

	private void fillHeaders(final List<String> headers, final Iterator<Cell> cellIterator) {
		while (cellIterator.hasNext()) {
			final Cell cell = cellIterator.next();
			headers.add(cell.getStringCellValue());
		}
	}
}
