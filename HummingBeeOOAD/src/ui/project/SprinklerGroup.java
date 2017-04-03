package ui.project;

import java.util.ArrayList;
import java.util.HashMap;

public class SprinklerGroup {
	
	private String sector;
	private HashMap<String,Schedule> bounds;
	private ArrayList<Sprinkler> spList;
	private boolean status;
	private boolean tempExt;
	private int tempExtValue;
	
	public SprinklerGroup(String s) {
		sector = s;
		status = true;
		spList = new ArrayList<Sprinkler>();
		for(int i=0;i<2;i++) spList.add(new Sprinkler(sector + Integer.toString(i)));
		bounds = new HashMap<String,Schedule>();
		tempExt = false;
		tempExtValue = 15;
	}
	
	public int getWaterConsumption() {
		int ans = 0;
		for(int i=0;i<2;i++) if(spList.get(i).getStatus() == true) {
			ans++;
		}
		return ans;
	}
	
	public String getSectorName() {
		return sector;
	}
	
	public boolean getStatus() {
		int c = 0;
		for(int i=0;i<2;i++) if(spList.get(i).getStatus()) c++;
		if(c == 0) status = false;
		else status = true;
		return status;
	}
	
	public void setIndiStatus(int i,boolean b) {
		spList.get(i).setStatus(b);
	}
	
	public boolean getIndiStatus(int i) {
		return spList.get(i).getStatus();
	}
	
	public void setStatus(boolean b) {
		for(int i=0;i<2;i++) spList.get(i).setStatus(b);
		status = b;	
	}
	
	public Schedule getBounds(String day) {
		return bounds.get(day);
	}
	
	public void setBounds(String day, Schedule sch) {
		bounds.put(day,sch);
	}
	
	public boolean getTempExtDoneStatus() {
		return tempExt;
	}
	
	public void setTempExtDoneStatus(boolean b) {
		tempExt = b;
	}
	
	public int getTempExtValue() {
		return tempExtValue;
	}
	
	public void setTempExtValue(int v) {
		tempExtValue = v;
	}
	
}
