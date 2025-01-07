package DormRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * A record representing a dormitory building on campus, providing comprehensive information such as:
 * <ul>
 *   <li>The official building name (as a {@link DormBuildingName} enum constant)</li>
 *   <li>The ratio of total occupancy to the number of washing machines (peoplePerWasher)</li>
 *   <li>The building's construction year (year)</li>
 *   <li>The building's postal address (address)</li>
 *   <li>The building's general campus area (campusLocation)</li>
 *   <li>Whether the building includes elevator access (hasElevatorAccess)</li>
 * </ul>
 *
 * <p>To create a new {@code DormBuilding} instance, you must provide a {@link DormBuildingName}.
 * The internal data (such as address, year, and so forth) is determined based on the building name.
 * This approach standardizes and centralizes building information, ensuring consistency across the application.</p>
 *
 * <p>For example:</p>
 * <pre>{@code
 * DormBuilding dorm = new DormBuilding(DormBuildingName.HOPE_COLLEGE);
 * System.out.println(dorm.address()); // Prints the address of Hope College
 * }</pre>
 *
 * <p>The data is retrieved via a private helper method {@link #getBuildingData(DormBuildingName)},
 * which uses a switch expression to return a {@link BuildingData} record pre-populated with all
 * known attributes of each building.</p>
 */
public record DormBuilding(
    DormBuildingName buildingName,
    float peoplePerWasher,
    int year,
    String address,
    CampusLocation campusLocation,
    boolean hasElevatorAccess
) {

  /**
   * A private record that encapsulates the attribute data for a building, excluding
   * the building name (because we already have it in {@code DormBuildingName}).
   */
  private record BuildingData(
      float peoplePerWasher,
      int year,
      String address,
      CampusLocation campusLocation,
      boolean hasElevatorAccess
  ) { }

  /**
   * Constructs a new {@code DormBuilding} from a {@link BuildingData} plus the given
   * {@link DormBuildingName}.
   */
  private DormBuilding(BuildingData data, DormBuildingName buildingName) {
    this(
        buildingName,
        data.peoplePerWasher,
        data.year,
        data.address,
        data.campusLocation,
        data.hasElevatorAccess
    );
  }

  /**
   * Constructs a new {@code DormBuilding} by looking up all attributes based on the provided
   * {@link DormBuildingName} constant.
   *
   * @param buildingName the enum constant representing the dorm building
   * @throws RuntimeException if {@code buildingName} does not match a known building
   */
  public DormBuilding(DormBuildingName buildingName) {
    this(getBuildingData(buildingName), buildingName);
  }

  /**
   * Returns the {@link BuildingData} for a given {@link DormBuildingName}.
   *
   * <p>This method uses a switch expression to match each building name with its corresponding data.
   * By centralizing this logic, we ensure that data remains consistent and easy to update. If you need
   * to add a new building or modify an existing one, you only need to update this switch expression.</p>
   *
   * <p>Buildings that are not recognized by the switch cause a {@link RuntimeException}.
   * This ensures fail-fast behavior if an unsupported building name is ever used.</p>
   *
   * @param buildingName the {@link DormBuildingName} for which data is requested
   * @return a {@link BuildingData} record containing all attributes of the specified building
   * @throws RuntimeException if no data is found for the specified building name
   */
  private static BuildingData getBuildingData(DormBuildingName buildingName) {
    return switch (buildingName) {
      // WristonQuad
      case BUXTON_HOUSE -> new BuildingData(0f, 1951, "27 Brown St, Providence, RI 02906", CampusLocation.WristonQuad, false);
      case CHAPIN_HOUSE -> new BuildingData(38.33333333f, 1951, "116 Thayer St, Providence, RI 02906", CampusLocation.WristonQuad, false);
      case DIMAN_HOUSE -> new BuildingData(29.5f, 1951, "41 Charlesfield St, Providence, RI 02906", CampusLocation.WristonQuad, false);
      case GODDARD_HOUSE -> new BuildingData(39.33333333f, 1951, "39 Charlesfield St, Providence, RI 02906", CampusLocation.WristonQuad, false);
      case HARKNESS_HOUSE -> new BuildingData(29f, 1951, "47 Charlesfield St, Providence, RI 02906", CampusLocation.WristonQuad, false);
      case MARCY_HOUSE -> new BuildingData(58f, 1951, "115 George St, Providence, RI 02906", CampusLocation.WristonQuad, false);
      case OLNEY_HOUSE -> new BuildingData(39.3333333f, 1951, "29 Brown St, Providence, RI 02906", CampusLocation.WristonQuad, false);
      case SEARS_HOUSE -> new BuildingData(23f, 1951, "113 George St, Providence, RI 02906", CampusLocation.WristonQuad, false);
      case WAYLAND_HOUSE -> new BuildingData(0f, 1951, "31 Brown StNorth Providence, RI 02904", CampusLocation.WristonQuad, false);

      // MainGreen
      case HOPE_COLLEGE -> new BuildingData(39f, 1822, "71 Waterman St, Providence, RI 02906", CampusLocation.MainGreen, false);
      case SLATER_HALL -> new BuildingData(28f, 1879, "70 George St, Providence, RI 02906", CampusLocation.MainGreen, false);

      // GradCenter
      case GRAD_CENTER_A -> new BuildingData(36.66666667f, 1968, "40 Charlesfield St, Providence, RI 02906", CampusLocation.GradCenter, false);
      case GRAD_CENTER_B -> new BuildingData(38.33333333f, 1968, "44 Charlesfield St, Providence, RI 02906", CampusLocation.GradCenter, false);
      case GRAD_CENTER_C -> new BuildingData(36.66666667f, 1968, "82 Thayer St, Providence, RI 02906", CampusLocation.GradCenter, false);
      case GRAD_CENTER_D -> new BuildingData(38f, 1968, "90 Thayer St, Providence, RI 02906", CampusLocation.GradCenter, false);

      // GregorianQuad
      case VARTAN_GREGORIAN_QUAD_A -> new BuildingData(28.5f, 1991, "103 Thayer St, Providence, RI 02906", CampusLocation.GregorianQuad, true);
      case VARTAN_GREGORIAN_QUAD_B -> new BuildingData(28.5f, 1991, "101 Thayer St, Providence, RI 02906", CampusLocation.GregorianQuad, true);

      // Pembroke (commented out as requested)
//            case NEW_PEMBROKE_1 -> new BuildingData(...);
//            case NEW_PEMBROKE_2 -> new BuildingData(...);
//            case NEW_PEMBROKE_3 -> new BuildingData(...);

      // RuthJSimmons
      case HEGEMAN_HALL -> new BuildingData(56.5f, 1991, "128 George St, Providence, RI 02906", CampusLocation.RuthJSimmons, true);
      case LITTLEFIELD_HALL -> new BuildingData(34f, 1926, "102 George St, Providence, RI 02906", CampusLocation.RuthJSimmons, false);

      // Thayer Street
      case CASWELL_HALL -> new BuildingData(47f, 1903, "168 Thayer St, Providence, RI 02906", CampusLocation.ThayerStreet, false);

      // East Campus
      case BARBOUR_HALL -> new BuildingData(56f, 1904, "100 Charlesfield St, Providence, RI 02906", CampusLocation.EastCampus, false);
      case KING_HOUSE -> new BuildingData(27f, 1895, "154 Hope St, Providence, RI 02912", CampusLocation.EastCampus, false);
      case MINDEN_HALL -> new BuildingData(25.3333333f, 1912, "121 Waterman St, Providence, RI 02906", CampusLocation.EastCampus, true);
      case PERKINS_HALL -> new BuildingData(38.6f, 1960, "154 Power St, Providence, RI 02906", CampusLocation.EastCampus, false);
      case YOUNG_ORCHARD_2 -> new BuildingData(28f, 1973, "Young Orchard Ave #2, Providence, RI 02906", CampusLocation.EastCampus, false);
      case YOUNG_ORCHARD_4 -> new BuildingData(28f, 1973, "Young Orchard Ave #4, Providence, RI 02906", CampusLocation.EastCampus, false);
      case YOUNG_ORCHARD_10 -> new BuildingData(18.6666667f, 1973, "Young Orchard Ave #10, Providence, RI 02906", CampusLocation.EastCampus, false);

      // Machado
      case MACHADO_HOUSE -> new BuildingData(425f, 1912, "87 Prospect St, Providence, RI 02906", CampusLocation.Machado, false);
    };
  }

  /**
   * Returns a list of all known dorm buildings by iterating through every value of
   * {@link DormBuildingName} and constructing a corresponding {@code DormBuilding} instance.
   *
   * <p>This method provides a convenient way to retrieve all possible dorm buildings at once.
   * It collects them into a {@link List} for easy iteration and processing in other parts
   * of the application.</p>
   *
   * @return a {@link List} of {@code DormBuilding} objects, one for each {@link DormBuildingName}
   */
  public static List<DormBuilding> dormBuildingList() {
    List<DormBuilding> output = new ArrayList<>();
    DormBuildingName[] dormBuildingNameList = DormBuildingName.values();

    for (DormBuildingName dormBuildingName : dormBuildingNameList) {
      output.add(new DormBuilding(dormBuildingName));
    }

    return output;
  }
}
