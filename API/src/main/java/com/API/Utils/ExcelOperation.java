package com.API.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// TODO: Auto-generated Javadoc
/**
 * The Class ExcelOperation.
 */
public class ExcelOperation {
	
	/** The config prop. */
	static Properties configProp;
	
	/** The config input. */
	static FileInputStream configInput;
	
	/**
	 * Gets the config properties.
	 *
	 * @return the config properties
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Properties getConfigProperties() throws FileNotFoundException, IOException {
		if (configProp==null) {
			configProp = new Properties();
			configInput = new FileInputStream("config.properties");
			configProp.load(configInput);
		}
		return configProp;
	}
	
	/**
	 * Sets the data in excel file.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param rowNum the row num
	 * @param colNum the col num
	 * @param data the data
	 * @throws Exception the exception
	 */
	public  void setDataInExcelFile(XSSFWorkbook workbook,String sheetName,int rowNum,int colNum,String data ) throws Exception {
		try {
			String excelFilePath = FrameworkServices.getConfigProperties().getProperty("TestDataFileLocation")+"\\"+FrameworkServices.getConfigProperties().getProperty("TestDataWorkBook");
			XSSFSheet sheet=workbook.getSheet(sheetName);
			XSSFCell cell = sheet.getRow(rowNum).getCell(colNum,Row.CREATE_NULL_AS_BLANK);
			cell.setCellValue(data);
			FileOutputStream fos = new FileOutputStream(excelFilePath);
			workbook.write(fos);
			fos.close();
		} catch (Exception e){
			throw (e);
		}
	}
	
	/**
	 * Gets the scenario data.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenario the scenario
	 * @return the scenario data
	 */
	public List<LinkedHashMap<String, String>> getScenarioData(XSSFWorkbook workbook,String sheetName,String scenario){
		List<LinkedHashMap<String, String>> testDataList=new ArrayList<>();
		XSSFSheet sheet=workbook.getSheet(sheetName);
		for(int i=1;i<=sheet.getPhysicalNumberOfRows()-1;i++) {
			Row headerRow=sheet.getRow(0);
			Row row=sheet.getRow(i);
			if(row.getCell(0).getStringCellValue().equalsIgnoreCase(scenario)) {
				LinkedHashMap<String, String> testData=new LinkedHashMap<>();
				for(int j=1;j<row.getLastCellNum();j++) {
					if(row.getCell(j)!=null) {
						testData.put(headerRow.getCell(j).getStringCellValue(), row.getCell(j).getStringCellValue());
					}
					else {
						testData.put(headerRow.getCell(j).getStringCellValue(), "");
					}
				}
				//System.out.println(testData);
				testDataList.add(testData);
			}
		}
		return testDataList;
	}


	/**
	 * Gets the scenario ID.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenario the scenario
	 * @return the scenario ID
	 */
	public List<LinkedHashMap<String, String>> getScenarioID(XSSFWorkbook workbook,String sheetName,String scenario){
		List<LinkedHashMap<String, String>> testDataList=new ArrayList<>();
		XSSFSheet sheet=workbook.getSheet(sheetName);
		for(int i=1;i<=sheet.getPhysicalNumberOfRows()-1;i++) {
			Row headerRow=sheet.getRow(0);
			Row row=sheet.getRow(i);
			
			if(row.getCell(0).getStringCellValue().equalsIgnoreCase(scenario)) {
				LinkedHashMap<String, String> testData=new LinkedHashMap<>();
				for(int j=0;j<row.getPhysicalNumberOfCells();j++) {
					if(row.getCell(j)!=null) {
						testData.put(headerRow.getCell(j).getStringCellValue(), row.getCell(j).getStringCellValue());
					}
					else {
						testData.put(headerRow.getCell(j).getStringCellValue(), "");
					}
				}
				//System.out.println(testData);
				testDataList.add(testData);
			}
		}
		return testDataList;

	}


	/**
	 * Gets the scenario rowfor setting data.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @return the scenario rowfor setting data
	 */
	/*
	 * PSS for jgetting Scenario Rows in List
	 * 
	 */
	public List<Integer> getScenarioRowforSettingData(XSSFWorkbook workbook,String sheetName, String scenarioID){
		List<Integer> rowPositionList=new ArrayList<>();
		XSSFSheet sheet=workbook.getSheet(sheetName);
		for(int i=1;i<=sheet.getPhysicalNumberOfRows()-1;i++) {
			int j=0;
			Row headerRow=sheet.getRow(0);
			Row row=sheet.getRow(i);
			if(row.getCell(0).getStringCellValue().equalsIgnoreCase(scenarioID)) {
				j=row.getRowNum();
				rowPositionList.add(j);
			}
		}
		return rowPositionList;
	}
	
	/**
	 * Sets the data into excel.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param rowNumber the row number
	 * @param columnName the column name
	 * @param testData the test data
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void setDataIntoExcel(XSSFWorkbook workbook,String sheetName,int rowNumber ,String columnName, String testData) throws FileNotFoundException, IOException {
		String excelFilePath = FrameworkServices.getConfigProperties().getProperty("TestDataFileLocation")+"\\"+FrameworkServices.getConfigProperties().getProperty("TestDataWorkBook");
		XSSFSheet sheet=workbook.getSheet(sheetName);
		Row headerRow=sheet.getRow(0);

		LinkedHashMap<String, Integer> headerPosition=new LinkedHashMap<>();
		for(int columnNo=1;columnNo<headerRow.getPhysicalNumberOfCells();columnNo++) {
			headerPosition.put(headerRow.getCell(columnNo).getStringCellValue(), columnNo);
		}
		Row dataRow=sheet.getRow(rowNumber);
		XSSFCell cell=(XSSFCell) dataRow.getCell(headerPosition.get(columnName),Row.CREATE_NULL_AS_BLANK);
		cell.setCellValue(testData);
		FileOutputStream fos = new FileOutputStream(excelFilePath);
		workbook.write(fos);
		fos.close();
	}
	
	/**
	 * Update data in excel.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioId the scenario id
	 * @param columnName the column name
	 * @param data the data
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void updateDataInExcel(XSSFWorkbook workbook, String sheetName,String scenarioId,String columnName,String data) throws FileNotFoundException, IOException {
		String excelFilePath =FrameworkServices.getConfigProperties().getProperty("TestDataFileLocation")+"\\"+FrameworkServices.getConfigProperties().getProperty("TestDataWorkBook");
		XSSFSheet sheet=workbook.getSheet(sheetName);
		Row headerRow=sheet.getRow(0);
		LinkedHashMap<String, Integer> headerPosition=new LinkedHashMap<>();
		for(int columnNo=1;columnNo<headerRow.getPhysicalNumberOfCells();columnNo++) {
			headerPosition.put(headerRow.getCell(columnNo).getStringCellValue(), columnNo);
		}
		//Row dataRow=sheet.getRow(rowNumber);
		for (int i=1;i<=sheet.getPhysicalNumberOfRows();i++)
		{
			XSSFCell cell1=sheet.getRow(i).getCell(0,Row.CREATE_NULL_AS_BLANK);
			//XSSFCell cell=(XSSFCell) headerRow.getCell(headerPosition.get(columnName),Row.CREATE_NULL_AS_BLANK);
			
			if(cell1.getStringCellValue().equalsIgnoreCase(scenarioId)) {
				XSSFCell cell2 = sheet.getRow(i).getCell(2);
				cell2.setCellValue(data);
				FileOutputStream fos = new FileOutputStream(excelFilePath);
				workbook.write(fos);
				fos.close();
				break;
			}
			
		}
	}
	
	
	/**
	 * Update data in excel for instruction ID.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioId the scenario id
	 * @param columnName the column name
	 * @param data the data
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void updateDataInExcelForInstructionID(XSSFWorkbook workbook, String sheetName,String scenarioId,String columnName,String data) throws FileNotFoundException, IOException {
		String excelFilePath =FrameworkServices.getConfigProperties().getProperty("TestDataFileLocation")+"\\"+FrameworkServices.getConfigProperties().getProperty("TestDataWorkBook");
		XSSFSheet sheet=workbook.getSheet(sheetName);
		Row headerRow=sheet.getRow(0);
		LinkedHashMap<String, Integer> headerPosition=new LinkedHashMap<>();
		for(int columnNo=1;columnNo<headerRow.getPhysicalNumberOfCells();columnNo++) {
			headerPosition.put(headerRow.getCell(columnNo).getStringCellValue(), columnNo);
		}
		//Row dataRow=sheet.getRow(rowNumber);
		for (int i=1;i<=sheet.getPhysicalNumberOfRows();i++)
		{
			XSSFCell cell1=sheet.getRow(i).getCell(0,Row.CREATE_NULL_AS_BLANK);
			//XSSFCell cell=(XSSFCell) headerRow.getCell(headerPosition.get(columnName),Row.CREATE_NULL_AS_BLANK);
			
			if(cell1.getStringCellValue().equalsIgnoreCase(scenarioId)) {
				XSSFCell cell2 = sheet.getRow(i).getCell(7);
				cell2.setCellValue(data);
				FileOutputStream fos = new FileOutputStream(excelFilePath);
				workbook.write(fos);
				fos.close();
				break;
			}
			
		}
	}
	
	/**
	 * Update data in excel fororder ID.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioId the scenario id
	 * @param columnName the column name
	 * @param data the data
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void updateDataInExcelFororderID(XSSFWorkbook workbook, String sheetName,String scenarioId,String columnName,String data) throws FileNotFoundException, IOException {
		String excelFilePath =FrameworkServices.getConfigProperties().getProperty("TestDataFileLocation")+"\\"+FrameworkServices.getConfigProperties().getProperty("TestDataWorkBook");
		XSSFSheet sheet=workbook.getSheet(sheetName);
		Row headerRow=sheet.getRow(0);
		LinkedHashMap<String, Integer> headerPosition=new LinkedHashMap<>();
		for(int columnNo=1;columnNo<headerRow.getPhysicalNumberOfCells();columnNo++) {
			headerPosition.put(headerRow.getCell(columnNo).getStringCellValue(), columnNo);
		}
		//Row dataRow=sheet.getRow(rowNumber);
		for (int i=1;i<=sheet.getPhysicalNumberOfRows();i++)
		{
			XSSFCell cell1=sheet.getRow(i).getCell(0,Row.CREATE_NULL_AS_BLANK);
			//XSSFCell cell=(XSSFCell) headerRow.getCell(headerPosition.get(columnName),Row.CREATE_NULL_AS_BLANK);
			
			if(cell1.getStringCellValue().equalsIgnoreCase(scenarioId)) {
				XSSFCell cell2 = sheet.getRow(i).getCell(3);
				cell2.setCellValue(data);
				FileOutputStream fos = new FileOutputStream(excelFilePath);
				workbook.write(fos);
				fos.close();
				break;
			}
			
		}
	}
	
	/**
	 * Update data in excel fortrade ID.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioId the scenario id
	 * @param columnName the column name
	 * @param data the data
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void updateDataInExcelFortradeID(XSSFWorkbook workbook, String sheetName,String scenarioId,String columnName,String data) throws FileNotFoundException, IOException {
		String excelFilePath =FrameworkServices.getConfigProperties().getProperty("TestDataFileLocation")+"\\"+FrameworkServices.getConfigProperties().getProperty("TestDataWorkBook");
		XSSFSheet sheet=workbook.getSheet(sheetName);
		Row headerRow=sheet.getRow(0);
		LinkedHashMap<String, Integer> headerPosition=new LinkedHashMap<>();
		for(int columnNo=1;columnNo<headerRow.getPhysicalNumberOfCells();columnNo++) {
			headerPosition.put(headerRow.getCell(columnNo).getStringCellValue(), columnNo);
		}
		//Row dataRow=sheet.getRow(rowNumber);
		for (int i=1;i<=sheet.getPhysicalNumberOfRows();i++)
		{
			XSSFCell cell1=sheet.getRow(i).getCell(0,Row.CREATE_NULL_AS_BLANK);
			//XSSFCell cell=(XSSFCell) headerRow.getCell(headerPosition.get(columnName),Row.CREATE_NULL_AS_BLANK);
			
			if(cell1.getStringCellValue().equalsIgnoreCase(scenarioId)) {
				XSSFCell cell2 = sheet.getRow(i).getCell(4);
				cell2.setCellValue(data);
				FileOutputStream fos = new FileOutputStream(excelFilePath);
				workbook.write(fos);
				fos.close();
				break;
			}
			
		}
	}
	
	
	
	/**
	 * Update data for order ID.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioId the scenario id
	 * @param columnName the column name
	 * @param data the data
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void updateDataForOrderID(XSSFWorkbook workbook, String sheetName,String scenarioId,String columnName,String data) throws FileNotFoundException, IOException {
		String excelFilePath =FrameworkServices.getConfigProperties().getProperty("TestDataFileLocation")+"\\"+FrameworkServices.getConfigProperties().getProperty("TestDataWorkBook");
		XSSFSheet sheet=workbook.getSheet(sheetName);
		Row headerRow=sheet.getRow(0);
		LinkedHashMap<String, Integer> headerPosition=new LinkedHashMap<>();
		for(int columnNo=1;columnNo<headerRow.getPhysicalNumberOfCells();columnNo++) {
			headerPosition.put(headerRow.getCell(columnNo).getStringCellValue(), columnNo);
		}
		//Row dataRow=sheet.getRow(rowNumber);
		for (int i=1;i<=sheet.getPhysicalNumberOfRows();i++)
		{
			XSSFCell cell1=sheet.getRow(i).getCell(0,Row.CREATE_NULL_AS_BLANK);
			XSSFCell cell=(XSSFCell) headerRow.getCell(headerPosition.get(columnName),Row.CREATE_NULL_AS_BLANK);
			
			if(cell1.getStringCellValue().equalsIgnoreCase(scenarioId)) {
				XSSFRow Row = sheet.getRow(i);
				XSSFCell cell2=Row.createCell(1);
				cell2.setCellValue(data);
				FileOutputStream fos = new FileOutputStream(excelFilePath);
				workbook.write(fos);
				fos.close();
				break;
			}
			
		}
	}
	
	/**
	 * Update data LTP.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioId the scenario id
	 * @param columnName the column name
	 * @param data the data
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void updateDataLTP(XSSFWorkbook workbook, String sheetName,String scenarioId,String columnName,String data) throws FileNotFoundException, IOException {
		String excelFilePath =FrameworkServices.getConfigProperties().getProperty("TestDataFileLocation")+"\\"+FrameworkServices.getConfigProperties().getProperty("TestDataWorkBook");
		XSSFSheet sheet=workbook.getSheet(sheetName);
		Row headerRow=sheet.getRow(0);
		LinkedHashMap<String, Integer> headerPosition=new LinkedHashMap<>();
		for(int columnNo=1;columnNo<headerRow.getPhysicalNumberOfCells();columnNo++) {
			headerPosition.put(headerRow.getCell(columnNo).getStringCellValue(), columnNo);
		}
		
		
		//Row dataRow=sheet.getRow(rowNumber);
		for (int i=1;i<=sheet.getPhysicalNumberOfRows();i++)
		{
			
			
				XSSFCell cell1=sheet.getRow(i).getCell(0,Row.CREATE_NULL_AS_BLANK);
				XSSFCell cell=(XSSFCell) headerRow.getCell(headerPosition.get(columnName),Row.CREATE_NULL_AS_BLANK);
				
				if(cell1.getStringCellValue().equalsIgnoreCase(scenarioId)) {
					XSSFRow Row = sheet.getRow(i);
					if (sheetName.equalsIgnoreCase("DT_ModifyOrderData")) {
						XSSFCell cell2=Row.createCell(16);
						cell2.setCellValue(data);
						FileOutputStream fos = new FileOutputStream(excelFilePath);
						workbook.write(fos);
						fos.close();
						break;
					}
					else {
					XSSFCell cell2=Row.createCell(12);
					cell2.setCellValue(data);
					FileOutputStream fos = new FileOutputStream(excelFilePath);
					workbook.write(fos);
					fos.close();
					break;
					}
					
				}
			
		}
	}
	
	/**
	 * Update trigger price for cover order.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioId the scenario id
	 * @param columnName the column name
	 * @param data the data
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void updateTriggerPriceForCoverOrder(XSSFWorkbook workbook, String sheetName,String scenarioId,String columnName,String data) throws FileNotFoundException, IOException {
		String excelFilePath =FrameworkServices.getConfigProperties().getProperty("TestDataFileLocation")+"\\"+FrameworkServices.getConfigProperties().getProperty("TestDataWorkBook");
		XSSFSheet sheet=workbook.getSheet(sheetName);
		Row headerRow=sheet.getRow(0);
		LinkedHashMap<String, Integer> headerPosition=new LinkedHashMap<>();
		for(int columnNo=1;columnNo<headerRow.getPhysicalNumberOfCells();columnNo++) {
			headerPosition.put(headerRow.getCell(columnNo).getStringCellValue(), columnNo);
		}
		//Row dataRow=sheet.getRow(rowNumber);
		for (int i=1;i<=sheet.getPhysicalNumberOfRows();i++)
		{
			XSSFCell cell11=sheet.getRow(i).getCell(0,Row.CREATE_NULL_AS_BLANK);
			XSSFCell cellData=(XSSFCell) headerRow.getCell(headerPosition.get(columnName),Row.CREATE_NULL_AS_BLANK);
			
			if(cell11.getStringCellValue().equalsIgnoreCase(scenarioId)) {
				XSSFRow Row = sheet.getRow(i);
				XSSFCell cell2=Row.createCell(7);
				cell2.setCellValue(data);
				FileOutputStream fos = new FileOutputStream(excelFilePath);
				workbook.write(fos);
				fos.close();
				break;
			}
			
		}
		
	}
	
	/**
	 * Update data for trigger price.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioId the scenario id
	 * @param columnName the column name
	 * @param data the data
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void updateDataForTriggerPrice(XSSFWorkbook workbook, String sheetName,String scenarioId,String columnName,String data) throws FileNotFoundException, IOException {
		String excelFilePath =FrameworkServices.getConfigProperties().getProperty("TestDataFileLocation")+"\\"+FrameworkServices.getConfigProperties().getProperty("TestDataWorkBook");
		XSSFSheet sheet=workbook.getSheet(sheetName);
		Row headerRow=sheet.getRow(0);
		LinkedHashMap<String, Integer> headerPosition=new LinkedHashMap<>();
		for(int columnNo=1;columnNo<headerRow.getPhysicalNumberOfCells();columnNo++) {
			headerPosition.put(headerRow.getCell(columnNo).getStringCellValue(), columnNo);
		}
		
		
		//Row dataRow=sheet.getRow(rowNumber);
		for (int i=1;i<=sheet.getPhysicalNumberOfRows();i++)
		{
			
			
				XSSFCell cell1=sheet.getRow(i).getCell(0,Row.CREATE_NULL_AS_BLANK);
				XSSFCell cell=(XSSFCell) headerRow.getCell(headerPosition.get(columnName),Row.CREATE_NULL_AS_BLANK);
				
				if(cell1.getStringCellValue().equalsIgnoreCase(scenarioId)) {
					XSSFRow Row = sheet.getRow(i);
					if (sheetName.equalsIgnoreCase("DT_ModifyOrderData")) {
						XSSFCell cell2=Row.createCell(15);
						cell2.setCellValue(data);
						FileOutputStream fos = new FileOutputStream(excelFilePath);
						workbook.write(fos);
						fos.close();
						break;
					}
					else {
					XSSFCell cell2=Row.createCell(14);
					cell2.setCellValue(data);
					FileOutputStream fos = new FileOutputStream(excelFilePath);
					workbook.write(fos);
					fos.close();
					break;
					}
					
				}
			
		}
	}
	
	/**
	 * Update order ID for modify order.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioId the scenario id
	 * @param columnName the column name
	 * @param data the data
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void updateOrderIDForModifyOrder(XSSFWorkbook workbook, String sheetName,String scenarioId,String columnName,String data) throws FileNotFoundException, IOException {
		String excelFilePath =FrameworkServices.getConfigProperties().getProperty("TestDataFileLocation")+"\\"+FrameworkServices.getConfigProperties().getProperty("TestDataWorkBook");
		XSSFSheet sheet=workbook.getSheet(sheetName);
		Row headerRow=sheet.getRow(0);
		LinkedHashMap<String, Integer> headerPosition=new LinkedHashMap<>();
		for(int columnNo=1;columnNo<headerRow.getPhysicalNumberOfCells();columnNo++) {
			headerPosition.put(headerRow.getCell(columnNo).getStringCellValue(), columnNo);
		}
		//Row dataRow=sheet.getRow(rowNumber);
		for (int i=1;i<=sheet.getPhysicalNumberOfRows();i++)
		{
			XSSFCell cell1=sheet.getRow(i).getCell(0,Row.CREATE_NULL_AS_BLANK);
			XSSFCell cell=(XSSFCell) headerRow.getCell(headerPosition.get(columnName),Row.CREATE_NULL_AS_BLANK);
			
			if(cell1.getStringCellValue().equalsIgnoreCase(scenarioId)) {
				XSSFRow Row = sheet.getRow(i);
				XSSFCell cell2=Row.createCell(4);
				cell2.setCellValue(data);
				FileOutputStream fos = new FileOutputStream(excelFilePath);
				workbook.write(fos);
				fos.close();
				break;
			}
			
		}
	}
	
	/**
	 * Update validate password data in excel.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioId the scenario id
	 * @param columnName the column name
	 * @param data the data
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void updateValidatePasswordDataInExcel(XSSFWorkbook workbook, String sheetName,String scenarioId,String columnName,String data) throws FileNotFoundException, IOException {
		String excelFilePath =FrameworkServices.getConfigProperties().getProperty("TestDataFileLocation")+"\\"+FrameworkServices.getConfigProperties().getProperty("TestDataWorkBook");
		XSSFSheet sheet=workbook.getSheet(sheetName);
		Row headerRow=sheet.getRow(0);
		LinkedHashMap<String, Integer> headerPosition=new LinkedHashMap<>();
		for(int columnNo=1;columnNo<headerRow.getPhysicalNumberOfCells();columnNo++) {
			headerPosition.put(headerRow.getCell(columnNo).getStringCellValue(), columnNo);
		}
		//Row dataRow=sheet.getRow(rowNumber);
		for (int i=1;i<=sheet.getPhysicalNumberOfRows();i++)
		{
			XSSFCell cell1=sheet.getRow(i).getCell(0,Row.CREATE_NULL_AS_BLANK);
			XSSFCell cell=(XSSFCell) headerRow.getCell(headerPosition.get(columnName),Row.CREATE_NULL_AS_BLANK);
			
			if(cell1.getStringCellValue().equalsIgnoreCase(scenarioId)) {
				XSSFCell cell2 = sheet.getRow(i).getCell(4);
				cell2.setCellValue(data);
				FileOutputStream fos = new FileOutputStream(excelFilePath);
				workbook.write(fos);
				fos.close();
				break;
			}
		}
	}
	
	
	/**
	 * Close workbook.
	 *
	 * @param workbook the workbook
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void closeWorkbook(XSSFWorkbook workbook) throws IOException {
		String excelFilePath = FrameworkServices.getConfigProperties().getProperty("TestDataFileLocation")+"\\"+FrameworkServices.getConfigProperties().getProperty("TestDataWorkBook");
		FileOutputStream fos = new FileOutputStream(excelFilePath);
		workbook.write(fos);
		fos.close();
	}
	
	/**
	 * Creates the scenario data from response.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioId the scenario id
	 * @param rowsToBeAdded the rows to be added
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void createScenarioDataFromResponse(XSSFWorkbook workbook, String sheetName,String scenarioId,int rowsToBeAdded) throws IOException {

		XSSFSheet sheet=workbook.getSheet(sheetName);
		for(int i=1;i<=rowsToBeAdded;i++) {
			int currentRow=sheet.getPhysicalNumberOfRows();
			sheet.createRow(currentRow);
			XSSFCell cell=sheet.getRow(currentRow).getCell(0,Row.CREATE_NULL_AS_BLANK);
			cell.setCellValue(scenarioId);

		}
		closeWorkbook(workbook);
	}
	
	/**
	 * Update status in excel.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioId the scenario id
	 * @param data the data
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void updateStatusInExcel(XSSFWorkbook workbook, String sheetName,String scenarioId, String data) throws FileNotFoundException, IOException {
		String excelFilePath = FrameworkServices.getConfigProperties().getProperty("TestDataFolder")+FrameworkServices.getConfigProperties().getProperty("TestScenarioFileLocation");
		XSSFSheet sheet=workbook.getSheet(sheetName);
		for (int i=1;i<=sheet.getPhysicalNumberOfRows();i++)
		{
			XSSFCell cell1=sheet.getRow(i).getCell(0,Row.CREATE_NULL_AS_BLANK);
			if(cell1.getStringCellValue().equalsIgnoreCase(scenarioId)) {
				XSSFCell cell = sheet.getRow(i).getCell(5);
				cell.setCellValue(data);
				FileOutputStream fos = new FileOutputStream(excelFilePath);
				workbook.write(fos);
				fos.close();
				break;
			}
		}
	}
	
	
	
}
