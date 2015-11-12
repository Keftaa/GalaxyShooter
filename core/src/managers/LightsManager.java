package managers;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;

import components.LightComponent;

public class LightsManager implements EntityListener {

	public float ambientLight;
	
	public RayHandler handler;
	private ComponentMapper<LightComponent> lightsMapper = ComponentMapper
			.getFor(LightComponent.class);

	public LightsManager(World world, FitViewport viewport) {
		handler = new RayHandler(world, (int) viewport.getWorldWidth(), (int) viewport.getWorldHeight());
		ambientLight = 0f;
	}



	@Override
	public void entityAdded(Entity entity) {
		LightComponent light = lightsMapper.get(entity);
		
		PointLight pointLight = new PointLight(handler, light.rays, light.color, light.distance, light.x, light.y);
		light.light = pointLight;
	}

	@Override
	public void entityRemoved(Entity entity) {
		LightComponent light = lightsMapper.get(entity);
		light.light.remove();
		entity.remove(LightComponent.class);
		
	}
	
}
