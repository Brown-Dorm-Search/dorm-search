package Server;

import DormRoom.DormBuilding;
import DormRoom.FloorNumber;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import spark.Request;
import spark.Response;
import spark.Route;
import DormRoom.BathroomType;
import DormRoom.CampusLocation;
import DormRoom.IDormRoom;
import DormRoom.RoomCapacity;
import Filtering.FilteringCache;
import Filtering.FilteringCriteria;

/**
 * The {@code FilteringHandler} class handles HTTP requests for dorm room filtering queries.
 * It validates query parameters, applies filtering criteria, and returns filtered dorm room results in JSON format.
 */
public class FilteringHandler implements Route {

  /** The caching layer for filtering operations to improve performance. */
  private final FilteringCache cache;

  /**
   * Constructs a new {@code FilteringHandler} with the provided cache for filtering dorm rooms.
   *
   * @param cache the {@link FilteringCache} to use for filtering operations
   */
  public FilteringHandler(FilteringCache cache) {
    this.cache = cache;
  }

  /**
   * Handles an HTTP request to filter dorm rooms based on query parameters.
   *
   * @param request  the HTTP request containing query parameters
   * @param response the HTTP response object
   * @return a JSON-formatted response containing the filtered dorm rooms or an error message
   * @throws Exception if an error occurs during filtering or parameter validation
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    Map<String, Object> output = new HashMap<>();

    // Get all the parameters from the request
    String campusLocation = request.queryParams("campusLocation");
    String isSuite = request.queryParams("isSuite");
    String hasKitchen = request.queryParams("hasKitchen");
    String bathroomType = request.queryParams("bathroomType");
    String minRoomSize = request.queryParams("minRoomSize");
    String maxRoomSize = request.queryParams("maxRoomSize");
    String roomCapacity = request.queryParams("roomCapacity");
    String floorNumber = request.queryParams("floorNumber");

    // Add the parameters to the output map for logging or debugging
    output.put("campusLocation", campusLocation);
    output.put("isSuite", isSuite);
    output.put("hasKitchen", hasKitchen);
    output.put("bathroomType", bathroomType);
    output.put("minRoomSize", minRoomSize);
    output.put("maxRoomSize", maxRoomSize);
    output.put("roomCapacity", roomCapacity);
    output.put("floorNumber", floorNumber);

    // Variables for parsed filtering criteria
    Set<CampusLocation> campusLocationCriteria;
    Set<Boolean> isSuiteCriteria;
    Set<Boolean> hasKitchenCriteria;
    Set<BathroomType> bathroomTypeCriteria;
    int minRoomSizeCriteria;
    int maxRoomSizeCriteria;
    Set<RoomCapacity> roomCapacityCriteria;
    Set<FloorNumber> floorNumberCriteria;

    try {
      // Validate and parse query parameters
      campusLocationCriteria = this.validateCampusLocationString(campusLocation);
      isSuiteCriteria = this.validateIsSuiteString(isSuite);
      hasKitchenCriteria = this.validateHasKitchenString(hasKitchen);
      bathroomTypeCriteria = this.validateBathroomTypeString(bathroomType);
      minRoomSizeCriteria = this.validateMinRoomSizeString(minRoomSize);
      maxRoomSizeCriteria = this.validateMaxRoomSizeString(maxRoomSize);
      roomCapacityCriteria = this.validateRoomCapacityString(roomCapacity);
      floorNumberCriteria = this.validateFloorNumberString(floorNumber);
    } catch (Exception error) {
      // Catch validation errors and return a structured error response
      output.put("result", "error_bad_request");
      output.put("error_message", error.getMessage());
      return this.serialize(output);
    }

    // Apply validated filtering criteria
    FilteringCriteria filteringCriteria = new FilteringCriteria(
        campusLocationCriteria, isSuiteCriteria, hasKitchenCriteria,
        bathroomTypeCriteria, minRoomSizeCriteria, maxRoomSizeCriteria,
        roomCapacityCriteria, floorNumberCriteria
    );
    Set<IDormRoom> filteredDormRoomSet = this.cache.getFilteredDormSet(filteringCriteria);

    // Return success response with filtered results
    output.put("result", "success");
    output.put("filteredDormRoomSet", filteredDormRoomSet);
    output.put("dormBuildingList", DormBuilding.dormBuildingList());
    return this.serialize(output);
  }

  /**
   * Converts a {@link Map} into a JSON string using the Moshi library.
   *
   * @param dict the dictionary to convert
   * @return a serialized JSON string
   */
  private String serialize(Map<String, Object> dict) {
    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<Map<String, Object>> jsonAdapter =
        moshi.adapter(Types.newParameterizedType(Map.class, String.class, Object.class));
    return jsonAdapter.toJson(dict);
  }

  /**
   * Validates and parses the campusLocation parameter.
   * If "all" is provided, returns all possible campus locations.
   *
   * @param campusLocation the input string for campus locations
   * @return a set of {@link CampusLocation} values
   * @throws IllegalArgumentException if the input is invalid or missing
   */
  private Set<CampusLocation> validateCampusLocationString(String campusLocation) throws
      IllegalArgumentException {
    // Empty case
    if (campusLocation == null || campusLocation.isEmpty()) {
      throw new IllegalArgumentException("campusLocation parameter is missing or empty.");
    }

    // "All" case
    if ("all".equalsIgnoreCase(campusLocation.trim())) {
      return Set.of(CampusLocation.values());
    }

    // Every other possible input
    Set<CampusLocation> campusLocations = new HashSet<>();
    for (String location : campusLocation.split(",")) {
      try {
        campusLocations.add(CampusLocation.valueOf(location.trim()));
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(
            "Invalid value for campusLocation: " + location + ". Valid values are: "
                + Set.of(CampusLocation.values()));
      }
    }
    return campusLocations;
  }

  /**
   * Validates and parses the isSuite parameter.
   * If "all" is provided, returns both true and false.
   *
   * @param isSuite the input string for suite criteria
   * @return a set of boolean values
   * @throws IllegalArgumentException if the input is invalid or missing
   */
  private Set<Boolean> validateIsSuiteString(String isSuite) throws IllegalArgumentException {
    // Empty case
    if (isSuite == null || isSuite.isEmpty()) {
      throw new IllegalArgumentException("isSuite parameter is missing or empty.");
    }

    // "All" case
    if ("all".equalsIgnoreCase(isSuite.trim())) {
      return Set.of(true, false);
    }

    // Every other case
    Set<Boolean> suiteCriteria = new HashSet<>();
    for (String suite : isSuite.split(",")) {
      if (!"true".equalsIgnoreCase(suite.trim()) && !"false".equalsIgnoreCase(suite.trim())) {
        throw new IllegalArgumentException(
            "Invalid value for isSuite: " + suite + ". Valid values are: true, false.");
      }
      suiteCriteria.add(Boolean.parseBoolean(suite.trim()));
    }
    return suiteCriteria;
  }

  /**
   * Validates and parses the hasKitchen parameter.
   * If "all" is provided, returns both true and false.
   *
   * @param hasKitchen the input string for kitchen criteria
   * @return a set of boolean values
   * @throws IllegalArgumentException if the input is invalid or missing
   */
  private Set<Boolean> validateHasKitchenString(String hasKitchen) throws IllegalArgumentException {
    if (hasKitchen == null || hasKitchen.isEmpty()) {
      throw new IllegalArgumentException("hasKitchen parameter is missing or empty.");
    }
    if ("all".equalsIgnoreCase(hasKitchen.trim())) {
      return Set.of(true, false);
    }
    Set<Boolean> kitchenCriteria = new HashSet<>();
    for (String kitchen : hasKitchen.split(",")) {
      if (!"true".equalsIgnoreCase(kitchen.trim()) && !"false".equalsIgnoreCase(kitchen.trim())) {
        throw new IllegalArgumentException(
            "Invalid value for hasKitchen: " + kitchen + ". Valid values are: true, false.");
      }
      kitchenCriteria.add(Boolean.parseBoolean(kitchen.trim()));
    }
    return kitchenCriteria;
  }

  /**
   * Validates and parses the bathroomType parameter.
   * If "all" is provided, returns all possible bathroom types.
   *
   * @param bathroomType the input string for bathroom types
   * @return a set of {@link BathroomType} values
   * @throws IllegalArgumentException if the input is invalid or missing
   */
  private Set<BathroomType> validateBathroomTypeString(String bathroomType) throws
      IllegalArgumentException {
    // Empty case
    if (bathroomType == null || bathroomType.isEmpty()) {
      throw new IllegalArgumentException("bathroomType parameter is missing or empty.");
    }

    // "All" case
    if ("all".equalsIgnoreCase(bathroomType.trim())) {
      return Set.of(BathroomType.values());
    }

    // Every other case
    Set<BathroomType> bathroomTypes = new HashSet<>();
    for (String type : bathroomType.split(",")) {
      try {
        bathroomTypes.add(BathroomType.valueOf(type.trim()));
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(
            "Invalid value for bathroomType: " + type + ". Valid values are: "
                + Set.of(BathroomType.values()));
      }
    }
    return bathroomTypes;
  }

  /**
   * Validates and parses the minRoomSize parameter.
   * If "all" is provided, returns 0 as the minRoomSize.
   *
   * @param minRoomSize the input string for minimum room size
   * @return the parsed integer value
   * @throws IllegalArgumentException if the input is invalid or missing
   */
  private int validateMinRoomSizeString(String minRoomSize) throws IllegalArgumentException {
    // Empty case
    if (minRoomSize == null || minRoomSize.isEmpty()) {
      throw new IllegalArgumentException("minRoomSize parameter is missing or empty.");
    }

    // "All" case
    if ("all".equalsIgnoreCase(minRoomSize.trim())) {
      return 0;
    }

    // Every other case
    try {
      return Integer.parseInt(minRoomSize.trim());
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid value for minRoomSize: " + minRoomSize
          + ". It must be an integer.");
    }
  }

  /**
   * Validates and parses the maxRoomSize parameter.
   * If "all" is provided, returns 99999.
   *
   * @param maxRoomSize the input string for maximum room size
   * @return the parsed integer value
   * @throws IllegalArgumentException if the input is invalid or missing
   */
  private int validateMaxRoomSizeString(String maxRoomSize) throws IllegalArgumentException {
    // Empty case
    if (maxRoomSize == null || maxRoomSize.isEmpty()) {
      throw new IllegalArgumentException("maxRoomSize parameter is missing or empty.");
    }

    // "All case"
    if ("all".equalsIgnoreCase(maxRoomSize.trim())) {
      return 99999;
    }

    // Every other case
    try {
      return Integer.parseInt(maxRoomSize.trim());
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid value for maxRoomSize: " + maxRoomSize
          + ". It must be an integer.");
    }
  }

  /**
   * Validates and parses the roomCapacity parameter.
   * If "all" is provided, returns all possible room capacities.
   *
   * @param roomCapacity the input string for room capacities
   * @return a set of {@link RoomCapacity} values
   * @throws IllegalArgumentException if the input is invalid or missing
   */
  private Set<RoomCapacity> validateRoomCapacityString(String roomCapacity) throws
      IllegalArgumentException{
    // Empty case
    if (roomCapacity == null || roomCapacity.isEmpty()) {
      throw new IllegalArgumentException("roomCapacity parameter is missing or empty.");
    }

    // "All" case
    if ("all".equalsIgnoreCase(roomCapacity.trim())) {
      return Set.of(RoomCapacity.values());
    }

    // Every other case
    Set<RoomCapacity> capacities = new HashSet<>();
    for (String capacity : roomCapacity.split(",")) {
      // Test numeric words roomCapacity. Example: "one", "Two", "THREE"
      try {
        capacities.add(RoomCapacity.valueOf(capacity.trim().toUpperCase()));
      } catch (IllegalArgumentException e1) {

        // Test integer values for roomCapacity. Example: "1", "2","3"
        try {
          capacities.add(RoomCapacity.fromInteger(Integer.parseInt(roomCapacity)));
        } catch(IllegalArgumentException e2){
          throw new IllegalArgumentException(
              "Invalid value for roomCapacity: " + capacity + ". Valid values are: "
                  + Set.of(RoomCapacity.values()));
        }
      }
    }

    return capacities;
  }

  /**
   * Validates and parses the floorNumber parameter.
   * If "all" is provided, returns all possible floor numbers.
   *
   * @param floorNumber the input string for floor numbers
   * @return a set of integer values
   * @throws IllegalArgumentException if the input is invalid or missing
   */
  private Set<FloorNumber> validateFloorNumberString(String floorNumber) throws
      IllegalArgumentException {
    // Empty case
    if (floorNumber == null || floorNumber.isEmpty()) {
      throw new IllegalArgumentException("floorNumber parameter is missing or empty.");
    }

    // "All case
    if ("all".equalsIgnoreCase(floorNumber.trim())) {
      Set<FloorNumber> allFloors = new HashSet<>();
      for (int i = 0; i <= (FloorNumber.values().length - 1); i++) {
        allFloors.add(FloorNumber.fromInteger(i));
      }
      return allFloors;
    }

    // Every other case
    Set<FloorNumber> floors = new HashSet<>();
    for (String floor : floorNumber.split(",")) {

      // Test numeric words for floorNumbers. Example: "one", "Two", "THREE", "zero"
      try {
        floors.add(FloorNumber.valueOf(floorNumber.trim().toUpperCase()));
      } catch (IllegalArgumentException e1) {

        // Test integer values for floorNumbers. Examples: "1", "2", "3"
        try {
          floors.add(FloorNumber.fromInteger(Integer.parseInt(floor.trim())));
        } catch (NumberFormatException e) {
          throw new
              IllegalArgumentException(
              "Invalid value for floorNumber: " + floor + ". It must be an integer.");
        }
      }
    }

    return floors;
  }
}