package com.API.Utils;

import org.testng.IClass;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

// TODO: Auto-generated Javadoc
/**
 * The Class ListenerClass.
 */
public class ListenerClass extends TestListenerAdapter {
	
	/* (non-Javadoc)
	 * @see org.testng.TestListenerAdapter#onTestStart(org.testng.ITestResult)
	 */
	@Override
	public void onTestStart(ITestResult tr) {
		//Reporter.log("Test Started....");
	}

	/* (non-Javadoc)
	 * @see org.testng.TestListenerAdapter#onTestSuccess(org.testng.ITestResult)
	 */
	@Override
	public void onTestSuccess(ITestResult tr) {

		log("Test '" + tr.getName() + "' PASSED");

		// This will print the class name in which the method is present
	

		// This will print the priority of the method.
		// If the priority is not defined it will print the default priority as
		// 'o'
		log("Priority of this method is " + tr.getMethod().getPriority());

		System.out.println(".....");
	}

	/* (non-Javadoc)
	 * @see org.testng.TestListenerAdapter#onTestFailure(org.testng.ITestResult)
	 */
	@Override
	public void onTestFailure(ITestResult tr) {

		log("Test '" + tr.getName() + "' FAILED");
		log("Priority of this method is " + tr.getMethod().getPriority());
		System.out.println(".....");
	}

	/* (non-Javadoc)
	 * @see org.testng.TestListenerAdapter#onTestSkipped(org.testng.ITestResult)
	 */
	@Override
	public void onTestSkipped(ITestResult tr) {
		log("Test '" + tr.getName() + "' SKIPPED");
		System.out.println(".....");
	}

	/**
	 * Log.
	 *
	 * @param methodName the method name
	 */
	private void log(String methodName) {
		System.out.println(methodName);
	}

	/**
	 * Log.
	 *
	 * @param testClass the test class
	 */
	private void log(IClass testClass) {
		System.out.println(testClass);
	}
}
