package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class RelativeSpeedComponent implements Component, Poolable {
	public boolean leader=false;
	public int groupId;
	
	@Override
	public void reset() {
		leader = false;
		groupId = -1;
		
	}
}
