/** Класс для работы с внутренней базой данных SQLite */
package com.example.blindchess.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.os.CancellationSignal;
import android.util.Log;

import androidx.annotation.Nullable;


public class DBHelper extends SQLiteOpenHelper {

    //Константы БД
    public static final int DATABASE_VERSION = 2;                                                  //Версия БД
    public static final String DATABASE_NAME = "SQLite";                                           //Имя БД

    //Константы таблицы User
    public static final String TABLE_NAME_USER = "User";                                           //Название таблицы с данными о данном пользователе
    public static final String KEY_ID_USER = "_id";                                                //Имя столбца с id пользователя
    public static final String KEY_NAME_USER = "name";                                             //Имя столбца с именем пользователя
    public static final String KEY_WINS_USER = "wins";                                             //Имя столбца с числом побед пользователя
    public static final String KEY_DEFEATS_USER = "defeats";                                       //Имя столбца с числом поражений пользователя
    public static final String KEY_BEST_LEAGUE_USER = "best_league";                               //Имя столбца с лучшей лигой пользователя
    public static final String KEY_BEST_LEAGUE_IN_THIS_SEASON_USER = "best_league_in_this_season"; //Имя столбца с лучшей лигой в данном сезоне
    public static final String KEY_LEAGUE_USER = "league";                                         //Имя столбца с текущей лигой пользователя
    public static final String KEY_LEAGUE_RATING_USER = "league_rating";                           //Имя столбца с рейтингом пользователя
    public static final String KEY_MONEY_USER = "money";                                           //Имя столбца с числом монет пользователя
    public static final String KEY_BACKGROUND_USER = "background_id";                              //Имя столбца с текущим задним фоном аватарки пользователя
    public static final String KEY_FOREGROUND_USER = "foreground_id";                              //Имя столбца с текущим передним планом аватарки пользователя
    public static final String KEY_SKIN_USER = "skin_id";                                          //Имя столбца с текущим названием переднего плана аватарки пользователя
    public static final String KEY_IS_LOGIN_USER = "is_login";                                     //Имя столбца со статусом пользователя (вошел в систему или нет)

    //Константы таблицы Achievement
    public static final String TABLE_NAME_ACHIEVEMENT = "Achievement";                             //Название таблицы с достижениями пользователя
    public static final String KEY_ID_ACHIEVEMENT = "_id";                                         //Имя столбца с id записи достижения
    public static final String KEY_USER_ID_ACHIEVEMENT = "user_id";                                //Имя столбца с id пользователя для данного достижения
    public static final String KEY_TITLE_ACHIEVEMENT = "title";                                    //Имя столбца с названием достижения
    public static final String KEY_DESCRIPTION_ACHIEVEMENT = "description";                        //Имя столбца с описанием достижения
    public static final String KEY_IMAGE_NAME_ACHIEVEMENT = "image_name";                          //Имя столбца с названием картинки достижения
    public static final String KEY_USER_PROGRESS_ACHIEVEMENT = "user_progress";                    //Имя столбца с прогрессом получения данного достижения пользователем
    public static final String KEY_MAX_PROGRESS_ACHIEVEMENT = "max_progress";                      //Имя столбца с необходимым прогрессом для получения данного достижения

    //Константы для таблицы Product
    public static final String TABLE_NAME_PRODUCT = "Product";                                     //Название таблицы с игровыми предметами
    public static final String KEY_PRODUCT_ID = "_id";                                             //Название столбца с id игрового предмета
    public static final String KEY_TITLE_PRODUCT = "title";                                        //Название столбца с названием игрового предмета
    public static final String KEY_TYPE_PRODUCT = "type";                                          //Название столбца с типом игрового предмета (BACKGROUND, FOREGROUND или SKIN)
    public static final String KEY_PRICE_PRODUCT = "price";                                        //Название столбца с ценой игрового предмета
    public static final String KEY_IMAGE_NAME_PRODUCT = "image_name";                              //Название столбца с названием картинки игрового предмета


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
        Log.d("DBHelper", "CREATE DATABASE");
        db.beginTransaction();
        try {
            //Создаем таблицу Product
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME_PRODUCT + " (" +
                    KEY_PRODUCT_ID + " INTEGER PRIMARY KEY, " +
                    KEY_TITLE_PRODUCT + " TEXT, " +
                    KEY_TYPE_PRODUCT + " TEXT, " +
                    KEY_PRICE_PRODUCT + " INTEGER, " +
                    KEY_IMAGE_NAME_PRODUCT + " TEXT" + ")");

            //Создаем таблицу User
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USER + " (" +
                    KEY_ID_USER + " INTEGER PRIMARY KEY, " +
                    KEY_NAME_USER + " TEXT, " +
                    KEY_WINS_USER + " INTEGER, " +
                    KEY_DEFEATS_USER + " INTEGER, " +
                    KEY_BEST_LEAGUE_USER + " INTEGER, " +
                    KEY_BEST_LEAGUE_IN_THIS_SEASON_USER + " INTEGER, " +
                    KEY_LEAGUE_USER + " INTEGER, " +
                    KEY_LEAGUE_RATING_USER + " INTEGER, " +
                    KEY_MONEY_USER + " INTEGER, " +
                    KEY_BACKGROUND_USER + " INTEGER, " +
                    KEY_FOREGROUND_USER + " INTEGER, " +
                    KEY_SKIN_USER + " INTEGER, " +
                    KEY_IS_LOGIN_USER + " INTEGER, " +
                    "FOREIGN KEY (" + KEY_BACKGROUND_USER + ") REFERENCES " + TABLE_NAME_PRODUCT + "(" + KEY_PRODUCT_ID + "), " +
                    "FOREIGN KEY (" + KEY_FOREGROUND_USER + ") REFERENCES " + TABLE_NAME_PRODUCT + "(" + KEY_PRODUCT_ID + "), " +
                    "FOREIGN KEY (" + KEY_SKIN_USER + ") REFERENCES " + TABLE_NAME_PRODUCT + "(" + KEY_PRODUCT_ID + ")" +
                    ")");

            //Создаем таблицу Achievement
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME_ACHIEVEMENT + " (" +
                    KEY_ID_ACHIEVEMENT + " INTEGER PRIMARY KEY, " +
                    KEY_USER_ID_ACHIEVEMENT + " INTEGER, " +
                    KEY_TITLE_ACHIEVEMENT + " TEXT, " +
                    KEY_DESCRIPTION_ACHIEVEMENT + " TEXT, " +
                    KEY_IMAGE_NAME_ACHIEVEMENT + " TEXT, " +
                    KEY_USER_PROGRESS_ACHIEVEMENT + " INTEGER, " +
                    KEY_MAX_PROGRESS_ACHIEVEMENT + " INTEGER, " +
                    "FOREIGN KEY (" + KEY_USER_ID_ACHIEVEMENT + ") REFERENCES " + TABLE_NAME_USER + "(" + KEY_ID_USER + ")" + ")");

            //Заполняем таблицу Product (ВРЕМЕННО первыми тремя предметами - потом заменить на подкачку данных с сервера)
            String sql = "INSERT INTO " + DBHelper.TABLE_NAME_PRODUCT + " VALUES(?, ?, ?, ?, ?);";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings(); //Очищаем все текущие привязки
            statement.bindLong(1, 1);
            statement.bindString(2, "Белая пешка");
            statement.bindString(3, "FOREGROUND");
            statement.bindLong(4, 0);
            statement.bindString(5, "foreground_pawn_white");
            statement.execute();
            statement.clearBindings(); //Очищаем все текущие привязки
            statement.bindLong(1, 13);
            statement.bindString(2, "Обычная доска");
            statement.bindString(3, "BACKGROUND");
            statement.bindLong(4, 0);
            statement.bindString(5, "background_board");
            statement.execute();
            statement.clearBindings(); //Очищаем все текущие привязки
            statement.bindLong(1, 14);
            statement.bindString(2, "Стандартный скин");
            statement.bindString(3, "SKIN");
            statement.bindLong(4, 0);
            statement.bindString(5, "skin_classical_chess");
            statement.execute();
            statement.clearBindings(); //Очищаем все текущие привязки

            db.setTransactionSuccessful(); //Если дошли досюда - значит транзакция прошла успешно
        } catch (Exception e){
            e.printStackTrace();
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
            db.execSQL("drop table if exists " + TABLE_NAME_PRODUCT);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        onCreate(db); //Создаем заново БД
    }

}
