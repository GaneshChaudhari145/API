package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForOrderHistory;
import com.API.Response.orderHistoryList;
import com.API.ServiceEnum.ServiceEndpoint;
import com.API.Utils.ExcelOperation;
import com.API.Utils.FrameworkServices;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import net.minidev.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderHistory.
 */
public class OrderHistory {
	
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
			parentJsonObject.put("request",generateOrderBookRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("echo",generateOrderBookEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Order History</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}
	
	/**
	 * Generate order book echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateOrderBookEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject orderBookEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			orderBookEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return orderBookEchoJsonObject;
	}
	
	/**
	 * Generate order book request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateOrderBookRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject orderBookRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			orderBookRequestJsonObject.put("data",generateOrderBookDataJSON(workbook, "DT_OrderHistoryData", scenarioID, excelOperation));
			orderBookRequestJsonObject.put("appID", json.get("appID"));
			orderBookRequestJsonObject.put("formFactor", json.get("formFactor"));
			orderBookRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return orderBookRequestJsonObject;
	}
	
	/**
	 * Generate order book data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateOrderBookDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject orderBookRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			orderBookRequestJsonObject.put("orderID", json.get("orderID"));
			orderBookRequestJsonObject.put("symbol", json.get("symbol"));
			
		}
		return orderBookRequestJsonObject;
	}

	/**
	 * Gets the order history.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the order history
	 * @throws Exception the exception
	 */
	public void getOrderHistory(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		String ExpectedStatus = null;
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Orders_OrderHistory.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
 			Gson gson=new Gson();
			JSONObject orderBookRequestJsonObject=new JSONObject();
			List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, "DT_OrderHistoryData", scenarioID);
			for(LinkedHashMap<String, String> jsonData:jsonMap) {
				//orderBookRequestJsonObject.put("orderID", jsonData.get("orderID"));
				//orderBookRequestJsonObject.put("symbol", json.get("symbol"));
				ExpectedStatus=jsonData.get("ExpectedStatus");
				}
			Thread.sleep(1000);
			GetResponseForOrderHistory getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForOrderHistory.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);	
			String Status =  (((JsonObject) ((JsonArray) ((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("orderHistoryList")).getAsJsonArray().get(1)).get("status").toString().replace("\"", ""));
			String RejectionReason=(((JsonObject) ((JsonArray) ((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("orderHistoryList")).getAsJsonArray().get(1)).get("rejectReason").toString());
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			
			if(!ExpectedStatus.equalsIgnoreCase("Yes")) {
				if(infoid.equalsIgnoreCase("0")) {
					if(!Status.equalsIgnoreCase("Rejected")) {
					//JsonArray result = (((JsonArray) ((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("orderHistoryList")).getAsJsonArray());
						List<orderHistoryList> orderHistoryList=getAllApplicationResponse.getResponse().getData().getOrderHistoryList();
						if(!orderHistoryList.isEmpty()) {
							for (orderHistoryList orderHistoryL:orderHistoryList)
							{				
								Reporter.log("<b>dTradingSym From Response is --></b>"+"<b>"+orderHistoryL.getdTradingSym()+"</b>");
								Reporter.log("<b>Order ID From Response is --></b>"+"<b>"+orderHistoryL.getOrderID()+"</b>");
								Reporter.log("---------------------------------------------------");
							}
						}
						else
						{
							Reporter.log("<b>Order History List is Empty</b>");
						}
						Assert.assertEquals("0", infoid);	
					}
					else {
						Reporter.log("<b>Status From Response is--></b>"+"<b>"+Status+"</b>");
						Reporter.log("<b>Rejection Reason is--></b>"+"<b>"+RejectionReason+"</b>");
						Assert.fail(RejectionReason);
					}
				}
				else {
						String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
						Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
						Assert.assertEquals("0", infoid, "Incorrect Parameters");
					}
			
			}	
			else
			{
				Reporter.log("<b>Rejection Reason is--></b>"+"<b>"+RejectionReason+"</b>");
				Assert.assertEquals( Status, "Rejected","Expected Error Match  Successfully");
			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}

	/**
	 * Invalid order history.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void invalidOrderHistory(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Orders_OrderHistory.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			Gson gson=new Gson();
			GetResponseForOrderHistory getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForOrderHistory.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);	

			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");	
			if(!infoid.equalsIgnoreCase("0")) {
				String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
				Assert.assertEquals("ENW0039", infoid);
			}

			else {
				String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
				Assert.assertEquals("ENW0039", infoid, "Incorrect Parameters");
			}	
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}
}
