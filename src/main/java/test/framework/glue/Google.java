package test.framework.glue;



import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import test.utils.CommonUtils;
import test.utils.Commons;
import test.utils.Wrapper;

public class Google {
	
	WebDriver driver;
	
	Wrapper wrap=new Wrapper();
	Commons com=new Commons();
	CommonUtils utils=new CommonUtils();
	
	public static  String driverpath = "C:\\Users\\vs00501395\\selenium-cucumber-java-volvo\\driver\\chromedriver.exe";
	
	public static String excelpath = "C:\\Users\\vs00501395\\selenium-cucumber-java-volvo\\src\\test\\resources\\exceldata";

	@And("^Invoke Browser$") 
	public void InvokeBrowser() {
		
		System.setProperty("webdriver.chrome.driver", driverpath);
		driver=new ChromeDriver();
	}
	
	@And("^Enter Google$") 
    public void EnterGoogle() {
		driver.get("http://www.google.com");
    	driver.manage().window().maximize();
		String expectedTitle = "Google";
        String actualTitle = ""; 
        
        actualTitle = driver.getTitle();
        System.out.println(actualTitle);
        if (actualTitle.contentEquals(expectedTitle)){
            System.out.println("Test Passed!");
        } else {
            System.out.println("Test Failed");
        }
    }
	
	@And("^Enter in search bar$") 
    public void EnterSearchBar() {
		
	driver.findElement(By.xpath("//input[@title='Search']")).sendKeys("Hello");
	driver.findElement(By.xpath("//input[@type='submit']")).click();
    	
}
	@And("^Verify the resultant page title$") 
	public void Verifytheresultantpage() {
		String expectedTitle = "Hello - Google Search";
        String actualTitle = ""; 
        
        actualTitle = driver.getTitle();
        System.out.println(actualTitle);
        if (actualTitle.contentEquals(expectedTitle)){
            System.out.println("Test Passed!");
        } else {
            System.out.println("Test Failed");
        }
	
}
	@And("^Click on the image tab$") 
	public void Clickonimagetab() throws IOException, InterruptedException {
		
		wrap.getElement(driver, com.getElementProperties("google", "moretab")).click();
		wrap.click(driver, com.getElementProperties("google", "Bookmenuitem"));
	
	}
	@And("^Clear Search Tab '(.*)'$") 
	public void ClearSearchTab(int rowcount) throws IOException, InterruptedException {
		
		for(int i=0;i<=rowcount;i++) {
		
		wrap.clear(driver, com.getElementProperties("google", "Search"));
		utils.convertExcelToMap(excelpath, "TestData.xls", "Sheet1");
		
		String username = utils.readColumn("input", i);
		
		driver.findElement(By.xpath("//input[@title='Search']")).sendKeys(username);
		
	}
	}
}