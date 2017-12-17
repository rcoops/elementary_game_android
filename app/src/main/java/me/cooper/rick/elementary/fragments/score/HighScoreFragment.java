package me.cooper.rick.elementary.fragments.score;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.cooper.rick.elementary.R;
import me.cooper.rick.elementary.services.FireBaseManager;

public class HighScoreFragment extends Fragment implements FireBaseManager.FireBaseListener {

    public static final String TAG = "highScores";

    private OnFragmentInteractionListener mListener;

    private static FireBaseManager fireBaseManager = FireBaseManager.getInstance();

    private HighScoreRecyclerViewAdapter adapter;

    public HighScoreFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_high_score_list, container, false);
        fireBaseManager.addHighScoreListListener(this);
        // Set the adapter
        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.score_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new HighScoreRecyclerViewAdapter(fireBaseManager.getHighScores(), getActivity());
        recyclerView.setAdapter(adapter);
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

    @Override
    public void onFireBaseChange() {
        adapter.notifyDataSetChanged();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }

}
