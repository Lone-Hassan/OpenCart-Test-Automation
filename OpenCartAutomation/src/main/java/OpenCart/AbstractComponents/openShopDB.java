package OpenCart.AbstractComponents;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class openShopDB {

	String url;
	String username;
	String password;
	Connection conn;
	

	public openShopDB(String url, String username, String password) throws SQLException {

		this.url = url;
		this.username = username;
		this.password = password;
		this.conn = DriverManager.getConnection(url, username, password);
		//this.st = conn.createStatement();
	}

	public int delAccount(String AccountEmail) throws SQLException {
		
		
		PreparedStatement ps = conn.prepareStatement("DELETE FROM oc_customer WHERE email=?;");
		ps.setString(1, AccountEmail);
		int rowsAfct=ps.executeUpdate();
		
		return rowsAfct;
	}
	
	/**
	 * Check does account exists in database or not
	 * @param email is email id to search for account
	 * @return true if account with email provided exists.
	 * */
	public boolean AccountExists(String email) throws SQLException {
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from oc_customer where email='"+email+"'");
		boolean found = false;
		while(rs.next()) {
			
			if(rs.getString("email").equals(email)) {
				found = true;
			}
			
		}
		return found;
	}
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

		String url = "jdbc:mysql://localhost:3306/openshop";
		String username = "root";
		String password = "";

		//Connection conn = DriverManager.getConnection(url, username, password);
		openShopDB db = new openShopDB(url,username,password);
		System.out.println(db.AccountExists("lone@ced13.com"));
		
		/*
		//st.executeQuery("DELETE FROM oc_customer WHERE email=lone@abc.com;");
		ResultSet rs = st.executeQuery("select * from oc_customer;");

		while (rs.next()) {
			System.out.println("Email: " + rs.getString("email"));
			// System.out.println("name: '"+rs.getString("name")+"' id:"+rs.getInt("id")+"
			// location: '"+rs.getString("location")+"' age:"+rs.getInt("age"));
		}*/
		

	}

}
