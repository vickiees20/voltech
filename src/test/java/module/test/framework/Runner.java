package module.test.framework;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.ExtentProperties;
import com.cucumber.listener.Reporter;



@RunWith(Cucumber.class)
@CucumberOptions( 
        features = {"src/test/resources/features"},
        glue = {"test.framework.glue"},
        tags = {"@Googlelogin"},
        plugin = {"com.cucumber.listener.ExtentCucumberFormatter:output/report.html"}

)
public class Runner {
	
	@BeforeClass
	public static void setup() {
	    ExtentProperties extentProperties = ExtentProperties.INSTANCE;
	    extentProperties.setReportPath("output/myreport.html");
	}
	
	@AfterClass
    public static void teardown() {
        Reporter.loadXMLConfig(new File("src/main/resources/config/extent-config.xml"));
        Reporter.setSystemInfo("user", System.getProperty("user.name"));
        //Reporter.setSystemInfo("os", "Mac OSX");
        Reporter.setTestRunnerOutput("Sample test runner output message");
    }
	
	
	


}
