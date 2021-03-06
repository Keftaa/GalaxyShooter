package screens;

import java.util.HashMap;

import managers.HealthManager;
import managers.ScoreManager;
import systems.BulletsLauncherSystem;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.galaxyshooter.game.Assets;

import components.EngineCapacityComponent;
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
	private Label scoreLabel, healthLabel;
	private int healthLeft;
	
	private float shotEngineCounter, maxTime;
	private boolean shootButtonPressed;
	private ScoreManager scoreManager;
	private HealthManager healthManager;
	HUD(Engine engine, Assets assets, Skin skin, ScoreManager scoreManager, HealthManager healthManager) {
		this.scoreManager = scoreManager;
		this.healthManager = healthManager;
		viewport = new FitViewport(800, 480);
		this.engine = engine;

		stage = new Stage(viewport);
		stage.getCamera().update();
		stage.setDebugAll(true);
		root = new Table();
		root.setFillParent(true);

		root.debug();
		lives = new HorizontalGroup();
//		life1 = new Image(assets.atlas.findRegion(Assets.GameSprite.GreenShipLives.getName()));
//		life2 = new Image(assets.atlas.findRegion(Assets.GameSprite.GreenShipLives.getName()));
//		life3 = new Image(assets.atlas.findRegion(Assets.GameSprite.GreenShipLives.getName()));
//		life4 = new Image(assets.atlas.findRegion(Assets.GameSprite.GreenShipLives.getName()));
//		life1.setPosition(0, 0);
//		life2.setPosition(life1.getWidth(), 0);
//		life3.setPosition(life1.getWidth() * 2, 0);
//		life4.setPosition(life1.getWidth() * 3, 0);
//		lives.addActor(life1);
//		lives.addActor(life2);
//		lives.addActor(life3);
//		lives.addActor(life4);
		
		scoreLabel = new Label(String.valueOf(scoreManager.score), skin, "lblSmall");
		healthLabel = new Label("HP: "+healthManager.healthLeft, skin, "lblSmall");
		arrows = new Group();
		arrows.setBounds(0, 0, 250, 150);
		upArrow = new Image(assets.atlas.findRegion(Assets.GameSprite.Up.getName()));
		downArrow = new Image(assets.atlas.findRegion(Assets.GameSprite.Down.getName()));
		rightArrow = new Image(assets.atlas.findRegion(Assets.GameSprite.Right.getName()));
		leftArrow = new Image(assets.atlas.findRegion(Assets.GameSprite.Left.getName()));
		shootButton = new Image(assets.atlas.findRegion(Assets.GameSprite.Shoot.getName()));

		upArrow.setBounds(arrows.getWidth() / 3,
				arrows.getHeight() - arrows.getHeight() / 2,
				upArrow.getWidth(), arrows.getHeight() / 2);

		downArrow.setBounds(arrows.getWidth() / 3, 0, downArrow.getWidth(),
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
		root.add(scoreLabel);
		root.add(healthLabel);
		
		root.row().expandY();
		root.add(arrows).bottom();
		root.add(shootButton).bottom().right().expand(true, false);
		

		stage.addActor(root);

		entityActor = new HashMap<Entity, Actor>();
		Gdx.input.setInputProcessor(stage);
		
		shotEngineCounter = 0;
		shootButtonPressed = false;

	}

	public void render() {
		stage.draw();
		stage.act();
		
		
		
		if(shootButtonPressed){
			shotEngineCounter+=Gdx.graphics.getDeltaTime();
		}
		else if(shotEngineCounter>0){
			shotEngineCounter -= Gdx.graphics.getDeltaTime();
		}
		
		if(shotEngineCounter>maxTime){
			engine.getSystem(BulletsLauncherSystem.class).setProcessing(false);
			System.out.println("Overheat");
		}
		
		colorizeShotButton();
		scoreLabel.setText(String.valueOf(scoreManager.score));
		scoreManager.render();

		healthLabel.setText(String.valueOf("HP: "+healthManager.healthLeft));
	}

	@Override
	public void entityAdded(Entity entity) {
		entityActor.put(entity, arrows);
		maxTime= entity.getComponent(EngineCapacityComponent.class).maxTime;
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

		shootButton.addCaptureListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

				engine.getSystem(BulletsLauncherSystem.class).setProcessing(
						true);
				shootButtonPressed = true;
				return true;
			}
			
			

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				engine.getSystem(BulletsLauncherSystem.class).setProcessing(
						false);
				shootButtonPressed = false;
			}

		});

	}

	@Override
	public void entityRemoved(Entity entity) {
		
	}
	
	private void colorizeShotButton(){
		
		shootButton.setColor(shotEngineCounter, maxTime-shotEngineCounter, 0, 1);
//		if(maxTime-shotEngineCounter<=0)
//			shootButton.setColor(Color.RED);
//		else if(maxTime-shotEngineCounter<=maxTime/4f)
//			shootButton.setColor(Color.ROYAL);
//		else if(maxTime-shotEngineCounter<=maxTime/3f)
//			shootButton.setColor(Color.ORANGE);
//		else if(maxTime-shotEngineCounter<=maxTime/2f)
//			shootButton.setColor(Color.GREEN);

	}

}
