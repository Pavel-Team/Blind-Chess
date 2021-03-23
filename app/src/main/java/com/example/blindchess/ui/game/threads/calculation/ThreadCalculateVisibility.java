/**Поток для расчета видимости клеток фигурами данной команды*/
package com.example.blindchess.ui.game.threads.calculation;

import android.os.AsyncTask;

import com.example.blindchess.ui.game.CellBoard;

public class ThreadCalculateVisibility extends AsyncTask<Void, Void, CellBoard[][]> {

    private CellBoard[][] board; //Игровая доска
    private String team;         //Цвет команды игрока (WHITE или BLACK)


    /**Конструктор класса
     * На вход принимает 2 параметра:
     * CellBoard[][] board - игровая доска
     * String team - цвет команды игрока (WHITE или BLACK)*/
    public ThreadCalculateVisibility(CellBoard[][] board, String team) {
        this.board = board;
        this.team = team;
    }


    /**Действия, выполняющиеся в потоке (расчет видимости клеток фигурами данной команды)*/
    @Override
    protected CellBoard[][] doInBackground(Void... voids) {

        //Обнуляем видимость всего поля (МБ ОПТИМИЗИРОВАТЬ)
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                board[i][j].setVisible(false);

        //Проходимся по каждой клетке поля в поиске фигур данной команды
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board[i][j].isBusy() && board[i][j].getFigure().getTeam().equals(team))
                    board = board[i][j].getFigure().calculateOfVisibility(board, j, i);


        return board;
    }


    /**Функция, возвращающая ответ - измененную (с учетом видимости клеток) доску board*/
    @Override
    protected void onPostExecute(CellBoard[][] board) {
        super.onPostExecute(board);
    }
}
