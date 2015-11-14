package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class SpeedComponent implements Component, Poolable {
    public float x = 0.0f;
    public float y = 0.0f;
    public boolean active = false;
    public float zDistance = 1f; // the farther, the faster, aka layering
	@Override
	public void reset() {
		x = 0;
		y = 0;
		active = false;
		zDistance = 1;
		
	}
} 