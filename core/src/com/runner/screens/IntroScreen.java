package com.runner.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.runner.utils.Constants;

/**
 * Created by bob on 22.11.16.
 */

public class IntroScreen extends AbstractScreen{

    private OrthographicCamera camera;
    private Viewport viewport;

    TextureRegion intro;
    SpriteBatch batch;
    float time = 0;

    public IntroScreen(Game game){
        super(game);

        intro = new TextureRegion(new Texture(Gdx.files.internal(Constants.INTRO_IMAGE_PATH)));
        batch = new SpriteBatch();
        //batch.getProjectionMatrix().setToOrtho2D(0, 0, title.getRegionWidth(), title.getRegionHeight());

        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.APP_WIDTH, Constants.APP_HEIGTH, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public void show(){
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

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
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public void hide(){
        Gdx.app.debug("Cubify", "dispose intro");
        batch.dispose();
        intro.getTexture().dispose();
    }
}
