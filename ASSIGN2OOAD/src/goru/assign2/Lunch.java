package goru.assign2;

import java.util.ArrayList;

public class Lunch implements Meal{
	Recipe recipe;
	MealCategory mealCategory;
	Boolean delivery;
	double deliveryFee;
	
	//Constructors
	public Lunch(MealCategory mealCategory) {
		this.mealCategory = mealCategory;
		this.recipe = getARecipe();      
	}

	//Returns recipe name
	public String getRecipeName() {
		return this.recipe.name;
	}

	// Returns the complete details of recipe
	public String getDetails() {
		String s = " Recipe Name:" + this.recipe.name + 
				   " Description:" + this.recipe.description + 
				   " Calories:" + this.recipe.calories + 
				   " Cost:$" + this.recipe.cost;
		if (this.delivery) {
			return  (s + " Delivery Fee:$" + this.deliveryFee);
		}
		return s;
	}

	//Returns recipe calories 
	public int getCalories() {
		return this.recipe.calories;
	}

	// Calculate and retruns the total cost of a recipe
	// Total cost = Recipe cost + delivery fee(if applicable)
	public double getCost() {
		if (this.delivery) {
			return this.recipe.cost + this.deliveryFee;
		} else {
			return this.recipe.cost;
		}
	}

	// Gets a random recipe
	public Recipe getARecipe() {
		return (this.mealCategory.getARecipe());
	}

	// Set the delivery fee if delivery option is chosen
	// Delivery fee is pre-set for lunch to $3.0 and dinner $4.0 
	public void setDeliveryFee(double fee) {
		if (this.delivery) {
			this.deliveryFee = fee;
		} else {
			System.out.println("\n Delivery Fee cannont be applied as delivery option is not seclected");
		}
	} 

	// Returns the delivery fee
	public double getDeliveryFee() {
		return this.deliveryFee;
	}

	// Sets if delivery option is chosen
	public void setDelivery() {
	    this.delivery = true;
	}
	
	// Returns a Recipe ArrayList
	public ArrayList<Recipe> showRecipeList() {
		return this.mealCategory.showRecipes();
	}
	
	// Sets a recipe to a meal
	public void setRecipe(Recipe r) {
		this.recipe = r;
	}
}

