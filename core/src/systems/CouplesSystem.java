package systems;

import managers.CouplesManager;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Renderable;

import components.BodyComponent;
import components.CoupledComponent;
import components.PositionComponent;
import components.RenderableComponent;

public class CouplesSystem extends IteratingSystem {
	
	private SpriteBatch batch;
	
	ComponentMapper<CoupledComponent> followerMapper = ComponentMapper.getFor(CoupledComponent.class);
	ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);

	public CouplesSystem(SpriteBatch batch) {
		super(Family.all(CoupledComponent.class).get());
		this.batch = batch;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		CoupledComponent coupled = followerMapper.get(entity);
		Entity leader = null;
		if(CouplesManager.couplesMap.containsKey(coupled.coupleId)){
			for(Entity e: CouplesManager.couplesMap.get(coupled.coupleId)){
				if(e.getComponent(CoupledComponent.class).leader){
					leader = e;
				}
				else if(leader!=null){
					e.getComponent(PositionComponent.class).overridenByBody = false;
					e.getComponent(PositionComponent.class).x = leader.getComponent(PositionComponent.class).x+e.getComponent(CoupledComponent.class).offsetX;
					e.getComponent(PositionComponent.class).y = leader.getComponent(PositionComponent.class).y+e.getComponent(CoupledComponent.class).offsetY;
				}
			}
		}
		
		
	}

}
