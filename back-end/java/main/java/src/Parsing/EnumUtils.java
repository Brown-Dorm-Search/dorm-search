package src.Parsing;

import src.DormRoom.BathroomType;
import src.DormRoom.RoomCapacity;

import java.io.IOException;

public class EnumUtils {

    public static RoomCapacity getRoomCapacity(String capacity) throws IllegalArgumentException {
        switch (capacity.toLowerCase()) {
            case "one":
                return RoomCapacity.One;
            case "two":
                return RoomCapacity.Two;
            case "three":
                return RoomCapacity.Three;
            case "four":
                return RoomCapacity.Four;
            case "five":
                return RoomCapacity.Five;
            case "six":
                return RoomCapacity.Six;
            default:
                throw new IllegalArgumentException("Room capacity must be a number from 1 to 6");
        }
    }


    public static RoomCapacity roomTypeToSize(String size) throws IllegalArgumentException {
        if (size.contains("Single")) {
            return getRoomCapacity("one");
        }

        if (size.contains("Double")) {
            return  getRoomCapacity("two");
        }

        if (size.contains("Triple")) {
            return getRoomCapacity("three");
        }

        throw new IllegalArgumentException("Unexpected room type" + size + size.length());
    }

    public static boolean parseYesNo(String input) throws IllegalArgumentException {
        return switch (input) {
            case "Yes" -> true;
            case "No" -> false;
            default -> throw new IllegalArgumentException("Input must be Yes or No");
        };
    }

    public static BathroomType getBathroomType(String type) throws IllegalArgumentException {
        return switch (type.toLowerCase()) {
            case "yes" -> BathroomType.Private;
            case "semi" -> BathroomType.SemiPrivate;
            case "no" -> BathroomType.Communal;
            default -> throw new IllegalArgumentException("Invalid bathroom type: " + type);
        };
    }
}

