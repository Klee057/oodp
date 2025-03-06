package classes;

import java.io.Serializable;

/**
 * Represents an ala carte item on the menu
 *
 */
public abstract class MenuItem implements Serializable{
	
	/**
	 *
	 */
	private static final long serialVersionUID = -4975638209362144716L;
	/**
	 * Name of this MenuItem
	 */
	private String itemName;
	/**
	 * Description of this MenuItem
	 */
	private String description;
	/**
	 * Price of this MenuItem
	 */
	private double price;
	
	/**
	 * Constructor for MenuItem.
	 * @param itemName
	 * @param description
	 * @param price
	 * @param type
	 */
	public MenuItem(String itemName, String description, double price) {
		this.setItemName(itemName);
		this.setDescription(description);
		this.setPrice(price);
	}
	
	/**
	 * Returns the name of this MenuItem
	 * @return String
	 */
	public String getItemName() {
		return this.itemName;
	}

	/**
	 * Sets the name of this MenuItem.
	 * @param itemName
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	/**
	 * Returns the description of this MenuItem
	 * @return String
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the description of this MenuItem
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Returns the price of this MenuItem
	 * @return double
	 */
	public double getPrice() {
		return this.price;
	}

	/**
	 * Sets the price of this MenuItem
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

}