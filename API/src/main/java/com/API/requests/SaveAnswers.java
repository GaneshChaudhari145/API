package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForUpdateUser2FAAnswers;
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
 * The Class SaveAnswers.
 */
public class SaveAnswers {
	
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
		LinkedHashMap<String, String>jsonMap=excelOperation.getScenarioData(workbook, "DT_SaveAnswersParent", scenarioID).get(0);
		parentJsonObject.put("request",generateSaveAnswersRequestJSON(workbook, "DT_SaveAnswersRequest", scenarioID, excelOperation));
		parentJsonObject.put("echo",generateSaveAnswersEchoJSON(workbook, "DT_SaveAnswersEcho", scenarioID, excelOperation));
		parentJsonObject.put("session", jsonMap.get("session"));
		ObjectMapper mapper=new ObjectMapper();
		Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
		String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
		Reporter.log("<b><font size=4 color=green>Save Answers</font></b>");
		Reporter.log("<b>Request is--></b>"+indented);
		return parentJsonObject.toString();
	}

	/**
	 * Generate save answers echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateSaveAnswersEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject saveAnswersEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			saveAnswersEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return saveAnswersEchoJsonObject;
	}

	/**
	 * Generate save answers request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateSaveAnswersRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject saveAnswersRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			saveAnswersRequestJsonObject.put("data",generateSaveAnswersDataJSON(workbook, "DT_SaveAnswersData", scenarioID, excelOperation));
			saveAnswersRequestJsonObject.put("appID", json.get("appID"));
			saveAnswersRequestJsonObject.put("formFactor", json.get("formFactor"));
			saveAnswersRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return saveAnswersRequestJsonObject;
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
			SystemInfoJsonObject.put("ApplicationVersion", "1.0.164");
		}
		return SystemInfoJsonObject;
	}

	/**
	 * Generate save answers data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateSaveAnswersDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject saveAnswersPasswordJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			saveAnswersPasswordJsonObject.put("userID",json.get("userID"));
			saveAnswersPasswordJsonObject.put("answers", generateAnswersJSON(workbook, "DT_Answers", scenarioID, excelOperation));
			saveAnswersPasswordJsonObject.put("deviceType",json.get("deviceType"));
			saveAnswersPasswordJsonObject.put("SystemInfo", generateSystemInfo(workbook, "DT_Answers", scenarioID, excelOperation));
			saveAnswersPasswordJsonObject.put("targetInterfaceIP",json.get("targetInterfaceIP"));
			saveAnswersPasswordJsonObject.put("memberID",json.get("memberID"));
		}
		return saveAnswersPasswordJsonObject;
	}

	/**
	 * Generate answers JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateAnswersJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONArray array=new JSONArray();

		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(int i=0;i<jsonMap.size();i++) {
			JSONObject qIndexJsonObject=new JSONObject();
			qIndexJsonObject.put("QuestionId", jsonMap.get(i).get("QuestionId"));
			qIndexJsonObject.put("AnswerText", jsonMap.get(i).get("AnswerText"));
			array.add(qIndexJsonObject);
		}
		return array;
	}
	
	/**
	 * Save answers.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void saveAnswers(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Login_SAVEANSWERS.getUrl();
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
			String result = ((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("message").toString();
			String expectedResult=result.replace("\"", " ").trim();	
			String infoid=  ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");

			if(infoid.equalsIgnoreCase("0")) {
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+expectedResult+"</b>");
				Assert.assertEquals("answers saved successfully!", expectedResult);
			}
			else {
				String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString();
				//String infomsgs=infomsg.toString();
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
				Assert.assertEquals(expectedResult, infomsg);
			}
			Assert.assertEquals("0", infoid);
		}
		catch(Exception e) {
			throw e;
		}
	}

	/**
	 * Invalid save answers.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void invalidSaveAnswers(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Login_SAVEANSWERS.getUrl();
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
			String result =((JsonObject) jsonObject.get("response")).get("infoMsg").toString();
			String expectedResult=result.replace("\"", " ").trim();	
			String infoid=  ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");

			if(!infoid.equalsIgnoreCase("0")) {
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+expectedResult+"</b>");
				if(infoid.equals("ENW0100")) {
					Assert.assertEquals("Answer Can not be Null",expectedResult);
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
				Assert.assertEquals("ENW0100","0");	
			}
		}
		catch(Exception e) {
			throw e;
		}
	}
}
