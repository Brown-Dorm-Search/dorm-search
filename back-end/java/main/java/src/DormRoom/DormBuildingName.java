package DormRoom;

/**
 * An enumeration of all possible dormitory buildings on campus.
 *
 * <p>Each value corresponds to a known dorm building. By using this enum, developers can
 * refer to buildings by a strongly typed constant rather than a string. This ensures
 * consistency and reduces the likelihood of errors due to typos or inconsistent naming.</p>
 *
 * <p>Some building names that are not used or are unsupported have been commented out for reference.
 * To reintroduce them, simply uncomment their respective enum constants and ensure they are handled
 * in the corresponding logic (e.g., in {@link DormBuilding}).</p>
 */
public enum DormBuildingName {
  // WristonQuad
  BUXTON_HOUSE,
  CHAPIN_HOUSE,
  DIMAN_HOUSE,
  GODDARD_HOUSE,
  HARKNESS_HOUSE,
  MARCY_HOUSE,
  OLNEY_HOUSE,
  SEARS_HOUSE,

  // MainGreen
  HOPE_COLLEGE,
  SLATER_HALL,

  // GradCenter
  GRAD_CENTER_A,
  GRAD_CENTER_B,
  GRAD_CENTER_C,
  GRAD_CENTER_D,

  // GregorianQuad
  VARTAN_GREGORIAN_QUAD_A,
  VARTAN_GREGORIAN_QUAD_B,

  // Pembroke (commented out as requested)
//    NEW_PEMBROKE_1,
//    NEW_PEMBROKE_2,
//    NEW_PEMBROKE_3,

  // RuthJSimmons
  HEGEMAN_HALL,
  LITTLEFIELD_HALL,

  // Thayer Street
  CASWELL_HALL,

  // East Campus
  BARBOUR_HALL,
  KING_HOUSE,
  MINDEN_HALL,
  PERKINS_HALL,
  YOUNG_ORCHARD_2,
  YOUNG_ORCHARD_4,
  YOUNG_ORCHARD_10,

  // Machado
  MACHADO_HOUSE;

  /**
   * Returns the corresponding {@link DormBuildingName} enum constant for the given building name string.
   *
   * <p>This method ignores case and converts spaces to underscores before attempting
   * to find a match. For example, "King House" or "king house" would map to {@code KING_HOUSE}.</p>
   *
   * @param name the building name to convert, case-insensitive, with spaces allowed.
   * @return the matching {@link DormBuildingName} enum constant
   * @throws IllegalArgumentException if the provided name does not match any enum constant
   */
  public static DormBuildingName fromString(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Building name cannot be null");
    }

    // Normalize the input: remove leading/trailing spaces, convert to uppercase, and replace spaces with underscores
    String normalized = name.trim().toUpperCase().replace(' ', '_');

    try {
      return DormBuildingName.valueOf(normalized);
    } catch (IllegalArgumentException e) {
      // If valueOf fails, the name does not match any known enum constant
      throw new IllegalArgumentException("No enum constant for building name: " + name, e);
    }
  }

  /**
   * Converts this enum constant into a human-readable string:
   * <ol>
   *   <li>Splits the enum's name by underscores into separate words.</li>
   *   <li>Converts each word to lowercase and capitalizes the first letter.</li>
   *   <li>Joins the words back together with spaces.</li>
   * </ol>
   *
   * For example:
   * <ul>
   *   <li>KING_HOUSE -> "King House"</li>
   *   <li>HOPE_COLLEGE -> "Hope College"</li>
   *   <li>GRAD_CENTER_A -> "Grad Center A"</li>
   * </ul>
   *
   * @return A string where the first letter of each word is capitalized and underscores are replaced with spaces.
   */
  @Override
  public String toString() {
    // Get the original enum constant's name (e.g., "KING_HOUSE")
    String originalName = name();

    // Split the name by underscores to separate words (e.g., ["KING", "HOUSE"])
    String[] parts = originalName.split("_");

    // Use a StringBuilder to efficiently construct the final string
    StringBuilder formattedName = new StringBuilder();

    // Process each word in the array
    for (int i = 0; i < parts.length; i++) {
      // Convert the entire word to lowercase
      String lowerCaseWord = parts[i].toLowerCase();

      // Capitalize the first letter of the word, if it exists
      // e.g., "king" -> "King"
      if (!lowerCaseWord.isEmpty()) {
        char firstLetter = Character.toUpperCase(lowerCaseWord.charAt(0));
        String remainder = lowerCaseWord.substring(1);
        lowerCaseWord = firstLetter + remainder;
      }

      // Append this processed word to the StringBuilder
      formattedName.append(lowerCaseWord);

      // If this is not the last word, add a space after it
      if (i < parts.length - 1) {
        formattedName.append(" ");
      }
    }

    // Convert the StringBuilder to a string and return it
    return formattedName.toString();
  }
}
