package classes;

import java.io.Serializable;

/**
 * Represents a table in the Restaurant
 *
 */
public class Table implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -956867197965776052L;
	
	/**
	 * ID of the Table
	 */
	private int tableId;
	/**
	 * Capacity of the Table
	 */
	private int capacity;
	/**
	 * Occupancy of a Table
	 */
	private boolean isOccupied;

	/**
	 * Constructor for this Table
	 * @param tableId
	 * @param capacity
	 */
	public Table(int tableId, int capacity) {
		this.setTableId(tableId);
		this.setCapacity(capacity);
	}
	
	/**
	 * Returns the ID of this Table
	 * @return int
	 */
	public int getTableId() {
		return this.tableId;
	}

	/**
	 * Sets the ID of this Table
	 * @param tableId
	 */
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	
	/**
	 * Returns the capacity of this Table
	 * @return int
	 */
	public int getCapacity() {
		return this.capacity;
	}

	/**
	 * Sets the capacity of this Table
	 * @param attribute
	 */
	public void setCapacity(int attribute) {
		this.capacity = attribute;
	}
	
	/**
	 * Returns the occupancy of this Table
	 * @return
	 */
	public boolean getIsOccupied() {
		return this.isOccupied;
	}

	/**
	 * Sets the occupancy of this Table
	 * @param isOccupied
	 */
	public void setIsOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

}