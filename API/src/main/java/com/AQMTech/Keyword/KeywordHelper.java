package com.AQMTech.Keyword;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.API.Utils.ExcelOperation;
import com.API.requests.AddGroup;
import com.API.requests.AddSymbol;
import com.API.requests.AlertBook;
import com.API.requests.AlertHistory;
import com.API.requests.CancelAMOTrade;
import com.API.requests.CancelOrder;
import com.API.requests.CancelSpreadOrder;
import com.API.requests.CancelSIPOrder;
import com.API.requests.ChangePassword;
import com.API.requests.DeleteAlerts;
import com.API.requests.DeleteGroup;
import com.API.requests.DeleteSymbol;
import com.API.requests.GetAllApplicationRequest;
import com.API.requests.GetAllImages;
import com.API.requests.GetAllQuestions;
import com.API.requests.GetApplicationRequestNewReqSubmit;
import com.API.requests.GetBalances;
import com.API.requests.GetConfig;
import com.API.requests.GetDayPositions;
import com.API.requests.GetDefaultGroups;
import com.API.requests.ValidateUser;
import com.API.requests.GetEntityRequests;
import com.API.requests.GetExchangeMessage;
import com.API.requests.ExitCoverOrder;
import com.API.requests.FutureCalculator;
import com.API.requests.GetGroupSymbolsLive;
import com.API.requests.GetGroupforLiveUser;
import com.API.requests.GetHoldings;
import com.API.requests.GetIndexList;
import com.API.requests.GetInstrumentInfo;
import com.API.requests.GetLastTradedSymbols;
import com.API.requests.GetMarketStatus;
import com.API.requests.NowDefaultSymbols;
import com.API.requests.OptionCaculator;
import com.API.requests.GetOverview;
import com.API.requests.PlaceCoverOrder;
import com.API.requests.PlaceEquitySIPOrder;
import com.API.requests.GetQuote;
import com.API.requests.GetSipOrderBookDetails;
import com.API.requests.GetSquareOff;
import com.API.requests.GetSymbolforDefaultGroup;
import com.API.requests.GetSymbolforOpenUser;
import com.API.requests.GetTickerBar;
import com.API.requests.UniversalSearchWithExchange;
import com.API.requests.GetUser2FAQuestions;
import com.API.requests.GetUserProfile;
import com.API.requests.IndexChart;
import com.API.requests.IntradayChart;
import com.API.requests.Logout;
import com.API.requests.ModifyAMOTrade;
import com.API.requests.ModifyAlerts;
import com.API.requests.ModifyOrder;
import com.API.requests.ModifySpreadOrder;
import com.API.requests.OrderBook;
import com.API.requests.OrderHistory;
import com.API.requests.OrderLogs;
import com.API.requests.PaymentClientDetails;
import com.API.requests.PaymentGatewayURLGenerator;
import com.API.requests.PaymentGatewayStatusGenerator;
import com.API.requests.PaymentTransactionHistoryGenerator;
import com.API.requests.PlaceAMOTrade;
import com.API.requests.PlaceOrder;
import com.API.requests.PlaceSpreadOrder;
import com.API.requests.PositionConversion;
import com.API.requests.SaveAnswers;
import com.API.requests.SaveImage;
import com.API.requests.SetAlerts;
import com.API.requests.SetDefault;
import com.API.requests.SipSearch;
import com.API.requests.TopActivebyVolume;
import com.API.requests.TopGainers;
import com.API.requests.TopLosers;
import com.API.requests.TradeBook;
import com.API.requests.TradeLogs;
import com.API.requests.UniversalSearch;
import com.API.requests.UpdateUser2FAAnswers;
import com.API.requests.ValidateAnswers;
import com.API.requests.ValidatePassword;

// TODO: Auto-generated Javadoc
/**
 * The Class KeywordHelper.
 */
public class KeywordHelper {
	
	/**
	 * Validate user.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Exception the exception
	 */
	public void validateUser(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Exception {
		ValidateUser validateuser = new ValidateUser();
		validateuser.getValidateUser(workbook,sheetName,excelOperation,scenarioID);
	}
	
	/**
	 * Validate password.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Exception the exception
	 */
	public void validatePassword(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Exception {
		ValidatePassword validatePassword =new ValidatePassword();
		validatePassword.getValidatePassword(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Invalid password data.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Exception the exception
	 */
	public void invalidPasswordData(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Exception {
		ValidatePassword validatePassword =new ValidatePassword();
		validatePassword.getInValidPassword(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Validate answers.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Exception the exception
	 */
	public void validateAnswers(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Exception {
		ValidateAnswers validateAnswers = new ValidateAnswers();
		validateAnswers.getValidateAnswers(workbook,sheetName,excelOperation, scenarioID);
	}
	
	/**
	 * Update user 2 FA answers.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void updateUser2FAAnswers(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		UpdateUser2FAAnswers updateUser2FAAnswers = new UpdateUser2FAAnswers();
		updateUser2FAAnswers.updateUser2FAAnswers(workbook, sheetName, scenarioID, excelOperation);
	}
	
	/**
	 * Save image.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void saveImage(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		SaveImage saveImageobj = new SaveImage();
		saveImageobj.saveImage(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Invalid image.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidImage(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		SaveImage saveImageobj = new SaveImage();
		saveImageobj.invalidImage(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Save answers.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void saveAnswers(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		SaveAnswers saveAnswersobj = new SaveAnswers();
		saveAnswersobj.saveAnswers(workbook, sheetName, excelOperation, scenarioID);
	}	
	
	/**
	 * Gets the invalid save answer.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the invalid save answer
	 * @throws Throwable the throwable
	 */
	public void getInvalidSaveAnswer(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		SaveAnswers saveAnswersobj = new SaveAnswers();
		saveAnswersobj.invalidSaveAnswers(workbook, sheetName, excelOperation, scenarioID);
	}	
	
	/**
	 * Invalid answers.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidAnswers(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {

		ValidateAnswers validateAnswers = new ValidateAnswers();
		validateAnswers.invalidAnswers(workbook,sheetName,excelOperation, scenarioID);
	}
	
	/**
	 * Logout.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void logout(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		Logout logoutobj = new Logout();
		logoutobj.logout(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the logout negative.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the logout negative
	 * @throws Throwable the throwable
	 */
	public void getLogoutNegative(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		Logout logoutobj = new Logout();
		logoutobj.getLogoutNegative(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the user FA questions.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the user FA questions
	 * @throws Throwable the throwable
	 */
	public void getUserFAQuestions(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetUser2FAQuestions getUserFAQuestionsobj = new GetUser2FAQuestions();
		getUserFAQuestionsobj.getUserFAQuestions(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the user 2 FA questions negative.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the user 2 FA questions negative
	 * @throws Throwable the throwable
	 */
	public void getUser2FAQuestionsNegative(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetUser2FAQuestions getUserFAQuestionsobj = new GetUser2FAQuestions();
		getUserFAQuestionsobj.getInvalidUserFAQuestions(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the invalid user FA questions.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the invalid user FA questions
	 * @throws Throwable the throwable
	 */
	public void getInvalidUserFAQuestions(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		UpdateUser2FAAnswers updateUser2FAAnswers = new UpdateUser2FAAnswers();
		updateUser2FAAnswers.updateUser2FABlankAnswers(workbook, sheetName, scenarioID, excelOperation);
	}
	
	/**
	 * Gets the all questions.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the all questions
	 * @throws Throwable the throwable
	 */
	public void getAllQuestions(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetAllQuestions getallQuestionsobj = new GetAllQuestions();
		getallQuestionsobj.getAllQuestions(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the all questions negative.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the all questions negative
	 * @throws Throwable the throwable
	 */
	public void getAllQuestionsNegative(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetAllQuestions getallQuestionsobj = new GetAllQuestions();
		getallQuestionsobj.getAllQuestionsNegative(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the all images negative.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the all images negative
	 * @throws Throwable the throwable
	 */
	public void getAllImagesNegative(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetAllImages getallimagesobj = new GetAllImages();
		getallimagesobj.getAllImagesNegative(workbook, sheetName, excelOperation, scenarioID);
	}	
	
	/**
	 * Gets the all images.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the all images
	 * @throws Throwable the throwable
	 */
	public void getAllImages(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetAllImages getallimagesobj = new GetAllImages();
		getallimagesobj.getAllImages(workbook, sheetName, excelOperation, scenarioID);
	}	
	
	/**
	 * Change password.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void changePassword(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		ChangePassword changepasswordobj = new ChangePassword();
		changepasswordobj.changePassword(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the balances.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the balances
	 * @throws Throwable the throwable
	 */
	public void getBalances(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetBalances getBalancesobj = new GetBalances();
		getBalancesobj.getBalances(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the balances negative.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the balances negative
	 * @throws Throwable the throwable
	 */
	public void getBalancesNegative(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetBalances getBalancesobj = new GetBalances();
		getBalancesobj.getBalancesNegative(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the config.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the config
	 * @throws Throwable the throwable
	 */
	public void getConfig(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetConfig getConfigobj = new GetConfig();
		getConfigobj.getConfig(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the user profile.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the user profile
	 * @throws Throwable the throwable
	 */
	public void getUserProfile(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetUserProfile getUserProfileobj = new GetUserProfile();
		getUserProfileobj.getUserProfile(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Invalid get user profile.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidGetUserProfile(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetUserProfile getUserProfileobj = new GetUserProfile();
		getUserProfileobj.invalidGetUserProfile(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the alert book.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the alert book
	 * @throws Throwable the throwable
	 */
	public void getAlertBook(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		AlertBook alertBookobj = new AlertBook();
		alertBookobj.getAlertBook(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the alert book for wrong data.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the alert book for wrong data
	 * @throws Throwable the throwable
	 */
	public void getAlertBookForWrongData(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		AlertBook alertBookobj = new AlertBook();
		alertBookobj.getAlertBookForWrongData(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the delete alert.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the delete alert
	 * @throws Throwable the throwable
	 */
	public void getDeleteAlert(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		DeleteAlerts deleteAlertsobj = new DeleteAlerts();
		deleteAlertsobj.getDeleteAlert(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the invalid delete alert.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the invalid delete alert
	 * @throws Throwable the throwable
	 */
	public void getInvalidDeleteAlert(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		DeleteAlerts deleteAlertsobj = new DeleteAlerts();
		deleteAlertsobj.getDeleteAlertForWrongData(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the modify alert for wrong data.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the modify alert for wrong data
	 * @throws Throwable the throwable
	 */
	public void getModifyAlertForWrongData(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		ModifyAlerts modifyAlertssobj = new ModifyAlerts();
		modifyAlertssobj.getModifyAlertForWrongData(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the sets the alert.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the sets the alert
	 * @throws Throwable the throwable
	 */
	public void getSetAlert(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		SetAlerts setAlertsobj = new SetAlerts();
		setAlertsobj.getSetAlert(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Invalid set alert data.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidSetAlertData(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		SetAlerts setAlertsobj = new SetAlerts();
		setAlertsobj.getSetAlertForWrongData(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the modify alert.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the modify alert
	 * @throws Throwable the throwable
	 */
	public void getModifyAlert(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		ModifyAlerts modifyAlertssobj = new ModifyAlerts();
		modifyAlertssobj.getModifyAlert(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the gets the overview.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the gets the overview
	 * @throws Throwable the throwable
	 */
	public void getGetOverview(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetOverview getOverviewobj = new GetOverview();
		getOverviewobj.getGetOverview(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Invalid get overview data.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidGetOverviewData(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetOverview getOverviewobj = new GetOverview();
		getOverviewobj.invalidGetOverviewData(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the market status.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the market status
	 * @throws Throwable the throwable
	 */
	public void getMarketStatus(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetMarketStatus getMarketStatusobj = new GetMarketStatus();
		getMarketStatusobj.getMarketStatus(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the exchange message.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the exchange message
	 * @throws Throwable the throwable
	 */
	public void getExchangeMessage(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetExchangeMessage getExchangeMessageobj = new GetExchangeMessage();
		getExchangeMessageobj.getExchangeMessage(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Invalid exchange message data.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidExchangeMessageData(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetExchangeMessage getExchangeMessageobj = new GetExchangeMessage();
		getExchangeMessageobj.invalidGetExchangeMessage(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the instrument info.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the instrument info
	 * @throws Throwable the throwable
	 */
	public void getInstrumentInfo(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetInstrumentInfo getInstrumentInfoobj = new GetInstrumentInfo();
		getInstrumentInfoobj.getInstrumentInfo(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Invalid get instrument info.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidGetInstrumentInfo(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetInstrumentInfo getInstrumentInfoobj = new GetInstrumentInfo();
		getInstrumentInfoobj.getInvalidInstrumentInfo(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the top losers.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the top losers
	 * @throws Throwable the throwable
	 */
	public void getTopLosers(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		TopLosers topLosersobj = new TopLosers();
		topLosersobj.getTopLoser(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the top gainers.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the top gainers
	 * @throws Throwable the throwable
	 */
	public void getTopGainers(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		TopGainers topGainersobj = new TopGainers();
		topGainersobj.getTopGainers(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the top activeby volume.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the top activeby volume
	 * @throws Throwable the throwable
	 */
	public void getTopActivebyVolume(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		TopActivebyVolume topActivebyVolumeobj = new TopActivebyVolume();
		topActivebyVolumeobj.getTopActivebyVolume(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the trade logs.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the trade logs
	 * @throws Throwable the throwable
	 */
	public void getTradeLogs(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		TradeLogs tradeLogsobj = new TradeLogs();
		tradeLogsobj.getTradeLogs(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the trade book.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the trade book
	 * @throws Throwable the throwable
	 */
	public void getTradeBook(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		TradeBook tradeBookobj = new TradeBook();
		tradeBookobj.getTradeBooks(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the order logs.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the order logs
	 * @throws Throwable the throwable
	 */
	public void getOrderLogs(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		OrderLogs orderLogsobj = new OrderLogs();
		orderLogsobj.getOrderLogs(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the order book.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the order book
	 * @throws Throwable the throwable
	 */
	public void getOrderBook(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		OrderBook orderBookobj = new OrderBook();
		orderBookobj.getOrderBooks(workbook, sheetName, excelOperation, scenarioID);
	}

	/**
	 * Gets the invalid trade logs.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the invalid trade logs
	 * @throws Throwable the throwable
	 */
	public void getInvalidTradeLogs(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		TradeLogs tradeLogsobj = new TradeLogs();
		tradeLogsobj.getInvalidTradeLogs(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the invalid trade book.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the invalid trade book
	 * @throws Throwable the throwable
	 */
	public void getInvalidTradeBook(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		TradeBook tradeBookobj = new TradeBook();
		tradeBookobj.getInvalidTradeBook(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the invalid order logs.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the invalid order logs
	 * @throws Throwable the throwable
	 */
	public void getInvalidOrderLogs(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		OrderLogs orderLogsobj = new OrderLogs();
		orderLogsobj.getInvalidOrderLogs(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the invalid order book.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the invalid order book
	 * @throws Throwable the throwable
	 */
	public void getInvalidOrderBook(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		OrderBook orderBookobj = new OrderBook();
		orderBookobj.getInvalidOrderBook(workbook, sheetName, excelOperation, scenarioID);
	}	
	
	/**
	 * Gets the last traded symbols.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the last traded symbols
	 * @throws Throwable the throwable
	 */
	public void getLastTradedSymbols(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetLastTradedSymbols getLastTradedSymbolsobj = new GetLastTradedSymbols();
		getLastTradedSymbolsobj.getLastTradedSymbols(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the default groups.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the default groups
	 * @throws Throwable the throwable
	 */
	public void getDefaultGroups(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetDefaultGroups getDefaultGroupsobj = new GetDefaultGroups();
		getDefaultGroupsobj.getDefaultGroups(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the groupfor live user.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the groupfor live user
	 * @throws Throwable the throwable
	 */
	public void getGroupforLiveUser(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetGroupforLiveUser getGroupforLiveUserobj = new GetGroupforLiveUser();
		getGroupforLiveUserobj.getGroupforLiveUser(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Invalid get groupfor live user.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidGetGroupforLiveUser(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetGroupforLiveUser getGroupforLiveUserobj = new GetGroupforLiveUser();
		getGroupforLiveUserobj.invalidGetGroupforLiveUser(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the index list.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the index list
	 * @throws Throwable the throwable
	 */
	public void getIndexList(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetIndexList getIndexListobj = new GetIndexList();
		getIndexListobj.getIndexList(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the symbolfor default group.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the symbolfor default group
	 * @throws Throwable the throwable
	 */
	public void getSymbolforDefaultGroup(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetSymbolforDefaultGroup getSymbolforDefaultGroupobj = new GetSymbolforDefaultGroup();
		getSymbolforDefaultGroupobj.getSymbolforDefaultGroup(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Invalid get symbolfor default group.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidGetSymbolforDefaultGroup(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetSymbolforDefaultGroup getSymbolforDefaultGroupobj = new GetSymbolforDefaultGroup();
		getSymbolforDefaultGroupobj.invalidGetSymbolforDefaultGroup(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the symbolfor open user.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the symbolfor open user
	 * @throws Throwable the throwable
	 */
	public void getSymbolforOpenUser(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetSymbolforOpenUser getSymbolforOpenUserobj = new GetSymbolforOpenUser();
		getSymbolforOpenUserobj.getSymbolforOpenUser(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Invalid get symbolfor open user.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidGetSymbolforOpenUser(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetSymbolforOpenUser getSymbolforOpenUserobj = new GetSymbolforOpenUser();
		getSymbolforOpenUserobj.invalidGetSymbolforOpenUser(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Adds the group.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void addGroup(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		AddGroup addGroupobj = new AddGroup();
		addGroupobj.addGroup(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Adds the duplicate group.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void addDuplicateGroup(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		AddGroup addGroupobj = new AddGroup();
		addGroupobj.addDuplicateGroup(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Adds the symbol.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void addSymbol(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		AddSymbol addSymbolobj = new AddSymbol();
		addSymbolobj.addSymbol(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Adds the invalid symbol.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void addInvalidSymbol(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		AddSymbol addSymbolobj = new AddSymbol();
		addSymbolobj.addInvalidSymbol(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Delete invalid symbol.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void deleteInvalidSymbol(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		DeleteGroup deleteGroupobj = new DeleteGroup();
		deleteGroupobj.deleteInvalidSymbol(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Delete group.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void deleteGroup(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		DeleteGroup deleteGroupobj = new DeleteGroup();
		deleteGroupobj.deleteGroup(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Delete symbol.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void deleteSymbol(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		DeleteSymbol deleteSymbolobj = new DeleteSymbol();
		deleteSymbolobj.getdeleteSymbol(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Invalid delete symbol.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidDeleteSymbol(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		DeleteSymbol deleteSymbolobj = new DeleteSymbol();
		deleteSymbolobj.invalidDeleteSymbol(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the group symbols live.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the group symbols live
	 * @throws Throwable the throwable
	 */
	public void getGroupSymbolsLive(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetGroupSymbolsLive getGroupSymbolsLiveobj = new GetGroupSymbolsLive();
		getGroupSymbolsLiveobj.getGroupSymbolsLive(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Invalid get group symbols live.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidGetGroupSymbolsLive(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetGroupSymbolsLive getGroupSymbolsLiveobj = new GetGroupSymbolsLive();
		getGroupSymbolsLiveobj.invalidGetGroupSymbolsLive(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Sets the default.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void setDefault(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		SetDefault setDefaultobj = new SetDefault();
		setDefaultobj.setDefault(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Invalid set default.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidSetDefault(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		SetDefault setDefaultobj = new SetDefault();
		setDefaultobj.invalidSetDefault(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Place order.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void placeOrder(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		PlaceOrder placeOrderobj = new PlaceOrder();
		placeOrderobj.placeOrder(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Place AMO trade.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void placeAMOTrade(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		PlaceAMOTrade placeAMOTradeobj = new PlaceAMOTrade();
		placeAMOTradeobj.placeAMOTrade(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the invalid place amo trade.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the invalid place amo trade
	 * @throws Throwable the throwable
	 */
	public void getInvalidPlaceAmoTrade(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		PlaceAMOTrade placeAMOTradeobj = new PlaceAMOTrade();
		placeAMOTradeobj.getInvalidPlaceAmoTrade(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Modify AMO trade.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void modifyAMOTrade(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		ModifyAMOTrade modifyAMOTradeobj = new ModifyAMOTrade();
		modifyAMOTradeobj.modifyAMOTrade(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the invalid modify amo trade.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the invalid modify amo trade
	 * @throws Throwable the throwable
	 */
	public void getInvalidModifyAmoTrade(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		ModifyAMOTrade modifyAMOTradeobj = new ModifyAMOTrade();
		modifyAMOTradeobj.getInvalidModifyAmoTrade(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Cancel order.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void cancelOrder(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		CancelOrder cancelOrderobj = new CancelOrder();
		cancelOrderobj.cancelOrder(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the invalid cancel order.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the invalid cancel order
	 * @throws Throwable the throwable
	 */
	public void getInvalidCancelOrder(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		CancelOrder cancelOrderobj = new CancelOrder();
		cancelOrderobj.getInvalidCancelOrder(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Cancel AMO trade.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void cancelAMOTrade(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		CancelAMOTrade cancelAMOTradeobj = new CancelAMOTrade();
		cancelAMOTradeobj.cancelAMOTrade(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the invalid cancel AMO trade.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the invalid cancel AMO trade
	 * @throws Throwable the throwable
	 */
	public void getInvalidCancelAMOTrade(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		CancelAMOTrade cancelAMOTradeobj = new CancelAMOTrade();
		cancelAMOTradeobj.getInvalidCancelAMOTrade(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Modify order.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void modifyOrder(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		ModifyOrder modifyOrderobj = new ModifyOrder();
		modifyOrderobj.modifyOrder(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the invalid modify order.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the invalid modify order
	 * @throws Throwable the throwable
	 */
	public void getInvalidModifyOrder(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		ModifyOrder modifyOrderobj = new ModifyOrder();
		modifyOrderobj.getInvalidModifyOrder(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the index chart.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the index chart
	 * @throws Throwable the throwable
	 */
	public void getIndexChart(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		IndexChart indexChartobj = new IndexChart();
		indexChartobj.indexChart(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the index chart negative.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the index chart negative
	 * @throws Throwable the throwable
	 */
	public void getIndexChartNegative(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		IndexChart indexChartobj = new IndexChart();
		indexChartobj.getIndexChartNegative(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Intraday chart.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void intradayChart(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		IntradayChart intradayChartobj = new IntradayChart();
		intradayChartobj.intradayChart(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the intra day chart negative.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the intra day chart negative
	 * @throws Throwable the throwable
	 */
	public void getIntraDayChartNegative(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		IntradayChart intradayChartobj = new IntradayChart();
		intradayChartobj.getIntraDayChartNegative(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Invalid password for validate user.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidPasswordForValidateUser(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		ChangePassword changepasswordobj = new ChangePassword();
		changepasswordobj.changePasswordWithWrongData(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Universal search.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void universalSearch(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		UniversalSearch universalSearchobj = new UniversalSearch();
		universalSearchobj.universalSearchData(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the quotes.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the quotes
	 * @throws Throwable the throwable
	 */
	public void getQuotes(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		GetQuote getQuoteobj = new GetQuote();
		getQuoteobj.getQuoteData(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the quotes invalid.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the quotes invalid
	 * @throws Throwable the throwable
	 */
	public void getQuotesInvalid(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		GetQuote getQuoteobj = new GetQuote();
		getQuoteobj.getQuotesInvalid(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the holdings.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the holdings
	 * @throws Throwable the throwable
	 */
	public void getHoldings(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		GetHoldings getHoldingsobj = new GetHoldings();
		getHoldingsobj.getHolding(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Invalid holdings.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidHoldings(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		GetHoldings getHoldingsobj = new GetHoldings();
		getHoldingsobj.invalidHoldings(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the order history.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the order history
	 * @throws Throwable the throwable
	 */
	public void getOrderHistory(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		OrderHistory getOrderHistoryobj = new OrderHistory();
		getOrderHistoryobj.getOrderHistory(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Invalid order history.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidOrderHistory(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		OrderHistory getOrderHistoryobj = new OrderHistory();
		getOrderHistoryobj.invalidOrderHistory(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Invalid data place order.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidDataPlaceOrder(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		PlaceOrder placeOrderobj = new PlaceOrder();
		placeOrderobj.placeOrderForInvalidData(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the square off.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the square off
	 * @throws Throwable the throwable
	 */
	public void getSquareOff(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		GetSquareOff getSquareOffobj = new GetSquareOff();
		getSquareOffobj.getSquareOff(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the day positions.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the day positions
	 * @throws Throwable the throwable
	 */
	public void getDayPositions(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		GetDayPositions getDayPositionsobj = new GetDayPositions();
		getDayPositionsobj.getDayPosition(workbook, sheetName, excelOperation, scenarioID);
	}

	/**
	 * Invalid get square off.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidGetSquareOff(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		GetSquareOff getSquareOffobj = new GetSquareOff();
		getSquareOffobj.invalidGetSquareOff(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Invalid get day positions.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidGetDayPositions(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		GetDayPositions getDayPositionsobj = new GetDayPositions();
		getDayPositionsobj.invalidGetDayPositions(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the alert history.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the alert history
	 * @throws Throwable the throwable
	 */
	public void getAlertHistory(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		AlertHistory getAlertHistoryobj = new AlertHistory();
		getAlertHistoryobj.getAlertHistory(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Invalid get alert history.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidGetAlertHistory(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		AlertHistory getAlertHistoryobj = new AlertHistory();
		getAlertHistoryobj.invalidGetAlertHistory(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the ticker bar.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the ticker bar
	 * @throws Throwable the throwable
	 */
	public void getTickerBar(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		GetTickerBar getTickerBarobj = new GetTickerBar();
		getTickerBarobj.getTickerBar(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the ticker bar negative.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the ticker bar negative
	 * @throws Throwable the throwable
	 */
	public void getTickerBarNegative(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		GetTickerBar getTickerBarobj = new GetTickerBar();
		getTickerBarobj.getTickerBarNegative(workbook, sheetName, excelOperation, scenarioID);
	}	
	
	/**
	 * Gets the universal search with exchange.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the universal search with exchange
	 * @throws Throwable the throwable
	 */
	public void getUniversalSearchWithExchange(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		UniversalSearchWithExchange getUniversalSearchWithExchangesobj = new UniversalSearchWithExchange();
		getUniversalSearchWithExchangesobj.getUniversalSearchWithExchange(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the place cover trade.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the place cover trade
	 * @throws Throwable the throwable
	 */
	public void getPlaceCoverTrade(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		PlaceCoverOrder getPlaceCoverTradeobj = new PlaceCoverOrder();
		getPlaceCoverTradeobj.getPlaceCoverTrade(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the invalid place cover trade.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the invalid place cover trade
	 * @throws Throwable the throwable
	 */
	public void getInvalidPlaceCoverTrade(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		PlaceCoverOrder getPlaceCoverTradeobj = new PlaceCoverOrder();
		getPlaceCoverTradeobj.getInvalidPlaceCoverTrade(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the exit cover trade.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the exit cover trade
	 * @throws Throwable the throwable
	 */
	public void getExitCoverTrade(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		ExitCoverOrder getExitCoverTradeobj = new ExitCoverOrder();
		getExitCoverTradeobj.getExitCoverTrade(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Invalid exit cover trade.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidExitCoverTrade(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		ExitCoverOrder getExitCoverTradeobj = new ExitCoverOrder();
		getExitCoverTradeobj.invalidExitCoverTrade(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the now default symbols.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @return the now default symbols
	 * @throws Throwable the throwable
	 */
	public void getNowDefaultSymbols(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		NowDefaultSymbols getNowDefaultSymbolsobj = new NowDefaultSymbols();
		getNowDefaultSymbolsobj.getNowDefaultSymbols(workbook, sheetName, excelOperation, scenarioID);
	}

	/**
	 * Invalid get last traded symbols.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidGetLastTradedSymbols(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetLastTradedSymbols getLastTradedSymbolsobj = new GetLastTradedSymbols();
		getLastTradedSymbolsobj.invalidGetLastTradedSymbols(workbook, sheetName, excelOperation, scenarioID);
	}

	/**
	 * Invalid universal search with exchange.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidUniversalSearchWithExchange(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		UniversalSearchWithExchange getUniversalSearchWithExchangesobj = new UniversalSearchWithExchange();
		getUniversalSearchWithExchangesobj.invalidUniversalSearchWithExchange(workbook, sheetName, excelOperation, scenarioID);
	}

	/**
	 * Invalid universal search data.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void invalidUniversalSearchData(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		UniversalSearch universalSearchobj = new UniversalSearch();
		universalSearchobj.invalidUniversalSearchData(workbook, sheetName, excelOperation, scenarioID);
	}
	
	
	
	/**
	 * Future claculator.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void futureClaculator(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		FutureCalculator futureCalculator = new FutureCalculator();
		futureCalculator.futurecalculator(workbook, sheetName, excelOperation, scenarioID);
	}

	
	/**
	 * Place equity SIP order.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void placeEquitySIPOrder(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		PlaceEquitySIPOrder placeEquitySIPOrder = new PlaceEquitySIPOrder();
		placeEquitySIPOrder.placeEquitySIPOrder(workbook, sheetName, excelOperation, scenarioID);
	}
	//Added By Suraj
	/**
	 * Getcancel SIP order.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	//GetcancelSIPOrder
	public void GetcancelSIPOrder(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		CancelSIPOrder cancelSIPOrder = new CancelSIPOrder();
		cancelSIPOrder.GetcancelSIPOrder(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Getsip search.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void GetsipSearch(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		SipSearch sipSearch = new SipSearch();
		sipSearch.GetSipSearch(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the sip order book detail.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void GetSipOrderBookDetail(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		GetSipOrderBookDetails getSipOrderBookDetails = new GetSipOrderBookDetails();
		getSipOrderBookDetails.GetSipOrderoftheDetail(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Place spread order.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void placeSpreadOrder(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		PlaceSpreadOrder placeSpreadOrder = new PlaceSpreadOrder();
		placeSpreadOrder.placeSpreadOrder(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the payment transaction history generator.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void GetPaymentTransactionHistoryGenerator(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		PaymentTransactionHistoryGenerator paymentTransactionHistoryGenerator = new PaymentTransactionHistoryGenerator();
		paymentTransactionHistoryGenerator.GetPaymentTransactionHistoryGenerator(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Position conversion.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void positionConversion(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		PositionConversion positioConversion = new PositionConversion();
		positioConversion.positionofthecalculator(workbook , sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Cancel spread order.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void cancelSpreadOrder(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		CancelSpreadOrder cancelSpreadOrder = new CancelSpreadOrder();
		cancelSpreadOrder.cancelSpreadOrder(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Modify spread order.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void modifySpreadOrder(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		ModifySpreadOrder modifySpreadOrder = new ModifySpreadOrder();
		modifySpreadOrder.modifySpreadOrder(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Paymentgateway for U rlgenrator.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	//paymentGatewayStatusGenerator
	public void PaymentgatewayForURlgenrator(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		PaymentGatewayURLGenerator paymentGatewayURLGenerator = new PaymentGatewayURLGenerator();
		paymentGatewayURLGenerator.PaymentgatewayforgeneratorDetail(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Getpayment gateway status generator.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void GetpaymentGatewayStatusGenerator(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		PaymentGatewayStatusGenerator paymentGatewayStatusGenerator = new PaymentGatewayStatusGenerator();
		paymentGatewayStatusGenerator.GetPaymentGatewayStatusGenerator(workbook, sheetName, excelOperation, scenarioID);
	}
	
	/**
	 * Gets the payment client details.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	//getPaymentClientDetails
	public void GetPaymentClientDetails(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		PaymentClientDetails paymentClientDetails = new PaymentClientDetails();
		paymentClientDetails.GetPaymentClientDetails(workbook, sheetName, excelOperation, scenarioID);
	}
	
	
	/**
	 * Optionalcalculatordetail.
	 *
	 * @param workbook the workbook
	 * @param sheetName the sheet name
	 * @param scenarioID the scenario ID
	 * @param excelOperation the excel operation
	 * @throws Throwable the throwable
	 */
	public void optionalcalculatordetail(XSSFWorkbook workbook,String sheetName,String scenarioID, ExcelOperation excelOperation) throws Throwable {
		//TODO Create object of Request then call method which contains request
		OptionCaculator optionCaculator = new OptionCaculator();
		optionCaculator.executeoptionCaculator(workbook, sheetName, excelOperation, scenarioID);
	}
	
	
}
