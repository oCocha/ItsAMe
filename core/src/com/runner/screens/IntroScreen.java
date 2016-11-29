package com.runner.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by bob on 22.11.16.
 */

public class IntroScreen extends AbstractScreen{

    TextureRegion intro;
    SpriteBatch batch;
    float time = 0;

    public IntroScreen(Game game){
        super(game);
    }

    @Override
    public void show(){
        intro = new TextureRegion(new Texture(Gdx.files.internal("intro.png")), 0, 0, 480,320);
        batch = new SpriteBatch();
        batch.getProjectionMatrix().setToOrtho2D(0, 0, intro.getRegionWidth(), intro.getRegionHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(intro, 0, 0);
        batch.end();

        time += delta;
        if(time > 1){
            if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()){
                game.setScreen(new GameScreen(game));
            }
        }
    }

    @Override
    public void hide(){
        Gdx.app.debug("Cubify", "dispose intro");
        batch.dispose();
        intro.getTexture().dispose();
    }
}
