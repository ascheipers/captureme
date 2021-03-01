package rs.scheipe.captureme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameView = (GameView) findViewById(R.id.game_view);

        Button leftButton = (Button) findViewById(R.id.left_button);
        Button rightButton = (Button) findViewById(R.id.right_button);

        leftButton.setOnTouchListener(onLeftButtonTouch);
        rightButton.setOnTouchListener(onRightButtonTouch);
    }

    private View.OnTouchListener onLeftButtonTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch ( event.getAction() ) {
                case MotionEvent.ACTION_DOWN: gameView.leftDown = true; break;
                case MotionEvent.ACTION_UP: gameView.leftDown = false; break;
            }
            return false;
        }
    };

    private View.OnTouchListener onRightButtonTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch ( event.getAction() ) {
                case MotionEvent.ACTION_DOWN: gameView.rightDown = true; break;
                case MotionEvent.ACTION_UP: gameView.rightDown = false; break;
            }
            return false;
        }
    };
}