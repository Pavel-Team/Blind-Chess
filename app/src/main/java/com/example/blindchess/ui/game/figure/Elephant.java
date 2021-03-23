/**Класс слона */
package com.example.blindchess.ui.game.figure;

import com.example.blindchess.ui.game.CellBoard;

public class Elephant implements Figure {

    private String team; //Цвет команды фигуры (WHITE или BLACK)


    /**Конструктор класса
     * На вход принимает 1 параметр:
     * String team - цвет команды (WHITE или BLACK)*/
    public Elephant(String team){
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
        int i; //Переменная для счетчика циклов

        //Проверка на ход вверх
        i = y;
        while (i != 0) {
            if (!board[i-1][x].isBusy()) {
                board[i-1][x].setCanMove(true);
            } else if (!board[i-1][x].getFigure().getTeam().equals(team)){
                board[i-1][x].setCanMove(true);
                break;
            } else {
                break;
            }
            i--;
        }

        //Проверка на ход вниз
        i = y;
        while (i != 7) {
            if (!board[i+1][x].isBusy()) {
                board[i+1][x].setCanMove(true);
            } else if (!board[i+1][x].getFigure().getTeam().equals(team)){
                board[i+1][x].setCanMove(true);
                break;
            } else {
                break;
            }
            i++;
        }

        //Проверка на ход влево
        i = x;
        while (i != 0) {
            if (!board[y][i-1].isBusy()) {
                board[y][i-1].setCanMove(true);
            } else if (!board[y][i-1].getFigure().getTeam().equals(team)){
                board[y][i-1].setCanMove(true);
                break;
            } else {
                break;
            }
            i--;
        }

        //Проверка на ход вправо
        i = x;
        while (i != 7) {
            if (!board[y][i+1].isBusy()) {
                board[y][i+1].setCanMove(true);
            } else if (!board[y][i+1].getFigure().getTeam().equals(team)){
                board[y][i+1].setCanMove(true);
                break;
            } else {
                break;
            }
            i++;
        }

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
        int i; //Переменная для счетчика циклов

        //Учитываем вверх
        i = y;
        while (i != 0) {
            if (!board[i-1][x].isBusy()) {
                board[i-1][x].setVisible(true);
            } else {
                board[i-1][x].setVisible(true);
                break;
            }
            i--;
        }

        //Учитываем низ
        i = y;
        while (i != 7) {
            if (!board[i+1][x].isBusy()) {
                board[i+1][x].setVisible(true);
            } else {
                board[i+1][x].setVisible(true);
                break;
            }
            i++;
        }

        //Учитываем лево
        i = x;
        while (i != 0) {
            if (!board[y][i-1].isBusy()) {
                board[y][i-1].setVisible(true);
            } else {
                board[y][i-1].setVisible(true);
                break;
            }
            i--;
        }

        //Учитываем право
        i = x;
        while (i != 7) {
            if (!board[y][i+1].isBusy()) {
                System.out.println("ПРАВО");
                board[y][i+1].setVisible(true);
            } else {
                board[y][i+1].setVisible(true);
                break;
            }
            i++;
        }

        //Учитываем сами себя
        board[y][x].setVisible(true);

        return board;
    }

}
