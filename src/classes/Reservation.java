package classes;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Reservation made by a customer
 */
public class Reservation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2778722174010909498L;

	/**
	 * Date of Reservation requested by Customer
	 */
	private Date dateTime;

	/**
	 * Number of Customers for Reservation
	 */
	private int pax;

	/**
	 * Name of representing Customer that made Reservation
	 */
	private String name;

	/**
	 * Contact Number of representing Customer that made Reservation
	 */
	private String contactNo;

	/**
	 * Id of Table that is kept for Reservation
	 */
	private int tableId;

	/**
	 * Boolean to check whether Customer has arrived upon reserving
	 */
	private boolean customerArrived = false;

	/**
	 * Constructor for Reservation
	 * Generates new Reservation with Date, Pax, Name, Contact Number and tableId
	 * @param dateTime
	 * @param pax
	 * @param name
	 * @param contactNo
	 * @param tableId
	 */
	public Reservation(Date dateTime, int pax, String name, String contactNo, int tableId) {
		this.setDateTime(dateTime);
		this.setPax(pax);
		this.setName(name);
		this.setContactNo(contactNo);
		this.setTableId(tableId);
	}

	/**
	 * Returns Date of Reservation requested
	 * @return Date
	 */
	public Date getDateTime() {
		return this.dateTime;
	}

	/**
	 * Sets Date of Reservation requested
	 * @param dateTime
	 */
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * Returns number of people for reservation
	 * @return int
	 */
	public int getPax() {
		return this.pax;
	}

	/**
	 * Sets number of people for reservation
	 * @param pax
	 */
	public void setPax(int pax) {
		this.pax = pax;
	}

	/**
	 * Returns the name of customer that made Reservation
	 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets name of customer that made Reservation
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns Contact Number of customer that made Reservation
	 * @return String
	 */
	public String getContactNo() {
		return this.contactNo;
	}

	/**
	 * Sets Contact Number of customer that made Reservation
	 * @param contactNo
	 */
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	/**
	 * Returns TableId assigned for Reservation
	 * @return int
	 */
	public int getTableId() {
		return this.tableId;
	}

	/**
	 * Sets TableId assigned for Reservation
	 * @param tableId
	 */
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	/**
	 * Returns boolean of whether Customer has arrived upon Reservation
	 * @return boolean
	 */
	public boolean getCustomerArrived() {
		return this.customerArrived;
	}

	/**
	 * Sets boolean of whether Customer has arrived upon Reservation
	 * @param customerArrived
	 */
	public void setCustomerArrived(boolean customerArrived) {
		this.customerArrived = customerArrived;
	}

	/**
	 * Prints out name and contact number of customer that made reservation
	 * Prints date, tableId, and number of people for reservation
	 */
	public void printReservation(){
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy hh:mm aa");  
		String strDate= formatter.format(this.dateTime);  
		System.out.println(this.getName() + "[" + this.getContactNo() + "] : Reservation at " + strDate + " - Table " + this.getTableId() + "(pax: " + this.getPax() + ")");
	}

}