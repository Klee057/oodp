package controllers;

import java.util.ArrayList;
import java.util.Scanner;

import classes.Member;
import classes.Restaurant;

/**
 * MemberController allows user to print memberList and verify Members
 *
 */
public class MemberController {
	/**
	 * ArrayList of members of the Restaurant
	 */
	private static ArrayList<Member> memberList = Restaurant.getMemberList();
	private static Scanner sc = new Scanner(System.in);
	
	/**
	 * Prints a list of Members of the Restaurant
	 */
	public static void printMemberList() {
		for(int i=0; i < memberList.size(); i++) {
			System.out.println("(" + memberList.get(i).getMemberId() + ")\t" + memberList.get(i).getName() + "\t" + memberList.get(i).getContactNo());
		}
	}
	
	/**
	 * Verifies the membership of a Member
	 * @return boolean
	 */
	public static boolean checkMembership() {
		String name, contactNo;
		Member m;
		
		printMemberList();
		
		System.out.printf("What is the customer's name? ");
		name = sc.nextLine();
		System.out.printf("What is the customer's contact number? ");
		contactNo = sc.nextLine();
        
		
		for (int i = 0; i < memberList.size(); i++)
		{
			m = memberList.get(i);
			if (m.getName().equalsIgnoreCase(name) && m.getContactNo().equals(contactNo))
			{
				System.out.println("Member verified");
				return true;
			}
		}
		System.out.println("Member not found");
		return false;
	}
}
