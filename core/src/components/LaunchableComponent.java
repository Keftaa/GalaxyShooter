package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class LaunchableComponent implements Component, Poolable {

	@Override
	public void reset() {}

	// entities that have the behaviour of being projected / launched when
	// a button is clicked.
}
