package core;

import java.util.ArrayList;

public class Sprinkler 
{
	char groupId; //N: north, S: south, E: east, W: west
	int locationId; //location of sprinkler in grp through r->l or top->bottom
	int rateOfFlow; //rate of flow of individual sprinklers
	String status;
	int totalWater;
	private static final int factor = 2;
	private ArrayList<Integer> simulatorTicksList = new ArrayList<Integer>();
	private ArrayList<Integer> rateList = new ArrayList<Integer>();
		  
	public Sprinkler(){}
	
	public Sprinkler(char groupId, int locationId, int rateOfFlow) 
	{
		this.groupId = groupId;
		this.locationId = locationId;
		this.rateOfFlow = rateOfFlow;
		status = "OFF";
		simulatorTicksList.add(0);
		rateList.add(0);
	}
		  
	public String getId() 
	{
		return Character.toString(groupId) + Integer.toString(locationId);
	}
		  
	void setFlow(int rateOfFlow) 
	{
		this.rateOfFlow = rateOfFlow;
	}
	
	int getRateOfFlow(String flow) 
	{
		if(status.equals("ON") || status.equals("FORCED_ON"))
		{
			if(flow.equals("Medium"))
				return rateOfFlow;
			else if(flow.equals("Fast"))
				return rateOfFlow*factor;
			else
				return rateOfFlow/factor;
		}
		else
			return 0;
	}
		  
	public void clearWaterConsumption() 
	{
		rateList.clear();
		simulatorTicksList.clear();
		    
		rateList.add(0);
		simulatorTicksList.add(0);
	}
		  
	public int getWaterConsumption() 
	{
		totalWater = 0;
		int tempTickList1, tempTickList2, tempRate;
		tempTickList1 = tempTickList2 = tempRate = 0;    
		for(int count = 0; count < simulatorTicksList.size() - 1; count++)
		{
			tempTickList1 = simulatorTicksList.get(count+1);
			tempTickList2 = simulatorTicksList.get(count);
			tempRate = rateList.get(count);
		      
			if(tempTickList1 >= tempTickList2)
				totalWater += (tempTickList1 - tempTickList2)*tempRate;
			else
			{
				System.out.println("Sprinkler" + this + " count " + count);
		        System.out.println("Did not expect tick1 " + tempTickList1 + " to be less than tick2 " + tempTickList2);
		        System.out.println("total ticks " + simulatorTicksList.size() + " finished " + simulatorTicksList.get(simulatorTicksList.size()-1));
			}
		}
		return totalWater;
	}
		    
	public void getWaterPerDay(int[] waterPerDay) 
	{    
		for(int i = 0; i < waterPerDay.length; ++i)
			waterPerDay[i] = 0;
		    
		int currentDay = 0;
		int prevDay = 0;
		    
		int index = 1;
		while(index < simulatorTicksList.size())
		{
			int tick = simulatorTicksList.get(index);
			//Check for hour tick or we reached end of the simulator tick list
			if(tick % (24*60) == 0 || index == simulatorTicksList.size() - 1)
			{
				int dayFlow = 0;
				currentDay = index;
		        //System.out.println("Previous day index " + prevDay + " Current day index " + currentDay);
		        for(int i = prevDay; i < currentDay; ++i)
		        {
		          int tick2 = simulatorTicksList.get(i+1);
		          int tick1 = simulatorTicksList.get(i);
		          int rateOfFlow = rateList.get(i);
		          dayFlow += (tick2 - tick1)*rateOfFlow;
		          //System.out.println("tick2 " + tick2 + " tick1 " + tick1 + " rate " + rateOfFlow + " dayFlow " + dayFlow);
		        }
		        
		        waterPerDay[prevDay] = dayFlow;
		        prevDay = currentDay;
			}
			index++;
		}
	}
		  
	String viewSprinklerStatus() 
	{
		return status;
	}
		  
	@Override public String toString() 
	{
		return getId() + " " + Double.toString(rateOfFlow) + " " + status;
	}
		  
	public void setTimeLog(int rate, int simulatorTicks) 
	{
		int sizeOfRate = rateList.size();
		int prevRate   = (Integer)rateList.get(sizeOfRate-1);
		    
		if(rate != prevRate)
		{
			rateList.add(rate);
			simulatorTicksList.add(simulatorTicks);
			//System.out.println("Set Time log " + this + " rate " + rate + " simulatorTicks " + simulatorTicks);
		}
	}
		    
	public void setDayLog(int simulatorTicks) 
	{
		int size = rateList.size() - 1;
		int rateOfFlow = rateList.get(size);
		int tick = simulatorTicksList.get(size);
		if(tick != simulatorTicks)
		{
			rateList.add(rateOfFlow);
			simulatorTicksList.add(simulatorTicks);
			//System.out.println("Set Day log " + this + " rate " + rateOfFlow + " simulatorTicks " + simulatorTicks);
		}
	}
		  
	public void setStatus(String status) 
	{
		this.status = status;
	}
		  
	public String getStatus() 
	{
		return status;
	}
}