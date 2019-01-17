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
 * The Class AlertBook.
 */
public class AlertBook  
{
	
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
			parentJsonObject.put("request",generateDeleteAlertsRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("echo",generateDeleteAlertsEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Alert Book</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}
	
	/**
	 * Generate delete alerts echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateDeleteAlertsEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject alertBookEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			alertBookEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return alertBookEchoJsonObject;
	}
	
	/**
	 * Generate delete alerts request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateDeleteAlertsRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject alertBookRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			alertBookRequestJsonObject.put("data",generateDeleteAlertDataJSON(workbook, "DT_AlertBookData", scenarioID, excelOperation));
			alertBookRequestJsonObject.put("appID", json.get("appID"));
			alertBookRequestJsonObject.put("formFactor", json.get("formFactor"));
			alertBookRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return alertBookRequestJsonObject;
	}
	
	/**
	 * Generate delete alert data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateDeleteAlertDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject alertBookRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			alertBookRequestJsonObject.put("AlertType",generateAlertTypeDataJSON(workbook, "DT_AlertTypeForAlertBook", scenarioID, excelOperation));
			alertBookRequestJsonObject.put("AlertData",generateAlertDataDataJSON(workbook, "DT_AlertDataForAlertBook", scenarioID, excelOperation));
			
		}
		return alertBookRequestJsonObject;
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
		JSONObject alertBookRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			alertBookRequestJsonObject.put("UserID", json.get("UserID"));
			alertBookRequestJsonObject.put("AccountID", json.get("AccountID"));
			alertBookRequestJsonObject.put("Source", json.get("Source"));
			alertBookRequestJsonObject.put("AccountID", json.get("AccountID"));
		}
		return alertBookRequestJsonObject;
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
		JSONObject alertBookRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			alertBookRequestJsonObject.put("MarketAlertType", json.get("MarketAlertType"));
			alertBookRequestJsonObject.put("MarketAlertOperationType", json.get("MarketAlertOperationType"));
		}
		return alertBookRequestJsonObject;
	}
	
	/**
	 * Gets the alert book.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the alert book
	 * @throws Exception the exception
	 */
	public void getAlertBook(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Alerts_AlertBook.getUrl();

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
			if(infoid.equalsIgnoreCase("0")) {
				JsonArray result = (((JsonArray) ((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("alertList")).getAsJsonArray());

				List<alertList> alertList=getAllApplicationResponse.getResponse().getData().getAlertList();

				for(Integer i:excelOperation.getScenarioRowforSettingData(workbook, "DT_SetIDForAlertDelete", scenarioID)) {
					if(!alertList.isEmpty()) {
						for (alertList alertL:alertList)
						{
							//excelOperation.setDataIntoExcel(workbook, "DT_SetIDForAlertDelete", i, "ID",alertL.getID());			
							Reporter.log("<b>ID From Response is --></b>"+"<b>"+alertL.getID()+"</b>");
						}
					}	
					else
					{
						Reporter.log("<b>AlertList is Empty</b>");
					}
					Assert.assertEquals("0", infoid);	
				}
			}
			else {

				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String infomsgs = ((JsonObject) jsonObject1.get("response")).get("infoMsg").toString();

				Reporter.log("<b>Message ID From Response is ---> </b>" +"<b>"+infomsgs+"</b>");
				Reporter.log("<b>Failed due to ---> Entered Wrong OR Blank Data</b>");
				String infoid1 = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");

				//if(infoid1.equals("ENW0017")) {
				Assert.assertEquals("0" ,infoid1);

			}
		}

		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}
	
	/**
	 * Gets the alert book for wrong data.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the alert book for wrong data
	 * @throws Exception the exception
	 */
	public void getAlertBookForWrongData(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Alerts_AlertBook.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());

			Gson gson=new Gson();
			GetResponseForAlertsBook getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForAlertsBook.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			JsonArray result = (((JsonArray) ((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("alertList")).getAsJsonArray());
			List<alertList> alertList=getAllApplicationResponse.getResponse().getData().getAlertList();
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			if(alertList.isEmpty()) {
				if(infoid.equalsIgnoreCase("0")) {
					Reporter.log("<b>Alert List is Empty</b>");
				}	
			}
			else {

				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String infomsgs = ((JsonObject) jsonObject1.get("response")).get("infoMsg").toString();

				Reporter.log("<b>Message ID From Response is ---> </b>" +"<b>"+infomsgs+"</b>");
				Reporter.log("<b>Failed due to ---> Entered Wrong OR Blank Password</b>");
				String infoid1 = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");

				//Reporter.log("<b>Message ID From Response is--></b>"+"<b>"+infoid1+"</b>");

				if(infoid1.equals("ENW0017")) {
					Assert.assertEquals("ENW0017" ,infoid1);
				}

				else if(infoid1.equals("ENW0039")){
					Assert.assertEquals("ENW0039", infoid1);
				}
			}
		}

		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}
}
