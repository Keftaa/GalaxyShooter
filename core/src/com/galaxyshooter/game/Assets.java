package com.galaxyshooter.game;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {
	private final static TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("game.pack"));;
	
	public enum GameSprite {
		GreenShip("player"), RedLaser("bulletRed"), GreenLaser("bulletGreen"),
		Damage1("playerShip2_damage1"), Damage3("playerShip2_damage3"),
		Damage4("playerShip2_damage4"),
		Damage2("playerShip2_damage2"), Background("background"), Star("star"),
		GreenShipLives("playerLives"), Up("up"), Down("down"), Left("right"),
		Right("left"), Shoot("shoot");
		
		private Sprite sprite;
		GameSprite(String atlasRef){
			sprite = atlas.createSprite(atlasRef);
		}
		
		public Sprite getSprite(){
			return sprite;
		}

		
	}
	

	
	public static int getSpriteID(Sprite sprite){
		for(GameSprite gameSprite: GameSprite.values())
			if(gameSprite.getSprite().equals(sprite))
				return gameSprite.ordinal();
		return -1;
	}

}
