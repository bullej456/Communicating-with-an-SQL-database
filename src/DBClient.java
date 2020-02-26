package uk.co.diegesis.edward.GT3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import uk.co.diegesis.edward.GT3.DBClientManager.CatOrDog;

public class DBClient {
	
	public static final String MARIADB_DRIVER = "org.mariadb.jdbc.Driver";
	private Connection dbConnection = null;
	
	private String mariaDbUser = "root";
    private String mariaDbPassword = "root";
    private String dbUrl = "jdbc:mariadb://127.0.0.1:3306/edwarddb";
    
	
	public boolean connect() {
		
		boolean connected = false;

	    try {
	    	  //Instantiate the DB Driver class
    		  Class.forName(MARIADB_DRIVER);
	    } catch (Exception e) {
	    	  e.printStackTrace();
	    	  dbConnection = null;
	    }
	    
	    try {
	    	//Open the connection to the DB
			dbConnection = DriverManager.getConnection(dbUrl, mariaDbUser, mariaDbPassword);
	        dbConnection.setAutoCommit(false);
	        connected = true;
	        System.out.println("Connected.");
	        
		} catch (SQLException e) {
			dbConnection = null;
			e.printStackTrace();
		}
	    
	    return connected;
	}
	
	public ResultSet getProducts() {
		
		try {
	        String sql = "SELECT * FROM product";
			Statement stmt = dbConnection.createStatement();
	        return stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void addProducts(CatOrDog catOrDog, int weight) {
		
		try {
	        String sql = "INSERT INTO product (catOrDog, weight) VALUES (\"" + catOrDog + "\", " + weight + ");";
			Statement stmt = dbConnection.createStatement();
	        stmt.executeUpdate(sql);
	        dbConnection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;
	}
	
	public void updateProduct(int productID, int newWeight) {
		
		try {
	        String sql = "UPDATE product SET weight = " + newWeight + " WHERE productID = " + productID + ";";
			Statement stmt = dbConnection.createStatement();
	        stmt.executeUpdate(sql);
	        dbConnection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;
	}
	
	public void deleteProduct(int productID) {
		
		try {
	        String sql = "DELETE FROM product WHERE productID = " + productID + ";";
			Statement stmt = dbConnection.createStatement();
	        stmt.executeUpdate(sql);
	        dbConnection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;
	}
	
	
	public void disconnect() {
		
		if(dbConnection != null) {
			try {
				dbConnection.close();
				dbConnection = null;
				System.out.println("Disconnected.");
			} catch (SQLException e) {
				dbConnection = null;
				e.printStackTrace();
			}
		}
	}

}
