package systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import components.DamageSpriteComponent;
import components.PositionComponent;
import components.SizeComponent;

public class DamageSpriteSystem extends IteratingSystem {
	private ComponentMapper<DamageSpriteComponent> damageMapper = ComponentMapper
			.getFor(DamageSpriteComponent.class);
	
	private ComponentMapper<SizeComponent> sizeMapper = ComponentMapper.getFor(SizeComponent.class);
	private ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);

	private SpriteBatch batch;
	public DamageSpriteSystem(SpriteBatch batch) {
		super(Family.all(DamageSpriteComponent.class, SizeComponent.class, PositionComponent.class).get());
		this.batch = batch;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		DamageSpriteComponent damageSprite = damageMapper.get(entity);
		PositionComponent position = positionMapper.get(entity);
		SizeComponent size = sizeMapper.get(entity);
		damageSprite.damageSprite.setPosition(position.x - damageSprite.damageSprite.getWidth()/2, 
										position.y - damageSprite.damageSprite.getHeight()/2);
		damageSprite.damageSprite.setSize(size.width*2, size.height*2);
		damageSprite.damageSprite.draw(batch);

	}

}
