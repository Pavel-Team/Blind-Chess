/**Класс королевы */
package com.example.blindchess.ui.game.figure;

import com.example.blindchess.ui.game.CellBoard;

public class Queen implements Figure {

    private String team; //Цвет команды фигуры (WHITE или BLACK)


    /**Конструктор класса
     * На вход принимает 1 параметр:
     * String team - цвет команды (WHITE или BLACK)*/
    public Queen(String team){
        this.team = team;
    }


    @Override
    public String getTeam() {
        return team;
    }


    @Override
    public CellBoard[][] calculateOfCanMove(CellBoard[][] board, int x, int y) {
        return new CellBoard[0][];
    }


    @Override
    public CellBoard[][] calculateOfVisibility(CellBoard[][] board, int x, int y) {
        //Учитываем сами себя
        board[y][x].setVisible(true);

        return board;
    }

}
