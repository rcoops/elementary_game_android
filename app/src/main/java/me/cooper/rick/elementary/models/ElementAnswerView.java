package me.cooper.rick.elementary.models;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.util.Pair;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class ElementAnswerView extends AppCompatTextView {

    private String answer;
    protected Rect viewBounds = new Rect();

    public ElementAnswerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        viewBounds.set(this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
    }

    public void setAnswer(Pair<String, String> answer) {
        this.answer = answer.second;
        setText(answer.first + ":\n" + answer.second);
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isIntersecting(ChemicalSymbolView view) {
        return viewBounds.intersect(view.viewBounds);
    }

}
