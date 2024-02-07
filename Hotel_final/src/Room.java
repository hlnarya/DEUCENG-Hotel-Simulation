
public class Room {

	
	//Attributes
	private int id;
	private String type;
	private boolean balcony;
	private boolean aircondition;
	private int price;
	private static int autoincrement = 1;
	
	//Constructor
	public Room(String inputType,boolean inputAircondition,boolean inputBalcony,int inputPrice)
			{
			  type = inputType;
			  balcony = inputBalcony;
			  aircondition = inputAircondition;
			  price = inputPrice;
			  this.id = autoincrement++;
			  
			}
	
	public int getRoomId(){
	    return this.id;

	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public float getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
		
	}
	
	public boolean isBalcony() {
		return balcony;
	}

	public void setBalcony(boolean balcony) {
		this.balcony = balcony;
	}

	public boolean isAircondition() {
		return aircondition;
	}

	public void setAircondition(boolean aircondition) {
		this.aircondition = aircondition;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
