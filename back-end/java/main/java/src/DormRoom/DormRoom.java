package DormRoom;

import java.util.Objects;

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
public class DormRoom implements IDormRoom {
    /**
     * The square footage of the room.
     */
    protected final int roomSize;
    /**
     * The room number, as assigned by the Office of Residential Life. For example, "Room 255". This
     *   is a string instead of a number because some rooms numbers have non-numeric characters, such
     *   as "Room 255A"
     */
    protected final String roomNumber;
    /**
     * The number of people that can reside in this dorm room. 1 for singles, 2 for doubles, etc.
     */
    protected final RoomCapacity roomCapacity;
    /**
     * A link (URL) to the floor plan that includes this room.
     */
    protected final String floorPlanLink;
    /**
     * Indicates whether the room has a kitchen. {@code true} if the dorm has their own private
     * kitchen. {@code false} if the room does not have their own private kitchen
     */
    protected final boolean hasKitchen;
    /**
     * Indicates whether the room is considered a suite. {@code true} if the dorm is a suite.
     * {@code false} if the room is not considered a suite.
     */
    protected final boolean isSuite;
    /**
     * The type of bathroom available for this room. The potential types are public, semi-private,
     * and private.
     */
    protected final BathroomType bathroomType;
    /**
     * The building in which this dorm room is located.
     */
    protected final DormBuilding dormBuilding;

    /**
     * Constructs a new {@code DormRoom} with the given attributes.
     *
     * @param roomSize       the square footage of the room
     * @param roomNumber     the assigned room number (e.g., 255A)
     * @param roomCapacity   the number of occupants the room can house
     * @param floorPlanLink  a URL linking to the floor plan containing this room
     * @param hasKitchen     whether the room includes a kitchen
     * @param isSuite        whether the room is considered part of a suite
     * @param bathroomType   the type of bathroom (Private, SemiPrivate, or Communal)
     * @param buildingName   the name of the building (must match one of the {@link DormBuilding}
     *                       constants)
     *
     * @throws RuntimeException if the specified buildingName does not match any known building
     */
    public DormRoom(int roomSize, String roomNumber, RoomCapacity roomCapacity,
        String floorPlanLink, boolean hasKitchen, boolean isSuite, BathroomType bathroomType,
        String buildingName) {
        this.roomSize = roomSize;
        this.roomNumber = roomNumber;
        this.roomCapacity = roomCapacity;
        this.floorPlanLink = floorPlanLink;
        this.hasKitchen = hasKitchen;
        this.isSuite = isSuite;
        this.bathroomType = bathroomType;
        this.dormBuilding = new DormBuilding(buildingName);
    }


    /**
     * Returns the square footage of this dorm room.
     *
     * @return the room's square footage
     */
    public int getRoomSize() {
        return roomSize;
    }

    /**
     * Returns the assigned room number. For example, 255A.
     *
     * @return the room number
     */
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * Returns the number of occupants this room can house as an int.
     *
     * @return the room's capacity
     */
    public int getRoomCapacityInt() {
        return roomCapacity.toInteger();
    }
    /**
     * Returns the number of occupants this room can house.
     *
     * @return the room's capacity
     */
    public RoomCapacity getRoomCapacity() {
        return roomCapacity;
    }

//    /**
//     * Returns the URL of the floor plan that includes this room.
//     *
//     * @return the floor plan URL
//     */
    public String getFloorPlanLink() {
        return floorPlanLink;
    }

    /**
     * Indicates whether the room is equipped with a kitchen.
     *
     * @return {@code true} if the room has a kitchen, {@code false} otherwise
     */
    public boolean hasKitchen() {
        return hasKitchen;
    }

    /**
     * Indicates whether the room is considered a suite.
     *
     * @return {@code true} if the room is a suite, {@code false} otherwise
     */
    public boolean isSuite() {
        return isSuite;
    }

    /**
     * Returns the type of bathroom available to this room.
     *
     * @return the bathroom type (Private, SemiPrivate, or Communal)
     */
    public BathroomType getBathroomType() {
        return bathroomType;
    }

    /**
     * Returns the {@link DormBuilding} associated with this room.
     *
     * @return the building in which the room is located
     */
    public DormBuilding getDormBuilding() {
        return dormBuilding;
    }
    /**
     * Returns the floor number of the dorm room
     *
     * @return the floor number of the dorm room
     */
    public int getFloorNumber(){
        return Integer.parseInt(this.roomNumber.substring(0,1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final DormRoom dormRoom)) {
            return false;
        }
      return roomSize == dormRoom.roomSize && hasKitchen == dormRoom.hasKitchen
            && isSuite == dormRoom.isSuite && roomNumber.equals(dormRoom.roomNumber)
            && roomCapacity == dormRoom.roomCapacity && Objects.equals(floorPlanLink,
            dormRoom.floorPlanLink) && bathroomType == dormRoom.bathroomType && Objects.equals(
            dormBuilding, dormRoom.dormBuilding);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomSize, roomNumber, roomCapacity, floorPlanLink, hasKitchen, isSuite,
            bathroomType, dormBuilding);
    }
}
