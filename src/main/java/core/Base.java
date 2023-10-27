package core;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import helper.BaseHelper;
import utils.ExtentReport;

public class Base
{
	public static ExtentReports extentreport =null;
	public static ExtentTest extenttest ;
	@BeforeSuite(alwaysRun=true)
	public void generatereport()
	{
		//-----------Define path for extent report and it is override as per boolean flag mentioned true is override
		extentreport = new ExtentReports(System.getProperty("user.dir")+"\\"+"advancereport.html",true); 
				System.out.println("Extent Report Started");	
		/*String subfolderpath = System.getProperty(("user.dir")+"//Reports//"+BaseHelper.Timestamp());
		BaseHelper.CreateFolder(subfolderpath);
		ExtentReport.intialize(subfolderpath+"//"+"API_AUTOMATION.html");*/
	}
	@AfterMethod(alwaysRun=true)
	public void executeCodeAfterEveryTestMethod(ITestResult result)
	{
		if(result.getStatus()==ITestResult.SUCCESS)
		{
			extenttest.log(LogStatus.PASS, "Test Case :"+result.getMethod().getMethodName()+"Is Pass");
		}
		else if(result.getStatus()==ITestResult.FAILURE)
		{
			extenttest.log(LogStatus.FAIL, "Test Case :"+result.getMethod().getMethodName()+"Is Failed");
			extenttest.log(LogStatus.FAIL, "Test Case is Failed due tp :"+result.getThrowable());
		}
		else if(result.getStatus()==ITestResult.SKIP)
		{
			extenttest.log(LogStatus.SKIP, "Test Case :"+result.getMethod().getMethodName()+"Is SKIPED");
		}
			extentreport.endTest(extenttest);
	}
	
	@AfterSuite(alwaysRun=true)
	public void endreport()
	{
		extentreport.flush();
		extentreport.close();
	}
}
