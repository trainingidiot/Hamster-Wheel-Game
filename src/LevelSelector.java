import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class LevelSelector extends BasicGameState {

	String mouse;
	private int state;
	private Image lvl1, lvl1Select;
	private boolean isMouseOver_lvl1;
	
	LevelSelector(int state){
		this.state = state;
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.setShowFPS(false);
		
		lvl1 = new Image("images/buttons/BUtton_Level_Neutral.png");
		lvl1Select = new Image("images/buttons/BUtton_Level_Selected.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString(mouse, 100, 50);
		
		Image backBttn = new Image("images/back-button.png");
		g.drawImage(backBttn, 20, 30);
		
		g.setBackground(Color.lightGray);
		
		g.drawImage(lvl1,30, 121);
		if(isMouseOver_lvl1){
			g.drawImage(lvl1Select,30,121);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = gc.getInput();
		
		int xpos = input.getMouseX();
		int ypos = input.getMouseY();
		
		mouse = "Mouse position x: " + xpos + " ypos: " + ypos;
		
		if((xpos>23 && xpos<69) && (ypos>33 && ypos<81)){
			if(input.isMouseButtonDown(0)){
				sbg.enterState(0); //return to menu screen
			}
		}
		
		//Level1
		if((xpos>30 && xpos<110) && (ypos>124 && ypos<203)){
			isMouseOver_lvl1 = true;
			if(input.isMousePressed(0)){
				sbg.enterState(2); //enter level 2 just for testing purposes
			}
		}
		else{
			isMouseOver_lvl1 = false;
		}
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return state;
	}

}
