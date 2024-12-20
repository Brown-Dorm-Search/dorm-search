import { SetStateAction, useState } from 'react'
// import reactLogo from './assets/react.svg'
// import viteLogo from '/vite.svg'
import './styles/App.css'
import './styles/Home.css'
import Dropdowns from './Dropdowns'
import MapBox from './Map/MapBox'
import StandardDorm from './StandardDorm'
import Suite, { DormRoomProps } from './Suite'
import InfoPage from './InfoPage'

/**
Home() displays the panels that are shown in the website. 
This includes the dorm building information on the right, the map in the middle,
and the dorm information on the right. Users cna use the ui 
to navigate between sets of information to learn more about possible 
dorms.*/
function Home() {
  //resultString is the json retreived from the backend
  const [resultString, setResultString] = useState<any>('');
  //filteredDorms is the names of the buildings that have dorm matches of the search request
  const [filteredDorms, setFilteredDorms] = useState<Array<string>>([]);
  //clickedDorm is the dorm ui that was clicked most recently, on the map or left pannel.
  const [clickedDorm, setClickedDorms] = useState<string>('');
  //how dorms on rigth panel are sorted.
  const [sortCriterion, setSortCriterion] = useState('roomSize');

  function Cap(roomCap: any) {
    if (roomCap == "One") {
      return (1);
    }
    if (roomCap == "Two") {
      return (2);
    }
    if (roomCap == "Three") {
      return (3);
    }
    if (roomCap == "Four") {
      return (4);
    }
    if (roomCap == "Five") {
      return (5);
    }
    if (roomCap == "Six") {
      return (6);
    }
    else {
      return (roomCap)
    }
  }
  // Function to handle sorting based on selected criterion
  const sortDorms = (dorms: any[]) => {
    switch (sortCriterion) {
      case 'roomCapacity':
        return dorms.sort((a, b) => Cap(a.roomCapacity) - Cap(b.roomCapacity));
      case 'floor':
        return dorms.sort((a, b) => {
          const getFloorNumber = (roomNumber: string) => {
            const match = roomNumber.match(/\d+/);
            return match ? parseInt(match[0]) : Infinity;
          };
          const floorA = getFloorNumber(a.roomNumber);
          const floorB = getFloorNumber(b.roomNumber);
          return floorA - floorB;
        });
      case 'roomSize':
        return dorms.sort((b, a) => {
          if (a.isSuite && b.isSuite) {
            return a.commonAreaSize - b.commonAreaSize;
          }
          if (a.isSuite && !b.isSuite) {
            return a.commonAreaSize - b.roomSize;
          }
          if (!a.isSuite && b.isSuite) {
            return a.roomSize - b.commonAreaSize;
          }
          return a.roomSize - b.roomSize;
        });
      default:
        return dorms;
    }
  };

  // Filter and map the resultString based on clickedDorm
  const filteringDorms = (Array.isArray(resultString) ? resultString : [])
    .filter((feature: { dormBuilding: { buildingName: string } }) => {
      if (clickedDorm === 'All') {
        return true;
      }
      const normalizedBuildingName = feature.dormBuilding.buildingName
        .toLowerCase()
        .replace(/_/g, ' ')
        .replace('grad', 'graduate');
      return normalizedBuildingName === clickedDorm.toLowerCase();
    });

  // Apply sorting to filtered dorms
  const sortedDorms = sortDorms(filteringDorms);

  /**
  * handleButtonClick sets the clickedDorm
  * in the case that a dorm button is pressed
  * on the left pannel.
  * 
  * @param name is the name of the dorm building clicked.
  */
  function handleButtonClick(name: SetStateAction<string>) {
    setClickedDorms(name);
  }


  return (
    <>
      <div>
        <div className="header-bottom">
          <Dropdowns resultStr={resultString} setResultStr={setResultString} setFilteredDorms={setFilteredDorms} FilteredDorms={filteredDorms}></Dropdowns>
        </div>
        <div className="panel-container">
          <div className="panel left">{resultString === '' && <p>Press Search to see more results</p>}
            {filteredDorms && <div>
              <p>There are matches in the following buildings:</p>
              {filteredDorms.length !== 0 && <button
                onClick={() => handleButtonClick("All")}
                className="dorm-button"
              >
                Click here to see results for all Dorm buildings
              </button>}
              {filteredDorms.map((dorm) => (
                <button
                  onClick={() => handleButtonClick(dorm)}
                  className="dorm-button"
                >
                  {dorm}
                </button>
              ))}</div>}
          </div>
          {(clickedDorm == "") &&
            <div className="panel center">
              <MapBox filteredDorms={filteredDorms} setFilteredDorms={setFilteredDorms}
                clickedDorm={clickedDorm} setClickedDorm={setClickedDorms}></MapBox>
            </div>}
          {(clickedDorm == "All") &&
            <div className="panel alt-center">
              <MapBox filteredDorms={filteredDorms} setFilteredDorms={setFilteredDorms}
                clickedDorm={clickedDorm} setClickedDorm={setClickedDorms}></MapBox>
            </div>}
          {(clickedDorm !== "All" && clickedDorm !== "") &&
            <div className="panel alt-center">
              <><p>Information Page</p><InfoPage clickedDorm={clickedDorm} setClickedDorm={setClickedDorms}></InfoPage></>
            </div>}

          {clickedDorm !== "" &&
            <div className="panel right">
              {resultString === "" && <p>Nothing Searched Yet</p>}
              {resultString !== "" &&
                <><>
                  <div>
                    <div>
                      <button onClick={() => setSortCriterion('roomCapacity')}>Sort by Room Capacity</button>
                      <button onClick={() => setSortCriterion('floor')}>Sort by Floor</button>
                      <button onClick={() => setSortCriterion('roomSize')}>Sort by Room Size</button>
                    </div>
                    {clickedDorm == "All" && <p>Below are all dorms that meet your search options</p>}
                    {clickedDorm !== "All" && <p>Below are dorms in {clickedDorm} that meet your search options</p>}
                    {sortedDorms.map((feature) => {
                      if (feature.isSuite) {
                        return (
                          <Suite
                            key={feature.roomNumber}
                            suiteNumber={feature.roomNumber}
                            floorPlanLink={feature.floorPlanLink}
                            capacity={feature.roomCapacity}
                            bathroom={feature.bathroomType}
                            kitchen={feature.hasKitchen}
                            commonAreaSize={feature.commonAreaSize}
                            dormRooms={feature.internalDormRooms.map((roomFeature: { roomNumber: any; roomCapacity: any; roomSize: any }) => ({
                              roomNumber: roomFeature.roomNumber,
                              roomType: roomFeature.roomCapacity,
                              roomSize: roomFeature.roomSize,
                            }))}
                          />
                        );
                      } else {
                        return (
                          <StandardDorm
                            key={feature.roomNumber}
                            roomNumber={feature.roomNumber}
                            floorPlanLink={feature.floorPlanLink}
                            roomType={feature.roomCapacity}
                            bathroomType={feature.bathroomType}
                            kitchenType={feature.hasKitchen}
                            roomSize={feature.roomSize}
                            building={feature.buildingName}
                          />
                        );
                      }
                    })}
                  </div>
                </><p><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /></p></>}
            </div>}
        </div>
      </div>
    </>
  )
}

export default Home