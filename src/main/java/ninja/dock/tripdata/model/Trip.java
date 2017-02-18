package ninja.dock.tripdata.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;

public class Trip {

    private LocalDate period;
    private Integer durationSeconds;
    private String bikeId;
    private UserType userType;
    private Gender gender;
    private Integer birthYear;
    private TripEvent tripStart;
    private TripEvent tripEnd;

    public LocalDate getPeriod() {
        return period;
    }

    public void setPeriod(final LocalDate period) {
        this.period = period;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(final Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public String getBikeId() {
        return bikeId;
    }

    public void setBikeId(final String bikeId) {
        this.bikeId = bikeId;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(final UserType userType) {
        this.userType = userType;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(final Gender gender) {
        this.gender = gender;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(final Integer birthYear) {
        this.birthYear = birthYear;
    }

    public TripEvent getTripStart() {
        return tripStart;
    }

    public void setTripStart(final TripEvent tripStart) {
        this.tripStart = tripStart;
    }

    public TripEvent getTripEnd() {
        return tripEnd;
    }

    public void setTripEnd(final TripEvent tripEnd) {
        this.tripEnd = tripEnd;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Trip trip = (Trip) o;

        return new EqualsBuilder()
                .append(period, trip.period)
                .append(durationSeconds, trip.durationSeconds)
                .append(bikeId, trip.bikeId)
                .append(userType, trip.userType)
                .append(gender, trip.gender)
                .append(birthYear, trip.birthYear)
                .append(tripStart, trip.tripStart)
                .append(tripEnd, trip.tripEnd)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(period)
                .append(durationSeconds)
                .append(bikeId)
                .append(userType)
                .append(gender)
                .append(birthYear)
                .append(tripStart)
                .append(tripEnd)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("period", period)
                .append("durationSeconds", durationSeconds)
                .append("bikeId", bikeId)
                .append("userType", userType)
                .append("gender", gender)
                .append("birthYear", birthYear)
                .append("tripStart", tripStart)
                .append("tripEnd", tripEnd)
                .toString();
    }
}
