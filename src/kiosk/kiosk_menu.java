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
	
	//arrraylist
	ArrayList<Button> buttonList= new ArrayList<>();
	ArrayList<Button> removebuttonList= new ArrayList<>();
	ArrayList<GridPane> gridlist= new ArrayList<>();
	ArrayList<Text> textnamelist= new ArrayList<>();
	ArrayList<Text> textpricelist= new ArrayList<>();
	ArrayList<TextField> textfieldlist= new ArrayList<>();
	ArrayList<Integer> itemSelected= new ArrayList<>();
	ArrayList<Integer> itemQuantity= new ArrayList<>();
	ArrayList<String> orderlist= new ArrayList<>();
	ArrayList<String> orderpricelist= new ArrayList<>();
	
	//grids
	GridPane grid= new GridPane();//grid for items
	GridPane displaygrid= new GridPane();
	GridPane cartgrid= new GridPane();
	GridPane cartgridlower= new GridPane();
	GridPane checkoutgrid= new GridPane();
	
	
	Stage cartStage = new Stage();

	//objects
	int itemSelect;
	int cartnum=0;
	//String order="";
	private Text itemname,itemdesciption,itemprice,totalprice,totalpricecount;
	ImageView im = new ImageView(); 
	Button addcart = new Button("add to cart");	
	Button checkout = new Button("checkout");
	Button menubutton = new Button("menu");
	Button tocart = new Button("cart");
	double orderCost=0.00;
	
	VBox cartinnervbox = new VBox();
	Scene cartscene =new Scene(cartContainer,400,600);
	ScrollPane menuPane= new ScrollPane(grid);//scrollpane with grid inside
	ScrollPane cartPane= new ScrollPane(cartinnervbox);
	VBox itemDisplay = new VBox();//pane for item display
	VBox checkoutDisplay = new VBox();
	VBox cartcheckoutDisplay = new VBox();
	//////////////////////////////////////////////////////kiosk menu methods
	public void start (Stage stager)throws Exception{
		createMenu();
		//menuObject=new Menu();
		createButtons();  
		//menuPane.setPadding(new Insets(10,0,0,50));
		addtoitemdisplay();
		addtocheckout();
		addToMain();
		showScreen();
		addtocartscreen();	
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

	private void addtoitemdisplay() {//adds item info to display
		itemname =new Text("");
		itemprice =new Text("");
		itemdesciption =new Text("");

		//displaygrid.add(im, 1, 1);
		displaygrid.add(itemname, 1, 2);
		displaygrid.add(itemprice, 1, 3);
		displaygrid.add(itemdesciption, 2, 2);
		displaygrid.add(addcart, 2, 3);
		
		addcart.setOnAction(event ->{//adds items to cart
		
			int index=itemSelected.indexOf(itemSelect);
			if(index==-1) {
				itemSelected.add(itemSelect);
				System.out.println("hjfyjhj");
				itemQuantity.add(1);
			}
			else {
				int newquanity=itemQuantity.get(index)+1;
				itemQuantity.set(index,newquanity );
			}
			cartnum++;
		});
		itemDisplay.getChildren().addAll(displaygrid);
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
	
	
	//////////////////////////////////////////////cart screen
	
	private void removeaction(int cartnum) {
		
	cartinnervbox.getChildren().remove(cartnum);
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
	private void buildCartscreen() {
		
		for(int k =0;k<itemSelected.size();k++) {
			
			
				removebuttonList.add(new Button("remove"));
				gridlist.add(new GridPane());
				final int item=k;
				//final int displayRow=j;
				
				
				
				textnamelist.add(new Text(menuObject.getResMenu().get(itemSelected.get(k)).getItemName()));
				textpricelist.add(new Text(String.valueOf(menuObject.getResMenu().get(itemSelected.get(k)).getPrice())));
				textfieldlist.add(new TextField(String.valueOf(itemQuantity.get(k))));
				gridlist.get(k).add(textnamelist.get(k), 1, 1);
				gridlist.get(k).add(textpricelist.get(k), 2, 1);
				gridlist.get(k).add(textfieldlist.get(k), 3, 1);
				gridlist.get(k).add(removebuttonList.get(k), 4, 1);
				cartinnervbox.getChildren().add(gridlist.get(k));
				
				textfieldlist.get(k).textProperty().addListener((obersavable,old,newvalue) ->{
					Integer value= Integer.parseInt(newvalue);
					System.out.println("new value equals " + value);
					itemQuantity.set(item, value);
					//recount
				});
				removebuttonList.get(k).setOnAction(event ->{
					//cartinnervbox.getChildren().remove(ind);
					removeaction(item);
				});	
			}
	}
	
	private void cartScreen() {
		cartStage.setResizable(false);	
		cartStage.setScene(cartscene);
		cartStage.show();
	}
	private void addtocartscreen() {
		
		cartgridlower.add(menubutton, 1, 0);
		cartgridlower.add(totalprice, 2, 1);
		cartgridlower.add(totalpricecount, 3, 1);
		cartgridlower.add(checkout, 4, 1);
		menubutton.setOnAction(event ->{
			textnamelist.clear();
			textpricelist.clear();
			textfieldlist.clear();
			gridlist.clear();
			
			cartinnervbox.getChildren().clear();
			cartStage.close();
			
		
		});
		checkout.setOnAction(event ->{
			recount();
			sendOrder();
			closecart();
		});
		cartcheckoutDisplay.getChildren().addAll(cartgridlower);
		cartContainer.getChildren().addAll(cartPane,cartcheckoutDisplay);
		
	};
	
	
	
	private void sendOrder() {
		// TODO Auto-generated method stub
		DatabaseObject dbo2 = new DatabaseObject();
		//createOrder(DatabaseObject dbo2)
		//Scanner input = new Scanner(System.in);
		//String gh= input.nextLine();
		
		
		dbo2.createOrder(menuObject, itemSelected, itemQuantity, orderCost);
		dbo2.close();
	}

	private void recount() {
		orderCost=0.00;
		
		for(int i=0; i<itemSelected.size();i++) {
			
			orderCost+=(menuObject.getResMenu().get(itemSelected.get(i)).getPrice())*(itemQuantity.get(i));
		}
		
	}
	
	private void closecart(){
		
		textnamelist.clear();
		textpricelist.clear();
		textfieldlist.clear();
		gridlist.clear();
		
		cartinnervbox.getChildren().clear();
		cartStage.close();
		itemSelected.clear();
		itemQuantity.clear();
		
	}
	
	private void addtocheckout() {
		
		totalprice =new Text("total price: ");
		totalpricecount =new Text("total count");
		checkoutgrid.add(tocart, 1, 1);
		checkoutgrid.add(totalprice, 2,1);
		checkoutgrid.add(totalpricecount, 3, 1);
		checkoutgrid.add(checkout, 4, 1);
		
		tocart.setOnAction(event->{
			buildCartscreen();
			cartScreen();
		});
		checkoutDisplay.getChildren().addAll(checkoutgrid);
	}
	
	private void cleararrays() {
		
	}
	
	
	
	
}
