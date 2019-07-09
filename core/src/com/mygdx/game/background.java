package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import sun.awt.image.PixelConverter;

public class background {
    private Texture tx;

    class BGPic {
        private Vector2 position;

        public BGPic(Vector2 pos) {
            tx = new Texture("backgroundEmpty.png");
            position = pos;
        }
    }

    private int speed;
    private BGPic[] backs;

    public background() {
        backs = new BGPic[2];
        backs[0] = new BGPic(new Vector2(0, 0));
        backs[1] = new BGPic(new Vector2(800, 0));
        speed = 3;
        Texture background = new Texture("backgroundEmpty.png");
    }

    public int GetSpeed() {
        return speed;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < backs.length; i++) {
            batch.draw(tx, backs[i].position.x, backs[i].position.y, 800, 480);
        }
    }

    public void update(int s) {
        speed = s;
        for (int i = 0; i < backs.length; i++) {
            backs[i].position.x -= speed;

        }

        if (backs[0].position.x < -800 || backs[1].position.x < -800) {
            backs[0].position.x = 0;
            backs[1].position.x = 800;
        }
    }

    public void rest() {
        backs[0] = new BGPic(new Vector2(0, 0));
        backs[1] = new BGPic(new Vector2(800, 0));
    }

    public void dispose() {
        tx.dispose();
    }
}
