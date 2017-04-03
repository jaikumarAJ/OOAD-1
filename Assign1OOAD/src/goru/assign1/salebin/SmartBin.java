package goru.assign1.salebin;

import goru.assign1.q1.USMoney;
/**@author SwathiGoru
* @version 3.0
**/
public class SmartBin extends Bin {
	private String label;
	
	/**
	 * SmartBin
	 * The default constructor assigns the uniquely generated binNumber to the bin.
	 */
	public SmartBin() {
		this.binNumber = "SM"+ Bin.generateBinNumber();
	}
	
	/**
	 * setLabel
	 * This method is used to set a label to an item.
	 * @param label: String label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * addItem
	 * @param item: An itemType
	 * <p>
	 * This method checks if the item's weight along with the smartBin's total weight is not exceeding maxWeight.
	 * If it doesn't exceed, we add the item to the smartBin.
	 * This method also calculates totalWeight of smartBin items while adding it to the array. 
	 * When adding an item whose weight exceeds smartBin's total weight beyond maxWeight, 
	 * it skips adding to the array and prints the item details.
	 * We check if the item is fragile and if fragile we set "Fragile - Handle with Care" as label for the item
	 */
	//Overrides and extends addItem from the Bin class
	@Override
	public void addItem (ItemType item) {
		double weight = item.getWeight();
		if (((weight + super.totalWeight) <= Bin.maxWeight)) {
		   items[super.numItems] = item;
		   super.numItems++;
		   super.totalWeight += weight;
		   System.out.println("\nAdding smartBin item => "+ item);
		} else {
			System.out.println("\nSkipping smartBin => "+ item + " because of excess weight");
		}
		if (item.isFragile() == true) {
			this.setLabel("Fragile - Handle with Care");
		}
	}
	
	/**
	 * calculatePrice
	 * <p>
	 * This method calculates the total price of items in the smartBin.
	 * If the item is fragile, we add an additional $10 for each fragile item to total cost.
	 * A cost of $100 is added to the total cost of the smartBin items.
	 * @return Total price of the items. 
	 */
	//Overrides and extends the method from Bin class
	@Override
	public USMoney calculatePrice() {
		USMoney totalPrice = new USMoney();
		for (int i = 0; i < super.numItems; i++) {
			ItemType tempItem = items[i];
			totalPrice = totalPrice.add(tempItem.getPrice());
			if (tempItem.isFragile() == true) {
				totalPrice.addTo(10, 0);
			}
		}
		totalPrice.addTo(100, 0);
		return totalPrice;
	}
	
	/**
	 * showDetails
	 * <p>
	 * This methods shows details of the Bin like Bin number, current weight and the total cost.
	 */
	//Overrides and extends the method showDetails() in the Bin class
	@Override
	public String showDetails() {
		return ("Bin Number:"+ this.binNumber + " Label:" + this.label +
				" Current Weight:" + this.getWeight() + " Total Cost:" + this.calculatePrice());
	}
	
}

