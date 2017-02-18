package ninja.dock.tripdata.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

public class TripEvent {

    private String stationId;
    private LocalDateTime timestamp;

    public TripEvent() {
        // default constructor
    }

    public TripEvent(final String stationId, final LocalDateTime timestamp) {
        this.stationId = stationId;
        this.timestamp = timestamp;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(final String stationId) {
        this.stationId = stationId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final TripEvent tripEvent = (TripEvent) o;

        return new EqualsBuilder()
                .append(stationId, tripEvent.stationId)
                .append(timestamp, tripEvent.timestamp)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(stationId)
                .append(timestamp)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
                .append("stationId", stationId)
                .append("timestamp", timestamp)
                .toString();
    }
}
