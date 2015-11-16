package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasSprite;
import com.badlogic.gdx.utils.Pool.Poolable;

public class SpriteComponent implements Component, Poolable {
	public AtlasSprite sprite;
	public boolean afterLight = false;
	
	@Override
	public void reset(){
		sprite = null;
		afterLight = false;
	}
}
