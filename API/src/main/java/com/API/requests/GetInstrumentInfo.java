package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
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
import net.minidev.json.parser.JSONParser;

// TODO: Auto-generated Javadoc
/**
 * The Class GetInstrumentInfo.
 */
public class GetInstrumentInfo {
	
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
			parentJsonObject.put("request",generateGetInstrumentInfoRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("echo",generateGetInstrumentInfoEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Get Instrument Info</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();
		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}
	
	/**
	 * Generate get instrument info echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetInstrumentInfoEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getInstrumentInfoEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getInstrumentInfoEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return getInstrumentInfoEchoJsonObject;
	}
	
	/**
	 * Generate get instrument info request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetInstrumentInfoRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getInstrumentInfoRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getInstrumentInfoRequestJsonObject.put("data",generateGetExchangeMessageDataJSON(workbook, "DT_GetInstrumentInfoData", scenarioID, excelOperation));
			getInstrumentInfoRequestJsonObject.put("appID", json.get("appID"));
			getInstrumentInfoRequestJsonObject.put("formFactor", json.get("formFactor"));
			getInstrumentInfoRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return getInstrumentInfoRequestJsonObject;
	}
	
	/**
	 * Generate get exchange message data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetExchangeMessageDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getInstrumentInfoRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getInstrumentInfoRequestJsonObject.put("symbol", json.get("symbol"));
		}
		return getInstrumentInfoRequestJsonObject;
	}

	/**
	 * Gets the instrument info.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the instrument info
	 * @throws Exception the exception
	 */
	public void getInstrumentInfo(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Info_GetInstrumentInfo.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());

			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String  abc=((JsonObject) jsonObject.get("response")).getAsJsonArray("data").toString().replace("[", " ");
			String ma=abc.replace("]", "");
			
			//String ab=abc.get("PriceBand").toString();
			JsonObject jsonob=new Gson().fromJson(ma.toString(), JsonObject.class);
			
			//JSONParser parser = new JSONParser();
			//JSONObject jsonob = (JSONObject) parser.parse(ma);
			//String Mn=abc.get("PriceBand").toString();
			
			
			String	LowPrice=((JsonObject) jsonob.get("PriceBand")).get("Low").toString();
			String	HighPrice=((JsonObject) jsonob.get("PriceBand")).get("High").toString();
			
			//String	HighPrice=((JsonObject) ((JsonObject) ((JsonObject) (jsonObject.get("response"))).get("data")).get("PriceBand")).get("High").toString();
			
			double High=Double.parseDouble(HighPrice);
			High=Math.round(High);
			double Low=Double.parseDouble(LowPrice)+Double.parseDouble(LowPrice)*0.01;
			Low=Math.round(Low);
			String LTP;
			String TriggerPrice;
			if (Low > High){
				 LTP=String.valueOf(High);
				 double TriggerPrice1=Double.parseDouble(LTP)+Double.parseDouble(LTP)*0.03;
				 TriggerPrice1=Math.round(TriggerPrice1);
				 TriggerPrice=String.valueOf(TriggerPrice1);
				 //excelOperation.updateDataLTP(workbook, "DT_PlaceOrderData",scenarioID,"LTP", LTP);
			}
			else 
			{
				LTP=String.valueOf(Low);
				double TriggerPrice1=Double.parseDouble(LTP)+Double.parseDouble(LTP)*0.03;
				 TriggerPrice1=Math.round(TriggerPrice1);
				 TriggerPrice=String.valueOf(TriggerPrice1);
				//excelOperation.updateDataLTP(workbook, "DT_PlaceOrderData",scenarioID,"LTP", LTP);
			}
			
			XSSFSheet sheet1=workbook.getSheet("DT_PlaceOrderData");
			XSSFSheet sheet2=workbook.getSheet("DT_ModifyOrderData");
			XSSFSheet sheet3=workbook.getSheet("DT_PlaceCoverTradeData");
			XSSFSheet sheet4=workbook.getSheet("DT_ModifyAMOTradeData");
			
			/*for (int i=1;i<=sheet1.getPhysicalNumberOfRows()-1;i++) {
				String ScenarioName=sheet1.getRow(i).getCell(0).toString();
				if (ScenarioName.equalsIgnoreCase(scenarioID)) {
					excelOperation.updateDataLTP(workbook, "DT_PlaceOrderData",scenarioID,"limitPrice", LTP);
					//excelOperation.updateDataLTP(workbook, "DT_ModifyOrderData",scenarioID,"limitPrice", LTP);
					excelOperation.updateDataForTriggerPrice(workbook, "DT_PlaceOrderData",scenarioID,"triggerPrice", TriggerPrice);
					//excelOperation.updateDataForTriggerPrice(workbook, "DT_ModifyOrderData",scenarioID,"triggerPrice", TriggerPrice);
					break;
				}
				
			}
			for (int i=1;i<=sheet2.getPhysicalNumberOfRows()-1;i++) {
				String ScenarioName=sheet2.getRow(i).getCell(0).toString();
				if (ScenarioName.equalsIgnoreCase(scenarioID)) {
					//excelOperation.updateDataLTP(workbook, "DT_PlaceOrderData",scenarioID,"limitPrice", LTP);
					excelOperation.updateDataLTP(workbook, "DT_ModifyOrderData",scenarioID,"limitPrice", LTP);
					//excelOperation.updateDataForTriggerPrice(workbook, "DT_PlaceOrderData",scenarioID,"triggerPrice", TriggerPrice);
					excelOperation.updateDataForTriggerPrice(workbook, "DT_ModifyOrderData",scenarioID,"triggerPrice", TriggerPrice);
					break;
				}
				
			}
			for (int i=1;i<=sheet3.getPhysicalNumberOfRows()-1;i++) {
				String ScenarioName=sheet3.getRow(i).getCell(0).toString();
				if (ScenarioName.equalsIgnoreCase(scenarioID)) {
					excelOperation.updateTriggerPriceForCoverOrder(workbook, "DT_PlaceCoverTradeData",scenarioID,"triggerPrice", TriggerPrice);
					break;
				}
				
			}
			for (int i=1;i<=sheet4.getPhysicalNumberOfRows()-1;i++) {
				String ScenarioName=sheet4.getRow(i).getCell(0).toString();
				if (ScenarioName.equalsIgnoreCase(scenarioID)) {
					excelOperation.updateTriggerPriceForCoverOrder(workbook, "DT_ModifyAMOTradeData",scenarioID,"triggerPrice", TriggerPrice);
					break;
				}
				
			}*/
			
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			if(infoid.equalsIgnoreCase("0")) {
				Assert.assertEquals("0", infoid);
				Reporter.log("<b>Response Received Successfully</b>");

			}
			else {
				Reporter.log("<b>Failed due to ---> Invalid Data</b>");
				Assert.assertEquals("0", infoid);
			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}
	
	/**
	 * Gets the invalid instrument info.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the invalid instrument info
	 * @throws Exception the exception
	 */
	public void getInvalidInstrumentInfo(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Info_GetInstrumentInfo.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());

			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			String infomsgs = ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
			if(!infoid.equalsIgnoreCase("0")) {
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsgs+"</b>");
				Assert.assertEquals("ENW0042", infoid);
				Reporter.log("<b>Failed due to ---> Entered Wrong OR Blank Instrument Info Data</b>");
				
			}
			else {
				Reporter.log("<b>Failed due to ---> Invalid Data</b>");
				Assert.assertEquals("ENW0042", infoid);
			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}
}
