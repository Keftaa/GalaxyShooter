package managers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.galaxyshooter.game.Constants;
import com.galaxyshooter.game.Constants.GameState;

import components.HealthComponent;

public class HealthManager implements EntityListener {
	private HealthComponent health;
	public int healthLeft;
	@Override
	public void entityAdded(Entity entity) {
		health = entity.getComponent(HealthComponent.class);
		healthLeft = health.hp;
		
	}

	@Override
	public void entityRemoved(Entity entity) {}
	
	public void setDamage(Entity entity, int damage){
		entity.getComponent(HealthComponent.class).damageTaken = damage;
		healthLeft = health.hp - damage;
		if(damage==health.hp){
			Constants.gameState = GameState.GameOver;
		}
	}
	
}
