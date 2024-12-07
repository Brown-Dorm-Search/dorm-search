package Filtering;

import DormRoom.BathroomType;
import DormRoom.DormBuilding;
import java.util.Set;


/**
 * The {@code FilteringCriteria} class provides the filtering constraints for all the dorm rooms.
 * This class stores boundaries and conditions on room attributes such as building, kitchen
 * availability, suite status, bathroom type, room size, and room capacity.
 *
 * <p>These criteria are represented as sets or numeric ranges, enabling one or more potential
 * values for each filter. For example, dormBuildingCriteria could be Goddard House, Buxton House,
 * and Chapin house. This would be used to get all the rooms within Goddard, Buxton, and Chapin.
 * The ranges, such as minRoomSizeCriteria and maxRoomSizeCriteria, filter all the rooms where the
 * room's square footage is not within the range.
 *
 * The following are the default filtering criteria
 * <p>
 *   dormBuildingCriteria: Set<"Barbour Hall", "Buxton House", "Caswell Hall", "Chapin House",
 *      "Diman House", "Goddard House", "Grad Center A", "Grad Center B", "Grad Center C",
 *      "Grad Center D">
 *   isSuiteCriteria: Set<True, False>
 *   hasKitchenCriteria: Set<True, False>
 *   bathroomTypeCriteria: Set<Private, Semi-Private, Communal>
 *   minRoomSizeCriteria: -infinity
 *   maxRoomSizeCriteria: infinity
 *   minRoomCapacityCriteria: -infinity
 *   maxRoomCapacityCriteria: infinity
 * </p>
 */
public record FilteringCriteria() {
  /**
   * A set of buildings within which dorm rooms should be considered.
   * If {@code dormBuildingSet} is non-empty, only rooms located in one of
   * these buildings should pass the filtering criteria.
   */
  static Set<DormBuilding> dormBuildingCriteria;
  /**
   * A set of boolean values indicating whether a dorm room should be part of a suite.
   * If this set is non-empty, only rooms whose suite status matches one of the values
   * in this set should be selected.
   */
  static Set<Boolean> isSuiteCriteria;
  /**
   * A set of boolean values indicating whether a dorm room should have a kitchen.
   * If this set is non-empty, only rooms whose kitchen availability matches one of
   * the values in this set should be considered.
   */
  static Set<Boolean> hasKitchenCriteria;
  /**
   * A set of {@link BathroomType} values representing acceptable bathroom configurations.
   * If {@code BathroomType} is non-empty, only rooms whose bathroom type is included in this set
   * should pass the filtering criteria.
   */
  static Set<BathroomType> bathroomTypeCriteria;
  /**
   * The minimum acceptable room size. If {@code min_roomSize} is set, any room smaller than
   * this size should be excluded from the results.
   */
  static Number minRoomSizeCriteria;
  /**
   * The maximum acceptable room size. If {@code max_roomSize} is set, any room larger than
   * this size should be excluded from the results.
   */
  static Number maxRoomSizeCriteria;
  /**
   * The minimum acceptable room capacity. If {@code min_roomCapacity} is set, any room whose capacity
   * is smaller than this value should be excluded from the results.
   */
  static Number minRoomCapacityCriteria;
  /**
   * The maximum acceptable room capacity. If {@code max_roomCapacity} is set, any room whose capacity
   * is larger than this value should be excluded from the results.
   */
  static Number maxRoomCapacityCriteria;
}
