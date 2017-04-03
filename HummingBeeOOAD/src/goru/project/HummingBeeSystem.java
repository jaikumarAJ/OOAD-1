package goru.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**@author SwathiGoru
* @version 4.0
**/

public class HummingBeeSystem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<SprinklerGroup> sprinklerGroups = new ArrayList<SprinklerGroup>();
	Sensor s;
	int lowestTemp;
	int highestTemp;
	ArrayList<Schedule> schedules = new ArrayList<Schedule>();
	int numSchedules;
	private static String OBJECT_STORE = "schedules.ser";
	private static String WATER_CONSUMPTION_FILE = "water_consumption.txt";
	ArrayList <Sprinkler> splEastList = new ArrayList<Sprinkler>();
	ArrayList <Sprinkler> splWestList = new ArrayList<Sprinkler>();
	ArrayList <Sprinkler> splNorthList = new ArrayList<Sprinkler>();
	ArrayList <Sprinkler> splSouthList = new ArrayList<Sprinkler>();
	
	int triggerTemp = 100; //Temperature at which the sprinkler schedule is overridden

	/*
	 *  =============== GETTER/SETTER METHODS ====================
	 */
	
	public void setSensor(Sensor s) {
		this.s = s;
	}
	
	public void setLowestTemp(int lowTemp) {
		this.lowestTemp = lowTemp;
	}
	
	public void setHighestTemp(int highTemp) {
		this.highestTemp = highTemp;
	}

	public void setTriggerTemp(int triggerTemp) {
		this.triggerTemp = triggerTemp;
	}
	
	public int getTriggerTemp() {
		return this.triggerTemp;
	}
	
	public ArrayList<SprinklerGroup> getSprinklerGroups() {
		return sprinklerGroups;
	}

	public void setSprinklerGroup(ArrayList<SprinklerGroup> sprinklerGroups) {
		this.sprinklerGroups = sprinklerGroups;
	}
	
		
	/*
	 * ====================== SCHEDULER METHODS =====================
	 * 
	 */
	
	
	// Adding a new schedule
	public void addNewSchedule(String scheduleName, String[] zones, int duration, DayOfWeek day, String startTime, String waterConsumptionMode) {
		ArrayList<SprinklerGroup> scheduleSprGroups = new ArrayList<SprinklerGroup>();
		for (int i = 0; i < zones.length; i++) {
			SprinklerGroup spr = getSprinklerGroupByID(zones[i]);
			if (spr != null) {
				scheduleSprGroups.add(spr);
			}
		}
		schedules.add(new Schedule(scheduleName, scheduleSprGroups, duration, day, startTime, waterConsumptionMode));
	}
	
	// Removes the schedule from the system 
	public void removeSchedule(String scheduleName) {
		schedules.remove(getScheduleFromName(scheduleName));

	}
	
	
	// Returns the SprinklerGroup of the specified zone/ID
	public SprinklerGroup getSprinklerGroupByID(String sprinklerGroup) {
		Iterator <SprinklerGroup> sprItr = sprinklerGroups.iterator();
	    SprinklerGroup resultGroup = null;
		while(sprItr.hasNext()){
			SprinklerGroup sprG = sprItr.next();
			if (sprG.getZoneID().equals(sprinklerGroup)) {
				resultGroup = sprG;
				break;
			}
		}
		return resultGroup;
	}
	
	//Get the schedule object with the name
	public Schedule getScheduleFromName(String schedule) {
		Schedule scheduleObj = null;
		Iterator <Schedule> schItr = schedules.iterator();
		while(schItr.hasNext()) {
			scheduleObj = schItr.next();
			if (scheduleObj.getScheduleName().equals(schedule)){
				break;
			}
		}
		return scheduleObj;
	}
	
	
	
	/*
	 *  ======================== DATA STORING/RETRIVING METHODS =====================
	 */
	
	//Serialize the schedules and store as a persistent data.
	public  void storeSchedulesToFile() {
		FileOutputStream fOut = null;
		ObjectOutputStream objOut = null;
		try {
			fOut = new FileOutputStream(OBJECT_STORE);
			objOut = new ObjectOutputStream(fOut);
			for(int i = 0; i < this.schedules.size(); i++) {
				objOut.writeObject(this.schedules.get(i));
			}
			objOut.flush();
			objOut.close();
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	//Reads from persistent data source and populates the schedules.
	public void readSchedulesFromFile() throws ClassNotFoundException {
		try {
			FileInputStream fileIn = new FileInputStream(OBJECT_STORE);
			ObjectInputStream objIn = new ObjectInputStream(fileIn);
 			
			while(objIn.available() > 0) {
				this.schedules.add((Schedule) objIn.readObject());
			}
			
			objIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//Add the formatted string of the days usage to a file
	public void addSpGWaterUsageToFile() {
		String newString = "";
		SprinklerGroup spg = null;
		for(int i = 0; i < this.sprinklerGroups.size() ; i++) {
			spg = this.sprinklerGroups.get(i);
			newString += spg.getZoneID() + ":" + spg.getWaterUsageSprG();
			if (i < (this.sprinklerGroups.size() -1)) {
				newString += ":";
			}
		}
		
		File usageFile = new File(WATER_CONSUMPTION_FILE);
		try {
			FileInputStream fIN = new FileInputStream(usageFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(fIN));
			String copyText = "";
			String nextLine = "";
			int lineNumber = 1;
			while(((nextLine = br.readLine()) != null) && (lineNumber < 30)) {
				copyText = copyText + nextLine + "\n";
				lineNumber++;
			}
			
			copyText = newString + "\n" + copyText;
			usageFile.delete();
			FileOutputStream fOUT = new FileOutputStream(WATER_CONSUMPTION_FILE);
			fOUT.write(copyText.getBytes());
			fOUT.flush();
			fOUT.close();	
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	
	/*
	 *  ====================== OUTPUT METHODS ==========================
	 */
	
	
	
	/*
	 * Input dayWeekMonth: 1 : water usage for one day
	 *                     2 : water usage for a week
	 *                     3 : water usage for last 30 days.
	 */
	public String [][] displayWaterUsage(int dayWeekMonth) {
		String [][] usageByGroup = new String[4][4];
	    int linesToRead = 1; // For a day
	    if (dayWeekMonth == 2) { // For a week
	    	linesToRead = 7;
	    } else if(dayWeekMonth == 3) { //For a month
	    	linesToRead = 30;
	    } 
		 
		File file = new File(WATER_CONSUMPTION_FILE);
		Scanner scan = null;
		
		try {
			scan = new Scanner(file);
			int lineCount = 1;
			while(scan.hasNextLine() && (lineCount <= linesToRead)) {
				String line = scan.nextLine();
				String [] details = line.split(":");
				if (lineCount == 1) {
					usageByGroup[0][0] = details[0];
					usageByGroup[0][1] = details[1];
					usageByGroup[1][0] = details[2];
					usageByGroup[1][1] = details[3];
					usageByGroup[2][0] = details[4];
					usageByGroup[2][1] = details[5];
					usageByGroup[3][0] = details[6];
					usageByGroup[3][1] = details[7];
				} else {
					//for EAST
					usageByGroup[0][1] = Integer.toString(Integer.parseInt(usageByGroup[0][1]) + Integer.parseInt(details[1]));
					// for WEST
					usageByGroup[1][1] = Integer.toString(Integer.parseInt(usageByGroup[1][1]) + Integer.parseInt(details[3]));
					//for NORTH
					usageByGroup[2][1] = Integer.toString(Integer.parseInt(usageByGroup[2][1]) + Integer.parseInt(details[5]));
					//for SOUTH
					usageByGroup[3][1] = Integer.toString(Integer.parseInt(usageByGroup[3][1]) + Integer.parseInt(details[7]));
					
				}
				lineCount++;
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found"+ file.toString());
		} finally {
			if (scan != null)
				scan.close();
		}
		return usageByGroup;
	}
	
	//Gives the current water consumption consumed till that point in a day
	public int getCurrentWaterConsumption() {
		int waterConsumed = 0;
		SprinklerGroup spg = null;
		for(int i = 0; i < this.sprinklerGroups.size() ; i++) {
			spg = this.sprinklerGroups.get(i);
			waterConsumed += spg.getWaterUsageSprG();
		}
		return waterConsumed;
	}
	
	
	/*
	 *   ========= DEFAULT SETUP METHODS =============
	 */
	
	
	public void setUpDefaultSprinklers() {
		Sprinkler spr1 = new Sprinkler("1E", "EAST");
		Sprinkler spr2 = new Sprinkler("2E", "EAST");
		Sprinkler spr3 = new Sprinkler("3E", "EAST");
		Sprinkler spr4 = new Sprinkler("4E", "EAST");
		splEastList.add(spr1);
		splEastList.add(spr2);
		splEastList.add(spr3);
		splEastList.add(spr4);
		
		Sprinkler spr5 = new Sprinkler("1W", "WEST");
		Sprinkler spr6 = new Sprinkler("2W", "WEST");
		Sprinkler spr7 = new Sprinkler("3W", "WEST");
		Sprinkler spr8 = new Sprinkler("4W", "WEST");
		splWestList.add(spr5);	
		splWestList.add(spr6);		
		splWestList.add(spr7);		
		splWestList.add(spr8);		
		Sprinkler spr9 = new Sprinkler("1N", "NORTH");
		Sprinkler spr10 = new Sprinkler("2N", "NORTH");
		Sprinkler spr11 = new Sprinkler("3N", "NORTH");
		Sprinkler spr12 = new Sprinkler("4N", "NORTH");
		splNorthList.add(spr9);
		splNorthList.add(spr10);
		splNorthList.add(spr11);
		splNorthList.add(spr12);
		Sprinkler spr13 = new Sprinkler("1S", "SOUTH");
		Sprinkler spr14 = new Sprinkler("2S", "SOUTH");
		Sprinkler spr15 = new Sprinkler("3S", "SOUTH");
		Sprinkler spr16 = new Sprinkler("4S", "SOUTH");
		splSouthList.add(spr13);
		splSouthList.add(spr14);
		splSouthList.add(spr15);
		splSouthList.add(spr16);
	}
	
	
	public void setupDefaultSprinklerGroups() {
		SprinklerGroup spGEast = new SprinklerGroup("EAST", splEastList);
		SprinklerGroup spGWest = new SprinklerGroup("WEST", splWestList);
		SprinklerGroup spGNorth = new SprinklerGroup("NORTH", splNorthList);
		SprinklerGroup spGSouth = new SprinklerGroup("SOUTH", splSouthList);
		sprinklerGroups.add(spGEast);
		sprinklerGroups.add(spGWest);
		sprinklerGroups.add(spGNorth);
		sprinklerGroups.add(spGSouth);
	}
	
	public void setupDefaultSchedules() {
		String [] zoneArray1 = {"EAST", "NORTH"};
		DayOfWeek day = DayOfWeek.MONDAY;
		addNewSchedule("MonSch", zoneArray1, 1, day,  "9", "HIGH");
		
		String [] zoneArray2 = {"EAST", "NORTH", "WEST"};
		day = DayOfWeek.TUESDAY;
		addNewSchedule("TueSch", zoneArray2, 30, day,  "8", "MEDIUM");
		
		String [] zoneArray3 = {"EAST", "NORTH", "WEST", "SOUTH"};
		day = DayOfWeek.WEDNESDAY;
		addNewSchedule("WedSch", zoneArray3, 30, day,  "19", "HIGH");
		
		String [] zoneArray4 = {"EAST", "WEST", "SOUTH"};
		day = DayOfWeek.THURSDAY;
		addNewSchedule("ThuSch", zoneArray4, 40, day,  "9", "LOW");
		
		String [] zoneArray5 = {"EAST", "NORTH", "SOUTH"};
		day = DayOfWeek.FRIDAY;
		addNewSchedule("FriSch", zoneArray5, 50, day,  "10", "HIGH");
		
		String [] zoneArray6 = {"NORTH", "WEST", "SOUTH"};
		day = DayOfWeek.SATURDAY;
		addNewSchedule("SatSch", zoneArray6, 45, day,  "12", "MEDIUM");
		
		String [] zoneArray7 = {"NORTH", "WEST", "SOUTH", "EAST"};
		day = DayOfWeek.SUNDAY;
		addNewSchedule("SunSch", zoneArray7, 1, day,  "18", "HIGH");
	}
	
	public void setUpDefaultSensor() {
		this.s = new Sensor(70, 120, 40);
	}
	
	public HummingBeeSystem() {
		this.setUpDefaultSprinklers();
		this.setupDefaultSprinklerGroups();
		this.setupDefaultSchedules();
		this.setUpDefaultSensor();
		// this.storeSchedulesToFile();
		//this.readSchedulesFromFile();
		this.setTriggerTemp(100);
        
	}

}
