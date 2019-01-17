package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

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
 * The Class GetHoldings.
 */
public class GetHoldings {
	
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
			parentJsonObject.put("request",generateGetHoldingsRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("echo",generateGetHoldingsEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Get Holdings</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();
		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}

	/**
	 * Generate get holdings echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetHoldingsEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject validateUserEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			validateUserEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return validateUserEchoJsonObject;
	}

	/**
	 * Generate get holdings request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetHoldingsRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject validateUserRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			validateUserRequestJsonObject.put("data",generateValidateUserDataJSON(workbook, "DT_GetHoldingsData", scenarioID, excelOperation));
			validateUserRequestJsonObject.put("appID", json.get("appID"));
			validateUserRequestJsonObject.put("formFactor", json.get("formFactor"));
			validateUserRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return validateUserRequestJsonObject;
	}

	/**
	 * Generate validate user data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateValidateUserDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject validateUserDataJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			validateUserDataJsonObject.put("accountID",json.get("accountID"));
			validateUserDataJsonObject.put("productCode",json.get("productCode"));
			validateUserDataJsonObject.put("memberID", json.get("memberID"));
		}
		return validateUserDataJsonObject;
	}

	/**
	 * Gets the holding.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the holding
	 * @throws Exception the exception
	 */
	public void getHolding(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Holdings_GetHoldings.getUrl();
			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String infoid=((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			if(infoid.equalsIgnoreCase("0")){
				Assert.assertEquals("0", infoid);
				Reporter.log("<b>Response Received Successfully</b>");

			}
			else {

				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String infomsgs = ((JsonObject) jsonObject1.get("response")).get("infoMsg").toString();
				Reporter.log("<b>Failed due to ---> </b>" +"<b>"+infomsgs+"</b>");

				String infoid1 = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
				//Reporter.log("<b>Message From Response is--></b>"+"<b>"+infoid1+"</b>");
				Assert.assertEquals("0", infoid1);
			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}

	/**
	 * Invalid holdings.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void invalidHoldings(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Holdings_GetHoldings.getUrl();
			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String infoid=((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			if(!infoid.equalsIgnoreCase("0")){
				String result =((JsonObject) jsonObject.get("response")).get("infoMsg").toString();
				String expectedResult=result.replace("\"", " ").trim();	
				Assert.assertEquals("No Data Available",expectedResult);
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+expectedResult+"</b>");
			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}
}
