package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.QAList;
import com.API.ServiceEnum.ServiceEndpoint;
import com.API.Utils.ExcelOperation;
import com.API.Utils.FrameworkServices;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import net.minidev.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class PlaceEquitySIPOrder.
 */
public class PlaceEquitySIPOrder {
	
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
			parentJsonObject.put("echo",generateGetforFutureCalculatorDefaultGroupEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>PlaceEquitySIPOrder</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}
	
	/**
	 * Generate getfor future calculator default group echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetforFutureCalculatorDefaultGroupEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
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
			getGroupforLiveUserRequestJsonObject.put("data",generateGetSymbolforDefaultGroupDataJSON(workbook, "DT_PlaceEquitySIPOrder", scenarioID, excelOperation));
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
			List<LinkedHashMap<String, String>>jsonMaps=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
			List<LinkedHashMap<String, String>>jsonMaps1=excelOperation.getScenarioID(workbook, sheetName, scenarioID);
			for(LinkedHashMap<String, String> jsons:jsonMaps) {
				for(LinkedHashMap<String, String> jsons1:jsonMaps1) {
					getGroupforLiveUserRequestJsonObject.put("transPass", jsons.get("transPass"));
					getGroupforLiveUserRequestJsonObject.put("userID", jsons.get("userID"));
					getGroupforLiveUserRequestJsonObject.put("accountID", jsons.get("accountID"));
					getGroupforLiveUserRequestJsonObject.put("source", jsons.get("source"));
					getGroupforLiveUserRequestJsonObject.put("operationType", jsons.get("operationType"));
					getGroupforLiveUserRequestJsonObject.put("sipRequestType", jsons.get("sipRequestType"));
					getGroupforLiveUserRequestJsonObject.put("sipInstructionID", jsons.get("sipInstructionID"));
					getGroupforLiveUserRequestJsonObject.put("exchangeSegment", jsons.get("exchangeSegment"));
					getGroupforLiveUserRequestJsonObject.put("exchangeInstrumentID", jsons.get("exchangeInstrumentID"));
					getGroupforLiveUserRequestJsonObject.put("sipType", jsons.get("sipType"));
					getGroupforLiveUserRequestJsonObject.put("sipValue", jsons.get("sipValue"));
					getGroupforLiveUserRequestJsonObject.put("sipFrequency", jsons.get("sipFrequency"));
					getGroupforLiveUserRequestJsonObject.put("startDate", jsons.get("startDate"));
					getGroupforLiveUserRequestJsonObject.put("endDate", jsons.get("endDate"));
					getGroupforLiveUserRequestJsonObject.put("dueDate", jsons.get("dueDate"));
					getGroupforLiveUserRequestJsonObject.put("clientID", jsons.get("clientID"));
					getGroupforLiveUserRequestJsonObject.put("clientName", jsons.get("clientName"));
					getGroupforLiveUserRequestJsonObject.put("orderQuantity", jsons.get("orderQuantity"));
					getGroupforLiveUserRequestJsonObject.put("orderAmount", jsons.get("orderAmount"));
					getGroupforLiveUserRequestJsonObject.put("participantCode", jsons.get("participantCode"));
					getGroupforLiveUserRequestJsonObject.put("sipStatus", jsons.get("sipStatus"));
					getGroupforLiveUserRequestJsonObject.put("orderStatus", jsons.get("orderStatus"));
					getGroupforLiveUserRequestJsonObject.put("rejCanReason", jsons.get("rejCanReason"));
					getGroupforLiveUserRequestJsonObject.put("orderID", jsons.get("orderID"));
					getGroupforLiveUserRequestJsonObject.put("exchangeOrderID", jsons.get("exchangeOrderID"));
					getGroupforLiveUserRequestJsonObject.put("lastUpdateDateTime", jsons.get("lastUpdateDateTime"));
					getGroupforLiveUserRequestJsonObject.put("nowOrderStatus", jsons.get("nowOrderStatus"));
					getGroupforLiveUserRequestJsonObject.put("isVisibleInAdminReport", jsons.get("isVisibleInAdminReport"));
					getGroupforLiveUserRequestJsonObject.put("createdBy", jsons.get("createdBy"));
					getGroupforLiveUserRequestJsonObject.put("createdOn", jsons.get("createdOn"));
					getGroupforLiveUserRequestJsonObject.put("updatedBy", jsons.get("updatedBy"));
					getGroupforLiveUserRequestJsonObject.put("updatedOn", jsons.get("updatedOn"));
			
				
			}
		}
		return getGroupforLiveUserRequestJsonObject;
	}
	
	/**
	 * Place equity SIP order.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void placeEquitySIPOrder(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Place_EquitySIPOrder.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
		
			//String sipInstructionID=((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("sipInstructionID").toString().replace("\"", "");
			 
			if(infoid.equalsIgnoreCase("0"))
			{
				    String sipInstructionID=((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("sipInstructionID").toString().replace("\"", "");
				    System.out.println("MrSingh SipInstructionID Value:-"+sipInstructionID);
				    
				    XSSFSheet sheet=workbook.getSheet("DT_CancelSIPOrder");
					Row headerRow=sheet.getRow(0);
					LinkedHashMap<String, Integer> headerPosition=new LinkedHashMap<>();
					for(int columnNo=0;columnNo<headerRow.getPhysicalNumberOfCells();columnNo++) {
						headerPosition.put(headerRow.getCell(columnNo).getStringCellValue(), columnNo);
					}
					for(int j=0;j<sheet.getPhysicalNumberOfRows();j++)
					{
						XSSFRow	row = sheet.getRow(j);
						XSSFCell cell = row.getCell(headerPosition.get("ScenarioId"),Row.CREATE_NULL_AS_BLANK);

						if(cell.getStringCellValue().equalsIgnoreCase(scenarioID)) {
							excelOperation.updateDataInExcelForInstructionID(workbook, "DT_CancelSIPOrder",scenarioID,"sipInstructionID", sipInstructionID);
							System.out.println("Updated Data SipInstructionID To DT_CancelSIPOrder ");
						}
					}
				    Assert.assertEquals("0", infoid);
				    Reporter.log("<b>Place order SuccessFully</b>");
			}
			else {
				String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
				Assert.assertEquals("ENW0039", infoid, "Incorrect Parameters");
			}	
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}
}
