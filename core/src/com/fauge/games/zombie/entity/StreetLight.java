package com.fauge.games.zombie.entity;

import box2dLight.ConeLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.fauge.games.zombie.screen.GameScreen;

public class StreetLight extends Entity {

	private float flicker;
	private float flickerTime = 0;
	private ConeLight light;
	public static final float FLICKER_MIN = 2f;
	public static final float FLICKER_MAX = 12f;
	public StreetLight(float X, float Y, boolean Right,RayHandler rayHandler) {
		super(X/GameScreen.PIXELS_IN_A_METER, Y/GameScreen.PIXELS_IN_A_METER, 500000, 0);
		// TODO Auto-generated constructor stub
		if(Right)
			tex = new Texture("lamp_R.png");
		else
			tex = new Texture("lamp_L.png");
		spr = new Sprite(tex);
		flicker = MathUtils.random(FLICKER_MIN, FLICKER_MAX);
		light = new ConeLight(rayHandler, 256, Color.YELLOW, 210, X, 200, 270, 45);
		light.setXray(true);
		light.setStaticLight(true);
		ID = -1;
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
	public void update(){
		if(flickerTime > flicker){
			light.setDistance(100);
			flickerTime = -.15f;
			flicker = MathUtils.random(FLICKER_MIN,FLICKER_MAX);
		}
		if(flickerTime < 0){
			light.setDistance(0);
			flickerTime+=Gdx.graphics.getDeltaTime();
		}
		else{
			light.setDistance(200);
			flickerTime+=Gdx.graphics.getDeltaTime();
		}
	}
}
