package com.runner.behaviour;

import com.badlogic.gdx.math.Vector2;
import com.runner.utils.Constants;

/**
 * Created by bob on 03.12.16.
 */

public class EnemyBehaviour {

    public EnemyBehaviour(){
    }

    public Vector2 update(Vector2 velocity){
        //System.out.print(velocity+" --- ");
        if(velocity.x == 0){
            velocity.y = Constants.ENEMY_JUMP_LINEAR_VELOCITY;
        }
        return velocity;
    }
}
