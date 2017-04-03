package goru.project;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Iterator;


/**@author SwathiGoru
* @version 4.0
**/

public class Schedule implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String scheduleName;
	String startTime;
	DayOfWeek day;
	int duration;
	boolean sensorOverrideFlag;
	ArrayList <SprinklerGroup> sprinklerGroups;
	long waterConsumed = 0;
	String waterConsumptionMode = null;
	
	public Schedule(String scheduleName, ArrayList <SprinklerGroup> sprinklerGroups, int duration, DayOfWeek day, String startTime, String waterConsumptionMode) {
		this.scheduleName = scheduleName;
		this.duration = duration;
		this.day = day;
		this.startTime = startTime;
		this.sprinklerGroups = sprinklerGroups;
		this.waterConsumptionMode = waterConsumptionMode;
	}
	
	public void setSensorOverrideFlag(boolean override) {
		this.sensorOverrideFlag = override;
	}
	
	public boolean getSensorOverrideFlag() {
		return this.sensorOverrideFlag;
	}
	
	public String getScheduleName() {
		return this.scheduleName;
	}
	
	public int getDuration() {
		return this.duration;
	}
	
	public void startStopSchedule(boolean start) {
		Iterator <SprinklerGroup> sprItr = this.sprinklerGroups.iterator();
		if (start) {
			this.waterConsumed = 0;
			while (sprItr.hasNext()){
				SprinklerGroup sprG = sprItr.next();
				sprG.startStopSprinklers(true, this.waterConsumptionMode, this.duration);
			}
		} else {
			while (sprItr.hasNext()){
				SprinklerGroup sprG = sprItr.next();
				sprG.startStopSprinklers(false, this.waterConsumptionMode, this.duration);
				this.waterConsumed += sprG.getWaterUsageSprG();
			}
		}

	}
	
	
	public long getWaterConsumedBySchedule() {
		return this.waterConsumed;
	} 
	
	public String [][] waterConsumedByGroupForSchd() {
		int numZones = this.sprinklerGroups.size();
		String[][] waterConsumed = new String[numZones][numZones];
		for (int i = 0; i < this.sprinklerGroups.size() ; i++){
			SprinklerGroup spg = this.sprinklerGroups.get(i);
			waterConsumed[i][0] = spg.getZoneID();
			waterConsumed[i][1] = Long.toString(spg.getWaterUsageSprG());
		}
		return waterConsumed;
	}
	
	
}
