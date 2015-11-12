package managers;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.galaxyshooter.game.Assets;

import components.BodyComponent;
import components.ControllableComponent;
import components.CoupledComponent;
import components.DamageSpriteComponent;
import components.DispatchableComponent;
import components.FPSDepComponent;
import components.LightComponent;
import components.OutOfBoundsComponent;
import components.OutOfBoundsComponent.AdequateAction;
import components.ParticleComponent;
import components.ParticleComponent.GameParticle;
import components.PositionComponent;
import components.RelativeSpeedComponent;
import components.RenderableComponent;
import components.SizeComponent;
import components.SpeedComponent;
import components.SpriteComponent;

public class EntitiesManager {
	private Engine engine;
	private FitViewport viewport;
	private TextureAtlas atlas;
	public EntitiesManager(Engine engine, FitViewport viewport, TextureAtlas atlas) {
		this.engine = engine;
		this.viewport = viewport;
		this.atlas = atlas;
	}

	public void addBullets(int count) {
		for (int i = 0; i < count; i++) {
			Entity bullet = new Entity();

			PositionComponent position = new PositionComponent();
			SizeComponent size = new SizeComponent();
			SpeedComponent speed = new SpeedComponent();
			SpriteComponent sprite = new SpriteComponent();
			CoupledComponent coupled = new CoupledComponent();
			DispatchableComponent dispatchable = new DispatchableComponent();
			ParticleComponent particle = new ParticleComponent();

			size.width = 0.4f;
			size.height = 0.4f;
			speed.x = 0;
			speed.y = 20;
			sprite.sprite = atlas.createSprite("bulletGreen");
			sprite.afterLight = true;
			coupled.coupleId = 0;
			coupled.offsetX = 0f;
			coupled.offsetY = 10f;
			coupled.leader = false;
			dispatchable.componentsToDispatch.add(position);
			dispatchable.componentsToDispatch.add(speed);
			dispatchable.componentsToDispatch.add(sprite);
			dispatchable.componentsToDispatch.add(particle);
			dispatchable.componentsToDispatch.add(size);
			particle.gameParticle = GameParticle.BulletThrustParticle;

			bullet.add(position);
			bullet.add(size);
			bullet.add(speed);
			bullet.add(sprite);
			bullet.add(coupled);
			bullet.add(dispatchable);
			bullet.add(particle);
			// Bullet's body auto-generated at launch.

			engine.addEntity(bullet);
		}

	}

	public void addPlayer() {
		Entity player = new Entity();
		RenderableComponent renderable = new RenderableComponent();
		PositionComponent position = new PositionComponent();
		SizeComponent size = new SizeComponent();
		SpeedComponent speed = new SpeedComponent();
		SpriteComponent sprite = new SpriteComponent();
		BodyComponent body = new BodyComponent();
		ControllableComponent controllable = new ControllableComponent();
		DamageSpriteComponent damageSprite = new DamageSpriteComponent();
		CoupledComponent coupled = new CoupledComponent();
		RelativeSpeedComponent relativeSpeed = new RelativeSpeedComponent();
		OutOfBoundsComponent outOfBounds = new OutOfBoundsComponent();
		ParticleComponent particle = new ParticleComponent();

		position.x = 10;
		position.y = 5;
		position.overridenByBody = true;
		size.width = 6;
		size.height = 4.01f;
		speed.x = 100;
		speed.y = 0;
		sprite.sprite = atlas.createSprite("player");
		sprite.afterLight = true;
		damageSprite.damageSprite = atlas.createSprite("playerShip2_damage2");
		coupled.coupleId = 0;
		coupled.leader = true;
		relativeSpeed.leader = true;
		relativeSpeed.groupId = 0;
		body.vertices = new Vector2[7];
		body.vertices[0] = new Vector2(-0.33036f * size.width * 2, -0.44000f
				* size.height * 2);
		body.vertices[1] = new Vector2(+0.32143f * size.width * 2, -0.44000f
				* size.height * 2);
		body.vertices[2] = new Vector2(+0.47321f * size.width * 2, -0.08000f
				* size.height * 2);
		body.vertices[3] = new Vector2(+0.13393f * size.width * 2, +0.21333f
				* size.height * 2);
		body.vertices[4] = new Vector2(+0.02679f * size.width * 2, +0.50667f
				* size.height * 2);
		body.vertices[5] = new Vector2(-0.10714f * size.width * 2, +0.21333f
				* size.height * 2);
		body.vertices[6] = new Vector2(-0.48214f * size.width * 2, -0.08000f
				* size.height * 2);
		
		outOfBounds.action = AdequateAction.ALERT;
		particle.gameParticle = GameParticle.ThrustParticle;
		player.add(renderable);
		player.add(position);
		player.add(size);
		player.add(speed);
		player.add(sprite);
		player.add(body);
		player.add(controllable);
		player.add(coupled);
		player.add(relativeSpeed);
		player.add(outOfBounds);
		player.add(damageSprite);
		player.add(particle);
		engine.addEntity(player);
	}

	public void addBackgroundWithStars(int starCount) {
		Entity background = new Entity();
		PositionComponent bPosition = new PositionComponent();
		bPosition.x = viewport.getWorldWidth()/2;
		bPosition.y =  viewport.getWorldHeight()/2;
		SizeComponent bSize = new SizeComponent();
		bSize.width = viewport.getWorldWidth();
		bSize.height = viewport.getWorldHeight();
		RenderableComponent bRenderable = new RenderableComponent();
		SpriteComponent bSprite = new SpriteComponent();
		bSprite.sprite = atlas.createSprite("background");

		background.add(bSize);
		background.add(bPosition);
		background.add(bRenderable);
		background.add(bSprite);

		engine.addEntity(background);

		for (int i = 0; i < starCount; i++) {
			Entity star = new Entity();
			star.add(new RenderableComponent());
			SpriteComponent sprite = new SpriteComponent();
			sprite.sprite = atlas.createSprite("star");
			sprite.afterLight = true;
			SizeComponent size = new SizeComponent();
			size.width = MathUtils.random(0.5f, 1);
			size.height = MathUtils.random(0.5f, 1);
			PositionComponent position = new PositionComponent();
			position.x = MathUtils.random(-viewport.getWorldWidth() / 2,
					viewport.getWorldWidth() / 2);
			position.y = MathUtils.random(-viewport.getWorldHeight() / 2,
					viewport.getWorldHeight() / 2);
			SpeedComponent speed = new SpeedComponent();
			speed.x = 100;
			speed.y = -30;
			speed.active = true;
			speed.zDistance = 10;
			RelativeSpeedComponent relativeSpeed = new RelativeSpeedComponent();
			relativeSpeed.groupId = 0;
			OutOfBoundsComponent outOfBounds = new OutOfBoundsComponent();
			outOfBounds.action = AdequateAction.RESPAWN;
			LightComponent light = new LightComponent();
			light.color = Color.WHITE;
			light.distance = 3.9f;
			light.rays = 5;
			light.x = position.x;
			light.y = position.y;
			FPSDepComponent fpsDep = new FPSDepComponent();
			star.add(relativeSpeed);
			star.add(size);
			star.add(sprite);
			star.add(position);
			star.add(speed);
			star.add(outOfBounds);
			star.add(light);
			star.add(fpsDep);

			engine.addEntity(star);

		}

	}

	public void addMoreStars(int starCount) {
		for (int i = 0; i < starCount; i++) {
			Entity star = new Entity();
			star.add(new RenderableComponent());
			SpriteComponent sprite = new SpriteComponent();
			sprite.sprite = atlas.createSprite("star");
			sprite.afterLight = true;
			SizeComponent size = new SizeComponent();
			size.width = MathUtils.random(0.5f, 1);
			size.height = MathUtils.random(0.5f, 1);
			PositionComponent position = new PositionComponent();
			position.x = MathUtils.random(-viewport.getWorldWidth() / 2,
					viewport.getWorldWidth() / 2);
			position.y = MathUtils.random(-viewport.getWorldHeight() / 2,
					viewport.getWorldHeight() / 2);
			SpeedComponent speed = new SpeedComponent();
			speed.x = 100;
			speed.y = -30;
			speed.active = true;
			speed.zDistance = 10;
			RelativeSpeedComponent relativeSpeed = new RelativeSpeedComponent();
			relativeSpeed.groupId = 0;
			OutOfBoundsComponent outOfBounds = new OutOfBoundsComponent();
			outOfBounds.action = AdequateAction.RESPAWN;
			LightComponent light = new LightComponent();
			light.color = Color.WHITE;
			light.distance = 1.9f;
			light.rays = 5;
			light.x = position.x;
			light.y = position.y;
			FPSDepComponent fpsDep = new FPSDepComponent();
			star.add(relativeSpeed);
			star.add(size);
			star.add(sprite);
			star.add(position);
			star.add(speed);
			star.add(outOfBounds);
			star.add(light);
			star.add(fpsDep);

			engine.addEntity(star);
		}
	}
	

}
