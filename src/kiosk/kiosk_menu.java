package kiosk;

import javafx.application.Application;
import javafx.geometry.Insets;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.sql.ResultSet;
import java.util.ArrayList;

import database.DatabaseObject;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import menu.Menu;
public class kiosk_menu extends Application  {

	public static void main(String[] args)   {
		// TODO Auto-generated method stub
		launch(args);
	}

	

	

	Menu menuObject;
	VBox mainContainer= new VBox(10);
	VBox cartContainer = new VBox(10);
	ArrayList<Button> buttonList= new ArrayList<>();
	ArrayList<Integer> itemQuantity= new ArrayList<>();

	GridPane grid= new GridPane();//grid for items
	GridPane displaygrid= new GridPane();
	GridPane cartgrid= new GridPane();
	GridPane cartgridlower= new GridPane();

	GridPane checkoutgrid= new GridPane();

	//objects
	int itemSelect;
	
	String order="";
	private Text itemname,itemdesciption,itemprice,totalprice,totalpricecount;
	ImageView im = new ImageView(); 
	Button addcart = new Button("add to cart");	
	Button checkout = new Button("checkout");
	Button menubutton = new Button("menu");
	Button tocart = new Button("cart");
		
	
	ScrollPane menuPane= new ScrollPane(grid);//scrollpane with grid inside
	ScrollPane cartPane= new ScrollPane(cartgrid);
	VBox itemDisplay = new VBox();//pane for item display
	VBox checkoutDisplay = new VBox();
	VBox cartcheckoutDisplay = new VBox();
	public void start (Stage stager)throws Exception{
		createMenu();
		//menuObject=new Menu();
		createButtons();  
		//menuPane.setPadding(new Insets(10,0,0,50));
		addtoitemdisplay();
		addtocheckout();
		addToMain();
		showScreen();
		
		//System.out.println(menuObject.getResMenu().get(3-1).getItemName());
		
		
	}


	private void showScreen() {
		Scene newscene =new Scene(mainContainer,400,600);
		Stage newStage = new Stage();
		
		
		newStage.setResizable(false);
		
		
		newStage.setScene(newscene);
		newStage.show();
	}
	private void addToMain() {
		// TODO Auto-generated method stub
		mainContainer.getChildren().addAll(menuPane,itemDisplay,checkoutDisplay);
	}

	private void addtoitemdisplay() {
		itemname =new Text("");
		itemprice =new Text("");
		itemdesciption =new Text("");

		//displaygrid.add(im, 1, 1);
		displaygrid.add(itemname, 1, 2);
		displaygrid.add(itemprice, 1, 3);
		displaygrid.add(itemdesciption, 2, 2);
		displaygrid.add(addcart, 2, 3);
		itemDisplay.getChildren().addAll(displaygrid);
	}

	private void cartScreen() {
		Scene cartscene =new Scene(cartContainer,400,600);
		Stage cartStage = new Stage();
		
		
		cartStage.setResizable(false);
		addtocartscreen();
		
		cartStage.setScene(cartscene);
		cartStage.show();
	}
	private void addtocartscreen() {
		
		cartgridlower.add(menubutton, 1, 0);
		cartgridlower.add(totalprice, 2, 1);
		cartgridlower.add(totalpricecount, 3, 1);
		cartgridlower.add(checkout, 4, 1);
		menubutton.setOnAction(event ->{
			showScreen();
		});
		
		cartcheckoutDisplay.getChildren().addAll(cartgridlower);
		
		cartContainer.getChildren().addAll(cartPane,cartcheckoutDisplay);
		
	};
	
	
	
	private void addtocheckout() {
		
		totalprice =new Text("total price: ");
		totalpricecount =new Text("total count");
		checkoutgrid.add(tocart, 1, 1);
		checkoutgrid.add(totalprice, 2,1);
		checkoutgrid.add(totalpricecount, 3, 1);
		checkoutgrid.add(checkout, 4, 1);
		tocart.setOnAction(event->{
			cartScreen();
		});
		checkoutDisplay.getChildren().addAll(checkoutgrid);
	}
	
	private void cleararray() {
		
	}
	//sends order to server
	/*for(int i =0;i<menuObject.getResMenu().size();i++){
	 *	if(itemQuantity.get(i)>0){
	 *		order+=menuObject.getResMenu().get(i).getItemName()+ "(" + itemQuantity.get(i) + "), "
	 *	} 
	 *}
	 *send order to server
	 *clear order string
	 *
	 */
	
	/*recounts total price
	 * total=0.00;
	 * for(int i =0;i<menuObject.getResMenu().size();i++){
	 * 	
	 * 	if(itemQuantity.get(i)>0){
	 * 		total+=(menuObject.getResMenu(i).getprice() * itemQuantity.get(i)*1.07)
	 * 	}
	 * }
	 */
	/*
	 * 
	 * 
	 * 
	 */
	
	
	
	
	
	
	
	private void fillquantity() {
		for(int i =0;i<menuObject.getResMenu().size();i++) {
			itemQuantity.add(0);
		}
	}
	private void createButtons() {
		// TODO Auto-generated method stub
		int row=0;
		int column=0;
		
		for(int i =0;i<menuObject.getResMenu().size();i++) {
			buttonList.add(new Button(menuObject.getResMenu().get(i).getItemName()));
			column = (i%2==0)?0:1;
			grid.add(buttonList.get(i),column,row);
			final int menunum = i;
			
			buttonList.get(i).setOnAction(event ->{
				
				itemname.setText((menuObject.getResMenu().get(menunum).getItemName()));
				itemprice.setText(String.valueOf(menuObject.getResMenu().get(menunum).getPrice()));
				itemdesciption.setText((menuObject.getResMenu().get(menunum).getDescription()));
				itemSelect = menunum;
			
			});
			
			
			if(i%2==1) {
				row++;
			}
		
		}
		
	}



	private void createMenu() {
		// TODO Auto-generated method stub
		menuObject=new Menu();

	}
	
	
}
