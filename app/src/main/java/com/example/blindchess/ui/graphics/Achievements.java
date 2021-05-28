/**Класс для получения Bitmap'ов достижений*/
package com.example.blindchess.ui.graphics;

import android.content.Context;
import android.graphics.Bitmap;


public class Achievements {

    private Context context; //Контекст приложения


    /**Конструктор класса
     * На вход принимает 1 параметр:
     * Context context - контекст приложения*/
    public Achievements (Context context) {
        this.context = context;
    }


    /**Функция получения Bitmap данного достижения по заданному titleAchievement
     * На вход принимает 1 параметр:
     * String titleAchievement - название достижения
     * Функция возвращает Bitmap - Bitmap заданного достижения*/
    public Bitmap getBitmapAchievement(String titleAchievement) {
        switch (titleAchievement) {
            default:
                return null;
        }
    }

}
