package goru.project;

import java.util.Random;


/**@author SwathiGoru
* @version 4.0
**/

public class Sensor {
//includes all the actions by the sensor
	int currentTemp; // current temperature of the sensor
	int highTemp; // Sensor highest temperature it can handle
	int lowTemp;  // Sensor lowest temperature it can handle
	String sensorStatus;
	
	public Sensor(int currentTemp, int highTemp, int lowTemp) {
		this.currentTemp = currentTemp;
		this.highTemp = highTemp;
		this.lowTemp = lowTemp;
		this.sensorStatus = "ACTIVE";
	}
	
	//Randomly selects the temperature between the given range when creating the object
	public int getSensorTemp() {
		Random rand = new Random();
		this.currentTemp =  rand.nextInt((highTemp - lowTemp)) + lowTemp;
		return this.currentTemp;
	}
	
	public String getSensorStatus() {
		return this.sensorStatus;
	}
}
