package controllers;

import java.util.ArrayList;

import classes.Restaurant;
import classes.Staff;

/**
 * Staff Controller handles methods related to Staff
 *
 */
public class StaffController {
	/**
	 * ArrayList of Staff in the Restaurant
	 */
	private static ArrayList<Staff> staffList = Restaurant.getStaffList();
	
	/**
	 * Prints out a list of the Staff working for the Restaurant
	 */
	public static void printStaffList() {
		for(int i=0; i < staffList.size(); i++) {
			System.out.println("(" + staffList.get(i).getStaffId() + ")\t" + staffList.get(i).getName() + "\t" + staffList.get(i).getJobTitle());
		}
	}
}
