package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForAlertsBook;
import com.API.Response.GetResponseForOrderHistory;
import com.API.Response.GetResponseForTickerBar;
import com.API.Response.positionList;
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
 * The Class GetDayPositions.
 */
public class GetDayPositions {
	
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
			parentJsonObject.put("echo",generateGetSquareOffEchoJSON(workbook, "DT_PositionsEchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("request",generateGetSquareOffRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Get Day Positions</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}

	/**
	 * Generate get square off echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetSquareOffEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getSquareOffEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getSquareOffEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return getSquareOffEchoJsonObject;
	}

	/**
	 * Generate get square off request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetSquareOffRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getSquareOffRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getSquareOffRequestJsonObject.put("data",generateGetSquareOffDataJSON(workbook, "DT_GetDayPositionsData", scenarioID, excelOperation));
			getSquareOffRequestJsonObject.put("appID", json.get("appID"));
			getSquareOffRequestJsonObject.put("formFactor", json.get("formFactor"));
			getSquareOffRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return getSquareOffRequestJsonObject;
	}

	/**
	 * Generate get square off data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetSquareOffDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getSquareOffJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getSquareOffJsonObject.put("accountID",json.get("accountID"));
			getSquareOffJsonObject.put("type",json.get("type"));
		}
		return getSquareOffJsonObject;
	}
	
	/**
	 * Gets the day position.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the day position
	 * @throws Exception the exception
	 */
	public void getDayPosition(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Positions_GetDayPositions.getUrl();
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
				//JsonArray result = (((JsonArray) ((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("positionList")).getAsJsonArray());
				List<positionList> positionList=getAllApplicationResponse.getResponse().getData().getPositionList();
				if(!positionList.isEmpty()) {
					for (positionList positionL:positionList)
					{				
						Reporter.log("<b>dTradingSym From Response is --></b>"+"<b>"+positionL.getdTradingSym()+"</b>");
						Reporter.log("<b>Symbol From Response is --></b>"+"<b>"+positionL.getSymbol()+"</b>");
						Reporter.log("---------------------------------------------------");
					}
				}
				else
				{
					Reporter.log("<b>Position List is Empty</b>");
				}
				Assert.assertEquals("0", infoid);	
			}
			else {
				String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
				Assert.assertEquals("0", infoid, "Incorrect Parameters");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Invalid get day positions.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void invalidGetDayPositions(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Positions_GetDayPositions.getUrl();
			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);
			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());

			/*Gson gson=new Gson();
			GetResponseForTickerBar getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForTickerBar.class);*/
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");

			if(infoid.equals("0")) {
				Reporter.log("<b>Response is Blank</b>");
				Assert.assertEquals("0", infoid);
			}	

			else {
				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String infomsgs = ((JsonObject) jsonObject1.get("response")).get("infoMsg").toString();
				Reporter.log("<b>Message ID From Response is ---> </b>" +"<b>"+infomsgs+"</b>");
				Assert.assertEquals("0", infoid);
			}
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
