package goru.assign2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


// This abstract class is to enable reusability amongst VeganMeal, LowCarbMeal and HighCarbMeal classes
public abstract class AllMeal {

	/* Loads the recipes into an arraylist from a specified file
	 *  Reads data from a text file and tokenizes each line based on delimiter(":")
	 *  Each token is add as data-member in Recipe ArrayList
	*/
	public ArrayList<Recipe> loadRecipesFromFile(String fileName) {
		ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
	
		File file = new File(fileName);
		Scanner scan = null;
		
		try {
			// Reading the file
            scan =  new Scanner (file);
			while(scan.hasNextLine()) {
			    String line = scan.nextLine();
			    
			    // Tokenizing each element
			    String [] details = line.split(":");
			    String name = details[0];
			    String description = details[1];
			    
			    //Parsing double value
			    double cost = Double.parseDouble(details[2]);
			    
			    //Parsing int value
			    int calories = Integer.parseInt(details[3]);
			    
			    //Creating new recipe object
			    Recipe r = new Recipe(name, description, cost, calories);
			    
			    // Adding recipe to ArrayList
			    recipeList.add(r);
			}		
		} catch (FileNotFoundException e) {
			System.out.println("File not found" + file.toString());
		} finally {
			if (scan!= null)
				scan.close();
		}
		
		return recipeList;
	}
	
	// Randomly selects a recipe from Recipe ArrayList
	public Recipe getARecipeFromList(ArrayList<Recipe> recipeList) {
		Random rand = new Random();
		Recipe recipeTest = new Recipe();
        recipeTest = recipeList.get(rand.nextInt(recipeList.size()));
		return recipeTest;    
	}
}
