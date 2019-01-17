package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForSetAlert;
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
 * The Class SetAlerts.
 */
public class SetAlerts {
	
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
			parentJsonObject.put("request",generateSetAlertsRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("echo",generateSetAlertsEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Set Alerts</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}
	
	/**
	 * Generate set alerts echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateSetAlertsEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject setAlertEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			setAlertEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return setAlertEchoJsonObject;
	}
	
	/**
	 * Generate set alerts request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateSetAlertsRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject setAlertRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			setAlertRequestJsonObject.put("data",generateSetAlertDataJSON(workbook, "DT_AlertBookData", scenarioID, excelOperation));
			setAlertRequestJsonObject.put("appID", json.get("appID"));
			setAlertRequestJsonObject.put("formFactor", json.get("formFactor"));
			setAlertRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return setAlertRequestJsonObject;
	}
	
	/**
	 * Generate set alert data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateSetAlertDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject setAlertRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			setAlertRequestJsonObject.put("AlertType",generateAlertTypeDataJSON(workbook, "DT_AlertTypeForAlertBook", scenarioID, excelOperation));
			setAlertRequestJsonObject.put("AlertData",generateAlertDataDataJSON(workbook, "DT_AlertDataForSetAlert", scenarioID, excelOperation));
		}
		return setAlertRequestJsonObject;
	}
	
	/**
	 * Generate alert data data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateAlertDataDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject setAlertRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			setAlertRequestJsonObject.put("ID", json.get("ID"));
			setAlertRequestJsonObject.put("MarketAlertType", json.get("MarketAlertType"));
			setAlertRequestJsonObject.put("Source", json.get("Source"));
			setAlertRequestJsonObject.put("UserID", json.get("UserID"));
			setAlertRequestJsonObject.put("ExpiryDate", json.get("ExpiryDate"));
			setAlertRequestJsonObject.put("MarketAlertTriggerType", json.get("MarketAlertTriggerType"));
			setAlertRequestJsonObject.put("ExchangeSegment", json.get("ExchangeSegment"));
			setAlertRequestJsonObject.put("InstrumentID", json.get("InstrumentID"));
			setAlertRequestJsonObject.put("MarketAlertPriceType", json.get("MarketAlertPriceType"));
			setAlertRequestJsonObject.put("MarketAlertWhenPrice", json.get("MarketAlertWhenPrice"));
			setAlertRequestJsonObject.put("AlertPrice", json.get("AlertPrice"));
			setAlertRequestJsonObject.put("Email", json.get("Email"));
		}
		return setAlertRequestJsonObject;
	}

	/**
	 * Generate alert type data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateAlertTypeDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject setAlertRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			setAlertRequestJsonObject.put("MarketAlertType", json.get("MarketAlertType"));
			setAlertRequestJsonObject.put("MarketAlertOperationType", json.get("MarketAlertOperationType"));
		}
		return setAlertRequestJsonObject;
	}
	
	/**
	 * Gets the sets the alert.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the sets the alert
	 * @throws Exception the exception
	 */
	public void getSetAlert(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Alerts_SetAlerts.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());

			Gson gson=new Gson();
			GetResponseForSetAlert getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForSetAlert.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			//int infoidss=Integer.parseInt(infoids) ;
			if(infoid.equalsIgnoreCase("0")) {
				String result = ((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("alertID").toString();
				Reporter.log("<b>Set Alert ID From Response is--></b>"+"<b>"+result+"</b>");
				excelOperation.setDataIntoExcel(workbook, "DT_AlertDataForModifyAlert",1, "ID",result);
				excelOperation.setDataIntoExcel(workbook, "DT_SetIDForAlertDelete",2, "ID",result);
				Assert.assertEquals("0", infoid);
			}
			else {

				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String infomsgs = ((JsonObject) jsonObject1.get("response")).get("infoMsg").toString();

				Reporter.log("<b>Message ID From Response is ---> </b>" +"<b>"+infomsgs+"</b>");
				Reporter.log("<b>Failed due to ---> Entered Wrong OR Blank Password</b>");
				String infoid1 = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
				Assert.assertEquals("0", infoid1);
			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}

	/**
	 * Gets the sets the alert for wrong data.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the sets the alert for wrong data
	 * @throws Exception the exception
	 */
	public void getSetAlertForWrongData(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Alerts_SetAlerts.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());

			Gson gson=new Gson();
			GetResponseForSetAlert getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForSetAlert.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			String infomsgs = ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
			if(!infoid.equalsIgnoreCase("0")) {	
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsgs+"</b>");
				Assert.assertEquals("ENW0017", infoid);
				Reporter.log("<b>Failed due to ---> Entered Wrong OR Blank Alert Data</b>");				
			}
			else {
				Reporter.log("<b>Message ID From Response is ---> </b>" +"<b>"+infomsgs+"</b>");	
				Assert.assertEquals("ENW0017", infoid);
			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}
}
