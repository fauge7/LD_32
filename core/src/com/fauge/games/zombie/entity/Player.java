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
import com.badlogic.gdx.utils.Array;
import com.fauge.games.zombie.screen.GameScreen;


public class Player extends Entity{

	public static WeaponState weaponState;
	public static int weaponSelected = 0;
	public static Direction directionState;
	public static boolean canJump;
	public static float jumpTime = 0;
	public static boolean canAttack;
	public static float attackTime = 0;
	public static int score = 0;
	private final float SPEED = 4f;
	private final float ACCELERATION = 8f;
	private PointLight light;
	public static Array<Integer> WeaponArray;
	public Player(float X, float Y,RayHandler rayHandler) {
		super(X/GameScreen.PIXELS_IN_A_METER, Y/GameScreen.PIXELS_IN_A_METER, 100, 0);
		// TODO Auto-generated constructor stub
		tex = new Texture("player.png");
		spr = new Sprite(tex);
		weaponState = WeaponState.NONE;
		directionState = Direction.STILL;
		light = new PointLight(rayHandler, 32, Color.WHITE, 10, X * GameScreen.PIXELS_IN_A_METER, Y * GameScreen.PIXELS_IN_A_METER);
		ID = 0;
		WeaponArray = new Array<Integer>();
	}
	public void update(){
		super.update();
		jumpTime+=Gdx.graphics.getDeltaTime();
		if(attackTime > 1){
			canAttack = true;
		}
		if(jumpTime > 2)
			canJump = true;
		if(Gdx.input.isKeyPressed(Keys.A) || GameScreen.Left.contains(GameScreen.getTouch()) || GameScreen.Left.contains(GameScreen.getTouch(1))){
			body.applyForceToCenter(-ACCELERATION, 0, true);
			if(body.getLinearVelocity().x < -SPEED)
				body.setLinearVelocity(-SPEED, body.getLinearVelocity().y);
			directionState = Direction.LEFT;
		}
		else if(Gdx.input.isKeyPressed(Keys.D)|| GameScreen.Right.contains(GameScreen.getTouch()) || GameScreen.Right.contains(GameScreen.getTouch(1))){
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
		if(Gdx.input.isKeyPressed(Keys.W) && canJump || GameScreen.Jump.contains(GameScreen.getTouch()) && canJump || GameScreen.Jump.contains(GameScreen.getTouch(1)) && canJump){
			body.setLinearVelocity(body.getLinearVelocity().x, 5f);
			canJump = false;
			jumpTime = 0;
		}
		else{
			
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
		body.setFixedRotation(true);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(4/GameScreen.PIXELS_IN_A_METER, 16/GameScreen.PIXELS_IN_A_METER);
		body.createFixture(shape, 20);
		body.getFixtureList().get(0).setUserData(this);
		shape.dispose();
	}
	public static int getSelected(){
		return weaponSelected;
	}
	public static void setSelected(int weaponID){
		if(weaponID == 1)
			weaponState = WeaponState.TEDDYBEAR;
		else if(weaponID == 2)
			weaponState = WeaponState.REMOTE;
		else if(weaponID == 3)
			weaponState = WeaponState.BICYCLE;
		else if(weaponID == 4)
			weaponState = WeaponState.CONE;
		else if(weaponID == 5)
			weaponState = WeaponState.TV;
	}
	public float getX() {
		// TODO Auto-generated method stub
		return posX;
	}
	public float getY(){
		return posY;
	}
	public static void nextWeapon(){
		weaponSelected++;
		if(weaponSelected > 2)
			weaponSelected = 1;
		setSelected(weaponSelected);
	}
	public static void previousWeapon(){
		weaponSelected--;
		if(weaponSelected < 1)
			weaponSelected = 2;
		setSelected(weaponSelected);
	}
}