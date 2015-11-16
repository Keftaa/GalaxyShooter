package systems;

import networking.MyClient;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;

import components.DispatchableComponent;

public class RealTimeNetworkSystem extends IntervalIteratingSystem {

	private ComponentMapper<DispatchableComponent> dispatchableMapper = ComponentMapper
			.getFor(DispatchableComponent.class);

	private MyClient client;
	
	public RealTimeNetworkSystem(MyClient client) {
		super(Family.all(DispatchableComponent.class).get(), 0.4f);
		
		this.client = client;
	}

	@Override
	protected void processEntity(Entity entity) {
		DispatchableComponent dispatchable = dispatchableMapper.get(entity);
		
		if(dispatchable.permanent){
			client.sendPacket(entity.getComponents(), "Position");
		}

	}

}
