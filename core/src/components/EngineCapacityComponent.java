package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class EngineCapacityComponent implements Component, Poolable {
	public float maxTime = 0;
	
	@Override
	public void reset() {
		maxTime = 0;
		
	}
}
