package ninja.dock.tripdata;

import ninja.dock.tripdata.model.Trip;

@FunctionalInterface
public interface TripHandler {

    void handle(final Trip trip);

}
