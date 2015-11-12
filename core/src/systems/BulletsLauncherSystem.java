package systems;

import managers.CouplesManager;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;

import components.BodyComponent;
import components.ControllableComponent;
import components.CoupledComponent;
import components.DispatchableComponent;
import components.RenderableComponent;
import components.SpeedComponent;

public class BulletsLauncherSystem extends IntervalSystem {
	public BulletsLauncherSystem() {
		super(0.4f);
	}

	private ImmutableArray<Entity> entities;

	private ComponentMapper<SpeedComponent> speedMapper = ComponentMapper
			.getFor(SpeedComponent.class);
	private ComponentMapper<CoupledComponent> coupleMapper = ComponentMapper
			.getFor(CoupledComponent.class);

	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family
				.all(CoupledComponent.class, SpeedComponent.class)
				.exclude(ControllableComponent.class).get());
	}


	@Override
	protected void updateInterval() {
		for (Entity entity : entities) {
			CoupledComponent couple = coupleMapper.get(entity);
			Array<Entity> entities = CouplesManager.couplesMap
					.get(couple.coupleId);
			for (Entity e : entities)
				if (e.getId() == entity.getId()) {
					entity.remove(CoupledComponent.class);
					SpeedComponent speed = speedMapper.get(entity);
					speed.active = true;
					entity.add(new BodyComponent());
					entity.add(new RenderableComponent());
					entity.add(new DispatchableComponent());
					return;
				}
	}
		
	}

}
