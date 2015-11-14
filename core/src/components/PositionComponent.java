package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class PositionComponent implements Component, Poolable {
    public float x = 0.0f;
    public float y = 0.0f;
    public boolean overridenByBody = false;
    
	@Override
	public void reset() {
		x=0;
		y=0;
		overridenByBody = false;
		
	}
}
