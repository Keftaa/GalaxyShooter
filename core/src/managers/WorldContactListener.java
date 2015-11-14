package managers;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import components.ContactDamageComponent;
import components.HealthComponent;

public class WorldContactListener implements ContactListener {

	private ComponentMapper<HealthComponent> healthMapper = ComponentMapper
			.getFor(HealthComponent.class);

	private ComponentMapper<ContactDamageComponent> damageMapper = ComponentMapper
			.getFor(ContactDamageComponent.class);

	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();

		Entity entityA = (Entity) fixtureA.getUserData();
		Entity entityB = (Entity) fixtureB.getUserData();
		
		
		HealthComponent health = healthMapper.get(entityA);
		if(health==null)
			health = healthMapper.get(entityB);

		
		ContactDamageComponent damage = damageMapper.get(entityA);
		if(damage==null)
			damage = damageMapper.get(entityB);

		
		if(health==null || damage==null) return;
		
		health.damageTaken += damage.damagePoints;
		
		System.out.println(health.damageTaken);
		
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
