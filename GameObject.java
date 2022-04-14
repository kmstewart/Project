import java.awt.*;
import javax.swing.*;

public class GameObject
{
   protected int x, y;
   protected Color c;
   private boolean collide;
   
   //constructor with x and y cordinates point to the middle of the block
   public GameObject (int x_in, int y_in, Color c_in)
   {
      x=x_in+12;
      y=y_in+12;
      c=c_in;
   }
   
   public int getX()
   {
      return x;
   }
   public int getY()
   {
      return y;
   }
   
   //checks if something collides with a game object
   public boolean collides(GameObject gm)
   {
      //if the game object is null or this then the collision is false
      if (gm == this || gm == null)
      {
         collide=false;
         return collide;
      }
      //calculating the this and others
      int topthis=y-11;
      int bottomthis=y+13;
      int leftthis=x-11;
      int rightthis=x+13;
      
      int topother=gm.getY()-11;
      int bottomother=gm.getY()+13;
      int leftother=gm.getX()-11;
      int rightother=gm.getY()+13;
      
      if (bottomthis<topother)
      {
         collide=true;
      }
      if (topthis>bottomother)
      {
         collide=true;
      }
      if (leftthis>rightother)
      {
         collide=true;
      }
      if (rightthis<leftother)
      {
         collide=true;
      }
      //checks if it collided with a victory block and makes the option pane
      if(gm instanceof VictoryBlock)
      {
         JOptionPane.showMessageDialog(null, "Win!");
         System.exit(1);
      }
      return collide;/*!((bottomthis<topother) || (topthis>bottomother) || (leftthis>rightother) || (rightthis<leftother));*/
   }
   
   public void draw(Graphics g)
   {
      g.setColor(c);
      g.fillRect(x-12, y-12, 25, 25);
   }
}