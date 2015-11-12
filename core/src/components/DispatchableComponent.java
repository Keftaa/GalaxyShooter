package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;

public class DispatchableComponent implements Component {
	
	public Array<Component> componentsToDispatch = new Array<Component>();
}
