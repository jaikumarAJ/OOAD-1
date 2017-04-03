package goru.project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**@author SwathiGoru
* @version 4.0
**/
public class SprinklerGroup implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String zoneID;
	long duration = 0;
	long startTime = 0;
	private ArrayList<Sprinkler> sprinklerList = new ArrayList<Sprinkler>();
	boolean groupRunningStatus= false;
	int numSprinklersRunning = 0;
	long timeRunning = 0; 
	long waterUsageThisGroup = 0;
	private final static int WATER_CONSUMPTION_HIGH = 3;
	private final static int WATER_CONSUMPTION_MEDIUM = 2;
	private final static int WATER_CONSUMPTION_LOW = 1;
	
	
	public SprinklerGroup(String zoneID, ArrayList<Sprinkler> sprinklerList) {
		this.zoneID = zoneID;
		this.sprinklerList = sprinklerList;
	}

	public String getZoneID() {
		return this.zoneID;
	}
	
	public void addSprinkler(Sprinkler s) {
		this.sprinklerList.add(s);
	}
	
	public ArrayList<Sprinkler> getSprinklerList() {
		return this.sprinklerList;
	}
	
	public void resetwaterUsageThisGroup() {
		this.waterUsageThisGroup = 0;
	}
	
	// Starts or stops Sprinkler Group
	// If Param is false, the group is turned off. Here the waterConsumptionMode can be of any value.
	//If it is true, it is turned on.
	public void startStopSprinklers(boolean start, String waterConsumptionMode, int duration) {
		Iterator<Sprinkler> sprinklerItr = this.sprinklerList.iterator();
		
		if(start) {
			this.groupRunningStatus = true;
			while (sprinklerItr.hasNext()) {
				Sprinkler sprinkler = sprinklerItr.next();
				if (sprinkler.getSprinklerFuncStatus().equals("OK")){
					this.numSprinklersRunning++;
					sprinkler.setrunningStatus("ON");
				}
			}
		} else {
			this.groupRunningStatus = false;
			if (waterConsumptionMode.equals("HIGH")) {
				this.waterUsageThisGroup += numSprinklersRunning * WATER_CONSUMPTION_HIGH * duration;
			} else if (waterConsumptionMode.equals("MEDIUM")) {
				this.waterUsageThisGroup += numSprinklersRunning * WATER_CONSUMPTION_MEDIUM * duration;
			} else {
				this.waterUsageThisGroup += numSprinklersRunning * WATER_CONSUMPTION_LOW * duration;
			}

			while (sprinklerItr.hasNext()) {
				Sprinkler sprinkler = sprinklerItr.next();
				sprinkler.setrunningStatus("OFF");
			}
			this.numSprinklersRunning = 0;
		}
		
	}
	
	public long getWaterUsageSprG() {
		return this.waterUsageThisGroup;
	}
	
	// Gives the status of the sprinkler group
	public boolean getSprinklerGroupStatus() {
		return this.groupRunningStatus;
	}
	
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public long getDuration() {
		return this.duration;
	}
}
