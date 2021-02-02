/**Поток, инициализирующий первоначальное расстановку доски CellBoard[8][8]*/
package com.example.blindchess.ui.game.threads.calculation;

import android.os.AsyncTask;

import com.example.blindchess.ui.game.CellBoard;
import com.example.blindchess.ui.game.figure.Elphant;
import com.example.blindchess.ui.game.figure.Horse;
import com.example.blindchess.ui.game.figure.King;
import com.example.blindchess.ui.game.figure.Officer;
import com.example.blindchess.ui.game.figure.Pawn;
import com.example.blindchess.ui.game.figure.Queen;


public class ThreadInitBoard extends AsyncTask<Void, Void, CellBoard[][]> {

    private CellBoard[][] board = new CellBoard[8][8]; //Игровая доска
    private String team;                               //Цвет команды игрока (WHITE или BLACK)


    /**Конструктор класса
     * На вход принимает 1 параметр:
     * String team - увет команды игрока (WHITE или BLACK)*/
    public ThreadInitBoard(String team){
        this.team = team;
    }


    /**Действия, выполняющиеся в потоке (первоначальная инициализация доски)*/
    @Override
    protected CellBoard[][] doInBackground(Void... voids) {
        //Если команда белая
        if (team == "WHITE") {
            //Заполнение черной команды
            board[0][0] = board[0][7] = new CellBoard(false, true, new Elphant());  //Черные слоны
            board[0][1] = board[0][6] = new CellBoard(false, true, new Horse());    //Черные кони
            board[0][2] = board[0][5] = new CellBoard(false, true, new Officer());  //Черные офицеры
            board[0][3] = new CellBoard(false, true, new Queen());                  //Черная королева
            board[0][4] = new CellBoard(false, true, new King());                   //Черный король
            for (int i = 0; i < 8; i++)
                board[1][i] = new CellBoard(false, true, new Pawn());               //Черные пешки

            //Заполнение пустых клеток
            for (int i = 2; i < 4; i++) {
                for (int j = 0; j < 8; j++)
                    board[i][j] = new CellBoard(false, false, null);
            }
            for (int i = 4; i < 6; i++) {
                for (int j = 0; j < 8; j++)
                    board[i][j] = new CellBoard(true, false, null);
            }

            //Заполнение белой команды
            board[7][0] = board[7][7] = new CellBoard(true, true, new Elphant());   //Белые слоны
            board[7][1] = board[7][6] = new CellBoard(true, true, new Horse());     //Белые кони
            board[7][2] = board[7][5] = new CellBoard(true, true, new Officer());   //Белые офицеры
            board[7][3] = new CellBoard(true, true, new Queen());                   //Белая королева
            board[7][4] = new CellBoard(true, true, new King());                    //Белый король
            for (int i = 0; i < 8; i++)
                board[6][i] = new CellBoard(true, true, new Pawn());                //Белые пешки

        } else {
        //Если команда черная
            //Заполнение черной команды
            board[0][0] = board[0][7] = new CellBoard(true, true, new Elphant());  //Черные слоны
            board[0][1] = board[0][6] = new CellBoard(true, true, new Horse());    //Черные кони
            board[0][2] = board[0][5] = new CellBoard(true, true, new Officer());  //Черные офицеры
            board[0][3] = new CellBoard(true, true, new Queen());                  //Черная королева
            board[0][4] = new CellBoard(true, true, new King());                   //Черный король
            for (int i = 0; i < 8; i++)
                board[1][i] = new CellBoard(true, true, new Pawn());               //Черные пешки

            //Заполнение пустых клеток
            for (int i = 2; i < 4; i++) {
                for (int j = 0; j < 8; j++)
                    board[i][j] = new CellBoard(true, false, null);
            }
            for (int i = 4; i < 6; i++) {
                for (int j = 0; j < 8; j++)
                    board[i][j] = new CellBoard(false, false, null);
            }

            //Заполнение белой команды
            board[7][0] = board[7][7] = new CellBoard(false, true, new Elphant());   //Белые слоны
            board[7][1] = board[7][6] = new CellBoard(false, true, new Horse());     //Белые кони
            board[7][2] = board[7][5] = new CellBoard(false, true, new Officer());   //Белые офицеры
            board[7][3] = new CellBoard(false, true, new Queen());                   //Белая королева
            board[7][4] = new CellBoard(false, true, new King());                    //Белый король
            for (int i = 0; i < 8; i++)
                board[6][i] = new CellBoard(false, true, new Pawn());                //Белые пешки
        }

        return board;
    }


    /**Функция, возвращающая ответ - первоначально проинициализорванную доску CellBoard[8][8]*/
    @Override
    protected void onPostExecute(CellBoard[][] cellBoards) {
        super.onPostExecute(cellBoards);
    }
}