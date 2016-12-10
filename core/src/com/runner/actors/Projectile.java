package com.runner.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.runner.box2d.ProjectileUserData;
import com.runner.box2d.UserData;
import com.runner.utils.Constants;

/**
 * Created by oCocha on 03.12.2016.
 */

public class Projectile extends GameActor {

    private Animation animation;
    private float stateTime;

    private boolean facingLeft;

    private ProjectileUserData projectileUserData;
    private float accumulator;

    public Projectile(Body body, boolean facingLeft, ProjectileUserData projectileUserData){
        super(body);

        this.projectileUserData = projectileUserData;
        TextureAtlas textureAtlas = new TextureAtlas(Constants.CHARACTERS_ATLAS_PATH);
        TextureRegion[] runningFrames = new TextureRegion[getUserData().getTextureRegions().length];
        for(int i = 0; i < getUserData().getTextureRegions().length; i++){
            String path = getUserData().getTextureRegions()[i];
            runningFrames[i] = textureAtlas.findRegion(path);
        }
        animation = new Animation(0.1f, runningFrames);
        stateTime = 0f;

        this.facingLeft = facingLeft;
    }

    @Override
    public ProjectileUserData getUserData() {
        return projectileUserData;
    }

    @Override
    public void act(float delta){
        super.act(delta);

        /**Handle movement for each kind of projectile*/
        switch (projectileUserData.getProjectileMode()){
            case 0:
                if(facingLeft == true){
                 Vector2 tempVelocity = new Vector2(-projectileUserData.getLinearVelocity().x, projectileUserData.getLinearVelocity().y);
                 body.setLinearVelocity(tempVelocity);
                 }
                 else{
                 body.setLinearVelocity(projectileUserData.getLinearVelocity());
                 }
                break;
            case 1:
                if(facingLeft == true){
                    while (accumulator < Constants.BOMB_MOVEMENT_DURATION) {
                        accumulator += delta;
                        body.applyForceToCenter(-Constants.PROJECTILE_BOMB_FORCE_X, Constants.PROJECTILE_BOMB_FORCE_Y,true);
                    }
                }
                else{
                    while (accumulator < Constants.BOMB_MOVEMENT_DURATION) {
                        accumulator += delta;
                        body.applyForceToCenter(Constants.PROJECTILE_BOMB_FORCE_X, Constants.PROJECTILE_BOMB_FORCE_Y,true);
                    }                }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        stateTime += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(stateTime, true), (screenRectangle.x - (screenRectangle.width * 0.1f)), screenRectangle.y, screenRectangle.width * 1.2f, screenRectangle.height * 1.1f);
    }
}
