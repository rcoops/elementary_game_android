package me.cooper.rick.elementary.models;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.widget.AppCompatTextView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import me.cooper.rick.elementary.activity.fragment.layout.AnswerViewParams;

import static me.cooper.rick.elementary.activity.fragment.layout.AnswerViewParams.buildRandomisedParams;

public class ElementAnswerView extends AppCompatTextView {

    private String answer;
    private RelativeLayout.LayoutParams layoutParams;

    public ElementAnswerView(Context context) {
        super(context);
    }

    public ElementAnswerView(Context context, AnswerViewParams params) {
        this(context);
        this.answer = params.getAnswer().second;
        this.layoutParams = params.getLayoutParams();
        setRotation(params.getRotation());
        setText(params.getAnswer().first + ": " + params.getAnswer().second);
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

}
