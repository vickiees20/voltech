package test.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.log4j.Logger;


import static org.apache.poi.ss.usermodel.Row.MissingCellPolicy.RETURN_BLANK_AS_NULL;

public class Commons {
    public static Wrapper wrap = new Wrapper();
    public WebDriver driver;
    //public static RemoteWebDriver driver;
  

    private static HSSFSheet ExcelWSheet;

    private static HSSFWorkbook ExcelWBook;

    private static HSSFCell Cell;

    private static HSSFRow Row;
    private static Logger logger = Logger.getLogger(Commons.class);

    public static String getProperties(String properties) throws IOException {

        File proFile = new File("C:" + File.separator + "Genie_Workspace" + File.separator + "com.scb.appWorkflowSIT" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "appworkFlowRepository" + File.separator + "applogin.properties");
        FileInputStream fis = new FileInputStream(proFile);
        Properties pro = new Properties();
        pro.load(fis);
        String value = pro.getProperty(properties);
        return value;

    }

    public String getElementProperties(String proFileName, String propertivalue) throws IOException {
        File file = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "object repo" + File.separator + proFileName + ".properties");
        FileInputStream fis = new FileInputStream(file);
        Properties pro = new Properties();
        pro.load(fis);
        String value = pro.getProperty(propertivalue);
        return value;
    }


    public void selectInSuggestionBox(WebDriver driver, String elementAttibute, String country, String code) throws InterruptedException {
        String sugOp;
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wrap.type(driver, code, elementAttibute);
        String qot = "\"";
        sugOp = "//tr[@data-gargs='" + "[" + "\"" + country + "\"" + "," + "\"" + code + "\"" + "]']";
        System.out.println(sugOp);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(sugOp)));
            driver.findElement(By.xpath(sugOp)).click();
            Thread.sleep(500);
            //wrap.click(driver, sugOp);
        } catch (NoSuchElementException e) {
            wrap.type(driver, code, elementAttibute);
        }

    }


    /**
     * @param driver
     * @param attribute=              the attribute of the element to validate
     * @param fieldValue=alphaNumeric value passed,if passed as null the default value is entered
     * @return
     */
    public String validateAlphaNumeric(WebDriver driver, String attribute, String fieldValue) {
        //long waitTime=3;
        //wrap.waitForElementVisibility(driver, attribute, waitTime);
        String status = "Fail";
        String valueFromField = "";
        String defaultAlphaNumericValue = "d123334324wsw113232123232331213sdfffcvvfvnjnnnndfdffgkfnknknmmmmmmmmsddsffddfdfffgfsxcscscsddvsvsfvsffvsfdvfvversadasdadadasdadsadasdasdafdf1213212";

        try {
            if (fieldValue.isEmpty()) {
                wrap.type(driver, defaultAlphaNumericValue, attribute);
            } else {
                wrap.type(driver, fieldValue, attribute);
            }
            //validation
            valueFromField = wrap.getElement(driver, attribute).getAttribute("value");//gets the value from an inputbox
            if (valueFromField.matches("[A-Za-z0-9]+")) {
                return status = "Pass";
            }

        } catch (Exception e) {
            return status + "," + e.toString();
        }
        return status;
    }

    public String validateText(WebDriver driver, String attribute, String fieldValue) {
        String status = "Fail";
        String valueFromField = "";
        String defaultAlphaNumericValue = "testtextqwertyuiqweqwesqdqsdffsdfsdfsgsgsddgfsdsdsdgsdgsgsdgdsgsdgsfgfggfaasdadasdasdassasdsdasadasdasdasdasdasdsasadssadsdsdasdasdsadasdasdasdasdasd";

        try {
            if (fieldValue.isEmpty()) {
                wrap.type(driver, defaultAlphaNumericValue, attribute);
            } else {
                wrap.clear(driver, attribute);
                wrap.type(driver, fieldValue, attribute);
            }
            //validation
            valueFromField = wrap.getElement(driver, attribute).getAttribute("value");
            if (valueFromField.matches("[A-Za-z]+")) {
                return status = "Pass";
            }

        } catch (Exception e) {
            return status + "," + e.toString();
        }
        return status;
    }

    public String validateNumericInputBox(WebDriver driver, String attribute, String fieldValue) {
        String status = "Fail";
        String valueFromField = "";
        String defaultAlphaNumericValue = "12121289899876656565455454454122222222222221321333333333333333333332433333333333333333333333334";

        try {
            if (fieldValue.isEmpty()) {
                wrap.type(driver, defaultAlphaNumericValue, attribute);
            } else {
                wrap.type(driver, fieldValue, attribute);
            }
            //validation
            valueFromField = wrap.getElement(driver, attribute).getAttribute("value");
            if (valueFromField.matches("[0-9]+")) {
                return status = "Pass";
            }

        } catch (Exception e) {
            return status + "," + e.toString();
        }
        return status;
    }


    public String validateDataLength(WebDriver driver, String attribute, String fieldValue, String fieldLength) {
        String status = "Fail";
        String valueFromField = "";
        int fielLen = Integer.parseInt(fieldLength);
        try {

            //validation
            valueFromField = wrap.getElement(driver, attribute).getAttribute("value");
            if (valueFromField.length() == fielLen) {
                return status = "Pass";
            }

        } catch (Exception e) {
            return status + "," + e.toString();
        }
        return status;
    }

    public String validateFiledVisible(WebDriver driver, String attribute) {
        String status = "Fail";
        WebElement field;
        try {

            //validation
            field = wrap.getElement(driver, attribute);
            if (field.isDisplayed()) {
                status = "Pass";
            }
            return status;
        } catch (Exception e) {
            status = status + e.toString();
            return status;
        }

    }


    public String validateDropdown(WebDriver driver, String attribute, String referenceValue) {
        WebElement selectBox;
        String status = "False";

        try {
            selectBox = wrap.getElement(driver, attribute);
            List<WebElement> options = selectBox.findElements(By.xpath("//option"));
            for (WebElement option : options) {
                if (option.getText().equalsIgnoreCase(referenceValue)) {
                    wrap.selectFromDropDown(driver, "id=" + attribute, referenceValue, "BYVISIBLETEXT");
                }
                return status;
            }

        } catch (Exception e) {
            return status + "," + e.toString();
        }
        return status;

    }

    public String validateSuggestionTextBox(WebDriver driver, String attribute, String referenceValue) {
        String status = "Fail";
        String valueFromField = "";
        try {

            wrap.typeInSuggestionTextbox(driver, attribute, "Code", referenceValue);
            valueFromField = wrap.getElement(driver, attribute).getAttribute("value");
            //validation
            if (valueFromField.equalsIgnoreCase(referenceValue)) {
                return status = "Pass";
            }
        } catch (Exception e) {
            return status + "," + e.toString();
        }
        return status;

    }


    public String validatErrorMsg(WebDriver driver, String attribute, String errorMsg) {
        String status = "Fail";
        WebElement field;
        String blankError;
        try {

            //validation
            field = wrap.getElement(driver, attribute);
            blankError = field.findElement(By.xpath("//parent::span//div/span")).getText();
            if (blankError.equalsIgnoreCase(errorMsg)) {
                status = "Pass";
            }
            return status;
        } catch (Exception e) {
            status = status + e.toString();
            return status;
        }
    }


    /**
     * @param driver
     * @param attribute
     * @param referenceValue= a list of values are cheked whether is inside drop down
     * @return
     */
    public String validateDropDown(WebDriver driver, String attribute, ArrayList<String> referenceValue) {
        WebElement selectBox;
        //ArrayList<String>optionsMissing = new ArrayList<String>();
        //ArrayList<String>exception = new ArrayList<String>();
        String status = "Fail";
        try {
            selectBox = wrap.getElement(driver, attribute);
            List<WebElement> options = selectBox.findElements(By.xpath("//option"));
            for (String lov : referenceValue) {
                for (WebElement option : options) {
                    option.getText().equalsIgnoreCase(lov);
                    //
                }
            }
        } catch (Exception e) {
            return status + ", " + e.toString();
        }

        return status;
    }


    /**
     * @param driver
     * @param attribute
     * @param referenceValue,list of values is typed into the suggestion text box
     * @return
     */
    public String validateSuggestionTextBox(WebDriver driver, String attribute, ArrayList<String> referenceValue) {
        String status = "Fail";
        String valueFromField = "";
        try {
            for (String value : referenceValue) {
                wrap.typeInSuggestionTextbox(driver, attribute, "Description", value);
                valueFromField = wrap.getElement(driver, attribute).getAttribute("value");
                //validation
                if (valueFromField.equalsIgnoreCase(value)) {
                    //return status="Pass";
                }
            }
        } catch (Exception e) {
            return status + "," + e.toString();
        }
        return status;

    }

    public String validateFiledEnable(WebDriver driver, String attribute) {
        String status = "Fail";
        WebElement field;
        try {

            //validation
            field = wrap.getElement(driver, attribute);
            if (field.isEnabled()) {
                status = "Pass";
            }
            return status;
        } catch (Exception e) {
            status = status + e.toString();
            return status;
        }

    }



    public void IsMandatory(WebDriver driver, String attribute, String Name) throws InterruptedException {
        WebElement ele = wrap.getElement(driver, attribute);
        //System.out.println()
        if ((ele.isDisplayed())) {
            System.out.println(Name + " is mandatory");
        } else {
            System.out.println(Name + " is not mandatory");
        }
    }


    public void validateEditable1(WebDriver driver, String attribute, String name) throws InterruptedException {
        //String name1=attribute.substring(attribute.indexOf(arg0, arg1))

        WebElement ele = wrap.getElement(driver, attribute);
        //System.out.println()
        if (!(ele.isDisplayed())) {
            if (wrap.scroll_to(driver, attribute)) {
                System.out.println(name + " element is  present in screen after scrolling");
            } else
                System.out.println(name + " element is  not present in screen after scrolling");

        }


        //Thread.sleep(2000);
        if ((ele.isDisplayed())) {
            //WebElement ele=driver.findElement(By.xpath(attribute));
            String tagName = ele.getTagName();
            //System.out.print(tagName+" ");
            String text_old;

            if (tagName.equalsIgnoreCase("select")) {
                try {
                    //if(ele.isEnabled())
                    Select sel = new Select(ele);
                    if (ele.isEnabled())
                        System.out.println(name + " dropdown is editable ");
                    else
                        System.out.println(name + "dropdown is non  editable ");

                } catch (Exception E) {
                    System.out.println(name + " dropdown is non  editable ");
                }

            } else if (tagName.equalsIgnoreCase("button")) {
                text_old = ele.getText();
                try {
                    //if(ele.isEnabled())

                    if (ele.isEnabled())
                        System.out.println(name + " " + text_old + " button is editable ");
                    else
                        System.out.println(name + " " + text_old + " button is non  editable ");

                } catch (Exception E) {
                    //System.out.println("dropdown is non  editable ");
                }

            } else if (tagName.equalsIgnoreCase("input")) {
                text_old = ele.getText();
                try {
                    //if(ele.isEnabled())

                    if (ele.isEnabled()) {
                        if (ele.getText().isEmpty())
                            System.out.println(name + " data is blank");
                        System.out.println(name + " " + text_old + " text box is editable ");
                    } else {
                        if (ele.getText().isEmpty())
                            System.out.println(name + " data is blank");
                        System.out.println(name + " " + text_old + " text box is non  editable ");
                    }

                } catch (Exception E) {
                    //System.out.println("dropdown is non  editable ");
                }

            } else {
                text_old = ele.getText();
                if (ele.isEnabled()) {

                    try {

                        ele.sendKeys("sample");
                        if (ele.getAttribute("Value").equals("sample")) {
                            if (ele.getText().isEmpty())
                                System.out.println(name + " data is blank");
                            System.out.println(name + " " + text_old + " is editable inside ");
                        } else {
                            if (ele.getText().isEmpty())
                                System.out.println(name + " data is blank");
                            System.out.println(name + " " + text_old + " is  non editable inside");
                        }

                    } catch (Exception e) {

                        System.out.println(name + " " + text_old + " is  non editable");

                    }
                } else
                    System.out.println(name + " " + text_old + " is  non editable");

            }
        } else
            System.out.println(name + " element is not present in screen");


    }


    public void validateAlphanumeric(WebDriver driver, String attribute, String name) throws InterruptedException {


        WebElement ele = wrap.getElement(driver, attribute);

        String text_old = "";

        if (!ele.isDisplayed()) {
            if (wrap.scroll_to(driver, attribute)) {
                System.out.println(name + " element is  present in screen after scrolling");
            } else
                System.out.println(name + " element is  not present in screen after scrolling");
            //WebElement scrollArea = driver.findElement(By.cssSelector("div.slimScrollBar"));
            //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
            //Thread.sleep(5000);
            //wrap.waitForElementVisibility(driver, attribute, 10);*/
        }
        if (ele.getTagName().matches("input"))
            text_old = wrap.getElement(driver, attribute).getAttribute("value");
        else if (ele.getTagName().equalsIgnoreCase("select"))
            System.out.println(name + " is a drop down");

        else
            text_old = ele.getText();
        if (text_old.isEmpty())
            System.out.println(name + "  is empty");
        else {
            String pattern = "^[a-z A-Z 0-9]*$";
            if (text_old.matches(pattern)) {

                System.out.println(name + " " + text_old + " is alphanumeric" + "It should be Alphabets");
                logger.info(name + " " + text_old + " is alphanumeric" + "It should be Alphabets");
                logger.info("Text should be VARCHAR");


            } else
                System.out.println(name + " " + text_old + " is not  alphanumeric");
            logger.info(name + " " + text_old + " is numeric" + "It should be numeric");
            logger.info("Text should be NUMERIC");
        }


    }


    public void readTableValues(WebDriver driver, String Attribute) {
        List<WebElement> listOfValues = wrap.getElements(driver, Attribute);
        for (WebElement value : listOfValues) {
            System.out.println(value.getText());
        }
    }


    public void seeAllInWorkbasket1(WebDriver driver, String workItem) throws IOException, InterruptedException {
    

        wrap.click(driver, "//a[text()='See all']");
        Thread.sleep(3000);
        wrap.click(driver, "id=CheckerReview");//by id
        Thread.sleep(5000);
        //wrap.waitForElementVisibility(driver, "//button[@id='ModalButtonSubmit']", 3);
        wrap.click(driver, "//div[@class='modal-overlay']//button[@id='ModalButtonSubmit']");
        return;
    }

    public boolean validateDropdown1(WebDriver driver, String attribute, String referenceValue) {
        WebElement selectBox = wrap.getExactAttribute(driver, attribute);
        boolean status = false;
        Select select = new Select(selectBox);

        try {
            List<WebElement> options = select.getOptions();
            for (WebElement we : options) {
                if (we.getText().equals(referenceValue)) {
                    status = true;
                }
            }
        } catch (Exception e) {
            System.out.println("value not present in dropdown");
        }
        return status;

    }

    public String readText(WebDriver driver, String attribute) {
        String text;
        WebElement field;
        field = wrap.getElement(driver, attribute);
        text = field.getText();
        return text;
    }

    public void validateEditable2(WebDriver driver, String attribute, String name) throws InterruptedException {
        //String name1=attribute.substring(attribute.indexOf(arg0, arg1))

        WebElement ele = wrap.getElement(driver, attribute);
        //System.out.println()
        if (!(ele.isDisplayed())) {
            if (wrap.scroll_to(driver, attribute)) {
                System.out.println(name + " element is  present in screen after scrolling");
            } else
                System.out.println(name + " element is  not present in screen after scrolling");

        }


        //Thread.sleep(2000);
        if ((ele.isDisplayed())) {
            //WebElement ele=driver.findElement(By.xpath(attribute));
            String tagName = ele.getTagName();
            System.out.print(tagName + " ");
            String text_old;

            if (tagName.equalsIgnoreCase("select")) {
                try {
                    //if(ele.isEnabled())
                    Select sel = new Select(ele);
                    if (ele.isEnabled())
                        System.out.println(name + " dropdown is editable ");
                    else
                        System.out.println(name + "dropdown is non  editable ");

                } catch (Exception E) {
                    System.out.println(name + " dropdown is non  editable ");
                }

            } else if (tagName.equalsIgnoreCase("button")) {
                text_old = ele.getText();
                try {
                    //if(ele.isEnabled())

                    if (ele.isEnabled())
                        System.out.println(name + " " + text_old + " button is editable ");
                    else
                        System.out.println(name + " " + text_old + " button is non  editable ");

                } catch (Exception E) {
                    //System.out.println("dropdown is non  editable ");
                }

            } else if (tagName.equalsIgnoreCase("input")) {
                text_old = ele.getText();
                try {
                    //if(ele.isEnabled())

                    if (ele.isEnabled()) {
                        if (ele.getText().isEmpty())
                            System.out.println(name + " data cannot be blank");
                        System.out.println(name + " " + text_old + " text box is editable ");
                    } else {
                        if (ele.getText().isEmpty())
                            System.out.println(name + " data is blank");
                        System.out.println(name + " " + text_old + " text box is non  editable ");
                    }

                } catch (Exception E) {
                    //System.out.println("dropdown is non  editable ");
                }

            } else {
                text_old = ele.getText();
                if (ele.isEnabled()) {

                    try {

                        ele.sendKeys("sample");
                        if (ele.getAttribute("Value").equals("sample")) {
                            if (ele.getText().isEmpty())
                                System.out.println(name + " data is blank");
                            System.out.println(name + " " + text_old + " is editable inside ");
                        } else {
                            if (ele.getText().isEmpty())
                                System.out.println(name + " data is blank");
                            System.out.println(name + " " + text_old + " is  non editable inside");
                        }

                    } catch (Exception e) {

                        System.out.println(name + " " + text_old + " is  non editable");

                    }
                } else
                    System.out.println(name + " " + text_old + " is  non editable");

            }
        } else
            System.out.println(name + " element is not present in screen");


    }

    public void validateAlphanumeric1(WebDriver driver, String attribute, String name) throws InterruptedException {
        WebElement ele = wrap.getElement(driver, attribute);

        String text_old = null;
        if (!ele.isDisplayed()) {
            if (wrap.scroll_to(driver, attribute)) {
                System.out.println(name + " element is  present in screen after scrolling");
            } else
                System.out.println(name + " element is  not present in screen after scrolling");
            //WebElement scrollArea = driver.findElement(By.cssSelector("div.slimScrollBar"));
            //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
            //Thread.sleep(5000);
            //wrap.waitForElementVisibility(driver, attribute, 10);*/
        }
        if (ele.getTagName().matches("input"))
            text_old = wrap.getElement(driver, attribute).getAttribute("value");
        else if (ele.getTagName().equalsIgnoreCase("select"))
            System.out.println(name + " is a drop down");

        else
            text_old = ele.getText();
        if (text_old.isEmpty())
            System.out.println(name + "  is empty");
        else {
            String pattern = "^[a-z A-Z 0-9]*$";
            if (text_old.matches(pattern))

                System.out.println(name + " " + text_old + " is alphanumeric");
            else
                System.out.println(name + " " + text_old + " is not  alphanumeric");
        }

    }

    // Balaji 4/25/2017
    public void suggestionTextBox(WebDriver driver, String elementAttibute, String code, String description) throws InterruptedException {


        //System.out.println("code:" + code);
        //System.out.println("Description:" + description);
        try {
            //System.out.println("//tr[@data-gargs='[\"" + code + "\"" + "," + "\"" + description + "\"]']']");
            //wrap.fieldClick(driver, elementAttibute);
            wrap.type(driver, description, elementAttibute);
            //wrap.fluentWait(driver,"//span[text()='"+description+"']");
            //By.xpath("//tr[@data-gargs='[\"" + code + "\"" + "," + "\"" + description + "\"]']")
            //new WebDriverWait(driver, 20).until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(By.xpath("//tr[@data-gargs='[\"" + code + "\"" + "," + "\"" + description + "\"]']"))));
            //driver.findElement(By.xpath("//tr[@data-gargs='[\"" + code + "\"" + "," + "\"" + description + "\"]']")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  //Younus - Updated 14-01-2018
  	public void suggestionTextBox_CodeDesc(WebDriver driver,String elementAttibute,String code, String description) throws InterruptedException{
  			
  			
  			System.out.println("code:"+ code);
  			System.out.println("Description:"+description);
  			
  		try{
  			
  			System.out.println("//tr[@data-gargs='[\""+code+"\""+","+"\""+description+"\"]']");
  			wrap.click_wait(driver, elementAttibute);
  			wrap.wait(1000);
  			wrap.type_wait(driver,description,elementAttibute);
  			wrap.wait(3000);
  			driver.findElement(By.xpath( "//tr[@data-gargs='[\""+code+"\""+","+"\""+description+"\"]']")).click();
  			
  		} catch(NoSuchElementException e){
  		
  			System.out.println("//tr[@data-gargs='[\""+description+"\""+","+"\""+code+"\"]']");
  			wrap.click_wait(driver, elementAttibute);
  			wrap.wait(1000);
  			wrap.type_wait(driver,description,elementAttibute);
  			wrap.wait(2000);
  			driver.findElement(By.xpath( "//tr[@data-gargs='[\""+description+"\""+","+"\""+code+"\"]']")).click();
  		}
  	}
  	
  	public void suggestionTextBox_Code(WebDriver driver,String elementAttibute,String code, String description) throws InterruptedException{
			
			
			System.out.println("code:"+ code);
			System.out.println("Description:"+description);
			
		try{
			
			System.out.println("//tr[@data-gargs='[\""+code+"\""+","+"\""+description+"\"]']");
			wrap.click_wait(driver, elementAttibute);
			wrap.wait(1000);
			wrap.type_wait(driver,code,elementAttibute);
			wrap.wait(3000);
			driver.findElement(By.xpath( "//tr[@data-gargs='[\""+code+"\""+"]']")).click();
			
		} catch(NoSuchElementException e){
		
			System.out.println("//tr[@data-gargs='[\""+description+"\""+","+"\""+code+"\"]']");
			wrap.click_wait(driver, elementAttibute);
			wrap.wait(1000);
			wrap.type_wait(driver,code,elementAttibute);
			wrap.wait(2000);
			driver.findElement(By.xpath( "//tr[@data-gargs='[\""+code+"\""+"]']")).click();
		}
	}


    // Balaji 4/25/2017
    public void mobileSuggestionTextBox(WebDriver driver, String elementAttibute, String code, String description) throws InterruptedException {


        System.out.println("code:" + code);

        System.out.println("//tr[@data-gargs='[\"" + code + "\"]']");
        wrap.click(driver, elementAttibute);
        wrap.type(driver, description, elementAttibute);
        //wrap.fluentWait(driver,"//span[text()='"+description+"']");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//tr[@data-gargs='[\"" + code + "\"]']")).click();


    }

    public boolean verifyTextBoxThnClick(WebDriver driver, String xpath) throws InterruptedException {
        try {
            wrap.fluentWait(driver, xpath);
            wrap.click(driver, xpath);

            while (!wrap.getElement(driver, xpath).isEnabled()) {
                //Thread.sleep(1000);
                if (!wrap.getElement(driver, xpath).isEnabled()) {
                    wrap.getElement(driver, xpath).click();
                }
            }
            return true;
        } catch (InvalidElementStateException e) {
            System.out.println(e);
            System.out.println(xpath);
            verifyTextBoxThnClick(driver, xpath);
            return true;
        }
    }


    public static void setExcelFile(String Path, String SheetName) throws Exception {

        try {

            // Open the Excel file

            FileInputStream ExcelFile = new FileInputStream(Path);

            // Access the required test data sheet

            ExcelWBook = new HSSFWorkbook(ExcelFile);

            ExcelWSheet = ExcelWBook.getSheet(SheetName);

        } catch (Exception e) {

            throw (e);

        }

    }


    //This method is to write in the Excel cell, Row num and Col num are the parameters

    public static void setCellData(String Result, int RowNum, int ColNum) throws Exception {

        try {

            Row = ExcelWSheet.getRow(RowNum);

            Cell = Row.getCell(ColNum, RETURN_BLANK_AS_NULL);

            if (Cell == null) {

                Cell = Row.createCell(ColNum);

                Cell.setCellValue(Result);

            } else {

                Cell.setCellValue(Result);

            }

            // Constant variables Test Data path and Test Data file name

            FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "ExcelData" + File.separator + "ProductCatalogue_Testdata_Sheet1.xls");

            ExcelWBook.write(fileOut);

            fileOut.flush();

            fileOut.close();

        } catch (Exception e) {

            throw (e);

        }

    }

   public void suggestionTextBox2(WebDriver driver, String elementAttibute, String code, String description) throws InterruptedException {


        System.out.println("code:" + code);
        System.out.println("Description:" + description);

        System.out.println("//tr[contains(@data-gargs,'" + code + "')]");
        wrap.click(driver, elementAttibute);
        wrap.clear(driver, elementAttibute);
        wrap.click(driver, elementAttibute);
        wrap.type(driver, code, elementAttibute);
//        wrap.typeToTextBox(driver, code, elementAttibute);
        wrap.wait(1000);
//        wrap.waitForElementVisibility(driver, "//tr[contains(@data-gargs,'" + code + "')]/td[1]", 1000);
        System.out.println("Selecting Suggested Product");     
        driver.findElement(By.xpath("//tr[contains(@data-gargs,'" + code + "')]/td[1]")).click();
        System.out.println("Selected Suggested Product"); 
              
    }
    
   
    
    public void suggestionTextBox4(WebDriver driver, String elementAttibute, String code, String description) throws InterruptedException {


        System.out.println("code:" + code);
        System.out.println("Description:" + description);

        System.out.println("//tr[@data-gargs='[\"" + description + "\"" + "," + "\"" + code + "\"]']");
        wrap.click(driver, elementAttibute);
        //wrap.clear(driver, elementAttibute);
       // wrap.type(driver, description, elementAttibute);
        wrap.typeToTextBox(driver, description, elementAttibute);
        //wrap.fluentWait(driver,"//span[text()='"+description+"']");
        //Thread.sleep(2000);
        new WebDriverWait(driver, 60, 1000).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(wrap.getExactAttributeBY(elementAttibute)));
        //driver.findElement(By.xpath("//tr[@data-gargs='[\"" + description + "\"" + "," + "\"" + code + "\"]']")).click();
        driver.findElement(By.xpath("//tr[contains(@data-gargs,'"+ code +"')]")).click();
    }

    public String validateDateFormat(WebDriver driver, String attribute, String date) {
              /*  StringBuffer sBuffer = new StringBuffer(date);
		        String mon;
		        String dd;
		        String year;
		        String status="Fail";
		        String valueFromField="";

		        // Store the Date in String Buffer and Break down the date
		        mon = sBuffer.substring(0,2);
		        dd = sBuffer.substring(3,5);
		        year = sBuffer.substring(6,10);
		        System.out.println("DD: "+ dd + " Mon: "+ mon + " Year: "+ year);*/
        String status = "Fail";
        String valueFromField = "";
        try {
            wrap.type(driver, date, attribute);
            valueFromField = wrap.getElement(driver, attribute).getAttribute("value");
            System.out.println("valueFromField ==== " + valueFromField);
            StringBuffer sBuffer = new StringBuffer(valueFromField);
            String mon;
            String dd;
            String year;

            // Store the Date in String Buffer and Break down the date
            dd = sBuffer.substring(0, 2);
            mon = sBuffer.substring(3, 5);
            year = sBuffer.substring(6, 10);
            System.out.println("DD: " + dd + " Mon: " + mon + " Year: " + year);
            // Validating Date Format for Tunr In Date and Live Date to be MM/dd/yyyy
            if (mon.matches("0[1-9]|1[0-2]") && dd.matches("0[1-9]|[12][0-9]|3[01]")
                    && year.matches("(19|20)" + File.separator + "d" + File.separator + "d")) {
                System.out.println("validateDateFormat" + "=" + "Pass");
                return status = "Pass";
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("validateDateFormat" + "=" + "Fail");
        return status;

    }

    public void tab(WebDriver driver, String Inattribute) throws InterruptedException {


        wrap.getExactAttribute(driver, Inattribute).sendKeys(Keys.TAB);
        System.out.println("Tab into the Field [" + Inattribute + "]");
        wrap.wait(1000);
    }

    public void validateIsNotEmptyDropDown(WebDriver driver, String attribute, String name) throws InterruptedException {


        WebElement ele = wrap.getElement(driver, attribute);

        Select allvalues = new Select(ele);
        String value = allvalues.getFirstSelectedOption().getText();


        if (!value.isEmpty()) {
            System.out.println(name + " Data avaiable:" + value);

        } else {
            System.out.println(name + " Data is blank");
            Assert.fail();
        }
    }

    public void validateIsEmpty(WebDriver driver, String attribute, String name) throws InterruptedException {


        WebElement ele = wrap.getElement(driver, attribute);


        String value = ele.getAttribute("value");


        if (value.isEmpty()) {
            System.out.println(name + " Data is blank");
        } else {
            System.out.println(name + " Data avaiable:" + value);
            Assert.fail();
        }
    }

    public void validateIsEmptyDropDown(WebDriver driver, String attribute, String name) throws InterruptedException {


        WebElement ele = wrap.getElement(driver, attribute);

        Select allvalues = new Select(ele);
        String value = allvalues.getFirstSelectedOption().getText();


        if (value.isEmpty()) {

            System.out.println(name + " Data is blank");
        } else {
            System.out.println(name + " Data avaiable:" + value);

            Assert.fail();
        }
    }

    public void validateIsNotEmpty(WebDriver driver, String attribute, String name) throws InterruptedException {


        WebElement ele = wrap.getElement(driver, attribute);


        String value = ele.getAttribute("value");


        if (!value.isEmpty()) {
            System.out.println(name + " Data avaiable:" + value);

        } else {
            System.out.println(name + " Data is blank");
            Assert.fail();
        }
    }

    public void validateDropDownDerived(WebDriver driver, String attribute, String name) throws InterruptedException {


        WebElement ele = wrap.getElement(driver, attribute);

        Select allvalues = new Select(ele);
        String value = allvalues.getFirstSelectedOption().getText();


        System.out.println(name + ": Derived the value:" + value);


    }

    public void validateUserInputText(WebDriver driver, String attribute, String name) throws InterruptedException {


        WebElement ele = wrap.getElement(driver, attribute);


        String value = ele.getText();


        if (value.isEmpty()) {
            System.out.println(name + ": Data is user input");
        } else {
            System.out.println(name + ": Data is Derived:" + value);
            Assert.fail();
        }

    }


    public void validateUserInput(WebDriver driver, String attribute, String name) throws InterruptedException {


        WebElement ele = wrap.getElement(driver, attribute);


        String value = ele.getAttribute("value");


        if (value.isEmpty()) {
            System.out.println(name + ": is User Input");
        } else {
            System.out.println(name + ": is derived:" + value);
            Assert.fail();
        }
    }


    public void validateDerived(WebDriver driver, String attribute, String name) throws InterruptedException {


        WebElement ele = wrap.getElement(driver, attribute);


        String value = ele.getAttribute("value");


        if (!value.isEmpty()) {
            System.out.println(name + ": Data is derived");
        } else {
            System.out.println(name + ": Data is user input:" + value);
            Assert.fail();
        }
    }


    public void validateDerivedText(WebDriver driver, String attribute, String name) throws InterruptedException {


        WebElement ele = wrap.getElement(driver, attribute);


        String value = ele.getText();


        if (!value.isEmpty()) {
            System.out.println(name + ": Data is derived:" + value);
        } else {
            System.out.println(name + ": Data is user input");
            Assert.fail();


        }
    }

    public static void validateFiledVisible1(WebDriver driver, String attribute) {
        WebElement field;
        field = wrap.getElement(driver, attribute);
        if (field.isDisplayed()) {
            logger.info("Attribute" + attribute + "is present");
        } else {
            logger.info("Attribute" + attribute + "is not present");
        }
        //return attribute;

    }

    public void typetoSuggestionTextBoxUsingCode(WebDriver driver, String elementAttibute, String code, String description) throws InterruptedException {


        System.out.println("code:" + code);
        System.out.println("Description:" + description);

        System.out.println("//tr[contains(@data-gargs,'" + code + "')]");
        wrap.click(driver, elementAttibute);
        wrap.clear(driver, elementAttibute);
        wrap.click(driver, elementAttibute);
        wrap.typeToTextBox(driver, code, elementAttibute);
        System.out.println("Selecting Suggested Product");
        //wrap.wait(2000);
        
        new WebDriverWait(driver, 60, 1000).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//tr[contains(@data-gargs,'" + code + "')]/td[1]/div/div/div/span/span"))));
        wrap.click(driver, "//tr[contains(@data-gargs,'" + code + "')]/td[1]/div/div/div/span/span");
        System.out.println("Selected Suggested Product"); 
              
    }

}
