/**ViewModel главного активити, содержащей в себе основную информацию о пользователе (модель User)*/
package com.example.blindchess.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.blindchess.model.User;
import com.example.blindchess.repository.SQLiteRepository;


public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<User> liveDataActivity; //LiveData с моделью User
    private SQLiteRepository sqLiteRepository;      //Репозиторий для работы с SQLite БД


    /**Конструктор класса
     * Внутри создается объект LiveData<User> и sqLiteRepository*/
    public MainActivityViewModel(){
        liveDataActivity = new MutableLiveData<>();
        User user;

        //Достаем пользователя из SQLite, если он есть. Если нет - делаем его пустым
        if (sqLiteRepository == null)
            sqLiteRepository = new SQLiteRepository();

        user = sqLiteRepository.findUserIsLogin();
        //if (user.getUser_id() != 1)
        //    //...Подгрузка данных с сервера
        liveDataActivity.setValue(user);
    }


    public MutableLiveData<User> getLiveData() {
        return liveDataActivity;
    }

}
