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
  Pembroke,
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
      case WristonQuad -> Set.of(new DormBuilding("Buxton House"),
          new DormBuilding("Chapin House"),
          new DormBuilding("Diman House"),
          new DormBuilding("Goddard House"),
          new DormBuilding("Harkness House"),
          new DormBuilding("Marcy House"),
          new DormBuilding("Olney House"),
          new DormBuilding("Sears House")
      );
      case MainGreen -> Set.of(new DormBuilding("Hope College"),
          new DormBuilding("Slater Hall"));
      case GradCenter -> Set.of(new DormBuilding("Grad Center A"),
          new DormBuilding("Grad Center B"),
          new DormBuilding("Grad Center C"),
          new DormBuilding("Grad Center D"));
      case GregorianQuad -> Set.of(new DormBuilding("Vartan Gregorian Quad A"),
          new DormBuilding("Vartan Gregorian Quad B"));
      case Pembroke -> Set.of(new DormBuilding("New Pembroke #1"),
          new DormBuilding("New Pembroke #2"),
          new DormBuilding("New Pembroke #3"),
          new DormBuilding("West House"));
      case RuthJSimmons -> Set.of(new DormBuilding("Hegeman Hall"),
          new DormBuilding("Littlefield Hall"));
      case ThayerStreet -> Set.of(new DormBuilding("Caswell Hall"));
      case EastCampus -> Set.of(new DormBuilding("Barbour Hall"),
          new DormBuilding("King House"),
          new DormBuilding("Minden Hall"),
          new DormBuilding("Perkins Hall"),
          new DormBuilding("Young Orchard #10"),
          new DormBuilding("Young Orchard #2"),
          new DormBuilding("Young Orchard #4"));
      case Machado -> Set.of(new DormBuilding("Machado House"));
    };
  }
}