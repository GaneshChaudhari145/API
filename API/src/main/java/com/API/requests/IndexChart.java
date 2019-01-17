package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForGetGroupSymbolsLive;
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
 * The Class IndexChart.
 */
public class IndexChart {
	
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
			parentJsonObject.put("request",generateIndexChartRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("echo",generateIndexChartEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Index Chart</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();
		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}
	
	/**
	 * Generate index chart echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateIndexChartEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject indexChartEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			indexChartEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return indexChartEchoJsonObject;
	}
	
	/**
	 * Generate index chart request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateIndexChartRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject indexChartRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			indexChartRequestJsonObject.put("data",generateIndexChartDataJSON(workbook, "DT_IndexChartData", scenarioID, excelOperation));
			indexChartRequestJsonObject.put("appID", json.get("appID"));
			indexChartRequestJsonObject.put("formFactor", json.get("formFactor"));
			indexChartRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return indexChartRequestJsonObject;
	}
	
	/**
	 * Generate index chart data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateIndexChartDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject indexChartRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			indexChartRequestJsonObject.put("index", json.get("index"));
		}
		return indexChartRequestJsonObject;
	}

	/**
	 * Index chart.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void indexChart(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Chart_IndexChart.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);	
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");	
			if(infoid.equalsIgnoreCase("0")) {				
				Assert.assertEquals("0", infoid);
				Reporter.log("<b>Response Received Successfully</b>");
			}
			else {
				String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
				Assert.assertEquals("ENW0042", infoid, "No Data Available");
			}	
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}
	
	/**
	 * Gets the index chart negative.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the index chart negative
	 * @throws Exception the exception
	 */
	public void getIndexChartNegative(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Chart_IndexChart.getUrl();
			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);
			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			System.out.println(res.asString());
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String result =((JsonObject) jsonObject.get("response")).get("infoMsg").toString();
			String expectedResult=result.replace("\"", " ").trim();	
			String infoid=  ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			if(!infoid.equalsIgnoreCase("0")) {
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+expectedResult+"</b>");
				if(infoid.equals("ENW0042")) {
					Assert.assertEquals("No Data Available",expectedResult);
				}	
				else if(infoid.equals("ENW0100")) {
					Assert.assertEquals("SYSTEM ERROR - (12010)",expectedResult);
				}
			}
			else {
				String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString();
				//String infomsgs=infomsg.toString();
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
				Assert.assertEquals("ENW0100",infoid);	
			}
		}
		catch(Exception e) {
			throw e;
		}
	}
}