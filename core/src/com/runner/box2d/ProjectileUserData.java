package com.runner.box2d;

import com.badlogic.gdx.math.Vector2;
import com.runner.enums.UserDataType;
import com.runner.utils.Constants;

/**
 * Created by oCocha on 03.12.2016.
 */

public class ProjectileUserData extends UserData{

    private Vector2 bulletLinearVelocityRight;
    private Vector2 bulletLinearVelocityLeft;
    private Vector2 bombLinearVelocityRight;
    private Vector2 bombLinearVelocityLeft;
    private Vector2 linearVelocity;
    private String[] textureRegions;

    private boolean destroyed;

    public ProjectileUserData(float width, float height, String[] textureRegions, boolean facingLeft, Vector2 linearVelocity){
        super(width, height);
        userDataType = UserDataType.PROJECTILE;
        bulletLinearVelocityRight = Constants.PROJECTILE_BULLET_LINEAR_VELOCITY_RIGHT;
        bulletLinearVelocityLeft= Constants.PROJECTILE_BULLET_LINEAR_VELOCITY_LEFT;
        bombLinearVelocityRight = Constants.PROJECTILE_BULLET_LINEAR_VELOCITY_RIGHT;
        bombLinearVelocityLeft= Constants.PROJECTILE_BULLET_LINEAR_VELOCITY_LEFT;
        this.textureRegions = textureRegions;
        this.linearVelocity = linearVelocity;
        destroyed = false;
    }

    public void setLinearVelocityRight(Vector2 linearVelocity){
        this.bulletLinearVelocityRight = linearVelocity;
    }

    public void setLinearVelocityLeft(Vector2 linearVelocity){
        this.bulletLinearVelocityLeft = linearVelocity;
    }

    public Vector2 getLinearBulletVelocityRight(){
        return bulletLinearVelocityRight;
    }

    public Vector2 getLinearBulletVelocityLeft(){
        return bulletLinearVelocityLeft;
    }

    public Vector2 getLinearBombVelocityRight(){
        return bombLinearVelocityRight;
    }

    public Vector2 getLinearBombVelocityLeft(){
        return bombLinearVelocityLeft;
    }

    public Vector2 getLinearVelocity(){
        return linearVelocity;
    }

    public String[] getTextureRegions(){
        return textureRegions;
    }

    public boolean getDestroyed(){
        return destroyed;
    }

    public void setDestroyed(boolean destroyed){
        this.destroyed = destroyed;
    }
}
