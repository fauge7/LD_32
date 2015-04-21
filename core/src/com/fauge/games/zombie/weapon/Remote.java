package com.fauge.games.zombie.weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.fauge.games.zombie.screen.GameScreen;

public class Remote extends ThrowableWeapon{

	public Remote(float X, float Y) {
		super(X / GameScreen.PIXELS_IN_A_METER, Y / GameScreen.PIXELS_IN_A_METER,25);
		// TODO Auto-generated constructor stub
		posX = X;
		posY = Y;
		tex = new Texture("remote.png");
		spr = new Sprite(tex);
	}
	public void initBox2D(World world){
		BodyDef bdef = new BodyDef();
		bdef.position.set(posX, posY);
		bdef.type = BodyType.DynamicBody;
		body = world.createBody(bdef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(3.5f/GameScreen.PIXELS_IN_A_METER, 8/GameScreen.PIXELS_IN_A_METER);
		body.createFixture(shape, 2);
		body.getFixtureList().get(0).setUserData(this);
		shape.dispose();
	}
}
