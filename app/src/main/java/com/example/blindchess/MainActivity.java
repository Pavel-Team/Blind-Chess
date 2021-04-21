/**Класс главной активности*/
package com.example.blindchess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.example.blindchess.ui.sqlite.DBHelper;


public class MainActivity extends AppCompatActivity {

    private static NavController navController; //Контроллер навигации приложения
    private DBHelper dbHelper;                  //База данных SQLite


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Создаем БД SQLite
        dbHelper = new DBHelper(getApplicationContext());

        //Создаем контроллер меню, который запускает первоначальный фрагмент (ищет в навигации) в mainContainer
        navController = Navigation.findNavController(this, R.id.main_container);
    }


    /**____________________ GETTER'Ы и SETTER'Ы ____________________*/
    public static NavController getNavController(){
        return navController;
    }
    /**_____________________________________________________________*/

}
