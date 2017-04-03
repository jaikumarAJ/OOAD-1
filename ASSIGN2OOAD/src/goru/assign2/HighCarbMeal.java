package goru.assign2;

import java.util.ArrayList;


public class HighCarbMeal extends AllMeal implements MealCategory{
	private ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
	
	// Loads the recipes into an arraylist from a specified file
	public void loadRecipes(String fileName) {
		recipeList = loadRecipesFromFile(fileName);
	}
	
	// Returns the arraylist of recipes
	public ArrayList<Recipe> showRecipes() {
		return recipeList;
	}
	
	//Returns a randomly selected recipe
	public Recipe getARecipe() {
        return(getARecipeFromList(recipeList));
	}
}
