package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForAlertsBook;
import com.API.Response.alertList;
import com.API.ServiceEnum.ServiceEndpoint;
import com.API.Utils.ExcelOperation;
import com.API.Utils.FrameworkServices;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import net.minidev.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class ModifyAlerts.
 */
public class ModifyAlerts {
	
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
			parentJsonObject.put("request",generateModifyAlertsRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("echo",generateModifyAlertsEchoJSON(workbook, "DT_ModifyAlertsEcho", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Modify Alerts</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}
	
	/**
	 * Generate modify alerts echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateModifyAlertsEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject modifyAlertEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			modifyAlertEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return modifyAlertEchoJsonObject;
	}
	
	/**
	 * Generate modify alerts request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateModifyAlertsRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject modifyAlertRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			modifyAlertRequestJsonObject.put("data",generateModifyAlertDataJSON(workbook, "DT_AlertBookDataForModifyAlert", scenarioID, excelOperation));
			modifyAlertRequestJsonObject.put("appID", json.get("appID"));
			modifyAlertRequestJsonObject.put("formFactor", json.get("formFactor"));
			modifyAlertRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return modifyAlertRequestJsonObject;
	}
	
	/**
	 * Generate modify alert data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateModifyAlertDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject modifyAlertRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			modifyAlertRequestJsonObject.put("AlertType",generateAlertTypeDataJSON(workbook, "DT_AlertTypeForModifyAlerts", scenarioID, excelOperation));
			modifyAlertRequestJsonObject.put("AlertData",generateAlertDataDataJSON(workbook, "DT_AlertDataForModifyAlert", scenarioID, excelOperation));
		}
		return modifyAlertRequestJsonObject;
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
		JSONObject modifyAlertRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			modifyAlertRequestJsonObject.put("ID", json.get("ID"));
			modifyAlertRequestJsonObject.put("MarketAlertType", json.get("MarketAlertType"));
			modifyAlertRequestJsonObject.put("Source", json.get("Source"));
			modifyAlertRequestJsonObject.put("UserID", json.get("UserID"));
			modifyAlertRequestJsonObject.put("ExpiryDate", json.get("ExpiryDate"));
			modifyAlertRequestJsonObject.put("MarketAlertTriggerType", json.get("MarketAlertTriggerType"));
			modifyAlertRequestJsonObject.put("MobileNumber", json.get("MobileNumber"));
			modifyAlertRequestJsonObject.put("InstrumentID", json.get("InstrumentID"));
			modifyAlertRequestJsonObject.put("MarketAlertPriceType", json.get("MarketAlertPriceType"));
			modifyAlertRequestJsonObject.put("MarketAlertWhenPrice", json.get("MarketAlertWhenPrice"));
			modifyAlertRequestJsonObject.put("AlertPrice", json.get("AlertPrice"));
			modifyAlertRequestJsonObject.put("Email", json.get("Email"));
			modifyAlertRequestJsonObject.put("Remarks", json.get("Remarks"));
		}
		return modifyAlertRequestJsonObject;
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
		JSONObject modifyAlertRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			modifyAlertRequestJsonObject.put("MarketAlertType", json.get("MarketAlertType"));
			modifyAlertRequestJsonObject.put("MarketAlertOperationType", json.get("MarketAlertOperationType"));
		}
		return modifyAlertRequestJsonObject;
	}
	
	/**
	 * Gets the modify alert.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the modify alert
	 * @throws Exception the exception
	 */
	public void getModifyAlert(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Alerts_ModifyAlerts.getUrl();

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
				Assert.assertEquals("0", infoid);
				Reporter.log("<b>Response Received Successfully</b>");
			}
			else {

				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String infomsgs = ((JsonObject) jsonObject1.get("response")).get("infoMsg").toString();

				Reporter.log("<b>Message ID From Response is ---> </b>" +"<b>"+infomsgs+"</b>");
				
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
	 * Gets the modify alert for wrong data.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the modify alert for wrong data
	 * @throws Exception the exception
	 */
	public void getModifyAlertForWrongData(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Alerts_ModifyAlerts.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());

			Gson gson=new Gson();
			GetResponseForAlertsBook getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForAlertsBook.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			//JsonArray result = (((JsonArray) ((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("alertList")).getAsJsonArray());
			//List<alertList> alertList=getAllApplicationResponse.getResponse().getData().getAlertList();
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			
				if(!infoid.equalsIgnoreCase("0")) {
					JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
					String infomsgs = ((JsonObject) jsonObject1.get("response")).get("infoMsg").toString();

					Reporter.log("<b>Message ID From Response is ---> </b>" +"<b>"+infomsgs+"</b>");
					//Reporter.log("<b>Failed due to ---> Entered Wrong OR Blank Password</b>");
					String infoid1 = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");

					//Reporter.log("<b>Message ID From Response is--></b>"+"<b>"+infoid1+"</b>");

					if(infoid1.equals("ENW0017")) {
						Assert.assertEquals("ENW0017" ,infoid1);
					}

					else if(infoid1.equals("ENW0039")){
						Assert.assertEquals("ENW0039", infoid1);
					}
					
				}	
			
			else {
				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String infomsgs = ((JsonObject) jsonObject1.get("response")).get("infoMsg").toString();
				Reporter.log("<b>Message ID From Response is ---> </b>" +"<b>"+infomsgs+"</b>");
				Assert.assertEquals("ENW0017" ,infoid);
			}
		}

		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}
}
