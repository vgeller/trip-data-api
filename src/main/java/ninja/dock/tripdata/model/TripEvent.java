package ninja.dock.tripdata.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TripEvent {

    private String stationId;
    private String stationName;
    private BigDecimal stationLatitude;
    private BigDecimal stationLongitude;
    private LocalDateTime timestamp;

    public TripEvent() {
        // default constructor
    }

    public TripEvent(final String stationId,
                     final LocalDateTime timestamp) {
        this.stationId = stationId;
        this.timestamp = timestamp;
    }

    public TripEvent(final String stationId,
                     final String stationName,
                     final BigDecimal stationLatitude,
                     final BigDecimal stationLongitude,
                     final LocalDateTime timestamp) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.stationLatitude = stationLatitude;
        this.stationLongitude = stationLongitude;
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

    public String getStationName() {
        return stationName;
    }

    public void setStationName(final String stationName) {
        this.stationName = stationName;
    }

    public BigDecimal getStationLatitude() {
        return stationLatitude;
    }

    public void setStationLatitude(final BigDecimal stationLatitude) {
        this.stationLatitude = stationLatitude;
    }

    public BigDecimal getStationLongitude() {
        return stationLongitude;
    }

    public void setStationLongitude(final BigDecimal stationLongitude) {
        this.stationLongitude = stationLongitude;
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
                .append(stationName, tripEvent.stationName)
                .append(stationLatitude, tripEvent.stationLatitude)
                .append(stationLongitude, tripEvent.stationLongitude)
                .append(timestamp, tripEvent.timestamp)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(stationId)
                .append(stationName)
                .append(stationLatitude)
                .append(stationLongitude)
                .append(timestamp)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
                .append("stationId", stationId)
                .append("stationName", stationName)
                .append("stationLatitude", stationLatitude)
                .append("stationLongitude", stationLongitude)
                .append("timestamp", timestamp)
                .toString();
    }
}
