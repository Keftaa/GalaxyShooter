package components;


import box2dLight.PointLight;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

public class LightComponent implements Component {

	public int rays; 
	public float distance;
	public float x;
	public float y;
	public Color color;
	public PointLight light;
}
