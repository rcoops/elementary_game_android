package me.cooper.rick.elementary.models.movestrategies;

import android.view.MotionEvent;
import android.view.View;

import me.cooper.rick.elementary.models.game.PlayerView;

public class TouchMoveStrategy implements MoveStrategy {

    private static final String DESCRIPTION = "Touch";

    private View view;
    private final OnTouchListener ON_TOUCH_LISTENER = new OnTouchListener();
    private final OnTouchListener DUMMY_LISTENER = new OnTouchListener() {
        @Override // Override our custom listener to do nothing
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
        private float xRelative = 0, yRelative = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v == view) {
                PlayerView playerView = (PlayerView) view;
                // Only do anything if view touched
                if (playerView.isInsideBounds(event.getX(), event.getY())) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // record current touch position in relation to view position
                            xRelative = event.getX() - view.getX();
                            yRelative = event.getY() - view.getY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            // use recorded position to move view without moving to touch centre
                            move(event.getX() - xRelative, event.getY() - yRelative);
                            break;
                    }
                }
            }
            return true;
        }

    }

}
