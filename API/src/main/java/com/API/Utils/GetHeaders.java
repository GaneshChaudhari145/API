package com.API.Utils;

import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// TODO: Auto-generated Javadoc
/**
 * The Class GetHeaders.
 */
public class GetHeaders {
	
	/**
	 * Gets the headers.
	 *
	 * @param workbook the workbook
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the headers
	 */
	public static HashMap<String, String> getHeaders(XSSFWorkbook workbook, String scenarioID,ExcelOperation excelOperation){
		return excelOperation.getScenarioData(workbook, "DT_Headers", scenarioID).get(0);
	}
}
