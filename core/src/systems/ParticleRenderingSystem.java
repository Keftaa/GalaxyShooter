package systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import components.ParticleComponent;
import components.ParticleComponent.ParticleType;
import components.PositionComponent;
import components.RenderableComponent;

public class ParticleRenderingSystem extends EntitySystem {

	ImmutableArray<Entity> entities;
	Array<Vector2> oldPositions;
	private ComponentMapper<ParticleComponent> particleMapper = ComponentMapper
			.getFor(ParticleComponent.class);

	private ComponentMapper<PositionComponent> positionMapper = ComponentMapper
			.getFor(PositionComponent.class);

	SpriteBatch batch;

	public ParticleRenderingSystem(SpriteBatch batch) {
		this.batch = batch;
		oldPositions = new Array<Vector2>();
	}

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(ParticleComponent.class,
				RenderableComponent.class).get());

	}

	@Override
	public void update(float deltaTime) {
		for (int i = 0; i < entities.size(); i++) {
			PositionComponent position = positionMapper.get(entities.get(i));
			ParticleComponent particle = particleMapper.get(entities.get(i));
			if(particle.getType()==ParticleType.Movement){
				if (oldPositions.size - 1 > i) {
					if (position.y != oldPositions.get(i).y) {
						particle.effect.setPosition(position.x, position.y);
						particle.effect.draw(batch, deltaTime);
						
						particle.effect.getEmitters().get(0).durationTimer = 0;
						oldPositions.set(i, new Vector2(position.x, position.y));
						if (particle.effect.isComplete())
							particle.effect.reset();
					} else {
						particle.effect.free();
					}
				} else {
					oldPositions.add(new Vector2(position.x, position.y));
				}	
			}


		}
	}

}
