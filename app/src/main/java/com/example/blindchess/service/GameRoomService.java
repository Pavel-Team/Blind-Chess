/**Класс, содержащий все запросы к БД и на сервер для модели GameRoom*/
package com.example.blindchess.service;

import com.example.blindchess.repository.GameRoomRepository;


public class GameRoomService {

    private static GameRoomRepository gameRoomRepository; //Объект для работы с запросами к БД


    /**Конструктор класса*/
    public GameRoomService(){
        if (gameRoomRepository == null)
            gameRoomRepository = new GameRoomRepository();
    }

}
