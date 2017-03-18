package ninja.dock.tripdata;

import ninja.dock.tripdata.model.Gender;
import ninja.dock.tripdata.model.Trip;
import ninja.dock.tripdata.model.TripEvent;
import ninja.dock.tripdata.model.UserType;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Iterator;

public class TripIterator implements Iterator<Trip> {

    private final Iterator<CSVRecord> csvRecordIterator;
    private final FileFormat format;
    private final DateParser dateParser;

    TripIterator(final Iterator<CSVRecord> csvRecordIterator,
                        final FileFormat format) {
        this.csvRecordIterator = csvRecordIterator;
        this.format = format;
        this.dateParser = new DateParser();
    }

    @Override
    public boolean hasNext() {
        return csvRecordIterator.hasNext();
    }

    @Override
    public Trip next() {
        final CSVRecord record = csvRecordIterator.next();
        return toTrip(record);
    }

    private Trip toTrip(final CSVRecord record) {
        final TripEvent tripStart = new TripEvent();
        tripStart.setStationId(getString(record, FileField.START_STATION_ID));
        tripStart.setTimestamp(getDate(record, FileField.START_TIME));
        tripStart.setStationName(getString(record, FileField.START_STATION_NAME));
        tripStart.setStationLatitude(getDecimal(record, FileField.START_STATION_LATITUDE));
        tripStart.setStationLongitude(getDecimal(record, FileField.START_STATION_LONGITUDE));

        final TripEvent tripEnd = new TripEvent();
        tripEnd.setStationId(getString(record, FileField.END_STATION_ID));
        tripEnd.setTimestamp(getDate(record, FileField.END_TIME));
        tripEnd.setStationName(getString(record, FileField.END_STATION_NAME));
        tripEnd.setStationLatitude(getDecimal(record, FileField.END_STATION_LATITUDE));
        tripEnd.setStationLongitude(getDecimal(record, FileField.END_STATION_LONGITUDE));

        final Trip trip = new Trip();
        trip.setTripStart(tripStart);
        trip.setTripEnd(tripEnd);
        trip.setDurationSeconds(getInteger(record, FileField.TRIP_DURATION));
        trip.setBirthYear(getInteger(record, FileField.BIRTH_YEAR));
        trip.setBikeId(getString(record, FileField.BIKE_ID));
        trip.setUserType(toUserType(getString(record, FileField.USER_TYPE)));
        trip.setGender(toGender(getString(record, FileField.GENDER)));

        return trip;
    }

    private String getString(final CSVRecord record, final FileField field) {
        return record.get(field.getFieldName(format));
    }

    private Integer getInteger(final CSVRecord record, final FileField field) {
        return parseInt(getString(record, field));
    }

    private LocalDateTime getDate(final CSVRecord record, final FileField field) {
        final String s = getString(record, field);
        return dateParser.parse(s);
    }

    private BigDecimal getDecimal(final CSVRecord record, final FileField field) {
        return new BigDecimal(getString(record, field));
    }

    private Integer parseInt(final String s) {
        return StringUtils.isNumeric(s) ?  Integer.parseInt(s) : null;
    }

    private Gender toGender(final String s) {
        if ("1".equals(s)) {
            return Gender.MALE;
        } else if ("2".equals(s)) {
            return Gender.FEMALE;
        } else if ("0".equals(s)) {
            return Gender.UNKNOWN;
        } else {
            throw new TripDataLoaderException("Unknown gender: " + s);
        }
    }

    private UserType toUserType(final String s) {
        if ("Subscriber".equals(s)) {
            return UserType.SUBSCRIBER;
        } else if ("Customer".equals(s)) {
            return UserType.CUSTOMER;
        } else {
            return UserType.UNKNOWN;
        }
    }
}
