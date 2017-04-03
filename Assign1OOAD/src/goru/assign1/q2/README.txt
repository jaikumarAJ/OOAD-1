QUESTION:

In this question, you will use the class USMoney from exercise 1, as the type for a data member in another class.
Define the classes and interfaces required in this question, in a package with name, your lastname.assign1.salebin
Define the classes and interfaces as described below:
interface ItemType
	isFragile(): Boolean
getPrice(): USMoney
getWeight(): double
getDetails() : String
class SaleItem implements ItemType
	itemName: String
	price: USMoney
	weight: double
	fragile: boolean
      getDetails(): String
		This method should return a concatenated string of itemName, price
SaleItem (itemName:String, price: USMoney, weight:double,fragile:boolean)     
interface BinType 
	addItem (ItemType item)
	calculatePrice(): USMoney
	getWeight(): double
	getNoOfItems() : integer
	showDetails() : String	
class Bin implments BinType 
-	binNumber: String

-	ItemType [] items;


-	double maxWeight (a constant and is the same for all Bins): initialized to a value of your choice


-	Bin()- The numbers for bins should start with at least one letter and followed by a unique number. Use the generateBinNumber() method to generate a unique binNumber.

generateBinNumber(): integer
A unique number (among all the bins created) should be generated and returned. This can be done using a class-level (static) counter that can be incremented after using the counter for the bin number. Therefore this method should be a class-level method.
-	addItem (ItemType item):checks the weight of the item and if adding it to the bin does not exceed Bin’s maxWeight, and if the item is not fragile, adds it to the array, items.
-	
-	calculatePrice(): USMoney 
calculates the total price of all items in items array and adds $100.00 as the bin cost and returns the total.
 
-	showDetails(): String 
returns a string by concatenating the binNumber+currentWeight+totalCost of Items.

class SmartBin extends Bin
-	label: String
-	
-	SmartBin()- Bin numbers for Smartbins should start with a “SM” and followed by an integer. Use the generateBinNumber() method to generate a unique binNumber.
-	
-	setLabel(String label) 
sets the label with the value in the parameter.
-	addItem (ItemType item):checks the weight of the item and if adding it to the bin does not exceed Bin’s maxWeight, adds it to the array, items. If the added item is fragile, sets the bin’s label to “Fragile - Handle with Care”.
-	
-	calculatePrice(): USMoney 
calculates the total price of all items in items array and adds $100.00 as the bin cost. If there are fragile items in the bin, adds $10 extra for each of the fragile items. Returns the total price.
 
-	showDetails(): String 
returns a string by concatenating the binNumber+label+currentWeight+totalCost of Items.

	
class Q2TestCase: This class will have the main(). 
Define the class required in this question, in a package with name, your lastname.assign1.q2
Include the following testcases and capture the output.
a)	Create at least 5 instances of SaleItems with data of your choice. Make some of the items “fragile”.
b)	Create an instance of a Bin.
c)	Add the instances of SaleItems from a) to the Bin instance in b). Make sure that you exceed the maximum weight of the bin.
d)	Show details on the Bin instance in b)
e)	Create at least 5 more instances of SaleItems with data of your choice. Make some of the items “fragile”.
f)	Create an instance of a SmartBin.
g)	Add the instances of SaleItems from e) to the Bin instance in f). Make sure that you exceed the maximum weight of the bin.
h)	Show details on the Bin instance in f)
i)	Create an instance of a Bin as follows:
Bin bin3 = new SmartBin();
j)	Can you add fragile SaleItems to bin3?
Note:
a)	Make the data members private or protected (not public)
b)	Write all the necessary constructors. Make sure that you have the required constructors to initialize the superclass members, in case of inheritance.
c)	You are free to add any extra data members or methods as you need.
d)	Remember the principle, “Program to the interface and not to the implementation”, wherever it is possible. For example, declare the most generic type (an interface?) for a parameter , so that you can actually pass an instance of a class that implements that interface.
General Requirements:
1.	Include comments in the program (where applicable) to make your logic clear.
2.	Use Java conventions for naming classes, methods and variables.
