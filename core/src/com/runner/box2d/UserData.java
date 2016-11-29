package com.runner.box2d;

import com.runner.enums.UserDataType;

/**
 * Created by bob on 20.11.16.
 */

public abstract class UserData {
    protected UserDataType userDataType;
    protected float width;
    protected float height;

    public UserData(){

    }

    public UserData(float width, float height){
        this.width = width;
        this.height = height;
    }

    public float getWidth(){
        return width;
    }

    public void setWidth(float width){
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public UserDataType getUserDataType(){
        return userDataType;
    }
}
