package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class FPSDepComponent implements Component, Poolable {

	@Override
	public void reset() {
		
	}
	
	// Entities having this component can be removed if the frame rate drops below a given rate.
}
