package com.runner.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.runner.utils.Constants;

import java.util.ArrayList;

/**
 * Created by bob on 22.11.16.
 */

public class FinishScreen extends AbstractScreen {

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

    Label nameLabel;
    Label timeLabel;

    ArrayList<ArrayList<String>> finishList = new ArrayList<ArrayList<String>>();

    public FinishScreen(Game game, ArrayList finishList){
        super(game);
        this.game = game;
        this.finishList = finishList;

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
//Create Table
        Table mainTable = new Table();
        //Set table to fill stage
        //mainTable.setFillParent(true);
        mainTable.setPosition(camera.viewportWidth / 2, camera.viewportHeight * 2 / 3);
        //Set alignment of contents in the table.
        mainTable.top();

        //Create finish actor name and time labels
        for(int i = 0, j = finishList.size(); i < j; i++){
            nameLabel = new Label(finishList.get(i).get(0), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            timeLabel = new Label(finishList.get(i).get(1), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        }

        //Create buttons
        TextButton replayButton = new TextButton("Play again", skin);
        TextButton menuButton = new TextButton("Main menu", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        //Add listeners to buttons
        replayButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelect(game));
            }
        });
        menuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new OptionsScreen(game));
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //Add labels to table
        mainTable.add(nameLabel).pad(20);
        mainTable.add(timeLabel).pad(20);
        //Add buttons to table
        mainTable.row();
        mainTable.add(replayButton).pad(20);
        mainTable.add(menuButton).pad(20);
        mainTable.add(exitButton).pad(20);

        //Add table to stage
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        //batch.draw(title, 0, 0);
        //font.draw(batch, "Welcome to Its a me!", Constants.APP_WIDTH / 2, Constants.APP_HEIGTH * 2/ 3);
        //font.draw(batch, "Tap anywhere to begin!!", Constants.APP_WIDTH / 2, Constants.APP_HEIGTH / 2);
        batch.end();

        /*
        time += delta;
        if(time > 1){
            if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()){
                game.setScreen(new IntroScreen(game));
            }
        }
        */
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public void hide(){
        Gdx.app.debug("Cubify", "dispose mainmenu");
        batch.dispose();
        title.getTexture().dispose();
        font.dispose();
        skin.dispose();
        atlas.dispose();
    }
}
