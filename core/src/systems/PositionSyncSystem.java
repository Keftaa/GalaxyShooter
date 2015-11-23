package systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import components.BodyComponent;
import components.PositionComponent;
import components.SpriteComponent;

public class PositionSyncSystem extends IteratingSystem {
	
	private ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<BodyComponent> bodyMapper = ComponentMapper.getFor(BodyComponent.class);
	
	public PositionSyncSystem() {
		super(Family.all(PositionComponent.class, BodyComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		BodyComponent body = bodyMapper.get(entity);
		PositionComponent position = positionMapper.get(entity);
		if(body.body==null) return;
		if(body.body.getPosition().x != position.x || body.body.getPosition().y != position.y){
			if(position.overridenByBody){
				position.x = body.body.getPosition().x;
				position.y = body.body.getPosition().y;
			}
			else{
				body.body.setTransform(position.x,  position.y, body.body.getAngle());
			}
			

			
		}
		
	}
	
	
}
