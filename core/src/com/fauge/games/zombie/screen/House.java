package com.fauge.games.zombie.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.fauge.games.zombie.entity.Player;


public class House {

	static Texture outsideTex;
	static Texture insideTex;
	static Texture[] weapons;
	Sprite spr;
	Sprite weaponspr;
	int weaponID;
	boolean inside = false;
	float posX;
	float posY;
	Rectangle bounds;
	public House(float X, float Y) {
		// TODO Auto-generated constructor stub
		weaponID = MathUtils.random(0, 4);
		outsideTex = new Texture("house.png");
		insideTex = new Texture("inside.png");
		if(weapons == null){
			weapons = new Texture[5];
			weapons[0] = new Texture("remote.png");
			weapons[1] = new Texture("teddy bear.png");
			weapons[2] = new Texture("bike.png");
			weapons[3] = new Texture("Cone.png");
			weapons[4] = new Texture("tv.png");
		}
		spr = new Sprite(outsideTex);
		spr.setPosition(X, Y);
		spr.setScale(1f, .7f);
		this.posX = X;
		this.posY = Y;
		bounds = spr.getBoundingRectangle();
		bounds.setHeight(spr.getBoundingRectangle().height+40);
		bounds.setY(bounds.getY()-40);
		weaponspr = new Sprite(weapons[weaponID]);
		weaponspr.setScale(3*.75f,3*.9f);
	}
	
	public int getWeapon(){
		return weaponID;
	}
	public void render(SpriteBatch batch){
		if(inside){
			batch.draw(insideTex, GameScreen.camX-720/2, 0);
			weaponspr.setPosition(GameScreen.camX-weaponspr.getWidth()/2, 220-weaponspr.getHeight()/2);
			weaponspr.draw(batch);
			if(!Player.WeaponArray.contains(weaponID, true))
				Player.WeaponArray.add(weaponID);
		}
		else{
			spr.draw(batch);
		}
	}
}
