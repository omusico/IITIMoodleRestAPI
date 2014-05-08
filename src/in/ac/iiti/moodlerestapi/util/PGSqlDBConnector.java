package in.ac.iiti.moodlerestapi.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class PGSqlDBConnector {
    //declaring class variables

    // attributes to be removed later
	private static Properties  propertyInstance = AppConfigProperty.getPropertyInstance();
    
	private static String url = propertyInstance.getProperty("acadPostGreServerUrl","jdgbc:postgresql://127.0.0.1:5432/");
	private static String dbName = propertyInstance.getProperty("acadDbName","acaddb");
	private static String driver = "org.postgresql.Driver";
	private static String userName = propertyInstance.getProperty("acadPostGreServerUsername","postgres"); 
	private static String password = propertyInstance.getProperty("acadPostGreServerPassword","harshit");
    
    public synchronized static Connection getConnection() {
        Connection con = null;
        try {
          Class.forName(driver);	
        } catch(ClassNotFoundException e) {
            System.out.println("log Could not load driver class "+e);
        }
        try {
              con= DriverManager.getConnection(url+dbName,userName,password);   
        } catch (SQLException e) {
            System.out.println("log Could not get connection "+e);
        }
        return con;
    }

    public synchronized static void closeDBConnection(Connection con)
    {
        if(con != null) {
            try{
                con.close();
                con = null;
            }
            catch (SQLException e) {
                System.out.println("log Could not close Connection "+e);
            }
        }
    }

    public synchronized static void closeStatement(Statement stmt) {
        if(stmt != null) {
            try{
                stmt.close();
                stmt = null;
            }
            catch (SQLException e) {
                System.out.println("log Could not close Statement "+e);
            }
        }
    }

    public synchronized static void closePStatement(PreparedStatement pstmt) {
        if(pstmt != null) {
            try{
                pstmt.close();
                pstmt = null;
            }
            catch (SQLException e) {
                System.out.println("log Could not close Prepared Statement "+e);
            }
        }
    }

    public synchronized static void closeResultSet(ResultSet res) {
        if(res != null) {
            try{
                res.close();
                res = null;
            }
            catch (SQLException e) {
                System.out.println("log Could not close ResultSet "+e);
            }
        }
    }
    public static synchronized boolean isClosed(Connection con) {
        boolean isClosed = false;
        try {
            if(con == null || con.isClosed()) {
                isClosed = true;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return isClosed;
    }
}