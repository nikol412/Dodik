package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Platforms2 {
    //16
    private static platform plats[];

    class platform {
        Vector2 position;

        public platform(Vector2 pos) {
            position = pos;
        }

        public void update() {
            position.x -= speed;
            if (position.x < -50) position.x = 800;
        }

    }

    //int speed;
    int count;
    private boolean stateOfObstacle;
    Texture imgPlatform;
    Texture imgPlatform1;
    boolean GameOver;
    Vector2 position;
    float speed;
    private Rectangle space;
    private Rectangle emptySpace;
    int StartX;
    int BtX;

    public Rectangle GetRec() {
        return space;
    }

    public Rectangle GetEmptySpace() {
        return emptySpace;
    }

    public Platforms2() {
        StartX = 0;
        BtX = 50;
        stateOfObstacle = false;
        plats = new platform[17];
        for (int i = 0; i < plats.length; i++) {
            plats[i] = new platform((new Vector2(i * 50, 0)));
        }
        //plats[0] = new platform(new Vector2(0, 0));
        //plats[1] = new platform(new Vector2(800, 0));
        speed = 3;
        imgPlatform = new Texture("blockY.png");
        imgPlatform1 = new Texture("blockB.png");
        count = 0;
        GameOver = false;
    }

    public int getCount() {
        return count;
    }

    public void SetSpeed(float s) {
        speed = s;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < plats.length; i++) {
            if (!stateOfObstacle)
                batch.draw(imgPlatform, plats[i].position.x, plats[i].position.y, 60, 50);
            else batch.draw(imgPlatform1, plats[i].position.x, plats[i].position.y, 60, 50);
        }
    }

    public void ChangeState() {
        stateOfObstacle = !stateOfObstacle;
    }

    public void update(boolean g, int s) {
        for (int i = 0; i < plats.length; i++) {
            plats[i].update();
        }
        SetSpeed(s);
        GameOver = g;
    }

    public void rest() {
        stateOfObstacle = false;
        for (int i = 0; i < plats.length; i++) {
            plats[i] = new platform((new Vector2(i * 50, 0)));
        }
        speed = 3;
        GameOver = false;
    }

    public Vector2 GetPos() {
        return position;
    }

    public void dispose() {
        imgPlatform.dispose();
    }
}

