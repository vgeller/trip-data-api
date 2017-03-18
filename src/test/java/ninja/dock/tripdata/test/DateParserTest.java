package ninja.dock.tripdata.test;

import ninja.dock.tripdata.DateParser;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DateParserTest {

    @Test
    public void test_2015_01_format() {
        final DateParser dateParser = new DateParser();
        assertEquals(LocalDateTime.of(2016, 12, 5, 23, 2, 0), dateParser.parse("12/5/2016 23:02"));
    }

    @Test
    public void test_2016_01_format() {
        final DateParser dateParser = new DateParser();
        assertEquals(LocalDateTime.of(2016, 12, 5, 1, 2, 3), dateParser.parse("12/5/2016 01:02:03"));
    }

    @Test
    public void test_2016_12_format() {
        final DateParser dateParser = new DateParser();
        assertEquals(LocalDateTime.of(2016, 12, 5, 1, 2, 3), dateParser.parse("2016-12-05 01:02:03"));
    }

    @Test
    public void test_unknown() {
        final DateParser dateParser = new DateParser();

        try {
            dateParser.parse("xyz");
            fail("expected DateTimeParseException");
        } catch (final DateTimeParseException e) {
            assertEquals("Could not parse date using any known format: xyz", e.getMessage());
        }
    }

}
