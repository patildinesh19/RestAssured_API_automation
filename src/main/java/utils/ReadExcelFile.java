package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelFile 
{	
	public ArrayList<String> readexceldatafile(String sheetname) throws IOException
	{
		ReadPropertiesfile readvalue = new ReadPropertiesfile();
		ArrayList<String> ap=new ArrayList<String>();
		FileInputStream file = new FileInputStream(readvalue.get_API_TestData_From_Excel_File());
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		//int sheets = workbook.getNumberOfSheets();
		XSSFSheet sheet = workbook.getSheet(sheetname);
		Iterator<Row> roweterator=sheet.iterator();
		Row firstrow=roweterator.next();
		while(roweterator.hasNext())
		{
			Row row = roweterator.next();
			Iterator <Cell> celliterator=row.cellIterator();
			while(celliterator.hasNext())
			{
				Cell cell= celliterator.next();
				if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
				{
				    String numericstr = NumberToTextConverter.toText(cell.getNumericCellValue());
				    ap.add(numericstr);
				}	
				else
				ap.add(cell.getStringCellValue());
			}
		}
		return ap;
	}
	
}