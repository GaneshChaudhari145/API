package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForUpdateUser2FAAnswers;
import com.API.Response.ResponseForGetUserFAQuestions;
import com.API.ServiceEnum.ServiceEndpoint;
import com.API.Utils.ExcelOperation;
import com.API.Utils.FrameworkServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class UpdateUser2FAAnswers.
 */
public class UpdateUser2FAAnswers {

	/**
	 * Update user 2 FA answers.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void updateUser2FAAnswers(XSSFWorkbook workbook,String sheetName,String scenarioID,ExcelOperation excelOperation) throws Throwable {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Login_UPDATEUSERSTOFAANSWERS.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());

			Gson gson=new Gson();
			GetResponseForUpdateUser2FAAnswers getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForUpdateUser2FAAnswers.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String infoid=((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			String infoids=infoid.toString().replace("\"", "");
			
			if(infoids.equalsIgnoreCase("0")) {	
				String result = ((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("message").toString().replace("\"", "");
				String expectedResult=result.replace("\"", " ").trim();	
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+expectedResult+"</b>");
				Assert.assertEquals("answers updated successfully!", expectedResult);
			}
			else {
				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String result1 = (((JsonObject) jsonObject1.get("response")).get("infoMsg")).toString().replace("\"", "");
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+result1+"</b>");
				Assert.assertEquals("Please enter correct Question Id's.", result1);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Update user 2 FA blank answers.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void updateUser2FABlankAnswers(XSSFWorkbook workbook,String sheetName,String scenarioID,ExcelOperation excelOperation) throws Throwable {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Login_UPDATEUSERSTOFAANSWERS.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());

			Gson gson=new Gson();
			ResponseForGetUserFAQuestions faQuestions=gson.fromJson(res.asString(), ResponseForGetUserFAQuestions.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			
			String infoid = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			
				if(!infoid.equalsIgnoreCase("0")) {
					String result = (((JsonObject) jsonObject.get("response")).get("infoMsg")).toString();
					String expectedResult=result.replace("\"", " ").trim();	
					Reporter.log("<b>Message From Response is--></b>"+"<b>"+expectedResult+"</b>");
					Assert.assertEquals("Answer Can not be Null", expectedResult);
				}
			
			else {
				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String infoMsg = (((JsonObject) jsonObject1.get("response")).get("infoMsg")).toString().replace("\"", "");
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infoMsg+"</b>");
				Assert.assertEquals("answers updated successfully!",infoMsg);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Generate parent json.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the string
	 * @throws Throwable the throwable
	 */
	private String generateParentJson(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) throws Throwable {
		try {
			JSONObject parentJsonObject=new JSONObject();
			LinkedHashMap<String, String>jsonMap=excelOperation.getScenarioData(workbook, "DT_UpdateUser2FAAnswersParent", scenarioID).get(0);
			parentJsonObject.put("request",generateUpdateUser2FAAnswersRequestJSON(workbook, "DT_UpdateUser2FAAnswersRequest", scenarioID, excelOperation));
			parentJsonObject.put("echo",generateUpdateUser2FAAnswersEchoJSON(workbook, "DT_UpdateUser2FAAnswersEcho", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>UpdateUser2FAAnswers:</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}

	/**
	 * Generate update user 2 FA answers echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateUpdateUser2FAAnswersEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject updateUser2FAAnswersEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			updateUser2FAAnswersEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return updateUser2FAAnswersEchoJsonObject;
	}

	/**
	 * Generate update user 2 FA answers data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateUpdateUser2FAAnswersDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject updateUser2FAAnswersDataJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			updateUser2FAAnswersDataJsonObject.put("userID",json.get("userID"));
			updateUser2FAAnswersDataJsonObject.put("answers", generateQuestionIdAndAnswerTextJSON(workbook, "DT_QuestionIdAndAnswerText", scenarioID, excelOperation));
			updateUser2FAAnswersDataJsonObject.put("memberID",json.get("memberID"));
		}
		return updateUser2FAAnswersDataJsonObject;
	}

	/**
	 * Generate question id and answer text JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateQuestionIdAndAnswerTextJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONArray array=new JSONArray();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(int i=0;i<jsonMap.size();i++) {
			JSONObject questionIdAndAnswerTextObject=new JSONObject();
			questionIdAndAnswerTextObject.put("QuestionId", jsonMap.get(i).get("QuestionId"));
			questionIdAndAnswerTextObject.put("AnswerText", jsonMap.get(i).get("AnswerText"));
			array.add(questionIdAndAnswerTextObject);
		}
		return array;
	}
	
	/**
	 * Generate update user 2 FA answers request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateUpdateUser2FAAnswersRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject updateUser2FAAnswersJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			updateUser2FAAnswersJsonObject.put("data",generateUpdateUser2FAAnswersDataJSON(workbook, "DT_UpdateUser2FAAnswersData", scenarioID, excelOperation));
			updateUser2FAAnswersJsonObject.put("appID", json.get("appID"));
			updateUser2FAAnswersJsonObject.put("formFactor", json.get("formFactor"));
			updateUser2FAAnswersJsonObject.put("requestType", json.get("requestType"));
		}
		return updateUser2FAAnswersJsonObject;
	}
}
