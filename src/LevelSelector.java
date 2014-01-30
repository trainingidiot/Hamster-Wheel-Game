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
	
	LevelSelector(int state){
		this.state = state;
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.setShowFPS(false);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString(mouse, 100, 50);
		
		Image backBttn = new Image("images/back-button.png");
		g.drawImage(backBttn, 20, 30);
		
		g.setBackground(Color.lightGray);
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
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return state;
	}

}
