package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

public class SprinklerSystem
{
	static final int MIN_SIMULATION_TIME = 0;
	static final int MAX_SIMULATION_TIME = 7*24*60;

	private ArrayList<Schedule> schedules = new ArrayList<Schedule>();
	private ArrayList<Sprinkler> sprinklers = new ArrayList<Sprinkler>();

	private HashMap<String, Sprinkler> idToSprinkler = new HashMap<String, Sprinkler>();
	
	private int minTemperature;
	private int maxTemperature;
	
	private boolean tempInRange = true;
	
	public void addSchedule(String day, int time, int duration, String location, String flow) 
	{
		if(idToSprinkler.containsKey(location))
	      schedules.add(new Schedule(day, time, duration, idToSprinkler.get(location), flow));
	    else
	      System.out.println("Location " + location + " not valid");
	}
	    
	public ArrayList<Schedule> getSchedules() 
	{
	    return schedules;
	}
	    
	public ArrayList<Sprinkler> getSprinklers() 
	{
		return sprinklers;
	}
	  
	public Sprinkler findSprinkler(String sprinklerId) 
	{
		for(int i = 0; i < sprinklers.size(); i++)
	    {
	      Sprinkler spr = sprinklers.get(i);
	      if((spr.getId().equals(sprinklerId)))
	        return spr;
	    }
	    return null;
	}

	public void addSprinklers() 
	{
		//North Sprinklers 1-5
		sprinklers.add(new Sprinkler('N', 1, 2));
	    sprinklers.add(new Sprinkler('N', 2, 2));
	    //South Sprinklers 1-5
	    sprinklers.add(new Sprinkler('S', 1, 2));
	    sprinklers.add(new Sprinkler('S', 2, 2));
	    //East Sprinklers 1-3
	    sprinklers.add(new Sprinkler('E', 1, 2));
	    sprinklers.add(new Sprinkler('E', 2, 2));
	    //West Sprinklers 1-3
	    sprinklers.add(new Sprinkler('W', 1, 2));
	    sprinklers.add(new Sprinkler('W', 2, 2));
	    //Central Sprinklers 1-4
	    sprinklers.add(new Sprinkler('C', 1, 2));
	    sprinklers.add(new Sprinkler('C', 2, 2));
	    for(int i=0; i<sprinklers.size(); i++)
	    {
	      String location = sprinklers.get(i).getId();
	      idToSprinkler.put(location, sprinklers.get(i));
	    }
	  }
	  
	  public void setMinTemperature(int minTemperature) 
	  {
	    this.minTemperature = minTemperature;
	  }
	  
	  public void setMaxTemperature(int maxTemperature) 
	  {
	    this.maxTemperature = maxTemperature;
	  }
	  
	  int getMinTemperature() 
	  {
	    return minTemperature;
	  }
	  
	  int getMaxTemperature() 
	  {
	    return maxTemperature;
	  }
	    
	  public int getMaxSimulationTime() 
	  {
	    return MAX_SIMULATION_TIME;
	  }
	    
	  void setAllSprinklersFast(int simulatorTicks) 
	  {
		for(int count = 0; count < sprinklers.size(); count++)
	    {
	      Sprinkler spr = sprinklers.get(count);
	      spr.setStatus("ON");
	      int rate = spr.getRateOfFlow("Fast");
	      spr.setTimeLog(rate, simulatorTicks);
	      System.out.println("Sprinkler " + sprinklers.get(count) + " turned ON");
	    }
	  }
	  
	  void setAllSprinklersOFF(int simulatorTicks) 
	  {
		  for(int count = 0; count < sprinklers.size(); count++)
		  {
			  Sprinkler spr = sprinklers.get(count);
			  spr.setStatus("OFF");
			  spr.setTimeLog(0, simulatorTicks);
			  System.out.println("Sprinkler " + sprinklers.get(count) + " turned OFF");
		  }
	  }
	  
	  public void clearWaterConsumption() 
	  {
	    for(int count = 0; count < sprinklers.size(); ++count)
	    {
	      Sprinkler spr = sprinklers.get(count);
	      spr.clearWaterConsumption();
	    }
	  }
	  
	  void simulate() 
	  {      
	    Temperature temperature = new Temperature();//Random temperature generator
	    for(int i=0; i < MAX_SIMULATION_TIME; i++)
	    {
	      int currentTemp = temperature.getRandomTemp();
	      simulate(i, currentTemp);
	    }
	  }
	    
	  public void simulate(int simulatorTicks, int currentTemp) 
	  {
	    if ((currentTemp > minTemperature) && (currentTemp < maxTemperature))
	    {
	      for(int numSpr = 0; numSpr < sprinklers.size(); numSpr++)
	      {
	        Sprinkler sprinkler = sprinklers.get(numSpr);
	        String status = sprinkler.getStatus();
	        if(sprinkler.getStatus().equals("ON") && !tempInRange)
	        {
	          sprinkler.setStatus("OFF");
	        }
	        if(status.equals("FORCED_ON"))
	        {
	          int rate = sprinkler.getRateOfFlow("Medium");
	          sprinkler.setTimeLog(simulatorTicks,rate);
	        }
	        if(status.equals("FORCED_OFF"))
	        {
	          sprinkler.setTimeLog(simulatorTicks, 0);
	        }
	      }
	      tempInRange = true;
	      
	      for(int count=0; count < schedules.size(); count ++)
	      {
	        Schedule sch = schedules.get(count);
	        Sprinkler spr = sch.getSprinkler();
	        String status = spr.getStatus();
	        
	        if(!(status.equals("FORCED_OFF") || status.equals("FORCED_ON")))
	        {
	          int endTime = sch.getTime() + sch.getDuration();
	          if(simulatorTicks >= sch.getTime() && simulatorTicks < endTime)
	          {
	            spr.setStatus("ON");
	            int sprinklerRate = spr.getRateOfFlow(sch.getFlow());
	            spr.setTimeLog(sprinklerRate, simulatorTicks);
	          }
	          if(simulatorTicks == endTime)
	          {
	            spr.setStatus("OFF");
	            spr.setTimeLog(0, simulatorTicks);
	          }
	        }
	      }
	    }
	    else if(currentTemp <= minTemperature)
	    {
	      setAllSprinklersOFF(simulatorTicks);
	    }
	    else if(currentTemp >= maxTemperature)
	    {
	      setAllSprinklersFast(simulatorTicks);
	      tempInRange = false;
	    }
	    
	    if(simulatorTicks > 0 && simulatorTicks%(24*60) == 0)
	      for(int count = 0; count < sprinklers.size(); count++)
	      {
	        Sprinkler spr = sprinklers.get(count);
	        spr.setDayLog(simulatorTicks);
	      }
	  }
	  
	  public void addDefaultSchedules() 
	  {
	    addSchedule("Sunday", 10, 40, "N1", "Medium");
	    addSchedule("Sunday", 10, 40, "N2", "Medium");
	    addSchedule("Sunday", 16, 60, "E1", "Medium");
	    addSchedule("Sunday", 16, 60, "E2", "Medium");
	    addSchedule("Monday", 2, 20, "W1", "Medium");
	    addSchedule("Monday", 2, 60, "W2", "Medium");
	    addSchedule("Tuesday", 7, 30, "S2", "Medium");
	    addSchedule("Tuesday", 13, 30, "E1", "Medium");
	    addSchedule("Tuesday", 13, 30, "E2", "Medium");
	    addSchedule("Tuesday", 13, 30, "S1", "Medium");
	    addSchedule("Tuesday", 13, 30, "S2", "Medium");
	    addSchedule("Wednesday", 11, 40, "E3", "Medium");
	    addSchedule("Wednesday", 420, 60, "A3", "Medium");   
	  }
	  
	  void viewSchedules() 
	  {
	    System.out.println("Inside view schedules");
	    for(int i = 0; i < schedules.size(); i++)
	      System.out.println(schedules.get(i));
	  }
	  
	  public void serialize(String fileName)
	  {
	    FileOutputStream fOut = null;
	    ObjectOutputStream out = null;
	    try 
	    {
	      fOut = new FileOutputStream(fileName); //wrapping
	      out = new ObjectOutputStream(fOut);
	      
	      out.writeObject(schedules.size());
	      for(int i = 0; i < schedules.size(); ++i)
	        out.writeObject(schedules.get(i));
	      out.close();
	    }
	    catch (IOException ex)
	    {
	      ex.printStackTrace();
	    }
	  }
	  
	  //returns how many schedules are read from serialized file
	  public int deSerialize(String fileName)
	  {
	    Schedule schOb = null;
	    FileInputStream fis = null;
	    ObjectInputStream fin = null;
	    Integer size = 0;
	    try 
	    {
	      fis = new FileInputStream(fileName);
	      fin = new ObjectInputStream(fis);
	      
	      //read the size of arraylist
	      size = (Integer)fin.readObject();
	      
	      for(int i = 0; i < size; ++i)
	      {
	        schOb = (Schedule)fin.readObject(); //casting it to the right type
	        String sprinklerId = schOb.getSprinklerId();
	        Sprinkler spr = findSprinkler(sprinklerId);
	        schOb.setSprinkler(spr);
	        System.out.println("Deserialized schedule " + i + " " + schOb);
	        schedules.add(schOb);
	      }
	      fin.close();
	    }
	    catch (IOException ex) {ex.printStackTrace();}
	    catch (ClassNotFoundException e) {e.printStackTrace();}
	    return size;
	  }
	  
	  public static void main(String[] args) throws IOException
	  {
	    SprinklerSystem system = new SprinklerSystem();
	    system.addSprinklers();
	    system.addDefaultSchedules();
	    
	    system.serialize("./schFile.ser");
	    
	    SprinklerSystem system1 = new SprinklerSystem();
	    system1.addSprinklers();
	    system1.deSerialize("./schFile.ser");
	    
	    String CurLine = "";
	    System.out.println("Do you want to start Sprinkler? Yes/No: ");
	    InputStreamReader converter = new InputStreamReader(System.in);
	    BufferedReader in = new BufferedReader(converter);
	    while(!(CurLine.equals("No")))
	    {
	      CurLine = in.readLine();
	      if(!(CurLine.equals("No")))
	        system.simulate();
	    }
	    
	    //Display the schedules stored in the arraylist
	    system.viewSchedules();
	  }
}