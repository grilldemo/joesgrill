package menu;

public class MenuItem {
	int itemId;
	String itemName;
	String description;
	double price;
	String image;
	int catagorey;
	public MenuItem(int itemId,String itemName,String description,double price,String image,int catagorey){
		this.itemId=itemId;
		this.itemName=itemName;
		this.description=description;
		this.price=price;
		this.image=image;
		this.catagorey=catagorey;
	}
	public int getItemId() {
		return itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public String getDescription() {
		return description;
	}
	public double getPrice() {
		return price;
	}
	public String getImage() {
		return image;
	}
	public int getCatagorey() {
		return catagorey;
	}
	
}
