package com.galaxyshooter.game;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {
	public final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("game.pack"));
	
	public enum GameSprite {
		GreenShip("player"), RedLaser("bulletRed"), GreenLaser("bulletGreen"),
		Damage1("playerShip2_damage1"), Damage3("playerShip2_damage3"),
		Damage4("playerShip2_damage4"),
		Damage2("playerShip2_damage2"), Background("background"), Star("star"),
		GreenShipLives("playerLives"), Up("up"), Down("down"), Left("right"),
		Right("left"), Shoot("shoot");
		
		private String name;
		GameSprite(String atlasRef){
			name = atlasRef;
		}
		
		public String getName(){
			return name;
		}

		
	}
	

	
	public static int getSpriteID(String name){
		for(GameSprite gameSprite: GameSprite.values())
			if(gameSprite.getName().equals(name))
				return gameSprite.ordinal();
		return -1;
	}

}
