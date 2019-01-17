package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

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

import net.minidev.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class ChangePassword.
 */
public class ChangePassword {
	
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
			parentJsonObject.put("echo",generateChangePasswordEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("request",generateChangePasswordRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			//parentJsonObject.put("session", generateChangePasswordSessionJSON(workbook, "DT_ChangePasswordSession", scenarioID, excelOperation));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Change Password</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}

	/*private Object generateChangePasswordSessionJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject changePasswordEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			changePasswordEchoJsonObject.put("session", json.get("session"));
		}
		return changePasswordEchoJsonObject;
	}*/

	/**
	 * Generate change password echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateChangePasswordEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject changePasswordEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			changePasswordEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return changePasswordEchoJsonObject;
	}

	/**
	 * Generate change password request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateChangePasswordRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject changePasswordRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			changePasswordRequestJsonObject.put("data",generateChangepasswordDataJSON(workbook, "DT_ChangePasswordData", scenarioID, excelOperation));
			changePasswordRequestJsonObject.put("appID", json.get("appID"));
			changePasswordRequestJsonObject.put("formFactor", json.get("formFactor"));
			changePasswordRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return changePasswordRequestJsonObject;
	}

	/**
	 * Generate changepassword data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateChangepasswordDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject changePasswordDataJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			changePasswordDataJsonObject.put("userID",json.get("userID"));
			changePasswordDataJsonObject.put("oldLoginPassword",json.get("oldLoginPassword"));
			changePasswordDataJsonObject.put("nwLoginPassword",json.get("nwLoginPassword"));
			changePasswordDataJsonObject.put("confirmNewLoginPassword",json.get("confirmNewLoginPassword"));
			changePasswordDataJsonObject.put("oldTransPassword",json.get("oldTransPassword"));
			changePasswordDataJsonObject.put("nwTransPassword",json.get("nwTransPassword"));
			changePasswordDataJsonObject.put("confirmNewTransPassword",json.get("confirmNewTransPassword"));

		}
		return changePasswordDataJsonObject;
	}

	/**
	 * Change password.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void changePassword(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {

			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Login_CHANGEPASSWORD.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);
			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());

			Gson gson=new Gson();
			GetResponseForValidatePassword getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForValidatePassword.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");

			if(infoid.equalsIgnoreCase("ENW0040")) {
				Assert.assertEquals("ENW0040", infoid);
				Reporter.log("<b>Password Changed Successfully</b>");
			}
			else {
				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String infomsgs = ((JsonObject) jsonObject1.get("response")).get("infoMsg").toString().replace("\"", "");
				Reporter.log("<b>Message From Response is---></b>"+"<b>"+infomsgs+"</b>");

				String infoid1 = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");

				if(infoid1.equals("ENW0103")) {
					Assert.assertEquals("ENW0040", infoid1);
				}
				else if(infoid1.equals("ENW0017")){
					Reporter.log("<b>Failed due to --> Old Password Is Wrong OR Entered Same With Previous 5 Passwords");
					Assert.assertEquals("ENW0040", infoid1);
				}
				else if(infoid1.equals("ENW0039")){
					Assert.assertEquals("ENW0040", infoid1);
				}

			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Change password with wrong data.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void changePasswordWithWrongData(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {

			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Login_CHANGEPASSWORD.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);
			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());

			Gson gson=new Gson();
			GetResponseForValidatePassword getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForValidatePassword.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			String infomsgs = ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
			if(!infoid.equalsIgnoreCase("ENW0040")) {

				if(infoid.equals("ENW0103")) {
					Assert.assertEquals("ENW0103", infoid);
					Reporter.log("<b>Message From Response is---></b>"+"<b>"+infomsgs+"</b>");
				}
				else if(infoid.equals("ENW0017")){

					Assert.assertEquals("ENW0017", infoid);
					Reporter.log("<b>Message From Response is---></b>"+"<b>"+infomsgs+"</b>");
				}
				else if(infoid.equals("ENW0039")){
					Assert.assertEquals("ENW0039", infoid);
					Reporter.log("<b>Message From Response is---></b>"+"<b>"+infomsgs+"</b>");
				}
				Reporter.log("<b>Failed due to --> Old Password Is Wrong OR Entered Same With Previous 5 Passwords");
			}
			else {

				Reporter.log("<b>Message From Response is---></b>"+"<b>"+infomsgs+"</b>");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
