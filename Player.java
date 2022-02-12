import Positions.*;
import java.util.Random;

public class Player{
	private String name;
	private int balance;
	public int round;
	public int diceNum;
	int totalprop=0;

	Random dice = new Random();
	Property[] ownedProperties = new Property[40];
	Property currentPosition = new Property();

	public Player(){

	}

	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}

	public void setBalance(int balance){
		this.balance=balance;
	}
	public int getBalance(){
		return balance;
	}
	
	public int rolldice(){
		diceNum = 1+dice.nextInt(12);
		return diceNum;
	}
	public void setCurrentPos(Property pos){
		this.currentPosition=pos;
	}
	public Property getCurrentPos(){
		return currentPosition;
	}
	public int getNumberOfStations(){
		int countStation=0;
		for(int i=0; i<totalprop; i++)
		{
			if(ownedProperties[i].type == 2){
				countStation++;
			}
		}
		return countStation;
	}
	public void setOwnedProperties(Property p){
		this.ownedProperties[totalprop]=p;
		totalprop++;
	}
	public void showOwnedProperties(){
		System.out.println("Number\t Name\t\t\t Price\t Rent\t MortgageValue\t Mortgage");
		for(int i=0; i<totalprop; i++)
		{
			System.out.println(ownedProperties[i].getNumber()+"\t "+ownedProperties[i].getName()+"\t\t "+ownedProperties[i].getPrice()
									+"\t "+ownedProperties[i].getRent()+"\t "+ownedProperties[i].getmortgageValue()+"\t\t "+!ownedProperties[i].getOccupied());
		}
	}
	public boolean mortgage(int mortgageIndex){
		for(int i=0; i<totalprop; i++)
		{
			if(mortgageIndex == ownedProperties[i].getNumber())
			{
				ownedProperties[i].setOccupied(false);
				return true;
			}
		}
		return false;
	}
	public boolean redeem(int redeemIndex){
		for(int i=0; i<totalprop; i++)
		{
			if(redeemIndex == ownedProperties[i].getNumber())
			{
				ownedProperties[i].setOccupied(true);
				return true;
			}
		}
		return false;
	}
}