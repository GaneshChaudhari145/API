package com.API.Response;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ValidateUserResponse.
 */
public class ValidateUserResponse {
	
	/** The data count. */
	private String dataCount;
	
	/** The data. */
	private List<GetResponseForValidateUser> data;
	
	/**
	 * Gets the image index count.
	 *
	 * @return the image index count
	 */
	public String getimageIndexCount() {
		return dataCount;
	}
	
	/**
	 * Sets the image index count.
	 *
	 * @param dataCount the new image index count
	 */
	public void setimageIndexCount(String dataCount) {
		this.dataCount = dataCount;
	}
	
	/**
	 * Gets the gets the image index responses.
	 *
	 * @return the gets the image index responses
	 */
	public List<GetResponseForValidateUser> getGetImageIndexResponses() {
		return data;
	}
	
	/**
	 * Sets the gets the image index responses.
	 *
	 * @param data the new gets the image index responses
	 */
	public void setGetImageIndexResponses(List<GetResponseForValidateUser> data) {
		this.data = data;
	}
	
}
