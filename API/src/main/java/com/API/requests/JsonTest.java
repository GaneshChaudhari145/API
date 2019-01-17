package com.API.requests;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.API.Utils.ExcelOperation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
// TODO: Auto-generated Javadoc

/**
 * The Class JsonTest.
 */
public class JsonTest {
	
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
		LinkedHashMap<String, String>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID).get(0);
		parentJsonObject.put("rgdtls",generateRGdtlsJSON(workbook, "DT_rgdtls", scenarioID, excelOperation));
		parentJsonObject.put("desmno", jsonMap.get("desmno"));
		parentJsonObject.put("desmdt", jsonMap.get("desmdt"));
		parentJsonObject.put("desmrs", jsonMap.get("desmrs"));
		parentJsonObject.put("pdtls",generatePdtlssJSON(workbook, "DT_pdtls", scenarioID, excelOperation));
		parentJsonObject.put("docsezed",generatedocsezed(workbook, "DT_docsezed", scenarioID, excelOperation));
		ObjectMapper mapper=new ObjectMapper();
		Object json1 = mapper.readValue(parentJsonObject.toString(), Object.class);
		String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
		System.out.println(indented);
		return parentJsonObject.toString();
	}
	
	/**
	 * Generate R gdtls JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the JSON object
	 */
	public JSONObject generateRGdtlsJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject rgdtlsJsonObject=new JSONObject();

		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			rgdtlsJsonObject.put("aplty", json.get("aplty"));
			rgdtlsJsonObject.put("stcd", json.get("stcd"));
		}
		return rgdtlsJsonObject;
	}
	
	/**
	 * Generate pdtlss JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the JSON object
	 */
	public JSONObject generatePdtlssJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONObject pddtlsJsonObject=new JSONObject();

		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(LinkedHashMap<String, String> json:jsonMap) {
			pddtlsJsonObject.put("fnm", json.get("fnm"));
			pddtlsJsonObject.put("mnm", json.get("mnm"));
			pddtlsJsonObject.put("lnm", json.get("lnm"));
			pddtlsJsonObject.put("ftfnm", json.get("ftfnm"));
			pddtlsJsonObject.put("ftlnm", json.get("ftlnm"));
			pddtlsJsonObject.put("ftmnm", json.get("ftmnm"));
			pddtlsJsonObject.put("gd", json.get("gd"));
			pddtlsJsonObject.put("dob", json.get("dob"));
			
		}
		return pddtlsJsonObject;
	}
	
	/**
	 * Generatedocsezed.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the JSON array
	 */
	public JSONArray generatedocsezed(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) {
		JSONArray array=new JSONArray();
	
		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		for(int i=0;i<jsonMap.size();i++) {
	
			JSONObject docsezedJsonObject=new JSONObject();
			docsezedJsonObject.put("docdesc", jsonMap.get(i).get("docdesc"));
			docsezedJsonObject.put("docdt", jsonMap.get(i).get("docdt"));
			docsezedJsonObject.put("docdtl", generatedocdtlJSON(workbook, "DT_docdtl", scenarioID, excelOperation,i));
			//array.appendElement(docsezedJsonObject);
	
		}
		return array;
	}
	
	/**
	 * Generatedocdtl JSON.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @param i the i
	 * @return the JSON object
	 */
	public JSONObject generatedocdtlJSON(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation,int i) {
		JSONObject doctdlJsonObject=new JSONObject();

		List<LinkedHashMap<String, String>>jsonMap=excelOperation.getScenarioData(workbook, sheetName, scenarioID);
		
	
			doctdlJsonObject.put("id", jsonMap.get(i).get("id"));
			doctdlJsonObject.put("ty", jsonMap.get(i).get("ty"));
			doctdlJsonObject.put("hash", jsonMap.get(i).get("hash"));
		
		return doctdlJsonObject;
	}
	
	/**
	 * Gets the json.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the json
	 * @throws JsonParseException the json parse exception
	 * @throws JsonMappingException the json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void getJson(XSSFWorkbook workbook, String sheetName, String scenarioID,ExcelOperation excelOperation) throws JsonParseException, JsonMappingException, IOException {

		generateParentJson(workbook, sheetName, scenarioID, excelOperation);
	}
}
