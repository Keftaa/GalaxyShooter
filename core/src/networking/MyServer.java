package networking;

import java.io.IOException;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import components.SpeedComponent;

public class MyServer {
	private Server server;
	private Engine engine;
	
	public MyServer(Engine engine){
		this.engine = engine;
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

			@SuppressWarnings("unchecked")
			@Override
			public void received(Connection connection, Object object) {
				if(object instanceof Array){
					Array<Component> components = (Array<Component>) object;
					for(Component c: components){
						if(c instanceof SpeedComponent){
							SpeedComponent speed = (SpeedComponent) c;
							speed.y = -speed.y;
						}
						
					}
						
					server.sendToAllExceptTCP(connection.getID(), components);
					
				}
			}
			
		});
	}
	
}
