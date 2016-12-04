package com.runner.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.runner.actors.Background;
import com.runner.actors.Enemy;
import com.runner.actors.Projectile;
import com.runner.actors.Runner;
import com.runner.box2d.GroundUserData;
import com.runner.box2d.ProjectileUserData;
import com.runner.screens.GameScreen;
import com.runner.utils.BodyUtils;
import com.runner.utils.Constants;
import com.runner.utils.WorldUtils;


/**
 * Created by bob on 20.11.16.
 */

public class GameStage extends Stage implements ContactListener {
    private static final int VIEWPORT_WIDTH = Constants.APP_WIDTH / (int)Constants.WORLD_TO_SCREEN;
    private static final int VIEWPORT_HEIGHT = Constants.APP_HEIGTH / (int)Constants.WORLD_TO_SCREEN;

    private World world;
    public Runner runner;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private OrthographicCamera camera;

    //Controls
    private Rectangle screenRightButton;
    private Rectangle screenLeftButton;
    private Rectangle screenTopButton;
    private Rectangle screenBotButton;
    private Touchpad touchpad;
    private Skin touchpadSkin;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Drawable touchBackground;
    private Drawable touchKnob;

    private Vector3 touchPoint;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMapTileLayer collisionLayer;
    private float tileSize;
    private PolygonShape shape;

    private ShapeRenderer shapeRenderer;

    private Box2DDebugRenderer debugRenderer;

    private Array<Body> destroyList = new Array<Body>();

    public GameStage(){
        super(new ScalingViewport(Scaling.stretch, VIEWPORT_WIDTH, VIEWPORT_HEIGHT));
        setupCamera();
        setupWorld();
    }

    private void setupWorld() {
        world = WorldUtils.createWorld();
        world.setContactListener(this);
        setupShapeRenderer();
        setupMap();
        setupBackGround();
        setUpRunner();
        createEnemy(Constants.ENEMY_X);
    }

    private void setupShapeRenderer() {

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
                fDef.filter.maskBits = Constants.COLLISION_PLAYER_BITS | Constants.COLLISION_ENEMY_BITS | Constants.COLLISION_PROJECTILE_BITS;
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

    private void createEnemy(float enemySpawnX) {
        Enemy enemy = new Enemy(WorldUtils.createEnemy(world, enemySpawnX));
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

        debugRenderer.render(world, camera.combined);

        Array<Body> bodies = new Array<Body>(world.getBodyCount());
        world.getBodies(bodies);

        /**Update all bodies in the stage*/
        for (Body body : bodies) {
            update(body);
        }

        /**SOLUTION 2
         * Destroy projectiles on ground collision
         *
        /**Destroy all bodies that were saved in the destroylist*/
        if(destroyList.size != 0){
            for(int i = 0, s = destroyList.size; i < s; i++){

                /**FEHLER HIER*/
                if(!world.isLocked()){
                    world.destroyBody(destroyList.get(i));
                }
            }
        }

        //TODO: Implement interpolation
    }

    /**Check the body for collisions and out of bounds*/
    private void update(Body body) {
        /**Check whether the body is out of bounds*/
        if(!BodyUtils.bodyInBounds(body, runner.getPosition().x - camera.viewportWidth / 2)){
            if(BodyUtils.bodyIsEnemy(body) && !runner.isHit()){
                createEnemy(runner.getPosition().x + camera.viewportWidth / 2);
            }
            if(BodyUtils.bodyIsRunner(body) && !runner.isHit()){
                GameScreen.restartGame();
            }else{
                world.destroyBody(body);
            }
            /**Destroy Projectiles when they are out of bounds
             *
            if(BodyUtils.bodyIsProjectile(body) && !runner.isHit()){
                world.destroyBody(body);
            }*/
        }

        /**SOLUTION 1
         * Destroy projectiles on ground collision
         *
        if(BodyUtils.bodyIsProjectile(body)){
            ProjectileUserData projectileUserData = (ProjectileUserData)body.getUserData();
            System.out.print(projectileUserData.getDestroyed());
            if(projectileUserData.getDestroyed() == true){
                world.destroyBody(body);
            }
        }
         */
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

        if((BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsEnemy(b)) ||
                (BodyUtils.bodyIsEnemy(a) && BodyUtils.bodyIsRunner(b))) {
            runner.hit();
        }else if((BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsGround(b)) ||
                (BodyUtils.bodyIsGround(a) && BodyUtils.bodyIsRunner(b))) {
            runner.landed();
        }else if(BodyUtils.bodyIsGround(a) && BodyUtils.bodyIsProjectile(b)){
            /**SOLUTION 1
             * Destroy projectiles on ground collision
             *
            ProjectileUserData projectileUserData = (ProjectileUserData)b.getUserData();
            projectileUserData.setDestroyed(true);
            System.out.print("a");
             */

            /**SOLUTION 2
             * Destroy projectiles on ground collision
             *
            destroyList.add(b);
             */
        }else if(BodyUtils.bodyIsGround(b) && BodyUtils.bodyIsProjectile(a)){
            /**SOLUTION 1
             * Destroy projectiles on ground collision
             *
            ProjectileUserData projectileUserData = (ProjectileUserData)a.getUserData();
            projectileUserData.setDestroyed(true);
            System.out.print("b");
             */

            /**SOLUTION 2
             * Destroy projectiles on ground collision
             *
            destroyList.add(a);
             */
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


    public void movePlayer(float knobPercentX, float knobPercentY) {
        runner.move(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());
    }

    public void shoot() {
        Projectile projectile = new Projectile(WorldUtils.createProjectile(world, runner.getPosition().x + Constants.RUNNER_WIDTH / Constants.WORLD_TO_SCREEN, runner.getPosition().y + Constants.RUNNER_HEIGHT / Constants.WORLD_TO_SCREEN));
        addActor(projectile);
    }

    @Override
    public void dispose(){
        map.dispose();
        renderer.dispose();
        debugRenderer.dispose();
    }
}
