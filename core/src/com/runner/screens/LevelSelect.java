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
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.runner.utils.Constants;

/**
 * Created by bob on 22.11.16.
 */

public class LevelSelect extends AbstractScreen {

    final Game game;
    protected Stage stage;

    private Viewport viewport;
    private TextureAtlas atlas;
    protected Skin skin;

    OrthographicCamera camera;
    float time = 0;

    //private Table tableLevel1;
    private TextButton playButton;
    private Label levelLabel;
    private String levelName;

    public LevelSelect(Game game){
        super(game);
        this.game = game;

        atlas = new TextureAtlas(Constants.UI_ATLAS_PATH);
        skin = new Skin(Gdx.files.internal(Constants.UI_SKIN_PATH), atlas);

        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.APP_WIDTH, Constants.APP_HEIGTH, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show(){
        //Add table to stage
        stage.addActor(createLevelTable());
    }

    private Table createLevelTable() {
        /**Create the maintable*/
        Table mainTable = new Table();
        mainTable.setFillParent(true);

        //Create buttons
        playButton = new TextButton("Play", skin);

        /**Create a label showing the selected level*/
        levelLabel = new Label("Select a level", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                /**Create the level image*/
                Image levelImage = new Image();
                levelImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(Constants.LEVEL_SELECT_LEVEL_IMAGES_PATHS[3 * i + j])))));
                Table tableLevel1 = new Table();
                tableLevel1.add(levelImage);
                tableLevel1.row();
                tableLevel1.add(new Label(Constants.LEVEL_SELECT_LEVEL_NAMES[3 * i + j], new Label.LabelStyle(new BitmapFont(), Color.WHITE)));
                tableLevel1.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        playButton.setTouchable(Touchable.enabled);
                        /**Get the name of the clicked level*/
                        levelLabel.setText((event.getTarget().getParent().getChildren().get(1).toString().substring(7)));
                    }
                });
                mainTable.add(tableLevel1).pad(Constants.LEVEL_SELECT_PADDING_TOP, Constants.LEVEL_SELECT_PADDING_SIDE, Constants.LEVEL_SELECT_PADDING_BOT, Constants.LEVEL_SELECT_PADDING_SIDE);
            }
            mainTable.row();
        }

        //Add listeners to buttons
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new IntroScreen(game, levelLabel.getText().toString()));
            }
        });
        playButton.setTouchable(Touchable.disabled);
        System.out.print(playButton.isDisabled());
        //Add buttons to table
        mainTable.add(playButton).pad(Constants.LEVEL_SELECT_PADDING_TOP, Constants.LEVEL_SELECT_PADDING_SIDE, Constants.LEVEL_SELECT_PADDING_BOT, Constants.LEVEL_SELECT_PADDING_SIDE);;
        mainTable.add(levelLabel);

        return mainTable;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        camera.update();

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
        skin.dispose();
        atlas.dispose();
    }
}
