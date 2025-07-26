package menu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseObject;

public class Menu {

	ArrayList<MenuItem> resMenu= new ArrayList<>();

	public Menu() {
		DatabaseObject dbo = new DatabaseObject();
		ResultSet rs = dbo.getMenu();
		try {
			while(rs.next()) {
				resMenu.add(new MenuItem( rs.getInt("item_id"),rs.getString("item_name"), 
						rs.getString("item_description"),rs.getDouble("item_price"),rs.getString("image"),rs.getInt("category_id")));
			}
			dbo.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<MenuItem> getResMenu() {
		return resMenu;
	}
	
}
