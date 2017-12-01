package me.cooper.rick.elementary.activity.fragment.layout;

import android.support.v4.util.Pair;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM;
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

    private AnswerViewParams(LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
    }

    public AnswerViewParams(TextViewParamSets paramSet) {
        this(buildLayoutParams(paramSet.width, paramSet.height, paramSet.alignParams));
    }

    public LayoutParams getLayoutParams() {
        return layoutParams;
    }

    public void setLayoutParams(LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
    }

    public static List<AnswerViewParams> buildAnswerLayoutParams() {
        List<AnswerViewParams> agg = new ArrayList<>();
        for (TextViewParamSets paramSet : TextViewParamSets.values()) {
            agg.add(new AnswerViewParams(paramSet));
        }
        return agg;
    }

    enum TextViewParamSets {

        TOP(MATCH_PARENT, WRAP_CONTENT, asList(ALIGN_PARENT_LEFT, ALIGN_PARENT_RIGHT,
                ALIGN_PARENT_START, ALIGN_PARENT_END, ALIGN_PARENT_TOP)),
        BOTTOM(MATCH_PARENT, WRAP_CONTENT, asList(ALIGN_PARENT_LEFT, ALIGN_PARENT_RIGHT,
                ALIGN_PARENT_START, ALIGN_PARENT_END, ALIGN_PARENT_BOTTOM)),
        LEFT(WRAP_CONTENT, MATCH_PARENT, asList(ALIGN_PARENT_LEFT, ALIGN_PARENT_START, ALIGN_PARENT_TOP, ALIGN_PARENT_BOTTOM)),
        RIGHT(WRAP_CONTENT, MATCH_PARENT, asList(ALIGN_PARENT_RIGHT, ALIGN_PARENT_END, ALIGN_PARENT_TOP, ALIGN_PARENT_BOTTOM));

        int width;
        int height;
        List<Integer> alignParams;

        TextViewParamSets(int width, int height, List<Integer> alignParams) {
            this.alignParams = alignParams;
            this.width = width;
            this.height = height;
        }

        static LayoutParams buildLayoutParams(int width, int height, List<Integer> rules) {
            LayoutParams params = new LayoutParams(width, height);
            for (int rule : rules) {
                params.addRule(rule);
            }

            return params;
        }

    }

}
