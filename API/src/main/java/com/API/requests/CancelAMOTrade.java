package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForPlaceOrder;
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
 * The Class CancelAMOTrade.
 */
public class CancelAMOTrade {
	
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
			parentJsonObject.put("request",generateCancelAMOTradeRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("echo",generateCancelAMOTradeEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Cancel AMO Trade</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();
		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}
	
	/**
	 * Generate cancel AMO trade echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateCancelAMOTradeEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject cancelAMOTradeEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			cancelAMOTradeEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return cancelAMOTradeEchoJsonObject;
	}
	
	/**
	 * Generate cancel AMO trade request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateCancelAMOTradeRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject cancelAMOTradeRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			cancelAMOTradeRequestJsonObject.put("data",generateCancelOrderDataJSON(workbook, "DT_CancelOrderData", scenarioID, excelOperation));
			cancelAMOTradeRequestJsonObject.put("appID", json.get("appID"));
			cancelAMOTradeRequestJsonObject.put("formFactor", json.get("formFactor"));
			cancelAMOTradeRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return cancelAMOTradeRequestJsonObject;
	}
	
	/**
	 * Generate cancel order data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateCancelOrderDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject cancelAMOTradeRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			cancelAMOTradeRequestJsonObject.put("transPass", json.get("transPass"));
			cancelAMOTradeRequestJsonObject.put("accountID", json.get("accountID"));
			cancelAMOTradeRequestJsonObject.put("userID", json.get("userID"));
			cancelAMOTradeRequestJsonObject.put("orderID", json.get("orderID"));
			cancelAMOTradeRequestJsonObject.put("symbol", json.get("symbol"));
		}
		return cancelAMOTradeRequestJsonObject;
	}

	/**
	 * Cancel AMO trade.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void cancelAMOTrade(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Trade_CancelAMOTrade.getUrl();

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
	 * Gets the invalid cancel AMO trade.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the invalid cancel AMO trade
	 * @throws Exception the exception
	 */
	public void getInvalidCancelAMOTrade(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Trade_CancelAMOTrade.getUrl();

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
				Assert.assertEquals("ENW0017", infoId);
			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}

	}
}


