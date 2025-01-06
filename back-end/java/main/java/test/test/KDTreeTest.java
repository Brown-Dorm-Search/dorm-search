package test;

import static org.junit.jupiter.api.Assertions.*;
import DormRoom.*;
import DormRoom.DormRoom;
import Filtering.*;
import Filtering.Node_KDTree.*;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KDTreeTest {

  private List<IDormRoom> baseRooms;

  @BeforeEach
  public void setUp() {
    // Base rooms for testing
    baseRooms = List.of(
        new DormRoom(200, "101A", RoomCapacity.ONE, "floorplan.com/101A", false, false, BathroomType.Private, "Barbour Hall"),
        new DormRoom(300, "102", RoomCapacity.TWO, "floorplan.com/102", true, false, BathroomType.Communal, "Buxton House"),
        new DormRoom(400, "201", RoomCapacity.THREE, "floorplan.com/201", false, true, BathroomType.SemiPrivate, "Caswell Hall"),
        new DormRoom(250, "202", RoomCapacity.TWO, "floorplan.com/202", true, true, BathroomType.Private, "Grad Center A"),
        new DormRoom(150, "301", RoomCapacity.FOUR, "floorplan.com/301", false, false, BathroomType.Communal, "Diman House")
    );
  }

  // --- KDTreeNode Tests ---

  @Test
  public void testEmptyRoomListThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> new KDTreeNode(Collections.emptyList()));
  }

  @Test
  public void testSingleRoomInTree() {
    List<IDormRoom> singleRoomList = List.of(new DormRoom(100, "001", RoomCapacity.ONE, "floorplan.com/001", false, false, BathroomType.Private, "Barbour Hall"));
    KDTreeNode node = new KDTreeNode(singleRoomList);

    FilteringCriteria criteria = new FilteringCriteria(
        Set.of(CampusLocation.WristonQuad),
        Set.of(false),
        Set.of(false),
        Set.of(BathroomType.Private),
        50, 150,
        Set.of(RoomCapacity.ONE),
        Set.of(FloorNumber.ZERO)
    );

    Set<IDormRoom> result = node.filterDormSet(criteria, 0);
    assertEquals(1, result.size());
  }

  @Test
  public void testDuplicateRoomsHandledCorrectly() {
    List<IDormRoom> duplicateRooms = List.of(
        new DormRoom(300, "102", RoomCapacity.TWO, "floorplan.com/102", true, false, BathroomType.Communal, "Buxton House"),
        new DormRoom(300, "102", RoomCapacity.TWO, "floorplan.com/102", true, false, BathroomType.Communal, "Buxton House")
    );
    KDTreeNode node = new KDTreeNode(duplicateRooms);

    FilteringCriteria criteria = new FilteringCriteria(
        Set.of(CampusLocation.WristonQuad),
        Set.of(false),
        Set.of(true),
        Set.of(BathroomType.Communal),
        300, 300,
        Set.of(RoomCapacity.TWO),
        Set.of(FloorNumber.ONE)
    );

    Set<IDormRoom> result = node.filterDormSet(criteria, 0);
    assertEquals(1, result.size(), "Duplicate rooms should not affect results.");
  }

  @Test
  public void testEmptyFilteringCriteriaReturnsEmptySet() {
    KDTreeNode node = new KDTreeNode(baseRooms);

    FilteringCriteria emptyCriteria = new FilteringCriteria(
        Set.of(), Set.of(), Set.of(), Set.of(), 0, 0, Set.of(), Set.of()
    );

    Set<IDormRoom> result = node.filterDormSet(emptyCriteria, 0);
    assertTrue(result.isEmpty(), "Empty filtering criteria should return an empty set.");
  }

  @Test
  public void testInvalidRangeInFilteringCriteria() {
    KDTreeNode node = new KDTreeNode(baseRooms);

    FilteringCriteria invalidRangeCriteria = new FilteringCriteria(
        Set.of(),
        Set.of(),
        Set.of(),
        Set.of(),
        400, 200, // Invalid range (min > max)
        Set.of(),
        Set.of()
    );

    Set<IDormRoom> result = node.filterDormSet(invalidRangeCriteria, 0);
    assertTrue(result.isEmpty(), "Invalid range should return an empty set.");
  }

  // --- KDTreeWrapper Tests ---



//  @Test
//  public void testLargeDormListFiltering() {
//    List<IDormRoom> largeRooms = generateRoomList(100_000);
//    assertDoesNotThrow(() -> new KDTreeWrapper(largeRooms));
//
//    KDTreeWrapper wrapper = new KDTreeWrapper(largeRooms);
//    FilteringCriteria criteria = new FilteringCriteria(
//        Set.of(CampusLocation.WristonQuad,
//            CampusLocation.MainGreen, CampusLocation.GradCenter, CampusLocation.GregorianQuad,
//            CampusLocation.RuthJSimmons, CampusLocation.ThayerStreet, CampusLocation.EastCampus,
//            CampusLocation.Machado),
//        Set.of(true),
//        Set.of(false),
//        Set.of(BathroomType.SemiPrivate),
//        200, 500,
//        Set.of(RoomCapacity.One, RoomCapacity.Two),
//        Set.of(0,1,2,3,4,5,6,7,8)
//    );
//
//    Set<IDormRoom> result = wrapper.filterDormSet(criteria);
//    assertNotNull(result);
//  }

  @Test
  public void testWrapperWithEmptyCriteria() {
    KDTreeWrapper wrapper = new KDTreeWrapper(baseRooms);

    FilteringCriteria criteria = new FilteringCriteria(
        Set.of(),
        Set.of(),
        Set.of(),
        Set.of(),
        0, 0,
        Set.of(),
        Set.of()
    );

    Set<IDormRoom> result = wrapper.filterDormSet(criteria);
    assertTrue(result.isEmpty(), "Empty filtering criteria should return no results.");
  }

  @Test
  public void testWrapperWithAllMatchingCriteria() {
    KDTreeWrapper wrapper = new KDTreeWrapper(baseRooms);

    FilteringCriteria criteria = new FilteringCriteria(
        Set.of(CampusLocation.WristonQuad,
            CampusLocation.MainGreen, CampusLocation.GradCenter, CampusLocation.GregorianQuad,
            CampusLocation.RuthJSimmons, CampusLocation.ThayerStreet, CampusLocation.EastCampus,
            CampusLocation.Machado),
        Set.of(true, false),
        Set.of(true, false),
        Set.of(BathroomType.Private, BathroomType.Communal, BathroomType.SemiPrivate),
        0, Integer.MAX_VALUE,
        Set.of(RoomCapacity.ONE, RoomCapacity.TWO, RoomCapacity.THREE, RoomCapacity.FOUR),
        Set.of(FloorNumber.ZERO, FloorNumber.ONE, FloorNumber.TWO, FloorNumber.THREE, FloorNumber.FOUR,
            FloorNumber.FIVE, FloorNumber.SIX, FloorNumber.SEVEN, FloorNumber.EIGHT)
    );

    Set<IDormRoom> result = wrapper.filterDormSet(criteria);
    assertEquals(baseRooms.size(), result.size(), "All rooms should match the criteria.");
  }

//  @Test
//  public void testLargeDormListPerformance() {
//    List<IDormRoom> largeRooms = generateRoomList(100_000);
//    assertDoesNotThrow(() -> new KDTreeWrapper(largeRooms));
//
//    KDTreeWrapper wrapper = new KDTreeWrapper(largeRooms);
//    FilteringCriteria criteria = new FilteringCriteria(
//        Set.of(CampusLocation.WristonQuad,
//            CampusLocation.MainGreen, CampusLocation.GradCenter, CampusLocation.GregorianQuad,
//            CampusLocation.RuthJSimmons, CampusLocation.ThayerStreet, CampusLocation.EastCampus,
//            CampusLocation.Machado),
//        Set.of(true, false),
//        Set.of(true, false),
//        Set.of(BathroomType.Communal, BathroomType.SemiPrivate, BathroomType.Private),
//        0, 200100,
//        Set.of(RoomCapacity.ONE, RoomCapacity.TWO, RoomCapacity.THREE, RoomCapacity.FOUR,
//            RoomCapacity.FIVE, RoomCapacity.SIX),
//        Set.of(0,1,2,3,4,5,6,7,8,9)
//    );
//
//    Set<IDormRoom> result = wrapper.filterDormSet(criteria);
//    assertNotNull(result);
//    assertEquals(largeRooms.size(), result.size(), "All rooms should match the criteria.");
//  }
//
//  private List<IDormRoom> generateRoomList(int size) {
//    return java.util.stream.IntStream.range(1, size + 1)
//        .mapToObj(i -> new DormRoom(
//            100 + (i % 500),
//            String.valueOf(i+100),
//            RoomCapacity.values()[i % RoomCapacity.values().length],
//            "floorplan.com/" + i,
//            i % 2 == 0,
//            i % 2 == 0,
//            BathroomType.values()[i % BathroomType.values().length],
//            "Barbour Hall"
//        ))
//        .collect(Collectors.toList());
//  }
}
