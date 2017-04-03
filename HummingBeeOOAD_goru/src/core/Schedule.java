package core;

import java.io.Serializable;

public class Schedule implements Serializable 
{
	String day;
	int time;
	int duration;
	String flow;
	String sprinklerId;
	int hourMultiplier;
	transient Sprinkler sprinkler;
	static final long serialVersionUID = 1;
	
	public Schedule(){}
	
	public Schedule(String day, int time, int duration, Sprinkler sprinkler, String flow)
	{
		this.day = day;	
		if(day.equals("Sunday"))
			hourMultiplier = 0;
		else if(day.equals("Monday"))
			hourMultiplier = 1;
		else if(day.equals("Tuesday"))
			hourMultiplier = 2;
		else if(day.equals("Wednesday"))
			hourMultiplier = 3;
		else if(day.equals("Thursday"))
			hourMultiplier = 4;
	    else if(day.equals("Friday"))
	    	hourMultiplier = 5;
	    else if(day.equals("Saturday"))
	    	hourMultiplier = 6;
		
		this.time = time;
		this.duration = duration;
		this.sprinkler = sprinkler;
		this.sprinklerId = sprinkler.getId();
		this.flow = flow;
	}	
	  
	void setSprinkler(Sprinkler sprinkler)
	{
		assert(sprinklerId.equals(sprinkler.getId()));
		this.sprinkler = sprinkler;
	}
	
	@Override public String toString()
	{
		return day + " " + Integer.toString(time) + " " + sprinkler + " " + flow;
	}
		
	int getTime()
	{
		return (hourMultiplier*24 + time)*60;
	}
	
	int getDuration()
	{
		return duration;
	}
	    
	String getFlow()
	{
		return flow;
	}
		
	String getSprinklerId()
	{
		return sprinklerId;
	}
		
	Sprinkler getSprinkler()
	{
		return sprinkler;
	}
}
