package kitchen;

import javafx.application.Application;
import javafx.geometry.Insets;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseObject;
import javafx.animation.PauseTransition;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import menu.Menu;

public class kitchenscreen extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	VBox kitchenmain= new VBox(10);
	VBox kitchentop= new VBox(10);
	VBox kitchenbottom= new VBox(10);//add grid to this
	Button refresh = new Button("refresh");
	ScrollPane orderPane= new ScrollPane(kitchenbottom);
	//ArrayList<GridPane> ordersgrid= new ArrayList<>();
	//ArrayList<Button> markgrid= new ArrayList<>();
	ArrayList<Text> ordertextlist= new ArrayList<>();
	//key grids
	ArrayList<GridPane> ordersgrid= new ArrayList<>();//arraylist grid
	ArrayList<Text> orderid= new ArrayList<>();
	ArrayList<Text> itemName= new ArrayList<>();
	ArrayList<Text> itemquatity= new ArrayList<>();
	ArrayList<Button> itemFinish= new ArrayList<>();
	int num=0;
	int buttonnum=0;

	
	public void start (Stage stager)throws Exception{
		repaintscreen();
		//addtokitchenupper();
		addtokitchen();
		showScreen2();
	}
	
	private void showScreen2() {
		Scene kitchenscene =new Scene(kitchenmain,400,600);
		Stage kitchenStage = new Stage();
		
		
		kitchenStage.setResizable(false);
		
		
		kitchenStage.setScene(kitchenscene);
		kitchenStage.show();
	}
	private void setTimeout(Runnable action) {
    	PauseTransition pause = new PauseTransition(Duration.millis(10000));
        pause.setOnFinished(event -> Platform.runLater(action));
        pause.play();
    }

	
	
	private void repaintscreen() {
		DatabaseObject dbok = new DatabaseObject();
		ResultSet krs = dbok.getOrders();
		orderid.clear();
		itemName.clear();
		itemquatity.clear();
		itemFinish.clear();
		ordersgrid.clear();
		kitchenbottom.getChildren().clear();
		num=0;
		buttonnum=0;
		int oldId=-1;
		try {
			while(krs.next()) {
				int curId= krs.getInt("order_id");
				String oid =String.valueOf(krs.getInt("order_id"));//order id
				String name = krs.getString("item_name");
				String quan =String.valueOf(krs.getInt("quantity"));
				//buttonList.add(new Button(menuObject.getResMenu().get(i).getItemName()));
				//orderid.add(new Text(oid));
				itemName.add(new Text(name));
				itemquatity.add(new Text(quan));
				//
				//make grid and add items to it and
				//gridlist.get(k).add(textnamelist.get(k), 1, 1);
				//grid.add(buttonList.get(i),column,row);
				ordersgrid.add(new GridPane());//new grid
				ordersgrid.get(num).add(itemName.get(num), 2, 1);//itemname
				ordersgrid.get(num).add(itemquatity.get(num), 3, 1);//itemquatity

				if(curId!=oldId) {
					//create texts and buttons here and add to grid and add grid to vbox
					oid =String.valueOf(krs.getInt("order_id"));//order id
					orderid.add(new Text(oid));
					ordersgrid.get(num).add(orderid.get(buttonnum), 1, 1);//orderid
					itemFinish.add(new Button("finished"));
					ordersgrid.get(num).add(itemFinish.get(buttonnum), 4, 1);
					final int id=curId;
					itemFinish.get(buttonnum).setOnAction(event ->{
						finishOrder(id);
					
					});
					buttonnum++;
				//add button listener
				
				
				
				}
				//add gird to vbox kitchenbottom
				//cartcheckoutDisplay.getChildren().addAll(cartgridlower);
				oldId=curId;
				kitchenbottom.getChildren().add(ordersgrid.get(num));
				num++;
				
			}
			dbok.close();
			setTimeout(()->repaintscreen());
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	}
	private void finishOrder(int num){
		DatabaseObject dbok = new DatabaseObject();
		dbok.updateOrder(num);
		dbok.close();
		repaintscreen();
	}
	
	
	private void addtokitchen() {
		kitchenmain.getChildren().addAll(orderPane);
	}
	private void addtokitchenupper() {//sorta finished
		kitchentop.getChildren().add(refresh);
		
		refresh.setOnAction(event->{
			
		});
		
	}
	
	//get data
	//loop tgrough order
	//
	
	
	
	
	
	

}
