package com.runner.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.runner.Runner;
import com.runner.utils.Constants;

/**
 * Created by bob on 22.11.16.
 */

public class MainMenu extends AbstractScreen {

    final Game game;
    protected Stage stage;

    private Viewport viewport;
    private TextureAtlas atlas;
    protected Skin skin;

    TextureRegion title;
    SpriteBatch batch;
    BitmapFont font;
    OrthographicCamera camera;
    float time = 0;

    public MainMenu(Game game){
        super(game);
        this.game = game;

        atlas = new TextureAtlas(Constants.UI_ATLAS_PATH);
        skin = new Skin(Gdx.files.internal(Constants.UI_SKIN_PATH), atlas);

        title = new TextureRegion(new Texture(Gdx.files.internal(Constants.MAINMENU_IMAGE_PATH)));
        batch = new SpriteBatch();
        //batch.getProjectionMatrix().setToOrtho2D(0, 0, title.getRegionWidth(), title.getRegionHeight());

        font = new BitmapFont();

        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.APP_WIDTH, Constants.APP_HEIGTH, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);
        //camera.setToOrtho(false, Constants.APP_WIDTH, Constants.APP_HEIGTH);

        Gdx.input.setInputProcessor(stage);
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
        batch.draw(title, 0, 0);
        font.draw(batch, "Welcome to Its a me!", Constants.APP_WIDTH / 2, Constants.APP_HEIGTH * 2/ 3);
        font.draw(batch, "Tap anywhere to begin!!", Constants.APP_WIDTH / 2, Constants.APP_HEIGTH / 2);
        batch.end();

        time += delta;
        if(time > 1){
            if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()){
                game.setScreen(new IntroScreen(game));
            }
        }
    }

    @Override
    public void hide(){
        Gdx.app.debug("Cubify", "dispose mainmenu");
        batch.dispose();
        title.getTexture().dispose();
        font.dispose();
    }
}
