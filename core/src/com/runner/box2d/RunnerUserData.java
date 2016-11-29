package com.runner.box2d;

import com.badlogic.gdx.math.Vector2;
import com.runner.enums.UserDataType;
import com.runner.utils.Constants;

import javax.xml.bind.ValidationException;

/**
 * Created by bob on 20.11.16.
 */

public class RunnerUserData extends UserData{

    private final Vector2 runningPosition = new Vector2(Constants.RUNNER_X, Constants.RUNNER_Y);
    private final Vector2 dodgePosition = new Vector2(Constants.RUNNER_DODGE_X, Constants.RUNNER_DODGE_Y);
    private Vector2 jumpingLinearImpulse;

    public RunnerUserData(float width, float height){
        super(width, height);
        jumpingLinearImpulse = Constants.RUNNER_JUMPING_LINEAR_IMPULSE;
        userDataType = UserDataType.RUNNER;
    }

    public Vector2 getJumpingLinearImpulse(){
        return jumpingLinearImpulse;
    }

    public void setJumpingLinearImpulse(Vector2 jumpingLinearImpulse){
        this.jumpingLinearImpulse = jumpingLinearImpulse;
    }

    public float getDodgeAngle(){
        return (float) (-90f * (Math.PI / 180f));
    }

    public Vector2 getRunningPosition(){
        return runningPosition;
    }

    public Vector2 getDodgePosition(){
        return dodgePosition;
    }

    public float getHitAngularImpulse(){
        return Constants.RUNNER_HIT_ANGULAR_IMPULSE;
    }

    public Vector2 getMoveRightLinearImpulse() {
        return Constants.RUNNER_MOVE_RIGHT_LINEAR_IMPULSE;
    }

    public Vector2 getMoveLeftLinearImpulse() {
        return Constants.RUNNER_MOVE_LEFT_LINEAR_IMPULSE;
    }
}
