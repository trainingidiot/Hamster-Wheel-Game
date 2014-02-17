
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.jbox2d.collision.shapes.CircleShape;
import org.newdawn.slick.geom.Transform;




public class GameLevel extends BasicGameState {

	Animation sprite, left, right, leftStill, rightStill;
	Image board, wheel, pauseBg, resumeBttn, resumeBttnSelect, menuBttn, menuBttnSelect, green;
	Image victoryScreen, failureScreen, nextBttn, nextBttnSelect, replayBttn, replayBttnSelect, levelBttn, levelBttnSelect, menuBttn2, menuBttn2Select;
	String mouse, level;
	private boolean isMouseOverPlay, isMouseOverMenu, isVictory, isFail;;
	
    private static final World world = new World(new Vec2(0, -9.8f));
    int velocityIterations;
    int positionIterations;
    float pixelsPerMeter;
    Body wheelArmA, wheelArmB, groundB;


	
	private int state;
	
	public GameLevel(int state) {
		this.state = state;
		level = "Level " + state;
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException{
        
		
		//arms
		{
            BodyDef wheelArm1 = new BodyDef();
            wheelArm1.active = true;
            wheelArm1.position = new Vec2(0.f, -6.7f);
            wheelArm1.type = BodyType.STATIC;
            wheelArmA = world.createBody(wheelArm1);
            PolygonShape bar = new PolygonShape();
            bar.setAsBox(6.6f, 0.1f);
            wheelArmA.createFixture(bar, 0);
        }
		{
            BodyDef wheelArm2 = new BodyDef();
            wheelArm2.active = true;
            wheelArm2.position = new Vec2(0.f, -6.7f);
            wheelArm2.type = BodyType.STATIC;
            wheelArmB = world.createBody(wheelArm2);
            PolygonShape bar = new PolygonShape();
            bar.setAsBox(.1f, 6.6f);
            wheelArmB.createFixture(bar, 0);
        }
		
		//ground
		{
            BodyDef ground = new BodyDef();
            ground.active = true;
            ground.position = new Vec2(0.f, -13.4f);
            ground.type = BodyType.STATIC;
            groundB = world.createBody(ground);
            PolygonShape bar = new PolygonShape();
            bar.setAsBox(6.6f, .1f);
            groundB.createFixture(bar, 0);
        }
		//Walls
		{
            BodyDef ground = new BodyDef();
            ground.active = true;
            ground.position = new Vec2(-6.7f, -6.7f);
            ground.type = BodyType.STATIC;
            groundB = world.createBody(ground);
            PolygonShape bar = new PolygonShape();
            bar.setAsBox(.1f, 6.6f);
            groundB.createFixture(bar, 0);
        }
		{
            BodyDef ground = new BodyDef();
            ground.active = true;
            ground.position = new Vec2(6.7f, -6.7f);
            ground.type = BodyType.STATIC;
            groundB = world.createBody(ground);
            PolygonShape bar = new PolygonShape();
            bar.setAsBox(.1f, 6.6f);
            groundB.createFixture(bar, 0);
        }
		
		/*
		{
            BodyDef ground = new BodyDef();
            ground.active = true;
            ground.position = new Vec2(3.35f, -12.5f);
            ground.type = BodyType.STATIC;
            groundB = world.createBody(ground);
            PolygonShape bar = new PolygonShape();
            bar.setAsBox(.1f, 6.6f);
            groundB.createFixture(bar, 0);
            groundB.setTransform(groundB.getPosition(), 2.35619449f);
        }
		
		{
            BodyDef ground = new BodyDef();
            ground.active = true;
            ground.position = new Vec2(-3.35f, -10.05f);
            ground.type = BodyType.STATIC;
            groundB = world.createBody(ground);
            PolygonShape bar = new PolygonShape();
            bar.setAsBox(.1f, 6.6f);
            groundB.createFixture(bar, 0);
            groundB.setTransform(groundB.getPosition(), 0.785398163f);
        }
        */
		
		
		
		
		//Inside of wheel collision circle
		{
			BodyDef bd = new BodyDef();
	        bd.position = new Vec2(0.f, -6.7f);
	        bd.type = BodyType.STATIC;
	        Body body = world.createBody(bd);
	        System.out.println(body);
	        CircleShape sd = new CircleShape();
	        sd.m_radius = (2f);
	        
	        body.createFixture(sd, 0);
		}

                
		wheel = new Image("images/background/Wheel.png");
        
	  //pause screen, set alpha to zero so it doesn't show up when the level starts          
        pauseBg.setAlpha(0);
        resumeBttn.setAlpha(0);
        menuBttn.setAlpha(0);
	        
        isVictory = false;
        isFail = false;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		gc.setShowFPS(false);

		
		//Image [] movementLeft =  {new Image("images/Biped/drag_wr_0.png"), new Image("images/Biped/drag_wr_1.png"), new Image("images/Biped/drag_wr_2.png"), new Image("images/Biped/drag_wr_3.png"),new Image("images/Biped/drag_wr_4.png"), new Image("images/Biped/drag_wr_5.png"), new Image("images/Biped/drag_wr_6.png"), new Image("images/Biped/drag_wr_7.png"),new Image("images/Biped/drag_wr_8.png"), new Image("images/Biped/drag_wr_9.png"), new Image("images/Biped/drag_wr_10.png"), new Image("images/Biped/drag_wr_11.png")} ;
        //Image [] movementRight =  {new Image("images/Biped/drag_wl_0.png"), new Image("images/Biped/drag_wl_1.png"), new Image("images/Biped/drag_wl_2.png"), new Image("images/Biped/drag_wl_3.png"),new Image("images/Biped/drag_wl_4.png"), new Image("images/Biped/drag_wl_5.png"), new Image("images/Biped/drag_wl_6.png"), new Image("images/Biped/drag_wl_7.png"),new Image("images/Biped/drag_wl_8.png"), new Image("images/Biped/drag_wl_9.png"), new Image("images/Biped/drag_wl_10.png"), new Image("images/Biped/drag_wl_11.png")} ;
        //Image [] movementLeftStill =  {new Image("images/Biped/drag_bl_0.png"), new Image("images/Biped/drag_bl_1.png"), new Image("images/Biped/drag_bl_2.png"), new Image("images/Biped/drag_bl_3.png")} ;
        //Image [] movementRightStill =  {new Image("images/Biped/drag_br_0.png"), new Image("images/Biped/drag_br_1.png"), new Image("images/Biped/drag_br_2.png"), new Image("images/Biped/drag_br_3.png")} ;
        //int [] duration = {50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50};  
        //int [] durationStill = {200, 200, 200, 200};
        board = new Image("images/background/TasteTheRainbow_Board.png");
        wheel = new Image("images/background/TasteTheRainbow_Wheel_Frame.png");
        green = new Image("images/droplets/TasteTheRainbow_Droplet_Green.png");

        //left = new Animation(movementLeft, duration, true);
        //right = new Animation(movementRight, duration, true);
        //leftStill = new Animation(movementLeftStill, durationStill, true);
        //rightStill = new Animation(movementRightStill, durationStill, true);
        
        velocityIterations = 10;
        positionIterations = 10;
        pixelsPerMeter = 30.f;
        
        
        //background for pause screen
        pauseBg = new Image("images/pauseScreen.png");
        
        //Background for victory and failure screens
        victoryScreen = new Image("images/background/Victory.png");
        failureScreen = new Image("images/background/Failure.png");
       
        //Play and level select buttons
        resumeBttn = new Image("images/buttons/Button_Play_Neutral.png");
        resumeBttnSelect = new Image("images/buttons/Button_Play_Selected.png");
        menuBttn = new Image("images/buttons/Button_Levels_Neutral.png"); //place holder art for now
        menuBttnSelect = new Image("images/buttons/Button_Levels_Selected.png");
       
        //Victory and Fail screens
        nextBttn = new Image("images/buttons/Button_Play_Neutral.png"); //use play button image for now
        nextBttnSelect = new Image("images/buttons/Button_Play_Selected.png");
        levelBttn = new Image("images/buttons/Button_Levels_Neutral.png");
        levelBttnSelect = new Image("images/buttons/Button_Levels_Selected.png");
        replayBttn = new Image("images/buttons/Button_Restart_Neutral.png");
        replayBttnSelect = new Image("images/buttons/Button_Restart_Depressed.png");
        menuBttn2 = new Image("images/buttons/Button_Menu_Neutral.png");
        menuBttn2Select = new Image("images/buttons/Button_Menu_Depressed.png");
        
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		 //g.setBackground(Color.white);
         board.draw(-1, -1);
         
//         g.drawString(mouse, 10, 10);
         g.drawString(level, 10, 8);
         
         
         Body current = world.getBodyList();
         Vec2 center = current.getLocalCenter();
         while(current != null)
         {
             Vec2 pos = current.getPosition();
             g.pushTransform();
             g.translate(-pos.x * pixelsPerMeter + (0.5f * gc.getWidth()), 
                        -pos.y * pixelsPerMeter + (0.5f * gc.getHeight()));
             Fixture f = current.getFixtureList();
             while(f != null)
             {
                 ShapeType type = f.getType();
                 switch(type)
                 {
                     case POLYGON:
                     {
                         PolygonShape shape = (PolygonShape)f.getShape();
                         Vec2[] verts = shape.getVertices();
                         int count = shape.getVertexCount();
                         Polygon p = new Polygon();
                         for(int i = 0; i < count; i++)
                         {
                             p.addPoint(verts[i].x, verts[i].y);
                         }
                         p.setCenterX(center.x);
                         p.setCenterY(center.y);
                         p = (Polygon)p.transform(Transform.createRotateTransform(current.getAngle() + MathUtils.PI, center.x, center.y));
                         p = (Polygon)p.transform(Transform.createScaleTransform(pixelsPerMeter, pixelsPerMeter));
                         g.draw(p);
                         break;
                     }
                     case CIRCLE:
                     {
                         CircleShape shape = (CircleShape)f.getShape();
                         
                         g.setColor(Color.blue);
                         g.fillOval(-7,-7,15,15);
                         //g.drawImage(green, -10, -10);
                         break;
                         
                     }
                     default:
                 }

                 f = f.getNext();
             }
             g.popTransform();
             current = current.getNext();
         }
         
         wheel.draw(-1,400);
         
         //Pause screen
         g.drawImage(pauseBg,1,1);
         if(isMouseOverPlay)
         {
        	 g.drawImage(resumeBttnSelect, 150, 300);
        	 
         } else{
        	 g.drawImage(resumeBttn, 150, 300);
         }
         
         if(isMouseOverMenu){
        	 g.drawImage(menuBttnSelect, 150,410);
         } else{
        	 g.drawImage(menuBttn, 150, 410);
         }
         
         
         //Victory/Fail screens
         if(isVictory){
        	 g.drawImage(victoryScreen,0,220);
        	 g.drawImage(nextBttn,25,396);
        	 g.drawImage(levelBttn,160,396);
        	 g.drawImage(replayBttn,294,396);
         }
         
         if(isFail){
        	 g.drawImage(failureScreen, 0,220);
        	 g.drawImage(menuBttn2,25,396);
        	 g.drawImage(levelBttn,160,396);
        	 g.drawImage(replayBttn,294,396);
         }
         
	}
	
	

	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
        world.step((float)delta / 1000.f, velocityIterations, positionIterations);

		
		Input input = container.getInput();

         if (input.isKeyDown(Input.KEY_LEFT)==true && container.isPaused() == false)
         {
        	 wheel.setRotation(wheel.getRotation()-1);
        	 wheelArmA.setTransform(wheelArmA.getPosition(), wheelArmA.getAngle()-0.0174532925f);
        	 wheelArmB.setTransform(wheelArmB.getPosition(), wheelArmB.getAngle()-0.0174532925f);
         }
         if (input.isKeyDown(Input.KEY_RIGHT)==true && container.isPaused() == false)
         {
        	 wheel.setRotation(wheel.getRotation()+1);
        	 wheelArmA.setTransform(wheelArmA.getPosition(), wheelArmA.getAngle()+0.0174532925f);
        	 wheelArmB.setTransform(wheelArmB.getPosition(), wheelArmB.getAngle()+0.0174532925f);
         }
         
         if (input.isKeyDown(Input.KEY_A)==true && container.isPaused() == false)
         {
        	 drawDropletLeft();
         }
         if (input.isKeyDown(Input.KEY_S)==true && container.isPaused() == false)
         {
        	 drawDropletRight();
         }
         
         
         //Pause the game
         if(input.isKeyDown(Input.KEY_ESCAPE)==true && (!isVictory || !isFail))
         {
        	 pauseBg.setAlpha(20);
        	 resumeBttn.setAlpha(100);
        	 menuBttn.setAlpha(100);
        	 container.pause();
         }
         
         int xpos = input.getMouseX();
         int ypos = input.getMouseY();
         mouse = "Mouse Position x: " + xpos + "  y: " + ypos;
         
         //Resume button
         if((container.isPaused() == true) && (xpos>165 && xpos<240) && (ypos>316 && ypos<390)){
        	 if(input.isMouseButtonDown(0)){
        		 pauseBg.setAlpha(0);
        		 resumeBttn.setAlpha(0);
        		 menuBttn.setAlpha(0);
        		 container.resume();
        	 }
        	 isMouseOverPlay = true;
         }
         else{
        	 isMouseOverPlay = false;
         }
         
         //Menu button
         if((container.isPaused() == true) && (xpos>169 && xpos<239) && (ypos>414 && ypos<484)){
        	 if(input.isMouseButtonDown(0)){
        		 world.destroyBody(wheelArmA);
        		 world.destroyBody(wheelArmB);
        		 container.resume();
        		 sbg.enterState(0); //enter menu screen
        	 }
        	 isMouseOverMenu = true;
        	 
         } else{
        	 isMouseOverMenu = false;
         }
         
         
         //Victory
 		if(input.isKeyPressed(Input.KEY_V)){
 			isVictory = true;
 			isFail = false;
 			victoryScreen.setAlpha(100);
 		}
 		//next button, from victory screen
 		if(isVictory && (xpos>25 && xpos<105) && (ypos>400 && ypos<478)){
 			if(input.isMousePressed(0)){
 				if(this.getID() < sbg.getStateCount()-2){
 					world.destroyBody(wheelArmA);
 					world.destroyBody(wheelArmB);
 					sbg.enterState(this.getID() + 1); //enter level selection screen
 				}
 			}
 		}
 		

 		//levels button from victory/fail screen
 		if((xpos>161 && xpos<240) && (ypos>400  && ypos<478) && (isVictory || isFail)){
 			if(input.isMousePressed(0)){
 				world.destroyBody(wheelArmA);
 				world.destroyBody(wheelArmB);
 				sbg.enterState(-1); //enter level selection screen
 			}
 		}
 		//replay button from victory/fail screen
 		if((xpos>295 && xpos<374) && (ypos>400  && ypos<478) && (isVictory || isFail)){
 			if(input.isMousePressed(0)){
 				world.destroyBody(wheelArmA);
 				world.destroyBody(wheelArmB);
 				sbg.enterState(this.getID()); //enter level selection screen
 			}
 		}
 		
 		
 		//Fail
 		if(input.isKeyPressed(Input.KEY_F)){
 			isFail = true;
 			isVictory = false;
 			failureScreen.setAlpha(100);
 		}
 		//menu button from fail screen
 		if(isFail && (xpos>25 && xpos<105) && (ypos>400 && ypos<478)){
 			if(input.isMousePressed(0)){	
 				world.destroyBody(wheelArmA);
 				world.destroyBody(wheelArmB);
 				sbg.enterState(0); //enter menu screen				
 			}
 		}
         
	}

	public int getID() {
		
		return state;
	}
	
	private void drawDropletLeft()
	{
		//Droplet collision
		
        	BodyDef bd = new BodyDef();
            bd.position = new Vec2(.8f, 8.7f);
            bd.type = BodyType.DYNAMIC;
            Body body = world.createBody(bd);
            System.out.println(body);
            body.setAngularVelocity(0.1f);
            body.setLinearVelocity(new Vec2( 0, -5 ));
            CircleShape sd = new CircleShape();
            sd.m_radius = (.15f);
            
            body.createFixture(sd, 0);
        
	}
	
	private void drawDropletRight()
	{
		//Droplet collision
		
        	BodyDef bd = new BodyDef();
            bd.position = new Vec2(-.8f, 8.7f);
            bd.type = BodyType.DYNAMIC;
            Body body = world.createBody(bd);
            System.out.println(body);
            body.setAngularVelocity(0.1f);
            body.setLinearVelocity(new Vec2( 0, -5 ));
            CircleShape sd = new CircleShape();
            sd.m_radius = (.15f);
            
            body.createFixture(sd, 0);
        
	}
	



}
