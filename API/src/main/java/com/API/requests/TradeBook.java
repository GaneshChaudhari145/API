package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForOrderHistory;
import com.API.Response.tradeList;
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
 * The Class TradeBook.
 */
public class TradeBook 
{
	
	/** The tradeid. */
	String Orderid="",tradeid="";
	
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
			parentJsonObject.put("request",generateTradeBookRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("echo",generateTradeBookEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Trade Book</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}
	
	/**
	 * Generate trade book echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateTradeBookEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getTradeBookEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getTradeBookEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return getTradeBookEchoJsonObject;
	}
	
	/**
	 * Generate trade book request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateTradeBookRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject tradeBookRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			tradeBookRequestJsonObject.put("data",generateTradeBookDataJSON(workbook, "DT_TradeModuleData", scenarioID, excelOperation));
			tradeBookRequestJsonObject.put("appID", json.get("appID"));
			tradeBookRequestJsonObject.put("formFactor", json.get("formFactor"));
			tradeBookRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return tradeBookRequestJsonObject;
	}
	
	/**
	 * Generate trade book data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateTradeBookDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject tradeBookRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			tradeBookRequestJsonObject.put("accountID", json.get("accountID"));
		}
		return tradeBookRequestJsonObject;
	}

	/**
	 * Gets the trade books.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the trade books
	 * @throws Exception the exception
	 */
	public void getTradeBooks(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Orders_TradeBook.getUrl();

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
			if(infoid.equalsIgnoreCase("0")) 
			{
				JsonArray result = (((JsonArray) ((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("tradeList")).getAsJsonArray());
				
				List<tradeList> tradeList=getAllApplicationResponse.getResponse().getData().getTradeList();
				if(!tradeList.isEmpty()) {
					for (tradeList tradeL:tradeList)
					{	
						Orderid=tradeL.getOrderID();
						tradeid=tradeL.getTradeId();
						Reporter.log("<b>dTradingSym From Response is --></b>"+"<b>"+tradeL.getdTradingSym()+"</b>");
						Reporter.log("<b>Order ID From Response is --></b>"+"<b>"+tradeL.getOrderID()+"</b>");
						Reporter.log("<b>Trade ID From Response is --></b>"+"<b>"+tradeL.getTradeId()+"</b>");
						Reporter.log("---------------------------------------------------");
					}
					
					//System.out.println("Mr Singh Orderid DATA  ----"+Orderid);
					//System.out.println("Mr Singh Tradeid DATA  ----"+tradeid);
					
					
					//Update Sheet DT_PositionConersion
					//System.out.println("Updating Sheet DT_PositionConersion-----");
					excelOperation.updateDataInExcelFororderID(workbook, "DT_PositionConersion",scenarioID,"orderID", Orderid);
					excelOperation.updateDataInExcelFortradeID(workbook, "DT_PositionConersion",scenarioID,"tradeID", tradeid);
					System.out.println("Updated Data Orderid, "+Orderid +" and Tradeid " +tradeid +" To DT_PositionConersion ");
						
				}
				else
				{
					Reporter.log("<b>Trade List is Empty</b>");
				}
				Assert.assertEquals("0", infoid);	
			}

			else {
				String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
				Assert.assertEquals("0", infoid, "Incorrect Parameters");
			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}


	/**
	 * Gets the invalid trade book.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the invalid trade book
	 * @throws Exception the exception
	 */
	public void getInvalidTradeBook(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Orders_TradeBook.getUrl();

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
			if(infoid.equalsIgnoreCase("0")) {
				JsonArray result = (((JsonArray) ((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("tradeList")).getAsJsonArray());
				List<tradeList> tradeList=getAllApplicationResponse.getResponse().getData().getTradeList();
				if(tradeList.isEmpty()) {
					Reporter.log("<b>Order List is Empty</b>");
					Assert.assertEquals("0", infoid);
				}

				else {
					String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
					Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
					Assert.assertEquals("0", infoid, "Incorrect Parameters");
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}
}
