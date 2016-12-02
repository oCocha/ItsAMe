package com.runner.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.StreamUtils;
import com.badlogic.gdx.utils.StringBuilder;

/**
 * Created by bob on 20.11.16.
 */

public class Constants {

    public static final int APP_WIDTH = 800;
    public static final int APP_HEIGTH = 480;
    public static final float WORLD_TO_SCREEN = 32.0f;

    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -10);

    public static final int COLLISION_PLAYER_BITS = 2;
    public static final int COLLISION_WALL_BITS = 4;
    public static final short COLLISION_ENEMY_BITS = 8;

    public static final float GROUND_X = 0;
    public static final float GROUND_Y = 0;
    public static final float GROUND_WIDTH = 40f;
    public static final float GROUND_HEIGHT = 2f;
    public static final float GROUND_DENSITY = 0f;

    public static final float RUNNER_X = APP_WIDTH / 2;
    public static final float RUNNER_Y = APP_HEIGTH / 2;
    public static final float RUNNER_WIDTH = 10f;
    public static final float RUNNER_HEIGHT = 20f;
    public static final float RUNNER_GRAVITY_SCALE = 3f;
    public static float PLAYER_STAMP = 0.97f;
    public static final float RUNNER_SPEED_MAX = 5f;    //The max speed of the player
    public static final float RUNNER_SPEED_STEP = 1f;   //The amount the players speed increases per button click
    public static float RUNNER_DENSITY = 0.5f;
    public static final float RUNNER_DODGE_X = 2f;
    public static final float RUNNER_DODGE_Y = 1.5f;
    public static final Vector2 RUNNER_JUMPING_LINEAR_IMPULSE = new Vector2(0, 13f);
    public static final Vector2 RUNNER_MOVE_RIGHT_LINEAR_IMPULSE = new Vector2(5f, 0f);
    public static final Vector2 RUNNER_MOVE_LEFT_LINEAR_IMPULSE = new Vector2(-5f, 0f);
    public static final float RUNNER_HIT_ANGULAR_IMPULSE = 10f;

    public static final float ENEMY_X = 25f;
    public static final float ENEMY_DENSITY = RUNNER_DENSITY;
    public static final float RUNNING_SHORT_ENEMY_WIDTH = 20f;
    public static final float RUNNING_SHORT_ENEMY_HEIGHT = 20f;
    public static final float RUNNING_LONG_ENEMY_WIDTH = 40f;
    public static final float RUNNING_LONG_ENEMY_HEIGHT = 20f;
    public static final float RUNNING_BIG_ENEMY_WIDTH = 40f;
    public static final float RUNNING_BIG_ENEMY_HEIGHT = 40f;
    public static final float FLYING_SMALL_ENEMY_WIDTH = 20f;
    public static final float FLYING_SMALL_ENEMY_HEIGHT = 20f;
    public static final float FLYING_WIDE_ENEMY_WIDTH = 40f;
    public static final float FLYING_WIDE_ENEMY_HEIGHT = 20f;
    public static final float RUNNING_SHORT_ENEMY_Y = 1.5f;
    public static final float RUNNING_LONG_ENEMY_Y = 2f;
    public static final float FLYING_ENEMY_Y = 3f;
    public static final Vector2 ENEMY_LINEAR_VELOCITY = new Vector2(-5f, 0);

    public static final String LOGO_IMAGE_PATH = "screens/logo.png";
    public static final String MAINMENU_IMAGE_PATH = "screens/title.png";
    public static final String INTRO_IMAGE_PATH = "screens/intro.png";
    public static final String GAMEOVER_IMAGE_PATH = "screens/gameover.png";
    public static final String BACKGROUND_IMAGE_PATH = "background.png";
    public static final String GROUND_IMAGE_PATH = "ground.png";

    public static final String UI_ATLAS_PATH = "ui/uiatlas.txt";
    public static final String UI_SKIN_PATH = "ui/uiskin.json";
    public static final String UI_TOUCHPAD_BACKGROUND_PATH = "ui/touchBackground.png";
    public static final String UI_TOUCHPAD_KNOB_PATH = "ui/touchKnob.png";
    public static final float UI_TOUCHPAD_X = APP_WIDTH / 8;
    public static final float UI_TOUCHPAD_Y = APP_HEIGTH / 3 ;
    public static final float UI_TOUCHPAD_WIDTH = 150;
    public static final float UI_TOUCHPAD_HEIGHT = 150;
    public static final float UI_TOUCHPAD_INTENSITY = 120;   //The lower the value the faster the joystick reacts
    public static final float UI_BUTTON_SHOOT_X = APP_WIDTH * 6 / 8;
    public static final float UI_BUTTON_SHOOT_Y = APP_HEIGTH / 3;
    public static final float UI_BUTTON_SHOOT_RADIUS = APP_WIDTH / 10;

    public static final String CHARACTERS_ATLAS_PATH = "characters.txt";
    public static final String[] RUNNER_RUNNING_REGION_NAMES = new String[] {"alienGreen_run1", "alienGreen_run2"};
    public static final String RUNNER_DODGING_REGION_NAME = "alienGreen_dodge";
    public static final String RUNNER_HIT_REGION_NAME = "alienGreen_hit";
    public static final String RUNNER_JUMPING_REGION_NAME = "alienGreen_jump";

    public static final String[] RUNNING_SMALL_ENEMY_REGION_NAMES = new String[] {"ladyBug_walk1", "ladyBug_walk2"};
    public static final String[] RUNNING_LONG_ENEMY_REGION_NAMES = new String[] {"barnacle_bite1", "barnacle_bite2"};
    public static final String[] RUNNING_BIG_ENEMY_REGION_NAMES = new String[] {"spider_walk1", "spider_walk2"};
    public static final String[] RUNNING_WIDE_ENEMY_REGION_NAMES = new String[] {"worm_walk1", "worm_walk2"};
    public static final String[] FLYING_SMALL_ENEMY_REGION_NAMES = new String[] {"bee_fly1", "bee_fly2"};
    public static final String[] FLYING_WIDE_ENEMY_REGION_NAMES = new String[] {"fly_fly1", "fly_fly2"};

}
