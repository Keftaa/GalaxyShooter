package com.galaxyshooter.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Fonts {
	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter parameter;
	public BitmapFont font24, font32, font64, font128;
	
	public Fonts(){
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Sansation-Regular.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.borderColor = Color.DARK_GRAY;
		parameter.borderWidth = 2;
		parameter.size = 24;
		font24 = generator.generateFont(parameter);
		parameter.size = 32;
		font32 = generator.generateFont(parameter);
		parameter.size = 64;
		font64 = generator.generateFont(parameter);
		parameter.size = 128;
		font128 = generator.generateFont(parameter);
		generator.dispose();
	}
	
	
}
