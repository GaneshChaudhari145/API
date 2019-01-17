package com.API.ServiceEnum;

// TODO: Auto-generated Javadoc
/**
 * The Enum ServiceEndpoint.
 */
public enum ServiceEndpoint {
	
	/** The authenticate. */
	AUTHENTICATE("/v0.2/authenticate"),
	
	/** The Registration DOWNLOA D DOCUMENT. */
	Registration_DOWNLOAD_DOCUMENT("/v0.2/document"),
	
	/** The Registration GE T AL L APPLICATIO N REQUEST. */
	Registration_GET_ALL_APPLICATION_REQUEST("/v0.2/application"),
	
	/** The Registration GETAPPRE Q NE W RE G SUBMIT. */
	Registration_GETAPPREQ_NEW_REG_SUBMIT("/v0.2/application"),
	
	/** The Registration GETALERT. */
	Registration_GETALERT("/v0.2/alert"),
	
	/** The Registration get entity requests. */
	Registration_GetEntityRequests("/v0.2/entity"),
	
	/** The logout. */
	LOGOUT("/v0.2/logout"),
	
	/** The Login VALIDATEPASSWORD. */
	Login_VALIDATEPASSWORD("/Login/ValidatePassword/1.0.0"),
	
	/** The Login VALIDATEANSWERS. */
	Login_VALIDATEANSWERS("/Login/ValidateAnswers/1.0.0"),
	
	/** The Login VALIDATEUSER. */
	Login_VALIDATEUSER("/Login/ValidateUser/1.0.0"),
	
	/** The Login UPDATEUSERSTOFAANSWERS. */
	Login_UPDATEUSERSTOFAANSWERS("/Login/updateUser2FAAnswers/1.0.0"),
	
	/** The Login SAVEIMAGE. */
	Login_SAVEIMAGE("/Login/SaveImage/1.0.0"),
	
	/** The Login SAVEANSWERS. */
	Login_SAVEANSWERS("/Login/SaveAnswers/1.0.0"),
	
	/** The Login LOGOUT. */
	Login_LOGOUT("/Login/Logout/1.0.0"),
	
	/** The Login GETUSER 2 FAQUESTIONS. */
	Login_GETUSER2FAQUESTIONS("/Login/GetUser2FAQuestions/1.0.0"),
	
	/** The Login GETALLQUESTIONS. */
	Login_GETALLQUESTIONS("/Login/GetAllQuestions/1.0.0"),
	
	/** The Login GETALLIMAGES. */
	Login_GETALLIMAGES("/Login/GetAllImages/1.0.0"),
	
	/** The Login CHANGEPASSWORD. */
	Login_CHANGEPASSWORD("/Login/ChangePassword/1.0.0"),
	
	/** The Balance GETBALANCES. */
	Balance_GETBALANCES("/Balance/GetBalances/1.0.0"),
	
	/** The Config CONFIG. */
	Config_CONFIG("/Config/Base/1.0.0"),
	
	/** The Info GETUSERPROFILE. */
	Info_GETUSERPROFILE("/Info/GetUserProfile/1.0.0"),
	
	/** The Alerts alert book. */
	Alerts_AlertBook("/Alerts/AlertBook/1.0.0"),
	
	/** The Alerts delete alerts. */
	Alerts_DeleteAlerts("/Alerts/DeleteAlerts/1.0.0"),
	
	/** The Alerts set alerts. */
	Alerts_SetAlerts("/Alerts/SetAlerts/1.0.0"),
	
	/** The Alerts modify alerts. */
	Alerts_ModifyAlerts("/Alerts/ModifyAlerts/1.0.0"),
	
	/** The Dashboard get overview. */
	Dashboard_GetOverview("/Dashboard/GetOverview/1.0.0"),
	
	/** The Dashboard get market status. */
	Dashboard_GetMarketStatus("/Dashboard/GetMarketStatus/1.0.0"),
	
	/** The Dashboard get exchange message. */
	Dashboard_GetExchangeMessage("/Dashboard/GetExchangeMessage/1.0.0"),
	
	/** The Info get instrument info. */
	Info_GetInstrumentInfo("/Info/GetInstrumentInfo/1.0.0"),
	
	/** The Market movers top activeby volume. */
	MarketMovers_TopActivebyVolume("/MarketMovers/TopActivebyVolume/1.0.0"),
	
	/** The Market movers top gainers. */
	MarketMovers_TopGainers("/MarketMovers/TopGainers/1.0.0"),
	
	/** The Market movers top losers. */
	MarketMovers_TopLosers("/MarketMovers/TopLosers/1.0.0"),
	
	/** The Orders trade logs. */
	Orders_TradeLogs("/Orders/TradeLogs/1.0.0"),
	
	/** The Orders trade book. */
	Orders_TradeBook("/Orders/TradeBook/1.0.0"),
	
	/** The Orders order logs. */
	Orders_OrderLogs("/Orders/OrderLogs/1.0.0"),
	
	/** The Orders order book. */
	Orders_OrderBook("/Orders/OrderBook/1.0.0"),
	
	/** The Orders order history. */
	Orders_OrderHistory("/Orders/OrderHistory/1.0.0"),
	
	/** The Watch list get default groups. */
	WatchList_GetDefaultGroups("/WatchList/GetDefaultGroups/1.0.0"),
	
	/** The Watch list get groupfor live user. */
	WatchList_GetGroupforLiveUser("/WatchList/GetGroupforLiveUser/1.0.0"),
	
	/** The Watch list get index list. */
	WatchList_GetIndexList("/WatchList/GetIndexList/1.0.0"),
	
	/** The Watch list get symbolfor default group. */
	WatchList_GetSymbolforDefaultGroup("/WatchList/GetSymbolforDefaultGroup/1.0.0"),
	
	/** The Watch list get symbolfor open user. */
	WatchList_GetSymbolforOpenUser("/WatchList/GetSymbolforOpenUser/1.0.0"),
	
	/** The Watch list add group. */
	WatchList_AddGroup("/WatchList/AddGroup/1.0.0"),
	
	/** The Watch list add symbol. */
	WatchList_AddSymbol("/WatchList/AddSymbol/1.0.0"),
	
	/** The Watch list delete group. */
	WatchList_DeleteGroup("/WatchList/DeleteGroup/1.0.0"),
	
	/** The Watch list delete symbol. */
	WatchList_DeleteSymbol("/WatchList/DeleteSymbol/1.0.0"),
	
	/** The Watch list get symbolfor live user. */
	WatchList_GetSymbolforLiveUser("/WatchList/GetSymbolforLiveUser/1.0.0"),
	
	/** The Watch list set default. */
	WatchList_SetDefault("/WatchList/SetDefault/1.0.0"),
	
	/** The Trade place order. */
	Trade_PlaceOrder("/Trade/PlaceTrade/1.0.0"),
	
	/** The Trade place AMO trade. */
	Trade_PlaceAMOTrade("/Trade/PlaceAMOTrade/1.0.0"),
	
	/** The Trade modify AMO trade. */
	Trade_ModifyAMOTrade("/Trade/ModifyAMOTrade/1.0.0"),
	
	/** The Trade cancel order. */
	Trade_CancelOrder("/Trade/CancelOrder/1.0.0"),
	
	/** The Trade cancel AMO trade. */
	Trade_CancelAMOTrade("/Trade/CancelAMOTrade/1.0.0"),
	
	/** The Trade modify order. */
	Trade_ModifyOrder("/Trade/ModifyOrder/1.0.0"),
	
	/** The Chart index chart. */
	Chart_IndexChart("/Chart/IndexChart/1.0.1"),
	
	/** The Chart intraday chart. */
	Chart_IntradayChart("/Chart/IntradayChart/1.0.1"),
	
	/** The Symbol search universal search. */
	SymbolSearch_UniversalSearch("/SymbolSearch/UniversalSearch/1.0.0"),
	
	/** The Symbol search get last traded symbols. */
	SymbolSearch_GetLastTradedSymbols("/SymbolSearch/GetLastTradedSymbols/1.0.0"),
	
	/** The Quote get quotes. */
	Quote_GetQuotes("/Quote/GetQuote/1.0.0"),
	
	/** The Holdings get holdings. */
	Holdings_GetHoldings("/Holdings/GetHoldings/1.0.0"),
	
	/** The Positions get day positions. */
	Positions_GetDayPositions("/Positions/GetPositions/1.0.0"),
	
	/** The Positions get square off. */
	Positions_GetSquareOff("/Positions/GetSquareOff/1.0.0"),
	
	/** The Alerts alert history. */
	Alerts_AlertHistory("/Alerts/AlertHistory/1.0.0"),
	
	/** The Dashboard get ticker bar. */
	Dashboard_GetTickerBar("/Dashboard/GetTickerBar/1.0.0"),
	
	/** The Symbol search universal search with exchange. */
	SymbolSearch_UniversalSearchWithExchange("/SymbolSearch/UniversalSearchWithExchange/1.0.0"),
	
	/** The Trade place cover trade. */
	Trade_PlaceCoverTrade("/Trade/PlaceCoverTrade/1.0.0"),
	
	/** The Trade exit cover trade. */
	Trade_ExitCoverTrade("/Trade/ExitCoverTrade/1.0.0"),
	
	/** The Watch list now default symbols. */
	WatchList_NowDefaultSymbols("/WatchList/NowDefaultSymbols/1.0.0"),
	
	/** The Future calculator. */
	Future_Calculator("/Calculator/GetFutureCalculator/1.0.0"),
	
	/** The Option calculator. */
	Option_Calculator("/Calculator/GetOptionCalculator/1.0.0"),
	
	/** The Sip order book. */
	Sip_OrderBook("/Orders/SIPOrderBook/1.0.0"),
	
	/** The Place equity SIP order. */
	Place_EquitySIPOrder("/Trade/PlaceEquitySIPOrder/1.0.0"),
	
	/** The Place spread order. */
	Place_SpreadOrder("/Trade/PlaceSpreadOrder/1.0.0"),
	
	/** The Position conversion. */
	Position_Conversion("/Orders/PositionConversion/1.0.0"),
	
	/** The Payment gateway URL generator. */
	Payment_GatewayURLGenerator("/PaymentGateway/PaymentGatewayURLGenerator/1.0.0"),
	
	/** The Cancel spread order. */
	Cancel_SpreadOrder("/Trade/cancelSpreadOrder/1.0.0"),
	
	/** The Modify spread orderr. */
	Modify_SpreadOrderr("/Trade/PlaceSpreadOrder/1.0.0"),
	
	/** The sip search. */
	//Added BY Suraj
	sip_Search("/SymbolSearch/SIPSearch/1.0.0"),
	
	/** The Cancel SIP order. */
	Cancel_SIPOrder("/Trade/CancelSIPOrder/1.0.0"),
	
	/** The payment transaction history generator. */
	paymentTransaction_HistoryGenerator("/PaymentGateway/paymentTransactionHistoryGenerator/1.0.0"),
	
	/** The Payment gateway status generator. */
	PaymentGateway_StatusGenerator("/PaymentGateway/getPaymentClientDetails/1.0.0"),
	
	/** The Get payment client details. */
	GetPayment_ClientDetails("/PaymentGateway/getPaymentClientDetails/1.0.0");
	
	/** The url. */
	private String url;
	
	/**
	 * Instantiates a new service endpoint.
	 *
	 * @param url the url
	 */
	ServiceEndpoint(String url) {
		this.url = url;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param data the new url
	 */
	public void setUrl(String data) {
		this.url = data+url;
	}

}
