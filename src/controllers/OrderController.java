package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

import classes.AlaCarte;
import classes.Order;
import classes.PromotionPackage;
import classes.Restaurant;

/**
 * OrderController allows users to manage orders, print Order Invoice and Sales Revenue
 */
public class OrderController {
	/**
	 * Dictionary to store the orders that the Restaurant has taken
	 */
	private static Hashtable<Integer, Order> orders = Restaurant.getOrderDict();
	/**
	 * ArrayList of ala carte items in the Menu
	 */
	private static ArrayList<AlaCarte> foodMenu = Restaurant.getFoodMenu();
	/**
	 * ArrayList of PromotionPackages in the Menu
	 */
	private static ArrayList<PromotionPackage> promoMenu = Restaurant.getPromoMenu();
	/**
	 * An array of the month values
	 */
	private static String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	static Scanner sc = new Scanner(System.in);
	
	/**
	 * Displays the options to interact with Orders, print order invoice or sales revenue
	 */
	public static void displayOptions() {
		int choice=0, orderId;
		double discount;
        do {
        	if(!Restaurant.getBackTrack()) {
        		System.out.println("\n");
        		System.out.println("Select option");
        		System.out.println("(1) Update Order");
        		System.out.println("(2) Remove Order");      
        		System.out.println("(3) Print Order Invoice");
        		System.out.println("(4) Print Sales Revenue");
        		System.out.println("(5) Back");
        		try {
        			choice = sc.nextInt();
        		}catch(InputMismatchException e){
        			sc.nextLine();
        			System.out.println("Please enter integer only!");
        			continue;
        		}
        	}else{
                Restaurant.setBackTrack(false);
            }
        	switch(choice) {
        		case 1:
        			if(!printUnpaidOrders()) {
        				try {
            				System.out.println("Which Order do you want to update?(Select by ID) (-2)Back");
                			orderId = sc.nextInt();
                            if(orderId == -2){
                                choice -=2;
                                break;
                            }
                			updateOrder(orders.get(orderId));
            			}catch(InputMismatchException e) {
            				sc.nextLine();
    						System.out.println("Please enter integer only!");
    					}catch(NullPointerException e) {
    						System.out.println("Order does not exist!");
    					}
        			}
                    break;
        		case 2:
        			System.out.println("\n");
        			if(!printUnpaidOrders()) {
        				try {
        					System.out.println("\n");
                			System.out.println("Which Order do you want to remove?(Select by ID) (-2)Back");
                			orderId = sc.nextInt();
                            if(orderId == -2){
                                choice -=2;
                                break;
                            }
                			removeOrder(orderId);
            			}catch(InputMismatchException e) {
            				sc.nextLine();
    						System.out.println("Please enter integer only!");
    					}
        			}        			
                    break;
        		case 3:
        			System.out.println("\n");
        			if(!printUnpaidOrders()) {
        				try {
        					System.out.println("\n");
            				System.out.println("Which OrderInvoice do you want to print?(Select by ID) (-2)Back");
                			orderId = sc.nextInt();
                            if(orderId == -2){
                                choice -=2;
                                break;
                            }
                			if(orders.get(orderId).getIsPaid()) {
                				System.out.println("Invalid unpaid order");
                				break;
                			}
							discount = checkDiscount();

							if(discount == 0.0) break;
                			orders.get(orderId).setDiscount(discount);
                			orders.get(orderId).printInvoice();
            			}catch(InputMismatchException e) {
            				sc.nextLine();
    						System.out.println("Please enter integer only!");
    					}catch(NullPointerException e) {
    						System.out.println("Order does not exist!");
    					}
        			}        			
        			break;
        		case 4:
        			int month;
        			System.out.println("\n");
        			System.out.println("Print for which month? (-2)Back");
        			try {
        				month = sc.nextInt();
                        if(month==-2){
                            choice-=2;
                            break;
                        }
        			}catch(InputMismatchException e){
        				sc.nextLine();
        				System.out.println("Please enter integer only!");
        				break;
        			}
        			if(month>12 || month<1) {
        				System.out.println("Invalid month");
        				break;
        			}
        			printSaleRevenue(month);
        			break;
        		case 5:
        			break;
                default:
                	System.out.println("Invalid Option, Try again.");
        	}        	
        }while(choice!=5);
	}

	/**
	 * Allows staff to create order for the respective table while storing the staff taking order and invoiceId
	 * @param tableId
	 */
	public static void createOrder(int tableId) {
		int staffId = 0;
		Order currentOrder;
    	if(Restaurant.getBackTrack()) {
            Restaurant.setBackTrack(false);
            return;
        }
		int invoiceId = Restaurant.getInvoiceId()[0];
		while(true) {
			System.out.println("Who is taking this order?");
			StaffController.printStaffList();
			try {
				staffId = sc.nextInt();
				Calendar c1 = Calendar.getInstance();
				Date now = c1.getTime();
				
				currentOrder = new Order(tableId, now, Restaurant.getStaffList().get(staffId-1), invoiceId);
				orders.put(invoiceId, currentOrder);
				break;
			}catch(InputMismatchException e) {
				sc.nextLine();
				System.out.println("Please enter integers only!");
			}catch(IndexOutOfBoundsException e) {
				System.out.println("Invalid Staff ID");
			}
		}
		Restaurant.getInvoiceId()[0]++;
		updateOrder(currentOrder);
	}

	/**
	 * Allows staff to update current orders by adding or removing more Alacarte items or Promotion Packages
	 * @param order
	 */
	
	public static void updateOrder(Order order) {
		if(order.getIsPaid()) {
			System.out.println("Cannot update a paid order!");
			return;
		}
		int choice=0, orderOption, quantity;
		Menu.printCombinedMenu();
		do {
			System.out.println("What would you like to do?");
			System.out.println("(1) Order Ala carte (2) Order Promotion Package (3) Remove from order (4) Back");
			try {
				choice = sc.nextInt();
			}catch(InputMismatchException e){
				sc.nextLine();
				System.out.println("Please enter integer only!");
				continue;
			}
			switch(choice) {
				case 1:
					try {
						System.out.println("Which Ala carte item do you want?");
						orderOption = sc.nextInt();
						if(orderOption < 1 || orderOption > foodMenu.size()){
					    System.out.println("Invalid Option, try again.");
              break;
            }
            System.out.println("What is the quantity you want?");
						quantity = sc.nextInt();
						order.addItem(orderOption, quantity);
					}catch(InputMismatchException e) {
						System.out.println("Please enter integer only!");
					}
					break;
				case 2:
					try {
						System.out.println("Which Promotion Package do you want?");
						orderOption = sc.nextInt();
            if(orderOption < 1 || orderOption > promoMenu.size()){
					    System.out.println("Invalid Option, try again.");
              break;
            }
						System.out.println("What is the quantity you want?");
						quantity = sc.nextInt();
						order.addPromo(orderOption, quantity);
					}catch(InputMismatchException e) {
						System.out.println("Please enter integer only!");
					}
					break;
				case 3:
					removeFromOrder(order);
				case 4:
					break;
				default:
					System.out.println("Invalid Option, try again.");
					break;
			}
		}while(choice!=4);
	}

	/**
	 * Allows staff to remove an Alacarte item or Promotion Package from order
	 * @param order
	 */
	public static void removeFromOrder(Order order) {
		int choice=0, removeOption, quantity;
		order.printAlacItems();
		order.printPromoItems();
		do {
			System.out.println("What would you like to remove?");
			System.out.println("(1) Ala carte (2) Promotion Package (3) Back");
			try {
				choice = sc.nextInt();
			}catch(InputMismatchException e) {
				sc.nextLine();
				System.out.println("Please enter integers only!");
				continue;
			}
			switch(choice) {
				case 1:
					try {
						System.out.println("Which Ala carte item do you want to remove?");
						removeOption = sc.nextInt();

						System.out.println("What is the quantity you want to remove?");
						quantity = sc.nextInt();
						order.removeItem(removeOption, quantity);
					}catch(InputMismatchException e) {
						System.out.println("Please enter integer only!");
					}
					break;
				case 2:
					try {
						System.out.println("Which Promotion Package do you want to remove?");
						removeOption = sc.nextInt();
						System.out.println("What is the quantity you want to remove?");
						quantity = sc.nextInt();
						order.removePromo(removeOption, quantity);
					}catch(InputMismatchException e) {
						System.out.println("Please enter integer only!");
					}
					break;
				case 3:
					break;
				default:
					System.out.println("Invalid Option, try again.");
					break;					
			}
		}while(choice!=3);
	}

	/**
	 * Returns the discount value of the table by checking membership validity
	 * @return double
	 */
	public static double checkDiscount() {
		char c;
		do {
			System.out.println("Is the customer a member? (Y/N)  ||  B to back");
			c = sc.next().charAt(0);
			sc.nextLine();
      if(c=='b' || c=='B'){
        Restaurant.setBackTrack(true);
        return 0.0;
      }
			switch(c) {
				case 'y':
				case 'Y':
					if(MemberController.checkMembership()) {
						return 0.7;
					}else {
						break;
					}					
				case 'n':
				case 'N':
					return 1.0;
				default:
					System.out.println("Invalid option");
					break;
			}
		}while(true);
	}

	/**
	 * Prints out OrderId, TableId and staff assigned to respective TableId of all Unpaid orders
	 * @return boolean
	 */
	public static boolean printUnpaidOrders() {
		Set<Integer> setOfKeys = orders.keySet();
		boolean noUnpaid = true;
		System.out.println("--------------Unpaid Orders--------------");
		for (Integer key : setOfKeys) {
			if(!orders.get(key).getIsPaid()) {
				System.out.println("OrderId:" + orders.get(key).getInvoiceId() + "\t TableId: " + orders.get(key).getTableId() + "\t Staff: " + orders.get(key).getServer().getName());
				noUnpaid = false;
			}				
		}
		if(noUnpaid) {
			System.out.println("There are no unpaid orders.");
		}
		return noUnpaid;
	}

	/**
	 * Removes whole order from dictionary of orders
	 * @param index
	 */
	public static void removeOrder(int index) {
        int cfm;
		try {
			if(orders.get(index).getIsPaid()) {
				System.out.println("Cannot remove order, it has already been paid.");
			}else {
                System.out.println("Confirm? (1)Yes (2)No");
                
                try {
        			cfm = sc.nextInt();
                    if(cfm == 1) {
                        orders.remove(index);
                        System.out.println("Order has been removed.");
                    }else if(cfm == 2){
                        return;
                    }
        		}catch(InputMismatchException e){
        			sc.nextLine();
        			System.out.println("Please enter integer only!");
        		}
			}
		}catch(NullPointerException e) {
			System.out.println("Order does not exist!");
		}	
	}

	/**
	 * Returns the month from Calendar
	 * @param timeStamp
	 * @return int
	 */
	public static int getMonth(Date timeStamp) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(timeStamp);
			return cal.get(Calendar.MONTH);
	}

	/**
	 * Prints out Sale Revenue and quantity of each item sold for the selected month
	 * @param month
	 */
	public static void printSaleRevenue(int month){
		Hashtable<Integer, Integer> byMonthAlac = new Hashtable<Integer, Integer>();
		Hashtable<Integer, Integer> byMonthPromo = new Hashtable<Integer, Integer>();
		ArrayList<AlaCarte> foodMenu = Restaurant.getFoodMenu();
		ArrayList<PromotionPackage> promoMenu = Restaurant.getPromoMenu();
	    double monthTotal = 0;
	    int qty=0;

	    Set<Integer> keySetOrder_dict = orders.keySet();
	    Set<Integer> alacKeySet = byMonthAlac.keySet();
	    Set<Integer> promoKeySet = byMonthPromo.keySet();

	    System.out.println("Month: " + monthName[month-1]);
	    System.out.println(String.format("%-15s |\t %s \t |%s","Item","Qty","Amt"));
	    System.out.println("======================================");

	    for(Integer key : keySetOrder_dict){		/* for each order, */
	      if(getMonth(orders.get(key).getTimestamp()) == month-1 && orders.get(key).getIsPaid()){		/* if month specified == order's month*/
	        Set<Integer> keySetAlac = orders.get(key).getAlac_dict().keySet();
	        Set<Integer> keySetPromo = orders.get(key).getPromo_dict().keySet();
	        for(Integer alacKey : keySetAlac){		/* iterate alac_dict, IF item is in bymonthAlac alr{aggregate quantity and add it in}*/
	          qty = orders.get(key).getAlac_dict().get(alacKey);		/* else just add it in*/
	          if(byMonthAlac.get(alacKey)!= null){
	          qty = qty + byMonthAlac.get(alacKey);
	            byMonthAlac.put(alacKey, qty);
	          }else{
	            byMonthAlac.put(alacKey, qty);
	          }
	        }
	        for(Integer promoKey : keySetPromo){
	          qty = orders.get(key).getPromo_dict().get(promoKey);		/* iterate promo_dict, IF item is alr in bymonthPromo aggregated qty .... same as above*/
	          if(byMonthPromo.get(promoKey)!= null){
	            qty = qty + byMonthPromo.get(promoKey);
	              byMonthPromo.put(promoKey, qty);
	            }else{
	              byMonthPromo.put(promoKey, qty);
	            }
	        }
	        monthTotal += orders.get(key).getTotalPrice();
	      }
	    }
	    // print out both bymonthAlac and bymonthPromo with their aggregated qty and prices AND monthTotal
	    for(Integer alacKey : alacKeySet){
	      System.out.println(String.format("%-15s |\t %s  \t |%s", foodMenu.get(alacKey-1).getItemName(),byMonthAlac.get(alacKey),byMonthAlac.get(alacKey)*foodMenu.get(alacKey-1).getPrice()));
//	      System.out.println(foodMenu.get(alacKey-1).getItemName() + "		|	" + byMonthAlac.get(alacKey) + "	|	" +  byMonthAlac.get(alacKey)*foodMenu.get(alacKey-1).getPrice());
	    }
	    for(Integer promoKey : promoKeySet){
	      System.out.println(String.format("%-15s |\t %s  \t |%s", promoMenu.get(promoKey-1).getItemName(),byMonthPromo.get(promoKey),byMonthPromo.get(promoKey)*promoMenu.get(promoKey-1).getPrice()));
//	      System.out.println( promoMenu.get(promoKey-1).getPackName() + "		|	" + byMonthPromo.get(promoKey)+ "	|	" + byMonthPromo.get(promoKey)*promoMenu.get(promoKey-1).getPrice());
	    }
	    System.out.println("--------------------------------------");
	    System.out.println("Total(GST & service charge inclusive): " + String.format("%.2f", monthTotal));
	}

}