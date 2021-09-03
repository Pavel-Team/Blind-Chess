/**Поток, инициализирующий первоначальное расстановку доски CellBoard[8][8]*/
package com.example.blindchess.game.threads.calculation;

import android.os.AsyncTask;

import com.example.blindchess.game.CellBoard;
import com.example.blindchess.game.figure.Elephant;
import com.example.blindchess.game.figure.Horse;
import com.example.blindchess.game.figure.King;
import com.example.blindchess.game.figure.Officer;
import com.example.blindchess.game.figure.Pawn;
import com.example.blindchess.game.figure.Queen;


public class ThreadInitBoard extends AsyncTask<Void, Void, CellBoard[][]> {

    private CellBoard[][] board = new CellBoard[8][8]; //Игровая доска
    private String team;                               //Цвет команды игрока (WHITE или BLACK)


    /**Конструктор класса
     * На вход принимает 1 параметр:
     * String team - цвет команды игрока (WHITE или BLACK)*/
    public ThreadInitBoard(String team){
        this.team = team;
    }


    /**Действия, выполняющиеся в потоке (первоначальная инициализация доски)*/
    @Override
    protected CellBoard[][] doInBackground(Void... voids) {
        //Если команда белая
        if (team == "WHITE") {
            //Заполнение черной команды
            //Черные слоны
            board[0][0] = new CellBoard(false, true, new Elephant("BLACK"));
            board[0][7] = new CellBoard(false, true, new Elephant("BLACK"));
            //Черные кони
            board[0][1] = new CellBoard(false, true, new Horse("BLACK"));
            board[0][6] = new CellBoard(false, true, new Horse("BLACK"));
            //Черные офицеры
            board[0][2] = new CellBoard(false, true, new Officer("BLACK"));
            board[0][5] = new CellBoard(false, true, new Officer("BLACK"));
            //Черная королева
            board[0][3] = new CellBoard(false, true, new Queen("BLACK"));
            //Черный король
            board[0][4] = new CellBoard(false, true, new King("BLACK"));
            //Черные пешки
            for (int i = 0; i < 8; i++)
                board[1][i] = new CellBoard(false, true, new Pawn("BLACK"));

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
            //Белые слоны
            board[7][0] = new CellBoard(true, true, new Elephant("WHITE"));
            board[7][7] = new CellBoard(true, true, new Elephant("WHITE"));
            //Белые кони
            board[7][1] = new CellBoard(true, true, new Horse("WHITE"));
            board[7][6] = new CellBoard(true, true, new Horse("WHITE"));
            //Белые офицеры
            board[7][2] = new CellBoard(true, true, new Officer("WHITE"));
            board[7][5] = new CellBoard(true, true, new Officer("WHITE"));
            //Белая королева
            board[7][3] = new CellBoard(true, true, new Queen("WHITE"));
            //Белый король
            board[7][4] = new CellBoard(true, true, new King("WHITE"));
            //Белые пешки
            for (int i = 0; i < 8; i++)
                board[6][i] = new CellBoard(true, true, new Pawn("WHITE"));

        } else {
        //Если команда черная
            //Заполнение черной команды
            //Черные слоны
            board[7][0] = new CellBoard(true, true, new Elephant("BLACK"));
            board[7][7] = new CellBoard(true, true, new Elephant("BLACK"));
            //Черные кони
            board[7][1] = new CellBoard(true, true, new Horse("BLACK"));
            board[7][6] = new CellBoard(true, true, new Horse("BLACK"));
            //Черные офицеры
            board[7][2] = new CellBoard(true, true, new Officer("BLACK"));
            board[7][5] = new CellBoard(true, true, new Officer("BLACK"));
            //Черная королева
            board[7][3] = new CellBoard(true, true, new Queen("BLACK"));
            //Черный король
            board[7][4] = new CellBoard(true, true, new King("BLACK"));
            //Черные пешки
            for (int i = 0; i < 8; i++)
                board[6][i] = new CellBoard(true, true, new Pawn("BLACK"));

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
            //Белые слоны
            board[0][0] = new CellBoard(false, true, new Elephant("WHITE"));
            board[0][7] = new CellBoard(false, true, new Elephant("WHITE"));
            //Белые кони
            board[0][1] = new CellBoard(false, true, new Horse("WHITE"));
            board[0][6] = new CellBoard(false, true, new Horse("WHITE"));
            //Белые офицеры
            board[0][2] = new CellBoard(false, true, new Officer("WHITE"));
            board[0][5] = new CellBoard(false, true, new Officer("WHITE"));
            //Белая королева
            board[0][3] = new CellBoard(false, true, new Queen("WHITE"));
            //Белый король
            board[0][4] = new CellBoard(false, true, new King("WHITE"));
            //Белые пешки
            for (int i = 0; i < 8; i++)
                board[1][i] = new CellBoard(false, true, new Pawn("WHITE"));
        }

        return board;
    }


    /**Функция, возвращающая ответ - первоначально проинициализорванную доску CellBoard[8][8]*/
    @Override
    protected void onPostExecute(CellBoard[][] cellBoards) {
        super.onPostExecute(cellBoards);
    }
}
