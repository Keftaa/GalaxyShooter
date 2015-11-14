package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

// Specifies how much damage it will cause on the body it hits
public class ContactDamageComponent implements Component, Poolable {
	public int damagePoints=0;

	@Override
	public void reset() {
		damagePoints=0;
		
	}
}
