import java.util.*;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import Positions.*;

public class Monopoly
{
	static Property[] board = new Property[40];
	public static void main(String[] args)
	{
		//object
		Scanner sc = new Scanner(System.in);
		Player[] player = new Player[4];
		int players=0;
		boolean quitGame = false;
		int diceNum;
		
		//General intitalization
		boardInit();
		for(int i = 0; i < 4 ; i++)
		{
		    player[i] = new Player();
		    player[i].setCurrentPos(board[0]);
		}

		//select player number
		do
		{
			try{
				System.out.print("Enter the number of players (2-4): ");
				players = sc.nextInt();
			}catch(Exception e){
				System.out.println("Sorry, Please Enter a number ranging from 2 to 4.");
				sc.nextLine();
				players = sc.nextInt();
			}

			if (players>4 || players<2)
			{
				System.out.print("Invalid input\n");
			}
		}while(players>4 || players<2);

		//get player's names
		for(int i=0; i<players; i++)
		{
			System.out.print("Player " + (i+1) + " Name: ");
			String playerName = sc.next();
			
			player[i].setName(playerName);
			player[i].setBalance(2000);
			player[i].setCurrentPos(board[0]);
		}

		//game loop
		while(quitGame == false)
		{	
			for(int i=0; i<players; i++)
			{
				System.out.print("Trun: " + player[i].getName() + " (Balance: "+player[i].getBalance()+")\n" );	
				
				//dice and moving
				diceNum = player[i].rolldice();
				System.out.print("Dice: " + diceNum+"\n");
				Property tempPos = player[i].getCurrentPos();

				if((tempPos.getNumber()+diceNum)<40)
				{	
					player[i].setCurrentPos(board[tempPos.getNumber()+diceNum]);
				}
				else
				{
					player[i].setCurrentPos(board[diceNum-(40-tempPos.getNumber())]);
					player[i].setBalance(player[i].getBalance()+200);

				}
				tempPos = player[i].getCurrentPos();
				
				//board place details
				System.out.println("Position: " + tempPos.getNumber());
				System.out.println("Property Name: " + tempPos.getName());
				System.out.println("Property Price: " + tempPos.getPrice());
				System.out.println("Property Rent: " + tempPos.getRent());

				//Buy/Rent
				if(tempPos.getOccupied() == false)
				{
					String decision = "";
					if(tempPos.type > 0 && tempPos.type < 4)
					{
						do{

							System.out.println("Property Owner: Bank" );
							System.out.println("Do you want to purchase? (y/n)");
							decision = sc.next();
							if(decision.equals("y"))
							{
								board[tempPos.getNumber()].setOwnerIndex(i);
								board[tempPos.getNumber()].setOccupied(true);
								player[i].setBalance(player[i].getBalance()-board[tempPos.getNumber()].getPrice());
								player[i].setOwnedProperties(board[tempPos.getNumber()]);
								System.out.println("Now " + player[i].getName() + " owns " +board[tempPos.getNumber()].getName());
								System.out.println("Current Balance: " + player[i].getBalance());
							}
							else if(decision.equals("n"))
							{
								System.out.println("Property not sold!");
							}
							else
							{
								System.out.println("Sorry, Please enter y or n");
							}
						}while(!decision.equals("y")&&!decision.equals("n"));
					}		
				}
				else
				{
					int ownerIndex = tempPos.getOwnerIndex();
					Player owner = player[ownerIndex];
					System.out.println("Property Owner: " + owner.getName());
					if(tempPos.type == 1)//Property
					{
						player[i].setBalance(player[i].getBalance()-board[tempPos.getNumber()].calculatePropertyRent());
						player[ownerIndex].setBalance(player[ownerIndex].getBalance()+board[tempPos.getNumber()].calculatePropertyRent());
						System.out.println("Rent Charged: " + board[tempPos.getNumber()].calculatePropertyRent() + "; " + "Current Balance: " + player[i].getBalance());
					}
					else if(tempPos.type == 2)//Station
					{
						player[i].setBalance(player[i].getBalance()-board[tempPos.getNumber()].calculateStationRent(player[i].getNumberOfStations(), tempPos));
						player[ownerIndex].setBalance(player[ownerIndex].getBalance()+board[tempPos.getNumber()].calculateStationRent(player[i].getNumberOfStations(), tempPos));
						System.out.println("Rent Charged: " + board[tempPos.getNumber()].calculateStationRent(player[i].getNumberOfStations(), tempPos) + "; " + "Current Balance: " + player[i].getBalance());
					}
					else if(tempPos.type == 3)//Utility
					{
						player[i].setBalance(player[i].getBalance()-board[tempPos.getNumber()].calculateUtilityRent(diceNum, tempPos));
						player[ownerIndex].setBalance(player[ownerIndex].getBalance()+board[tempPos.getNumber()].calculateUtilityRent(diceNum, tempPos));
						System.out.println("Rent Charged: " + board[tempPos.getNumber()].calculateUtilityRent(diceNum, tempPos) + "; " + "Current Balance: " + player[i].getBalance());
					}
					else if(tempPos.type == 4)//Tax
					{
						player[i].setBalance(player[i].getBalance()-board[tempPos.getNumber()].calculateTaxRent(tempPos));
						System.out.println("Rent Charged: " + board[tempPos.getNumber()].calculateTaxRent(tempPos) + "; " + "Current Balance: " + player[i].getBalance());
					}
					

				}

				//Player's choice
				int chooseOption;
				do{
					try{
					
						System.out.print("1. Pass\n2. See owned properties\n3. Mortgage\n4. Redeem Mortgage \n5. Quit Game\nChoose an option: ");
						chooseOption = sc.nextInt();
						switch(chooseOption)
						{
							case 1: 
								break;
							case 2:
								player[i].showOwnedProperties();
								break;
							case 3:
								player[i].showOwnedProperties();
								System.out.println("Please select the property number you want to mortgage: ");
								int mortPropIndex = sc.nextInt();
								board[mortPropIndex].setOccupied(false);
								if(player[i].mortgage(mortPropIndex))
								{
									player[i].mortgage(mortPropIndex);
									player[i].setBalance(player[i].getBalance()+board[mortPropIndex].getmortgageValue());
									System.out.println("Now " +board[mortPropIndex].getName()+"is Mortgaged to the bank");
									System.out.println("Current Balance: " + player[i].getBalance());
								}
								else
								{
									System.out.println("Property was not found to be mortgage");
								}
								break;
							case 4:
								player[i].showOwnedProperties();
								System.out.println("Please select the property number you want to redeem: ");
								int redeemPropIndex = sc.nextInt();
								board[redeemPropIndex].setOccupied(true);
								if(player[i].redeem(redeemPropIndex))
								{
									player[i].setBalance(player[i].getBalance()-board[redeemPropIndex].getmortgageValue());
									System.out.println("Now " +board[redeemPropIndex].getName()+"is redeemed from the bank");
									System.out.println("Current Balance: " + player[i].getBalance());
								}
								else
								{
									System.out.println("Property was not found to be redeemed");
								}
								break;
							case 5:
								quitGame = true;
							default:
								System.out.println("Sorry, you did not choose any valid options.");
						}
					
					}catch(Exception e){
						System.out.println("Sorry Invalid choice.");
						sc.nextLine();
						chooseOption = 0;
					}
				}while(chooseOption < 1 || chooseOption > 5);
				System.out.println("----------------------------------");
			}
		}
		for(int i=0; i<players;i++)
		{

			System.out.println("Player: "+player[i].getName() +"\tBalance: "+ player[i].getBalance());
			player[i].showOwnedProperties();
		}
	}
	//Board positions intitalization
	static void boardInit()
	{
		File boardData = new File("MonopolyBoardData.txt");
		try
		{
			FileReader reader = new FileReader(boardData);
			BufferedReader bfr = new BufferedReader(reader);

			String line = "";
			int i = 0;
			while((line = bfr.readLine())!=null)
			{
				String[] propertyAttr = line.split(",",0);
				board[i] = new Property();
				board[i].setNumber(Integer.parseInt(propertyAttr[0]));
				board[i].setName(propertyAttr[1]);
				board[i].setPrice(Integer.parseInt(propertyAttr[2]));
				board[i].setRent(Integer.parseInt(propertyAttr[3]));
				board[i].setmortgageValue(Integer.parseInt(propertyAttr[4]));
				board[i].type = Integer.parseInt(propertyAttr[5]);
				i++;
			}

		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}

		
	}
}