package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForGetGroupSymbolsLive;
import com.API.Response.assoSymList;
import com.API.Response.symbolList;
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
 * The Class GetGroupSymbolsLive.
 */
public class GetGroupSymbolsLive {
	
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
			parentJsonObject.put("request",generateGetGroupSymbolsLiveRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("echo",generateGetGroupSymbolsLiveEchoJSON(workbook, "DT_GetGroupSymbolsLiveEcho", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Get Group Symbols Live</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}
	
	/**
	 * Generate get group symbols live echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetGroupSymbolsLiveEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getGroupSymbolsLiveEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getGroupSymbolsLiveEchoJsonObject.put("requestOwner", json.get("requestOwner"));
			getGroupSymbolsLiveEchoJsonObject.put("wlname", json.get("wlname"));
		}
		return getGroupSymbolsLiveEchoJsonObject;
	}
	
	/**
	 * Generate get group symbols live request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetGroupSymbolsLiveRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getGroupSymbolsLiveRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getGroupSymbolsLiveRequestJsonObject.put("data",generateGetGroupSymbolsLiveDataJSON(workbook, "DT_GetGroupSymbolsLiveData", scenarioID, excelOperation));
			getGroupSymbolsLiveRequestJsonObject.put("appID", json.get("appID"));
			getGroupSymbolsLiveRequestJsonObject.put("formFactor", json.get("formFactor"));
			getGroupSymbolsLiveRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return getGroupSymbolsLiveRequestJsonObject;
	}
	
	/**
	 * Generate get group symbols live data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetGroupSymbolsLiveDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getGroupSymbolsLiveRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getGroupSymbolsLiveRequestJsonObject.put("userID", json.get("userID"));
			getGroupSymbolsLiveRequestJsonObject.put("wlname", json.get("wlname"));
		}
		return getGroupSymbolsLiveRequestJsonObject;
	}

	/**
	 * Gets the group symbols live.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the group symbols live
	 * @throws Exception the exception
	 */
	public void getGroupSymbolsLive(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.WatchList_GetSymbolforLiveUser.getUrl();

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
						Assert.assertEquals("0", infoid);
					}
				}
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
	
	/**
	 * Invalid get group symbols live.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void invalidGetGroupSymbolsLive(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.WatchList_GetSymbolforLiveUser.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);			
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			if(!infoid.equalsIgnoreCase("0")) {
				Gson gson=new Gson();
				GetResponseForGetGroupSymbolsLive getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForGetGroupSymbolsLive.class);
				//List<symbolList> symbolList=getAllApplicationResponse.getResponse().getData().getSymbolList();
				String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
				Assert.assertEquals("ENW0017", infoid);
				}
			
			else {
				String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
				Assert.assertEquals("ENW0017", infoid);
			}
		}

		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}
}
