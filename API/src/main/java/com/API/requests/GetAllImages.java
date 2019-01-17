package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.GetResponseForUpdateUser2FAAnswers;
import com.API.Response.ResponseForGetAllImages;
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
 * The Class GetAllImages.
 */
public class GetAllImages {
	
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
			parentJsonObject.put("echo",generateGetAllImagesEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("request",generateGetAllImagesRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>Get All Images</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}

	/**
	 * Generate get all images echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetAllImagesEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getAllImagesEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getAllImagesEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return getAllImagesEchoJsonObject;
	}

	/**
	 * Generate get all images request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetAllImagesRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getAllImagesRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getAllImagesRequestJsonObject.put("data",generateGetAllImagesDataJSON(workbook, "DT_LogoutData", scenarioID, excelOperation));
			getAllImagesRequestJsonObject.put("appID", json.get("appID"));
			getAllImagesRequestJsonObject.put("formFactor", json.get("formFactor"));
			getAllImagesRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return getAllImagesRequestJsonObject;
	}

	/**
	 * Generate get all images data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetAllImagesDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getAllImagesDataJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getAllImagesDataJsonObject.put("userID",json.get("userID"));
			getAllImagesDataJsonObject.put("memberID",json.get("memberID"));
		}
		return getAllImagesDataJsonObject;
	}
	
	/**
	 * Gets the all images.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the all images
	 * @throws Exception the exception
	 */
	public void getAllImages(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Login_GETALLIMAGES.getUrl();
			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);
			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			//System.out.println(res.asString());

			Gson gson=new Gson();
			ResponseForGetAllImages getAllImages=gson.fromJson(res.asString(), ResponseForGetAllImages.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String result = ((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("imageList").toString();
			String expectedResult=result.replace("\"", " ").trim();
			//System.out.println(result);
			List<imageList> imageList=getAllImages.getResponse().getData().getImageList();
			String infoid = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			if(!imageList.isEmpty()) {
				if(infoid.equalsIgnoreCase("0")) {
					//excelOperation.createScenarioDataFromResponse(workbook, "DT_ResponseForGetAllImages", scenarioID, imageList.size());
					Assert.assertEquals("0", infoid);
					for(Integer i:excelOperation.getScenarioRowforSettingData(workbook, "DT_ResponseForGetAllImages", scenarioID)) {
						for (imageList il:imageList)
						{
							excelOperation.setDataIntoExcel(workbook, "DT_ResponseForGetAllImages", i, "imageIndex", il.getImageIndex());
							excelOperation.setDataIntoExcel(workbook, "DT_ResponseForGetAllImages", i, "image", il.getImage());
							i++;
							Reporter.log("<b>Image Index---></b>" +"<b>"+il.getImageIndex()+"</b>");
						}
						excelOperation.closeWorkbook(workbook);
						break;
					}
				}
				else {
					String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
					Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
					Assert.assertEquals("0", infoid, "Incorrect Parameters");
				}	
			}
			else {
				Reporter.log("<b>Image List is Empty</b>");
				Assert.assertEquals("0", infoid);
			}	
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * Gets the all images negative.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @return the all images negative
	 * @throws Exception the exception
	 */
	public void getAllImagesNegative(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Login_GETALLIMAGES.getUrl();
			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);
			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			System.out.println(res.asString());
			Gson gson=new Gson();
			ResponseForGetAllImages getAllImages=gson.fromJson(res.asString(), ResponseForGetAllImages.class);
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			String result = ((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("imageList").toString();
			String expectedResult=result.replace("\"", " ").trim();
			//System.out.println(result);
			List<imageList> imageList=getAllImages.getResponse().getData().getImageList();
			String infoid = ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
			if(!imageList.isEmpty()) {
				if(infoid.equalsIgnoreCase("0")) {
					//excelOperation.createScenarioDataFromResponse(workbook, "DT_ResponseForGetAllImages", scenarioID, imageList.size());
					Assert.assertEquals("0", infoid);
					for(Integer i:excelOperation.getScenarioRowforSettingData(workbook, "DT_ResponseForGetAllImages", scenarioID)) {
						for (imageList il:imageList)
						{
							excelOperation.setDataIntoExcel(workbook, "DT_ResponseForGetAllImages", i, "imageIndex", il.getImageIndex());
							excelOperation.setDataIntoExcel(workbook, "DT_ResponseForGetAllImages", i, "image", il.getImage());
							i++;
							Reporter.log("<b>Image Index---></b>" +"<b>"+il.getImageIndex()+"</b>");
						}
						excelOperation.closeWorkbook(workbook);
						break;
					}
				}
				else {
					String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
					Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
					Assert.assertEquals("0", infoid, "Incorrect Parameters");
				}	
			}
		}
		catch(Exception e) {
			throw e;
		}
	}
}
