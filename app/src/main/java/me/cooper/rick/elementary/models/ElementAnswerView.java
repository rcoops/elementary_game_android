package me.cooper.rick.elementary.models;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.util.Pair;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import me.cooper.rick.elementary.R;
import me.cooper.rick.elementary.activity.fragment.layout.AnswerViewParams;

import static android.view.Gravity.CENTER;
import static me.cooper.rick.elementary.activity.fragment.layout.AnswerViewParams.buildRandomisedParams;

public class ElementAnswerView extends AppCompatTextView {

    private String answer;
    private RelativeLayout.LayoutParams layoutParams;
    protected Rect viewBounds = new Rect();

    public ElementAnswerView(Context context) {
        super(context);
    }

    public ElementAnswerView(Context context, AnswerViewParams params) {
        this(context);
        this.answer = params.getAnswer().second;
        this.layoutParams = params.getLayoutParams();
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        setGravity(CENTER);
        setMaxLines(5);
        setPadding(8,8,8,8);
        setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        setText(params.getAnswer().first + ":\n" + params.getAnswer().second);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        viewBounds.set(this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
    }

    public String getAnswer() {
        return answer;
    }

    public RelativeLayout.LayoutParams getLayoutParams() {
        return layoutParams;
    }

    public static List<ElementAnswerView> buildAnswerViews(Context context,
                                                           List<Pair<String, String>> answers) {
        List<AnswerViewParams> answerViewParams = buildRandomisedParams(answers);
        List<ElementAnswerView> agg = new ArrayList<>();
        for (AnswerViewParams params : answerViewParams) {
            agg.add(new ElementAnswerView(context, params));
        }
        return agg;
    }

    public boolean isIntersecting(ChemicalSymbolView view) {
        return viewBounds.intersect(view.viewBounds);
    }

}
