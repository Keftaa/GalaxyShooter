package networking;

import java.io.IOException;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.galaxyshooter.game.Constants;

import components.SpeedComponent;

public class MyServer {
	private Server server;
	
	public MyServer(){
		server = new Server();
		Kryo kryo = server.getKryo();
		Kryos.registerAll(kryo);

		
		server.start();
		try {
			server.bind(5000, 6000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void serverListener(){
		server.addListener(new Listener(){


			@Override
			public void disconnected(Connection connection) {
				
			}

			@Override
			public void received(Connection connection, Object object) {
				if(object instanceof Packet){
					Packet packet = (Packet) object;
					switch(packet.name){
					case "Bullet":
						float [] speedInfo = (float[]) packet.stuff.get("Speed");
						speedInfo[1] = -speedInfo[1];
						packet.stuff.put("Speed", speedInfo);
						server.sendToAllExceptUDP(connection.getID(), packet);
						break;
					case "Position":
						float [] positionInfo = (float[]) packet.stuff.get("Position");
						positionInfo[1] = Constants.WORLD_HEIGHT/2;
						positionInfo[2] = 0;
						
						packet.stuff.put("Position", positionInfo);
						
						
						packet.stuff.remove("Speed");
						packet.stuff.remove("Body");
						packet.stuff.remove("ContactDamage");
						packet.stuff.remove("Coupled");
						packet.stuff.remove("DamageSprite");
						packet.stuff.remove("Dispatchable");
						packet.stuff.remove("EngineCapacity");
						packet.stuff.remove("Health");
						packet.stuff.remove("OutOfBounds");
						packet.stuff.remove("Particle");
						packet.stuff.remove("RelativeSpeed");
						packet.stuff.remove("Size");
						packet.stuff.remove("Speed");
						packet.stuff.remove("Sprite");
						packet.stuff.remove("Light");
						
						server.sendToAllExceptUDP(connection.getID(), packet);
						break;
						
 					}

				}
			}
			
		});
	}
	
	public static void main (String[] args){
		MyServer server = new MyServer(); server.serverListener();
	}
	
}
