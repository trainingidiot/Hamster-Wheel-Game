
 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.Animation; 
import org.newdawn.slick.AppGameContainer; 
import org.newdawn.slick.BasicGame; 
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer; 
import org.newdawn.slick.Graphics; 
import org.newdawn.slick.Image; 
import org.newdawn.slick.Input; 
import org.newdawn.slick.SlickException; 
 
/**  
*  
* @author panos  
*/
public class Game extends BasicGame
{
  
	Animation sprite, left, right, leftStill, rightStill;
	Image board, wheel;

	
     public Game()
     {
         super("Taste The Rainbow");
     }
 //comment
     public static void main(String [] arguments) throws FileNotFoundException
     {
    	 
         // parser
  		File file = new File("/Users/yifanz/Desktop/CS 113/CS 113/Input.txt");
  		FileReader fr = new FileReader(file);
  		Scanner scanner = new Scanner(fr);
  		
  		ListStorage storage = new ListStorage();
  		String left = scanner.next();
  		
  		while(!left.equals("Right"))
  		{
  			storage.addToLeftList(left);
  			left = scanner.next();
  		}
  		
  		while(scanner.hasNext())
  		{
  			storage.addToRightList(scanner.next());
  		}
  		
  		scanner.close();
  		
  		//parser test
  		System.out.println("Beginning of parser test");
  		ArrayList<String> leftListTest = storage.getCurrentLeftList();
 		ArrayList<String> rightListTest = storage.getCurrentRightList();
 		System.out.print("Left list: ");
 		for(int i = 0; i < leftListTest.size(); i++)
 		{
 			System.out.print(leftListTest.get(i)+" ");
 		}
 		System.out.println();
 		System.out.print("Right list: ");
 		for(int i = 0; i < rightListTest.size(); i++)
 		{
 			System.out.print(rightListTest.get(i)+" ");
 		}
 		System.out.println();
 		System.out.println("End of parser test");
 		System.out.println();
 		System.out.println();
 		
         try
         {
             AppGameContainer app = new AppGameContainer(new Game());
             app.setDisplayMode(400, 800, false);
             app.start();
         }
         catch (SlickException e)
         {
             e.printStackTrace();
         }
     }
 
     @Override
     public void init(GameContainer container) throws SlickException
     {
         //Image [] movementLeft =  {new Image("images/Biped/drag_wr_0.png"), new Image("images/Biped/drag_wr_1.png"), new Image("images/Biped/drag_wr_2.png"), new Image("images/Biped/drag_wr_3.png"),new Image("images/Biped/drag_wr_4.png"), new Image("images/Biped/drag_wr_5.png"), new Image("images/Biped/drag_wr_6.png"), new Image("images/Biped/drag_wr_7.png"),new Image("images/Biped/drag_wr_8.png"), new Image("images/Biped/drag_wr_9.png"), new Image("images/Biped/drag_wr_10.png"), new Image("images/Biped/drag_wr_11.png")} ;
         //Image [] movementRight =  {new Image("images/Biped/drag_wl_0.png"), new Image("images/Biped/drag_wl_1.png"), new Image("images/Biped/drag_wl_2.png"), new Image("images/Biped/drag_wl_3.png"),new Image("images/Biped/drag_wl_4.png"), new Image("images/Biped/drag_wl_5.png"), new Image("images/Biped/drag_wl_6.png"), new Image("images/Biped/drag_wl_7.png"),new Image("images/Biped/drag_wl_8.png"), new Image("images/Biped/drag_wl_9.png"), new Image("images/Biped/drag_wl_10.png"), new Image("images/Biped/drag_wl_11.png")} ;
         //Image [] movementLeftStill =  {new Image("images/Biped/drag_bl_0.png"), new Image("images/Biped/drag_bl_1.png"), new Image("images/Biped/drag_bl_2.png"), new Image("images/Biped/drag_bl_3.png")} ;
         //Image [] movementRightStill =  {new Image("images/Biped/drag_br_0.png"), new Image("images/Biped/drag_br_1.png"), new Image("images/Biped/drag_br_2.png"), new Image("images/Biped/drag_br_3.png")} ;
         //int [] duration = {50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50};  
         //int [] durationStill = {200, 200, 200, 200};
         board = new Image("images/background/TasteTheRainbow_Board.png");
         wheel = new Image("images/background/TasteTheRainbow_Wheel.png");
       
         //left = new Animation(movementLeft, duration, true);
         //right = new Animation(movementRight, duration, true);
         //leftStill = new Animation(movementLeftStill, durationStill, true);
         //rightStill = new Animation(movementRightStill, durationStill, true);
         
 
         
         
         
         
     }
 
     @Override
     public void update(GameContainer container, int delta) throws SlickException
     {
         Input input = container.getInput();

         if (input.isKeyDown(Input.KEY_LEFT)==false)
         {
        	 wheel.setRotation(wheel.getRotation()+1);
         }
         if (input.isKeyDown(Input.KEY_RIGHT)==false)
         {
        	 wheel.setRotation(wheel.getRotation()-1);
         }
         
     }
 
     public void render(GameContainer container, Graphics g) throws SlickException
     {
    	 g.setBackground(Color.white);
         board.draw(-1, -1);
         wheel.draw(-1,400);
         
     }
     
   

 }