
 
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
import org.newdawn.slick.state.StateBasedGame;
 
/**  
*  
* @author panos  
*/
public class Game extends StateBasedGame
{

	public static final int menu = 0;
	public static final int gameLevel = 1;
	
     public Game()
     {
         super("Taste The Rainbow");
         this.addState(new Menu(menu));
         this.addState(new GameLevel(gameLevel));
     }

 	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(menu).init(gc, this);
		this.getState(gameLevel).init(gc, this);
		this.enterState(menu); //first screen is menu
	}
 	
     public static void main(String[] args) throws FileNotFoundException
     {
    	 
         // parser
  		File file = new File("resources/Input.txt");
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
  

 }