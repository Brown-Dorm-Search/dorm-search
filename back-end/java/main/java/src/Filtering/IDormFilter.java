package Filtering;

import java.util.Set;
import DormRoom.IDormRoom;

/**
 * The {@code IDormFilter} interface defines the contract for filtering
 * collections of dorm rooms based on specified criteria. Implementations
 * of this interface should provide mechanisms to reduce a larger set of
 * dorm rooms down to those that match given filtering conditions.
 *
 * <p>This interface is intended for integration with various room-finding
 * utilities, enabling the application to dynamically apply different filters
 * or combine them as needed.</p>
 */
public interface IDormFilter {

  /**
   * Filters the provided set of dorm rooms according to the specified
   * {@link FilteringCriteria}. This method returns a subset of rooms that
   * fully match the conditions outlined in the criteria.
   *
   * @param filteringCriteria the criteria defining which rooms should be included
   *                          in the filtered result
   * @return a set of {@link IDormRoom} instances that meet all the specified
   *         filtering conditions
   */
  Set<IDormRoom> filterDormSet(FilteringCriteria filteringCriteria);
}
