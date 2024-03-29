/**Поток загрузки спрайтов доски*/
package com.example.blindchess.game.threads.sprite;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;

import com.example.blindchess.R;


public class ThreadSpriteBoard extends AsyncTask<Void, Void, Bitmap[]> {

    private Context context;                      //Контекст приложения
    private float widthCell;                      //Размер клетки в пикселях на экране
    private float scaleBoard;                     //Масштаб, на который нужно увеличить спрайты доски для адаптации к размеру телефона

    private Bitmap spriteBoard;                   //Спрайт доски
    private Bitmap[] arraySpites = new Bitmap[6]; //Массив со спрайтами доски ([0] - белая клетка; [1] - черная клетка; [2] - выбранная клетка; [3] - неизвестная клетка; [4] - доступный ход; [5] - доступная атака)


    /**Конструктор класса
     * На вход принимает 1 параметр:
     * Context context - контекст приложения*/
    public  ThreadSpriteBoard(Context context){
        this.context = context;
    }


    /**Действия, выполняемые в потоке*/
    @Override
    protected Bitmap[] doInBackground(Void... voids) {

        System.out.println("Начало потока загрузки спрайтов доски");
        this.spriteBoard = BitmapFactory.decodeResource(context.getResources(), R.drawable.board); //Загружаем спрайт доски со всеми спрайтами клеток

        //Необходимые расчеты
        widthCell = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth() / 8; //Ширина одной клетки поля в пикселях на экране
        int widthSpriteCell = spriteBoard.getHeight() / 2; //Ширина одной клетки поля спрайта в пикселях
        scaleBoard = widthCell/widthSpriteCell; //Масштаб для клетки доски

        //Получение спрайтов
        Matrix matrix = new Matrix();
        matrix.setScale(scaleBoard, scaleBoard);
        arraySpites[0] = Bitmap.createBitmap(spriteBoard, 0, 0, widthSpriteCell,widthSpriteCell, matrix, false);                           //Спрайт белой клетки
        arraySpites[1] = Bitmap.createBitmap(spriteBoard, widthSpriteCell, 0, widthSpriteCell,widthSpriteCell, matrix, false);                //Спрайт черной клетки
        arraySpites[2] = Bitmap.createBitmap(spriteBoard, 2*widthSpriteCell, 0, widthSpriteCell,widthSpriteCell, matrix, false);           //Спрайт выбранной клетки
        arraySpites[3] = Bitmap.createBitmap(spriteBoard, 0, widthSpriteCell, widthSpriteCell,widthSpriteCell, matrix, false);                //Спрайт неизвестной клетки
        arraySpites[4] = Bitmap.createBitmap(spriteBoard, widthSpriteCell, widthSpriteCell, widthSpriteCell,widthSpriteCell, matrix, true);      //Спрайт доступного хода
        arraySpites[5] = Bitmap.createBitmap(spriteBoard, 2*widthSpriteCell, widthSpriteCell, widthSpriteCell,widthSpriteCell, matrix, true); //Спрайт доступной атаки

        System.out.println("Завершение потока загрузки спрайтов доски");
        return arraySpites;
    }


    /**Функция возвращения ответа - массива Bitmap со всеми спрайтами*/
    @Override
    protected void onPostExecute(Bitmap[] arraySprites) {
        super.onPostExecute(arraySprites);
    }

}