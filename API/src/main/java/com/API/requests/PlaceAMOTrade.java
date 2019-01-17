package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
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
 * The Class PlaceAMOTrade.
 */
public class PlaceAMOTrade {
	
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
			parentJsonObject.put("request",generatePlaceAMOTradeRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("echo",generatePlaceAMOTradeEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Place AMO Trade</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}
	
	/**
	 * Generate place AMO trade echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generatePlaceAMOTradeEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject placeAMOTradeEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			placeAMOTradeEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return placeAMOTradeEchoJsonObject;
	}
	
	/**
	 * Generate place AMO trade request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generatePlaceAMOTradeRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject placeAMOTradeRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			placeAMOTradeRequestJsonObject.put("data",generatePlaceTradeDataJSON(workbook, "DT_PlaceOrderData", scenarioID, excelOperation));
			placeAMOTradeRequestJsonObject.put("appID", json.get("appID"));
			placeAMOTradeRequestJsonObject.put("formFactor", json.get("formFactor"));
			placeAMOTradeRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return placeAMOTradeRequestJsonObject;
	}
	
	/**
	 * Generate place trade data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generatePlaceTradeDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject placeAMOTradeRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			placeAMOTradeRequestJsonObject.put("transPass", json.get("transPass"));
			placeAMOTradeRequestJsonObject.put("accountID", json.get("accountID"));
			placeAMOTradeRequestJsonObject.put("userID", json.get("userID"));
			placeAMOTradeRequestJsonObject.put("exchange", json.get("exchange"));
			placeAMOTradeRequestJsonObject.put("tradingSymbol", json.get("tradingSymbol"));
			placeAMOTradeRequestJsonObject.put("action", json.get("action"));
			placeAMOTradeRequestJsonObject.put("expiry", json.get("expiry"));
			placeAMOTradeRequestJsonObject.put("quantity", json.get("quantity"));
			placeAMOTradeRequestJsonObject.put("orderType", json.get("orderType"));
			placeAMOTradeRequestJsonObject.put("productCode", json.get("productCode"));
			placeAMOTradeRequestJsonObject.put("disclosedQuantity", json.get("disclosedQuantity"));
			placeAMOTradeRequestJsonObject.put("limitPrice", json.get("limitPrice"));
			placeAMOTradeRequestJsonObject.put("symbol", json.get("symbol"));
			placeAMOTradeRequestJsonObject.put("participationCode", json.get("participationCode"));
			if(!json.get("orderType").equalsIgnoreCase("LIMIT")&&!json.get("orderType").equalsIgnoreCase("MARKET")) {
				if(json.get("action").equalsIgnoreCase("BUY")) {
					placeAMOTradeRequestJsonObject.put("limitPrice", json.get("triggerPrice"));
					placeAMOTradeRequestJsonObject.put("triggerPrice", json.get("triggerPrice"));
				}
				else {
					/*double LimitPriceForSell=Double.parseDouble(json.get("limitPrice"));
					double LimitPriceForSellOrder=LimitPriceForSell-(LimitPriceForSell*0.03);
					double TriggerPriceForSell=LimitPriceForSell-(LimitPriceForSell*0.02);
					String LimitPrice=String.valueOf(LimitPriceForSellOrder);
					String triggerPrice=String.valueOf(TriggerPriceForSell);
					placeAMOTradeRequestJsonObject.put("limitPrice", LimitPrice);*/
					placeAMOTradeRequestJsonObject.put("triggerPrice", json.get("triggerPrice"));
				}
				
			}
			placeAMOTradeRequestJsonObject.put("source", json.get("source"));
		}
		return placeAMOTradeRequestJsonObject;
	}

	/**
	 * Place AMO trade.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void placeAMOTrade(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Trade_PlaceAMOTrade.getUrl();

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
				excelOperation.updateDataForOrderID(workbook, "DT_OrderHistoryData",scenarioID,"orderID", orderID);
				XSSFSheet sheet1=workbook.getSheet("DT_ModifyAMOTradeData");
				XSSFSheet sheet2=workbook.getSheet("DT_CancelOrderData");
				for (int i=1;i<=sheet1.getPhysicalNumberOfRows()-1;i++) {
					String ScenarioName=sheet1.getRow(i).getCell(0).toString();
					if (ScenarioName.equalsIgnoreCase(scenarioID)) {
						excelOperation.updateOrderIDForModifyOrder(workbook, "DT_ModifyAMOTradeData",scenarioID,"orderID", orderID);
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
				if(scenarioID.equals("Scenario036"))  {
					excelOperation.updateValidatePasswordDataInExcel(workbook, "DT_ModifyAMOTradeData",scenarioID,"orderID", orderID);
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
	 * Gets the invalid place amo trade.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the invalid place amo trade
	 * @throws Exception the exception
	 */
	public void getInvalidPlaceAmoTrade(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Trade_PlaceAMOTrade.getUrl();

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
