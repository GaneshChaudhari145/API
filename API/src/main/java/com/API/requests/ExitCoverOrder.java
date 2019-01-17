package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForPlaceOrder;
import com.API.ServiceEnum.ServiceEndpoint;
import com.API.Utils.ExcelOperation;
import com.API.Utils.FrameworkServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import net.minidev.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class ExitCoverOrder.
 */
public class ExitCoverOrder {
	
	/**
	 * Generate parent json.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the string
	 * @throws Throwable the throwable
	 */
	public String generateParentJson(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) throws Throwable {
		try {
			JSONObject parentJsonObject=new JSONObject();
			LinkedHashMap<String, String>jsonMap=excelOperation.getScenarioData(workbook, "DT_ParentEntity", scenarioID).get(0);
			parentJsonObject.put("request",generatePlaceTradeRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("echo",generatePlaceTradeEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Exit Cover Trade</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}
	
	/**
	 * Generate place trade echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generatePlaceTradeEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject placeTradeEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			placeTradeEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return placeTradeEchoJsonObject;
	}
	
	/**
	 * Generate place trade request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 * @throws Exception the exception
	 */
	private Object generatePlaceTradeRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) throws Exception {
		JSONObject placeTradeRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			placeTradeRequestJsonObject.put("data",generatePlaceTradeDataJSON(workbook, "DT_ExitCoverTradeData", scenarioID, excelOperation));
			placeTradeRequestJsonObject.put("appID", json.get("appID"));
			placeTradeRequestJsonObject.put("formFactor", json.get("formFactor"));
			placeTradeRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return placeTradeRequestJsonObject;
	}

	/**
	 * Generate place trade data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 * @throws Exception the exception
	 */
	private Object generatePlaceTradeDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) throws Exception {
		JSONObject placeTradeRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			placeTradeRequestJsonObject.put("UserID", json.get("UserID"));
			placeTradeRequestJsonObject.put("AccountID", json.get("AccountID"));	
			placeTradeRequestJsonObject.put("OrderID", json.get("OrderID"));
			placeTradeRequestJsonObject.put("Source", json.get("Source"));
			placeTradeRequestJsonObject.put("transPass", json.get("transPass"));
			placeTradeRequestJsonObject.put("ExchangeSegment", json.get("ExchangeSegment"));
			placeTradeRequestJsonObject.put("ExchangeInstrumentID", json.get("ExchangeInstrumentID"));
		}
		return placeTradeRequestJsonObject;
	}

	/**
	 * Gets the exit cover trade.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the exit cover trade
	 * @throws Throwable the throwable
	 */
	public void getExitCoverTrade(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Throwable {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Trade_ExitCoverTrade.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);
			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			Gson gson =new Gson();
			GetResponseForPlaceOrder getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForPlaceOrder.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String infoid=((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");

			if(infoid.equalsIgnoreCase("0")) {
				String orderID=getAllApplicationResponse.getResponse().getData().getOrderID();
				Reporter.log("<b>OrderID From Response is--></b>"+"<b>"+orderID+"</b>");
				Assert.assertEquals("0", infoid);
			}
			else
			{
				String infoMsg=((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infoMsg+"</b>");
				Assert.assertEquals("0", infoid);
			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}

	/**
	 * Invalid exit cover trade.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Throwable the throwable
	 */
	public void invalidExitCoverTrade(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Throwable {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Trade_ExitCoverTrade.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);
			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String result =((JsonObject) jsonObject.get("response")).get("infoMsg").toString();
			String expectedResult=result.replace("\"", "");
			String infoId = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			if(!infoId.equalsIgnoreCase("0"))
			{
				Reporter.log("<b>Message From Response is ---> </b>" +"<b>"+expectedResult+"</b>");
				if(infoId.equals("ENW0017")) {
					Assert.assertEquals("Transaction Password Is Wrong", expectedResult);
				}					
			}
			else {
				String infoMsg=((JsonObject) jsonObject.get("response")).get("infoMsg").toString();
				Reporter.log("<b>Message From Response is ---> </b>" +"<b>"+infoMsg+"</b>");
				Assert.assertEquals("ENW0039", infoId);
			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}

	}
}
