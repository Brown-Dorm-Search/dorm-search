package DormRoom;

/**
 * The {@code DormBuilding} class represents data about a specific dormitory building on campus.
 * Each instance contains information such as the building's name, its location (latitude
 * and longitude), the year it was built, and its corresponding campus area. Additionally,
 * it captures the ratio of the building's occupancy to the number of available washing machines
 * (peoplePerWasher), which provides insight into the building's laundry capacity.
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
  final String buildingName;
  /**
   * The ratio of total building occupancy to the number of available washing machines.
   * A higher value indicates more people sharing a single washer, while a lower value
   * suggests better laundry accessibility for residents.
   */
  final float peoplePerWasher;
  /**
   * The year in which the building was constructed. This can be useful for historical data,
   * maintenance schedules, or evaluating the age of infrastructure.
   */
  final int year;
  /**
   * The longitude coordinate of the building's location on campus, given in decimal degrees.
   * This can be used in geographical calculations, mapping features, or integrated with
   * location-based services.
   */
  final double longitude;
  /**
   * The latitude coordinate of the building's location on campus, given in decimal degrees.
   * Used in conjunction with longitude, this allows the building's position to be plotted
   * or integrated with GIS systems.
   */
  final double latitude;
  /**
   * The general location or neighborhood of the building on the campus. This can be used
   * for wayfinding, categorizing accommodations, or filtering buildings by campus area.
   */
  final CampusLocation campusLocation;


  /**
   * Constructs a new {@code DormBuilding} using the supplied building name to determine
   * its attributes. This constructor uses a predefined mapping of building names to their
   * associated details (e.g., geographical coordinates, construction year, and laundry ratio).
   *
   * @param buildingName the official name of the building (e.g. "Barbour Hall", "Buxton House")
   * @throws RuntimeException if the building name does not match any known buildings in the predefined mapping
   */
  public DormBuilding(String buildingName) {
    this.buildingName = buildingName;

    switch (buildingName) {
      case "Barbour Hall" -> {
        this.peoplePerWasher = 56f;
        this.year = 1904;
        this.longitude = -71.398025;
        this.latitude = 41.823856;
        this.campusLocation = CampusLocation.EastCampus;
      }
      case "Buxton House" -> {
        this.peoplePerWasher = 0f;
        this.year = 1951;
        this.longitude = -71.402334;
        this.latitude = 41.824522;
        this.campusLocation = CampusLocation.WristonQuad;
      }
      case "Caswell Hall" -> {
        this.peoplePerWasher = 47f;
        this.year = 1903;
        this.longitude = -71.400623;
        this.latitude = 41.826106;
        this.campusLocation = CampusLocation.SimmonsQuad;
      }
      case "Chapin House" -> {
        this.peoplePerWasher = 38.33333333f;
        this.year = 1951;
        this.longitude = -71.400398;
        this.latitude = 41.82446;
        this.campusLocation = CampusLocation.WristonQuad;
      }
      case "Diman House" -> {
        this.peoplePerWasher = 29.5f;
        this.year = 1951;
        this.longitude = -71.401556;
        this.latitude = 41.82439;
        this.campusLocation = CampusLocation.WristonQuad;
      }
      case "Goddard House" -> {
        this.peoplePerWasher = 39.33333333f;
        this.year = 1951;
        this.longitude = -71.401293;
        this.latitude = 41.824126;
        this.campusLocation = CampusLocation.WristonQuad;
      }
      case "Grad Center A" -> {
        this.peoplePerWasher = 36.66666667f;
        this.year = 1968;
        this.longitude = -71.400953;
        this.latitude = 41.823065;
        this.campusLocation = CampusLocation.GradCenter;
      }
      case "Grad Center B" -> {
        this.peoplePerWasher = 38.33333333f;
        this.year = 1968;
        this.longitude = -71.401009;
        this.latitude = 41.823566;
        this.campusLocation = CampusLocation.GradCenter;
      }
      case "Grad Center C" -> {
        this.peoplePerWasher = 36.66666667f;
        this.year = 1968;
        this.longitude = -71.400368;
        this.latitude = 41.823628;
        this.campusLocation = CampusLocation.GradCenter;
      }
      case "Grad Center D" -> {
        this.peoplePerWasher = 38f;
        this.year = 1968;
        this.longitude = -71.400305;
        this.latitude = 41.823124;
        this.campusLocation = CampusLocation.GradCenter;
      }
      case null, default -> throw new RuntimeException("Not a valid building name: " + buildingName);
    }
  }


  /**
   * Returns the name of the building.
   *
   * @return the building's name, for example "Barbour Hall" or "Grad Center A"
   */
  public String getBuildingName() {
    return buildingName;
  }

  /**
   * Returns the ratio of total building occupancy to the number of available washing machines.
   * A higher value indicates more people per washer.
   *
   * @return the people-per-washer ratio
   */
  public float getPeoplePerWasher() {
    return peoplePerWasher;
  }

  /**
   * Returns the year in which the building was constructed.
   *
   * @return the building's construction year
   */
  public int getYear() {
    return year;
  }

  /**
   * Returns the building's longitude coordinate in decimal degrees.
   *
   * @return the building's longitude
   */
  public double getLongitude() {
    return longitude;
  }

  /**
   * Returns the building's latitude coordinate in decimal degrees.
   *
   * @return the building's latitude
   */
  public double getLatitude() {
    return latitude;
  }

  /**
   * Returns the general campus location or neighborhood of the building.
   *
   * @return the building's campus location
   */
  public CampusLocation getCampusLocation() {
    return campusLocation;
  }
}
