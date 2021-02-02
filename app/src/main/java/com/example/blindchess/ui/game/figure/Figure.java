/**Интерфейс шахматной фигуры*/
package com.example.blindchess.ui.game.figure;

/**Перечисления возможного вида шахматной фигуры*/
enum Type {
    PAWN,
    ELEPHANT,
    HORSE,
    OFFICER,
    QUEEN,
    KING
}


public interface Figure {
    public Type getType(); //Получение типа фигуры
    public void setType(); //Установка типа фигуры
    public int[] calculateOfPossibleMove(); //Расчет возможного хода фигуры (видимость фигуры)
}
