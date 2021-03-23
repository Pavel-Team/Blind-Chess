/**Класс пешки */
package com.example.blindchess.ui.game.figure;

import com.example.blindchess.ui.game.CellBoard;

public class Pawn implements Figure {

    private String team;     //Цвет команды фигуры (WHITE или BLACK)


    /**Конструктор класса
     * На вход принимает 1 параметр:
     * String team - цвет команды (WHITE или BLACK)*/
    public Pawn(String team){
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

        //Проверка на ход вперед (если нет фигуры - то можно идти). *P.S. пешка не может ходить с координаты y = 0, т.к. она автоматически превратится в другую фигуру
        if (!board[y-1][x].isBusy())
            board[y-1][x].setCanMove(true);

        //Проверка на удар слева
        if (x != 0 && board[y-1][x-1].isBusy() && !board[y-1][x-1].getFigure().getTeam().equals(team))
            board[y-1][x-1].setCanMove(true);

        //Проверка на удар справа
        if (x != 7 && board[y-1][x+1].isBusy() && !board[y-1][x+1].getFigure().getTeam().equals(team))
            board[y-1][x+1].setCanMove(true);

        //Если пешка еще не ходила - учитываем, что она может пойти на 2 клетки вперед
        if (y == 6 && !board[y-1][x].isBusy())
            board[y-2][x].setCanMove(true);

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

        //Делаем видимыми клетки после хода (когда пешка перебирается в последнем ряду, то нет необходимости смотреть за поле)
        if (y != 0) {
            //Учитываем перед
            board[y-1][x].setVisible(true);
            if (y == 6 && !board[y-1][x].isBusy())
                board[y-2][x].setVisible(true);
            //Учитываем левую сторону
            if (x != 0)
                board[y-1][x-1].setVisible(true);
            //Учитываем правую сторону
            if (x != 7)
                board[y-1][x+1].setVisible(true);
        }
        //Учитываем сами себя
        board[y][x].setVisible(true);

        return board;
    }

}
