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
	//vboxs
	VBox kitchenmain= new VBox(10);//main vbox
	VBox kitchenBottom= new VBox(10);//vbox being added to scrollpane
	//buttons
	Button refresh = new Button("refresh");
	//scrollpanes
	ScrollPane orderPane= new ScrollPane(kitchenBottom);//scrollpane containing vbox
	//arraylist
	ArrayList<Label> ordertextList= new ArrayList<>();
	ArrayList<GridPane> ordersGrid= new ArrayList<>();//arraylist of grids
	ArrayList<Label> orderId= new ArrayList<>();
	ArrayList<Label> itemName= new ArrayList<>();
	ArrayList<Label> itemQuatity= new ArrayList<>();
	ArrayList<Label> quanity= new ArrayList<>();
	ArrayList<Button> itemFinish= new ArrayList<>();
	//ints
	int num=0;//counter
	int buttonnum=0;//counter for orderid and itemfinish

	
	public void start (Stage stager)throws Exception{
		repaintScreen();
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
	private void setTimeout(Runnable action) {//timer that refreses every 20 seconds
    	PauseTransition pause = new PauseTransition(Duration.millis(20000));
        pause.setOnFinished(event -> Platform.runLater(action));
        pause.play();
    }

	
	
	private void repaintScreen() {//adds orders to sceen
		DatabaseObject dbok = new DatabaseObject();
		ResultSet krs = dbok.getOrders();
		orderId.clear();
		itemName.clear();
		itemQuatity.clear();
		itemFinish.clear();
		ordersGrid.clear();
		kitchenBottom.getChildren().clear();
		num=0;
		buttonnum=0;
		int oldId=-1;
		try {
			while(krs.next()) {
				int curId= krs.getInt("order_id");
				String oid =String.valueOf(krs.getInt("order_id"));//order id
				String name = krs.getString("item_name");//items name
				String quan =String.valueOf(krs.getInt("quantity"));//items quanity
				
				itemName.add(new Label(name));//items name
				itemQuatity.add(new Label(quan));//item quanity
				quanity.add(new Label("quanity: "));//label that directly tells whats next to it
			
				//make grid and add items to it and
				//gridlist.get(k).add(textnamelist.get(k), 1, 1);
				//grid.add(buttonList.get(i),column,row);
				ordersGrid.add(new GridPane());//new grid
				ordersGrid.get(num).add(itemName.get(num), 2, 1);//itemname
				ordersGrid.get(num).add(quanity.get(num), 3, 1);
				ordersGrid.get(num).add(itemQuatity.get(num), 4, 1);//itemquatity
				//menuPane.setPadding(new Insets(top right bottum left));
				//.setFont(new Font("Ariel",16));
				itemName.get(num).setPadding(new Insets(5, 5, 5, 5));
				if(curId!=oldId) {
					//create texts and buttons here and add to grid and add grid to vbox
					oid =String.valueOf(krs.getInt("order_id"));//order id
					orderId.add(new Label(oid));
					ordersGrid.get(num).add(orderId.get(buttonnum), 1, 1);//orderid
					itemFinish.add(new Button("finished"));
					ordersGrid.get(num).add(itemFinish.get(buttonnum), 5, 1);
					
					
					final int id=curId;
					itemFinish.get(buttonnum).setOnAction(event ->{
						finishOrder(id);
					
					});
					buttonnum++;
				//add button listener
				
				
				
				}
				
				oldId=curId;
				kitchenBottom.getChildren().add(ordersGrid.get(num));
				num++;
				
			}
			dbok.close();
			setTimeout(()->repaintScreen());
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	}
	private void finishOrder(int num){//update order and repaints screen
		DatabaseObject dbok = new DatabaseObject();
		dbok.updateOrder(num);
		dbok.close();
		repaintScreen();
	}
	
	
	private void addtokitchen() {//adds scrollpane to main vbox
		kitchenmain.getChildren().addAll(orderPane);
	}
	
	
	
	
	
	
	
	
	

}
