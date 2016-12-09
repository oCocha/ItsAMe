package com.runner.stages;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.runner.actors.Background;
import com.runner.actors.Enemy;
import com.runner.actors.Projectile;
import com.runner.actors.Runner;
import com.runner.box2d.GroundUserData;
import com.runner.box2d.HazardUserData;
import com.runner.box2d.ProjectileUserData;
import com.runner.screens.GameScreen;
import com.runner.utils.BodyUtils;
import com.runner.utils.Constants;
import com.runner.utils.WorldUtils;
import java.util.ArrayList;


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
    private float explodeAccumulator = 0f;

    private OrthographicCamera camera;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMapTileLayer collisionLayer;
    private float tileSize;

    private Box2DDebugRenderer debugRenderer;

    private ArrayList<Body> destroyList = new ArrayList<Body>();
    private ArrayList<Vector2> explodeList = new ArrayList<Vector2>();
    private boolean noEnemy = true;

    private int score = 0;
    private Texture explosionTexture;

    private String levelName;

    /**Constructor*/
    public GameStage(String levelName){
        super(new ScalingViewport(Scaling.stretch, VIEWPORT_WIDTH, VIEWPORT_HEIGHT));

        this.levelName = levelName;

        setupCamera();
        setupWorld();
    }

    /**Initiate the game world*/
    private void setupWorld() {
        world = WorldUtils.createWorld();
        world.setContactListener(this);
        setupDebugRenderer();
        setupMap();
        setupBackGround();
        setUpRunner();
        setupTextures();
        createEnemy(Constants.ENEMY_X);
    }

    /**Initiate textures for later use*/
    private void setupTextures() {
        explosionTexture = new Texture(Constants.EXPLOSION_IMAGE_PATH);
    }

    /**Initiate the debug renderer*/
    private void setupDebugRenderer() {
        debugRenderer = new Box2DDebugRenderer();
    }

    /**Load the tile map, initiate the tilemap renderer and get the maps's "wall" elements*/
    private void setupMap() {
        map = new TmxMapLoader().load("map/" + levelName + ".tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Constants.WORLD_TO_SCREEN);
        collisionLayer = (TiledMapTileLayer)map.getLayers().get("walls");
        _createBox2DObjects(collisionLayer, Constants.TILED_LAYER_WALLS);
        collisionLayer = (TiledMapTileLayer)map.getLayers().get("hazards");
        _createBox2DObjects(collisionLayer, Constants.TILED_LAYER_HAZARDS);
    }

    /**Create a Box2D Object for every cell in the tilemap layer(collision)*/
    private void _createBox2DObjects(TiledMapTileLayer tiledMapTileLayer,int tileMode) {
        tileSize = tiledMapTileLayer.getTileWidth();
        //Go through all the cells in the tilemap layer
        for(int row = 0, height = tiledMapTileLayer.getHeight(); row < height; row++){
            for(int col = 0, width = tiledMapTileLayer.getWidth(); col < width; col++){
                //Get the current cell
                Cell cell = tiledMapTileLayer.getCell(col, row);

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
                /**Set either wall or hazard collision bits*/
                switch (tileMode){
                    case Constants.TILED_LAYER_WALLS:{
                        fDef.filter.categoryBits = Constants.COLLISION_WALL_BITS;
                        fDef.filter.maskBits = Constants.COLLISION_PLAYER_BITS | Constants.COLLISION_ENEMY_BITS | Constants.COLLISION_PROJECTILE_BITS;
                        break;
                    }
                    case Constants.TILED_LAYER_HAZARDS:{
                        fDef.filter.categoryBits = Constants.COLLISION_HAZARDS_BITS;
                        fDef.filter.maskBits = Constants.COLLISION_PLAYER_BITS;
                        break;
                    }
                }
                fDef.isSensor = false;
                Body body = world.createBody(bDef);
                /**Set either wall or hazard user data*/
                switch (tileMode){
                    case Constants.TILED_LAYER_WALLS:{
                        body.setUserData(new GroundUserData(tileSize, tileSize));
                        break;
                    }
                    case Constants.TILED_LAYER_HAZARDS:{
                        body.setUserData(new HazardUserData(tileSize, tileSize));
                        break;
                    }
                }
                body.createFixture(fDef);
            }
        }
    }

    /**Add a background object to the stage*/
    private void setupBackGround() {
        addActor(new Background());
    }

    /**Create a new enemy object and add it to the game stage*/
    private void createEnemy(float enemySpawnX) {
        noEnemy = false;
        Enemy enemy = new Enemy(WorldUtils.createEnemy(world, enemySpawnX));
        addActor(enemy);
    }

    /**Create a new runner/player object and add it to the game stage*/
    private void setUpRunner() {
        runner = new Runner(WorldUtils.createRunner(world));
        addActor(runner);
    }

    /**Create a new camera and set its position to the middle of the stage*/
    private void setupCamera() {
        camera = (OrthographicCamera) getViewport().getCamera();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0.0f);
        camera.update();
    }

    /**Act the stage every delta seconds*/
    @Override
    public void act(float delta) {
        super.act(delta);

        /**Step the world after a fixed timestap*/
        accumulator += delta;
        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        /**Clear the explodeList after a fixed timestap*/
        explodeAccumulator += delta;
        while (explodeAccumulator >= delta) {
            explodeList.clear();
            explodeAccumulator -= Constants.EXPLOSION_TIME;
        }

        /**Update the camera position*/
        camera.position.set((runner.getPosition().x) , VIEWPORT_HEIGHT / 2, 0.0f);
        camera.update();

        /**Render the map*/
        renderer.setView(camera);
        renderer.render();

        /**Render the bodies/fixtures of the box2d objects*/
        debugRenderer.render(world, camera.combined);

        /**Draw an explosion for every Vector2 in the explosionList*/
        for(Vector2 explosion : explodeList){
            explode(explosion.x, explosion.y);
        }

        /**Get all bodies in the world and save them*/
        Array<Body> bodies = new Array<Body>(world.getBodyCount());
        world.getBodies(bodies);
        /**Update all bodies in the stage*/
        for (Body body : bodies) {
            update(body);
        }

        /**Create a new enemy object if the world contains no enemy objects*/
        if(noEnemy == true)
            createEnemy(runner.getPosition().x + camera.viewportWidth / 2);

        /**Destroy all bodies that were saved in the destroylist
         * Add an explosion to the explodeList for every body that gets destroyed*/
        while(destroyList.size() != 0){
            if(!world.isLocked()){
                Body tempBody = destroyList.get(0);
                explodeList.add(new Vector2(tempBody.getPosition().x, tempBody.getPosition().y));
                tempBody  = null;
                world.destroyBody(destroyList.get(0));
                destroyList.remove(0);
            }
        }

        /**Restart the game if the runner/player was hit*/
        if(runner.isHit() == true)
            GameScreen.restartGame();
    }

    /**Draw an explosion at the given parameters*/
    private void explode(float bodyX, float bodyY) {
        getBatch().begin();
        getBatch().draw(explosionTexture, bodyX - Constants.EXPLOSION_WIDTH / 2, bodyY - Constants.EXPLOSION_HEIGHT / 2, Constants.EXPLOSION_WIDTH, Constants.EXPLOSION_HEIGHT);
        getBatch().end();
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
                destroyList.add(body);
            }
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
    public void beginContact(Contact contact){
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if((BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsEnemy(b)) ||
                (BodyUtils.bodyIsEnemy(a) && BodyUtils.bodyIsRunner(b)) ||
                (BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsHazard(b)) ||
                (BodyUtils.bodyIsHazard(a) && BodyUtils.bodyIsRunner(b))) {
            runner.hit();
        }else if((BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsGround(b)) ||
                (BodyUtils.bodyIsGround(a) && BodyUtils.bodyIsRunner(b))) {
            landed();
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
             */
            if(!destroyList.contains(b))
                destroyList.add(b);
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
             */
            if(!destroyList.contains(a))
                destroyList.add(a);
        }else if(BodyUtils.bodyIsEnemy(b) && BodyUtils.bodyIsProjectile(a) || BodyUtils.bodyIsEnemy(a) && BodyUtils.bodyIsProjectile(b)){
            if(!destroyList.contains(a) && !destroyList.contains(b)){
                destroyList.add(a);
                destroyList.add(b);
                noEnemy = true;
                score++;
                setScore(score);
            }
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

    private void setScore(int score){
        GameScreen.setScore(score);
    }

    /**Create a new projectile object and add it to the stage*/
    public void shoot() {
        Body projectileBody = WorldUtils.createProjectile(world, runner.getPosition().x + Constants.RUNNER_WIDTH / Constants.WORLD_TO_SCREEN, runner.getPosition().y + Constants.RUNNER_HEIGHT / Constants.WORLD_TO_SCREEN, runner.getFacingLeft(), runner.getShootMode());
        Projectile projectile = new Projectile(projectileBody, runner.getFacingLeft(), (ProjectileUserData) projectileBody.getUserData());
        addActor(projectile);
    }

    /**Reset the jump state of the runner/player*/
    public void landed(){
        runner.landed();
    }

    @Override
    public void dispose(){
        map.dispose();
        renderer.dispose();
        debugRenderer.dispose();
    }
}
