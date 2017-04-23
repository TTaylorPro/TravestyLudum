import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JComponent;

public class Rotater extends JComponent{
   public void paintArena(Graphics2D g){
        g.setColor(Color.BLUE);
        g.fillRect(150,150,200,200);
    }
}