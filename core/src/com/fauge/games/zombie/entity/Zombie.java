package com.fauge.games.zombie.entity;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import box2dLight.Spinor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.collision.Ray;
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
	PointLight eye1;
	PointLight eye2;
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
		eye1 = new PointLight(rayHandler, 64, Color.RED, 20, X, Y);
		eye1.setSoft(false);
//		eye2 = new PointLight(rayHandler, 32, Color.RED, 6, X+2, Y);
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
			direction = direction.STILL;
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
		eye1.setPosition(posX * GameScreen.PIXELS_IN_A_METER, posY * GameScreen.PIXELS_IN_A_METER + 8);
		eye1.setDistance(10f);
		eye1.update();
//		eye2.setPosition(posX * GameScreen.PIXELS_IN_A_METER, posY * GameScreen.PIXELS_IN_A_METER);
//		eye2.update();
	}
}
