package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import menu.Menu;



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
	
	public ResultSet getOrders() {
		ResultSet order = null;
		String sqlStatement ="SELECT order_id,item_name,quantity\r\n"
				+ "FROM `order` NATURAL JOIN order_items NATURAL JOIN menu\r\n"
				+ "WHERE finished = false;";
		try {
			order = statement.executeQuery(sqlStatement);
			System.out.println("adsa");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return order;
	}
	public void updatemenu(String name,String descipion,double price,int selected) {
		//String update = "UPDATE `order` SET finished= true where order_id = "+orderIdnum+";";
		//UPDATE `order` SET finished= true where order_id = 1;
		String updatemenu ="UPDATE menu SET "
				+ "item_name = '" + name +
				"',item_description = '"+descipion+
				"',item_price = "+price+
				" WHERE item_id = "+selected+";";
		try {
			statement.executeUpdate(updatemenu);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createitem(String name,String descipion,double price) {
		String insermenu = "INSERT INTO `menu` (`item_name`,`item_description`,`item_price`,`image`,category_id)"
				+ " VALUES ('"+ name +"','"+ descipion +"',"+price+",null,1);";
		try {
			statement.executeUpdate(insermenu);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	//('water','tap water in a glass',0.00,NULL,1);
	}
	public void updateOrder(int orderIdnum) {
		String update = "UPDATE `order` SET finished= true where order_id = "+orderIdnum+";";
		try {
			statement.executeUpdate(update);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ResultSet getaccount() {
		ResultSet accounts = null;
		String accountsqlStatement ="SELECT * FROM joes_grill.admin_board;";
		try {
			accounts=statement.executeQuery(accountsqlStatement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accounts;
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
	public void createOrder(Menu menuobj,ArrayList<Integer> items,ArrayList<Integer> itemquanity, double totalprice  ) {
		int orderid=0;
		ResultSet rs = null;
		String insertsql = "INSERT INTO `order` (`order_cost`) VALUES ("+totalprice+")";
		try {
			statement.executeUpdate(insertsql, Statement.RETURN_GENERATED_KEYS);
			rs = statement.getGeneratedKeys();
			if (rs.next()) {
			    orderid = rs.getInt(1);  // The auto-generated ID
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		for(int i =0;i<items.size();i++) {
			
			String insertordersql = "INSERT INTO `order_items` (`order_id`,`item_id`,`quantity`) VALUES ("
					+orderid+","+menuobj.getResMenu().get(items.get(i)).getItemId()+","+itemquanity.get(i)+")";
			
			try {
				statement.executeUpdate(insertordersql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}
		
		
		
		
		//1 insert the total price into order table and get id return
		//1a example;  
//		String insertsql = create sql string
//		statement.executeUpdate(insertSQL, Statement.RETURN_GENERATED_KEYS);
//		ResultSet rs = stmt.getGeneratedKeys();
//		if (rs.next()) {
//		    int orderid = rs.getInt(1);  // The auto-generated ID
//		}
	//2 loop throught items array list . in each loop;
		//a creare sql string to iinset order id , item id , quanity into order items table
		
	}
	public void close() {
		try {
			connecter.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
