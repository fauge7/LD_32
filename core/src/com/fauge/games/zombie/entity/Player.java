package com.fauge.games.zombie.entity;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.fauge.games.zombie.screen.GameScreen;


public class Player extends Entity{

	public static WeaponState weaponState;
	public static Direction directionState;
	public static boolean canJump;
	public static float jumpTime = 0;
	public static boolean canAttack;
	public static float attackTime = 0;
	public static int score = 0;
	private final float SPEED = 8f;
	private final float ACCELERATION = 8f;
	private PointLight light;
	public Player(float X, float Y,RayHandler rayHandler) {
		super(X/GameScreen.PIXELS_IN_A_METER, Y/GameScreen.PIXELS_IN_A_METER, 1000, 0);
		// TODO Auto-generated constructor stub
		tex = new Texture("player.png");
		spr = new Sprite(tex);
		weaponState = WeaponState.NONE;
		directionState = Direction.STILL;
		light = new PointLight(rayHandler, 32, Color.WHITE, 10, X * GameScreen.PIXELS_IN_A_METER, Y * GameScreen.PIXELS_IN_A_METER);
	}
	public void update(){
		super.update();
		jumpTime+=Gdx.graphics.getDeltaTime();
		if(attackTime > 1){
			canAttack = true;
		}
		if(jumpTime > 2)
			canJump = true;
		if(Gdx.input.isKeyPressed(Keys.A)){
			body.applyForceToCenter(-ACCELERATION, 0, true);
			if(body.getLinearVelocity().x < -SPEED)
				body.setLinearVelocity(-SPEED, body.getLinearVelocity().y);
			directionState = Direction.LEFT;
		}
		else if(Gdx.input.isKeyPressed(Keys.D)){
			body.applyForceToCenter(ACCELERATION, 0, true);
			if(body.getLinearVelocity().x > SPEED)
				body.setLinearVelocity(SPEED, body.getLinearVelocity().y);
			directionState = Direction.RIGHT;
		}
		else{
			if(body.getLinearVelocity().x < -SPEED)
				body.setLinearVelocity(-SPEED, body.getLinearVelocity().y);
			if(body.getLinearVelocity().x > SPEED)
				body.setLinearVelocity(SPEED, body.getLinearVelocity().y);
			directionState = Direction.STILL;
		}
		if(Gdx.input.isKeyJustPressed(Keys.W) && canJump){
			body.setLinearVelocity(body.getLinearVelocity().x, 5);
			canJump = false;
			jumpTime = 0;
		}
		posX = body.getPosition().x;
		posY = body.getPosition().y;
		light.setPosition(posX * GameScreen.PIXELS_IN_A_METER, posY * GameScreen.PIXELS_IN_A_METER);
		light.update();
		
	}
	public void initBox2D(World world){
		BodyDef bdef = new BodyDef();
		bdef.position.set(posX, posY);
		bdef.type = BodyType.DynamicBody;
		body = world.createBody(bdef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(4/GameScreen.PIXELS_IN_A_METER, 16/GameScreen.PIXELS_IN_A_METER);
		body.createFixture(shape, 20);
		body.getFixtureList().get(0).setUserData(this);
		shape.dispose();
	}
	public static int getSelected(){
		switch (weaponState) {
		case REMOTE:
			return 1;
		case TEDDYBEAR:
			return 2;
		case BICYCLE:
			return 3;
		case CONE:
			return 4;
		case TV:
			return 5;
		default:
			return 0;
		}
	}
	public float getX() {
		// TODO Auto-generated method stub
		return posX;
	}
	public float getY(){
		return posY;
	}
}