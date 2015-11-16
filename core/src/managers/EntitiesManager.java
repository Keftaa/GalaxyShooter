package managers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasSprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.galaxyshooter.game.Assets;
import com.galaxyshooter.game.Constants;

import components.BodyComponent;
import components.BulletRateComponent;
import components.ContactDamageComponent;
import components.ControllableComponent;
import components.CoupledComponent;
import components.DamageSpriteComponent;
import components.DispatchableComponent;
import components.EngineCapacityComponent;
import components.FPSDepComponent;
import components.HealthComponent;
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
	private PooledEngine engine;
	private FitViewport viewport;
	private Assets assets;
	
	public EntitiesManager(PooledEngine engine, FitViewport viewport, Assets assets) {
		this.engine = engine;
		this.viewport = viewport;
		this.assets = assets;
	}

	public Entity createBullet() {
			Entity bullet = engine.createEntity();

			PositionComponent position = engine.createComponent(PositionComponent.class);
			SizeComponent size = engine.createComponent(SizeComponent.class);
			SpeedComponent speed = engine.createComponent(SpeedComponent.class);
			SpriteComponent sprite = engine.createComponent(SpriteComponent.class);
			CoupledComponent coupled = engine.createComponent(CoupledComponent.class);
			DispatchableComponent dispatchable = engine.createComponent(DispatchableComponent.class);
			ParticleComponent particle = engine.createComponent(ParticleComponent.class);
			OutOfBoundsComponent outOfBounds = engine.createComponent(OutOfBoundsComponent.class);
			BulletRateComponent bulletRate = engine.createComponent(BulletRateComponent.class);
			ContactDamageComponent damage = engine.createComponent(ContactDamageComponent.class);

			size.width = 0.4f;
			size.height = 1f;
			speed.x = 0;
			speed.y = 20;
			sprite.sprite =  new AtlasSprite(assets.atlas.findRegion(Assets.GameSprite.GreenLaser.getName()));
			sprite.afterLight = true;
			coupled.coupleId = 0;
			coupled.offsetX = 0f;
			coupled.offsetY = 10f;
			coupled.leader = false;
			particle.gameParticle = GameParticle.BulletThrustParticle;
			outOfBounds.action = AdequateAction.DISPOSE;
			bulletRate.bulletsPerSecond = 3f;
			damage.damagePoints = 5;
			
			bullet.add(coupled);
			bullet.add(position);
			bullet.add(size);
			bullet.add(speed);
			bullet.add(sprite);
			bullet.add(dispatchable);
			bullet.add(particle);
			bullet.add(outOfBounds);
			bullet.add(bulletRate);
			bullet.add(damage);
			// Bullet's body auto-generated at launch.
			
			return bullet;

	}
	
	public void initBullet(){
		Entity bullet = createBullet();
		engine.addEntity(bullet);
	}

	public void addPlayer() {
		Entity player = engine.createEntity();
		RenderableComponent renderable = engine.createComponent(RenderableComponent.class);
		PositionComponent position = engine.createComponent(PositionComponent.class);
		SizeComponent size = engine.createComponent(SizeComponent.class);
		SpeedComponent speed = engine.createComponent(SpeedComponent.class);
		SpriteComponent sprite = engine.createComponent(SpriteComponent.class);
		BodyComponent body = engine.createComponent(BodyComponent.class);
		ControllableComponent controllable = engine.createComponent(ControllableComponent.class);
		DamageSpriteComponent damageSprite = engine.createComponent(DamageSpriteComponent.class);
		CoupledComponent coupled = engine.createComponent(CoupledComponent.class);
		RelativeSpeedComponent relativeSpeed = engine.createComponent(RelativeSpeedComponent.class);
		OutOfBoundsComponent outOfBounds = engine.createComponent(OutOfBoundsComponent.class);
		ParticleComponent particle = engine.createComponent(ParticleComponent.class);
		EngineCapacityComponent engineCapacity = engine.createComponent(EngineCapacityComponent.class);
		HealthComponent health = engine.createComponent(HealthComponent.class);
		DispatchableComponent dispatchable = engine.createComponent(DispatchableComponent.class);

		position.x = 10;
		position.y = 5;
		position.overridenByBody = true;
		size.width = 6;
		size.height = 4.01f;
		speed.x = 100;
		speed.y = 0;
		sprite.sprite = new AtlasSprite(assets.atlas.findRegion(Assets.GameSprite.GreenShip.getName()));
		sprite.afterLight = true;
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
		engineCapacity.maxTime = 10;
		health.hp = 100;
		dispatchable.permanent = true;
		
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
		player.add(engineCapacity);
		player.add(health);
		player.add(dispatchable);
		engine.addEntity(player);
	}

	public void addBackgroundWithStars(int starCount) {
		Entity background = engine.createEntity();
		PositionComponent bPosition = engine.createComponent(PositionComponent.class);
		bPosition.x = viewport.getWorldWidth()/2;
		bPosition.y =  viewport.getWorldHeight()/2;
		SizeComponent bSize = engine.createComponent(SizeComponent.class);
		bSize.width = viewport.getWorldWidth();
		bSize.height = viewport.getWorldHeight();
		RenderableComponent bRenderable = engine.createComponent(RenderableComponent.class);
		SpriteComponent bSprite = engine.createComponent(SpriteComponent.class);
		bSprite.sprite = new AtlasSprite(assets.atlas.findRegion(Assets.GameSprite.Background.getName()));

		background.add(bSize);
		background.add(bPosition);
		background.add(bRenderable);
		background.add(bSprite);

		engine.addEntity(background);

		for (int i = 0; i < starCount; i++) {
			Entity star = engine.createEntity();
			star.add(new RenderableComponent());
			SpriteComponent sprite = engine.createComponent(SpriteComponent.class);
			sprite.sprite =  new AtlasSprite(assets.atlas.findRegion(Assets.GameSprite.Star.getName()));
			
			sprite.afterLight = true;
			SizeComponent size = engine.createComponent(SizeComponent.class);
			size.width = MathUtils.random(0.5f, 1);
			size.height = MathUtils.random(0.5f, 1);
			PositionComponent position = engine.createComponent(PositionComponent.class);
			position.x = MathUtils.random(-viewport.getWorldWidth() / 2,
					viewport.getWorldWidth() / 2);
			position.y = MathUtils.random(-viewport.getWorldHeight() / 2,
					viewport.getWorldHeight() / 2);
			SpeedComponent speed = engine.createComponent(SpeedComponent.class);
			speed.x = 100;
			speed.y = -30;
			speed.active = true;
			speed.zDistance = 10;
			RelativeSpeedComponent relativeSpeed = engine.createComponent(RelativeSpeedComponent.class);
			relativeSpeed.groupId = 0;
			OutOfBoundsComponent outOfBounds = engine.createComponent(OutOfBoundsComponent.class);
			outOfBounds.action = AdequateAction.RESPAWN;
			LightComponent light = engine.createComponent(LightComponent.class);
			light.color = Color.WHITE;
			light.distance = 3.9f;
			light.rays = 5;
			light.x = position.x;
			light.y = position.y;
			FPSDepComponent fpsDep = engine.createComponent(FPSDepComponent.class);
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
			Entity star = engine.createEntity();
			star.add(new RenderableComponent());
			SpriteComponent sprite = engine.createComponent(SpriteComponent.class);
			sprite.sprite =  new AtlasSprite(assets.atlas.findRegion(Assets.GameSprite.Star.getName()));
			sprite.afterLight = true;
			SizeComponent size = engine.createComponent(SizeComponent.class);
			size.width = MathUtils.random(0.5f, 1);
			size.height = MathUtils.random(0.5f, 1);
			PositionComponent position = engine.createComponent(PositionComponent.class);
			position.x = MathUtils.random(-viewport.getWorldWidth() / 2,
					viewport.getWorldWidth() / 2);
			position.y = MathUtils.random(-viewport.getWorldHeight() / 2,
					viewport.getWorldHeight() / 2);
			SpeedComponent speed = engine.createComponent(SpeedComponent.class);
			speed.x = 100;
			speed.y = -30;
			speed.active = true;
			speed.zDistance = 10;
			RelativeSpeedComponent relativeSpeed = engine.createComponent(RelativeSpeedComponent.class);
			relativeSpeed.groupId = 0;
			OutOfBoundsComponent outOfBounds = engine.createComponent(OutOfBoundsComponent.class);
			outOfBounds.action = AdequateAction.RESPAWN;
			LightComponent light = engine.createComponent(LightComponent.class);
			light.color = Color.WHITE;
			light.distance = 1.9f;
			light.rays = 5;
			light.x = position.x;
			light.y = position.y;
			FPSDepComponent fpsDep = engine.createComponent(FPSDepComponent.class);
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
	
	public Entity oppLight(){
		Entity oppLight = engine.createEntity();
		PositionComponent position = engine.createComponent(PositionComponent.class);
		position.x = 0;
		position.y = 0;

		oppLight.add(engine.createComponent(RenderableComponent.class));
		
		LightComponent light = engine.createComponent(LightComponent.class);
		light.x = 0;
		light.y = Constants.WORLD_HEIGHT /2;
		light.color = Color.RED;
		light.distance = 300;
		light.rays = 200;
		
		oppLight.add(position);
		oppLight.add(light);
		
		return oppLight;
	}
	
	public void initOppLight(){
		Entity oppLight = oppLight();
		engine.addEntity(oppLight);
	}
	

}
