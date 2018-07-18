package test.utils;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import cucumber.api.java.en.Then;


import org.junit.Assert;

public class DBUtils {

    public static Logger logger = Logger.getLogger(DBUtils.class);

    public static int rowCount = 0;
    public static int columnCount = 0;

    public static Properties CONFIG;

    public static List<HashMap<String, Object>> testdatamap = new ArrayList<HashMap<String, Object>>();

    public static void main(String args[]) throws ClassNotFoundException, SQLException, IOException {


    }

    //@Then("^Convert Database into Map$")
    public static void convertDBtoMap(String tablename) throws ClassNotFoundException, SQLException, IOException {
        String envName = System.getProperty("env");
        URL resource = DBUtils.class.getClassLoader().getResource("config" + File.separator + envName + "Config.properties");
        File file = null;
        FileInputStream fis = null;
        try {
            file = new File(resource.toURI());
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }


        //File file = new File(System.getProperty("user.dir")+ File.separator + "src\\main\\java\\com\\standardchartered\\techm\\rtob\\config\\config.properties");
        //FileInputStream fis = new FileInputStream(file);;
        CONFIG = new Properties();
        CONFIG.load(fis);

        String dbUrl = CONFIG.getProperty("DataBaseURL");
        System.out.println(dbUrl);
        String username = CONFIG.getProperty("Username").trim();
        System.out.println(username);
        String password = CONFIG.getProperty("password").trim();
        System.out.println(password);
        //String query = "select * from "+tablename;
        String query = CONFIG.getProperty(tablename).trim();
        System.out.println("Query Results :" + query);

		
/*		String dbUrl ="jdbc:sqlserver://10.23.221.163:10501;databaseName=RTOB_TestAutomation_Data_ST;user=pega;password=Gridt00ls;";
		String username ="pega";	    					
		String password ="Gridt00ls";
		//String query = "insert into test11 values('Balaji')";
		String query = "select * from basicdata_sample";
		//String query = "Create table test12 (fname varchar(255))";
		//String query = "drop table test12";
*/
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        Connection con = DriverManager.getConnection(dbUrl, username, password);
        System.out.println("Connnected with DB");

        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData metadata = rs.getMetaData();
        int columnCount = metadata.getColumnCount();

        if (query.toLowerCase().contains("select")) {
            rowCount = 0;
            testdatamap.clear();
            while (rs.next()) {
                rowCount++;
                HashMap<String, Object> row = new HashMap<String, Object>(columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    //System.out.println(rs.getString(i));
                    row.put(metadata.getColumnName(i), rs.getObject(i));
                }
                testdatamap.add(row);

            }
        }
        con.close();

        //System.out.println(testdatamap);


    }


    private static String readColumn(String column1, int row) {

   
        HashMap<String, Object> map = testdatamap.get(row);
        String result = null;
        for (Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(readColumn(null, 0))) {
                System.out.println(entry.getValue());
				if(entry.getValue()==null)
                	result =null;
                else
					result = entry.getValue().toString();
            }

        }
        return result;
    }


    public static String readColumnWithRowID(String column, String rowID) {

        String result = null;
        try {
            for (int i = 0; i < rowCount; i++) {

                if (readColumn("Scenarioid", i).equalsIgnoreCase(rowID)) {
                    System.out.println("Match Success");
                    logger.info("matched with " + readColumn("Scenarioid", i));
                    result = readColumn(column, i);
                    break;
                }
            }
        } catch (IndexOutOfBoundsException I) {
            logger.info("Row ID didn't match");
            I.printStackTrace();
            Assert.fail();
        } catch (Exception E) {
            logger.info("Reading from Database has failed");
            E.printStackTrace();
            Assert.fail();
        }
        logger.info("result : " + result);
        return result;
    }
    
    public static String readColumn(String column, String rowID) {

        String result = null;
        try {
            for (int i = 0; i < rowCount; i++) {

                if (readColumn("Scenarioid", i).equalsIgnoreCase(rowID)) {
                    System.out.println("Match Success");
                    logger.info("matched with " + readColumn("Scenarioid", i));
                    result = column;
                    break;
                }
            }
        } catch (IndexOutOfBoundsException I) {
            logger.info("Row ID didn't match");
            I.printStackTrace();
            Assert.fail();
        } catch (Exception E) {
            logger.info("Reading from Database has failed");
            E.printStackTrace();
            Assert.fail();
        }
        logger.info("result : " + result);
        return result;
    }


}