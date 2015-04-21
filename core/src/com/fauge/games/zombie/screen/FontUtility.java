package com.fauge.games.zombie.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class FontUtility {
	private FreeTypeFontGenerator gen;
	private FreeTypeFontParameter genperam;
	private BitmapFont font;
	
	public FontUtility(){
		gen = new FreeTypeFontGenerator(Gdx.files.internal("Remains.ttf"));
		genperam = new FreeTypeFontParameter();
		genperam.size = 60;
		font = gen.generateFont(genperam);	
	}
	public FontUtility(int size){
		gen = new FreeTypeFontGenerator(Gdx.files.internal("Remains.ttf"));
		genperam = new FreeTypeFontParameter();
		genperam.size = size;
		font = gen.generateFont(genperam);
	}
	public BitmapFont getFont(){
		return font;
	}
	public BitmapFont getFont(Color c,int size){
		genperam.size = size;
		font = gen.generateFont(genperam);
		font.setColor(c);
		return font;
	}
	public void setFont(Color c,int size){
		genperam.size = size;
		font.dispose();
		font = gen.generateFont(genperam);
		font.setColor(c);
	}
	public void draw(SpriteBatch batch,String str,float x,float y){
		font.draw(batch, str, x, y);
	}
	public TextBounds getBounds(String str){
		return font.getBounds(str);
	}
	public void setColor(Color c){
		font.setColor(c);
	}
	public void setScale(float s){
		font.setScale(s);
	}
	

}
