package goru.assign1.salebin;

import goru.assign1.q1.*;
/**@author SwathiGoru
* @version 3.0
**/

interface BinType {
	
	public void addItem(ItemType item);
	public USMoney calculatePrice();
	public double getWeight();
	public int getNoOfItems();
	public String showDetails();	
}

public class Bin implements BinType {
	protected String binNumber;
	ItemType[] items = new ItemType[100];
	protected static final double maxWeight = 10;
	protected static int counter = 0;
	protected int numItems = 0;
	protected double totalWeight = 0;
	
	/**
	 * Bin
	 * The default constructor assigns the uniquely generated binNumber to the bin.
	 */
	public Bin() {
		this.binNumber = "B"+ generateBinNumber(); //Adds an alphabet B to every Bin number.
	}
	
	/**
	 * generateBinNumber
	 * <p>
	 * This method is used to generate a unique number.
	 * This unique number is later appended to an alphabet to get a BinNumber.
	 * @return integer (unique number) 
	 */
	public static int generateBinNumber() {
		return ++counter;
	}
	
	/**
	 * addItem
	 * @param item: ItemType
	 * <p>
	 * This method checks if the item's weight along with the Bin's total weight is not exceeding maxWeight.
	 * If the item is not fragile, it adds to the array.
	 * This method also calculates totalWeight of bin items while adding it to the array. 
	 * When adding an item whose weight exceeds Bin's total weight beyond maxWeight, 
	 * it skips adding to the array and prints the item details.
	 */
	public void addItem (ItemType item) {
		double weight = item.getWeight();
		if ((weight + this.totalWeight <= Bin.maxWeight) && item.isFragile() != true) {
		   items[this.numItems] = item;
		   this.numItems++;
		   this.totalWeight += weight;
		   System.out.println("\nAdding Bin item => "+ item);
		} else {
			System.out.println("\nSkipping Bin item => "+ item + " because of excess weight");
		}

	}
	
	/**
	 * calculatePrice
	 * <p>
	 * This method calculates the total price of items in the Bin.
	 * A cost of $100 is added to the total cost of the Bin items.
	 * @return Total price of the items. 
	 */
	public USMoney calculatePrice() {
		USMoney totalPrice = new USMoney();
		for (int i = 0; i < this.numItems; i++) {
			ItemType tempItem = items[i];
			totalPrice = totalPrice.add(tempItem.getPrice()); 
		}
		totalPrice.addTo(100, 0);
		return totalPrice;
	}
	
	/**
	 * getWeight
	 * <p>
	 * This method gets the total weight of the bin
	 */
	public double getWeight() {
		return this.totalWeight;
	}
	
	/**
	 * getNoOfItems
	 * <p>
	 * This methods gets the total number of items in the Bin.
	 */
	public int getNoOfItems() {
		return this.numItems;
	}
	
	/**
	 * showDetails
	 * <p>
	 * This methods shows details of the Bin like Bin number, current weight and the total cost.
	 */
	public String showDetails() {
		return ("Bin Number:"+ this.binNumber + " Current Weight:" + 
	             this.getWeight() + " Total Cost:" + this.calculatePrice());
	}
	
}