package Parsing;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

/**
 * A utility class for parsing CSV files related to dorm rooms.
 * It uses the Apache Commons CSV library to read and iterate through the CSV records.
 */
public class Parser {

    /**
     * The CSVParser instance used for parsing CSV files.
     */
    public CSVParser csvParser;

    /**
     * Constructs a new {@link Parser} instance and initializes the CSVParser with the provided file path.
     *
     * @param path The path to the CSV file to be parsed.
     * @throws FileNotFoundException If the file at the specified path does not exist.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public Parser(String path) throws FileNotFoundException, IOException {
        Reader reader = new FileReader(path);
        this.csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());
    }

    /**
     * Returns a list of the records in the CSV file.
     *
     * @return A list of the CSV records.
     */
    public List<CSVRecord> getRecords() {
        return this.csvParser.getRecords();
    }
}
