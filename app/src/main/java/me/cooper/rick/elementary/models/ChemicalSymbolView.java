package me.cooper.rick.elementary.models;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.view.View;

import me.cooper.rick.elementary.constants.Element;

public class ChemicalSymbolView extends View {

    public static final float RADIUS = 100f;

    private Element element;

    private Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private PointF position;
    private RectF maxBounds;

    public ChemicalSymbolView(Context context) {
        super(context);
    }

    public ChemicalSymbolView(Context context, Element element, float positionX, float positionY, float maxX, float maxY) {
        super(context);
        this.element = element;

        circlePaint.setColor(Color.parseColor("#" + element.hexColourCode));
        circlePaint.setStrokeWidth(5f);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(100f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        position = new PointF(positionX, positionY);
        maxBounds = new RectF(RADIUS, RADIUS, maxX - RADIUS, maxY - (2 * RADIUS));
    }

    public Element getElement() {
        return element;
    }

    @Override
    public float getX() {
        return position.x;
    }

    @Override
    public void setX(float x) {
        position.x = getAdjustedAxis(x, maxBounds.left, maxBounds.right);
    }

    @Override
    public float getY() {
        return position.y;
    }

    @Override
    public void setY(float y) {
        position.y = getAdjustedAxis(y, maxBounds.top, maxBounds.bottom);
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(position.x, position.y, RADIUS, circlePaint);
        canvas.drawText(element.chemicalSymbol, position.x, position.y, textPaint);

        invalidate();
    }

    private boolean isOutsideBound(float position, float bound) {
        return position < bound;
    }

    private float getAdjustedAxis(float position, float minBound, float maxBound) {
        if (isOutsideBound(-position, -maxBound)) {
            return maxBound;
        } else if (isOutsideBound(position, minBound)) {
            return minBound;
        }
        return position;
    }

}

