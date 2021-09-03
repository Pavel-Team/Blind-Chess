/**Класс клетки доски */
package com.example.blindchess.game;

import com.example.blindchess.game.figure.Figure;


public class CellBoard {

    private boolean isVisible; //Видимая (исследованная) ли клетка
    private boolean isBusy;    //Есть ли на данной клетке фигура (занята ли данная клетка)
    private Figure figure;     //Объект фигуры (её тип), если она есть в данной клетке
    private boolean isCanMove; //Можно ли походить выбранной фигурой на данную клетку (нужна для отрисовки спрайтов хода/удара)


    /**Конструктор класса
     * На вход принимает 3 параметра:
     * boolean isVisible - видимо ли содержимое клетки для игрока (исследована ли она)
     * boolean isBusy - занята ли данная клетка какой-нибудь фигурой
     * Figure figure - объект фигуры (её тип), если она есть в данной клетке
     * Внутри также инициализирует перемнную isCanMove = false по умолчанию
     * (т.к. первоначально ни одна клетка не выбрана, следовательно ходить на любую клетку поля еще нельзя)*/
    public CellBoard(boolean isVisible, boolean isBusy, Figure figure){
        this.isVisible = isVisible;
        this.isBusy = isBusy;
        this.figure = figure;
        isCanMove = false;  //Первоначально фигура не выбрана, значит походить на любую клетку поля нельзя
    }


    /**____________________ GETTER'Ы и SETTER'Ы ____________________*/
    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure){
        this.figure = figure;
    }

    public boolean isCanMove() {
        return isCanMove;
    }

    public void setCanMove(boolean canMove) {
        this.isCanMove = canMove;
    }
    /**_____________________________________________________________*/
}
