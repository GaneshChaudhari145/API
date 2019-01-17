package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
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

// TODO: Auto-generated Javadoc
/**
 * The Class AddGroup.
 */
public class AddGroup {
	
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
			Reporter.log("<b><font size=4 color=green>Add Group</font></b>");
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
			getGroupforLiveUserRequestJsonObject.put("data",generateGetSymbolforDefaultGroupDataJSON(workbook, "DT_AddGroupData", scenarioID, excelOperation));
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
		if(!scenarioID.equals("Scenario054")) {
			autoWLNameGenerator(workbook, sheetName, scenarioID, excelOperation);
			List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
			List<LinkedHashMap<String, String>>jsonMap1=excelOperation.getScenarioID(workbook, sheetName, scenarioID);
			for(LinkedHashMap<String, String> json:jsonMap) {
				for(LinkedHashMap<String, String> json1:jsonMap1) {
					getGroupforLiveUserRequestJsonObject.put("userID", json.get("userID"));
					if(scenarioID.equals(json1.get("ScenarioId"))) {
						getGroupforLiveUserRequestJsonObject.put("wlname", json.get("wlname"));
					}
				}
			}
		}
		else {
			List<LinkedHashMap<String, String>>jsonMaps=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
			List<LinkedHashMap<String, String>>jsonMaps1=excelOperation.getScenarioID(workbook, sheetName, scenarioID);
			for(LinkedHashMap<String, String> jsons:jsonMaps) {
				for(LinkedHashMap<String, String> jsons1:jsonMaps1) {
					getGroupforLiveUserRequestJsonObject.put("userID", jsons.get("userID"));
					if(scenarioID.equals(jsons1.get("ScenarioId"))) {
						getGroupforLiveUserRequestJsonObject.put("wlname", jsons.get("wlname"));
					}
				}
			}
		}
		return getGroupforLiveUserRequestJsonObject;
	}

	/**
	 * Adds the group.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void addGroup(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.WatchList_AddGroup.getUrl();

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
				Reporter.log("<b>Group Added Successfully</b>");
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
	 * Adds the duplicate group.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void addDuplicateGroup(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.WatchList_AddGroup.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);			
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");	
			if(!infoid.equalsIgnoreCase("0")) {
				String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
				Assert.assertEquals("ENW0017", infoid);
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
	 * Auto WL name generator.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void autoWLNameGenerator(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) throws FileNotFoundException, IOException {

		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 3; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();
		//System.out.println(output);

		XSSFSheet sheet=workbook.getSheet(sheetName);
		Row headerRow=sheet.getRow(0);
		LinkedHashMap<String, Integer> headerPosition=new LinkedHashMap<>();
		for(int columnNo=0;columnNo<headerRow.getPhysicalNumberOfCells();columnNo++) {
			headerPosition.put(headerRow.getCell(columnNo).getStringCellValue(), columnNo);
		}
		for(int j=0;j<sheet.getPhysicalNumberOfRows()-1;j++){
			XSSFRow	row = sheet.getRow(j);
			XSSFCell cell = row.getCell(headerPosition.get("ScenarioId"),Row.CREATE_NULL_AS_BLANK);
			//if(cell.getStringCellValue().equalsIgnoreCase(scenarioID)) {
			//excelOperation.setDataIntoExcel(workbook,"DT_AddGroupData",j,"wlname",output);

			excelOperation.updateDataInExcel(workbook, "DT_AddGroupData",scenarioID,"wlname", output);

			if((!scenarioID.equals("Scenario056")) && (!scenarioID.equals("Scenario057"))) {
				excelOperation.updateDataInExcel(workbook, "DT_AddSymbolData",scenarioID,"wlname", output);
			}
			if(scenarioID.equals("Scenario032")) {
				excelOperation.updateDataInExcel(workbook, "DT_GetGroupSymbolsLiveEcho",scenarioID,"wlname", output);
			}
			if(scenarioID.equals("Scenario030"))  {
				excelOperation.updateDataInExcel(workbook, "DT_DeleteGroupData",scenarioID,"wlname", output);
			}	
			if(scenarioID.equals("Scenario033"))  {
				excelOperation.updateDataInExcel(workbook, "DT_SetDefaultData",scenarioID,"wlname", output);
			}
			if(scenarioID.equals("Scenario032"))  {
				excelOperation.updateDataInExcel(workbook, "DT_GetGroupSymbolsLiveData",scenarioID,"wlname", output);
			}
			if(scenarioID.equals("Scenario031"))  {
				excelOperation.updateDataInExcel(workbook, "DT_DeleteSymbolData",scenarioID,"wlname", output);
			}
		}
	}
}
