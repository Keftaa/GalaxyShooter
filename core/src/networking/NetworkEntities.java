package networking;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Color;
import com.galaxyshooter.game.Constants;

import components.LightComponent;
import components.PositionComponent;
import components.RenderableComponent;

public class NetworkEntities {
	public static boolean loaded = false;
	
	private PooledEngine engine;
	public Entity oppLight;
	
	
	public NetworkEntities(PooledEngine engine){
		this.engine = engine;
	}
	
	public void opponentLight(){
		oppLight = engine.createEntity();
		PositionComponent position = engine.createComponent(PositionComponent.class);
		position.x = 0;
		position.y = 0;

		oppLight.add(position);
		oppLight.add(engine.createComponent(RenderableComponent.class));
		
		LightComponent light = engine.createComponent(LightComponent.class);
		light.x = 0;
		light.y = Constants.WORLD_HEIGHT /2;
		light.color = Color.RED;
		light.distance = 20;
		light.rays = 200;
		
		oppLight.add(light);
		oppLight.add(position);
		
		engine.addEntity(oppLight);
		System.out.println(oppLight);
	}
	
	public void loadAll(){
		opponentLight();
		loaded = true;
	}
}
