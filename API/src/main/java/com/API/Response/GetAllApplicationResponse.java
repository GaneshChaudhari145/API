package com.API.Response;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class GetAllApplicationResponse.
 */
public class GetAllApplicationResponse {
	
	/** The arn list count. */
	private String arnListCount;
	
	/** The arn list. */
	private List <arnList> arnList;
	
	/**
	 * Gets the arn list count.
	 *
	 * @return the arn list count
	 */
	public String getArnListCount() {
		return arnListCount;
	}
	
	/**
	 * Sets the arn list count.
	 *
	 * @param arnListCount the new arn list count
	 */
	public void setArnListCount(String arnListCount) {
		this.arnListCount = arnListCount;
	}
	
	/**
	 * Gets the gets the arn list responses.
	 *
	 * @return the gets the arn list responses
	 */
	public List<arnList> getGetArnListResponses() {
		return arnList;
	}
	
	/**
	 * Sets the gets the arn list responses.
	 *
	 * @param arnLists the new gets the arn list responses
	 */
	public void setGetArnListResponses(List<arnList> arnLists) {
		this.arnList = arnLists;
	}
	
}
