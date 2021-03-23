/**Класс короля */
package com.example.blindchess.ui.game.figure;

import com.example.blindchess.ui.game.CellBoard;

public class King implements Figure {

    private String team; //Цвет команды фигуры (WHITE или BLACK)


    /**Конструктор класса
     * На вход принимает 1 параметр:
     * String team - цвет команды (WHITE или BLACK)*/
    public King(String team){
        this.team = team;
    }


    @Override
    public String getTeam() {
        return team;
    }


    /**Функция рассчета возможности хода данной фигуры на все доступные ей клетки
     * На вход принимает 3 параметра:
     * CellBoard[][] board - текущее состояние доски
     * int x - положение данной фигуры по оси x
     * int y - положение данной фигуры по оси y
     * Внутри происходит рассчет возможности хода для данной фигуры, изменение (в соответствии с текущим состоянием доски)
     * параметра isCanMove у клеток доски с учетом данной фигуры, возврат нового состояния доски
     * Функция возвращает CellBoard[][] board - новое состояние доски, с учетом возможности хода данной фигуры (isCanMove)*/
    @Override
    public CellBoard[][] calculateOfCanMove(CellBoard[][] board, int x, int y) {

        //Проверка на ход вперед
        if (y != 0 && (!board[y-1][x].isBusy() || !board[y-1][x].getFigure().getTeam().equals(team)))
            board[y-1][x].setCanMove(true);

        //Проверка на ход вперед и вправо
        if (y != 0 && x != 7 && (!board[y-1][x+1].isBusy() || !board[y-1][x+1].getFigure().getTeam().equals(team)))
            board[y-1][x+1].setCanMove(true);

        //Проверка на ход вправо
        if (x != 7 && (!board[y][x+1].isBusy() || !board[y][x+1].getFigure().getTeam().equals(team)))
            board[y][x+1].setCanMove(true);

        //Проверка на ход вниз и вправо
        if (y != 7 && x != 7 && (!board[y+1][x+1].isBusy() || !board[y+1][x+1].getFigure().getTeam().equals(team)))
            board[y+1][x+1].setCanMove(true);

        //Проверка на ход вниз
        if (y != 7 && (!board[y+1][x].isBusy() || !board[y+1][x].getFigure().getTeam().equals(team)))
            board[y+1][x].setCanMove(true);

        //Проверка на ход вниз и влево
        if (y != 7 && x != 0 && (!board[y+1][x-1].isBusy() || !board[y+1][x-1].getFigure().getTeam().equals(team)))
            board[y+1][x-1].setCanMove(true);

        //Проверка на ход влево
        if (x != 0 && (!board[y][x-1].isBusy() || !board[y][x-1].getFigure().getTeam().equals(team)))
            board[y][x-1].setCanMove(true);

        //Проверка на ход вперед и влево
        if (y != 0 && x != 0 && (!board[y-1][x-1].isBusy() || !board[y-1][x-1].getFigure().getTeam().equals(team)))
            board[y-1][x-1].setCanMove(true);

        return board;
    }


    /**Функция рассчета видимости клеток поля данной фигурой
     * На вход принимает 3 параметра:
     * CellBoard[][] board - текущее состояние доски
     * int x - положение данной фигуры по оси x
     * int y - положение данной фигуры по оси y
     * Внутри происходит рассчет видимости клеток поля данной фигурой, изменение (в соответствии с текущим состоянием доски)
     * параметра isVisibility у клеток доски с учетом видимости данной фигуры, возврат нового состояния доски
     * Функция возвращает CellBoard[][] board - новое состояние доски, с учетом видимости клеток поля данной фигуры (isVisibility)*/
    @Override
    public CellBoard[][] calculateOfVisibility(CellBoard[][] board, int x, int y) {

        //Учитываем верх
        if (y != 0)
            board[y-1][x].setVisible(true);

        //Учитиваем верх и право
        if (y != 0 && x != 7)
            board[y-1][x+1].setVisible(true);

        //Учитиваем право
        if (x != 7)
            board[y][x+1].setVisible(true);

        //Учитиваем низ и право
        if (y != 7 && x != 7)
            board[y+1][x+1].setVisible(true);

        //Учитиваем низ
        if (y != 7)
            board[y+1][x].setVisible(true);

        //Учитиваем низ и лево
        if (y != 7 && x != 0)
            board[y+1][x-1].setVisible(true);

        //Учитиваем лево
        if (x != 0)
            board[y][x-1].setVisible(true);

        //Учитиваем верх и лево
        if (y != 0 && x != 0)
            board[y-1][x-1].setVisible(true);

        //Учитываем сами себя
        board[y][x].setVisible(true);

        return board;
    }

}
