package me.cooper.rick.elementary.activity.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import me.cooper.rick.elementary.R;
import me.cooper.rick.elementary.activity.MainActivity;
import me.cooper.rick.elementary.models.Player;


public class NewPlayerFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnPlayerCreatedListener mListener;

    private Button btnCreate;

    public NewPlayerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewPlayerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewPlayerFragment newInstance(String param1, String param2) {
        NewPlayerFragment fragment = new NewPlayerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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
