/**ViewModel для окна входа LoginFragment*/
package com.example.blindchess.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.blindchess.model.Login;
import com.example.blindchess.service.LoginService;


public class LoginViewModel extends ViewModel {

    private MutableLiveData<Login> liveDataLogin; //LiveData для модели Login
    private static LoginService loginService;     //Объект для работы с бизнес-логикой сущности Login


    /**Конструктор класса
     * Внутри создается пустой объект LiveData<Login> и loginService*/
    public LoginViewModel() {
        liveDataLogin = new MutableLiveData<>(new Login());

        if (loginService == null)
            loginService = new LoginService();
    }


    /**Метод нажатия на кнопку "Войти"
     * Если ...*/
    public String onClickButtonLogin() {
        Login login = liveDataLogin.getValue();
        return loginService.login(login.getEmailLogin(), login.getPasswordLogin());
    }


    public LiveData<Login> getLiveData() {

        System.out.println("LoginLiveData" +
                "\nemail: " + liveDataLogin.getValue().getEmailLogin() + "\n" +
                "password: " + liveDataLogin.getValue().getPasswordLogin());
        System.out.println("--------------");

        return liveDataLogin;
    }

}
