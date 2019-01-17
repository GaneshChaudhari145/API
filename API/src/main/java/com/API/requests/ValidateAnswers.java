package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

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

import com.API.Response.GetResponseForValidatePassword;
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

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class ValidateAnswers.
 */
public class ValidateAnswers {

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
		JSONObject parentJsonObject=new JSONObject();
		LinkedHashMap<String, String>jsonMap=excelOperation.getScenarioData(workbook, "DT_ValidateAnswersParent", scenarioID).get(0);
		parentJsonObject.put("request",generateValidateAnswersRequestJSON(workbook, "DT_ValidateAnswersRequest", scenarioID, excelOperation));
		parentJsonObject.put("echo",generateValidateAnswersEchoJSON(workbook, "DT_ValidateAnswersEcho", scenarioID, excelOperation));
		parentJsonObject.put("session", jsonMap.get("session"));
		ObjectMapper mapper=new ObjectMapper();
		Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
		String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
		Reporter.log("<b><font size=4 color=green>Validate Answers</font></b>");
		Reporter.log("<b>Request is--></b>"+indented);
		return parentJsonObject.toString();
	}

	/**
	 * Generate validate answers echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateValidateAnswersEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject validateAnswersEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			validateAnswersEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return validateAnswersEchoJsonObject;
	}

	/**
	 * Generate validate answers request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateValidateAnswersRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject validateAnswersRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			validateAnswersRequestJsonObject.put("data",generateValidateAnswersDataJSON(workbook, "DT_ValidateAnswersData", scenarioID, excelOperation));
			validateAnswersRequestJsonObject.put("appID", json.get("appID"));
			validateAnswersRequestJsonObject.put("formFactor", json.get("formFactor"));
			validateAnswersRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return validateAnswersRequestJsonObject;
	}
	
	/**
	 * Generate system info.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateSystemInfo(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject SystemInfoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			
			SystemInfoJsonObject.put("OperatingSystem", "Windows 10 Pro");
			SystemInfoJsonObject.put("UserName", "");
			SystemInfoJsonObject.put("SytemID", "192.168.10.89");
			SystemInfoJsonObject.put("ApplicationVersion", "1.0.177");
		}
		return SystemInfoJsonObject;
	}
	
	/**
	 * Generate validate answers data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateValidateAnswersDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject validateAnswerDataJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			validateAnswerDataJsonObject.put("userID",json.get("userID"));
			validateAnswerDataJsonObject.put("memberID", json.get("memberID"));
			validateAnswerDataJsonObject.put("password",json.get("password"));
			validateAnswerDataJsonObject.put("deviceType", json.get("deviceType"));
			validateAnswerDataJsonObject.put("source", json.get("Source"));
			validateAnswerDataJsonObject.put("targetInterfaceIP", json.get("TargetIP"));
			validateAnswerDataJsonObject.put("QAList", generateQIndexJSON(workbook, "DT_QAList", scenarioID, excelOperation));
			validateAnswerDataJsonObject.put("SystemInfo", generateSystemInfo(workbook, "DT_QAList", scenarioID, excelOperation));
		}
		return validateAnswerDataJsonObject;
	}
	
	/**
	 * Generate Q index JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateQIndexJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONArray array=new JSONArray();

		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(int i=0;i<jsonMap.size();i++) {
			JSONObject qIndexJsonObject=new JSONObject();
			qIndexJsonObject.put("QIndex", jsonMap.get(i).get("QIndex"));
			qIndexJsonObject.put("answer", jsonMap.get(i).get("answer"));
			array.add(qIndexJsonObject);
		}
		return array;
	}
	
	/**
	 * Gets the validate answers.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the validate answers
	 * @throws Exception the exception
	 */
	public void getValidateAnswers(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Login_VALIDATEANSWERS.getUrl();
			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);
			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			Gson gson=new Gson();
			GetResponseForValidatePassword getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForValidatePassword.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String infoid=((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			if(infoid.equalsIgnoreCase("0")){
				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String session=jsonObject.get("session").toString().replace("\"", "");
				//excelOperation.setDataIntoExcel(workbook, "DT_ParentEntity", 5, "session",session);

				XSSFSheet sheet=workbook.getSheet("DT_ParentEntity");
				Row headerRow=sheet.getRow(0);
				LinkedHashMap<String, Integer> headerPosition=new LinkedHashMap<>();
				for(int columnNo=0;columnNo<headerRow.getPhysicalNumberOfCells();columnNo++) {
					headerPosition.put(headerRow.getCell(columnNo).getStringCellValue(), columnNo);
				}
				for(int j=0;j<sheet.getPhysicalNumberOfRows();j++){
					XSSFRow	row = sheet.getRow(j);
					XSSFCell cell = row.getCell(headerPosition.get("ScenarioId"),Row.CREATE_NULL_AS_BLANK);

					if(cell.getStringCellValue().equalsIgnoreCase(scenarioID)) {
						excelOperation.updateDataInExcel(workbook, "DT_ParentEntity",scenarioID,"session", session);
					}
				}

				String accountID=((JsonObject) ((JsonObject) jsonObject1.get("response")).get("data")).get("accountID").toString().replace("\"", "");
				String displayName=((JsonObject) ((JsonObject) jsonObject1.get("response")).get("data")).get("displayName").toString().replace("\"", "");
				Reporter.log("<b>Account ID From Response is--></b>"+"<b>"+accountID+"</b>");
				Reporter.log("<b>Display Name From Response is--></b>"+"<b>"+displayName+"</b>");
				Assert.assertEquals("0",infoid);	
			}
			else {

				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String infomsgs = ((JsonObject) jsonObject1.get("response")).get("infoMsg").toString();
				Reporter.log("<b>Failed due to ---> </b>" +"<b>"+infomsgs+"</b>");

				String infoid1 = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infoid1+"</b>");
				Assert.assertEquals("0",infoid);	
			}

		}
		catch(Exception e) {
			throw e;
		}
	}

	/**
	 * Invalid answers.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void invalidAnswers(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Login_VALIDATEANSWERS.getUrl();
			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);
			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			Gson gson=new Gson();
			GetResponseForValidatePassword getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForValidatePassword.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String infoid=((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			String result =((JsonObject) jsonObject.get("response")).get("infoMsg").toString();
			String expectedResult=result.replace("\"", " ").trim();	
			//System.out.println(session);

			if(!infoid.equalsIgnoreCase("0")){
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+expectedResult+"</b>");
				if(infoid.equals("ENW0100")) {
					Assert.assertEquals("Unable to Update 2FA Answer Details.",expectedResult);
				}		
				else 
				{
					Assert.assertEquals("Invalid Answer, 2 attempts remaining.", expectedResult);
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
