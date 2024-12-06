package Filtering.Node_KDTree;

import DormRoom.DormRoom;

/**
 * The {@code Node} class represents a node in a k-dimensional tree (kd-tree) structure.
 * Each node holds a reference to a {@link DormRoom} object and optionally references
 * to left and right child nodes. This class serves as a basic building block for
 * spatial searches and hierarchical data organization within a kd-tree.
 */
public class Node {

  /**
   * The left child of this node, representing subtrees with lower coordinate values
   * along a chosen dimension.
   */
  private Node left;

  /**
   * The right child of this node, representing subtrees with higher coordinate values
   * along a chosen dimension.
   */
  private Node right;

  /**
   * The {@link DormRoom} object stored at this node.
   * This encapsulates details about a specific dorm room.
   */
  private final DormRoom dormRoom;

  /**
   * Constructs a new {@code Node} that wraps the given {@link DormRoom} object.
   * Initially, this node has no left or right child.
   *
   * @param dormRoom the dorm room data object to be stored in this node
   */
  public Node(DormRoom dormRoom) {
    this.dormRoom = dormRoom;
    this.left = null;
    this.right = null;
  }

  /**
   * Returns the left child node of this {@code Node}.
   *
   * @return the left child node, or {@code null} if none exists
   */
  public Node getLeft() {
    return left;
  }

  /**
   * Returns the right child node of this {@code Node}.
   *
   * @return the right child node, or {@code null} if none exists
   */
  public Node getRight() {
    return right;
  }

  /**
   * Returns the {@link DormRoom} associated with this {@code Node}.
   *
   * @return the {@code DormRoom} object stored in this node
   */
  public DormRoom getDormRoom() {
    return dormRoom;
  }

  /**
   * Sets the left child of this {@code Node}.
   *
   * @param left the new left child node
   */
  public void setLeft(Node left) {
    this.left = left;
  }

  /**
   * Sets the right child of this {@code Node}.
   *
   * @param right the new right child node
   */
  public void setRight(Node right) {
    this.right = right;
  }
}
