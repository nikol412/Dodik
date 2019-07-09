package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;


public class MenuScreen implements Screen {

    SpriteBatch batch; // объект для отрисовки спрайтов нашей игры
    OrthographicCamera camera; // область просмотра нашей игры

    Texture startButtonTexture;
    Texture exitButtonTexture;
    Texture backGroundTexture;
    Sprite startButtonSprite;
    Sprite exitButtonSprite;
    Sprite backGroundSprite;

    private static float BUTTON_RESIZE_FACTOR = 800f; // задаём относительный размер
    private static float START_VERT_POSITION_FACTOR = 2.7f; // задаём позицию конпки start
    private static float EXIT_VERT_POSITION_FACTOR = 4.2f; // задаём позицию кнопки exit
    MainGame game; // экземпляр класса MainGame нужен для доступа к вызову метода setScreen
    Vector3 temp = new Vector3(); // временный вектор для "захвата" входных координат

    public MenuScreen(MainGame game) {
        this.game = game;

        // получаем размеры экрана устройства пользователя и записываем их в переменнные высоты и ширины
        float height = 480;
        float width = 800;
        // устанавливаем переменные высоты и ширины в качестве области просмотра нашей игры
        camera = new OrthographicCamera(width, height);
        // этим методом мы центруем камеру на половину высоты и половину ширины
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        // инициализируем текстуры и спрайты
        startButtonTexture = new Texture(Gdx.files.internal("start.png"));
        exitButtonTexture = new Texture(Gdx.files.internal("exit.png"));
        backGroundTexture = new Texture(Gdx.files.internal("block.png"));
        startButtonSprite = new Sprite(startButtonTexture);
        exitButtonSprite = new Sprite(exitButtonTexture);
        backGroundSprite = new Sprite(backGroundTexture);
        // устанавливаем размер и позиции
        startButtonSprite.setSize(550, 200);
        exitButtonSprite.setSize(400, 100);
        backGroundSprite.setSize(width, height);
        startButtonSprite.setPosition((width / 2f - startButtonSprite.getWidth() / 2), (height+100) / START_VERT_POSITION_FACTOR);
        exitButtonSprite.setPosition((width / 2f - exitButtonSprite.getWidth() / 2), (height-100) / EXIT_VERT_POSITION_FACTOR);
        // устанавливаем прозрачность заднего фона
        backGroundSprite.setAlpha(0.2f);
    }

    public void handleTouch() {
        // Проверяем были ли касание по экрану?
        if (Gdx.input.justTouched()) {
            // Получаем координаты касания и устанавливаем эти значения в временный вектор
            temp.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            // получаем координаты касания относительно области просмотра нашей камеры
            camera.unproject(temp);
            float touchX = temp.x;
            float touchY = temp.y;
            // обработка касания по кнопке Stare
            if ((touchX >= startButtonSprite.getX()) && touchX <= (startButtonSprite.getX() + startButtonSprite.getWidth()) && (touchY >= startButtonSprite.getY()) && touchY <= (startButtonSprite.getY() + startButtonSprite.getHeight())) {
                game.setScreen(new MyGdxGame()); // Переход к экрану игры
            }
            // обработка касания по кнопке Exit
            else if ((touchX >= exitButtonSprite.getX()) && touchX <= (exitButtonSprite.getX() + exitButtonSprite.getWidth()) && (touchY >= exitButtonSprite.getY()) && touchY <= (exitButtonSprite.getY() + exitButtonSprite.getHeight())) {
                Gdx.app.exit(); // выход из приложения
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);// устанавливаем в экземпляр spritebatch вид с камеры (области просмотра)

        //отрисовка игровых объектов
        batch.begin();
        backGroundSprite.draw(batch);
        startButtonSprite.draw(batch);
        exitButtonSprite.draw(batch);
        handleTouch();
        batch.end();
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
        startButtonTexture.dispose();
        exitButtonTexture.dispose();
        batch.dispose();
    }

}
