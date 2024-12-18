package DormRoom;

import java.util.Set;

/**
 * An enum representing general campus areas where a dorm room
 * building can be located.
 */
public enum CampusLocation {
  WristonQuad,
  MainGreen,
  GradCenter,
  GregorianQuad,
//  Pembroke,
  RuthJSimmons,
  ThayerStreet,
  EastCampus,
  Machado;

  /**
   * Method that maps areas of campus to dorm buildings within that area of campus
   * @return a set of the dorm buildings within a particle area of campus
   */
  public Set<DormBuilding> dormBuildingsInCampusLocation() {
    return switch (this) {
      case WristonQuad -> Set.of(new DormBuilding(DormBuildingName.fromString("Buxton House")),
          new DormBuilding(DormBuildingName.fromString("Chapin House")),
          new DormBuilding(DormBuildingName.fromString("Diman House")),
          new DormBuilding(DormBuildingName.fromString("Goddard House")),
          new DormBuilding(DormBuildingName.fromString("Harkness House")),
          new DormBuilding(DormBuildingName.fromString("Marcy House")),
          new DormBuilding(DormBuildingName.fromString("Olney House")),
          new DormBuilding(DormBuildingName.fromString("Sears House"))
      );
      case MainGreen -> Set.of(new DormBuilding(DormBuildingName.fromString("Hope College")),
          new DormBuilding(DormBuildingName.fromString("Slater Hall")));
      case GradCenter -> Set.of(new DormBuilding(DormBuildingName.fromString("Grad Center A")),
          new DormBuilding(DormBuildingName.fromString("Grad Center B")),
          new DormBuilding(DormBuildingName.fromString("Grad Center C")),
          new DormBuilding(DormBuildingName.fromString("Grad Center D")));
      case GregorianQuad -> Set.of(new DormBuilding(DormBuildingName.fromString("Vartan Gregorian Quad A")),
          new DormBuilding(DormBuildingName.fromString("Vartan Gregorian Quad B")));
//      case Pembroke -> Set.of(new DormBuilding("New Pembroke #1"),
//          new DormBuilding("New Pembroke #2"),
//          new DormBuilding("New Pembroke #3"),
//          new DormBuilding("West House"));
      case RuthJSimmons -> Set.of(new DormBuilding(DormBuildingName.fromString("Hegeman Hall")),
          new DormBuilding(DormBuildingName.fromString("Littlefield Hall")));
      case ThayerStreet -> Set.of(new DormBuilding(DormBuildingName.fromString("Caswell Hall")));
      case EastCampus -> Set.of(new DormBuilding(DormBuildingName.fromString("Barbour Hall")),
          new DormBuilding(DormBuildingName.fromString("King House")),
          new DormBuilding(DormBuildingName.fromString("Minden Hall")),
          new DormBuilding(DormBuildingName.fromString("Perkins Hall")),
          new DormBuilding(DormBuildingName.fromString("Young Orchard 10")),
          new DormBuilding(DormBuildingName.fromString("Young Orchard 2")),
          new DormBuilding(DormBuildingName.fromString("Young Orchard 4")));
      case Machado -> Set.of(new DormBuilding(DormBuildingName.fromString("Machado House")));
    };
  }
}