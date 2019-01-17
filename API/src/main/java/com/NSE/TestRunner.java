package com.NSE;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.API.TestManagementPOJO.MasterScriptPOJO;
import com.API.Utils.ExcelOperation;
import com.API.Utils.FrameworkServices;
import com.AQMTech.Keyword.FunctionalKeyword;

// TODO: Auto-generated Javadoc
/**
 * The Class TestRunner.
 */
public class TestRunner  extends FunctionalKeyword{
	
	/** The input stream. */
	FileInputStream inputStream;
	
	/** The workbook. */
	XSSFWorkbook workbook; 
	
	/**
	 * Test.
	 *
	 * @param description the description
	 * @param scriptReference the script reference
	 * @param scenarioReference the scenario reference
	 * @throws Throwable the throwable
	 */
	@Parameters({ 
		"Description", "ScriptReference", "ScenarioReference", 
	})
	@Test(testName="Description")
	public void test(String description,String scriptReference,String scenarioReference) throws Throwable {
		ExcelOperation excelOperation=new ExcelOperation();
		boolean flag=false;
		try {
			Reporter.log("<b>Scenario Description is--></b>"+description);
			FrameworkServices frameworkServices=new FrameworkServices();
			//ExcelOperation excelOperation=new ExcelOperation();
			List<MasterScriptPOJO> scriptList=frameworkServices.getMasterScript(scriptReference);    //D:/NSE_API_TestData/DataForAPI/TestData/NSETemplate.xlsx
			FileInputStream fileInputStream=new FileInputStream(new File(FrameworkServices.getConfigProperties().getProperty("TestDataFileLocation")+"//"+FrameworkServices.getConfigProperties().getProperty("TestDataWorkBook")));
			XSSFWorkbook workbook=new XSSFWorkbook(fileInputStream);
			String sheetName="";
			for(MasterScriptPOJO masterScriptPOJO:scriptList) {
 				executeTestStep(workbook, masterScriptPOJO,sheetName, scenarioReference,excelOperation);
			}
			flag=true;
			//excelOperation.updateStatusInExcel(workbook, "TestScenarios", scenarioReference, "Passed");
		}
		catch(Exception |AssertionError e) {
			flag=false;
			Assert.assertEquals(true, false,"Failed due to exception");
			//excelOperation.updateStatusInExcel(workbook, "TestScenarios", scenarioReference, "Failed");
		}
		finally{
			FileInputStream fileInputStream=new FileInputStream(new File(FrameworkServices.getConfigProperties().getProperty("TestDataFolder")+FrameworkServices.getConfigProperties().getProperty("TestScenarioFileLocation")));
			XSSFWorkbook workbook=new XSSFWorkbook(fileInputStream);
			if(flag) {
				excelOperation.updateStatusInExcel(workbook, "TestScenarios", scenarioReference, "Passed");
			}
			else
			{
				excelOperation.updateStatusInExcel(workbook, "TestScenarios", scenarioReference, "Failed");
			}
		}
	}
}
