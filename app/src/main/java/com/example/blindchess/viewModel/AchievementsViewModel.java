/**ViewModel для окна со списком достижений FragmentAchievements*/
package com.example.blindchess.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.blindchess.model.Achievement;
import com.example.blindchess.service.AchievementsService;

import java.util.ArrayList;


public class AchievementsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Achievement>> liveDataListAchievements; //LiveData для модели одного пункта Achievement
    private static AchievementsService achievementsService;                   //Объект для работы с бизнес-логикой сущности Achievement


    /**Конуструктор класса
     * Внутри достает из SQLite весь список достижений, полученных пользователем*/
    public AchievementsViewModel(){
        if (achievementsService == null)
            achievementsService = new AchievementsService();

        liveDataListAchievements = new MutableLiveData<>(achievementsService.getListAchievements());
    }


    public MutableLiveData<ArrayList<Achievement>> getLiveData() {
        return liveDataListAchievements;
    }

}
