/**ViewModel для окна регистрации RegistrationFragment*/
package com.example.blindchess.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.blindchess.model.Registration;
import com.example.blindchess.service.RegistrationService;

import java.util.regex.Pattern;


public class RegistrationViewModel extends ViewModel {

    private MutableLiveData<Registration> liveDataRegistration; //LiveData для модели Registration
    private static RegistrationService registrationService;     //Объект для работы с бизнес-логикой сущности Registration


    /**Конструктор класса
     * Внутри создается пустой объект LiveData<Registration> и RegistrationService*/
    public RegistrationViewModel() {
        liveDataRegistration = new MutableLiveData<>();
        liveDataRegistration.setValue(new Registration());

        if (registrationService == null)
            registrationService = new RegistrationService();
    }


    /**Метод нажатия на кнопку "Зарегистрироваться"
     * В случае успеха вернет - ACCOUNT_CREATED
     * Если данные введены неправильно - вернет ERROR_INPUT
     * Если пользователь с таким именем существует, вернет - ERROR_NAME
     * Если пользователь с таким логином существет, вернет - ERROR_EMAIL
     * Иначе - ERROR_ACCOUNT_CREATED*/
    public String onClickButtonRegistration() {
        Registration model = liveDataRegistration.getValue();
        String name = model.getNameRegistration();
        String email = model.getEmailRegistration();
        String password = model.getPasswordRegistration();

        Pattern patternNick = Pattern.compile("^[А-Яа-яa-zA-Z_0-9]+$");       //Регулярное выражение для EditText имени
        Pattern patternLoginAndPassword = Pattern.compile("^[a-zA-Z_0-9]+$"); //Регулярное выражение для EditText логина и пароля

        //Отправляем запрос, только если все данные введены правильно
        if(patternNick.matcher(name).matches() && name.length() >= 4 &&
                patternLoginAndPassword.matcher(email).matches() && email.length() >= 6 &&
                patternLoginAndPassword.matcher(password).matches() && password.length() >= 6) {
            return registrationService.registrationUser(name, email, password);
        } else {
            return "ERROR_INPUT";
        }
    }


    /**Метод нажатия на кнопку "Войти как гость"*/
    public void onClickButtonLoginGuest() {
        registrationService.loginGuest();
    }


    /**Метод нажатия на кнопку "Войти через Google"*/
    public String onClickButtonLoginGoogle() {
        return null;
    }


    public LiveData<Registration> getLiveData() {

        System.out.println("RegistrationLiveData" +
                "\nname: " + liveDataRegistration.getValue().getNameRegistration() +
                "\nemail: " + liveDataRegistration.getValue().getEmailRegistration() +
                "\npassword: " + liveDataRegistration.getValue().getPasswordRegistration());
        System.out.println("--------------");

        return liveDataRegistration;
    }

}
