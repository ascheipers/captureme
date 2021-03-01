package rs.scheipe.captureme;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Player {
    float position = 0.5f;
    float speed = 0.01f;
    int playerHeight = 20;
    int playerWidth = 50;

    int playerColor = Color.BLUE;

    public void draw(Canvas canvas) {
        Paint filling_paint = new Paint() ;
        filling_paint.setStyle(Paint.Style.FILL) ;
        filling_paint.setColor(playerColor);

        Paint outline_paint = new Paint() ;
        outline_paint.setStyle(Paint.Style.STROKE) ;

        Rect r = getRect(canvas);

        canvas.drawRect(r, filling_paint);
        canvas.drawRect(r, outline_paint);
    }

    public Rect getRect(Canvas canvas) {
        float normalizedPlayerWidth = 1.0f - ((float)playerWidth / (float)canvas.getWidth());

        return new Rect(
                (int)(canvas.getWidth() * position * normalizedPlayerWidth), // left
                (int)(canvas.getHeight() - playerHeight), // top
                (int)(canvas.getWidth() * position * normalizedPlayerWidth + playerWidth), // right
                (int)(canvas.getHeight()) // bottom
        );
    }
}
