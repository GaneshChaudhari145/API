package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForTopGainers;
import com.API.Response.gainersList;
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
 * The Class TopGainers.
 */
public class TopGainers {
	
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
			parentJsonObject.put("request",generateTopGainersRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("echo",generateTopGainersEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Top Gainers</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}
	
	/**
	 * Generate top gainers echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateTopGainersEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject GetOverviewEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			GetOverviewEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return GetOverviewEchoJsonObject;
	}
	
	/**
	 * Generate top gainers request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateTopGainersRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject topGainersRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			topGainersRequestJsonObject.put("data",generateTopGainersDataJSON(workbook, "DT_TopGainersData", scenarioID, excelOperation));
			topGainersRequestJsonObject.put("appID", json.get("appID"));
			topGainersRequestJsonObject.put("formFactor", json.get("formFactor"));
			topGainersRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return topGainersRequestJsonObject;
	}
	
	/**
	 * Generate top gainers data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateTopGainersDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject topGainersRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap)
		{
			topGainersRequestJsonObject.put("exchange", json.get("exchange"));
		}
		return topGainersRequestJsonObject;
	}

	/**
	 * Gets the top gainers.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the top gainers
	 * @throws Exception the exception
	 */
	public void getTopGainers(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.MarketMovers_TopGainers.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			Gson gson=new Gson();
			GetResponseForTopGainers getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForTopGainers.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);			
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			if(infoid.equalsIgnoreCase("0")) {
				List<gainersList> gainersList=getAllApplicationResponse.getResponse().getData().getGainersList();
				String asOnDate= getAllApplicationResponse.getResponse().getData().getAsOnDate();
				Reporter.log("<b>As On Date From Response is --></b>"+"<b>"+asOnDate+"</b>");
				Reporter.log("<br>");
				for (gainersList gainersL:gainersList) 
				{
					if(gainersL!=null)
					{
						Reporter.log("<b>DTrading Sym From Response is  --></b>"+"<b>"+gainersL.getdTradingSym()+"</b>");
						Reporter.log("<b>Description From Response is --></b>"+"<b>"+gainersL.getDescription()+"</b>");
						Reporter.log("<b>Trading Symbol From Response is --></b>"+"<b>"+gainersL.getTradingSymbol()+"</b>");
						Reporter.log("<b>Symbol From Response is --></b>"+"<b>"+gainersL.getSymbol()+"</b>");
						Reporter.log("-------------------------------------------------------------------");
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
}
