package com.fauge.games.zombie.weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.fauge.games.zombie.screen.GameScreen;

public class TeddyBear extends ThrowableWeapon {

	public TeddyBear(float X, float Y) {
		super(X / GameScreen.PIXELS_IN_A_METER, Y / GameScreen.PIXELS_IN_A_METER,30);
		// TODO Auto-generated constructor stub
		posX = X;
		posY = Y;
		tex = new Texture("teddy bear.png");
		spr = new Sprite(tex);
	}

	public void initBox2D(World world){
		BodyDef bdef = new BodyDef();
		bdef.position.set(posX, posY);
		bdef.type = BodyType.DynamicBody;
		body = world.createBody(bdef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(8f/GameScreen.PIXELS_IN_A_METER, 8/GameScreen.PIXELS_IN_A_METER);
		body.createFixture(shape, 1);
		body.getFixtureList().get(0).setUserData(this);
		shape.dispose();
	}
	public Body getBody(){
		return body;
	}
}
