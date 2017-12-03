package me.cooper.rick.elementary.models.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.util.Pair;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import me.cooper.rick.elementary.R;

public class ElementAnswerView extends AppCompatTextView {

    private String answer;
    protected Rect viewBounds = new Rect();

    public ElementAnswerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewBounds.set(this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
    }

    public void setAnswer(Pair<String, String> answer) {
        this.answer = answer.second;
        setText(getContext().getString(R.string.txt_ele_answer, answer.first,  answer.second));
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isIntersecting(ChemicalSymbolView view) {
        return viewBounds.intersect(view.viewBounds);
    }

}
