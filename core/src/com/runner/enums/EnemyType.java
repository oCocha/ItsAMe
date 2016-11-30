package com.runner.enums;

import com.runner.utils.Constants;

/**
 * Created by bob on 20.11.16.
 */

public enum EnemyType {

    RUNNING_SMALL(Constants.RUNNING_SHORT_ENEMY_WIDTH / Constants.WORLD_TO_SCREEN, Constants.RUNNING_SHORT_ENEMY_HEIGHT / Constants.WORLD_TO_SCREEN, Constants.ENEMY_X, Constants.RUNNING_SHORT_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.RUNNING_SMALL_ENEMY_REGION_NAMES),
    RUNNING_WIDE(Constants.RUNNING_SHORT_ENEMY_WIDTH / Constants.WORLD_TO_SCREEN, Constants.RUNNING_SHORT_ENEMY_HEIGHT / Constants.WORLD_TO_SCREEN, Constants.ENEMY_X, Constants.RUNNING_SHORT_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.RUNNING_WIDE_ENEMY_REGION_NAMES),
    RUNNING_LONG(Constants.RUNNING_LONG_ENEMY_WIDTH / Constants.WORLD_TO_SCREEN, Constants.RUNNING_LONG_ENEMY_HEIGHT / Constants.WORLD_TO_SCREEN, Constants.ENEMY_X, Constants.RUNNING_LONG_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.RUNNING_LONG_ENEMY_REGION_NAMES),
    RUNNING_BIG(Constants.RUNNING_BIG_ENEMY_WIDTH / Constants.WORLD_TO_SCREEN, Constants.RUNNING_BIG_ENEMY_HEIGHT / Constants.WORLD_TO_SCREEN, Constants.ENEMY_X, Constants.RUNNING_LONG_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.RUNNING_BIG_ENEMY_REGION_NAMES),
    FLYING_SMALL(Constants.FLYING_SMALL_ENEMY_WIDTH / Constants.WORLD_TO_SCREEN, Constants.FLYING_SMALL_ENEMY_HEIGHT / Constants.WORLD_TO_SCREEN, Constants.ENEMY_X, Constants.FLYING_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.FLYING_SMALL_ENEMY_REGION_NAMES),
    FLYING_WIDE(Constants.FLYING_WIDE_ENEMY_WIDTH / Constants.WORLD_TO_SCREEN, Constants.FLYING_WIDE_ENEMY_HEIGHT / Constants.WORLD_TO_SCREEN, Constants.ENEMY_X, Constants.FLYING_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.FLYING_WIDE_ENEMY_REGION_NAMES);

    private float width;
    private float height;
    private float x;
    private float y;
    private float density;
    private String[] regions;

    EnemyType(float width, float height, float x, float y, float density, String[] regions) {
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
