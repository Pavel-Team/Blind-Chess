/**Класс главной активности*/
package com.example.blindchess.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.example.blindchess.R;
import com.example.blindchess.sqlite.DBHelper;
import com.example.blindchess.databinding.ActivityMainBinding;
import com.example.blindchess.viewModel.MainActivityViewModel;


public class MainActivity extends AppCompatActivity {

    private static NavController navController; //Контроллер навигации приложения
    private static DBHelper dbHelper;           //База данных SQLite

    private ActivityMainBinding binding;        //Binding для связывания DataBinding и ViewModel
    private static MainActivityViewModel viewModel;    //ViewModel для нашего активити


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Создаем ЕДИНСТВЕННЫЙ объект БД SQLite
        dbHelper = new DBHelper(this);
        dbHelper.close();

        //Привязываем ViewModel
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.setVariable(BR.viewModelActivity, viewModel);

        //Создаем контроллер меню, который запускает первоначальный фрагмент (ищет в навигации) в mainContainer
        navController = Navigation.findNavController(this, R.id.main_container);

        //Далее проверяем, зашел ли пользователь в систему или нет. Если да - открываем фрагмент главного меню, иначе - запускаем фрагмент входа
        if (viewModel.getLiveData().getValue() != null)
            navController.navigate(R.id.action_fragmentLogin_to_fragmentMainMenu);

    }


    /**____________________ GETTER'Ы и SETTER'Ы ____________________*/
    public static NavController getNavController(){
        return navController;
    }

    public static DBHelper getDbHelper(){
        return dbHelper;
    }

    public static MainActivityViewModel getViewModel() {
        return viewModel;
    }
    /**_____________________________________________________________*/

}
