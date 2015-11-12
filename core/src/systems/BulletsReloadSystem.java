package systems;

import managers.BodyGenerator;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.viewport.FitViewport;

import components.BodyComponent;
import components.ControllableComponent;
import components.CoupledComponent;
import components.DispatchableComponent;
import components.ParticleComponent;
import components.PositionComponent;
import components.RenderableComponent;

public class BulletsReloadSystem extends IteratingSystem {
	
	private FitViewport viewport;
	private ComponentMapper<PositionComponent> positionMapper = ComponentMapper
			.getFor(PositionComponent.class);
	
	private BodyGenerator bodyGenerator;

	@SuppressWarnings("unchecked")
	public BulletsReloadSystem(FitViewport viewport, BodyGenerator bodyGenerator) {
		super(Family.all(PositionComponent.class, 
				RenderableComponent.class, ParticleComponent.class).exclude(ControllableComponent.class).get());
		this.viewport = viewport;
		this.bodyGenerator = bodyGenerator;
	}
	//TODO switch to pooling to make bullets easier to maintain 
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		PositionComponent position = positionMapper.get(entity);
		if(position.y > viewport.getWorldHeight()/2){
			CoupledComponent coupled = new CoupledComponent();
			coupled.coupleId = 0;
			coupled.offsetX = 0;
			coupled.offsetY = 10;
			entity.add(coupled);
			entity.remove(DispatchableComponent.class);
			System.out.println("removed dispatchable component");
			bodyGenerator.destroyBody(entity);
			entity.remove(BodyComponent.class);
			entity.remove(RenderableComponent.class);
			
		}
			
	}

}
