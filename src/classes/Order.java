package classes;

import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;
import java.util.Set;

/**
 * Order made by Staff
 */
public class Order implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3178873410914280102L;

	/**
	 * Id of Table that Order was created for
	 */
	private int tableId;

	/**
	 * Date that Order was created
	 */
	private Date timestamp;

	/**
	 * Dictionary of Alacarte Items in order
	 */
	private Hashtable<Integer, Integer> alac_dict = new Hashtable<Integer, Integer>();

	/**
	 * Dictionary of Promotion Packages in order
	 */
	private Hashtable<Integer, Integer> promo_dict = new Hashtable<Integer, Integer>();

	/**
	 * Staff that created the order
	 */
	private Staff server;

	/**
	 * InvoiceId of order
	 */
	private int invoiceId;

	/**
	 * Percentage Discount generated for Order
	 */
	private double discount;

	/**
	 * Absolute Discount generated for Order
	 */
	private double discountGiven;

	/**
	 * GST charged for Order
	 */
	private double GST = 0.07;

	/**
	 * Total price of order after including GST
	 */
	private double GSTFee;

	/**
	 * Service charge for Order
	 */
	private double serviceCharge = 0.1;

	/**
	 * Total price of order after service charge
	 */
	private double serviceFee;

	/**
	 * Total price of Order
	 */
	private double totalPrice = 0;

	/**
	 * Boolean for whether Order has been paid
	 */
	private boolean isPaid = false;

	/**
	 * Constructor for Order
	 * Generates new Order with tableId, Date, Staff, invoiceId and discount
	 * @param tableId
	 * @param timestamp
	 * @param server
	 * @param invoiceId
	 * @param discount
	 */
	public Order(int tableId, Date timestamp, Staff server, int invoiceId) {
		this.setTableId(tableId);
		this.setTimestamp(timestamp);
		this.setServer(server);
		this.setInvoiceId(invoiceId);	
	}
	
	/**
	 * Adds Alacarte item to Order by selecting Alacarte ID and quantity
	 * @param alacItemId
	 * @param quantity
	 */
	public void addItem(int alacItemId, int quantity) {
		if(alac_dict.get(alacItemId) != null){
			alac_dict.put(alacItemId, alac_dict.get(alacItemId)+quantity);
		}else {
			alac_dict.put(alacItemId, quantity);
		}

	}
	
	/**
	 * Removes Alacarte item from Order by selecting Alacarte Id and quantity to remove
	 * @param alacItemId
	 * @param quantity
	 */
	public void removeItem(int alacItemId, int quantity) {
		if(alac_dict.get(alacItemId) != null){
			if(quantity > 0 && quantity < alac_dict.get(alacItemId)) {
				alac_dict.put(alacItemId, alac_dict.get(alacItemId)-quantity);
			}else if(quantity == alac_dict.get(alacItemId)) {
				alac_dict.remove(alacItemId);
			}else {
				System.out.println("Please enter a proper quantity.");
			}
		}else {
			System.out.println("This item is not in the order.");
		}
	}
	

	/**
	 * Adds Promotion Package to Order by selecting package ID and quantity
	 * @param promoItemId
	 * @param quantity
	 */
	public void addPromo(int promoItemId, int quantity) {
		if(promo_dict.get(promoItemId) != null){
			promo_dict.put(promoItemId, alac_dict.get(promoItemId)+quantity);
		}else {
			promo_dict.put(promoItemId, quantity);
		}
		
	}
	
	/**
	 * Removes Promotion Package from Order by selecting package ID and quantity to remove
	 * @param promoItemId
	 * @param quantity
	 */
	public void removePromo(int promoItemId, int quantity) {
		if(promo_dict.get(promoItemId) != null){
			if(quantity > 0 && quantity < promo_dict.get(promoItemId)) {
				promo_dict.put(promoItemId, promo_dict.get(promoItemId)-quantity);
			}else if(quantity == promo_dict.get(promoItemId)) {
				promo_dict.remove(promoItemId);
			}else {
				System.out.println("Please enter a proper quantity.");
			}
		}else {
			System.out.println("This item is not in the order.");
		}
	}

	/**
	 * Prints out Ala carte items ordered
	 */
	public void printAlacItems() {
		System.out.println("--------------Ala carte items in order--------------");
		Set<Integer> setOfKeys = alac_dict.keySet();
		for (Integer key : setOfKeys) {
			 System.out.println("\t" + alac_dict.get(key) + "\t" + Restaurant.getFoodMenu().get(key-1).getItemName());
		}
	}

	/**
	 * Prints out Promotion Packages ordered
	 */
	public void printPromoItems() {
		System.out.println("--------------Promotion Packages in order--------------");
		Set<Integer> setOfKeys = promo_dict.keySet();
		for (Integer key : setOfKeys) {
			 System.out.println("\t" + promo_dict.get(key) + "\t" + Restaurant.getPromoMenu().get(key-1).getItemName());
		}
	}

	/**
	 * Prints Invoice
	 * Includes invoiceID, Server, Date, TableID, discount and Total Price
	 */
	public void printInvoice() {
		System.out.println("===================================================");
		System.out.format("                 Invoice ID:%d                 \n",this.invoiceId);
		System.out.println("Server: " + this.server.getName());
		System.out.println("Date: " + this.timestamp.toString());
		System.out.println("Table: " + this.tableId);
		System.out.println("---------------------------------------------------");
		
		
		if(!this.isPaid) {
			Set<Integer> setOfKeys = alac_dict.keySet();
			for (Integer key : setOfKeys) {
				 System.out.println("\t" + alac_dict.get(key) + "\t" + Restaurant.getFoodMenu().get(key-1).getItemName() + "\t"+ String.format("%.2f",alac_dict.get(key)*Restaurant.getFoodMenu().get(key-1).getPrice()));
				 totalPrice+=alac_dict.get(key)*Restaurant.getFoodMenu().get(key-1).getPrice();
			}
			setOfKeys = promo_dict.keySet();
			for (Integer key : setOfKeys) {
				 System.out.println("\t" + promo_dict.get(key) + "\t" + Restaurant.getPromoMenu().get(key-1).getItemName() + "\t"+ String.format("%.2f",promo_dict.get(key)*Restaurant.getPromoMenu().get(key-1).getPrice()));
				 totalPrice+=promo_dict.get(key)*Restaurant.getPromoMenu().get(key-1).getPrice();
			}
			System.out.println("---------------------------------------------------");
			System.out.println("                                     SubTotal: " + String.format("%.2f", totalPrice));
			System.out.println("                                     GST: " + String.format("%.2f", totalPrice*GST));
			GSTFee = totalPrice * GST;
			totalPrice  = totalPrice + totalPrice*GST;			
			System.out.println("                                     Service Charge: " + String.format("%.2f", totalPrice*serviceCharge));
			serviceFee = totalPrice * serviceCharge;
			totalPrice  = totalPrice + totalPrice*serviceCharge;			
			if(discount == 1.0) {
				System.out.println("                                     Discount: N.A.");
			}else {
				System.out.println("                                     Discount: -" + String.format("%.2f", totalPrice - totalPrice*discount));
			}
			discountGiven = totalPrice - totalPrice*discount;
			totalPrice  = totalPrice*discount;			
			System.out.println("                                     Total: " + String.format("%.2f", totalPrice));
			System.out.println("===================================================");
			this.setIsPaid(true);
			Restaurant.getTableList().get(this.tableId-1).setIsOccupied(false);
		}else {
			Set<Integer> setOfKeys = alac_dict.keySet();
			for (Integer key : setOfKeys) {
				 System.out.println("\t" + alac_dict.get(key) + "\t" + Restaurant.getFoodMenu().get(key-1).getItemName() + "\t"+ String.format("%.2f",alac_dict.get(key)*Restaurant.getFoodMenu().get(key-1).getPrice()));
			}
			setOfKeys = promo_dict.keySet();
			for (Integer key : setOfKeys) {
				 System.out.println("\t" + promo_dict.get(key) + "\t" + Restaurant.getPromoMenu().get(key-1).getItemName() + "\t"+ String.format("%.2f",promo_dict.get(key)*Restaurant.getPromoMenu().get(key-1).getPrice()));
			}
			System.out.println("---------------------------------------------------");
			System.out.println("                                     SubTotal: " + String.format("%.2f", totalPrice-GSTFee-serviceFee+discountGiven));
			System.out.println("                                     GST: " + String.format("%.2f", GSTFee));
			System.out.println("                                     Service Charge: " + String.format("%.2f", serviceFee));
			if(discount == 1.0) {
				System.out.println("                                     Discount: N.A.");
			}else {
				System.out.println("                                     Discount: -" + String.format("%.2f", discountGiven));
			}
			System.out.println("                                     Total: " + String.format("%.2f", totalPrice));
			System.out.println("===================================================");
		}
		
	}

	/**
	 * Returns TableId of order
	 * @return int
	 */
	public int getTableId() {
		return this.tableId;
	}

	/**
	 * Sets TableId of Order
	 * @param tableId
	 */
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	/**
	 * Returns Date that Order was created
	 * @return Date
	 */
	public Date getTimestamp() {
		return this.timestamp;
	}

	/**
	 * Sets Date that Order was created
	 * @param timestamp
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Returns Dictionary of Ala carte items ordered
	 * @return Hashtable<Integer, Integer>
	 */
	public Hashtable<Integer, Integer> getAlac_dict() {
		return this.alac_dict;
	}

	/**
	 * Returns Dictionary of Promotion Packages ordered
	 * @return Hashtable<Integer, Integer>
	 */
	public Hashtable<Integer, Integer> getPromo_dict() {
		return this.promo_dict;
	}

	/**
	 * Returns Staff that created Order
	 * @return Staff
	 */
	public Staff getServer() {
		return this.server;
	}

	/**
	 * Sets Staff that created Order
	 * @param server
	 */
	public void setServer(Staff server) {
		this.server = server;
	}

	/**
	 * Returns InvoiceId of Order
	 * @return int
	 */
	public int getInvoiceId() {
		return this.invoiceId;
	}

	/**
	 * Sets InvoiceId of Order
	 * @param invoiceId
	 */
	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	/**
	 * Returns Absolute Discount of Order
	 * @return double
	 */
	public double getDiscount() {
		return this.discount;
	}

	/**
	 * Sets Absolute Discount of Order
	 * @param discount
	 */
	public void setDiscount(double discount) {
		this.discount = discount;
	}

	/**
	 * Returns Total Price of Order
	 * @return double
	 */
	public double getTotalPrice() {
		return this.totalPrice;
	}

	/**
	 * Returns boolean of whether Order has been paid by Customer
	 * @return boolean
	 */
	public boolean getIsPaid() {
		return this.isPaid;
	}

	/**
	 * Sets the boolean of whether Order has been paid by Customer
	 * @param isPaid
	 */
	public void setIsPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

}