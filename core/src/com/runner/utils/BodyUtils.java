package com.runner.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.runner.box2d.UserData;
import com.runner.enums.UserDataType;

/**
 * Created by bob on 20.11.16.
 */

public class BodyUtils {

    public static boolean bodyInBounds(Body body){
        UserData userData = (UserData)body.getUserData();

        switch (userData.getUserDataType()){
            case RUNNER:
                return body.getPosition().y > 0;
            case ENEMY:
                return body.getPosition().x + userData.getWidth() / 2 > 0;
        }
        return true;
    }

    public static boolean bodyIsEnemy(Body body){
        UserData userData = (UserData)body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.ENEMY;
    }

    public static boolean bodyIsRunner(Body body){
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.RUNNER;
    }

    public static boolean bodyIsGround(Body body){
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.GROUND;
    }
}
