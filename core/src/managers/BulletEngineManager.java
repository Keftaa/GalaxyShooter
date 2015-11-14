package managers;


import systems.BulletsLauncherSystem;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.PooledEngine;

import components.BulletRateComponent;

public class BulletEngineManager implements EntityListener {

	public float bulletsPerSecond=1; // just not to get an infinity on start
	private PooledEngine engine;
	private EntitiesManager manager;
	
	public BulletEngineManager(PooledEngine engine, EntitiesManager manager){
		this.engine = engine;
		this.manager = manager;
		
	}
	
	@Override
	public void entityAdded(Entity entity) {
		
		if(bulletsPerSecond!=entity.getComponent(BulletRateComponent.class).bulletsPerSecond){
			bulletsPerSecond = entity.getComponent(BulletRateComponent.class).bulletsPerSecond;

		}
	}

	@Override
	public void entityRemoved(Entity entity) {
	}

	public void renewSystem(BulletsLauncherSystem bulletsLauncherSystem) {
		engine.removeSystem(bulletsLauncherSystem);
		engine.addSystem(new BulletsLauncherSystem(engine, this));
	}
	

	public void tick() {
		Entity bullet = manager.createBullet();
		engine.addEntity(bullet);
		
	}

}
