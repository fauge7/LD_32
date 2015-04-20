package com.fauge.games.zombie.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Entity {

	public float posX;
	public float posY;
	public Texture tex;
	public Sprite spr;
	public Body body;
	public float HP;
	public float damage;
	public boolean Alive = true;
	public Entity(float X, float Y, float HP, float damage) {
		// TODO Auto-generated constructor stub
		this.posX = X;
		this.posY = Y;
		this.damage = damage;
		this.HP = HP;
	}
	public void dispose(){
		tex.dispose();
	}
	public void initBox2D(World world){
		
	}
	public void update() {
		// TODO Auto-generated method stub
		posX = body.getPosition().x;
		posY = body.getPosition().y;
		if(HP <=0){
			Alive = false;
			Player.score+=10;
		}
	}
	public void hit(float damageToDO) {
		// TODO Auto-generated method stub
		HP -= damageToDO;
	}
}
