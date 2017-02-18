package ninja.dock.tripdata;

import com.google.common.io.Resources;
import ninja.dock.tripdata.model.Gender;
import ninja.dock.tripdata.model.Trip;
import ninja.dock.tripdata.model.TripEvent;
import ninja.dock.tripdata.model.UserType;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TripParserTest {

    @Test
    public void oldFormat() throws URISyntaxException {
        final File file = file("201601-citibike-tripdata.csv");

        final List<Trip> expected = new ArrayList<>();
        {
            final Trip trip = new Trip();
            trip.setPeriod(LocalDate.of(2016, 1, 1));
            trip.setDurationSeconds(923);
            trip.setBikeId("22285");
            trip.setUserType(UserType.SUBSCRIBER);
            trip.setGender(Gender.MALE);
            trip.setBirthYear(1958);
            trip.setTripStart(new TripEvent("268", LocalDateTime.of(2016, 1, 1, 0, 0, 41)));
            trip.setTripEnd(new TripEvent("3002", LocalDateTime.of(2016, 1, 1, 0, 16, 4)));
            expected.add(trip);
        }
        {
            final Trip trip = new Trip();
            trip.setPeriod(LocalDate.of(2016, 1, 1));
            trip.setDurationSeconds(379);
            trip.setBikeId("17827");
            trip.setUserType(UserType.SUBSCRIBER);
            trip.setGender(Gender.MALE);
            trip.setBirthYear(1969);
            trip.setTripStart(new TripEvent("476", LocalDateTime.of(2016, 1, 1, 0, 0, 45)));
            trip.setTripEnd(new TripEvent("498", LocalDateTime.of(2016, 1, 1, 0, 7, 4)));
            expected.add(trip);
        }

        final List<Trip> trips = new ArrayList<>();
        new TripParser(trips::add).load(file);

        assertEquals(expected, trips);
    }

    @Test
    public void newFormat() throws URISyntaxException {
        final File file = file("201612-citibike-tripdata.csv");

        final List<Trip> expected = new ArrayList<>();
        {
            final Trip trip = new Trip();
            trip.setPeriod(LocalDate.of(2016, 12, 1));
            trip.setDurationSeconds(528);
            trip.setBikeId("26931");
            trip.setUserType(UserType.SUBSCRIBER);
            trip.setGender(Gender.MALE);
            trip.setBirthYear(1964);
            trip.setTripStart(new TripEvent("499", LocalDateTime.of(2016, 12, 1, 0, 0, 4)));
            trip.setTripEnd(new TripEvent("228", LocalDateTime.of(2016, 12, 1, 0, 8, 52)));
            expected.add(trip);
        }
        {
            final Trip trip = new Trip();
            trip.setPeriod(LocalDate.of(2016, 12, 1));
            trip.setDurationSeconds(1967);
            trip.setBikeId("21348");
            trip.setUserType(UserType.CUSTOMER);
            trip.setGender(Gender.UNKNOWN);
            trip.setBirthYear(null);
            trip.setTripStart(new TripEvent("387", LocalDateTime.of(2016, 12, 1, 0, 1, 52)));
            trip.setTripEnd(new TripEvent("387", LocalDateTime.of(2016, 12, 1, 0, 34, 40)));
            expected.add(trip);
        }
        {
            final Trip trip = new Trip();
            trip.setPeriod(LocalDate.of(2016, 12, 1));
            trip.setDurationSeconds(160);
            trip.setBikeId("27008");
            trip.setUserType(UserType.SUBSCRIBER);
            trip.setGender(Gender.FEMALE);
            trip.setBirthYear(1995);
            trip.setTripStart(new TripEvent("504", LocalDateTime.of(2016, 12, 1, 0, 13, 41)));
            trip.setTripEnd(new TripEvent("511", LocalDateTime.of(2016, 12, 1, 0, 16, 21)));
            expected.add(trip);
        }


        final List<Trip> trips = new ArrayList<>();
        new TripParser(trips::add).load(file);

        assertEquals(expected, trips);
    }

    private File file(final String filename) throws URISyntaxException {
        final URI uri = Resources.getResource(filename).toURI();
        return new File(uri);
    }
}
