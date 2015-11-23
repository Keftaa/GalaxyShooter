package screens;

import networking.MyServer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.galaxyshooter.game.Constants;
import com.galaxyshooter.game.Main;

public class MainScreen implements Screen {
	Main main;
	Stage stage;
	FitViewport viewport; // special viewport coz' we're working on pixel coordinates here
	Table root;
	Label gameTitle, errorMsg;
	LabelStyle labelStyle;
	TextField ipField;
	TextFieldStyle fieldStyle;
	TextButton playAsServerButton, playAsClientButton;
	TextButtonStyle buttonStyle;
	
	Skin skin;
	
	
	public MainScreen(Main main){
		this.main = main;
	}

	@Override
	public void show() {
		
		viewport = new FitViewport(Constants.PIXEL_WIDTH, Constants.PIXEL_HEIGHT);
		stage = new Stage(viewport);
	    root = new Table();
	    root.setFillParent(true);
	    stage.addActor(root);
	    root.setDebug(true);

	    gameTitle = new Label("GalaxyShooter", main.skinManager.skin, "lblBig");
	    playAsServerButton = new TextButton("Play as Server", main.skinManager.skin, "txtButton");
	    playAsClientButton = new TextButton("Play as Client", main.skinManager.skin, "txtButton");
	    ipField = new TextField("", main.skinManager.skin, "txtField");
	    errorMsg = new Label("Disconnected", main.skinManager.skin, "lblSmall");
	    
	    root.add(gameTitle);
	    root.row().pad(10);
	    root.add(playAsServerButton).size(300, 75);
	    root.row().pad(10);
		root.add(ipField).size(300, 75);
		root.row().pad(10);
	    root.add(playAsClientButton).size(300, 75);
	    root.row().pad(10);
	    root.add(errorMsg);
	    

	    playAsServerButton.addCaptureListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				MyServer server = new MyServer();
				server.serverListener();
				main.connect("127.0.0.1");
				main.setScreen(new GameScreen(main));
			}
	    	
	    });
	    

	    playAsClientButton.addCaptureListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				try{
					main.connect(ipField.getText());
					errorMsg.setText("Connected successfully");
					main.setScreen(new GameScreen(main));
				} catch(Exception e){
					errorMsg.setText("Could not connect to the specified address");
				}
			}
	    	
	    });
	    
	    
	    
	    Gdx.input.setInputProcessor(stage);
		
	}

	@Override
	public void render(float delta) {
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    stage.act(delta);
	    stage.draw();

	}

	@Override
	public void resize(int width, int height) {		
		viewport.update(width, height);
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {
		stage.dispose();
	}
	
}
