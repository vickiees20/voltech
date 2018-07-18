package test.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class CommonUtils {

	
	
	public static List<HashMap<String, String>> mydata = new ArrayList<HashMap<String, String>>();
	
	@SuppressWarnings("deprecation")
	public void convertExcelToMap(String FilePath,String FileName,String sheetName) throws IOException{
		mydata.clear();
		String MyFile= FilePath+"\\"+FileName;
		FileInputStream fin = new FileInputStream(MyFile);
		HSSFWorkbook book = new HSSFWorkbook(fin);
		HSSFSheet sheet = book.getSheet(sheetName);
		for(int i=0;i<sheet.getPhysicalNumberOfRows();i++)
		{
		    Row currentRow = sheet.getRow(i);
		    for(int j=0;j<currentRow.getPhysicalNumberOfCells();j++)
		    {
		        Cell currentCell = currentRow.getCell(j);
		        switch (currentCell.getCellType())
		        {
		            case Cell.CELL_TYPE_STRING:
		                System.out.print(currentCell.getStringCellValue() + "|");
		                break;
		            case Cell.CELL_TYPE_NUMERIC:
		                System.out.print(currentCell.getNumericCellValue() + "|");
		                break;
		              
		            case Cell.CELL_TYPE_BLANK:
		                System.out.print("<blank>|");
		                break;
		        }
		    }
		    System.out.println("\n");
		}
//		List<HashMap<String, String>> mydata = new ArrayList<HashMap<String, String>>();
		Row HeaderRow = sheet.getRow(0);
		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
		    Row currentRow = sheet.getRow(i);
		    HashMap<String, String> currentHash = new HashMap<String, String>();
		    for (int j = 0; j < currentRow.getPhysicalNumberOfCells(); j++) {
		        Cell currentCell = currentRow.getCell(j);
		        switch (currentCell.getCellType()) {
		            case Cell.CELL_TYPE_STRING:
		                currentHash.put(HeaderRow.getCell(j).getStringCellValue(), currentCell.getStringCellValue());
		                break;
		            case Cell.CELL_TYPE_NUMERIC:
		                currentHash.put(HeaderRow.getCell(j).getStringCellValue(), String.valueOf(currentCell.getNumericCellValue()));
		                break;
		        }
		    }
//	        System.out.println(currentHash.get("username"));
		    mydata.add(currentHash);
		}
		System.out.println(mydata);
		System.out.println(mydata.get(0));
	}
	
	
	
	public String readColumn(String column,int row){
		HashMap<String, String> map = mydata.get(row);
		String result=null;
        for (Entry<String, String> entry : map.entrySet()) {
        	if(entry.getKey().equalsIgnoreCase(column)){
        		System.out.println(entry.getValue());
        		result = entry.getValue();
        	}

        }
       return result;
       
       
	}
}
