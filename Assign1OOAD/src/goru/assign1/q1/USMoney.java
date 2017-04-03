package goru.assign1.q1;

/**@author SwathiGoru
 * @version 3.0
 **/

public class USMoney {
	private int dollars;
	private int cents;

	//constructor
	public USMoney(int dollars, int cents) {
		dollars = checkDollars(dollars);
		cents = checkCents(cents);
		// Adjusting cents to dollars if cents are more than 99
		if(!(cents >= 0 && cents <= 99)) {
		 dollars += cents/100;
		 cents = cents%100;
		}
		this.dollars = dollars;
		this.cents = cents;
	}
	
	//default constructor
		public USMoney(){ }
	
		/**
		 * Set Dollars
		 * @param dollar: An integer. This cannot be a negative number.
		 * <p>
		 * This function sets a given value to dollars. 
		 * It resets the already existing dollars (if any) 
		 * to the value provided within this function.
		 */
		public void setDollars(int dollars) {
			dollars = checkDollars(dollars);
			this.dollars = dollars;
		}
		
	/**
	 * setCents
	 * This function sets a given value to cents. 
	 * The function checks if cents is greater than 99.
	 * If yes, it transfers some of the cents to dollars to make cents between 0 and 99.
	 * It will also reset the dollars to carried-over value.
	 * @param cents: Integer. Cannot be a negative number.
	 */
		public void setCents(int cents) {
			cents = checkCents(cents);
			if(!(cents>=0 && cents <=99)) {
				 this.dollars = cents/100;
				 cents = cents%100;
				}
			this.cents = cents;
		}
		
		public int getDollars(){
			return this.dollars;
		}
		public int getCents(){
			return this.cents;
		}
		
		/**
		 * checkCents
		 * @param cents : an integer. Cents cannot be a negative number.
		 * <p> Program checkCents checks if cents is a valid number.
		 * If not, it sets it to zero.
		 */
		private int checkCents(int cents) {
			if (cents < 0) {
				cents = 0;
				//System.out.println("Cents cannot be a negative number. Hence setting Cents to Zero.");
			}
			return cents;
		}
		
		/**
		 * check Dollars
		 * This function is to check if Dollars is a valid number. 
		 * If Dollars is a negative number, it automatically resets the value to Zero.
		 * @param dollars : integer. This cannot be a negative number.
		 */
		private int checkDollars(int dollars){
			if (dollars < 0) {
				dollars = 0;
			}
			return dollars;
		}
		
		/**
		 * addTo function adds the parameter values to USMoney object
		 * @param addDollars : Integer. Cannot be a negative number.
		 * @param addCents : Integer. Cannot be a negative number.
		 */
		public void addTo(int addDollars, int addCents){
			
			addDollars = checkDollars(addDollars);
			addCents = checkCents(addCents);
			addCents += this.cents;
			
			if(!(addCents>=0 && addCents <=99)) {
				 addDollars += addCents / 100;
				 addCents = addCents % 100;
			}
			this.dollars += addDollars;
			this.cents = addCents;
			
		}
		
		public USMoney add (USMoney m) {
//			USMoney returnMoney = new USMoney();
			int mDollars = m.getDollars();
			int mCents = m.getCents();
			
			//checkDollars(mDollars);
			//checkCents(mCents);
			
			int returnDollars = this.getDollars() + mDollars;
			int returnCents = this.getCents() + mCents;
			USMoney returnMoney = new USMoney(returnDollars,returnCents);
			/* if(!(returnCents>=0 && returnCents <=99)) {
				 returnDollars += returnCents / 100;
				 returnCents = returnCents % 100;
			}	
			returnMoney.setDollars(returnDollars);
			returnMoney.setCents(returnCents);	*/
			return returnMoney;
		}
		
		@Override
		public String toString() {
			return "$"+this.dollars+"."+this.cents;
			
		}
		
}
