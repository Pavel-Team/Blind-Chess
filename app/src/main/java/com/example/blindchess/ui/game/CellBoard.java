/**Класс клетки доски */
package com.example.blindchess.ui.game;

import com.example.blindchess.ui.game.figure.Figure;


public class CellBoard {

    private boolean isVisible; //Видимая (исследованная) ли клетка
    private boolean isBusy;    //Есть ли на данной клетке фигура (занята ли данная клетка)
    private Figure figure;     //Объект фигуры (её тип), если она есть в данной клетке


    /**Конструктор класса
     * На вход принимает 3 параметра:
     * boolean isVisible - видимо ли содержимое клетки для игрока (исследована ли она)
     * boolean isBusy - занята ли данная клетка какой-нибудь фигурой
     * Figure figure - объект фигуры (её тип), если она есть в данной клетке*/
    public CellBoard(boolean isVisible, boolean isBusy, Figure figure){
        this.isVisible = isVisible;
        this.isBusy = isBusy;
        this.figure = figure;
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
    /**_____________________________________________________________*/
}
