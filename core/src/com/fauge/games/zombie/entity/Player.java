package com.fauge.games.zombie.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.fauge.games.zombie.screen.GameScreen;
import com.fauge.games.zombie.weapon.Remote;
import com.fauge.games.zombie.weapon.TeddyBear;
import com.fauge.games.zombie.weapon.ThrowableWeapon;


public class Player extends Entity{

	public static WeaponState weaponState;
	public static Direction directionState;
	public static boolean canJump;
	public static float jumpTime = 0;
	public static int score = 0;
	public Player(float X, float Y) {
		super(X/GameScreen.PIXELS_IN_A_METER, Y/GameScreen.PIXELS_IN_A_METER, 100, 0);
		// TODO Auto-generated constructor stub
		tex = new Texture("player.png");
		spr = new Sprite(tex);
		weaponState = WeaponState.NONE;
		directionState = Direction.STILL;
	}
	public void update(){
		super.update();
		System.out.println(Alive);
		jumpTime+=Gdx.graphics.getDeltaTime();
		if(jumpTime > 2)
			canJump = true;
		if(Gdx.input.isKeyPressed(Keys.A)){
			body.applyForceToCenter(-8f, 0, true);
			if(body.getLinearVelocity().x < -2f)
				body.setLinearVelocity(-2f, body.getLinearVelocity().y);
			directionState = Direction.LEFT;
		}
		else if(Gdx.input.isKeyPressed(Keys.D)){
			body.applyForceToCenter(8f, 0, true);
			if(body.getLinearVelocity().x > 2f)
				body.setLinearVelocity(2f, body.getLinearVelocity().y);
			directionState = Direction.RIGHT;
		}
		else{
			if(body.getLinearVelocity().x < -2f)
				body.setLinearVelocity(-2f, body.getLinearVelocity().y);
			if(body.getLinearVelocity().x > 2f)
				body.setLinearVelocity(2f, body.getLinearVelocity().y);
			directionState = Direction.STILL;
		}
		if(Gdx.input.isKeyJustPressed(Keys.W) && canJump){
			body.setLinearVelocity(body.getLinearVelocity().x, 5);
			canJump = false;
			jumpTime = 0;
		}
		posX = body.getPosition().x;
		posY = body.getPosition().y;
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