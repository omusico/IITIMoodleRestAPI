package in.ac.iiti.moodlerestapi.util;
import java.sql.*;
import java.util.Properties;
/**
 *
 * @author harshit
 */
public class MySqlDBConnector {
	
	static String driver = "com.mysql.jdbc.Driver";
	private static Properties  propertyInstance = AppConfigProperty.getPropertyInstance();
    
	private static String url = propertyInstance.getProperty("moodleMySqlUrl","jdbc:mysql://localhost:3306/");
	private static String dbName = propertyInstance.getProperty("moodleDbName","moodledb");
	private static String userName = propertyInstance.getProperty("moodleMySqlUsername","root"); 
	private static String password = propertyInstance.getProperty("moodleMySqlPassword","harshit");
    
    public synchronized static Connection getConnection() {
        Connection con = null;
        try {
          Class.forName("com.mysql.jdbc.Driver");	
        } catch(ClassNotFoundException e) {
            System.out.println("Could not load driver class "+e);
        }
        try {
              con= DriverManager.getConnection(url+dbName,userName,password);
                
        } catch (SQLException e) {
            System.out.println("Could not get connection "+e);
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
                System.out.println("Could not close Connection "+e);
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
                System.out.println("Could not close Statement "+e);
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
                System.out.println("Could not close Prepared Statement "+e);
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
                System.out.println("Could not close ResultSet "+e);
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