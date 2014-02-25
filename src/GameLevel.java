
import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.contacts.ContactEdge;
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
	Image  boardTop, spigots, bottomBlock, wheelPanel, wheel, pauseBg, resumeBttn, resumeBttnSelect, menuBttn, menuBttnSelect, black, blue, green, orange, purple, red, white, yellow;
	Image victoryScreen, failureScreen, nextBttn, nextBttnSelect, replayBttn, replayBttnSelect, levelBttn, levelBttnSelect, menuBttn2, menuBttn2Select;
	Image backgroundImage;
	String mouse, level;
	private boolean isMouseOverPlay, isMouseOverMenu, isVictory, isFail;;
	
    private static final World world = new World(new Vec2(0, -20f));
    int velocityIterations;
    int positionIterations;
    float pixelsPerMeter;
    Body wheelArmA, wheelArmB, groundB, polygonGround;


    private LevelListStorage dropletList;
	private int state;
	
	public GameLevel(int state) {
		this.state = state;
		level = "Level " + state;
	}
	
	public GameLevel(int state, LevelListStorage list) {
		this.state = state;
		level = "Level " + state;
		dropletList = list;
		testList();
	}
	
	public void testList(){
		for(int i = 0; i < dropletList.getList(state).getCurrentLeftList().size(); i++){
			System.out.print(dropletList.getList(state).getCurrentLeftList().get(i) + " ");
		}
		for(int i = 0; i < dropletList.getList(state).getCurrentRightList().size(); i++){
			System.out.print(dropletList.getList(state).getCurrentRightList().get(i) + " ");
		}
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
		
		
		
		//Circle on bottom
		{
            BodyDef polygon = new BodyDef();

			Vec2[] vertices = {
	                new Vec2(-6.7f, -6.7f),
	                new Vec2(-6.6926f, -7f),
	                new Vec2(-6.5889f, -7.9091f),
	                new Vec2(-6.2876f, -9.0166f),
	                new Vec2(-5.7837f, -10.0820f),
	                new Vec2(-5.0911f, -11.0554f),
	                new Vec2(-4.0633f, -12f),
	                new Vec2(-3f, -12.699f),
	                new Vec2(-2f, -13.0895f),
	                new Vec2(-1f, -13.3256f),
	                new Vec2(0f, -13.4f),
	                new Vec2(1f, -13.3256f),
	                new Vec2(2f, -13.0895f),
	                new Vec2(3f, -12.699f),
	                new Vec2(4.0633f, -12f),
	                new Vec2(5.0911f, -11.0554f),
	                new Vec2(5.7837f, -10.0820f),
	                new Vec2(6.2876f, -9.0166f),
	                new Vec2(6.5889f, -7.9091f),
	                new Vec2(6.6926f, -7f),
	                new Vec2(6.7f, -6.7f),
	                new Vec2(6.7f, -13.4f),
	                new Vec2(-6.7f, -13.4f)
	        };

			ChainShape shape = new ChainShape();
	        shape.createLoop(vertices, vertices.length);
	        //shape.m_centroid.set(polygon.position);

	        FixtureDef fixtureDef = new FixtureDef();
	        fixtureDef.shape = shape;
	        fixtureDef.density = 0.5f;
	        fixtureDef.friction = 0.3f;
	        fixtureDef.restitution = 0.5f;

	        polygonGround = world.createBody(polygon);
	        polygonGround.createFixture(fixtureDef);
		}
		
		
		//Catcher / No overflow body
		{
            BodyDef polygon = new BodyDef();

			Vec2[] vertices = {
	                new Vec2(-6.7f, -6.7f),
	                new Vec2(-4.9764f, -2.2140f),
	                new Vec2(-3.7395f, -1.1408f),
	                new Vec2(-2.5824f, -0.5177f),
	                new Vec2(-6.7f, 10f)
	                
	        };

			ChainShape shape = new ChainShape();
	        shape.createLoop(vertices, vertices.length);

	        FixtureDef fixtureDef = new FixtureDef();
	        fixtureDef.shape = shape;
	        fixtureDef.density = 0.5f;
	        fixtureDef.friction = 0.3f;
	        fixtureDef.restitution = 0.5f;

	        polygonGround = world.createBody(polygon);
	        polygonGround.createFixture(fixtureDef);
		}
		
		{
            BodyDef polygon = new BodyDef();

			Vec2[] vertices = {
	                new Vec2(6.7f, -6.7f),
	                new Vec2(4.9764f, -2.2140f),
	                new Vec2(3.7395f, -1.1408f),
	                new Vec2(2.5824f, -0.5177f),
	                new Vec2(6.7f, 10f)
	                
	        };

			ChainShape shape = new ChainShape();
	        shape.createLoop(vertices, vertices.length);

	        FixtureDef fixtureDef = new FixtureDef();
	        fixtureDef.shape = shape;
	        fixtureDef.density = 0.5f;
	        fixtureDef.friction = 0.99f;
	        fixtureDef.restitution = 0.5f;

	        polygonGround = world.createBody(polygon);
	        polygonGround.createFixture(fixtureDef);
		}
		
		
		
		// Droplets
		{
			BodyDef bd = new BodyDef();
	        bd.position = new Vec2(0.f, -6.7f);
	        bd.type = BodyType.STATIC;
	        Body body = world.createBody(bd);
	        CircleShape sd = new CircleShape();
	        sd.m_radius = (2f);
	        
	        FixtureDef fd = new FixtureDef();
	        fd.shape = sd;
	        fd.density = 0.5f;
	        fd.friction = 0.99f;        
	        fd.restitution = 0.5f;

	        body.createFixture(sd, 0);
		}

                
		wheel = new Image("images/background/Wheel.png");
		wheelPanel = new Image("images/background/Game_wheelbackpanel.png");
        
        
	  //pause screen, set alpha to zero so it doesn't show up when the level starts          
        pauseBg.setAlpha(0);
        resumeBttn.setAlpha(0);
        menuBttn.setAlpha(0);
	        
        isVictory = false;
        isFail = false;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		gc.setShowFPS(false);
		backgroundImage = new Image("images/background/game_background.png");
		
		//Image [] movementLeft =  {new Image("images/Biped/drag_wr_0.png"), new Image("images/Biped/drag_wr_1.png"), new Image("images/Biped/drag_wr_2.png"), new Image("images/Biped/drag_wr_3.png"),new Image("images/Biped/drag_wr_4.png"), new Image("images/Biped/drag_wr_5.png"), new Image("images/Biped/drag_wr_6.png"), new Image("images/Biped/drag_wr_7.png"),new Image("images/Biped/drag_wr_8.png"), new Image("images/Biped/drag_wr_9.png"), new Image("images/Biped/drag_wr_10.png"), new Image("images/Biped/drag_wr_11.png")} ;
        //Image [] movementRight =  {new Image("images/Biped/drag_wl_0.png"), new Image("images/Biped/drag_wl_1.png"), new Image("images/Biped/drag_wl_2.png"), new Image("images/Biped/drag_wl_3.png"),new Image("images/Biped/drag_wl_4.png"), new Image("images/Biped/drag_wl_5.png"), new Image("images/Biped/drag_wl_6.png"), new Image("images/Biped/drag_wl_7.png"),new Image("images/Biped/drag_wl_8.png"), new Image("images/Biped/drag_wl_9.png"), new Image("images/Biped/drag_wl_10.png"), new Image("images/Biped/drag_wl_11.png")} ;
        //Image [] movementLeftStill =  {new Image("images/Biped/drag_bl_0.png"), new Image("images/Biped/drag_bl_1.png"), new Image("images/Biped/drag_bl_2.png"), new Image("images/Biped/drag_bl_3.png")} ;
        //Image [] movementRightStill =  {new Image("images/Biped/drag_br_0.png"), new Image("images/Biped/drag_br_1.png"), new Image("images/Biped/drag_br_2.png"), new Image("images/Biped/drag_br_3.png")} ;
        //int [] duration = {50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50};  
        //int [] durationStill = {200, 200, 200, 200};
		 boardTop = new Image("images/background/game_toppanel.png");
	     spigots = new Image("images/background/game_spigots.png");
	     bottomBlock = new Image("images/background/game_bottomblock.png");
         green = new Image("images/droplets/Droplet_green.png");
         black = new Image("images/droplets/Droplet_black.png");
         blue = new Image("images/droplets/Droplet_blue.png");
         orange = new Image("images/droplets/Droplet_orange.png");
         purple = new Image("images/droplets/Droplet_purple.png");
         red = new Image("images/droplets/Droplet_red.png");
         white = new Image("images/droplets/Droplet_white.png");
         yellow = new Image("images/droplets/Droplet_yellow.png");

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
		backgroundImage.draw(0,53); 
		boardTop.draw(0, 0);
		spigots.draw(161,27);
		wheelPanel.draw(0,400);
     	
         
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
                    	 switch ((int)f.m_density)
                    	 {
	                    	 case 500:
	                    	 {
	                    		 g.drawImage(black, -10, -10);
	                             break; 
	                    	 }
	                    	 case 501:
	                    	 {
	                    		 g.drawImage(blue, -10, -10);
	                             break;  
	                    	 }
	                    	 case 502:
	                    	 {
	                    		 g.drawImage(green, -10, -10);
	                             break; 
	                    	 }
	                    	 case 503:
	                    	 {
	                    		 g.drawImage(orange, -10, -10);
	                             break;  
	                    	 }
	                    	 case 504:
	                    	 {
	                    		 g.drawImage(purple, -10, -10);
	                             break; 
	                    	 }
	                    	 case 505:
	                    	 {
	                    		 g.drawImage(red, -10, -10);
	                             break;  
	                    	 }
	                    	 case 506:
	                    	 {
	                    		 g.drawImage(white, -10, -10);
	                             break; 
	                    	 }
	                    	 case 507:
	                    	 {
	                    		 g.drawImage(yellow, -10, -10);
	                             break;  
	                    	 }
                    	 }
                         
                         
                     }
                     default:
                 }
                 
                 
                 checkContacts();

                 f = f.getNext();
             }
             g.popTransform();
             current = current.getNext();
         }
         
         wheel.draw(-1,400);
         bottomBlock.draw(0,400);
         
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
        	 wheelPanel.setRotation(wheelPanel.getRotation()-1);
        	 wheelArmA.setTransform(wheelArmA.getPosition(), wheelArmA.getAngle()-0.0174532925f);
        	 wheelArmB.setTransform(wheelArmB.getPosition(), wheelArmB.getAngle()-0.0174532925f);
         }
         if (input.isKeyDown(Input.KEY_RIGHT)==true && container.isPaused() == false)
         {
        	 wheel.setRotation(wheel.getRotation()+1);
        	 wheelPanel.setRotation(wheelPanel.getRotation()+1);
        	 wheelArmA.setTransform(wheelArmA.getPosition(), wheelArmA.getAngle()+0.0174532925f);
        	 wheelArmB.setTransform(wheelArmB.getPosition(), wheelArmB.getAngle()+0.0174532925f);
         }
         
         if (input.isKeyDown(Input.KEY_A)==true && container.isPaused() == false)
         {
        	 drawDropletLeft(501);
         }
         if (input.isKeyDown(Input.KEY_S)==true && container.isPaused() == false)
         {
        	 drawDropletRight(505);
         }
         if (input.isKeyDown(Input.KEY_D)==true && container.isPaused() == false)
         {
        	 drawDropletRight(507);
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

	public int getID() 
	{	
		return state;
	}
	
	private void drawDropletLeft(int num)
	{
        new Droplet (world, "left", num);  	   
	}
	
	private void drawDropletRight(int num)
	{
        new Droplet (world, "right", num);  	        
	}
	
	private void checkContacts()
	{
		Contact edge = world.getContactList();
		while (edge != null)
		{

			Fixture f1 = edge.getFixtureA();
			Fixture f2 = edge.getFixtureB();
			if(f1.m_density>=500 && f2.m_density >= 500)
			{
				if(f1.m_density != f2.m_density)
				{
					changeColors(f1, f2);
					changeColors(f2, f1);
				}
			}
			
		edge = edge.getNext();
		}
		
	}
	
	private void changeColors(Fixture f1, Fixture f2)
	{
		
		// blue and red
		if (f1.m_density == 501 && f2.m_density==505)
		{
			f1.m_density = 504;
			f2.m_density = 504;
		}
		// purple and blue
		if (f1.m_density == 504 && f2.m_density==501)
		{
			f1.m_density = 504;
			f2.m_density = 504;
		}
		//red and purple
		if (f1.m_density == 505 && f2.m_density==504)
		{
			f1.m_density = 504;
			f2.m_density = 504;
		}
		// blue and yellow
		if (f1.m_density == 501 && f2.m_density==507)
		{
			f1.m_density = 502;
			f2.m_density = 502;
		}
		// green and blue
		if (f1.m_density == 502 && f2.m_density==501)
		{
			f1.m_density = 502;
			f2.m_density = 502;
		}
		// yellow and green
		if (f1.m_density == 507 && f2.m_density==502)
		{
			f1.m_density = 502;
			f2.m_density = 502;
		}
		// yellow and red
		if (f1.m_density == 507 && f2.m_density==505)
		{
			f1.m_density = 503;
			f2.m_density = 503;
		}
		// orange and red
		if (f1.m_density == 503 && f2.m_density==505)
		{
			f1.m_density = 503;
			f2.m_density = 503;
		}
		// orange and yellow
		if (f1.m_density == 503 && f2.m_density==507)
		{
			f1.m_density = 503;
			f2.m_density = 503;
		}
	}

}
