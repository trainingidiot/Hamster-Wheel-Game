import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class LevelSelector extends BasicGameState {

	String mouse;
	private int state;
	private Image backBttn,backBttnSelect, lvl1, lvl1Select, lvl2, lvl3, lvl2Select, lvl3Select, lvl4, lvl4Select, lvl5, lvl5Select;
	private boolean isMouseOver_Back, isMouseOver_lvl1, isMouseOver_lvl2, isMouseOver_lvl3, isMouseOver_lvl4, isMouseOver_lvl5;
	private Image background;
	private Music music;
	
	LevelSelector(int state){
		this.state = state;
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.setShowFPS(false);
		
		background = new Image("images/background/level_background_final.png");
		
		lvl1 = new Image("images/buttons/Buttons_Levels_1.png");
		lvl1Select = new Image("images/buttons/Buttons_Levels_1_depressed.png");
		lvl2 = new Image("images/buttons/Buttons_Levels_2.png");
		lvl2Select = new Image("images/buttons/Buttons_Levels_2_depressed.png");
		lvl3 = new Image("images/buttons/Buttons_Levels_3.png");
		lvl3Select = new Image("images/buttons/Buttons_Levels_3_depressed.png");
		lvl4 = new Image("images/buttons/Buttons_Levels_4.png");
		lvl4Select = new Image("images/buttons/Buttons_Levels_4_depressed.png");
		lvl5 = new Image("images/buttons/Buttons_Levels_5.png");
		lvl5Select = new Image("images/buttons/Buttons_Levels_5_depressed.png");
		backBttn = new Image("images/buttons/Back_neutral_final.png");
		backBttnSelect = new Image("images/buttons/Back_pressed_final.png");
		
		music = new Music("resources/MenuMusic.wav");
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(background, 0,0);
//		g.drawString(mouse, 100, 50);
//		g.setBackground(Color.lightGray);
		
		if(isMouseOver_Back){
			g.drawImage(backBttnSelect, 160, 650);
		}else{
			g.drawImage(backBttn, 160, 650);
		}
		
		if(isMouseOver_lvl1){
			g.drawImage(lvl1Select,50,150);
		}else{
			g.drawImage(lvl1,50, 150);
		}
		
		if(isMouseOver_lvl2){
			g.drawImage(lvl2Select,160,150);
		}else{
			g.drawImage(lvl2,160,150);
		}
		
		if(isMouseOver_lvl3){
			g.drawImage(lvl3Select,270,150);
		}else{
			g.drawImage(lvl3,270,150);
		}
		
		if(isMouseOver_lvl4){
			g.drawImage(lvl4Select,50,280);
		}else{
			g.drawImage(lvl4,50,280);
		}
		
		if(isMouseOver_lvl5){
			g.drawImage(lvl5Select,160,280);
		}else{
			g.drawImage(lvl5,160,280);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		int xpos = input.getMouseX();
		int ypos = input.getMouseY();
		
		if(music.playing() == false)
		{
			music.play();
		}
		
		mouse = "Mouse position x: " + xpos + " ypos: " + ypos;
		
		//back button
		if((xpos>160 && xpos<219) && (ypos>653 && ypos<711)){
			isMouseOver_Back = true;
			if(input.isMousePressed(0)){
				music.stop();
				sbg.enterState(0); //return to menu screen
			}
		}else{
			isMouseOver_Back = false;
		}
		
		//Level1
		if((xpos>56 && xpos<135) && (ypos>157 && ypos<238)){
			isMouseOver_lvl1 = true;
			if(input.isMousePressed(0)){
				music.stop();
				sbg.enterState(1); //enter level 1 
			}
		}
		else{
			isMouseOver_lvl1 = false;
		}
				
		//Level2
		if((xpos>165 && xpos<245) && (ypos>157 && ypos<238)){
			isMouseOver_lvl2 = true;
			if(input.isMousePressed(0)){
				music.stop();
				sbg.enterState(2); //enter level 2 
			}
		}
		else{
			isMouseOver_lvl2 = false;
		}
		
		//Level3
		if((xpos>275 && xpos<354) && (ypos>157 && ypos<238)){
			isMouseOver_lvl3 = true;
			if(input.isMousePressed(0)){
				music.stop();
				sbg.enterState(3); //enter level 3
			}
		}
		else{
			isMouseOver_lvl3 = false;
		}
		
		//Level4
		if((xpos>56 && xpos<135) && (ypos>287 && ypos<369)){
			isMouseOver_lvl4 = true;
			if(input.isMousePressed(0)){
				music.stop();
				sbg.enterState(4);
			}
		}
		else{
			isMouseOver_lvl4 = false;
		}
		
		//Level5
		if((xpos>165 && xpos<245) && (ypos>287 && ypos<369)){
			isMouseOver_lvl5 = true;
			if(input.isMousePressed(0)){
				music.stop();
				sbg.enterState(5);
			}
		}
		else{
			isMouseOver_lvl5 = false;
		}		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return state;
	}

}
