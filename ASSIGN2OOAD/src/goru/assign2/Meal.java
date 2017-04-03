package goru.assign2;

import java.util.ArrayList;

public interface Meal {
	public Recipe getARecipe();
	public int getCalories();
	public double getCost();
	public String getDetails();
	public String getRecipeName();
	public void setDeliveryFee(double d);
	public void setDelivery();
	public ArrayList<Recipe> showRecipeList();
	public void setRecipe(Recipe r);
}
