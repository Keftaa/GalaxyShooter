package systems;

import managers.BulletEngineManager;
import managers.CouplesManager;
import managers.EntitiesManager;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;

import components.BodyComponent;
import components.BulletRateComponent;
import components.CoupledComponent;
import components.RenderableComponent;
import components.SpeedComponent;


public class BulletsLauncherSystem extends IntervalSystem {

	private PooledEngine engine;
	public boolean shouldReload;
	private BulletEngineManager bulletEngineManager;
	private float bulletsPerSecond;

	public BulletsLauncherSystem(PooledEngine engine, BulletEngineManager bulletEngineManager) {
		super(1f/bulletEngineManager.bulletsPerSecond);
		this.engine = engine;
		this.bulletEngineManager = bulletEngineManager;
		shouldReload = false;
		bulletsPerSecond = bulletEngineManager.bulletsPerSecond;
		System.out.println(1f/bulletEngineManager.bulletsPerSecond);
		
	}

	private ImmutableArray<Entity> entities;

	private ComponentMapper<SpeedComponent> speedMapper = ComponentMapper
			.getFor(SpeedComponent.class);
	private ComponentMapper<CoupledComponent> coupleMapper = ComponentMapper
			.getFor(CoupledComponent.class);


	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(CoupledComponent.class,
				SpeedComponent.class, BulletRateComponent.class).get());
	}

	@Override
	protected void updateInterval() {


		Entity entity = entities.first();
		entity.remove(CoupledComponent.class);
		SpeedComponent speed = speedMapper.get(entity);
		speed.active = true;
		entity.add(engine.createComponent(BodyComponent.class));
		entity.add(engine
				.createComponent(RenderableComponent.class));

		bulletEngineManager.tick();		

		
		if(bulletEngineManager.bulletsPerSecond != bulletsPerSecond){
			bulletEngineManager.renewSystem(this);
		}
			
	}


}
