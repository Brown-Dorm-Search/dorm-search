package Filtering;

import java.util.List;
import DormRoom.DormRoom;

/**
 * Interface that unifies the required functionality for different room filters.
 */
public interface IDormFilter {

  /**
   * Creates the data structure used to filter the dorm rooms.
   * @param roomList - the list of rooms on Brown's campus.
   * @return A data structure than can be used to efficiently filter rooms based on specific categorical data,
   *    such as building, or by a range of numerical data, such as 2-4 roommates.
   */
  IDormFilter generateFilter(List<DormRoom> roomList);

  /**
   * Used to minimize the list of potential rooms until only dorms that fit the specific filtering
   *    criteria are returned.
   * @param filteringCriteria - the criteria that the user wants to filter all the dorm rooms by.
   * @return A list of rooms that match the filtering criteria
   */
  List<DormRoom> filterDormList(List<Object> filteringCriteria);
}
