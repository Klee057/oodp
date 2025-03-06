package controllers;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import classes.Restaurant;
import classes.Table;

/**
 * TableController allows user to check and manage tables
 */
public class TableController {
	static Scanner sc = new Scanner(System.in);
	private static ArrayList<Table> tables = Restaurant.getTableList();

    /**
     * Display options to check available tables and assign tables for user input
     */
	public static void displayOptions() {
    int choice = 0, tableId = 0;
        do {
          if(!Restaurant.getBackTrack()){
			System.out.println("\n");
        	System.out.println("Select option");
    		System.out.println("(1) Check Available Tables");
            System.out.println("(2) Assign table to Walk in Customer");      
            System.out.println("(3) Back");
            try {
				choice = sc.nextInt();
			}catch(InputMismatchException e){
				sc.nextLine();
				System.out.println("Please enter integer only!");
				continue;
			}
          }
        	switch(choice) {
        		case 1:
        			checkAvailTables();
                    break;
        		case 2:
                    tableId = assignTable();
                    if(tableId >0) {
                      OrderController.createOrder(tableId);
                      if(Restaurant.getBackTrack()){
                        break;
                      }
                    }
                    Restaurant.setBackTrack(false);
                    break;
        		case 3:
        			break;
                default:
                	System.out.println("Invalid Option, Try again.");
        	}
        }while(choice!=3);
	}

    /**
     * Allows user to check all available tables by printing out Table ID and capacity
     */
    public static void checkAvailTables() {
		int j;
		Restaurant.refresh();
		System.out.println("\n");
		System.out.println("These are the available tables");
		System.out.println("| Table ID | Capacity |");
		for (j = 0; j < tables.size(); j++)
		{
			if(!tables.get(j).getIsOccupied())
			System.out.printf("\t" + tables.get(j).getTableId() + "\t" + tables.get(j).getCapacity() + "\n");
		}
	}

    /**
     * Allows user to check available tables based minimum capacity
     * @param pax
     */
	public static void checkAvailTables(int pax) {
		int j;
		boolean suitable = false;
		Restaurant.refresh();
		for(j=0; j < tables.size(); j++) {
			if (!tables.get(j).getIsOccupied())
			{
				suitable = true;
				break;
			}
		}
		if(!suitable) {
			return;
		}
		System.out.println("\n");
		System.out.println("These are the available tables");
		System.out.println("| Table ID | Capacity |");
		for (j = 0; j < tables.size(); j++)
		{
			if(!tables.get(j).getIsOccupied() && tables.get(j).getCapacity() >= pax)
			System.out.printf("\t" + tables.get(j).getTableId() + "\t" + tables.get(j).getCapacity() + "\n");
		}
	}

    /**
     * Allows user to assign table to customer based on capacity
     * @return int
     */
	public static int assignTable() {
    int tableId = 0, pax = 0, step = 0, exit = 10;
    boolean suitable = false;
    for(step = 0; step<2; step++){
      switch(step){
        case 0:
        System.out.println("====  -1: Quit  ||  -2: Back  ====");
          System.out.println("How many pax?");
          try {
        	  pax = sc.nextInt();
          }catch(InputMismatchException e){
        	  System.out.println("Please enter integer only!");
        	  sc.nextLine();
        	  step--;
        	  break;
          }
          
          if(pax== -1 || pax == -2) {
            step = exit;
          }
          break;
        case 1:
        	if(pax > 10 || pax < 1) {
        		System.out.println("No suitable tables");
        		break;
        	}
        	checkAvailTables(pax);
	  		for(int j=0; j < tables.size(); j++) {
	  			if (!tables.get(j).getIsOccupied())
	  			{
	  				suitable = true;
	  				break;
	  			}
	  		}
	  		if(!suitable) {
	  			System.out.println("No available tables");
	  			break;
	  		}
	        do {
	            System.out.println("Choose a table:");
	            try {
	            	tableId = sc.nextInt();
	            }catch(InputMismatchException e){
	          	  	System.out.println("Please enter integer only!");
	          	  	sc.nextLine();
	          	  	continue;
	            }
	            
	            if(tableId==-1) {
	            	return tableId;
	            }else if(tableId==-2){
	            	step -= 2;
	              	break;
	            }else{
	            	try {
	            		if(!tables.get(tableId-1).getIsOccupied() && tables.get(tableId-1).getCapacity() >= pax) {
	            			tables.get(tableId-1).setIsOccupied(true);
		  	            	return tableId;
		  	            }else {
		  	            	System.out.println("Invalid Table, choose again");
		  	            }
	            	}catch(IndexOutOfBoundsException e){
	            		System.out.println("Table does not exist!");
	            	}
	              
	            }
	        }while(true);
      }
    }
    return -1;
	}

}