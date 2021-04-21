/** Класс для работы с внутренней базой данных SQLite */
package com.example.blindchess.ui.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    //Константы БД
    public static final int DATABASE_VERSION = 1;                           //Версия БД
    public static final String DATABASE_NAME = "SQLite";                    //Имя БД

    //Константы таблицы User
    public static final String TABLE_NAME_USER = "User";                    //Название таблицы с данными о данном пользователе
    public static final String KEY_ID_USER = "_id";                         //Имя столбца с id пользователя
    public static final String KEY_NAME_USER = "name";                      //Имя столбца с именем пользователя
    public static final String KEY_IMAGE_NAME_USER = "image_name";          //Имя столбца с названием аватарки пользователя
    public static final String KEY_RATING_USER = "rating";                  //Имя столбца с ейтингом пользователя
    public static final String KEY_WINS_USER = "wins";                      //Имя столбца с числом побед пользователя
    public static final String KEY_DEFEATS_USER = "defeats";                //Имя столбца с числом поражений пользователя
    public static final String KEY_BEST_LEAGUE_USER = "best_league";        //Имя столбца с лучшей лигой пользователя
    public static final String KEY_LEAGUE_USER = "league";                  //Имя столбца с текущей лигой пользователя
    public static final String KEY_LEAGUE_WINS_USER = "league_wins";        //Имя столбца с числом побед пользователя в текущей лиге
    public static final String KEY_LEAGUE_DEFEATS_USER = "league_defeats";  //Имя столбца с числом поражений пользователя в текущей лиге
    public static final String KEY_IS_LOGIN_USER = "is_login";              //Имя столбца со статусом пользователя (вошел в систему или нет)

    //Константы таблицы Achievement
    public static final String TABLE_NAME_ACHIEVEMENT = "Achievement";      //Название таблицы с достижениями пользователя
    public static final String KEY_ID_USER_ACHIEVEMENT = "_id";              //Имя столбца с id пользователя для данного достижения
    public static final String KEY_TITLE_ACHIEVEMENT = "title";            //Имя столбца с названием достижения
    public static final String KEY_DESCRIPTION_ACHIEVEMENT = "description"; //Имя столбца с описанием достижения
    public static final String KEY_IS_GET_ACHIEVEMENT = "is_get";           //Имя столбца с булевским значением, получил ли пользователь данное достижение


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
                    KEY_IMAGE_NAME_USER + " TEXT, " +
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


    /**Метод, выводящий БД в консоль (рабочая версия)*/
    /*public void print() {
        DBHelper db = new DBHelper(context);
                SQLiteDatabase database = db.getReadableDatabase();

                //Таблица User
                Cursor cursor = database.query(DBHelper.TABLE_NAME_USER, null, null, null, null, null, null);
                cursor.moveToFirst(); //Перемещаемся на первую строку
                do {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID_USER);
                    int idName = cursor.getColumnIndex(DBHelper.KEY_NAME_USER);
                    int idImageName = cursor.getColumnIndex(DBHelper.KEY_IMAGE_NAME_USER);
                    int idRating = cursor.getColumnIndex(DBHelper.KEY_RATING_USER);
                    int idWins = cursor.getColumnIndex(DBHelper.KEY_WINS_USER);
                    int idDefeats = cursor.getColumnIndex(DBHelper.KEY_DEFEATS_USER);
                    int idBestLeague = cursor.getColumnIndex(DBHelper.KEY_BEST_LEAGUE_USER);
                    int idLeague = cursor.getColumnIndex(DBHelper.KEY_LEAGUE_USER);
                    int idLeagueWins = cursor.getColumnIndex(DBHelper.KEY_LEAGUE_WINS_USER);
                    int idLeagueDefeats = cursor.getColumnIndex(DBHelper.KEY_LEAGUE_DEFEATS_USER);
                    int idIsLogin = cursor.getColumnIndex(DBHelper.KEY_IS_LOGIN_USER);

                    int id = cursor.getInt(idIndex); //Получаем значение id по заданному индексу
                    String name = cursor.getString(idName);
                    String imageName = cursor.getString(idImageName);
                    int rating = cursor.getInt(idRating);
                    int wins = cursor.getInt(idWins);
                    int defeats = cursor.getInt(idDefeats);
                    int bestLeague = cursor.getInt(idBestLeague);
                    int league = cursor.getInt(idLeague);
                    int leagueWins = cursor.getInt(idLeagueWins);
                    int leagueDefeats = cursor.getInt(idLeagueDefeats);
                    int isLogin = cursor.getInt(idIsLogin);

                    //Вывод таблицы
                    System.out.println(String.valueOf(id) + " | " + name + " | " + imageName + " | " + String.valueOf(rating) + " | " + String.valueOf(wins) + " | "
                            + String.valueOf(defeats) + " | " + String.valueOf(bestLeague) + " | " + String.valueOf(league) + " | " + String.valueOf(leagueWins) + " | "
                            + String.valueOf(leagueDefeats) + " | " + String.valueOf(isLogin));
                } while(cursor.moveToNext());
                cursor.close();

                //Таблица Achievement
                cursor = database.query(DBHelper.TABLE_NAME_ACHIEVEMENT, null, null, null, null, null, null);
                cursor.moveToFirst(); //Перемещаемся на первую строку
                do {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID_USER_ACHIEVEMENT);
                    int idTitle = cursor.getColumnIndex(DBHelper.KEY_TITLE_ACHIEVEMENT);
                    int idDescription = cursor.getColumnIndex(DBHelper.KEY_DESCRIPTION_ACHIEVEMENT);
                    int idIsGetAchievement = cursor.getColumnIndex(DBHelper.KEY_IS_GET_ACHIEVEMENT);

                    int id = cursor.getInt(idIndex); //Получаем значение id по заданному индексу
                    String title = cursor.getString(idTitle);
                    String description = cursor.getString(idDescription);
                    int isGetAchievement = cursor.getInt(idIsGetAchievement);

                    //Вывод таблицы
                    System.out.println(String.valueOf(id) + " | " + title + " | " + description + " | " + String.valueOf(isGetAchievement));
                } while(cursor.moveToNext());
                cursor.close();

                database.close();
                db.close();
    }*/
}
