package com.API.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.JFrame;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.API.TestManagementPOJO.MasterScriptPOJO;
import com.API.TestManagementPOJO.TestScenariosPOJO;

// TODO: Auto-generated Javadoc
/**
 * The Class FrameworkServices.
 */
public class FrameworkServices {
	
	/** The config prop. */
	static Properties configProp;
	
	/** The config input. */
	static FileInputStream configInput;
	
	/** The table config prop. */
	static Properties tableConfigProp;
	
	/** The table config input. */
	static FileInputStream tableConfigInput;
	
	/** The service config prop. */
	static Properties serviceConfigProp;
	
	/** The service config input. */
	static FileInputStream serviceConfigInput;

	/**
	 * Gets the config properties.
	 *
	 * @return the config properties
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//private XSSFWorkbook workbook;
	public static Properties getConfigProperties() throws FileNotFoundException, IOException {
		if (configProp==null) {
			configProp = new Properties();
			configInput = new FileInputStream("./config.properties");
			configProp.load(configInput);
		}
		return configProp;
	}
	
	/**
	 * Gets the table config properties.
	 *
	 * @return the table config properties
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Properties getTableConfigProperties() throws FileNotFoundException, IOException {
		if (tableConfigProp==null) {
			tableConfigProp = new Properties();
			tableConfigInput = new FileInputStream("./MasterStructure.properties");
			tableConfigProp.load(tableConfigInput);
		}
		return tableConfigProp;
	}
	
	/**
	 * Gets the service config properties.
	 *
	 * @return the service config properties
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Properties getServiceConfigProperties() throws FileNotFoundException, IOException {
		if (serviceConfigProp==null) {
			serviceConfigProp = new Properties();
			serviceConfigInput = new FileInputStream("./Service.properties");
			serviceConfigProp.load(serviceConfigInput);
		}
		return tableConfigProp;
	}

	/**
	 * Gets the test to be executed.
	 *
	 * @return the test to be executed
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public List<TestScenariosPOJO> getTestToBeExecuted() throws FileNotFoundException, IOException {
		List<TestScenariosPOJO> scenariosToBeExecuted=new ArrayList<TestScenariosPOJO>();
		FileInputStream fileInputStream=new FileInputStream(new File(getConfigProperties().getProperty("TestDataFolder")+ getConfigProperties().getProperty("TestScenarioFileLocation")));
		XSSFWorkbook workbook = new  XSSFWorkbook (fileInputStream);
		XSSFSheet testScenarioSheet=workbook.getSheet("TestScenarios");
		//	Iterator<Row> testScenarioIterator = testScenarioSheet.iterator();
		getTableConfigProperties();
		int rowCount=testScenarioSheet.getPhysicalNumberOfRows()-1;
		/*
		 * Column number
		 * 
		 */
		int Reference=Integer.parseInt(tableConfigProp.getProperty("Reference"));
		int Description= Integer.parseInt(tableConfigProp.getProperty("Description"));
		int AutomationScriptReference= Integer.parseInt(tableConfigProp.getProperty("AutomationScriptReferenceForTestScenarios"));
		int SerialNumber= Integer.parseInt(tableConfigProp.getProperty("SerialNumber"));
		int ExecuteTestScenario=Integer.parseInt(tableConfigProp.getProperty("ExecuteTestScenario"));
		int ExecutionStatus= Integer.parseInt(tableConfigProp.getProperty("ExecutionStatus"));
		int AutomationScripterName= Integer.parseInt(tableConfigProp.getProperty("AutomationScripterName"));
		int LogFile= Integer.parseInt(tableConfigProp.getProperty("LogFile"));
		int StartTime= Integer.parseInt(tableConfigProp.getProperty("StartTime"));
		int EndTime= Integer.parseInt(tableConfigProp.getProperty("EndTime"));
		int Remarks= Integer.parseInt(tableConfigProp.getProperty("Remarks"));
		for(int i=1;i<=rowCount;i++) {
			TestScenariosPOJO scenariosPOJO=new TestScenariosPOJO();
			Row rowData=testScenarioSheet.getRow(i);
			if(rowData.getCell(ExecuteTestScenario).getStringCellValue().equalsIgnoreCase("Yes")) {
				scenariosPOJO.setReference(rowData.getCell(Reference).getStringCellValue());  //CancelSIPOrder_001
				
				//scenariosPOJO.setPrerequisiteTestSuiteReference(rowData.getCell(PrerequisiteTestScenarioReference).getStringCellValue());
				//scenariosPOJO.setPrerequisiteTestScenarioReference(rowData.getCell(PrerequisiteTestScenarioReference).getStringCellValue());
				scenariosPOJO.setDescription(rowData.getCell(Description).getStringCellValue());	
				scenariosPOJO.setAutomationScriptReference(rowData.getCell(AutomationScriptReference).getStringCellValue());
				scenariosPOJO.setSerialNumber(rowData.getCell(SerialNumber).getStringCellValue());
				scenariosPOJO.setExecuteTestScenario(rowData.getCell(ExecuteTestScenario).getStringCellValue());
				scenariosPOJO.setAutomationScripterName(rowData.getCell(AutomationScripterName).getStringCellValue());
				scenariosToBeExecuted.add(scenariosPOJO);
			}
		}
		System.out.println(scenariosToBeExecuted);
		return scenariosToBeExecuted;

	}
	
	/**
	 * Gets the master script.
	 *
	 * @param ScriptReference the script reference
	 * @return the master script
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public List<MasterScriptPOJO> getMasterScript(String ScriptReference) throws FileNotFoundException, IOException{
		List<MasterScriptPOJO> masterScriptStepList=new ArrayList<MasterScriptPOJO>();
		FileInputStream fileInputStream=new FileInputStream(new File(getConfigProperties().getProperty("TestDataFolder")+"\\TestSuite\\1000_TestSuite.xlsx"));
		XSSFWorkbook workbook = new  XSSFWorkbook (fileInputStream);
		XSSFSheet masterScriptSheet=workbook.getSheet("MasterScripts");
		//	Iterator<Row> testScenarioIterator = testScenarioSheet.iterator();
		getTableConfigProperties();
		int rowCount=masterScriptSheet.getPhysicalNumberOfRows()-1;
		/*
		 * Column number
		 * 
		 */
		int Reference=Integer.parseInt(tableConfigProp.getProperty("Reference"));
		int AutomationScriptReference =Integer.parseInt(tableConfigProp.getProperty("AutomationScriptReference"));
		int StepKeyword =Integer.parseInt(tableConfigProp.getProperty("StepKeyword"));
		int ConfigData =Integer.parseInt(tableConfigProp.getProperty("ConfigData"));
		int StepGroup =Integer.parseInt(tableConfigProp.getProperty("StepGroup"));
		int SerialNumber =Integer.parseInt(tableConfigProp.getProperty("SerialNumber"));
		int ExecutionSequence =Integer.parseInt(tableConfigProp.getProperty("ExecutionSequence"));
		int SkipStep =Integer.parseInt(tableConfigProp.getProperty("SkipStep"));
		int ToBeExecutedByMachine =Integer.parseInt(tableConfigProp.getProperty("ToBeExecutedByMachine"));
		for(int i=1;i<=rowCount;i++) {
			MasterScriptPOJO masterScriptStep=new MasterScriptPOJO();
			Row rowData=masterScriptSheet.getRow(i);
			if(rowData.getCell(AutomationScriptReference).getStringCellValue().equalsIgnoreCase(ScriptReference)) {
				masterScriptStep.setReference(rowData.getCell(Reference).getStringCellValue());
				masterScriptStep.setAutomationscriptreference(rowData.getCell(AutomationScriptReference).getStringCellValue());
				masterScriptStep.setStepkeyword(rowData.getCell(StepKeyword).getStringCellValue());
				masterScriptStep.setConfigdata(rowData.getCell(ConfigData).getStringCellValue());
				masterScriptStep.setStepgroup(rowData.getCell(StepGroup).getStringCellValue());
				masterScriptStep.setSerialnumber(rowData.getCell(SerialNumber).getStringCellValue());
				masterScriptStep.setExecutionsequence(rowData.getCell(ExecutionSequence).getStringCellValue());
				masterScriptStep.setSkipstep(rowData.getCell(SkipStep).getStringCellValue());
				masterScriptStep.setTobeexecutedbymachine(rowData.getCell(ToBeExecutedByMachine).getStringCellValue());
				
				masterScriptStepList.add(masterScriptStep);
			}
		}
		System.out.println(masterScriptStepList);
		return masterScriptStepList;

	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		FrameworkServices frameworkServices=new FrameworkServices();
		frameworkServices.getTestToBeExecuted();
	}
}
