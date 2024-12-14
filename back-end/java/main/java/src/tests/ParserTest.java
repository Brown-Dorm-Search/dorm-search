package src.tests;

//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.Test;
import org.testng.Assert;
import org.testng.annotations.Test;
import src.DormRoom.IDormRoom;
import src.Parsing.Parser;
import src.Parsing.RoomParser;

import java.io.IOException;
import java.util.ArrayList;

public class ParserTest {

    @Test
    public void testParsePartial() throws IOException {
        RoomParser parser = new RoomParser("data/PartialDataset.csv");
        ArrayList<IDormRoom> rooms = parser.getRooms();
        System.out.println("Done");
    }
}
