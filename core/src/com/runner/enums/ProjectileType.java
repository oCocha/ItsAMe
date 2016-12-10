package com.runner.enums;

import com.badlogic.gdx.math.Vector2;
import com.runner.utils.Constants;

/**
 * Created by oCocha on 03.12.2016.
 */

public enum ProjectileType {

    BULLET_SMALL(Constants.PROJECTILE_BULLET_WIDTH / Constants.WORLD_TO_SCREEN, Constants.PROJECTILE_BULLET_HEIGHT / Constants.WORLD_TO_SCREEN, Constants.PROJECTILE_BULLET_X, Constants.PROJECTILE_BULLET_Y, Constants.PROJECTILE_BULLET_DENSITY,
            Constants.PROJECTILE_BULLET_REGION_NAMES, Constants.PROJECTILE_BULLET_GRAVITY_SCALE, Constants.PROJECTILE_BULLET_LINEAR_VELOCITY_RIGHT),
    BOMB_SMALL(Constants.PROJECTILE_BOMB_WIDTH / Constants.WORLD_TO_SCREEN, Constants.PROJECTILE_BOMB_HEIGHT / Constants.WORLD_TO_SCREEN, Constants.PROJECTILE_BOMB_X, Constants.PROJECTILE_BOMB_Y, Constants.PROJECTILE_BOMB_DENSITY,
                 Constants.PROJECTILE_BOMB_REGION_NAMES, Constants.PROJECTILE_BOMB_GRAVITY_SCALE, Constants.PROJECTILE_BOMB_LINEAR_VELOCITY_RIGHT),
    MINE_SMALL(Constants.PROJECTILE_MINE_WIDTH / Constants.WORLD_TO_SCREEN, Constants.PROJECTILE_MINE_HEIGHT / Constants.WORLD_TO_SCREEN, Constants.PROJECTILE_MINE_X, Constants.PROJECTILE_MINE_Y, Constants.PROJECTILE_MINE_DENSITY,
               Constants.PROJECTILE_MINE_REGION_NAMES, Constants.PROJECTILE_MINE_GRAVITY_SCALE, Constants.PROJECTILE_MINE_LINEAR_VELOCITY_RIGHT);

    private float width;
    private float height;
    private float x;
    private float y;
    private float density;
    private String[] regions;
    private float gravity;
    public Vector2 linearVelocity;

    ProjectileType(float width, float height, float x, float y, float density, String[] regions, float gravity, Vector2 linearVelocity) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.density = density;
        this.regions = regions;
        this.gravity = gravity;
        this.linearVelocity = linearVelocity;
    }

    public String[] getRegions(){
        return regions;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getDensity() {
        return density;
    }

    public float getGravity() {
        return gravity;
    }

    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }

}
