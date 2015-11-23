package com.galaxyshooter.game;

public class Constants {
	public final static float WORLD_WIDTH = 800/8;
	public final static float WORLD_HEIGHT = 480/8;
	
	public final static float PIXEL_WIDTH = 800;
	public final static float PIXEL_HEIGHT = 480;
	
	public enum GameState{
		Running, GameOver;
	}
	
	public static GameState gameState = GameState.Running;
}
