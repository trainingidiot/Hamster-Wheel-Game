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
	private Image lvl1, lvl1Select, lvl2, lvl3, lvl2Select, lvl3Select;
	private boolean isMouseOver_lvl1, isMouseOver_lvl2, isMouseOver_lvl3;
	
	LevelSelector(int state){
		this.state = state;
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.setShowFPS(false);
		
		lvl1 = new Image("images/buttons/BUtton_Level_Neutral.png");
		lvl1Select = new Image("images/buttons/BUtton_Level_Selected.png");
		lvl2 = new Image("images/buttons/Button_Level_Neutral.png");
		lvl2Select = new Image("images/buttons/Button_Level_Selected.png");
		lvl3 = new Image("images/buttons/Button_Level_Neutral.png");
		lvl3Select = new Image("images/buttons/Button_Level_Selected.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString(mouse, 100, 50);
		
		Image backBttn = new Image("images/back-button.png");
		g.drawImage(backBttn, 20, 30);
		
		g.setBackground(Color.lightGray);
		
		if(isMouseOver_lvl1){
			g.drawImage(lvl1Select,50,121);
		}else{
			g.drawImage(lvl1,50, 121);
		}
		
		if(isMouseOver_lvl2){
			g.drawImage(lvl2Select,160,121);
		}else{
			g.drawImage(lvl2,160,121);
		}
		
		if(isMouseOver_lvl3){
			g.drawImage(lvl3Select,270,121);
		}else{
			g.drawImage(lvl3,270,121);
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
						sbg.enterState(1); //enter level 1 
					}
				}
				else{
					isMouseOver_lvl1 = false;
				}
				
				//Level2
				if((xpos>161 && xpos<239) && (ypos>124 && ypos<203)){
					isMouseOver_lvl2 = true;
					if(input.isMousePressed(0)){
						sbg.enterState(2); //enter level 2 
					}
				}
				else{
					isMouseOver_lvl2 = false;
				}
				
				//Level3
				if((xpos>271 && xpos<350) && (ypos>124 && ypos<203)){
					isMouseOver_lvl3 = true;
					if(input.isMousePressed(0)){
						sbg.enterState(3); //enter level 3
					}
				}
				else{
					isMouseOver_lvl3 = false;
				}
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return state;
	}

}
