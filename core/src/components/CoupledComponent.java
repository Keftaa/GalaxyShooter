package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CoupledComponent implements Component, Poolable {
	
	public int coupleId;
	public boolean leader = false;
	public float offsetX=0f;
	public float offsetY=0f;
	@Override
	public void reset() {
		coupleId = -1;
		leader = false;
		offsetX = 0f;
		offsetY = 0f;
		
	}

}
