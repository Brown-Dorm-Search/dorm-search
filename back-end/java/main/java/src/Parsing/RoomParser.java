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

        // For handling consecutive suites
        CSVRecord temp = null;

        while (roomsIterator.hasNext()) {
            CSVRecord room = roomsIterator.next();

            // Consecutive suite case
            if (temp != null) {
                // List to keep track of dorm rooms that belong to this suite
                ArrayList<DormRoom> roomsInSuite = new ArrayList<>();
                roomsInSuite.add(parseDormRoom(temp));
                String suiteNumber = temp.get("Suite");

                int commonAreaSize = Integer.parseInt(temp.get("Common Area Size"));


                // Add current room to suite if possible
                if (room.get("Suite").equals(suiteNumber)) {
                    roomsInSuite.add(parseDormRoom(room));
                }

                while (roomsIterator.hasNext()) {
                    CSVRecord nextRoom = roomsIterator.next();
                    if (nextRoom.get("Suite").equals(suiteNumber)) {
                        roomsInSuite.add(parseDormRoom(nextRoom));
                    }
                    else {
                        // We have reached the end of this suite
                        temp = null;
                        // If next room is the end or is a dorm room we are done
                        if (!roomsIterator.hasNext() || nextRoom.get("Is Suite?").equals("No")) {
                            result.add(parseDormRoom(nextRoom));
                            break;
                        }

                        // Otherwise, nextRoom is a suite, so we assign it to temp
                        temp = nextRoom;
                        break;
                    }
                }

                result.add(parseSuite(roomsInSuite, commonAreaSize));



            }
            else if (room.get("Is Suite?").equals("No")) {
                result.add(parseDormRoom(room));
            }
            else
            {

                // List to keep track of dorm rooms that belong to this suite
                ArrayList<DormRoom> roomsInSuite = new ArrayList<>();
                roomsInSuite.add(parseDormRoom(room));
                String suiteNumber = room.get("Suite");

                // Common area size to be added to suite
                int commonAreaSize = Integer.parseInt(room.get("Common Area Size"));

                while (roomsIterator.hasNext()) {
                    CSVRecord nextRoom = roomsIterator.next();
                    if (nextRoom.get("Suite").equals(suiteNumber)) {
                        roomsInSuite.add(parseDormRoom(nextRoom));
                    }
                    else {
                        //We have reached the end of this suite

                        // If next room is the end or is a dorm room we are done
                        if (!roomsIterator.hasNext() || nextRoom.get("Is Suite?").equals("No")) {
                            result.add(parseDormRoom(nextRoom));
                            break;
                        }

                        // Otherwise, nextRoom is a suite, so we assign it to temp
                        temp = nextRoom;
                        break;
                    }
                }

                result.add(parseSuite(roomsInSuite, commonAreaSize));
            }
        }

        return result;
    }

    private Suite parseSuite(ArrayList<DormRoom> roomsInSuite, int commonAreaSize) {
        DormRoom firstRoom = roomsInSuite.getFirst();

        // Get common area data

        // Get input data
        int roomSize = firstRoom.getRoomSize();
        String roomNumber = firstRoom.getRoomNumber();
        RoomCapacity capacity = firstRoom.getRoomCapacity();
        String floorPlan = firstRoom.getFloorPlanLink();
        boolean hasKitchen = firstRoom.hasKitchen();
        boolean isSuite = firstRoom.isSuite();
        BathroomType bathType = firstRoom.getBathroomType();
        String building = firstRoom.getDormBuilding().getBuildingName();

        Suite suiteToReturn = new Suite(roomSize, roomNumber, capacity, floorPlan, hasKitchen,
                                        isSuite, bathType, building, commonAreaSize, roomsInSuite);

        return suiteToReturn;
    }

    private DormRoom parseDormRoom(CSVRecord room) throws IllegalArgumentException {

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
