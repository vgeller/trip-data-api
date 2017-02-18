package ninja.dock.tripdata;

import ninja.dock.tripdata.model.Gender;
import ninja.dock.tripdata.model.Trip;
import ninja.dock.tripdata.model.TripEvent;
import ninja.dock.tripdata.model.UserType;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Load trip history data from https://www.citibikenyc.com/system-data
 */
public class TripParser {

    private final TripHandler tripHandler;

    public TripParser(final TripHandler tripHandler) {
        this.tripHandler = tripHandler;
    }

    public void load(final Path importDir) {
        try {
            try (final Stream<Path> paths = Files.walk(importDir)) {
                paths.forEach(filePath -> {
                    final File file = filePath.toFile();
                    if (file.isFile()) {
                        load(file);
                    }
                });
            }
        } catch (IOException e) {
            throw new TripDataLoaderException(e);
        }
    }

    public void load(final File file) {
        try (final Reader reader = new FileReader(file)) {
            final CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
            final LocalDate period = toPeriod(file.getName());
            final FileFormat fileFormat = determineFileFormat(parser);
            for (final CSVRecord record : parser) {
                tripHandler.handle(toTrip(record, period, fileFormat));
            }
        } catch (final IOException e) {
            throw new TripDataLoaderException(e);
        }
    }

    private Trip toTrip(final CSVRecord record, final LocalDate period, final FileFormat format) {
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

    private LocalDate toPeriod(final String filename) {
        final String dateStr = filename.substring(0, 6) + "01";
        return LocalDate.parse(dateStr, DateTimeFormatter.BASIC_ISO_DATE);
    }

    private FileFormat determineFileFormat(final CSVParser parser) {
        final Map<String, Integer> headers = parser.getHeaderMap();
        final FileField testField = FileField.BIKE_ID;
        if (headers.containsKey(testField.getFieldName(FileFormat.NEW))) {
            return FileFormat.NEW;
        } else if (headers.containsKey(testField.getFieldName(FileFormat.OLD))) {
            return FileFormat.OLD;
        } else {
            throw new TripDataLoaderException("Unknown file format.  Headers: " + headers);
        }
    }
}
