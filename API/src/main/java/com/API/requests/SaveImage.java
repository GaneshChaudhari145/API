package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

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

import net.minidev.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class SaveImage.
 */
public class SaveImage {
	
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
			LinkedHashMap<String, String>jsonMap=excelOperation.getScenarioData(workbook, "DT_SaveImageParent", scenarioID).get(0);
			parentJsonObject.put("request",generateSaveImageRequestJSON(workbook, "DT_SaveImageRequest", scenarioID, excelOperation));
			parentJsonObject.put("echo",generateSaveImageEchoJSON(workbook, "DT_SaveImageEcho", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Save Image</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}

	/**
	 * Generate save image echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateSaveImageEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject saveImageEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			saveImageEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return saveImageEchoJsonObject;
	}

	/**
	 * Generate save image request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private Object generateSaveImageRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) throws FileNotFoundException, IOException {
		JSONObject saveImageRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			saveImageRequestJsonObject.put("data",generateSaveImageDataJSON(workbook, "DT_SaveImageData", scenarioID, excelOperation));
			saveImageRequestJsonObject.put("appID", json.get("appID"));
			saveImageRequestJsonObject.put("formFactor", json.get("formFactor"));
			saveImageRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return saveImageRequestJsonObject;
	}

	/**
	 * Generate save image data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private Object generateSaveImageDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) throws FileNotFoundException, IOException {
		JSONObject saveImageDataJsonObject=new JSONObject();
		autoImageIndexGenerator(workbook, sheetName, scenarioID, excelOperation);
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			saveImageDataJsonObject.put("userID",json.get("userID"));
			saveImageDataJsonObject.put("imageIndex", json.get("imageIndex"));
			saveImageDataJsonObject.put("memberID", json.get("memberID"));
		}
		return saveImageDataJsonObject;
	}
	
	/**
	 * Save image.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void saveImage(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Login_SAVEIMAGE.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());

			Gson gson=new Gson();
			GetResponseForUpdateUser2FAAnswers getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForUpdateUser2FAAnswers.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);			
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");	
			if(infoid.equalsIgnoreCase("0")) {
				String result = ((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("message").toString();
				String expectedResult=result.replace("\"", " ").trim();	
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+expectedResult+"</b>");
				Assert.assertEquals("image set successfully!", expectedResult);
			}
			else {
				String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
				Assert.assertEquals("0", infoid, "Incorrect Image");
			}			
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Invalid image.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void invalidImage(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Login_SAVEIMAGE.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());

			Gson gson=new Gson();
			GetResponseForUpdateUser2FAAnswers getAllApplicationResponse=gson.fromJson(res.asString(), GetResponseForUpdateUser2FAAnswers.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);			
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");	
			if(!infoid.equalsIgnoreCase("0")) {
				String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
				String expectedResult=infomsg.replace("\"", " ").trim();	
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+expectedResult+"</b>");
				Assert.assertEquals("SYSTEM ERROR - (3000008)", expectedResult);
				Assert.assertEquals("ENW0098",infoid);	
			}
			else {
				String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");				
				Assert.assertEquals("ENW0098",infoid);			
			}			
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Auto image index generator.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void autoImageIndexGenerator(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) throws FileNotFoundException, IOException {

		int[] number = new int[1];
		int count=0;
		int num;
		Random r = new Random();
		while(count<number.length){
			num = r.nextInt(21);
			boolean repeat=false;
			do{
				for(int i=0; i<=number.length; i++){
					if(num==number[i]){
						repeat=true;
						break;
					}
					else if(i==count){
						number[count]=num;
						count++;
						repeat=true;
						break;
					}
				}
			}while(!repeat);
		}
		for(int j=0;j<number.length;j++){
			String s=Integer.toString(number[j]);
			excelOperation.setDataIntoExcel(workbook, "DT_SaveImageData", 1, "imageIndex",s);
		}
	}
}
