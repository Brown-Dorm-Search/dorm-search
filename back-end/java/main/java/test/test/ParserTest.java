package test;

import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.*;

import org.testng.Assert;
import org.testng.annotations.Test;
import DormRoom.IDormRoom;
import DormRoom.RoomCapacity;
import DormRoom.BathroomType;
import Parsing.ParserUtils;
import Parsing.RoomParser;

import java.io.IOException;
import java.util.ArrayList;

public class ParserTest {

    /**
     * Test for {@link ParserUtils#getRoomCapacity(String)} with valid room capacity strings.
     * Verifies that the method correctly converts string inputs ("one", "two", etc.) to corresponding {@link RoomCapacity} enum values.
     */
    @Test
    void testGetRoomCapacityValid() throws IOException{
        // Test valid inputs
        assertEquals(RoomCapacity.One, ParserUtils.getRoomCapacity("one"));
        assertEquals(RoomCapacity.Two, ParserUtils.getRoomCapacity("two"));
        assertEquals(RoomCapacity.Three, ParserUtils.getRoomCapacity("three"));
        assertEquals(RoomCapacity.Four, ParserUtils.getRoomCapacity("four"));
        assertEquals(RoomCapacity.Five, ParserUtils.getRoomCapacity("five"));
        assertEquals(RoomCapacity.Six, ParserUtils.getRoomCapacity("six"));
    }

    /**
     * Test for {@link ParserUtils#getRoomCapacity(String)} with invalid room capacity strings.
     * Verifies that the method throws an {@link IllegalArgumentException} when an invalid capacity is provided.
     */
    @Test
    void testGetRoomCapacityInvalid() throws IOException{
        // Test invalid inputs
        assertThrows(IllegalArgumentException.class, () -> {ParserUtils.getRoomCapacity("seven");});
        assertThrows(IllegalArgumentException.class, () -> {ParserUtils.getRoomCapacity("abc");});
    }

    /**
     * Test for {@link ParserUtils#roomTypeToSize(String)} with valid room type strings.
     * Verifies that the method correctly converts room type strings ("Single Room", "Double Room", etc.)
     * to corresponding {@link RoomCapacity} enum values.
     */
    @Test
    void testRoomTypeToSizeValid() throws IOException{
        assertEquals(RoomCapacity.One, ParserUtils.roomTypeToSize("Single Room"));
        assertEquals(RoomCapacity.Two, ParserUtils.roomTypeToSize("Double Room"));
        assertEquals(RoomCapacity.Three, ParserUtils.roomTypeToSize("Triple Room"));
    }

    /**
     * Test for {@link ParserUtils#roomTypeToSize(String)} with invalid room type strings.
     * Verifies that the method throws an {@link IllegalArgumentException} when an unrecognized room type string is provided.
     */
    @Test
    void testRoomTypeToSizeInvalid() throws IOException{
        // Test for invalid room type string
        assertThrows(IllegalArgumentException.class, () -> {ParserUtils.roomTypeToSize("Quad Room");});
    }

    /**
     * Test for {@link ParserUtils#parseYesNo(String)} with valid "Yes" and "No" inputs.
     * Verifies that the method correctly converts "Yes" to true and "No" to false.
     */
    @Test
    void testParseYesNoValid() {
        assertTrue(ParserUtils.parseYesNo("Yes"));
        assertFalse(ParserUtils.parseYesNo("No"));
    }

    /**
     * Test for {@link ParserUtils#parseYesNo(String)} with invalid input.
     * Verifies that the method throws an {@link IllegalArgumentException} when an invalid string (e.g., "Maybe") is provided.
     */
    @Test
    void testParseYesNoInvalid() {
        // Test for invalid inputs
        assertThrows(IllegalArgumentException.class, () -> {ParserUtils.parseYesNo("Maybe");});
    }

    /**
     * Test for {@link ParserUtils#getBathroomType(String)} with valid bathroom type strings.
     * Verifies that the method correctly maps the bathroom type strings ("yes", "semi", "no") to corresponding {@link BathroomType} enum values.
     */
    @Test
    void testGetBathroomTypeValid() {
        assertEquals(BathroomType.Private, ParserUtils.getBathroomType("yes"));
        assertEquals(BathroomType.SemiPrivate, ParserUtils.getBathroomType("semi"));
        assertEquals(BathroomType.Communal, ParserUtils.getBathroomType("no"));
    }

    /**
     * Test for {@link ParserUtils#getBathroomType(String)} with invalid bathroom type strings.
     * Verifies that the method throws an {@link IllegalArgumentException} when an unrecognized bathroom type (e.g., "shared") is provided.
     */
    @Test
    void testGetBathroomTypeInvalid() {
        // Test for invalid bathroom type
        assertThrows(IllegalArgumentException.class, () -> {ParserUtils.getBathroomType("shared");});
    }

    /**
     * Test for the {@link RoomParser#getRooms()} method using a partial dataset.
     * Verifies that the method correctly parses a CSV file and returns the expected number of dorm rooms and suites.
     * Also checks specific properties of parsed rooms and suites to ensure correctness.
     */
    @Test
    public void testParsePartial() throws IOException, NumberFormatException, NullPointerException {
        RoomParser parser = new RoomParser("data/PartialDataset.csv");
        ArrayList<IDormRoom> rooms = parser.getRooms();
        Assert.assertEquals(548, rooms.size());

        // Get number of suites and number of dorm rooms
        int numDorms = 0;
        int numSuites = 0;

        for (IDormRoom room : rooms) {
            if(room.isSuite()) {
                numSuites ++;
            } else {
                numDorms++;
            }
        }

        assertEquals(536, numDorms);
        assertEquals(12, numSuites);

        IDormRoom room0 = rooms.get(0);
        assertEquals("BARBOUR 050", room0.getRoomNumber());
        assertTrue(room0.hasKitchen());
        assertTrue(room0.isSuite());
        assertEquals(BathroomType.Private, room0.getBathroomType());
        assertEquals(736, room0.getRoomSize());
        assertEquals(RoomCapacity.Three, room0.getRoomCapacity());

        IDormRoom room136 = rooms.get(136);
        assertEquals("CHAPIN 330", room136.getRoomNumber());
        assertEquals(room136.getRoomSize(), 232);
        assertFalse(room136.hasKitchen());
        assertFalse(room136.isSuite());
        assertEquals(BathroomType.Communal, room136.getBathroomType());
        assertEquals(RoomCapacity.Two, room136.getRoomCapacity());
        assertEquals("Chapin House", room136.getDormBuilding().buildingName().toString());
    }
}
