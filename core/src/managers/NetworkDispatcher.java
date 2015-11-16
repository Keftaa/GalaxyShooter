package managers;

import networking.MyClient;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;

import components.DispatchableComponent;

public class NetworkDispatcher implements EntityListener {

	

	private MyClient client;

	public NetworkDispatcher(MyClient client) {
		this.client = client;
	}

	@Override
	public void entityAdded(Entity entity) {

	}

	@Override
	public void entityRemoved(Entity entity) {
		client.sendPacket(entity.getComponents(), "Bullet");
	}

}
