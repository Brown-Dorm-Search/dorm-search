package Filtering.Node_KDTree;

import Filtering.FilteringCriteria;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import DormRoom.IDormRoom;
import DormRoom.RoomCapacity;
import java.util.List;
import java.util.Comparator;

/**
 * The {@code KDTreeNode} class represents a single node in a 4-dimensional KD-Tree
 * used to organize {@link IDormRoom} instances. Each node corresponds to one room
 * and partitions the dataset based on one of four dimensions at each level:
 *
 * <ul>
 *   <li>Dimension 0: {@code roomSize}</li>
 *   <li>Dimension 1: {@code roomCapacity}</li>
 *   <li>Dimension 2: {@code roomNumber}</li>
 *   <li>Dimension 3: {@code floorNumber}</li>
 * </ul>
 *
 * The KD-Tree is constructed by recursively splitting a list of rooms around the median
 * in the current dimension. As a result, querying the tree (e.g., filtering) can be more
 * efficient than linear scans, especially for large datasets.
 *
 * <p>Each node stores:
 * <ol>
 *   <li>A reference to a dorm room ({@link IDormRoom}) acting as the pivot.</li>
 *   <li>References to left and right child nodes, representing subsets of the data
 *       that are respectively "less than" or "greater or equal to" the pivot room
 *       along the current dimension.</li>
 * </ol>
 *
 * <p>An empty set for any criterion implies that no dorm rooms can match that criterion.</p>
 *
 * This node is immutable after construction.
 */
public class KDTreeNode {

  /** The number of dimensions: roomSize, roomCapacity, roomNumber, floorNumber. */
  private static final int K = 4;

  private final IDormRoom value;
  private final KDTreeNode left;
  private final KDTreeNode right;

  /**
   * Constructs a {@code KDTreeNode} from a provided list of {@link IDormRoom} instances,
   * starting at depth 0.
   *
   * @param dormRoomList the non-empty list of rooms to build this KD-tree node from
   * @throws IllegalArgumentException if the provided list is null or empty
   */
  public KDTreeNode(List<IDormRoom> dormRoomList) {
    this(dormRoomList, 0);
  }

  /**
   * Recursive constructor that picks a median room based on the current axis and
   * splits the list into left and right subsets.
   *
   * @param rooms a non-empty list of rooms
   * @param depth the current tree depth, used to determine the splitting axis
   * @throws IllegalArgumentException if rooms is null or empty
   */
  private KDTreeNode(List<IDormRoom> rooms, int depth) {
    if (rooms == null || rooms.isEmpty()) {
      throw new IllegalArgumentException("Cannot create a KDTreeNode from an empty or null list of rooms.");
    }

    int axis = depth % K;
    List<IDormRoom> sortableDormList = new ArrayList<>(rooms);
    sortableDormList.sort(this.getComparatorForAxis(axis));

    int medianIndex = sortableDormList.size() / 2;
    this.value = sortableDormList.get(medianIndex);

    // Left subtree
    if (medianIndex > 0) {
      List<IDormRoom> leftRooms = sortableDormList.subList(0, medianIndex);
      this.left = new KDTreeNode(leftRooms, depth + 1);
    } else {
      this.left = null;
    }

    // Right subtree
    if (medianIndex < rooms.size() - 1) {
      List<IDormRoom> rightRooms = sortableDormList.subList(medianIndex + 1, rooms.size());
      this.right = new KDTreeNode(rightRooms, depth + 1);
    } else {
      this.right = null;
    }
  }

  /**
   * Returns a comparator that orders {@link IDormRoom} objects along the specified axis.
   *
   * <p>Axis mapping:
   * <ul>
   *   <li>0: roomSize</li>
   *   <li>1: roomCapacity</li>
   *   <li>2: roomNumber</li>
   *   <li>3: floorNumber</li>
   * </ul>
   *
   * @param axis the axis to sort on, must be between 0 and 3 inclusive
   * @return a comparator for sorting rooms on the given axis
   * @throws IllegalArgumentException if the axis is out of range
   */
  private Comparator<IDormRoom> getComparatorForAxis(int axis) {
    return switch (axis) {
      case 0 -> Comparator.comparingInt(IDormRoom::getRoomSize);
      case 1 -> Comparator.comparingInt(IDormRoom::getRoomCapacityInt);
      case 2 -> Comparator.comparing(IDormRoom::getRoomNumber);
      case 3 -> Comparator.comparingInt(IDormRoom::getFloorNumber);
      default -> throw new IllegalArgumentException("Invalid axis: " + axis + ". Axis must be 0,1,2, or 3.");
    };
  }

  /**
   * Filters the dorm rooms in this subtree based on the given {@link FilteringCriteria} and the
   * specified axis. Uses dimension-specific methods for pruning the search space.
   *
   * <p>An empty set for any criterion implies that no dorm rooms match that criterion, resulting in an empty set.</p>
   *
   * @param filteringCriteria the criteria specifying which rooms should be included
   * @param axis the current dimension axis (0 to 3)
   * @return a set of {@link IDormRoom} instances that match the filtering criteria
   * @throws IllegalArgumentException if the axis is out of range
   */
  public Set<IDormRoom> filterDormSet(FilteringCriteria filteringCriteria, int axis) {
    return switch (axis % K) {
      case 0 -> this.roomSizeFilter(filteringCriteria, axis);
      case 1 -> this.roomCapacityFilter(filteringCriteria, axis);
      case 2 -> this.roomNumberFilter(filteringCriteria, axis);
      case 3 -> this.floorNumberFilter(filteringCriteria, axis);
      default -> throw new IllegalArgumentException("Invalid axis: " + axis + ". Axis must be in [0,3].");
    };
  }

  /**
   * Filters rooms by their room size dimension. Uses min and max room size bounds from
   * {@link FilteringCriteria} to prune subtrees.
   *
   * <p>If the min and max room sizes result in no possible rooms, returns empty.
   *
   * @param filteringCriteria criteria including min and max room size
   * @param axis the current axis (0 for roomSize)
   * @return a set of matching rooms
   */
  private Set<IDormRoom> roomSizeFilter(FilteringCriteria filteringCriteria, int axis) {
    int min = filteringCriteria.minRoomSizeCriteria();
    int max = filteringCriteria.maxRoomSizeCriteria();
    int currVal = this.value.getRoomSize();

    if (min > max) {
      // No possible rooms if min > max
      return new HashSet<>();
    }

    FilteringCriteria newMinFiltering = this.getNewMinFilteringCriteria(filteringCriteria, currVal);
    FilteringCriteria newMaxFiltering = this.getNewMaxFilteringCriteria(filteringCriteria, currVal);

    return this.rangeFilter(filteringCriteria, axis, currVal, min, max, newMinFiltering, newMaxFiltering);
  }

  /**
   * Filters rooms by room capacity. If {@link FilteringCriteria#roomCapacityCriteria()} is empty,
   * that implies no possible rooms match, so returns empty immediately.
   *
   * @param filteringCriteria criteria including allowed room capacities
   * @param axis the current axis (1 for roomCapacity)
   * @return a set of matching rooms
   */
  private Set<IDormRoom> roomCapacityFilter(FilteringCriteria filteringCriteria, int axis) {
    Set<RoomCapacity> capacities = filteringCriteria.roomCapacityCriteria();

    // Compute min and max capacity
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    for (RoomCapacity rc : capacities) {
      int val = rc.toInteger();
      min = Math.min(min, val);
      max = Math.max(max, val);
    }

    return this.rangeFilter(filteringCriteria, axis, this.value.getRoomCapacityInt(), min, max, filteringCriteria, filteringCriteria);
  }

  /**
   * Filters rooms by room number. There's no set-based range, so if any set-based criterion is empty,
   * that means no rooms match. If roomNumber is unrestricted by sets, it defaults to exploring both sides.
   *
   * @param filteringCriteria criteria including allowed room numbers, if any sets are used for roomNumber
   *                          and are empty, return empty.
   * @param axis the current axis (2 for roomNumber)
   * @return a set of matching rooms
   */
  private Set<IDormRoom> roomNumberFilter(FilteringCriteria filteringCriteria, int axis) {
    Set<IDormRoom> output = this.addCurrentRoomIfValid(filteringCriteria);

    if (this.left != null) {
      output.addAll(this.left.filterDormSet(filteringCriteria, axis + 1));
    }
    if (this.right != null) {
      output.addAll(this.right.filterDormSet(filteringCriteria, axis + 1));
    }
    return output;
  }

  /**
   * Filters rooms by floor number. If {@link FilteringCriteria#floorNumberCriteria()} is empty,
   * that implies no possible rooms, so returns empty.
   *
   * @param filteringCriteria criteria including allowed floor numbers
   * @param axis the current axis (3 for floorNumber)
   * @return a set of matching rooms
   */
  private Set<IDormRoom> floorNumberFilter(FilteringCriteria filteringCriteria, int axis) {
    Set<Integer> floors = filteringCriteria.floorNumberCriteria();

    // Empty floor set means no possible rooms
    if (floors.isEmpty()) {
      return new HashSet<>();
    }

    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    for (Integer floor : floors) {
      min = Math.min(min, floor);
      max = Math.max(max, floor);
    }

    return this.rangeFilter(filteringCriteria, axis, this.value.getFloorNumber(), min, max, filteringCriteria, filteringCriteria);
  }

  /**
   * Generic range-based filtering helper.
   * <p>If sets corresponding to other criteria are empty, returns empty immediately.</p>
   *
   * @param filteringCriteria the original filtering criteria
   * @param axis the current axis
   * @param currVal the current node's dimension value
   * @param min the minimum allowed value for this dimension
   * @param max the maximum allowed value for this dimension
   * @param newMinFiltering updated criteria for the right subtree
   * @param newMaxFiltering updated criteria for the left subtree
   * @return a set of matching rooms
   */
  private Set<IDormRoom> rangeFilter(FilteringCriteria filteringCriteria, int axis, int currVal, int min, int max,
      FilteringCriteria newMinFiltering, FilteringCriteria newMaxFiltering) {

    Set<IDormRoom> output = this.addCurrentRoomIfValid(filteringCriteria);

    // Pruning logic
    // If currVal is less than the min, than only the right subtree can have higher values that might
    //   be greater than the min.
    if (currVal < min) {
      if (this.right != null) {
        output.addAll(this.right.filterDormSet(filteringCriteria, axis + 1));
      }
      return output;
    }

    // If currVal is greater than the max, than only the left subtree can have lower values that might
    //   be less than the max.
    if (currVal > max) {
      if (this.left != null) {
        output.addAll(this.left.filterDormSet(filteringCriteria, axis + 1));
      }
      return output;
    }

    // Within range, explore both sides of the subtree.
    if (this.left != null) {
      output.addAll(this.left.filterDormSet(newMaxFiltering, axis + 1));
    }
    if (this.right != null) {
      output.addAll(this.right.filterDormSet(newMinFiltering, axis + 1));
    }

    return output;
  }

  /**
   * Adds the current room to the result set if it meets all filtering criteria.
   *
   * @param filteringCriteria the criteria to validate against
   * @return a set containing the current room if it is valid, otherwise empty
   */
  private Set<IDormRoom> addCurrentRoomIfValid(FilteringCriteria filteringCriteria) {
    Set<IDormRoom> result = new HashSet<>();
    if (this.isValidDormRoom(this.value, filteringCriteria)) {
      result.add(this.value);
    }
    return result;
  }


  /**
   * Returns a new {@link FilteringCriteria} with an adjusted maximum room size.
   *
   * @param filteringCriteria the original criteria
   * @param newMaxRoomSizeCriteria the new maximum room size
   * @return a new {@link FilteringCriteria} with updated max room size
   */
  @NotNull
  private FilteringCriteria getNewMaxFilteringCriteria(FilteringCriteria filteringCriteria, int newMaxRoomSizeCriteria) {
    return new FilteringCriteria(
        filteringCriteria.campusLocationCriteria(),
        filteringCriteria.isSuiteCriteria(),
        filteringCriteria.hasKitchenCriteria(),
        filteringCriteria.bathroomTypeCriteria(),
        filteringCriteria.minRoomSizeCriteria(),
        newMaxRoomSizeCriteria,
        filteringCriteria.roomCapacityCriteria(),
        filteringCriteria.floorNumberCriteria());
  }

  /**
   * Returns a new {@link FilteringCriteria} with an adjusted minimum room size.
   *
   * @param filteringCriteria the original criteria
   * @param newMinRoomSizeCriteria the new minimum room size
   * @return a new {@link FilteringCriteria} with updated min room size
   */
  @NotNull
  private FilteringCriteria getNewMinFilteringCriteria(FilteringCriteria filteringCriteria, int newMinRoomSizeCriteria) {
    return new FilteringCriteria(
        filteringCriteria.campusLocationCriteria(),
        filteringCriteria.isSuiteCriteria(),
        filteringCriteria.hasKitchenCriteria(),
        filteringCriteria.bathroomTypeCriteria(),
        newMinRoomSizeCriteria,
        filteringCriteria.maxRoomSizeCriteria(),
        filteringCriteria.roomCapacityCriteria(),
        filteringCriteria.floorNumberCriteria());
  }

  /**
   * Checks if a given dorm room meets all specified filtering criteria. This includes:
   * <ul>
   *   <li>Room size within min and max bounds.</li>
   *   <li>Room capacity must be allowed if capacity criteria is not empty.</li>
   *   <li>Floor number must be allowed if floor criteria is not empty.</li>
   * </ul>
   *
   * <p>An empty set for any of these criteria means no possible rooms, so if that set applies
   * to this dimension, this room can never be valid.</p>
   *
   * @param dormRoom the room to validate
   * @param filteringCriteria the conditions the room must meet
   * @return true if the room meets all criteria, false otherwise
   */
  private boolean isValidDormRoom(IDormRoom dormRoom, FilteringCriteria filteringCriteria) {
    // RoomSize must be between minimum and maximum room sizes
    if (dormRoom.getRoomSize() < filteringCriteria.minRoomSizeCriteria() ||
        dormRoom.getRoomSize() > filteringCriteria.maxRoomSizeCriteria()) {
      return false;
    }

    // RoomCapacity must be a valid roomCapacity
    if (!filteringCriteria.roomCapacityCriteria().contains(dormRoom.getRoomCapacity())) {
      return false;
    }

    // the floor number must be a valid floor number
    return filteringCriteria.floorNumberCriteria().contains(dormRoom.getFloorNumber());
  }
}
