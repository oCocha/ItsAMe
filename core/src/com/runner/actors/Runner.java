package com.runner.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.runner.box2d.RunnerUserData;
import com.runner.screens.GameScreen;
import com.runner.utils.Constants;

/**
 * Created by bob on 20.11.16.
 */

public class Runner extends GameActor {

    private boolean jumping;
    private boolean dodging;
    private boolean hit;
    private Animation runningAnimation;
    private TextureRegion jumpingTexture;
    private TextureRegion dodgingTexture;
    private TextureRegion hitTexture;
    private float stateTime;
    private boolean facingLeft = false;

    private Vector2 velocity;

    private TiledMapTileLayer collisionLayer;
    private float tileWidth, tileHeight, oldX, oldY;
    private boolean collisionX, collisionY;

    private Body runnerBody;

    public Runner(Body body/*, TiledMapTileLayer collisionLayer*/){

        super(body);
        TextureAtlas textureAtlas = new TextureAtlas(Constants.CHARACTERS_ATLAS_PATH);
        TextureRegion[] runningFrames = new TextureRegion[Constants.RUNNER_RUNNING_REGION_NAMES.length];
        for(int i = 0; i < Constants.RUNNER_RUNNING_REGION_NAMES.length; i++){
            String path = Constants.RUNNER_RUNNING_REGION_NAMES[i];
            runningFrames[i] = textureAtlas.findRegion(path);
        }
        runningAnimation = new Animation(0.1f, runningFrames);
        stateTime = 0f;
        jumpingTexture = textureAtlas.findRegion(Constants.RUNNER_JUMPING_REGION_NAME);
        dodgingTexture = textureAtlas.findRegion(Constants.RUNNER_DODGING_REGION_NAME);
        hitTexture = textureAtlas.findRegion(Constants.RUNNER_HIT_REGION_NAME);

        velocity = new Vector2();
        //this.collisionLayer = collisionLayer;
        //tileHeight = collisionLayer.getTileHeight();
        //tileWidth = collisionLayer.getTileWidth();

        runnerBody = body;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        if(dodging){
            batch.draw(dodgingTexture, screenRectangle.x, screenRectangle.y + screenRectangle.height / 4, facingLeft ? -screenRectangle.width * 2 : screenRectangle.width * 2, screenRectangle.height * 6 / 4);
        }else if(hit){
            batch.draw(hitTexture, screenRectangle.x, screenRectangle.y, facingLeft ? -screenRectangle.width * 0.5f : screenRectangle.width * 0.5f, screenRectangle.height * 0.5f, screenRectangle.width, screenRectangle.height, 1f, 1f, (float)Math.toDegrees(body.getAngle()));
        }else if(jumping){
            batch.draw(jumpingTexture, facingLeft ? screenRectangle.x + screenRectangle.width : screenRectangle.x - screenRectangle.width / 2, screenRectangle.y - screenRectangle.height / 2, facingLeft ? -screenRectangle.width * 2 : screenRectangle.width * 2, screenRectangle.height * 2);
        }else{
            stateTime += Gdx.graphics.getDeltaTime();
            batch.draw(runningAnimation.getKeyFrame(stateTime, true), facingLeft ? screenRectangle.x + screenRectangle.width : screenRectangle.x - screenRectangle.width / 2, screenRectangle.y - screenRectangle.height / 2, facingLeft ? -screenRectangle.width * 2 : screenRectangle.width * 2, screenRectangle.height * 2);
        }
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }

    @Override
    public RunnerUserData getUserData(){
        return(RunnerUserData) userData;
    }

    public void jump(){
        if(!jumping || dodging || hit){
            body.applyLinearImpulse(getUserData().getJumpingLinearImpulse(), body.getWorldCenter(), true);
            jumping = true;
        }
    }

    public void landed(){
        jumping = false;
    }

    public void dodge(){
        if(!jumping || hit){
            body.setTransform(body.getPosition(), getUserData().getDodgeAngle());
            dodging = true;
        }
    }

    public void stopDodge(){
        dodging = false;
        if (!hit) {
            body.setTransform(body.getPosition(), 0f);
        }
    }

    public boolean isDodging(){
        return dodging;
    }

    public void hit(){
        //body.applyAngularImpulse(getUserData().getHitAngularImpulse(), true);
        //hit = true;
        //GameScreen.restartGame();
    }

    public boolean isHit(){
        return hit;
    }

    /** Accelerate the player in right direction if he hasnt reached max speed already and set the facing direction*/
    public void moveRight() {
        if(!hit){
            facingLeft = false;
            velocity = body.getLinearVelocity();
            if(velocity.x < (Constants.RUNNER_SPEED_MAX - Constants.RUNNER_SPEED_STEP)){
                velocity.set(velocity.x + Constants.RUNNER_SPEED_STEP, velocity.y);
            }else{
                velocity.set(Constants.RUNNER_SPEED_MAX, velocity.y);
            }
            body.setLinearVelocity(velocity);

            //ALTERNATIVE
            //body.applyLinearImpulse(getUserData().getMoveRightLinearImpulse(), body.getWorldCenter(), true);
        }
    }

    /** Accelerate the player in right direction  if he hasnt reached max speed already and set the facing direction*/
    public void moveLeft() {
        if(!hit){
            facingLeft = true;
            velocity = body.getLinearVelocity();
            if(velocity.x > (-Constants.RUNNER_SPEED_MAX + Constants.RUNNER_SPEED_STEP)){
                velocity.set(velocity.x - Constants.RUNNER_SPEED_STEP, velocity.y);
            }else{
                velocity.set(-Constants.RUNNER_SPEED_MAX, velocity.y);
            }
            body.setLinearVelocity(velocity);

            //ALTERNATIVE
            //body.applyLinearImpulse(getUserData().getMoveLeftLinearImpulse(), body.getWorldCenter(), true);
        }
    }

    public void respawn(){
        setPosition(new Vector2(Constants.RUNNER_X / Constants.WORLD_TO_SCREEN, Constants.RUNNER_Y / Constants.WORLD_TO_SCREEN));
        resetVelocity();
    }
}