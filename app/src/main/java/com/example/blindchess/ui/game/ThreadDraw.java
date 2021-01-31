/** Поток для отрисовки всех графических объектов */
package com.example.blindchess.ui.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.PixelCopy;
import android.view.SurfaceHolder;

import com.example.blindchess.R;

public class ThreadDraw extends Thread{

    private SurfaceHolder surfaceHolder; //Объект класса SurfaceHolder (для поприсовки изображения на Canvas'е)
    private Context context; //Контекст приложения
    private Handler handler; //Механизм для обработки очереди сообщений в другом потоке
    private Matrix matrix; //Матрица определяющая расположение и ориентацию картинки
    private boolean isRun = false; //Выполняется ли поток
    private Canvas canvas = null; //Канвас, на котором все отображается
    private Paint paint = new Paint(); //Кисть для рисования
    private Bitmap lastScreen; //Последний отрисованный экран (нужен из-за буферизации SurfaceView)
    private float widthCell; //Размер клетки в пикселях на экране

    private Bitmap spriteBoard; //Спрайт доски
    private Bitmap spriteCellW; //Спрайт белого исследованного поля
    private Bitmap spriteCellUnknownW; //Спрайт белого неиследованного поля
    private Bitmap spriteCellB; //Спрайт черного иследованного поля
    private Bitmap spriteCellUnknownB; //Спрайт черного неиследованного поля
    private Bitmap spriteCellCheck; //Спрайт выбора поля

    private Bitmap spriteFigures; //Спрайт всех фигур
    private Bitmap spritePawnW; //Спрайт белой пешки
    private Bitmap spriteElphantW; //Спрайт белого слона
    private Bitmap spriteHorseW; //Спрайт белого коня
    private Bitmap spriteOfficerW; //Спрайт белого офицера
    private Bitmap spriteQueenW; //Спрайт белой королевы
    private Bitmap spriteKingW; //Спрайт белого короля
    private Bitmap spritePawnB; //Спрайт черной пешки
    private Bitmap spriteElphantB; //Спрайт черного слона
    private Bitmap spriteHorseB; //Спрайт черного коня
    private Bitmap spriteOfficerB; //Спрайт черного офицера
    private Bitmap spriteQueenB; //Спрайт черной королевы
    private Bitmap spriteKingB; //Спрайт черного короля


    /**Конструктор класса для графического потока
     * Принимает 2 параметра:
     * Context context - контекст приложения
     * SurfaceHolder surfaceHolder - объект класса SurfaceHolder - нужен для "создания" канваса */
    public ThreadDraw(Context context, SurfaceHolder surfaceHolder, Handler handler){

        this.surfaceHolder = surfaceHolder; //Передаем наш канвас, на котором будет производить отрисовку
        this.context = context;
        this.handler = handler;



        //Загрузка спрайтов
        this.spriteBoard = BitmapFactory.decodeResource(context.getResources(), R.drawable.board);
        this.spriteFigures = BitmapFactory.decodeResource(context.getResources(), R.drawable.figures);

        /**---Расчеты для масштабирования всех спрайтов под данное устройство--------------------------*/

        widthCell = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth() / 8; //Ширина одной клетки поля в пикселях на экране
        System.out.println("Ширина клетки на экране в пикселях = " + String.valueOf(widthCell));
        int widthSpriteCell = spriteBoard.getHeight() / 2; //Ширина одной клетки поля спрайта в пикселях
        System.out.println("Ширина клетки в спрайте в пикселях = " + String.valueOf(widthSpriteCell));
        float scaleBoard = widthCell/widthSpriteCell; //Масштаб для клетки доски
        System.out.println("Масштаб клетки = " + String.valueOf(scaleBoard));

        float widthSpriteFigure = spriteFigures.getWidth() / 6; //Ширина одной текстуры спрайта в пискселях
        System.out.println("Ширина фигуры в спрайте в пикселях = " + String.valueOf(widthSpriteFigure));
        float scaleFigure = widthCell/widthSpriteFigure; //Масштаб для фигуры
        System.out.println("Масштаб фигуры = " + String.valueOf(scaleFigure));

        int widthFigure = spriteFigures.getWidth()/6; //Ширина и высота одной фигуры в спрайте в dp

        /**-----------------------------------------------------------------------------------------------*/

        this.matrix = new Matrix(); //Формируем матрицу для преобразования всех спрайтов

        //Загрузка доски
        /**Сделать потоком*/
        matrix.setScale(scaleBoard, scaleBoard);
        this.spriteCellW = Bitmap.createBitmap(spriteBoard, 0, 0, widthSpriteCell,widthSpriteCell, matrix, false);
        this.spriteCellB = Bitmap.createBitmap(spriteBoard, widthSpriteCell, 0, widthSpriteCell,widthSpriteCell, matrix, false);
        this.spriteCellUnknownW = Bitmap.createBitmap(spriteBoard, 0, widthSpriteCell, widthSpriteCell,widthSpriteCell, matrix, false);
        this.spriteCellUnknownB = Bitmap.createBitmap(spriteBoard, widthSpriteCell, widthSpriteCell, widthSpriteCell,widthSpriteCell, matrix, false);
        this.spriteCellCheck = Bitmap.createBitmap(spriteBoard, 2*widthSpriteCell, 0, widthSpriteCell,widthSpriteCell, matrix, true);

        //Загрузка фигур
        /**Сделать потоком*/
        matrix.setScale(scaleFigure, scaleFigure);
        this.spritePawnW = Bitmap.createBitmap(spriteFigures, 5*widthFigure, 0, widthFigure, widthFigure, matrix, true);
        this.spritePawnB = Bitmap.createBitmap(spriteFigures, 5*widthFigure, widthFigure, widthFigure, widthFigure, matrix, true);
        this.spriteElphantW = Bitmap.createBitmap(spriteFigures, 4*widthFigure, 0, widthFigure, widthFigure, matrix, true);
        this.spriteElphantB = Bitmap.createBitmap(spriteFigures, 4*widthFigure, widthFigure, widthFigure, widthFigure, matrix, true);
        this.spriteHorseW = Bitmap.createBitmap(spriteFigures, 3*widthFigure, 0, widthFigure, widthFigure, matrix, true);
        this.spriteHorseB = Bitmap.createBitmap(spriteFigures, 3*widthFigure, widthFigure, widthFigure, widthFigure, matrix, true);
        this.spriteOfficerW = Bitmap.createBitmap(spriteFigures, 2*widthFigure, 0, widthFigure, widthFigure, matrix, true);
        this.spriteOfficerB = Bitmap.createBitmap(spriteFigures, 2*widthFigure, widthFigure, widthFigure, widthFigure, matrix, true);
        this.spriteQueenW = Bitmap.createBitmap(spriteFigures, widthFigure, 0, widthFigure, widthFigure, matrix, true);
        this.spriteQueenB = Bitmap.createBitmap(spriteFigures, widthFigure, widthFigure, widthFigure, widthFigure, matrix, true);
        this.spriteKingW = Bitmap.createBitmap(spriteFigures, 0, 0, widthFigure, widthFigure, matrix, true);
        this.spriteKingB = Bitmap.createBitmap(spriteFigures, 0, widthFigure, widthFigure, widthFigure, matrix, true);

    }


    /**Функция, определяющая начало потока (true или false)*/
    public void setRunning(boolean run) {
        this.isRun = run;
    }


    /**Функция возвращающая последний скриншот surfaceView
     * Скриншот нужен из-за двойной буферизации SurfaceView*/
    private Bitmap getLastScreen() {
        lastScreen = Bitmap.createBitmap((int) widthCell*8, (int) widthCell*8, Bitmap.Config.ARGB_8888); //Создаем bitmap заданного размера

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            PixelCopy.request(surfaceHolder.getSurface(), lastScreen, ThreadDraw::onPixelCopyFinished, handler);
        }

        return lastScreen;
    }


    /**Функция отрисовки спрайта в данной клетке поля
     * На вход принимает 3 параметра:
     * String nameSprite - отрисовываемый спрайт
     * int numberCellX - номер клетки по оси x (от 0 до 7)
     * int numberCellY - номер клетки по оси y (от 0 до 7)*/
    public void drawSprite(String nameSprite, int numberCellX, int numberCellY) {
        Bitmap sprite = null; //Отрисовываемый спрайт

        //Присваивание спрайта по его названию
        switch (nameSprite) {
            case "cellW":
                sprite = spriteCellW;
                break;
            case "cellB":
                sprite = spriteCellB;
                break;
            case "cellUnknownW":
                sprite = spriteCellUnknownW;
                break;
            case "cellUnknownB":
                sprite = spriteCellUnknownB;
                break;
            case "cellCheck":
                sprite = spriteCellCheck;
                break;
            case "pawnW":
                sprite = spritePawnW;
                break;
            case "pawnB":
                sprite = spritePawnB;
                break;
            case "elphantW":
                sprite = spriteElphantW;
                break;
            case "elphantB":
                sprite = spriteElphantB;
                break;
            case "horseW":
                sprite = spriteHorseW;
                break;
            case "horseB":
                sprite = spriteHorseB;
                break;
            case "officerW":
                sprite = spriteOfficerW;
                break;
            case "officerB":
                sprite = spriteOfficerB;
                break;
            case "queenW":
                sprite = spriteQueenW;
                break;
            case "queenB":
                sprite = spriteQueenB;
                break;
            case "kingW":
                sprite = spriteKingW;
                break;
            case "kingB":
                sprite = spriteKingB;
                break;
            default:
                System.out.println("Ошибка в названии спрайта");
                break;
        }

        //Отрисовывем спрайт
        try {
            lastScreen = getLastScreen(); //Получаем скриншот из прошлого буфера

            //Получаем объект Canvas и рисуем на нем изменения
            canvas = surfaceHolder.lockCanvas(null);

            synchronized (surfaceHolder) {
                if (canvas != null) {
                    canvas.drawBitmap(lastScreen, 0, 0, paint);
                    canvas.drawBitmap(sprite, (int) widthCell * numberCellX, (int) widthCell * numberCellY, paint);
                }
            }
        } finally {
            if (canvas != null) {
                //Отрисовка выполнена. выводим результат на экран
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }


    /**Действия, выполняемые в потоке
     * Здесь происходит первоначальная отрисовка доски*/
    @Override
    public void run() {

        System.out.println("Запуск ThreadDraw");
        float widthCell = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth() / 8; //Ширина одной клетки поля в пикселях на экране


        /** -----------------------------------------------Перваночальная прорисовка доски-------------------------------------------------*/

        try {
            //Получаем объект Canvas
            canvas = surfaceHolder.lockCanvas(null);

            //Отрисовываем доску и фигуры
            synchronized (surfaceHolder) {
                if (canvas != null) {
                    canvas.drawColor(Color.LTGRAY); //Устанавливаем фон

                    //Отрисовка доски (ВРЕМЕННО)
                    /**Сделать потоком*/
                    for (int i = 0; i < 8; i++) {
                        if(i % 2 == 0) {
                            for (int j = 0; j < 4; j++) {
                                canvas.drawBitmap(spriteCellW, 2*j * widthCell, widthCell * i, paint);
                                canvas.drawBitmap(spriteCellB, (2*j + 1) * widthCell, widthCell * i, paint);
                            }
                        } else {
                            for (int j = 0; j < 4; j++) {
                                canvas.drawBitmap(spriteCellB, 2*j * widthCell, widthCell * i, paint);
                                canvas.drawBitmap(spriteCellW, (2*j + 1) * widthCell, widthCell * i, paint);
                            }
                        }
                    }

                    /**Сделать потоком*/
                    //(ВРЕМЕННО)
                    //Отрисовка пешек
                    for (int i = 0; i < 8; i++) {
                        canvas.drawBitmap(spritePawnB, i*widthCell, widthCell*1, paint);
                    }
                    for (int i = 0; i < 8; i++) {
                        canvas.drawBitmap(spritePawnW, i*widthCell, widthCell*6, paint);
                    }

                    //Отрисовка слонов
                    for (int i = 0; i < 2; i++) {
                        canvas.drawBitmap(spriteElphantB, i*widthCell*7, 0, paint);
                    }
                    for (int i = 0; i < 2; i++) {
                        canvas.drawBitmap(spriteElphantW, i*widthCell*7, widthCell*7, paint);
                    }

                    //Отрисовка коней
                    for (int i = 0; i < 2; i++) {
                        canvas.drawBitmap(spriteHorseB, (i*5+1)*widthCell, 0, paint);
                    }
                    for (int i = 0; i < 2; i++) {
                        canvas.drawBitmap(spriteHorseW, (i*5+1)*widthCell, widthCell*7, paint);
                    }

                    //Отрисовка офицеров
                    for (int i = 0; i < 2; i++) {
                        canvas.drawBitmap(spriteOfficerB, (i*3+2)*widthCell, 0, paint);
                    }
                    for (int i = 0; i < 2; i++) {
                        canvas.drawBitmap(spriteOfficerW, (i*3+2)*widthCell, widthCell*7, paint);
                    }

                    //Отрисовка королев
                    canvas.drawBitmap(spriteQueenB, 3*widthCell, 0, paint);
                    canvas.drawBitmap(spriteQueenW, 3*widthCell, widthCell*7, paint);

                    //Отрисовка королей
                    canvas.drawBitmap(spriteKingB, 4*widthCell, 0, paint);
                    canvas.drawBitmap(spriteKingW, 4*widthCell, widthCell*7, paint);


                }
            }

        } finally {

            if (canvas != null) {
                //Отрисовка выполнена. выводим результат на экран
                surfaceHolder.unlockCanvasAndPost(canvas);
            }

        }

        /** ------------------------------------------------------------------------------------------------------------------------- */


        /*//Ожидание кликов
        while (isRun) {

        }*/

    }


    /**Listener для класса CopyPixel (сообщает об возникшей ошибке во время копирования lastScreen)*/
    private static void onPixelCopyFinished(int result) {
        //Если неудачно - сообщаем об ошибке
        if (result != PixelCopy.SUCCESS) {
            Log.e("err", "Не удалось сделать lastScreen");
            return;
        }
    }

}