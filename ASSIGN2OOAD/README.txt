Assignment 2 (100 pts)							Due: (24 Oct 10am)

In this assignment, you will learn to design and implement with composition and inheritance effectively, to solve the given problem
•	Use inheritance, interfaces
•	Use Text file IO
•	Use UML class diagram

1.0 Omicron Catering Inc offers diet-related services. The company supplies various types of diet plans (for eg: Low Carb Diet Plan, High Carb Diet Plan, Hitch Hiker Diet Plan etc) to catering services of hospitals and schools. The list of available diet plans are subject to future changes with a high likelihood of more plans being added to the list in future.
A DietPlan gives the details of the meals offered by day. A Meal can be a Lunch or Dinner. Meals can be of various categories, namely, Vegan, Low-Carb, High-Carb etc. A meal (lunch or dinner) can of type Vegan, Low-carb etc.
Clients can order DietPlans, make Payment and request to generate an invoice.
See the UML class diagram below:


The salient details of each of the classes (interfaces) are given in Section 2. You are free to add any additional data or methods of your choice.
2.0 Details of classes / interfaces
class Recipe
Data Members:
name:String;
	description : String
	cost : double
calories: integer

interface MealCategory
	void showRecipes()
Recipe getARecipe()
loadRecipes(String fileName)

class VeganMeal implements MealCategory
Data Members:
	ArrayList<Recipe> recipeList;	
Methods:
loadRecipes(String fileName); Opens the file with the filename, read the contents, line by line. Each line (see the format of the file, recipes.txt in section 3.0), contains the Recipe name, description, calories and cost. The line should be tokenized by fields, an instance of a Recipe created with the field values and the instance added to the recipeList.
 
showRecipes(): ArrayList<Recipe>- Should return recipeList

getARecipe():Recipe -  Should randomly pick a recipe from recipeList and return it.
		
Note: Classes Low-CarbMeal and High-CarbMeal and so on are similar to Vegan.

interface Meal 
			 
getARecipe():Recipe
getCalories():integer
getCost() :double
getDetails():String


class Lunch implements Meal
Data Members:
	recipe:Recipe;
	
	mealCategory: MealCategory		

Methods: 

Lunch(mealCategory: MealCategory) – sets the value of mealCategory, with the parameter value. Calls the method, getARecipe() on mealCategory and initializes the data member, recipe, with it.

showCalories() : double - Should return the calories for recipe.

showCost(): double - Should return cost for the recipe

getDetails(): String – Should return a String with the recipe name, description, calories and cost of recipe.

	
class Dinner implements Meal

Data Members:
	
	recipe:Recipe;
	
	mealCategory: MealCategory
	
delivery : Boolean

deliveryFee : double


Dinner(mealCategory: MealCategory) – sets the value of mealCategory, with the parameter value. Calls the method, getARecipe() on mealCategory and initializes the data member, recipe, with it.

showCalories() : double - Should return the calories for recipe.

showCost(): double - Should return cost for the recipe + deliveryFee

getDetails(): String – Should return a String with the recipe name, description, calories and cost of recipe and the delivery fee.

setDeliveryFee(double fee)

interface DietPlan 

	showPlan() : String

	getCost() :double

class VeganDietPlan implements DietPlan

Data Members:
lunch: Meal
	dinner: Meal
	filename: String	
	dayOfWeek : String
	veganMeal: MealCategory;
		
VeganDietPlan(filename:String, dayOfWeek:integer)- Constructor

The datamember, filename is set. Based on the dayOfWeek passed in as an integer, the corresponding day of the week as a String is set.
Creates an instance of VeganMeal and sets the data member. Calls the loadRecipes(filename) on veganMeal. 
Creates an instance of Lunch and sets the data member, lunch.
Creates an instance of Dinner and sets the data member, dinner.

showPlan() : String – Returns a String with description of lunch and dinner (should call the method getDetails()of lunch and dinner) and day of week. 

getCostOfPlan() : double - should return the total cost of lunch and dinner.

enum MealType {VEGAN,LOW_CARB,HIGH_CARB}

class DietPlanOrder

Data Members

customerName : String

plan : DietPlan;

paymentStatus : boolean;


DietPlanOrder (customerName:String, String filename, MealType mealType): 
Initalizes the customerName. Using a switch statement, creates the appropriate plan. For example, if mealType is VEGAN, creates an instance of VeganDietPlan and sets the data member, plan, with it.

getCost() : double – returns the cost by calling the getCostOfPlan() of plan
	
	
acceptPayment(String cardName, String cardNumber, double amount) : Boolean

a)	A credit card number, for the purpose of this assignment, is verified as follows:
Master Card: A length of 16 digits. First digit is a 5 and second digit is in the range 1 through 5 inclusive. 
Visa: The length is either 13 or 16 digits with a first digit of 4.
b)	If the card number is valid (according to rules in a), paymentStatus is set to true. Otherwise, to false.
	
generateInvoice() : void - Displays the following:
a)	Customer Name
b)	Today’s Date
c)	Details of Diet Plan: These include the recipe details of lunch and dinner.

3.0 Data format in the recipes.txt file
The format is as follows (fields are separated by a colon (:)
recipeName:description:cost:calories
Example:
Pizza:Thin crust:10.00:360

3.1 Your application should allow a client to do the following:
a) Create an instance of a specific DietPlanOrder.
b) Pay the amount due to the diet plan order.
c) Generate invoice.
Note: Before you test your code, make sure that you create the recipes.txt file (using a text editor) using the data format shown in 3.0. Create at least 20 lines of data (one line for each recipe).



Example:
DietPlanOrder planOrder = new DietPlanOrder("Mary Jones",MealType.VEGAN,"recipes.txt");
planOrder.acceptPayment(“Visa”,”7434394923”,planOrder.getCost());
planOrder.generateInvoice();

Note: Your method calls may be different depending on the names you have given.

3.2 Some General Considerations 
a)  Details of recipes are read from text files, where separate files contain recipes for Vegan recipes, Low-carb recipes etc.
b) You are free to create more interfaces and abstract classes.
c) Please keep in mind that a meal can be lunch or dinner and each of them can be of different meal categories. For example, lunch can be Vegan, Low-Carb, High-Carb etc.
d) For sake of simplicity, we assume that each meal consists of a dish based on a single recipe.
e) Same recipes are used for both lunch and dinner
