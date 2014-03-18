
import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class Credits extends BasicGameState{

	private int state;
	private Image background, backBttn, backBttnSelect, logo;
	private boolean isMouseOverBack;
	private Font font, font2;
	private TrueTypeFont ttf;
	
	private String mouse, team;
	
	Credits(int state){
		this.state = state;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.setShowFPS(false);
		
		background = new Image("images/background/credits_background_final.png");
		backBttn = new Image("images/buttons/Back_neutral_final.png");
		backBttnSelect = new Image("images/buttons/Back_pressed_final.png");
		logo = new Image("images/background/Logo_small.png");
		
		font = new Font("Serif", Font.BOLD, 20);
		ttf = new TrueTypeFont(font, true);
		font2 = new Font("Serif", Font.BOLD, 20);
		
		team = "- Jesse Huff, Programmer \n- Brian Jeon, Artist \n- Calvin Liu, Project Manager \n- Kelly Yin, Programmer \n- Yvonne Zhang, Programmer";
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbc, Graphics graphics) throws SlickException {
//		 graphics.drawString(mouse, 100, 50);
		 graphics.drawImage(background, 0,0);
		 graphics.drawImage(logo, 60,50);
		 ttf.drawString(170f, 250f, "by PandaWolves",Color.yellow);
		 ttf = new TrueTypeFont(font2,true);
		 ttf.drawString(130f,310f,"Team Members:", Color.red);
		 graphics.drawString(team, 90, 340);
		 ttf.drawString(125f, 490f, "Artwork Created By:", Color.blue);
		 graphics.drawString("Brian Jeon", 155, 510);
		
		 if(isMouseOverBack){
			 backBttnSelect.draw(160,650);
		 }else
			 backBttn.draw(160,650);
			
		 
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub
		mouse = "Mouse x: " + gc.getInput().getMouseX() + "   y: " + gc.getInput().getMouseY();
		int xpos = gc.getInput().getMouseX();
		int ypos = gc.getInput().getMouseY();
		
		if((xpos>160 && xpos<219) && (ypos>653  && ypos<711)){
			isMouseOverBack = true;
			if(gc.getInput().isMousePressed(0)){
				sbg.enterState(0); // go back to main menu
			}
		}else{
			isMouseOverBack = false;
		}
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return state;
	}

}
