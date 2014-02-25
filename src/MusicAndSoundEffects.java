import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class MusicAndSoundEffects extends BasicGameState {

	private Music music;
	private Sound sound;
	
	private int state;
	
	public MusicAndSoundEffects(int state) {
		this.state = state;
	}
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		music = new Music("resources/MenuMusic4.wav");
		music.setVolume(0.5f);
		music.loop();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return state;
	}

}
