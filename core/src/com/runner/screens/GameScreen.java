package com.runner.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.runner.stages.GameStage;
import com.runner.stages.HudStage;
import com.runner.utils.Constants;

/**
 * Created by bob on 20.11.16.
 */

public class GameScreen implements Screen {

    private static HudStage hudStage;
    public static GameStage gameStage;
    private static Game game;

    public GameScreen(Game game){
        gameStage = new GameStage();
        hudStage = new HudStage();

        this.game = game;

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameStage.draw();
        gameStage.act(delta);
        hudStage.draw();
        hudStage.act(delta);
    }

    static public void restartGame() {
        game.setScreen(new GameOverScreen(game));
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        gameStage.dispose();
        hudStage.dispose();
    }

    public static void setScore(int score) {
        hudStage.setScore(score);
    }
}
