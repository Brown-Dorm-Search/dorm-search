package src.Parsing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.csv.CSVRecord;
import src.DormRoom.*;

public class RoomParser {

    public Parser parser;

    public RoomParser(String path) throws FileNotFoundException, IOException {
        this.parser = new Parser(path);
    }

    public ArrayList<IDormRoom> getRooms(){
        ArrayList<IDormRoom> result = new ArrayList<>();


        Iterator<CSVRecord> roomsIterator = this.parser.iterator();
        while (roomsIterator.hasNext()) {
            CSVRecord room = roomsIterator.next();

            if (room.get("Is Suite?").equals("No")) {
                result.add(parseDormRoom(room));
            }
            else
            {
                // TODO: Parse suites
                System.out.println("TODO");
            }
        }

        return result;
    }

    private IDormRoom parseDormRoom(CSVRecord room) throws IllegalArgumentException {
        EnumUtils enumUtils = new EnumUtils();

        // Parse CSV row data
        int roomSize = Integer.parseInt(room.get("Room Size"));
        String roomNumber = room.get("Room");
        RoomCapacity capacity = EnumUtils.roomTypeToSize(room.get("Room Type"));
        String floorPlan = room.get("Floor Plan Link");
        boolean hasKitchen = EnumUtils.parseYesNo(room.get("Has Kitchen?"));
        boolean isSuite = EnumUtils.parseYesNo(room.get("Is Suite?"));
        BathroomType bathType = EnumUtils.getBathroomType(room.get("Has Bathroom?"));
        String building = room.get("Building");

        DormRoom newRoom = new DormRoom(roomSize, roomNumber, capacity, floorPlan, hasKitchen,
                                        isSuite, bathType, building);


        return newRoom;
    }


}
