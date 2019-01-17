package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForUpdateUser2FAAnswers;
import com.API.Response.imageList;
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
 * The Class GetAllQuestions.
 */
public class GetAllQuestions {
	
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
			parentJsonObject.put("echo",generateGetAllQuestionsEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("request",generateGetAllQuestionsRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Get All Questions</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}

	/**
	 * Generate get all questions echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetAllQuestionsEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getAllQuestionsEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getAllQuestionsEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return getAllQuestionsEchoJsonObject;
	}

	/**
	 * Generate get all questions request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetAllQuestionsRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getAllQuestionsRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getAllQuestionsRequestJsonObject.put("data",generateGetAllQuestionsDataJSON(workbook, "DT_LogoutData", scenarioID, excelOperation));
			getAllQuestionsRequestJsonObject.put("appID", json.get("appID"));
			getAllQuestionsRequestJsonObject.put("formFactor", json.get("formFactor"));
			getAllQuestionsRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return getAllQuestionsRequestJsonObject;
	}

	/**
	 * Generate get all questions data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetAllQuestionsDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getAllQuestionsDataJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getAllQuestionsDataJsonObject.put("userID",json.get("userID"));
			getAllQuestionsDataJsonObject.put("memberID",json.get("memberID"));
		}
		return getAllQuestionsDataJsonObject;
	}

	/**
	 * Gets the all questions.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the all questions
	 * @throws Exception the exception
	 */
	public void getAllQuestions(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Login_GETALLQUESTIONS.getUrl();
			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);
			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);

			Reporter.log("<b>Response is--></b>"+res.asString());
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);

			//System.out.println(result);

			String infoid = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			if(infoid.equalsIgnoreCase("0")) {	
				Assert.assertEquals("0", infoid);
				Reporter.log("<b>Response Received Successfully</b>");
			}
			else {
				String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
				Assert.assertEquals("0", infoid, "Incorrect Parameters");
			}	
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Gets the all questions negative.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the all questions negative
	 * @throws Exception the exception
	 */
	public void getAllQuestionsNegative(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Login_GETALLQUESTIONS.getUrl();
			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);
			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			System.out.println(res.asString());
			Gson gson=new Gson();
			GetResponseForUpdateUser2FAAnswers getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForUpdateUser2FAAnswers.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			
			String infoid=  ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");

			if(infoid.equalsIgnoreCase("0")) {	
				Assert.assertEquals("0", infoid);
				Reporter.log("<b>Response Received Successfully</b>");
			}
			else {
				String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
				Assert.assertEquals("ENW0099", infoid, "Incorrect Parameters");
			}	
		}
		catch(Exception e) {
			throw e;
		}
	}
}
