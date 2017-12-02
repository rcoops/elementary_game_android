package me.cooper.rick.elementary.activities.fragments.game.movestrategies;

import android.view.MotionEvent;
import android.view.View;
//import static android.view.View.OnTouchListener;

public class TouchMoveStrategy implements MoveStrategy {

    private static final String DESCRIPTION = "Touch";

    private View view;
    private final OnTouchListener ON_TOUCH_LISTENER = new OnTouchListener();
    private final OnTouchListener DUMMY_LISTENER = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }
    };

    public TouchMoveStrategy(View view) {
        this.view = view;
    }

    @Override
    public void move(float xPosition, float yPosition) {
        view.setX(xPosition);
        view.setY(yPosition);
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public void registerListener() {
        view.setOnTouchListener(ON_TOUCH_LISTENER);
    }

    @Override
    public void unregisterListener() {
        view.setOnTouchListener(DUMMY_LISTENER);
    }

    private class OnTouchListener implements View.OnTouchListener {
        private float dx = 0, dy = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v == view) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dx = event.getX() - view.getX();
                        dy = event.getY() - view.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        move(event.getX() - dx, event.getY() - dy);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
            }
            return true;
        }

    }

}
