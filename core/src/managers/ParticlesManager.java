package managers;

import java.util.HashMap;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import components.ParticleComponent;
import components.ParticleComponent.GameParticle;
import components.PositionComponent;

public class ParticlesManager implements EntityListener {

	private HashMap<GameParticle, ParticleEffectPool> pools;
	private ComponentMapper<ParticleComponent> particleMapper = ComponentMapper
			.getFor(ParticleComponent.class);
	private ComponentMapper<PositionComponent> positionMapper = ComponentMapper
			.getFor(PositionComponent.class);
	
	public ParticlesManager() {
		pools = new HashMap<GameParticle, ParticleEffectPool>();
	}

	@Override
	public void entityAdded(Entity entity) {
		ParticleComponent particle = particleMapper.get(entity);
		PositionComponent position = positionMapper.get(entity);
		if (pools.containsKey(particle.gameParticle)) {
			particle.effect = pools.get(particle.gameParticle).obtain();
			particle.effect.setPosition(position.x, position.y);
			System.out.println("Created pooled particle ..." + particle.effect);
		}

		else {
			pools.put(particle.gameParticle, new ParticleEffectPool(
					getParticlePrototype(particle.gameParticle), 1, 10));
			particle.effect = pools.get(particle.gameParticle).obtain();
			particle.effect.setPosition(position.x, position.y);
			System.out.println("Created new particle ..." + particle.effect);
			
		//	activeEffects.add(particle.effect);
		}
		
	}

	@Override
	public void entityRemoved(Entity entity) {
		
	}

	private ParticleEffect getParticlePrototype(GameParticle gameParticle) {
		switch (gameParticle) {

		case ThrustParticle:
			ParticleEffect effect = new ParticleEffect();
			effect.load(Gdx.files.internal("particles/thrust"),
					Gdx.files.internal("particles"));
			return effect;
		case BulletThrustParticle:
			ParticleEffect effect2 = new ParticleEffect();
			effect2.load(Gdx.files.internal("particles/bulletMovement"), Gdx.files.internal("particles"));
			return effect2;
		case BulletExplosionParticle:
			break;
		default:
			break;
		}
		return null;
	}

}
