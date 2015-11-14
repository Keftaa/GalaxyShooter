package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

//To be set on the bullet
public class BulletRateComponent implements Component, Poolable {
	public float bulletsPerSecond = 0;

	@Override
	public void reset() {
		bulletsPerSecond = 0;
		
	}
}
