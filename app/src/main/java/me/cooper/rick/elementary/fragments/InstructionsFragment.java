package me.cooper.rick.elementary.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.cooper.rick.elementary.R;

import static me.cooper.rick.elementary.models.score.Score.SCORE_BASE_INCREMENT;
import static me.cooper.rick.elementary.models.score.Score.SCORE_INCREMENT_INCREMENT;
import static me.cooper.rick.elementary.services.QuizManager.NO_OF_ELEMENTS;

public class InstructionsFragment extends DialogFragment {

    public static final String TAG = "instructions";

    private OnFragmentInteractionListener mListener;

    public InstructionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instructions, container, false);
        TextView basicInstructions = view.findViewById(R.id.ins_basic_gameplay);
        basicInstructions.setText(getString(R.string.ins_basic_gameplay, NO_OF_ELEMENTS,
                SCORE_INCREMENT_INCREMENT, SCORE_BASE_INCREMENT));
        view.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteraction();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }

}
