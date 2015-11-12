package managers;

import java.util.HashMap;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.utils.Array;

import components.RelativeSpeedComponent;

public class RelativeSpeedManager implements EntityListener {

	public static HashMap<Integer, Array<Entity>> speedDependenciesMap = new HashMap<Integer, Array<Entity>>();
	
	@Override
	public void entityAdded(Entity entity) {
		int key = entity.getComponent(RelativeSpeedComponent.class).groupId;
		if(!speedDependenciesMap.containsKey(key)){
			Array<Entity> entities = new Array<Entity>();
			if(entity.getComponent(RelativeSpeedComponent.class).leader)
				entities.insert(0, entity);
			else
				entities.add(entity);
			speedDependenciesMap.put(key, entities);
		}
		else{
			if(entity.getComponent(RelativeSpeedComponent.class).leader)
				speedDependenciesMap.get(key).insert(0, entity);
			else
				speedDependenciesMap.get(key).add(entity);
		}

		
	}

	@Override
	public void entityRemoved(Entity entity) {
		for(int key: speedDependenciesMap.keySet()){
			for(Entity e: speedDependenciesMap.get(key))
				if(e.equals(entity))
					speedDependenciesMap.get(key).removeValue(entity, true);			
		}

	}

}
