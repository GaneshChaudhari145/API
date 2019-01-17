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
 * The Class ModifyOrder.
 */
public class ModifyOrder {
	
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
			parentJsonObject.put("request",generateModifyOrderRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("echo",generateModifyOrderEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Modify Order</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}
	
	/**
	 * Generate modify order echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateModifyOrderEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject modifyOrderEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			modifyOrderEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return modifyOrderEchoJsonObject;
	}
	
	/**
	 * Generate modify order request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateModifyOrderRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject modifyOrderRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			modifyOrderRequestJsonObject.put("data",generateModifyOrderDataJSON(workbook, "DT_ModifyOrderData", scenarioID, excelOperation));
			modifyOrderRequestJsonObject.put("appID", json.get("appID"));
			modifyOrderRequestJsonObject.put("formFactor", json.get("formFactor"));
			modifyOrderRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return modifyOrderRequestJsonObject;
	}
	
	/**
	 * Generate modify order data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateModifyOrderDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject modifyOrderRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			modifyOrderRequestJsonObject.put("transPass", json.get("transPass"));
			modifyOrderRequestJsonObject.put("accountID", json.get("accountID"));
			modifyOrderRequestJsonObject.put("userID", json.get("userID"));
			modifyOrderRequestJsonObject.put("exchange", json.get("exchange"));
			modifyOrderRequestJsonObject.put("tradingSymbol", json.get("tradingSymbol"));
			modifyOrderRequestJsonObject.put("action", json.get("action"));
			modifyOrderRequestJsonObject.put("expiry", json.get("expiry"));
			modifyOrderRequestJsonObject.put("filledQuantity", json.get("filledQuantity"));
			modifyOrderRequestJsonObject.put("quantity", json.get("quantity"));
			modifyOrderRequestJsonObject.put("orderType", json.get("orderType"));
			modifyOrderRequestJsonObject.put("orderID", json.get("orderID"));
			modifyOrderRequestJsonObject.put("productCode", json.get("productCode"));
			modifyOrderRequestJsonObject.put("disclosedQuantity", json.get("disclosedQuantity"));
			modifyOrderRequestJsonObject.put("limitPrice", json.get("limitPrice"));
			if(!json.get("orderType").equalsIgnoreCase("LIMIT")&&!json.get("orderType").equalsIgnoreCase("MARKET")) {
				if(json.get("action").equalsIgnoreCase("BUY")) {
					modifyOrderRequestJsonObject.put("limitPrice", json.get("triggerPrice"));
					modifyOrderRequestJsonObject.put("triggerPrice", json.get("triggerPrice"));
				}
				else {
					double LimitPriceForSell=Double.parseDouble(json.get("limitPrice"));
					double LimitPriceForSellOrder=LimitPriceForSell-(LimitPriceForSell*0.03);
					double TriggerPriceForSell=LimitPriceForSell-(LimitPriceForSell*0.02);
					String LimitPrice=String.valueOf(LimitPriceForSellOrder);
					String triggerPrice=String.valueOf(TriggerPriceForSell);
					modifyOrderRequestJsonObject.put("limitPrice", LimitPrice);
					modifyOrderRequestJsonObject.put("triggerPrice", triggerPrice);
				}
			}
			modifyOrderRequestJsonObject.put("symbol", json.get("symbol"));
		}
		return modifyOrderRequestJsonObject;
	}

	/**
	 * Modify order.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void modifyOrder(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Trade_ModifyOrder.getUrl();

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
				if(scenarioID.equals("Scenario039"))  {
					excelOperation.updateValidatePasswordDataInExcel(workbook, "DT_ModifyOrderData",scenarioID,"orderID", orderID);
					Assert.assertEquals("0", infoid);
				}
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
	 * Gets the invalid modify order.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the invalid modify order
	 * @throws Exception the exception
	 */
	public void getInvalidModifyOrder(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Trade_ModifyOrder.getUrl();

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
					Assert.assertEquals("Not Able to Modify Order", expectedResult);
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

