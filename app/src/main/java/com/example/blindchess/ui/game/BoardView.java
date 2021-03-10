/**Класс View, создающий доску в игровой комнате FragmentGameRoom*/
package com.example.blindchess.ui.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.example.blindchess.ui.game.figure.Elephant;
import com.example.blindchess.ui.game.figure.Figure;
import com.example.blindchess.ui.game.figure.Horse;
import com.example.blindchess.ui.game.figure.Officer;
import com.example.blindchess.ui.game.figure.Pawn;
import com.example.blindchess.ui.game.figure.Queen;
import com.example.blindchess.ui.game.threads.calculation.ThreadCalculateVisibility;
import com.example.blindchess.ui.game.threads.calculation.ThreadInitBoard;
import com.example.blindchess.ui.game.threads.sprite.ThreadSpriteBoard;
import com.example.blindchess.ui.game.threads.sprite.ThreadSpriteFigures;

import java.util.concurrent.ExecutionException;


public class BoardView extends View {

    private Context context;                                     //Контекст приложения
    private String team;                                         //Цвет команды игрока (WHITE или BLACK)
    private float widthCell;                                     //Размер клетки в пикселях на экране
    private Paint paint;                                         //Кисть для отрсиовки спрайтов

    private CellBoard[][] board = new CellBoard[8][8];           //Шахматная доска со всеми данными, состоящая из 8х8 клеток (CellBoard)
    private Bitmap[] spritesBoard = new Bitmap[6];               //Массив со спрайтами доски ([0] - белая клетка; [1] - черная клетка; [2] - выбранная клетка; [3] - неизвестная клетка; [4] - доступный ход; [5] - доступная атака)
    private Bitmap[] spritesFigures = new Bitmap[12];            //Массив со спрайтами фигур ([0] - белая пешка; [1] - белый слон; [2] - белый конь; [3] - белый офицер; [4] - белая королева; [5] - белый король; Остальные другого цвета)
    private Figure figure;                                       //Объект фигуры в данной клетке (используется для определения типа фигур (конь, пешка и тд) в методе OnDraw())

    private boolean isMyMove;                                    //Является ли текущий ход - ходом пользователя (если я хожу - true, если ходит противник - false)
    private int numberCellX;                                     //Номер клетки по X, на которую последний раз нажал пользователь (от 0 до 7)
    private int numberCellY;                                     //Номер клетки по Y, на которую последний раз нажал пользователь (от 0 до 7)

    private ThreadSpriteBoard threadSpriteBoard;                 //Поток для загрузки спрайтов доски
    private ThreadSpriteFigures threadSpriteFigures;             //Поток для загрузки спрайтов фигур
    private ThreadInitBoard threadInitBoard;                     //Поток для первоначальной инициализации доски CellBoard[8][8] board
    private ThreadCalculateVisibility threadCalculateVisibility; //Поток для расчета видимости клеток фигурами данной команды


    /**Конструткор класса
     * На вход принимает 2 параметра:
     * Context context - контекст приложения
     * String team - цвет команды игрока (WHITE или BLACK)
     * Внутри инициализирует первоначальное состояние доски, возможность первого хода
     * и загружаются все спрайты*/
    public BoardView(Context context, String team) {
        super(context);

        this.context = context;
        this.team = team;
        paint = new Paint();
        widthCell = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth() / 8; //Размер клетки в пикселях на экране

        //Определение первого хода
        if (team.equals("WHITE"))
            isMyMove = true;
        else
            isMyMove = false;

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


    /** Обработка касаний до холста
     * Здесь реализованы все действия пользователя - выбор фигур, ход, атака (P.S. атака - это тот же ход)
     * Реализация выполнена путем осуществления проверок нажатия на клетку и ее анализа (ялвяется ли она моей фигурой,
     * может ли пользователь пойти на данную клетку и т.д.)*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //Проверяем, могу ли я вообще ходить (есть ли смысл вообще обрабатывать нажатие на доску)
        if (isMyMove) {
            //Обработка нажатия на экран
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                int lastNumberCellX = numberCellX;                //Координата X предыдущей нажатой клетки. Необходима для перемещения (хода) фигур
                int lastNumberCellY = numberCellY;                //Координата Y предыдущей нажатой клетки. Необходима для перемещения (хода) фигур
                numberCellX = (int) (event.getX() / widthCell);   //Номер клетки по X, на которую нажал пользователь (от 0 до 7)
                numberCellY = (int) (event.getY() / widthCell);   //Номер клетки по Y, на которую нажал пользователь (от 0 до 7)
                CellBoard cell = board[numberCellY][numberCellX]; //Текущая выбранная клетка

                //Проверяем, является ли нажатая клетка - фигурой моего цвета (если да - расчитываем isCanMove (меняя состояние доски board), если нет - далее проверяем на наличие isCanMove в нажатой клетке)
                if (cell.getFigure() != null && cell.getFigure().getTeam().equals(team)) {
                    /**Обнуляем прошлый isCanMove (НАДО ОПТИМИЗИРОВАТЬ)*/
                    for (int i = 0; i < 8; i++)
                        for(int j = 0; j < 8; j++)
                            board[i][j].setCanMove(false);

                    board = cell.getFigure().calculateOfCanMove(board, numberCellX, numberCellY); //Меняем состояние доски, рассчитывая isCanMove
                } else {
                    //Проверяем, есть ли у данной клетки isCanMove (если да - изменили состояние доски (переместили фигуру), если нет - ничего не делаем)
                    if (cell.isCanMove()) {
                        //Меняем состояние доски
                        board[numberCellY][numberCellX] = board[lastNumberCellY][lastNumberCellX];
                        board[lastNumberCellY][lastNumberCellX] = new CellBoard(false,false,null);
                    }

                    //Сбрасываем подсветку возможного хода (т.к. игрок сейчас либо походил, либо нажал на пустую клетку)
                    /**Обнуляем прошлый isCanMove (НАДО ОПТИМИЗИРОВАТЬ)*/
                    for (int i = 0; i < 8; i++)
                        for(int j = 0; j < 8; j++)
                            board[i][j].setCanMove(false);
                }

                invalidate(); //Переотрисовываем нашу доску
            }
        }

        return super.onTouchEvent(event);
    }


    /**Функция отрисовывающая каждую клетку доски по заданному полю Board board*/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        System.out.println("Отрисовка доски");
        long startTime = System.currentTimeMillis();

        threadCalculateVisibility = new ThreadCalculateVisibility(board, team);
        threadCalculateVisibility.execute(); //Открываем поток для расчета видимости клеток данной командой

        /*Thread t1 = new ThreadDraw(context, canvas, spritesBoard, 1);
        Thread t2 = new ThreadDraw(context, canvas, spritesBoard, 2);
        t1.start();
        t2.start();*/

        //Отрисовка доски (ВРЕМЕННО - МБ СДЕЛАТЬ ПОТОКОМ И ИЗМЕНИТЬ ЛОГИКУ)
        for (int i = 0; i < 8; i++) {
            if(i % 2 == 0) {
                for (int j = 0; j < 4; j++) {
                    canvas.drawBitmap(spritesBoard[0], 2*j * widthCell, widthCell * i, paint);
                    canvas.drawBitmap(spritesBoard[1], (2*j + 1) * widthCell, widthCell * i, paint);
                }
            } else {
                for (int j = 0; j < 4; j++) {
                    canvas.drawBitmap(spritesBoard[1], 2*j * widthCell, widthCell * i, paint);
                    canvas.drawBitmap(spritesBoard[0], (2*j + 1) * widthCell, widthCell * i, paint);
                }
            }
        }

        //Получаем доску с рассчитанной видимостью клеток
        try {
            board = threadCalculateVisibility.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Отрисовка всего поля (фигур и невидимых клеток), в зависимости от состояния CellBoard[8][8] board
        //СДЕЛАТЬ 2МЯ-4МЯ ПОТОКАМИ
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //Если клетка невидима
                if (!board[i][j].isVisible()) {
                    canvas.drawBitmap(spritesBoard[3], j * widthCell, i * widthCell, paint);
                } else {
                    //Если клетка видима, но в ней ничего нет
                    if (!board[i][j].isBusy()) {
                        //Если на нее можно походить (то есть, если ранее выбрали фигуру и должна быть подсветка возможных ходов)
                        if (board[i][j].isCanMove())
                            canvas.drawBitmap(spritesBoard[4], j * widthCell, i * widthCell, paint);
                    } else {
                        //Иначе отрисовываем необходимую фигуру (после делаем проверку, можно ли ударить фигуру, если ранее выбирали фигуру)
                        figure = board[i][j].getFigure();
                        //Белые фигуры
                        if (figure.getTeam() == "WHITE") {
                            if (figure instanceof Pawn)
                                canvas.drawBitmap(spritesFigures[0], j * widthCell, i * widthCell, paint);
                            else if (figure instanceof Elephant)
                                canvas.drawBitmap(spritesFigures[1], j * widthCell, i * widthCell, paint);
                            else if (figure instanceof Horse)
                                canvas.drawBitmap(spritesFigures[2], j * widthCell, i * widthCell, paint);
                            else if (figure instanceof Officer)
                                canvas.drawBitmap(spritesFigures[3], j * widthCell, i * widthCell, paint);
                            else if (figure instanceof Queen)
                                canvas.drawBitmap(spritesFigures[4], j * widthCell, i * widthCell, paint);
                            else
                                canvas.drawBitmap(spritesFigures[5], j * widthCell, i * widthCell, paint);
                        } else {
                        //Черные фигуры
                            if (figure instanceof Pawn)
                                canvas.drawBitmap(spritesFigures[6], j * widthCell, i * widthCell, paint);
                            else if (figure instanceof Elephant)
                                canvas.drawBitmap(spritesFigures[7], j * widthCell, i * widthCell, paint);
                            else if (figure instanceof Horse)
                                canvas.drawBitmap(spritesFigures[8], j * widthCell, i * widthCell, paint);
                            else if (figure instanceof Officer)
                                canvas.drawBitmap(spritesFigures[9], j * widthCell, i * widthCell, paint);
                            else if (figure instanceof Queen)
                                canvas.drawBitmap(spritesFigures[10], j * widthCell, i * widthCell, paint);
                            else
                                canvas.drawBitmap(spritesFigures[11], j * widthCell, i * widthCell, paint);
                        }

                        //Если эту фигуру можно ударить (в случае если ранее была выбрана фигура и сейчас идет подсветка ходов)
                        if (board[i][j].isCanMove())
                            canvas.drawBitmap(spritesBoard[5], j * widthCell, i * widthCell, paint);
                    }
                }
            }
        }

        //Отрисовка подсветки клетки
        if (board[numberCellY][numberCellX].getFigure() != null && board[numberCellY][numberCellX].getFigure().getTeam().equals(team))
            canvas.drawBitmap(spritesBoard[2], numberCellX * widthCell, numberCellY * widthCell, paint);


        long endTime = System.currentTimeMillis();
        System.out.println("Время отрисовки: " + String.valueOf(endTime - startTime) + " мс");
    }

}
