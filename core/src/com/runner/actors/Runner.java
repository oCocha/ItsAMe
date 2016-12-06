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
    private boolean readyToJump = true;
    private Animation runningAnimation;
    private TextureRegion jumpingTexture;
    private TextureRegion dodgingTexture;
    private TextureRegion hitTexture;
    private float stateTime;
    private boolean facingLeft = false;
    private float accumulator = 0f;

    private Vector2 velocity;

    private Body runnerBody;

    private int shootMode = Constants.DEFAULT_SHOOT_MODE;

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
            batch.draw(jumpingTexture, facingLeft ? screenRectangle.x + screenRectangle.width * 3 / 2 : screenRectangle.x - screenRectangle.width / 2, screenRectangle.y - screenRectangle.height / 2, facingLeft ? -screenRectangle.width * 2 : screenRectangle.width * 2, screenRectangle.height * 2);
        }else{
            stateTime += Gdx.graphics.getDeltaTime();
            batch.draw(runningAnimation.getKeyFrame(stateTime, true), facingLeft ? screenRectangle.x + screenRectangle.width * 3 / 2 : screenRectangle.x - screenRectangle.width / 2, screenRectangle.y - screenRectangle.height / 2, facingLeft ? -screenRectangle.width * 2 : screenRectangle.width * 2, screenRectangle.height * 2);
        }
    }

    @Override
    public void act(float delta){
        super.act(delta);
        update();

        /**Refresh the readytojump variable*/
        if(!jumping && !readyToJump){
            accumulator += delta;
            while (accumulator >= delta) {
                readyToJump = true;
                accumulator -= Constants.RUNNER_JUMP_DELAY;
            }
        }
    }

    private void update() {
        velocity = body.getLinearVelocity();
        velocity.x *= Constants.PLAYER_STAMP;
        body.setLinearVelocity(velocity);
    }

    @Override
    public RunnerUserData getUserData(){
        return(RunnerUserData) userData;
    }

    public void jump(){
        if((!jumping /*|| dodging || hit*/) && readyToJump){
            body.applyLinearImpulse(getUserData().getJumpingLinearImpulse(), body.getWorldCenter(), true);
            jumping = true;
            readyToJump = false;
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
        System.out.print("Stop");
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
        hit = true;
    }

    public boolean isHit(){
        return hit;
    }

    public void respawn(){
        setPosition(new Vector2(Constants.RUNNER_X / Constants.WORLD_TO_SCREEN, Constants.RUNNER_Y / Constants.WORLD_TO_SCREEN));
        resetVelocity();
    }

    /**Move the player if the joystick was moved*/
    public void move(float knobPercentX, float knobPercentY) {
        velocity = body.getLinearVelocity();
        /**Control max speed in right direction*/
        if(velocity.x < (-Constants.RUNNER_SPEED_MAX) && !dodging){
            facingLeft = true;
            velocity.set(-Constants.RUNNER_SPEED_MAX, velocity.y);
        /**Control max speed in left direction*/
        }else if(velocity.x > (Constants.RUNNER_SPEED_MAX) && !dodging){
            facingLeft = false;
            velocity.set(Constants.RUNNER_SPEED_MAX, velocity.y);
        /**Set the player speed according to the percentage value the player moved the joystick*/
        }else if(!dodging){
            velocity.set(velocity.x + knobPercentX * Constants.RUNNER_SPEED_STEP, velocity.y);
        }
        /**Change the direction the player sprite is looking at*/
        if(velocity.x > 0  && !dodging){
            facingLeft = false;
        }else if(!dodging){
            facingLeft = true;
        }
        /**Apply the movement velocity*/
        body.setLinearVelocity(velocity);
        /**Jump if the user moved the joystick in top direction*/
        if(knobPercentY > Constants.UI_TOUCHPAD_JUMP_SENSITY && !dodging){
            jump();
        }
        /**Dodge if the user moved the joystick in bottom direction*/
        if(knobPercentY < Constants.UI_TOUCHPAD_DODGE_SENSITY){
            dodge();
        }
    }

    public void setShootMode(int newMode){
        shootMode = newMode;
    }

    public boolean getFacingLeft() {
        return facingLeft;
    }

    public int getShootMode() {
        return shootMode;
    }
}