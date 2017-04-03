QUESTION:

Define the class required in this question, in a package with name, your lastname.assign1.money
Create a class called USMoney with two instance data members (integers), dollars and cents. Define the following methods:
•	A constructor that takes two parameters (dollars and cents) and initializes the corresponding data members. The constructor should check that the cents value is between 0 and 99 and if not, transfer some of the cents to the dollars to make it between and 99. 
•	Define a default constructor.
•	Define two setter methods, one to set dollars (setDollars()) and one to set cents (setCents()).
•	Define two getter methods, one to return the dollars and one to return the cents.
•	Define a method called addTo(int dollars, int cents) that add the parameter values to the data members.
•	Define a method called add (USMoney money) that creates and returns a new USMoney object representing the sum of the object whose add() is invoked and the object passed as parameter. Make sure it does not modify the value of the two existing objects.
•	Define a method called toString() that returns a string representation of the object. 
Given below is an example of using some of the methods of USMoney object.
Usage: 
USMoney m1 = new USMoney (15,80);
System.out.println (m1); 	// Should print $15.80
m1.addTo(25,100);
System.out.println (m1);    // Should print $41.80
USMoney m2 = m1.add( new USMoney (2.90));
System.out.println (m2);    // Should print $44.70	
System.out.println (m1);    // Should print $41.80
   


Create a class called USMoneyTester with the main, in a package with name, your lastname.assign1.q1
 Inside the main, include statements to create instances of USMoney (you must create instances using each of the constructors) and call the methods on each of the instances.
Include the following test case. Make sure that you are calling the methods with values that test the constraints and rules specified for the class.
// Creating instances

USMoney amt1 = new USMoney ();
System.out.println (amt1); 
amt1.setCents (250);
System.out.println (amt1); 
amt1.setDollars (10);
System.out.println (amt1); 
System.out.println (amt1.getCents()); 
USMoney amt2 = amt1.add( new USMoney (2,90)); 
System.out.println (amt1);   
System.out.println (amt2);    
amt2.addTo(amt1.getDollars(), amt1.getCents());
System.out.println (amt2);   
USMoney amt3 = new USMoney (99,120);
amt3.addTo(99,120);
System.out.println (amt3);   
Define the class required in this question, in a package with name, your lastname.assign1.money
Create a class called USMoney with two instance data members (integers), dollars and cents. Define the following methods:
•	A constructor that takes two parameters (dollars and cents) and initializes the corresponding data members. The constructor should check that the cents value is between 0 and 99 and if not, transfer some of the cents to the dollars to make it between and 99. 
•	Define a default constructor.
•	Define two setter methods, one to set dollars (setDollars()) and one to set cents (setCents()).
•	Define two getter methods, one to return the dollars and one to return the cents.
•	Define a method called addTo(int dollars, int cents) that add the parameter values to the data members.
•	Define a method called add (USMoney money) that creates and returns a new USMoney object representing the sum of the object whose add() is invoked and the object passed as parameter. Make sure it does not modify the value of the two existing objects.
•	Define a method called toString() that returns a string representation of the object. 
Given below is an example of using some of the methods of USMoney object.
Usage: 
USMoney m1 = new USMoney (15,80);
System.out.println (m1); 	// Should print $15.80
m1.addTo(25,100);
System.out.println (m1);    // Should print $41.80
USMoney m2 = m1.add( new USMoney (2.90));
System.out.println (m2);    // Should print $44.70	
System.out.println (m1);    // Should print $41.80
   


Create a class called USMoneyTester with the main, in a package with name, your lastname.assign1.q1
 Inside the main, include statements to create instances of USMoney (you must create instances using each of the constructors) and call the methods on each of the instances.
Include the following test case. Make sure that you are calling the methods with values that test the constraints and rules specified for the class.
// Creating instances

USMoney amt1 = new USMoney ();
System.out.println (amt1); 
amt1.setCents (250);
System.out.println (amt1); 
amt1.setDollars (10);
System.out.println (amt1); 
System.out.println (amt1.getCents()); 
USMoney amt2 = amt1.add( new USMoney (2,90)); 
System.out.println (amt1);   
System.out.println (amt2);    
amt2.addTo(amt1.getDollars(), amt1.getCents());
System.out.println (amt2);   
USMoney amt3 = new USMoney (99,120);
amt3.addTo(99,120);
System.out.println (amt3);   
