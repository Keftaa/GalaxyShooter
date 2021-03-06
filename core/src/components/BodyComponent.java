package components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.ashley.core.Component;

public class BodyComponent implements Component, Poolable {
	public Body body;
	public boolean applyingForce = false;
	public Vector2[] vertices;
	public boolean contactListener = false;
	
	@Override
	public void reset() {
		applyingForce = false;
		vertices = null;
		contactListener = false;
	}
}
