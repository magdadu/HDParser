import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelCreator {

	String filename;
	HSSFWorkbook workbook;
	HSSFSheet sheet;
	HSSFRow row;

	public ExcelCreator(String nazwa) {
		nazwa = nazwa.replace('/', '_');
		try {
			filename = "C:/HD/" + nazwa + ".xls";
			workbook = new HSSFWorkbook();
			sheet = workbook.createSheet("FirstSheet");
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void createRow(int rowNumber) {
		row = sheet.createRow( rowNumber);

	}

	public void insertValue(int columnNumber, String wartosc) {

		row.createCell(columnNumber).setCellValue(wartosc);

	}

	public void generate() {

		try {
			FileOutputStream fileOut = new FileOutputStream(filename);
			workbook.write(fileOut);
			fileOut.close();
			System.out.println("Your excel file has been generated!");
		} catch (IOException e) {
			System.out.println("Your excel  encountered problems!");
			e.printStackTrace();
		}

	}

}
