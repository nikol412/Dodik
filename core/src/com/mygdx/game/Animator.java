package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.graphics.GL20.GL_DEPTH_BUFFER_BIT;

public class Animator {

    private static final int FRAME_COLS = 5; // #1
    private static final int FRAME_ROWS = 1; // #2

    Animation walkAnimation; // #3
    Texture walkSheet; // #4
    TextureRegion[] walkFrames; // #5
    SpriteBatch spriteBatch; // #6
    TextureRegion currentFrame; // #7
    int X;
    int Y;
    float stateTime; // #8

    public void create() {
        walkSheet = new Texture(Gdx.files.internal("anim.png")); // #9
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS); // #10
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        X = 0;
        Y = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(0.025f, walkFrames); // #11
        spriteBatch = new SpriteBatch(); // #12
        stateTime = 0f; // #13
    }

    public void render(SpriteBatch batch, Vector2 pos) {
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // #14
        stateTime += Gdx.graphics.getDeltaTime(); // #15

        currentFrame = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true); // #16

        batch.draw(currentFrame, pos.x, pos.y); // #17

    }

    public void dispose() {

    }
}