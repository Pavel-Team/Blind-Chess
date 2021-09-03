/**Поток, вызываемый после каждого хода для проверки на получение какого-либо достижения
 * P.S. Некоторые достижения (например, на число побед и поражений) вызываются ВНЕ потока обычными статическими функциями в целях производительности*/
package com.example.blindchess.game.threads.calculation;


import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.blindchess.R;
import com.example.blindchess.game.CellBoard;
import com.example.blindchess.sqlite.DBHelper;


public class ThreadCheckAchievements extends Thread{

    private Context context;                //Контекст приложения
    private CellBoard cellStartMove;        //Клетка поля, с которой сделан ход
    private CellBoard cellEndMove;          //Клетка поля, на которую походили
    private static DBHelper db;             //Объект класса базы данных SQLite
    private static SQLiteDatabase database; //База данных SQLite


    /**Конструктор класса
     * На вход принимает 3 параметра:
     * Context context - контекст приложения
     * CellBoard cellStartMove - клетка поля, с которой сделан ход
     * CellBoard cellEndMove - клетка поля, на которую походили*/
    public ThreadCheckAchievements(Context context, CellBoard cellStartMove, CellBoard cellEndMove) {
        this.cellStartMove = cellStartMove;
        this.cellEndMove = cellEndMove;
    }


    /**Действия, выполняемые в потоке:
     * Проверка на выполнение всех достижений, связанных с игрой*/
    public void run() {
        //...Здесь будут другие достижения, связанные, непосредственно, с ходами (например: уничтожить 100 фигур конем)
    }


    /*Функция обновления данных в таблице Achievement
     * На вход принимает 2 параметра:
     * String conditionWhere - SQL-условие, в каких местах менять значения
     * ContentValues newValues - значения для замены
     * Внутри функции просисходит обновление БД
    private void updateTableAchievements(String conditionWhere, ContentValues newValues){
        db = new DBHelper(context);
        database = db.getReadableDatabase();
        database.update(DBHelper.TABLE_NAME_ACHIEVEMENT, newValues, conditionWhere, null);
        database.close();
        db.close();
    }*/


    /**Функция обновления статусов всех достижений, связанных с победой
     * На вход принимает 1 параметр:
     * Context context - контекст приложения*/
    public static void setWin(Context context){
        db = new DBHelper(context);
        database = db.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_NAME_USER, new String[]{DBHelper.KEY_WINS_USER}, null, null, null, null, null);
        cursor.moveToFirst();

        int countWins = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_WINS_USER)); //Число побед пользователя
        cursor.close();
        ContentValues newValues = new ContentValues(); //Обновляемые данные

        //Проверка на первую победу
        if (countWins == 0) {
            newValues.put(DBHelper.KEY_USER_PROGRESS_ACHIEVEMENT, 1);
            database.update(DBHelper.TABLE_NAME_ACHIEVEMENT, newValues,
                    DBHelper.KEY_TITLE_ACHIEVEMENT + " = ?", new String[] {context.getResources().getString(R.string.achievement_title_first_win)});
            newValues.clear();

            //Создаем Push-уведомление
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context, context.getResources().getString(R.string.achievement_title_first_win))
                            .setSmallIcon(R.drawable.icon_achievements)
                            .setContentTitle(context.getResources().getString(R.string.get_achievement))
                            .setContentText(context.getResources().getString(R.string.achievement_title_first_win))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(countWins, builder.build());
        }

        //Закрываем соединения
        database.close();
        db.close();
    }


    /**Функция обновления статусов всех достижений, связанных с числом игр
     * На вход принимает 1 параметр:
     * Context context - контекст приложения*/
    public static void setMatch(Context context){
        db = new DBHelper(context);
        database = db.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_NAME_USER, new String[]{DBHelper.KEY_WINS_USER, DBHelper.KEY_DEFEATS_USER}, null, null, null, null, null);
        cursor.moveToFirst();

        int countWins = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_WINS_USER)); //Число побед пользователя
        int countDefeats = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_DEFEATS_USER)); //Число поражений пользователя
        cursor.close();
        ContentValues newValues = new ContentValues(); //Обновляемые данные

        //Проверка на первый матч
        if (countWins + countDefeats == 0) {
            newValues.put(DBHelper.KEY_USER_PROGRESS_ACHIEVEMENT, 1);
            database.update(DBHelper.TABLE_NAME_ACHIEVEMENT, newValues,
                    DBHelper.KEY_TITLE_ACHIEVEMENT + " = ?", new String[] {context.getResources().getString(R.string.achievement_title_first_game)});
            newValues.clear();

            //Создаем Push-уведомление
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context, context.getResources().getString(R.string.achievement_title_first_game))
                            .setSmallIcon(R.drawable.achievement_first_match)
                            .setContentTitle(context.getResources().getString(R.string.get_achievement))
                            .setContentText(context.getResources().getString(R.string.achievement_title_first_game))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            //NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            notificationManager.notify(countWins+countDefeats, builder.build());
        }

        //Закрываем соединения
        database.close();
        db.close();
    }

}
