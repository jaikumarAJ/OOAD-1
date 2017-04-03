package gui;

/**
*
* @author Ruchi
*/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;

public class DrawPanel extends JPanel{
 static final long serialVersionUID = 4L;
 
 private ArrayList<java.awt.Color> colors = new ArrayList<java.awt.Color>();
 
 public DrawPanel() {
   for(int i = 0; i < 10; ++i)
     colors.add(java.awt.Color.BLACK);
 }
 
 public void paint(Graphics g) {
   System.out.println("Inside DrawSprinkler paint");
   Graphics2D g2d = (Graphics2D) g;
   
   Rectangle2D rect = this.getBounds();
   
   System.out.println("x " + rect.getX() + " y " + rect.getY() + " w " + rect.getWidth() + " h " + rect.getHeight());
   
   RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                                          RenderingHints.VALUE_ANTIALIAS_ON);
   
   rh.put(RenderingHints.KEY_RENDERING,
          RenderingHints.VALUE_RENDER_QUALITY);
   
   g2d.setRenderingHints(rh);
   
   g2d.setColor(Color.CYAN);
   g2d.fillRect(0, 0, 525, 525);
       
   //Sprinkler order
   
   g2d.setColor(Color.BLACK);
   g2d.drawString("North", 190, 10);
   g2d.drawString("South", 190, 520);
   g2d.drawString("East", 20 + 350, 245);
   g2d.drawString("West", 20, 245);
   g2d.drawString("Center", 190, 235);

   //Markers
   g2d.setColor(Color.BLUE);
   g2d.fillRect(10, 500, 20, 20);
   g2d.drawString("ON", 40, 515);
   
   g2d.setColor(Color.BLACK);
   g2d.fillRect(10, 470, 20, 20);
   g2d.drawString("OFF", 40, 485);
   
   g2d.setColor(Color.MAGENTA);
   g2d.fillRect(10, 440, 20, 20);
   g2d.drawString("FORCED_ON", 40, 455);
   
   g2d.setColor(Color.RED);
   g2d.fillRect(10, 410, 20, 20);
   g2d.drawString("FORCED_OFF", 40, 425);
   
   ///N1 N2
   g2d.setColor(colors.get(0));
   g2d.fillRect(120, 20, 20, 20);
   g2d.setColor(colors.get(1));
   g2d.fillRect(260, 20, 20, 20);
   
   //S1 S2
   g2d.setColor(colors.get(2));
   g2d.fillRect(120, 480, 20, 20);
   g2d.setColor(colors.get(3));
   g2d.fillRect(260, 480, 20, 20);
   
   //E1 E2
   g2d.setColor(colors.get(4));
   g2d.fillRect(20 + 350, 180, 20, 20);
   g2d.setColor(colors.get(5));
   g2d.fillRect(20 + 350, 300, 20, 20);
       
   //W1 W2
   g2d.setColor(colors.get(6));
   g2d.fillRect(20, 180, 20, 20);
   g2d.setColor(colors.get(7));
   g2d.fillRect(20, 300, 20, 20);
   
   //C1 C2
   g2d.setColor(colors.get(8));
   g2d.fillRect(200, 130, 20, 20);
   g2d.setColor(colors.get(9));
   g2d.fillRect(200, 340, 20, 20);
 }
 
 public ArrayList<java.awt.Color> getColors() {
   return colors;
 }
 
 public static void main(String[] args) {
   JFrame frame = new JFrame("Basic Shapes");
   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   frame.add(new DrawPanel());
   frame.setSize(350, 250);
   frame.setLocationRelativeTo(null);
   frame.setVisible(true);
 }
}