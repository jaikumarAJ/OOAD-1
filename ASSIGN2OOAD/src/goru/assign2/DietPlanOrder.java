package goru.assign2;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DietPlanOrder {
	private String customerName;
	private DietPlan plan;
	private boolean paymentStatus = false;
	
	/*
	 * DietPlanOrder constructor chooses a specific meal type based on given parameters.
	 */
	public DietPlanOrder(String customerName, String fileName, MealType mealType, boolean delivery, int dayOfWeek) {
		this.customerName = customerName;
		
		
		switch(mealType){
		case VEGAN:
			VeganDietPlan veganDietPlan = new VeganDietPlan("veganRecipes.txt", dayOfWeek, delivery);
			this.plan = veganDietPlan;
			break;
		case LOW_CARB:
			LowCarbDietPlan lowCarbDietPlan = new LowCarbDietPlan("lowCarbRecipes.txt", dayOfWeek, delivery);
			this.plan = lowCarbDietPlan;
			break;
		case HIGH_CARB:
			HighCarbDietPlan highCarbDietPlan = new HighCarbDietPlan("highCarbRecipes.txt", dayOfWeek, delivery);
			this.plan = highCarbDietPlan;
		}
	}
	
	// Gets the total cost
	public double getCost() {
		return plan.getCost();
	}
	
	// Validates the card information given by the customer and processes payments
	public boolean acceptPayment(String cardName, String cardNumber, double amount) {
		String masterCard = "Master Card";
		String visaCard = "Visa";
		
		/*Pattern to check if it is a valid master card
		 * Valid master card has:
		 * 		1) 1st digit is numeric 5
		 *      2) 2nd digit is numeric between 1 and 5 inclusive.
		 *      3) It has 16 digits in total
		*/ 
		Pattern masterCardPattern = Pattern.compile("^[5]{1}[1-5]{1}[0-9]{14}");
		
		/*Pattern to check if it is a valid visa card
		 * Valid visa card has:
		 * 		1) 1st digit is numeric 4
		 *      2) All the remaining characters are numeric digits
		*/ 
		Pattern visaCadPattern = Pattern.compile("^[4]{1}[0-9]*$");
		
		// If it is a master card
		if (cardName.equalsIgnoreCase(masterCard)) {			
			Matcher matcher = masterCardPattern.matcher(cardNumber);
			if (!(matcher.find())) {
				System.out.println(" Invalid Card");
			} else {
				this.paymentStatus = true;
			}
			
		} else if (cardName.equalsIgnoreCase(visaCard)) { // If it is a visa card
			// Visa card needs to be either 16 digits or 13 digits
			if (cardNumber.length() == 16 || cardNumber.length() == 13) {
				
				Matcher matcher = visaCadPattern.matcher(cardNumber);
				if (!(matcher.find())) {
					System.out.println(" Invalid Card");
				} else {
					this.paymentStatus = true;
				}
				
			} else {
				System.out.println(" Invalid Card");
			}
		} else { // Print invalid if the card doesn't match Visa or MasterCard
			System.out.println(" Invalid Card type. Please enter either Visa or MasterCard information");
		}
		
		if (this.paymentStatus) {
			System.out.println("\n The payment of amount: " + amount + " is accepted");
		}
		
		return this.paymentStatus;
	}
	
	//Generates invoice after a successful payment
	public void generateInvoice() {
		if( !this.paymentStatus) {
			System.out.println("\n Unable to generate invoice due to payment not recieved. "
					           + "Please process your payment prior to generating invoice");
		} else {
			System.out.println("\n ====================== INVOICE ======================");
			System.out.println("\n Customer Name :"+ this.customerName);
			System.out.println("\n Date: " + (new Date()).toString());
			System.out.println("\n Diet Plan Details: \n "+ this.plan.showPlan());
			
			// Prints total cost by adding all meal costs and delivery fee as applicable
			System.out.println("\n Total Cost : $" + this.plan.getCost());
		}
	}
	
}
