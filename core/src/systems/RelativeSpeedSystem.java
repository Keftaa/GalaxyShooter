package systems;

import managers.RelativeSpeedManager;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import components.BodyComponent;
import components.CoupledComponent;
import components.PositionComponent;
import components.RelativeSpeedComponent;
import components.RenderableComponent;
import components.SpeedComponent;

public class RelativeSpeedSystem extends IteratingSystem {

	private ComponentMapper<SpeedComponent> speedMapper = ComponentMapper
			.getFor(SpeedComponent.class);
	private ComponentMapper<RelativeSpeedComponent> relativeSpeedMapper = ComponentMapper
			.getFor(RelativeSpeedComponent.class);

	public RelativeSpeedSystem() {
		super(Family.all(RenderableComponent.class,
				RelativeSpeedComponent.class, SpeedComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		RelativeSpeedComponent relativeSpeed = relativeSpeedMapper.get(entity);
		Entity leader = RelativeSpeedManager.speedDependenciesMap.get(relativeSpeed.groupId).first();

		for(Entity e: RelativeSpeedManager.speedDependenciesMap.get(relativeSpeed.groupId)){
			if(e!=leader){
				speedMapper.get(e).x = -leader.getComponent(BodyComponent.class).body.getLinearVelocity().x;
				speedMapper.get(e).y = -leader.getComponent(BodyComponent.class).body.getLinearVelocity().y;	
			}

		}
		

	}

}
