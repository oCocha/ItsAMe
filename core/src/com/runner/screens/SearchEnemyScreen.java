package com.runner.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.runner.multiplayer.WarpController;
import com.runner.multiplayer.WarpListener;
import com.runner.utils.Constants;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;

/**
 * Created by bob on 22.11.16.
 */

public class SearchEnemyScreen extends AbstractScreen implements WarpListener{

    private OrthographicCamera camera;
    private Viewport viewport;

    private TextureAtlas atlas;
    protected Skin skin;
    protected Stage stage;

    TextureRegion intro;
    //SpriteBatch batch;
    float time = 0;
    private String levelName;

    private TextButton searchButton;
    private Label statusLabel;

    public SearchEnemyScreen(Game game, String levelName){
        super(game);

        this.levelName = levelName;

        atlas = new TextureAtlas(Constants.UI_ATLAS_PATH);
        skin = new Skin(Gdx.files.internal(Constants.UI_SKIN_PATH), atlas);

        //intro = new TextureRegion(new Texture(Gdx.files.internal(Constants.INTRO_IMAGE_PATH)));
        //batch = new SpriteBatch();
        //batch.getProjectionMatrix().setToOrtho2D(0, 0, title.getRegionWidth(), title.getRegionHeight());

        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.APP_WIDTH, Constants.APP_HEIGTH, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        WarpController.getInstance().setListener(this);
    }

    @Override
    public void show(){
        stage.addActor(createLevelTable());
    }

    private Table createLevelTable() {
        /**Create the maintable*/
        Table mainTable = new Table();
        mainTable.setFillParent(true);

        //Create buttons
        searchButton = new TextButton("Search for opponent", skin);

        /**Create a label showing the selected level*/
        statusLabel = new Label("Status", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //Add listeners to buttons
        searchButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                /**OHNE MULTIPLAYER
                 game.setScreen(new IntroScreen(game, levelLabel.getText().toString()));
                 */
                System.out.print("CLICK");
                WarpController.getInstance().startApp(Constants.TEST_USER_NAME);
            }
        });
        //Add buttons to table
        mainTable.add(searchButton).pad(Constants.LEVEL_SELECT_PADDING_TOP, Constants.LEVEL_SELECT_PADDING_SIDE, Constants.LEVEL_SELECT_PADDING_BOT, Constants.LEVEL_SELECT_PADDING_SIDE);;
        mainTable.add(statusLabel);

        return mainTable;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        camera.update();
        /*
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(intro, 0, 0);
        batch.end();
        */

        time += delta;
        /**1 Sekunde warten
        if(time > 1){
        }*/
        /**Spiel starten
        game.setScreen(new GameScreen(game, levelName));
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
        Gdx.app.debug("Cubify", "dispose intro");
        //batch.dispose();
        //intro.getTexture().dispose();
    }

    @Override
    public void onWaitingStarted(String message) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onGameStarted(String message) {

    }

    @Override
    public void onGameFinished(int code, boolean isRemote) {

    }

    @Override
    public void onGameUpdateReceived(String message) {

    }
}
