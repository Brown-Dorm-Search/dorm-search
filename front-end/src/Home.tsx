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

function Home() {
  const [resultString, setResultString] = useState<any>('');
  const [filteredDorms, setFilteredDorms] = useState<Array<string>>([]);
  const [clickedDorm, setClickedDorms] = useState<string>('');
  //set filtered dorms to change map highlights.

  const suiteRoom1: DormRoomProps = { roomNumber: "BARBOUR 080 081", roomType: "Double", roomSize: 228 }
  const suiteRoom2: DormRoomProps = { roomNumber: "BARBOUR 080 084", roomType: "Double", roomSize: 199 }
  const suiteRooms = [suiteRoom1, suiteRoom2]


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
              <p>Below are dorms in {clickedDorm} that meet your search options</p>
              <>
                {
                  resultString
                    .filter((feature: { dormBuilding: { buildingName: string } }) => {
                      if (clickedDorm === "All") {
                        return true;
                      }
                      const normalizedBuildingName = feature.dormBuilding.buildingName
                        .toLowerCase()
                        .replace(/_/g, ' ')
                        .replace("grad", "graduate");
                      return normalizedBuildingName === clickedDorm.toLowerCase();
                    })
                    .map((feature: { isSuite: string; roomNumber: string; floorPlanLink: string; roomCapacity: string; bathroomType: string; hasKitchen: boolean; roomSize: number; internalDormRooms: { roomNumber: any; roomCapacity: any; roomCapcity: any }[]; buildingName: string }) => {
                      // Choose whether to render a Room or Suite based on isSuite
                      if (feature.isSuite == "true") {
                        return <Suite
                          suiteNumber={feature.roomNumber}
                          floorPlanLink={feature.floorPlanLink}
                          capacity={feature.roomCapacity}
                          bathroom={feature.bathroomType}
                          kitchen={feature.hasKitchen}
                          commonAreaSize={feature.roomSize}
                          dormRooms={
                            feature.internalDormRooms.map((roomFeature: { roomNumber: any; roomCapacity: any; roomCapcity: any }) => (
                              { roomNumber: roomFeature.roomNumber, roomType: roomFeature.roomCapacity, roomSize: roomFeature.roomCapcity }
                            ))}>
                        </Suite>;
                      } else {
                        return <StandardDorm
                          roomNumber={feature.roomNumber}
                          floorPlanLink={feature.floorPlanLink}
                          roomType={feature.roomCapacity}
                          bathroomType={feature.bathroomType}
                          kitchenType={feature.hasKitchen}
                          roomSize={feature.roomSize}
                          building={feature.buildingName}>
                        </StandardDorm>;
                      }
                    })}
              </>
              <p><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /></p>
            </div>}
        </div>
      </div>
    </>
  )
}

export default Home