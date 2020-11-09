/** Класс-канвас, в котором запускаются поток для рисования */
package com.example.blindchess.ui.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.blindchess.R;


public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private ThreadDraw threadDraw; //Поток для отрисовки объекта
    private float widthCell; //Размер клетки в пикселях на экране


    /**Конструктор класса
     * На вход принимает 1 параметр: Context context - контекст фрагмента
     * Внутри создает канвас*/
    public GameSurfaceView(Context context) {
        super(context);

        getHolder().addCallback(this); //Этот объект будет предоставлять нам полотно (canvas) для рисования на нем объектов
        widthCell = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth() / 8;
    }


    /**Вызывается при создании области рисования
       Здесь выполняется отрисовака объектов (запуск соответствующих потоков DrawThread)*/
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        //Создаем плееров, добавляя их в List
        //Делаем это, получая события от сервера
        //Player player1 = new Player(new Tank(100, 150, 30, spriteTank)); //ПО УМОЛЧАНИЮ
        //player1.setId(1); //Присваиваем id


        //Открываем поток для рисования
        threadDraw = new ThreadDraw(getContext(), getHolder());
        threadDraw.setRunning(true);
        threadDraw.start();
    }


    /**Вызывается при изменении области рисования*/
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }


    /**Вызывается при разрушении области рисования
       Здесь заканчивается отрисовка объектов*/
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true; //Отвечает за повторную попытку завершения потока

        // завершаем работу потока
        //drawThread.setRunning(false);

        /*//Ждем завершение потока
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                //Если не получилось, то будем пытаться снова и снова
            }
        }*/

    }


    /** Обработка касания до холста*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if (event.getAction() == MotionEvent.ACTION_DOWN) { //Нажатие

            System.out.println("X = " + String.valueOf(event.getX()));
            System.out.println("Y = " + String.valueOf(event.getY()));

            int numberCellX = (int) (event.getX()/widthCell); //Номер клетки по X, на которую нажал пользователь (от 0 до 7)
            int numberCellY = (int) (event.getY()/widthCell); //Номер клетки по Y, на которую нажал пользователь (от 0 до 7)

            System.out.println("cellX = " + String.valueOf(numberCellX));
            System.out.println("cellY = " + String.valueOf(numberCellY));

            threadDraw.drawSprite("cellCheck", numberCellX, numberCellY); //Отрисовка выбора клетки

        } /*else if (event.getAction() == MotionEvent.ACTION_MOVE) { //Перемещение
            System.out.println(event.getX());
        } else if (event.getAction() == MotionEvent.ACTION_UP) { //Отпускание
            System.out.println(event.getX());
        }*/

        return true;
    }

}
