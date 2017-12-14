package me.cooper.rick.elementary.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.cooper.rick.elementary.R;
import me.cooper.rick.elementary.constants.Constants;

import static me.cooper.rick.elementary.constants.Constants.SCORE_BASE_INCREMENT;

public class InstructionsFragment  extends DialogFragment {

    private InstructionsFragmentListener mListener;

    public InstructionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instructions, container, false);
        TextView basicInstructions = view.findViewById(R.id.ins_basic_gameplay);
        basicInstructions.setText(getString(R.string.ins_basic_gameplay, SCORE_BASE_INCREMENT));
        return view;
    }

    interface InstructionsFragmentListener {

    }

}
