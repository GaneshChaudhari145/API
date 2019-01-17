package com.API.requests;

import static com.jayway.restassured.RestAssured.given;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForValidatePassword;
import com.API.Response.QAList;
import com.API.ServiceEnum.ServiceEndpoint;
import com.API.Utils.ExcelOperation;
import com.API.Utils.FrameworkServices;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import net.minidev.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class ValidatePassword.
 */
public class ValidatePassword {
	
	/**
	 * Gets the validate password.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the validate password
	 * @throws Exception the exception
	 */
	public void getValidatePassword(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Login_VALIDATEPASSWORD.getUrl();
			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			String respo=res.asString();

			Gson gson=new Gson();
			GetResponseForValidatePassword getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForValidatePassword.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");

			//int infoidss=Integer.parseInt(infoids) ;
			if(infoid.equalsIgnoreCase("0")) {
				JsonArray result = (((JsonArray) ((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("QAList")).getAsJsonArray());
				List<QAList> qAList=getAllApplicationResponse.getResponse().getDataForValidatePassword().getQAList();

				for(Integer i:excelOperation.getScenarioRowforSettingData(workbook, "DT_QAList", scenarioID)) {

					for (QAList qAIndex:qAList)
					{
						excelOperation.setDataIntoExcel(workbook, "DT_QAList", i, "QIndex",qAIndex.getQIndex());			
						i++;
						Reporter.log("<b>QIndex From Response is --></b>"+"<b>"+qAIndex.getQIndex()+"</b>");
					}
					excelOperation.closeWorkbook(workbook);
					break;
				}	
				Assert.assertEquals("0",infoid);	
			}
			else {

				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String infomsgs = ((JsonObject) jsonObject1.get("response")).get("infoMsg").toString();

				Reporter.log("<b>Message ID From Response is ---> </b>" +"<b>"+infomsgs+"</b>");
				Reporter.log("<b>Failed due to ---> Entered Wrong OR Blank Password OR Entered Wrong pawword More Than 3 Times.</b>");
				String infoid1 = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
				Assert.assertEquals("0", infoid1, "Incorrect Password");
			}
		}
		catch(Exception e) {
			throw e;
		}
	}


	/**
	 * Gets the in valid password.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the in valid password
	 * @throws Exception the exception
	 */
	public void getInValidPassword(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Login_VALIDATEPASSWORD.getUrl();
			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			String respo=res.asString();

			Gson gson=new Gson();
			GetResponseForValidatePassword getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForValidatePassword.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			String infomsg = ((JsonObject) jsonObject.get("response")).get("infoMsg").toString();
			//int infoidss=Integer.parseInt(infoids) ;
			if(!infoid.equalsIgnoreCase("0")) {
				String infomsgs = ((JsonObject) jsonObject.get("response")).get("infoMsg").toString();
				Reporter.log("<b>Message ID From Response is ---> </b>" +"<b>"+infomsgs+"</b>");
				Reporter.log("<b>Failed due to ---> Entered Wrong OR Blank Password OR Entered Wrong pawword More Than 3 Times.</b>");
				if(infoid.equals("ENW0017"))
				{
					Assert.assertEquals("ENW0017" ,infoid);
				}
				else if(infoid.equals("ENW0039"))
				{
					Assert.assertEquals("ENW0039", infoid);
				}
			}
			else {

				JsonObject jsonObject1=new Gson().fromJson(res.asString(), JsonObject.class);
				String infomsgs = ((JsonObject) jsonObject1.get("response")).get("infoMsg").toString();

				Reporter.log("<b>Message ID From Response is ---> </b>" +"<b>"+infomsgs+"</b>");
				//Reporter.log("<b>Failed due to ---> Entered Wrong OR Blank Password OR Entered Wrong pawword More Than 3 Times.</b>");
				Assert.assertEquals("0",infoid);	
			}
		}
		catch(Exception e) {
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
	 * @throws JsonParseException the json parse exception
	 * @throws JsonMappingException the json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private String generateParentJson(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) throws JsonParseException, JsonMappingException, IOException {
		JSONObject parentJsonObject=new JSONObject();
		LinkedHashMap<String, String>jsonMap=excelOperation.getScenarioData(workbook, "DT_ValidatePasswordParent", scenarioID).get(0);
		parentJsonObject.put("request",generateValidatePasswordRequestJSON(workbook, "DT_ValidatePasswordRequest", scenarioID, excelOperation));
		parentJsonObject.put("echo",generateValidatePasswordEchoJSON(workbook, "DT_ValidatePasswordEcho", scenarioID, excelOperation));
		parentJsonObject.put("session", jsonMap.get("session"));
		ObjectMapper mapper=new ObjectMapper();
		Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
		String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);		
		Reporter.log("<b><font size=4 color=green>Validate Password</font></b>");
		Reporter.log("<b>Request is--></b>"+indented);
		return parentJsonObject.toString();
	}

	/**
	 * Generate validate password echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateValidatePasswordEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject validatePasswordEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			validatePasswordEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return validatePasswordEchoJsonObject;
	}
	
	/**
	 * Generate validate password request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateValidatePasswordRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject validatePasswordRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			validatePasswordRequestJsonObject.put("data",generateValidatePasswordDataJSON(workbook, "DT_ValidatePasswordData", scenarioID, excelOperation));
			validatePasswordRequestJsonObject.put("appID", json.get("appID"));
			validatePasswordRequestJsonObject.put("formFactor", json.get("formFactor"));
			validatePasswordRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return validatePasswordRequestJsonObject;
	}

	/**
	 * Generate validate password data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateValidatePasswordDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject validatePasswordJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			validatePasswordJsonObject.put("userID",json.get("userID"));
			validatePasswordJsonObject.put("memberID", json.get("memberID"));
			validatePasswordJsonObject.put("password",json.get("password"));
			validatePasswordJsonObject.put("deviceType", json.get("deviceType"));
			validatePasswordJsonObject.put("imageIndex", json.get("imageIndex"));
		}
		return validatePasswordJsonObject;
	}

}
