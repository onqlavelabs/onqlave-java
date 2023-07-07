package com.onqlave.utils;

import java.time.Duration;
import java.time.Instant;

public class DurationFormatter {
    public static String DurationBetween(Instant start, Instant end) {
        Duration duration = Duration.between(start, end);
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        double seconds = duration.toSecondsPart() + duration.toMillisPart() / 1000.0;
        return String.format("%dh%dm%.1fs", hours, minutes, seconds);
    }
}
