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
 * The Class GetDefaultGroups.
 */
public class GetDefaultGroups {
	
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
			parentJsonObject.put("request",generateGetDefaultGroupsRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("echo",generateGetDefaultGroupsEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Default groups</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}
	
	/**
	 * Generate get default groups echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetDefaultGroupsEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getDefaultGroupsEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getDefaultGroupsEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return getDefaultGroupsEchoJsonObject;
	}
	
	/**
	 * Generate get default groups request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetDefaultGroupsRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getDefaultGroupsRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getDefaultGroupsRequestJsonObject.put("data",generateGetDefaultGroupsDataJSON(workbook, "DT_TopLosersData", scenarioID, excelOperation));
			getDefaultGroupsRequestJsonObject.put("appID", json.get("appID"));
			getDefaultGroupsRequestJsonObject.put("formFactor", json.get("formFactor"));
			getDefaultGroupsRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return getDefaultGroupsRequestJsonObject;
	}
	
	/**
	 * Generate get default groups data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetDefaultGroupsDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getDefaultGroupsRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			//getDefaultGroupsRequestJsonObject.put("exchange", json.get("exchange"));
		}
		return getDefaultGroupsRequestJsonObject;
	}

	/**
	 * Gets the default groups.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the default groups
	 * @throws Exception the exception
	 */
	public void getDefaultGroups(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.WatchList_GetDefaultGroups.getUrl();

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
				Reporter.log("<b>Message List is Empty</b>");
				Assert.assertEquals("ENW0042", infoid);
			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}
}
