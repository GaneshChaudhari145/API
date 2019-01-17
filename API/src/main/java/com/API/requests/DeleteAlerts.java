package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForAlertsBook;
import com.API.Response.GetResponseForDeleteAlert;
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
 * The Class DeleteAlerts.
 */
public class DeleteAlerts {
	
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
			parentJsonObject.put("echo",generateDeleteAlertsEchoJSON(workbook, "DT_DeleteAlertsEcho", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Delete Alert</font></b>");
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
		JSONObject deleteAlertsEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			deleteAlertsEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return deleteAlertsEchoJsonObject;
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
		JSONObject deleteAlertsRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			deleteAlertsRequestJsonObject.put("data",generateDeleteAlertDataJSON(workbook, "DT_AlertBookDataForDeleteAlert", scenarioID, excelOperation));
			deleteAlertsRequestJsonObject.put("appID", json.get("appID"));
			deleteAlertsRequestJsonObject.put("formFactor", json.get("formFactor"));
			deleteAlertsRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return deleteAlertsRequestJsonObject;
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
		JSONObject deleteAlertsRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			deleteAlertsRequestJsonObject.put("AlertType",generateDeleteAlertsTypeDataJSON(workbook, "DT_AlertTypeForDeleteAlert", scenarioID, excelOperation));
		
			deleteAlertsRequestJsonObject.put("AlertData",generateDeleteAlertsDataJSON(workbook, "DT_SetIDForAlertDelete", scenarioID, excelOperation));
		}
		return deleteAlertsRequestJsonObject;
	}
	
	/**
	 * Generate delete alerts data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateDeleteAlertsDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject deleteAlertsRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			deleteAlertsRequestJsonObject.put("ID", json.get("ID"));
		}
		return deleteAlertsRequestJsonObject;
	}

	/**
	 * Generate delete alerts type data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateDeleteAlertsTypeDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject deleteAlertsRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			deleteAlertsRequestJsonObject.put("MarketAlertType", json.get("MarketAlertType"));
			deleteAlertsRequestJsonObject.put("MarketAlertOperationType", json.get("MarketAlertOperationType"));
		}
		return deleteAlertsRequestJsonObject;
	}
	
	/**
	 * Gets the delete alert.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the delete alert
	 * @throws Exception the exception
	 */
	public void getDeleteAlert(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Alerts_DeleteAlerts.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());

			Gson gson=new Gson();
			GetResponseForDeleteAlert getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForDeleteAlert.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			//int infoidss=Integer.parseInt(infoids) ;
			if(infoid.equalsIgnoreCase("0")) {
				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String alertID=((JsonObject) ((JsonObject) jsonObject1.get("response")).get("data")).get("alertID").toString().replace("\"", "");
				Reporter.log("<b>"+alertID+ "</b>"+"<b> Delete Successfully" +"</b>");
				Assert.assertEquals("0", infoid);
			}
			else {

				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String infomsgs = ((JsonObject) jsonObject1.get("response")).get("infoMsg").toString();

				Reporter.log("<b>Message ID From Response is ---> </b>" +"<b>"+infomsgs+"</b>");
				//Reporter.log("<b>Failed due to ---> Entered Wrong OR Blank Password</b>");
				String infoid1 = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
				Assert.assertEquals("0", infoid1,infomsgs);
			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}

	/**
	 * Gets the delete alert for wrong data.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the delete alert for wrong data
	 * @throws Exception the exception
	 */
	public void getDeleteAlertForWrongData(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Alerts_DeleteAlerts.getUrl();

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
					//Reporter.log("<b>Failed due to ---> Entered Wrong OR Blank ID</b>");
					String infoid1 = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
					
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
				
			}
		}

		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}
}
