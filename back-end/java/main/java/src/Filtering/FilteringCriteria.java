package src.Filtering;

import src.DormRoom.BathroomType;
import src.DormRoom.CampusLocation;
import java.util.Set;
import src.DormRoom.RoomCapacity;


/**
 * The {@code FilteringCriteria} class provides the filtering constraints for all the dorm rooms.
 * This class stores boundaries and conditions on room attributes such as building, kitchen
 * availability, suite status, bathroom type, room size, and room capacity.
 *
 * <p>These criteria are represented as sets or numeric ranges, enabling one or more potential
 * values for each filter. For example, dormBuildingCriteria could be Goddard House, Buxton House,
 * and Chapin house. This would be used to get all the rooms within Goddard, Buxton, and Chapin. The
 * ranges, such as minRoomSizeCriteria and maxRoomSizeCriteria, filter all the rooms where the
 * room's square footage is not within the range. The following are the default filtering criteria
 * <p>
 * dormBuildingCriteria: Set<"Buxton House", "Chapin House", "Diman House", "Goddard House",
 *    "Harkness House", "Marcy House", "Olney House", "Sears House", "Hope College", "Slater Hall",
 *    "Grad Center A", "Grad Center B", "Grad Center C", "Grad Center D", "Vartan Gregorian Quad A",
 *    "Vartan Gregorian Quad B", "New Pembroke #1", "New Pembroke #2", "New Pembroke #3",
 *    "Hegemen Hall", "Littlefield Hall", "Caswell Hall", "Barbour Hall", "Minden Hall",
 *    "Perkins Hall", "Young Orchard #2", "Young Orchard #4", "Young Orchard #10", "Machado House">
 * isSuiteCriteria: Set<True, False>
 * hasKitchenCriteria: Set<True, False>
 * bathroomTypeCriteria: Set<Private, Semi-Private, Communal>
 * minRoomSizeCriteria: 0
 * maxRoomSizeCriteria: 99999
 * roomCapacityCriteria: Set<One,Two,Three,Four,Five,Six>
 * </p>
 *
 * @param campusLocationCriteria  A set of campus locations within which dorm rooms should be
 *                                considered. If {@code campusLocationCriteria} is non-empty, only
 *                                rooms located in one of these buildings should pass the filtering
 *                                criteria.
 * @param isSuiteCriteria         A set of boolean values indicating whether a dorm room should be
 *                                part of a suite. If this set is non-empty, only rooms whose suite
 *                                status matches one of the values in this set should be selected.
 * @param hasKitchenCriteria      A set of boolean values indicating whether a dorm room should have
 *                                a kitchen. If this set is non-empty, only rooms whose kitchen
 *                                availability matches one of the values in this set should be
 *                                considered.
 * @param bathroomTypeCriteria    A set of {@link BathroomType} values representing acceptable
 *                                bathroom configurations. If {@code BathroomTypeCriteria} is
 *                                non-empty, only rooms whose bathroom type is included in this set
 *                                should pass the filtering criteria.
 * @param minRoomSizeCriteria     The minimum acceptable room square footage. If
 *                                {@code minRoomSizeCriteria} is set, any room smaller than this
 *                                size should be excluded from the results.
 * @param maxRoomSizeCriteria     The maximum acceptable room square footage. If
 *                                {@code maxRoomSizeCriteria} is set, any room larger than this size
 *                                should be excluded from the results.
 * @param roomCapacityCriteria    A set of {@link RoomCapacity} values representing acceptable
 *  *                             capacities of dorm rooms. If {@code roomCapacityCriteria} is
 *                                non-empty, only rooms whose roomCapacity is included in this set
 *                                should pass the filtering criteria.
 * @param floorNumberCriteria     A set of integer values indicating what floor a dorm room should
 *                                be on. If this set is non-empty, only rooms whose floor matches
 *                                one of the values in this set should be considered.
 *
 */
public record FilteringCriteria(Set<CampusLocation> campusLocationCriteria,
                                Set<Boolean> isSuiteCriteria, Set<Boolean> hasKitchenCriteria,
                                Set<BathroomType> bathroomTypeCriteria, int minRoomSizeCriteria,
                                int maxRoomSizeCriteria, Set<RoomCapacity> roomCapacityCriteria,
                                Set<Integer> floorNumberCriteria) {}
