/**Класс с константными именами drawable изображений*/
package com.example.blindchess.constants;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.blindchess.R;


public class ConstantsImageView {


    /**Функция получения R.drawable определенного заднего фона по заданному nameBackground
     * На вход принимает 1 параметр:
     * String nameBackground - название заднего фона*/
    public static int getBitmapBackground(String nameBackground) {
        switch (nameBackground) {
            case "background_board":
                return R.drawable.background_board;
            default:
                return 0;
        }
    }


    /**Функция получения R.drawable определенного переднего плана по заданному nameForeground
     * На вход принимает 1 параметр:
     * String nameForeground - название переднего плана*/
    public static int getBitmapForeground(String nameForeground) {
        switch (nameForeground) {
            case "foreground_pawn_white":
                return R.drawable.foreground_pawn_white;
            case "foreground_pawn_black":
                return R.drawable.foreground_pawn_black;
            case "foreground_elephant_white":
                return R.drawable.foreground_elephant_white;
            case "foreground_elephant_black":
                return R.drawable.foreground_elephant_black;
            case "foreground_horse_white":
                return R.drawable.foreground_horse_white;
            case "foreground_horse_black":
                return R.drawable.foreground_horse_black;
            case "foreground_officer_white":
                return R.drawable.foreground_officer_white;
            case "foreground_officer_black":
                return R.drawable.foreground_officer_black;
            case "foreground_queen_white":
                return R.drawable.foreground_queen_white;
            case "foreground_queen_black":
                return R.drawable.foreground_queen_black;
            case "foreground_king_white":
                return R.drawable.foreground_king_white;
            case "foreground_king_black":
                return R.drawable.foreground_king_black;
            default:
                return 0;
        }
    }

    /**Функция возврата R.drawable изображения лиги по её номеру league
     * На вход принимает 1 параметр:
     * int league - номер заданной лиги*/
    public static int getDrawableLeague(int league) {
        switch (league) {
            case 15:
                return R.drawable.league_15;
            default:
                return 15;
        }
    }


    /**Функция получения R.drawable изображения данного достижения по заданному titleAchievement
     * На вход принимает 1 параметр:
     * String titleAchievement - название достижения*/
    public static int getDrawableAchievement(String titleAchievement) {
        switch (titleAchievement) {
            case "achievement_first_match":
                return R.drawable.achievement_first_match;
            case "achievement_first_win":
                return R.drawable.icon_achievements;
            default:
                return 0;
        }
    }


    /**Функция получения R.drawable определенного набора фигур по заданному id
     * На вход принимает 1 параметр:
     * int idSkin - название набора фигур*/
    public static int getSkinOfFigures(int idSkin) {
        switch (idSkin) {
            default:
                return 0;
        }
    }

}
