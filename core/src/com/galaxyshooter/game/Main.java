package com.galaxyshooter.game;

import networking.MyClient;
import networking.MyServer;
import screens.GameScreen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends Game {
	
	public TextureAtlas atlas;
	public Engine engine;
	public SpriteBatch batch;
	public FitViewport viewport;
	public MyClient client;
	private MyServer server;
	private GameScreen gameScreen;
	
	
	@Override
	public void create() {
		engine = new Engine();
		server = new MyServer(engine); server.serverListener();
		client = new MyClient(engine);
		
		batch = new SpriteBatch();
		viewport = new FitViewport(800/8, 480/8);
		gameScreen = new GameScreen(this);
		
		setScreen(gameScreen);
		
	}

	
	
	
}
