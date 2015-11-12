package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;

public class ParticleComponent implements Component {

	public enum GameParticle{
		ThrustParticle, BulletThrustParticle, BulletExplosionParticle;
		
	}
	
	public enum ParticleType{ 
		Explosion, Movement
	}
	
	public ParticleType getType(){
		switch(gameParticle){
		case ThrustParticle:
			return ParticleType.Movement;
		case BulletThrustParticle:
			return ParticleType.Movement;
		case BulletExplosionParticle:
			return ParticleType.Explosion;
			
		default: return null;
			
		}
	}
	
	public GameParticle gameParticle;
	
	public PooledEffect effect; // set by the particle manager
}
