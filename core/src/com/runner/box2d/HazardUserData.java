package com.runner.box2d;

import com.runner.enums.UserDataType;

/**
 * Created by bob on 20.11.16.
 */

public class HazardUserData extends UserData {

    public HazardUserData(float width, float height){
        super(width, height);
        userDataType = UserDataType.HAZARD;
    }
}
