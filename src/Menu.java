import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.lwjgl.input.Mouse;

public class Menu extends BasicGameState{
	
	//Animation sprite, right;
	private String mouse ="";
	private int state;
	private boolean isMouseOverStartBttn,isMouseOverLvls;
	private Image background,startBttn,startBttnHover,lvlsBttn,lvlsBttnHover,creditsBttn;; //, bottomBlock;
	
	private Music music;
	
	public Menu(int state) {
		this.state = state;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		gc.setShowFPS(false);
		music = new Music("resources/MenuMusic.wav");
		
		background = new Image("images/background/menu_background_final.png");
		/*bottomBlock = new Image("images/background/game_bottomblock.png");
		Image [] movementRight =  {new Image("images/hamster/Run_1.png"), new Image("images/hamster/Run_2.png"), new Image("images/hamster/Run_3.png"), new Image("images/hamster/Run_4.png"),new Image("images/hamster/Run_5.png"), new Image("images/hamster/Run_6.png"), new Image("images/hamster/Run_7.png"), new Image("images/hamster/Run_8.png")} ;
		int [] duration = {50, 50, 50, 50, 50, 50, 50, 50}; 
		right = new Animation(movementRight, duration, true);
		sprite = right;
		*/
		
		//Buttons
		startBttn = new Image("images/buttons/Play_neutral_final.png");
		startBttnHover = new Image("images/buttons/Play_pressed_final.png");
		lvlsBttn = new Image("images/buttons/Levels_neutral_final.png");
		lvlsBttnHover = new Image("images/buttons/Levels_pressed_final.png");
		creditsBttn = new Image("images/Stars.png"); //star is place holder art for now
	}
	
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		background.draw(0,0);
		g.drawString(mouse, 50, 50);
		//sprite.draw(0,400);
		//bottomBlock.draw(0,400);
		
		if(isMouseOverStartBttn)
			g.drawImage(startBttnHover, 40, 700);
		else
			g.drawImage(startBttn, 40,700);
		
		if(isMouseOverLvls)
			g.drawImage(lvlsBttnHover, 160, 700);
		else
			g.drawImage(lvlsBttn, 160,700);
		
		g.drawImage(creditsBttn, 270, 700);
		
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
		if((xpos>41 && xpos <120) && (ypos>21 && ypos<96))
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
		if((xpos>161 && xpos<240) && (ypos>21 && ypos<96 ))
		{
			isMouseOverLvls = true;
			
			if(input.isMouseButtonDown(0)){
				music.stop();
				sbg.enterState(-1); //enters level selector screen
			}
			
		} else{
			isMouseOverLvls = false;
		}
		
		//credits button
		if((xpos>287 && xpos<334) && (ypos>34 && ypos<80)){
			if(input.isMouseButtonDown(0)){
				sbg.enterState(-2); //enter the credits screen
			}
		}
		
	}

	@Override
	public int getID() {
		
		return state;
	}

}
