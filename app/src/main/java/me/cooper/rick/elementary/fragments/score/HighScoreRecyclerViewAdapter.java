package me.cooper.rick.elementary.fragments.score;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.cooper.rick.elementary.R;
import me.cooper.rick.elementary.models.score.Score;


public class HighScoreRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Score> highScores;

    private final Context context;

    public HighScoreRecyclerViewAdapter(List<Score> highScores, Context context) {
        this.highScores = highScores;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.fragment_high_score, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ViewHolder itemHolder = (ViewHolder) holder;
        itemHolder.score = highScores.get(position);
        itemHolder.numberView.setText(context.getString(R.string.numeric, position + 1));
        itemHolder.playerNameView.setText(itemHolder.score.getPlayerName());
        itemHolder.scoreView.setText(context.getString(R.string.numeric, itemHolder.score.getScore()));
    }

    @Override
    public int getItemCount() {
        return highScores.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView numberView;
        final TextView playerNameView;
        final TextView scoreView;
        Score score;

        ViewHolder(View view) {
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

