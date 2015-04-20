package com.fauge.games.zombie.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
	Texture tex;
	float x;
	float y;
	boolean moving;
	public Background() {
		// TODO Auto-generated constructor stub
		tex = new Texture("background.png");
	}
	public void draw(SpriteBatch batch){
		batch.draw(tex, x, y);
	}

}
