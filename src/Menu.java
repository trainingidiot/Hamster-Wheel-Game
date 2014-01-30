import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.lwjgl.input.Mouse;

public class Menu extends BasicGameState{
	
	private String mouse ="";
	private int state;
	
	public Menu(int state) {
		this.state = state;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		gc.setShowFPS(false);
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		g.drawString(mouse, 50, 50);
		Image startBttn = new Image("images/start-button.png");
		Image lvlsBttn = new Image("images/level-button.png");
		g.drawImage(startBttn, 100, 650);
		g.drawImage(lvlsBttn, 220, 665);
		g.setBackground(Color.lightGray);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		
		mouse = "Mouse Position x: " + xpos + "   y: " + ypos;
		
		if((xpos>115 && xpos <190) && (ypos>60 && ypos<134)){
			if(input.isMouseButtonDown(0)){ //0 means left click, 1 means right click
				sbg.enterState(1); //enters state with id 1 which is gameLevel
			}
		}
		
		
		if((xpos>220 && xpos<291) && (ypos>62   && ypos<133 ))
		{
			if(input.isMouseButtonDown(0)){
				sbg.enterState(2); //enters level selector screen
			}
		}
		
	}

	@Override
	public int getID() {
		
		return state;
	}

}