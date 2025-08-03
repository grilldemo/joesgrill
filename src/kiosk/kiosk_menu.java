package kiosk;

import javafx.application.Application;
import javafx.geometry.Insets;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import menu.Menu;
public class kiosk_menu extends Application  {

	public static void main(String[] args)   {
		// TODO Auto-generated method stub
		launch(args);
	}

	

	

	Menu menuObject;
	VBox mainContainer= new VBox(10);//main menu vbox
	VBox cartContainer = new VBox(10);//cart vbox
	
	//arrraylist
	ArrayList<Button> buttonList= new ArrayList<>();
	ArrayList<Button> removeButtonList= new ArrayList<>();
	ArrayList<GridPane> gridList= new ArrayList<>();
	ArrayList<Label> textNameList= new ArrayList<>();
	ArrayList<Label> textPriceList= new ArrayList<>();
	ArrayList<Label> quanityList= new ArrayList<>();
	ArrayList<TextField> textFieldList= new ArrayList<>();
	ArrayList<Integer> itemSelected= new ArrayList<>();
	ArrayList<Integer> itemQuantity= new ArrayList<>();
	ArrayList<String> orderList= new ArrayList<>();
	ArrayList<String> orderPriceList= new ArrayList<>();
	
	//grids
	GridPane grid= new GridPane();//grid for items
	GridPane displayGrid= new GridPane();//grid for itemdisplay
	GridPane cartGrid= new GridPane();//grid for cart
	GridPane cartGridLower= new GridPane();//grid for cart checkout
	GridPane checkoutGrid= new GridPane();//gid for menu checkout
	
	
	Stage cartStage = new Stage();
	

	//objects
	int itemSelect=1;//set to one because if it was 0 or null it might crash the program when add to cart is pressed without selecting an item first  
	int cartNum=0;//count for items in the cart
	//String order="";
	private Label itemName,itemDesciption,itemPrice;
	private Label totalPrice= new Label("totalprice: ");
	private Label totalPriceCount = new Label(" 0.00");
	private Label totalPrice2= new Label("totalprice: ");//totalPrice duplacate
	private Label totalPriceCount2 = new Label(" 0.00");//totalPriceCount duplacate
	
	ImageView im = new ImageView(); 
	Button addCart = new Button("add to cart");	
	Button checkout = new Button("checkout");
	Button checkout2 = new Button("checkout");
	Button menuButton = new Button("menu");
	Button toCart = new Button("cart");
	double orderCost=0.00;//default order cost
	
	VBox cartInnerVbox = new VBox();//
	Scene cartScene =new Scene(cartContainer,400,600);
	ScrollPane menuPane= new ScrollPane(grid);//scrollpane with grid inside
	ScrollPane cartPane= new ScrollPane(cartInnerVbox);//scrollpane for cart
	VBox itemDisplay = new VBox();//pane for item display
	VBox checkoutDisplay = new VBox();
	VBox cartCheckoutDisplay = new VBox();
	//////////////////////////////////////////////////////kiosk menu methods
	public void start (Stage stager)throws Exception{
		createMenu();
		createButtons();  
		addtoItemDisplay();
		addToCheckout();
		addToMain();
		showScreen();
		addToCartScreen();	
	}
	
	private void showScreen() {
		Scene newScene =new Scene(mainContainer,400,600);
		Stage newStage = new Stage();
		
		
		newStage.setResizable(false);
		
		
		newStage.setScene(newScene);
		newStage.show();
	}
	private void addToMain() {//adds the containers to main
		// TODO Auto-generated method stub
		mainContainer.getChildren().addAll(menuPane,itemDisplay,checkoutDisplay);
	}

	private void addtoItemDisplay() {//adds item info to display
		itemName =new Label("");
		itemPrice =new Label("");
		itemDesciption =new Label("");
		//menuPane.setPadding(new Insets(top right bottum left));
		//.setFont(new Font("Ariel",16));
		itemName.setPadding(new Insets(0, 25, 5, 20));
		itemPrice.setPadding(new Insets(5, 25, 0, 20));
		itemDesciption.setPadding(new Insets(0, 0, 5, 0));
		addCart.setPadding(new Insets(5, 10, 0, 10));
		itemName.setFont(new Font("Ariel",14));
		itemPrice.setFont(new Font("Ariel",18));
		itemDesciption.setFont(new Font("Ariel",12));
		addCart.setFont(new Font("Ariel",16));
		addCart.setStyle("-fx-background-color: yellow;");
		//set font size and cholor and vbox color
		//displaygrid.add(im, 1, 1);
		displayGrid.add(itemName, 1, 2);
		displayGrid.add(itemPrice, 1, 3);
		displayGrid.add(itemDesciption, 2, 2);
		displayGrid.add(addCart, 2, 3);
		itemDisplay.setStyle("-fx-background-color: orange;");
		
		
		
		
		
		
		addCart.setOnAction(event ->{//adds items to cart
		
			int index=itemSelected.indexOf(itemSelect);
			if(index==-1) {
				itemSelected.add(itemSelect);
				itemQuantity.add(1);
			}
			else {
				int newquanity=itemQuantity.get(index)+1;
				itemQuantity.set(index,newquanity );
			}
			cartNum++;
			reCount();
		});
		itemDisplay.getChildren().addAll(displayGrid);
	}

	private void createButtons() {//creates menu buttons
		// TODO Auto-generated method stub
		int row=0;
		int column=0;
		
		for(int i =0;i<menuObject.getResMenu().size();i++) {
			buttonList.add(new Button(menuObject.getResMenu().get(i).getItemName()));
			column = (i%2==0)?0:1;
			grid.add(buttonList.get(i),column,row);
			final int menunum = i;
			//menuPane.setPadding(new Insets(top right bottum left));
			//.setFont(new Font("Ariel",16));
			//.setStyle("-fx-background-color: yellow;");
			buttonList.get(i).setPadding(new Insets(5, 20, 5, 20));
			buttonList.get(i).setFont(new Font("Ariel",16));
			buttonList.get(i).setStyle("-fx-background-color: yellow;");
			grid.setPadding(new Insets(10, 20, 10, 40));
			
			buttonList.get(i).setOnAction(event ->{
				itemName.setText((menuObject.getResMenu().get(menunum).getItemName()));
				itemPrice.setText(String.valueOf(menuObject.getResMenu().get(menunum).getPrice()));
				itemDesciption.setText((menuObject.getResMenu().get(menunum).getDescription()));
				itemSelect = menunum;
			});
			if(i%2==1) {
				row++;
			}
		}
	}
	private void createMenu() {//creates menuobject
		// TODO Auto-generated method stub
		menuObject=new Menu();

	}
	
	
	//////////////////////////////////////////////cart screen
	
	private void removeAction(int cartnum) {//removes item from cart
		
	cartInnerVbox.getChildren().remove(cartnum);
		int quanity=itemQuantity.get(cartnum);
		if(quanity>1) {
			quanity--;
			itemQuantity.set(cartnum, quanity);
		}
		else {
			itemQuantity.remove(cartnum);
			itemSelected.remove(cartnum);
		}
	}
	private void buildCartScreen() {//makes cart screen
		
		for(int k =0;k<itemSelected.size();k++) {
			
			
				removeButtonList.add(new Button("remove"));
				gridList.add(new GridPane());
				final int item=k;
				
				
				textNameList.add(new Label(menuObject.getResMenu().get(itemSelected.get(k)).getItemName()));
				textPriceList.add(new Label(String.valueOf(menuObject.getResMenu().get(itemSelected.get(k)).getPrice())));
				quanityList.add(new Label("quanity:"));
				textFieldList.add(new TextField(String.valueOf(itemQuantity.get(k))));
				//menuPane.setPadding(new Insets(top right bottum left));
				//.setFont(new Font("Ariel",16));
				//.setStyle("-fx-background-color: yellow;");
				textNameList.get(k).setPadding(new Insets(10, 5, 10, 5));
				textPriceList.get(k).setPadding(new Insets(10, 5, 5, 5));
				quanityList.get(k).setPadding(new Insets(10, 5, 5, 5));
				textFieldList.get(k).setPadding(new Insets(10, 5, 5, 5));
				removeButtonList.get(k).setPadding(new Insets(10, 5, 5, 5));
				
				textNameList.get(k).setFont(new Font("Ariel",14));
				textPriceList.get(k).setFont(new Font("Ariel",14));
				quanityList.get(k).setFont(new Font("Ariel",14));
				textFieldList.get(k).setFont(new Font("Ariel",12));
				removeButtonList.get(k).setFont(new Font("Ariel",12));
				removeButtonList.get(k).setStyle("-fx-background-color: yellow;");

				gridList.get(k).add(textNameList.get(k), 1, 1);
				gridList.get(k).add(textPriceList.get(k), 2, 1);
				gridList.get(k).add(quanityList.get(k), 3, 1);
				gridList.get(k).add(textFieldList.get(k), 4, 1);
				gridList.get(k).add(removeButtonList.get(k), 5, 1);
				cartInnerVbox.getChildren().add(gridList.get(k));
				
				textFieldList.get(k).textProperty().addListener((obersavable,old,newvalue) ->{
					Integer value= Integer.parseInt(newvalue);
					if(value==0) {
						itemQuantity.set(item, value);
						removeAction(item);
						reCount();
					}
					else {
						itemQuantity.set(item, value);
						reCount();	
					}
					
				});
				removeButtonList.get(k).setOnAction(event ->{
					removeAction(item);
					reCount();
				});	
			}
	}
	
	private void cartScreen() {//shows cart screen
		cartStage.setResizable(false);	
		cartStage.setScene(cartScene);
		cartStage.show();
	}
	private void addToCartScreen() {//adds items to cart screen
		
		cartGridLower.add(menuButton, 1, 0);
		cartGridLower.add(totalPrice, 1, 2);
		cartGridLower.add(totalPriceCount, 2, 2);
		cartGridLower.add(checkout, 3, 2);
		
		//menuPane.setPadding(new Insets(top right bottum left));
		//.setFont(new Font("Ariel",16));
		//.setStyle("-fx-background-color: yellow;");
		menuButton.setPadding(new Insets(0, 20, 5, 20));
		totalPrice.setPadding(new Insets(5, 10, 0, 20));
		totalPriceCount.setPadding(new Insets(5, 5, 0, 5));
		checkout.setPadding(new Insets(5, 5, 0, 5));
		menuButton.setFont(new Font("Ariel",16));
		totalPrice.setFont(new Font("Ariel",16));
		totalPriceCount.setFont(new Font("Ariel",16));
		checkout.setFont(new Font("Ariel",16));
		menuButton.setStyle("-fx-background-color: yellow;");
		checkout.setStyle("-fx-background-color: yellow;");
		cartCheckoutDisplay.setStyle("-fx-background-color: orange;");
		
		menuButton.setOnAction(event ->{
			textNameList.clear();
			textPriceList.clear();
			textFieldList.clear();
			gridList.clear();
			
			cartInnerVbox.getChildren().clear();
			cartStage.close();
			
		
		});
		checkout.setOnAction(event ->{
			reCount();
			sendOrder();
			closeCart();
		});
		cartCheckoutDisplay.getChildren().addAll(cartGridLower);
		cartContainer.getChildren().addAll(cartPane,cartCheckoutDisplay);
		
	};
	
	
	
	private void sendOrder() {//sends order to mysql
		// TODO Auto-generated method stub
		DatabaseObject dbo2 = new DatabaseObject();
		
		dbo2.createOrder(menuObject, itemSelected, itemQuantity, orderCost);
		dbo2.close();
	}

	private void reCount() {//counts order cost
		orderCost=0.00;
		String cost;
		for(int i=0; i<itemSelected.size();i++) {
			
			orderCost+=(menuObject.getResMenu().get(itemSelected.get(i)).getPrice())*(itemQuantity.get(i));
		}
		
		cost= String.format("%.2f", orderCost);
		
		
		
		totalPriceCount.setText(cost);
		totalPriceCount2.setText(cost);
	}
	
	private void closeCart(){//closes the cart
		
		textNameList.clear();
		textPriceList.clear();
		textFieldList.clear();
		gridList.clear();
		
		cartInnerVbox.getChildren().clear();
		cartStage.close();
		itemSelected.clear();
		itemQuantity.clear();
		
	}
	
	private void addToCheckout() {
		//.setFont(new Font("Ariel",24));
		//totalprice =new Text("total price: ");
		//totalpricecount =new Text("total count");
		//menuPane.setPadding(new Insets(10,0,0,50));
		//menuPane.setPadding(new Insets(top right bottum left));
		toCart.setPadding(new Insets(5,10,5,10));
		totalPrice2.setPadding(new Insets(5,0,5,10));
		totalPriceCount2.setPadding(new Insets(5,10,5,0));
		checkout2.setPadding(new Insets(5,10,5,10));
		toCart.setFont(new Font("Ariel",16));
		totalPrice2.setFont(new Font("Ariel",16));
		totalPriceCount2.setFont(new Font("Ariel",16));
		checkout2.setFont(new Font("Ariel",16));
		//.setStyle("-fx-background-color: orange;");
		toCart.setStyle("-fx-background-color: yellow;");
		checkout2.setStyle("-fx-background-color: yellow;");
		checkoutDisplay.setStyle("-fx-background-color: red;");
		checkoutGrid.add(toCart, 1, 1);
		checkoutGrid.add(totalPrice2, 2,1);//create duplacate vsrisbles
		checkoutGrid.add(totalPriceCount2, 3, 1);
		//checkoutGrid.add(checkout2, 4, 1);
		
		checkout2.setOnAction(event->{//unused it doesnt clear ordercost
			reCount();
			sendOrder();
			closeCart();
		});
		
		toCart.setOnAction(event->{
			buildCartScreen();
			cartScreen();
		});
		checkoutDisplay.getChildren().addAll(checkoutGrid);
		checkoutDisplay.setPadding(new Insets(10,0,0,20));
	}
	
	
	
	
	
	
}
