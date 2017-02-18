package ninja.dock.tripdata;

import java.util.EnumMap;

public enum FileField {
    TRIP_DURATION("Trip Duration", "tripduration"),
    START_TIME("Start Time", "starttime"),
    END_TIME("Stop Time", "stoptime"),
    START_STATION_ID("Start Station ID", "start station id"),
    END_STATION_ID("End Station ID", "end station id"),
    BIKE_ID("Bike ID", "bikeid"),
    USER_TYPE("User Type", "usertype"),
    BIRTH_YEAR("Birth Year", "birth year"),
    GENDER("Gender", "gender");

    private final EnumMap<FileFormat, String> map = new EnumMap<>(FileFormat.class);

    FileField(final String newValue, final String oldValue) {
        map.put(FileFormat.NEW, newValue);
        map.put(FileFormat.OLD, oldValue);
    }

    public String getFieldName(final FileFormat fileFormat) {
        return map.get(fileFormat);
    }

}
