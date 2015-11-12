package screens;

import java.util.HashMap;

import systems.BulletsLauncherSystem;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.galaxyshooter.game.Assets;

import components.SpeedComponent;

public class HUD implements EntityListener {
	public Stage stage;
	public Viewport viewport;
	private HashMap<Entity, Actor> entityActor;
	private ComponentMapper<SpeedComponent> speedMapper = ComponentMapper
			.getFor(SpeedComponent.class);


	Engine engine;
	private HorizontalGroup lives;
	private Group arrows;
	private Table root;
	private Image life1, life2, life3, life4, upArrow, downArrow, leftArrow,
			rightArrow, shootButton;

	
	HUD(Engine engine) {
		viewport = new FitViewport(800, 480);
		this.engine = engine;

		stage = new Stage(viewport);
		stage.getCamera().update();
		stage.setDebugAll(true);
		root = new Table();
		root.setFillParent(true);

		root.debug();
		lives = new HorizontalGroup();
		life1 = new Image(Assets.GameSprite.GreenShipLives.getSprite());
		life2 = new Image(Assets.GameSprite.GreenShipLives.getSprite());
		life3 = new Image(Assets.GameSprite.GreenShipLives.getSprite());
		life4 = new Image(Assets.GameSprite.GreenShipLives.getSprite());
		life1.setPosition(0, 0);
		life2.setPosition(life1.getWidth(), 0);
		life3.setPosition(life1.getWidth() * 2, 0);
		life4.setPosition(life1.getWidth() * 3, 0);
		lives.addActor(life1);
		lives.addActor(life2);
		lives.addActor(life3);
		lives.addActor(life4);

		arrows = new Group();
		arrows.setBounds(0, 0, 250, 150);
		upArrow = new Image(Assets.GameSprite.Up.getSprite());
		downArrow = new Image(Assets.GameSprite.Down.getSprite());
		rightArrow = new Image(Assets.GameSprite.Right.getSprite());
		leftArrow = new Image(Assets.GameSprite.Left.getSprite());
		shootButton = new Image(Assets.GameSprite.Shoot.getSprite());

		upArrow.setBounds(arrows.getWidth() / 3,
				arrows.getHeight() - arrows.getHeight() / 2,
				upArrow.getWidth() / 2, arrows.getHeight() / 2);
		// maybe create enums with the textures ? :\
		downArrow.setBounds(arrows.getWidth() / 3, 0, downArrow.getWidth() / 2,
				arrows.getHeight() / 2);

		leftArrow.setBounds(0, 0, arrows.getWidth() / 3, arrows.getHeight());

		rightArrow.setBounds(arrows.getWidth() - arrows.getWidth() / 3, 0,
				arrows.getWidth() / 3, arrows.getHeight());
		arrows.addActor(upArrow);
		arrows.addActor(downArrow);
		arrows.addActor(leftArrow);
		arrows.addActor(rightArrow);

		arrows.setPosition(0, 0);

		root.setPosition(0, 0);
		root.align(Align.bottomLeft);
		// root.align(Align.left);
		root.add(lives);
		root.row().expandY();
		root.add(arrows).bottom();
		root.add(shootButton).bottom().right().expand(true, false);

		stage.addActor(root);

		entityActor = new HashMap<Entity, Actor>();
		Gdx.input.setInputProcessor(stage);

	}

	public void render() {
		stage.draw();
		stage.act();
	}

	@Override
	public void entityAdded(Entity entity) {
		entityActor.put(entity, arrows);
		final SpeedComponent speed = speedMapper.get(entity);
		upArrow.addCaptureListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

				speed.y = 200;
				speed.x = 0;
				speed.active = true;

				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				speed.active = false;
			}

		});

		downArrow.addCaptureListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

				speed.y = -200;
				speed.x = 0;
				speed.active = true;

				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				speed.active = false;
			}

		});

		leftArrow.addCaptureListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

				speed.y = 0;
				speed.x = -200;
				speed.active = true;

				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				speed.active = false;
			}

		});

		rightArrow.addCaptureListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

				speed.y = 0;
				speed.x = 200;
				speed.active = true;

				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				speed.active = false;
			}

		});
		
		
		shootButton.addCaptureListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				
				engine.getSystem(BulletsLauncherSystem.class).setProcessing(true);
				return true;
			}	
			
			
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				engine.getSystem(BulletsLauncherSystem.class).setProcessing(false);
			}		
			
		});


	}

	@Override
	public void entityRemoved(Entity entity) {
		// TODO Auto-generated method stub

	}

}
