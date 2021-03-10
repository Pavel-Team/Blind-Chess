/**Интерфейс шахматной фигуры*/
package com.example.blindchess.ui.game.figure;


import com.example.blindchess.ui.game.CellBoard;

public interface Figure {
    public String getTeam();                                                                      //Получение цвета команды (WHITE ИЛИ BLACK)
    public CellBoard[][] calculateOfCanMove(CellBoard[][] board, int x, int y);                   //Расчет возможного хода фигуры (видимость фигуры) по заданному состоянию доски и данным координатам фигуры
    public CellBoard[][] calculateOfVisibility(CellBoard[][] board, int x, int y);                //Расчет видимости клеток данной фигурой (какие клетки фигура видит). Используется в потоке ThreadCalculateVisibility
}
