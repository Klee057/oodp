package classes;

import java.io.Serializable;

/**
 * Staff of Restaurant
 */
public class Staff implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8671625386057887471L;

	/**
	 * Name of Staff
	 */
	private String name;

	/**
	 * Gender of Staff
	 */
	private genderType gender;

	/**
	 * Id of Staff
	 */
	private int staffId;

	/**
	 * Job of Staff
	 */
	private jobType jobTitle;
	
	/**
	 * Constructor for Staff
	 * Creates new Staff with name, gender, staffId, jobType
	 * @param name
	 * @param gender
	 * @param staffId
	 * @param jobTitle
	 */
	public Staff(String name, genderType gender, int staffId, jobType jobTitle) {
		this.setName(name);
		this.setGender(gender);
		this.setStaffId(staffId);
		this.setJobTitle(jobTitle);
	}

	/**
	 * Returns name of Staff
	 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets name of Staff
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns gender of Staff
	 * @return
	 */
	public genderType getGender() {
		return this.gender;
	}

	/**
	 * Sets gender of Staff
	 * @param gender
	 */
	public void setGender(genderType gender) {
		this.gender = gender;
	}

	/**
	 * Returns Id of Staff
	 * @return int
	 */
	public int getStaffId() {
		return this.staffId;
	}

	/**
	 * Sets Id of Staff
	 * @param staffId
	 */
	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	/**
	 * Returns Job Title of Staff
	 * @return jobType
	 */
	public jobType getJobTitle() {
		return this.jobTitle;
	}

	/**
	 * Sets Job Title of Staff
	 * @param jobTitle
	 */
	public void setJobTitle(jobType jobTitle) {
		this.jobTitle = jobTitle;
	}

}