package com.API.Utils;

import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// TODO: Auto-generated Javadoc
/**
 * The Class GetParameters.
 */
public class GetParameters {
	
	/**
	 * Gets the parameters.
	 *
	 * @param workbook the workbook
	 * @param scenarioID the scenario ID
	 * @return the parameters
	 */
	public static HashMap<String, String> getParameters(XSSFWorkbook workbook, String scenarioID){
		ExcelOperation excelOperation=new ExcelOperation();
		return excelOperation.getScenarioData(workbook, "DT_Parameters", scenarioID).get(0);
	}

}
