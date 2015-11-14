package systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import components.DamageSpriteComponent;
import components.PositionComponent;
import components.RenderableComponent;
import components.SizeComponent;
import components.SpriteComponent;

public class SpriteRenderingSystem extends IteratingSystem implements
		EntityListener {
	private ComponentMapper<SpriteComponent> spriteMapper = ComponentMapper
			.getFor(SpriteComponent.class);

	private ComponentMapper<SizeComponent> sizeMapper = ComponentMapper
			.getFor(SizeComponent.class);
	private ComponentMapper<PositionComponent> positionMapper = ComponentMapper
			.getFor(PositionComponent.class);
//	private ComponentMapper<DamageSpriteComponent> damageSpriteMapper = ComponentMapper
//			.getFor(DamageSpriteComponent.class);

	private SpriteBatch batch;

	public SpriteRenderingSystem(SpriteBatch batch) {
		super(Family.all(SpriteComponent.class, RenderableComponent.class,
				SizeComponent.class, PositionComponent.class).get());

		this.batch = batch;
	}

	@Override
	public void entityAdded(Entity entity) {
		SpriteComponent sprite = spriteMapper.get(entity);
		SizeComponent size = sizeMapper.get(entity);
//		DamageSpriteComponent damageSprite = damageSpriteMapper.get(entity);
		sprite.sprite.setSize(size.width * 2, size.height * 2); // bcos body is
//		if(damageSprite != null && damageSprite.damageSprite != null)
//			damageSprite.damageSprite.setSize(size.width * 2, size.height * 2);														// hw hh
	}

	@Override
	public void entityRemoved(Entity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		PositionComponent position = positionMapper.get(entity);
		SpriteComponent sprite = spriteMapper.get(entity);
//		DamageSpriteComponent damageSprite = damageSpriteMapper.get(entity);
		if (!sprite.afterLight) {
			sprite.sprite.draw(batch);
			sprite.sprite.setPosition(
					position.x - sprite.sprite.getWidth() / 2, position.y
							- sprite.sprite.getHeight() / 2);
//			if(damageSprite != null){
//				damageSprite.damageSprite.setPosition(
//						position.x - sprite.sprite.getWidth() / 2, position.y
//								- sprite.sprite.getHeight() / 2);			
//				damageSprite.damageSprite.draw(batch);			
//			}

		}
		

		

	}

}
