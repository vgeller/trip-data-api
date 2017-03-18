package ninja.dock.tripdata;

import java.util.EnumMap;

public enum FileField {
    TRIP_DURATION("Trip Duration", "tripduration"),
    START_TIME("Start Time", "starttime"),
    END_TIME("Stop Time", "stoptime"),
    START_STATION_ID("Start Station ID", "start station id"),
    START_STATION_NAME("Start Station Name", "start station name"),
    START_STATION_LATITUDE("Start Station Latitude", "start station latitude"),
    START_STATION_LONGITUDE("Start Station Longitude", "start station longitude"),
    END_STATION_ID("End Station ID", "end station id"),
    END_STATION_NAME("End Station Name", "end station name"),
    END_STATION_LATITUDE("End Station Latitude", "end station latitude"),
    END_STATION_LONGITUDE("End Station Longitude", "end station longitude"),
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
