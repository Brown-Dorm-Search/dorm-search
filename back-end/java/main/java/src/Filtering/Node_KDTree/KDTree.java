package src.Filtering.Node_KDTree;

import src.DormRoom.DormBuilding;
import src.DormRoom.BathroomType;
import src.DormRoom.DormRoom;
import src.Filtering.FilteringCriteria;
import src.Filtering.Node_KDTree.KDTreeNode;
import src.Filtering.IDormFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The {@code KDTree} class is a hierarchical data structure that organizes
 * {@link DormRoom} instances according to multiple categorical attributes:
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
public class KDTree implements IDormFilter {

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
   * Constructs a new {@code KDTree} from a provided list of {@link DormRoom}s.
   * Rooms are first grouped by building, suite status, kitchen availability,
   * and bathroom type. Each group is then managed by a {@link KDTreeNode},
   * which organizes rooms in a KD-tree structure using the specified dimensionality
   * for square footage, people per washer, and room capacity.
   *
   * @param dormRoomList a list of {@link DormRoom} instances to be integrated into the structure
   * @throws RuntimeException if a dorm room references an invalid or null building or bathroom type
   */
  public KDTree(List<DormRoom> dormRoomList) throws RuntimeException {
    this.dormRoomHierarchy = new HashMap<>();

    HashMap<DormBuilding, HashMap<Boolean, HashMap<Boolean, HashMap<BathroomType, List<DormRoom>>>>> roomCategorizationMap = new HashMap<>();

    for (DormRoom dormRoom : dormRoomList) {
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

      List<DormRoom> specificRoomList = roomCategorizationMap
          .get(dormBuilding)
          .get(isSuite)
          .get(hasKitchen)
          .get(bathroomType);
      specificRoomList.add(dormRoom);
    }

    // Create KDTreeNodes for each category and populate them with the respective rooms
    for (DormBuilding dormBuilding : roomCategorizationMap.keySet()) {
      HashMap<Boolean, HashMap<Boolean, HashMap<BathroomType, List<DormRoom>>>> isSuiteMap =
          roomCategorizationMap.get(dormBuilding);
      for (Boolean isSuite : isSuiteMap.keySet()) {
        HashMap<Boolean, HashMap<BathroomType, List<DormRoom>>> hasKitchenMap = isSuiteMap.get(isSuite);
        for (Boolean hasKitchen : hasKitchenMap.keySet()) {
          HashMap<BathroomType, List<DormRoom>> bathroomTypeMap = hasKitchenMap.get(hasKitchen);
          for (BathroomType bathroomType : bathroomTypeMap.keySet()) {
            List<DormRoom> specificRoomList = bathroomTypeMap.get(bathroomType);
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
  public void ensureHierarchyStructure(DormBuilding dormBuilding, boolean isSuite, boolean hasKitchen) {
    HashMap<Boolean, HashMap<Boolean, HashMap<BathroomType, KDTreeNode>>> isSuiteMap =
        this.dormRoomHierarchy.computeIfAbsent(dormBuilding, k -> new HashMap<>());

    HashMap<Boolean, HashMap<BathroomType, KDTreeNode>> hasKitchenMap = isSuiteMap.computeIfAbsent(
        isSuite, k -> new HashMap<>());

    HashMap<BathroomType, KDTreeNode> bathroomTypeMap = hasKitchenMap.computeIfAbsent(hasKitchen,
        k -> new HashMap<>());
  }

  /**
   * Ensures that the intermediate mapping structure for categorizing dorm rooms
   * into lists (prior to creating their {@link KDTreeNode}) is fully initialized.
   * This structure, passed in as {@code roomCategorizationMap}, groups
   * {@link DormRoom} objects by building, suite status, kitchen availability,
   * and bathroom type.
   *
   * <p>This method guarantees that a {@link HashMap} is in place at each level, so that
   * {@link List<DormRoom>} instances can be safely created later without encountering null
   * references.
   *
   * @param roomCategorizationMap the nested mapping of rooms being built
   * @param dormBuilding the building associated with the dorm room
   * @param isSuite whether the dorm room is a suite
   * @param hasKitchen whether the dorm room includes a kitchen
   * @param bathroomType the type of bathroom in the dorm room
   */
  public void ensureRoomListStructure(
      HashMap<DormBuilding, HashMap<Boolean, HashMap<Boolean, HashMap<BathroomType, List<DormRoom>>>>> roomCategorizationMap,
      DormBuilding dormBuilding, boolean isSuite, boolean hasKitchen, BathroomType bathroomType) {

    HashMap<Boolean, HashMap<Boolean, HashMap<BathroomType, List<DormRoom>>>> isSuiteMap =
        roomCategorizationMap.computeIfAbsent(dormBuilding, k -> new HashMap<>());

    HashMap<Boolean, HashMap<BathroomType, List<DormRoom>>> hasKitchenMap = isSuiteMap.computeIfAbsent(
        isSuite, k -> new HashMap<>());

    HashMap<BathroomType, List<DormRoom>> bathroomTypeMap = hasKitchenMap.computeIfAbsent(
        hasKitchen, k -> new HashMap<>());

    List<DormRoom> specificRoomList = bathroomTypeMap.computeIfAbsent(bathroomType,
        k -> new ArrayList<>());
  }

  /**
   * Used to minimize the list of potential rooms until only dorms that fit the specific filtering
   * criteria are returned. This class calls the {@code KDTreeCache} which also calls this class'
   * filterDormListFunctionality method.
   *
   * @param filteringCriteria - the criteria that the user wants to filter all the dorm rooms by.
   * @return A list of rooms that match the filtering criteria
   */
  @Override
  public List<DormRoom> filterDormList(FilteringCriteria filteringCriteria) {
    return null;
  }
}
