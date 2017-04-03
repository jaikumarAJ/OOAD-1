package goru.assign1.q1;

/**@author SwathiGoru
* @version 3.0
**/

public class USMoneyTester {
	
	public static void main (String[] args){
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
	}
}
