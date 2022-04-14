import java.awt.*;
import java.util.*;

public class Player extends GameObject
{
   //constructor that uses super
   public Player(int x_in, int y_in, Color c_in)
   {
      super(x_in, y_in, c_in);
   }
   
   //checks to see if player is on the ground by taking in the map
   public boolean isOnGround(ArrayList<ArrayList<GameObject>> al)
   {
      boolean crash = false;
      //finds the x and y index near the player for the array list
      int findX, findY;
      findX=(x-22)/25;
      findY=(y-22)/25;
      //lowers the y by one pixel
      y++;
      //checks if it collides with any game objects around it
      if(collides(al.get(findY+1).get(findX+1)) || collides(al.get(findY+1).get(findX)))
      {
         //raises y back up to its origional place
         y--;
         crash = true;
      }
      else
      {
         y--;
      }
      //returns crash boolean
      return crash;  
   }
   
   //returns if move was succesful or not
   public boolean move(int xMove, int yMove, ArrayList<ArrayList<GameObject>> al)
   {
      boolean move=true;
      //moves the x and y based on what is passed in
      x+=xMove;
      y+=yMove;
      //calculates the x and y indexes for the map
      int findX, findY;
      findX=(x-22)/25;
      findY=(y-22)/25;
      
      //if the x move is positive
      if(xMove>0)
      {
         //and if it collides with a game object near it
         if (collides(al.get(findY).get(findX+1)) || collides(al.get(findY+1).get(findX+1)))
         {
            //subtracts the movement to go back to its originoal spot
            x-=xMove;
            y-=yMove;
            move = false;
         }
      }
      //if the x move is negative
      if(xMove<0)
      {
         //and if it collides with a game object near it
         if (collides(al.get(findY).get(findX)) || collides(al.get(findY+1).get(findX)))
         {
            //subtracts the movement to go back to its originoal spot
            x-=xMove;
            y-=yMove;
            move = false;
         }
      }
      
      //if the y move is positive
      if(yMove>0)
      {
      //and if it collides with a game object near it
         if (collides(al.get(findY+1).get(findX)) || collides(al.get(findY+1).get(findX+1)))
         {
            //subtracts the movement to go back to its originoal spot
            x-=xMove;
            y-=yMove;
            move = false;
         }
      }
      
      //if the y move is negative
      if(yMove<0)
      {
         //and if it collides with a game object near it
         if (collides(al.get(findY).get(findX)) || collides(al.get(findY).get(findX+1)))
         {
            //subtracts the movement to go back to its originoal spot
            x-=xMove;
            y-=yMove;
            move = false;

         }
      }
      return move;
   }
   
   //an old way to check if the player touched victory block, I moved it to be more effient to the collide method
   /*public boolean checkVictory(ArrayList<ArrayList<GameObject>> al)
   {
      boolean win=false;
      int findX, findY;
      findX=(x-22)/25;
      findY=(y-22)/25;
      
      if(!move(-1, 0, al))
      {
         if(al.get(findY).get(findX-1) instanceof VictoryBlock || al.get(findY).get(findX-1) instanceof VictoryBlock || al.get(findY-1).get(findX-1) instanceof VictoryBlock)
         {
            win=true;
         }
      }
      else
      {
         move(1, 0, al);
      }
      if(!move(1, 0, al))
      {
         if(al.get(findY).get(findX+1) instanceof VictoryBlock || al.get(findY).get(findX-1) instanceof VictoryBlock || al.get(findY-1).get(findX-1) instanceof VictoryBlock)
         {
            win=true;
         }
      }
      else
      {
         move(-1, 0, al);
      }

      if(!move(0, -1, al))
      {
         if(al.get(findY-1).get(findX) instanceof VictoryBlock || al.get(findY).get(findX+1) instanceof VictoryBlock || al.get(findY+1).get(findX+1) instanceof VictoryBlock)
         {
            win=true;
         }
      }
      else
      {
         move(0, 1, al);
      }
      if(!move(0, 1, al))
      {
         if(al.get(findY-1).get(findX) instanceof VictoryBlock || al.get(findY).get(findX+1) instanceof VictoryBlock || al.get(findY+1).get(findX+1) instanceof VictoryBlock)
         {
            win=true;
         }
      }
      else
      {
         move(0, -1, al);
      }

      return win;
   }*/
}