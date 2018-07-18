package test.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
//import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;


import com.google.common.base.Function;


public class Wrapper {

    private static Logger logger = Logger.getLogger(Wrapper.class);
    
    
    public static WebDriver driver;
    //public static RemoteWebDriver driver;
    static Commons com = new Commons();
    static Wrapper wrap = new Wrapper();
    public static WebDriverWait wait;
    long starttime, endTime, totalTime;

    //private SeleniumService service;
    //public WebDriver driver;
    //======universal webdriver since declared public  in Commons class========================================

    /**
     * Perform Click operation
     *
     * @param attribute= passing operation
     * @throws InterruptedException
     */


    public void click(WebDriver driver, final String attribute) throws InterruptedException {
        // Disable implicitWait to resolve some SSL connectivity issue. Some object being killed too fast; causing webdriver to keep retrying on the page
        driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);

        final Wait<WebDriver> waitExt = new FluentWait<WebDriver>(driver)
                .withTimeout(60, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotVisibleException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(WebDriverException.class);

        logger.info("Going to Click on the  [" + attribute + "] Field");

        try {
           // SeleniumScreenshotUtil.takeScreenshot(BaseProject.driver, BaseProject.genieScenario);
            WebElement elem = waitExt.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    //WebElement element = getExactAttribute(driver, attribute);
                    WebElement element = waitExt.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(getExactAttributeBY(attribute))));
                    element.click();
                    return element;
                }
            });

            logger.info("Clicked on the  [" + attribute + "] Field");
            // Enable implicitWait back for general page loading time wait
            driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("[Exception caught! Unable to click on the  [" + attribute + "] Field");
            e.printStackTrace();
            Assert.fail();

        }
    }

    public void rtSubmit(WebDriver driver, final String attribute) throws InterruptedException {
        // Disable implicitWait to resolve some SSL connectivity issue. Some object being killed too fast; causing webdriver to keep retrying on the page
        driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);

        final Wait<WebDriver> waitExt = new FluentWait<WebDriver>(driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(2, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotVisibleException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(WebDriverException.class);

        logger.info("Going to rtSubmit on the  [" + attribute + "] Field");

        try {
           
            WebElement elem = waitExt.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    //WebElement element = getExactAttribute(driver, attribute);
                    WebElement element = waitExt.until(ExpectedConditions.elementToBeClickable(getExactAttributeBY(attribute)));
                    // Adding a slight interval help increase consistent submission
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Assert.fail();
                    }
                    element.click();
                    logger.info("Click instruction sent");
                    return element;
                }
            });
            

            logger.info("rtSubmited on the  [" + attribute + "] Field");
            // Enable implicitWait back for general page loading time wait
            driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("[Exception caught! Unable to rtSubmit on the  [" + attribute + "] Field");
            e.printStackTrace();
            Assert.fail();

        }
    }

    public void fieldClick(WebDriver driver, final String attribute) throws InterruptedException {
        // Disable implicitWait to resolve some SSL connectivity issue. Some object being killed too fast; causing webdriver to keep retrying on the page
        driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);
        //because of the application issue of reloading the form HTML on each form element interaction
        final Wait<WebDriver> waitExt = new FluentWait<WebDriver>(driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(2, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotVisibleException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(WebDriverException.class);
        logger.info("Going to Field Click on the  [" + attribute + "] Field");

        try {
           ;
            WebElement elem = waitExt.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                   // WebElement element = getElement(driver, attribute);
                    WebElement element = waitExt.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(getExactAttributeBY(attribute))));
                    element.click();
                    return element;
                }
            });


            logger.info("Field Clicked on the  [" + attribute + "] Field");
            // Enable implicitWait back for general page loading time wait
            driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
        } catch (StaleElementReferenceException e) {
            logger.error("[Exception caught! Unable to click on the  [" + attribute + "] Field");
            e.printStackTrace();
            Assert.fail();
        }
    }

    public void setAttributeValue(WebDriver driver, WebElement elem, String attributeName, String value) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        String scriptSetAttrValue = "arguments[0].setAttribute(arguments[1],arguments[2])";

        js.executeScript(scriptSetAttrValue, elem, attributeName, value);

    }

/*    public void changeValue(WebDriver driver, String value, String Inattribute) throws InterruptedException {
        try {
          //  driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);
            logger.info("The Value [" + value + "] going to Enter into the Field [" + Inattribute + "]");
            WebElement elem = getExactAttribute(driver, Inattribute);
            new WebDriverWait(driver, 60, 1000).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(elem));

            setAttributeValue(driver, elem, "value", value);

            logger.info("Value [" + value + "] Entered Successfully");
            driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
        } catch (StaleElementReferenceException e) {
            Thread.sleep(2000);
           // driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);
            WebElement elem = getExactAttribute(driver, Inattribute);
            setAttributeValue(driver, elem, "value", value);
            logger.info("Value [" + value + "] Entered Successfully with a StaleElementReferenceException");
           // driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }*/

    public void type(WebDriver driver, final String value, final String Inattribute) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);
        final Wait<WebDriver> waitExt = new FluentWait<WebDriver>(driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(InvalidElementStateException.class)
                .ignoring(NullPointerException.class)
                .ignoring(WebDriverException.class);


        logger.info("type:The Value [" + value + "] going to Enter into the Field [" + Inattribute + "]");
        try {
            final int retryLimit = 3;
            boolean verifiedPass = false;
            String finalAttributeValue = null;
            for (int retryCnt=0; retryCnt < retryLimit; retryCnt++)
            {
                WebElement elem = waitExt.until(new Function<WebDriver, WebElement>() {
                    public WebElement apply(WebDriver driver) {
                        waitExt.until(ExpectedConditions.elementToBeClickable(getExactAttributeBY(Inattribute))).click();
                        //  WebElement element = getElementWithoutAssertion(driver, Inattribute);
                      //  element.click();
                        logger.info("type:clear");
                        //String tmpValue = waitExt.until(ExpectedConditions.elementToBeClickable(getExactAttributeBY(Inattribute))).getAttribute("value");
                        logger.info("type:tmpValue=" + waitExt.until(ExpectedConditions.elementToBeClickable(getExactAttributeBY(Inattribute))).getAttribute("value"));
                        waitExt.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(getExactAttributeBY(Inattribute)))).clear();
                        String tmpValue2 = waitExt.until(ExpectedConditions.elementToBeClickable(getExactAttributeBY(Inattribute))).getAttribute("value");
                        logger.info("type:tmpValue2=" + tmpValue2);
                        waitExt.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(getExactAttributeBY(Inattribute)))).click();
                        for (int charCnt = 0; charCnt < tmpValue2.length(); charCnt++) {
                            waitExt.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(getExactAttributeBY(Inattribute)))).sendKeys(Keys.BACK_SPACE);
                            String tmpValue3 =  waitExt.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(getExactAttributeBY(Inattribute)))).getAttribute("value");
                            logger.info("type:tmpValue3=" + tmpValue3);
                        }

                        logger.info("type:cleared");
                        waitExt.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(getExactAttributeBY(Inattribute)))).sendKeys(value);
                        logger.info("type:wait completed 1:" + waitExt.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(getExactAttributeBY(Inattribute)))).getAttribute("value").trim());
                      //  element.sendKeys(value);
                      //  logger.info("type:wait completed 2");
                        waitExt.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(getExactAttributeBY(Inattribute)))).sendKeys(Keys.TAB);
                        //element.sendKeys(Keys.TAB);
                        WebElement element =waitExt.until(ExpectedConditions.elementToBeClickable(getExactAttributeBY(Inattribute)));
                        logger.info("type:Sent TAB:" + element.getAttribute("value").trim());
                        return element;
                    }
                });
                logger.info("Verification started..");
                //finalAttributeValue = elem.getAttribute("value").trim();
//                finalAttributeValue = waitExt.until(ExpectedConditions.visibilityOfElementLocated(getExactAttributeBY(Inattribute))).getAttribute("value");
                WebElement element1 = waitExt.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(getExactAttributeBY(Inattribute))));
                finalAttributeValue = element1.getAttribute("value").trim();

                logger.info("Obtained filled value..");
                if (value.trim().equalsIgnoreCase(finalAttributeValue) || value.trim().contains(finalAttributeValue)) {
                    logger.info("Value [" + value + "] Entered and verified Successfully");
                    verifiedPass = true;
                    break;
                }
                else {
                    int logicalRetryCnt = retryCnt + 1;
                    logger.info("Retrying [" + String.valueOf(logicalRetryCnt) + "/" + String.valueOf(retryLimit) + "] because entered [" + value + "] but final value in field is [" + finalAttributeValue + "]");
                    verifiedPass = false;
                   // Assert.fail();
                }
            }
            driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
            if (verifiedPass == false){
                logger.error("Entered [" + value + "] but final value in field is [" + finalAttributeValue + "]");
            //    Assert.fail();
            }



        } catch(StaleElementReferenceException e){
            logger.info("Stale element exception caught");
            wrap.wait(2000);
            //Relocate element
            WebElement element = driver.findElement(getExactAttributeBY(Inattribute));
            //Validation
            String finalAttribute = element.getAttribute("value").trim();
            if (finalAttribute.equalsIgnoreCase(value.trim())) {
                logger.info("Type successful");
            }else {
                logger.info("Final value: " + finalAttribute + " does not match with intended value: " + value);
            //    Assert.fail();
            }
        } catch (Exception e) {
            logger.error("Failed entering Value [" + value + "]");
            e.printStackTrace();
         //   Assert.fail();
        }


    }

    public void typeToTextBox(WebDriver driver, String value, String Inattribute) throws InterruptedException {
        try {
            logger.info("typeToTextBox:The Value [" + value + "] going to Enter into the Field [" + Inattribute + "]");
            WebElement elem = getExactAttribute(driver, Inattribute);
            new WebDriverWait(driver, 60, 1000).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf(elem));
			elem.click();
            elem.clear();
            elem.sendKeys(value);
            logger.info("Value [" + value + "]Entered Successfully");
        } catch (Exception e) {
            logger.error("Failed entering Value [" + value + "]");
            e.printStackTrace();
        //    Assert.fail();
        }
    }


    public java.util.List<WebElement> getElements(WebDriver driver, String Attribute) {///list of elements
        return getExactAttributes(driver, Attribute);
    }

    public WebElement getElement(WebDriver driver, String Attribute) {
        return getExactAttribute(driver, Attribute);
    }
    public WebElement getElementWithoutAssertion(WebDriver driver, String Attribute) {
        return getExactAttributeWithoutAssertion(driver, Attribute);
    }

    public void clear(WebDriver driver, String Attribute) {
        try {
            getExactAttribute(driver, Attribute).clear();
        } catch (Exception e) {
            logger.error("Failed to clear Attribute:" + Attribute);
            e.printStackTrace();
            Assert.fail();
        }

    }

    public By getExactAttributeBY(String Attribute) {
        try {
            String attri = Attribute.replace("\"", "").trim();
            if (attri.contains("xpath")) {
                return getXpath(attri.substring("xpath=".length()));

            }

            if (attri.startsWith("//")) {
                return getXpath(attri);

            }
            
            if (attri.startsWith("(")) {
                return getXpath(attri);

            }

            if (attri.contains("css")) {
                return getCssSelector(attri.substring("css=".length()));

            }

            if (attri.contains("name")) {
                return getName(attri.substring("name=".length()));

            }

            if (attri.contains("id")) {
                return getId(attri.substring("id=".length()));

            }

            if (attri.contains("linkText")) {
                logger.info(attri.substring("linkText=".length()));
                String tes = attri.substring("linkText=".length());
                logger.info(tes);
                //driver1.findElement(By.linkText("\""+tes+"\"")).click();
                return getLinkText(tes);

            }
            if (attri.contains("className")) {
                return getClassName(attri.substring("className=".length()));

            }
            if (attri.contains("tagName")) {
                return getTagName(attri.substring("tagName=".length()));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    ////=========================returns single web element==============================================

    public WebElement getExactAttribute(WebDriver driver, String Attribute) {
        try {
            String attri = Attribute.replace("\"", "").trim();
            if (attri.contains("xpath")) {
                return driver.findElement(getXpath(attri.substring("xpath=".length())));

            }

            if (attri.startsWith("//")) {
                return driver.findElement(getXpath(attri));

            }
            
            if (attri.startsWith("(")) {
                return driver.findElement(getXpath(attri));

            }

            if (attri.contains("css")) {
                return driver.findElement(getCssSelector(attri.substring("css=".length())));

            }

            if (attri.contains("name")) {
                return driver.findElement(getName(attri.substring("name=".length())));

            }

            if (attri.contains("id")) {
                return driver.findElement(getId(attri.substring("id=".length())));

            }

            if (attri.contains("linkText")) {
                logger.info(attri.substring("linkText=".length()));
                String tes = attri.substring("linkText=".length());
                logger.info(tes);
                //driver1.findElement(By.linkText("\""+tes+"\"")).click();
                return driver.findElement(getLinkText(tes));

            }
            if (attri.contains("className")) {
                return driver.findElement(getClassName(attri.substring("className=".length())));

            }
            if (attri.contains("tagName")) {
                return driver.findElement(getTagName(attri.substring("tagName=".length())));

            }
        } catch (Exception e) {
                logger.info("Failed to get Attribute:" + Attribute);
            //    e.printStackTrace();
            //    Assert.fail();
        }
        return null;
    }

    public WebElement getExactAttributeWithoutAssertion(WebDriver driver, String Attribute) {
        try {
            String attri = Attribute.replace("\"", "").trim();
            if (attri.contains("xpath")) {
                return driver.findElement(getXpath(attri.substring("xpath=".length())));

            }

            if (attri.startsWith("//")) {
                return driver.findElement(getXpath(attri));

            }

            if (attri.contains("css")) {
                return driver.findElement(getCssSelector(attri.substring("css=".length())));

            }

            if (attri.contains("name")) {
                return driver.findElement(getName(attri.substring("name=".length())));

            }

            if (attri.contains("id")) {
                return driver.findElement(getId(attri.substring("id=".length())));

            }

            if (attri.contains("linkText")) {
                logger.info(attri.substring("linkText=".length()));
                String tes = attri.substring("linkText=".length());
                logger.info(tes);
                //driver1.findElement(By.linkText("\""+tes+"\"")).click();
                return driver.findElement(getLinkText(tes));

            }
            if (attri.contains("className")) {
                return driver.findElement(getClassName(attri.substring("className=".length())));

            }
            if (attri.contains("tagName")) {
                return driver.findElement(getTagName(attri.substring("tagName=".length())));

            }
        } catch (Exception e) {
            logger.info("Failed to get Attribute:" + Attribute);
           // e.printStackTrace();
            //    Assert.fail();
        }
        return null;
    }

    ////============================returns a list of webElements==========================================

    public java.util.List<WebElement> getExactAttributesWithoutAssertion(WebDriver driver, String Attribute) {
        try {
            String attri = Attribute.replace("\"", "").trim();//data retrieved with quotes are removed
            if (attri.contains("xpath")) {
                return driver.findElements(getXpath(attri.substring("xpath=".length())));

            }
            if (attri.startsWith("//")) {
                return driver.findElements(getXpath(attri));

            }

            if (attri.contains("css")) {
                return driver.findElements(getCssSelector(attri.substring("css=".length())));

            }

            if (attri.contains("name")) {
                return driver.findElements(getName(attri.substring("name=".length())));

            }

            if (attri.contains("id")) {
                return driver.findElements(getId(attri.substring("id=".length())));

            }

            if (attri.contains("linkText")) {
                logger.info(attri.substring("linkText=".length()));
                String tes = attri.substring("linkText=".length());
                logger.info(tes);
                //driver1.findElement(By.linkText("\""+tes+"\"")).click();
                return driver.findElements(getLinkText(tes));

            }
            if (attri.contains("className")) {
                return driver.findElements(getClassName(attri.substring("className=".length())));

            }
            if (attri.contains("tagName")) {
                return driver.findElements(getTagName(attri.substring("tagName=".length())));

            }
        } catch (Exception e) {
            logger.error("Failed to get Attribute:" + Attribute);
        }
        return null;
    }

    public java.util.List<WebElement> getExactAttributes(WebDriver driver, String Attribute) {
        try {
            String attri = Attribute.replace("\"", "").trim();//data retrieved with quotes are removed
            if (attri.contains("xpath")) {
                return driver.findElements(getXpath(attri.substring("xpath=".length())));

            }
            if (attri.startsWith("//")) {
                return driver.findElements(getXpath(attri));

            }

            if (attri.contains("css")) {
                return driver.findElements(getCssSelector(attri.substring("css=".length())));

            }

            if (attri.contains("name")) {
                return driver.findElements(getName(attri.substring("name=".length())));

            }

            if (attri.contains("id")) {
                return driver.findElements(getId(attri.substring("id=".length())));

            }

            if (attri.contains("linkText")) {
                logger.info(attri.substring("linkText=".length()));
                String tes = attri.substring("linkText=".length());
                logger.info(tes);
                //driver1.findElement(By.linkText("\""+tes+"\"")).click();
                return driver.findElements(getLinkText(tes));

            }
            if (attri.contains("className")) {
                return driver.findElements(getClassName(attri.substring("className=".length())));

            }
            if (attri.contains("tagName")) {
                return driver.findElements(getTagName(attri.substring("tagName=".length())));

            }
        } catch (Exception e) {
            logger.error("Failed to get Attribute:" + Attribute);
            e.printStackTrace();
            Assert.fail();
        }
        return null;
    }

    ///==========================returns by type=====================================================

    public By getXpath(String Attribute) {
        return By.xpath(Attribute);

    }

    public By getCssSelector(String Attribute) {
        return By.cssSelector(Attribute);

    }

    public By getName(String Attribute) {
        return By.name(Attribute);

    }

    public By getId(String Attribute) {
        return By.id(Attribute);

    }

    public By getLinkText(String Attribute) {
        logger.info(Attribute);
        return By.linkText(Attribute);

    }

    public By getClassName(String Attribute) {
        return By.className(Attribute);

    }

    public By getTagName(String Attribute) {
        return By.tagName(Attribute);

    }

    public void waitForElementVisibility(WebDriver driver,WebElement elem) {
        new WebDriverWait(driver, 60, 1000).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(elem));
        return;
    }
    ////========================================================================================

    public void waitForElementVisibility(WebDriver driver, String attribute, int time) {

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(InvalidElementStateException.class);

        try {

            String attri = attribute.replace("\"", "").trim();//data retrieved with quotes are removed
            if (attri.contains("xpath=")) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(attri.substring("xpath=".length()))));

            }
            if (attri.contains("//")) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(attri)));

            }
            if (attri.contains("id=")) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(attri.substring("id=".length()))));

            }
            if (attri.contains("css=")) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(attri.substring("css=".length()))));

            }
            if (attri.contains("name=")) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(attri.substring("name=".length()))));

            }
            if (attri.contains("linkText=")) {
                wait.until(ExpectedConditions.elementToBeClickable(By.linkText(attri.substring("linkText=".length()))));

            }


            if (attri.contains("partialLinkText=")) {
                wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(attri.substring("partialLinkText=".length()))));

            }
            if (attri.contains("tagName=")) {
                wait.until(ExpectedConditions.elementToBeClickable(By.tagName(attri.substring("tagName=".length()))));

            }
        } catch (Exception e) {
            logger.error("Failed to wait for element visibility:" + attribute);
            e.printStackTrace();
            Assert.fail();
        }

    }


	/*public void waitForElementPresent(WebDriver driver,String attribute,int time, String type)
    {
	try{	Long waitTime=(long)time;
		WebDriverWait wait = new WebDriverWait(driver,waitTime);
		String attri=attribute.replace("\"", "").trim();//data retrieved with quotes are removed
		switch(type){
		case "visible":

		if (attri.contains("xpath=")){
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(attri.substring("xpath=".length()))));

		}
		if (attri.contains("//")){
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(attri)));

		}
		if (attri.contains("id=")){
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(attri.substring("id=".length()))));

		}
		if (attri.contains("css=")){
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(attri.substring("css=".length()))));

		}
		if (attri.contains("name=")){
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(attri.substring("name=".length()))));

		}
		if (attri.contains("linkText=")){
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(attri.substring("linkText=".length()))));

		}


		if (attri.contains("partialLinkText=")){
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(attri.substring("partialLinkText=".length()))));

		}
		if (attri.contains("tagName=")){
			wait.until(ExpectedConditions.elementToBeClickable(By.tagName(attri.substring("tagName=".length()))));

		}

		break;
		case "click":

			if (attri.contains("xpath=")){
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(attri.substring("xpath=".length()))));

			}
			if (attri.contains("//")){
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(attri)));

			}
			if (attri.contains("id=")){
				wait.until(ExpectedConditions.elementToBeClickable(By.id(attri.substring("id=".length()))));

			}
			if (attri.contains("css=")){
				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(attri.substring("css=".length()))));

			}
			if (attri.contains("name=")){
				wait.until(ExpectedConditions.elementToBeClickable(By.name(attri.substring("name=".length()))));

			}
			if (attri.contains("linkText=")){
				wait.until(ExpectedConditions.elementToBeClickable(By.linkText(attri.substring("linkText=".length()))));

			}


			if (attri.contains("partialLinkText=")){
				wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(attri.substring("partialLinkText=".length()))));

			}
			if (attri.contains("tagName=")){
				wait.until(ExpectedConditions.elementToBeClickable(By.tagName(attri.substring("tagName=".length()))));

			}

		break;
		case "select":
			if (attri.contains("xpath=")){

				wait.until(ExpectedConditions.elementToBeSelected(By.xpath(attri.substring("xpath=".length()))));
			}
			if (attri.contains("//")){
				wait.until(ExpectedConditions.elementToBeSelected(By.xpath(attri)));

			}
			if (attri.contains("id=")){
				wait.until(ExpectedConditions.elementToBeSelected(By.id(attri.substring("id=".length()))));

			}
			if (attri.contains("css=")){
				wait.until(ExpectedConditions.elementToBeSelected(By.cssSelector(attri.substring("css=".length()))));

			}
			if (attri.contains("name=")){
				wait.until(ExpectedConditions.elementToBeSelected(By.name(attri.substring("name=".length()))));

			}
			if (attri.contains("linkText=")){
				wait.until(ExpectedConditions.elementToBeSelected(By.linkText(attri.substring("linkText=".length()))));

			}


			if (attri.contains("partialLinkText=")){
				wait.until(ExpectedConditions.elementToBeSelected(By.partialLinkText(attri.substring("partialLinkText=".length()))));

			}
			if (attri.contains("tagName=")){
				wait.until(ExpectedConditions.elementToBeSelected(By.tagName(attri.substring("tagName=".length()))));

			}

			break;
		case "allElements":
			if (attri.contains("xpath=")){

				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(attri.substring("xpath=".length()))));
			}
			if (attri.contains("//")){
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(attri)));

			}
			if (attri.contains("id=")){
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(attri.substring("id=".length()))));

			}
			if (attri.contains("css=")){
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(attri.substring("css=".length()))));

			}
			if (attri.contains("name=")){
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name(attri.substring("name=".length()))));

			}
			if (attri.contains("linkText=")){
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.linkText(attri.substring("linkText=".length()))));

			}


			if (attri.contains("partialLinkText=")){
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.partialLinkText(attri.substring("partialLinkText=".length()))));

			}
			if (attri.contains("tagName=")){
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName(attri.substring("tagName=".length()))));

			}
			break;

		case "present":
			if (attri.contains("xpath=")){

				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(attri.substring("xpath=".length()))));
			}
			if (attri.contains("//")){
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(attri)));

			}
			if (attri.contains("id=")){
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id(attri.substring("id=".length()))));

			}
			if (attri.contains("css=")){
				wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(attri.substring("css=".length()))));

			}
			if (attri.contains("name=")){
				wait.until(ExpectedConditions.presenceOfElementLocated(By.name(attri.substring("name=".length()))));

			}
			if (attri.contains("linkText=")){
				wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(attri.substring("linkText=".length()))));

			}


			if (attri.contains("partialLinkText=")){
				wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(attri.substring("partialLinkText=".length()))));

			}
			if (attri.contains("tagName=")){
				wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(attri.substring("tagName=".length()))));

			}
			break;

		case "alert":

			wait.until(ExpectedConditions.alertIsPresent());
			break;
		}
	}
	catch(Exception e)
	{
		//e.printStackTrace();
	}
	}*/

    public void waitForElement(WebDriver driver, String attribute, int time, String type) {
        try {
            Long waitTime = (long) time;
            WebDriverWait wait = new WebDriverWait(driver, waitTime);
            String attri = attribute.replace("\"", "").trim();//data retrieved with quotes are removed
            switch (type) {

                case "present":

                    if (attri.contains("id=")) {
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(attri.substring("id=".length()))));
                    } else if (attri.contains("//")) {
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(attri)));

                    } else if (attri.contains("xpath=")) {
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(attri.substring("xpath=".length()))));

                    } else if (attri.contains("tagName=")) {
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(attri.substring("tagName=".length()))));
                    } else if (attri.contains("name=")) {
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.name(attri.substring("name=".length()))));

                    } else if (attri.contains("linkText=")) {
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(attri.substring("linkText=".length()))));
                    } else if (attri.contains("partialLinkText=")) {
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(attri.substring("partialLinkText=".length()))));
                    } else if (attri.contains("css=")) {
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(attri.substring("css=".length()))));
                    }

                    break;

                case "visible":

                    if (attri.contains("id=")) {
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(attri.substring("id=".length()))));
                    } else if (attri.contains("//")) {
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(attri)));

                    } else if (attri.contains("xpath=")) {
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(attri.substring("xpath=".length()))));

                    } else if (attri.contains("tagName=")) {
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(attri.substring("tagName=".length()))));
                    } else if (attri.contains("name=")) {
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(attri.substring("name=".length()))));

                    } else if (attri.contains("linkText=")) {
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(attri.substring("linkText=".length()))));
                    } else if (attri.contains("partialLinkText=")) {
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(attri.substring("partialLinkText=".length()))));
                    } else if (attri.contains("css=")) {
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(attri.substring("css=".length()))));

                    }

                    break;

                case "click":

                    if (attri.contains("id=")) {
                        wait.until(ExpectedConditions.elementToBeClickable(By.id(attri.substring("id=".length()))));
                    } else if (attri.contains("//")) {
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(attri)));

                    } else if (attri.contains("xpath=")) {
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(attri.substring("xpath=".length()))));

                    } else if (attri.contains("tagName=")) {
                        wait.until(ExpectedConditions.elementToBeClickable(By.tagName(attri.substring("tagName=".length()))));
                    } else if (attri.contains("name=")) {
                        wait.until(ExpectedConditions.elementToBeClickable(By.name(attri.substring("name=".length()))));

                    } else if (attri.contains("linkText=")) {
                        wait.until(ExpectedConditions.elementToBeClickable(By.linkText(attri.substring("linkText=".length()))));
                    } else if (attri.contains("partialLinkText=")) {
                        wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(attri.substring("partialLinkText=".length()))));
                    } else if (attri.contains("css=")) {
                        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(attri.substring("css=".length()))));
                    }


                    break;

                case "select":

                    if (attri.contains("id=")) {
                        wait.until(ExpectedConditions.elementToBeSelected(By.id(attri.substring("id=".length()))));
                    } else if (attri.contains("//")) {
                        wait.until(ExpectedConditions.elementToBeSelected(By.xpath(attri)));

                    } else if (attri.contains("xpath=")) {
                        wait.until(ExpectedConditions.elementToBeSelected(By.xpath(attri.substring("xpath=".length()))));

                    } else if (attri.contains("tagName=")) {
                        wait.until(ExpectedConditions.elementToBeSelected(By.tagName(attri.substring("tagName=".length()))));
                    } else if (attri.contains("name=")) {
                        wait.until(ExpectedConditions.elementToBeSelected(By.name(attri.substring("name=".length()))));

                    } else if (attri.contains("linkText=")) {
                        wait.until(ExpectedConditions.elementToBeSelected(By.linkText(attri.substring("linkText=".length()))));
                    } else if (attri.contains("partialLinkText=")) {
                        wait.until(ExpectedConditions.elementToBeSelected(By.partialLinkText(attri.substring("partialLinkText=".length()))));
                    } else if (attri.contains("css=")) {
                        wait.until(ExpectedConditions.elementToBeSelected(By.cssSelector(attri.substring("css=".length()))));
                    }

                    break;

                case "allElements":

                    if (attri.contains("id=")) {
                        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(attri.substring("id=".length()))));
                    } else if (attri.contains("//")) {
                        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(attri)));

                    } else if (attri.contains("xpath=")) {
                        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(attri.substring("xpath=".length()))));

                    } else if (attri.contains("tagName=")) {
                        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName(attri.substring("tagName=".length()))));
                    } else if (attri.contains("name=")) {
                        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name(attri.substring("name=".length()))));

                    } else if (attri.contains("linkText=")) {
                        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.linkText(attri.substring("linkText=".length()))));
                    } else if (attri.contains("partialLinkText=")) {
                        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.partialLinkText(attri.substring("partialLinkText=".length()))));
                    } else if (attri.contains("css=")) {
                        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(attri.substring("css=".length()))));
                    }

                    break;

                case "alert":

                    wait.until(ExpectedConditions.alertIsPresent());

                    break;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("The element is incorrect, please verify respective properties file:" + attribute);
            e.printStackTrace();
            Assert.fail();
        }
    }

    public void waitForTextDisplay(WebDriver driver, String attribute, String data, int time, String type) {
        try {
            Long waitTime = (long) time;
            WebDriverWait wait = new WebDriverWait(driver, waitTime);
            String attri = attribute.replace("\"", "").trim();//data retrieved with quotes are removed

            switch (type) {
                case "ExactText":

                    if (attri.contains("id=")) {
                        wait.until(ExpectedConditions.textToBe(By.id(attri.substring("id=".length())), data));
                    } else if (attri.contains("//")) {
                        wait.until(ExpectedConditions.textToBe(By.xpath(attri), data));

                    } else if (attri.contains("xpath=")) {
                        wait.until(ExpectedConditions.textToBe(By.xpath(attri.substring("xpath=".length())), data));

                    } else if (attri.contains("tagName=")) {
                        wait.until(ExpectedConditions.textToBe(By.tagName(attri.substring("tagName=".length())), data));
                    } else if (attri.contains("name=")) {
                        wait.until(ExpectedConditions.textToBe(By.name(attri.substring("name=".length())), data));

                    } else if (attri.contains("linkText=")) {
                        wait.until(ExpectedConditions.textToBe(By.linkText(attri.substring("linkText=".length())), data));
                    } else if (attri.contains("partialLinkText=")) {
                        wait.until(ExpectedConditions.textToBe(By.partialLinkText(attri.substring("partialLinkText=".length())), data));
                    } else if (attri.contains("css=")) {
                        wait.until(ExpectedConditions.textToBe(By.cssSelector(attri.substring("css=".length())), data));
                    } else
                        logger.info("The element is incorrect, please verify respective properties file");

                    break;

                case "PartialText":

                    if (attri.contains("id=")) {
                        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id(attri.substring("id=".length())), data));
                    } else if (attri.contains("//")) {
                        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(attri), data));

                    } else if (attri.contains("xpath=")) {
                        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(attri.substring("xpath=".length())), data));

                    } else if (attri.contains("tagName=")) {
                        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName(attri.substring("tagName=".length())), data));
                    } else if (attri.contains("name=")) {
                        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.name(attri.substring("name=".length())), data));

                    } else if (attri.contains("linkText=")) {
                        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.linkText(attri.substring("linkText=".length())), data));
                    } else if (attri.contains("partialLinkText=")) {
                        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.partialLinkText(attri.substring("partialLinkText=".length())), data));
                    } else if (attri.contains("css=")) {
                        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(attri.substring("css=".length())), data));
                    }

                    break;

                case "ElementValue":

                    if (attri.contains("id=")) {
                        wait.until(ExpectedConditions.textToBePresentInElementValue(By.id(attri.substring("id=".length())), data));
                    } else if (attri.contains("//")) {
                        wait.until(ExpectedConditions.textToBePresentInElementValue(By.xpath(attri), data));

                    } else if (attri.contains("xpath=")) {
                        wait.until(ExpectedConditions.textToBePresentInElementValue(By.xpath(attri.substring("xpath=".length())), data));

                    } else if (attri.contains("tagName=")) {
                        wait.until(ExpectedConditions.textToBePresentInElementValue(By.tagName(attri.substring("tagName=".length())), data));
                    } else if (attri.contains("name=")) {
                        wait.until(ExpectedConditions.textToBePresentInElementValue(By.name(attri.substring("name=".length())), data));

                    } else if (attri.contains("linkText=")) {
                        wait.until(ExpectedConditions.textToBePresentInElementValue(By.linkText(attri.substring("linkText=".length())), data));
                    } else if (attri.contains("partialLinkText=")) {
                        wait.until(ExpectedConditions.textToBePresentInElementValue(By.partialLinkText(attri.substring("partialLinkText=".length())), data));
                    } else if (attri.contains("css=")) {
                        wait.until(ExpectedConditions.textToBePresentInElementValue(By.cssSelector(attri.substring("css=".length())), data));
                    } else
                        logger.info("The element is incorrect, please verify respective properties file");

                    break;
            }


        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("The element is incorrect, please verify respective properties file");
        }
    }

    ////==================Selectbox or drop down list=====================================================
    public void selectFromDropDown(WebDriver driver, final String Attribute, final String optionToSelect, final String byWhat) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);
        final Wait<WebDriver> waitExt = new FluentWait<WebDriver>(driver)
                .withTimeout(40, TimeUnit.SECONDS)
                .pollingEvery(3, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(NullPointerException.class)
                .ignoring(StaleElementReferenceException.class);

        logger.info("Going to Select on the  [" + Attribute + "] Field. Value :: " + optionToSelect + " BY " + byWhat);

        try {

            WebElement elem = waitExt.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    //WebElement element = getElementWithoutAssertion(driver, Attribute);
                    WebElement element = waitExt.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(getExactAttributeBY(Attribute))));
                    Select selectBox = new Select(element);
                    String byw = byWhat.toUpperCase();
                    switch (byw) {

                        case "BYVISIBLETEXT":
                            selectBox.selectByVisibleText(optionToSelect);
                            break;
                        case "BYVALUE":
                            selectBox.selectByValue(optionToSelect);
                            break;

                        case "BYINDEX":
                            selectBox.selectByIndex(Integer.valueOf(optionToSelect));
                            break;

                    }
                    element.sendKeys(Keys.TAB);
                    return element;
                }
            });
            logger.info("Selected on the  [" + Attribute + "] Field. Value :: " + optionToSelect + " BY " + byWhat);
            driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Unable to select " + optionToSelect + " by " + byWhat + " at field " + Attribute);
            e.printStackTrace();
            Assert.fail();
        }

    }


    /**
     * Dynamic wait
     *
     * @param element= the webElement for which wait is required
     * @return= returns the element after the wait
     */
    public WebElement fluentWait(WebDriver driver, final WebElement element) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement elementWaitedFor = wait.until(
                new Function<WebDriver, WebElement>() {
                    public WebElement apply(WebDriver driver) {
                        return element;
                    }
                }
        );
        return elementWaitedFor;
    }


    /**
     * @param attribute=the attribute of the element i.e xpath,id,name etc
     * @return the element waited for
     */
    public WebElement fluentWait(WebDriver driver, final String attribute) {//
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(10, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.MILLISECONDS);
        //.ignoring(NoSuchElementException.class);

        WebElement elementWaitedFor = wait.until(
                new Function<WebDriver, WebElement>() {
                    public WebElement apply(WebDriver driver) {
                        return getElement(driver, attribute);
                    }
                }
        );
        return elementWaitedFor;
    }


    public void setDateInDatePicker(WebDriver driver, String datePickerImg, String datePickerList, String value) throws InterruptedException {
        try {
            click(driver, datePickerImg);
            click(driver, "//td[@id='previousYear']/a");
            click(driver, "//td[@id='previousYear']/a");
            click(driver, "//td[@id='previousYear']/a");
            Iterator itr = getElements(driver, datePickerList).iterator();

            while (itr.hasNext()) {
                WebElement date = (WebElement) itr.next();
                if (date.getText().equalsIgnoreCase(value)) {

                    date.click();


                }
            }
            Thread.sleep(1000);
        } catch (Exception e) {
            ////e.printStackTrace();
        }
    }

    /**
     * Set the DatePicker of the element
     *
     * @param datePickerImg=Image of picker
     * @param datePickerList=     list of elements
     * @param value=set           the values
     * @throws InterruptedException
     */
    public void setDateInDatePickerFuture(WebDriver driver, String datePickerImg, String datePickerList, String value) throws InterruptedException {
        try {
            click(driver, datePickerImg);
            click(driver, "//td[@id='nextYear']/a");
            click(driver, "//td[@id='nextYear']/a");
            click(driver, "//td[@id='nextYear']/a");
            Iterator itr = getElements(driver, datePickerList).iterator();

            while (itr.hasNext()) {
                WebElement date = (WebElement) itr.next();
                if (date.getText().equalsIgnoreCase(value)) {

                    date.click();


                }
            }
            Thread.sleep(1000);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    //===========================Taking the Date and Time===========================
    public String getDateTime(String dateOrTime) {
        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

        //Calendar cal = Calendar.getInstance();
        if (dateOrTime.equalsIgnoreCase("Date")) {
            return new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
        } else {
            return new SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
            //return dateFormat.format(cal.getTime());
        }


    }


    public void highLightElement(WebDriver driver, String higlightElement) {
        try {
            // draw a border around the found element
            WebElement ele = getElement(driver, higlightElement);
            if (driver instanceof JavascriptExecutor) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='5px solid red'", ele);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return;
    }


    public void deHighLightElement(WebDriver driver, String higlightElement) {
        try {
            // draw a border around the found element
            WebElement ele = getElement(driver, higlightElement);
            if (driver instanceof JavascriptExecutor) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='5px solid white'", ele);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return;
    }


    public void highLightElement(WebDriver driver, WebElement higlightElement) {
        try {


            // draw a border around the found element
            //WebElement ele=	Wrapper.getElement(higlightElement);
            if (driver instanceof JavascriptExecutor) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='5px solid blue'", higlightElement);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return;
    }


    public void moushover(WebDriver driver, String ele) {
        WebElement hoverEle = getElement(driver, ele);

        Actions ac = new Actions(driver);
        ac.moveToElement(hoverEle).click().perform();

    }


    /**
     * Selecting the Dynamic values
     *
     * @param elementAttibute=  Identifying the Elements,id of the element is passed in arg element attribute
     * @param codeOrDescription = Selecting based on the Country or Code
     * @param value             = Passing value
     * @throws InterruptedException
     */
    public void typeInSuggestionTextbox(WebDriver driver, String elementAttibute, String codeOrDescription, String value) throws InterruptedException {
        //Wrapper.performClick(elementAttibute);
        try {
            //description ===== //div[contains(@id,'po') and (contains(@style,'visible'))]//span[@class='match-highlight']
            Thread.sleep(1000);

            type(driver, value, elementAttibute);
            //waitForElementVisibility(driver, "//td[2]/div/span", 4);
            Thread.sleep(1000);
            if (codeOrDescription.equalsIgnoreCase("Description")) {
                List<WebElement> itr = getElements(driver, "//td/div/span");
                for (WebElement ele : itr) {
                    WebElement description = ele;
                    if (description.getText().equalsIgnoreCase(value)) {
                        waitForElementVisibility(driver, "//td/div/span", 4);
                        Thread.sleep(1000);
                        description.click();
                        break;

                    }
                }


            }
            if (codeOrDescription.equalsIgnoreCase("code")) {
                try {
                    waitForElementVisibility(driver, "//td[2]/div/span", 4);
                } catch (NoSuchElementException e) {
                    clear(driver, elementAttibute);
                    type(driver, value, elementAttibute);
                }
                Thread.sleep(1000);
                Iterator<WebElement> itr = getElements(driver, "//td[2]/div/span").iterator();

                while (itr.hasNext()) {
                    WebElement code = (WebElement) itr.next();
                    if (code.getText().equalsIgnoreCase(value)) {
                        Thread.sleep(1500);
                        code.click();

                        break;
                    }


                }

            }
        } catch (Exception e) {
            logger.error("Unable to type on attribute:" + elementAttibute);
            e.printStackTrace();
            Assert.fail();
        }


    }

    /**
     * Selecting the Dynamic values
     *
     * @param elementAttibute=   Identifying the Elements,id of the element is passed in arg element attribute
     * @param codeOrDescription= Selecting based on the Country or Code
     * @param value              = Passing value
     * @throws InterruptedException
     */
    public void typeToSuggestionTextbox(WebDriver driver, String elementAttibute, String codeOrDescription, String value) throws InterruptedException {
        //Wrapper.performClick(elementAttibute);
        // type(driver,elementAttibute, value);
        try {
            type(driver, value, elementAttibute);
            Thread.sleep(1000);

            if (codeOrDescription.equalsIgnoreCase("Description")) {
                List<WebElement> itr = getElements(driver, "//td[2]/div/span/span");
                Thread.sleep(1000);
                for (WebElement ele : itr) {
                    WebElement description = ele;
                    if (description.getText().equalsIgnoreCase(value)) {
                        Thread.sleep(1000);
                        waitForElementVisibility(driver, "//td[2]/div/span", 4);
                        description.click();
                        return;
                    }
                }


            }
           /*if (codeOrDescription.equalsIgnoreCase("Description"))               {
			Iterator<WebElement> itr =getElements(driver,"//td[2]/div/span").iterator();
			while(itr.hasNext()){
				Thread.sleep(100);
				WebElement description = (WebElement) itr.next();
				if(description.getText().equalsIgnoreCase(value)){
					Thread.sleep(1000);
					description.click();
				}
			}

		}*/

            if (codeOrDescription.equalsIgnoreCase("code")) {
                try {
                    waitForElementVisibility(driver, "//td[1]/div/div/div/span", 5);
                } catch (NoSuchElementException e) {
                    clear(driver, elementAttibute);
                    type(driver, value, elementAttibute);
                    //typeToTextBox(driver,value,elementAttibute);
                }
                Iterator<WebElement> itr = getElements(driver, "//td[1]/div/div/div/span").iterator();
                Thread.sleep(1000);
                while (itr.hasNext()) {
                    WebElement code = itr.next();
                    if (code.getText().equalsIgnoreCase(value)) {
                        //Thread.sleep(1000);
                        //waitForElementVisibility(driver, "//td[1]/div/span//span", 5);
                        code.click();
                        break;
                    }


                }

            }
        } catch (Exception e) {
            logger.error("Failed typeToSuggestionTextbox:" + elementAttibute);
            e.printStackTrace();
            Assert.fail();
        }
    /*if (codeOrDescription.equalsIgnoreCase("code"))            {
           //Iterator<WebElement> itr = getElements(driver,"//td[1]/div/span/span").iterator();
           Iterator<WebElement> itr = getElements(driver,"//td[1]/div/span").iterator();

           while(itr.hasNext()){
                 Thread.sleep(100);
                 WebElement code = (WebElement) itr.next();
                 if(code.getText().equalsIgnoreCase(value)){
                        Thread.sleep(1000);
                        code.click();
                 }



           }

    }*/
    }


    /**
     * @param filePath
     * @return= the headers,that is the first row in an excelSheet as a list
     */
    @SuppressWarnings("resource")
    public List<String> getHeadersFromCsv(String filePath) {
        Scanner scanIn = null;
        String InputLine = "";
        List<String> Headers = new ArrayList<String>();
        String returnvalue = null;
        String[] firstRow = null;
        try {

            scanIn = new Scanner(new BufferedReader(new FileReader(filePath)));
            scanIn.useDelimiter(",");
            InputLine = scanIn.nextLine();
            firstRow = InputLine.split(",");
            for (String header : firstRow) {
                Headers.add(header);
            }
            scanIn.close();
            return Headers;

        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }

    }


    /**
     * @param filePath
     * @return= the headers,that is the first row in an excelSheet as a list
     */
    public ArrayList<String> convertCsvToList(String filePath) {
        Scanner scanIn = null;
        ArrayList<String> rows = new ArrayList<String>();
        rows.clear();
        String row = null;
        try {

            scanIn = new Scanner(new BufferedReader(new FileReader(filePath)));
            scanIn.useDelimiter(",");
            while (scanIn.hasNext()) {
                row = scanIn.next();
                rows.add(row);

            }
            scanIn.close();
            return rows;

        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }

    }


    /**
     * @param filePath
     * @param testCaseIdColumnNumber
     * @return= the headers,that is the first row in an excelSheet as a list
     */
    //gets the cloumn number of the testcase id and adds only values in that column
    @SuppressWarnings("resource")
    public List<String> getTestCaseIds(String filePath, int testCaseIdColumnNumber) {
        Scanner scanIn = null;
        String InputLine = "";
        List<String> testCaseId = new ArrayList<String>();
        String returnvalue = null;
        String row = null;
        String cellValues[] = null;
        int rowNumber = 0;
        int columnNumber = 0;

        try {
            scanIn = new Scanner(new BufferedReader(new FileReader(filePath)));

            while (scanIn.hasNext()) {//each row
                if (rowNumber == 0) {//if first row skip i because data table of gerkin skips first row
                    break;
                }
                scanIn.useDelimiter(",");
                row = scanIn.nextLine();
                cellValues = row.split(",");
                for (String value : cellValues) {
                    if (columnNumber == testCaseIdColumnNumber) {//the column of testcase id
                        testCaseId.add(value);
                        break;
                    }
                    columnNumber++;
                }
                rowNumber++;
            }

            scanIn.close();
            return testCaseId;
        } catch (Exception e) {
            logger.error(e);
        }
        return testCaseId;
    }


    public void screenShot(WebDriver driver, String FilePath, String testName) throws IOException {
        logger.info("Omitting screenshot request due to path mismatch");
        return;

        /*File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenShotName;
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd_MM_yyyy_'at'_hh_mm_ss_a");
        screenShotName = testName;
        FileUtils.copyFile(screenShot, new File(FilePath + File.separator + screenShotName + ".jpg"));*/

    }


    public String waitForElement(WebDriver driver, String attribute) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement elem = wait.until(
                ExpectedConditions.elementToBeClickable(By.id(attribute)));
        return attribute;
    }


	/*public void writeOutPutToCSV(String filePath,List<String> outputs) throws IOException{
		//String csv = "C:"+File.separator+"Users"+File.separator+"1555976"+File.separator+"Desktop"+File.separator+"output.csv";
		FileWriter pw = new FileWriter("C:"+File.separator+"AppWorkFlow"+File.separator+"Reports"+File.separator+"awfReports.csv",true);
		/// Iterator rows = outputs.iterator();
		for(String rows:outputs){
			String[] eachRow = rows.split("/n");//row is split based on null value
			for (String row:eachRow){
				//				String[] eachCell= row.split(",");
				//				for(String cell:eachCell){
				//					pw.write(cell);
				//					 //next column
				//				}
				//				 // newline
				pw.write(row);
				pw.write("\n");
			}

		}
		pw.flush();
		pw.close();
	}
	 */

    public void createNewCsv(String path, List<String> outputs) throws IOException {

        String newFileName = path;

        File newFile = new File(newFileName);

        BufferedWriter writer = new BufferedWriter(new FileWriter(newFile, false));

        for (String rows : outputs) {

            String[] eachRow = rows.split("/n");//row is split based on null value

            for (String row : eachRow) {

                writer.write(row);

                writer.write("\n");

            }


        }

        writer.flush();

        writer.close();

    }

    public static void switch_to_alert_dismiss(String alertType) {

    	String type = alertType;
        //Alert alert = service.getWebDriver().switchTo().alert();
        WebDriverWait wait = new WebDriverWait(driver,60);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        switch (type) {
            case "dismiss":
                alert.dismiss();
                break;
            case "accept":
                alert.accept();
                break;


        }


    }


    public void switch_to_Iframe(WebDriver driver, String frameName) {

        driver.switchTo().defaultContent();

        logger.info("User should switch to this frame name " + frameName);
        try {
            WebDriverWait wait = new WebDriverWait(driver, 120, 1000);
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//iframe[contains(@name,'" + frameName + "')]")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
            logger.info("User switched to this frame name PegaGadget" + frameName);
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            logger.error("An exception occured while switch_to_Iframe " + e.getCause());
            Assert.fail(e.getMessage());
        }


    }

    public void switch_to_frame(WebDriver driver, String frameName, int seconds) {
        wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
        driver.switchTo().frame(frameName);
    }

    public void switch_to_default_Content(WebDriver driver) {
        driver.switchTo().defaultContent();

    }


    public static void switch_to_window() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    

        //logger.info(firstWindow);

  

        //logger.info(windows);


       

            
            //logger.info(listOfWindows);

            //logger.info(service.getWebDriver().getTitle());

           
        }




    public void writeOutPutToCSV(String filePath, List<String> outputs) throws IOException {

        //String csv = "C:"+File.separator+"Users"+File.separator+"1555976"+File.separator+"Desktop"+File.separator+"output.csv";

        FileWriter pw = new FileWriter(filePath, true);

        /// Iterator rows = outputs.iterator();

        for (String rows : outputs) {

            String[] eachRow = rows.split("/n");//row is split based on null value

            for (String row : eachRow) {

                // String[] eachCell= row.split(",");

                // for(String cell:eachCell){

                // pw.write(cell);

                // //next column

                // }

                // // newline

                pw.write(row);

                pw.write("\n");

            }


        }

        pw.flush();

        pw.close();

    }



    public void read_Table(WebDriver driver, String data) {
        WebElement table = driver.findElement(By.xpath(data));
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));

            for (WebElement cell : cells) {

                logger.info(cell.getText());
            }

        }

    }

    public void select_application_number(WebDriver driver, String appNumber) throws IOException, InterruptedException {


        Thread.sleep(1000);

        String filter = com.getElementProperties("BasicDataCapture", "filter_link");
        String search = com.getElementProperties("BasicDataCapture", "search_text");
        String apply_button = com.getElementProperties("BasicDataCapture", "apply_button");

        click(driver, filter);
        Thread.sleep(1000);

        typeToTextBox(driver, appNumber, search);
        click(driver, apply_button);


        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[text()='" + appNumber + "']")).click();
    }


    public String getAttributeOfId(WebDriver driver, String webelement) {


        String value = driver.findElement(By.xpath(webelement)).getAttribute("id");
        return value;


    }


    public void verifyTextByXPath(WebDriver driver, String webelement, String value) {


        String str = driver.findElement(By.xpath(webelement)).getText();
        if (str.equalsIgnoreCase(value)) {
            logger.info("Text available:" + value);
        }


    }

    public void switchToWindow(WebDriver driver, String windowTitle) {
        Set<String> windows = driver.getWindowHandles();

        for (String window : windows) {
            driver.switchTo().window(window);
            if (driver.getTitle().contains(windowTitle)) {
                return;
            }
        }
    }

    //Balaji [3/21/2017]
    public void switchFrame() throws InterruptedException {
        int Last = 0;
        Thread.sleep(1000);
        driver.switchTo().defaultContent();
        Thread.sleep(1000);
        List<WebElement> frames = driver.findElements(By.tagName("iframe"));
        for (WebElement frame : frames) {
            logger.info(frame.getAttribute("Name"));
        }

        Last = frames.size() - 1;
        logger.info(Last);
        driver.switchTo().frame(Last);
        Thread.sleep(1000);
    }




    public boolean scroll_to(WebDriver driver, String attribute) throws InterruptedException {
    	logger.info("Before scroll div section of the page");
        String divScroll = "//*[@id='INNERDIV-SubSectionCaptureDetailsB']";
        //*[@id='INNERDIV-SubSectionCaptureDetailsB']/div/div[1]/div
        WebElement ele = getElement(driver, attribute);
      
        new WebDriverWait(driver, 60, 1000).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(wrap.getExactAttributeBY(attribute)));      
        
        /*Actions a = new Actions(driver);
              WebElement scrollBlock = driver.findElement(By.xpath(divScroll));
        for (int j = 1; j < 10; j++) 
        {
              logger.info("Scrolled "+j+" time");
            a.sendKeys(scrollBlock, Keys.PAGE_DOWN).sendKeys(Keys.DOWN).build().perform();
            if (ele.isDisplayed())
             {
              logger.info("Given element is found");
              break;             
            }
        }
        logger.info("After scroll page");*/
    
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].scrollIntoView();",ele);
        return true;

    }

    public boolean check_element1(WebDriver driver, String attribute) throws InterruptedException {

        //String atrr= "//h2[text()='Related Party']//parent::div//preceding-sibling::div";
        //logger.info((getElement(driver,attribute).getAttribute("title")));
        //logger.info(("(getElement(driver,attribute).isDisplayed()"+getElement(driver,attribute).isDisplayed()));
        if (!(getElement(driver, attribute).isDisplayed())) {
            if (scroll_to(driver, attribute)) {
                //logger.info((getElement(driver,attribute).getAttribute("title")));
                if ((getElement(driver, attribute).getAttribute("title")).contains("Disclose"))
                    click(driver, attribute);
                //logger.info("im inside first function of check element");
                return true;
            }
        } else //if((getElement(driver,attribute).isDisplayed()))
        {
            Boolean rt = scroll_to(driver, attribute);
            logger.info(rt);
            logger.info((getElement(driver, attribute).getAttribute("title")));
            if (getElement(driver, attribute).getAttribute("title").contains("Disclose")) {
                //logger.info((getElement(driver,attribute).getAttribute("title")));

                click(driver, attribute);

                //logger.info("im inside third function of check element");
                return true;
            }
        }
        return true;

        //return false;
    }


    public static void scrollDown(WebDriver driver, int i) throws InterruptedException {

        logger.info("Before scroll div section of the page");
        String divScroll = "//*[@id='INNERDIV-SubSectionCaptureDetailsB']/div/div[1]/div";

        WebElement divElement = driver.findElement(By.xpath(divScroll));

        Actions a = new Actions(driver);

        for (int j = 0; j < i; j++) {
            a.sendKeys(divElement, Keys.ARROW_DOWN).sendKeys(Keys.DOWN).build().perform();
        }
        Thread.sleep(1000);
        logger.info("After scroll page");
    }

    public void TypeToTextBoxAndTabOut(WebDriver driver, String value, String Inattribute) throws InterruptedException {
        //getExactAttribute(driver,Inattribute).clear();
        //Thread.sleep(100);
        wait(1000);
        getExactAttribute(driver, Inattribute).sendKeys(value);
        wait(1000);
        getExactAttribute(driver, Inattribute).sendKeys(Keys.TAB);
        wait(1000);
        //getExactAttribute(driver,Inattribute).sendKeys(Keys.TAB);
        //getExactAttribute(driver,Inattribute).sendKeys(Keys.TAB);
    }

    /**
     * @param filePath
     * @param delimiter
     * @return= the headers,that is the first row in an excelSheet as a list
     */
    @SuppressWarnings("resource")
    public List<String> getHeadersFromCsv(String filePath, String delimiter) {
        Scanner scanIn = null;
        String InputLine = "";
        List<String> Headers = new ArrayList<String>();
        String returnvalue = null;
        String[] firstRow = null;
        try {

            scanIn = new Scanner(new BufferedReader(new FileReader(filePath)));
            scanIn.useDelimiter(delimiter);
            InputLine = scanIn.nextLine();
            firstRow = InputLine.split(delimiter);
            for (String header : firstRow) {
                Headers.add(header);
            }
            scanIn.close();
            return Headers;

        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }

    }

    public static String getTextValue(WebDriver driver, String attribute) throws InterruptedException {

        String str = driver.findElement(By.xpath(attribute)).getText();
        logger.info("String value from the given attribute is : " + str);
        return str;


    }

    public boolean scrollTillElement(WebDriver driver, String attribute, String DivScrollPath) throws InterruptedException {
        logger.info("Before scroll div section of the page");
        //String divScroll = "//*[@id='INNERDIV-SubSectionCaptureDetailsB']/div/div[1]/div";
        WebElement ele = getElement(driver, attribute);

        //WebElement divElement = driver.findElement(By.xpath(DivScrollPath));

        WebElement divElement = getElement(driver, DivScrollPath);

        Actions a = new Actions(driver);

        for (int j = 0; j < 8; j++) {
            a.sendKeys(divElement, Keys.PAGE_DOWN).sendKeys(Keys.DOWN).build().perform();
            if (ele.isDisplayed()) {
                return true;
            }
        }

        Thread.sleep(3000);
        logger.info("After scroll page");
        return false;


    }

    public void wait(int mseconds) throws InterruptedException {
        Thread.sleep(mseconds);
    }


    public void switchToNewWindow(WebDriver driver) {
        String firstWindow = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();
        Iterator<?> itr = windows.iterator();
        while (itr.hasNext()) {
            String listOfWindows = (String) itr.next();
            if (!listOfWindows.equals(firstWindow)) {
                driver.switchTo().window(listOfWindows);
                logger.info(driver.getTitle());
            } else {
                driver.switchTo().window(firstWindow);
            }
        }
    }

    public boolean isElementEnabled(WebDriver driver, String attribute) throws IOException {
        try {
            WebElement we = getExactAttribute(driver, attribute);
            if (we.isEnabled()) {
                return true;
            }
            return false;
        } catch (Exception E) {
            E.printStackTrace();
            return false;
        }
    }

    public boolean isElementPresent(WebDriver driver, String attribute) throws IOException {
        driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
        try {
            WebElement we = new WebDriverWait(driver, 30, 1000).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(getExactAttributeBY(attribute))));
            if (we.isDisplayed()) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
            return false;
        }
        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);

        return false;
    }

    public boolean isApplicationPresent(WebDriver driver, String appNumber) {
        try {
            return driver.findElement(By.xpath("//span[text()='" + appNumber + "']")).isDisplayed();
        } catch (Exception E) {
            return false;
        }
    }

    public boolean isDocumentTypePresent(WebDriver driver, String docNumber) {
        try {
            return driver.findElement(By.xpath("//nobr/span[contains(.,'" + docNumber + "')]")).isDisplayed();
        } catch (Exception E) {
            logger.info("Document type is not present");
            return false;
        }
    }

    public void getWorkbasketoption(WebDriver driver, String Value) throws IOException, InterruptedException {
        boolean isFound = false;
       // driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);
        final Wait<WebDriver> waitExt = new FluentWait<WebDriver>(driver)
                .withTimeout(60, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotVisibleException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(WebDriverException.class);

        logger.info("Going to getWorkbasketoption on  [" + Value + "]");
        try{
             wait(5000);

            //  List<WebElement> workBasketItems  = waitExt.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(getExactAttributeBY(com.getElementProperties("DI", "Workbasket_items")))));

            for (int i = 0; i < 7; i++) {
              //  logger.info("Ant 1");
                List<WebElement> workBasketItems = waitExt.until(new Function<WebDriver, List<WebElement>>() {
                    public List<WebElement> apply(WebDriver driver) {
                        //WebElement element = getExactAttribute(driver, attribute);
                       // logger.info("Ant 2");
                        List<WebElement> tmpListElement = null;
                        try {
                           // tmpListElement = getExactAttributesWithoutAssertion(driver, com.getElementProperties("DI", "Workbasket_items"));
                            tmpListElement  = waitExt.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(getExactAttributeBY(com.getElementProperties("DI", "Workbasket_items")))));
                          //  logger.info("Ant 3");
                            return tmpListElement;
                        } catch (IOException e) {
                            e.printStackTrace();
                            Assert.fail();
                        }
                       // logger.info("Ant 4");
//                        element.click();
//                        return element;
                        return tmpListElement;
                    }
                });
               // logger.info("Ant 5");
              //  List<WebElement> workBasketItems = getExactAttributeWithoutAssertion(driver, com.getElementProperties("DI", "Workbasket_items"));
                for (WebElement we : workBasketItems) {
                   // logger.info("Ant 6");
                    String tmpElementText = waitExt.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(we))).getText();
                    tmpElementText = tmpElementText.trim();             
                   logger.info("Ant 6a: tmpElementText:" + tmpElementText);
                    if (tmpElementText.equalsIgnoreCase(Value)) {
                        isFound = true;

                        logger.info("Found and going to click subelement: " + Value);
                        waitExt.until(ExpectedConditions.elementToBeClickable(we)).click();
                        logger.info("Clicked subelement: " + Value);
                        //wait(2000);
                        break;
                    }
                   // logger.info("Ant 7");
                }
                if (isFound == true) {
                    break;
                }
               // logger.info("Ant 8");
                this.click(driver, com.getElementProperties("DI", "modal_next_button"));
                wait(2000);
               // logger.info("Ant 9");
            //    driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);
              //  logger.info("Ant 10");
            }
           // logger.info("Ant 11");
         //   driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
            logger.info("Successfully go to screen  [" + Value + "]");
        } catch (Exception e) {
            logger.error("Unable to go to screen [" + Value + "]");
            e.printStackTrace();
            Assert.fail();
        }

    }

    public void writeToATextFile(String filePath, String text, boolean value) {


        try (FileWriter fw = new FileWriter(filePath, value);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(text);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void TextFieldMaxLengthValidation(WebDriver driver, String excelreadattri, String variablepath) throws InterruptedException {
        int actTxtLen, maxLeng = 0;
        String maxLen;
        String inputText;

        wait(1500);

        click(driver, variablepath);

        maxLen = getElement(driver, variablepath).getAttribute("maxlength");
        maxLeng = Integer.parseInt(maxLen);

        type(driver, excelreadattri, variablepath);
        actTxtLen = getElement(driver, variablepath).getAttribute("value").length();
        inputText = getElement(driver, variablepath).getAttribute("value");

        logger.info("The actual text entered in the text field is " + inputText);
        logger.info("The length of actual text entered in the text field is : " + actTxtLen);

        if (actTxtLen <= maxLeng)

            logger.info("The actual input to the text field data " + inputText + " is within the maximum limit range of " + maxLeng);
        else
            logger.info("The actual input to the text field data " + inputText + " exceeds the maximum limit range of " + maxLeng);

        logger.info("-----------------------------------------------------------------------------");
    }

    public void isProductTypePresent(WebDriver driver, String docNumber) {
        try {
            driver.findElement(By.xpath("//nobr/span[contains(.,'" + docNumber + "')]")).isDisplayed();
            driver.findElement(By.xpath("//nobr/span[contains(.,'" + docNumber + "')]")).click();
            logger.info("Product type is clicked");
        } catch (Exception E) {
            logger.info("Product type is not clicked");

        }
    }


    public void starttimer() {
        starttime = System.currentTimeMillis();
        logger.info("Start time is" + starttime);
    }

    public void endtimer() {
        endTime = System.currentTimeMillis();
        logger.info("End time is" + endTime);
        totalTime = endTime - starttime;
        logger.info("time duration is " + (totalTime / 1000.00000) + "Seconds");
    }

    public void logout() throws InterruptedException, IOException {
        final Wait<WebDriver> waitExt = new FluentWait<WebDriver>(driver)
                .withTimeout(60, TimeUnit.SECONDS)
                .pollingEvery(2, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(NullPointerException.class)
                .ignoring(StaleElementReferenceException.class);

    //    boolean visi_logout=false;
        logger.info("Started to switch_to_default_Content");
        this.switch_to_default_Content(driver);
     //   logger.info("Check if logout button visible");
        wrap.wait(1000);
       // waitExt.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(wrap.getElement(BaseProject.driver, com.getElementProperties("ProductCatalogue","logOut_xpath"))))).click();
        wrap.click(driver, com.getElementProperties("ProductCatalogue","logOut_xpath"));
        logger.info("User logged out successfully");
        this.wait(3000);
      //  SeleniumScreenshotUtil.takeScreenshot(driver, genieScenario);
     //   visi_logout = wrap.getElement(driver, com.getElementProperties("ProductCatalogue","logOut_xpath")).isDisplayed();
//        logger.info("visi_logout:" + String.valueOf(visi_logout);
//        if (visi_logout==true)
//        {
//            wrap.getElement(driver, com.getElementProperties("ProductCatalogue","logOut_xpath")).click();
//            logger.info("User logged out successfully");
//            this.wait(3000);
//            //	driver.quit();
//	/*		try {
//				switch_to_Parent_Window();
//			} catch (Throwable e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	*/	}
    }

    /*public void enterDate(WebDriver driver, final String value, final String Inattribute) throws InterruptedException {
      //  final String value2 = "02/02/1992";

        driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);

        final Wait<WebDriver> waitExt = new FluentWait<WebDriver>(driver)
                .withTimeout(60, TimeUnit.SECONDS)
                .pollingEvery(2, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(NullPointerException.class)
                .ignoring(StaleElementReferenceException.class);

       logger.info("The Value [" + value + "] going to Enter into the Field [" + Inattribute + "]");
        SeleniumScreenshotUtil.takeScreenshot(driver, genieScenario);
        try {
            //CharSequence[] temp;
            WebElement elem = waitExt.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {

                   // WebElement element = getElement(driver, Inattribute);
                    WebElement element = waitExt.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(getExactAttributeBY(Inattribute))));
                    element.clear();
                 //   WebElement element2 =  waitExt.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(getExactAttributeBY(Inattribute))));

                    for (int stringIndex = 0;stringIndex < value.length();stringIndex++){
                        logger.info("Sending key: " + String.valueOf(value.charAt(stringIndex)));
                        element = waitExt.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(getExactAttributeBY(Inattribute))));
                        element.sendKeys(String.valueOf(value.charAt(stringIndex)));

                    }
                    
                   
                    	
                    element = waitExt.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(getExactAttributeBY(Inattribute))));
                    element.sendKeys(Keys.TAB);
                    return element;
                }
            });
            driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
            String finalAttributeValue = elem.getAttribute("value").trim();
            if (value.trim().equalsIgnoreCase(finalAttributeValue)) {
                logger.info("Value [" + value + "] Entered and verified Successfully");
            }
            else {
                logger.error("Entered [" + value + "] but final value in field is [" + finalAttributeValue + "]");
                Assert.fail();
            }


        } catch (Exception e) {
            logger.error("An exception occured while enterDate " + e.getCause());
            Assert.fail(e.getMessage());
        }
    }*/
    
    
    public void enterDate(WebDriver driver,String value,String Inattribute) throws InterruptedException
	{

    	logger.info("The Value [" + value + "] going to Enter into the Field [" + Inattribute + "]");
		try
		{
			CharSequence[] temp;
			new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(getExactAttribute(driver,Inattribute)));
			System.out.println("The Value ["+value+"] going to Enter into the Field ["+Inattribute+"]");
			new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(getExactAttribute(driver,Inattribute)));
			getExactAttribute(driver,Inattribute).click();
			getExactAttribute(driver,Inattribute).clear();
			wrap.wait(1000);
			getExactAttribute(driver,Inattribute).click();
			new WebDriverWait(driver, 20).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(getExactAttribute(driver,Inattribute)));
			wrap.wait(1000);
			
			for (int i=0 ; i<value.length();i++)
			{
				switch(value.charAt(i)){  
				case '0': getExactAttribute(driver,Inattribute).sendKeys(Keys.NUMPAD0);
				break;  
				case '1': getExactAttribute(driver,Inattribute).sendKeys(Keys.NUMPAD1);
				break;  
				case '2': getExactAttribute(driver,Inattribute).sendKeys(Keys.NUMPAD2);
				break;  
				case '3':getExactAttribute(driver,Inattribute).sendKeys(Keys.NUMPAD3);
				break; 
				case '4':getExactAttribute(driver,Inattribute).sendKeys(Keys.NUMPAD4);
				break; 
				case '5':getExactAttribute(driver,Inattribute).sendKeys(Keys.NUMPAD5);
				break; 
				case '6':getExactAttribute(driver,Inattribute).sendKeys(Keys.NUMPAD6);
				break; 
				case '7':getExactAttribute(driver,Inattribute).sendKeys(Keys.NUMPAD7);
				break; 
				case '8':getExactAttribute(driver,Inattribute).sendKeys(Keys.NUMPAD8);
				break; 
				case '9':getExactAttribute(driver,Inattribute).sendKeys(Keys.NUMPAD9);
				break;
				case '/':getExactAttribute(driver,Inattribute).sendKeys("/");
				break;

				}  

			}
			System.out.println("Value ["+value+"]Entered Sucessfully");
		}
		catch(StaleElementReferenceException e)
		{
			new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(getExactAttribute(driver,Inattribute)));
			getExactAttribute(driver,Inattribute).clear();
			//Thread.sleep(1000);
			new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(getExactAttribute(driver,Inattribute)));
			getExactAttribute(driver,Inattribute).sendKeys(value);
		}
		catch(Exception e)
		{
			System.out.println("Unable to Enter the Date");
		}
	} 
    
    

    public void validateField(String attribute, boolean alphaNumeric, boolean mandatory, String length, String value) {

        WebElement we = getExactAttribute(driver, attribute);

        com.validateDataLength(driver, attribute, value, length);

        if (alphaNumeric) {

            com.validateAlphaNumeric(driver, attribute, value);

        }

    }

    public void switchToNewWindow1(WebDriver driver, int windowNumber) {
        Set<String> s = driver.getWindowHandles();
        Iterator<String> ite = s.iterator();
        int i = 1;
        while (ite.hasNext() && i < 10) {
            String popupHandle = ite.next().toString();
            driver.switchTo().window(popupHandle);
            logger.info("Window title is : " + driver.getTitle());
            if (i == windowNumber) break;
            i++;
        }
    }

    public String captureScreenShot(WebDriver driver,
                                    String testName) {
        
 /*
        String fileSeperator = System.getProperty("file.separator");
        String screenShotName;
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd_MM_yyyy_'at'_hh_mm_ss_a");
        String screenshotTakenTime = ft.format(dNow);
        screenShotName = testName + "_" + screenshotTakenTime + ".jpg";
        //	screenShotName=testName+".jpg";
        try {
            File file = new File("Screenshots" + fileSeperator + "Results");
            if (!file.exists()) {
                logger.info("File created " + file);
                file.mkdir();
            }
            logger.info("Screenshots are stored in path:" + file.getAbsolutePath());
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File targetFile = new File("Screenshots" + fileSeperator + "Results" + fileSeperator + testName, screenShotName);
            FileUtils.copyFile(screenshotFile, targetFile);
            return screenShotName;
        } catch (Exception e) {
            logger.error("An exception occured while taking screenshot " + e.getCause());
            return null;
        }*/
        return testName;
    }

    public void typenewSuggestionTextbox(WebDriver driver, String elementAttibute, String codeOrDescription, String value, String code2) throws InterruptedException {

        //Wrapper.performClick(elementAttibute);

        try {

            //description ===== //div[contains(@id,'po') and (contains(@style,'visible'))]//span[@class='match-highlight']

            Thread.sleep(1000);
            type(driver, value, elementAttibute);
            //waitForElementVisibility(driver, "//td[2]/div/span", 4);

            Thread.sleep(1000);
            if (codeOrDescription.equalsIgnoreCase("Description")) {
                List<WebElement> itr = getElements(driver, "//td/div/span");
                for (WebElement ele : itr) {
                    WebElement description = ele;
                    if (description.getText().equalsIgnoreCase(value)) {
                        Thread.sleep(1000);
                        waitForElementVisibility(driver, "//td/div/span", 4);
                        description.click();
                        break;
                    }
                }
            }
            if (codeOrDescription.equalsIgnoreCase("code")) {
                try {
                    waitForElementVisibility(driver, "//td[1]/div/div/div/span", 4);
                } catch (NoSuchElementException e) {
                    clear(driver, elementAttibute);
                    type(driver, value, elementAttibute);
                }
                Thread.sleep(1000);
                Iterator<WebElement> itr = getElements(driver, "//td[1]/div/div/div/span").iterator();
                while (itr.hasNext()) {
                    WebElement code = (WebElement) itr.next();
                    if (code.getText().equalsIgnoreCase(code2)) {
                        Thread.sleep(1000);
                        code.click();
                        break;
                    }
                }
            }
        } catch (Exception e)
        {
            logger.error("An exception occured while typenewSuggestionTextbox " + e.getCause());
            Assert.fail(e.getMessage());
            //e.printStackTrace();
        }


    }


    public void SelectAutosuggestionTextBox(WebDriver driver, String elementAttibute, String code) throws InterruptedException {

        final Wait<WebDriver> waitExt = new FluentWait<WebDriver>(driver)
                .withTimeout(60, TimeUnit.SECONDS)
                .pollingEvery(2, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(NullPointerException.class)
                .ignoring(StaleElementReferenceException.class);

        try {
            waitExt.until(ExpectedConditions.elementToBeClickable(getExactAttributeBY(elementAttibute))).click();

            WebElement element = driver.findElement(getExactAttributeBY(elementAttibute));
            logger.info("Went into SelectAutoSuggest");
            element.clear();
            logger.info("SelectAutoSuggestionTextBox cleared");
            element.sendKeys(code);
            logger.info("SelectAutoSuggestionTextBox - entered " + code);
            element.sendKeys(Keys.TAB);
            logger.info("SelectAutoSuggestionTextBox - tabbed out");

            //Refresh to prevent stale
            waitExt.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(getExactAttributeBY(elementAttibute))));
            //Relocate element
            logger.info("Element refreshed");

            element = driver.findElement(getExactAttributeBY(elementAttibute));
            logger.info("Relocated element");
            //Validation (---Expected to meet stale element here---)
            String finalAttribute = element.getAttribute("value").trim();
            logger.info("Relocated element value is: " + finalAttribute);

            if (finalAttribute.equalsIgnoreCase(code.trim())) {
                logger.info("SelectAutoSuggestionTextBox successful");
            }else {
                logger.info("Final value: " + finalAttribute + " does not match with intended value: " + code);
                Assert.fail();
            }

        }catch(StaleElementReferenceException e){
            logger.info("Stale element exception caught at SelectAutosuggestionTextBox method");

            //Relocate element
            WebElement element = driver.findElement(getExactAttributeBY(elementAttibute));
            //Validation
            String finalAttribute = element.getAttribute("value").trim();
            if (finalAttribute.equalsIgnoreCase(code.trim())) {
                logger.info("SelectAutoSuggestionTextBox successful");
            }else {
                logger.info("Final value: " + finalAttribute + " does not match with intended value: " + code);
                Assert.fail();
            }
        } catch (Exception e) {
            logger.error("An exception occured while SelectAutosuggestionTextBox " + e.getCause());
            Assert.fail(e.getMessage());
        }
    }

    public void selectformLOV_Values(WebDriver driver, String elementAttibute, String value) throws InterruptedException {

        Thread.sleep(1000);
        type(driver, value, elementAttibute);
        Thread.sleep(1000);
        Iterator<WebElement> itr = getElements(driver, "//td[2]/div/span").iterator();

        while (itr.hasNext()) {
            WebElement code = (WebElement) itr.next();
            if (code.getText().equalsIgnoreCase(value)) {
                Thread.sleep(1000);
                code.click();

                break;
            }
        }

    }

    public void IsMandatoryField(WebDriver driver, String attribute) {

        String value = getElement(driver, attribute).getAttribute("validationtype");

        if (value.equalsIgnoreCase("required"))
            logger.info("The given field is mandatory");
        else
            logger.info("The given field is not a mandatory");

    }

    public void splcharval(WebDriver driver, String attribute) {

        String s = getElement(driver, attribute).getAttribute("value");
        logger.info("Entered" + attribute + "value is" + s);
        if (s == null || s.trim().isEmpty()) {
            logger.info("Incorrect format of string");

        }
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(s);
        // boolean b = m.matches();
        boolean b = m.find();
        if (b == true) {
            logger.info("There is a special character in my string");
            logger.info("There is a special character in my string for" + s);
        } else {
            logger.info("There is no special char.");
            logger.info("There is not a special character in my string for" + s);
        }
    }

    public void textvarcharvalidation(WebDriver driver, String attribute) {

        String s = getElement(driver, attribute).getAttribute("data-ctl");
        if (s.contains("TextInput")) {
            logger.info("It is freetext/VARCHAR for the entered attribute of" + s);
        } else {
            logger.info("It is not freetext/VARCHAR for the entered attribute of" + s);
        }
    }

    public void isMandatoryField(WebDriver driver, String attribute) {

        String value = getElement(driver, attribute).getAttribute("validationtype");

        if (value.equalsIgnoreCase("required")) {

            logger.info("The given field is mandatory");
            logger.info("The given field is mandatory");

        } else if (value.equalsIgnoreCase("required,calculateexpression")) {
            logger.info("The given field is a mandatory with calculateexpression");
            logger.info("The given field is not a with calculateexpression");
        } else if (value.equalsIgnoreCase("date")) {
            logger.info("The given field is Date format");
            logger.info("The given field is Date");
        } else {
            logger.info("The given field is Not Mandatory");
            logger.info("The given field is Not Mandatory");
        }


    }

    public void fieldFormValidation(WebDriver driver, String attribute) {

        try {
            String fieldType = getElement(driver, attribute).getAttribute("data-ctl");

            if (fieldType.isEmpty() || fieldType == null) {
                logger.info("Filed is not data control");
            } else {
                fieldType = fieldType.replace("[\"", "").replaceAll("\"]", "");

                switch (fieldType) {
                    case "Dropdown":
                        logger.info("Filed Type is Dropdown");
                        logger.info("Filed Type is Dropdown");
                        break;
                    case "AutoCompleteAG":
                        logger.info("Filed type is AutoComplete box");
                        logger.info("Filed Type is AutoComplete box");
                        break;
                    case "DatePicker":
                        logger.info("Field type is Date Picker");
                        logger.info("Filed Type is Date Picker");
                        break;
                    case "TextInput":
                        logger.info("Filed type is Free text");
                        logger.info("Filed Type is Free text");
                        break;
                    default:
                        logger.info(fieldType);
                        break;
                }

            }
        } catch (Exception e) {
            logger.info(e);
        }

    }

    public void fieldFormValidationReadFromExcel(WebDriver driver, String attribute, String dataType) {

        try {
            String fieldType = getElement(driver, attribute).getAttribute("data-ctl");

            if (fieldType.isEmpty() || fieldType == null) {
                logger.info("Field is not data control");
            } else {
                fieldType = fieldType.replace("[\"", "").replaceAll("\"]", "");
                try {
                    if (fieldType.equalsIgnoreCase(dataType)) {
                        logger.info("Field type is" + fieldType);
                        logger.info("Field type is" + fieldType);
                    }

                } catch (Exception e) {
                    logger.info("Filed Type validation not match");
                }

            }
        } catch (Exception e) {
            logger.info(e);
        }

    }


    public void fieldLengthValidationReadFromExcel(WebDriver driver, String attribute, String length) {

        try {
            String fieldType = getElement(driver, attribute).getAttribute("maxlength");

            if (fieldType.isEmpty() || fieldType == null) {
                logger.info("Field lenght is not available");
            } else {
                try {
                    if (fieldType.equalsIgnoreCase(length)) {
                        logger.info("Field length is" + fieldType);
                        logger.info("Field length is" + fieldType);
                    }

                } catch (Exception e) {
                    logger.info("Filed length validation not match");
                }


            }
        } catch (Exception e) {
            logger.info("Filed length Not available" + e);
        }
    }

    public void fieldDisplayEditableValidation(WebDriver driver, String attribute, String displayValidation) {
        try {
            String displayed = getElement(driver, attribute).getAttribute("disabled");
            if (!displayed.isEmpty()) {
                logger.info("Filed displayed and Editable");
            } else if (displayed.equals(null)) {
                logger.info("Filed displayed but not Editable");
            }

        } catch (Exception e) {
            logger.info("Element Not available" + e);
        }


    }

    public boolean isElementEditable(WebDriver driver, String attribute) throws IOException {
        boolean editable = false;
        try {
            WebElement we = getExactAttribute(driver, attribute);
            if (!we.getAttribute("value").equals(null)) {
                editable = true;
            }
        } catch (Exception E) {

        }
        return editable;
    }

    public String editableFieldCheck(WebDriver driver, String attribute, String editable) throws IOException {

        String editableCheck = "false";

        if (wrap.isElementEditable(driver, attribute) && (editable.equalsIgnoreCase("Editable"))) {

            editableCheck = "true";
        } else if ((!wrap.isElementEditable(driver, attribute)) && ((editable.equalsIgnoreCase("Display")) || (editable.equalsIgnoreCase("Backend")))) {

            editableCheck = "true";
        }

        if(editableCheck.equalsIgnoreCase("false"))
        {
        	logger.info("Editable check is false");
        	try {
				String readOnlyValue = wrap.getTextValue(driver, attribute);
				logger.info("Value retrieved for readonly field is" + readOnlyValue );
				if(!readOnlyValue.equalsIgnoreCase(""))
				{
					logger.info("Text value for read only field is not blank");
				}
			} catch (InterruptedException e) {
				logger.error("Unable to retrieve text from editable field" + e);
			}
        	
        }
        
        return editableCheck;
    }

    public boolean isMandatoryField(WebDriver driver, String attribute, String moc) {

        logger.info("Expected Mandatory :" + moc);

        boolean isMandatory = false;
        boolean mandatoryResult = false;

        try {
            String value = getElement(driver, attribute).getAttribute("validationtype");

            if (value.contains("required")) {
                logger.info("The given field is mandatory");
                isMandatory = true;
            } else {
                logger.info("The given field is Not Mandatory");
            }
        } catch (Exception E) {
            logger.info("The given field is not Mandatory");
        }

        if ((isMandatory) && moc.equalsIgnoreCase("m")) {
            mandatoryResult = true;
        } else if ((isMandatory) && moc.equalsIgnoreCase("c")) {
            mandatoryResult = true;
        } else if ((!isMandatory) && moc.equalsIgnoreCase("o")) {
            mandatoryResult = true;
        }
        return mandatoryResult;

    }

    public boolean fieldObjectTypeVal(WebDriver driver, String attribute, String dataType) {

        boolean dataTypeMatched = false;

        logger.info("Expected Type :" + dataType);

        /* String fieldType1 = getElement(driver, attribute).getAttribute("type");
         logger.info(fieldType1);*/


        try {
            String fieldType = getElement(driver, attribute).getAttribute("data-ctl");
            logger.info(fieldType);

                /*if(fieldType.isEmpty()||fieldType.equals(null)){
                      logger.info("Field is not data control");
                }*/

            if (fieldType == null) {

                logger.info("Field is not data control");

                fieldType = getElement(driver, attribute).getAttribute("type");


                if ((fieldType.equalsIgnoreCase("radio")) && (dataType.equalsIgnoreCase("Radio Button"))) {
                    logger.info("field is radio button datatype");
                    dataTypeMatched = true;

                } else if ((fieldType.equalsIgnoreCase("checkbox")) && (dataType.equalsIgnoreCase("Checkbox"))) {
                    logger.info("field is checkbox datatype");
                    dataTypeMatched = true;

                }
            } else {
                fieldType = fieldType.replace("[\"", "").replaceAll("\"]", "");

                logger.info("Actual Type :" + fieldType);

                switch (fieldType) {
                    case "Dropdown":
                        if (dataType.equalsIgnoreCase("Dropdown")) {
                            dataTypeMatched = true;
                        }
                        break;

                    case "AutoCompleteAG":
                        if (dataType.equalsIgnoreCase("Dropdown")) {
                            dataTypeMatched = true;
                        }
                        break;

                    case "DatePicker":
                        if (dataType.equalsIgnoreCase("Calendar")) {
                            dataTypeMatched = true;
                        }
                        break;

                    case "TextInput":
                        if (dataType.equalsIgnoreCase("Free Text")) {
                            dataTypeMatched = true;
                        }
                        break;


                    default:

                        	 /* if(fieldType1.equalsIgnoreCase("radio") && dataType.equalsIgnoreCase("Radio button")){
                           		  logger.info("field will be radio button");
                           		  dataTypeMatched = true;
                           	  }
                           	  else{
                                    logger.info("Invalid data type");
                                   }*/
                        break;

                }


            }
        } catch (Exception e) {
            logger.info("Data type check failed");
            e.printStackTrace();
        }
        return dataTypeMatched;

    }


    public boolean fieldLenghtVal(WebDriver driver, String attribute, String length, String dataType) {

        boolean lengthMatched = false;

        try {
            String fieldType = getElement(driver, attribute).getAttribute("maxlength");

            logger.info("Actual Length :" + fieldType);

            if (fieldType.isEmpty() || fieldType == null) {
                logger.info("Field lenght is not available");
            } else if (length.contains(","))

            {
                String[] split = length.split(",");
                int sum = Integer.parseInt(split[0].replace(" ", "")) + Integer.parseInt(split[1].replace(" ", "")) + 1;
                if (Integer.parseInt(fieldType) == sum) {
                    logger.info("Expected Length : " + sum);
                    lengthMatched = true;
                }
            } else {
                if (Integer.parseInt(fieldType) == Integer.parseInt(length.replace(".0", ""))) {
                    lengthMatched = true;
                }
            }
        } catch (Exception e) {
            logger.info("Filed length Not available ");
        }

        return lengthMatched;

    }


    public boolean validateAlphanumeric(WebDriver driver, String attribute, String dataformat) throws InterruptedException {

        boolean validateAlphanumeric = false;

        WebElement ele = wrap.getElement(driver, attribute);

        String dataformat1 = getElement(driver, attribute).getAttribute("validationtype");
        logger.info("dataformat is " + dataformat1);

        if (dataformat.equalsIgnoreCase("NUMERIC") && dataformat1.contains("isnonnegative")) {

            logger.info("It should be Numeric");
            logger.info("It should be Numeric");
            validateAlphanumeric = true;
        } else {
            if (dataformat.equalsIgnoreCase("VARCHAR") && !dataformat1.contains("isnonnegative")) {

                logger.info("It should be VARCHAR");
                logger.info("It should be VARCHAR");
                validateAlphanumeric = true;
            } else {
                if (dataformat.equalsIgnoreCase("DATE") && dataformat1.contains("date")) {

                    logger.info("It should be DATE");
                    logger.info("It should be DATE");
                    validateAlphanumeric = true;
                }


            }
        }
        return validateAlphanumeric;

    }


    public boolean validateSingleMultiple(WebDriver driver, String attribute, String singlemulti, String fieldname) throws InterruptedException {

        boolean validateSingleMultiple = false;

        List<String> singmultilist = new ArrayList<String>();
        singmultilist.add("Full Name");
        singmultilist.add("Document Type");
        singmultilist.add("MIS Code");
        if (singlemulti.equalsIgnoreCase("M") && singmultilist.contains(fieldname)) {

            logger.info("It should be Multiple");
            logger.info("It should be Multiple");
            validateSingleMultiple = true;
        } else {

            logger.info("It should be SINGLE");
            logger.info("It should be SINGLE");
            validateSingleMultiple = true;

        }


        return validateSingleMultiple;


    }

    public boolean derivedVal(WebDriver driver, String attribute, String derived) throws IOException {

        boolean derivedCheck = false;

        try {

            WebElement we = getExactAttribute(driver, attribute);

            if (!isElementEditable(driver, attribute)) {

                if ((!(we.getText() == null))) {

                    if (derived.equalsIgnoreCase("Derived")) {

                        derivedCheck = true;

                    }

                }

            } else {

                if ((!(we.getText() == null))) {

                    if (we.getAttribute("data-ctl").contains("Dropdown")) {

                        logger.info("It is a Dropdown");

                        Select sel = new Select(we);
                        sel.getFirstSelectedOption();

                        if ((!sel.getFirstSelectedOption().getText().contains("Please Select"))) {

                            if ((derived.equalsIgnoreCase("User Input and Derived"))) {
                                derivedCheck = true;
                            }
                        } else {

                            if ((derived.equalsIgnoreCase("User Input"))) {
                                derivedCheck = true;
                            }
                        }
                    } else {

                        if (derived.equalsIgnoreCase("User Input and Derived")) {

                            derivedCheck = true;

                        }
                    }

                } else {

                    if (derived.equalsIgnoreCase("User Input")) {

                        derivedCheck = true;

                    }

                }

            }

        } catch (Exception E) {

            logger.info("Derived check failed");

        }

        return derivedCheck;

    }

    //To get the selected value from drop down
    public String SelectedValueDropDown(WebDriver driver, String attribute) throws InterruptedException {
        try {
            logger.info("Fetching SelectedValue from DropDown.. ");
            WebElement ele = getElement(driver, attribute);
            //logger.info("Webelement "+ele);
            Select allvalues = new Select(ele);
            //logger.info("allvalues "+allvalues);
            String value = allvalues.getFirstSelectedOption().getText();
            logger.info("Selected value " + value);
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    public void validateNumericInputBox(WebDriver driver, String locator, String field) throws IOException {
        String status = "Fail";
        String valueFromField = "";
        String defaultAlphaNumericValue = "12121289899876656565";
        String attribute = com.getElementProperties("Fulldatacapturemaker", locator);
        try {
            click(driver, attribute);
            wait(1000);
            clear(driver, attribute);
            type(driver, defaultAlphaNumericValue, attribute);
            wait(1000);
            //validation
            valueFromField = getElement(driver, attribute).getAttribute("value");
            if (valueFromField.matches("[0-9]+")) {
                logger.info(field + "accepts numeric value");
                logger.info("Fail");
            } else if (valueFromField.isEmpty()) {
                logger.info("Value is empty");
                logger.info("Pass");
                logger.info(field + "do not accept numeric value");
            }

        } catch (Exception e) {
            logger.info(status);
        }
    }

    public boolean validateFiledVisible1(WebDriver driver, String attribute) {
        boolean status = false;
        WebElement field;
        try {

            //validation
            field = getElement(driver, attribute);

            if (field.isDisplayed()) {
                status = true;
            }
            return status;
        } catch (Exception e) {
            logger.info("The section is not present");
        }
        return status;
    }

//To read the values and to check it is editable or not

    public void validateEditable(WebDriver driver, String FieldAttribute, String ValueAttribute, String name) {
        logger.info("Inside validateEditable method");
        List<WebElement> listOfFields = getElements(driver, FieldAttribute);
        List<WebElement> listOfValues = getElements(driver, ValueAttribute);
        int listOfFieldsSize = listOfValues.size();
        logger.info("List of fields:");
        for (int i = 0; i <= listOfFieldsSize - 1; i++) {
            String field = listOfFields.get(i).getText().replace("Required", "");
            String value = listOfValues.get(i).getText();

            if ((!field.isEmpty()) && (!value.isEmpty())) {
                logger.info(field + " :  " + value);
                if (value == null)
                    logger.info(field + " field is Editable");
                else
                    logger.info(field + " field is Non-Editable");

            }
        }
    }

    public boolean dataTypeVal(WebDriver driver, String attribute, String dataformat) throws IOException {
        boolean dataTypeCheck = false;
        try {
            WebElement we = getExactAttribute(driver, attribute);
            String valtype = getElement(driver, attribute).getAttribute("validationtype");

            logger.info("Expected format : " + dataformat);
            logger.info("dataformat is " + valtype);
            
            outer:
            if(valtype==null)
            {
            	valtype = getElement(driver, attribute).getAttribute("type");
            	if(valtype.equalsIgnoreCase("text") && dataformat.equalsIgnoreCase("alphabet"))
            		dataTypeCheck = true;
            	break outer;
            }

            if ((valtype.contains("isnonnegative")) || (valtype.contains("decimal")) || (valtype.contains("integer")) || valtype.contains("currencyformatmax")) {
                if (dataformat.equalsIgnoreCase("numeric")) {
                    dataTypeCheck = true;
                }
            } else if (valtype.contains("date")) {
                if (dataformat.equalsIgnoreCase("date")) {
                    dataTypeCheck = true;
                }
            } else {
                if (dataformat.equalsIgnoreCase("varchar")) {
                    dataTypeCheck = true;
                }
            }

        } catch (Exception E) {
            if (dataformat.equalsIgnoreCase("varchar")) {
                dataTypeCheck = true;
            }
        }
        return dataTypeCheck;
    }

    public static String convertIdtoXpath(String attribute) {

        logger.info(attribute);

        attribute = attribute.replace(" ", "");

        String xpath = "//*[@id='" + attribute.substring(3) + "']";

        return xpath;
    }

    public String verifySectionName(WebDriver driver, String attribute, String sectionName) {

        String sectionNameCheck = "false";

        if (attribute.trim().startsWith("id=") || attribute.trim().startsWith("id =")) {

            attribute = convertIdtoXpath(attribute);

        }

        String section = attribute + "/ancestor::div[@id='EXPAND-OUTERFRAME']//h2";

        logger.info("Expected Section: " + sectionName);
        logger.info("Actual Section: " + wrap.getExactAttribute(driver, section).getText());

        if (sectionName.equalsIgnoreCase(wrap.getExactAttribute(driver, section).getText())) {


            sectionNameCheck = "true";

        }
        return sectionNameCheck;
    }

    public String verifyTabName(WebDriver driver, String attribute, String tabname) {

        String tabNameCheck = "false";

        if (attribute.trim().startsWith("id=") || attribute.trim().startsWith("id =")) {

            attribute = convertIdtoXpath(attribute);

        }

        String tab = attribute + "/ancestor::div[@id='PEGA_TABBED0']//li[@aria-selected='true']//label";


        logger.info("Expected Tab: " + tabname);
        logger.info("Actual Tab: " + wrap.getExactAttribute(driver, tab).getText());


        if (tabname.equalsIgnoreCase(wrap.getExactAttribute(driver, tab).getText())) {


            tabNameCheck = "true";

        }
        return tabNameCheck;
    }


    public void TypeInSugBox(WebDriver driver, String Attribute, String value) throws InterruptedException {
        String val = value;
        WebElement element = wrap.getExactAttribute(driver, Attribute);
        element.click();
        element.clear();
        wrap.wait(1500);

        for (int i = 0; i < val.length(); i++) {
            char c = val.charAt(i);
            String s = new StringBuilder().append(c).toString();
            element.sendKeys(s);

        }
        wrap.wait(2000);
        wrap.getElement(driver, Attribute).sendKeys(Keys.DOWN);
        Thread.sleep(400);
        wrap.getElement(driver, Attribute).sendKeys(Keys.TAB);
        wrap.wait(7000);
    }
    

    public void validatevalue(WebDriver driver, String Attribute, String value) throws InterruptedException, IOException {
    	
    	System.out.println(Attribute);
    	if(wrap.isElementPresent(driver,Attribute))
    	{	
		logger.info("Field is present in the screen");
    	new WebDriverWait(driver, 6, 1000).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf(wrap.getExactAttribute(driver, Attribute)));
    	Attribute=wrap.getExactAttribute(driver, Attribute).getText();
    	if(Attribute.equalsIgnoreCase(value)){logger.info("value" +value+ "is in sync with UI");}
    	else{logger.info("value" +value+ " is not in sync with UI/would be NULL");}
    	
    	}
    	
    	else{
			
			logger.info("Field is not present in the screen");
		}
}

  //Younus - Updated 16-01-2018
    public static String getValue(WebDriver driver, String attribute) throws InterruptedException {

        String str = driver.findElement(By.xpath(attribute)).getAttribute("value");
        logger.info("String value is" + str);
        return str;


    }


    public void click_wait(WebDriver driver,String attribute) throws InterruptedException{
		try
		{
			wrap.wait(1000);
			System.out.println("Going to Click on the  ["+attribute+"] Field");
			new WebDriverWait(driver, 30).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(wrap.getExactAttribute(driver,attribute)));
			wrap.getExactAttribute(driver,attribute).click();
		}
		catch(StaleElementReferenceException e)
		{
			wrap.wait(1000);	
			wrap.getExactAttribute(driver,attribute).click();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void type_wait(WebDriver driver,String value,String Inattribute) throws InterruptedException
	{
		try
		{
			wrap.wait(1000);		
			System.out.println("The Value ["+value+"] going to Enter into the Field ["+Inattribute+"]");
			new WebDriverWait(driver, 30).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(wrap.getExactAttribute(driver,Inattribute)));
			wrap.getExactAttribute(driver,Inattribute).clear();
			wrap.wait(2000);		
			wrap.getExactAttribute(driver,Inattribute).sendKeys(value);
			System.out.println("Value ["+value+"] Entered Sucessfully");
		}
		catch(StaleElementReferenceException e)
		{
			wrap.wait(1000);	
			wrap.getExactAttribute(driver,Inattribute).clear();
			wrap.wait(2000);		
			wrap.getExactAttribute(driver,Inattribute).sendKeys(value);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void checkBoxSelect(WebDriver driver, String DBfieldName, String attribute ) throws InterruptedException, IOException {
		
    	String checkBox = DBfieldName;
        logger.info("The Check Box of "+DBfieldName+" Value is : " + checkBox);
        
        if (checkBox.equalsIgnoreCase("YES")) {
        		
            click(driver, attribute);
            
        } else {
            logger.info("No - Available in Database, Hence Check Box NOT selected !!!");
        }	
    }
	
	public void elementTabOut(WebDriver driver,String Attribute)
	{
		wrap.getElement(driver, Attribute).sendKeys(Keys.TAB);
	}
	
	 public void verifyDefaultValue(WebDriver driver, String attribute, String value) throws InterruptedException
	 {
		 String defaultValue =  driver.findElement(By.xpath(attribute)).getAttribute("value");
		 logger.info("Expected value is :" + value);
		 if(defaultValue.equalsIgnoreCase(value))
		 {
			 logger.info("Default value displayed is :" + defaultValue);
			 logger.info("Default value matches");
		 }else
		 {
			 logger.info("Default value displayed is :" + defaultValue);
			 logger.info("Default value mismatches with the displayed value");
			 Assert.fail();
		 }
	 }
}

	
