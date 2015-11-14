package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class SizeComponent implements Component, Poolable {
	public float width=0f;
	public float height=0f;
	@Override
	public void reset() {
		width = 0;
		height = 0;
		
	}
}
