package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Виктор on 12.01.2017.
 */
public class MyPlayer extends Image {
    Sprite sprite= new Sprite(new Texture(Gdx.files.internal("NewSoldier.png")));

    Animation moveup;
    Animation moveleft;
    Animation movedown;
    Animation moveright;
    Animation current;
    float elapsedtime=0;

    Vector2 velocity= new Vector2();
    int[] moves=new int[5];
    float normalspeed=50;


    public MyPlayer(MapLayer collisionlayer){
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        setTouchable(Touchable.enabled);
        moveup= MyUtils.getAnimation(new Texture(Gdx.files.internal("soldier.png")),0.1f, 9, 4, 0);
        moveleft= MyUtils.getAnimation(new Texture(Gdx.files.internal("soldier.png")),0.1f, 9, 4, 1);
        movedown= MyUtils.getAnimation(new Texture(Gdx.files.internal("soldier.png")),0.1f, 9, 4, 2);
        moveright= MyUtils.getAnimation(new Texture(Gdx.files.internal("soldier.png")),0.1f, 9, 4, 3);
        current=moveleft;
        oldcord=new Vector2(getX(), getY());
        setCollisionLayer(collisionlayer);
        //current= (Animation) movedown.getKeyFrame(0f);
    }
    public void setAction(FGame.MyAction action){
        switch(action){
            case UP:
                moves[0]=1;
                break;
            case LEFT:
                moves[1]=-1;
                break;
            case DOWN:
                moves[2]=-1;
                break;
            case RIGHT:
                moves[3]=1;
                break;
            case JUMP:
                moves[4]=25;
                break;
            case CUP:
                moves[0]=0;
                break;
            case CLEFT:
                moves[1]=0;
                break;
            case CDOWN:
                moves[2]=0;
                break;
            case CRIGHT:
                moves[3]=0;
                break;
        }
        Rectangle r= sprite.getBoundingRectangle();
    }

    boolean move=false;
    FGame.MyAction direction= FGame.MyAction.DOWN;
    Vector2 oldcord;

    Rectangle[] objects;

    public void setCollisionLayer(MapLayer layer){
        MapObjects objects = layer.getObjects();
        this.objects= new Rectangle[objects.getCount()];
        int index=0;
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            this.objects[index++] = rectangleObject.getRectangle();
            /*if (Intersector.overlaps(rectangle, sprite.getBoundingRectangle())) {
                // collision happened
            }*/
        }
    }
    private void checkOnIntersection(){
        Rectangle bounds = sprite.getBoundingRectangle();
        //bounds.height/=2;
        for(Rectangle rectangle : objects)
            if (Intersector.overlaps(rectangle, bounds)) {
                setPosition(oldcord.x, oldcord.y);
                break;
            }
        oldcord=new Vector2(getX(), getY());
    }


    private void updatestate(float delta){
        MoveByAction mba = new MoveByAction();
        velocity=new Vector2(moves[1]+moves[3], moves[0]+moves[2]).nor();
        move=(moves[0]+moves[2]!=0)||(moves[1]+moves[3]!=0);
        if(moves[4]!=1){
            velocity.x*=moves[4];
            velocity.y*=moves[4];
            moves[4]=1;
            mba.setAmount(velocity.x, velocity.y);
            mba.setDuration(0.3f);}
        else{
            mba.setAmount(velocity.x, velocity.y);
            mba.setDuration(0.005f);
        }
        MyPlayer.this.addAction(mba);
        if(moves[0]+moves[2]>0) {current=moveup; direction = FGame.MyAction.UP;}
        else if(moves[0]+moves[2]<0) {current=movedown; direction= FGame.MyAction.DOWN;}
        else if(moves[1]+moves[3]>0) {current=moveright; direction = FGame.MyAction.RIGHT;}
        else if(moves[1]+moves[3]<0){current=moveleft; direction= FGame.MyAction.LEFT;}
        checkOnIntersection();
    }
    Vector2 size=new Vector2();
    public void setScreenSize(float width, float height){
        size.x=width;
        size.y=height;
    }
    Vector2 onscreen=new Vector2();
    public void setCordOnScreen(float x, float y){
        onscreen.x=x;
        onscreen.y=y;
    }

    @Override
    protected void positionChanged() {
        sprite.setPosition(getX(), getY());
        super.positionChanged();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {//можно добавить обработчик стоячей анимации
        TextureRegion currentFrame = (TextureRegion) current.getKeyFrame(elapsedtime, true);
        batch.draw(currentFrame,getX(), getY());
    }

    @Override
    public void act(float delta) {
        elapsedtime+=delta;
        updatestate(delta);
        super.act(delta);
    }
}
