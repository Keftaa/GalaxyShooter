package systems;

import managers.BodyGenerator;
import managers.LightsManager;
import box2dLight.RayHandler;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

import components.BodyComponent;
import components.OutOfBoundsComponent;
import components.OutOfBoundsComponent.AdequateAction;
import components.PositionComponent;
import components.RenderableComponent;

public class OutOfBoundsSystem extends IteratingSystem {

	private FitViewport viewport;
	private LightsManager lightsManager;
	private ComponentMapper<PositionComponent> positionMapper = ComponentMapper
			.getFor(PositionComponent.class);
	private ComponentMapper<OutOfBoundsComponent> outOfBoundsMapper = ComponentMapper
			.getFor(OutOfBoundsComponent.class);
	private ComponentMapper<BodyComponent> bodyMapper = ComponentMapper.getFor(BodyComponent.class);

	private PooledEngine engine;
	private BodyGenerator bodyGenerator;
	
	public OutOfBoundsSystem(FitViewport viewport, LightsManager lightsManager, PooledEngine engine, BodyGenerator bodyGenerator) {
		super(Family.all(RenderableComponent.class, OutOfBoundsComponent.class)
				.get());
		this.viewport = viewport;
		this.lightsManager = lightsManager;
		this.engine = engine;
		this.bodyGenerator = bodyGenerator;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		PositionComponent position = positionMapper.get(entity);
		OutOfBoundsComponent outOfBounds = outOfBoundsMapper.get(entity);
		if (position.x > viewport.getWorldWidth() / 2
				|| position.x < -viewport.getWorldWidth() / 2
				|| position.y > viewport.getWorldHeight() / 2
				|| position.y < -viewport.getWorldHeight() / 2) {
			if(outOfBounds.action==AdequateAction.RESPAWN){
				position.x = MathUtils.random(-viewport.getWorldWidth() / 2,
						viewport.getWorldWidth() / 2);
				position.y = MathUtils.random(-viewport.getWorldHeight() / 2,
						viewport.getWorldHeight() / 2);
			}
			
			else if(outOfBounds.action==AdequateAction.ALERT){
				lightsManager.ambientLight = 0.1f;
			}
			else if(outOfBounds.action==AdequateAction.DISPOSE){
				BodyComponent body = bodyMapper.get(entity);
				if(body != null)
					bodyGenerator.destroyBody(entity);
				engine.removeEntity(entity);
			}
				
				

		}
	}

}
