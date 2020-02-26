package uk.co.diegesis.edward.GT3;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class DBClientManager {
	
	private DBClient dbc = null;
	
	public enum CatOrDog{
		CAT, DOG
	}
	
	public DBClientManager() {
		
		//Creates instance of the client and connects.
		dbc = new DBClient();
		if(dbc.connect()) {
			
			showTable();
			System.out.println(" ");
			
			addCustomer(CatOrDog.DOG);
			
			removeCustomer(1);
			
			replaceCustomerProduct(2);
			
			showTable();
			
//			//Adds a line to the DB table.
//			dbc.addProducts(CatOrDog.CAT, 15);
//			System.out.println("Added row.");
//			
//			//Updates one row from the table.
//			dbc.updateProduct(4, 20);
//			System.out.println("Updated row.");
//			
//			//Reads and prints all the row in the table.
//	        showTable();
//	        
//	        //Deletes a row.
//	        dbc.deleteProduct(4);
//	        System.out.println("Deleted row.");
			
	        //Disconnects the client.
			dbc.disconnect();
		}
		
	}
	
	private void showTable() {
        try {
    		ResultSet products = dbc.getProducts();
			while (products.next()) {
				int productId = products.getInt("productID");
				String catOrDog = products.getString("catOrDog");
				int weight = products.getInt("weight");
				System.out.println(productId + ", " + catOrDog + ", " + weight);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void addCustomer(CatOrDog catOrDog) {

		Random r = new Random();
		dbc.addProducts(catOrDog, r.nextInt(20)+5);
		System.out.println("Customer added.");
	}
	
	private void removeCustomer(int productID) {
		
		if(checkID(productID)) {
			dbc.deleteProduct(productID);
			System.out.println("Customer removed.");
		}
		else {
			System.out.println("Customer/product ID not found.");
		}
	}
	
	private void replaceCustomerProduct(int productID) {
		
		if(checkID(productID)) {
			Random r = new Random();
			dbc.updateProduct(productID, r.nextInt(20)+5);
			System.out.println("Product replaced.");
		}
		else {
			System.out.println("Customer/product ID not found.");
		}
	}
	
	private boolean checkID(int productID) {
		
		try {
    		ResultSet products = dbc.getProducts();
			while (products.next()) {
				if(products.getInt("productID") == productID) return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
