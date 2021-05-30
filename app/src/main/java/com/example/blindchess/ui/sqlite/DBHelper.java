/** Класс для работы с внутренней базой данных SQLite */
package com.example.blindchess.ui.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DBHelper extends SQLiteOpenHelper {

    //Константы БД
    public static final int DATABASE_VERSION = 1;                                   //Версия БД
    public static final String DATABASE_NAME = "SQLite";                            //Имя БД

    //Константы таблицы User
    public static final String TABLE_NAME_USER = "User";                            //Название таблицы с данными о данном пользователе
    public static final String KEY_ID_USER = "_id";                                 //Имя столбца с id пользователя
    public static final String KEY_NAME_USER = "name";                              //Имя столбца с именем пользователя
    public static final String KEY_IMAGE_NAME_BACKGROUND = "image_name_background"; //Имя столбца с названием фона аватарки пользователя
    public static final String KEY_IMAGE_NAME_FOREGROUND = "image_name_foreground"; //Имя столбца с названием переднего плана аватарки пользователя
    public static final String KEY_RATING_USER = "rating";                          //Имя столбца с ейтингом пользователя
    public static final String KEY_WINS_USER = "wins";                              //Имя столбца с числом побед пользователя
    public static final String KEY_DEFEATS_USER = "defeats";                        //Имя столбца с числом поражений пользователя
    public static final String KEY_BEST_LEAGUE_USER = "best_league";                //Имя столбца с лучшей лигой пользователя
    public static final String KEY_LEAGUE_USER = "league";                          //Имя столбца с текущей лигой пользователя
    public static final String KEY_LEAGUE_WINS_USER = "league_wins";                //Имя столбца с числом побед пользователя в текущей лиге
    public static final String KEY_LEAGUE_DEFEATS_USER = "league_defeats";          //Имя столбца с числом поражений пользователя в текущей лиге
    public static final String KEY_IS_LOGIN_USER = "is_login";                      //Имя столбца со статусом пользователя (вошел в систему или нет)

    //Константы таблицы Achievement
    public static final String TABLE_NAME_ACHIEVEMENT = "Achievement";              //Название таблицы с достижениями пользователя
    public static final String KEY_ID_USER_ACHIEVEMENT = "_id";                     //Имя столбца с id пользователя для данного достижения
    public static final String KEY_TITLE_ACHIEVEMENT = "title";                     //Имя столбца с названием достижения
    public static final String KEY_DESCRIPTION_ACHIEVEMENT = "description";         //Имя столбца с описанием достижения
    public static final String KEY_IMAGE_NAME_ACHIEVEMENT = "image_name";           //Имя столбца с названием картинки достижения
    public static final String KEY_USER_PROGRESS_ACHIEVEMENT = "user_progress";     //Имя столбца с прогрессом получения данного достижения пользователем
    public static final String KEY_MAX_PROGRESS_ACHIEVEMENT = "max_progress";       //Имя столбца с необходимым прогрессом для получения данного достижения
    public static final String KEY_IS_GET_ACHIEVEMENT = "is_get";                   //Имя столбца с булевским значением, получил ли пользователь данное достижение


    /**Конструктор класса
     * На вход принимает 1 параметр:
     * Context context - контекст приложения
     * Внутри создает БД с заданным именем и версией через соответствующие константы*/
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /** Метод, вызываемый при первом создании БД
     * Внутри создаются две таблицы:
     * User - таблица с данным пользователем
     * Achievements - таблица с достижениями пользователя*/
    @Override
    public void onCreate(SQLiteDatabase db) {

        //Начинаем одну большую транзакцию
        db.beginTransaction();
        try {
            //Создаем таблицу User
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USER + "(" +
                    KEY_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_NAME_USER + " TEXT, " +
                    KEY_IMAGE_NAME_BACKGROUND + " TEXT, " +
                    KEY_IMAGE_NAME_FOREGROUND + " TEXT, " +
                    KEY_RATING_USER + " INTEGER, " +
                    KEY_WINS_USER + " INTEGER, " +
                    KEY_DEFEATS_USER + " INTEGER, " +
                    KEY_BEST_LEAGUE_USER + " INTEGER, " +
                    KEY_LEAGUE_USER + " INTEGER, " +
                    KEY_LEAGUE_WINS_USER + " INTEGER, " +
                    KEY_LEAGUE_DEFEATS_USER + " INTEGER, " +
                    KEY_IS_LOGIN_USER + " INTEGER" + ")");

            //Создаем таблицу Achievement
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME_ACHIEVEMENT + "(" +
                    KEY_ID_USER_ACHIEVEMENT + " INTEGER, " +
                    KEY_TITLE_ACHIEVEMENT + " TEXT PRIMARY KEY, " +
                    KEY_DESCRIPTION_ACHIEVEMENT + " TEXT, " +
                    KEY_IMAGE_NAME_ACHIEVEMENT + " TEXT, " +
                    KEY_USER_PROGRESS_ACHIEVEMENT + " INTEGER, " +
                    KEY_MAX_PROGRESS_ACHIEVEMENT + " INTEGER, " +
                    KEY_IS_GET_ACHIEVEMENT + " INTEGER" + ")");

            db.setTransactionSuccessful(); //Если дошли досюда - значит транзакция прошла успешно
        } finally {
            db.endTransaction();
        }
    }


    /**Метод, вызываемый при изменении БД (её версии)
     * Внутри (в случае изменении версии) удаляются старые версии таблиц и создаются новые*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Если версия БД изменилась (вышло обновление) - то удаляем прошлую БД и создаем новую
        db.beginTransaction();
        try {
            db.execSQL("drop table if exists " + TABLE_NAME_USER);
            db.execSQL("drop table if exists " + TABLE_NAME_ACHIEVEMENT);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        onCreate(db); //Создаем заново БД
    }

}
