package goru.assign2;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Iterator;


public class VeganDietPlan implements DietPlan {
	
	Meal lunch;
	Meal dinner;
	String fileName;
	String dayOfWeek;
	MealCategory veganMeal;
	Recipe recipe;
	

	public VeganDietPlan(String fileName, int dayOfWeek, boolean delivery)  {
		// Set the filename
		this.fileName = fileName;
		
		//Set the day of the week
		DayOfWeek day = DayOfWeek.of(dayOfWeek);
		this.dayOfWeek = day.name();
		System.out.println("DayofWeek:"+this.dayOfWeek);
		
		// Load the recipes
		VeganMeal vMeal = new VeganMeal();
		vMeal.loadRecipes(fileName);
		
		//Create lunch and dinner instances
		this.lunch = new Lunch(vMeal);
		this.dinner = new Dinner(vMeal);
		String dinnerRecipe = this.dinner.getRecipeName();
		String lunchRecipe = this.lunch.getRecipeName();

		if (dinnerRecipe.equals(lunchRecipe)) {
			ArrayList<Recipe> recipeList = this.dinner.showRecipeList();
			Iterator<Recipe> iterator = recipeList.iterator();
			while(iterator.hasNext()) {
				Recipe r = iterator.next();
				System.out.println("Recipe : " + r.name);
				if (!r.name.equals(lunchRecipe)) {
					this.dinner.setRecipe(r);
					break;
				}
			}
		} 
		
		if (delivery) {
			this.lunch.setDelivery();
			this.lunch.setDeliveryFee(3.0);
			this.dinner.setDelivery();
			this.dinner.setDeliveryFee(4.0);
		}
		
	}
	
	public String showPlan() {
		String s = "Lunch details: " + this.lunch.getDetails() + "\n Dinner details: " + this.dinner.getDetails() + 
				   "\n Day of the week: " + this.dayOfWeek;
		return s;
	}

	public double getCostOfPlan() {
		return (lunch.getCost() + dinner.getCost());
	}

	public double getCost() {
		return (getCostOfPlan());
	}
}
