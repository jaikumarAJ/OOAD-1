package goru.assign1.salebin;
import goru.assign1.q1.*;

/**@author SwathiGoru
* @version 3.0
**/

interface ItemType {
	public boolean isFragile();
	public USMoney getPrice();
	public double getWeight();
	public String getDetails();
}

public class SaleItem implements ItemType {
	protected String itemName;
	USMoney price = new USMoney();
	protected double weight;
	protected boolean fragile;
	
	/**
	 * getDetails
	 * @return String ItemName + Price
	 * <p>
	 * This method returns a concatenated string of itemName and price of the item.
	 */
	public String getDetails() {
		String s = this.itemName + this.price;
		return s;
	}
	
	//constructor with parameters
	public SaleItem (String itemName, USMoney price, double weight, boolean fragile) {
		this.itemName = itemName;
		this.price = price;
		this.weight = weight;
		this.fragile = fragile;
	}
	
	/**
	 * isFragile
	 * @return boolean 
	 * <p>
	 * This method returns if the item is fragile or not
	 */
	public boolean isFragile() {
		return this.fragile;
	}
	
	/**
	 * getPrice
	 * @return  USMoney price
	 * <p>
	 * This method returns the price of the sale item
	 */
	public USMoney getPrice() {
		return this.price;
	}
    
	/**
	 * getPrice
	 * @return weight 
	 * <p>
	 * This method returns the weight of the sale item
	 */
	public double getWeight() {
		return weight;
	}
	
	/**
	 * toString
	 * @return String Itemname+weight+price 
	 * <p>
	 * This method returns the item's information as a string
	 */
	public String toString(){
		return "ItemName: " + this.itemName + " Weight:" + this.weight + " Price:" + this.price;
	}
	
}