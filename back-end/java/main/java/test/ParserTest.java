package test;

//import org.junit.jupiter.api.Test;
import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.*;

import org.testng.Assert;
import org.testng.annotations.Test;
import src.DormRoom.IDormRoom;
import src.DormRoom.RoomCapacity;
import src.DormRoom.BathroomType;
import src.Parsing.ParserUtils;
import src.Parsing.RoomParser;

import java.io.IOException;
import java.util.ArrayList;

public class ParserTest {

    // Test for getRoomCapacity(String capacity)
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

    @Test
    void testGetRoomCapacityInvalid() throws IOException{
        // Test invalid inputs
        assertThrows(IllegalArgumentException.class, () -> {ParserUtils.getRoomCapacity("seven");});

        assertThrows(IllegalArgumentException.class, () -> {ParserUtils.getRoomCapacity("abc");});

    }

    // Test for roomTypeToSize(String size)
    @Test
    void testRoomTypeToSizeValid() throws IOException{
        assertEquals(RoomCapacity.One, ParserUtils.roomTypeToSize("Single Room"));
        assertEquals(RoomCapacity.Two, ParserUtils.roomTypeToSize("Double Room"));
        assertEquals(RoomCapacity.Three, ParserUtils.roomTypeToSize("Triple Room"));
    }

    @Test
    void testRoomTypeToSizeInvalid() throws IOException{
        // Test for invalid room type string
        assertThrows(IllegalArgumentException.class, () -> {ParserUtils.roomTypeToSize("Quad Room");});

    }

    // Test for parseYesNo(String input)
    @Test
    void testParseYesNoValid() {
        assertTrue(ParserUtils.parseYesNo("Yes"));
        assertFalse(ParserUtils.parseYesNo("No"));
    }

    @Test
    void testParseYesNoInvalid() {
        // Test for invalid inputs
        assertThrows(IllegalArgumentException.class, () -> {ParserUtils.parseYesNo("Maybe");});
    }

    // Test for getBathroomType(String type)
    @Test
    void testGetBathroomTypeValid() {
        assertEquals(BathroomType.Private, ParserUtils.getBathroomType("yes"));
        assertEquals(BathroomType.SemiPrivate, ParserUtils.getBathroomType("semi"));
        assertEquals(BathroomType.Communal, ParserUtils.getBathroomType("no"));
    }

    @Test
    void testGetBathroomTypeInvalid() {
        // Test for invalid bathroom type
        assertThrows(IllegalArgumentException.class, () -> {ParserUtils.getBathroomType("shared");});
    }

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
        assertEquals(room0.getRoomNumber(), "BARBOUR 050 051");
        assertTrue(room0.hasKitchen());
        assertTrue(room0.isSuite());
        assertEquals(BathroomType.Private, room0.getBathroomType());

        IDormRoom room136 = rooms.get(136);
        assertEquals(room136.getRoomNumber(), "CHAPIN 330");
        assertEquals(room136.getRoomSize(), 232);
        assertFalse(room136.hasKitchen());
        assertFalse(room136.isSuite());
        assertEquals(BathroomType.Communal, room136.getBathroomType());
        assertEquals(RoomCapacity.Two, room136.getRoomCapacity());
        assertEquals("CHAPIN HOUSE", room136.getDormBuilding().getBuildingName());
    }
}
