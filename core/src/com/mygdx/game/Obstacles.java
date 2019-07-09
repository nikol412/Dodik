package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Obstacles {
    private int count;
    int StartX;
    private Texture imgWhite;
    private Texture imgOrange;
    private static obstacle[] obs;
    private int BetweenDistance;
    int complexity;
    int speed;

    class obstacle {
        private boolean invisible;
        private boolean stateOfObstacle;
        Vector2 position;
        Rectangle rec;
        double r;

        public obstacle(Vector2 pos, int compl) {
            r = Math.random();
            invisible = false;
            if (r > 0.5) stateOfObstacle = true;
            else stateOfObstacle = false;
            position = pos;


            rec = new Rectangle(pos.x, pos.y, 50, 50);
        }

        public int GetSpeed() {
            return speed;
        }

        public void DeleteObstacle() {
            invisible = true;
        }

        public boolean IsInvisible() {
            return invisible;
        }

        public void SetInvisible(boolean s) {
            invisible = s;
        }

        public void update() {
            position.x -= speed;
            rec.x = position.x;
            rec.y = position.y;
            if (position.x < -50) {
                position.x = 800;
                position.y = (int) (Math.random() * 380 + 50);
                r = Math.random();
                if (invisible) invisible = false;
                if (r > 0.6) stateOfObstacle = true;
                else stateOfObstacle = false;
            }

        }

        public boolean GetState() {
            return stateOfObstacle;
        }
    }

    public Obstacles() {
        StartX = 300;
        BetweenDistance = 100;
        obs = new obstacle[7];
        count = 0;
        complexity = 1;
        speed = 3 * complexity;
        int temp;
        imgOrange = new Texture("blockB.png");
        imgWhite = new Texture("blockY.png");
        complexity = 2;
        for (int i = 0; i < obs.length; i++) {
            obs[i] = new obstacle(new Vector2(StartX, (int) (Math.random() * 380 + 50)), complexity);
            StartX += BetweenDistance;
        }
    }

    public int GetLength() {
        return obs.length;
    }

    public void update() {
        for (int i = 0; i < obs.length; i++) {
            obs[i].update();
        }
        if (count > complexity * 30) {
            AddComplexity();
            speed += 2;
        }
    }

    public boolean IsCollided(Rectangle rc, boolean state) {
        boolean answer = false;
        for (int i = 0; i < obs.length; i++) {
            if (Intersector.overlaps(rc, obs[i].rec) && !obs[i].IsInvisible()) {
                if (state == obs[i].stateOfObstacle) {
                    obs[i].SetInvisible(true);
                    count++;
                } else {
                    answer = true;
                }
            }
        }
        return answer;
    }

    public void AddComplexity() {
        complexity++;
    }

    public void SetComplexity(int c) {
        complexity = c;
        speed = 3 * complexity;

    }

    public String GetCount() {
        return Integer.toString(count);
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < obs.length; i++) {
            if (!obs[i].IsInvisible()) {
                if (obs[i].GetState()) {
                    batch.draw(imgWhite, obs[i].position.x, obs[i].position.y, 50, 50);
                } else {
                    batch.draw(imgOrange, obs[i].position.x, obs[i].position.y, 50, 50);
                }
            }
        }
    }

    public int getCompl() {
        return complexity;
    }

    public int getSpeed() {
        return speed;
    }

    public void rest() {
        SetComplexity(1);

        for (int i = 0; i < obs.length; i++) {
            obs[i] = new obstacle(new Vector2(StartX, (int) (Math.random() * 380 + 50)), complexity);
            StartX += BetweenDistance;
        }
        count = 0;
    }

    public void dispose() {
        imgOrange.dispose();
        imgWhite.dispose();

    }
}
