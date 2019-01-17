package com.API.Response;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class dataForOrderHistory.
 */
public class dataForOrderHistory {
	
	/** The order history list. */
	private List<orderHistoryList> orderHistoryList;
	
	/** The trade list. */
	private List<tradeList> tradeList;
	
	/** The order list. */
	private List<orderList> orderList;
	
	/** The position list. */
	private List<positionList> positionList;
	
	/**
	 * Gets the order history list.
	 *
	 * @return the order history list
	 */
	public List<orderHistoryList> getOrderHistoryList() {
		return orderHistoryList;
	}

	/**
	 * Sets the order history list.
	 *
	 * @param orderHistoryList the new order history list
	 */
	public void setOrderHistoryList(List<orderHistoryList> orderHistoryList) {
		this.orderHistoryList = orderHistoryList;
	}

	/**
	 * Gets the trade list.
	 *
	 * @return the trade list
	 */
	public List<tradeList> getTradeList() {
		return tradeList;
	}

	/**
	 * Sets the trade list.
	 *
	 * @param tradeList the new trade list
	 */
	public void setTradeList(List<tradeList> tradeList) {
		this.tradeList = tradeList;
	}

	/**
	 * Gets the order list.
	 *
	 * @return the order list
	 */
	public List<orderList> getOrderList() {
		return orderList;
	}

	/**
	 * Sets the order list.
	 *
	 * @param orderList the new order list
	 */
	public void setOrderList(List<orderList> orderList) {
		this.orderList = orderList;
	}

	/**
	 * Gets the position list.
	 *
	 * @return the position list
	 */
	public List<positionList> getPositionList() {
		return positionList;
	}

	/**
	 * Sets the position list.
	 *
	 * @param positionList the new position list
	 */
	public void setPositionList(List<positionList> positionList) {
		this.positionList = positionList;
	}
	
}
