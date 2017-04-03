package gui;

/**
*
* @author Ruchira De
*/

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.GridLayout;
import javax.swing.JFrame;

public class DrawGraph extends JFrame {
 static final long serialVersionUID = 6;
 
 public DrawGraph(int[] xData, int[] yData, String title) {
   super(title);
   setSize(720, 280);
   getContentPane().setLayout(new GridLayout(1, 3, 10, 0));
   getContentPane().setBackground(Color.white);
       
   JChart2D chart = new JChart2D(JChart2D.ColumnChart, xData.length, xData, yData, title);
   GradientPaint gp = new GradientPaint(0, 100, Color.white, 0, 300, Color.blue, true);
   chart.setGradient(gp);
   chart.setEffectIndex(JChart2D.Gradientffect);
   chart.setDrawShadow(true);
   getContentPane().add(chart);
   setVisible(true);
 }
 
 public static void main(String argv[]) {
   int[] xData = new int[8];
   int[] yData = new int[8];
   for (int i = 0; i < yData.length; i++) 
   {
     xData[i] = i;
     yData[i] = (int) (Math.random() * 100);
     System.out.println("Water consumed on day " + i + " " + yData[i]);
   }
   new DrawGraph(xData, yData, "Water Consumption");
 }
}
