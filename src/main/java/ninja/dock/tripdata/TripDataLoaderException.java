package ninja.dock.tripdata;

public class TripDataLoaderException extends RuntimeException {

    TripDataLoaderException(final Throwable cause) {
        super(cause);
    }

    TripDataLoaderException(final String message) {
        super(message);
    }
}
