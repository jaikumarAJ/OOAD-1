package ui.project;

public class Sprinkler {
	
	private String id;
	private boolean status;
	private int wc;
	
	public Sprinkler(String id) {
		this.id = id;
		status = true;
		wc = 1;
	}
	
	public int getWaterConsumption() {
		return wc;
	}
	
	public boolean getStatus() {
		return status;
	}
	
	public void setStatus(boolean s) {
		status = s;
	}
}
