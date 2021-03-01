package rs.scheipe.captureme;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameView extends View {
    Player player = new Player();
    List<Enemy> enemies = new ArrayList<Enemy>();
    boolean leftDown = false;
    boolean rightDown = false;
    boolean gameRunning = false;
    float difficulty = 0.0f;
    int score = 0;
    Random random = new Random();

    public GameView(Context context) {
        super(context);
        gameStart();
    }

    public GameView(Context context, AttributeSet attributes) {
        super(context, attributes);
        gameStart();
    }

    public void onSizeChanged(int current_width_of_this_view,
                              int current_height_of_this_view,
                              int old_width_of_this_view,
                              int old_height_of_this_view)
    {

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        setBackgroundColor(0xFFFFFFFF);
        player.draw(canvas);

        // User Input
        if (rightDown && leftDown) {
            // Do nothing
        }
        else if (rightDown) {
            float newPosition = player.position + player.speed;
            player.position = Math.min(newPosition, 1);
        }
        else if (leftDown) {
            float newPosition = player.position - player.speed;
            player.position = Math.max(newPosition, 0);
        }

        // Spawn Enemies
        if (random.nextFloat() < 0.01 + difficulty) {
            enemies.add(new Enemy(random.nextInt(canvas.getWidth()),
                    -20,
                    20,
                    random.nextFloat() + 1.0f * 3.0f));
        }

        // Enemy Logic
        for (Iterator<Enemy> it = enemies.iterator(); it.hasNext();) {
            Enemy enemy = it.next();
            if (enemy.positionY - enemy.size > canvas.getHeight()) {
                it.remove();
                score++;
            }
            else if (isColliding(canvas, player, enemy)) {
                gameOver();
            }
            else {
                enemy.draw(canvas);
            }
        }

        // High score Text
        Paint text_paint = new Paint();
        text_paint.setStyle(Paint.Style.STROKE);
        text_paint.setTextSize(25);
        canvas.drawText("High Score: " + score, 15, 30, text_paint);

        difficulty += 0.0001f;
        player.speed += 0.000001f;

        if (gameRunning) {
            invalidate();
        }
    }

    private boolean isColliding(Canvas canvas, Player player, Enemy enemy) {
        boolean collided = false;
        Rect playerRect = player.getRect(canvas);

        // Case 1: circle center is inside rect
        if (playerRect.contains((int) enemy.positionX, (int) enemy.positionY)) {
            return true;
        }

        // Case 2: circle center is not inside rect
        PointF p = new PointF();

        if (enemy.positionX < playerRect.left) {
            p.x = playerRect.left;
        }
        else if (enemy.positionX > playerRect.left) {
            p.x = playerRect.right;
        }
        else {
            p.x = enemy.positionX;
        }

        if (enemy.positionY < playerRect.top) {
            p.y = playerRect.top;
        }
        else if (enemy.positionY > playerRect.bottom) {
            p.y = playerRect.bottom;
        }
        else {
            p.y = enemy.positionY;
        }

        float deltaX = enemy.positionX - p.x;
        float deltaY = enemy.positionY - p.y;
        float distance = (float)Math.sqrt(deltaX*deltaX + deltaY*deltaY);
        return distance <= enemy.size;
    }

    public void resetGame() {
        score = 0;
        difficulty = 0.0f;
        enemies.clear();
        player = new Player();
        gameRunning = true;
        invalidate();
    }

    public void gameStart() {
        new AlertDialog.Builder(this.getContext())
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("CaptureMe")
                .setMessage("Dodge the red enemies!")
                .setPositiveButton(android.R.string.yes, onStartGame)
                .show();
    }

    DialogInterface.OnClickListener onStartGame = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            gameRunning = true;
            invalidate();
        }
    };

    public void gameOver() {
        gameRunning = false;

        new AlertDialog.Builder(this.getContext())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Lost the Game")
                .setMessage("Do you want to play another round?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        resetGame();
                    }
                })
                .show();
    }
}
