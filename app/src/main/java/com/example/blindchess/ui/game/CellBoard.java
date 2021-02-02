/**Класс клетки доски */
package com.example.blindchess.ui.game;

import com.example.blindchess.ui.game.figure.Figure;


//Перечисления возможных статусов клетки
enum Status {
    UNKNOWN, //Неизвестная клетка
    EMPTY,   //Пустая клетка
    FIGURE   //Клетка с фигурой
}

//Перечисления возможного цвета клетки/фигуры
enum Color {
    BLACK,
    WHITE
}


public class CellBoard {

    private Status status;  //Статус клетки
    private Color color;    //Цвет клетки/фигуры
    private Figure figure;  //Объект фигуры (её тип), если она есть в данной клетке


    /**Конструктор класса
     * На вход принимает 3 параметра:
     * Status status - статус клетки
     * Color color - цвет клетки/фигуры
     * Figure figure - объект фигуры (её тип), если она есть в данной клетке*/
    public CellBoard(Status status, Color color, Figure figure){
        this.status = status;
        this.color = color;
        this.figure = figure;
    }


    /**____________________ GETTER'Ы и SETTER'Ы ____________________*/
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    /**_____________________________________________________________*/
}
