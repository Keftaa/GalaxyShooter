package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pool.Poolable;

public class DamageSpriteComponent implements Component, Poolable {
	public Sprite damageSprite;

	@Override
	public void reset() {
		damageSprite = null;
		
	}
}
