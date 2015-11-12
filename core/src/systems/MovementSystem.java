package systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import components.BodyComponent;
import components.PositionComponent;
import components.SpeedComponent;

public class MovementSystem extends IteratingSystem {

	private ComponentMapper<PositionComponent> positionMapper = ComponentMapper
			.getFor(PositionComponent.class);
	private ComponentMapper<SpeedComponent> speedMapper = ComponentMapper
			.getFor(SpeedComponent.class);
	private ComponentMapper<BodyComponent> bodyMapper = ComponentMapper.getFor(BodyComponent.class);
	@SuppressWarnings("unchecked")
	public MovementSystem() {
		super(Family.all(SpeedComponent.class, PositionComponent.class).get());
	}


	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		PositionComponent position = positionMapper.get(entity);
		SpeedComponent speed = speedMapper.get(entity);
		if(speed.active){
			if(!position.overridenByBody){
				position.x += speed.x * deltaTime * speed.zDistance;
				position.y += speed.y * deltaTime * speed.zDistance;
			}
			else{
				BodyComponent body = bodyMapper.get(entity);
				body.body.applyForce(new Vector2(speed.x, speed.y), new Vector2(0, 0), true);
				
			}
		}
	}


}