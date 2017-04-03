package ui.project;

import java.util.Random;
public class Sensor {
	
	private int temp;
	
	public Sensor() {
		temp = 48;
	}
	
	public int getTemperature(int time) {
		int mid = 90 - Math.abs(time-120)/3;
		Random r = new Random();
		int dev = r.nextInt(5);
		if(time <= 120) {
			if(mid + dev > temp) temp = mid + dev;
		} else {
			if(mid - dev < temp) temp = mid - dev;
		}
		return temp;
	}
}
