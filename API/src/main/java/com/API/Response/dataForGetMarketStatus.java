package com.API.Response;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class dataForGetMarketStatus.
 */
public class dataForGetMarketStatus {
	
	/** The as on date. */
	private String asOnDate;
	
	/** The db status. */
	private String dbStatus;
	
	/** The market status. */
	private List<marketStatus> marketStatus;
	
	/**
	 * Gets the as on date.
	 *
	 * @return the as on date
	 */
	public String getAsOnDate() {
		return asOnDate;
	}
	
	/**
	 * Sets the as on date.
	 *
	 * @param asOnDate the new as on date
	 */
	public void setAsOnDate(String asOnDate) {
		this.asOnDate = asOnDate;
	}
	
	/**
	 * Gets the db status.
	 *
	 * @return the db status
	 */
	public String getDbStatus() {
		return dbStatus;
	}
	
	/**
	 * Sets the db status.
	 *
	 * @param dbStatus the new db status
	 */
	public void setDbStatus(String dbStatus) {
		this.dbStatus = dbStatus;
	}
	
	/**
	 * Gets the market status.
	 *
	 * @return the market status
	 */
	public List<marketStatus> getMarketStatus() {
		return marketStatus;
	}
	
	/**
	 * Sets the market status.
	 *
	 * @param marketStatus the new market status
	 */
	public void setMarketStatus(List<marketStatus> marketStatus) {
		this.marketStatus = marketStatus;
	}
	
}
