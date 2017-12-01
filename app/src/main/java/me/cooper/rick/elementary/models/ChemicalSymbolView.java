package me.cooper.rick.elementary.models;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import me.cooper.rick.elementary.constants.Element;

public class ChemicalSymbolView extends View {

    public static final int RADIUS = 100;

    private Element element;

    private Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int backgroundColor = Color.WHITE;
    private Point startingPosition;
    private Point position = new Point();
    private Rect maxBounds;
    protected Rect viewBounds = new Rect();

    private final float HSL[] = new float[3];

    public ChemicalSymbolView(Context context) {
        super(context);
    }

    public ChemicalSymbolView(Context context, Point startingPosition, Point maxBounds) {
        super(context);
        this.startingPosition = startingPosition;
        this.maxBounds = new Rect(RADIUS, RADIUS, maxBounds.x - RADIUS, maxBounds.y - RADIUS);
        textPaint.setTextSize(100f);
        textPaint.setTextAlign(Paint.Align.CENTER);
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
        circlePaint.setColor(Color.BLACK);
        canvas.drawCircle(position.x, position.y, RADIUS, circlePaint);
        circlePaint.setColor(backgroundColor);
        canvas.drawCircle(position.x, position.y, RADIUS - 5, circlePaint);
        canvas.drawText(element.chemicalSymbol, position.x, position.y + 35, textPaint);

        invalidate();
    }

    private boolean isOutsideBound(int position, int bound) {
        return position < bound;
    }

    private int getAdjustedAxis(int position, int minBound, int maxBound) {
        if (isOutsideBound(-position, -maxBound)) {
            return maxBound;
        } else if (isOutsideBound(position, minBound)) {
            return minBound;
        }
        return position;
    }

    private int getForeGroundColor(int backgroundColor) {
        int red   = Color.red(backgroundColor);
        int green = Color.green(backgroundColor);
        int blue  = Color.blue(backgroundColor);

        ColorUtils.RGBToHSL(red, green, blue, HSL);
        return HSL[2] > 0.5 ? Color.BLACK : Color.WHITE;
    }

    public void reset(Element element) {
        this.element = element;
        backgroundColor = Color.parseColor("#" + element.hexColourCode);
        textPaint.setColor(getForeGroundColor(backgroundColor));
        position.set(startingPosition.x, startingPosition.y);
    }

}

