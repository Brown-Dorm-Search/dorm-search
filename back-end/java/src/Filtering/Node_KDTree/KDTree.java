package Filtering.Node_KDTree;

import DormRoom.DormBuilding;
import DormRoom.BathroomType;
import DormRoom.DormRoom;
import Filtering.IDormFilter;
import java.util.HashMap;
import java.util.List;

/**
 * The {@code KDTree} class serves as a complex hierarchical structure
 * for organizing {@link DormRoom} instances based on multiple criteria.
 * It leverages nested {@link HashMap} structures to group rooms by their
 * building, suite status, kitchen availability, and bathroom type.
 *
 * <p>This design provides a multifaceted indexing system, allowing for
 * efficient categorization and potentially faster lookups of dorm rooms
 * that share certain attributes. Each terminal structure in the nesting
 * is a {@link KDTreeDataStructure}, which itself manages spatial or
 * multidimensional search capabilities over a subset of {@link DormRoom} instances.
 *
 * <p>The {@code KDTree} implements the {@link IDormFilter} interface,
 * which can be used to integrate additional filtering or searching
 * functionalities on the dorm data.</p>
 */
public class KDTree implements IDormFilter {

  /**
   * The nested HashMap structure storing dorm rooms. It is keyed successively by:
   * <ul>
   *   <li>{@link DormBuilding} - The building to which the dorm room belongs.</li>
   *   <li>{@code Boolean} (isSuite) - Whether the room is part of a suite.</li>
   *   <li>{@code Boolean} (hasKitchen) - Whether the room includes a kitchen.</li>
   *   <li>{@link BathroomType} - The type of bathroom available to the room.</li>
   * </ul>
   * Each leaf node in this nested structure is a {@link KDTreeDataStructure} that
   * manages the dorm rooms matching all these criteria.
   */
  private HashMap<DormBuilding, HashMap<Boolean, HashMap<Boolean, HashMap<BathroomType, KDTreeDataStructure>>>> KDTree;

  /**
   * The dimensionality parameter (K) for the underlying kd-tree data structures
   * ({@link KDTreeDataStructure}) that store the dorm rooms.
   */
  private final int K;

  /**
   * Constructs a new {@code KDTree} from a list of {@link DormRoom} instances.
   * Rooms are categorized into the nested structure by their building, suite status,
   * kitchen availability, and bathroom type. Each room is then inserted into the
   * appropriate {@link KDTreeDataStructure}.
   *
   * @param dormRoomList the list of dorm rooms to be organized into the kd-tree
   * @param K the dimensionality parameter for the kd-trees managing the rooms
   * @throws RuntimeException if a room references an invalid building name
   */
  public KDTree(List<DormRoom> dormRoomList, int K) {
    this.KDTree = new HashMap<>();
    this.K = K;

    for (DormRoom dormRoom : dormRoomList) {
      DormBuilding dormBuilding = dormRoom.getDormBuilding();
      boolean isSuite = dormRoom.isSuite();
      boolean hasKitchen = dormRoom.hasKitchen();
      BathroomType bathroomType = dormRoom.getBathroomType();

      // Ensure that the nested structure is fully initialized for these keys
      verifyValidHashmap(dormBuilding, isSuite, hasKitchen, bathroomType, dormRoom);

      // Insert the dorm room into the fully-initialized KDTreeDataStructure
      KDTreeDataStructure specificKDTree = this.KDTree.get(dormBuilding).get(isSuite).get(hasKitchen).get(bathroomType);
      specificKDTree.insert(dormRoom);
    }
  }

  /**
   * Ensures that the nested HashMap structure is fully initialized for the provided keys.
   * If any level of the nested maps does not exist, it is created and populated accordingly,
   * ultimately guaranteeing that a {@link KDTreeDataStructure} is available at the
   * specified {@link DormBuilding}, suite status, kitchen availability, and {@link BathroomType}.
   *
   * <p>If the {@link KDTreeDataStructure} for the specified combination does not exist,
   * it will be instantiated using the given {@link DormRoom}. This ensures that subsequent
   * insertions can proceed without encountering {@code null} references.</p>
   *
   * @param dormBuilding the building associated with the dorm room
   * @param isSuite whether the dorm room is part of a suite
   * @param hasKitchen whether the dorm room includes a kitchen
   * @param bathroomType the type of bathroom in the dorm room
   * @param dormRoom the dorm room to use for initializing the KDTreeDataStructure if needed
   */
  public void verifyValidHashmap(DormBuilding dormBuilding, boolean isSuite, boolean hasKitchen, BathroomType bathroomType, DormRoom dormRoom) {
    HashMap<Boolean, HashMap<Boolean, HashMap<BathroomType, KDTreeDataStructure>>> isSuiteMap = this.KDTree.get(dormBuilding);
    if (isSuiteMap == null) {
      isSuiteMap = new HashMap<>();
      KDTree.put(dormBuilding, isSuiteMap);
    }

    HashMap<Boolean, HashMap<BathroomType, KDTreeDataStructure>> hasKitchenMap = isSuiteMap.get(isSuite);
    if (hasKitchenMap == null) {
      hasKitchenMap = new HashMap<>();
      isSuiteMap.put(isSuite, hasKitchenMap);
    }

    HashMap<BathroomType, KDTreeDataStructure> bathroomTypeMap = hasKitchenMap.get(hasKitchen);
    if (bathroomTypeMap == null) {
      bathroomTypeMap = new HashMap<>();
      hasKitchenMap.put(hasKitchen, bathroomTypeMap);
    }

    KDTreeDataStructure KDTreeDataStructure = bathroomTypeMap.get(bathroomType);
    if (KDTreeDataStructure == null) {
      KDTreeDataStructure = new KDTreeDataStructure(dormRoom, this.K);
      bathroomTypeMap.put(bathroomType, KDTreeDataStructure);
    }
  }
}
