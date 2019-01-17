package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.QAList;
import com.API.Response.ResponseForGetUserFAQuestions;
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
 * The Class GetUserFAQuestions.
 */
public class GetUserFAQuestions {

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
			LinkedHashMap<String, String>jsonMap=excelOperation.getScenarioData(workbook, "DT_LogoutParent", scenarioID).get(0);
			parentJsonObject.put("echo",generateLogoutEchoJSON(workbook, "DT_LogoutEcho", scenarioID, excelOperation));
			parentJsonObject.put("request",generateLogoutRequestJSON(workbook, "DT_LogoutRequest", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Get User 2 FA Questions</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}

	/**
	 * Generate logout echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateLogoutEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject logoutEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			logoutEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return logoutEchoJsonObject;
	}

	/**
	 * Generate logout request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateLogoutRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject logoutRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			logoutRequestJsonObject.put("data",generateLogoutDataJSON(workbook, "DT_LogoutData", scenarioID, excelOperation));
			logoutRequestJsonObject.put("appID", json.get("appID"));
			logoutRequestJsonObject.put("formFactor", json.get("formFactor"));
			logoutRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return logoutRequestJsonObject;
	}

	/**
	 * Generate logout data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateLogoutDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject logoutDataJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			logoutDataJsonObject.put("userID",json.get("userID"));
		}
		return logoutDataJsonObject;
	}

	/**
	 * Gets the user FA questions.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the user FA questions
	 * @throws Exception the exception
	 */
	public void getUserFAQuestions(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {


			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Login_GETUSER2FAQUESTIONS.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);
			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());

			Gson gson=new Gson();
			ResponseForGetUserFAQuestions faQuestions=gson.fromJson(res.asString(), ResponseForGetUserFAQuestions.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String result = ((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("QAList").toString();
			String infoid = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			String expectedResult=result.replace("\"", " ").trim();
			if(infoid.equalsIgnoreCase("0")) {
				List<QAList> qaList=faQuestions.getResponse().getData().getQAList();	
				for(Integer i:excelOperation.getScenarioRowforSettingData(workbook, "DT_ResUpdateUser2FAQuestion", scenarioID)) {
					for (QAList qa:qaList)
					{
						excelOperation.setDataIntoExcel(workbook, "DT_ResUpdateUser2FAQuestion", i, "question", qa.getQuestion());
						excelOperation.setDataIntoExcel(workbook, "DT_ResUpdateUser2FAQuestion", i, "QIndex", qa.getQIndex().toString());
					}

					excelOperation.closeWorkbook(workbook);
				}
				Assert.assertEquals("0", infoid);
			}
			else
			{
				Reporter.log("Invalid data");
				Assert.assertEquals("0", infoid);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
