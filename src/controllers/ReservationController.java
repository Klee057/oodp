package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import classes.*;

/**
 * ReservationController allows user to check and manage reservations
 */
public class ReservationController {	
	static Scanner sc = new Scanner(System.in);
	/**
	 * ArrayList of all Tables in the restaurant;
	 */
	private static ArrayList<Table> allTables = Restaurant.getTableList();
	/**
	 * ArrayList of reservations made
	 */
	private static ArrayList<Reservation> reservationList = Restaurant.getReservationList();
	
	/**
	 * Displays the options to interact with reservations
	 */
	public static void displayOptions() {
		int choice = 0, pax = 0, x = 1;
		String d_str;
		Date dateTime;

		String name="", cNo="";
        do {
			System.out.println("\n");
        	System.out.println("Select option");
    		System.out.println("(1) Reserve Table");
            System.out.println("(2) Check/Remove Reservations");
			System.out.println("(3) Customer Arrive for Reservation");   
            System.out.println("(4) Back");
            try {
            	choice = sc.nextInt();
            	sc.nextLine();
            }catch(InputMismatchException e){
            	System.out.println("Please enter integers only!");
            	sc.nextLine();
            	continue;
            }
            
			if (choice == 1)
			{
				System.out.println("---------------- RESERVE  TABLE ------------------");
				System.out.println("============  -1: Quit  ||  -2: Back  ============");
				x = 1;
			}
			while (choice==1 && x > 0)
			{
				switch(x) {
					case 1:
						System.out.println("Enter Customer Name: ");
						name = sc.nextLine();
						if(name.equals("-1")) {
							x = Integer.parseInt(name);
						}else if(name.equals("-2")){
							x-=2;
						}
						x++;
						break;
					case 2:
						System.out.println("Enter Customer Contact No: ");
						cNo = sc.next();
						sc.nextLine();
						if(cNo.equals("-1")) {
							x = Integer.parseInt(cNo);
						}else if(cNo.equals("-2")){
							x-=2;
						}
						x++;
						break;
					case 3:
						try {
							System.out.println("Enter number of Pax (max 10):");
							pax = sc.nextInt();
							sc.nextLine();
						}catch(InputMismatchException e) {
							System.out.println("Enter numbers only!");
							sc.nextLine();
							break;
						}
						if(pax == -1) {
							x = pax;
						}else if(pax == -2){
							x-=2;
						}else if(pax < 1 || pax > 10) {
							System.out.println("Invalid number of pax");
							break;
						}
						x++;
						break;
					case 4:
						System.out.println("Enter Reservation Date [e.g 01/01/2021 06:00 PM]: ");
						d_str = sc.nextLine();
						if(d_str.equals("-1")) {
							x = Integer.parseInt(d_str);
						}else if(d_str.equals("-2")){
							x-=2;
						}
						else{
							// if it reaches here, all details, user has corrected all details for reservation
							// and does not want to quit
							SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
							try 
							{
								dateTime = format.parse(d_str);
								reserveTable(dateTime, pax, name, cNo);								
							} catch (ParseException | InputMismatchException e) {
								// if invalid date format
								System.out.println("Invalid date format, please follow [e.g 01/01/2021 06:00 PM]");
								break;
							}
							choice = 0; //break choice=1 while loop, into menu loop
						}
						x++;
						break;
					default:
						break;
				}
            }
			// end of reserveTable loop

			switch(choice)
			{
				case 2:
					checkAllReservation();
					break;
				case 3:
					acceptReservation();
					break;
				case 0:
				case 1:
				case 4:
					break;
				default:
					System.out.println("Invalid option");
					break; // cannot remove default, it catches choice = 0 at the end of reserveTable
			}
			
		}while(choice != 4);        	
	}
	/**
	 * Adds Reservation object with name, contactNo, pax, dateTime, tableId arguments into reservationList else print error message
	 * <p> Calls refresh() from Restaurant Class.
	 * Searches through reservationList for Table objects with capacity less or equal than pax and are available for 1hr duration at dateTime argument.
	 * If no Table objects satisfy the criteria, prints error message.
	 * Otherwise, it prints the list of Table objects that matches, ask for user input of tableId and adds Reservation object
	 * @param dateTime  Date and Time of Reservation Booking (format: dd/MM/yyyy hh:mm aa)
	 * @param pax  No. of people to allocate Table with appropriate capacity
	 * @param name Unique ID
	 * @param contactNo Unique ID
	 */
	public static void reserveTable(Date dateTime, int pax, String name, String contactNo) {
		ArrayList<Table> suiTables = new ArrayList<Table>();
		int i,j;
		boolean flag=false;
		
		Calendar c1 = Calendar.getInstance();
		c1.setTime(dateTime);
		c1.add(Calendar.HOUR,1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(dateTime);
		c2.add(Calendar.HOUR,-1);
		
		int ptr = 0;
		Reservation r;
		Restaurant.refresh();
		
		for(i=0; i < allTables.size(); i++) {
			if (allTables.get(i).getCapacity()>=pax)
			{
				suiTables.add(allTables.get(i));
			}
		}
				
		// stop checking when none suitable or finished checking all
		while (!suiTables.isEmpty() && ptr < suiTables.size())
		{
			//Go through reservationlist and check for clash
			for (i = 0; i < reservationList.size(); i++)
			{
				// if there is suiTable in reservationlist, check
				r = reservationList.get(i);
				if (r.getTableId()==suiTables.get(ptr).getTableId())
				{
					// if suitable tables is booked +- 1hr from dateTime == CLASH
					if (r.getDateTime().after(c2.getTime()) && r.getDateTime().before(c1.getTime()))
					{
						
						suiTables.remove(ptr); // not suiTable
						ptr--; // because it is removed, decrement ptr
						break; // escape for loop -> move to next suiTable
					}
					else
					{
						//it is suiTable -> leave it
						continue;
					}
				}
			}
			ptr++;
		}
		
		// if no suitable tables
		if(suiTables.isEmpty())
		{
			System.out.println("No suitable tables for Reservation");
			return;
		}
		// show available tables with suitable capacity
		System.out.println("These are the available tables");
		System.out.println("| Table ID | Capacity |");
		for (j = 0; j < suiTables.size(); j++)
		{
			System.out.printf("  " + suiTables.get(j).getTableId() + "\t\t" + suiTables.get(j).getCapacity() + "\n");
		}
		System.out.printf("Choose Table ID to book: ");
		int tableId = sc.nextInt(); // get input from user for tableId to book
		sc.nextLine();
		if (tableId == -1){
			return;
		}
		// if unsuitable tableid entered, keep on asking until they give correct input
		while (flag == false){
			for (j = 0; j < suiTables.size(); j++)
			{
				if (tableId == suiTables.get(j).getTableId())
				{
					flag = true;
					break;
				}
			}
			if(flag==false){
				System.out.println("Table not available!\n");
				System.out.println("CHOOSE AGAIN\n");
				tableId = sc.nextInt(); // get input from user for tableId to book
				sc.nextLine();
			}
		}
		
		Reservation r1 = new Reservation(dateTime, pax, name, contactNo, tableId);
		reservationList.add(r1);
		System.out.println("Successfully Reserved!");
	}
	
	/**
	 * Removes Reservation object at index position from reservationList
	 * <p> Calls refresh() from Restaurant Class. If index out of bounds, print error message. Otherwise, removes Reservation object at index position from reservationList
	 * @param index position of Reservation object in reservationList
	 */
	public static void removeReservation(int index) {
		Calendar c1 = Calendar.getInstance();
		long now = c1.getTime().getTime();
		Reservation r = reservationList.get(index);
		try {
			if(now-r.getDateTime().getTime()<=1800000) {
				allTables.get(r.getTableId()-1).setIsOccupied(false);
			}
			reservationList.remove(index);
			System.out.println("Reservation succesfully removed");
			return;
		}catch(IndexOutOfBoundsException e) {
			System.out.println("Invalid index.");
		}
		System.out.println("Reservation not found. Please try again");
	}
	/**
	 * Prints Reservation objects belonging to a Unique customer and returns ArrayList of Integers(Reservation index)
	 * @param name Unique ID
	 * @param contactNo Unique ID
	 * @return ArrayList of Integers (Reservation index)
	 */
	public static ArrayList<Integer> checkReservation(String name, String contactNo) {
		int flag = 0, i;
		// Create empty arraylist of indexes(reservationList) belonging to customer
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		Reservation r;
		Restaurant.refresh();

		for (i = 0; i < reservationList.size(); i++)
		{
			r = reservationList.get(i);
			//if same name & contact no
			if (name.equalsIgnoreCase(r.getName()) && contactNo.equals(r.getContactNo()))
			{
				flag++; // indicate found at least 1 reservation belonging to person
				System.out.printf("("+flag+") ");
				r.printReservation();
				tmp.add(i);
			}
		}
		if(flag>0) 
		{
			System.out.println();
			return tmp; // if found, end program and return arraylist
		}

		// else print cannot find
		System.out.println("No reservation found");
		return tmp;
	}

	/**
	 * Prints All Reservation objects in reservationList and (optional) removes 1 Reservation object from reservationList.
	 */
	public static void checkAllReservation() {
		int i, index;
		char c;

		Restaurant.refresh();
		if(reservationList.size()==0)
		{
			System.out.println("No reservations at the moment");
			return;
		}
		System.out.println("---------------------------------- RESERVATION LIST ------------------------------------------");
		System.out.println("INDEX");
		for (i = 0; i < reservationList.size(); i++)
		{
			System.out.print(i+1 + "\t");
			reservationList.get(i).printReservation();
		}
		System.out.println("Would you like to remove any reservation? (y/n) || (b)Back");
		c = sc.next().charAt(0);
		sc.nextLine();
		if (c == 'Y' || c == 'y')
		{
			System.out.println("Choose the index of reservation to remove");
			try {
				index = sc.nextInt();
			}catch(InputMismatchException e){
				sc.nextLine();
				System.out.println("Please enter integer only!");
				return;
			}
			
			removeReservation(index-1);
		}
	}
	
	/**
	 * Sets CustomerArrived attribute of Reservation class as true 
	 * @param name Unique ID
	 * @param contactNo Unique ID
	 */
	public static void acceptReservation() {
		int i, index;
		Calendar c1 = Calendar.getInstance();
		long now = c1.getTime().getTime();

		if(reservationList.size()==0)
			{
				System.out.println("No reservations at the moment");
				return;
			}
			
			System.out.println("INDEX");
			for (i = 0; i < reservationList.size(); i++)
			{
				System.out.print(i+1 + "\t");
				reservationList.get(i).printReservation();
			}
		System.out.println("Choose the index of reservation to mark arrive");
		try {
			index = sc.nextInt();
			if(index > reservationList.size() || index <= 0)
			{
				System.out.println("Reservation does not exist");
				return;
			}
			if(now-reservationList.get(index-1).getDateTime().getTime() - now <= 1800000) {
				System.out.println("Customer arrived too early, come back 30mins before Reservation");
				return;
			}
			reservationList.get(index-1).setCustomerArrived(true);
			System.out.printf("Reservation index %d is marked ARRIVED\n", i);
			OrderController.createOrder(reservationList.get(index-1).getTableId());
		}catch(InputMismatchException e) {
			sc.nextLine();
			System.out.println("Please enter integer only");
		}catch(IndexOutOfBoundsException e) {
			System.out.println("Reservation does not exist");
		}
		
		}

	}