/**ViewModel для окна главного меню MainMenuFragment*/
package com.example.blindchess.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.blindchess.model.User;
import com.example.blindchess.service.MainMenuService;
import com.example.blindchess.ui.activity.MainActivity;


public class MainMenuViewModel extends ViewModel {

    private static MainMenuService mainMenuService;   //Объект для работы с SQLite
    private static MutableLiveData<User> liveData;    //Объект с моделью пользователя (ссылается на MutableLiveData<User> в MainActivityViewModel)


    /**Конструктор класса
     * Внутри единожды инициализируется объект mainMenuService и liveData*/
    public MainMenuViewModel(){
        if (mainMenuService == null)
            mainMenuService = new MainMenuService();
        liveData = MainActivity.getViewModel().getLiveData();
    }


    public MutableLiveData<User> getLiveData() {
        return liveData;
    }


    /**Метод выхода пользователя из аккаунта*/
    public void quitFromAccount(){
        mainMenuService.quitFromAccount(liveData.getValue().getUser_id());
    }

}
