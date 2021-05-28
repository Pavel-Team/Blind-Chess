/**Класс главной активности*/
package com.example.blindchess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.blindchess.ui.sqlite.DBHelper;


public class MainActivity extends AppCompatActivity {

    private static NavController navController; //Контроллер навигации приложения
    private DBHelper dbHelper;                  //База данных SQLite


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Создаем контроллер меню, который запускает первоначальный фрагмент (ищет в навигации) в mainContainer
        navController = Navigation.findNavController(this, R.id.main_container);

        //Создаем объект БД SQLite
        dbHelper = new DBHelper(getApplicationContext());
        //Далее проверяем, зашел ли пользователь в систему или нет. Если да - открываем фрагмент главного меню, иначе - запускаем фрагмент входа
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_NAME_USER, new String[]{DBHelper.KEY_IS_LOGIN_USER}, null, null, null, null, null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0 && cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_IS_LOGIN_USER)) == 1)
                navController.navigate(R.id.action_fragmentLogin_to_fragmentMainMenu);
        cursor.close();
        database.close();
        dbHelper.close();

    }


    /**____________________ GETTER'Ы и SETTER'Ы ____________________*/
    public static NavController getNavController(){
        return navController;
    }
    /**_____________________________________________________________*/

    /**ВРЕМЕННО*/
    public void print() {
        DBHelper db = new DBHelper(getApplicationContext());
        SQLiteDatabase database = db.getReadableDatabase();

        //Таблица User
        Cursor cursor = database.query(DBHelper.TABLE_NAME_USER, null, null, null, null, null, null);
        cursor.moveToFirst(); //Перемещаемся на первую строку
        do {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID_USER);
            int idName = cursor.getColumnIndex(DBHelper.KEY_NAME_USER);
            int idImageNameBackground = cursor.getColumnIndex(DBHelper.KEY_IMAGE_NAME_BACKGROUND);
            int idImageNameForeground = cursor.getColumnIndex(DBHelper.KEY_IMAGE_NAME_FOREGROUND);
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
            String imageNameBackground = cursor.getString(idImageNameBackground);
            String imageNameForeground = cursor.getString(idImageNameForeground);
            int rating = cursor.getInt(idRating);
            int wins = cursor.getInt(idWins);
            int defeats = cursor.getInt(idDefeats);
            int bestLeague = cursor.getInt(idBestLeague);
            int league = cursor.getInt(idLeague);
            int leagueWins = cursor.getInt(idLeagueWins);
            int leagueDefeats = cursor.getInt(idLeagueDefeats);
            int isLogin = cursor.getInt(idIsLogin);

            //Вывод таблицы
            System.out.println(String.valueOf(id) + " | " + name + " | " + imageNameBackground + " | " + imageNameForeground + " | " + String.valueOf(rating) + " | " + String.valueOf(wins) + " | "
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
    }
}
