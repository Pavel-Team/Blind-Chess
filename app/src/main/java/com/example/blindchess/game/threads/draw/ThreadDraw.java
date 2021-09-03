package com.example.blindchess.game.threads.draw;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;


public class ThreadDraw extends Thread {

    private Context context;
    private Canvas canvas;
    private Bitmap[] sprites;
    private int i;

    public ThreadDraw(Context context, Canvas canvas, Bitmap[] sprites, int i) {
        this.context = context;
        this.canvas = canvas;
        this.sprites = sprites;
        this.i = i;
    }

    @Override
    public void run() {
        super.run();

        System.out.println("Старт потока " + String.valueOf(i));
        float widthCell = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth() / 8; //Ширина одной клетки поля в пикселях на экране

        Paint paint = new Paint();
        canvas.drawBitmap(sprites[0], i * widthCell, widthCell * i, paint);
    }
}
