package com.runner.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.runner.box2d.EnemyUserData;
import com.runner.box2d.GroundUserData;
import com.runner.box2d.ProjectileUserData;
import com.runner.box2d.RunnerUserData;
import com.runner.enums.EnemyType;
import com.runner.enums.ProjectileType;

/**
 * Created by bob on 20.11.16.
 */

public class WorldUtils {

    public static World createWorld(){
        return new World(Constants.WORLD_GRAVITY, true);
    }

    public static Body createGround(World world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(Constants.GROUND_X, Constants.GROUND_Y));
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.GROUND_WIDTH / 2, Constants.GROUND_HEIGHT / 2);
        body.createFixture(shape, Constants.GROUND_DENSITY);
        body.setUserData(new GroundUserData(Constants.GROUND_WIDTH, Constants.GROUND_HEIGHT));
        shape.dispose();
        return body;
    }

    public static Body createRunner(World world){
        /*
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(Constants.RUNNER_X, Constants.RUNNER_Y));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.RUNNER_WIDTH / 2, Constants.RUNNER_HEIGHT / 2);
        Body body = world.createBody(bodyDef);
        body.setGravityScale(Constants.RUNNER_GRAVITY_SCALE);
        body.createFixture(shape, Constants.RUNNER_DENSITY);
        body.resetMassData();
        body.setUserData(new RunnerUserData(Constants.RUNNER_WIDTH, Constants.RUNNER_HEIGHT));
        shape.dispose();
        return body;
        */

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(Constants.RUNNER_X / Constants.WORLD_TO_SCREEN, Constants.RUNNER_Y / Constants.WORLD_TO_SCREEN));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.RUNNER_WIDTH / Constants.WORLD_TO_SCREEN, Constants.RUNNER_HEIGHT / Constants.WORLD_TO_SCREEN);

        FixtureDef fDef = new FixtureDef();
        fDef.friction = 0;
        fDef.shape = shape;
        fDef.filter.categoryBits = Constants.COLLISION_PLAYER_BITS;
        fDef.filter.maskBits = Constants.COLLISION_WALL_BITS | Constants.COLLISION_ENEMY_BITS | Constants.COLLISION_HAZARDS_BITS | Constants.COLLISION_PROJECTILE_BITS;
        fDef.isSensor = false;

        Body body = world.createBody(bodyDef);
        body.setGravityScale(Constants.RUNNER_GRAVITY_SCALE);
        body.createFixture(fDef);
        body.resetMassData();
        body.setUserData(new RunnerUserData(Constants.RUNNER_WIDTH / Constants.WORLD_TO_SCREEN, Constants.RUNNER_HEIGHT / Constants.WORLD_TO_SCREEN));
        shape.dispose();

        return body;

    }

    public static Body createEnemy(World world, float spawnX){
        EnemyType enemyType = RandomUtils.getRandomEnemyType();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(spawnX, enemyType.getY()));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(enemyType.getWidth() / 2, enemyType.getHeight() / 2);

        FixtureDef fDef = new FixtureDef();
        fDef.friction = 0;
        fDef.shape = shape;
        fDef.filter.categoryBits = Constants.COLLISION_ENEMY_BITS;
        fDef.filter.maskBits = Constants.COLLISION_PLAYER_BITS | Constants.COLLISION_WALL_BITS | Constants.COLLISION_PROJECTILE_BITS;
        fDef.isSensor = false;

        Body body = world.createBody(bodyDef);
        body.setGravityScale(Constants.RUNNER_GRAVITY_SCALE);
        body.createFixture(fDef);
        body.resetMassData();
        EnemyUserData userData = new EnemyUserData(enemyType.getWidth(), enemyType.getHeight(), enemyType.getRegions());
        body.setUserData(userData);
        shape.dispose();
        return body;
    }

    public static Body createProjectile(World world, float spawnX, float spawnY, boolean facingLeft, int shootMode){
        /**Get a random projectileType
         *
        ProjectileType projectileType = RandomUtils.getRandomProjectileType();
         */
        ProjectileType projectileType = RandomUtils.getProjectileType(shootMode);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        if(facingLeft == true){
            bodyDef.position.set(new Vector2(shootMode == 2 ? spawnX + projectileType.getWidth() : spawnX - Constants.RUNNER_WIDTH / Constants.WORLD_TO_SCREEN - projectileType.getWidth(), spawnY));
        }else{
            bodyDef.position.set(new Vector2(shootMode == 2 ? spawnX - Constants.RUNNER_WIDTH / Constants.WORLD_TO_SCREEN - projectileType.getWidth() : spawnX + projectileType.getWidth(), spawnY));
        }
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(projectileType.getWidth() / 2, projectileType.getHeight() / 2);

        FixtureDef fDef = new FixtureDef();
        fDef.friction = 0;
        fDef.shape = shape;
        fDef.filter.categoryBits = Constants.COLLISION_PROJECTILE_BITS;
        fDef.filter.maskBits = Constants.COLLISION_PLAYER_BITS | Constants.COLLISION_WALL_BITS | Constants.COLLISION_ENEMY_BITS;
        /**Set either projectile or mine collision bits*//**Unnötig?????
        switch (shootMode){
            case 2:{
                fDef.filter.categoryBits = Constants.COLLISION_MINE_BITS;
                fDef.filter.maskBits = Constants.COLLISION_PLAYER_BITS | Constants.COLLISION_WALL_BITS | Constants.COLLISION_ENEMY_BITS | Constants.COLLISION_PROJECTILE_BITS;
                break;
            }
            default:{
                fDef.filter.categoryBits = Constants.COLLISION_PROJECTILE_BITS;
                fDef.filter.maskBits = Constants.COLLISION_PLAYER_BITS | Constants.COLLISION_WALL_BITS | Constants.COLLISION_ENEMY_BITS | Constants.COLLISION_MINE_BITS;
                break;
            }
        }*/
        fDef.isSensor = false;

        Body body = world.createBody(bodyDef);
        body.setGravityScale(projectileType.getGravity());
        body.createFixture(fDef);
        body.resetMassData();
        ProjectileUserData userData = new ProjectileUserData(shootMode, projectileType.getWidth(), projectileType.getHeight(), projectileType.getRegions(), facingLeft, projectileType.getLinearVelocity());
        body.setUserData(userData);
        shape.dispose();
        return body;
    }
}
