package Generic_Utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * its developed using Apache POI libraries, which used to handle Microsoft
 * Excel sheet
 *
 *
 */

public class ExcelUtility {

	/**
	 * its used read the data from excel base done below arguments
	 * 
	 * @param sheetName
	 * @param rowNum
	 * @param celNum
	 * @param Data
	 * @param Throwable
	 * @throws Throwable
	 * @throws EncryptedDocumentException
	 *
	 */
	public String getDataFromExcel(String sheetName, int rowNum, int celNum)
			throws EncryptedDocumentException, Throwable {
		FileInputStream fis = new FileInputStream(ConstantFilePath.excelfilepath);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sh = wb.getSheet(sheetName);
		Row row = sh.getRow(rowNum);

		// Handle null row
		if (row == null) {
			wb.close();
			fis.close();
			return "";
		}

		Cell cel = row.getCell(celNum);

		// Handle null cell
		if (cel == null) {
			wb.close();
			fis.close();
			return "";
		}

		// Use DataFormatter to handle all cell types (String, Numeric, Boolean,
		// Formula, etc.)
		DataFormatter formatter = new DataFormatter();
		String data = formatter.formatCellValue(cel);

		wb.close();
		fis.close();
		return data;

	}

	/**
	 * this method is used to get last row Number
	 * 
	 * @throws Throwable
	 * @throws EncryptedDocumentException
	 */

	public int getRowCount(String sheetName) throws EncryptedDocumentException, Throwable {
		FileInputStream fis = new FileInputStream(ConstantFilePath.excelfilepath);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sh = wb.getSheet(sheetName);
		int lastRowNum = sh.getLastRowNum();
		return lastRowNum;
	}

}
