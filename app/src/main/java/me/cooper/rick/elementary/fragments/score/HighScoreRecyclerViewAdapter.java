package me.cooper.rick.elementary.fragments.score;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.cooper.rick.elementary.R;
import me.cooper.rick.elementary.fragments.score.HighScoreFragment.OnListFragmentInteractionListener;
import me.cooper.rick.elementary.models.Score;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Score} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class HighScoreRecyclerViewAdapter extends RecyclerView.Adapter<HighScoreRecyclerViewAdapter.ViewHolder> {

    private final List<Score> highScores;

    private final Context context;

    public HighScoreRecyclerViewAdapter(List<Score> highScores, Context context) {
        this.highScores = highScores;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_high_score, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.score = highScores.get(position);
        holder.numberView.setText(context.getString(R.string.numeric, position));
        holder.playerNameView.setText(holder.score.getPlayerName());
        holder.scoreView.setText(context.getString(R.string.numeric, holder.score.getScore()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return highScores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView numberView;
        public final TextView playerNameView;
        public final TextView scoreView;
        public Score score;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            numberView = (TextView) view.findViewById(R.id.number);
            playerNameView = (TextView) view.findViewById(R.id.player_name);
            scoreView = (TextView) view.findViewById(R.id.high_score);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + scoreView.getText() + "'";
        }
    }
}
