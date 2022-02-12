package Positions;

public class Property extends Rules implements IPosition {
	private int number;
	private String name;
	private int price;
	private int rent;
	private int mortgageValue;
	private boolean occupied;
	private int ownerIndex;
	public  int type; //1 = Property, 2 = Station, 3 = Utility, 4 = Tax 
	
	public Property(){

	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	public int getNumber() {
		return number;
	}
	

	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getPrice() {
		return price;
	}
	
	public void setRent(int rent) {
		this.rent = rent;
	}
	public int getRent() {
		return rent;
	}
	public void setmortgageValue(int mortgageValue){
		this.mortgageValue=mortgageValue;
	}
	public int getmortgageValue(){
		return mortgageValue;
	}
	public void setOccupied(boolean occupied){
		this.occupied=occupied;
	}
	public boolean getOccupied(){
		return occupied;
	}
	public void setOwnerIndex(int ownerIndex){
		this.ownerIndex=ownerIndex;
	}
	public int getOwnerIndex(){
		return ownerIndex;
	}
	
	public int calculatePropertyRent(){
		//Rent will change if we add house and hotel options in the future version
		return rent;
	}
}