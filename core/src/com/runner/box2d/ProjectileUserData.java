package com.runner.box2d;

import com.badlogic.gdx.math.Vector2;
import com.runner.enums.UserDataType;
import com.runner.utils.Constants;

/**
 * Created by oCocha on 03.12.2016.
 */

public class ProjectileUserData extends UserData{

    private Vector2 linearVelocityRight;
    private Vector2 linearVelocityLeft;
    private String[] textureRegions;

    private boolean destroyed;

    public ProjectileUserData(float width, float height, String[] textureRegions, boolean facingLeft){
        super(width, height);
        userDataType = UserDataType.PROJECTILE;
        linearVelocityRight = Constants.PROJECTILE_LINEAR_VELOCITY_RIGHT;
        linearVelocityLeft= Constants.PROJECTILE_LINEAR_VELOCITY_LEFT;
        this.textureRegions = textureRegions;
        destroyed = false;
    }

    public void setLinearVelocityRight(Vector2 linearVelocity){
        this.linearVelocityRight = linearVelocity;
    }

    public void setLinearVelocityLeft(Vector2 linearVelocity){
        this.linearVelocityLeft = linearVelocity;
    }

    public Vector2 getLinearVelocityRight(){
        return linearVelocityRight;
    }

    public Vector2 getLinearVelocityLeft(){
        return linearVelocityLeft;
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
