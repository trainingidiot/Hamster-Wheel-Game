
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
	private Image backBttn, backBttnSelect;
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
		
		backBttn = new Image("images/buttons/Back_neutral_final.png");
		backBttnSelect = new Image("images/buttons/Back_pressed_final.png");
		
		font = new Font("Serif", Font.BOLD, 21);
		ttf = new TrueTypeFont(font, true);
		font2 = new Font("Serif", Font.BOLD, 18);
		
		team = "- Jesse Huff \n- Brian Jeon \n- Calvin Liu \n- Kelly Yin \n- Yvonne Zhang";
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbc, Graphics graphics) throws SlickException {
//		 graphics.drawString(mouse, 100, 50);
		 graphics.drawString("Credits", 150, 40);
		 ttf.drawString(45f, 110f, "Taste The Rainbow by PandaWolves",Color.orange);
		 ttf = new TrueTypeFont(font2,true);
		 ttf.drawString(100f,180f,"Team Members:", Color.red);
		 graphics.drawString(team, 110, 210);
		 ttf.drawString(100f, 350f, "Artwork created by:", Color.blue);
		 graphics.drawString("Brian Jeon", 125, 380);
		
		 if(isMouseOverBack){
			 backBttnSelect.draw(10,10);
		 }else
			 backBttn.draw(10,10);
			
		 
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub
		mouse = "Mouse x: " + gc.getInput().getMouseX() + "   y: " + gc.getInput().getMouseY();
		int xpos = gc.getInput().getMouseX();
		int ypos = gc.getInput().getMouseY();
		
		if((xpos>10 && xpos<75) && (ypos>12  && ypos<78)){
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
