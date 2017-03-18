package ninja.dock.tripdata.test;

import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import ninja.dock.tripdata.TripDataLoaderException;
import ninja.dock.tripdata.TripParser;
import ninja.dock.tripdata.model.Gender;
import ninja.dock.tripdata.model.Trip;
import ninja.dock.tripdata.model.TripEvent;
import ninja.dock.tripdata.model.UserType;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class TripParserTest {

    @Test
    public void oldFormat() throws URISyntaxException, IOException {
        final File file = file("201601-citibike-tripdata.csv");

        final List<Trip> expectedTrips = new ArrayList<>();
        {
            final Trip trip = new Trip();
            trip.setDurationSeconds(923);
            trip.setBikeId("22285");
            trip.setUserType(UserType.SUBSCRIBER);
            trip.setGender(Gender.MALE);
            trip.setBirthYear(1958);

            final TripEvent start = new TripEvent("268", LocalDateTime.of(2016, 1, 1, 0, 0, 41));
            start.setStationName("Howard St & Centre St");
            start.setStationLatitude(new BigDecimal("40.71910537"));
            start.setStationLongitude(new BigDecimal("-73.99973337"));
            trip.setTripStart(start);

            final TripEvent end = new TripEvent("3002", LocalDateTime.of(2016, 1, 1, 0, 16, 4));
            end.setStationName("South End Ave & Liberty St");
            end.setStationLatitude(new BigDecimal("40.711512"));
            end.setStationLongitude(new BigDecimal("-74.015756"));
            trip.setTripEnd(end);

            expectedTrips.add(trip);
        }
        {
            final Trip trip = new Trip();
            trip.setDurationSeconds(379);
            trip.setBikeId("17827");
            trip.setUserType(UserType.SUBSCRIBER);
            trip.setGender(Gender.MALE);
            trip.setBirthYear(1969);

            final TripEvent start = new TripEvent("476", LocalDateTime.of(2016, 1, 1, 0, 0, 45));
            start.setStationName("E 31 St & 3 Ave");
            start.setStationLatitude(new BigDecimal("40.74394314"));
            start.setStationLongitude(new BigDecimal("-73.97966069"));
            trip.setTripStart(start);

            final TripEvent end = new TripEvent("498", LocalDateTime.of(2016, 1, 1, 0, 7, 4));
            end.setStationName("Broadway & W 32 St");
            end.setStationLatitude(new BigDecimal("40.74854862"));
            end.setStationLongitude(new BigDecimal("-73.98808416"));
            trip.setTripEnd(end);

            expectedTrips.add(trip);
        }

        final List<Trip> actualTrips;
        try (final TripParser parser = new TripParser(file)) {
            actualTrips = Lists.newArrayList(parser);
        }

        assertEquals(expectedTrips, actualTrips);
    }

    @Test
    public void newFormat() throws URISyntaxException, IOException {
        final File file = file("201612-citibike-tripdata.csv");

        final List<Trip> expectedTrips = new ArrayList<>();
        {
            final Trip trip = new Trip();
            trip.setDurationSeconds(528);
            trip.setBikeId("26931");
            trip.setUserType(UserType.SUBSCRIBER);
            trip.setGender(Gender.MALE);
            trip.setBirthYear(1964);

            final TripEvent start = new TripEvent("499", LocalDateTime.of(2016, 12, 1, 0, 0, 4));
            start.setStationName("Broadway & W 60 St");
            start.setStationLatitude(new BigDecimal("40.76915505"));
            start.setStationLongitude(new BigDecimal("-73.98191841"));
            trip.setTripStart(start);

            final TripEvent end = new TripEvent("228", LocalDateTime.of(2016, 12, 1, 0, 8, 52));
            end.setStationName("E 48 St & 3 Ave");
            end.setStationLatitude(new BigDecimal("40.7546011026"));
            end.setStationLongitude(new BigDecimal("-73.971878855"));
            trip.setTripEnd(end);

            expectedTrips.add(trip);
        }
        {
            final Trip trip = new Trip();
            trip.setDurationSeconds(1967);
            trip.setBikeId("21348");
            trip.setUserType(UserType.CUSTOMER);
            trip.setGender(Gender.UNKNOWN);
            trip.setBirthYear(null);

            final TripEvent start = new TripEvent("387", LocalDateTime.of(2016, 12, 1, 0, 1, 52));
            start.setStationName("Centre St & Chambers St");
            start.setStationLatitude(new BigDecimal("40.71273266"));
            start.setStationLongitude(new BigDecimal("-74.0046073"));
            trip.setTripStart(start);

            final TripEvent end = new TripEvent("387", LocalDateTime.of(2016, 12, 1, 0, 34, 40));
            end.setStationName("Centre St & Chambers St");
            end.setStationLatitude(new BigDecimal("40.71273266"));
            end.setStationLongitude(new BigDecimal("-74.0046073"));
            trip.setTripEnd(end);

            expectedTrips.add(trip);
        }
        {
            final Trip trip = new Trip();
            trip.setDurationSeconds(160);
            trip.setBikeId("27008");
            trip.setUserType(UserType.SUBSCRIBER);
            trip.setGender(Gender.FEMALE);
            trip.setBirthYear(1995);

            final TripEvent start = new TripEvent("504", LocalDateTime.of(2016, 12, 1, 0, 13, 41));
            start.setStationName("1 Ave & E 16 St");
            start.setStationLatitude(new BigDecimal("40.73221853"));
            start.setStationLongitude(new BigDecimal("-73.98165557"));
            trip.setTripStart(start);

            final TripEvent end = new TripEvent("511", LocalDateTime.of(2016, 12, 1, 0, 16, 21));
            end.setStationName("E 14 St & Avenue B");
            end.setStationLatitude(new BigDecimal("40.72938685"));
            end.setStationLongitude(new BigDecimal("-73.97772429"));
            trip.setTripEnd(end);

            expectedTrips.add(trip);
        }

        final List<Trip> actualTrips;
        try (final TripParser parser = new TripParser(file)) {
            actualTrips = Lists.newArrayList(parser);
        }

        assertEquals(expectedTrips, actualTrips);
    }

    @Test
    public void invalidFile() throws URISyntaxException, IOException {
        final File file = file("201601-invalid-file.csv");
        try (final TripParser parser = new TripParser(file)) {
            parser.iterator();
            fail("Expected TripDataLoaderException");
        } catch (final TripDataLoaderException e) {
            assertEquals("Unknown file format.  Headers: {foo=0, bar=1}", e.getMessage());
        }
    }

    private File file(final String filename) throws URISyntaxException {
        final URI uri = Resources.getResource(filename).toURI();
        return new File(uri);
    }
}
