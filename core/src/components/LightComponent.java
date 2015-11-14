package components;


import box2dLight.PointLight;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Pool.Poolable;

public class LightComponent implements Component, Poolable {

	public int rays; 
	public float distance;
	public float x;
	public float y;
	public Color color;
	public PointLight light; // auto generated
	@Override
	public void reset() {
		
		rays = 0;
		distance = 0;
		x = 0;
		y = 0;
		color = null;
		light = null;
		
	}
}
