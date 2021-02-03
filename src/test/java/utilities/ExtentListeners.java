package utilities;

import java.util.Arrays;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ExtentListeners implements ITestListener {

	static Date d = new Date();
	static String fileName = "ExtentReport_" + d.toString().replace(":", "_").replace(" ", "_") + ".html";

	private static ExtentReports extent = ExtentManager.createInstance(".\\reports\\" + fileName);
	private static ExtentTest test;

	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName());
	}

	public void onTestSuccess(ITestResult result) {
		String testMethodName = result.getMethod().getMethodName();
		String logMsg = "<b>" + "TestName: " + testMethodName.toUpperCase() + "   PASSED" + "</b>";
		Markup m = MarkupHelper.createLabel(logMsg, ExtentColor.GREEN);
		test.pass(m);
	}

	public void onTestFailure(ITestResult result) {
		String testMethodName = result.getMethod().getMethodName();
		String logMsg = "<b>" + "TestName: " + testMethodName.toUpperCase() + "   FAILED" + "</b>";
		Markup m = MarkupHelper.createLabel(logMsg, ExtentColor.RED);
		test.fail(m);

		String exceptionMsg = Arrays.toString(result.getThrowable().getStackTrace());
		test.fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured:Click to view"
				+ "</font>" + "</b>" + "</summary>" + exceptionMsg.replaceAll(",", "<br>") + "</details>");

		/*
		 * try { ExtentManager.captureScreenshot(); test.fail("<b>" +
		 * "<font color=" + "red>" + "Screenshot of failure" + "</font>" +
		 * "</b>", MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.
		 * screenshotName) .build()); } catch (Exception e) { }
		 */
	}

	public void onTestSkipped(ITestResult result) {
		String testMethodName = result.getMethod().getMethodName();
		String logMsg = "<b>" + "TestName: " + testMethodName.toUpperCase() + "   SKIPPED" + "</b>";
		Markup m = MarkupHelper.createLabel(logMsg, ExtentColor.AMBER);
		test.skip(m);
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	public void onStart(ITestContext context) {

	}

	public void onFinish(ITestContext context) {
		if (extent != null)
			extent.flush();
	}

}
