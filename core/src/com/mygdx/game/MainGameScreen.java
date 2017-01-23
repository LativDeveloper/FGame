package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by Vetal on 19.01.2017.
 */
public class MainGameScreen implements Screen, InputProcessor {
    Stage stage;
    ScreenViewport viewport;
    MyPlayer player;
    Group group;
    TiledMap map;
    private Vector2 _deflection = new Vector2(0, 0);
    Vector2 dir = new Vector2(0, 0);
    Vector2 n = new Vector2(0, 0);
    private Label _fpsLabel;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera _camera;
    private Entity _player;
    private PlayerController _playerController;

    public MainGameScreen() {

        Utility.loadMapAsset("maps/room.tmx");
        map = Utility.getMapAsset("maps/room.tmx");

        _camera = new OrthographicCamera();
//        _camera.setToOrtho(false, 130, 1/16f);
        _camera.update();

        renderer = new OrthogonalTiledMapRenderer(map);
        renderer.setView(_camera);

//        Cursor cursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("cursor.png")), 32,32);
//        Gdx.graphics.setCursor(cursor);

        viewport = new ScreenViewport();
        stage = new Stage(viewport);
        player = new MyPlayer(map.getLayers().get(2));
        player.setName("me1");
        player.setPosition(100, 100);

        _player = new Entity();
        _playerController = new PlayerController(_player);

        _fpsLabel = new Label("fps: 0", new Label.LabelStyle(new BitmapFont(false), Color.ORANGE));

        group = new Group();
        group.addActor(player);
        group.setPosition(100f, 100f);
        stage.addActor(group);
        stage.addActor(_fpsLabel);
        //player.setCollisionLayer((TiledMapTileLayer) map.getLayers().get("collision"));
        /*Cursor customCursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("cursor.png")), 32, 32);
		Gdx.graphics.setCursor(customCursor);
		*/
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(_playerController);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        Vector2 view = stage.stageToScreenCoordinates(new Vector2(player.getX(), player.getY()));
//        player.setCordOnScreen(view.x, view.y);
        updateCam(Gdx.graphics.getDeltaTime());
        _player.update(delta);
        _camera.position.set(_player.getPosition().x, _player.getPosition().y, 0);
        _camera.update();

        renderer.setView(_camera);
        renderer.render();

        renderer.getBatch().begin();
        renderer.getBatch().draw(_player.getCurrentFrame(), _player.getPosition().x, _player.getPosition().y, 32, 32);
        renderer.getBatch().end();
        group.setPosition(_deflection.x - player.getX() + stage.getViewport().getScreenWidth() / 2,
                _deflection.y - player.getY() + stage.getViewport().getScreenHeight() / 2);
        _camera.position.set(player.getX()+ _deflection.x, player.getY()+ _deflection.y, 0);
        _fpsLabel.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        _camera.viewportWidth = width;
        _camera.viewportHeight = height;
        _camera.position.x = width / 2;
        _camera.position.y = height / 2;
        _camera.update();
        stage.getViewport().update(width, height, true);
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
    public boolean keyDown(int keycode) {
        int k = 2;
        switch (keycode) {
            case Input.Keys.W:
                player.setAction(MyAction.UP);
                break;
            case Input.Keys.A:
                player.setAction(MyAction.LEFT);
                break;
            case Input.Keys.S:
                player.setAction(MyAction.DOWN);
                break;
            case Input.Keys.D:
                player.setAction(MyAction.RIGHT);
                break;
            case Input.Keys.SPACE:
                player.setAction(MyAction.JUMP);
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                player.setAction(MyAction.CUP);
                break;
            case Input.Keys.A:
                player.setAction(MyAction.CLEFT);
                break;
            case Input.Keys.S:
                player.setAction(MyAction.CDOWN);
                break;
            case Input.Keys.D:
                player.setAction(MyAction.CRIGHT);
                break;
        }
        return true;
    }

    private void updateCam(float delta) {
        Vector2 p = new Vector2(group.getX(), group.getY());//указатель на начало карты
        Vector2 a = new Vector2(player.getX(), player.getY());//указатель на игрока относительно карты
        dir = new Vector2(p.x + a.x - n.x, p.y + a.y - n.y);
        _deflection.x = (dir.x * 10 - _deflection.x) * 0.01f;
        _deflection.y = (dir.y * 10 - _deflection.y) * 0.01f;
        _camera.position.set(player.getX() - _deflection.x, player.getY() - _deflection.y, 0);
        _camera.update();
        renderer.setView(_camera);
        renderer.render();

        renderer.getBatch().begin();
        renderer.getBatch().draw(_player.getCurrentFrame(), _player.getPosition().x, _player.getPosition().y, 1, 1);
        renderer.getBatch().end();
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
//        n = new Vector2(screenX, stage.getViewport().getScreenHeight() - screenY);//координаты курсора в координатах Gdx
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    private void updateView() {

    }

    @Override
    public void dispose() {
//        stage.dispose();
    }

    enum MyAction {UP, LEFT, DOWN, RIGHT, JUMP, CUP, CLEFT, CDOWN, CRIGHT}
}
