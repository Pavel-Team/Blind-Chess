/**Класс, содержащий всю бизнес-логику для модели Registration*/
package com.example.blindchess.service;

import androidx.lifecycle.ViewModelProviders;

import com.example.blindchess.model.User;
import com.example.blindchess.repository.RegistrationRepository;
import com.example.blindchess.repository.SQLiteRepository;
import com.example.blindchess.ui.activity.MainActivity;
import com.example.blindchess.viewModel.MainActivityViewModel;


public class RegistrationService {

    private static RegistrationRepository registrationRepository; //Объект для работы с запросами к БД
    private static SQLiteRepository sqLiteRepository;             //Объект для работы с запросами к SQLite БД


    /**Конструктор класса*/
    public RegistrationService(){
        if (registrationRepository == null)
            registrationRepository = new RegistrationRepository();
        if (sqLiteRepository == null)
            sqLiteRepository = new SQLiteRepository();
    }


    /**Метод регистрации нового пользователя
     * На вход принимает 3 параметра:
     * String name - имя пользователя
     * String email - email пользователя
     * String password - пароль пользователя
     * В случае успеха вернет - ACCOUNT_CREATED
     * Если пользователь с таким именем существует, вернет - ERROR_NAME
     * Если пользователь с таким логином существет, вернет - ERROR_EMAIL
     * Иначе - ERROR_ACCOUNT_CREATED*/
    public String registrationUser(String name, String email, String password) {
        return registrationRepository.registrationUser(name, email, password);
    }


    /**Метод входа в качетсве гостя
     * Внутри проверяется, была ла раньше запись в SQLite:
     * если да - то зайдет с старого аккаунта
     * если нет - создаст новый*/
    public void loginGuest() {
        User user = sqLiteRepository.findUserById(1);

        //Вносим изменения в SQLite
        if (user != null) {
            sqLiteRepository.setIsLogin(1, 1);
        } else {
            sqLiteRepository.addNewUser(1, "Guest");
            user = sqLiteRepository.findUserById(1); //ВРЕМЕННО (сделать по умолчанию конструктор)
        }

        //Добавляем во ViewModel
        MainActivity.getViewModel().getLiveData().setValue(user);
    }

}
