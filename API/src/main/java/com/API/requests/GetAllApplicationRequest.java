package com.API.requests;

import static com.jayway.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import com.API.Response.arnList;
import com.API.Response.GetAllApplicationResponse;
import com.API.ServiceEnum.ServiceEndpoint;
import com.API.Utils.ExcelOperation;
import com.API.Utils.FrameworkServices;
import com.API.Utils.GetHeaders;
import com.API.Utils.SchemaValidator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

// TODO: Auto-generated Javadoc
/**
 * The Class GetAllApplicationRequest.
 */
public class GetAllApplicationRequest {

	/**
	 * Gets the all application request.
	 *
	 * @param workbook the workbook
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the all application request
	 * @throws Exception the exception
	 */
	public void getAllApplicationRequest(XSSFWorkbook workbook,String scenarioID, ExcelOperation excelOperation) throws Exception {
		try {
		HashMap<String, String> headers = GetHeaders.getHeaders(workbook, scenarioID,excelOperation);
		HashMap<String, String> parameters = excelOperation.getScenarioData(workbook, "DT_Parameters", scenarioID).get(0);
		String uri=FrameworkServices.getConfigProperties().getProperty("ApplicationURI")+ServiceEndpoint.Registration_GET_ALL_APPLICATION_REQUEST.getUrl();
		//String json=generateJson(workbook, scenarioID);
		//GetAuthToken authToken= new GetAuthToken();
		
		Response res = given().
				header("clientid",headers.get("clientid")).
				header("client-secret", headers.get("client-secret")).
				header("state-cd", headers.get("state-cd")).
				header("auth-token", headers.get("auth-token")).
				header("username",headers.get("username")).
				parameters("action",parameters.get("action"),
						"state_cd",parameters.get("state_cd"),
						"start_tm",parameters.get("start_tm"),
						"end_tm",parameters.get("end_tm")).
				when().
				contentType(ContentType.JSON).get(uri);

		Reporter.log(res.asString());
		System.out.println(res.asString());
		/*
		 * Status code assertion 
		 * 
		 */
		String actualStatus_cd = JsonPath.read(res.asString(),"status_cd").toString();
		//boolean assertstatuscode=SchemaValidator.isJsonValid("1", Actualstatus_cd);
		Assert.assertEquals(actualStatus_cd, "1");
		/*
		 * Decrypt Response
		 * 
		 */
		String ActualData = JsonPath.read(res.asString(),"data").toString();
		NseUtil gstUtil = new NseUtil();
		byte[] byteDecodedData = gstUtil.decodeBase64StringTOByte(ActualData);
		String strDecodedData = new String(byteDecodedData);
		System.out.println(strDecodedData);
		/*
		 * Schema Validation
		 * 
		 */
		Reporter.log(strDecodedData);
		String expectedSchema=SchemaValidator.getJsonExpected(FrameworkServices.getConfigProperties().getProperty("SchemaLocation")+"\\Get All Application Requests response_schema.json");
		boolean assertT=SchemaValidator.isJsonValid(expectedSchema, strDecodedData);
		Assert.assertEquals(assertT, true,"Failed due to schema is not matching");
		/*
		 * Convert response into POJO
		 * 
		 */
		Gson gson=new Gson();
		GetAllApplicationResponse getAllApplicationResponse=gson.fromJson(strDecodedData, GetAllApplicationResponse.class);
		JsonObject jsonObject=new Gson().fromJson(strDecodedData, JsonObject.class);
		String result = jsonObject.get("arnList").toString();
		System.out.println("----->"+result);

		List<arnList> arnList=	getAllApplicationResponse.getGetArnListResponses();
		
		/*
		 * 1. Respone sheet -> Scenario Id -> 
		 * 
		 */
		excelOperation.createScenarioDataFromResponse(workbook, "DT_ResponeGetAllApplication", scenarioID, arnList.size());
		for(Integer i:excelOperation.getScenarioRowforSettingData(workbook, "DT_ResponeGetAllApplication", scenarioID)) {
			for (arnList arn:arnList)
			{
				excelOperation.setDataIntoExcel(workbook, "DT_ResponeGetAllApplication", i, "Aplty", arn.getAplty());
				excelOperation.setDataIntoExcel(workbook, "DT_ResponeGetAllApplication", i, "arn", arn.getArn());
				excelOperation.setDataIntoExcel(workbook, "DT_ResponeGetAllApplication", i, "Ismig", arn.getIsmig());
			}
			
			excelOperation.closeWorkbook(workbook);
		}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		//ArrayList<String> list = gson.fromJson(jsonString, new TypeToken<ArrayList<String>>>() {}.getType());
	}

}