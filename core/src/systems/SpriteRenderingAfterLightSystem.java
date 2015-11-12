package systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import components.DamageSpriteComponent;
import components.PositionComponent;
import components.RenderableComponent;
import components.SizeComponent;
import components.SpriteComponent;

public class SpriteRenderingAfterLightSystem extends IteratingSystem {

	private ComponentMapper<PositionComponent> positionMapper = ComponentMapper
			.getFor(PositionComponent.class);
	private ComponentMapper<SpriteComponent> spriteMapper = ComponentMapper
			.getFor(SpriteComponent.class);
	private ComponentMapper<DamageSpriteComponent> damageSpriteMapper = ComponentMapper
			.getFor(DamageSpriteComponent.class);

	private SpriteBatch batch;

	public SpriteRenderingAfterLightSystem(SpriteBatch batch) {
		super(Family.all(SpriteComponent.class, RenderableComponent.class,
				SizeComponent.class, PositionComponent.class).get());

		this.batch = batch;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		PositionComponent position = positionMapper.get(entity);
		SpriteComponent sprite = spriteMapper.get(entity);
		DamageSpriteComponent damageSprite = damageSpriteMapper.get(entity);
		if(sprite.afterLight){
			sprite.sprite.setPosition(position.x - sprite.sprite.getWidth()/2,
					position.y - sprite.sprite.getHeight()/2);
			sprite.sprite.draw(batch);
			if(damageSprite != null){
				damageSprite.damageSprite.setPosition(
						position.x - sprite.sprite.getWidth() / 2, position.y
								- sprite.sprite.getHeight() / 2);			
				damageSprite.damageSprite.draw(batch);			
			}

		}
			
	}
}
