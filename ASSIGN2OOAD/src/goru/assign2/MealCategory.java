package goru.assign2;

import java.util.ArrayList;

public interface MealCategory {
	public ArrayList<Recipe> showRecipes();
	public Recipe getARecipe();
	public void loadRecipes(String filename);
}
