/**Репозиторий со всеми запросами к SQLite*/
package com.example.blindchess.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.blindchess.model.Achievement;
import com.example.blindchess.model.Product;
import com.example.blindchess.model.User;
import com.example.blindchess.sqlite.DBHelper;
import com.example.blindchess.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;


public class SQLiteRepository {

    private static DBHelper db; //Объект базы данных SQLite


    /**Конструктор класса
     * Внутри единожды инициализирует объект БД SQLite*/
    public SQLiteRepository(){
        if (db == null) {
            System.out.println("getDbHelper");
            this.db = MainActivity.getDbHelper();
        }
    }


    /**Функция добавления нового пользователя в БД SQLite
     * На вход принимает 2 параметра:
     * int id - ид пользователя (для гостя id = 1)
     * String name - имя пользователя (для гостя name = Guest)
     * Внутри происходит занесение пользователя в таблицы User и Achievement*/
    public void addNewUser(int id, String name){
        //Получаем объекты нашей БД для дальнейшей записи информации в них
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //Создаем транзакцию
        database.beginTransaction();
        try {
            //Заполняем таблицу User
            contentValues.put(DBHelper.KEY_ID_USER, id);
            contentValues.put(DBHelper.KEY_NAME_USER, name);
            contentValues.put(DBHelper.KEY_WINS_USER, 0);
            contentValues.put(DBHelper.KEY_DEFEATS_USER, 0);
            contentValues.put(DBHelper.KEY_BEST_LEAGUE_USER, 15);
            contentValues.put(DBHelper.KEY_BEST_LEAGUE_IN_THIS_SEASON_USER, 15);
            contentValues.put(DBHelper.KEY_LEAGUE_USER, 15);
            contentValues.put(DBHelper.KEY_LEAGUE_RATING_USER, 0);
            contentValues.put(DBHelper.KEY_MONEY_USER, 0);
            contentValues.put(DBHelper.KEY_BACKGROUND_USER, 13);
            contentValues.put(DBHelper.KEY_FOREGROUND_USER, 1);
            contentValues.put(DBHelper.KEY_SKIN_USER, 14);
            contentValues.put(DBHelper.KEY_IS_LOGIN_USER, 1);
            database.insert(DBHelper.TABLE_NAME_USER, null, contentValues);
            contentValues.clear();

            //Заполняем таблицу Achievement
            String sql2 = "INSERT INTO " +  DBHelper.TABLE_NAME_ACHIEVEMENT + "(" +
                    DBHelper.KEY_USER_ID_ACHIEVEMENT + ", " +
                    DBHelper.KEY_TITLE_ACHIEVEMENT + ", " +
                    DBHelper.KEY_DESCRIPTION_ACHIEVEMENT + ", " +
                    DBHelper.KEY_IMAGE_NAME_ACHIEVEMENT + ", " +
                    DBHelper.KEY_USER_PROGRESS_ACHIEVEMENT + ", " +
                    DBHelper.KEY_MAX_PROGRESS_ACHIEVEMENT + ")" +
                    " VALUES(?, ?, ?, ?, ?, ?);";
            SQLiteStatement statement2 = database.compileStatement(sql2);
            statement2.clearBindings(); //Очищаем все текущие привязки
            statement2.bindLong(1, id);
            /*statement.bindString(2, getResources().getString(R.string.achievement_title_first_game));
            statement.bindString(3, getResources().getString(R.string.achievement_description_first_game));
            statement.bindString(4, getResources().getString(R.string.achievement_image_name_first_game));*/
            statement2.bindString(2, "Первое сражение");
            statement2.bindString(3, "Сыграть первую игру");
            statement2.bindString(4, "achievement_first_match");
            statement2.bindLong(5, 0);
            statement2.bindLong(6, 1);
            statement2.execute();
            statement2.clearBindings();
            statement2.bindLong(1, id);
            /*statement.bindString(2, getResources().getString(R.string.achievement_title_first_win));
            statement.bindString(3, getResources().getString(R.string.achievement_description_first_win));
            statement.bindString(4, getResources().getString(R.string.achievement_image_name_first_win));*/
            statement2.bindString(2, "Первая победа!");
            statement2.bindString(3, "Одержать первую победу");
            statement2.bindString(4, "achievement_first_win");
            statement2.bindLong(5, 0);
            statement2.bindLong(6, 1);
            statement2.execute();

            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }

        //Закрываем соединение
        database.close();
        db.close();
    }


    /**Метод поиска пользователя по заданному id
     * На вход принимает 1 параметр:
     * int user_id - id пользовталея, которого ищем в SQLite
     * Если пользователь найден, вернется его модель User
     * Иначе вернется null*/
    public User findUserById(int user_id){
        User user;

        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_NAME_USER, null, DBHelper.KEY_ID_USER + " = ?", new String[]{String.valueOf(user_id)}, null, null, null);
        cursor.moveToFirst();

        if (cursor.getCount() != 0) {
            user = new User();

            int id = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID_USER));
            String name = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME_USER));
            int wins = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_WINS_USER));
            int defeats = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_DEFEATS_USER));
            int best_league = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_BEST_LEAGUE_USER));
            int best_league_in_this_season = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_BEST_LEAGUE_IN_THIS_SEASON_USER));
            int league = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_LEAGUE_USER));
            int money = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_MONEY_USER));
            Product background = findProductById(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_BACKGROUND_USER)));
            Product foreground = findProductById(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_FOREGROUND_USER)));
            Product skin = findProductById(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_SKIN_USER)));
            List<Achievement> achievements = getAchievementsFromSQLite(id);

            user.setUser_id(id);
            user.setName(name);
            user.setWins(wins);
            user.setDefeats(defeats);
            user.setBest_league(best_league);
            user.setLeague_max_in_this_season(best_league_in_this_season);
            user.setLeague(league);
            user.setMoney(money);
            user.setBackground(background);
            user.setForeground(foreground);
            user.setSkin(skin);
            user.setAchievements(achievements);
        } else {
            user = null;
        }

        //Закрываем соединения
        cursor.close();
        database.close();
        db.close();

        return user;
    }


    /**Метод поиска пользователя, который в данный момент вошел в систему
     * Если такой найден - вернет его объект User, иначе - вернет null*/
    public User findUserIsLogin() {
        User user;

        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_NAME_USER, null, DBHelper.KEY_IS_LOGIN_USER + " = 1", null, null, null, null);
        cursor.moveToFirst();

        if (cursor.getCount() != 0) {
            user = new User();

            int id = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID_USER));
            String name = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME_USER));
            int wins = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_WINS_USER));
            int defeats = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_DEFEATS_USER));
            int best_league = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_BEST_LEAGUE_USER));
            int best_league_in_this_season = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_BEST_LEAGUE_IN_THIS_SEASON_USER));
            int league = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_LEAGUE_USER));
            int money = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_MONEY_USER));
            Product background = findProductById(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_BACKGROUND_USER)));
            Product foreground = findProductById(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_FOREGROUND_USER)));
            Product skin = findProductById(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_SKIN_USER)));
            List<Achievement> achievements = getAchievementsFromSQLite(id);

            user.setUser_id(id);
            user.setName(name);
            user.setWins(wins);
            user.setDefeats(defeats);
            user.setBest_league(best_league);
            user.setLeague_max_in_this_season(best_league_in_this_season);
            user.setLeague(league);
            user.setMoney(money);
            user.setBackground(background);
            user.setForeground(foreground);
            user.setSkin(skin);
            user.setAchievements(achievements);
        } else {
            user = null;
        }

        //Закрываем соединения
        cursor.close();
        database.close();
        db.close();

        return user;
    }


    /**Метод установки значения входа пользователя isLogin
     * На вход принимает 2 параметра:
     * int user_id - id пользователя в SQLite, которому устанваливаем isLogin
     * int isLogin - значение isLogin (0 или 1)*/
    public void setIsLogin(int user_id, int isLogin) {
        SQLiteDatabase database = db.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_NAME_USER, null, DBHelper.KEY_ID_USER + " = ?", new String[]{String.valueOf(user_id)}, null, null, null);
        cursor.moveToFirst();

        if (cursor.getCount() != 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.KEY_IS_LOGIN_USER, isLogin);

            database.update(DBHelper.TABLE_NAME_USER, contentValues, DBHelper.KEY_ID_USER + "=" + String.valueOf(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID_USER))), null);
        }

        cursor.close();
        database.close();
        db.close();
    }


    /**Удаление записей из SQLite, с которыми связан пользователь с заданным id
     * На вход принимает 1 параметр:
     * int user_id - id заданного пользователя
     * Внутри удаляются все записи из всех таблиц с данным пользователем*/
    public void deleteUser(int user_id) {
        SQLiteDatabase database = db.getWritableDatabase();

        database.beginTransaction();
        try {
            database.delete(DBHelper.TABLE_NAME_USER, DBHelper.KEY_ID_USER + " = ?", new String[]{String.valueOf(user_id)});
            database.delete(DBHelper.TABLE_NAME_ACHIEVEMENT, DBHelper.KEY_USER_ID_ACHIEVEMENT + " = ?", new String[]{String.valueOf(user_id)});
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }

        database.close();
        db.close();
    }


    /**Функция получения информации о всех достиженях, полученных пользователем с данныи user_id
     * На вход принимает 1 параметр:
     * int user_id - id пользователя, достижения которого мы ищем в SQLite
     * Возвращает ArrayList<AchievementDTO> - список всех достижений пользователя*/
    public ArrayList<Achievement> getAchievementsFromSQLite(int user_id){
        ArrayList<Achievement> listAchievements = new ArrayList<Achievement>(); //Результирующий лист
        int id, userProgress, maxProgress;                            //Все свойства объекта achievement
        String title, description, image_name;

        //Берем данные из БД и заносим их в наш результирующий список в виде объектов achievement
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_NAME_ACHIEVEMENT, null, DBHelper.KEY_USER_ID_ACHIEVEMENT + "= ?", new String[] {String.valueOf(user_id)}, null, null, null);
        cursor.moveToFirst();

        do {
            Achievement achievement = new Achievement(); //Объект одного достижения
            database.beginTransaction(); //Начинаем транзакцию взятия одной строки
            try {
                id = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_USER_ID_ACHIEVEMENT));
                title = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TITLE_ACHIEVEMENT));
                description = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DESCRIPTION_ACHIEVEMENT));
                image_name = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_IMAGE_NAME_ACHIEVEMENT));
                userProgress = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_USER_PROGRESS_ACHIEVEMENT));
                maxProgress = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_MAX_PROGRESS_ACHIEVEMENT));

                database.setTransactionSuccessful();
            } finally {
                database.endTransaction();
            }

            //Заносим в объект
            achievement.setUser_id(id);
            achievement.setTitle(title);
            achievement.setDescription(description);
            achievement.setImageName(image_name);
            achievement.setUserProgress(userProgress);
            achievement.setMaxProgress(maxProgress);

            listAchievements.add(achievement);
        } while (cursor.moveToNext());

        //Закрываем соединения
        cursor.close();
        database.close();
        db.close();

        return listAchievements;
    }


    /**Функия поиска продукта по его id
     * На вход принимает 1 парметр:
     * int product_id - id данного продукта
     * Если продукт найден - вернет его, иначе - вернет null*/
    public Product findProductById(int product_id) {
        Product product;

        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_NAME_PRODUCT, null, DBHelper.KEY_PRODUCT_ID + " = ?", new String[] {String.valueOf(product_id)}, null, null, null);
        cursor.moveToFirst();

        if (cursor != null) {
            product = new Product();

            String title = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TITLE_PRODUCT));
            String type = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TYPE_PRODUCT));
            int price = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_PRICE_PRODUCT));
            String image_name = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_IMAGE_NAME_PRODUCT));

            product.setProduct_id(product_id);
            product.setTitle(title);
            product.setType(type);
            product.setPrice(price);
            product.setImage_name(image_name);
        } else {
            product = null;
        }

        cursor.close();
        database.close();
        db.close();

        return product;
    }

}
