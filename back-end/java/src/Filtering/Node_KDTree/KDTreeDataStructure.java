package Filtering.Node_KDTree;

import DormRoom.BathroomType;
import java.util.HashMap;

public class KDTreeDataStructure {

  /**
   * The root of the KD Tree
   */
  private Node root;
  /**
   * The maximum dimension of this tree
   */
  private final int K;
  private HashMap<String, HashMap<Boolean, HashMap<Boolean, HashMap<BathroomType, KDTree>>>> kr;


}
