package com.runner.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.runner.multiplayer.WarpController;
import com.runner.utils.Constants;

import org.json.JSONObject;

/**
 * Created by oCocha on 10.12.2016.
 */

public class Player extends Runner {

    public Player(Body body){

        super(body);

        TextureAtlas textureAtlas = new TextureAtlas(Constants.CHARACTERS_ATLAS_PATH);
        TextureRegion[] runningFrames = new TextureRegion[Constants.PLAYER_RUNNING_REGION_NAMES.length];
        for(int i = 0; i < Constants.PLAYER_RUNNING_REGION_NAMES.length; i++){
            String path = Constants.PLAYER_RUNNING_REGION_NAMES[i];
            runningFrames[i] = textureAtlas.findRegion(path);
        }
        runningAnimation = new Animation(0.1f, runningFrames);
        stateTime = 0f;
        jumpingTexture = textureAtlas.findRegion(Constants.PLAYER_JUMPING_REGION_NAME);
        dodgingTexture = textureAtlas.findRegion(Constants.PLAYER_DODGING_REGION_NAME);
        hitTexture = textureAtlas.findRegion(Constants.PLAYER_HIT_REGION_NAME);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        if(dodging){
            batch.draw(dodgingTexture, screenRectangle.x, screenRectangle.y + screenRectangle.height / 4, facingLeft ? -screenRectangle.width * 2 : screenRectangle.width * 2, screenRectangle.height * 6 / 4);
            sendLocation(screenRectangle.x, screenRectangle.y, Constants.MULTIPLAYER_DODGING_CODE);
        }else if(hit){
            batch.draw(hitTexture, screenRectangle.x, screenRectangle.y, facingLeft ? -screenRectangle.width * 0.5f : screenRectangle.width * 0.5f, screenRectangle.height * 0.5f, screenRectangle.width, screenRectangle.height, 1f, 1f, (float)Math.toDegrees(body.getAngle()));
        }else if(jumping){
            batch.draw(jumpingTexture, facingLeft ? screenRectangle.x + screenRectangle.width * 3 / 2 : screenRectangle.x - screenRectangle.width / 2, screenRectangle.y - screenRectangle.height / 2, facingLeft ? -screenRectangle.width * 2 : screenRectangle.width * 2, screenRectangle.height * 2);
            sendLocation(screenRectangle.x, screenRectangle.y, Constants.MULTIPLAYER_JUMPING_CODE);
        }else{
            stateTime += Gdx.graphics.getDeltaTime();
            batch.draw(runningAnimation.getKeyFrame(stateTime, true), facingLeft ? screenRectangle.x + screenRectangle.width * 3 / 2 : screenRectangle.x - screenRectangle.width / 2, screenRectangle.y - screenRectangle.height / 2, facingLeft ? -screenRectangle.width * 2 : screenRectangle.width * 2, screenRectangle.height * 2);
            sendLocation(screenRectangle.x, screenRectangle.y, Constants.MULTIPLAYER_RUNNING_CODE);
        }
    }

    private void sendLocation(float x, float y, int status) {
        try {
            JSONObject data = new JSONObject();
            data.put("x", x);
            data.put("y", y);
            data.put("status", status);
            WarpController.getInstance().sendGameUpdate(data.toString());
            //System.out.print("SendLocation success: x: "+x+" y: "+y+" status: "+status);
        } catch (Exception e) {
            // exception in sendLocation
            System.out.print("SendLoccatipn error: "+e);
        }
    }
}
