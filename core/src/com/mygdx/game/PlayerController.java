package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import java.util.HashMap;

/**
 * Created by vetal on 23.01.17.
 */
public class PlayerController implements InputProcessor {
    private HashMap<Keys, Boolean> _keys;
    private Entity _player;

    private enum Keys {
        LEFT, UP, RIGHT, DOWN
    }

    public PlayerController(Entity player) {
        _keys = new HashMap<Keys, Boolean>();
        _player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {

            if (_player.getState() == Entity.State.IDLE) {
                _player.setDirection(Entity.Direction.LEFT);
                _player.setState(Entity.State.WALKING);
            }
            else
                if (_player.getDirection() == Entity.Direction.UP)
                    _player.setDirection(Entity.Direction.LEFT_UP);
                else
                    if (_player.getDirection() == Entity.Direction.DOWN)
                        _player.setDirection(Entity.Direction.LEFT_DOWN);
                    else
                        _player.setDirection(Entity.Direction.LEFT);

        }

        if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {

            if (_player.getState() == Entity.State.IDLE) {
                _player.setDirection(Entity.Direction.UP);
                _player.setState(Entity.State.WALKING);
            }
            else
                if (_player.getDirection() == Entity.Direction.LEFT)
                    _player.setDirection(Entity.Direction.LEFT_UP);
                else
                    if (_player.getDirection() == Entity.Direction.RIGHT)
                        _player.setDirection(Entity.Direction.RIGHT_UP);
                    else
                        _player.setDirection(Entity.Direction.UP);
        }

        if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {

            if (_player.getState() == Entity.State.IDLE) {
                _player.setDirection(Entity.Direction.RIGHT);
                _player.setState(Entity.State.WALKING);
            }
            else
                if (_player.getDirection() == Entity.Direction.UP)
                    _player.setDirection(Entity.Direction.RIGHT_UP);
                else
                    if (_player.getDirection() == Entity.Direction.DOWN)
                        _player.setDirection(Entity.Direction.RIGHT_DOWN);
                    else
                        _player.setDirection(Entity.Direction.RIGHT);
        }

        if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {

            if (_player.getState() == Entity.State.IDLE) {
                _player.setDirection(Entity.Direction.DOWN);
                _player.setState(Entity.State.WALKING);
            }
            else
                if (_player.getDirection() == Entity.Direction.LEFT)
                    _player.setDirection(Entity.Direction.LEFT_DOWN);
                else
                    if (_player.getDirection() == Entity.Direction.RIGHT)
                        _player.setDirection(Entity.Direction.RIGHT_DOWN);
                    else
                        _player.setDirection(Entity.Direction.DOWN);
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
            if (_player.getDirection() == Entity.Direction.LEFT_UP) _player.setDirection(Entity.Direction.UP);
            else if (_player.getDirection() == Entity.Direction.LEFT_DOWN) _player.setDirection(Entity.Direction.DOWN);
            else _player.setState(Entity.State.IDLE);
        }

        if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
            if (_player.getDirection() == Entity.Direction.LEFT_UP) _player.setDirection(Entity.Direction.LEFT);
            else if (_player.getDirection() == Entity.Direction.RIGHT_UP) _player.setDirection(Entity.Direction.RIGHT);
            else _player.setState(Entity.State.IDLE);
        }

        if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
            if (_player.getDirection() == Entity.Direction.RIGHT_UP) _player.setDirection(Entity.Direction.UP);
            else if (_player.getDirection() == Entity.Direction.RIGHT_DOWN) _player.setDirection(Entity.Direction.DOWN);
            else _player.setState(Entity.State.IDLE);
        }

        if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
            if (_player.getDirection() == Entity.Direction.LEFT_DOWN) _player.setDirection(Entity.Direction.LEFT);
            else if (_player.getDirection() == Entity.Direction.RIGHT_DOWN) _player.setDirection(Entity.Direction.RIGHT);
            else _player.setState(Entity.State.IDLE);
        }

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
