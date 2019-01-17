package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class GetSymbolforOpenUser.
 */
public class GetSymbolforOpenUser {
	
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
			parentJsonObject.put("request",generateGetSymbolforOpenUserRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("echo",generateGetSymbolforOpenUserEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Get Symbol for Open User</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}
	
	/**
	 * Generate get symbolfor open user echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetSymbolforOpenUserEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getSymbolforOpenUserEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getSymbolforOpenUserEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return getSymbolforOpenUserEchoJsonObject;
	}
	
	/**
	 * Generate get symbolfor open user request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetSymbolforOpenUserRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getSymbolforOpenUserRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getSymbolforOpenUserRequestJsonObject.put("data",generateGetSymbolforDefaultGroupDataJSON(workbook, "DT_GetSymbolforOpenUserData", scenarioID, excelOperation));
			getSymbolforOpenUserRequestJsonObject.put("appID", json.get("appID"));
			getSymbolforOpenUserRequestJsonObject.put("formFactor", json.get("formFactor"));
			getSymbolforOpenUserRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return getSymbolforOpenUserRequestJsonObject;
	}

	/**
	 * Generate get symbolfor default group data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the JSON object
	 */
	public JSONObject generateGetSymbolforDefaultGroupDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		//JSONArray array=new JSONArray();
		JSONObject getSymbolforOpenUserRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(int i=0;i<jsonMap.size();i++) {
			if(!scenarioID.equalsIgnoreCase("Scenario104")) {
			List<Object> symbolList=new ArrayList<>();
			symbolList.add(jsonMap.get(i).get("symbolList"));
		
				getSymbolforOpenUserRequestJsonObject.put("symbolList",symbolList.toArray() );
				//array.add(getSymbolforOpenUserRequestJsonObject);
			}
			else
			{
				List<String> symbolList=new ArrayList<>();
				getSymbolforOpenUserRequestJsonObject.put("symbolList",symbolList);
			}
		}
		return getSymbolforOpenUserRequestJsonObject;
	}
	
	/**
	 * Gets the symbolfor open user.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the symbolfor open user
	 * @throws Exception the exception
	 */
	public void getSymbolforOpenUser(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.WatchList_GetSymbolforOpenUser.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);
			System.out.println(json);
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

					//Reporter.log("<b>Asso SymList From Response is --></b>"+"<b>"+symbolL.getISINCode()+"</b>");
					Reporter.log("<b>Asso SymList From Response is --></b>"+"<b>"+symbolL.getAssetType()+"</b>");
					List<assoSymList> assoSymList=symbolL.getAssoSymList();
					for (assoSymList assoL:assoSymList) {
						Reporter.log("<b>Asso SymList From Response is --></b>"+"<b>"+assoL.getKey()+"</b>");
						Reporter.log("<b>Asso SymList From Response is --></b>"+"<b>"+assoL.getValue()+"</b>");
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

	/**
	 * Invalid get symbolfor open user.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void invalidGetSymbolforOpenUser(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.WatchList_GetSymbolforOpenUser.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);
			//System.out.println(json);
			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);			
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			if(infoid.equalsIgnoreCase("0")) {
				//Gson gson=new Gson();
				//GetResponseForGetGroupSymbolsLive getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForGetGroupSymbolsLive.class);
				//String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
				//Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
				Assert.assertEquals("0", infoid);
				Reporter.log("<b>Response is Blank</b>");
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
