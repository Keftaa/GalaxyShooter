package managers;

import networking.MyClient;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;

import components.DispatchableComponent;

public class NetworkDispatcher implements EntityListener {
	// When an entity loses the component specified in the constructor,
	// we dispatch this to the appropriate server

	private Component targetComponent;
	private MyClient client;

	private ComponentMapper<DispatchableComponent> dispatchableMapper = ComponentMapper
			.getFor(DispatchableComponent.class);
	/**
	 * When an entity loses the component specified in the constructor,
	 * we dispatch the entity's components to the server.
	 * 
	 * @params
	 * client: the networking client
	 */
	public NetworkDispatcher(MyClient client) {
		this.client = client;
	}

	@Override
	public void entityAdded(Entity entity) {

	}

	@Override
	public void entityRemoved(Entity entity) {
		client.sendPacket(entity.getComponents());
	}

}
