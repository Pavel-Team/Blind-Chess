/**Класс, содержащий всю бизнес-логику для модели Login*/
package com.example.blindchess.service;

import com.example.blindchess.model.User;
import com.example.blindchess.repository.LoginRepository;
import com.example.blindchess.repository.SQLiteRepository;

public class LoginService {

    private static LoginRepository loginRepository; //Объект для работы с запросами к БД


    /**Конструктор класса*/
    public LoginService(){
        if (loginRepository == null)
            loginRepository = new LoginRepository();
    }


    /**Метод входа пользователя в аккаунт
     * На вход принимает 2 параметра:
     * String email - email пользователя
     * String password - пароль пользователя
     * Внутри отправляет запрос на сервер на проверху входа
     * В случае успеха вернет объект пользователя, если пароль неверный - вернет ERROR_PASSWORD, если пользователя с таким email не существует - ERROR_EMAIL*/
    public String login(String email, String password) {
        //User response = LoginRepository.login(email, password);
        //if (response.equals(...) {
        //addToSQLite(user)
        //return "LOGIN"
        //else
        //return "ERROR";
        System.out.println(email + password);
        return "null ВРЕМЕННО";
    }

}
