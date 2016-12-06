package com.runner;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.runner.screens.GameScreen;
import com.runner.screens.LevelSelect;
import com.runner.screens.LogoScreen;
import com.runner.screens.MainMenu;

public class Runner extends Game {
	
	@Override
	public void create () {
        setScreen(new LevelSelect(this));
	}
}
