/**Класс View, создающий доску в игровой комнате FragmentGameRoom*/
package com.example.blindchess.ui.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.example.blindchess.ui.game.threads.calculation.ThreadInitBoard;
import com.example.blindchess.ui.game.threads.sprite.ThreadSpriteBoard;
import com.example.blindchess.ui.game.threads.sprite.ThreadSpriteFigures;

import java.util.concurrent.ExecutionException;


public class BoardView extends View {

    private String team;                                      //Цвет команды игрока (WHITE или BLACK)
    private float widthCell;                                  //Размер клетки в пикселях на экране

    private CellBoard[][] board = new CellBoard[8][8];        //Шахматная доска со всеми данными, состоящая из 8х8 клеток (CellBoard)
    private Bitmap[] spritesBoard = new Bitmap[5];            //Массив со спрайтами доски ([0] - белая клекта; [1] - черная клетка; [2] - белая неизвестная клетка; [3] - черная неизвестная клетка; [4] - выбранная клетка)
    private Bitmap[] spritesFigures = new Bitmap[12];         //Массив со спрайтами фигур ([0] - белая пешка; [1] - белый слон; [2] - белый конь; [3] - белый офицер; [4] - белая королева; [5] - белый король; Остальные другого цвета)

    private ThreadSpriteBoard threadSpriteBoard;              //Поток для загрузки спрайтов доски
    private ThreadSpriteFigures threadSpriteFigures;          //Поток для загрузки спрайтов фигур
    private ThreadInitBoard threadInitBoard;                  //Поток для первоначальной инициализации доски CellBoard[8][8] board


    /**Конструткор класса
     * На вход принимает 2 параметра:
     * Context context - контекст приложения
     * String team - цвет команды игрока (WHITE или BLACK)
     * Внутри инициализирует первоначальное состояние доски
     * и загружаются все спрайты*/
    public BoardView(Context context, String team) {
        super(context);

        this.team = team;
        widthCell = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth() / 8; //Размер клетки в пикселях на экране

        //Загрузка спрайтов в отдельных потоках
        threadSpriteFigures = new ThreadSpriteFigures(context);
        threadSpriteFigures.execute();
        threadSpriteBoard = new ThreadSpriteBoard(context);
        threadSpriteBoard.execute();

        //Первоначальная инициализация доски в отдельном потоке
        threadInitBoard = new ThreadInitBoard(team);
        threadInitBoard.execute();

        //Получаем наши спрайты и первоначальное состояние доски
        try {
            spritesBoard = threadSpriteBoard.get();
            spritesFigures = threadSpriteFigures.get();
            board = threadInitBoard.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /** Обработка касаний до холста */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //Обработка нажатия на экран
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int numberCellX = (int) (event.getX() / widthCell); //Номер клетки по X, на которую нажал пользователь (от 0 до 7)
            int numberCellY = (int) (event.getY() / widthCell); //Номер клетки по Y, на которую нажал пользователь (от 0 до 7)

            System.out.println("cellX = " + String.valueOf(numberCellX));
            System.out.println("cellY = " + String.valueOf(numberCellY));

            invalidate();
        }

        return super.onTouchEvent(event);
    }


    /**Функция отрисовывающая каждую клетку доски по заданному полю Board board*/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (board[i][j].getStatus()){
                    canvas.drawBitmap(sprite, j * widthCell, widthCell * i, paint);
                }
            }
        }*/
    }

}
