/**Класс, содержащий всю бизнес-логику для окна FragmentAchievements*/
package com.example.blindchess.service;

import com.example.blindchess.model.Achievement;
import com.example.blindchess.repository.SQLiteRepository;
import com.example.blindchess.ui.activity.MainActivity;

import java.util.ArrayList;


public class AchievementsService {

    private static SQLiteRepository sqLiteRepository; //Объект для работы с SQLite


    /**Конструктор класса*/
    public AchievementsService(){
        if (sqLiteRepository == null)
            sqLiteRepository = new SQLiteRepository();
    }


    /**Метод возвращающий список всех достижений*/
    public ArrayList<Achievement> getListAchievements(){
        return sqLiteRepository.getAchievementsFromSQLite(MainActivity.getViewModel().getLiveData().getValue().getUser_id());
    }
}
