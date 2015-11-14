package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class HealthComponent implements Component, Poolable {

	public int hp=0; 
	public int damageTaken = 0;
	
	@Override
	public void reset() {
		hp=0;
		damageTaken = 0;
		
	}

}
