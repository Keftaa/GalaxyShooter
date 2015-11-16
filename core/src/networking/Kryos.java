package networking;


import java.util.HashMap;

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
		kryo.register(Packet.class);
		kryo.register(float[].class);
		kryo.register(Object[].class);
		kryo.register(int[].class);
		kryo.register(float[][].class);
		kryo.register(HashMap.class);
		kryo.register(com.badlogic.gdx.graphics.Color.class);
	}
}
