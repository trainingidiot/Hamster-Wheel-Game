import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.lwjgl.input.Mouse;

public class Menu extends BasicGameState{
	
	private String mouse ="";
	public Menu(int state) {
		
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		g.drawString(mouse, 50, 50);
		Image startBttn = new Image("images/start-button.png");
		g.drawImage(startBttn, 100, 650);
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
		
	}

	@Override
	public int getID() {
		
		return 0;
	}

}
