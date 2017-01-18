package com.mygdx.game;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.Arrays;

/**
 * Created by Виктор on 18.01.2017.
 */

public class MapEdit {
    public void CreateMap(MyMap[] maps){
        int[][][]v=new int[maps.length][][];
        for(int i=0; i<v.length; i++)
            v[i]=getIntegerView(maps[i]);




    }
    private int[][] getIntegerView(MyMap map){//переводит карту в интовую версию
        return null;
    }

    private int[][] resizeAndSetInCenter(int[][]array, int lines, int columns){
        int[][]a=initArray(array.length+lines, array[0].length+columns);
        int fline=lines/2, fcolm=columns/2;
        for(int i=0; i<array.length; i++)
            for(int j=0; j<array[0].length; j++)
                a[i+fline][j+fcolm]=array[i][j];
        return a;
    }

    private int[][] getNoneEmptyMap(int[][]array){//делает обрезку массива по инициализированной части карты
        int fline=0, fcolm=0, lline=0, lcolm=0;
        for(int i=0; i<array.length; i++)//верхняя граница
            for(int j=0; j<array[0].length; j++)
                if(array[i][j]!=0)
                {
                    fline=i;
                    break;
                }
        for(int j=0; j<array[0].length; j++)//левая граница
            for(int i=0; i<array.length; i++)
                if(array[i][j]!=0)
                {
                    fcolm=j;
                    break;
                }
        for(int i=array.length-1; i>=0; i--)//нижняя граница
            for(int j=0; j<array[0].length; j++)
                if(array[i][j]!=0)
                {
                    lline=i;
                    break;
                }
        for(int j=array[0].length-1; j>=0; j--)//правая граница
            for(int i=0; i<array.length; i++)
            if(array[i][j]!=0)
            {
                lcolm=j;
                break;
            }
        int[][]a=initArray(lline-fline, lcolm-fcolm);
        for(int i=0; i<a.length; i++)//выносит в новый массив выбранный кусок инициализированной карты
            for(int j=0; j<a[0].length;j++)
                a[i][j]=array[i+fline][j+fcolm];
        return a;
    }

    private int[][] initArray(int num_of_lines, int num_of_columns){
        int[][]array=new int[num_of_lines][num_of_columns];
        for(int i=0; i<num_of_lines; i++)
            for(int j=0; j<num_of_columns; j++)
                array[i][j]=0;
        return array;
    }
}
