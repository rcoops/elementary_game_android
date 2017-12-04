package me.cooper.rick.elementary.fragments.score;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.cooper.rick.elementary.R;
import me.cooper.rick.elementary.models.Score;


public class HighScoreRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private final List<Score> highScores;

    private final Context context;

    public HighScoreRecyclerViewAdapter(List<Score> highScores, Context context) {
        this.highScores = highScores;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(context)
                    .inflate(R.layout.fragment_high_score_header, parent, false);
            return new HeaderHolder(v);
        }
        View view = LayoutInflater.from(context)
                .inflate(R.layout.fragment_high_score, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderHolder) {
            HeaderHolder headerHolder = (HeaderHolder) holder;
            headerHolder.headerView.setText("High Scores");
        } else {
            ViewHolder itemHolder = (ViewHolder) holder;
            itemHolder.score = highScores.get(position - 1);
            itemHolder.numberView.setText(context.getString(R.string.numeric, position));
            itemHolder.playerNameView.setText(itemHolder.score.getPlayerName());
            itemHolder.scoreView.setText(context.getString(R.string.numeric, itemHolder.score.getScore()));

            itemHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }
    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return highScores.size() + 1;
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

    public class HeaderHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView headerView;

        public HeaderHolder(View view) {
            super(view);
            mView = view;
            headerView = (TextView) view.findViewById(R.id.txt_header);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + headerView.getText() + "'";
        }
    }
}
