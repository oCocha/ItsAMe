package com.runner.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.runner.actors.Background;
import com.runner.actors.Enemy;
import com.runner.actors.Ground;
import com.runner.actors.Runner;
import com.runner.box2d.GroundUserData;
import com.runner.screens.GameScreen;
import com.runner.utils.BodyUtils;
import com.runner.utils.Constants;
import com.runner.utils.WorldUtils;


/**
 * Created by bob on 20.11.16.
 */

public class GameStage extends Stage implements ContactListener {
    private static final int VIEWPORT_WIDTH = Constants.APP_WIDTH / 32;
    private static final int VIEWPORT_HEIGHT = Constants.APP_HEIGTH / 32;

    private World world;
    private Runner runner;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private OrthographicCamera camera;

    private Rectangle screenRightButton;
    private Rectangle screenLeftButton;
    private Rectangle screenTopButton;
    private Rectangle screenBotButton;

    private Vector3 touchPoint;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMapTileLayer collisionLayer;
    private float tileSize;
    private PolygonShape shape;

    private ShapeRenderer shapeRenderer;

    private Box2DDebugRenderer debugRenderer;

    public GameStage(){
        super(new ScalingViewport(Scaling.stretch, VIEWPORT_WIDTH, VIEWPORT_HEIGHT));
        setupCamera();
        setupWorld();
    }

    private void setupTouchControlAreas() {
        touchPoint = new Vector3();
        screenLeftButton = new Rectangle(runner.getPosition().x - getCamera().viewportWidth / 2, 0, getCamera().viewportWidth / 3, getCamera().viewportHeight);
        screenRightButton = new Rectangle(runner.getPosition().x + (getCamera().viewportWidth / 6), 0, getCamera().viewportWidth / 3, getCamera().viewportHeight);
        screenTopButton = new Rectangle(runner.getPosition().x - (getCamera().viewportWidth / 6), (getCamera().viewportHeight / 3) * 2, getCamera().viewportWidth / 3, getCamera().viewportHeight / 3);
        screenBotButton = new Rectangle(runner.getPosition().x - (getCamera().viewportWidth / 6), 0, getCamera().viewportWidth / 3, getCamera().viewportHeight / 3);
        Gdx.input.setInputProcessor(this);
    }

    private void setupWorld() {
        world = WorldUtils.createWorld();
        world.setContactListener(this);
        setupShapeRenderer();
        setupMap();
        setupBackGround();
        setUpRunner();
        //createEnemy();
    }

    private void setupShapeRenderer() {
        shapeRenderer = new ShapeRenderer();

        //DEBUG TEST
        debugRenderer = new Box2DDebugRenderer();
    }

    private void setupMap() {
        map = new TmxMapLoader().load("map/level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Constants.WORLD_TO_SCREEN);
        collisionLayer = (TiledMapTileLayer)map.getLayers().get("walls");
        _createBox2DObjects();
    }

    //Create a Box2D Object for every cell in the tilemap layer(collision)
    private void _createBox2DObjects() {
        tileSize = collisionLayer.getTileWidth();
        //Go through all the cells in the tilemap layer
        for(int row = 0, height = collisionLayer.getHeight(); row < height; row++){
            for(int col = 0, width = collisionLayer.getWidth(); col < width; col++){
                //Get the current cell
                Cell cell = collisionLayer.getCell(col, row);

                //CHeck if the cell exists
                if(cell == null) continue;
                if(cell.getTile() == null) continue;

                //Create a body & fixture for the cell
                BodyDef bDef = new BodyDef();
                bDef.type = BodyDef.BodyType.StaticBody;
                bDef.position.set(((col + 0.5f) * tileSize) / Constants.WORLD_TO_SCREEN, ((row + 0.5f) * tileSize) / Constants.WORLD_TO_SCREEN);

                ChainShape cs = new ChainShape();
                Vector2[] v = new Vector2[4];
                v[0] = new Vector2((-tileSize / 2) / Constants.WORLD_TO_SCREEN , (-tileSize / 2) / Constants.WORLD_TO_SCREEN );
                v[1] = new Vector2((-tileSize / 2) / Constants.WORLD_TO_SCREEN , (tileSize / 2) / Constants.WORLD_TO_SCREEN );
                v[2] = new Vector2((tileSize / 2) / Constants.WORLD_TO_SCREEN , (tileSize / 2) / Constants.WORLD_TO_SCREEN );
                v[3] = new Vector2((tileSize / 2) / Constants.WORLD_TO_SCREEN , (-tileSize / 2) / Constants.WORLD_TO_SCREEN );
                cs.createChain(v);

                FixtureDef fDef = new FixtureDef();
                fDef.friction = 0;
                fDef.shape = cs;
                fDef.filter.categoryBits = Constants.COLLISION_WALL_BITS;
                fDef.filter.maskBits = Constants.COLLISION_PLAYER_BITS;
                fDef.isSensor = false;
                Body body = world.createBody(bDef);
                body.setUserData(new GroundUserData(tileSize, tileSize));
                body.createFixture(fDef);
            }
        }
    }

    private void setupBackGround() {
        addActor(new Background());
    }

    private void createEnemy() {
        Enemy enemy = new Enemy(WorldUtils.createEnemy(world));
        addActor(enemy);
    }

    private void setUpRunner() {
        runner = new Runner(WorldUtils.createRunner(world)/*, collisionLayer*/);
        addActor(runner);
    }

    private void setupCamera() {
        camera = (OrthographicCamera) getViewport().getCamera();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0.0f);
        camera.update();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Fixed timestep
        accumulator += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        camera.position.set((runner.getPosition().x) , VIEWPORT_HEIGHT / 2, 0.0f);
        camera.update();

        renderer.setView(camera);
        renderer.render();

        //Update the controls and render them
        setupTouchControlAreas();
        renderButtons();

        debugRenderer.render(world, camera.combined);

        Array<Body> bodies = new Array<Body>(world.getBodyCount());
        world.getBodies(bodies);

        for (Body body : bodies) {
            update(body);
        }

        //TODO: Implement interpolation
    }

    private void renderButtons() {

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0, 1, 0, 0.2f));
        //Draw the leftSideButton
        shapeRenderer.rect(runner.getPosition().x - getCamera().viewportWidth / 2, 0, getCamera().viewportWidth / 3, getCamera().viewportHeight);
        shapeRenderer.setColor(new Color(1, 1, 1, 0.2f));
        //Draw the rightSideButton
        shapeRenderer.rect(runner.getPosition().x + (getCamera().viewportWidth / 6), 0, getCamera().viewportWidth / 3, getCamera().viewportHeight);
        shapeRenderer.setColor(new Color(1, 0, 0, 0.2f));
        //Draw the topSideButton
        shapeRenderer.rect(runner.getPosition().x - (getCamera().viewportWidth / 6), (getCamera().viewportHeight / 3) * 2, getCamera().viewportWidth / 3, getCamera().viewportHeight / 3);
        shapeRenderer.setColor(new Color(0, 0, 1, 0.2f));
        //Draw the botSideButton
        shapeRenderer.rect(runner.getPosition().x - (getCamera().viewportWidth / 6), 0, getCamera().viewportWidth / 3, getCamera().viewportHeight / 3);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }


    private void update(Body body) {
        if(!BodyUtils.bodyInBounds(body)){
            if(BodyUtils.bodyIsEnemy(body) && !runner.isHit()){
                createEnemy();
            }
            if(BodyUtils.bodyIsRunner(body) && !runner.isHit()){
                GameScreen.restartGame();
            }else{
                world.destroyBody(body);
            }
        }
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button){
        _translateScreenToWorldCoordinates(x, y);

        if(_rightSideTouched(touchPoint.x, touchPoint.y)){
            runner.moveRight();
        }else if(_leftSideTouched(touchPoint.x, touchPoint.y)){
            runner.moveLeft();
        }else if(_botSideTouched(touchPoint.x, touchPoint.y)){
            runner.dodge();
            runner.respawn();
        }else if(_topSideTouched(touchPoint.x, touchPoint.y)){
            runner.jump();
        }
        return super.touchDown(x, y, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button){
        if(runner.isDodging()){
            runner.stopDodge();
        }
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public void beginContact(Contact contact){
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if ((BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsEnemy(b)) ||
                (BodyUtils.bodyIsEnemy(a) && BodyUtils.bodyIsRunner(b))) {
            runner.hit();
        } else if ((BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsGround(b)) ||
                (BodyUtils.bodyIsGround(a) && BodyUtils.bodyIsRunner(b))) {
            runner.landed();
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private boolean _leftSideTouched(float x, float y){
        return screenLeftButton.contains(x, y);
    }

    private boolean _rightSideTouched(float x, float y){
        return screenRightButton.contains(x, y);
    }

    private boolean _topSideTouched(float x, float y){
        return screenTopButton.contains(x, y);
    }

    private boolean _botSideTouched(float x, float y){
        return screenBotButton.contains(x, y);
    }

    private void _translateScreenToWorldCoordinates(int x, int y){
        getCamera().unproject(touchPoint.set(x, y, 0));
    }

    //MAP TEST
    @Override
    public void dispose(){
        map.dispose();
        //renderer.dispose();
        debugRenderer.dispose();
    }
    //MAP TEST

}
