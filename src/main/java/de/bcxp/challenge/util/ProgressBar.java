package de.bcxp.challenge.util;

/**
 * Simple console progress bar for in-place updates. Usage: ProgressBar bar =
 * new ProgressBar("Loading"); bar.update(i, total); bar.complete();
 */
public final class ProgressBar {

    private static final int BAR_WIDTH = 30;
    private final String label;
    private int lastPercent = -1;

    public ProgressBar(String label) {
        this.label = label;
    }

    public void update(int current, int total) {
        if (total <= 0) {
            return;
        }
        int percent = Math.min(100, (int) Math.round(current * 100.0 / total));
        if (percent == lastPercent) {
            return;
        }
        lastPercent = percent;
        int filled = percent * BAR_WIDTH / 100;
        StringBuilder bar = new StringBuilder();
        bar.append('\r').append(label).append(" [");
        for (int i = 0; i < BAR_WIDTH; i++) {
            bar.append(i < filled ? '=' : ' ');
        }
        bar.append("] ").append(String.format("%3d%%", percent));
        System.out.print(bar);
        System.out.flush();
    }

    public void complete() {
        if (lastPercent < 100) {
            update(1, 1);
        }
        System.out.println();
    }
}
