package systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasSprite;
import com.galaxyshooter.game.Assets;

import components.DamageSpriteComponent;
import components.HealthComponent;
import components.PositionComponent;
import components.SizeComponent;

public class DamageSpriteSystem extends IteratingSystem {
	private ComponentMapper<DamageSpriteComponent> damageMapper = ComponentMapper
			.getFor(DamageSpriteComponent.class);

	private ComponentMapper<SizeComponent> sizeMapper = ComponentMapper
			.getFor(SizeComponent.class);
	private ComponentMapper<PositionComponent> positionMapper = ComponentMapper
			.getFor(PositionComponent.class);
	private ComponentMapper<HealthComponent> healthMapper = ComponentMapper
			.getFor(HealthComponent.class);

	private SpriteBatch batch;
	private Assets assets;

	public DamageSpriteSystem(SpriteBatch batch, Assets assets) {
		super(Family.all(DamageSpriteComponent.class, SizeComponent.class,
				PositionComponent.class, HealthComponent.class).get());
		this.batch = batch;
		this.assets = assets;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {

		DamageSpriteComponent damageSprite = damageMapper.get(entity);
		PositionComponent position = positionMapper.get(entity);
		HealthComponent health = healthMapper.get(entity);
		SizeComponent size = sizeMapper.get(entity);

		updateDamageSprite(damageSprite, health);
		if(damageSprite.damageSprite==null) return;

		damageSprite.damageSprite.setPosition(position.x
				- damageSprite.damageSprite.getWidth() / 2, position.y
				- damageSprite.damageSprite.getHeight() / 2);
		damageSprite.damageSprite.setSize(size.width * 2, size.height * 2);
		damageSprite.damageSprite.draw(batch);

	}

	private void updateDamageSprite(DamageSpriteComponent damageSprite,
			HealthComponent health) {
		
		

		if(health.damageTaken>health.hp/2f){
			if(!damageSprite.damageSprite.getAtlasRegion().name.equals(Assets.GameSprite.Damage3.getName()))
				damageSprite.damageSprite = new AtlasSprite(assets.atlas.findRegion(Assets.GameSprite.Damage3.getName())); 
		}
		else if(health.damageTaken>health.hp/4f){
			if(!damageSprite.damageSprite.getAtlasRegion().name.equals(Assets.GameSprite.Damage2.getName()))
				damageSprite.damageSprite = new AtlasSprite(assets.atlas.findRegion(Assets.GameSprite.Damage2.getName())); 
		}
		
		else if(health.damageTaken>health.hp/6f){
			if(damageSprite.damageSprite==null)
				damageSprite.damageSprite = new AtlasSprite(assets.atlas.findRegion(Assets.GameSprite.Damage1.getName())); 
			if(!damageSprite.damageSprite.getAtlasRegion().name.equals(Assets.GameSprite.Damage1.getName()))
				damageSprite.damageSprite = new AtlasSprite(assets.atlas.findRegion(Assets.GameSprite.Damage1.getName())); 
			
		}
		else{
			damageSprite.damageSprite = null;
		}
		

	}

}
