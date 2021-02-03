package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports createInstance(String fileName) {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(fileName);
        sparkReporter.config().setEncoding("utf-8");
        sparkReporter.config().setDocumentTitle("Test Execution Report");
        sparkReporter.config().setReportName("Run Report");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("API Test Suite", "VideoGame API");
        extent.setSystemInfo("Application Name", "VideoGane");
        extent.setSystemInfo("Platform", "Win 10");
        return extent;
    }
}
