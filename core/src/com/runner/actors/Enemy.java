package com.runner.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.runner.behaviour.EnemyBehaviour;
import com.runner.box2d.EnemyUserData;
import com.runner.utils.Constants;

/**
 * Created by bob on 21.11.16.
 */

public class Enemy extends GameActor {

    private Animation animation;
    private float stateTime;

    private EnemyBehaviour enemyBehaviour;

    public Enemy(Body body){

        super(body, "PLACEHOLDER_EBENY_NAME");
        TextureAtlas textureAtlas = new TextureAtlas(Constants.CHARACTERS_ATLAS_PATH);
        TextureRegion[] runningFrames = new TextureRegion[getUserData().getTextureRegions().length];
        for(int i = 0; i < getUserData().getTextureRegions().length; i++){
            String path = getUserData().getTextureRegions()[i];
            runningFrames[i] = textureAtlas.findRegion(path);
        }
        animation = new Animation(0.1f, runningFrames);
        stateTime = 0f;
        enemyBehaviour = new EnemyBehaviour();
    }

    @Override
    public EnemyUserData getUserData(){
        return(EnemyUserData) userData;
    }

    @Override
    public void act(float delta){
        super.act(delta);
        body.setLinearVelocity(getUserData().getLinearVelocity());

        //body.setLinearVelocity(enemyBehaviour.update(body.getLinearVelocity()));

    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        stateTime += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(stateTime, true), (screenRectangle.x - (screenRectangle.width * 0.1f)), screenRectangle.y, screenRectangle.width * 1.2f, screenRectangle.height * 1.1f);
    }
}
