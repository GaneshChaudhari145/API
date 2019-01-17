package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForGetUserProfile;
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
 * The Class GetUserProfile.
 */
public class GetUserProfile {
	
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
			parentJsonObject.put("echo",generateLogoutEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("request",generateLogoutRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Get User Profile</font></b>");
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
			logoutRequestJsonObject.put("data",generateGetBalancesDataJSON(workbook, "DT_LogoutData", scenarioID, excelOperation));
			logoutRequestJsonObject.put("appID", json.get("appID"));
			logoutRequestJsonObject.put("formFactor", json.get("formFactor"));
			logoutRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return logoutRequestJsonObject;
	}

	/**
	 * Generate get balances data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetBalancesDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getBalancesJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getBalancesJsonObject.put("userID",json.get("userID"));
			
		}
		return getBalancesJsonObject;
	}

	/**
	 * Gets the user profile.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the user profile
	 * @throws Exception the exception
	 */
	public void getUserProfile(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {

			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Info_GETUSERPROFILE.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);
			Response res = given().		
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String infoid=((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			if(infoid.equalsIgnoreCase("0")){
				Assert.assertEquals("0", infoid);
				Gson gson=new Gson();
				GetResponseForGetUserProfile getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForGetUserProfile.class);
				String branchId = getAllApplicationResponse.getResponse().getData().getBranchId();
				String clientId = getAllApplicationResponse.getResponse().getData().getClientId();
				String clientName = getAllApplicationResponse.getResponse().getData().getClientName();
				String emailId = getAllApplicationResponse.getResponse().getData().getEmailId();
				String isInvestorClient = getAllApplicationResponse.getResponse().getData().getIsInvestorClient();
				String pan = getAllApplicationResponse.getResponse().getData().getPAN();
				Reporter.log("<b>Branch Id---> </b>"+"<b>"+branchId+ "</b>");
				Reporter.log("<b>ClientId---> </b>"+"<b>"+clientId+ "</b>");
				Reporter.log("<b>Client Name---> </b>"+"<b>"+clientName+ "</b>");
				Reporter.log("<b>Is Investor Client---> </b>"+"<b>"+isInvestorClient+ "</b>");
				Reporter.log("<b>PAN---> </b>"+"<b>"+pan+ "</b>");
			}
			else {
				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String infomsgs = ((JsonObject) jsonObject1.get("response")).get("infoMsg").toString();
				Reporter.log("<b>Failed due to ---> </b>" +"<b>"+infomsgs+"</b>");

				String infoid1 = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
				//Reporter.log("<b>Message From Response is--></b>"+"<b>"+infoid1+"</b>");
				Assert.assertEquals("0", infoid1);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Invalid get user profile.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void invalidGetUserProfile(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {

			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Info_GETUSERPROFILE.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);
			Response res = given().		
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());

			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String infoid=((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			//System.out.println(session);
			if(!infoid.equalsIgnoreCase("0")){
				String result =((JsonObject) jsonObject.get("response")).get("infoMsg").toString();
				String expectedResult=result.replace("\"", " ").trim();	
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+expectedResult+"</b>");
				if(infoid.equals("ENW0016")) {
					Assert.assertEquals("User details not found.",expectedResult);
				}		
				else 
				{
					Reporter.log("<b>Invalid Data</b>");
					Assert.assertEquals("ENW0016",infoid);	
				}
			}
			else {
				String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString();
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
				Assert.assertEquals("ENW0016",infoid);	
			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
