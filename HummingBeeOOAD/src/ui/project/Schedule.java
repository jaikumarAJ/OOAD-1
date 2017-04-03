package ui.project;

public class Schedule {
	private int st,ed;
	public Schedule(int st,int ed) {
		this.st = st;
		this.ed = ed;
	}
	
	public int getStart() {
		return st;
	}
	
	public int getEnd() {
		return ed;
	}
	
	@Override
	public String toString() {
		return st/10+"-"+ed/10;
	}
}
