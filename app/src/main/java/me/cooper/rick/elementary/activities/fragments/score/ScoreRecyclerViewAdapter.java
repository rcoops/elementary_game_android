package me.cooper.rick.elementary.activities.fragments.score;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.cooper.rick.elementary.R;
import me.cooper.rick.elementary.activities.fragments.score.content.ScoreContent.ScoreItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ScoreItem} and makes a call to the
 * specified {@link ScoreFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ScoreRecyclerViewAdapter extends RecyclerView.Adapter<ScoreRecyclerViewAdapter.ViewHolder> {

    private final List<ScoreItem> mValues;
    private final ScoreFragment.OnListFragmentInteractionListener mListener;

    public ScoreRecyclerViewAdapter(List<ScoreItem> items, ScoreFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_score, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).playerName);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public ScoreItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.playerName);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
