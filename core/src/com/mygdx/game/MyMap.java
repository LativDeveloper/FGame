package com.mygdx.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Виктор on 18.01.2017.
 */
public class MyMap {
    public class MyAnim{//экземпляр анимации .tmx
        int anumationcode;
        String[] frames;
        /*
        public class HFrame{//экземпляр фрейма анимации .tmx
            int tileid;
            int duration;
        }
        HFrame[]tiles;//набор фреймов для аниации с указанием их свойств
        *///не нужно
    }

    //хранит id, строки со сзанчениями свойтв, позиционные составляющие
    public class MyObject{//объекты карты P.S.потом id всех объектов надо перенумеровать
        /*
        public class MyProperty{
            Boolean bool;
            String color;
            String file;
            float floatValue;
            int intValue;
            String stringValue;
        }*///не нужно
        public int id;//id обекта, изменится на последней стадии постойки карты
        public float x, y, width, height;//позиционные составляющие
        String[] Property;//хранилище свойств объекта
    }

    public int[] layers;
    public MyObject[] objects;
    public MyAnim animation;
    public MyMap(String pathToDirectory){
        //считывает директорию с файлами и записывает данные об аниации, кодах тайлов, объектах на карте

    }
    /*
    public static class readMyFile{
        public static int[] readLayers(String path) throws FileNotFoundException {//считывает коды тайлов для каждой карты
            Scanner scanner = new Scanner(new File(path));
            int lines=Integer.parseInt(scanner.nextLine());
            String tmp=new String[lines];
            for(int i=0; i<lines;i++)
        }
        public static MyObject[] readObjects(String path) throws FileNotFoundException {//считвает экземпляры объектов для каждой карты
            Scanner scanner = new Scanner(new File(path));
        }
        public  static MyAnim[] readAnimation(String path) throws FileNotFoundException {//считывает экземпляры анимации для каждой карты
            Scanner scanner = new Scanner(new File(path));
        }
    }
    */
}