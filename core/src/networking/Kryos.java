package networking;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;

import components.BodyComponent;
import components.CoupledComponent;
import components.LightComponent;
import components.OutOfBoundsComponent;
import components.ParticleComponent;
import components.PositionComponent;
import components.RenderableComponent;
import components.SizeComponent;
import components.SpeedComponent;
import components.SpriteComponent;

public class Kryos {
	
	public static void registerAll(Kryo kryo){
		kryo.register(BodyComponent.class);
		kryo.register(CoupledComponent.class);
		kryo.register(LightComponent.class);
		kryo.register(ParticleComponent.class);
		kryo.register(PositionComponent.class);
		kryo.register(SizeComponent.class);
		kryo.register(SpeedComponent.class);
		kryo.register(SpriteComponent.class);
		kryo.register(RenderableComponent.class);
		kryo.register(OutOfBoundsComponent.class);
		kryo.register(Object[].class);
		kryo.register(Array.class);
		kryo.register(Sprite.class);
		kryo.register(Texture.class);
		kryo.register(com.badlogic.gdx.graphics.Color.class);
	}
}
