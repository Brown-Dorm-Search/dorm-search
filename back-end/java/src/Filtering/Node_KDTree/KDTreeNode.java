package Filtering.Node_KDTree;

import DormRoom.DormRoom;
import java.util.List;
import java.util.Comparator;

/**
 * The {@code KDTreeNode} class represents a single node in a 3-dimensional KD-Tree
 * used to organize {@link DormRoom} instances. Each node corresponds to a dorm room,
 * and the node's position in the tree is determined by splitting the dataset along
 * one of three dimensions:
 *
 * <ul>
 *   <li>Dimension 0: {@code roomSize}</li>
 *   <li>Dimension 1: {@code roomCapacity}</li>
 *   <li>Dimension 2: {@code roomNumber}</li>
 * </ul>
 *
 * By recursively splitting the list of dorm rooms into halves at the median room
 * along the current dimension, the KD-Tree provides efficient multidimensional querying.
 *
 * <p>The KD-tree construction algorithm ensures that:
 * <ol>
 *   <li>At each node, all rooms in the left subtree have smaller values on the
 *       current dimension than the node's room.</li>
 *   <li>All rooms in the right subtree have larger or equal values on the current dimension.</li>
 * </ol>
 *
 * <p>This node implementation is immutable once constructed, as it is built from a
 * static list of {@link DormRoom} instances.</p>
 */
public class KDTreeNode {
  /**
   * The dorm room represented by this KD-Tree node. This node acts as a pivot
   * point along one of the three dimensions (roomSize, roomCapacity, or roomNumber).
   */
  private final DormRoom value;

  /**
   * The left child node, containing dorm rooms that are "less" than this node's room
   * along the current splitting dimension.
   */
  private final KDTreeNode left;

  /**
   * The right child node, containing dorm rooms that are "greater" or equal to this node's room
   * along the current splitting dimension.
   */
  private final KDTreeNode right;

  // Since we know the KD-tree is split on three dimensions, we fix K = 3.
  private static final int K = 3;


  /**
   * Constructs a {@code KDTreeNode} from a list of {@link DormRoom}s by recursively
   * selecting medians and splitting along the appropriate dimension at each level.
   * This constructor uses a default initial depth of 0.
   *
   * @param dormRoomList the list of dorm rooms to build the KD-tree node from; must not be empty
   * @throws IllegalArgumentException if {@code dormRoomList} is null or empty
   */
  public KDTreeNode(List<DormRoom> dormRoomList) throws IllegalArgumentException{
    this(dormRoomList, 0);
  }

  private KDTreeNode(List<DormRoom> rooms, int depth) throws IllegalArgumentException {
    // Checks that the dorm list is not empty
    if (rooms == null || rooms.isEmpty()) {
      throw new IllegalArgumentException(
          "Cannot create a KDTreeNode from an empty or null list of rooms.");
    }

    // Determine the axis (dimension) to split on.
    int axis = depth % K;

    // Sort the rooms
    rooms.sort(getComparatorForAxis(axis));

    // Select the median room as the node's value.
    int medianIndex = rooms.size() / 2;
    this.value = rooms.get(medianIndex);

    // Build left subtree (rooms before the median)
    if (medianIndex > 0) { // Check that there are rooms left on the medium
      List<DormRoom> leftRooms = rooms.subList(0, medianIndex);
      this.left = new KDTreeNode(leftRooms, depth + 1);
    } else {
      this.left = null;
    }

    // Build right subtree (rooms after the median)
    if (medianIndex < rooms.size() - 1) { // Check that there are rooms right on the medium
      List<DormRoom> rightRooms = rooms.subList(medianIndex + 1, rooms.size());
      this.right = new KDTreeNode(rightRooms, depth + 1);
    } else {
      this.right = null;
    }
  }


  /**
   * Creates and returns a comparator for sorting dorm rooms along a given axis.
   * The axis mapping is:
   * <ul>
   *   <li>0: roomSize</li>
   *   <li>1: roomCapacity</li>
   *   <li>2: roomNumber</li>
   * </ul>
   *
   * @param axis the axis to sort on, must be an integer between 0 and 2 inclusive
   * @return a comparator that compares dorm rooms on the specified axis
   * @throws IllegalArgumentException if the axis is out of range
   */
  private Comparator<DormRoom> getComparatorForAxis(int axis) {
    return switch (axis) {
      case 0 ->
        // Compare by roomSize
          Comparator.comparingInt(DormRoom::getRoomSize);
      case 1 ->
        // Compare by roomCapacity
          Comparator.comparingInt(DormRoom::getRoomCapacity);
      case 2 ->
        // Compare by roomNumber
          Comparator.comparingInt(DormRoom::getRoomNumber);
      default ->
          throw new IllegalArgumentException("Invalid axis: " + axis + ". Axis must be in 0,1, or 2.");
    };
  }


  /**
   * Returns the dorm room stored at this node.
   *
   * @return the dorm room for this node
   */
  public DormRoom getValue() {
    return value;
  }

  /**
   * Returns the left child node.
   *
   * @return the left child of this node, or null if none
   */
  public KDTreeNode getLeft() {
    return left;
  }

  /**
   * Returns the right child node.
   *
   * @return the right child of this node, or null if none
   */
  public KDTreeNode getRight() {
    return right;
  }
}
