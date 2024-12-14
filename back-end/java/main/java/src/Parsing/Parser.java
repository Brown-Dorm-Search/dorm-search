package src.Parsing;


import src.DormRoom.IDormRoom;
import src.DormRoom.DormRoom;
import src.DormRoom.Suite;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;




public class Parser {

    public CSVParser csvParser;

    public Parser(String path) throws FileNotFoundException, IOException {
        Reader reader = new FileReader(path);
        this.csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());
    }


    public Iterator<CSVRecord> iterator(){
        return this.csvParser.iterator();
    }
}
