package src.DormRoom;

/**
 * Interface for a dormRoom. This is used, so a KDTree can filter on both a regular DormRoom and a
 * suite at the same time.
 *
 * {@link DormRoom} and {@link Suite} are implementing this interface.
 */
public interface IDormRoom {
  /**
   * Returns the square footage of this dorm room.
   *
   * @return the room's square footage
   */
  public int getRoomSize();

  /**
   * Returns the assigned room number. For example, 255A.
   *
   * @return the room number
   */
  public String getRoomNumber();

  /**
   * Returns the number of occupants this room can house as an int.
   *
   * @return the room's capacity
   */
  public int getRoomCapacityInt();
  /**
   * Returns the type of bathroom available to this room.
   *
   * @return the bathroom type (Private, SemiPrivate, or Communal)
   */
  public BathroomType getBathroomType();

  /**
   * Returns the {@link DormBuilding} associated with this dorm room.
   *
   * @return the building in which the room is located
   */
  public DormBuilding getDormBuilding();
  /**
   * Returns the floor number of the dorm room
   *
   * @return the floor number of the dorm room
   */
  public int getFloorNumber();
  /**
   * Indicates whether the room is equipped with a kitchen.
   *
   * @return {@code true} if the room has a kitchen, {@code false} otherwise
   */
  public boolean hasKitchen();
  /**
   * Indicates whether the room is considered a suite.
   *
   * @return {@code true} if the room is a suite, {@code false} otherwise
   */
  public boolean isSuite();
}
