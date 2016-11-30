package com.runner.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.runner.utils.Constants;

/**
 * Created by bob on 22.11.16.
 */

public class LogoScreen extends AbstractScreen {

    TextureRegion title;
    SpriteBatch batch;
    float time = 0;

    public LogoScreen(Game game){
        super(game);
    }

    @Override
    public void show(){
        title = new TextureRegion(new Texture(Gdx.files.internal(Constants.LOGO_IMAGE_PATH)));
        batch = new SpriteBatch();
        batch.getProjectionMatrix().setToOrtho2D(0, 0, title.getRegionWidth(), title.getRegionHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(title, 0, 0);
        batch.end();

        time += delta;
        if(time > 1){
            if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()){
                game.setScreen(new MainMenu(game));
            }
        }
    }

    @Override
    public void hide(){
        Gdx.app.debug("Cubify", "dispose mainmenu");
        batch.dispose();
        title.getTexture().dispose();
    }
}
