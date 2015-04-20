package com.fauge.games.zombie;

import sun.applet.AppletEvent;
import sun.applet.AppletListener;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fauge.games.zombie.screen.GameScreen;

public class MyGdxGame extends Game implements AppletListener {
	
	@Override
	public void create () {
		this.setScreen(new GameScreen());
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void appletStateChanged(AppletEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
