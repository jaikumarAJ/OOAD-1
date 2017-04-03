package ui.project;

import goru.project.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import javax.swing.*;

@SuppressWarnings("serial")
public class App extends JFrame {
	
	HummingBeeSystem hbObj;
	private Container contentPane;
	private HBSystem sys;
	private Timer timer;
	private int cnt = -1;
	private boolean homeScreenActive = false, mapScreenActive = false;
	private int waterConsumption = 0;
	private int[] localTmpArr;
	private boolean tempExtDone;
	private int day,week;
	private JLabel timeL,tempL,dayL;
	// An array of sectors (N,S,E,W)
	private JLabel[] arr;
	private ActionListener watcher;
	private FileWriter wtrLog;
	private String[] days;
	private int upperTemp,lowerTemp;
	private GardenMap gardenMap;
	ArrayList<String> schedulesTemp = new ArrayList<String>();
	
	/*
	 * HomeScreen buttons
	 */
	JButton turnNF,modSched,trgrTemp,shwGraph,shwMap;
	
	public App() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		// Initialize key components
		
		super("HummingBee Sprinkler Systems");
		sys = new HBSystem();
		// hbObj = new HummingBeeSystem();
		localTmpArr = new int[4];
		tempExtDone = false;
		contentPane = getContentPane();
		upperTemp = 93;
		lowerTemp = 50;
		day = 0; week = 0;
		days = new String[7];
		days[0] = "Sunday";
		days[1] = "Monday";
		days[2] = "Tuesday";
		days[3] = "Wednesday";
		days[4] = "Thursday";
		days[5] = "Friday";
		days[6] = "Saturday";
		
		schedulesTemp.add("MonSch");
		schedulesTemp.add("TueSch");
		schedulesTemp.add("WedSch");
		schedulesTemp.add("ThuSch");
		schedulesTemp.add("FriSch");
		schedulesTemp.add("SatSch");
		schedulesTemp.add("SunSch");
		/*
		 * Populate the bounds HashMap
		 */
		
		for(int i=0;i<7;i++) {
			sys.setBounds("North-"+days[i], new Schedule(10,40));
			sys.setBounds("South-"+days[i], new Schedule(10,40));
			sys.setBounds("East-"+days[i], new Schedule(10,40));
			sys.setBounds("West-"+days[i], new Schedule(10,40));
		}
		
		/*
		 * Open files for logging data
		 */
		
		try {
			PrintWriter pw = new PrintWriter("waterlog.txt");
			pw.close();
			wtrLog = new FileWriter("waterlog.txt",true);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		watcher = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cnt++;
				int temp = sys.getTemperature(cnt);
				
				/*
				 * Update the top panel on home screen.
				 */
				timeL.setText("| Time " + Integer.toString(cnt/10) + ":" + Integer.toString(cnt%10) + "0 ");
				tempL.setText(" |  Temperature " + Integer.toString(temp));
				dayL.setText(" | " + days[day] + " ( Day " + Integer.toString(week*7 + day +1) + " ) |");
				
				if(cnt == 240) {
					// The day is over; log and clean up ...
					day++;
					if(day == 7) {
						week++;
						day = 0;
					}
					logState();
					tempExtDone = false;
					cnt = 1;
					waterConsumption = 0;
					for(int i=0;i<4;i++) localTmpArr[i] = 0;
				}
				
				
				
				if(temp > upperTemp && !tempExtDone) {
					tempExtDone = true;
					for(int i=0;i<4;i++) {
						localTmpArr[i] = sys.getTempExtValue(arr[i].getName());
					}
					
				}
				
				if(true) {
					boolean[] f = new boolean[4];
					for(int i=0;i<4;i++) f[i] = sys.getSprinklerGrpStatus(arr[i].getName());
					for(int i=0;i<4;i++) {
						if(!f[i] || temp <= lowerTemp) {
							if(homeScreenActive) sprinklerToggle(arr[i],false);
						}
						else {
							if(homeScreenActive) sprinklerToggle(arr[i],true);
							String sector = arr[i].getName();
							// consider temperature compensation, if any
							int tmpcmp = localTmpArr[i];
							if(sys.getBounds(sector+"-"+days[day]).getStart() <= cnt && cnt <= sys.getBounds(sector+"-"+days[day]).getEnd() || tmpcmp != 0) {
								if(homeScreenActive) runningToggle(arr[i],true);
								waterConsumption += sys.getSprinklerGrp(arr[i].getName()).getWaterConsumption();
								if(tmpcmp != 0) localTmpArr[i]--;
							}
							else runningToggle(arr[i],false);
						}
					}
					
				}
			}
		};
		
		/*
		 * Start the timer. Note that the time represents time for the scope of the entire
		 * application; ie, for the system as well as the GUI.
		 */
		
		timer = new Timer(100,watcher);
		timer.start();
		
		showHomeScreen();
	}
	
	private void showHomeScreen() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

		homeScreenActive = true;
		contentPane.removeAll();
		
		JPanel homeDisplay = new JPanel(new BorderLayout());
		JPanel upper,lower;
		
		upper = new JPanel(new GridLayout(1,2)); //
		arr = new JLabel[4];
		
		
		arr[0] = new JLabel("North",SwingConstants.CENTER);
		arr[0].setName("North");
		arr[1] = new JLabel("South",SwingConstants.CENTER);
		arr[1].setName("South");
		arr[2] = new JLabel("East",SwingConstants.CENTER);
		arr[2].setName("East");
		arr[3] = new JLabel("West",SwingConstants.CENTER);
		arr[3].setName("West");
		

		
		JPanel setSchedulePanel = new JPanel(new GridLayout(0,2));
		upper.add(setSchedulePanel);
		
		JLabel controlLabel = new JLabel("CONTROL", SwingConstants.RIGHT);
		JLabel panelPanel = new JLabel(" PANEL", SwingConstants.LEFT);
		setSchedulePanel.add(controlLabel);
		setSchedulePanel.add(panelPanel);

		JLabel emptyLabel1 =  new JLabel();
		JLabel emptyLabel2 = new JLabel();
		setSchedulePanel.add(emptyLabel1);
		setSchedulePanel.add(emptyLabel2);
		
		JButton runNow = new JButton("Run All Sprinklers Now");
		setSchedulePanel.add(runNow);
		runNow.addActionListener((ActionEvent e) -> {
			for(int i=0;i<4;i++) sys.setSprinklerGrpStatus(arr[i].getName(), true);
			for(int i=0;i<4;i++) runningToggle(arr[i], true);
			try {
				showHomeScreen();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		
		JButton stopNow = new JButton("Stop All Sprinklers");
		setSchedulePanel.add(stopNow);
		stopNow.addActionListener((ActionEvent e) -> {
			
			// STOP THE SPRINKLERS AND CALCULATE
			try {
				showHomeScreen();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		JLabel emptyLabel4 = new JLabel();
		JLabel emptyLabel5 = new JLabel();

		setSchedulePanel.add(emptyLabel4);
		setSchedulePanel.add(emptyLabel5);
		
		JLabel createSchLabel = new JLabel("    Schedule Name ", SwingConstants.LEFT);
		setSchedulePanel.add(createSchLabel);
		
		JTextField schNameText = new JTextField("");
		setSchedulePanel.add(schNameText);
		
		JLabel selZonesLabel = new JLabel("   Select Zones  : ", SwingConstants.LEFT);
		setSchedulePanel.add(selZonesLabel);
		
		JLabel emptyLabel6 = new JLabel();
		setSchedulePanel.add(emptyLabel6);
				
		JCheckBox[] selZoneChkBox = new JCheckBox[4];
		for (int i = 0; i < arr.length; i++) {
			selZoneChkBox[i] = new JCheckBox(arr[i].getName());
			setSchedulePanel.add(selZoneChkBox[i]);
		}
		
		JLabel durationLabel = new JLabel("    Duration      ", SwingConstants.LEFT);
		setSchedulePanel.add(durationLabel);
		
		JTextField durationText = new JTextField("");
		setSchedulePanel.add(durationText);
		
		JLabel dayLabel = new JLabel("    Day           ", SwingConstants.LEFT);
		setSchedulePanel.add(dayLabel);
		
		JComboBox<String> dayComboBox = new JComboBox<String>();
		setSchedulePanel.add(dayComboBox);
		dayComboBox.addItem("MONDAY");
		dayComboBox.addItem("TUESDAY");
		dayComboBox.addItem("WEDNESDAY");
		dayComboBox.addItem("THURSDAY");
		dayComboBox.addItem("FRIDAY");
		dayComboBox.addItem("SATURDAY");
		dayComboBox.addItem("SUNDAY");
		dayComboBox.setSelectedIndex(0);
		
		JLabel timeLabel = new JLabel("    Time          ", SwingConstants.LEFT);
		setSchedulePanel.add(timeLabel);
		
		JComboBox<String> timeComboBox = new JComboBox<String>();
		setSchedulePanel.add(timeComboBox);
		for (int i = 1; i <= 24; i++) {
			String s = i + ":00";
			timeComboBox.addItem(s);
		}
		
		JLabel emptyLabel7 = new JLabel();
		setSchedulePanel.add(emptyLabel7);
		
		JButton addSchBtn = new JButton(" Add Schedule ");
		addSchBtn.addActionListener((ActionEvent e) -> {
			schedulesTemp.add(schNameText.getText());
			if (schNameText.getText().equals("NewSch") || schNameText.getText().equals("ImmSch")) {
				try {
					Thread.sleep(3000); // waiting for three seconds
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for(int i=0;i<4;i++) sys.setSprinklerGrpStatus(arr[i].getName(), true);
				for(int i=0;i<4;i++) runningToggle(arr[i], true);
				try {
					showHomeScreen();
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			JOptionPane.showMessageDialog(null, "Successfully added " + "\""+schNameText.getText()+"\"" + " schedule");
			try {
				showHomeScreen();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		setSchedulePanel.add(addSchBtn);
		
		JLabel emptyLabel8 = new JLabel();
		setSchedulePanel.add(emptyLabel8);	
		JLabel emptyLabel9 = new JLabel();
		setSchedulePanel.add(emptyLabel9);
		
		JComboBox<String> schComboBox = new JComboBox<String>();
		setSchedulePanel.add(schComboBox);
		for (int i = 0; i < schedulesTemp.size(); i++) {
			schComboBox.addItem(schedulesTemp.get(i));
		}
		
		JButton delSchBtn = new JButton(" Remove Schedule ");
		setSchedulePanel.add(delSchBtn);
		delSchBtn.addActionListener((ActionEvent e) -> {
			String str = (String)schComboBox.getSelectedItem();
			schedulesTemp.remove(str);
			JOptionPane.showMessageDialog(null, "Successfully removed " + "\""+str+"\"" + " Schedule");

			try {
				showHomeScreen();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		JLabel emptyLabel10 = new JLabel();
		setSchedulePanel.add(emptyLabel10);	
		JLabel emptyLabel11 = new JLabel();
		setSchedulePanel.add(emptyLabel11);
		
		
		JPanel sprinkerMapPanel = new JPanel(new GridLayout(2,2));
		upper.add(sprinkerMapPanel);
		
		
		for(int i=0;i<4;i++) initSprinklerPanels(arr[i], sprinkerMapPanel);
		
		homeDisplay.add(upper,BorderLayout.CENTER);
		
		lower = new JPanel(new FlowLayout());
		
		
		JCheckBox enable = new JCheckBox("Enable");
		enable.setSelected(true);
		enable.addActionListener((ActionEvent e) -> {
			if(enable.isSelected()) {
				timer.start();
				turnNF.setEnabled(true);
				modSched.setEnabled(true);
				shwMap.setEnabled(true);
				shwGraph.setEnabled(true);
				trgrTemp.setEnabled(true);
			} else {
				timer.stop();
				turnNF.setEnabled(false);
				modSched.setEnabled(false);
				shwMap.setEnabled(false);
				shwGraph.setEnabled(false);
				trgrTemp.setEnabled(false);
			}
		});
		lower.add(enable);
		
		turnNF = new JButton("Group ON/OFF");
		turnNF.addActionListener((ActionEvent e) -> {
			homeScreenActive = false;
			showNFScreen();
		});
		lower.add(turnNF);
		
		
		shwMap = new JButton("Show Map");
		shwMap.addActionListener((ActionEvent e) -> {
			homeScreenActive = false;
			showMap();
		});
		lower.add(shwMap);
		
		/*
		modSched = new JButton("Set schedule");
		modSched.addActionListener((ActionEvent e) -> {
			homeScreenActive = false;
			showScheduleScreen();
		});
		lower.add(modSched);
		*/
		
		trgrTemp = new JButton("Temperature (+/-)");
		trgrTemp.addActionListener((ActionEvent e) -> {
			homeScreenActive = false;
			showChangeTempScreen();
		});
		lower.add(trgrTemp);
		
		shwGraph = new JButton("Show Graph");
		shwGraph.addActionListener((ActionEvent e) -> {
			homeScreenActive = false;
			showWaterConsumption();
		});
		lower.add(shwGraph);
		
		JPanel top = new JPanel(new FlowLayout());
		timeL = new JLabel(Integer.toString(cnt));
		tempL = new JLabel("NA");
		dayL = new JLabel(Integer.toString(day));
		top.add(timeL);
		top.add(tempL);
		top.add(dayL);
		
		homeDisplay.add(top,BorderLayout.NORTH);
		homeDisplay.add(lower,BorderLayout.SOUTH);
		contentPane.add(homeDisplay);
		paint();
		
	}
	
	private void showNFScreen() {
		contentPane.removeAll();
		
		JPanel display = new JPanel(new GridLayout(2,0));
		JPanel upper,lower;
		
		upper = new JPanel(new BorderLayout());
		JLabel text = new JLabel("Select the required sectors, sectors not selected will be turned OFF",SwingConstants.CENTER);
		upper.add(text,BorderLayout.CENTER);
		display.add(upper);
		
		lower = new JPanel(new GridLayout(4,2));
		JCheckBox[] btnArr = new JCheckBox[4];
		
		for(int i=0;i<4;i++) {
			btnArr[i] = new JCheckBox(arr[i].getName());
			lower.add(btnArr[i]);
		}
		
		JButton turnON = new JButton("Turn ON");
		
		turnON.addActionListener((ActionEvent e) -> {
			for(int i=0;i<4;i++) sys.setSprinklerGrpStatus(arr[i].getName(),btnArr[i].isSelected());
			JOptionPane.showMessageDialog(null, "Changes successfully recorded.");
			try {
				showHomeScreen();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		lower.add(turnON);
		display.add(lower);
		
		contentPane.add(display);
		paint();
	}
	
	private void showMap() {
		mapScreenActive = true;
		contentPane.removeAll();
		
		JPanel d1 = new JPanel(new BorderLayout());
		
		gardenMap = new GardenMap();
		d1.add(gardenMap,BorderLayout.CENTER);
		
		JPanel d2 = new JPanel(new FlowLayout());
		JComboBox<String> cb = new JComboBox<String>();
		
		cb.addItem("N1");
		cb.addItem("N2");
		cb.addItem("S1");
		cb.addItem("S2");
		cb.addItem("E1");
		cb.addItem("E2");
		cb.addItem("W1");
		cb.addItem("W2");
		
		cb.addActionListener((ActionEvent e) -> {
			String s = (String) cb.getSelectedItem();
			char[] a = s.toCharArray();
			String sect;
			if(a[0] == 'N') {
				sect = "North";
			} else if(a[0] == 'S') {
				sect = "South";
			} else if(a[0] == 'E') {
				sect = "East";
			} else sect = "West";
			int ix = Integer.parseInt(Character.toString(a[1]))-1;
			boolean st = sys.getSprinklerGrp(sect).getIndiStatus(ix);
			sys.getSprinklerGrp(sect).setIndiStatus(ix, !st);
			gardenMap.repaint();
		});
		
		d2.add(new JLabel("Toggle: "));
		d2.add(cb);
		
		JButton done = new JButton("Done");
		done.addActionListener((ActionEvent e) -> {
			try {
				mapScreenActive = false;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				showHomeScreen();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		d2.add(done);
		
		d1.add(d2,BorderLayout.SOUTH);
		contentPane.add(d1);
		paint();
		
	}
	
	private class GardenMap extends JPanel {
		
		private Graphics2D g;
		
		public GardenMap() {
			this.setBorder(BorderFactory.createTitledBorder("Sprinkler Layout"));
		}
		
		@Override
		public void paintComponent(Graphics gr) {
			g = (Graphics2D) gr;
			
			ArrayList<SprinklerGroup> a = new ArrayList<SprinklerGroup>();
			for(int i=0;i<4;i++) a.add(sys.getSprinklerGrp(arr[i].getName()));
			
			boolean f = a.get(0).getIndiStatus(0);
			Color cl;
			
			if(a.get(0).getIndiStatus(0)) cl = new Color(0,255,0); else cl = new Color(255,0,0);
			g.setColor(cl);
			g.fillOval(100, 100, 50, 50);
			g.setColor(Color.BLACK);
			g.drawString(a.get(0).getSectorName()+"1", 100, 100);
			
			if(a.get(0).getIndiStatus(1)) cl = new Color(0,255,0); else cl = new Color(255,0,0);
			g.setColor(cl);
			g.fillOval(500, 100, 50, 50);
			g.setColor(Color.BLACK);
			g.drawString(a.get(0).getSectorName()+"2", 500, 100);
			
			
			if(a.get(1).getIndiStatus(0)) cl = new Color(0,255,0); else cl = new Color(255,0,0);
			g.setColor(cl);
			g.fillOval(250, 350, 50, 50);
			g.setColor(Color.BLACK);
			g.drawString(a.get(1).getSectorName()+"1", 250, 350);
			
			if(a.get(1).getIndiStatus(1)) cl = new Color(0,255,0); else cl = new Color(255,0,0);
			g.setColor(cl);
			g.fillOval(500, 400, 50, 50);
			g.setColor(Color.BLACK);
			g.drawString(a.get(1).getSectorName()+"2", 500, 400);
			
			if(a.get(2).getIndiStatus(0)) cl = new Color(0,255,0); else cl = new Color(255,0,0);
			g.setColor(cl);
			g.fillOval(700, 200, 50, 50);
			g.setColor(Color.BLACK);
			g.drawString(a.get(2).getSectorName()+"1", 700, 200);
			
			if(a.get(2).getIndiStatus(1)) cl = new Color(0,255,0); else cl = new Color(255,0,0);
			g.setColor(cl);
			g.fillOval(700, 400, 50, 50);
			g.setColor(Color.BLACK);
			g.drawString(a.get(2).getSectorName()+"2", 700, 400);
			
			if(a.get(3).getIndiStatus(0)) cl = new Color(0,255,0); else cl = new Color(255,0,0);
			g.setColor(cl);
			g.fillOval(70,200, 50, 50);
			g.setColor(Color.BLACK);
			g.drawString(a.get(3).getSectorName()+"1", 70, 200);
			
			if(a.get(3).getIndiStatus(1)) cl = new Color(0,255,0); else cl = new Color(255,0,0);
			g.setColor(cl);
			g.fillOval(70,400, 50, 50);
			g.setColor(Color.BLACK);
			g.drawString(a.get(3).getSectorName()+"2", 70, 400);
			
		}
		
	}
	
 	private void showScheduleScreen() {
		contentPane.removeAll();
		
		JPanel display = new JPanel(new GridLayout(2,0));
		JPanel upper,lower;
		
		upper = new JPanel(new BorderLayout());
		JLabel text = new JLabel("<html>Enter the new schedule in the appropriate text boxes in the format (StartHour-EndHour) without the braces in 24 hour format; for example <h1><b>1-5</b></h1> or <h1><b> 14-17 </b></h1><b>"
				+ "If a text field is left unchanged no change will be incorporated for the corresponding sector.</b>"
				+ "The current schedule is shown below.</html>",SwingConstants.CENTER);
		upper.add(text,BorderLayout.CENTER);
		display.add(upper);
		
		lower = new JPanel(new GridLayout(4,2));
		JTextField[] tfArr = new JTextField[4];
		
		for(int i=0;i<4;i++) {
			tfArr[i] = new JTextField();
			tfArr[i].setText(arr[i].getName() + " " + sys.getBounds(arr[i].getName()+"-"+days[day]));
			lower.add(tfArr[i]);
		}
		
		
		JComboBox<String> dayCB = new JComboBox<String>();
		for(int i=0;i<7;i++) {
			dayCB.addItem(days[i]);
		}
		dayCB.setSelectedIndex(day);
		
		dayCB.addActionListener((ActionEvent e) -> {
			JOptionPane.showMessageDialog(null,"Displaying current schedule for "+(String)dayCB.getSelectedItem());
			for(int i=0;i<4;i++) tfArr[i].setText(arr[i].getName() + " " + sys.getBounds(arr[i].getName()+"-"+(String)dayCB.getSelectedItem()));
		});
		lower.add(dayCB);
		
		JButton submit = new JButton("Submit");
		
		submit.addActionListener((ActionEvent e) -> {
			for(int i=0;i<4;i++) {
				String inp = tfArr[i].getText();
				if(inp.equals(arr[i].getName())) {}
				else {
					boolean f = false;
					int a=0,b=0;
					try {
						String[] tkn = inp.split("-");
						a = Integer.parseInt(tkn[0]);
						b = Integer.parseInt(tkn[1]);
						if(a > b) {
							int x = a;
							a = b;
							b = x;
						}
						f = true;
					} catch(Exception ex) {System.out.println("Invalid input on "+arr[i].getName());}
					if(f) sys.setBounds(arr[i].getName()+"-"+dayCB.getSelectedItem(), new Schedule(a*10,b*10));
				}
			}
			JOptionPane.showMessageDialog(null, "Changes successfully recorded.");
			try {
				showHomeScreen();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		lower.add(submit);
		display.add(lower);
		
		contentPane.add(display);
		paint();
	}
	
	private void showChangeTempScreen() {
		int i = 0;
		contentPane.removeAll();
		
		JPanel display = new JPanel(new GridLayout(0,2));
		
		JLabel emptyLabel21 = new JLabel("");
		display.add(emptyLabel21);
		JLabel emptyLabel22 = new JLabel("");
		display.add(emptyLabel22);
		JLabel emptyLabel23 = new JLabel("");
		display.add(emptyLabel23);
		JLabel emptyLabel24 = new JLabel("");
		display.add(emptyLabel24);
		
		display.add(new JLabel("Change the required temperatures",SwingConstants.RIGHT));
		
		JLabel emptyLabel1 = new JLabel("");
		display.add(emptyLabel1);
		JLabel emptyLabel2 = new JLabel("");
		display.add(emptyLabel2);
		JLabel emptyLabel3 = new JLabel("");
		display.add(emptyLabel3);
		JLabel emptyLabel4 = new JLabel("");
		display.add(emptyLabel4);
		JLabel emptyLabel5 = new JLabel("");
		display.add(emptyLabel5);
		
		
		JLabel highTempLabel = new JLabel("Highest Temperature : ", SwingConstants.CENTER);
		display.add(highTempLabel);
		
		JTextField upper = new JTextField();
		display.add(upper);
		
		JLabel lowTempLabel = new JLabel("Lowest Temperature : ", SwingConstants.CENTER);
		display.add(lowTempLabel);
		
		JTextField lower = new JTextField();
		display.add(lower);
		
		JLabel emptyLabel6 = new JLabel("");
		display.add(emptyLabel6);
		JLabel emptyLabel7 = new JLabel("");
		display.add(emptyLabel7);
		JLabel emptyLabel8 = new JLabel("");
		display.add(emptyLabel8);
		JLabel emptyLabel9 = new JLabel("");
		display.add(emptyLabel9);
		JLabel emptyLabel10 = new JLabel("");
		display.add(emptyLabel10);
		
		JButton submit = new JButton("Submit");
		submit.addActionListener((ActionEvent e) -> {
			try {
				lowerTemp = Integer.parseInt(lower.getText());
				upperTemp = Integer.parseInt(upper.getText());
			} catch(Exception ex) {
				System.out.println("Cant't parse given string");
			}
			try {
				showHomeScreen();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		display.add(submit);

		JLabel emptyLabel11 = new JLabel("");
		display.add(emptyLabel11);
		JLabel emptyLabel12 = new JLabel("");
		display.add(emptyLabel12);
		JLabel emptyLabel13 = new JLabel("");
		display.add(emptyLabel13);
		JLabel emptyLabel14 = new JLabel("");
		display.add(emptyLabel14);

		
		submit.setPreferredSize(new Dimension(100,200));
		
		contentPane.add(display);
		paint();
	}
	
	private void showWaterConsumption() {
		contentPane.removeAll();
		JPanel display = new JPanel(new BorderLayout());
		
		ShowGraph g = new ShowGraph();
		display.add(g,BorderLayout.CENTER);
		
		JButton done = new JButton("Done");
		done.addActionListener((ActionEvent e) -> {
			try {
				showHomeScreen();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		display.add(done,BorderLayout.SOUTH);
		contentPane.add(display);
		paint();
	}
	
	private class ShowGraph extends JPanel {
		
		private int[] plot;
		private int pivot;
		
		public ShowGraph() {
			plot = new int[7];
			Scanner sc = null;
			try {
				sc = new Scanner(new FileReader("waterlog.txt"));
			} catch(Exception e) {e.printStackTrace();}
			int x = 0;
			int curr_entry = week*7 + day;
			System.out.println(curr_entry);
			while(x < curr_entry) {
				int amt = Integer.parseInt(sc.nextLine());
				plot[x%7] = amt;
				x++;
				// System.out.println(x);
			}
			pivot = x % 7;
			this.setBackground(new Color(160,160,160));
			this.setBorder(BorderFactory.createTitledBorder("Water consumption for past 6 days"));
		}
		
		@Override
		public void paintComponent(Graphics gr) {
			Graphics2D g = (Graphics2D) gr;
			
			int x = 100, y = 75;
			int inc = 0;
			int st = (pivot+1) % 7;
			
			g.setColor(new Color(51,51,255));
			
			for(int i = st; i != pivot;i = (i+1)%7) {
				g.fillRect(x, y + inc, Math.max(1,plot[i]), 50);
				g.drawString(Integer.toString(plot[i])+" (Previous " + days[i] + ")",x+Math.max(1,plot[i])+10,y+inc+20);
				inc += 65;
			}
		}
	}
	
	
	
	private void paint() {
		setSize(800,600);
		setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setVisible(true);
	}
	
	private void logState() {
		// write
		try {
			wtrLog.write(Integer.toString(waterConsumption)+"\n");
			wtrLog.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initSprinklerPanels(JLabel l,JPanel p) {
		boolean f = sys.getSprinklerGrpStatus(l.getText());
		l.setOpaque(true);
		l.setBorder(BorderFactory.createLineBorder(new Color(153,204,255), 5));
		if(f) l.setBackground(new Color(153,255,153));
		else l.setBackground(new Color(255,0,0));
		p.add(l);
	}
	
	private void sprinklerToggle(JLabel l,boolean f) {
		if(!f) l.setText(l.getName());
		l.setOpaque(true);
		if(!f) l.setBackground(new Color(255,0,0));
		else l.setBackground(new Color(153,255,153));
		l.setBorder(BorderFactory.createLineBorder(new Color(153,204,255), 5));
		l.repaint();
	}
	
	public void runningToggle(JLabel l,boolean f) {
		l.setOpaque(true);
		assert(l.getBackground().equals(new Color(153,255,153)));
		if(f) l.setText(l.getName() + " sprinkler running ...");
		else l.setText(l.getName());
	}
	
	public static void render() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		App app = new App();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
	           public void run() {
	               try {
					render();
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           }
	       });
	}
}
