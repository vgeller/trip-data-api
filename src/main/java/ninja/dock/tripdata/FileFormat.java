package ninja.dock.tripdata;

import java.time.format.DateTimeFormatter;

public enum FileFormat {
    OLD(DateTimeFormatter.ofPattern("M/d/yyyy HH:mm:ss")),
    NEW(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    ;

    private final DateTimeFormatter dateTimeFormatter;

    FileFormat(final DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }
}
