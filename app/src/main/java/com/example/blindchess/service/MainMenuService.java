/**Класс, содержащий всю бизнес-логику для фрагмента MainMenuFragment*/
package com.example.blindchess.service;

import com.example.blindchess.repository.SQLiteRepository;

public class MainMenuService {

    private static SQLiteRepository sqLiteRepository; //Объект для работы с SQLite


    /**Конструктор класса*/
    public MainMenuService(){
        if (sqLiteRepository == null)
            sqLiteRepository = new SQLiteRepository();
    }


    /**Метод выхода из аккаунта пользователя с заданным id
     * На вход принимает 1 параметр:
     * int id - id заданного пользователя, который выходит из системы
     * Внутри устанавивает соответствующее поле в SQLite = 0, если пользователем был гостем. Если нет - удаляется запись*/
    public void quitFromAccount(int id){
        if (id == 1)
            sqLiteRepository.setIsLogin(id, 0);
        else
            sqLiteRepository.deleteUser(id);
    }

}
