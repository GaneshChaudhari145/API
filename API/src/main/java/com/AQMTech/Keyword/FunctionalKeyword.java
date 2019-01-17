package com.AQMTech.Keyword;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.API.TestManagementPOJO.MasterScriptPOJO;
import com.API.Utils.ExcelOperation;

// TODO: Auto-generated Javadoc
/**
 * The Class FunctionalKeyword.
 */
public class FunctionalKeyword extends KeywordHelper {
	
	/**
	 * Execute test step.
	 *
	 * @param workbook the workbook
	 * @param masterScriptPOJO the master script POJO
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void executeTestStep(XSSFWorkbook workbook,MasterScriptPOJO masterScriptPOJO,String sheetName,String scenarioID,ExcelOperation excelOperation) throws Throwable {
		String executeStep=masterScriptPOJO.getStepkeyword();
		switch (executeStep) {
		case "ValidateUser":
			validateUser(workbook,sheetName,scenarioID,excelOperation);
			break;
		case "ValidatePassword":
			validatePassword(workbook,sheetName,scenarioID,excelOperation);
			break;
		case "ValidateAnswers":
			validateAnswers(workbook,sheetName, scenarioID, excelOperation);
			break;
		case "UpdateUser2FAAnswers":
			updateUser2FAAnswers(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "SaveImage":
			saveImage(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "SaveAnswers":
			saveAnswers(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "Logout":
			logout(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "getUser2FAQuestions":
			getUserFAQuestions(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "getAllQuestions":
			getAllQuestions(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "getAllImages":
			getAllImages(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "changePassword":
			changePassword(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "getBalances":
			getBalances(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "getconfig":
			getConfig(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "getUserProfile":
			getUserProfile(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "AlertBook":
			getAlertBook(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "DeleteAlert":
			getDeleteAlert(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "SetAlert":
			getSetAlert(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "ModifyAlert":
			getModifyAlert(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "GetOverview":
			getGetOverview(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "GetMarketStatus":
			getMarketStatus(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "GetExchangeMessage":
			getExchangeMessage(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "GetInstrumentInfo":
			getInstrumentInfo(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "GetTopLosers":
			getTopLosers(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "GetTopGainers":
			getTopGainers(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "GetTopActivebyVolume":
			getTopActivebyVolume(workbook,sheetName,scenarioID, excelOperation);
			break;			
		case "TradeLogs":
			getTradeLogs(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "TradeBook":
			getTradeBook(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "OrderLogs":
			getOrderLogs(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "OrderBook":
			getOrderBook(workbook,sheetName,scenarioID, excelOperation);
			break;
			/*case "GetLastTradedSymbols":
			getLastTradedSymbols(workbook,sheetName,scenarioID, excelOperation);
			break;*/			
		case "GetDefaultGroups":
			getDefaultGroups(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "GetGroupforLiveUser":
			getGroupforLiveUser(workbook,sheetName,scenarioID, excelOperation);
			break;			
		case "GetIndexList":
			getIndexList(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "GetSymbolforDefaultGroup":
			getSymbolforDefaultGroup(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "GetSymbolforOpenUser":
			getSymbolforOpenUser(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "AddGroup":
			addGroup(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "AddSymbol":
			addSymbol(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "DeleteGroup":
			deleteGroup(workbook,sheetName,scenarioID, excelOperation);
			break;			
		case "DeleteSymbol":
			deleteSymbol(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "GetGroupSymbolsLive":
			getGroupSymbolsLive(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "SetDefault":
			setDefault(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "PlaceOrder":
			placeOrder(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "PlaceAMOTrade":
			placeAMOTrade(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "ModifyAMOTrade":
			modifyAMOTrade(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "CancelOrder":
			cancelOrder(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "CancelAMOTrade":
			cancelAMOTrade(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "ModifyOrder":
			modifyOrder(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidPassword":
			invalidPasswordForValidateUser(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidImage":
			invalidImage(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidAnswers":
			invalidAnswers(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidUpdateUser2FAAnswers":
			getInvalidUserFAQuestions(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "getBalancesForNegative":
			getBalancesNegative(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidPasswordData":
			invalidPasswordData(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidAlert":
			invalidSetAlertData(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidGetOverview":
			invalidGetOverviewData(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidExchangeMessage":
			invalidExchangeMessageData(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidGetInstrumentInfo":
			invalidGetInstrumentInfo(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidGetUserProfile":
			invalidGetUserProfile(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "AddDuplicateGroup":
			addDuplicateGroup(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "AddInvalidSymbol":
			addInvalidSymbol(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "DeleteInvalidGroup":
			deleteInvalidSymbol(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "UniversalSearch":
			universalSearch(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "GetLastTradedSymbols":
			getLastTradedSymbols(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "GetQuotes":
			getQuotes(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "IntraDayChart":
			intradayChart(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "GetHoldings":
			getHoldings(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidHoldings":
			invalidHoldings(workbook,sheetName,scenarioID, excelOperation);
			break;	
		case "GetOrderHistory":
			getOrderHistory(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidOrderHistory":
			invalidOrderHistory(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidDataPlaceOrder":
			invalidDataPlaceOrder(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "GetSquareOff":
			getSquareOff(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "GetDayPositions":
			getDayPositions(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "AlertHistory":
			getAlertHistory(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "GetTickerBar":
			getTickerBar(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "UniversalSearchWithExchange":
			getUniversalSearchWithExchange(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "PlaceCoverTrade":
			getPlaceCoverTrade(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "ExitCoverTrade":
			getExitCoverTrade(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "NowDefaultSymbols":
			getNowDefaultSymbols(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "IndexChart":
			getIndexChart(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidSaveAnswer":
			getInvalidSaveAnswer(workbook,sheetName,scenarioID, excelOperation);			
			break;
		case "InvalidGetUser2FAQuestions":
			getUser2FAQuestionsNegative(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "LogoutNegative":
			getLogoutNegative(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidAlertBook":
			getAlertBookForWrongData(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidModifyAlert":
			getModifyAlertForWrongData(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "GetAllQuestionsNegative":
			getAllQuestionsNegative(workbook,sheetName,scenarioID, excelOperation);
			break;		
		case "IndexChartNegative":
			getIndexChartNegative(workbook,sheetName,scenarioID, excelOperation);
			break;	
		case "IntraDayChartNegative":
			getIntraDayChartNegative(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidDeleteAlert":
			getInvalidDeleteAlert(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "NegativeTickerBar":
			getTickerBarNegative(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "GetQuotesNegative":
			getQuotesInvalid(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "PlaceAMOTradeNegative":
			getInvalidPlaceAmoTrade(workbook,sheetName,scenarioID, excelOperation);
			break;	
		case "InvalidOrderBook":
			getInvalidOrderBook(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidOrderLogs":
			getInvalidOrderLogs(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidTradeBook":
			getInvalidTradeBook(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "ModifyAMOTradeNegative":
			getInvalidModifyAmoTrade(workbook,sheetName,scenarioID, excelOperation);
			break;	
		case "InvalidCancelOrder":
			getInvalidCancelOrder(workbook,sheetName,scenarioID, excelOperation);
			break;	
		case "InvalidCancelAMOTrade":
			getInvalidCancelAMOTrade(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidTradeLogs":
			getInvalidTradeLogs(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidModifyOrder":
			getInvalidModifyOrder(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidUniversalSearchData":
			invalidUniversalSearchData(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidPlaceCoverOrder":
			getInvalidPlaceCoverTrade(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidUniversalSearchWithExchange":
			invalidUniversalSearchWithExchange(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidGetLastTradedSymbols":
			invalidGetLastTradedSymbols(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidSetDefault":
			invalidSetDefault(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidGetGroupSymbolsLive":
			invalidGetGroupSymbolsLive(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidGetSymbolforOpenUser":
			invalidGetSymbolforOpenUser(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidGetSymbolforDefaultGroup":
			invalidGetSymbolforDefaultGroup(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidGetGroupforLiveUser":
			invalidGetGroupforLiveUser(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidGetDayPositions":
			invalidGetDayPositions(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidExitCoverTrade":
			invalidExitCoverTrade(workbook,sheetName,scenarioID, excelOperation);
			break;

		case "InvalidGetSquareOff":
			invalidGetSquareOff(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidGetAlertHistory":
			invalidGetAlertHistory(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidDeleteSymbol":
			invalidDeleteSymbol(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "GetAllImagesNegative":
			getAllImagesNegative(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "InvalidGetAllQuestions":
			getAllQuestionsNegative(workbook,sheetName,scenarioID, excelOperation);
			break;

		case "futureClaculator":
			futureClaculator(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "optionClaculator":
			optionalcalculatordetail(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "placeEquitySIPOrder":
			placeEquitySIPOrder(workbook,sheetName,scenarioID, excelOperation);
			break;
			
		case "placeSpreadOrder":
			placeSpreadOrder(workbook,sheetName,scenarioID, excelOperation);
			break;
	
		case"positionConversion":
			positionConversion(workbook,sheetName,scenarioID, excelOperation);
			break;
			
		case"PaymentgatewayForURlgenrator":
			PaymentgatewayForURlgenrator(workbook,sheetName,scenarioID, excelOperation);
			break;
			
		case"GetSipOrderBookDetail":
			GetSipOrderBookDetail(workbook,sheetName,scenarioID, excelOperation);
			break;
		case "cancelSpreadOrder":
			cancelSpreadOrder(workbook,sheetName,scenarioID, excelOperation);
			break;
				
		case "modifySpreadOrder":
			modifySpreadOrder(workbook,sheetName,scenarioID, excelOperation);
			break;
			
		//Added By Suraj
			
		case "CancelSIPOrder":
			GetcancelSIPOrder(workbook,sheetName,scenarioID, excelOperation);
			break;
			
		case "SipSearch":
			GetsipSearch(workbook,sheetName,scenarioID, excelOperation);
			break;
			
		case "PaymentTransactionHistoryGenerator":
			GetPaymentTransactionHistoryGenerator(workbook,sheetName,scenarioID, excelOperation);
			break;
			
		case "PaymentGatewayStatusGenerator":
			GetpaymentGatewayStatusGenerator(workbook,sheetName,scenarioID, excelOperation);
			break;
		
		case "GetPaymentClientDetails":
			GetPaymentClientDetails(workbook,sheetName,scenarioID, excelOperation);
			break;
			

		default:			
			break;

		}
	}
}
