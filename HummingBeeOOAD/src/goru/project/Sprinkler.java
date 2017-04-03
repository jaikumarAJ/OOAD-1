package goru.project;

import java.io.Serializable;

/**@author SwathiGoru
* @version 4.0
**/

public class Sprinkler implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String sprinklerID;
	String funcStatus;
	String Zone;
	String runningStatus;
	
	public String getSprinklerFuncStatus() {
		return this.funcStatus;
	}
	
	public String getSprinklerZone() {
		return this.Zone;
	}
	public void setSprinklerFuncStatus(String OK_NOTOK) {
		this.funcStatus = OK_NOTOK;
	}
	
	public Sprinkler (String sprinklerID, String Zone) {
		this.sprinklerID = sprinklerID;
		this.Zone = Zone;
		this.funcStatus = "OK";
		this.runningStatus = "OFF";
	}
	
	public String getSprinklerRunningStatus() {
		return this.runningStatus;
	}
	
	public void setrunningStatus(String runningStatus){
		this.runningStatus = runningStatus;
	}
}

