/**Класс офицера */
package com.example.blindchess.ui.game.figure;

public class Officer implements Figure {
    @Override
    public Type getType() {
        return null;
    }

    @Override
    public void setType() {

    }

    @Override
    public int[] calculateOfPossibleMove() {
        return new int[0];
    }
}
