/**Класс для получения Bitmap'ов достижений*/
package com.example.blindchess.ui.graphics;

import android.content.Context;

import com.example.blindchess.R;


public class Achievements {

    private Context context; //Контекст приложения


    /**Конструктор класса
     * На вход принимает 1 параметр:
     * Context context - контекст приложения*/
    public Achievements (Context context) {
        this.context = context;
    }


    /**Функция получения R.drawable изображения данного достижения по заданному titleAchievement
     * На вход принимает 1 параметр:
     * String titleAchievement - название достижения
     * Функция возвращает int - int id drawable-изображения заданного достижения*/
    public int getBitmapAchievement(String titleAchievement) {
        switch (titleAchievement) {
            case "achievement_first_match":
                return R.drawable.achievement_first_match;
            case "achievement_first_win":
                return R.drawable.icon_achievements;
            default:
                return 0;
        }
    }

}
