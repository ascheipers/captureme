package rs.scheipe.captureme;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

public class Enemy {
    float positionX = 0;
    float positionY = 0;
    int size = 10;
    float speed = 0.01f;
    int color = Color.RED;

    public Enemy(int positionX, int positionY, int size, float speed) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.size = size;
        this.speed = speed;
    }

    public void draw(Canvas canvas) {

        Paint filling_paint = new Paint() ;
        filling_paint.setStyle(Paint.Style.FILL) ;
        filling_paint.setColor(color);

        Paint outline_paint = new Paint() ;
        outline_paint.setStyle(Paint.Style.STROKE) ;

        canvas.drawCircle(positionX, positionY, size, filling_paint);
        canvas.drawCircle(positionX, positionY, size, outline_paint);

        positionY += speed;
    }
}
