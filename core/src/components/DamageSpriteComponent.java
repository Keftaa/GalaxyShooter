package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasSprite;
import com.badlogic.gdx.utils.Pool.Poolable;

public class DamageSpriteComponent implements Component, Poolable {
	public AtlasSprite damageSprite;

	@Override
	public void reset() {
		damageSprite = null;
		
	}
}
