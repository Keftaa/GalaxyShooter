package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class OutOfBoundsComponent implements Component, Poolable {
	
	public AdequateAction action;
	
	public enum AdequateAction{
		NONE, RESPAWN, ALERT, DISPOSE;
	}

	@Override
	public void reset() {
		action = null;
		
	}

}
