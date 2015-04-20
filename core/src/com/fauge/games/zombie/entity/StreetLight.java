package com.fauge.games.zombie.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.fauge.games.zombie.screen.GameScreen;

public class StreetLight extends Entity {

	public StreetLight(float X, float Y, boolean Right) {
		super(X/GameScreen.PIXELS_IN_A_METER, Y/GameScreen.PIXELS_IN_A_METER, 500000, 0);
		// TODO Auto-generated constructor stub
		if(Right)
			tex = new Texture("lamp_R.png");
		else
			tex = new Texture("lamp_L.png");
		spr = new Sprite(tex);
	}
	public void initBox2D(World world){
		BodyDef bdef = new BodyDef();
		bdef.position.set(posX, posY);
		bdef.type = BodyType.StaticBody;
		body = world.createBody(bdef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(8/GameScreen.PIXELS_IN_A_METER, 64/GameScreen.PIXELS_IN_A_METER);
		FixtureDef fixdef = new FixtureDef();
		fixdef.shape = shape;
		fixdef.isSensor = true;
		body.createFixture(fixdef);
		body.getFixtureList().get(0).setUserData(this);
		shape.dispose();
	}
	public Body getBody(){
		return body;
	}

}
