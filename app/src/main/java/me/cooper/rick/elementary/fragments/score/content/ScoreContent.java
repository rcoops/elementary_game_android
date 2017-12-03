package me.cooper.rick.elementary.fragments.score.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample playerName for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ScoreContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<ScoreItem> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, ScoreItem> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(ScoreItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static ScoreItem createDummyItem(int position) {
        return new ScoreItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore playerScore information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of Score.
     */
    public static class ScoreItem {
        public final String id;
        public final String playerName;
        public final String playerScore;

        public ScoreItem(String id, String playerName, String playerScore) {
            this.id = id;
            this.playerName = playerName;
            this.playerScore = playerScore;
        }

        @Override
        public String toString() {
            return playerName + ", " + playerScore;
        }
    }
}
