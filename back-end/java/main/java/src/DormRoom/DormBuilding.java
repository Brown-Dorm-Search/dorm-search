package src.DormRoom;

/**
 * The {@code DormBuilding} class represents data about a specific dormitory building on campus.
 * Each instance contains information such as the building's name, its postal location,
 * the year it was built, whether is had a washing machine, and its corresponding campus area.
 * Additionally, it captures the ratio of the building's occupancy to the number of available
 * washing machines (peoplePerWasher), which provides insight into the building's laundry capacity.
 *
 * <p>Constructors create an object fully initialized with the building's attributes,
 * inferred from the building name. This approach ensures that instances of {@code DormBuilding}
 * remain consistent and standardized across the application.</p>
 */
public class DormBuilding {

  /**
   * The name of the dorm building. Examples include "Barbour Hall", "Buxton House", and
   * "Grad Center A".
   */
  private final String buildingName;
  /**
   * The ratio of total building occupancy to the number of available washing machines.
   * A higher value indicates more people sharing a single washer, while a lower value
   * suggests better laundry accessibility for residents.
   */
  private final float peoplePerWasher;
  /**
   * The year in which the building was constructed. Generally, builds with more recent years
   * are cleaner, have less operational issues, and are more likely to be higher quality buildings.
   */
  private final int year;
  /**
   * The postal address of the dorm building, as specified by Google Maps.
   */
  private final String address;
  /**
   * The general location or neighborhood of the building on the campus.
   */
  private final CampusLocation campusLocation;
  /**
   * Whether a building has an elevator to different rooms. This is used primarily for accessibility
   * and also convenience for taller buildings.
   */
  private final boolean hasElevatorAccess;

  /**
   * Constructs a new {@code DormBuilding} using the supplied building name to determine
   * its attributes. This constructor uses a predefined mapping of building names to their
   * associated details (e.g., geographical coordinates, construction year, and laundry ratio).
   *
   * @param buildingName the official name of the building (e.g. "Barbour Hall", "Buxton House")
   * @throws RuntimeException if the building name does not match any known buildings in the
   * predefined mapping. All buildings should be included in this mapping.
   */
  public DormBuilding(String buildingName) {
    this.buildingName = buildingName;

    switch (buildingName) {
      // WristonQuad
      case "Buxton House" -> {
        this.peoplePerWasher = 0f;
        this.year = 1951;
        this.address = "27 Brown St, Providence, RI 02906";
        this.campusLocation = CampusLocation.WristonQuad;
        this.hasElevatorAccess = false;
      }
      case "Chapin House" -> {
        this.peoplePerWasher = 38.33333333f;
        this.year = 1951;
        this.address = "116 Thayer St, Providence, RI 02906";
        this.campusLocation = CampusLocation.WristonQuad;
        this.hasElevatorAccess = false;
      }
      case "Diman House" -> {
        this.peoplePerWasher = 29.5f;
        this.year = 1951;
        this.address = "41 Charlesfield St, Providence, RI 02906";
        this.campusLocation = CampusLocation.WristonQuad;
        this.hasElevatorAccess = false;
      }
      case "Goddard House" -> {
        this.peoplePerWasher = 39.33333333f;
        this.year = 1951;
        this.address = "39 Charlesfield St, Providence, RI 02906";
        this.campusLocation = CampusLocation.WristonQuad;
        this.hasElevatorAccess = false;
      }
      case "Harkness House" -> {
        this.peoplePerWasher = 29f;
        this.year = 1951;
        this.address = "47 Charlesfield St, Providence, RI 02906";
        this.campusLocation = CampusLocation.WristonQuad;
        this.hasElevatorAccess = false;
      }
      case "Marcy House" -> {
        this.peoplePerWasher = 58f;
        this.year = 1951;
        this.address = "115 George St, Providence, RI 02906";
        this.campusLocation = CampusLocation.WristonQuad;
        this.hasElevatorAccess = false;
      }
      case "Olney House" -> {
        this.peoplePerWasher = 39.3333333f;
        this.year = 1951;
        this.address = "29 Brown St, Providence, RI 02906";
        this.campusLocation = CampusLocation.WristonQuad;
        this.hasElevatorAccess = false;
      }
      case "Sears House" -> {
        this.peoplePerWasher = 23f;
        this.year = 1951;
        this.address = "113 George St, Providence, RI 02906";
        this.campusLocation = CampusLocation.WristonQuad;
        this.hasElevatorAccess = false;
      }

      // MainGreen
      case "Hope College" -> {
        this.peoplePerWasher = 39f;
        this.year = 1822;
        this.address = "71 Waterman St, Providence, RI 02906";
        this.campusLocation = CampusLocation.MainGreen;
        this.hasElevatorAccess = false;
      }
      case "Slater Hall" -> {
        this.peoplePerWasher = 28f;
        this.year = 1879;
        this.address = "70 George St, Providence, RI 02906";
        this.campusLocation = CampusLocation.MainGreen;
        this.hasElevatorAccess = false;
      }

      //GradCenter
      case "Grad Center A" -> {
        this.peoplePerWasher = 36.66666667f;
        this.year = 1968;
        this.address = "40 Charlesfield St, Providence, RI 02906";
        this.campusLocation = CampusLocation.GradCenter;
        this.hasElevatorAccess = false;
      }
      case "Grad Center B" -> {
        this.peoplePerWasher = 38.33333333f;
        this.year = 1968;
        this.address = "44 Charlesfield St, Providence, RI 02906";
        this.campusLocation = CampusLocation.GradCenter;
        this.hasElevatorAccess = false;
      }
      case "Grad Center C" -> {
        this.peoplePerWasher = 36.66666667f;
        this.year = 1968;
        this.address = "82 Thayer St, Providence, RI 02906";
        this.campusLocation = CampusLocation.GradCenter;
        this.hasElevatorAccess = false;
      }
      case "Grad Center D" -> {
        this.peoplePerWasher = 38f;
        this.year = 1968;
        this.address = "90 Thayer St, Providence, RI 02906";
        this.campusLocation = CampusLocation.GradCenter;
        this.hasElevatorAccess = false;
      }

      // GregorianQuad
      case "Vartan Gregorian Quad A" -> {
        this.peoplePerWasher = 28.5f;
        this.year = 1991;
        this.address = "103 Thayer St, Providence, RI 02906";
        this.campusLocation = CampusLocation.GregorianQuad;
        this.hasElevatorAccess = true;
      }
      case "Vartan Gregorian Quad B" -> {
        this.peoplePerWasher = 28.5f;
        this.year = 1991;
        this.address = "101 Thayer St, Providence, RI 02906";
        this.campusLocation = CampusLocation.GregorianQuad;
        this.hasElevatorAccess = true;
      }

      // Pembroke
      case "New Pembroke #1" -> {
        this.peoplePerWasher = Float.parseFloat(null);
        this.year = 0;
        this.address = null;
        this.campusLocation = CampusLocation.Pembroke;
        this.hasElevatorAccess = Boolean.getBoolean("null");
      }
      case "New Pembroke #2" -> {
        this.peoplePerWasher = Float.parseFloat(null);
        this.year = 0;
        this.address = null;
        this.campusLocation = CampusLocation.Pembroke;
        this.hasElevatorAccess = Boolean.getBoolean("null");
      }
      case "New Pembroke #3" -> {
        this.peoplePerWasher = Float.parseFloat(null);
        this.year = 0;
        this.address = null;
        this.campusLocation = CampusLocation.Pembroke;
        this.hasElevatorAccess = Boolean.getBoolean("null");
      }

      // RuthJSimmons
      case "Hegemen Hall" -> {
        this.peoplePerWasher = 56.5f;
        this.year = 1991;
        this.address = "128 George St, Providence, RI 02906";
        this.campusLocation = CampusLocation.RuthJSimmons;
        this.hasElevatorAccess = true;
      }
      case "Littlefield Hall" -> {
        this.peoplePerWasher = 34f;
        this.year = 1926;
        this.address = "102 George St, Providence, RI 02906";
        this.campusLocation = CampusLocation.RuthJSimmons;
        this.hasElevatorAccess = false;
      }

      // Thayer Street
      case "Caswell Hall" -> {
        this.peoplePerWasher = 47f;
        this.year = 1903;
        this.address = "168 Thayer St, Providence, RI 02906";
        this.campusLocation = CampusLocation.ThayerStreet;
        this.hasElevatorAccess = false;
      }

      // East Campus
      case "Barbour Hall" -> {
        this.peoplePerWasher = 56f;
        this.year = 1904;
        this.address = "100 Charlesfield St, Providence, RI 02906";
        this.campusLocation = CampusLocation.EastCampus;
        this.hasElevatorAccess = false;
      }
      case "Minden Hall" -> {
        this.peoplePerWasher = 25.3333333f;
        this.year = 1912;
        this.address = "121 Waterman St, Providence, RI 02906";
        this.campusLocation = CampusLocation.EastCampus;
        this.hasElevatorAccess = true;
      }
      case "Perkins Hall" -> {
        this.peoplePerWasher = 38.6f;
        this.year = 1960;
        this.address = "154 Power St, Providence, RI 02906";
        this.campusLocation = CampusLocation.EastCampus;
        this.hasElevatorAccess = false;
      }
      case "Young Orchard #2" -> {
        this.peoplePerWasher = 28f;
        this.year = 1973;
        this.address = "Young Orchard Ave #2, Providence, RI 02906";
        this.campusLocation = CampusLocation.EastCampus;
        this.hasElevatorAccess = false;
      }
      case "Young Orchard #4" -> {
        this.peoplePerWasher = 28f;
        this.year = 1973;
        this.address = "Young Orchard Ave #4, Providence, RI 02906";
        this.campusLocation = CampusLocation.EastCampus;
        this.hasElevatorAccess = false;
      }
      case "Young Orchard #10" -> {
        this.peoplePerWasher = 18.6666667f;
        this.year = 1973;
        this.address = "Young Orchard Ave #10, Providence, RI 02906";
        this.campusLocation = CampusLocation.EastCampus;
        this.hasElevatorAccess = false;
      }

      // Machado
      case "Machado House" -> {
        this.peoplePerWasher = 425.f;
        this.year = 1912;
        this.address = "87 Prospect St, Providence, RI 02906";
        this.campusLocation = CampusLocation.Machado;
        this.hasElevatorAccess = false;
      }

      case null, default -> throw new RuntimeException("Not a valid building name: " + buildingName);
    }
  }


//  /**
//   * Returns the name of the building.
//   *
//   * @return the building's name, for example "Barbour Hall" or "Grad Center A"
//   */
//  public String getBuildingName() {
//    return buildingName;
//  }
//
//  /**
//   * Returns the ratio of total building occupancy to the number of available washing machines.
//   * A higher value indicates more people per washer.
//   *
//   * @return the people-per-washer ratio
//   */
//  public float getPeoplePerWasher() {
//    return peoplePerWasher;
//  }
//
//  /**
//   * Returns the year in which the building was constructed.
//   *
//   * @return the building's construction year
//   */
//  public int getYear() {
//    return year;
//  }
//
//  /**
//   * Returns the building's longitude coordinate in decimal degrees.
//   *
//   * @return the building's longitude
//   */
//  public double getLongitude() {
//    return longitude;
//  }
//
//  /**
//   * Returns the building's latitude coordinate in decimal degrees.
//   *
//   * @return the building's latitude
//   */
//  public double getLatitude() {
//    return latitude;
//  }
//
//  /**
//   * Returns the general campus location or neighborhood of the building.
//   *
//   * @return the building's campus location
//   */
//  public CampusLocation getCampusLocation() {
//    return campusLocation;
//  }
}
