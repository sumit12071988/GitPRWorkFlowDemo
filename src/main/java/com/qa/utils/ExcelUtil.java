package com.qa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Reads data from Excel files
 * @author User
 *
 */
public class ExcelUtil {
	
	// NK_Selenium_TDD_2021\src\main\resources\testdata\TestData.xlsx
	public static String excelFilePath = System.getProperty("user.dir")
											+ File.separator + "src"
											+ File.separator + "main"
											+ File.separator + "resources"
											+ File.separator + "testdata"
											+ File.separator + "TestData.xlsx";	
	private static Workbook workbook;
	private static Sheet worksheet;
	
	/**
	 * To get TestData from a specific Worksheet of Excel
	 * @param worksheetName
	 * @return 2D Array filled up with data from specified worksheet
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 */
	public static Object[][] getTestData(String worksheetName) throws InvalidFormatException, IOException {
		workbook = WorkbookFactory.create(new FileInputStream(excelFilePath)); // Creating Workbook object to store exact replica of excel file to work with.
		worksheet = workbook.getSheet(worksheetName);	// Creating Worksheet object to locate the exact Worksheet inside Workbook to work with
		
		int rowsWithData = worksheet.getLastRowNum();	// Fetching last row index. This also counts Header row as index = 0.
		int columnsWithData = worksheet.getRow(0).getLastCellNum();  // Fetching 0th index row's last column index.
		
		Object[][] data = new Object[rowsWithData][columnsWithData]; // Create an EMPTY 2D Object Array & setting row count and columns count
																	 // Note: Column count doesn't change. Only number of rows changes as more data is added
		// Fetching cell data from Excel and adding in 2D Object array
		for (int i = 0; i < rowsWithData; i++) {
			for(int j=0; j < columnsWithData; j++) {
				data[i][j] = worksheet.getRow(i+1).getCell(j).toString(); 	// row(i+1) used in order to ignore Header row
			}															    // 1st Iteration: [1,0]=[dev], [1,1]=[pawar], [1,2]=[dev@gmail.com], [1,3]=[1111111111] etc.. 
		}
		return data;
	}
	
}
