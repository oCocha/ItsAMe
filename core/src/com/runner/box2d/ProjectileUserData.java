package com.runner.box2d;

import com.badlogic.gdx.math.Vector2;
import com.runner.enums.UserDataType;
import com.runner.utils.Constants;

/**
 * Created by oCocha on 03.12.2016.
 */

public class ProjectileUserData extends UserData{

    private Vector2 linearVelocity;
    private String[] textureRegions;

    private boolean destroyed;

    public ProjectileUserData(float width, float height, String[] textureRegions){
        super(width, height);
        userDataType = UserDataType.PROJECTILE;
        linearVelocity = Constants.PROJECTILE_LINEAR_VELOCITY;
        this.textureRegions = textureRegions;
        destroyed = false;
    }

    public void setLinearVelocity(Vector2 linearVelocity){
        this.linearVelocity = linearVelocity;
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
