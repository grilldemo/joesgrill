package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class DatabaseObject {

	Connection connecter; 
	Statement statement;
	public DatabaseObject() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver loaded");
			connecter=DriverManager.getConnection("jdbc:mysql://localhost:3306/joes_grill","root","1234");
			System.out.println("database loaded");
			statement=connecter.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			System.out.println("Statement created");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ResultSet getMenu() {
		ResultSet menu = null;
		String sqlStatement ="SELECT * FROM joes_grill.menu;";
		try {
			menu = statement.executeQuery(sqlStatement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menu;
	}
	
}
