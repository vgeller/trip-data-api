package ninja.dock.tripdata;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class DateParser {

    private static final List<DateTimeFormatter> FORMATTER_LIST = new ArrayList<>();

    static {
        FORMATTER_LIST.add(DateTimeFormatter.ofPattern("M/d/yyyy HH:mm:ss"));
        FORMATTER_LIST.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        FORMATTER_LIST.add(DateTimeFormatter.ofPattern("M/d/yyyy H:mm"));
    }

    private DateTimeFormatter currentFormatter;

    public LocalDateTime parse(final String s) {
        if (currentFormatter != null) {
            return LocalDateTime.parse(s, currentFormatter);
        }
        for (final DateTimeFormatter formatter : FORMATTER_LIST) {
            try {
                final LocalDateTime dateTime = LocalDateTime.parse(s, formatter);
                currentFormatter = formatter;
                return dateTime;
            } catch (final DateTimeParseException e) {
                // do nothing
            }
        }

        throw new DateTimeParseException("Could not parse date using any known format: " + s, s, 0);
    }
}
