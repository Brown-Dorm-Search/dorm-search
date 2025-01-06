package Parsing;

import DormRoom.BathroomType;
import DormRoom.RoomCapacity;

/**
 * Utility class that provides methods to parse and convert various string inputs
 * related to dorm room characteristics such as capacity, type, bathroom type, and yes/no responses.
 */
public class ParserUtils {

    /**
     * Converts a string representing room capacity to a corresponding {@link RoomCapacity} enum value.
     *
     * @param capacity A string representing the room capacity (e.g., "one", "two", "three", etc.).
     * @return The corresponding {@link RoomCapacity} enum value.
     * @throws IllegalArgumentException If the capacity string is invalid or not in the expected range (1-6).
     */
    public static RoomCapacity getRoomCapacity(String capacity) throws IllegalArgumentException {
      return switch (capacity.toLowerCase()) {
        case "one" -> RoomCapacity.ONE;
        case "two" -> RoomCapacity.TWO;
        case "three" -> RoomCapacity.THREE;
        case "four" -> RoomCapacity.FOUR;
        case "five" -> RoomCapacity.FIVE;
        case "six" -> RoomCapacity.SIX;
        case null, default ->
            throw new IllegalArgumentException("Room capacity must be a number from 1 to 6");
      };
    }

    /**
     * Converts an integer representing room capacity to a corresponding {@link RoomCapacity} enum value.
     *
     * @param capacity An integer representing the room capacity (e.g., 1, 2, 3, etc.).
     * @return The corresponding {@link RoomCapacity} enum value.
     * @throws IllegalArgumentException If the capacity integer is invalid or not in the expected range (1-6).
     */
    public static RoomCapacity getRoomCapacity(int capacity) throws IllegalArgumentException {
      return switch (capacity) {
        case 1 -> RoomCapacity.ONE;
        case 2 -> RoomCapacity.TWO;
        case 3 -> RoomCapacity.THREE;
        case 4 -> RoomCapacity.FOUR;
        case 5 -> RoomCapacity.FIVE;
        case 6 -> RoomCapacity.SIX;
        default ->
            throw new IllegalArgumentException("Room capacity must be an integer between 1 and 6");
      };
    }


    /**
     * Converts a room type string (such as "Single", "Double", "Triple") to the corresponding room capacity.
     *
     * @param size A string representing the room type (e.g., "Single", "Double", "Triple").
     * @return The corresponding {@link RoomCapacity} enum value.
     * @throws IllegalArgumentException If the room type is unexpected or not supported.
     */
    public static RoomCapacity roomTypeToSize(String size) throws IllegalArgumentException {
        if (size.contains("Single")) {
            return getRoomCapacity("one");
        }

        if (size.contains("Double")) {
            return getRoomCapacity("two");
        }

        if (size.contains("Triple")) {
            return getRoomCapacity("three");
        }

        throw new IllegalArgumentException("Unexpected room type " + size + " with length " + size.length());
    }

    /**
     * Parses a "Yes" or "No" string input and converts it to a boolean value.
     *
     * @param input A string representing a yes/no response (e.g., "Yes", "No").
     * @return true if the input is "Yes", false if the input is "No".
     * @throws IllegalArgumentException If the input is neither "Yes" nor "No".
     */
    public static boolean parseYesNo(String input) throws IllegalArgumentException {
        return switch (input) {
            case "Yes" -> true;
            case "No" -> false;
            case null, default -> throw new IllegalArgumentException("Input must be Yes or No");
        };
    }

    /**
     * Converts a string representing the bathroom type to the corresponding {@link BathroomType} enum value.
     *
     * @param type A string representing the bathroom type (e.g., "yes", "semi", "no").
     * @return The corresponding {@link BathroomType} enum value.
     * @throws IllegalArgumentException If the bathroom type string is invalid.
     */
    public static BathroomType getBathroomType(String type) throws IllegalArgumentException {
        return switch (type.toLowerCase()) {
            case "yes" -> BathroomType.Private;
            case "semi" -> BathroomType.SemiPrivate;
            case "no" -> BathroomType.Communal;
            case null, default -> throw new IllegalArgumentException("Invalid bathroom type: " + type);
        };
    }
}
