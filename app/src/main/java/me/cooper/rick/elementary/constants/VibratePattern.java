package me.cooper.rick.elementary.constants;

public enum VibratePattern {

    CORRECT(new long[] {0, 100, 50, 800 }),
    WRONG(new long[] {0, 300, 50, 300, 50, 600}),
    QUIT(new long[] {0, 600, 50, 100, 50, 1200}),
    CLICK(new long[] {0, 100});

    public final long[] pattern;

    VibratePattern(long[] pattern) {
        this.pattern = pattern;
    }

}
