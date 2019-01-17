package com.API.requests;

import static com.jayway.restassured.RestAssured.given;

import java.io.FileNotFoundException;
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
 * The Class PaymentGatewayURLGenerator.
 */
public class PaymentGatewayURLGenerator {
	
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
			parentJsonObject.put("request",generateGetSymbolforDefaultGroupRequestJSON(workbook, "DT_RequestEntity", scenarioID, excelOperation));
			parentJsonObject.put("echo",generateGetforFutureCalculatorDefaultGroupEchoJSON(workbook, "DT_EchoEntity", scenarioID, excelOperation));
			parentJsonObject.put("session", jsonMap.get("session"));
			ObjectMapper mapper=new ObjectMapper();
			Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
			Reporter.log("<b><font size=4 color=green>PaymentGateway</font></b>");
			Reporter.log("<b>Request is--></b>"+indented);
			return parentJsonObject.toString();

		}
		catch (Exception |AssertionError e) {
			throw e;
		}
	}
	
	/**
	 * Generate getfor future calculator default group echo JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 */
	private Object generateGetforFutureCalculatorDefaultGroupEchoJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject getGroupforLiveUserEchoJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getGroupforLiveUserEchoJsonObject.put("requestOwner", json.get("requestOwner"));
		}
		return getGroupforLiveUserEchoJsonObject;
	}
	
	/**
	 * Generate get symbolfor default group request JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private Object generateGetSymbolforDefaultGroupRequestJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) throws FileNotFoundException, IOException {
		JSONObject getGroupforLiveUserRequestJsonObject=new JSONObject();
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			getGroupforLiveUserRequestJsonObject.put("data",generateGetSymbolforDefaultGroupDataJSON(workbook, "DT_paymentGatewayGenerator", scenarioID, excelOperation));
			getGroupforLiveUserRequestJsonObject.put("appID", json.get("appID"));
			getGroupforLiveUserRequestJsonObject.put("formFactor", json.get("formFactor"));
			getGroupforLiveUserRequestJsonObject.put("requestType", json.get("requestType"));
		}
		return getGroupforLiveUserRequestJsonObject;
	}
	
	/**
	 * Generate get symbolfor default group data JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the object
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private Object generateGetSymbolforDefaultGroupDataJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) throws FileNotFoundException, IOException {
		JSONObject getGroupforLiveUserRequestJsonObject=new JSONObject();
			List<LinkedHashMap<String, String>>jsonMaps=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
			List<LinkedHashMap<String, String>>jsonMaps1=excelOperation.getScenarioID(workbook, sheetName, scenarioID);
			for(LinkedHashMap<String, String> jsons:jsonMaps) {
				for(LinkedHashMap<String, String> jsons1:jsonMaps1) {
					getGroupforLiveUserRequestJsonObject.put("brokerID", jsons.get("brokerID"));
					getGroupforLiveUserRequestJsonObject.put("userID", jsons.get("userID"));
					getGroupforLiveUserRequestJsonObject.put("accountID", jsons.get("accountID"));
					getGroupforLiveUserRequestJsonObject.put("segment", jsons.get("segment"));
					getGroupforLiveUserRequestJsonObject.put("amount", jsons.get("amount"));
					getGroupforLiveUserRequestJsonObject.put("transCurrency", jsons.get("transCurrency"));
					getGroupforLiveUserRequestJsonObject.put("customerAccount", jsons.get("customerAccount"));
					getGroupforLiveUserRequestJsonObject.put("paymentGatewayType", jsons.get("paymentGatewayType"));
					getGroupforLiveUserRequestJsonObject.put("source", jsons.get("source"));
					getGroupforLiveUserRequestJsonObject.put("returnURL", jsons.get("returnURL"));
					getGroupforLiveUserRequestJsonObject.put("paymentType", jsons.get("paymentType"));
					getGroupforLiveUserRequestJsonObject.put("token", jsons.get("token"));
			}
		}
		return getGroupforLiveUserRequestJsonObject;
	}
	
	/**
	 * Paymentgatewayforgenerator detail.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param excelOperation the excel operation
	 * @param scenarioID the scenario ID
	 * @throws Exception the exception
	 */
	public void PaymentgatewayforgeneratorDetail(XSSFWorkbook workbook,String sheetName,ExcelOperation excelOperation,String scenarioID) throws Exception {
		try {
			String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Payment_GatewayURLGenerator.getUrl();

			String json=generateParentJson(workbook,sheetName,scenarioID,excelOperation);

			Response res = given().	
					body(json).
					when().
					contentType(ContentType.JSON).post(uri);
			Reporter.log("<b>Response is--></b>"+res.asString());
			
			JsonObject jsonObject=new Gson().fromJson(res.asString(), JsonObject.class);
			
			String infoid= ((JsonObject) jsonObject.get("response")).get("infoID").toString().replace("\"", "");
		
			//String sipInstructionID=((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("sipInstructionID").toString().replace("\"", "");
			 
			if(infoid.equalsIgnoreCase("0"))
			{
				    String url=((JsonObject) ((JsonObject) jsonObject.get("response")).get("data")).get("uri").toString().replace("\"", "");
				    System.out.println("uri Value:-"+url);
				    String[] msg=url.split("=");
				 
				    
				    XSSFSheet sheet=workbook.getSheet("DT_PaymentGatewayStatusGeneratr");
					Row headerRow=sheet.getRow(0);
					LinkedHashMap<String, Integer> headerPosition=new LinkedHashMap<>();
					for(int columnNo=0;columnNo<headerRow.getPhysicalNumberOfCells();columnNo++) {
						headerPosition.put(headerRow.getCell(columnNo).getStringCellValue(), columnNo);
					}
					for(int j=0;j<sheet.getPhysicalNumberOfRows();j++)
					{
						XSSFRow	row = sheet.getRow(j);
						XSSFCell cell = row.getCell(headerPosition.get("ScenarioId"),Row.CREATE_NULL_AS_BLANK);

						if(cell.getStringCellValue().equalsIgnoreCase(scenarioID)) {
							excelOperation.updateDataInExcelForInstructionID(workbook, "DT_PaymentGatewayStatusGeneratr",scenarioID,"Msg", msg[1]);
							System.out.println("Updated Data PaymentGatewayStatusGeneratTo DT_PaymentGatewayStatusGeneratr ");
						}
					}
				    Assert.assertEquals("0", infoid);
				    Reporter.log("<b>Place order SuccessFully</b>");
			}
			else {
				String infomsg=  ((JsonObject) jsonObject.get("response")).get("infoMsg").toString().replace("\"", "");
				Reporter.log("<b>Message From Response is--></b>"+"<b>"+infomsg+"</b>");
				Assert.assertEquals("ENW0039", infoid, "Incorrect Parameters");
			}	
		}
		catch(Exception e) {
			e.printStackTrace();	
			throw e;
		}
	}
}
