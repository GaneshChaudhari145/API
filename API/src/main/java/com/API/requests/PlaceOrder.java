package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
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
 * The Class PlaceOrder.
 */
public class PlaceOrder {
	
	/** The Constant AES_TRANSFORMATION. */
	private static final String AES_TRANSFORMATION = "AES/ECB/PKCS5Padding";
	
	/** The Constant AES_ALGORITHM. */
	private static final String AES_ALGORITHM = "AES";

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
			Reporter.log("<b><font size=4 color=green>Place Trade</font></b>");
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
			placeTradeRequestJsonObject.put("data",generatePlaceTradeDataJSON(workbook, "DT_PlaceOrderData", scenarioID, excelOperation));
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
			
			placeTradeRequestJsonObject.put("transPass",json.get("transPass"));
			placeTradeRequestJsonObject.put("accountID", json.get("accountID"));
			placeTradeRequestJsonObject.put("userID", json.get("userID"));
			placeTradeRequestJsonObject.put("exchange", json.get("exchange"));
			placeTradeRequestJsonObject.put("tradingSymbol", json.get("tradingSymbol"));
			placeTradeRequestJsonObject.put("action", json.get("action"));
			placeTradeRequestJsonObject.put("expiry", json.get("expiry"));
			placeTradeRequestJsonObject.put("quantity", json.get("quantity"));
			placeTradeRequestJsonObject.put("orderType", json.get("orderType"));
			placeTradeRequestJsonObject.put("productCode", json.get("productCode"));
			placeTradeRequestJsonObject.put("disclosedQuantity", json.get("disclosedQuantity"));
			placeTradeRequestJsonObject.put("limitPrice", json.get("limitPrice"));
			placeTradeRequestJsonObject.put("symbol", json.get("symbol"));
			placeTradeRequestJsonObject.put("participationCode", json.get("participationCode"));
			if(!json.get("orderType").equalsIgnoreCase("LIMIT")&&!json.get("orderType").equalsIgnoreCase("MARKET")) 
			{
				if(json.get("action").equalsIgnoreCase("BUY")) {
					placeTradeRequestJsonObject.put("limitPrice", json.get("triggerPrice"));
					placeTradeRequestJsonObject.put("triggerPrice", json.get("triggerPrice"));
				}
				else {
					/*double LimitPriceForSell=Double.parseDouble(json.get("limitPrice"));
					double LimitPriceForSellOrder=LimitPriceForSell-(LimitPriceForSell*0.03);
					double TriggerPriceForSell=LimitPriceForSell-(LimitPriceForSell*0.02);
					String LimitPrice=String.valueOf(LimitPriceForSellOrder);
					String triggerPrice=String.valueOf(TriggerPriceForSell);
					placeTradeRequestJsonObject.put("limitPrice", LimitPrice);
					placeTradeRequestJsonObject.put("triggerPrice", triggerPrice);*/
					placeTradeRequestJsonObject.put("triggerPrice", json.get("triggerPrice"));
				}
				
				
			}
			placeTradeRequestJsonObject.put("source", json.get("source"));
		}
		return placeTradeRequestJsonObject;
	}

	/**
	 * Place order.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Throwable the throwable
	 */
	public void placeOrder(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Throwable {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Trade_PlaceOrder.getUrl();

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
			
			if(infoid.equalsIgnoreCase("0"))
			{
				String orderID=getAllApplicationResponse.getResponse().getData().getOrderID();
				
				excelOperation.updateDataForOrderID(workbook, "DT_OrderHistoryData",scenarioID,"orderID", orderID);
				
				XSSFSheet sheet1=workbook.getSheet("DT_ModifyOrderData");
				XSSFSheet sheet2=workbook.getSheet("DT_CancelOrderData");
				
				
				for (int i=1;i<=sheet1.getPhysicalNumberOfRows()-1;i++) {
					String ScenarioName=sheet1.getRow(i).getCell(0).toString();
					if (ScenarioName.equalsIgnoreCase(scenarioID)) {
						excelOperation.updateOrderIDForModifyOrder(workbook, "DT_ModifyOrderData",scenarioID,"orderID", orderID);
						break;
					}
					
				}
				for (int i=1;i<=sheet2.getPhysicalNumberOfRows()-1;i++) {
					String ScenarioName=sheet2.getRow(i).getCell(0).toString();
					if (ScenarioName.equalsIgnoreCase(scenarioID)) {
						excelOperation.updateOrderIDForModifyOrder(workbook, "DT_CancelOrderData",scenarioID,"orderID", orderID);
						break;
					}
					
				}
				Reporter.log("<b>OrderID From Response is--></b>"+"<b>"+orderID+"</b>");
				if(scenarioID.equals("Scenario037") || scenarioID.equals("Scenario038"))  {
					excelOperation.updateValidatePasswordDataInExcel(workbook, "DT_CancelOrderData",scenarioID,"orderID", orderID);
				}
				if(scenarioID.equals("Scenario039")){
					excelOperation.updateValidatePasswordDataInExcel(workbook, "DT_ModifyOrderData",scenarioID,"orderID", orderID);
				}
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
	 * Place order for invalid data.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Throwable the throwable
	 */
	public void placeOrderForInvalidData(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Throwable {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Trade_PlaceOrder.getUrl();

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
			
			if(!infoid.equalsIgnoreCase("0")) 
			{
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
