package Filtering.Node_KDTree;

import DormRoom.DormBuilding;
import DormRoom.BathroomType;
import DormRoom.DormRoom;
import Filtering.IDormFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The {@code KDTree} class is a hierarchical data structure that organizes
 * {@link DormRoom} instances according to multiple categorical attributes:
 * {@link DormBuilding}, suite availability, kitchen availability, and
 * {@link BathroomType}.
 *
 * <p>Internally, it maintains a nested {@link HashMap} structure keyed by these
 * attributes, ultimately storing rooms in {@link KDTreeNode} instances, which
 * themselves manage spatial (multi-dimensional) searching of rooms based on the
 * given dimensionality {@code K}.
 *
 * <p>The outer layers of these nested maps categorize rooms, while the
 * {@link KDTreeNode} at the leaves is responsible for efficient multi-dimensional
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
   *   <li>{@code Boolean} (isSuite): Whether the room is part of a suite</li>
   *   <li>{@code Boolean} (hasKitchen): Whether the room has a kitchen</li>
   *   <li>{@link BathroomType}: Type of bathroom available</li>
   * </ul>
   * Each terminal node of this structure is a {@link KDTreeNode}, containing
   * a KD-tree of rooms that share all these categorical attributes.
   */
  private HashMap<DormBuilding, HashMap<Boolean, HashMap<Boolean, HashMap<BathroomType, KDTreeNode>>>> dormRoomHierarchy;

  /**
   * The dimensionality {@code K} used for constructing KD-trees in
   * {@link KDTreeNode}. This typically corresponds to the number of
   * attributes or dimensions that each KDTreeNode indexes spatially.
   */
  private final int K;

  /**
   * Constructs a new {@code KDTree} from a provided list of {@link DormRoom}s.
   * Rooms are first grouped by building, suite status, kitchen availability,
   * and bathroom type. Each group is then managed by a {@link KDTreeNode},
   * which organizes rooms in a KD-tree structure using the specified dimensionality.
   *
   * @param dormRoomList a list of {@link DormRoom} instances to be integrated into the structure
   * @param K the number of dimensions used in the KD-tree (e.g., number of attributes to index)
   * @throws RuntimeException if a dorm room references an invalid or null building or bathroom type
   */
  public KDTree(List<DormRoom> dormRoomList, int K) {
    this.dormRoomHierarchy = new HashMap<>();
    this.K = K;

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
                .put(bathroomType, new KDTreeNode(specificRoomList, K));
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
   * @param isSuite whether the dorm room is part of a suite
   * @param hasKitchen whether the dorm room includes a kitchen
   */
  public void ensureHierarchyStructure(DormBuilding dormBuilding, boolean isSuite, boolean hasKitchen) {
    HashMap<Boolean, HashMap<Boolean, HashMap<BathroomType, KDTreeNode>>> isSuiteMap = this.dormRoomHierarchy.get(dormBuilding);
    if (isSuiteMap == null) {
      isSuiteMap = new HashMap<>();
      dormRoomHierarchy.put(dormBuilding, isSuiteMap);
    }

    HashMap<Boolean, HashMap<BathroomType, KDTreeNode>> hasKitchenMap = isSuiteMap.get(isSuite);
    if (hasKitchenMap == null) {
      hasKitchenMap = new HashMap<>();
      isSuiteMap.put(isSuite, hasKitchenMap);
    }

    HashMap<BathroomType, KDTreeNode> bathroomTypeMap = hasKitchenMap.get(hasKitchen);
    if (bathroomTypeMap == null) {
      bathroomTypeMap = new HashMap<>();
      hasKitchenMap.put(hasKitchen, bathroomTypeMap);
    }
  }

  /**
   * Ensures that the intermediate mapping structure for categorizing dorm rooms
   * into lists (prior to creating their {@link KDTreeNode}) is fully initialized.
   * This structure, passed in as {@code roomCategorizationMap}, groups
   * {@link DormRoom} objects by building, suite status, kitchen availability,
   * and bathroom type.
   *
   * @param roomCategorizationMap the nested mapping of rooms being built
   * @param dormBuilding the building associated with the dorm room
   * @param isSuite whether the dorm room is part of a suite
   * @param hasKitchen whether the dorm room includes a kitchen
   * @param bathroomType the type of bathroom in the dorm room
   */
  public void ensureRoomListStructure(
      HashMap<DormBuilding, HashMap<Boolean, HashMap<Boolean, HashMap<BathroomType, List<DormRoom>>>>> roomCategorizationMap,
      DormBuilding dormBuilding, boolean isSuite, boolean hasKitchen, BathroomType bathroomType) {

    HashMap<Boolean, HashMap<Boolean, HashMap<BathroomType, List<DormRoom>>>> isSuiteMap =
        roomCategorizationMap.get(dormBuilding);
    if (isSuiteMap == null) {
      isSuiteMap = new HashMap<>();
      roomCategorizationMap.put(dormBuilding, isSuiteMap);
    }

    HashMap<Boolean, HashMap<BathroomType, List<DormRoom>>> hasKitchenMap = isSuiteMap.get(isSuite);
    if (hasKitchenMap == null) {
      hasKitchenMap = new HashMap<>();
      isSuiteMap.put(isSuite, hasKitchenMap);
    }

    HashMap<BathroomType, List<DormRoom>> bathroomTypeMap = hasKitchenMap.get(hasKitchen);
    if (bathroomTypeMap == null) {
      bathroomTypeMap = new HashMap<>();
      hasKitchenMap.put(hasKitchen, bathroomTypeMap);
    }

    List<DormRoom> specificRoomList = bathroomTypeMap.get(bathroomType);
    if (specificRoomList == null) {
      specificRoomList = new ArrayList<>();
      bathroomTypeMap.put(bathroomType, specificRoomList);
    }
  }
}
