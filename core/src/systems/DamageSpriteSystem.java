package systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

	public DamageSpriteSystem(SpriteBatch batch) {
		super(Family.all(DamageSpriteComponent.class, SizeComponent.class,
				PositionComponent.class, HealthComponent.class).get());
		this.batch = batch;
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
			if(damageSprite.damageSprite != Assets.GameSprite.Damage3.getSprite())
				damageSprite.damageSprite = Assets.GameSprite.Damage3.getSprite();
		}
		else if(health.damageTaken>health.hp/4f){
			if(damageSprite.damageSprite != Assets.GameSprite.Damage2.getSprite())
				damageSprite.damageSprite = Assets.GameSprite.Damage2.getSprite();
		}
		
		else if(health.damageTaken>health.hp/6f){
			if(damageSprite.damageSprite != Assets.GameSprite.Damage1.getSprite())
				damageSprite.damageSprite = Assets.GameSprite.Damage1.getSprite();		
			
		}
		else{
			damageSprite.damageSprite = null;
		}
		

	}

}
