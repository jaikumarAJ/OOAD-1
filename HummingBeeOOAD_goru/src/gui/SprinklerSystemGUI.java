/**
*
* @author Ruchira De
*/
package gui;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import core.*;

public class SprinklerSystemGUI extends javax.swing.JFrame 
{
	static final long serialVersionUID = 2;
	
	private int simulatorTicks;
	private boolean simulationStarted = false;
	private Simulator simulatorTask;
 
	private SprinklerSystem sys; 
	private core.Temperature temp;
   
	private javax.swing.DefaultListModel<String> listModel = new javax.swing.DefaultListModel<String>();
 
	public SprinklerSystemGUI() 
	{
		final String fileName = "./schFile.ser";
		java.io.File f = new java.io.File(fileName);
		
		sys = new SprinklerSystem();
		sys.addSprinklers();
   
		if(f.exists())
		{
			System.out.println("DeSerializing schedules from file " + fileName);
			sys.deSerialize(fileName);
		}
		else 
		{
			System.out.println("Adding default schedules");
			sys.addDefaultSchedules();
		}
   
		temp = new Temperature();
		initComponents();
   
		// Window Listeners
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("Serializing schedules in file " + fileName);
				sys.serialize(fileName);
			} //windowClosing
		}); //addWindowLister
	}
 
	private class Simulator extends javax.swing.SwingWorker<Void, Void>
	{
		private int simulatorTicks;
		private SprinklerSystem sys;
		private javax.swing.JLabel temperatureLabel;
		private DrawPanel drawPanel;
		private ArrayList<Sprinkler> sprinklers;
		private Temperature temp;
   
		Simulator(int simulatorTicks, SprinklerSystem sys, Temperature temp, JLabel temperatureLabel, DrawPanel drawPanel) 
		{
			this.simulatorTicks = simulatorTicks;
			this.sys = sys;
			this.temperatureLabel = temperatureLabel;
			this.temp = temp;
			this.drawPanel = drawPanel;
			sprinklers = sys.getSprinklers();
		}
   
		@Override
		protected Void doInBackground() throws InterruptedException 
		{
			int maxSimulationTime = sys.getMaxSimulationTime();
     
			while(!this.isCancelled())
			{
				Thread.sleep(5);
				//Change temperature every 12 hrs = 12*60 simulation ticks
				int currentTemp = 0;
				if(simulatorTicks%(12*60) == 0)
				{
					currentTemp = temp.getRandomTemp();
				}
				else
				{
					currentTemp = temp.getCurrentTemp();
				}
				
				temperatureLabel.setText(Integer.toString(currentTemp));
				System.out.println("Simulation tick " + simulatorTicks + " Temperature " + currentTemp);
				sys.simulate(simulatorTicks, currentTemp);
       
				ArrayList<Color> colors = drawPanel.getColors();
             
				for(int count = 0; count < sprinklers.size() ; count++)
				{
					Sprinkler spr = sprinklers.get(count);
					System.out.println(spr + " " + spr.getStatus());
					java.awt.Color color;
					if (spr.getStatus() == "ON")
						color = java.awt.Color.BLUE;
					else if (spr.getStatus() == "OFF")
						color = java.awt.Color.BLACK;
					else if (spr.getStatus() == "FORCED_ON")
						color = java.awt.Color.MAGENTA;
					else 
						color = java.awt.Color.RED;
					colors.set(count, color);
				}
       
				drawPanel.repaint();                
				simulatorTicks++;
				if(simulatorTicks == maxSimulationTime)
				{
					simulatorTicks = 0;
					sys.clearWaterConsumption();
				}
			}
			return null;
		}
   
		public int simulationTicks() {
			return this.simulatorTicks;
		}
 }
 
 private void initComponents() {
       jPanel1 = new javax.swing.JPanel();
       jButton1 = new javax.swing.JButton();

       String [] day = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
       jComboBox1 = new javax.swing.JComboBox<String>(day);
       
       jLabel3 = new javax.swing.JLabel();
       jLabel4 = new javax.swing.JLabel();
       jLabel5 = new javax.swing.JLabel();

       String [] group = {"North", "South", "East", "West", "Center"};
       jComboBox2 = new javax.swing.JComboBox<String>(group);

       String [] time = new String [24];
       for(int numHour = 0; numHour < 24; numHour++)
           time[numHour] = Integer.toString(numHour + 1);
       jComboBox3 = new javax.swing.JComboBox<String>(time);

       String [] flow = {"Low", "Medium", "High"};
       jComboBox4 = new javax.swing.JComboBox<String>(flow);
       
       jLabel6 = new javax.swing.JLabel();

       String [] location = {"1", "2"};
       jComboBox5 = new javax.swing.JComboBox<String>(location);
       
       jLabel7 = new javax.swing.JLabel();
       jLabel8 = new javax.swing.JLabel();
       jButton2 = new javax.swing.JButton();
       jButton3 = new javax.swing.JButton();
       jComboBox6 = new javax.swing.JComboBox<String>();
       jLabel11 = new javax.swing.JLabel();
       jComboBox7 = new javax.swing.JComboBox<String>();
       jLabel12 = new javax.swing.JLabel();
       jComboBox8 = new javax.swing.JComboBox<String>();
       jButton4 = new javax.swing.JButton();
       jLabel13 = new javax.swing.JLabel();
       jLabel14 = new javax.swing.JLabel();
       jComboBox9 = new javax.swing.JComboBox<String>();
       jLabel15 = new javax.swing.JLabel();
       jComboBox10 = new javax.swing.JComboBox<String>();
       jLabel16 = new javax.swing.JLabel();
       jButton5 = new javax.swing.JButton();
       jLabel17 = new javax.swing.JLabel();
       
       String [] duration = {"20", "30", "40", "50", "60"};
       jComboBox11 = new javax.swing.JComboBox<String>(duration);
       
       jLabel2 = new javax.swing.JLabel();
       jLabel1 = new javax.swing.JLabel();
       jScrollPane2 = new javax.swing.JScrollPane();

       ArrayList<Schedule> scheduleList = sys.getSchedules(); //defined at top
       for(int i = 0; i < scheduleList.size(); ++i)
       {
         System.out.println("Schedule " + i + " " + scheduleList.get(i));
         listModel.addElement(scheduleList.get(i).toString());
       }
       jList1 = new javax.swing.JList<String>(listModel);
       jList1.setModel(listModel);
       
       jLabel9 = new javax.swing.JLabel();
       jLabel10 = new javax.swing.JLabel();
       jPanel2 = new DrawPanel();

       setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

       jButton1.setText("Add");
       jButton1.addActionListener(new java.awt.event.ActionListener() {
           public void actionPerformed(java.awt.event.ActionEvent evt) {
               jButton1ActionPerformed(evt);
           }
       });
       
       jLabel3.setText("Group");

       jLabel4.setText("Time");

       jLabel5.setText("Flow");
       
       jLabel6.setText("Location");

       jLabel7.setText("Sprinkler Control");

       jLabel8.setText("Group");

       jButton2.setText("Simulate");
       jButton2.addActionListener(new java.awt.event.ActionListener() {
           public void actionPerformed(java.awt.event.ActionEvent evt) {
               jButton2ActionPerformed(evt);
           }
       });

       jButton3.setText("Stop");
       jButton3.addActionListener(new java.awt.event.ActionListener() {
           public void actionPerformed(java.awt.event.ActionEvent evt) {
               jButton3ActionPerformed(evt);
           }
       });

       jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<String>(group));

       jLabel11.setText("Location");

       jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<String>(location));

       jLabel12.setText("Action");

       String [] actionInBox = {"ON", "OFF"};
       jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<String>(actionInBox));
       
       jButton4.setText("Ok");
       jButton4.addActionListener(new java.awt.event.ActionListener() {
           public void actionPerformed(java.awt.event.ActionEvent evt) {
             jButton4ActionPerformed(evt);
           }
       });

       jLabel13.setText("Temperature Control");

       jLabel14.setText("Low");

       String [] lowTemp = new String[15];
       for(int countLow = 50; countLow < 65; countLow++)
           lowTemp[countLow-50] = Integer.toString(countLow);
       
       jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<String>(lowTemp));
       
       jLabel15.setText("High");
       String [] highTemp = new String[15];
       for(int countHigh = 85; countHigh < 100; countHigh++)
           highTemp[countHigh-85] = Integer.toString(countHigh);
       jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<String>(highTemp));
       
       jButton5.setText("Remove");
       jButton5.addActionListener(new java.awt.event.ActionListener() {
           public void actionPerformed(java.awt.event.ActionEvent evt) {
               jButton5ActionPerformed(evt);
           }
       });

       jLabel17.setText("Duration (in min)");
       
       jLabel2.setText("Day");

       jLabel1.setText("Schedule");

       javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
       jPanel1.setLayout(jPanel1Layout);
       jPanel1Layout.setHorizontalGroup(
           jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
               .addContainerGap()
               .addComponent(jButton2)
               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                   .addComponent(jButton4)
                   .addComponent(jButton3))
               .addGap(34, 34, 34))
           .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
               .addGap(0, 0, Short.MAX_VALUE)
               .addComponent(jLabel2)
               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
               .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addContainerGap())
           .addGroup(jPanel1Layout.createSequentialGroup()
               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                   .addGroup(jPanel1Layout.createSequentialGroup()
                       .addContainerGap()
                       .addComponent(jLabel13))
                   .addGroup(jPanel1Layout.createSequentialGroup()
                       .addGap(37, 37, 37)
                       .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                           .addGroup(jPanel1Layout.createSequentialGroup()
                               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                   .addComponent(jLabel5)
                                   .addComponent(jLabel17)
                                   .addComponent(jLabel6)
                                   .addComponent(jLabel4))
                               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                   .addGroup(jPanel1Layout.createSequentialGroup()
                                       .addGap(6, 6, 6)
                                       .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                           .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                           .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                   .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                       .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                   .addGroup(jPanel1Layout.createSequentialGroup()
                                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                       .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                           .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                               .addComponent(jLabel3)
                               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                               .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                   .addGroup(jPanel1Layout.createSequentialGroup()
                       .addContainerGap()
                       .addComponent(jLabel16))
                   .addGroup(jPanel1Layout.createSequentialGroup()
                       .addContainerGap()
                       .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                           .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                   .addComponent(jLabel7)
                                   .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                                   .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                                   .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                                   .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING))
                               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                   .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                   .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                   .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                   .addComponent(jButton5)))
                           .addGroup(jPanel1Layout.createSequentialGroup()
                               .addComponent(jLabel14)
                               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                               .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                           .addGroup(jPanel1Layout.createSequentialGroup()
                               .addComponent(jLabel15)
                               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                               .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                   .addGroup(jPanel1Layout.createSequentialGroup()
                       .addContainerGap()
                       .addComponent(jLabel1)))
               .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
       );
       jPanel1Layout.setVerticalGroup(
           jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addGroup(jPanel1Layout.createSequentialGroup()
               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                   .addGroup(jPanel1Layout.createSequentialGroup()
                       .addGap(23, 23, 23)
                       .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                           .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                           .addComponent(jLabel2))
                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                       .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                           .addComponent(jLabel3)
                           .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                       .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                           .addComponent(jLabel6)
                           .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                       .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                           .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                           .addComponent(jLabel4)))
                   .addGroup(jPanel1Layout.createSequentialGroup()
                       .addContainerGap()
                       .addComponent(jLabel1)))
               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                   .addComponent(jLabel17)
                   .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                   .addComponent(jLabel5)
                   .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                   .addGroup(jPanel1Layout.createSequentialGroup()
                       .addGap(0, 0, Short.MAX_VALUE)
                       .addComponent(jLabel7))
                   .addGroup(jPanel1Layout.createSequentialGroup()
                       .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                           .addComponent(jButton5)
                           .addComponent(jButton1))
                       .addGap(0, 0, Short.MAX_VALUE)))
               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                   .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                   .addComponent(jLabel8))
               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                   .addComponent(jLabel11)
                   .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                   .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                   .addComponent(jLabel12))
               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
               .addComponent(jButton4)
               .addGap(18, 18, 18)
               .addComponent(jLabel13)
               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                   .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                   .addComponent(jLabel14))
               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                   .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                   .addComponent(jLabel15))
               .addGap(14, 14, 14)
               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                   .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                       .addComponent(jLabel16)
                             .addGap(48, 48, 48)))
               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                   .addComponent(jButton2)
                   .addComponent(jButton3)))
       );
       
       jScrollPane2.setViewportView(jList1);

       jLabel9.setText("Temperature");

       jLabel10.setText("65");

       javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
       jPanel2.setLayout(jPanel2Layout);
       jPanel2Layout.setHorizontalGroup(
           jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addGap(0, 407, Short.MAX_VALUE)
       );
       jPanel2Layout.setVerticalGroup(
           jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addGap(0, 528, Short.MAX_VALUE)
       );
       
       javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
       getContentPane().setLayout(layout);
       layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addGroup(layout.createSequentialGroup()
                     .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                         .addGroup(layout.createSequentialGroup()
                             .addGap(51, 51, 51)
                             .addComponent(jLabel9)
                             .addGap(18, 18, 18)
                             .addComponent(jLabel10))
                         .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                     .addGap(18, 18, 18)
                     .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                   .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                         .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                     .addContainerGap())
                                 );
       layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addGroup(layout.createSequentialGroup()
               .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                         .addGroup(layout.createSequentialGroup()
                             .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                 .addComponent(jLabel9)
                                 .addComponent(jLabel10))
                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                             .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                         .addGroup(layout.createSequentialGroup()
                             .addContainerGap(14, Short.MAX_VALUE)
                             .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                             .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                     .addContainerGap())
                               );
       pack();
   }
 
   private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
     if(simulationStarted)    
     {
       simulatorTask.cancel(true);
       simulatorTicks = simulatorTask.simulationTicks();
           simulatorTask = null;
           System.out.println("Simulation halted at " + simulatorTicks + "to add schedules");
      }
      
      System.out.println("Adding schedule");
      String day = (String)jComboBox1.getSelectedItem();
      char group = ((String)jComboBox2.getSelectedItem()).charAt(0);
      int time = Integer.parseInt((String)jComboBox3.getSelectedItem());
      int duration = Integer.parseInt((String)jComboBox11.getSelectedItem());
      String flow = (String)jComboBox4.getSelectedItem();
      int location = Integer.parseInt((String)jComboBox5.getSelectedItem());
      System.out.println(day + " " + group + location + " " + time + " " + flow);
      sys.addSchedule(day, time, duration, Character.toString(group)+Integer.toString(location), flow);
      ArrayList<Schedule> scheduleList = sys.getSchedules();
      listModel = (javax.swing.DefaultListModel<String>)jList1.getModel();
      Schedule last = scheduleList.get(scheduleList.size()-1);
      listModel.addElement(last.toString());
      
      if(simulationStarted)
      {
           simulatorTask = new Simulator(simulatorTicks, sys, temp, jLabel10, (DrawPanel)jPanel2);
           simulatorTask.execute();
      }
   }
 
 private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
   char group = ((String)jComboBox6.getSelectedItem()).charAt(0);
   int location = Integer.parseInt((String)jComboBox7.getSelectedItem());
   String actionInBox = (String)jComboBox8.getSelectedItem();
       Sprinkler updatedSprinkler = sys.findSprinkler(Character.toString(group) + Integer.toString(location));
       if(actionInBox.equals("ON"))
       {
           updatedSprinkler.setStatus("FORCED_ON");
       }
       else
       {
           updatedSprinkler.setStatus("FORCED_OFF");
       }
       System.out.println("Updated sprinkler " + updatedSprinkler);
 }
 
   private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
     simulatorTicks = 0;
     int minTemperature = Integer.parseInt((String)jComboBox9.getSelectedItem());
     int maxTemperature = Integer.parseInt((String)jComboBox10.getSelectedItem());
     
     sys.setMinTemperature(minTemperature);
     sys.setMaxTemperature(maxTemperature);
     
     simulatorTask = new Simulator(simulatorTicks, sys, temp, jLabel10, (DrawPanel)jPanel2);
     System.out.println("Initialized simulation");
     simulationStarted = true;
     this.jButton2.setEnabled(false);
     this.jButton3.setEnabled(true);
     simulatorTask.execute();
   }
 
 private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
   System.out.println("Inside Stop button");
   if(simulationStarted)
   {
     this.jButton3.setEnabled(false);
     this.jButton2.setEnabled(true);
     simulatorTask.cancel(true);
     
     DrawPanel dp = (DrawPanel)jPanel2;
     ArrayList<Color> colors = dp.getColors();
     for(int i = 0; i < colors.size(); ++i)
       colors.set(i, java.awt.Color.BLACK);
     dp.repaint();
     
     simulatorTicks = simulatorTask.simulationTicks();
     System.out.println("Simulation stopped ticks: " + simulatorTicks);
     ArrayList<Sprinkler> sprinklers = sys.getSprinklers();
     
     //Add the stop tick on each of the sprinkler
     for(int i = 0; i < sprinklers.size(); ++i)
       sprinklers.get(i).setDayLog(simulatorTicks);
     
     //String[] sprinklerId = new String[sprinklers.size()];
     int[] sprinklerId = new int[sprinklers.size()];
     int[] sprinklerWater = new int[sprinklers.size()];

     /*
     String[] dayId = new String[7];
     dayId[0] = "Sunday";
     dayId[1] = "Monday";
     dayId[2] = "Tuesday";
     dayId[3] = "Wednesday";
     dayId[4] = "Thursday";
     dayId[5] = "Friday";
     dayId[6] = "Saturday";
     */
     int[] dayId = new int[7];
     int[] waterPerDay = new int[7];
     int[] cumWaterPerDay = new int[7];
     
     for(int count = 0; count < 7; ++count)
     {
       dayId[count] = count;
       cumWaterPerDay[count] = 0;
     }
     
     int totalWater = 0;
     for(int count = 0; count < sprinklers.size(); count++)
     {
       Sprinkler spr = sprinklers.get(count);
       int waterConsumed = spr.getWaterConsumption();
       //This has to changed to Sprinkler's label
       //sprinklerId[count] = spr.getId();
       sprinklerId[count] = count;
       sprinklerWater[count] = waterConsumed;
       totalWater += waterConsumed;
       
       System.out.println("Sprinkler: " + spr + " Water consumed:" + waterConsumed);
       //get water consumed per day
       spr.getWaterPerDay(waterPerDay);
       for(int i = 0; i < 7; ++i) cumWaterPerDay[i] += waterPerDay[i];
       
       spr.clearWaterConsumption();
       spr.setStatus("OFF");
     }
     
     System.out.println("Weekly water consumed through sprinklers: " + totalWater);

     int totalWaterDay = 0;
     for(int i = 0; i < 7; ++i)
     {
       System.out.println("Day " + i + " water consumed " + cumWaterPerDay[i]);
       totalWaterDay += cumWaterPerDay[i];
     }
     System.out.println("Weekly water consumed through days: " + totalWaterDay);
     
     new DrawGraph(dayId, cumWaterPerDay, "Day Water Consumption");
     new DrawGraph(sprinklerId, sprinklerWater, "Sprinkler Water Consumption");
           
     simulatorTask = null;
     simulatorTicks = 0;
     simulationStarted = false;
   }
 }
   private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
       System.out.println("Inside Remove button");
       int indexSelected = jList1.getSelectedIndex();
       System.out.println("Selected user to remove " + indexSelected);
       ArrayList<Schedule> scheduleList = sys.getSchedules();
       if(indexSelected >= 0) 
       {    
            scheduleList.remove(indexSelected);
            listModel.remove(indexSelected);
       }
       else
              System.out.println("Select the user to remove");
   }
 
   /**
    * @param args the command line arguments
    */
   public static void main(String args[]) {
     try {
       for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
         if ("Nimbus".equals(info.getName())) {
           javax.swing.UIManager.setLookAndFeel(info.getClassName());
           break;
         }
       }
     } catch (ClassNotFoundException ex) {
       java.util.logging.Logger.getLogger(SprinklerSystemGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
     } catch (InstantiationException ex) {
       java.util.logging.Logger.getLogger(SprinklerSystemGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
     } catch (IllegalAccessException ex) {
       java.util.logging.Logger.getLogger(SprinklerSystemGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
     } catch (javax.swing.UnsupportedLookAndFeelException ex) {
       java.util.logging.Logger.getLogger(SprinklerSystemGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
     }
     
     java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
           new SprinklerSystemGUI().setVisible(true);
         }
       });
   }
 
 private javax.swing.JButton jButton1;
 private javax.swing.JButton jButton2;
 private javax.swing.JButton jButton3;
 private javax.swing.JButton jButton4;
 private javax.swing.JButton jButton5;
 private javax.swing.JComboBox<String> jComboBox1;
 private javax.swing.JComboBox<String> jComboBox10;
 private javax.swing.JComboBox<String> jComboBox11;
 private javax.swing.JComboBox<String> jComboBox2;
 private javax.swing.JComboBox<String> jComboBox3;
 private javax.swing.JComboBox<String> jComboBox4;
 private javax.swing.JComboBox<String> jComboBox5;
 private javax.swing.JComboBox<String> jComboBox6;
 private javax.swing.JComboBox<String> jComboBox7;
 private javax.swing.JComboBox<String> jComboBox8;
 private javax.swing.JComboBox<String> jComboBox9;
 private javax.swing.JLabel jLabel1;
 private javax.swing.JLabel jLabel10;
 private javax.swing.JLabel jLabel11;
 private javax.swing.JLabel jLabel12;
 private javax.swing.JLabel jLabel13;
 private javax.swing.JLabel jLabel14;
 private javax.swing.JLabel jLabel15;
 private javax.swing.JLabel jLabel16;
 private javax.swing.JLabel jLabel17;
 private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
 private javax.swing.JLabel jLabel4;
 private javax.swing.JLabel jLabel5;
 private javax.swing.JLabel jLabel6;
 private javax.swing.JLabel jLabel7;
 private javax.swing.JLabel jLabel8;
 private javax.swing.JLabel jLabel9;
 private javax.swing.JList<String> jList1;
 private javax.swing.JPanel jPanel1;
 private javax.swing.JPanel jPanel2;
 private javax.swing.JScrollPane jScrollPane2;
}