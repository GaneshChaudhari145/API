package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForGetSquareOff;
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
 * The Class GetSquareOff.
 */
public class GetSquareOff {
	
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
			Reporter.log("<b><font size=4 color=green>Get Square Off</font></b>");
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
			getSquareOffRequestJsonObject.put("data",generateGetSquareOffDataJSON(workbook, "DT_GetSquareOffData", scenarioID, excelOperation));
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
			getSquareOffJsonObject.put("userID",json.get("userID"));
			getSquareOffJsonObject.put("accountID",json.get("accountID"));
			getSquareOffJsonObject.put("exchangeSegment",json.get("exchangeSegment"));
			getSquareOffJsonObject.put("instrumentID",json.get("instrumentID"));
			getSquareOffJsonObject.put("rmsProduct",json.get("rmsProduct"));
			getSquareOffJsonObject.put("positionSquareOffQuantityType",json.get("positionSquareOffQuantityType"));
			getSquareOffJsonObject.put("squareOffQtyValue",json.get("squareOffQtyValue"));
		}
		return getSquareOffJsonObject;
	}
	
	/**
	 * Gets the square off.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the square off
	 * @throws Exception the exception
	 */
	public void getSquareOff(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Positions_GetSquareOff.getUrl();
			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);
			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());

			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);

			String infoid = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			Gson gson=new Gson();
			GetResponseForGetSquareOff getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForGetSquareOff.class);
			if(infoid.equalsIgnoreCase("0")) {
				String isOperationSuccess=getAllApplicationResponse.getResponse().getData().getIsOperationSuccess();
				String squareOffQtyValue=getAllApplicationResponse.getResponse().getData().getSquareOffQtyValue();
				String userID=getAllApplicationResponse.getResponse().getData().getUserID();
				Reporter.log("<b>UserID--></b>"+"<b>"+userID+"</b>");
				Reporter.log("<b>SquareOffQtyValue--></b>"+"<b>"+squareOffQtyValue+"</b>");
				Reporter.log("<b>IsOperationSuccess--></b>"+"<b>"+isOperationSuccess+"</b>");
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
	 * Invalid get square off.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void invalidGetSquareOff(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Positions_GetSquareOff.getUrl();
			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);
			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());

			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);

			String infoid = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			Gson gson=new Gson();
			GetResponseForGetSquareOff getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForGetSquareOff.class);
			if(infoid.equalsIgnoreCase("0")) {
				
				String key=getAllApplicationResponse.getResponse().getData().getKey();
				String message=getAllApplicationResponse.getResponse().getData().getValue().getMessage();
				
				String action=getAllApplicationResponse.getResponse().getData().getValue().getAction();
				Reporter.log("<b>Key--></b>"+"<b>"+key+"</b>");
				Reporter.log("<b>Action--></b>"+"<b>"+action+"</b>");
				Reporter.log("<b>Message--></b>"+"<b>"+message+"</b>");
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
}
