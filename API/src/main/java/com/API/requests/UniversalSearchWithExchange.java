package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForAlertsBook;
import com.API.Response.GetResponseForUniversalSearch;
import com.API.Response.symbolList;
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
 * The Class UniversalSearchWithExchange.
 */
public class UniversalSearchWithExchange {
	
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
			parentJsonObject.put("request",generateUniversalSearchWithExchangeRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("echo",generateUniversalSearchWithExchangeEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Universal Search With Exchange</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {			
			throw e;
		}
	}
	
	/**
	 * Generate universal search with exchange echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateUniversalSearchWithExchangeEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject universalSearchWithExchangeEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			universalSearchWithExchangeEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return universalSearchWithExchangeEchoJsonObject;
	}
	
	/**
	 * Generate universal search with exchange request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateUniversalSearchWithExchangeRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject universalSearchWithExchangeRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			universalSearchWithExchangeRequestJsonObject.put("data",generateUniversalSearchWithExchangeDataJSON(workbook, "DT_UniversalSearchWithExgData", scenarioID, excelOperation));
			universalSearchWithExchangeRequestJsonObject.put("appID", json.get("appID"));
			universalSearchWithExchangeRequestJsonObject.put("formFactor", json.get("formFactor"));
			universalSearchWithExchangeRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return universalSearchWithExchangeRequestJsonObject;
	}
	
	/**
	 * Generate universal search with exchange data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateUniversalSearchWithExchangeDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject universalSearchWithExchangeRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			universalSearchWithExchangeRequestJsonObject.put("searchString",json.get("searchString"));
			universalSearchWithExchangeRequestJsonObject.put("source",json.get("source"));
			universalSearchWithExchangeRequestJsonObject.put("exchangeSegment",json.get("exchangeSegment"));
		}
		return universalSearchWithExchangeRequestJsonObject;
	}

	/**
	 * Gets the universal search with exchange.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the universal search with exchange
	 * @throws Exception the exception
	 */
	public void getUniversalSearchWithExchange(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.SymbolSearch_UniversalSearchWithExchange.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());

			Gson gson=new Gson();
			GetResponseForUniversalSearch getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForUniversalSearch.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);	
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			if(infoid.equalsIgnoreCase("0")) {
				JsonArray result = (((JsonArray) ((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("symbolList")).getAsJsonArray());
				List<symbolList> symbolList=getAllApplicationResponse.getResponse().getData().getSymbolList();
				if(!symbolList.isEmpty()) {
					for (symbolList symbolL:symbolList)
					{				
						Reporter.log("<b>ISIN Code From Response is --></b>"+"<b>"+symbolL.getISINCode()+"</b>");
						Reporter.log("<b>Base Key From Response is --></b>"+"<b>"+symbolL.getBaseKey()+"</b>");
						Reporter.log("--------------------------------------------------------------");
					}
				}
				else
				{
					Reporter.log("<b>Symbol List is Empty</b>");
				}
				Assert.assertEquals("0", infoid);	
				//}
			}
			else {

				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String infomsgs = ((JsonObject) jsonObject1.get("response")).get("infoMsg").toString();

				Reporter.log("<b>Message ID From Response is ---> </b>" +"<b>"+infomsgs+"</b>");
				Reporter.log("<b>Failed due to ---> Entered Wrong OR Blank Data</b>");
				String infoid1 = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");

				//if(infoid1.equals("ENW0017")) {
				Assert.assertEquals("0" ,infoid1);

			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}
	
	/**
	 * Invalid universal search with exchange.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void invalidUniversalSearchWithExchange(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.SymbolSearch_UniversalSearchWithExchange.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());

			Gson gson=new Gson();
			GetResponseForUniversalSearch getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForUniversalSearch.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);	
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			if(infoid.equalsIgnoreCase("0")) {
				JsonArray result = (((JsonArray) ((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("symbolList")).getAsJsonArray());
				List<symbolList> symbolList=getAllApplicationResponse.getResponse().getData().getSymbolList();
				if(symbolList.isEmpty()) {
					Reporter.log("<b>Symbol List is Empty</b>");
					Assert.assertEquals("0", infoid);	
				}
			}
			else {

				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String infomsgs = ((JsonObject) jsonObject1.get("response")).get("infoMsg").toString();

				Reporter.log("<b>Message ID From Response is ---> </b>" +"<b>"+infomsgs+"</b>");
				Reporter.log("<b>Failed due to ---> Entered Wrong OR Blank Data</b>");
				String infoid1 = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");

				//if(infoid1.equals("ENW0017")) {
				Assert.assertEquals("0" ,infoid1);

			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}
}
