package classes;

import java.io.Serializable;

public class AlaCarte extends MenuItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4180173667539439998L;
	/**
	 * The itemType of this MenuItem(mainCourse, drink, dessert)
	 */
	private itemType type;
	
	/**
	 * Constructor for MenuItem.
	 * @param itemName
	 * @param description
	 * @param price
	 * @param type
	 */
	public AlaCarte(String itemName, String description, double price, itemType type) {
		super(itemName, description, price);
		this.type = type;
	}
	
	/**
	 * Returns the itemType of this MenuItem
	 * @return itemType
	 */
	public itemType getType() {
		return this.type;
	}

	/**
	 * Sets the itemType of this MenuItem
	 * @param type
	 */
	public void setType(itemType type) {
		this.type = type;
	}
}
