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
 * The Class PlaceCoverOrder.
 */
public class PlaceCoverOrder {
	
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
			Reporter.log("<b><font size=4 color=green>Place Cover Order</font></b>");
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
			placeTradeRequestJsonObject.put("data",generatePlaceTradeDataJSON(workbook, "DT_PlaceCoverTradeData", scenarioID, excelOperation));
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

			placeTradeRequestJsonObject.put("UserID",json.get("UserID"));
			placeTradeRequestJsonObject.put("AccountID", json.get("AccountID"));
			placeTradeRequestJsonObject.put("ExchangeSegment", json.get("ExchangeSegment"));
			placeTradeRequestJsonObject.put("ExchangeInstrumentID", json.get("ExchangeInstrumentID"));
			placeTradeRequestJsonObject.put("OrderQuantity", json.get("OrderQuantity"));
			placeTradeRequestJsonObject.put("DisclosedQuantity", json.get("DisclosedQuantity"));
			placeTradeRequestJsonObject.put("TriggerPrice", json.get("triggerPrice"));
			placeTradeRequestJsonObject.put("OrderSide", json.get("OrderSide"));
			placeTradeRequestJsonObject.put("transPass", json.get("transpass"));
			placeTradeRequestJsonObject.put("Source", json.get("Source"));
			placeTradeRequestJsonObject.put("participationCode", json.get("participationCode"));
			placeTradeRequestJsonObject.put("TriggerPrice", json.get("triggerPrice"));
			//placeTradeRequestJsonObject.put("productCode", json.get("productCode"));
			/*if(json.get("OrderSide").equalsIgnoreCase("BUY")) {
				double LimitPriceForSell=Double.parseDouble(json.get("triggerPrice"));
				//double LimitPriceForSellOrder=LimitPriceForSell-(LimitPriceForSell*0.03);
				double TriggerPriceForSell=LimitPriceForSell-(LimitPriceForSell*0.03);
				String triggerPrice=String.valueOf(TriggerPriceForSell);
				placeTradeRequestJsonObject.put("TriggerPrice", triggerPrice);
			}*/

		}
		return placeTradeRequestJsonObject;
	}

	/**
	 * Gets the place cover trade.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the place cover trade
	 * @throws Throwable the throwable
	 */
	public void getPlaceCoverTrade(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Throwable {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Trade_PlaceCoverTrade.getUrl();

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
				String entryorderID=getAllApplicationResponse.getResponse().getData().getEntryOrderID();
				String exitorderID=getAllApplicationResponse.getResponse().getData().getExitOrderID();
				excelOperation.updateDataForOrderID(workbook, "DT_OrderHistoryData",scenarioID,"orderID", entryorderID);
				excelOperation.updateValidatePasswordDataInExcel(workbook, "DT_ExitCoverTradeData",scenarioID,"OrderID", exitorderID);
				Reporter.log("<b>Entry OrderID From Response is--></b>"+"<b>"+entryorderID+"</b>");
				Reporter.log("<b>Exit OrderID From Response is--></b>"+"<b>"+exitorderID+"</b>");
				/*XSSFSheet sheet1=workbook.getSheet("DT_ModifyOrderData");
				XSSFSheet sheet2=workbook.getSheet("DT_CancelOrderData");
				for (int i=1;i<=sheet1.getPhysicalNumberOfRows()-1;i++) {
					String ScenarioName=sheet1.getRow(i).getCell(0).toString();
					if (ScenarioName.equalsIgnoreCase(scenarioID)) {
						excelOperation.updateOrderIDForModifyOrder(workbook, "DT_ModifyOrderData",scenarioID,"orderID", entryorderID);
						break;
					}
					
				}
				for (int i=1;i<=sheet2.getPhysicalNumberOfRows()-1;i++) {
					String ScenarioName=sheet2.getRow(i).getCell(0).toString();
					if (ScenarioName.equalsIgnoreCase(scenarioID)) {
						excelOperation.updateOrderIDForModifyOrder(workbook, "DT_CancelOrderData",scenarioID,"orderID", entryorderID);
						break;
					}
					
				}*/
				if(scenarioID.equals("Scenario078"))  {
					excelOperation.updateValidatePasswordDataInExcel(workbook, "DT_ExitCoverTradeData",scenarioID,"OrderID", exitorderID);
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
	 * Gets the invalid place cover trade.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the invalid place cover trade
	 * @throws Throwable the throwable
	 */
	public void getInvalidPlaceCoverTrade(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Throwable {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Trade_PlaceCoverTrade.getUrl();

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
				Assert.assertEquals("Transaction Password Is Wrong", expectedResult);
				Assert.assertEquals("ENW0017", infoId);

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
