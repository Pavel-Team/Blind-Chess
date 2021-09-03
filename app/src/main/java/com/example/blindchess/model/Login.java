/**Модель, содержащая в себе email и пароль пользователя.
 * Используется в LoginViewModel*/
package com.example.blindchess.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.blindchess.BR;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
public class Login extends BaseObservable {

    private String emailLogin = "";          //email пользователя
    private String passwordLogin = "";       //Пароль пользователя


    @Bindable //Создает BR.email (указываем что это значение может меняться и что эти изменения должны отслеживаться)
    public String getEmailLogin() {
        return emailLogin;
    }

    @Bindable
    public String getPasswordLogin() {
        return passwordLogin;
    }

    public void setEmailLogin(String email) {
        this.emailLogin = email;
        notifyPropertyChanged(BR.emailLogin); //Для мгновенного обновления пользовательского интерфейса
    }

    public void setPasswordLogin(String password) {
        this.passwordLogin = password;
        notifyPropertyChanged(BR.passwordLogin);
    }

}
