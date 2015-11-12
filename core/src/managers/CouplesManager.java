package managers;

import java.util.HashMap;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.utils.Array;
import components.CoupledComponent;

public class CouplesManager implements EntityListener {
	public static HashMap<Integer, Array<Entity>> couplesMap = new HashMap<Integer, Array<Entity>>();
	
	@Override
	public void entityAdded(Entity entity) {
		int key = entity.getComponent(CoupledComponent.class).coupleId;
		if(!couplesMap.containsKey(key)){
			Array<Entity> entities = new Array<Entity>();
			entities.add(entity);
			couplesMap.put(key, entities);
		}
		else{
			couplesMap.get(key).add(entity);
		}
		
	}

	@Override
	public void entityRemoved(Entity entity) {
		for(int key: couplesMap.keySet()){
			for(Entity e: couplesMap.get(key))
				if(e.equals(entity))
					couplesMap.get(key).removeValue(entity, true);			
		}

	}
	
	
}
