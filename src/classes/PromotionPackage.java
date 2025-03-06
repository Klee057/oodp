package classes;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Represents a PromotionPackage in the Menu.
 * A PromotionPackage can have multiple AlaCarte
 *
 */
public class PromotionPackage extends MenuItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6336164071140287891L;
	
	/**
	 * promoList is an ArrayList of AlaCarte in this promotion package;
	 */
	private ArrayList<AlaCarte> promoList;
	/**
	 * Constructor for the PromotionPackage object, initialises empty ArrayList to store the AlaCarte in this package
	 * @param description
	 * @param price
	 * @param packName
	 */
	public PromotionPackage(String description, double price, String packName) {
		super(packName, description, price);		
		this.promoList = new ArrayList<AlaCarte>();
	}
	
	/**
	 * Returns promoList
	 * @return ArrayList<AlaCarte>
	 */
	public ArrayList<AlaCarte> getPromoList() {
		return this.promoList;
	}

	/**
	 * Adds a AlaCarte into this PromoPackage
	 * @param item
	 */
	public void addItem(AlaCarte item) {
		promoList.add(item);
		System.out.println(item.getItemName() + " added to the Promotion Package " + this.getItemName() + "!");
	}

	/**
	 * Removes a AlaCarte from this PromoPackage
	 * @param item
	 */
	public void removeItem(AlaCarte item) {
		promoList.remove(item);
		System.out.println(item.getItemName() + " removed from Promotion Package " + this.getItemName() + "!");
	}

}