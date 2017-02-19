package ninja.dock.tripdata;

import ninja.dock.tripdata.model.Gender;
import ninja.dock.tripdata.model.Trip;
import ninja.dock.tripdata.model.TripEvent;
import ninja.dock.tripdata.model.UserType;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;

public class TripIterator implements Iterator<Trip> {

    private final Iterator<CSVRecord> csvRecordIterator;
    private final LocalDate period;
    private final FileFormat format;

    TripIterator(final Iterator<CSVRecord> csvRecordIterator,
                        final LocalDate period,
                        final FileFormat format) {
        this.csvRecordIterator = csvRecordIterator;
        this.period = period;
        this.format = format;
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
        tripStart.setStationId(record.get(FileField.START_STATION_ID.getFieldName(format)));
        tripStart.setTimestamp(LocalDateTime.parse(record.get(FileField.START_TIME.getFieldName(format)),
                format.getDateTimeFormatter()));

        final TripEvent tripEnd = new TripEvent();
        tripEnd.setStationId(record.get(FileField.END_STATION_ID.getFieldName(format)));
        tripEnd.setTimestamp(LocalDateTime.parse(record.get(FileField.END_TIME.getFieldName(format)),
                format.getDateTimeFormatter()));

        final Trip trip = new Trip();
        trip.setPeriod(period);
        trip.setTripStart(tripStart);
        trip.setTripEnd(tripEnd);
        trip.setDurationSeconds(parseInt(record.get(FileField.TRIP_DURATION.getFieldName(format))));
        trip.setBirthYear(parseInt(record.get(FileField.BIRTH_YEAR.getFieldName(format))));
        trip.setBikeId(record.get(FileField.BIKE_ID.getFieldName(format)));
        trip.setUserType(toUserType(record.get(FileField.USER_TYPE.getFieldName(format))));
        trip.setGender(toGender(record.get(FileField.GENDER.getFieldName(format))));

        return trip;
    }

    private Integer parseInt(final String s) {
        return StringUtils.isBlank(s) ? null : Integer.parseInt(s);
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
            throw new TripDataLoaderException("Unknown user type: " + s);
        }
    }
}
