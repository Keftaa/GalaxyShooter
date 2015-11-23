package com.galaxyshooter.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SkinManager {
	
	public Skin skin;
	private TextureAtlas uiAtlas;
	private Fonts fonts;
	
	public SkinManager(){
		uiAtlas = new TextureAtlas(Gdx.files.internal("ui.pack"));
		fonts = new Fonts();
		
		skin = new Skin();

		skin.addRegions(uiAtlas);
		skin.add("fontsmall", fonts.font24);
		skin.add("fontmedium", fonts.font32);
		skin.add("fontbig", fonts.font64);
		skin.add("fonthuge", fonts.font128);
		
		skin.load(Gdx.files.internal("json/skin.json"));
	}
	
}
