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

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class HighScoreFragment extends Fragment implements FireBaseManager.FireBaseListener {

    private static FireBaseManager fireBaseManager = FireBaseManager.getInstance();

    private HighScoreRecyclerViewAdapter adapter;

    public HighScoreFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_high_score_list, container, false);
        fireBaseManager.addHighScoreListListener(this);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter = new HighScoreRecyclerViewAdapter(fireBaseManager.getHighScores(), getActivity());
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    @Override
    public void onFireBaseChange() {
        adapter.notifyDataSetChanged();
    }

}
