package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.File;
import java.io.IOException;

public class FGame extends ApplicationAdapter implements InputProcessor{
	Stage stage;
	ScreenViewport viewport;
	MyPlayer player;
	Group group;

	TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;


	@Override
	public void create () {
		Gdx.input.setInputProcessor(this);
		File f = new File("cat.txt");
		File newmap= new File("cat.tmx");
		f.renameTo(newmap);
		map= new TmxMapLoader().load("map.tmx");
		renderer= new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30, 20);

		viewport= new ScreenViewport();
		stage= new Stage(viewport);
		player= new MyPlayer(map.getLayers().get(2));
		player.setName("me1");
		player.setPosition(100, 100);
		group = new Group();
		group.addActor(player);
		group.setPosition(100f, 100f);
		stage.addActor(group);
		//player.setCollisionLayer((TiledMapTileLayer) map.getLayers().get("collision"));
		/*Cursor customCursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("cursor.png")), 32, 32);
		Gdx.graphics.setCursor(customCursor);
		*/
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.position.x=width/2;
		camera.position.y=height/2;
		camera.update();
		stage.getViewport().update(width, height, true);
	}


	enum MyAction { UP, LEFT, DOWN, RIGHT, JUMP, CUP, CLEFT, CDOWN, CRIGHT}

	@Override
	public boolean keyDown(int keycode) {
		int k= 2;
		switch (keycode){
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
		switch (keycode){
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


	Vector2 deflection = new Vector2(0, 0);
	Vector2 dir=new Vector2(0, 0);
	Vector2 n=new Vector2(0, 0);
	private void updateCam(float delta){
		Vector2 p= new Vector2(group.getX(), group.getY());//указатель на начало карты
		Vector2 a= new Vector2(player.getX(), player.getY());//указатель на игрока относительно карты
		dir=new Vector2(p.x+a.x-n.x, p.y+a.y-n.y);
		deflection.x=(dir.x*10-deflection.x)*0.01f;
		deflection.y=(dir.y*10-deflection.y)*0.01f;
		camera.position.set(player.getX()-deflection.x, player.getY()-deflection.y, 0);
		camera.update();
		renderer.setView(camera);
 		renderer.render();
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		n=new Vector2(screenX, stage.getViewport().getScreenHeight()-screenY);//координаты курсора в координатах Gdx
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
	private void updateView(){

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Vector2 view= stage.stageToScreenCoordinates(new Vector2(player.getX(), player.getY()));
		player.setCordOnScreen(view.x, view.y);
		updateCam(Gdx.graphics.getDeltaTime());
		group.setPosition(deflection.x-player.getX() +stage.getViewport().getScreenWidth()/2,
				deflection.y-player.getY()+stage.getViewport().getScreenHeight()/2);
		//camera.position.set(player.getX()+deflection.x, player.getY()+deflection.y, 0);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	@Override
	public void dispose () {
		stage.dispose();
	}



}
