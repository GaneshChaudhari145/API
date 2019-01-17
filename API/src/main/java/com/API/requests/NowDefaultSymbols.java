package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForAlertsBook;
import com.API.Response.GetResponseForGetGroupSymbolsLive;
import com.API.Response.alertList;
import com.API.Response.assoSymList;
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
 * The Class NowDefaultSymbols.
 */
public class NowDefaultSymbols {
	
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
			parentJsonObject.put("request",generateGetSymbolforDefaultGroupRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("echo",generateGetSymbolforDefaultGroupEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Now Default Symbols</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}
	
	/**
	 * Generate get symbolfor default group echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetSymbolforDefaultGroupEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getSymbolforDefaultGroupEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getSymbolforDefaultGroupEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return getSymbolforDefaultGroupEchoJsonObject;
	}
	
	/**
	 * Generate get symbolfor default group request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetSymbolforDefaultGroupRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getSymbolforDefaultGroupRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getSymbolforDefaultGroupRequestJsonObject.put("data",generateGetSymbolforDefaultGroupDataJSON(workbook, "DT_NowDefaultSymbolsData", scenarioID, excelOperation));
			getSymbolforDefaultGroupRequestJsonObject.put("appID", json.get("appID"));
			getSymbolforDefaultGroupRequestJsonObject.put("formFactor", json.get("formFactor"));
			getSymbolforDefaultGroupRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return getSymbolforDefaultGroupRequestJsonObject;
	}
	
	/**
	 * Generate get symbolfor default group data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetSymbolforDefaultGroupDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getSymbolforDefaultGroupRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
		}
		return getSymbolforDefaultGroupRequestJsonObject;
	}

	/**
	 * Gets the now default symbols.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the now default symbols
	 * @throws Exception the exception
	 */
	public void getNowDefaultSymbols(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.WatchList_NowDefaultSymbols.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);			
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			if(infoid.equalsIgnoreCase("0")) {
				Gson gson=new Gson();
				GetResponseForGetGroupSymbolsLive getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForGetGroupSymbolsLive.class);
				List<symbolList> symbolList=getAllApplicationResponse.getResponse().getData().getSymbolList();
				for (symbolList symbolL:symbolList) {

					Reporter.log("<b>ISIN Code From Response is --></b>"+"<b>"+symbolL.getISINCode()+"</b>");
					Reporter.log("<b>Asset Type From Response is --></b>"+"<b>"+symbolL.getAssetType()+"</b>");
					List<assoSymList> assoSymList=symbolL.getAssoSymList();
					for (assoSymList assoL:assoSymList) {
						Reporter.log("<b>Asso SymList Key From Response is --></b>"+"<b>"+assoL.getKey()+"</b>");
						Reporter.log("<b>Asso SymList Value From Response is --></b>"+"<b>"+assoL.getValue()+"</b>");
						Reporter.log("------------------------------------------------------------");
					}
				}
				Assert.assertEquals("0", infoid);
			}
			else {
				String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
				Assert.assertEquals("0", infoid);
			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}
}
