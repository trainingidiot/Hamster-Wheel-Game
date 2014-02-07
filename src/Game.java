
 
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

import com.sun.org.apache.xalan.internal.xsltc.compiler.Parser;
 
/**  
*  
* @author panos  
*/
public class Game extends StateBasedGame
{

	public static final int menu = 0;
	public static final int gameLevel = 1;
	public static final int levelSelector = 2;
     public Game()
     {
         super("Taste The Rainbow");
         this.addState(new Menu(menu));
         this.addState(new GameLevel(gameLevel));
         this.addState(new LevelSelector(levelSelector));
     }

 	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(menu).init(gc, this);
//		this.getState(gameLevel).init(gc, this);
		this.getState(levelSelector).init(gc, this);
		this.enterState(menu); //first screen is menu
	}
 	
     public static void main(String[] args) throws FileNotFoundException
     {
    	 // call parser
    	Parser parse = new Parser();
    	parse.callParser();
 		
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