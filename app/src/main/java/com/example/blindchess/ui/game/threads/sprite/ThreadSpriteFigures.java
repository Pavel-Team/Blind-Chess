/**Поток загрузки спрайтов фигур*/
package com.example.blindchess.ui.game.threads.sprite;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;

import com.example.blindchess.R;


public class ThreadSpriteFigures extends AsyncTask<Void, Void, Bitmap[]> {

    private Context context;                       //Контекст приложения
    private float widthCell;                       //Размер клетки в пикселях на экране
    private float scaleFigure;                     //Масштаб, на который нужно увеличить спрайты фигур для адаптации к размеру телефона

    private Bitmap spriteFigures;                  //Спрайт всех фигур
    private Bitmap[] arraySpites = new Bitmap[12]; //Массив со спрайтами фигур ([0] - белая пешка; [1] - белый слон; [2] - белый конь; [3] - белый офицер; [4] - белая королева; [5] - белый король; Остальные другого цвета)


    /**Конструктор класса
     * На вход принимает 1 параметр:
     * Context context - контекст приложения*/
    public  ThreadSpriteFigures(Context context){
        this.context = context;
    }


    /**Действия, выполняемые в потоке*/
    @Override
    protected Bitmap[] doInBackground(Void... voids) {
        this.spriteFigures = BitmapFactory.decodeResource(context.getResources(), R.drawable.figures); //Загрузка спрайта фигур со всеми спрайтами

        //Необходимые расчеты
        widthCell = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth() / 8; //Ширина одной клетки поля в пикселях на экране
        float widthSpriteFigure = spriteFigures.getWidth() / 6; //Ширина одной текстуры спрайта в пискселях
        scaleFigure = widthCell/widthSpriteFigure; //Масштаб для фигуры
        int widthFigure = spriteFigures.getWidth()/6; //Ширина и высота одной фигуры в спрайте в dp

        //Загрузка спрайтов
        Matrix matrix = new Matrix(); //Матрица для преобразований спрайтов
        matrix.setScale(scaleFigure, scaleFigure);
        arraySpites[0] = Bitmap.createBitmap(spriteFigures, 5*widthFigure, 0, widthFigure, widthFigure, matrix, true);        //Спрайт белой пешки
        arraySpites[1] = Bitmap.createBitmap(spriteFigures, 4*widthFigure, 0, widthFigure, widthFigure, matrix, true);        //Спрайт белого слона
        arraySpites[2] = Bitmap.createBitmap(spriteFigures, 3*widthFigure, 0, widthFigure, widthFigure, matrix, true);        //Спрайт белого коня
        arraySpites[3] = Bitmap.createBitmap(spriteFigures, 2*widthFigure, 0, widthFigure, widthFigure, matrix, true);        //Спрайт белого офицера
        arraySpites[4] = Bitmap.createBitmap(spriteFigures, widthFigure, 0, widthFigure, widthFigure, matrix, true);             //Спрайт белой королевы
        arraySpites[5] = Bitmap.createBitmap(spriteFigures, 0, 0, widthFigure, widthFigure, matrix, true);                    //Спрайт белого корля
        arraySpites[6] = Bitmap.createBitmap(spriteFigures, 5*widthFigure, widthFigure, widthFigure, widthFigure, matrix, true); //Спрайт черной пешки
        arraySpites[7] = Bitmap.createBitmap(spriteFigures, 4*widthFigure, widthFigure, widthFigure, widthFigure, matrix, true); //Спрайт черного слона
        arraySpites[8] = Bitmap.createBitmap(spriteFigures, 3*widthFigure, widthFigure, widthFigure, widthFigure, matrix, true); //Спрайт черного коня
        arraySpites[9] = Bitmap.createBitmap(spriteFigures, 2*widthFigure, widthFigure, widthFigure, widthFigure, matrix, true); //Спрайт черного офицера
        arraySpites[10] = Bitmap.createBitmap(spriteFigures, widthFigure, widthFigure, widthFigure, widthFigure, matrix, true);     //Спрайт черной королевы
        arraySpites[11] = Bitmap.createBitmap(spriteFigures, 0, widthFigure, widthFigure, widthFigure, matrix, true);            //Спрайт черного короля

        return new Bitmap[0];
    }


    /**Функция возвращения ответа - массива Bitmap со всеми спрайтами*/
    @Override
    protected void onPostExecute(Bitmap[] bitmaps) {
        super.onPostExecute(bitmaps);
    }
}
