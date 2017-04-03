package goru.assign2;

public class Recipe {
	String name;
	String description;
	double cost;
	int calories;
	
	// Constructor to set recipe name, description, cost, calories
	Recipe(String name, String description, double cost, int calories) {
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.calories = calories;
	}
	
	// Default Constructor
    Recipe() {}
}
