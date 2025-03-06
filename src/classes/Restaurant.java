package classes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Scanner;

import controllers.Menu;

/**
 * Represents the Restaurant entity which has the different components of a restaurant like staff,member,table,menu,reservation and order.
 *
 */
public class Restaurant {
	
	/**
	 * staffList is an ArrayList of Staff
	 */
	private static ArrayList<Staff> staffList;
	/**
	 * memberList is an ArrayList of Member
	 */
	private static ArrayList<Member> memberList;
	/**
	 * tableList is an ArrayList of Table
	 */
	private static ArrayList<Table> tableList;
	/**
	 * order_dict is a dictionary which matches a invoice/order id to each Order object
	 */
	private static Hashtable<Integer, Order> order_dict;
	/**
	 * reservationList is an ArrayList of Reservation
	 */
	private static ArrayList<Reservation> reservationList;
	/**
	 * foodMenu is an ArrayList of AlaCarte
	 */
	private static ArrayList<AlaCarte> foodMenu;
	/**
	 * promoMenu is an ArrayList of PromotionPackage
	 */
	private static ArrayList<PromotionPackage> promoMenu;
	/**
	 * invoiceId is an array of integer objects for tracking the invoiceId
	 */
	private static int[] invoiceId;
	/**
	 * backtrack is a boolean value for users to backtrack in the case of false inputs
	 */
	private static boolean backtrack = false;

	static Scanner sc = new Scanner(System.in);
	
	/**
	 * saveState saves the data of the application into a txt file
	 */
	public static void saveState() {
		Object[] savedData 	= {
				staffList,
				memberList,
				tableList,
				order_dict,
				reservationList,
				Menu.getFoodMenu(),
				Menu.getPromoMenu(),
				invoiceId
				};
		try {			
			FileOutputStream f = new FileOutputStream(new File("restaurantData.txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);
			// Write objects to file
			o.writeObject(savedData);

			o.close();
			f.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * loadState loads the data from the saved txt file into the application through the respective objects;
	 */
	@SuppressWarnings("unchecked")
	public static void loadState() {
		Object[] loadedData=null;
		try {
			FileInputStream fi = new FileInputStream(new File("restaurantData.txt"));
			ObjectInputStream oi = new ObjectInputStream(fi);
			
			loadedData = (Object[]) oi.readObject();
			if(loadedData != null){
				staffList = (ArrayList<Staff>) loadedData[0];
				memberList = (ArrayList<Member>) loadedData[1];
				tableList = (ArrayList<Table>) loadedData[2];
				order_dict = (Hashtable<Integer,Order>) loadedData[3];
				reservationList = (ArrayList<Reservation>) loadedData[4];
				Menu.setFoodMenu((ArrayList<AlaCarte>) loadedData[5]);
				Menu.setPromoMenu((ArrayList<PromotionPackage>) loadedData[6]);
				invoiceId = (int[]) loadedData[7];
			}
			
			oi.close();
			fi.close();
		}catch (FileNotFoundException e) {
			System.out.println("No existing file found, will create blank slate");
			initialData();
		}catch (IOException e) {
			System.out.println("Error initializing stream");
			initialData();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
			initialData();
		}
	}
	
	/**
	 * refresh allows the restaurant to update the current reservations and tables occupied
	 */
	public static void refresh() {
		Calendar c1 = Calendar.getInstance();
		long now = c1.getTime().getTime();
		int i;
		Reservation r;
		for(i=0; i<reservationList.size(); i++) {
			r = reservationList.get(i);
			
			// Mark table occupied if currentTime is 30min before reservation
			if(r.getDateTime().getTime() - now <= 1800000) {
				tableList.get(r.getTableId()-1).setIsOccupied(true);				
			}
			
			// Remove reservation if currentTime has passed timing by 10min
			if(now - r.getDateTime().getTime() >= 600000) {
				reservationList.remove(i);
				i--;
				//if customer didn't come for reservation - make available for others
				if(!r.getCustomerArrived()) {
					tableList.get(r.getTableId()-1).setIsOccupied(false);
				}
			}
		}
	}
	
	/**
	 * initialData initialises the data in the various objects when no txt file is found in directory
	 */
	public static void initialData() {
		order_dict = new Hashtable<Integer,Order>();
		reservationList = new ArrayList<Reservation>();
		foodMenu = new ArrayList<AlaCarte>();
		
		AlaCarte f1 = new AlaCarte("Fries", "Crispy potato fries", 2.50, itemType.mainCourse);
		foodMenu.add(f1);
		AlaCarte f2 = new AlaCarte("Burger", "Beef patty in bread", 5.70, itemType.mainCourse);
		foodMenu.add(f2);
		AlaCarte f3 = new AlaCarte("Soda", "Gasy and sweet", 1.50, itemType.drink);
		foodMenu.add(f3);
		AlaCarte f4 = new AlaCarte("Vanilla ice cream", "Freezing goodness with fragrance", 3.20, itemType.dessert);
		foodMenu.add(f4);
		
		
		PromotionPackage p1 = new PromotionPackage("Value meal", 8.30, "Burger set");
		p1.addItem(f1);
		p1.addItem(f2);
		p1.addItem(f3);
		
		PromotionPackage p2 = new PromotionPackage("Good for kids", 4.50, "Kids set");
		p2.addItem(f1);
		p2.addItem(f4);
		
		promoMenu = new ArrayList<PromotionPackage>();
		promoMenu.add(p1);
		promoMenu.add(p2);
		Menu.setFoodMenu(foodMenu);
		Menu.setPromoMenu(promoMenu);
		invoiceId = new int[1];
		invoiceId[0] = 1;
		createStaff();
		createMembers();
		createTables();
	}
	
	/**
	 * createStaff generates the Staff to put into the staffList
	 */
	public static void createStaff() {
		staffList = new ArrayList<Staff>();
		//Staff(String name, genderType gender, int staffId, jobType jobTitle)
		staffList.add(new Staff("Lisa", genderType.female, 1, jobType.waiter));
		staffList.add(new Staff("Gordon", genderType.male, 2, jobType.chef));
		staffList.add(new Staff("Karen", genderType.female, 3, jobType.manager));
		staffList.add(new Staff("Avery", genderType.others, 4, jobType.receptionist));
	}
	
	/**
	 * createMembers generates the Member to put into the memberList
	 */
	public static void createMembers() {
		memberList = new ArrayList<Member>();
		//Member(int id, String name, String contactNo, genderType gender)
		memberList.add(new Member(1, "Parker", "91237940", genderType.male));
		memberList.add(new Member(2, "Stephanie", "99384590", genderType.female));
		memberList.add(new Member(3, "Joe", "90384124", genderType.male));
		memberList.add(new Member(4, "Hank", "99048301", genderType.others));
	}
	
	/**
	 * createTables generates the Table to put into the tableList
	 */
	public static void createTables() {
		tableList = new ArrayList<Table>();
		int i;
		for(i=0; i<20; i++) {
			if(i<4) {
				tableList.add(new Table(i+1, 2));
			}else if(i<8) {
				tableList.add(new Table(i+1, 4));
			}else if(i<12) {
				tableList.add(new Table(i+1, 6));
			}else if(i<16) {
				tableList.add(new Table(i+1, 8));
			}else if(i<20) {
				tableList.add(new Table(i+1, 10));
			}
		}
	}
	
	/**
	 * returns staffList
	 * @return ArrayList<Staff> getStaffList
	 */
	public static ArrayList<Staff> getStaffList(){
		return staffList;
	}
	
	/**
	 * returns memberList
	 * @return ArrayList<Member> getMemberList
	 */
	public static ArrayList<Member> getMemberList(){
		return memberList;
	}
	
	/**
	 * returns tableList
	 * @return ArrayList<Table>
	 */
	public static ArrayList<Table> getTableList(){
		return tableList;
	}
	
	/**
	 * returns reservationList
	 * @return ArrayList<Reservation>
	 */
	public static ArrayList<Reservation> getReservationList(){
		return reservationList;
	}
	
	/**
	 * returns ArrayList of AlaCartes on the menu
	 * @return ArrayList<AlaCarte> getFoodMenu
	 */
	public static ArrayList<AlaCarte> getFoodMenu(){
		return Menu.getFoodMenu();
	}
	
	/**
	 * returns ArrayList of PromotionPackages on the menu
	 * @return ArrayList<PromotionPackage>
	 */
	public static ArrayList<PromotionPackage> getPromoMenu(){
		return Menu.getPromoMenu();
	}
	
	/**
	 * returns order_dict
	 * @return Hashtable<Integer, Order>
	 */
	public static Hashtable<Integer, Order> getOrderDict(){
		return order_dict;
	}
	
	/**
	 * return invoiceId array
	 * @return int[]
	 */
	public static int[] getInvoiceId(){
		return invoiceId;
	}
	
	/**
	 * returns backtrack boolean value
	 * @return boolean
	 */
	public static boolean getBackTrack(){
	    return backtrack;
	}
	
	/**
	 * 
	 * sets value of backtrack
	 */
	public static void setBackTrack(boolean tf){
		backtrack = tf;
	}    
  
}