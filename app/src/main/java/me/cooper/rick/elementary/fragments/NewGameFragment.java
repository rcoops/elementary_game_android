package me.cooper.rick.elementary.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.jetbrains.annotations.NotNull;

import me.cooper.rick.elementary.R;
import me.cooper.rick.elementary.models.score.Player;

public class NewGameFragment extends DialogFragment {

    public static final String TAG = "newGame";

    private OnPlayerCreatedListener mListener;

    private Button btnCreate;

    public NewGameFragment() {}

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_game, container, false);
        final EditText edtEnterName = view.findViewById(R.id.edt_enter_name);
        edtEnterName.addTextChangedListener(new EditNameTextWatcher());

        btnCreate = view.findViewById(R.id.btn_create_player);
        btnCreate.setEnabled(false);
        btnCreate.setOnClickListener(v ->
                mListener.onPlayerCreated(new Player(edtEnterName.getText().toString()))
        );

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPlayerCreatedListener) {
            mListener = (OnPlayerCreatedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnPlayerCreatedListener {
        void onPlayerCreated(Player player);
    }

    private class EditNameTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // No implementation necessary
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // No implementation necessary
        }

        @Override
        public void afterTextChanged(Editable editable) {
            btnCreate.setEnabled(!editable.toString().isEmpty());
        }

    }

}
