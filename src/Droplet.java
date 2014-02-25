import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;


public class Droplet {

	/* 
	 * black == 500
	 * blue == 501
	 * green == 502
	 * orange == 503
	 * purple == 504
	 * red == 505
	 * white == 506
	 * yellow == 507
	 */
	int color;
	
	public Droplet (World world, String initPos, int initColor)
	{
		color = initColor;
		
		BodyDef bd = new BodyDef();
		if (initPos.equals("left"))
		{
			bd.position = new Vec2(.8f, 8.7f);
		}
		else
		{
            bd.position = new Vec2(-.8f, 8.7f);
		}
        bd.type = BodyType.DYNAMIC;
        Body body = world.createBody(bd);
        body.setAngularVelocity(0.1f);
        body.setLinearVelocity(new Vec2( 0, -5 ));
        CircleShape sd = new CircleShape();
        sd.m_radius = (.17f);
        
        FixtureDef fd = new FixtureDef();
        fd.shape = sd;
        fd.density = color;
        fd.friction = 1;        
        fd.restitution = 0;
        
        body.createFixture(fd);
	}
	
}
