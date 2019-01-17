package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForGetQuates;
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
 * The Class GetQuote.
 */
public class GetQuote {
	
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
			Reporter.log("<b><font size=4 color=green>Get Quote</font></b>");
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
		JSONObject getGroupforLiveUserEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getGroupforLiveUserEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return getGroupforLiveUserEchoJsonObject;
	}
	
	/**
	 * Generate get symbolfor default group request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private Object generateGetSymbolforDefaultGroupRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) throws FileNotFoundException, IOException {
		JSONObject getGroupforLiveUserRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getGroupforLiveUserRequestJsonObject.put("data",generateGetSymbolforDefaultGroupDataJSON(workbook, "DT_GetInstrumentInfoData", scenarioID, excelOperation));
			getGroupforLiveUserRequestJsonObject.put("appID", json.get("appID"));
			getGroupforLiveUserRequestJsonObject.put("formFactor", json.get("formFactor"));
			getGroupforLiveUserRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return getGroupforLiveUserRequestJsonObject;
	}
	
	/**
	 * Generate get symbolfor default group data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private Object generateGetSymbolforDefaultGroupDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) throws FileNotFoundException, IOException {
		JSONObject getGroupforLiveUserRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		List<LinkedHashMap<String, String>>jsonMap1=excelOperation.getScenarioID(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getGroupforLiveUserRequestJsonObject.put("symbol", json.get("symbol"));	
		}
		return getGroupforLiveUserRequestJsonObject;
	}

	/**
	 * Gets the quote data.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the quote data
	 * @throws Exception the exception
	 */
	public void getQuoteData(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Quote_GetQuotes.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());

			Gson gson=new Gson();
			GetResponseForGetQuates getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForGetQuates.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);	

			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");	
			if(infoid.equalsIgnoreCase("0")) {
				//JsonArray result = (((JsonArray) ((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("quoteValues")).getAsJsonArray());
				String assetType=getAllApplicationResponse.getResponse().getData().getQuoteValues().getAssetType();
				String iSINCode=getAllApplicationResponse.getResponse().getData().getQuoteValues().getISINCode();
				String avgPrice=getAllApplicationResponse.getResponse().getData().getQuoteValues().getAvgPrice();
				String bidPrice=getAllApplicationResponse.getResponse().getData().getQuoteValues().getBidPrice();
				String bidSize=getAllApplicationResponse.getResponse().getData().getQuoteValues().getBidSize();
				String askPrice=getAllApplicationResponse.getResponse().getData().getQuoteValues().getAskPrice();
				String askSize=getAllApplicationResponse.getResponse().getData().getQuoteValues().getAskSize();
				String dTradingSym=getAllApplicationResponse.getResponse().getData().getQuoteValues().getdTradingSym();
				String description=getAllApplicationResponse.getResponse().getData().getQuoteValues().getDescription();
				String futureAsset=getAllApplicationResponse.getResponse().getData().getQuoteValues().getFutureAsset();
				String symbol=getAllApplicationResponse.getResponse().getData().getQuoteValues().getSymbol();
				//excelOperation.setDataIntoExcel(workbook, "DT_SetIDForAlertDelete", i, "ID",alertL.getID());			
				Reporter.log("<b>ISINCode From Response is --></b>"+"<b>"+iSINCode+"</b>");
				Reporter.log("<b>AssetType From Response is --></b>"+"<b>"+assetType+"</b>");
				Reporter.log("<b>AvgPrice From Response is --></b>"+"<b>"+avgPrice+"</b>");
				Reporter.log("<b>BidPrice From Response is --></b>"+"<b>"+bidPrice+"</b>");
				Reporter.log("<b>BidSize From Response is --></b>"+"<b>"+bidSize+"</b>");
				Reporter.log("<b>AskPrice From Response is --></b>"+"<b>"+askPrice+"</b>");
				Reporter.log("<b>AskSize From Response is --></b>"+"<b>"+askSize+"</b>");
				Reporter.log("<b>ISINCode From Response is --></b>"+"<b>"+dTradingSym+"</b>");
				Reporter.log("<b>dTradingSym From Response is --></b>"+"<b>"+description+"</b>");
				Reporter.log("<b>FutureAsset From Response is --></b>"+"<b>"+futureAsset+"</b>");
				Reporter.log("<b>Symbol From Response is --></b>"+"<b>"+symbol+"</b>");

				Assert.assertEquals("0", infoid);	
			}

			else {
				String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
				Assert.assertEquals("0", infoid, "Incorrect Parameters");
			}	
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}

	/**
	 * Gets the quotes invalid.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the quotes invalid
	 * @throws Exception the exception
	 */
	public void getQuotesInvalid(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Quote_GetQuotes.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);	
			String result =((JsonObject) jsonObject.get("response")).get("infoMsg").toString();
			String expectedResult=result.replace("\"", "");
			String infoId = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			if(!infoId.equalsIgnoreCase("0"))
			{
				Reporter.log("<b>Message From Response is ---> </b>" +"<b>"+expectedResult+"</b>");
				if(infoId.equals("ENW0042")) {
					Assert.assertEquals("No Data Available", expectedResult);
					Reporter.log("<b>Response Received Successfully</b>");

				}					
			}
			else {
				String infoMsg=((JsonObject) jsonObject.get("response")).get("infoMsg").toString();
				Reporter.log("<b>Message From Response is ---> </b>" +"<b>"+infoMsg+"</b>");
				Assert.assertEquals("ENW0042", infoId);
			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}

	}
}
