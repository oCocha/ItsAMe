package com.runner.actors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.runner.box2d.UserData;
import com.runner.utils.Constants;

/**
 * Created by bob on 20.11.16.
 */

public abstract class GameActor extends Actor {

    protected Body body;
    protected UserData userData;
    protected Rectangle screenRectangle;

    private Vector2 velocity;

    public GameActor(Body body){
        this.body = body;
        this.userData = (UserData) body.getUserData();
        screenRectangle = new Rectangle();
    }

    @Override
    public void act(float delta){
        super.act(delta);
        if(body.getUserData() != null){
            updateRectangle();
        }else{
            remove();
        }
    }

    private void updateRectangle() {
        screenRectangle.x = transformToScreen(body.getPosition().x - userData.getWidth() / 2);
        screenRectangle.y = transformToScreen(body.getPosition().y - userData.getHeight() / 2);
        screenRectangle.width = transformToScreen(userData.getWidth());
        screenRectangle.height = transformToScreen(userData.getHeight());
    }

    public Vector2 getPosition(){
        return new Vector2(screenRectangle.x, screenRectangle.y);
    }

    public void setX(int xValue){
        screenRectangle.x = xValue;
    }

    public void setY(int yValue){
        screenRectangle.y = yValue;
    }

    public void setPosition(Vector2 position){
        body.setTransform(position,body.getAngle());
    }

    public void resetVelocity(){
        velocity = body.getLinearVelocity();
        velocity.set(0, 0);
        body.setLinearVelocity(velocity);
    }

    protected float transformToScreen(float n) {
        return n /** Constants.WORLD_TO_SCREEN*/;
    }

    public abstract UserData getUserData();

}
