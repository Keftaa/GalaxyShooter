package com.galaxyshooter.game;

import networking.MyClient;
import networking.MyServer;
import networking.NetworkEntities;
import screens.GameScreen;
import screens.MainScreen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends Game {
	

	public PooledEngine engine;
	public SpriteBatch batch;
	public FitViewport viewport;
	public MyClient client;
	public Assets assets;
	public NetworkEntities nEntities;
	// private MyServer server;
	private MainScreen mainScreen;
	private GameScreen gameScreen;
	public SkinManager skinManager;
	
	
	
	@Override
	public void create() {
		skinManager = new SkinManager();
		assets = new Assets();
		engine = new PooledEngine();
	//	server = new MyServer(); server.serverListener();

		
		batch = new SpriteBatch();
		viewport = new FitViewport(800/8, 480/8);
		mainScreen = new MainScreen(this);
		nEntities = new NetworkEntities(engine);
		
		setScreen(mainScreen);
		
		
		
	}
	
	public void connect(String ip){
		client = new MyClient(ip, engine, assets, nEntities);
	}

	
	
	
}
