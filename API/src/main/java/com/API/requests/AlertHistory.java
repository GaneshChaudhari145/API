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
 * The Class AlertHistory.
 */
public class AlertHistory {
	
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
			parentJsonObject.put("request",generateAlertHistoryRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("echo",generateAlertHistoryEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Alert History</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}
	
	/**
	 * Generate alert history echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateAlertHistoryEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject alertHistoryEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			alertHistoryEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return alertHistoryEchoJsonObject;
	}
	
	/**
	 * Generate alert history request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateAlertHistoryRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject alertHistoryRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			alertHistoryRequestJsonObject.put("data",generateAlertHistoryDataJSON(workbook, "DT_LogoutData", scenarioID, excelOperation));
			alertHistoryRequestJsonObject.put("appID", json.get("appID"));
			alertHistoryRequestJsonObject.put("formFactor", json.get("formFactor"));
			alertHistoryRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return alertHistoryRequestJsonObject;
	}
	
	/**
	 * Generate alert history data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateAlertHistoryDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject alertHistoryRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			alertHistoryRequestJsonObject.put("userID",json.get("userID"));
		}
		return alertHistoryRequestJsonObject;
	}

	/**
	 * Gets the alert history.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the alert history
	 * @throws Exception the exception
	 */
	public void getAlertHistory(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Alerts_AlertHistory.getUrl();

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
							Reporter.log("<b>UserID From Response is --></b>"+"<b>"+alertL.getUserID()+"</b>");
							Reporter.log("<b>Email From Response is --></b>"+"<b>"+alertL.getEmail()+"</b>");
							Reporter.log("<b>Symbol From Response is --></b>"+"<b>"+alertL.getSymbol()+"</b>");
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
				//Reporter.log("<b>Failed due to ---> Entered Wrong OR Blank Data</b>");
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
	 * Invalid get alert history.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void invalidGetAlertHistory(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Alerts_AlertHistory.getUrl();

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
			if(!infoid.equalsIgnoreCase("0")) {
				String infomsg = ((JsonObject) jsonObject.get("response")).get("infoMsg").toString();
				Reporter.log("<b>Message ID From Response is ---> </b>" +"<b>"+infomsg+"</b>");
				Assert.assertEquals("ENW0042", infoid);	
			}

			else {

				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String infomsgs = ((JsonObject) jsonObject1.get("response")).get("infoMsg").toString();
				Reporter.log("<b>Message ID From Response is ---> </b>" +"<b>"+infomsgs+"</b>");
				String infoid1 = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
				Assert.assertEquals("ENW0042" ,infoid1);

			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}
}
