package networking;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import components.BodyComponent;
import components.ControllableComponent;
import components.SpriteComponent;

public class MyClient {
	private Client client;
	private Engine engine;
	private Packet packet;

	public MyClient(Engine engine) {
		this.engine = engine;
		client = new Client();
		packet = new Packet();
		Kryo kryo = client.getKryo();
		Kryos.registerAll(kryo);
		client.start();
		try {
			client.connect(5000, "localhost", 5000, 6000);
			clientListener();
		} catch (IOException e) {
			System.out.println("Could not connect to server");
			e.printStackTrace();
		}
	}

	private void clientListener() {
		client.addListener(new Listener() {

			@Override
			public void connected(Connection connection) {
			}

			@Override
			public void received(Connection connection, Object object) {
				if (object instanceof Array) {
					@SuppressWarnings("unchecked")
					Array<Component> components = (Array<Component>) object;
					Entity entity = new Entity();
					for (Component c : components) {
						entity.add(c);
						engine.addEntity(entity);
					}
				}
			}

		});
	}

	public void sendPacket(Array<Component> components) {
		packet.stuff = new HashMap<String, Object>();
		for (int i = 0; i < components.size; i++) {
			packet.stuff.put(translateComponent(components.get(i)).getKey(),
					translateComponent(components.get(i)).getValue());
		}
		client.sendUDP(components);
	}

	public SimpleEntry<String, Object> translateComponent(Component component) {
		String key;
		Object value;
		
		if(component instanceof SpriteComponent){
			SpriteComponent sprite = (SpriteComponent) component;
			sprite.sprite.getTexture().
			key = "Sprite";
			//Texture texture = new Texture();
		}
		
		return new SimpleEntry<String, Object>(null, "hello");
	}

}
