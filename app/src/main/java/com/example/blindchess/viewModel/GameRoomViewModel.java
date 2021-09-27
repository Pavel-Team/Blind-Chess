/**ViewModel для окна игровой команты FragmentGameRoom*/
package com.example.blindchess.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.blindchess.model.Achievement;
import com.example.blindchess.model.GameRoom;
import com.example.blindchess.model.Product;
import com.example.blindchess.model.User;
import com.example.blindchess.service.GameRoomService;
import com.example.blindchess.ui.activity.MainActivity;


public class GameRoomViewModel extends ViewModel {

    private MutableLiveData<GameRoom> liveDataGameRoom; //LiveData для модели GameRoom
    private static GameRoomService gameRoomService;     //Объект для работы с бизнес-логикой сущности GameRoom


    /**Конструктор класса
     * Внутри создается пустой объект LiveData<Login> и loginService*/
    public GameRoomViewModel() {
        if (gameRoomService == null)
            gameRoomService = new GameRoomService();

        GameRoom model = new GameRoom();
        model.setMyUser(MainActivity.getViewModel().getLiveData().getValue());
        model.setTime(60);
        //Запрос на сервер за втормы игроком
        //...
        model.setEnemyUser(MainActivity.getViewModel().getLiveData().getValue()); //ВРЕМЕННО
        model.setMyMove(true); //ВРЕМЕННО

        liveDataGameRoom = new MutableLiveData<>();
        liveDataGameRoom.setValue(model);
    }


    public MutableLiveData<GameRoom> getLiveData() {
        return liveDataGameRoom;
    }
}
