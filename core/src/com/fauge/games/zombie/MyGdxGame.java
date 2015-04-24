package com.fauge.games.zombie;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.fauge.games.zombie.screen.GameScreen;

public class MyGdxGame extends Game implements ApplicationListener {
	
	@Override
	public void create () {
		this.setScreen(new GameScreen());
	}

	@Override
	public void render () {
		super.render();
	}

}
