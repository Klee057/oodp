import java.util.InputMismatchException;
import java.util.Scanner;

import classes.Restaurant;
import controllers.Menu;
import controllers.OrderController;
import controllers.ReservationController;
import controllers.TableController;

/**
 * Main Application class to start the RRPSS program.
 * This is the user interface for staff to manage, order and reserve.
 */
public class RestaurantApp {

	/**
	 * Load state from txt file
	 * Calls method homePageOptions to display main functionalities
	 * Saves state from application into txt file
	 * @param args
	 */
	public static void main(String[] args) {
		Restaurant.loadState();
		homePageOptions();
		Restaurant.saveState();
    }

	/**
	 * Method for user to select main functionality to manage
	 */

	public static void homePageOptions() {
		int choice=0;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("\n");
			System.out.println("What would you like to do?");
			System.out.println("(1) Menu");
			System.out.println("(2) Tables");
			System.out.println("(3) Order");
			System.out.println("(4) Reservations");
			System.out.println("(5) Exit");
			System.out.println("Enter option:");
			try {
				choice = sc.nextInt();
			}catch(InputMismatchException e){
				sc.nextLine();
				System.out.println("Please enter integer only!");
				continue;
			}			
			switch(choice) {
				case 1:
					Menu.displayOptions();
					break;
				case 2:
					TableController.displayOptions();
					break;
				case 3:
					OrderController.displayOptions();
					break;
				case 4:
					ReservationController.displayOptions();
					break;
				case 5:
					break;
				default:
					System.out.println("Invalid Option, Try again.");
					break;
			}			
		}while(choice < 5);
		sc.close();
	}

}