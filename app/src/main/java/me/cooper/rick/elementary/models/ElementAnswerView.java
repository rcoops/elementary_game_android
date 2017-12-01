package me.cooper.rick.elementary.models;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.util.Pair;
import android.support.v7.widget.AppCompatTextView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import me.cooper.rick.elementary.R;
import me.cooper.rick.elementary.activity.fragment.layout.AnswerViewParams;

import static android.view.Gravity.CENTER;
import static me.cooper.rick.elementary.activity.fragment.layout.AnswerViewParams.buildAnswerLayoutParams;

public class ElementAnswerView extends AppCompatTextView {

    private String answer;
    private RelativeLayout.LayoutParams layoutParams;
    protected Rect viewBounds = new Rect();

    public ElementAnswerView(Context context) {
        super(context);
    }

    public ElementAnswerView(Context context, AnswerViewParams params) {
        this(context);
        this.layoutParams = params.getLayoutParams();
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        setGravity(CENTER);
        setMaxLines(5);
        setPadding(8,8,8,8);
        setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
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

    public RelativeLayout.LayoutParams getLayoutParams() {
        return layoutParams;
    }

    public static List<ElementAnswerView> buildAnswerViews(Context context) {
        List<ElementAnswerView> agg = new ArrayList<>();

        List<AnswerViewParams> answerViewParams = buildAnswerLayoutParams();

        for (int i = 0; i < 4; ++i) {
            agg.add(new ElementAnswerView(context, answerViewParams.get(i)));
        }

        return agg;
    }

    public boolean isIntersecting(ChemicalSymbolView view) {
        return viewBounds.intersect(view.viewBounds);
    }

}
