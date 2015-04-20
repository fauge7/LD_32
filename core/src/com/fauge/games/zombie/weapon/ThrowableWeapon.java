package com.fauge.games.zombie.weapon;

import com.fauge.games.zombie.entity.Entity;

public class ThrowableWeapon extends Entity{

	public ThrowableWeapon(float X, float Y, float damage) {
		super(X, Y, 1, damage);
		// TODO Auto-generated constructor stub
	}
	public void update(){
		posX = body.getPosition().x;
		posY = body.getPosition().y;
	}

}
