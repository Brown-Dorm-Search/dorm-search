package src.DormRoom;

/**
 * An enum representing the maximum capacity of a dorm.
 */
public enum RoomCapacity {
  One, Two, Three, Four, Five, Six;

  /**
   * Converts the roomCapacity into an int representing the room capacity.
   *
   * @return the integer representation of the enum.
   */
  public int toInteger() {
    return this.ordinal() + 1;
  }
}