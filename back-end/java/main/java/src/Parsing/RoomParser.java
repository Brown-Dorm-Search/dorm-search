package Parsing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.csv.CSVRecord;
import DormRoom.*;

/**
 * A utility class for parsing CSV files related to dorm rooms and suites.
 * This class reads the data from the CSV file and constructs instances of {@link DormRoom} and {@link Suite}.
 */
public class RoomParser {

    /**
     * The parser instance used for reading the CSV file.
     */
    public final Parser parser;

    /**
     * Constructs a new {@link RoomParser} instance and initializes the {@link Parser} with the provided file path.
     *
     * @param path The path to the CSV file containing dorm room data.
     * @throws FileNotFoundException If the file at the specified path does not exist.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public RoomParser(String path) throws FileNotFoundException, IOException {
        this.parser = new Parser(path);
    }

    /**
     * Parses the CSV data and constructs a list of dorm rooms and suites.
     *
     * @return A list of {@link IDormRoom} objects, including both individual dorm rooms and suites.
     * @throws NumberFormatException If there is an issue parsing a numeric value from the CSV.
     * @throws NullPointerException If any required data is missing in the CSV.
     */
    public ArrayList<IDormRoom> getRooms() throws NumberFormatException, NullPointerException {
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
                    } else {
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

            } else if (room.get("Is Suite?").equals("No")) {
                result.add(parseDormRoom(room));
            } else {
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
                    } else {
                        // We have reached the end of this suite
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

    /**
     * Converts a list of dorm rooms and a common area size into a {@link Suite} instance.
     *
     * @param roomsInSuite A list of dorm rooms that belong to the suite.
     * @param commonAreaSize The size of the common area for the suite.
     * @return A {@link Suite} instance containing the dorm rooms and the common area size.
     */
    private Suite parseSuite(ArrayList<DormRoom> roomsInSuite, int commonAreaSize) {
        DormRoom firstRoom = roomsInSuite.get(0);

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

    /**
     * Parses a {@link CSVRecord} into a {@link DormRoom} instance.
     *
     * @param room The {@link CSVRecord} representing a single dorm room.
     * @return A {@link DormRoom} object constructed from the CSV data.
     * @throws IllegalArgumentException If there is an issue parsing the dorm room data.
     */
    private DormRoom parseDormRoom(CSVRecord room) throws IllegalArgumentException {

        // Parse CSV row data
        int roomSize = Integer.parseInt(room.get("Room Size"));
        String roomNumber = room.get("Room");
        RoomCapacity capacity = ParserUtils.roomTypeToSize(room.get("Room Type"));
        String floorPlan = room.get("Floor Plan Link");
        boolean hasKitchen = ParserUtils.parseYesNo(room.get("Has Kitchen?"));
        boolean isSuite = ParserUtils.parseYesNo(room.get("Is Suite?"));
        BathroomType bathType = ParserUtils.getBathroomType(room.get("Has Bathroom?"));
        String building = room.get("Building");

        DormRoom newRoom = new DormRoom(roomSize, roomNumber, capacity, floorPlan, hasKitchen,
                isSuite, bathType, building);

        return newRoom;
    }
}
