package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForGetGroupSymbolsLive;
import com.API.Response.GetResponseForTopActivebyVolume;
import com.API.Response.assoSymList;
import com.API.Response.symbolList;
import com.API.Response.topActivebyVolumeList;
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
 * The Class TopActivebyVolume.
 */
public class TopActivebyVolume {
	
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
			parentJsonObject.put("request",generateTopActivebyVolumeRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("echo",generateTopActivebyVolumeEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Top Active by Volume</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}
	
	/**
	 * Generate top activeby volume echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateTopActivebyVolumeEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject topActivebyVolumeEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			topActivebyVolumeEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return topActivebyVolumeEchoJsonObject;
	}
	
	/**
	 * Generate top activeby volume request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateTopActivebyVolumeRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject topActivebyVolumeRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			topActivebyVolumeRequestJsonObject.put("data",generateTopActivebyVolumeDataJSON(workbook, "DT_TopActivebyVolumeData", scenarioID, excelOperation));
			topActivebyVolumeRequestJsonObject.put("appID", json.get("appID"));
			topActivebyVolumeRequestJsonObject.put("formFactor", json.get("formFactor"));
			topActivebyVolumeRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return topActivebyVolumeRequestJsonObject;
	}
	
	/**
	 * Generate top activeby volume data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateTopActivebyVolumeDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject topActivebyVolumeRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) 
		{
			topActivebyVolumeRequestJsonObject.put("exchange", json.get("exchange"));
		}
		return topActivebyVolumeRequestJsonObject;
	}

	/**
	 * Gets the top activeby volume.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the top activeby volume
	 * @throws Exception the exception
	 */
	public void getTopActivebyVolume(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.MarketMovers_TopActivebyVolume.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			Gson gson=new Gson();
			GetResponseForTopActivebyVolume getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForTopActivebyVolume.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);			
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			if(infoid.equalsIgnoreCase("0")) 
			{
				List<topActivebyVolumeList> topActivebyVolumeList=getAllApplicationResponse.getResponse().getData().getTopActivebyVolumeList();
				String asOnDate= getAllApplicationResponse.getResponse().getData().getAsOnDate();
				Reporter.log("<b>As On Date From Response is --></b>"+"<b>"+asOnDate+"</b>");
				Reporter.log("<br>");
				for (topActivebyVolumeList topActivebyVolumeL:topActivebyVolumeList) {					
					Reporter.log("<b>DTrading Sym From Response is --></b>"+"<b>"+topActivebyVolumeL.getdTradingSym()+"</b>");
					Reporter.log("<b>Description From Response is --></b>"+"<b>"+topActivebyVolumeL.getDescription()+"</b>");
					Reporter.log("<b>Trading Symbol From Response is --></b>"+"<b>"+topActivebyVolumeL.getTradingSymbol()+"</b>");
					Reporter.log("<b>Symbol From Response is --></b>"+"<b>"+topActivebyVolumeL.getSymbol()+"</b>");
					Reporter.log("-------------------------------------------------------------------");
					Assert.assertEquals("0", infoid);
				}
			}
			else 
			{
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
