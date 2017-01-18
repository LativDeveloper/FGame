package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Виктор on 09.01.2017.
 */
public class MyUtils {
    public static Animation getAnimation(Texture texture, float frametime, int x_imgs, int y_imgs, int line){
        TextureRegion[] animframes = new TextureRegion[y_imgs];
        TextureRegion[][] tmpFrames=TextureRegion.split(texture, texture.getWidth()/x_imgs, texture.getHeight()/y_imgs);
        int index=0;
            for(int j=0; j<y_imgs; j++)
                animframes[index++]=tmpFrames[line][j];
        return new Animation(frametime, animframes);
    }
}
