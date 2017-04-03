package goru.project;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Iterator;

/**@author SwathiGoru
* @version 4.0
**/
public class Starter {

	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		HummingBeeSystem hb = new HummingBeeSystem();
		hb.setTriggerTemp(90);
		long startTime = 0;
		Schedule sch = hb.getScheduleFromName("SunSch");
		
		
		startTime = System.currentTimeMillis();
		sch.startStopSchedule(true);
		while(true) {
			if(((sch.getDuration()*1000)+startTime) <= System.currentTimeMillis()) {
				sch.startStopSchedule(false);;
				break;
			}
		}
		
		hb.addSpGWaterUsageToFile();
		System.out.println("\nGet water consumed :" + hb.getCurrentWaterConsumption());
		
		Schedule sch1 = hb.getScheduleFromName("MonSch");
		
		
		startTime = System.currentTimeMillis();
		sch1.startStopSchedule(true);;
		while(true) {
			if(((sch.getDuration()*1000)+startTime) <= System.currentTimeMillis()) {
				sch1.startStopSchedule(false);
				break;
			}
		}
		
		hb.addSpGWaterUsageToFile();
		System.out.println("\nGet water consumed2 :" + hb.getCurrentWaterConsumption());
		//Helper Code for UI
		
		// Temperature checking code
		//can be used for Run Now too
		while (true) {
			int temp = hb.s.getSensorTemp();
			if ( temp > hb.getTriggerTemp()) {
				ArrayList <SprinklerGroup> spg = hb.getSprinklerGroups();
				Iterator <SprinklerGroup> spgItr = spg.iterator();
				startTime = System.currentTimeMillis();
				while(spgItr.hasNext()) {
					spgItr.next().startStopSprinklers(true, "HIGH", 0);; //Starting sprinklers				
				}
				break;
			}
		}
		
		// After the duration we need to stop the sprinklers
		int duration = 1; // this is an example duration.
		while (true) {
			if  ((startTime + duration*1000) <= System.currentTimeMillis()) {
				ArrayList <SprinklerGroup> spg = hb.getSprinklerGroups();
				Iterator <SprinklerGroup> spgItr = spg.iterator();
				while(spgItr.hasNext()) {
					spgItr.next().startStopSprinklers(false, "HIGH", duration); // Stopping Sprinklers
				}
				break;
			}
		}
		
		/*
		ArrayList<SprinklerGroup> spGs = hb.getSprinklerGroups();
		Iterator<SprinklerGroup> spgItr = spGs.iterator();
		while(spgItr.hasNext()) {
			SprinklerGroup spg = spgItr.next();
		}*/
		// hb.addSpGWaterUsageToFile();
		// System.out.println("\nGet water consumed3 :" + hb.getCurrentWaterConsumption());
		
		String [][] resultArray = hb.displayWaterUsage(1);
		for (int i=0; i < resultArray.length; i++) {
			System.out.println("\n Group :" + resultArray[i][0] + "  Usage:" + resultArray[i][1]);
		}

		//End of everyday , we need to put the sprinker usage Data to a file
		// hb.addSpGWaterUsageToFile();
		ArrayList <SprinklerGroup> spg = hb.getSprinklerGroups();
		Iterator <SprinklerGroup> spgIt = spg.iterator();
		while(spgIt.hasNext()) {
			spgIt.next().resetwaterUsageThisGroup(); //Starting sprinklers
		}
		hb.addSpGWaterUsageToFile();
		
		//Addding a new schedule use this function:
		//	public void addNewSchedule(String scheduleName, String[] zones, int duration, DayOfWeek day, String startTime, String waterConsumptionMode) {

       /*		
		// If schedule is reached and we need to start the sprinklers:
		if (hb.s.getSensorTemp() > 55) {
			int duration =schedule.getDuration();
			schedule.startStopSchedule(true);
			int startTime = System.currentTimeMillis();
			while(1) {
				if ((startTime + duration*1000) >= System.currentTimeMillis()) {
					schedule.startStopSchedule(false);
					break;
				}
			} 
			
		}*/
	}

}
