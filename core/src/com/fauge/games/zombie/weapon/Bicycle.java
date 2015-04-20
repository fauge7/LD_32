package com.fauge.games.zombie.weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.fauge.games.zombie.screen.GameScreen;

public class Bicycle extends MeleeWeapon{

	public Bicycle(float X, float Y, float HP, float damage) {
		super(X, Y, HP, damage);
		// TODO Auto-generated constructor stub
		tex = new Texture("bike.png");
		spr = new Sprite(tex);
	}
	public void initBox2D(World world){
		BodyDef bdef = new BodyDef();
		bdef.position.set(posX, posY);
		bdef.type = BodyType.KinematicBody;
		body = world.createBody(bdef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(32f/GameScreen.PIXELS_IN_A_METER, 16/GameScreen.PIXELS_IN_A_METER);
		body.createFixture(shape, 3);
		body.getFixtureList().get(0).setUserData(this);
		shape.dispose();
	}
}
