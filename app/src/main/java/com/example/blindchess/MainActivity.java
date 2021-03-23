/**Класс главной активности*/
package com.example.blindchess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Создаем контроллер меню, который запускает первоначальный фрагмент (ищет в навигации) в mainContainer
        NavController navController = Navigation.findNavController(this, R.id.main_container);
    }

}
