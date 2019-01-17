package com.NSE;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ITestNGListener;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.API.TestManagementPOJO.MasterScriptPOJO;
import com.API.TestManagementPOJO.TestScenariosPOJO;
import com.API.Utils.FrameworkServices;
import com.API.Utils.ListenerClass;

// TODO: Auto-generated Javadoc
/**
 * The Class TestEngine.
 */
public class TestEngine {

	/**
	 * The main method.
	 *
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//public void aPIAutomation() throws FileNotFoundException, IOException {
	public static void Start() throws FileNotFoundException, IOException {
		try {
			
			FrameworkServices frameworkServices=new FrameworkServices();
			TestNG testNG=new TestNG();
			 ListenerClass frl = new ListenerClass();
			 testNG.addListener(frl);
			Date date = new Date();  
			SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy hh mm ss");  
			String strDate= formatter.format(date);  
			testNG.setOutputDirectory(frameworkServices.getConfigProperties().getProperty("Execution_Folder")+strDate);
			XmlSuite suite = new XmlSuite();
			suite.setName("API EXECUTION Suite");
			suite.setParallel(XmlSuite.ParallelMode.FALSE);
			List<XmlSuite> xmlSuites = new ArrayList<XmlSuite>();
			xmlSuites.add(suite);
			for(TestScenariosPOJO testScenariosPOJO:frameworkServices.getTestToBeExecuted()) {
				XmlTest test=new XmlTest(suite);
				test.setName(testScenariosPOJO.getReference());
				test.setVerbose(3);
				HashMap<String, String> parameter=new HashMap<String, String>();
				parameter.put("Description", testScenariosPOJO.getDescription());
				parameter.put("ScriptReference", testScenariosPOJO.getAutomationScriptReference());  //
				parameter.put("ScenarioReference", testScenariosPOJO.getReference());
				test.setParameters(parameter);
				List<XmlClass> classes = new ArrayList<XmlClass>();
				classes.add(new XmlClass("com.NSE.TestRunner"));
				test.setXmlClasses(classes);
			}
			//	xmlSuites.add(suite);
			testNG.setXmlSuites(xmlSuites);
			try {
				testNG.run();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}catch(Exception e) {
			e.printStackTrace();}
	}
}
