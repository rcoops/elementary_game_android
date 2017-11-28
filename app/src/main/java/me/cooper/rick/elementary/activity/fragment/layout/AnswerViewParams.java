package me.cooper.rick.elementary.activity.fragment.layout;

import android.support.v4.util.Pair;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.RelativeLayout.ALIGN_PARENT_END;
import static android.widget.RelativeLayout.ALIGN_PARENT_LEFT;
import static android.widget.RelativeLayout.ALIGN_PARENT_RIGHT;
import static android.widget.RelativeLayout.ALIGN_PARENT_START;
import static android.widget.RelativeLayout.ALIGN_PARENT_TOP;
import static android.widget.RelativeLayout.LayoutParams;
import static java.util.Arrays.asList;
import static java.util.Collections.shuffle;
import static me.cooper.rick.elementary.activity.fragment.layout.AnswerViewParams.TextViewParamSets.buildLayoutParams;

public class AnswerViewParams {

    private RelativeLayout.LayoutParams layoutParams;
    private int rotation;
    private Pair<String, String> answer;

    private AnswerViewParams(LayoutParams layoutParams, int rotation) {
        this.layoutParams = layoutParams;
        this.rotation = rotation;
    }

    public AnswerViewParams(TextViewParamSets paramSet) {
        this(buildLayoutParams(paramSet.layoutParams), paramSet.rotation);
    }

    public LayoutParams getLayoutParams() {
        return layoutParams;
    }

    public void setLayoutParams(LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public Pair<String, String> getAnswer() {
        return answer;
    }

    public void setAnswer(Pair<String, String> answer) {
        this.answer = answer;
    }

    private static List<AnswerViewParams> buildAnswerParamStructures() {
        List<AnswerViewParams> agg = new ArrayList<>();
        for (TextViewParamSets paramSet : TextViewParamSets.values()) {
            agg.add(new AnswerViewParams(paramSet));
        }
        return agg;
    }

    public static List<AnswerViewParams> buildRandomisedParams(List<Pair<String, String>> answers) {
        List<AnswerViewParams> params = buildAnswerParamStructures();

        shuffle(params); // Randomise Elements

        for (int i = 0; i < params.size(); ++i) {
            params.get(i).setAnswer(answers.get(i));
        }

        return params;
    }

    enum TextViewParamSets {

        TOP(asList(ALIGN_PARENT_LEFT, ALIGN_PARENT_RIGHT,
                ALIGN_PARENT_START, ALIGN_PARENT_END, ALIGN_PARENT_TOP), 0),
        BOTTOM(asList(ALIGN_PARENT_LEFT, ALIGN_PARENT_RIGHT,
                ALIGN_PARENT_START, ALIGN_PARENT_END, ALIGN_PARENT_TOP), 0),
        LEFT(asList(ALIGN_PARENT_LEFT, ALIGN_PARENT_START), 270),
        RIGHT(asList(ALIGN_PARENT_RIGHT, ALIGN_PARENT_END), 90);

        List<Integer> layoutParams;
        int rotation;

        TextViewParamSets(List<Integer> layoutParams, int rotation) {
            this.layoutParams = layoutParams;
            this.rotation = rotation;
        }

        static LayoutParams buildLayoutParams(List<Integer> rules) {
            LayoutParams params = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            for (int rule : rules) {
                params.addRule(rule);
            }

            return params;
        }

    }

}
