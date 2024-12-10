package src.DormRoom;

import java.util.List;

/**
 * The {@code Suite} class represents a specialized type of {@link DormRoom} that contains
 * multiple internal rooms and a common area. A suite can be thought of as a larger
 * living space shared by multiple occupants, featuring multiple bedrooms and
 * possibly other amenities, all falling under a single room number or identifier.
 *
 * <p>This class extends the {@link DormRoom} class, adding the concept of a shared
 * common area and a list of internal dorm rooms. The {@code Suite} is distinguished
 * by having multiple rooms within it, each of which can be considered a standard
 * {@link DormRoom}, and a communal space accessible to all occupants of the suite.</p>
 *
 * <p><strong>Usage Example:</strong>
 * <pre>{@code
 * // Create individual dorm rooms that make up the suite
 * DormRoom roomA = new DormRoom(120, "201A", new RoomCapacity(2), "floorplan_url", true, true,
 *     BathroomType.SemiPrivate, "Barbour Hall");
 * DormRoom roomB = new DormRoom(130, "201B", new RoomCapacity(2), "floorplan_url", true, true,
 *     BathroomType.SemiPrivate, "Barbour Hall");
 *
 * // Create a suite composed of roomA and roomB, with a shared living area
 * Suite mySuite = new Suite(
 *     300, "201", new RoomCapacity(4), "floorplan_url", true, true,
 *     BathroomType.SemiPrivate, "Barbour Hall", 50, List.of(roomA, roomB)
 * );
 * }</pre>
 */
public class Suite extends DormRoom {

  /**
   * The square footage of the suite's common area. This space is shared among
   * the occupants of all internal dorm rooms that make up the suite.
   */
  private final int commonAreaSize;

  /**
   * A list of the individual dorm rooms contained within this suite. These may be
   * rooms that share a common entrance, lounge, kitchen, or other amenities.
   * Occupants of these internal rooms can access the common area and potentially
   * other shared resources.
   */
  private final List<DormRoom> internalDormRooms;

  /**
   * Constructs a new {@code Suite} instance with the specified attributes. Suites are
   * considered a special type of {@link DormRoom} that contain multiple rooms and a common area.
   *
   * @param roomSize       the total square footage of the suite (excluding the common area size)
   * @param roomNumber     the identifying number or label for the suite (e.g., "201")
   * @param roomCapacity   the total number of occupants that the suite can house
   * @param floorPlanLink  a URL linking to a floor plan that includes this suite
   * @param hasKitchen     whether the suite includes a kitchen
   * @param isSuite        whether the room is considered part of a suite (should be {@code true})
   * @param bathroomType   the type of bathroom(s) available in the suite
   * @param buildingName   the name of the building in which this suite is located; must match
   *                       a known {@link DormBuilding} constant
   * @param commonAreaSize the square footage of the suite's common area
   * @param internalDormRooms the list of individual {@link DormRoom} instances that form the suite
   *
   * @throws RuntimeException if the specified buildingName does not match any known building
   */
  public Suite(int roomSize,
      String roomNumber,
      RoomCapacity roomCapacity,
      String floorPlanLink,
      boolean hasKitchen,
      boolean isSuite,
      BathroomType bathroomType,
      String buildingName,
      int commonAreaSize,
      List<DormRoom> internalDormRooms) {
    super(roomSize, roomNumber, roomCapacity, floorPlanLink, hasKitchen, isSuite, bathroomType, buildingName);
    this.commonAreaSize = commonAreaSize;
    this.internalDormRooms = internalDormRooms;
  }
}
