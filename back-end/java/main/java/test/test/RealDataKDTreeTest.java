package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import DormRoom.*;
import Filtering.*;

import Filtering.Node_KDTree.KDTreeWrapper;
import Parsing.RoomParser;
import java.io.IOException;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RealDataKDTreeTest {
  private List<IDormRoom> dormList;

  @BeforeEach
  public void setUp() {
    // Official Dorm List
    try {
      RoomParser parser = new RoomParser("data/PartialDataset.csv");
      this.dormList = parser.getRooms();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testEveryRoomFilter(){
    FilteringCriteria criteria = new FilteringCriteria(
        Set.of(CampusLocation.WristonQuad,
            CampusLocation.MainGreen, CampusLocation.GradCenter, CampusLocation.GregorianQuad,
            CampusLocation.RuthJSimmons, CampusLocation.ThayerStreet, CampusLocation.EastCampus,
            CampusLocation.Machado),
        Set.of(true, false),
        Set.of(true, false),
        Set.of(BathroomType.Communal, BathroomType.SemiPrivate, BathroomType.Private),
        0, 200100,
        Set.of(RoomCapacity.One, RoomCapacity.Two, RoomCapacity.Three, RoomCapacity.Four,
            RoomCapacity.Five, RoomCapacity.Six),
        Set.of(0,1,2,3,4,5,6,7,8,9)
    );

    KDTreeWrapper kd = new KDTreeWrapper(this.dormList);
    Set<IDormRoom> filteredDormList = kd.filterDormSet(criteria);

    assertEquals(548, filteredDormList.size());
  }

}
