package goru.assign2;


public class OmicronApp {
	
	public static void main(String[] args) {
		DietPlanOrder planOrder = new DietPlanOrder("Mary Jones", "veganRecipes.txt", MealType.VEGAN, true, 2);
		planOrder.acceptPayment("Master Card", "5112345678901230", planOrder.getCost());
		planOrder.generateInvoice();
	}
}
