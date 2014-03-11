import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.lwjgl.input.Mouse;

public class Menu extends BasicGameState{
	
	Animation sprite, right;
	private String mouse ="";
	private int state;
	private boolean isMouseOverStartBttn,isMouseOverLvls;
	private Image background, bottomBlock;
	
	private Music music;
	
	public Menu(int state) {
		this.state = state;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		gc.setShowFPS(false);
		music = new Music("resources/MenuMusic.wav");
		
		background = new Image("images/background/game_background.png");
		bottomBlock = new Image("images/background/game_bottomblock.png");
		Image [] movementRight =  {new Image("images/hamster/Run_1.png"), new Image("images/hamster/Run_2.png"), new Image("images/hamster/Run_3.png"), new Image("images/hamster/Run_4.png"),new Image("images/hamster/Run_5.png"), new Image("images/hamster/Run_6.png"), new Image("images/hamster/Run_7.png"), new Image("images/hamster/Run_8.png")} ;
		int [] duration = {50, 50, 50, 50, 50, 50, 50, 50}; 
		right = new Animation(movementRight, duration, true);
		sprite = right;
	}
	
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		background.draw(0,0);
		g.drawString(mouse, 50, 50);
		sprite.draw(0,400);
		bottomBlock.draw(0,400);
		
		
		//Buttons
		Image startBttn = new Image("images/buttons/Button_Play_Neutral.png");
		Image startBttnHover = new Image("images/buttons/Button_Play_Selected.png");
		Image lvlsBttn = new Image("images/buttons/Button_Levels_Neutral.png");
		Image lvlsBttnHover = new Image("images/buttons/Button_Levels_Selected.png");

		
		if(isMouseOverStartBttn)
			g.drawImage(startBttnHover, 100, 350);
		else
			g.drawImage(startBttn, 100,350);
		
		if(isMouseOverLvls)
			g.drawImage(lvlsBttnHover, 220, 350);
		else
			g.drawImage(lvlsBttn, 220,350);
		
		g.setBackground(Color.lightGray);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		
		if(music.playing() == false)
		{
			music.play();
		}
		
		mouse = "Mouse Position x: " + xpos + "   y: " + ypos;
		
		//Start button
		if((xpos>101 && xpos <180) && (ypos>367 && ypos<447))
		{
			isMouseOverStartBttn = true;
			if(input.isMouseButtonDown(0)){ //0 means left click, 1 means right click
				music.stop();
				sbg.enterState(1); //enters state with id 1 which is gameLevel 1
			}
		}
		else{
				isMouseOverStartBttn = false;
			}
		
		//Levels button
		if((xpos>221 && xpos<300) && (ypos>367 && ypos<447 ))
		{
			isMouseOverLvls = true;
			
			if(input.isMouseButtonDown(0)){
				music.stop();
				sbg.enterState(-1); //enters level selector screen
			}
			
		} else{
			isMouseOverLvls = false;
		}
		
	}

	@Override
	public int getID() {
		
		return state;
	}

}
