package goru.assign1.q2;
import goru.assign1.salebin.*;
import goru.assign1.q1.USMoney;

/**@author SwathiGoru
* @version 3.0
**/

public class Q2TestCase {
	
	public static void main (String[] args) {
		SaleItem s1 = new SaleItem("Milk", new USMoney (2,90), 3.5, false);
		SaleItem s2 = new SaleItem("Eggs", new USMoney (3,10), 2.5, true);
		SaleItem s3 = new SaleItem("Candy", new USMoney (1,50), 2.0, false);
		SaleItem s4 = new SaleItem("Wine", new USMoney (4,50), 2, true);
		SaleItem s5 = new SaleItem("Soda", new USMoney (2,00), 2, false);
		Bin b1 = new Bin();
		b1.addItem(s1);
		b1.addItem(s2);
		b1.addItem(s3);
		b1.addItem(s4);
		b1.addItem(s5);
		System.out.println("Bin Details: \n"+ b1.showDetails());
		
		SaleItem s6 = new SaleItem("Apples", new USMoney (2,00), 2.0, false);
		SaleItem s7 = new SaleItem("Oranges", new USMoney (4,00), 3.0, false);
		SaleItem s8 = new SaleItem("Mason Jars", new USMoney (5,00), 3.0, true);
		SaleItem s9 = new SaleItem("Mugs", new USMoney (4,00), 2.5, true);
		SaleItem s10 = new SaleItem("Kiwis", new USMoney (2,00), 2.0, true);
		SmartBin b2 = new SmartBin();
		b2.addItem(s6);
		b2.addItem(s7);
		b2.addItem(s8);
		b2.addItem(s9);
		b2.addItem(s10);
		System.out.println("\nSmartBin Details: \n" + b2.showDetails());

		Bin bin3 = new SmartBin();
		SaleItem s11 = new SaleItem("Banana", new USMoney (3,00), 2.0, false);
		SaleItem s12 = new SaleItem("Banana", new USMoney (3,00), 2.0, true);
		bin3.addItem(s11);
		bin3.addItem(s12);
		//Bin3 will add fragile item to the list as the object Bin3 is of SmartBin type. 
		//Hence the method addItem within SmartBin will be invoked here.
		System.out.println("\nBin3 Details: \n"+ bin3.showDetails());
		
		}
	
	
}
