import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Project extends JPanel
{
   private ArrayList<ArrayList<GameObject>> map;
   private ArrayList<GameObject> innerGO;
   private int playerStartX, playerStartY, rows, columns, jump=0, N=1, count=0, counter=0;
   private Player player;
   private static boolean up, down, left, right;
   private int xPosition, yPosition;
   
   public Project()
   {
      super();
      
      map = new ArrayList<ArrayList<GameObject>>();
      
      try
      {
         //try to read in from file using scanner
         Scanner file = new Scanner(new File("map.txt"));
         //read the players start x and y position
         playerStartX = 10+(25*file.nextInt());
         playerStartY = 10+(25*file.nextInt());
         //create player with the start x and y position
         player = new Player (playerStartX, playerStartY, Color.CYAN);
         //get the x and y position
         xPosition=player.getX();
         yPosition=player.getY();
         //read in the number of rows and columns
         rows = file.nextInt();
         columns = file.nextInt();
         
         //add the inner array list
         for (int i=0; i<rows; i++)
         {
            innerGO = new ArrayList<GameObject>();
            map.add(innerGO);
         }
         
         //2 for loops to go through the text file
         for (int i=0; i<rows; i++)
         {
            for(int j=0; j<columns; j++)
            {
               int temp = file.nextInt();
               //if temp is 0 make it null
               if (temp==0)
               {
                  map.get(i).add(null);
               }
               //if temp is 1 make it a border block
               else if (temp==1)
               {
                  Block border = new Block((25*j)+10, (25*i)+10, Color.BLUE);
                  map.get(i).add(border);
               }
               //if temp is 2 make it a victory block
               else if (temp==2)
               {
                  VictoryBlock victory = new VictoryBlock((25*j)+10, (25*i)+10, Color.GREEN);
                  map.get(i).add(victory);
               }
            }
         }
      }
      //catch exception if file is not found
      catch (FileNotFoundException fnfe)
      {
         System.out.println ("File not found");
      } 

      
      //adds a key listenenr and allows it to be focusable
      addKeyListener(new KeyEventDemo());
      setFocusable(true); 
      
      //set timer for Player Listener
      Timer t = new Timer(10, new PlayerListener()); 
      t.start();     
      
      setPreferredSize(new Dimension (820, 620));
      
      setBackground(Color.GRAY);
   }
   
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      
      //set color to black and make 4 rectangles for border
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, 810, 10);
      g.fillRect(0, 610, 820, 10);
      g.fillRect(0, 0, 10, 610);
      g.fillRect(810, 0, 10, 610);
      
      //a for loop that goes through the map
      for (int i=0; i<map.size(); i++)
      {
         for (int j=0; j<map.get(i).size(); j++)
         {
            //if the arraylist doesn't contain null then it uses the draw method from game object to draw the square
            if(!(map.get(i).get(j)==null))
            {
               map.get(i).get(j).draw(g);
            }
         }
      }
      
      //draws the player
      player.draw(g);
   }
   
   private class KeyEventDemo implements KeyListener 
   {
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) 
    {
      //says if whatever key is released and sets the boolean to false for that key
       if(e.getKeyCode() == KeyEvent.VK_W)
       {
          up=false;
       }
      if(e.getKeyCode() == KeyEvent.VK_A)
      {
         left=false;
      }
      if(e.getKeyCode() == KeyEvent.VK_D)
      {
         right=false;
      }

    }
    public void keyPressed(KeyEvent e) 
    {
      //says if whatever key is pressed and sets the boolean to true for that key
      if(e.getKeyCode() == KeyEvent.VK_W)
      {
         up=true;
         //if(player.isOnGround(map)) //isOnGround is not working
         //{
            jump=7;
         //}
      }
      if(e.getKeyCode() == KeyEvent.VK_A)
      {
         left=true;
      }
      if(e.getKeyCode() == KeyEvent.VK_D)
      {
         right=true;
      }
      if(e.getKeyCode() == KeyEvent.VK_S)
      {
         down=true;
      }

     }
   }
   
   private class PlayerListener implements ActionListener 
   {
      public void actionPerformed(ActionEvent e)
      {
         //if left is true move player -1 to the left
         if (left)
         {
            player.move(-1, 0, map);
         }
         //check if it hit a victory block
         
         /*if (player.checkVictory(map))
         {
            JOptionPane.showMessageDialog(null, "Win!");
            System.exit(1);
         }*/
         
         //if left is true move player -1 to the left
         if (right)
         {
            player.move(1, 0, map);
         }
         //check if it hit a victory block
         
         /*if (player.checkVictory(map))
         {
            JOptionPane.showMessageDialog(null, "Win!");
            System.exit(1);
         }*/

         repaint();
       
            //check if it hit ceiling 
            for(int i=0; i<jump; i++)
            {
               if (jump>0)
               {
                  N=1;
                  if(!player.move(0,-1, map))
                  {
                     jump=0;
                  }
                  repaint();
               }
            }
            //if a jump is happening and counter has gone through 10 ticks, then decrease jump by one (.1*10=1)
            if(jump>0)
            {
               if(count>10)
               {
                   jump--;
                   count=0;
               }
            }
            count++;
            
            if (player.collides(map.get((player.getY()-22)/25).get((player.getX()-22)/25)) || 
            player.collides(map.get((player.getY()-22)/25).get(((player.getX()-22)/25)+1)))
            {
               jump=0;
            }
            
            //gravity
            //move down one until a is N
            for (int a=0; a<N; a++)
            {  
               player.move(0, 1, map);
               repaint();
            }
            //if N is less than 7 and the counter has gone through 20 ticks, then increase N
            if (N<7)
            {
               if(counter>20)
               {
                  N++;
                  //put counter back to 0 so it counts another 20 ticks
                  counter=0;
               }
            }
            counter++;
            //if the player is on the ground, reset gravity
            if (player.isOnGround(map))
            {
               N=1;
            }
            repaint(); 
         //check if player hit a victory block
         /*if (player.checkVictory(map))
         {
            JOptionPane.showMessageDialog(null, "Win!");
            System.exit(1);
         } */
      }
   }
   
   public static void main(String[] args)
   {
      //make frame and exit program when it closes
      JFrame frame = new JFrame("Project");
      frame.setSize(820, 647);
      frame.add(new Project());
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
      frame.setVisible(true);    
   }
}

/*Hi Dr. Mood
I was not able to finish completely, there are few things that dont work. The isOnGround does
not work when checking if W was pressed, but works everywhere else I think. The player seems to
sit one pixel above the blocks, but fixing that breaks a lot of other things and I would have to
make the x and y positions not indicate the middle of the square. Grant helped me work on this and
we couldn't find how to fix it. We tried a lot and changed some of my code to similar to his to see 
if that would work, but it didn't, I tried to change most of it back, but if some of it similar that
is why. Sorry I couldn't finish.
-Kailen*/