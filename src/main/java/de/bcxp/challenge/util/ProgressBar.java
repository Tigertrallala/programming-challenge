package de.bcxp.challenge.util;

/**
 * Console progress bar with in-place updates using carriage return.
 *
 * <p>
 * Displays a visual progress indicator like:</p>
 * <pre>
 * Reading CSV [==============                ] 47%
 * </pre>
 *
 * <p>
 * The bar updates in-place on the same line, only redrawing when the percentage
 * value changes to minimize console output.</p>
 *
 * <p>
 * Usage:</p>
 * <pre>
 * ProgressBar progress = new ProgressBar("Loading");
 * for (int i = 0; i &lt; total; i++) {
 *     // do work
 *     progress.update(i + 1, total);
 * }
 * progress.complete();
 * </pre>
 */
public final class ProgressBar {

    /**
     * Width of the progress bar in characters.
     */
    private static final int BAR_WIDTH = 30;

    /**
     * Label displayed before the progress bar.
     */
    private final String label;

    /**
     * Last displayed percentage, used to avoid redundant redraws.
     */
    private int lastPercent = -1;

    /**
     * Creates a progress bar with the specified label.
     *
     * @param label text displayed before the bar (e.g., "Loading", "Reading
     * CSV")
     */
    public ProgressBar(String label) {
        this.label = label;
    }

    /**
     * Updates the progress bar display.
     *
     * <p>
     * Only redraws when the percentage value changes to minimize console output
     * overhead.</p>
     *
     * @param current current progress value (0 to total)
     * @param total total value representing 100%
     */
    public void update(int current, int total) {
        if (total <= 0) {
            return;
        }
        int percent = (int) Math.round((current * 100.0) / total);
        percent = Math.min(percent, 100);
        if (percent == lastPercent) {
            return;
        }
        lastPercent = percent;
        int filled = (percent * BAR_WIDTH) / 100;
        StringBuilder bar = new StringBuilder();
        bar.append('\r').append(label).append(" [");
        for (int i = 0; i < BAR_WIDTH; i++) {
            bar.append(i < filled ? '=' : ' ');
        }
        bar.append("] ").append(String.format("%3d%%", percent));
        System.out.print(bar);
        System.out.flush();
    }

    /**
     * Completes the progress bar and moves to a new line.
     */
    public void complete() {
        if (lastPercent < 100) {
            update(1, 1);
        }
        System.out.println();
    }
}
