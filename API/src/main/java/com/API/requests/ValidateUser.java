package com.API.requests;

import static com.jayway.restassured.RestAssured.*;

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
import com.API.Response.GetResponseForValidateUser;
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
 * The Class ValidateUser.
 */
public class ValidateUser  
{
	
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
			LinkedHashMap<String, String>jsonMap=excelOperation.getScenarioData(workbook, "DT_ValidateUserParent", scenarioID).get(0);
			parentJsonObject.put("request",generateValidateUserRequestJSON(workbook, "DT_ValidateUserRequest", scenarioID, excelOperation));
			parentJsonObject.put("echo",generateValidateUserEchoJSON(workbook, "DT_ValidateUserEcho", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Validate User</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) 
		{
			throw e;
		}
	}

	/**
	 * Generate validate user echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateValidateUserEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject validateUserEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			validateUserEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return validateUserEchoJsonObject;
	}

	/**
	 * Generate validate user request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateValidateUserRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject validateUserRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			validateUserRequestJsonObject.put("data",generateValidateUserDataJSON(workbook, "DT_ValidateUserData", scenarioID, excelOperation));
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
			validateUserDataJsonObject.put("userID",json.get("userID"));
			validateUserDataJsonObject.put("memberID", json.get("memberID"));
		}
		return validateUserDataJsonObject;
	}

	/**
	 * Gets the validate user.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the validate user
	 * @throws Exception the exception
	 */
	public void getValidateUser(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Login_VALIDATEUSER.getUrl();
			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			
			Gson gson=new Gson();
			GetResponseForValidateUser getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForValidateUser.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String result = ((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("imageIndex").toString();
			Reporter.log("<b>Image Index From Response is--></b>"+"<b>"+result+"</b>");
			//excelOperation.setDataIntoExcel(workbook, "DT_ValidatePasswordData", 1, "imageIndex",result);
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			if(infoid.equalsIgnoreCase("0")) {
				XSSFSheet sheet=workbook.getSheet("DT_ValidatePasswordData");
				Row headerRow=sheet.getRow(0);
				LinkedHashMap<String, Integer> headerPosition=new LinkedHashMap<>();
				for(int columnNo=0;columnNo<headerRow.getPhysicalNumberOfCells();columnNo++) {
					headerPosition.put(headerRow.getCell(columnNo).getStringCellValue(), columnNo);
				}
				for(int j=0;j<sheet.getPhysicalNumberOfRows();j++){
					XSSFRow	row = sheet.getRow(j);
					XSSFCell cell = row.getCell(headerPosition.get("ScenarioId"),Row.CREATE_NULL_AS_BLANK);
					if(cell.getStringCellValue().equalsIgnoreCase(scenarioID)) {
						excelOperation.updateValidatePasswordDataInExcel(workbook, "DT_ValidatePasswordData",scenarioID,"imageIndex", result);
					}
				}
				Assert.assertEquals("0",infoid);
			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}
}
