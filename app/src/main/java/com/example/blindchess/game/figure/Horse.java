/**Класс коня */
package com.example.blindchess.game.figure;

import com.example.blindchess.game.CellBoard;

public class Horse implements Figure {

    private String team; //Цвет команды фигуры (WHITE или BLACK)


    /**Конструктор класса
     * На вход принимает 1 параметр:
     * String team - цвет команды (WHITE или BLACK)*/
    public Horse(String team){
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

        //Вперед и влево
        if (x > 0 && y > 1 && (!board[y-2][x-1].isBusy() || !board[y-2][x-1].getFigure().getTeam().equals(team)))
            board[y-2][x-1].setCanMove(true);

        //Вперед и вправо
        if (x < 7 && y > 1 && (!board[y-2][x+1].isBusy() || !board[y-2][x+1].getFigure().getTeam().equals(team)))
            board[y-2][x+1].setCanMove(true);

        //Вправо и вперед
        if (x < 6 && y > 0 && (!board[y-1][x+2].isBusy() || !board[y-1][x+2].getFigure().getTeam().equals(team)))
            board[y-1][x+2].setCanMove(true);

        //Вправо и вниз
        if (x < 6 && y < 7 && (!board[y+1][x+2].isBusy() || !board[y+1][x+2].getFigure().getTeam().equals(team)))
            board[y+1][x+2].setCanMove(true);

        //Вниз и вправо
        if (x < 7 && y < 6 && (!board[y+2][x+1].isBusy() || !board[y+2][x+1].getFigure().getTeam().equals(team)))
            board[y+2][x+1].setCanMove(true);

        //Вниз и влево
        if (x > 0 && y < 6 && (!board[y+2][x-1].isBusy() || !board[y+2][x-1].getFigure().getTeam().equals(team)))
            board[y+2][x-1].setCanMove(true);

        //Влево и вниз
        if (x > 1 && y < 7 && (!board[y+1][x-2].isBusy() || !board[y+1][x-2].getFigure().getTeam().equals(team)))
            board[y+1][x-2].setCanMove(true);

        //Влево и вперед
        if (x > 1 && y > 0 && (!board[y-1][x-2].isBusy() || !board[y-1][x-2].getFigure().getTeam().equals(team)))
            board[y-1][x-2].setCanMove(true);

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

        //Вперед и влево
        if (x > 0 && y > 1)
            board[y-2][x-1].setVisible(true);

        //Вперед и вправо
        if (x < 7 && y > 1)
            board[y-2][x+1].setVisible(true);

        //Вправо и вперед
        if (x < 6 && y > 0)
            board[y-1][x+2].setVisible(true);

        //Вправо и вниз
        if (x < 6 && y < 7)
            board[y+1][x+2].setVisible(true);

        //Вниз и вправо
        if (x < 7 && y < 6)
            board[y+2][x+1].setVisible(true);

        //Вниз и влево
        if (x > 0 && y < 6)
            board[y+2][x-1].setVisible(true);

        //Влево и вниз
        if (x > 1 && y < 7)
            board[y+1][x-2].setVisible(true);

        //Влево и вперед
        if (x > 1 && y > 0)
            board[y-1][x-2].setVisible(true);

        //Учитываем сами себя
        board[y][x].setVisible(true);

        return board;
    }

}
