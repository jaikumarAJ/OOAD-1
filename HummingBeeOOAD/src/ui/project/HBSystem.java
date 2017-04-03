package ui.project;

import java.util.HashMap;

public class HBSystem {
	private HashMap<String,SprinklerGroup> grp;
	private Sensor sensor;
	
	public HBSystem() {
		
		sensor = new Sensor();
		grp = new HashMap<String,SprinklerGroup>();
		
		grp.put("North",new SprinklerGroup("N"));
		grp.put("South",new SprinklerGroup("S"));
		grp.put("East",new SprinklerGroup("E"));
		grp.put("West",new SprinklerGroup("W"));
	}
	
	public boolean getSprinklerGrpStatus(String s) {
		return grp.get(s).getStatus();
	}
	
	public void setSprinklerGrpStatus(String s,boolean f) {
		grp.get(s).setStatus(f);
	}
	
	public Schedule getBounds(String sector) {
		String[] tkn = sector.split("-");
		assert(tkn.length == 2);
		return grp.get(tkn[0]).getBounds(tkn[1]);
	}
	
	public void setBounds(String s,Schedule sch) {
		String[] tkn = s.split("-");
		assert(tkn.length == 2);
		grp.get(tkn[0]).setBounds(tkn[1],sch);
	}
	
	public boolean getTempExtDone(String s) {
		return grp.get(s).getTempExtDoneStatus();
	}
	
	public void setTempExtDone(String s,boolean b) {
		grp.get(s).setTempExtDoneStatus(b);
	}
	
	public int getTempExtValue(String s) {
		return grp.get(s).getTempExtValue();
	}
	
	public void setTempExtValue(String s,int v) {
		grp.get(s).setTempExtValue(v);
	}
	
	public int getTemperature(int cnt) {
		return sensor.getTemperature(cnt);
	}
	
	public SprinklerGroup getSprinklerGrp(String s) {
		return grp.get(s);
	}
	
}
