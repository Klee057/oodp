package classes;

import java.io.Serializable;

/**
 * Represents a member of the restaurant who is entitled to discount
 *
 */
public class Member implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2520737208314194649L;
	
	/**
	 * ID number of this member
	 */
	private int memberId;
	/**
	 * Name of this member
	 */
	private String name;
	/**
	 * Contact Number of this member
	 */
	private String contactNo;
	/**
	 * Gender of this member
	 */
	private genderType gender;

	/**
	 * Constructor for this member
	 * @param id
	 * @param name
	 * @param contactNo
	 * @param gender
	 */
	public Member(int id, String name, String contactNo, genderType gender) {
		this.setMemberId(id);
		this.setName(name);
		this.setContactNo(contactNo);
		this.setGender(gender);
	}
	
	/**
	 * Returns memberId of this Member
	 * @return int
	 */
	public int getMemberId() {
		return this.memberId;
	}

	/**
	 * Sets the memberId for this Member
	 * @param memberId
	 */
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	
	/**
	 * Returns the name of this Member
	 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the Name of this Member 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the contact number of this Member
	 * @return String
	 */
	public String getContactNo() {
		return this.contactNo;
	}

	/**
	 * Sets the contact number of this Member
	 * @param contactNo
	 */
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	
	/**
	 * Returns the gender of this Member
	 * @return genderType
	 */
	public genderType getGender() {
		return this.gender;
	}

	/**
	 * Sets the gender of this Member
	 * @param gender
	 */
	public void setGender(genderType gender) {
		this.gender = gender;
	}

}