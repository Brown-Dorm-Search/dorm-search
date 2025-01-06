package DormRoom;

/**
 * An enum representing the maximum capacity of a dorm.
 */
public enum RoomCapacity {
  ONE, TWO, THREE, FOUR, FIVE, SIX;

  /**
   * Converts the roomCapacity into an int representing the room capacity.
   *
   * @return the integer representation of the enum.
   */
  public int toInteger() {
    return this.ordinal() + 1;
  }

  /**
   * Creates a {@code RoomCapacity} from the given integer value.
   * The integer must correspond to a valid roomCapacity, ranging from 1 to 6.
   *
   * @param num the integer representing the roomCapacity.
   * @return the corresponding {@code RoomCapacity}.
   * @throws IllegalArgumentException if the num is not in the range of valid Room Capacity
   *                                  [1,6].
   */
  public static RoomCapacity fromInteger(int num){
    if(num < 1 || num > RoomCapacity.values().length){
      throw new IllegalArgumentException("Invalid floor number: " + num +
          ". Must be between 1 and " + values().length + ".");
    }

    return RoomCapacity.values()[num - 1];
  }
}