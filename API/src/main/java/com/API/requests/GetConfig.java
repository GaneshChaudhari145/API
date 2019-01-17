package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForAlertsBook;
import com.API.ServiceEnum.ServiceEndpoint;
import com.API.Utils.ExcelOperation;
import com.API.Utils.FrameworkServices;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import net.minidev.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class GetConfig.
 */
public class GetConfig {
	
	/**
	 * Generate parent json.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the string
	 * @throws JsonParseException the json parse exception
	 * @throws JsonMappingException the json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String generateParentJson(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) throws JsonParseException, JsonMappingException, IOException {
		try {
			JSONObject parentJsonObject=new JSONObject();
			LinkedHashMap<String, String>jsonMap=excelOperation.getScenarioData(workbook, "DT_ParentEntity", scenarioID).get(0);
			parentJsonObject.put("echo",generateGetConfigEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("request",generateGetConfigRequestJSON(workbook, "DT_ConfigRequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Config</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}

	/**
	 * Generate get config echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetConfigEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getConfigEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getConfigEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return getConfigEchoJsonObject;
	}

	/**
	 * Generate get config request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetConfigRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getConfigRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getConfigRequestJsonObject.put("data",generateConfigDataJSON(workbook, "DT_GetConfigData", scenarioID, excelOperation));
			getConfigRequestJsonObject.put("appID", json.get("appID"));
			getConfigRequestJsonObject.put("formFactor", json.get("formFactor"));
			getConfigRequestJsonObject.put("requestType", json.get("requestType"));
			getConfigRequestJsonObject.put("source", json.get("source"));
			getConfigRequestJsonObject.put("session", json.get("session"));
		}
		return getConfigRequestJsonObject;
	}

	/**
	 * Generate config data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateConfigDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getConfigJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getConfigJsonObject.put("label",json.get("label"));
			getConfigJsonObject.put("message",json.get("message"));
			getConfigJsonObject.put("menu",json.get("menu"));
			getConfigJsonObject.put("app",json.get("app"));
		}
		return getConfigJsonObject;
	}
	
	/**
	 * Gets the config.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the config
	 * @throws Exception the exception
	 */
	public void getConfig(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {

			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Config_CONFIG.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);
			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());

			Gson gson=new Gson();
			GetResponseForAlertsBook getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForAlertsBook.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");

			//int infoidss=Integer.parseInt(infoids) ;
			if(infoid.equalsIgnoreCase("0")) {
				Reporter.log("<b>"+"Response Successfully Received"+"</b>");
				Assert.assertEquals("0", infoid);

			}
			else {
				String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
				//Reporter.log("<b>"+"Response is Incorrect"+"</b>");
			}
			Assert.assertEquals("0", infoid);	
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
