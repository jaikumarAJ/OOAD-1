package goru.assign3;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
/**
 * @author SwathiGoru
 */
public class DrawBarChart extends JPanel {
	
	
	private static final long serialVersionUID = 1L;
	private String[][] resultsArray;
	private int panelWidth;
	private int panelHeight;
	private Color[] colorList = new Color [] { Color.BLUE, Color.YELLOW, Color.RED, Color.GREEN };
	
	
	public DrawBarChart(String[][] resultsArray, int panelWidth, int panelHeigth) {
		this.resultsArray = resultsArray;
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeigth;
	}
	
	
	public void refreshPaint() {
		repaint();
	}
	
	/**
	 * Renders the graph based on the user scores.
	 * 
	 * @author SwathiGoru
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int numOfTopics = resultsArray.length;
		int maxQuestions = 10;
		for (int i = 0; i < numOfTopics; i++) {
			if (Integer.parseInt(resultsArray[i][1]) > maxQuestions) {
				maxQuestions = Integer.parseInt(resultsArray[i][1]);
			}
		}
		
		int width = (this.panelWidth - 100)/numOfTopics ;
		int heightScaling = (this.panelHeight - 80) /  maxQuestions;
		int x = 40;
		for (int i = 0; i < numOfTopics; i++) {
			int height = (Integer.parseInt(resultsArray[i][1]) * heightScaling);
			int y = this.panelHeight - height;
			System.out.println("\nx,y,w,h:" + x + "," + y +"," + width +"," +height);
			g.setColor(Color.BLACK);			
			g.drawRect(x, y, width, height);
			g.setColor(colorList[i]);
			g.fillRect(x, y, width, height);
			g.drawString(resultsArray[i][0], 10, 10);
			x += (width+1);
		}
		
	}
}
