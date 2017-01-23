package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by vetal on 23.01.17.
 */
public class Entity {
    private static final String TAG = Entity.class.getName();
    private static final String DEFAULT_SPRITE_PATH = "player.png";
    private static final float FRAME_DURATION = 0.25f;

    private final int FRAME_WIDTH = 16;
    private final int FRAME_HEIGHT = 16;

    private float _velocity;
    private Vector2 _position;

    private Direction _direction;
    private State _state;

    private Array<TextureRegion> _walkLeftFrames;
    private Array<TextureRegion> _walkUpFrames;
    private Array<TextureRegion> _walkRightFrames;
    private Array<TextureRegion> _walkDownFrames;

    private Animation _walkLeftAnimation;
    private Animation _walkUpAnimation;
    private Animation _walkRightAnimation;
    private Animation _walkDownAnimation;

    private TextureRegion _currentFrame;
    private float _frameTime;
    private Rectangle _boundingBox;

    public enum State {
        IDLE, WALKING
    }

    public enum Direction {
        LEFT, LEFT_UP, UP, RIGHT_UP, RIGHT, RIGHT_DOWN, DOWN, LEFT_DOWN
    }

    public Entity() {
        _velocity = 2;
        _position = new Vector2();

        _direction = Direction.DOWN;
        _state = State.IDLE;

        Utility.loadTextureAsset(DEFAULT_SPRITE_PATH);
        Texture texture = Utility.getTextureAsset(DEFAULT_SPRITE_PATH);
        TextureRegion[][] framesTexture = TextureRegion.split(texture, FRAME_WIDTH, FRAME_HEIGHT);

        _walkLeftFrames = new Array<TextureRegion>();
        _walkUpFrames = new Array<TextureRegion>();
        _walkRightFrames = new Array<TextureRegion>();
        _walkDownFrames = new Array<TextureRegion>();

        for (int i = 0; i < framesTexture.length; i++)
            for (int j = 0; j < framesTexture[i].length; j++)
                switch (i) {
                    case 0:
                        _walkDownFrames.insert(j, framesTexture[i][j]);
                        break;
                    case 1:
                        _walkLeftFrames.insert(j, framesTexture[i][j]);
                        break;
                    case 2:
                        _walkRightFrames.insert(j, framesTexture[i][j]);
                        break;
                    case 3:
                        _walkUpFrames.insert(j, framesTexture[i][j]);
                        break;
                }

        _walkLeftAnimation = new Animation(FRAME_DURATION, _walkLeftFrames, Animation.PlayMode.LOOP);
        _walkUpAnimation = new Animation(FRAME_DURATION, _walkUpFrames, Animation.PlayMode.LOOP);
        _walkRightAnimation = new Animation(FRAME_DURATION, _walkRightFrames, Animation.PlayMode.LOOP);
        _walkDownAnimation = new Animation(FRAME_DURATION, _walkDownFrames, Animation.PlayMode.LOOP);

        _currentFrame = _walkDownFrames.get(0);
        _frameTime = 0;
        _boundingBox = new Rectangle();
    }

    public void update(float delta) {
        if (_state == State.IDLE) return;
        _frameTime += delta;
        switch (_direction) {
            case LEFT:
                _currentFrame = (TextureRegion) _walkLeftAnimation.getKeyFrame(_frameTime);
                _position.x -= _velocity;
                break;
            case LEFT_UP:
                _currentFrame = (TextureRegion) _walkUpAnimation.getKeyFrame(_frameTime);
                _position.x -= _velocity;
                _position.y += _velocity;
                break;
            case UP:
                _currentFrame = (TextureRegion) _walkUpAnimation.getKeyFrame(_frameTime);
                _position.y += _velocity;
                break;
            case RIGHT_UP:
                _currentFrame = (TextureRegion) _walkUpAnimation.getKeyFrame(_frameTime);
                _position.x += _velocity;
                _position.y += _velocity;
                break;
            case RIGHT:
                _currentFrame = (TextureRegion) _walkRightAnimation.getKeyFrame(_frameTime);
                _position.x += _velocity;
                break;
            case RIGHT_DOWN:
                _currentFrame = (TextureRegion) _walkRightAnimation.getKeyFrame(_frameTime, true);
                _position.x += _velocity;
                _position.y -= _velocity;
                break;
            case DOWN:
                _currentFrame = (TextureRegion) _walkDownAnimation.getKeyFrame(_frameTime);
                _position.y -= _velocity;
                break;
            case LEFT_DOWN:
                _currentFrame = (TextureRegion) _walkDownAnimation.getKeyFrame(_frameTime);
                _position.x -= _velocity;
                _position.y -= _velocity;
                break;
        }
    }

    public TextureRegion getCurrentFrame() {
        return _currentFrame;
    }

    public Vector2 getPosition() {
        return _position;
    }

    public Direction getDirection() {
        return _direction;
    }

    public void setDirection(Direction direction) {
        _direction = direction;
    }

    public State getState() {
        return _state;
    }

    public void setState(State state) {
        _state = state;
    }

    public void dispose() {
        Utility.unloadAsset(DEFAULT_SPRITE_PATH);
    }
}
