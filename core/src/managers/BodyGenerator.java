package managers;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import components.BodyComponent;
import components.PositionComponent;
import components.SizeComponent;
import components.SpriteComponent;

public class BodyGenerator implements EntityListener {
	
	private ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<BodyComponent> bodyMapper = ComponentMapper.getFor(BodyComponent.class);
	private ComponentMapper<SizeComponent> sizeMapper = ComponentMapper.getFor(SizeComponent.class);
	

	private World world;
	
	
	public BodyGenerator(World world){
		this.world = world;
	}
	
	
	public void createAndAddBody(Entity owner, Vector2 size, Vector2 position){

			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.DynamicBody;
			bodyDef.position.set(position.x, position.y);
			Body body = world.createBody(bodyDef);
			
			PolygonShape polygonShape = new PolygonShape();
			polygonShape.setAsBox(size.x, size.y);
			
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = polygonShape;
			fixtureDef.density = 1;
			fixtureDef.friction = 0;
			fixtureDef.restitution = 0;
			fixtureDef.filter.groupIndex = -1;
			
			Fixture fixture = body.createFixture(fixtureDef);
			
			//sprite.sprite.setOriginCenter(); // to match the rotation
			
			body.setUserData(owner);
			fixture.setUserData(owner);
			
			polygonShape.dispose();
			
			body.setFixedRotation(true);
			
			
			owner.getComponent(BodyComponent.class).body = body;

	}

	public void createAndAddBody(Entity owner, Vector2 size, Vector2 position, Vector2[] vertices){

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(position.x, position.y);
		Body body = world.createBody(bodyDef);
		
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.set(vertices);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		fixtureDef.density = 1;
		fixtureDef.friction = 0;
		fixtureDef.restitution = 0;
		//fixtureDef.filter.groupIndex = -1;
		
		Fixture fixture = body.createFixture(fixtureDef);
		
		//sprite.sprite.setOriginCenter(); // to match the rotation
		
		body.setUserData(owner);
		fixture.setUserData(owner);
		
		polygonShape.dispose();
		
		body.setFixedRotation(true);
		
		
		owner.getComponent(BodyComponent.class).body = body;

}

	
	
	public void destroyBody(Entity owner){
		world.destroyBody(owner.getComponent(BodyComponent.class).body);
	}

	@Override
	public void entityAdded(Entity entity) {
		PositionComponent position = positionMapper.get(entity);
		SizeComponent size = sizeMapper.get(entity);
		Vector2 sizeVector = new Vector2(size.width, size.height);
		Vector2 positionVector = new Vector2(position.x, position.y);
		BodyComponent body = bodyMapper.get(entity);
		if(body.vertices == null)
			this.createAndAddBody(entity, sizeVector, positionVector);
		else
			this.createAndAddBody(entity, sizeVector, positionVector, body.vertices);
	}

	@Override
	public void entityRemoved(Entity entity) {
		
	}

}
