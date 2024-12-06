package DormRoom;

/**
 * The {@code DormRoom} class represents a dormitory room with various attributes such as size,
 * capacity, building location, and amenities. It is designed to encapsulate data about a single
 * dorm room, including its building, the type of bathroom it has, and whether it includes a kitchen
 * or is part of a suite. Instances of this class are immutable once constructed.
 *
 * <p>Usage example:
 * <pre>
 * DormRoom myRoom = new DormRoom(200, 255, 2,
 *    'example.com', true, false,
 *    BathroomType.Private, "Barbour Hall");
 * </pre>
 */
public class DormRoom {

    /**
     * The square footage of the room.
     */
    final int roomSize;

    /**
     * The room number, as assigned by the Office of Residential Life. For example, "Room 255".
     */
    final int roomNumber;

    /**
     * The number of people that can reside in this dorm room.
     */
    final int roomCapacity;

    /**
     * A link (URL) to the floor plan that includes this room.
     */
    final String floorPlanLink;

    /**
     * Indicates whether the room has a kitchen.
     */
    final boolean hasKitchen;

    /**
     * Indicates whether the room is considered a suite.
     */
    final boolean isSuite;

    /**
     * The type of bathroom available for this room.
     */
    final BathroomType bathroomType;

    /**
     * The building in which this dorm room is located.
     */
    final DormBuilding building;

    /**
     * Constructs a new {@code DormRoom} with the given attributes.
     *
     * @param roomSize       the square footage of the room
     * @param roomNumber     the assigned room number (e.g., 255)
     * @param roomCapacity   the number of occupants the room can house
     * @param floorPlanLink  a URL linking to the floor plan containing this room
     * @param hasKitchen     whether the room includes a kitchen
     * @param isSuite        whether the room is considered part of a suite
     * @param bathroomType   the type of bathroom (Private, SemiPrivate, or Communal)
     * @param buildingName   the name of the building (must match one of the {@link DormBuilding} enum constants)
     *
     * @throws RuntimeException if the specified buildingName does not match any known building
     */
    public DormRoom(int roomSize, int roomNumber, int roomCapacity,
        String floorPlanLink, boolean hasKitchen, boolean isSuite, BathroomType bathroomType,
        String buildingName) {
        this.roomSize = roomSize;
        this.roomNumber = roomNumber;
        this.roomCapacity = roomCapacity;
        this.floorPlanLink = floorPlanLink;
        this.hasKitchen = hasKitchen;
        this.isSuite = isSuite;
        this.bathroomType = bathroomType;
        this.building = new DormBuilding(buildingName);
    }
}
