package com.runner.enums;

import com.runner.utils.Constants;

/**
 * Created by oCocha on 03.12.2016.
 */

public enum ProjectileType {

    BULLET_SMALL(Constants.PROJECTILE_BULLET_WIDTH / Constants.WORLD_TO_SCREEN, Constants.PROJECTILE_BULLET_HEIGHT / Constants.WORLD_TO_SCREEN, Constants.PROJECTILE_BULLET_X, Constants.PROJECTILE_BULLET_Y, Constants.PROJECTILE_BULLET_DENSITY,
            Constants.PROJECTILE_BULLET_REGION_NAMES);

    private float width;
    private float height;
    private float x;
    private float y;
    private float density;
    private String[] regions;

    ProjectileType(float width, float height, float x, float y, float density, String[] regions) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.density = density;
        this.regions = regions;
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

}
