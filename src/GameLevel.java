

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.PolygonShape;
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
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.jbox2d.collision.shapes.CircleShape;
import org.newdawn.slick.geom.Transform;




public class GameLevel extends BasicGameState {

	Animation sprite, left, right, leftStill, rightStill, rest;
	Image  boardTop, spigots, bottomBlock, wheelPanel, wheel, pauseBg, resumeBttn, resumeBttnSelect, menuBttn, menuBttnSelect, black, blue, green, orange, purple, red, white, yellow;
	Image victoryScreen, failureScreen, nextBttn, nextBttnSelect, replayBttn, replayBttnSelect, levelBttn, levelBttnSelect, menuBttn2, menuBttn2Select, star;
	Image backgroundImage;
	String mouse, level;
	private boolean isMouseOverPlay, isMouseOverMenu, isVictory, isFail, isMouseOverReplay, isMouseOverLevels, isMouseOverNext;
	private int leftCount = 0;	
	private int rightCount = 0;	
	private int leftColor = 0;	
	private int rightColor = 0;	
	
	//music
	private Sound sound;
	
    private static final World world = new World(new Vec2(0, -2000f));
    int velocityIterations;
    int positionIterations;
    float pixelsPerMeter;
    Body wheelArmA, wheelArmB, groundB, polygonGround;
    
    //Timer and listener for droplets
	int delay = 2550; //milliseconds
	int rightListCount = 0;
	int leftListCount = 0;
	ActionListener taskPerformer = new ActionListener() 
	{
		public void actionPerformed(ActionEvent evt) 
		{
			parseList();
	  	}
	};
	Timer timer = new Timer(delay, taskPerformer);
    
    private LevelListStorage dropletList; //holds the list of what droplets each level has
	private int state;
	private int rating;
	
	public GameLevel(int state) {
		this.state = state;
		level = "Level " + state;
	}
	
	public GameLevel(int state, LevelListStorage list) {
		this.state = state;
		level = "Level " + state;
		dropletList = list;
//		testList();
	}
	
	public void testList(){
		System.out.println("Level: " + dropletList.getList(state).getLevelInfo());
		for(int i = 0; i < dropletList.getList(state).getCurrentLeftList().size(); i++){
			System.out.print(dropletList.getList(state).getCurrentLeftList().get(i) + " ");
		}
		System.out.println();
		for(int i = 0; i < dropletList.getList(state).getCurrentRightList().size(); i++){
			System.out.print(dropletList.getList(state).getCurrentRightList().get(i) + " ");
		}
		System.out.println();
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException{
		leftListCount = 0;
		rightListCount = 0;
		
		//need to destory previous droplets whenever we enter the game level
		Body current = world.getBodyList();
			while(current != null){
				Fixture f = current.getFixtureList();
				while(f !=null){
					ShapeType type = f.getType();
					switch(type){
						case CIRCLE:
							{
								world.destroyBody(current);
							break;}
						default:
							break;
					}
					f = f.getNext();
				}
				current = current.getNext();
			}
		//arms
		{
            BodyDef wheelArm1 = new BodyDef();
            wheelArm1.active = true;
            wheelArm1.position = new Vec2(0.f, -6.7f);
            wheelArm1.type = BodyType.STATIC;
            wheelArmA = world.createBody(wheelArm1);
            PolygonShape bar = new PolygonShape();
            bar.setAsBox(7f, 0.2f);
            wheelArmA.createFixture(bar, 0);
        }
		{
            BodyDef wheelArm2 = new BodyDef();
            wheelArm2.active = true;
            wheelArm2.position = new Vec2(0.f, -6.7f);
            wheelArm2.type = BodyType.STATIC;
            wheelArmB = world.createBody(wheelArm2);
            PolygonShape bar = new PolygonShape();
            bar.setAsBox(.2f, 7f);
            wheelArmB.createFixture(bar, 0);
        }
		
		{
            BodyDef sensor1 = new BodyDef();
            
            Vec2[] vertices = {
	                new Vec2(0f, -6.7f),
	                new Vec2(0f, 0f),
	                new Vec2(-6.7f, 0f),
	                new Vec2(-6.7f, -6.7f)
	                
	        };
            
            ChainShape shape = new ChainShape();
	        shape.createLoop(vertices, vertices.length);
	        
	        FixtureDef fixtureDef = new FixtureDef();
	        fixtureDef.shape = shape;
	        fixtureDef.density = 0.5f;
	        fixtureDef.friction = 0.3f;
	        fixtureDef.restitution = 0.5f;
	        fixtureDef.isSensor = true;

	        polygonGround = world.createBody(sensor1);
	        polygonGround.createFixture(fixtureDef);
	        
	        Vec2 localAnchor = new Vec2(0f, -6.7f);
	        RevoluteJointDef anchor = new RevoluteJointDef();
	        anchor.localAnchorA = localAnchor;
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
		wheelPanel = new Image("images/background/Game_wheelbackpanel_FINAL.png");
        
        
	  //pause screen, set alpha to zero so it doesn't show up when the level starts          
        pauseBg.setAlpha(0);
        resumeBttn.setAlpha(0);
        menuBttn.setAlpha(0);
	        
        isVictory = false;
        isFail = false;
        
        //Initiate falling of droplets
        timer.start();
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		gc.setShowFPS(false);
		backgroundImage = new Image("images/background/Game_background_final.png");
		sound = new Sound("resources/Water Drop.wav");
		
		//Hamster Animation
		Image [] movementLeft =  {new Image("images/hamster/Run_left_final_01.png"), new Image("images/hamster/Run_left_final_02.png"), new Image("images/hamster/Run_left_final_03.png"), new Image("images/hamster/Run_left_final_04.png"),new Image("images/hamster/Run_left_final_05.png"), new Image("images/hamster/Run_left_final_06.png"), new Image("images/hamster/Run_left_final_07.png"), new Image("images/hamster/Run_left_final_08.png")} ;
        Image [] movementRight =  {new Image("images/hamster/Run_final_01.png"), new Image("images/hamster/Run_final_02.png"), new Image("images/hamster/Run_final_03.png"), new Image("images/hamster/Run_final_04.png"),new Image("images/hamster/Run_final_05.png"), new Image("images/hamster/Run_final_06.png"), new Image("images/hamster/Run_final_07.png"), new Image("images/hamster/Run_final_08.png")} ;
        Image [] movementLeftStill =  {new Image("images/hamster/Idle_left_final_01.png"), new Image("images/hamster/Idle_left_final_02.png"),new Image("images/hamster/Idle_left_final_03.png"), new Image("images/hamster/Idle_left_final_04.png")};
        Image [] movementRightStill =  {new Image("images/hamster/Idle_final_01.png"), new Image("images/hamster/Idle_final_02.png"),new Image("images/hamster/Idle_final_03.png"), new Image("images/hamster/Idle_final_04.png")};
        Image [] movementRest = {new Image("images/hamster/Rest_final_01.png"), new Image("images/hamster/Rest_final_02.png"), new Image("images/hamster/Rest_final_03.png"), new Image("images/hamster/Rest_final_04.png")};
        int [] duration = {50, 50, 50, 50, 50, 50, 50, 50};  
        int [] durationStill = {200, 200, 200, 200};
        left = new Animation(movementLeft, duration, true);
        right = new Animation(movementRight, duration, true);
        leftStill = new Animation(movementLeftStill, durationStill, true);
        rightStill = new Animation(movementRightStill, durationStill, true);
        rest = new Animation(movementRest, durationStill, true);
        sprite = rightStill;
        
		 boardTop = new Image("images/background/Game_Panel_final.png");
	     spigots = new Image("images/background/Game_spigots_final.png");
	     bottomBlock = new Image("images/background/Game_bottomblock_final.png");
	     
         green = new Image("images/droplets/Droplet_green.png");
         black = new Image("images/droplets/Droplet_black.png");
         blue = new Image("images/droplets/Droplet_blue.png");
         orange = new Image("images/droplets/Droplet_orange.png");
         purple = new Image("images/droplets/Droplet_purple.png");
         red = new Image("images/droplets/Droplet_red.png");
         white = new Image("images/droplets/Droplet_white.png");
         yellow = new Image("images/droplets/Droplet_yellow.png");
         star = new Image("images/Stars.png");
        
        velocityIterations = 10;
        positionIterations = 10;
        pixelsPerMeter = 30.f;
        
        
        //background for pause screen
        pauseBg = new Image("images/background/Paused_final.png");
        
        //Background for victory and failure screens
        victoryScreen = new Image("images/background/Victory_final.png");
        failureScreen = new Image("images/background/Failure_final.png");
       
        //Play and level select buttons
        resumeBttn = new Image("images/buttons/Play_neutral_final.png");
        resumeBttnSelect = new Image("images/buttons/Play_pressed_final.png");
        menuBttn = new Image("images/buttons/Menu_neutral_final.png"); 
        menuBttnSelect = new Image("images/buttons/Menu_pressed_final.png");
       
        //Victory and Fail screens
        nextBttn = new Image("images/buttons/Play_neutral_final.png"); 
        nextBttnSelect = new Image("images/buttons/Play_pressed_final.png");
        levelBttn = new Image("images/buttons/Levels_neutral_final.png");
        levelBttnSelect = new Image("images/buttons/Levels_pressed_final.png");
        replayBttn = new Image("images/buttons/Retry_neutral_final.png");
        replayBttnSelect = new Image("images/buttons/Retry_pressed_final.png");
        menuBttn2 = new Image("images/buttons/Menu_neutral_final.png");
        menuBttn2Select = new Image("images/buttons/Menu_pressed_final.png");

		
        
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		backgroundImage.draw(0,53); 
		boardTop.draw(0, 0);
		spigots.draw(154,27);
		//spigots.draw(161,27);
		wheelPanel.draw(0,400);
		sprite.draw(138,541); //hamster
         
         g.drawString(level, 10, 8);
         
         // droplets drawing size=22
//         g.drawImage(red, 6, 29);
//         g.drawImage(green, 28, 29);
//         g.drawImage(blue, 50, 29);
//         g.drawImage(orange, 72, 29);
//         g.drawImage(purple, 94, 29);
//         g.drawImage(white, 116, 29);
//         g.drawImage(yellow, 138, 29);
//         
//         g.drawImage(red, 373, 29);
//         g.drawImage(green, 351, 29);
//         g.drawImage(blue, 329, 29);
//         g.drawImage(orange, 307, 29);
//         g.drawImage(purple, 285, 29);
//         g.drawImage(white, 263, 29);
//         g.drawImage(yellow, 241, 29);

         
         int leftCount = 138;
         for(int i = 0; i < dropletList.getList(state).getCurrentLeftList().size(); i++)
         {
        	 if (dropletList.getList(state).getCurrentLeftList().get(i).equals("r"))
 			{
        		 g.drawImage(red, leftCount, 29);
        		 leftCount = leftCount-22;
 			}
 			if (dropletList.getList(state).getCurrentLeftList().get(i).equals("b"))
 			{
 				g.drawImage(blue, leftCount, 29);
 				leftCount = leftCount-22;
 			}
 			if (dropletList.getList(state).getCurrentLeftList().get(i).equals("y"))
 			{
 				g.drawImage(yellow, leftCount, 29);
 				leftCount = leftCount-22;
 			}
 			if (dropletList.getList(state).getCurrentLeftList().get(i).equals("p"))
 			{
 				g.drawImage(purple, leftCount, 29);
 				leftCount = leftCount-22;
 			}
 			if (dropletList.getList(state).getCurrentLeftList().get(i).equals("g"))
 			{
 				g.drawImage(green, leftCount, 29);
 				leftCount = leftCount-22;
 			}
 			if (dropletList.getList(state).getCurrentLeftList().get(i).equals("o"))
 			{
 				g.drawImage(orange, leftCount, 29);
 				leftCount = leftCount-22;
 			}
 			if (dropletList.getList(state).getCurrentLeftList().get(i).equals("bl"))
 			{
 				g.drawImage(black, leftCount, 29);
 				leftCount = leftCount-22;
 			}
 			if (dropletList.getList(state).getCurrentLeftList().get(i).equals("w"))
 			{
 				g.drawImage(white, leftCount, 29);
 				leftCount = leftCount-22;
 			}
 		}
         
         //leftCount = 138;
        
         int rightCount = 241;
         for(int i = 0; i < dropletList.getList(state).getCurrentRightList().size(); i++)
         {
        	 if (dropletList.getList(state).getCurrentRightList().get(i).equals("r"))
 			{
        		 g.drawImage(red, rightCount, 29);
        		 rightCount = rightCount +22;
 			}
 			if (dropletList.getList(state).getCurrentRightList().get(i).equals("b"))
 			{
 				g.drawImage(blue, rightCount, 29);
 				rightCount = rightCount +22;
 			}
 			if (dropletList.getList(state).getCurrentRightList().get(i).equals("y"))
 			{
 				g.drawImage(yellow, rightCount, 29);
 				rightCount = rightCount +22;
 			}
 			if (dropletList.getList(state).getCurrentRightList().get(i).equals("p"))
 			{
 				g.drawImage(purple, rightCount, 29);
 				rightCount = rightCount +22;
 			}
 			if (dropletList.getList(state).getCurrentRightList().get(i).equals("g"))
 			{
 				g.drawImage(green, rightCount, 29);
 				rightCount = rightCount +22;
 			}
 			if (dropletList.getList(state).getCurrentRightList().get(i).equals("o"))
 			{
 				g.drawImage(orange, rightCount, 29);
 				rightCount = rightCount +22;
 			}
 			if (dropletList.getList(state).getCurrentRightList().get(i).equals("bl"))
 			{
 				g.drawImage(black, rightCount, 29);
 				rightCount = rightCount +22;
 			}
 			if (dropletList.getList(state).getCurrentRightList().get(i).equals("x"))
 			{
 				g.drawImage(white, rightCount, 29);
 				rightCount = rightCount +22;
 			}
 		}

         
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
                         //g.draw(p);
                         break;
                     }
                     case CIRCLE:
                     {
                    	 
                    	 Vec2 currentVel = current.getLinearVelocity();
                    	 float x = currentVel.x;
                    	 float y = currentVel.y;
                    	 
                    	 
                    	 float minX = -2.0f;
                    	 float maxX = 2.0f;

                    	 Random rand = new Random();
                    	 
                    	 float randomFloat = rand.nextFloat() * (maxX - minX) + minX;
                    	 if (x<0.1f&&x>-0.1f)
                    	 {
                    		 current.setLinearVelocity(new Vec2( x+randomFloat, y+10 ));
                    	 }
                    	 
                    	 
                    	 switch ((int)f.m_density)
                    	 {
	                    	 case 500:
	                    	 {
	                    		 Circle circle = new Circle(0,0,10);
	                    		 g.setColor(Color.black);
	                    		 g.draw(circle);
	                    		 g.fill(circle);
	                             break; 
	                    	 }
	                    	 case 501:
	                    	 {
	                    		 Circle circle = new Circle(0,0,10);
	                    		 g.setColor(Color.blue);
	                    		 g.draw(circle);
	                    		 g.fill(circle);
	                             break;  
	                    	 }
	                    	 case 502:
	                    	 {
	                    		 Circle circle = new Circle(0,0,10);
	                    		 g.setColor(Color.green);
	                    		 g.draw(circle);
	                    		 g.fill(circle);
	                             break; 
	                    	 }
	                    	 case 503:
	                    	 {
	                    		 Circle circle = new Circle(0,0,10);
	                    		 g.setColor(Color.orange);
	                    		 g.draw(circle);
	                    		 g.fill(circle);
	                             break;  
	                    	 }
	                    	 case 504:
	                    	 {
	                    		 Circle circle = new Circle(0,0,10);
	                    		 g.setColor(Color.magenta);
	                    		 g.draw(circle);
	                    		 g.fill(circle);
	                             break; 
	                    	 }
	                    	 case 505:
	                    	 {
	                    		 Circle circle = new Circle(0,0,10);
	                    		 g.setColor(Color.red);
	                    		 g.draw(circle);
	                    		 g.fill(circle);
	                             break;  
	                    	 }
	                    	 case 506:
	                    	 {
	                    		 Circle circle = new Circle(0,0,10);
	                    		 g.setColor(Color.white);
	                    		 g.draw(circle);
	                    		 g.fill(circle);
	                             break; 
	                    	 }
	                    	 case 507:
	                    	 {
	                    		 Circle circle = new Circle(0,0,10);
	                    		 g.setColor(Color.yellow);
	                    		 g.draw(circle);
	                    		 g.fill(circle);
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
         bottomBlock.draw(0,500);
         
         //Pause screen
         g.drawImage(pauseBg,0,205);
         if(isMouseOverPlay)
         {
        	 g.drawImage(resumeBttnSelect, 25, 396);
        	 
         } else{
        	 g.drawImage(resumeBttn, 25, 396);
         }
         if(isMouseOverMenu){
        	 g.drawImage(menuBttnSelect, 160,396);
         } else{
        	 g.drawImage(menuBttn, 160, 396);
         }
         if(gc.isPaused()){
        	 if(isMouseOverReplay){
        		 g.drawImage(replayBttnSelect, 294,396);
        	 }
        	 else
        		 g.drawImage(replayBttn,294,396);
         }
         
         
         //Victory
         if(isVictory){
        	 g.drawImage(victoryScreen,0,205);
        	 if(isMouseOverNext)
        		 g.drawImage(nextBttnSelect, 25,396);
        	 else
        		 g.drawImage(nextBttn,25,396);
        	 
        	 if(isMouseOverLevels)
        		 g.drawImage(levelBttnSelect,160,396);
        	 else
        		 g.drawImage(levelBttn,160,396);
        	 
        	 if(isMouseOverReplay)
        		 g.drawImage(replayBttnSelect, 294,396);
        	 else
        		 g.drawImage(replayBttn,294,396);
        	 
        	 switch(getRating()){
	        	 case 1:
	      	 		g.drawImage(star,25,318);
	      	 		break;
	      	 	case 2:
	      	 		g.drawImage(star,25,318);
	      	 		g.drawImage(star,140,318);
	      	 		break;
	      	 	case 3:
	      	 		g.drawImage(star,45,318);
	      	 		g.drawImage(star,140,318);
	      	 		g.drawImage(star,234,318);
	      	 	default:
     	 			break;
        	}
         }
         //Fail
         if(isFail){
        	 g.drawImage(failureScreen, 0,205);
        	 if(isMouseOverMenu)
        		 g.drawImage(menuBttn2Select,25,396);
        	 else
        		 g.drawImage(menuBttn2,25,396);
        	 
        	 if(isMouseOverLevels)
        		 g.drawImage(levelBttnSelect,160,396);
        	 else
        		 g.drawImage(levelBttn,160,396);
        	 
        	 if(isMouseOverReplay)
        		 g.drawImage(replayBttnSelect, 294,396);
        	 else
        		 g.drawImage(replayBttn,294,396);
         }
         
	}
	
	

	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
       
		world.step((float)delta/25000f, velocityIterations, positionIterations);
		
		Input input = container.getInput();
		int xpos = input.getMouseX();
	    int ypos = input.getMouseY();
	    mouse = "Mouse Position x: " + xpos + "  y: " + ypos;

         if (input.isKeyDown(Input.KEY_LEFT)==true && container.isPaused() == false)
         {
        	 wheel.setRotation(wheel.getRotation()-1);
        	 wheelPanel.setRotation(wheelPanel.getRotation()-1);
        	 wheelArmA.setTransform(wheelArmA.getPosition(), wheelArmA.getAngle()-0.0174532925f);
        	 wheelArmB.setTransform(wheelArmB.getPosition(), wheelArmB.getAngle()-0.0174532925f);
        	 sprite = left;
         }
         if(sprite == left && input.isKeyDown(Input.KEY_LEFT)==false){
        	 sprite = leftStill;
         }
         if (input.isKeyDown(Input.KEY_RIGHT)==true && container.isPaused() == false)
         {
        	 wheel.setRotation(wheel.getRotation()+1);
        	 wheelPanel.setRotation(wheelPanel.getRotation()+1);
        	 wheelArmA.setTransform(wheelArmA.getPosition(), wheelArmA.getAngle()+0.0174532925f);
        	 wheelArmB.setTransform(wheelArmB.getPosition(), wheelArmB.getAngle()+0.0174532925f);
        	 sprite = right;
         }
         if(sprite == right && input.isKeyDown(Input.KEY_RIGHT)==false){
        	 sprite = rightStill;
         }
         
         //Timer controls, the droplet flow still spontaneously stops if the timer is paused at any point
         //The timer can temporarily be restarted but will eventually stop again
         
         //Stop the Timer
         if (input.isKeyDown(Input.KEY_X)==true && container.isPaused() == false)
         {
        	 timer.stop();
         }
         
         //Start the Timer
         if (input.isKeyDown(Input.KEY_Z)==true && container.isPaused() == false)
         {
        	 timer.stop();
        	 timer.start();
         }
         
         //Stops the Timer at any sort of menu
         if(container.isPaused() == true || isVictory== true || isFail== true)
         {
        	 timer.stop();
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
         if(input.isKeyDown(Input.KEY_ESCAPE)==true && isVictory==false && isFail==false)
         {
        	 pauseBg.setAlpha(20);
        	 resumeBttn.setAlpha(100);
        	 menuBttn.setAlpha(100);
        	 container.pause();
        	 sound.stop();
        	 sprite = rest;
        	 
         }
         //Resume button
         if(container.isPaused() && (xpos>25 && xpos<105) && (ypos>400 && ypos<478)){
        	 if(input.isMouseButtonDown(0)){
        		 pauseBg.setAlpha(0);
        		 resumeBttn.setAlpha(0);
        		 menuBttn.setAlpha(0);
        		 container.resume();
        		 timer.start();
        	 }
        	 isMouseOverPlay = true;
         }
         else{
        	 isMouseOverPlay = false;
         }
         //Menu button
         if(container.isPaused() && (xpos>169 && xpos<239) && (ypos>414 && ypos<484)){
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
         //replay
         if((xpos>295 && xpos<374) && (ypos>400  && ypos<478) && container.isPaused()){
  			isMouseOverReplay = true;
        	 if(input.isMousePressed(0)){
  				world.destroyBody(wheelArmA);
  				world.destroyBody(wheelArmB);
  				container.resume();
  				sbg.enterState(this.getID()); //enter level selection screen
  			}
  		}else{
  			isMouseOverReplay = false;
  		}
         
         //Victory
 		if(input.isKeyPressed(Input.KEY_V)){
 			isVictory = true;
 			isFail = false;
 			victoryScreen.setAlpha(100);
 			sound.stop();
 			setRating(3); //set the player's rating for number of stars they get
 		}
 		//next button, from victory screen
 		if(isVictory && (xpos>25 && xpos<105) && (ypos>400 && ypos<478)){
 			if(input.isMousePressed(0)){
 				if(this.getID() < sbg.getStateCount()-2){
 					world.destroyBody(wheelArmA);
 					world.destroyBody(wheelArmB);
 					sbg.enterState(this.getID() + 1); //enter next level
 				}
 			}
 			isMouseOverNext = true;
 		} else{
 			isMouseOverNext = false;
 		}
 		

 		//levels button from victory/fail screen
 		if((xpos>161 && xpos<240) && (ypos>400  && ypos<478) && (isVictory || isFail)){
 			if(input.isMousePressed(0)){
 				world.destroyBody(wheelArmA);
 				world.destroyBody(wheelArmB);
 				sbg.enterState(-1); //enter level selection screen
 			}
 			isMouseOverLevels = true;
 		} else{
 			isMouseOverLevels = false;
 		}
 		//replay button from victory/fail screen
 		if((xpos>295 && xpos<374) && (ypos>400  && ypos<478) && (isVictory || isFail)){
 			if(input.isMousePressed(0)){
 				world.destroyBody(wheelArmA);
 				world.destroyBody(wheelArmB);
 				sbg.enterState(this.getID()); //re-enter game level state
 			}
 			isMouseOverReplay = true;
 		}
 		
 		
 		//Fail
 		if(input.isKeyPressed(Input.KEY_F)){
 			isFail = true;
 			isVictory = false;
 			failureScreen.setAlpha(100);
 			sound.stop();
 		}
 		//menu button from fail screen
 		if(isFail && (xpos>25 && xpos<105) && (ypos>400 && ypos<478)){
 			if(input.isMousePressed(0)){	
 				world.destroyBody(wheelArmA);
 				world.destroyBody(wheelArmB);
 				sbg.enterState(0); //enter menu screen				
 			}
 			isMouseOverMenu = true;
 		}
 		
 		if(leftCount>0)
 		{
 			new Droplet (world, "left", leftColor);
 			leftCount = leftCount-1;
 		}
 		
 		if(rightCount>0)
 		{
 			new Droplet (world, "right", rightColor);
 			rightCount = rightCount-1;
 		}
         
	}

	public int getID() 
	{	
		return state;
	}
	
	private void parseList()
	{
		if(leftListCount < dropletList.getList(this.state).getCurrentLeftList().size())
		{
			sound.play();
			if (dropletList.getList(this.state).getCurrentLeftList().get(leftListCount).equals("r"))
			{
				drawDropletLeft(505);
			}
			
			if (dropletList.getList(this.state).getCurrentLeftList().get(leftListCount).equals("b"))
			{
				drawDropletLeft(501);
			}
			
			if (dropletList.getList(this.state).getCurrentLeftList().get(leftListCount).equals("y"))
			{
				drawDropletLeft(507);
			}
			
			if (dropletList.getList(this.state).getCurrentLeftList().get(leftListCount).equals("p"))
			{
				drawDropletLeft(504);
			}
			
			if (dropletList.getList(this.state).getCurrentLeftList().get(leftListCount).equals("g"))
			{
				drawDropletLeft(502);
			}
			
			if (dropletList.getList(this.state).getCurrentLeftList().get(leftListCount).equals("o"))
			{
				drawDropletLeft(503);
			}
			
			if (dropletList.getList(this.state).getCurrentLeftList().get(leftListCount).equals("bl"))
			{
				drawDropletLeft(500);
			}
			
			if (dropletList.getList(this.state).getCurrentLeftList().get(leftListCount).equals("w"))
			{
				drawDropletLeft(506);
			}
			
			leftListCount++;
		}
		
		if(rightListCount < dropletList.getList(this.state).getCurrentRightList().size())
		{
			sound.play();
			if (dropletList.getList(this.state).getCurrentRightList().get(rightListCount).equals("r"))
			{
				drawDropletRight(505);
			}
			
			if (dropletList.getList(this.state).getCurrentRightList().get(rightListCount).equals("b"))
			{
				drawDropletRight(501);
			}
			
			if (dropletList.getList(this.state).getCurrentRightList().get(rightListCount).equals("y"))
			{
				drawDropletRight(507);
			}
			
			if (dropletList.getList(this.state).getCurrentRightList().get(rightListCount).equals("p"))
			{
				drawDropletRight(504);
			}
			
			if (dropletList.getList(this.state).getCurrentRightList().get(rightListCount).equals("g"))
			{
				drawDropletRight(502);
			}
			
			if (dropletList.getList(this.state).getCurrentRightList().get(rightListCount).equals("o"))
			{
				drawDropletRight(503);
			}
			
			if (dropletList.getList(this.state).getCurrentRightList().get(rightListCount).equals("bl"))
			{
				drawDropletRight(500);
			}
			
			if (dropletList.getList(this.state).getCurrentRightList().get(rightListCount).equals("w"))
			{
				drawDropletRight(506);
			}
			
			rightListCount++;
		}
		sound.stop();
	}
	
	private void drawDropletLeft(int num)
	{
		leftColor = num;
		leftCount = 7;

	}
	
	private void drawDropletRight(int num)
	{
		
		rightColor = num;
		rightCount = 7; 
		
	}
	
	private void checkContacts()
	{
		Contact edge = world.getContactList();
		while (edge != null)
		{

			Fixture f1 = edge.getFixtureA();
			Fixture f2 = edge.getFixtureB();
			if(f1.m_density >= 500 && f2.m_density >= 500)
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
		{f1.m_density = 504;f2.m_density = 504;}
		// blue and purple
		if (f1.m_density == 504 && f2.m_density==501)
		{f1.m_density = 504;f2.m_density = 504;}
		// blue and yellow
		if (f1.m_density == 501 && f2.m_density==507)
		{f1.m_density = 502;f2.m_density = 502;}
		// blue and green
		if (f1.m_density == 502 && f2.m_density==501)
		{f1.m_density = 502;f2.m_density = 502;}
		// blue and orange
		if (f1.m_density == 503 && f2.m_density==501)
		{f1.m_density = 500;f2.m_density = 500;}
		// blue and black
		if (f1.m_density == 500 && f2.m_density==501)
		{f1.m_density = 500;f2.m_density = 500;}
		// blue and white
		if (f1.m_density == 506 && f2.m_density==501)
		{f1.m_density = 501;f2.m_density = 501;}
		
		
		//red and purple
		if (f1.m_density == 505 && f2.m_density==504)
		{f1.m_density = 504;f2.m_density = 504;}
		// red and yellow
		if (f1.m_density == 507 && f2.m_density==505)
		{f1.m_density = 503;f2.m_density = 503;}
		// red and orange
		if (f1.m_density == 503 && f2.m_density==505)
		{f1.m_density = 503;f2.m_density = 503;}
		// red and black
		if (f1.m_density == 500 && f2.m_density==505)
		{f1.m_density = 500;f2.m_density = 500;}
		// red and green
		if (f1.m_density == 502 && f2.m_density==505)
		{f1.m_density = 500;f2.m_density = 500;}
		// red and white
		if (f1.m_density == 506 && f2.m_density==505)
		{f1.m_density = 505;f2.m_density = 505;}
		
		
		// yellow and orange
		if (f1.m_density == 503 && f2.m_density==507)
		{f1.m_density = 503;f2.m_density = 503;}
		// yellow and green
		if (f1.m_density == 507 && f2.m_density==502)
		{f1.m_density = 502;f2.m_density = 502;}
		// yellow and black
		if (f1.m_density == 500 && f2.m_density==507)
		{f1.m_density = 500;f2.m_density = 500;}
		// yellow and purple
		if (f1.m_density == 504 && f2.m_density==507)
		{f1.m_density = 500;f2.m_density = 500;}
		// yellow and white
		if (f1.m_density == 506 && f2.m_density==507)
		{f1.m_density = 507;f2.m_density = 507;}
		
		
		// black and green
		if (f1.m_density == 500 && f2.m_density==502)
		{f1.m_density = 500;f2.m_density = 500;}
		// black and orange
		if (f1.m_density == 500 && f2.m_density==503)
		{f1.m_density = 500;f2.m_density = 500;}
		// black and purple
		if (f1.m_density == 500 && f2.m_density==504)
		{f1.m_density = 500;f2.m_density = 500;}
		// black and white
		if (f1.m_density == 500 && f2.m_density==506)
		{f1.m_density = 500;f2.m_density = 500;}
		
		
		// green and purple
		if (f1.m_density == 502 && f2.m_density==504)
		{f1.m_density = 500;f2.m_density = 500;}
		// green and orange
		if (f1.m_density == 502 && f2.m_density==503)
		{f1.m_density = 500;f2.m_density = 500;}
		// green and white
		if (f1.m_density == 502 && f2.m_density==506)
		{f1.m_density = 502;f2.m_density = 502;}
		
		
		// orange and purple
		if (f1.m_density == 503 && f2.m_density==504)
		{f1.m_density = 500;f2.m_density = 500;}
		// orange and white
		if (f1.m_density == 503 && f2.m_density==506)
		{f1.m_density = 503;f2.m_density = 503;}
		
		
		// purple and white
		if (f1.m_density == 504 && f2.m_density==506)
		{f1.m_density = 504;f2.m_density = 504;}
	}
	
	private void setRating(int r){
		rating = r;
	}
	private int getRating(){
		return rating;
	}

}
