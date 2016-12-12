package com.runner.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.runner.utils.Constants;

/**
 * Created by bob on 12.12.16.
 */

public class Opponent extends GhostActor {

    public boolean jumping;
    public boolean dodging;
    public boolean hit;
    public Animation runningAnimation;
    public TextureRegion jumpingTexture;
    public TextureRegion dodgingTexture;
    public TextureRegion hitTexture;
    public float stateTime;
    public boolean facingLeft = false;

    public Opponent(){

        super();
        TextureAtlas textureAtlas = new TextureAtlas(Constants.CHARACTERS_ATLAS_PATH);
        TextureRegion[] runningFrames = new TextureRegion[Constants.RUNNER_RUNNING_REGION_NAMES.length];
        for(int i = 0; i < Constants.RUNNER_RUNNING_REGION_NAMES.length; i++){
            String path = Constants.RUNNER_RUNNING_REGION_NAMES[i];
            runningFrames[i] = textureAtlas.findRegion(path);
        }
        screenRectangle.width = Constants.RUNNER_WIDTH / Constants.WORLD_TO_SCREEN;
        screenRectangle.height = Constants.RUNNER_HEIGHT / Constants.WORLD_TO_SCREEN;
        runningAnimation = new Animation(0.25f, runningFrames);
        stateTime = 0f;
        jumpingTexture = textureAtlas.findRegion(Constants.RUNNER_JUMPING_REGION_NAME);
        dodgingTexture = textureAtlas.findRegion(Constants.RUNNER_DODGING_REGION_NAME);
        hitTexture = textureAtlas.findRegion(Constants.RUNNER_HIT_REGION_NAME);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        if(dodging){
            batch.draw(dodgingTexture, screenRectangle.x, screenRectangle.y + screenRectangle.height / 4, facingLeft ? -screenRectangle.width * 2 : screenRectangle.width * 2, screenRectangle.height * 6 / 4);
        }/*else if(hit){
            batch.draw(hitTexture, screenRectangle.x, screenRectangle.y, facingLeft ? -screenRectangle.width * 0.5f : screenRectangle.width * 0.5f, screenRectangle.height * 0.5f, screenRectangle.width, screenRectangle.height, 1f, 1f, (float)Math.toDegrees(body.getAngle()));
        }*/else if(jumping){
            batch.draw(jumpingTexture, facingLeft ? screenRectangle.x + screenRectangle.width * 3 / 2 : screenRectangle.x - screenRectangle.width / 2, screenRectangle.y - screenRectangle.height / 2, facingLeft ? -screenRectangle.width * 2 : screenRectangle.width * 2, screenRectangle.height * 2);
        }else{
            stateTime += Gdx.graphics.getDeltaTime();
            batch.draw(runningAnimation.getKeyFrame(stateTime, true), facingLeft ? screenRectangle.x + screenRectangle.width * 3 / 2 : screenRectangle.x - screenRectangle.width / 2, screenRectangle.y - screenRectangle.height / 2, facingLeft ? -screenRectangle.width * 2 : screenRectangle.width * 2, screenRectangle.height * 2);
        }
        System.out.println("DRAW");
    }

    public void updatePosition(float x, float y, int status) {
        screenRectangle.x = x;
        screenRectangle.y = y;
        //screenRectangle.width = 5;
        //screenRectangle.height = 5;
        //System.out.println("GHOST RECT x: "+screenRectangle.x+" y: "+screenRectangle.y);
        //setPosition(new Vector2(x, y));
    }
}
