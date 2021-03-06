package ninja.dock.tripdata;

import ninja.dock.tripdata.model.Trip;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Load trip history data from https://www.citibikenyc.com/system-data
 */
public class TripParser implements Iterable<Trip>, Closeable {

    private final File file;
    private FileReader reader;
    private CSVParser parser;

    public TripParser(final File file) throws IOException {
        this.file = file;
    }

    @Override
    public Iterator<Trip> iterator() {
        try {
            this.reader = new FileReader(file);
            this.parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
            final FileFormat fileFormat = determineFileFormat(parser);
            return new TripIterator(parser.iterator(), fileFormat);
        } catch (final IOException e) {
            throw new TripDataLoaderException(e);
        }
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

    @Override
    public void close() throws IOException {
        if (parser != null) {
            parser.close();
        }
        if (reader != null) {
            reader.close();
        }
    }
}
