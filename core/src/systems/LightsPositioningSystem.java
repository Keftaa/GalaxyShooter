package systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import components.LightComponent;
import components.PositionComponent;
import components.RenderableComponent;

public class LightsPositioningSystem extends IteratingSystem {

	private ComponentMapper<PositionComponent> positionsMapper = ComponentMapper
			.getFor(PositionComponent.class);
	private ComponentMapper<LightComponent> lightsMapper = ComponentMapper
			.getFor(LightComponent.class);

	@SuppressWarnings("unchecked")
	public LightsPositioningSystem() {
		super(Family.all(PositionComponent.class, LightComponent.class,
				RenderableComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
			PositionComponent position = positionsMapper.get(entity);
			LightComponent light = lightsMapper.get(entity);
			
			light.light.setPosition(position.x, position.y);
	}

}
