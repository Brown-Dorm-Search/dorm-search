package Filtering;

import java.util.List;
import DormRoom.DormRoom;

/**
 * Interface that unifies the required functionality for different room filters.
 */
public interface IDormFilter {

  /**
   * Used to minimize the list of potential rooms until only dorms that fit the specific filtering
   *    criteria are returned.
   * @param filteringCriteria - the criteria that the user wants to filter all the dorm rooms by.
   * @return A list of rooms that match the filtering criteria
   */
  List<DormRoom> filterDormList(FilteringCriteria filteringCriteria);
}
