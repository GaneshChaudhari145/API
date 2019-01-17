package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForAlertsBook;
import com.API.Response.GetResponseForTickerBar;
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
 * The Class GetTickerBar.
 */
public class GetTickerBar {
	
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
			parentJsonObject.put("request",generateGetTickerBarRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("echo",generateGetTickerBarEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Get Ticker Bar</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {			
			throw e;
		}
	}
	
	/**
	 * Generate get ticker bar echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetTickerBarEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getTickerBarEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getTickerBarEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return getTickerBarEchoJsonObject;
	}
	
	/**
	 * Generate get ticker bar request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetTickerBarRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getTickerBarRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getTickerBarRequestJsonObject.put("data",generateGetTickerBarDataJSON(workbook, "DT_GetTickerBarData", scenarioID, excelOperation));
			getTickerBarRequestJsonObject.put("appID", json.get("appID"));
			getTickerBarRequestJsonObject.put("formFactor", json.get("formFactor"));
			getTickerBarRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return getTickerBarRequestJsonObject;
	}
	
	/**
	 * Generate get ticker bar data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetTickerBarDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getTickerBarRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getTickerBarRequestJsonObject.put("userID",json.get("userID"));
			getTickerBarRequestJsonObject.put("accountID",json.get("accountID"));
			getTickerBarRequestJsonObject.put("memberID",json.get("memberID"));
			getTickerBarRequestJsonObject.put("exchangeSegment",json.get("exchangeSegment"));
		}
		return getTickerBarRequestJsonObject;
	}

	/**
	 * Gets the ticker bar.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the ticker bar
	 * @throws Exception the exception
	 */
	public void getTickerBar(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Dashboard_GetTickerBar.getUrl();

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
				Assert.assertEquals("0", infoid);	
				Reporter.log("<b>Response Received Successfully</b>");
				
			}
			else {

				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String infomsgs = ((JsonObject) jsonObject1.get("response")).get("infoMsg").toString();

				Reporter.log("<b>Message ID From Response is ---> </b>" +"<b>"+infomsgs+"</b>");
				Reporter.log("<b>Failed due to ---> Entered Wrong OR Blank Data</b>");
				String infoid1 = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
				Assert.assertEquals("0" ,infoid1);

			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}

	/**
	 * Gets the ticker bar negative.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the ticker bar negative
	 * @throws Exception the exception
	 */
	public void getTickerBarNegative(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Dashboard_GetTickerBar.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());

			Gson gson=new Gson();
			GetResponseForTickerBar getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForTickerBar.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");

			JsonObject result = (JsonObject) ((((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("indexData")));

			String getKeyValue=getAllApplicationResponse.getResponse().getData().getIndexData().getKey();
			String getActionValue=getAllApplicationResponse.getResponse().getData().getIndexData().getValue().getAction();
			String getMessageValue=getAllApplicationResponse.getResponse().getData().getIndexData().getValue().getMessage();	

			if(getKeyValue.equals("ENW0042")) {
				Reporter.log("<b>Message ID From Response is ---> </b>" +"<b>"+getActionValue+"</b>");
				Reporter.log("<b>Message ID From Response is ---> </b>" +"<b>"+getMessageValue+"</b>");
				Assert.assertEquals("No Data Available", getMessageValue);
			}	

			else {
				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String infomsgs = ((JsonObject) jsonObject1.get("response")).get("infoMsg").toString();
				Reporter.log("<b>Message ID From Response is ---> </b>" +"<b>"+infomsgs+"</b>");
				Assert.assertEquals("No Data Available", getMessageValue);
			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}

}
