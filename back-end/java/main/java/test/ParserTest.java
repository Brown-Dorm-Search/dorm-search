package test;

//import org.junit.jupiter.api.Test;
import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.*;

import org.testng.Assert;
import org.testng.annotations.Test;
import src.DormRoom.IDormRoom;
import src.DormRoom.RoomCapacity;
import src.DormRoom.BathroomType;
import src.Parsing.EnumUtils;
import src.Parsing.RoomParser;

import java.io.IOException;
import java.util.ArrayList;

public class ParserTest {

    // Test for getRoomCapacity(String capacity)
    @Test
    void testGetRoomCapacityValid() throws IOException{
        // Test valid inputs
        assertEquals(RoomCapacity.One, EnumUtils.getRoomCapacity("one"));
        assertEquals(RoomCapacity.Two, EnumUtils.getRoomCapacity("two"));
        assertEquals(RoomCapacity.Three, EnumUtils.getRoomCapacity("three"));
        assertEquals(RoomCapacity.Four, EnumUtils.getRoomCapacity("four"));
        assertEquals(RoomCapacity.Five, EnumUtils.getRoomCapacity("five"));
        assertEquals(RoomCapacity.Six, EnumUtils.getRoomCapacity("six"));
    }

    @Test
    void testGetRoomCapacityInvalid() throws IOException{
        // Test invalid inputs
        assertThrows(IllegalArgumentException.class, () -> {EnumUtils.getRoomCapacity("seven");});

        assertThrows(IllegalArgumentException.class, () -> {EnumUtils.getRoomCapacity("abc");});

    }

    // Test for roomTypeToSize(String size)
    @Test
    void testRoomTypeToSizeValid() throws IOException{
        assertEquals(RoomCapacity.One, EnumUtils.roomTypeToSize("Single Room"));
        assertEquals(RoomCapacity.Two, EnumUtils.roomTypeToSize("Double Room"));
        assertEquals(RoomCapacity.Three, EnumUtils.roomTypeToSize("Triple Room"));
    }

    @Test
    void testRoomTypeToSizeInvalid() throws IOException{
        // Test for invalid room type string
        assertThrows(IllegalArgumentException.class, () -> {EnumUtils.roomTypeToSize("Quad Room");});

    }

    // Test for parseYesNo(String input)
    @Test
    void testParseYesNoValid() {
        assertTrue(EnumUtils.parseYesNo("Yes"));
        assertFalse(EnumUtils.parseYesNo("No"));
    }

    @Test
    void testParseYesNoInvalid() {
        // Test for invalid inputs
        assertThrows(IllegalArgumentException.class, () -> {EnumUtils.parseYesNo("Maybe");});
    }

    // Test for getBathroomType(String type)
    @Test
    void testGetBathroomTypeValid() {
        assertEquals(BathroomType.Private, EnumUtils.getBathroomType("yes"));
        assertEquals(BathroomType.SemiPrivate, EnumUtils.getBathroomType("semi"));
        assertEquals(BathroomType.Communal, EnumUtils.getBathroomType("no"));
    }

    @Test
    void testGetBathroomTypeInvalid() {
        // Test for invalid bathroom type
        assertThrows(IllegalArgumentException.class, () -> {EnumUtils.getBathroomType("shared");});
    }

    @Test
    public void testParsePartial() throws IOException {
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
