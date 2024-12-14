package src.Filtering.Node_KDTree;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import src.DormRoom.DormBuilding;
import src.DormRoom.BathroomType;
import src.DormRoom.DormRoom;
import src.DormRoom.IDormRoom;
import src.Filtering.FilteringCriteria;
import src.Filtering.IDormFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The {@code KDTreeWrapper} class is a hierarchical data structure that organizes
 * {@link IDormRoom} instances according to multiple categorical attributes:
 * {@link DormBuilding}, suite availability, kitchen availability, and
 * {@link BathroomType} respectively.
 *
 * <p>Internally, it maintains a nested {@link HashMap} structure keyed by these
 * categorical attributes, ultimately storing rooms in {@link KDTreeNode} instances, which
 * themselves manage spatial (k=3 dimensions) searching of rooms based on the
 * square footage, people per washer, and room capacity.
 *
 * <p>The outer layers of these nested maps categorize rooms, while the
 * {@link KDTreeNode} at the leaves is responsible for efficient quantitative
 * queries and filtering.
 *
 * <p>This class implements the {@link IDormFilter} interface, allowing it to
 * be integrated with other dorm filtering systems.
 */
public class KDTreeWrapper implements IDormFilter {
  /**
   * A nested hierarchical data structure:
   * <ul>
   *   <li>{@link DormBuilding}: Building the dorm room belongs to</li>
   *   <li>{@code Boolean} (isSuite): Whether the room is a suite</li>
   *   <li>{@code Boolean} (hasKitchen): Whether the room has a kitchen</li>
   *   <li>{@link BathroomType}: Type of bathroom available</li>
   * </ul>
   * Each terminal node of this structure is a {@link KDTreeNode}, containing
   * a KD-tree of rooms that share all these categorical attributes.
   */
  private final HashMap<DormBuilding, HashMap<Boolean, HashMap<Boolean, HashMap<BathroomType, KDTreeNode>>>> dormRoomHierarchy;

  /**
   * Constructs a new {@code KDTreeWrapper} from a provided list of {@link DormRoom}s.
   * Rooms are first grouped by building, suite status, kitchen availability,
   * and bathroom type. Each group is then managed by a {@link KDTreeNode},
   * which organizes rooms in a KD-tree structure using the specified dimensionality
   * for square footage, people per washer, and room capacity.
   *
   * @param dormRoomList a list of {@link DormRoom} instances to be integrated into the structure
   * @throws RuntimeException if a dorm room references an invalid or null building or bathroom type
   */
  public KDTreeWrapper(List<IDormRoom> dormRoomList) throws RuntimeException {
    this.dormRoomHierarchy = new HashMap<>();

    // This is a map of all the categorical variables to a list of the dormBuildings that satisfy those
    // categorical variables
    HashMap<DormBuilding, HashMap<Boolean, HashMap<Boolean, HashMap<BathroomType, List<IDormRoom>>>>> roomCategorizationMap = new HashMap<>();

    /*
     * This for loops does 2 main things
     * 1: Prevents NullPointerExceptions by initializing all the nested HashMaps within this for
     *    this.dormRoomHierarchy and roomCategorizationMap
     * 2: Places the list of dorm rooms that have specific categorical variables into roomCategorizationMap,
     *    so those lists can then be used to create a KDTreeNode.
     */
    for (IDormRoom dormRoom : dormRoomList) {
      DormBuilding dormBuilding = dormRoom.getDormBuilding();
      if (dormBuilding == null) {
        throw new RuntimeException("Dorm building cannot be null for dorm room: " + dormRoom);
      }

      boolean isSuite = dormRoom.isSuite();
      boolean hasKitchen = dormRoom.hasKitchen();
      BathroomType bathroomType = dormRoom.getBathroomType();
      if (bathroomType == null) {
        throw new RuntimeException("BathroomType cannot be null for dorm room: " + dormRoom);
      }

      // Ensure maps for KDTreeNode and for specificRoomListMap are initialized
      ensureHierarchyStructure(dormBuilding, isSuite, hasKitchen);
      ensureRoomListStructure(roomCategorizationMap, dormBuilding, isSuite, hasKitchen, bathroomType);

      // Adds the dormRoom to the list
      List<IDormRoom> specificRoomList = roomCategorizationMap
          .get(dormBuilding)
          .get(isSuite)
          .get(hasKitchen)
          .get(bathroomType);
      specificRoomList.add(dormRoom);
    }

    // Create KDTreeNodes for each category and populate them with the respective rooms
    for (DormBuilding dormBuilding : roomCategorizationMap.keySet()) {
      HashMap<Boolean, HashMap<Boolean, HashMap<BathroomType, List<IDormRoom>>>> isSuiteMap =
          roomCategorizationMap.get(dormBuilding);
      for (Boolean isSuite : isSuiteMap.keySet()) {
        HashMap<Boolean, HashMap<BathroomType, List<IDormRoom>>> hasKitchenMap = isSuiteMap.get(isSuite);
        for (Boolean hasKitchen : hasKitchenMap.keySet()) {
          HashMap<BathroomType, List<IDormRoom>> bathroomTypeMap = hasKitchenMap.get(hasKitchen);
          for (BathroomType bathroomType : bathroomTypeMap.keySet()) {
            List<IDormRoom> specificRoomList = bathroomTypeMap.get(bathroomType);
            // Initialize the KDTreeNode with the list of rooms
            this.dormRoomHierarchy.get(dormBuilding)
                .get(isSuite)
                .get(hasKitchen)
                .put(bathroomType, new KDTreeNode(specificRoomList));
          }
        }
      }
    }
  }

  /**
   * Ensures that the main nested hierarchy {@link #dormRoomHierarchy} is fully initialized for
   * the given keys: {@link DormBuilding}, suite status, and kitchen availability.
   *
   * <p>This method guarantees that a {@link HashMap} is in place at each level, so that
   * {@link KDTreeNode} instances can be safely created later without encountering null references.
   *
   * @param dormBuilding the building associated with the dorm room
   * @param isSuite whether the dorm room is a suite
   * @param hasKitchen whether the dorm room includes a kitchen
   */
  private void ensureHierarchyStructure(DormBuilding dormBuilding, boolean isSuite, boolean hasKitchen) {
    HashMap<Boolean, HashMap<Boolean, HashMap<BathroomType, KDTreeNode>>> isSuiteMap =
        this.dormRoomHierarchy.computeIfAbsent(dormBuilding, k -> new HashMap<>());

    HashMap<Boolean, HashMap<BathroomType, KDTreeNode>> hasKitchenMap = isSuiteMap.computeIfAbsent(
        isSuite, k -> new HashMap<>());

    hasKitchenMap.computeIfAbsent(hasKitchen, k -> new HashMap<>());
  }

  /**
   * Ensures that the intermediate mapping structure for categorizing dorm rooms
   * into lists (prior to creating their {@link KDTreeNode}) is fully initialized.
   * This structure, passed in as {@code roomCategorizationMap}, groups
   * {@link IDormRoom} objects by building, suite status, kitchen availability,
   * and bathroom type.
   *
   * <p>This method guarantees that a {@link HashMap} is in place at each level, so that
   * {@link List<IDormRoom>} instances can be safely created later without encountering null
   * references.
   *
   * @param roomCategorizationMap the nested mapping of rooms being built
   * @param dormBuilding the building associated with the dorm room
   * @param isSuite whether the dorm room is a suite
   * @param hasKitchen whether the dorm room includes a kitchen
   * @param bathroomType the type of bathroom in the dorm room
   */
  private void ensureRoomListStructure(
      HashMap<DormBuilding, HashMap<Boolean, HashMap<Boolean, HashMap<BathroomType, List<IDormRoom>>>>> roomCategorizationMap,
      DormBuilding dormBuilding, boolean isSuite, boolean hasKitchen, BathroomType bathroomType) {

    HashMap<Boolean, HashMap<Boolean, HashMap<BathroomType, List<IDormRoom>>>> isSuiteMap =
        roomCategorizationMap.computeIfAbsent(dormBuilding, k -> new HashMap<>());

    HashMap<Boolean, HashMap<BathroomType, List<IDormRoom>>> hasKitchenMap = isSuiteMap.computeIfAbsent(
        isSuite, k -> new HashMap<>());

    HashMap<BathroomType, List<IDormRoom>> bathroomTypeMap = hasKitchenMap.computeIfAbsent(
        hasKitchen, k -> new HashMap<>());

    bathroomTypeMap.computeIfAbsent(bathroomType, k -> new ArrayList<>());
  }

  /**
   * Filters the dorm rooms using the provided {@link FilteringCriteria}, returning only those that meet
   * the specified conditions. The method traverses through the nested hierarchy of dorm rooms
   * (organized by building, suite, kitchen availability, and bathroom type) to find the matching
   * {@link KDTreeNode} instances, and then applies quantitative filters through the KD-tree search.
   *
   * @param filteringCriteria the criteria specifying which dorm rooms should be returned
   * @return a set of dorm rooms that match all the filtering criteria
   */
  @Override
  public Set<IDormRoom> filterDormSet(FilteringCriteria filteringCriteria) {
    Set<IDormRoom> result = new HashSet<>();

    // The dorm room hierarchy is organized as:
    // dormBuilding -> isSuite -> hasKitchen -> bathroomType -> KDTreeNode
    Set<DormBuilding> dormBuildingsInHierarchy = this.dormRoomHierarchy.keySet();

    // Intersect the dorm buildings in the criteria with those actually present in the hierarchy
    // This step avoids unnecessary looping over buildings not present in the hierarchy.
    Set<DormBuilding> dormBuildingCriteriaSet = filteringCriteria.campusLocationCriteria().stream(). // Gets the set of all potential rooms
        flatMap(campusLocation -> campusLocation.dormBuildingsInCampusLocation().stream()).collect(
            Collectors.toSet());
    for (DormBuilding building : dormBuildingCriteriaSet) {
      if (!dormBuildingsInHierarchy.contains(building)) {
        continue; // Skip if the building is not in the hierarchy
      }

      HashMap<Boolean, HashMap<Boolean, HashMap<BathroomType, KDTreeNode>>> isSuiteMap = this.dormRoomHierarchy.get(building);
      Set<Boolean> suiteSetInHierarchy = isSuiteMap.keySet();

      // Loop through suite criteria
      for (Boolean suiteCriteria : filteringCriteria.isSuiteCriteria()) {
        if (!suiteSetInHierarchy.contains(suiteCriteria)) {
          continue; // Skip if suite criteria is not in hierarchy
        }

        HashMap<Boolean, HashMap<BathroomType, KDTreeNode>> kitchenMap = isSuiteMap.get(suiteCriteria);
        Set<Boolean> kitchenSetInHierarchy = kitchenMap.keySet();

        // Loop through kitchen criteria
        for (Boolean kitchenCriteria : filteringCriteria.hasKitchenCriteria()) {
          if (!kitchenSetInHierarchy.contains(kitchenCriteria)) {
            continue; // Skip if kitchen criteria is not in hierarchy
          }

          HashMap<BathroomType, KDTreeNode> bathroomTypeMap = kitchenMap.get(kitchenCriteria);
          Set<BathroomType> bathroomTypeSetInHierarchy = bathroomTypeMap.keySet();

          // Loop through bathroom type criteria
          for (BathroomType bathroomTypeCriteria : filteringCriteria.bathroomTypeCriteria()) {
            if (!bathroomTypeSetInHierarchy.contains(bathroomTypeCriteria)) {
              continue; // Skip if bathroom type criteria is not in hierarchy
            }

            // Retrieve the KDTreeNode for the fully matched criteria and filter its dorm rooms
            KDTreeNode kdTreeNode = bathroomTypeMap.get(bathroomTypeCriteria);
            Set<IDormRoom> filteredRooms = kdTreeNode.filterDormSet(filteringCriteria, 0);

            // Only add if there are filtered results
            if (!filteredRooms.isEmpty()) {
              result.addAll(filteredRooms);
            }
          } // end bathroomType loop
        } // end hasKitchen loop
      } // end isSuite loop
    } // end dormBuilding loop

    return result;
  }
}