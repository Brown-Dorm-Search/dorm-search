package DormRoom;

/**
 * Represents the floor numbers in a dormitory building.
 * Each constant corresponds to a specific floor.
 */
public enum FloorNumber {
  ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT;

  /**
   * Converts a {@code FloorNumber} enum constant to its integer representation.
   * The mapping is based on the ordinal value of the enum, where {@code ZERO} maps to 0, {@code ONE} to 1, and so on.
   *
   * @return the integer representation of the floor number.
   */
  public int toInteger() {
    return this.ordinal();
  }


  /**
   * Creates a {@code FloorNumber} from the given integer value.
   * The integer must correspond to a valid floor number, ranging from 0 to 8.
   *
   * @param number the integer representing the floor number.
   * @return the corresponding {@code FloorNumber}.
   * @throws IllegalArgumentException if the number is not in the range of valid floor numbers
   *                                  [0, 8].
   */
  public static FloorNumber fromInteger(int number) throws IllegalArgumentException {
    if (number < 0 || number >= values().length) {
      throw new IllegalArgumentException("Invalid floor number: " + number +
          ". Must be between 0 and " + (values().length - 1) + ".");
    }
    return values()[number];
  }
}
