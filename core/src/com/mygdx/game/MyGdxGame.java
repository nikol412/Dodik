package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame implements Screen {
    private BitmapFont FontMaroon3;
    BitmapFont font;
    SpriteBatch batch;
    Texture imgGO;
    OrthographicCamera camera;
    background BG;
    Dodik man;
    Platforms2 plat;
    Obstacles obstcls;

    boolean GameOver;

    public MyGdxGame() {
        GameOver = false;

        imgGO = new Texture("Orange.png");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        BG = new background();
        man = new Dodik();
        plat = new Platforms2();
        obstcls = new Obstacles();

        font = new BitmapFont();
        font.setColor(Color.BLACK);
        FontMaroon3 = new BitmapFont();
        FontMaroon3.setColor(Color.WHITE); // White
    }

    @Override
    public void render(float delta) {
        batch.begin();
        if (!GameOver) {
            update();
            camera.update();

            batch.setProjectionMatrix(camera.combined);
            BG.render(batch);
            man.render(batch);
            plat.render(batch);
            obstcls.render(batch);
            FontMaroon3.draw(batch, obstcls.GetCount(), 10, 460);
        } else {

            batch.draw(imgGO, 0, 0, 800, 480);
            FontMaroon3.draw(batch, obstcls.GetCount(), 400, 240);
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) | Gdx.input.isTouched()) {
                GameOver = false;
                obstcls.rest();
                plat.rest();
                BG.rest();
                man.rest();
            }
        }
        batch.end();
    }

    public void update() {

        BG.update(obstcls.getSpeed());
        obstcls.update();
        plat.update(false, obstcls.getSpeed()); //Основная платформа
        GameOver = obstcls.IsCollided(man.GetRec(), man.GetState());

        if (man.IsFalling() && man.GetPos().y > 50) {        // продолжает падать
            man.update();
        }

        if (man.IsFalling() && man.GetPos().y <= 50) {       // в этот момент он перестает падать
            man.ChangeFallingF();
            plat.ChangeState();

        }

        if (man.IsFalling() && ((Gdx.input.isKeyJustPressed(Input.Keys.S)) || Gdx.input.isTouched() && Gdx.input.getDeltaY() > 0)) {      // ускоряется в падении
            man.SetQuickFalling(true);
        }
        if (!man.IsFalling()) {       // если нажали кнопку, то прыгаем, если нет, то нет
            man.SetQuickFalling(false);
            if ((Gdx.input.isKeyJustPressed(Input.Keys.W) | Gdx.input.isTouched())) {
                man.ChangeFallingT();
                man.update();

            } else {
                man.update(50);
            }


        }

    }

    @Override
    public void show() {
        new MainGame();
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

    }

    @Override
    public void dispose() {
        batch.dispose();
        man.dispose();
        BG.dispose();
        plat.dispose();
        obstcls.dispose();
        imgGO.dispose();
    }
}
