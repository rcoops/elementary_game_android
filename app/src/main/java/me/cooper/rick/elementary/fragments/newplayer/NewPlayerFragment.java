package me.cooper.rick.elementary.fragments.newplayer;

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

import me.cooper.rick.elementary.R;
import me.cooper.rick.elementary.models.Player;


public class NewPlayerFragment extends DialogFragment {

    private OnPlayerCreatedListener mListener;

    private Button btnCreate;

    public NewPlayerFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_player, container, false);
        final EditText edtEnterName = (EditText) view.findViewById(R.id.edt_enter_name);
        edtEnterName.addTextChangedListener(new EditNameTextWatcher());

        btnCreate = view.findViewById(R.id.btn_create_player);
        btnCreate.setEnabled(false);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPlayerCreated(new Player(edtEnterName.getText().toString()));
            }
        });

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
