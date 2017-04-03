//Generate a random temperature within lowTempVal and highTempVal

package core;

public class Temperature 
{
	int currentTemp;
    int lowTempVal;
    int highTempVal;
    
    static final int minTemp = 55;
    static final int maxTemp = 99;
    
    public Temperature(int currentTemp)
    {
    	this.currentTemp = currentTemp;
    	this.lowTempVal = minTemp;
    	this.highTempVal = maxTemp;
    }
    
    public Temperature()
    {
    	currentTemp = 65;
    	this.lowTempVal = minTemp;
    	this.highTempVal = maxTemp;
    }
    
    public void setCurrentTemp(int currentTemp)
    {
    	this.currentTemp = currentTemp;
    }
    
    public void setLowTemp(int lowTempVal)
    {
    	this.lowTempVal = lowTempVal;
    }
    
    public void setHighTemp(int highTempVal)
    {
    	this.highTempVal = highTempVal;
    }
    
    public int lowTemp()
    {
    	return lowTempVal;
    }
    
    public int highTemp()
    {
    	return highTempVal;
    }
    
    public int getRandomTemp()
    {
    	currentTemp = minTemp + (int)(Math.random() * ((maxTemp - minTemp) + 1));
        return currentTemp;
    }
    
    public int getCurrentTemp()
    {
        return currentTemp;
    }
    
    public static void main(String args[])
    {
    	int currentTemp = 72;
    	int lowTemp = 65;
    	int highTemp = 90;
    	
    	Temperature temp = new Temperature(currentTemp);
    	
    	temp.setLowTemp(lowTemp);
    	temp.setHighTemp(highTemp);
    	
    	for(int i = 0; i < 20; ++i)
    	{
    		if (i%4 == 0) currentTemp = temp.getRandomTemp();
    		else currentTemp = temp.getCurrentTemp();
    		System.out.println("Simulator_tick " + i + " temperature " + currentTemp);
    		if(currentTemp < temp.lowTemp())
    			System.out.println("Temperature less than " + temp.lowTemp());
    		if(currentTemp > temp.highTemp())
    			System.out.println("Temperature higher than " + temp.highTemp());
    	}
    }
}
