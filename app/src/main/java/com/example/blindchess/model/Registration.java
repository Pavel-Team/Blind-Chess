/**Модель, содержащая в себе email, имя и пароль пользователя.
 * Используется в RegistrationViewModel*/
package com.example.blindchess.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.blindchess.BR;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
public class Registration extends BaseObservable {

    private String nameRegistration = "";           //Имя пользователя
    private String emailRegistration = "";          //email пользователя
    private String passwordRegistration = "";       //Пароль пользователя
    private String errorInputRegistration = "";     //Текст ошибки в TextView при неправильном вводе в поля регистрации


    @Bindable
    public String getNameRegistration() {
        return nameRegistration;
    }

    @Bindable
    public String getEmailRegistration() {
        return emailRegistration;
    }

    @Bindable
    public String getPasswordRegistration() {
        return passwordRegistration;
    }

    @Bindable
    public String getErrorInputRegistration() {
        return errorInputRegistration;
    }

    public void setNameRegistration(String name) {
        this.nameRegistration = name;
        notifyPropertyChanged(BR.nameRegistration);
    }

    public void setEmailRegistration(String email) {
        this.emailRegistration = email;
        notifyPropertyChanged(BR.emailRegistration); //Для мгновенного обновления пользовательского интерфейса
    }

    public void setPasswordRegistration(String password) {
        this.passwordRegistration = password;
        notifyPropertyChanged(BR.passwordRegistration);
    }

    public void setErrorInputRegistration(String errorInput) {
        this.errorInputRegistration = errorInput;
        notifyPropertyChanged(BR.errorInputRegistration);
    }

}
