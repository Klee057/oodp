package controllers;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import classes.AlaCarte;
import classes.PromotionPackage;
import classes.itemType;

/**
 * Represents the menu that customer can choose from to order
 *
 */
public class Menu {
	/**
	 * foodMenu is an ArrayList of AlaCartes on the menu
	 */
	private static ArrayList<AlaCarte> foodMenu;
	/**
	 * promoMenu is an ArrayList of PromotionPackages on the menu
	 */
	private static ArrayList<PromotionPackage> promoMenu;
	static Scanner sc = new Scanner(System.in);
	
	/**
	 * Displays the interface for user to interact with the Menu and update it
	 */
	public static void displayOptions() {
		int choice=0;

			do {
				System.out.println("\n");
				System.out.println("Select Option:");
				System.out.println("(1) Print out Full Menu");
				System.out.println("(2) Print out AlaCarte menu");
				System.out.println("(3) Update AlaCarte menu");
				System.out.println("(4) Print out Promo Package menu");
				System.out.println("(5) Update Promotion Package menu");
				System.out.println("(6) Exit");
				System.out.print("Enter the number of your choice: ");
				try {
					choice = sc.nextInt();
				}catch(InputMismatchException e) {
					sc.nextLine();
					System.out.println("Please enter integers only!");
					continue;
				}

				switch(choice) {
					case 1:
						printCombinedMenu();
						break;
					case 2:
						printAlaCarteMenu();
						break;
					case 3:
						updateAlaCarte();
						break;
					case 4:
						printPromoPackageMenu();
						break;
					case 5:
						updatePromoMenu();
						break;
				}

			}while(choice <= 5);
		}
	
	/**
	 * Returns ArrayList of ala carte items on the menu
	 * @return ArrayList<AlaCarte>
	 */
	public static ArrayList<AlaCarte> getFoodMenu() {
		return foodMenu;
	}
	/**
	 * Sets the ArrayList of ala carte items on the menu
	 * @param menuItems
	 */
	public static void setFoodMenu(ArrayList<AlaCarte> menuItems) {
		foodMenu = menuItems;
	}
	/**
	 * Returns ArrayList of PromotionPackages on the Menu
	 * @return
	 */
	public static ArrayList<PromotionPackage> getPromoMenu() {
		return promoMenu;
	}
	/**
	 * Sets the ArrayList of PromotionPackages on the Menu
	 * @param packages
	 */
	public static void setPromoMenu(ArrayList<PromotionPackage> packages) {
		promoMenu = packages;
	}
	/**
	 * Displays options to update ala carte items on the menu
	 */
	public static void updateAlaCarte() {
		int id, choice=0;
		String name, des;
		itemType itemtype;
		double itemPrice;
		
		do {
			System.out.print("\n");
			System.out.println("--------------Update AlaCarte--------------");
			System.out.println("(1) Add new item to menu");
			System.out.println("(2) Update item in menu");
			System.out.println("(3) Remove item from menu");
			System.out.println("(4) Exit");
			System.out.print("\n");
			System.out.print("Enter the number of your choice: ");
			try {
				choice = sc.nextInt();
			}catch(InputMismatchException e) {
				sc.nextLine();
				System.out.println("Please enter integers only!");
				continue;
			}
			switch (choice) {
				case 1:
					System.out.print("Enter the item name: ");             
					sc.nextLine();                                         
					name = sc.nextLine();                                  
					System.out.print("Enter the item description: ");      
					des = sc.nextLine();                                   
					System.out.print("Enter the item price: ");            
					try {                                                  
						itemPrice = sc.nextDouble();                       
						System.out.println("Enter the item type: ");       
						printItemType();                                   
						itemtype = itemType.values()[sc.nextInt()-1];     
						addMenuItem(name, des, itemPrice, itemtype);    
					}catch(IndexOutOfBoundsException e) {               
						System.out.println("Invalid Option!");          
					}catch(InputMismatchException e) {
						sc.nextLine();
						System.out.println("Please enter numbers only!");
					}
					break;
				case 2:
					printAlaCarteMenu();
					if (foodMenu.size() == 0) {
						break;
					}
					updateMenuItem();
					break;
				case 3:
					printAlaCarteMenu();
					if (foodMenu.size() == 0) {
						break;
					}
					System.out.print("Enter the ID of the item to remove: ");
					try {
						id = sc.nextInt();
					}catch(InputMismatchException e) {
						sc.nextLine();
						System.out.println("Please enter integers only!");
						break;
					}
					removeMenuItem(id-1);
					break;
				case 4:
					break;
				default:
					System.out.println("Invalid Option");
					break;
			}
		}while (choice != 4);

	}
	/**
	 * Prints out a list of available itemTypes on the menu
	 */
	public static void printItemType() {
		int i=0;
		for (itemType itemtype : itemType.values()) {
			System.out.print("(" + (i+1) + ")");
			System.out.println(itemtype);
			i++;
		}
	}
	/**
	 * Displays options to update PromotionPackages on the menu
	 */
	public static void updatePromoMenu() {
		int id, choice=0;
		String name, des;
		double promoPrice;

		do {
			System.out.print("\n");
			System.out.println("--------------Update Promotion Package--------------");
			System.out.println("(1) Add new Promotion Package");
			System.out.println("(2) Remove Promotion Package");
			System.out.println("(3) Update Promotion Package");
			System.out.println("(4) Exit");
			System.out.print("\n");
			System.out.print("Enter the number of your choice: ");
			try {
				choice = sc.nextInt();
			}catch(InputMismatchException e){
				sc.nextLine();
				System.out.println("Please enter integer only!");
				continue;
			}
			switch (choice) {
				case 1:
					System.out.print("Enter the promotion package name: ");
					sc.nextLine();
					name = sc.nextLine();
					System.out.print("Enter the promotion package description: ");
					des = sc.nextLine();
					System.out.print("Enter the promotion package price: ");
					try {
						promoPrice = sc.nextDouble();
					}catch(InputMismatchException e){
						sc.nextLine();
						System.out.println("Please enter numbers only!");
						break;
					}
					addPromo(des, promoPrice, name);
					break;
				case 2:
					printPromoPackageMenu();
					if (promoMenu.size() == 0) {
						break;
					}
					System.out.println("Enter ID of promotion package to remove");
					try {
						id = sc.nextInt();
					}catch(InputMismatchException e){
						sc.nextLine();
						System.out.println("Please enter integer only!");
						break;
					}					
					removePromo(id-1);
					break;
				case 3:
					printPromoPackageMenu();
					if (promoMenu.size() == 0) {
						break;
					}
					updatePromo();
					break;
				case 4:
					break;
				default:
					System.out.println("Invalid option");
			}
		}while (choice != 4);
	}
	
	/**
	 * Prints out the list of AlaCartes on the menu
	 */
	public static void printAlaCarteMenu() {
		if (foodMenu.size() == 0) {
			System.out.println("Ala carte menu is empty!");
			return;
		}
		System.out.println("--------------Ala carte Menu--------------");
		for (int i=0;i<foodMenu.size();i++) {
			AlaCarte item = foodMenu.get(i);
			System.out.println("Id: " + (i+1));
			System.out.println("Name: " + item.getItemName());
			System.out.println("Description: " + item.getDescription());
			System.out.println("Price: " + item.getPrice());
			System.out.println("Type: " + item.getType());
			System.out.print("\n");
		}
	}
	
	/**
	 * Prints out the list of PromotionPacakges on the menu
	 */
	public static void printPromoPackageMenu() {
		if (promoMenu.size() == 0) {
			System.out.println("Promotion Package menu is empty!");
			return;
		}
		System.out.println("--------------Promotion Packages--------------");
		for (int j=0;j<promoMenu.size();j++) {
			PromotionPackage pack = promoMenu.get(j);
			System.out.println("Id: " + (j+1));
			System.out.println("Package Name: " + pack.getItemName());
			System.out.println("Package Description: " + pack.getDescription());
			System.out.println("Package Total Price: " + pack.getPrice());
			System.out.print("\n");
			System.out.println("Items in Package:");

			ArrayList <AlaCarte> promoList = promoMenu.get(j).getPromoList();
			for (AlaCarte item : promoList) {
				System.out.println("Name: " + item.getItemName());
			}
			System.out.print("\n");
		}
	}
	
	/**
	 * Prints out the list of ala carte items and PromotionPackages on the menu
	 */
	public static void printCombinedMenu() {
		printAlaCarteMenu();
		printPromoPackageMenu();
	}

	/**
	 * Adds an ala carte item to the menu. If there is a duplicate on menu, then reject the entry
	 * @param itemName
	 * @param description
	 * @param price
	 * @param type
	 */
	public static void addMenuItem(String itemName, String description, double price, itemType type) {
		for (AlaCarte item : foodMenu) {
			if(item.getItemName().equals(itemName)) {
				System.out.println("This item already exists in the menu.");
				return;
			}
		}

		AlaCarte item2;
		item2 = new AlaCarte(itemName, description, price, type);
		foodMenu.add(item2);
		System.out.println(itemName + " added to the menu!");
	}

	/**
	 * Remove an ala carte item from the menu. If item does not exist, then reject the removal
	 * @param id
	 */
	public static void removeMenuItem(int id) {
		
		try{
			for (PromotionPackage pack : promoMenu) {
				if(pack.getPromoList().contains(foodMenu.get(id))) {
					pack.removeItem(foodMenu.get(id));
				}
			}
			foodMenu.remove(id);
			System.out.println("Item removed!");
		}catch(IndexOutOfBoundsException e) {
			System.out.println("Invalid option! This item does not exist!");
		}catch(InputMismatchException e) {
			System.out.println("Invalid option!");
		}
	}
	
	/**
	 * Displays the options to update a specific AlaCarte
	 */
	public static void updateMenuItem() {
		int id, choice=0;
		double newPrice;
		while(true) {
			System.out.println("ID of the item you would like to update: ");
			try {
				id = sc.nextInt();
				break;
			}catch(InputMismatchException e) {
				sc.nextLine();
				System.out.println("Please enter integers only!");
			}
		}
		

		if (id <= foodMenu.size() && id > 0) {
			while(true) {
				System.out.println("What would you like to update?");
				System.out.println("(1) Name");
				System.out.println("(2) Description");
				System.out.println("(3) Price");
				try {
					choice = sc.nextInt();
					break;
				}catch(InputMismatchException e) {
					sc.nextLine();
					System.out.println("Please enter integers only!");
				}
			}
			switch (choice) {
				case 1:
					System.out.println("Input new name: ");
					sc.nextLine();
					String newName;
					newName = sc.nextLine();
					foodMenu.get(id-1).setItemName(newName);
					System.out.println("New name " + foodMenu.get(id-1).getItemName() + " successfully updated!");
					break;
				case 2:
					System.out.println("Input new description: ");
					sc.nextLine();
					String newDes;
					newDes = sc.nextLine();
					foodMenu.get(id-1).setDescription(newDes);
					System.out.println("New description " + foodMenu.get(id-1).getDescription() + " successfully updated!");
					break;
				case 3:
					System.out.println("Input new price: ");
					try {
						newPrice = sc.nextDouble();
					}catch(InputMismatchException e) {
						sc.nextLine();
						System.out.println("Please enter integers only!");
						break;
					}
					foodMenu.get(id-1).setPrice(newPrice);
					System.out.println("New price " + foodMenu.get(id-1).getPrice() + " successfully updated!");
					break;
			}
		}
		else {
			System.out.println("This item does not exist!");
		}
	}

	/**
	 * Adds a PromotionPackage to the menu and allow user to choose which items to include in package
	 * @param description
	 * @param price
	 * @param packName
	 */
	public static void addPromo(String description, double price, String packName) {
		int id;
		String add = "";
		PromotionPackage newPackage = new PromotionPackage(description, price, packName);
		boolean isAdding = true;

		while (isAdding) {
			printAlaCarteMenu();
			System.out.println("Enter item ID to add in Promotion Package");
			try {
				id = sc.nextInt();
			}catch(InputMismatchException e){
				sc.nextLine();
				System.out.println("Please enter integer only!");
				continue;
			}
			
			if (id <= foodMenu.size() && id > 0) {
				AlaCarte item = foodMenu.get(id-1);
				newPackage.addItem(item);
			}
			else {
				System.out.println("Sorry, this item does not exist!");
			}
			System.out.println("Continue adding item to Promotion Package? (Y/N)");
			sc.nextLine();
			add = sc.nextLine();
			if (Character.toUpperCase(add.charAt(0)) != 'Y') {
				isAdding = false;
			}
		}
		promoMenu.add(newPackage);
		System.out.println("Promotion Package " + newPackage.getItemName() + " successfully added!");
	}

	/**
	 * Removes PromotionPackage from the menu
	 * @param id
	 */
	public static void removePromo(int id) {
		try {			
			promoMenu.remove(id);
			System.out.println("Promotion Package successfully removed!");
		}catch(IndexOutOfBoundsException e) {
			System.out.println("This Promotion Package does not exist!");
		}
		
	}

	/**
	 * Displays the options to update the PromotionPackages
	 * 
	 */
	public static void updatePromo() {
		int id, choice, c, id2;
		double newPrice;

		printPromoPackageMenu(); //call to printMenu so that can choose index
		System.out.println("ID of the package you would like to update: ");
		try {
			id = sc.nextInt();
		}catch(InputMismatchException e){
			sc.nextLine();
			System.out.println("Please enter integer only!");
			return;
		}

		if (id <= promoMenu.size() && id>0) {
			PromotionPackage updatePackage = promoMenu.get(id-1);
			System.out.println("What would you like to update?");
			System.out.println("(1) Name");
			System.out.println("(2) Description");
			System.out.println("(3) Price");
			System.out.println("(4) Items in Package");
			try {
				choice = sc.nextInt();
			}catch(InputMismatchException e){
				sc.nextLine();
				System.out.println("Please enter integer only!");
				return;
			}

			switch (choice) {
				case 1:
					System.out.println("Input new name: ");
					String newName;
					sc.nextLine();
					newName = sc.nextLine();
					promoMenu.get(id-1).setItemName(newName);
					System.out.println("New name successfully updated!");
					break;
				case 2:
					System.out.println("Input new description: ");
					String newDes;
					sc.nextLine();
					newDes = sc.nextLine();
					promoMenu.get(id-1).setDescription(newDes);
					System.out.println("New description successfully updated!");
					break;
				case 3:
					System.out.println("Input new price: ");
					try {
						newPrice = sc.nextDouble();
					}catch(InputMismatchException e){
						sc.nextLine();
						System.out.println("Please enter numbers only!");
						break;
					}
					promoMenu.get(id-1).setPrice(newPrice);
					System.out.println("New price of promotion Package: " + promoMenu.get(id-1).getPrice());
					break;
				case 4:
					System.out.println("Would you like to add or remove item from Promotion Package?");
					System.out.println("(1) Add item");
					System.out.println("(2) Remove item");
					try {
						c = sc.nextInt();					
						switch(c) {
							case 1:
								printAlaCarteMenu();
								System.out.println("Enter item ID to add in Promotion Package");
								id2 = sc.nextInt();
								if (id2 <= foodMenu.size()) {
									AlaCarte item = foodMenu.get(id2-1);
									updatePackage.addItem(item);
									//System.out.println(item.getItemName() + "added to Promotion Package!");
								}
								else {
									System.out.println("Sorry, this item does not exist!");
								}
								break;
							case 2:
								int removeId;
								System.out.println("Enter the item ID to remove from Promotion Package");
								ArrayList <AlaCarte> promoList = updatePackage.getPromoList();
								for (int i=0;i<promoList.size();i++) {
									System.out.println("("+(i+1)+") " + promoList.get(i).getItemName());
								}
								System.out.println("Enter the ID of item to remove from Package");
								removeId = sc.nextInt();
								String removeName = promoList.get(removeId-1).getItemName();
								promoList.remove(removeId-1);
								System.out.println(removeName + " removed from Promotion Package!");
								break;
							default:
								System.out.println("Invalid Option!");
								break;
						}
					}catch(InputMismatchException e){
						sc.nextLine();
						System.out.println("Please enter integer only!");
						break;
					}catch(IndexOutOfBoundsException e) {
						System.out.println("Sorry, this item does not exist!");
					}
			}
		}
		else {
			System.out.println("This item does not exist!");
		}
	}

}