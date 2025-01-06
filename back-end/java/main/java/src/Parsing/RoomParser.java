package Parsing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        List<CSVRecord> roomsList = this.parser.getRecords();

        for (int i = 0; i < roomsList.size(); i++) {
            CSVRecord room = roomsList.get(i);

            if (room.get("Is Suite?").equals("No")) {
                result.add(parseDormRoom(room));
            } else {
                // List to keep track of dorm rooms that belong to this suite
                ArrayList<DormRoom> roomsInSuite = new ArrayList<>();
                roomsInSuite.add(parseDormRoom(room));
                String suiteNumber = room.get("Suite");

                // Common area size to be added to suite
                int commonAreaSize = Integer.parseInt(room.get("Common Area Size"));

                // Process subsequent rooms that belong to the same suite
                while (i + 1 < roomsList.size()) {
                    CSVRecord nextRoom = roomsList.get(i + 1);
                    if (nextRoom.get("Suite").equals(suiteNumber)) {
                        roomsInSuite.add(parseDormRoom(nextRoom));
                        i++;
                    } else {
                        break;  // Break the loop, as we've finished processing this suite
                    }
                }

                // After processing all rooms for this suite, add the suite to the result
                result.add(parseSuite(roomsInSuite, commonAreaSize));
            }
        }

        return result;
    }


    /**
     * Converts a list of dorm rooms and a common area size into a {@link Suite} instance.
     * RoomSize is set to be the sum of the common area size and all individual dorm sizes
     * RoomCapacity is the sum of the individual dorm room capacities
     *
     * @param roomsInSuite A list of dorm rooms that belong to the suite.
     * @param commonAreaSize The size of the common area for the suite.
     * @return A {@link Suite} instance containing the dorm rooms and the common area size.
     */
    private static Suite parseSuite(ArrayList<DormRoom> roomsInSuite, int commonAreaSize) {
        DormRoom firstRoom = roomsInSuite.getFirst();

        // Get common area data

        // Get input data
        int roomSize = calculateSuiteSize(commonAreaSize, roomsInSuite);
        String roomNumber = getSuiteNumber(firstRoom.getRoomNumber());
        RoomCapacity capacity = calculateRoomCapacity(roomsInSuite);
        String floorPlan = firstRoom.getFloorPlanLink();
        boolean hasKitchen = firstRoom.hasKitchen();
        boolean isSuite = firstRoom.isSuite();
        BathroomType bathType = firstRoom.getBathroomType();
        String building = firstRoom.getDormBuilding().buildingName().toString();

      return new Suite(roomSize, roomNumber, capacity, floorPlan, hasKitchen,
                isSuite, bathType, building, commonAreaSize, roomsInSuite);
    }

    /**
     * Extracts the suite number from a room number string. The suite number is assumed to be the portion of the room number
     * before the room-specific number, which is represented by the digits following the three suite digits.
     *
     * <p>The method works by locating the third-to-last digit in the room number string and removing the room-specific number
     * (i.e., the digits after this position) to return the suite number.</p>
     *
     * @param roomNumber The full room number string, which includes both the suite number and the room-specific number.
     *                   The suite number is the portion of the string before the room-specific number (i.e., the digits after
     *                   the third-to-last digit).
     * @return The suite number, which is a substring of the input room number excluding the room-specific part.
     * @throws IllegalArgumentException If the room number string is invalid or does not contain enough digits to extract a valid suite number.
     */
    private static String getSuiteNumber(String roomNumber) throws IllegalArgumentException{
        int digitCount = 0;

        int index = -1;

        // Traverse the string in reverse order and find the index where the Suite number ends
        for (int i = roomNumber.length() - 1; i >= 0; i--) {
            if (Character.isDigit(roomNumber.charAt(i))) {
                digitCount++;
                // If we've found the third-to-last digit, return the index
                if (digitCount == 3) {
                    index = i;
                }
            }
        }

        if (index == -1)
            throw new IllegalArgumentException("Invalid room number: " + roomNumber);

        // Remove index-1 to remove the space between suit and room numbers
        return roomNumber.substring(0, index-1);
    }

    /**
     * Calculates the total room capacity for a suite by summing the individual capacities of each room.
     * The total capacity is then converted to a corresponding {@link RoomCapacity} enum value.
     *
     * @param roomsInSuite An {@link ArrayList} of {@link DormRoom} objects representing the rooms in the suite.
     *                     Each room's capacity is determined by calling {@link DormRoom#getRoomCapacityInt()}.
     * @return The corresponding {@link RoomCapacity} enum value representing the total capacity of the suite.
     * @throws IllegalArgumentException If the calculated total capacity is invalid or not in the expected range (1-6).
     */
    private static RoomCapacity calculateRoomCapacity(ArrayList<DormRoom> roomsInSuite) {
        int capacity = 0;

        for (DormRoom room: roomsInSuite) {
            capacity += room.getRoomCapacityInt();
        }

        return ParserUtils.getRoomCapacity(capacity);
    }

    /**
     * Calculates the total size of a suite by adding the size of the common area and the sizes of all the rooms in the suite.
     *
     * @param commonAreaSize The size of the common area in the suite.
     * @param roomsInSuite An {@link ArrayList} containing the {@link DormRoom} objects in the suite.
     * @return The total size of the suite, which is the sum of the common area size and the sizes of all individual rooms.
     */
    private static int calculateSuiteSize(int commonAreaSize, ArrayList<DormRoom> roomsInSuite) {
        int size = commonAreaSize;

        for (DormRoom room: roomsInSuite) {
            size += room.getRoomSize();
        }

        return size;
    }

    /**
     * Parses a {@link CSVRecord} into a {@link DormRoom} instance.
     *
     * @param room The {@link CSVRecord} representing a single dorm room.
     * @return A {@link DormRoom} object constructed from the CSV data.
     * @throws IllegalArgumentException If there is an issue parsing the dorm room data.
     */
    private static DormRoom parseDormRoom(CSVRecord room) throws IllegalArgumentException {

        // Parse CSV row data
        int roomSize = Integer.parseInt(room.get("Room Size"));
        String roomNumber = room.get("Room");
        RoomCapacity capacity = ParserUtils.roomTypeToSize(room.get("Room Type"));
        String floorPlan = room.get("Floor Plan Link");
        boolean hasKitchen = ParserUtils.parseYesNo(room.get("Has Kitchen?"));
        boolean isSuite = ParserUtils.parseYesNo(room.get("Is Suite?"));
        BathroomType bathType = ParserUtils.getBathroomType(room.get("Has Bathroom?"));
        String building = room.get("Building");

      return new DormRoom(roomSize, roomNumber, capacity, floorPlan, hasKitchen,
                isSuite, bathType, building);
    }
}
