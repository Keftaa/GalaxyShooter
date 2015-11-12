//package systems;
//
//import com.badlogic.ashley.core.ComponentMapper;
//import com.badlogic.ashley.core.Entity;
//import com.badlogic.ashley.core.Family;
//import com.badlogic.ashley.systems.IteratingSystem;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.math.Vector2;
//
//import components.BodyComponent;
//import components.ControllableComponent;
//import components.PositionComponent;
//import components.SpeedComponent;
//
//public class ControllingSystem extends IteratingSystem {
//	
//	ComponentMapper<BodyComponent> bodyMapper = ComponentMapper.getFor(BodyComponent.class);
//	ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);
//	public ControllingSystem() {
//		super(Family.all(ControllableComponent.class, BodyComponent.class, PositionComponent.class).get());
//	}
//
//	@Override
//	protected void processEntity(Entity entity, float deltaTime) {
//		BodyComponent body = bodyMapper.get(entity);
//		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
//			entity.getComponent(SpeedComponent.class).x = -200f;
//			entity.getComponent(SpeedComponent.class).active = true;
//		}
//		else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
//			body.body.applyForceToCenter(new Vector2(200, 0), true);
//			body.applyingForce = true;
//		}
//		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
//			body.body.applyForceToCenter(new Vector2(0, 200), true);
//			body.applyingForce = true;
//		}
//		else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
//			body.body.applyForceToCenter(new Vector2(0, -200), true);
//			body.applyingForce = true;
//		}
//		//entity.getComponent(SpeedComponent.class).active = false;
////		position.x = body.body.getPosition().x;
////		position.y = body.body.getPosition().y;
//		
//	}
//
//}
