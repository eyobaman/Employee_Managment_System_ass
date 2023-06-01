package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class database {
	static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/employee";
	static final String DB_USER = "root";
	static final String DB_PASS = "";
    
    public static Connection connectDb(){
        
        try{
            
            Class.forName(DB_DRIVER);
            
            Connection connect = DriverManager.getConnection(DB_URL, DB_USER,DB_PASS);
            return connect;
        }catch(Exception e)
        {
        	e.printStackTrace();
        }
        return null;
    }

}
