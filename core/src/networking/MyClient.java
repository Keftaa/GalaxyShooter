package networking;

import java.io.IOException;
import java.net.InetAddress;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import managers.ScoreManager;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasSprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.galaxyshooter.game.Assets;
import com.galaxyshooter.game.Constants;
import com.galaxyshooter.game.Assets.GameSprite;
import com.galaxyshooter.game.Constants.GameState;

import components.BodyComponent;
import components.BulletRateComponent;
import components.ContactDamageComponent;
import components.ControllableComponent;
import components.CoupledComponent;
import components.DamageSpriteComponent;
import components.DispatchableComponent;
import components.EngineCapacityComponent;
import components.HealthComponent;
import components.LaunchableComponent;
import components.LightComponent;
import components.OutOfBoundsComponent;
import components.ParticleComponent;
import components.PositionComponent;
import components.RelativeSpeedComponent;
import components.RenderableComponent;
import components.SizeComponent;
import components.SpeedComponent;
import components.SpriteComponent;

public class MyClient {
	private Client client;
	private PooledEngine engine;
	private Packet packet;
	private Assets assets;

	private NetworkEntities nEntities;
	public int score;
	public boolean connected;
	public MyClient(String IP, PooledEngine engine, Assets assets, NetworkEntities nEntities) {
		connected = false;
		this.nEntities = nEntities;
		this.engine = engine;
		client = new Client();
		this.assets = assets;
		packet = new Packet();
		Kryo kryo = client.getKryo();
		Kryos.registerAll(kryo);
		client.start();
		try {
			client.connect(5000, IP, 5000, 6000);
			clientListener();
			connected = true;
			
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
				if (object instanceof Packet) {
					Packet packet = (Packet) object;
					if(packet.name.equals("Position")){
						Array<Component> components = translatePacket(packet);
						for(Component c: components){
							if(c instanceof PositionComponent){
								if(NetworkEntities.loaded && nEntities.oppLight != null){
									nEntities.oppLight.getComponent(PositionComponent.class).x = ((PositionComponent) c).x;
									nEntities.oppLight.getComponent(PositionComponent.class).y = ((PositionComponent) c).y;
									System.out.println(nEntities.oppLight.getComponent(PositionComponent.class).x);
								}

							}
						
						}
					}
					else if(packet.name.equals("Bullet")){
						Entity entity = engine.createEntity();
						Array<Component> components = translatePacket((Packet) object);
						for (Component c : components){
							entity.add(c);
						}
						engine.addEntity(entity);

					}
					
					else if(packet.name.equals("Score")){
						score = packet.oppScore; // we're receiving it, so oppScore is actually our Score
						
					}
					
					else if(packet.name.equals("GameOver")){
						Constants.gameState = GameState.GameOver;
					}
				}
			}
		});
	}

	public void sendPacket(ImmutableArray<Component> immutableArray, String name) {
		if(NetworkEntities.loaded){
			packet.stuff = new HashMap<String, Object>();
			for (int i = 0; i < immutableArray.size(); i++) {
				SimpleEntry<String, Object> entry = translateComponent(immutableArray
						.get(i));
				if (entry != null)
					packet.stuff.put(entry.getKey(), entry.getValue());
			}

			packet.name = name;
			client.sendTCP(packet);
		}

	}

	private SimpleEntry<String, Object> translateComponent(Component component) {
		if (component instanceof SpriteComponent) {
			SpriteComponent sprite = (SpriteComponent) component;
			Object[] spriteInfo = new Object[2];
			int spriteID = Assets
					.getSpriteID(sprite.sprite.getAtlasRegion().name);
			boolean afterLight = sprite.afterLight;
			spriteInfo[0] = spriteID;
			spriteInfo[1] = afterLight;
			return new SimpleEntry<String, Object>("Sprite", spriteInfo);

		}

		else if (component instanceof SpeedComponent) {
			SpeedComponent speed = (SpeedComponent) component;
			float[] speeds = { speed.x, speed.y };
			return new SimpleEntry<String, Object>("Speed", speeds);
		}

		else if (component instanceof SizeComponent) {
			SizeComponent size = (SizeComponent) component;
			float[] sizes = { size.width, size.height };
			return new SimpleEntry<String, Object>("Size", sizes);
		}

		else if (component instanceof RenderableComponent) {
			return new SimpleEntry<String, Object>("Renderable", true);
		} else if (component instanceof RelativeSpeedComponent) {
			RelativeSpeedComponent speed = (RelativeSpeedComponent) component;
			Object[] data = {speed.groupId, speed.leader};
			
			return new SimpleEntry<String, Object>("RelativeSpeed", data);
		}

		else if (component instanceof PositionComponent) {
			PositionComponent position = (PositionComponent) component;
			int overridenByBody = 0;
			if (position.overridenByBody)
				overridenByBody = 1;
			float[] positions = { position.x, position.y, overridenByBody };
			return new SimpleEntry<String, Object>("Position", positions);
		}

		else if (component instanceof ParticleComponent) {
			ParticleComponent particle = (ParticleComponent) component;
			int particleID = particle.gameParticle.ordinal();

			return new SimpleEntry<String, Object>("Particle", particleID);

		}

		else if (component instanceof OutOfBoundsComponent) {
			OutOfBoundsComponent outOfBounds = (OutOfBoundsComponent) component;
			return new SimpleEntry<String, Object>("OutOfBounds",
					outOfBounds.action.ordinal());
		}

		else if (component instanceof LightComponent) {
			LightComponent light = (LightComponent) component;
			Object[] lightStuff = { light.color, light.distance, light.rays,
					light.x, light.y };
			return new SimpleEntry<String, Object>("Light", lightStuff);
		}

		else if (component instanceof LaunchableComponent) {
			return null;
		}

		else if (component instanceof DispatchableComponent)
			return new SimpleEntry<String, Object>("Dispatchable", true);

		else if (component instanceof DamageSpriteComponent) {
			DamageSpriteComponent damageSprite = (DamageSpriteComponent) component;
			if(damageSprite.damageSprite==null) return null;
			int spriteID = Assets.getSpriteID(damageSprite.damageSprite
					.getAtlasRegion().name);
			return new SimpleEntry<String, Object>("DamageSprite", spriteID);
		}

		else if (component instanceof CoupledComponent) {
			return null;
		}

		else if (component instanceof ControllableComponent) {
			return null;
		}

		else if (component instanceof BodyComponent) {
			BodyComponent body = (BodyComponent) component;
			float[][] vertices = null;
			if (body.vertices != null)
				vertices = new float[body.vertices.length][2];
			else
				return new SimpleEntry<String, Object>("Body", null);

			for (int i = 0; i < body.vertices.length; i++) {
				vertices[i][0] = body.vertices[i].x;
				vertices[i][1] = body.vertices[i].y;
			}

			return new SimpleEntry<String, Object>("Body", vertices);
		}

		else if (component instanceof EngineCapacityComponent) {
			EngineCapacityComponent engine = (EngineCapacityComponent) component;
			float maxTime = engine.maxTime;
			return new SimpleEntry<String, Object>("EngineCapacity", maxTime);

		}

		else if (component instanceof BulletRateComponent) {
			BulletRateComponent bulletRate = (BulletRateComponent) component;
			float rate = bulletRate.bulletsPerSecond;
			return new SimpleEntry<String, Object>("BulletRate", rate);

		}

		else if (component instanceof HealthComponent) {
			HealthComponent health = (HealthComponent) component;
			int[] hpAndDamage = { health.hp, health.damageTaken };

			return new SimpleEntry<String, Object>("Health", hpAndDamage);
		}

		else if (component instanceof ContactDamageComponent) {
			ContactDamageComponent damage = (ContactDamageComponent) component;
			int damagePoints = damage.damagePoints;

			return new SimpleEntry<String, Object>("ContactDamage",
					damagePoints);
		}

		return null;
	}

	private Array<Component> translatePacket(Packet packet) {
		HashMap<String, Object> map = packet.stuff;
		Array<Component> components = new Array<Component>();

		for (String key : map.keySet()) {
			if (key.equals("Sprite")) {
				Object[] info = (Object[]) map.get(key);
				SpriteComponent sprite = engine
						.createComponent(SpriteComponent.class);

				sprite.sprite = new AtlasSprite(
						assets.atlas.findRegion(Assets.GameSprite.values()[(Integer) info[0]]
								.getName()));
				sprite.afterLight = (Boolean) info[1];

				components.add(sprite);
			}

			else if (key.equals("Speed")) {
				float[] info = (float[]) map.get(key);
				SpeedComponent speed = engine
						.createComponent(SpeedComponent.class);
				speed.x = info[0];
				speed.y = info[1];
				speed.active = true; // we're not going to transfer that's not
										// moving lol

				components.add(speed);
			}

			else if (key.equals("Size")) {
				float[] info = (float[]) map.get(key);
				SizeComponent size = engine
						.createComponent(SizeComponent.class);
				size.width = info[0];
				size.height = info[1];

				components.add(size);
			}

			else if (key.equals("Renderable")) {
				RenderableComponent renderable = engine
						.createComponent(RenderableComponent.class);
				components.add(renderable);
			}

			else if (key.equals("RelativeSpeed")){
				Object[] info = (Object[]) map.get(key);
				RelativeSpeedComponent relSpeed = engine.createComponent(RelativeSpeedComponent.class);
				relSpeed.groupId = (Integer) info[0];
				relSpeed.leader = (Boolean) info[1];
				
				components.add(relSpeed);
			}
				

			else if (key.equals("Position")) {
				float[] info = (float[]) map.get(key);
				PositionComponent position = engine
						.createComponent(PositionComponent.class);
				position.x = info[0];
				position.y = info[1];
				position.overridenByBody = info[2] == 1;

				components.add(position);
			}

			else if (key.equals("Particle")) {
				int info = (Integer) map.get(key);
				ParticleComponent particle = engine
						.createComponent(ParticleComponent.class);
				particle.gameParticle = ParticleComponent.GameParticle.values()[info];

				components.add(particle);
			}

			else if (key.equals("OutOfBounds")) {
				int info = (Integer) map.get(key);
				OutOfBoundsComponent outOfBounds = engine
						.createComponent(OutOfBoundsComponent.class);
				outOfBounds.action = OutOfBoundsComponent.AdequateAction
						.values()[info];
			}

			else if (key.equals("Light")) {
				Object[] info = (Object[]) map.get(key);
				LightComponent light = engine
						.createComponent(LightComponent.class);
				light.color = (Color) info[0];
				light.distance = (Integer) info[1];
				light.rays = (Integer) info[2];
				light.x = (Float) info[3];
				light.y = (Float) info[4];

				components.add(light);
			}

			else if (key.equals("Launchable"))
				;

			else if (key.equals("Dispatchable"))
				;

			else if (key.equals("DamageSprite")) {
				int info = (Integer) map.get(key);
				DamageSpriteComponent damageSprite = engine
						.createComponent(DamageSpriteComponent.class);
				damageSprite.damageSprite = new AtlasSprite(
						assets.atlas.findRegion(Assets.GameSprite.values()[(Integer) info]
								.getName()));

				components.add(damageSprite);
			}

			else if (key.equals("Coupled"))
				;

			else if (key.equals("Controllable"))
				;

			else if (key.equals("Body")) {
				if (map.get(key) == null) {
					components.add(engine.createComponent(BodyComponent.class));
				} else {
					float[][] info = (float[][]) map.get(key);
					Vector2[] vertices = new Vector2[info.length];
					for (int i = 0; i < info.length; i++)
						vertices[i].set(info[i][0], info[i][1]);
					BodyComponent body = engine
							.createComponent(BodyComponent.class);
					body.vertices = vertices;

					components.add(body);
				}

			}

			else if (key.equals("EngineCapacity")) {
				float info = (Float) map.get(key);
				EngineCapacityComponent engineCapacity = engine
						.createComponent(EngineCapacityComponent.class);
				engineCapacity.maxTime = info;
				components.add(engineCapacity);
			}

			else if (key.equals("BulletRate")) {
				float info = (Float) map.get(key);
				BulletRateComponent bulletRate = engine
						.createComponent(BulletRateComponent.class);
				bulletRate.bulletsPerSecond = info;

				components.add(bulletRate);
			}

			else if (key.equals("Health")) {
				Integer[] info = (Integer[]) map.get(key);
				HealthComponent health = engine
						.createComponent(HealthComponent.class);
				health.hp = info[0];
				health.damageTaken = info[1];

				components.add(health);
			}

			else if (key.equals("ContactDamage")) {
				int info = (Integer) map.get(key);
				ContactDamageComponent damage = engine
						.createComponent(ContactDamageComponent.class);
				damage.damagePoints = info;

				components.add(damage);
			}

		}

		return components;
	}
	
	
	public void sendScore(int score){
		Packet packet = new Packet();
		packet.oppScore = score;
		packet.name = "Score";
		client.sendTCP(packet);
	}

	public void sendGameOverPacket() {
		Packet packet = new Packet();
		packet.name = "GameOver";
		client.sendTCP(packet);
		
	}
}
