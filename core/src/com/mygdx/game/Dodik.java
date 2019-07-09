package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.graphics.GL20.GL_DEPTH_BUFFER_BIT;

public class Dodik {

    private static final int FRAME_COLS = 2;
    private static final int FRAME_ROWS = 1;
    Animation<TextureRegion> walkAnimation;
    Texture walkSheet;
    TextureRegion[] walkFrames;
    TextureRegion currentFrame;
    float stateTime;

    private boolean stateOfMan;
    private Texture skinWhite;
    private Texture skinOrange;
    private Vector2 position;
    private double gravity;
    private double vy;
    private boolean isFalling;
    private Rectangle rec;
    boolean quickFalling;
    Texture jump;
    Texture falling;

    public Dodik() {
        walkSheet = new Texture("run.png");
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(0.075f, walkFrames);
        stateTime = 0f;
        jump = new Texture("jump.png");
        falling = new Texture("falling.png");
        quickFalling = false;
        stateOfMan = true;
        skinWhite = new Texture("GrayD.png");
        skinOrange = new Texture("orangeD.png");
        position = new Vector2(50, 200);
        vy = 0;
        gravity = -0.6f;
        isFalling = true;
        rec = new Rectangle(position.x, position.y, 50, 50);
    }

    public void SetX(int x) {
        position.x = x;
    }

    public Vector2 GetPos() {
        return position;
    }

    public Rectangle GetRec() {
        return rec;
    }

    public boolean IsFalling() {
        return isFalling;
    }

    public void ChangeFallingT() {
        isFalling = true;
    }

    public void ChangeFallingF() {
        isFalling = false;
        stateOfMan = !stateOfMan;
    }

    public void Jump() {
        vy = 9;
    }

    public void render(SpriteBatch batch) {
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // #14
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        if (IsFalling() && vy > 0) {
            batch.draw(jump, position.x, position.y, rec.width + 10, rec.height + 20);
        } else if (IsFalling() && vy < 0) {
            batch.draw(falling, position.x, position.y, rec.width + 10, rec.height + 20);
        } else batch.draw(currentFrame, position.x, position.y, rec.width + 10, rec.height + 20);


    }

    public boolean GetState() {
        return stateOfMan;
    }

    public void update(float p) {      //если просто бежит

        position.y = p;
        rec.y = position.y;
    }

    public void SetQuickFalling(boolean r) {
        quickFalling = r;
    }

    public void update() {       // если просто падает
        if (quickFalling) {
            vy = -35;

        } else if (Gdx.input.isKeyJustPressed(Input.Keys.W) | Gdx.input.isTouched()) vy = 7;
        vy += gravity;
        if (vy < -12 && !quickFalling) vy = -12;
        else if (vy < -40 && quickFalling) vy = -40;
        position.y += vy;
        if (position.y > 425) position.y = 425;
        rec.y = position.y;
    }

    public void rest() {
        stateOfMan = true;
        position = new Vector2(50, 200);
        isFalling = true;
        rec = new Rectangle(position.x, position.y, 50, 50);
    }

    public void resize() {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void dispose() {
        skinOrange.dispose();
        skinWhite.dispose();
    }
}
