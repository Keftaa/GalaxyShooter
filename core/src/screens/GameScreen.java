package screens;

import managers.BodyGenerator;
import managers.BulletEngineManager;
import managers.CouplesManager;
import managers.EntitiesManager;
import managers.LightsManager;
import managers.NetworkDispatcher;
import managers.ParticlesManager;
import managers.RelativeSpeedManager;
import managers.WorldContactListener;
import systems.BulletsLauncherSystem;
import systems.CouplesSystem;
import systems.DamageSpriteSystem;
import systems.LightsPositioningSystem;
import systems.MovementSystem;
import systems.OutOfBoundsSystem;
import systems.ParticleRenderingSystem;
import systems.PositionSyncSystem;
import systems.RelativeSpeedSystem;
import systems.SpriteRenderingAfterLightSystem;
import systems.SpriteRenderingSystem;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.galaxyshooter.game.Main;

import components.BodyComponent;
import components.BulletRateComponent;
import components.ControllableComponent;
import components.CoupledComponent;
import components.DispatchableComponent;
import components.LightComponent;
import components.ParticleComponent;
import components.PositionComponent;
import components.RelativeSpeedComponent;
import components.RenderableComponent;
import components.SizeComponent;
import components.SpriteComponent;

public class GameScreen implements Screen {

	private Main main;
	private World world;
	private WorldContactListener worldListener;
	private Box2DDebugRenderer b2drenderer;

	private BodyGenerator bodyGenerator;
	private EntitiesManager entitiesManager;
	private LightsManager lightsManager;
	private BulletEngineManager bulletEngineManager;
	private HUD hud;

	public GameScreen(Main main) {
		this.main = main;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void show() {
		world = new World(new Vector2(0, 0), true);
		worldListener = new WorldContactListener();
		world.setContactListener(worldListener);
		b2drenderer = new Box2DDebugRenderer();

		bodyGenerator = new BodyGenerator(world);

		entitiesManager = new EntitiesManager(main.engine, main.viewport);

		lightsManager = new LightsManager(world, main.viewport);

		main.engine.addEntityListener(
				Family.all(BodyComponent.class, SizeComponent.class,
						PositionComponent.class).get(), bodyGenerator);

		// To be replaced by a sprite generator manager
		main.engine.addEntityListener(
				Family.all(SpriteComponent.class, RenderableComponent.class,
						SizeComponent.class, PositionComponent.class).get(),
				new SpriteRenderingSystem(main.batch));

		bulletEngineManager = new BulletEngineManager(main.engine,
				entitiesManager);

		main.engine.addEntityListener(Family.all(BulletRateComponent.class)
				.get(), bulletEngineManager);

		main.engine.addEntityListener(Family.all(CoupledComponent.class).get(),
				new CouplesManager());

		main.engine.addEntityListener(Family.all(RelativeSpeedComponent.class)
				.get(), new RelativeSpeedManager());

		main.engine.addEntityListener(
				Family.all(LightComponent.class, RenderableComponent.class)
						.get(), lightsManager);

		main.engine.addEntityListener(Family.all(DispatchableComponent.class)
				.get(), new NetworkDispatcher(main.client));

		main.engine.addEntityListener(
				Family.all(ParticleComponent.class).get(),
				new ParticlesManager());

		hud = new HUD(main.engine);
		main.engine.addEntityListener(Family.all(ControllableComponent.class)
				.get(), hud);

		// main.engine.addSystem(new ControllingSystem());
		main.engine.addSystem(new DamageSpriteSystem(main.batch));
		main.engine.addSystem(new PositionSyncSystem());
		main.engine.addSystem(new CouplesSystem(main.batch));
		main.engine.addSystem(new MovementSystem());
		main.engine.addSystem(new BulletsLauncherSystem(main.engine,
				bulletEngineManager));
		main.engine.getSystem(BulletsLauncherSystem.class).setProcessing(false);
		main.engine.addSystem(new RelativeSpeedSystem());
		main.engine.addSystem(new OutOfBoundsSystem(main.viewport,
				lightsManager, main.engine, bodyGenerator));
		main.engine.addSystem(new LightsPositioningSystem());
		main.engine.addSystem(new SpriteRenderingAfterLightSystem(main.batch));
		main.engine.addSystem(new SpriteRenderingSystem(main.batch));
		main.engine.addSystem(new ParticleRenderingSystem(main.batch));

		entitiesManager.addBackgroundWithStars(40);
		entitiesManager.addPlayer();
		entitiesManager.initBullet();

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		main.batch.setProjectionMatrix(main.viewport.getCamera().combined);
		main.viewport.apply();

		main.batch.begin();

		for (EntitySystem system : main.engine.getSystems())
			if (system.checkProcessing())
				if (!(system instanceof DamageSpriteSystem)
						&& !(system instanceof SpriteRenderingAfterLightSystem)
						&& !(system instanceof ParticleRenderingSystem))
					system.update(delta);

		main.batch.end();

		lightsManager.handler
				.setCombinedMatrix((OrthographicCamera) main.viewport
						.getCamera());
		lightsManager.handler.setAmbientLight(lightsManager.ambientLight);
		lightsManager.handler.updateAndRender();

		hud.render();

		main.batch.begin();
		main.engine.getSystem(ParticleRenderingSystem.class).update(delta);
		main.engine.getSystem(SpriteRenderingAfterLightSystem.class).update(
				delta);

		main.engine.getSystem(DamageSpriteSystem.class).update(delta);

		main.batch.end();
	//	b2drenderer.render(world, main.viewport.getCamera().combined);
		world.step(1 / 60f, 6, 2);

		if (Gdx.input.isKeyPressed(Input.Keys.Z))
			((OrthographicCamera) main.viewport.getCamera()).zoom += 0.01f;

	}

	@Override
	public void resize(int width, int height) {
		main.viewport.update(width, height);
		hud.viewport.update(width, height);
		lightsManager.handler.useCustomViewport(main.viewport.getScreenX(),
				main.viewport.getScreenY(),
				(int) main.viewport.getScreenWidth(),
				(int) main.viewport.getScreenHeight());

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

}
