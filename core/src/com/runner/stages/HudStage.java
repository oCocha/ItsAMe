package com.runner.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.runner.hud.MyButton;
import com.runner.screens.GameScreen;
import com.runner.utils.Constants;
import com.sun.org.apache.bcel.internal.classfile.ConstantNameAndType;

/**
 * Created by bob on 02.12.16.
 */

public class HudStage extends Stage {
    private static final int VIEWPORT_WIDTH = Constants.APP_WIDTH /*/ (int)Constants.WORLD_TO_SCREEN*/;
    private static final int VIEWPORT_HEIGHT = Constants.APP_HEIGTH /*/ (int)Constants.WORLD_TO_SCREEN*/;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private FitViewport viewport;

    private Skin touchpadSkin;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Drawable touchBackground;
    private Drawable touchKnob;
    private Touchpad touchpad;

    private Circle shootButton;
    private Circle switchModeButton;

    private Label levelLabel;
    private Label playerLabel;
    private Label itsAMeLabel;
    private Label shootLabel;
    private Label scoreLabel;

    private MyButton bombButton;

    private ShapeRenderer shapeRenderer;
    private Vector3 touchPoint;

    private Texture switchModeBackground;

    public HudStage(){
        shapeRenderer = new ShapeRenderer();
        touchPoint = new Vector3();
        setupTable();
        setupUI();
    }

    //Setup a table containing some labels which display game data
    private void setupTable() {
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        levelLabel = new Label("Testlevel 1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        playerLabel = new Label("Player", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        itsAMeLabel = new Label("Its a Me!", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        shootLabel = new Label("BULLET", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label("Score: 0", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(itsAMeLabel).expandX().padTop(10);
        table.add(scoreLabel).expandX();
        table.row();
        table.add(levelLabel).expandX();
        table.add(shootLabel).expandX();

        addActor(table);
    }

    private void setupUI() {
        setupTouchpad();
        setupButtons();
        Gdx.input.setInputProcessor(this);
    }

    private void setupButtons() {
        shootButton = new Circle(getViewport().getScreenWidth() * 9 / 10, getViewport().getScreenHeight() / 2, Constants.UI_BUTTON_SHOOT_RADIUS);
        switchModeButton = new Circle(getViewport().getScreenWidth() * 9 / 10, getViewport().getScreenHeight() * 5 / 6, Constants.UI_BUTTON_SWITCH_RADIUS / 2);
        switchModeBackground = new Texture("ui/buttons/shootButton.png");
        /**Setup the bomb button*//*
        Texture textureUp   = new Texture(Gdx.files.internal("ui/buttons/shootButton.png"));
        Texture textureDown = new Texture(Gdx.files.internal("ui/buttons/shootButton.png"));
        Texture background  = new Texture(Gdx.files.internal("ui/buttons/shootButton.png"));
        bombButton   = new MyButton(textureUp, textureDown, background);
        bombButton.setPosition(getViewport().getScreenWidth() * 9 / 10, getViewport().getScreenHeight() / 2);
        bombButton.setBounds(getViewport().getScreenWidth() * 9 / 10, getViewport().getScreenHeight() / 2, 100, 100);
        bombButton.addListener(new InputListener() {
                                   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                       Gdx.app.log("my app", "Pressed");
                                       toggleShootMode();
                                       return true;
                                   }
                               });
        addActor(bombButton);*/

    }

    private void setupTouchpad() {
        //Setup the touchpad joystick to move the player
        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture(Constants.UI_TOUCHPAD_BACKGROUND_PATH));
        touchpadSkin.add("touchKnob", new Texture(Constants.UI_TOUCHPAD_KNOB_PATH));
        touchpadStyle = new Touchpad.TouchpadStyle();
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        touchpad = new Touchpad(Constants.UI_TOUCHPAD_INTENSITY / Constants.WORLD_TO_SCREEN, touchpadStyle);
        touchpad.setBounds(getViewport().getScreenWidth() / 8 - Constants.UI_TOUCHPAD_WIDTH / 2, getViewport().getScreenHeight() / 2 - Constants.UI_TOUCHPAD_WIDTH / 2, Constants.UI_TOUCHPAD_WIDTH, Constants.UI_TOUCHPAD_HEIGHT);
        addActor(touchpad);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (touchpad.isTouched())
            GameScreen.gameStage.runner.move(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());

        renderButtons();

        //TODO: Implement interpolation
    }

    private void renderButtons() {

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        /**Buttons mit Shaperenderer*/
        shapeRenderer.setProjectionMatrix(getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0, 1, 0, 0.2f));
        /**Draw the shootButton*/
        shapeRenderer.circle(getViewport().getScreenWidth() * 9 / 10, getViewport().getScreenHeight() / 2, Constants.UI_BUTTON_SHOOT_RADIUS);
        //shapeRenderer.setColor(new Color(1, 0, 0, 0.2f));
        /**Draw the switchModeButton*/
        //shapeRenderer.circle(getViewport().getScreenWidth() * 9 / 10, getViewport().getScreenHeight() * 5/ 6, Constants.UI_BUTTON_SHOOT_RADIUS / 2);
        shapeRenderer.end();

        /**Buttons mit Spritebatch*/
        getBatch().begin();
        getBatch().draw(switchModeBackground, getViewport().getScreenWidth() * 9 / 10 - Constants.UI_BUTTON_SWITCH_RADIUS / 2, getViewport().getScreenHeight() * 5/ 6 - Constants.UI_BUTTON_SWITCH_RADIUS / 2, Constants.UI_BUTTON_SWITCH_RADIUS, Constants.UI_BUTTON_SWITCH_RADIUS);
        getBatch().end();

        draw();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button){
        _translateScreenToWorldCoordinates(x, y);

        if(_shootButtonTouched(touchPoint.x, touchPoint.y)){
            GameScreen.gameStage.shoot();
        }
        if(_switchModeButtonTouched(touchPoint.x, touchPoint.y)){
            toggleShootMode();
        }
        return super.touchDown(x, y, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button){
        System.out.print("TOUCHUP");
        if(GameScreen.gameStage.runner.isDodging()){
            GameScreen.gameStage.runner.stopDodge();
        }
        return super.touchUp(screenX, screenY, pointer, button);
    }

    private void toggleShootMode(){
        if(GameScreen.gameStage.runner.getShootMode() == 1){
            GameScreen.gameStage.runner.setShootMode(0);
            //bombButton.setBackground(new Texture(Gdx.files.internal("ui/buttons/shootButton.png")));
            //bombButton.(new Texture(Gdx.files.internal("ui/buttons/shootButton.png")));
            switchModeBackground = new Texture("ui/buttons/shootButton.png");
            shootLabel.setText("BULLET");
        }
        else{
            GameScreen.gameStage.runner.setShootMode(1);
            //bombButton.setBackground(new Texture(Gdx.files.internal("ui/buttons/bombButton.png")));
            //bombButton.setTextureUp(new Texture(Gdx.files.internal("ui/buttons/bombButton.png")));
            switchModeBackground = new Texture("ui/buttons/bombButton.png");
            shootLabel.setText("BOMB");
        }
    }

    private boolean _shootButtonTouched(float x, float y){
        return shootButton.contains(x, y);
    }

    private boolean _switchModeButtonTouched(float x, float y){
        return switchModeButton.contains(x, y);
    }

    private void _translateScreenToWorldCoordinates(int x, int y){
        getCamera().unproject(touchPoint.set(x, y, 0));
    }

    public void setScore(int score){
        scoreLabel.setText("SCORE: " + score);
    }

    @Override
    public void dispose(){
        touchpadSkin.dispose();
        switchModeBackground.dispose();
    }
}
