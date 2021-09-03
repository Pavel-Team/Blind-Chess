/**Класс, содержащий все запросы к БД, связанные с регистрацией*/
package com.example.blindchess.repository;


public class RegistrationRepository {

    private SQLiteRepository sqLiteRepository = new SQLiteRepository(); //Репозиторий для работы с БД SQLite


    /**Метод регистрации нового пользователя
     * На вход принимает 3 параметра:
     * String name - имя пользователя
     * String email - email пользователя
     * String password - пароль пользователя
     * Внутри отправляет запрос на сервер
     * В случае успеха вернет - ACCOUNT_CREATED и создаст аккаунт в SQLite
     * Если пользователь с таким именем существует, вернет - ERROR_NAME
     * Если пользователь с таким логином существет, вернет - ERROR_EMAIL
     * Иначе - ERROR_ACCOUNT_CREATED*/
    public String registrationUser(String name, String email, String password) {
        //Запрос...
        //sqLiteRepository.addNewUserToSQLite(1, "PASHA");
        return "ACCOUNT_CREATED";
    }

}
