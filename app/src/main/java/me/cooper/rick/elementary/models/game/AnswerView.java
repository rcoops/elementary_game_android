package me.cooper.rick.elementary.models.game;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.util.Pair;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import me.cooper.rick.elementary.R;

public class AnswerView extends AppCompatTextView {

    private String answer;
    private final Rect viewBounds = new Rect();

    public AnswerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        viewBounds.set(getLeft(), getTop(), getRight(), getBottom());
    }

    public void setAnswer(Pair<String, String> answer) {
        this.answer = answer.second;
        setText(getContext().getString(R.string.txt_ele_answer, answer.first,  answer.second));
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isIntersecting(PlayerView view) {
        return viewBounds.intersect(view.viewBounds);
    }

}
