package com.API.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class SchemaValidator.
 */
public class SchemaValidator {
	
	/** The Constant JSON_V4_SCHEMA_IDENTIFIER. */
	public static final String JSON_V4_SCHEMA_IDENTIFIER = "http://json-schema.org/draft-04/schema#";
	
	/** The Constant JSON_SCHEMA_IDENTIFIER_ELEMENT. */
	public static final String JSON_SCHEMA_IDENTIFIER_ELEMENT = "$schema";

	/**
	 * Gets the json node.
	 *
	 * @param jsonText the json text
	 * @return the json node
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static JsonNode getJsonNode(String jsonText) 
			throws IOException
	{
		return JsonLoader.fromString(jsonText);
	} // getJsonNode(text) ends

	/**
	 * Gets the json node.
	 *
	 * @param jsonFile the json file
	 * @return the json node
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static JsonNode getJsonNode(File jsonFile) 
			throws IOException 
	{
		return JsonLoader.fromFile(jsonFile);
	} // getJsonNode(File) ends

	/**
	 * Gets the json node.
	 *
	 * @param url the url
	 * @return the json node
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static JsonNode getJsonNode(URL url) 
			throws IOException 
	{
		return JsonLoader.fromURL(url);
	} // getJsonNode(URL) ends

	/**
	 * Gets the json node from resource.
	 *
	 * @param resource the resource
	 * @return the json node from resource
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static JsonNode getJsonNodeFromResource(String resource) 
			throws IOException
	{
		return JsonLoader.fromResource(resource);
	} // getJsonNode(Resource) ends

	/**
	 * Gets the schema node.
	 *
	 * @param schemaText the schema text
	 * @return the schema node
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ProcessingException the processing exception
	 */
	public static JsonSchema getSchemaNode(String schemaText)
			throws IOException, ProcessingException 
	{
		final JsonNode schemaNode = getJsonNode(schemaText);
		return _getSchemaNode(schemaNode);
	} // getSchemaNode(text) ends

	/**
	 * Gets the schema node.
	 *
	 * @param schemaFile the schema file
	 * @return the schema node
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ProcessingException the processing exception
	 */
	public static JsonSchema getSchemaNode(File schemaFile)
			throws IOException, ProcessingException
	{
		final JsonNode schemaNode = getJsonNode(schemaFile);
		return _getSchemaNode(schemaNode);
	} // getSchemaNode(File) ends

	/**
	 * Gets the schema node.
	 *
	 * @param schemaFile the schema file
	 * @return the schema node
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ProcessingException the processing exception
	 */
	public static JsonSchema getSchemaNode(URL schemaFile)
			throws IOException, ProcessingException
	{
		final JsonNode schemaNode = getJsonNode(schemaFile);
		return _getSchemaNode(schemaNode);
	} // getSchemaNode(URL) ends
	
	/**
	 * Gets the schema node from resource.
	 *
	 * @param resource the resource
	 * @return the schema node from resource
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ProcessingException the processing exception
	 */
	public static JsonSchema getSchemaNodeFromResource(String resource)
			throws IOException, ProcessingException 
	{
		final JsonNode schemaNode = getJsonNodeFromResource(resource);
		return _getSchemaNode(schemaNode);
	} // getSchemaNode() ends

	/**
	 * Validate json.
	 *
	 * @param jsonSchemaNode the json schema node
	 * @param jsonNode the json node
	 * @throws ProcessingException the processing exception
	 */
	public static void validateJson(JsonSchema jsonSchemaNode, JsonNode jsonNode)
			throws ProcessingException 
	{
		ProcessingReport report = jsonSchemaNode.validate(jsonNode);
		if (!report.isSuccess()) {
			for (ProcessingMessage processingMessage : report) {
				throw new ProcessingException(processingMessage);
			}
		}
	} // validateJson(Node) ends
	
	/**
	 * Checks if is json valid.
	 *
	 * @param jsonSchemaNode the json schema node
	 * @param jsonNode the json node
	 * @return true, if is json valid
	 * @throws ProcessingException the processing exception
	 */
	public static boolean isJsonValid(JsonSchema jsonSchemaNode, JsonNode jsonNode) throws ProcessingException
	{
		ProcessingReport report = jsonSchemaNode.validate(jsonNode);
		return report.isSuccess();
	} // validateJson(Node) ends

	/**
	 * Checks if is json valid.
	 *
	 * @param schemaText the schema text
	 * @param jsonText the json text
	 * @return true, if is json valid
	 * @throws ProcessingException the processing exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static boolean isJsonValid(String schemaText, String jsonText) throws ProcessingException, IOException
	{   
		final JsonSchema schemaNode = getSchemaNode(schemaText);
		final JsonNode jsonNode = getJsonNode(jsonText);
		return isJsonValid(schemaNode, jsonNode);
	} // validateJson(Node) ends
	
	/**
	 * Gets the schema node.
	 *
	 * @param jsonNode the json node
	 * @return the json schema
	 * @throws ProcessingException the processing exception
	 */
	private static JsonSchema _getSchemaNode(JsonNode jsonNode)
			throws ProcessingException
	{
		final JsonNode schemaIdentifier = jsonNode.get(JSON_SCHEMA_IDENTIFIER_ELEMENT);
		if (null == schemaIdentifier){
			((ObjectNode) jsonNode).put(JSON_SCHEMA_IDENTIFIER_ELEMENT, JSON_V4_SCHEMA_IDENTIFIER);
		}
		final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
		return factory.getJsonSchema(jsonNode);
	} // _getSchemaNode() ends
	
	/**
	 * Gets the json expected.
	 *
	 * @param jsonLocation the json location
	 * @return the json expected
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String getJsonExpected(String jsonLocation) throws IOException {
		String json="";
		File file=new File(jsonLocation);
		if (file.exists()){
			InputStream is = new FileInputStream(file);
			json = IOUtils.toString(is);
		}
		return json;
	}
}
