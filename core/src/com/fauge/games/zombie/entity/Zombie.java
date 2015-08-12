package com.fauge.games.zombie.entity;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.fauge.games.zombie.screen.GameScreen;

public class Zombie extends Entity{

	static Array<TextureRegion> walkingLeft;
	static Array<TextureRegion> walkingRight;
	static Animation walkingRightAnimation;
	static Animation walkingLeftAnimation;
	Direction direction;
	float animTime = 0;
	private PointLight eye;
	public Zombie(float X, float Y,Player p, RayHandler rayHandler) {
		super(X/GameScreen.PIXELS_IN_A_METER, Y/GameScreen.PIXELS_IN_A_METER, 50, 5);
		// TODO Auto-generated constructor stub
		tex = new Texture("zombie_front.png");
		spr = new Sprite(tex);
		walkingLeft = new Array<TextureRegion>();
		walkingRight = new Array<TextureRegion>();
		for(int i = 1; i < 6;i++){
			walkingLeft.add(new TextureRegion(new Texture("ZL" + i + ".png")));
			walkingRight.add(new TextureRegion(new Texture("ZR" + i + ".png")));
		}
		walkingLeftAnimation= new Animation(.45f, walkingLeft);
		walkingLeftAnimation.setPlayMode(PlayMode.LOOP_PINGPONG);
		walkingRightAnimation= new Animation(.45f, walkingRight);
		walkingRightAnimation.setPlayMode(PlayMode.LOOP_PINGPONG);
		if(p.posX > X)
			direction = Direction.LEFT;
		else
			direction = Direction.RIGHT;
		eye = new PointLight(rayHandler, 64, Color.RED, 10, X, Y);
		eye.setSoft(false);
		ID = 2;
	}
	public void initBox2D(World world){
		BodyDef bdef = new BodyDef();
		bdef.position.set(posX, posY);
		bdef.type = BodyType.DynamicBody;
		body = world.createBody(bdef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(8/GameScreen.PIXELS_IN_A_METER, 16/GameScreen.PIXELS_IN_A_METER);
		body.createFixture(shape, 15);
		body.getFixtureList().get(0).setUserData(this);
		shape.dispose();
	}
	public void update(Player p){
		super.update();
		if(p.posX > posX){
			direction = Direction.LEFT;
		}
		else
			direction = Direction.RIGHT;
		if(!Alive)
			direction = Direction.STILL;
		animTime+=Gdx.graphics.getDeltaTime();
		spr.setPosition(posX, posY);
		if(direction == Direction.RIGHT){
			spr.setRegion(walkingLeftAnimation.getKeyFrame(animTime));
			body.applyForceToCenter(-8, 0, true);
			if(body.getLinearVelocity().x < -2.25f)
				body.setLinearVelocity(-2.25f, body.getLinearVelocity().y);
		}
		else if(direction == Direction.LEFT){
			spr.setRegion(walkingRightAnimation.getKeyFrame(animTime));
			body.applyForceToCenter(8, 0, true);	
			if(body.getLinearVelocity().x > 2.25f)
				body.setLinearVelocity(2.25f, body.getLinearVelocity().y);
		}
		else{
			spr.setRegion(tex);
			body.applyForceToCenter(0, 0, true);
			if(body.getLinearVelocity().x < -2.25f)
				body.setLinearVelocity(-2.25f, body.getLinearVelocity().y);
			if(body.getLinearVelocity().x > 2.25f)
				body.setLinearVelocity(2.25f, body.getLinearVelocity().y);
			direction = Direction.STILL;
			body.setUserData(this);
		}
		if(Math.abs(Math.abs(body.getAngle() * MathUtils.radiansToDegrees)-90) < 10f)
			body.setLinearVelocity(0, body.getLinearVelocity().y);
		eye.setPosition(posX * GameScreen.PIXELS_IN_A_METER, posY * GameScreen.PIXELS_IN_A_METER + 8);
		if(!Alive)
			eye.setDistance(0);
//		eye2.setPosition(posX * GameScreen.PIXELS_IN_A_METER, posY * GameScreen.PIXELS_IN_A_METER);
//		eye2.update();
	}
}
