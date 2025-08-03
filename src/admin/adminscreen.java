package admin;
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

public class adminscreen extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	Menu menuObject;
	int itemSelected;

	//stage
	Stage adminStage = new Stage();

	//grids
	GridPane menuGrid= new GridPane();
	GridPane editGrid= new GridPane();
	GridPane insertGrid= new GridPane();

	//vboxs
	VBox adminMain= new VBox(10);//main screen
	VBox editMain= new VBox(10);
	VBox insertMain= new VBox(10);
	//scrollpane
	ScrollPane menuPane= new ScrollPane(menuGrid);

	//buttons
	ArrayList<Button> menubutton2= new ArrayList<>();
	Button save = new Button("save");
	Button insert = new Button("insert");

	//text fields
	TextField name= new TextField("");
	TextField description= new TextField("");
	TextField price= new TextField("");
	TextField insertName= new TextField("");
	TextField insertDescription= new TextField("");
	TextField insertPrice= new TextField("");
	//text
	Label er1= new Label("");
	Label er2= new Label("");
	//strings
	String selectName="";
	String selectDescription="";
	String insertedName="";
	String insertedDescription="";
	//double
	Double selectprice=null;
	Double inertedprice=null;
	//boolean
	boolean number=true;

	
	
	
	
	
	public void start (Stage stager)throws Exception{
		showAdminScreen();
	}
	
	private void showAdminScreen() {
		insertGrid.getChildren().clear();
		editGrid.getChildren().clear();
		menuGrid.getChildren().clear();
		insertMain.getChildren().clear();
		editMain.getChildren().clear();
		adminMain.getChildren().clear();
		adminStage.close();
		
		createMenu();
		createButtons();
		addEdit();
		addInsert();
		addMain();
		showScreen3();
		
	}
	
	private void showScreen3() {
		Scene adminscene =new Scene(adminMain,400,600);
		
		adminStage.setResizable(false);
		adminStage.setScene(adminscene);
		adminStage.show();
	
	}
	private void addMain() {
		adminMain.getChildren().addAll(menuPane,editMain,insertMain);
	}
	private void createButtons() {//creates menu buttons
		// TODO Auto-generated method stub
		int row=0;
		int column=0;
		
		for(int i =0;i<menuObject.getResMenu().size();i++) {
			menubutton2.add(new Button(menuObject.getResMenu().get(i).getItemName()));
			column = (i%2==0)?0:1;
			menuGrid.add(menubutton2.get(i),column,row);
			final int menunum = i;
			
			menubutton2.get(i).setOnAction(event ->{
				name.setText((menuObject.getResMenu().get(menunum).getItemName()));
				selectName =menuObject.getResMenu().get(menunum).getItemName();
				//System.out.println("asd");
				price.setText(String.valueOf(menuObject.getResMenu().get(menunum).getPrice()));
				selectprice= menuObject.getResMenu().get(menunum).getPrice();
				description.setText((menuObject.getResMenu().get(menunum).getDescription()));
				selectDescription= menuObject.getResMenu().get(menunum).getDescription();
				itemSelected = menunum;
			});
			if(i%2==1) {
				row++;
			}
		}
	}
	private void addEdit() {
		//collumn row
		editGrid.add(name, 1, 1);
		editGrid.add(description, 2, 1);
		editGrid.add(price, 3, 1);
		editGrid.add(save, 1, 2);
		editGrid.add(er1, 2, 2);
		name.textProperty().addListener((obersavable,old,newvalue) ->{
			selectName=newvalue;
		});
		description.textProperty().addListener((obersavable,old,newvalue) ->{
			selectDescription=newvalue;
		});
		price.textProperty().addListener((obersavable,old,newvalue) ->{
			
			if(isNum(newvalue)==true) {
				selectprice= Double.parseDouble(newvalue);
				er1.setText("");//clear er1
			}
			else {
				er1.setText("price is not a number");//display error when detected
				System.out.println("console isnumerror");
			}
		});
		
		save.setOnAction(event->{
			
			DatabaseObject dbo3 = new DatabaseObject();
			String priceString = String.valueOf(selectprice);
			if(isNum(priceString)==true) {
				dbo3.updatemenu(selectName,selectDescription,selectprice,itemSelected+1);//itemselected need +1 because when selecting it would select the prvious item
			}
			else {
				er1.setText("there is a letter in price");//dissplay error and negates save if save is while error displays
			}
		
		});
		
		editMain.getChildren().addAll(editGrid);

	}
	private void addInsert() {
	
		
		
		insertGrid.add(insertName, 1, 1);
		insertGrid.add(insertDescription, 2, 1);
		insertGrid.add(insertPrice, 3, 1);
		insertGrid.add(insert, 1, 2);
		insertGrid.add(er2, 2, 2);

		//add listeners to rextfileds
		insertName.textProperty().addListener((obersavable,old,newvalue) ->{
			insertedName = newvalue;
		});
		insertDescription.textProperty().addListener((obersavable,old,newvalue) ->{
			insertedDescription = newvalue;
		});
		insertPrice.textProperty().addListener((obersavable,old,newvalue) ->{//listener for textfield

			if(isNum(newvalue)==true) {
				inertedprice= Double.parseDouble(newvalue);
				er2.setText("");
			}
			else {
				er2.setText("there is a letter in price");
				//System.out.println("console isnumerror");
			}
		});
		insert.setOnAction(event->{
			//check for number on price
		
			DatabaseObject dbo4 = new DatabaseObject();
			String priceString = String.valueOf(inertedprice);
			if(isNum(priceString)==true) {
				dbo4.createitem(insertedName,insertedDescription,inertedprice);
			}
			else {
				er2.setText("there is a letter in price");//displays error
			}
		});
		
		
		insertMain.getChildren().addAll(insertGrid);
	}
	
	private void createMenu() {
		// TODO Auto-generated method stub
		menuObject=new Menu();

	}

	private boolean isNum(String rest) {//checks if price is number
		number=true;
		try {
			Double check = Double.parseDouble(rest);
		}catch(NumberFormatException nfe){
			number=false;
			return number;
		}
		number=true;
		return number;
	}

}

