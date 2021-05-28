/**Класс для получения Bitmap'ов всех предметов пользователя (фонов, фигур переднего плана, скинов для шахмат),
 * а также для получения картинки лиги пользователя*/
package com.example.blindchess.ui.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.blindchess.R;


public class User {

    private Context context; //Контекст приложения


    /**Конструктор класса
     * На вход принимает 1 параметр:
     * Context context - контекст приложения*/
    public User (Context context) {
        this.context = context;
    }


    /**Функция получения Bitmap определенного заднего фона по заданному nameBackground
     * На вход принимает 1 параметр:
     * String nameBackground - название заднего фона
     * Функция возвращает Bitmap - Bitmap заданного заднего фона*/
    public Bitmap getBitmapBackground(String nameBackground) {
        switch (nameBackground) {
            case "background_board":
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.background_board);
            default:
                return null;
        }
    }


    /**Функция получения Bitmap определенного переднего плана по заданному nameForeground
     * На вход принимает 1 параметр:
     * String nameForeground - название переднего плана
     * Функция возвращает Bitmap - Bitmap переднего плана*/
    public Bitmap getBitmapForeground(String nameForeground) {
        switch (nameForeground) {
            case "foreground_pawn_white":
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.foreground_pawn_white);
            case "foreground_pawn_black":
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.foreground_pawn_black);
            case "foreground_elephant_white":
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.foreground_elephant_white);
            case "foreground_elephant_black":
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.foreground_elephant_black);
            case "foreground_horse_white":
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.foreground_horse_white);
            case "foreground_horse_black":
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.foreground_horse_black);
            case "foreground_officer_white":
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.foreground_officer_white);
            case "foreground_officer_black":
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.foreground_officer_black);
            case "foreground_queen_white":
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.foreground_queen_white);
            case "foreground_queen_black":
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.foreground_queen_black);
            case "foreground_king_white":
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.foreground_king_white);
            case "foreground_king_black":
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.foreground_king_black);
            default:
                return null;
        }
    }


    /**Функция получения Bitmap лиги по заданному номеру лиги league
     * На вход принимает 1 параметр:
     * int league - номер лиги
     * Функция возвращает Bitmap - Bitmap заданной лиги*/
    public Bitmap getBitmapLeague(int league) {
        switch (league) {
            case 15:
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.league_15);
            default:
                return null;
        }
    }


    /**Функция получения Bitmap определенного набора фигур по заданному названию nameSetOfFigures
     * На вход принимает 1 параметр:
     * String nameSetOfFigures - название набора фигур
     * Функция возвращает Bitmap - Bitmap заданного набора фигур*/
    public Bitmap getSetOfFigures(String nameSetOfFigures) {
        switch (nameSetOfFigures) {
            default:
                return null;
        }
    }

}
