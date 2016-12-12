package com.runner.actors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by bob on 12.12.16.
 */

public class GhostActor extends Actor {

    protected Rectangle screenRectangle;

    public GhostActor(){
        screenRectangle = new Rectangle();
    }

    public Vector2 getPosition(){
        return new Vector2(screenRectangle.x, screenRectangle.y);
    }

    public void setX(int xValue){
        screenRectangle.x = xValue;
    }

    public void setY(int yValue){
        screenRectangle.y = yValue;
    }

    public void setPosition(Vector2 position){
        screenRectangle.x = position.x;
        screenRectangle.y = position.y;
        System.out.println("GHOST RECT x: "+screenRectangle.x+" y: "+screenRectangle.y);
    }
}
