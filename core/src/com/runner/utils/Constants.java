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
    public static final short COLLISION_PROJECTILE_BITS = 16;
    public static final short COLLISION_HAZARDS_BITS = 32;
    public static final short COLLISION_MINE_BITS = 64;

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
    public static float PLAYER_STAMP = 0.89f;
    public static final float RUNNER_SPEED_MAX = 5f;    //The max speed of the player
    public static final float RUNNER_SPEED_STEP = 1f;   //The amount the players speed increases per button click
    public static final float RUNNER_JUMP_DELAY = 1/3f;
    public static float RUNNER_DENSITY = 0.5f;
    public static final float RUNNER_DODGE_X = 2f;
    public static final float RUNNER_DODGE_Y = 1.5f;
    public static final Vector2 RUNNER_JUMPING_LINEAR_IMPULSE = new Vector2(0, 17f);
    public static final Vector2 RUNNER_MOVE_RIGHT_LINEAR_IMPULSE = new Vector2(5f, 0f);
    public static final Vector2 RUNNER_MOVE_LEFT_LINEAR_IMPULSE = new Vector2(-5f, 0f);
    public static final float RUNNER_HIT_ANGULAR_IMPULSE = 10f;
    public static final float RUNNER_PROJECTILE_HIT_MULTI_X = 10000f;
    public static final float RUNNER_PROJECTILE_HIT_MULTI_Y = 20000f;

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
    public static float ENEMY_JUMP_LINEAR_VELOCITY = 13f;
    public static final Vector2 ENEMY_LINEAR_VELOCITY = new Vector2(-5f, 0);

    public static final int DEFAULT_SHOOT_MODE = 0;

    public static final String[] PROJECTILES_BUTTONS = new String[] {"ui/buttons/shootButton.png", "ui/buttons/bombButton.png", "ui/buttons/mineButton.png"};
    public static final String[] PROJECTILES_NAMES = new String[] {"BULLET", "BOMB", "MINE"};
    public static final Vector2 PROJECTILE_BULLET_LINEAR_VELOCITY_RIGHT = new Vector2(10f, 0);
    public static final Vector2 PROJECTILE_BULLET_LINEAR_VELOCITY_LEFT = new Vector2(-10f, 0);
    public static final float PROJECTILE_BULLET_DENSITY = 0.5f;
    public static final float PROJECTILE_BULLET_GRAVITY_SCALE = 1f;
    public static final float PROJECTILE_BULLET_Y = 1f;
    public static final float PROJECTILE_BULLET_X = 1f;
    public static final float PROJECTILE_BULLET_WIDTH = 20f;
    public static final float PROJECTILE_BULLET_HEIGHT = 5f;
    public static final Vector2 PROJECTILE_BOMB_LINEAR_VELOCITY_RIGHT = new Vector2(1f, 0f);
    public static final Vector2 PROJECTILE_BOMB_LINEAR_VELOCITY_LEFT = new Vector2(-3f, -3);
    public static final float PROJECTILE_BOMB_DENSITY = 0.5f;
    public static final float PROJECTILE_BOMB_GRAVITY_SCALE = 6f;
    public static final float PROJECTILE_BOMB_FORCE_X = 2000f;
    public static final float PROJECTILE_BOMB_FORCE_Y = 2000f;
    public static final float PROJECTILE_BOMB_Y = 1f;
    public static final float PROJECTILE_BOMB_X = 1f;
    public static final float PROJECTILE_BOMB_WIDTH = 20f;
    public static final float PROJECTILE_BOMB_HEIGHT = 20f;
    public static final float BOMB_MOVEMENT_DURATION = 1/3000f;
    public static final Vector2 PROJECTILE_MINE_LINEAR_VELOCITY_RIGHT = new Vector2(1f, 0f);
    public static final Vector2 PROJECTILE_MINE_LINEAR_VELOCITY_LEFT = new Vector2(-3f, -3);
    public static final float PROJECTILE_MINE_DENSITY = 0.5f;
    public static final float PROJECTILE_MINE_GRAVITY_SCALE = 6f;
    public static final float PROJECTILE_MINE_FORCE_X = 2000f;
    public static final float PROJECTILE_MINE_FORCE_Y = 2000f;
    public static final float PROJECTILE_MINE_Y = 1f;
    public static final float PROJECTILE_MINE_X = 1f;
    public static final float PROJECTILE_MINE_WIDTH = 20f;
    public static final float PROJECTILE_MINE_HEIGHT = 20f;
    public static final float MINE_MOVEMENT_DURATION = 1/3000f;
    public static final String PROJECTILES_ATLAS_PATH = "textures/characterProjectilesAtlas.txt";
    public static final String[] PROJECTILE_BULLET_REGION_NAMES = new String[] {"rocket_green1", "rocket_green2"};
    public static final String[] PROJECTILE_BOMB_REGION_NAMES = new String[] {"bomb_black1", "bomb_black2"};
    public static final String[] PROJECTILE_MINE_REGION_NAMES = new String[] {"mine"};

    public static final String LOGO_IMAGE_PATH = "screens/logo.png";
    public static final String MAINMENU_IMAGE_PATH = "screens/title.png";
    public static final String INTRO_IMAGE_PATH = "screens/intro.png";
    public static final String GAMEOVER_IMAGE_PATH = "screens/gameover.png";
    public static final String BACKGROUND_IMAGE_PATH = "background.png";
    public static final String GROUND_IMAGE_PATH = "ground.png";

    public static final String EXPLOSION_IMAGE_PATH = "explosion.png";
    public static final float EXPLOSION_WIDTH = APP_WIDTH / (30 * WORLD_TO_SCREEN);
    public static final float EXPLOSION_HEIGHT = APP_HEIGTH / (15f * WORLD_TO_SCREEN);
    public static final float EXPLOSION_TIME = 1/2f;

    public static final String UI_ATLAS_PATH = "ui/uiatlas.txt";
    public static final String UI_SKIN_PATH = "ui/uiskin.json";
    public static final String UI_TOUCHPAD_BACKGROUND_PATH = "ui/joystick/touchBackgroundTrans1.png";
    public static final String UI_TOUCHPAD_KNOB_PATH = "ui/joystick/touchKnobTrans1.png";
    public static final float UI_TOUCHPAD_WIDTH = 250;
    public static final float UI_TOUCHPAD_HEIGHT = 250;
    public static final float UI_TOUCHPAD_X = 0;
    public static final float UI_TOUCHPAD_Y = APP_HEIGTH / 2  ;
    public static final float UI_TOUCHPAD_INTENSITY = 120;   //The lower the value the faster the joystick reacts
    public static final float UI_BUTTON_SHOOT_RADIUS = APP_WIDTH / 10;
    public static final float UI_BUTTON_SHOOT_X = APP_WIDTH;
    public static final float UI_BUTTON_SHOOT_Y = APP_HEIGTH / 2;
    public static final float UI_BUTTON_SWITCH_RADIUS = APP_WIDTH / 5;
    public static final float UI_BUTTON_SWITCH_X = APP_WIDTH;
    public static final float UI_BUTTON_SWITCH_Y = APP_HEIGTH / 4;
    public static final float UI_TOUCHPAD_JUMP_SENSITY = 0.5f;  //0: Player always jumps; 1: Player jumps only when joystick is pointing exactly to top
    public static final float UI_TOUCHPAD_DODGE_SENSITY = -0.9f;     //0: Player always dodges; 1: Player dodges only when joystick is pointing exactly to bottom

    public static final float LEVEL_SELECT_PADDING_TOP = 10f;
    public static final float LEVEL_SELECT_PADDING_BOT = 10f;
    public static final float LEVEL_SELECT_PADDING_SIDE = 60f;
    public static final String[] LEVEL_SELECT_LEVEL_NAMES = new String[] {"Mario Bros lvl. 1", "level 2", "level 3", "level 4", "level 5", "level 6", "level 7", "level 8", "level 9"};
    public static final String[] LEVEL_SELECT_LEVEL_IMAGES_PATHS = new String[] {"level/ground.png", "level/farbkombi.JPG", "level/explosion.png", "level/ground.png", "level/farbkombi.JPG", "level/explosion.png", "level/ground.png", "level/farbkombi.JPG", "level/explosion.png"};

    public static final int TILED_LAYER_WALLS = 0;
    public static final int TILED_LAYER_HAZARDS = 1;

    public static final String CHARACTERS_ATLAS_PATH = "textures/characterProjectilesAtlas.txt";
    public static final String[] PLAYER_RUNNING_REGION_NAMES = new String[] {"alienGreen_run1", "alienGreen_run2"};
    public static final String PLAYER_DODGING_REGION_NAME = "alienGreen_dodge";
    public static final String PLAYER_HIT_REGION_NAME = "alienGreen_hit";
    public static final String PLAYER_JUMPING_REGION_NAME = "alienGreen_jump";
    public static final String[] RUNNER_RUNNING_REGION_NAMES = new String[] {"alienRed_run1", "alienRed_run2"};
    public static final String RUNNER_DODGING_REGION_NAME = "alienRed_dodge";
    public static final String RUNNER_HIT_REGION_NAME = "alienRed_hit";
    public static final String RUNNER_JUMPING_REGION_NAME = "alienRed_jump";

    public static final String[] RUNNING_SMALL_ENEMY_REGION_NAMES = new String[] {"ladyBug_walk1", "ladyBug_walk2"};
    public static final String[] RUNNING_LONG_ENEMY_REGION_NAMES = new String[] {"barnacle_bite1", "barnacle_bite2"};
    public static final String[] RUNNING_BIG_ENEMY_REGION_NAMES = new String[] {"spider_walk1", "spider_walk2"};
    public static final String[] RUNNING_WIDE_ENEMY_REGION_NAMES = new String[] {"worm_walk1", "worm_walk2"};
    public static final String[] FLYING_SMALL_ENEMY_REGION_NAMES = new String[] {"bee_fly1", "bee_fly2"};
    public static final String[] FLYING_WIDE_ENEMY_REGION_NAMES = new String[] {"fly_fly1", "fly_fly2"};

    //Multiplayer Constants
    public static final String TEST_USER_NAME = "testUser2";
    public static final int MULTIPLAYER_DODGING_CODE = 2;
    public static final int MULTIPLAYER_JUMPING_CODE = 1;
    public static final int MULTIPLAYER_RUNNING_CODE = 0;
}
