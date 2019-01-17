package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForAlertsBook;
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
 * The Class GetBalances.
 */
public class GetBalances {
	
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
			parentJsonObject.put("echo",generateGetBalancesEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("request",generateGetBalancesRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Get Balances</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}

	/**
	 * Generate get balances echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetBalancesEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getBalancesEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getBalancesEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return getBalancesEchoJsonObject;
	}

	/**
	 * Generate get balances request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetBalancesRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getBalancesRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getBalancesRequestJsonObject.put("data",generateGetBalancesDataJSON(workbook, "DT_GetBalancesData", scenarioID, excelOperation));
			getBalancesRequestJsonObject.put("appID", json.get("appID"));
			getBalancesRequestJsonObject.put("formFactor", json.get("formFactor"));
			getBalancesRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return getBalancesRequestJsonObject;
	}

	/**
	 * Generate get balances data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetBalancesDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getBalancesJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getBalancesJsonObject.put("userID",json.get("userID"));
			getBalancesJsonObject.put("accountID",json.get("accountID"));
		}
		return getBalancesJsonObject;
	}

	/**
	 * Gets the balances.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the balances
	 * @throws Exception the exception
	 */
	public void getBalances(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {

			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Balance_GETBALANCES.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);
			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			System.out.println(res.asString());

		
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");

			//int infoidss=Integer.parseInt(infoids) ;
			if(infoid.equalsIgnoreCase("0")) {
				Assert.assertEquals("0", infoid);
				Reporter.log("<b>Response Received Successfully</b>");
			}
			else {

				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String infomsgs = ((JsonObject) jsonObject1.get("response")).get("infoMsg").toString().replace("\"", "");

				Reporter.log("<b>Message ID From Response is ---> </b>" +"<b>"+infomsgs+"</b>");

				String infoid1 = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
				Assert.assertEquals("0", infoid);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Gets the balances negative.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the balances negative
	 */
	public void getBalancesNegative(XSSFWorkbook workbook, String sheetName, ExcelOperation excelOperation,String scenarioID) {
		try {

			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Balance_GETBALANCES.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);
			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			System.out.println(res.asString());

			Gson gson=new Gson();
			GetResponseForAlertsBook getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForAlertsBook.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");

			//int infoidss=Integer.parseInt(infoids) ;
			if(!infoid.equalsIgnoreCase("0")) {
				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String infomsgs = ((JsonObject) jsonObject1.get("response")).get("infoMsg").toString().replace("\"", "");

				Reporter.log("<b>Message ID From Response is ---> </b>" +"<b>"+infomsgs+"</b>");

				String infoid1 = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
				Assert.assertEquals("Invalid Access Token", infomsgs);
			}
			else {
				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String infomsgs = ((JsonObject) jsonObject1.get("response")).get("infoMsg").toString().replace("\"", "");

				Reporter.log("<b>Message ID From Response is ---> </b>" +"<b>"+infomsgs+"</b>");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

