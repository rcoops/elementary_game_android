package me.cooper.rick.elementary.models.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.v4.graphics.ColorUtils;
import android.view.View;

import me.cooper.rick.elementary.constants.element.Element;
import me.cooper.rick.elementary.models.movestrategies.MoveStrategy;
import me.cooper.rick.elementary.models.movestrategies.SensorMoveStrategy;
import me.cooper.rick.elementary.models.movestrategies.TouchMoveStrategy;
import me.cooper.rick.elementary.models.util.CircularLinkedList;

import static android.content.Context.SENSOR_SERVICE;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;
import static android.graphics.Color.blue;
import static android.graphics.Color.green;
import static android.graphics.Color.parseColor;
import static android.graphics.Color.red;

public class PlayerView extends View {

    private static final int RADIUS = 75;

    private Element element;

    private Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int backgroundColor = WHITE;
    private Point startingPosition;
    private Point position = new Point();
    private Rect maxBounds;
    protected Rect viewBounds = new Rect();

    private final float HUE_SATURATION_LIGHTNESS[] = new float[3];

    private MoveStrategy activeMoveStrategy;

    private CircularLinkedList<MoveStrategy> moveStrategies = new CircularLinkedList<>();

    public PlayerView(Context context) {
        super(context);
    }

    public PlayerView(Context context, Point startingPosition, Point maxBounds) {
        super(context);
        this.startingPosition = startingPosition;
        this.maxBounds = new Rect(RADIUS, RADIUS, maxBounds.x - RADIUS, maxBounds.y - RADIUS);
        textPaint.setTextSize(RADIUS);
        textPaint.setTextAlign(Paint.Align.CENTER);

        SensorManager sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            moveStrategies.add(new SensorMoveStrategy(this, sensorManager));
        }
        moveStrategies.add(new TouchMoveStrategy(this));
        activeMoveStrategy = moveStrategies.getCurrent();
    }

    @Override
    public float getX() {
        return position.x;
    }

    @Override
    public void setX(float x) {
        position.x = getAdjustedAxis((int) x, maxBounds.left, maxBounds.right);
    }

    @Override
    public float getY() {
        return position.y;
    }

    @Override
    public void setY(float y) {
        position.y = getAdjustedAxis((int) y, maxBounds.top, maxBounds.bottom);
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        viewBounds.set(position.x - RADIUS, position.y - RADIUS, position.x + RADIUS, position.y + RADIUS);
        circlePaint.setColor(BLACK);
        canvas.drawCircle(position.x, position.y, RADIUS, circlePaint);
        circlePaint.setColor(backgroundColor);
        canvas.drawCircle(position.x, position.y, RADIUS - 5, circlePaint);
        canvas.drawText(element.chemicalSymbol, position.x, position.y + 17, textPaint);

        invalidate();
    }

    public boolean isInsideBounds(float x, float y) {
        return x > viewBounds.left
                && x < viewBounds.right
                && y > viewBounds.top
                && y < viewBounds.bottom;
    }

    private boolean isOutsideMaxBound(int position, int bound) {
        return position < bound;
    }

    private int getAdjustedAxis(int position, int minBound, int maxBound) {
        if (isOutsideMaxBound(-position, -maxBound)) {
            return maxBound;
        } else if (isOutsideMaxBound(position, minBound)) {
            return minBound;
        }
        return position;
    }

    //https://developer.android.com/reference/android/support/v4/graphics/ColorUtils.html
    private int getForeGroundColor(int backgroundColor) {
        ColorUtils.RGBToHSL(red(backgroundColor), green(backgroundColor), blue(backgroundColor),
                HUE_SATURATION_LIGHTNESS
        );

        return HUE_SATURATION_LIGHTNESS[2] > 0.5 ? BLACK : WHITE;
    }

    public void resetPosition() {
        position.set(startingPosition.x, startingPosition.y);
    }

    public void reset(Element element) {
        this.element = element;
        backgroundColor = parseColor("#" + element.hexColourCode);
        textPaint.setColor(getForeGroundColor(backgroundColor));
        resetPosition();
    }

    public void startMoving() {
        activeMoveStrategy.registerListener();
    }

    public void stopMoving() {
        activeMoveStrategy.unregisterListener();
    }

    public String cycleNextStrategy() {
        stopMoving();
        activeMoveStrategy = moveStrategies.cycleCurrentToNext();
        startMoving();
        MoveStrategy next = moveStrategies.getNext();

        return next == null ? null : next.getDescription();
    }

}

